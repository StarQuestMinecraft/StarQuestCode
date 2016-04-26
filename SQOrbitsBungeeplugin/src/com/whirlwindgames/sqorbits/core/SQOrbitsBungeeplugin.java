package com.whirlwindgames.sqorbits.core;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

import com.google.common.io.ByteStreams;

import net.md_5.bungee.api.plugin.Plugin;
import net.md_5.bungee.config.Configuration;
import net.md_5.bungee.config.ConfigurationProvider;
import net.md_5.bungee.config.YamlConfiguration;

public class SQOrbitsBungeeplugin extends Plugin {
	private static final double radiansPerDegree = Math.PI/180;		//useful math constant
	private String chatPrefix = "[SQOrbitsBungeeplugin]";
	private File configFile;
	private Configuration config;
	
	private Map<String, PlanetData> planetData;
	private Set<String> planetsToDelete;
	
	private String hostName;
	private String dbName;
	private Integer port;
	private String userName;
	private String password;
	private String tableName;
	
	private String dbURL;
	private Connection connection;
	
	
    @Override
    public void onEnable() {    	
    	planetData = new HashMap<>();
		planetsToDelete = new HashSet<>();
    	try {
			setupFromConfig();
		} catch (BadConfigException ex) {
			print("Errors setting up from config mean the plugin will stop running.");
	    	return;
		}    	
    	try {
			connectToDB();
			Driver driver = (Driver) Class.forName("com.mysql.jdbc.Driver").newInstance();
			DriverManager.registerDriver(driver);
			
			String tableCreationString = 
					"CREATE TABLE IF NOT EXISTS "+tableName+" ("
					+ "name VARCHAR(32) NOT NULL,"
					+ "rotation DOUBLE PRECISION DEFAULT 0,"
					+ "system_name VARCHAR(32) NOT NULL,"
					+ "world_name VARCHAR(32) NOT NULL,"
					+ "x INTEGER DEFAULT 0,"
					+ "y INTEGER DEFAULT 0,"
					+ "z INTEGER DEFAULT 0,"
					+ "region_max_x INTEGER DEFAULT 0,"
					+ "region_max_y INTEGER DEFAULT 0,"
					+ "region_max_z INTEGER DEFAULT 0,"
					+ "region_min_x INTEGER DEFAULT 0,"
					+ "region_min_y INTEGER DEFAULT 0,"
					+ "region_min_z INTEGER DEFAULT 0,"					
					+ "schematic_path VARCHAR(256) NOT NULL,"
					+ "PRIMARY KEY(name)"
					+");";			
			PreparedStatement tableCreationStatement = connection.prepareStatement(tableCreationString);	
			tableCreationStatement.execute();
			tableCreationStatement.close();
			print("SQL table creation/checking was successful.");
			
			String selectPlanetsString = "SELECT name, rotation FROM "+tableName+";";					
			Statement selectPlanetsStatement = connection.createStatement();
			selectPlanetsStatement.execute(selectPlanetsString);
			ResultSet dbPlanets = selectPlanetsStatement.getResultSet();	
			
			planetsToDelete = new HashSet<>();
			while(dbPlanets.next()){	//iterates through all rows in set
				String planetName = dbPlanets.getString("name");
				PlanetData data = planetData.get(planetName);				
				//if planet not present in config (meaning it is old and should be deleted from the DB)
				//marks planet for deletion in DB
				if(data==null)	planetsToDelete.add(planetName);
				//else (not a new planet as it is in DB but not one to be deleted as still in config)
				//changes the rotation value of the planet so it is moved
				else data.rotation = dbPlanets.getDouble("rotation")+data.speed;	
			}
			selectPlanetsStatement.close();	
			
		} catch (SQLException | InstantiationException | IllegalAccessException | ClassNotFoundException ex) {
			print("An error connecting to or reading from the database mean the plugin will stop running. Stack trace: ");
			ex.printStackTrace();
			return;
		}
    	deleteOldPlanets();
    	updateDB();  	
    }
    
	private void updateDB() {
		String planetMergeString = ""
				+ "INSERT INTO "+tableName+" "
				+ "("
				+ "    name, "
				+ "    rotation, "
				+ "    system_name, "
				+ "    world_name, "
				+ "    x, "
				+ "    y, "
				+ "    z, "
				+ "    region_max_x, "
				+ "    region_max_y, "
				+ "    region_max_z, "
				+ "    region_min_x, "
				+ "    region_min_y, "
				+ "    region_min_z, "				
				+ "    schematic_path"
				+ ") VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)"
				+ "ON DUPLICATE KEY UPDATE "
				+ "    name=VALUES(name),"
				+ "    rotation=VALUES(rotation),"
				+ "    system_name=VALUES(system_name),"
				+ "    world_name=VALUES(world_name),"
				+ "    x=VALUES(x),"
				+ "    y=VALUES(y),"
				+ "    z=VALUES(z),"
				+ "    region_max_x=VALUES(region_max_x),"
				+ "    region_max_y=VALUES(region_max_y),"
				+ "    region_max_z=VALUES(region_max_z),"
				+ "    region_min_x=VALUES(region_min_x),"
				+ "    region_min_y=VALUES(region_min_y),"
				+ "    region_min_z=VALUES(region_min_z),"				
				+ "    schematic_path=VALUES(schematic_path)"
				+ "";
		
		for(String planetName : planetData.keySet()){
			PlanetData data = planetData.get(planetName);
			int[] centreCoords = calculatePlanetCoords(planetName, planetData.get(planetName).rotation);
			int centreX = centreCoords[0];
			int centreY = centreCoords[1];
			int centreZ = centreCoords[2];
			int maxX = centreX + data.relativeRegionMaxCornerX;
			int maxY = centreY + data.relativeRegionMaxCornerY;
			int maxZ = centreZ + data.relativeRegionMaxCornerZ;
			int minX = centreX + data.relativeRegionMinCornerX;
			int minY = centreY + data.relativeRegionMinCornerY;
			int minZ = centreZ + data.relativeRegionMinCornerZ;
			
			try {
				PreparedStatement statement = connection.prepareStatement(planetMergeString);
				statement.setString(1, planetName);
				statement.setDouble(2, data.rotation);
				statement.setString(3, data.systemName);
				statement.setString(4, data.worldName);
				statement.setInt(5, centreX);
				statement.setInt(6, centreY);
				statement.setInt(7, centreZ);
				statement.setInt(8,  maxX);
				statement.setInt(9, maxY);
				statement.setInt(10, maxZ);
				statement.setInt(11, minX);
				statement.setInt(12, minY);
				statement.setInt(13, minZ);
				statement.setString(14, data.schematicPath);
				statement.execute();
				statement.close();
			} catch (SQLException e) {
				print("An error merging data for planet '"+planetName+"' into the database. "
						+ "As a result the planet will not be added. Stack trace: ");
				e.printStackTrace();
			}
		}
	}

	
	//NB: this method does not delete the planet structure+region in the world, 
	//it only cleans it from the database so it won't move anymore
	private void deleteOldPlanets(){
		String planetDeletionString = "DELETE FROM "+tableName+" WHERE name=?";
		for(String planetName : planetsToDelete){
			try {
				PreparedStatement planetDeletionStatement = connection.prepareStatement(planetDeletionString);
				planetDeletionStatement.setString(1, planetName);
				planetDeletionStatement.execute();			
				planetDeletionStatement.close();
			} catch (SQLException e) {
				print("An SQLException occured when attempting to delete the planet '"+planetName+"' from the database. "
						+ "As a result, the planet will not be deleted from the database.");
			}
		}
	}

	//fetches all data but rotation from respective PlanetData object stored in map
	private int[] calculatePlanetCoords(String planetName, double rotation){
		PlanetData data = planetData.get(planetName);
		double sin = Math.sin(rotation*radiansPerDegree);
		double cos = Math.cos(rotation*radiansPerDegree);
		double rawCentreX = data.blocksFromSun*cos+data.sunX;
		double rawCentreZ = data.blocksFromSun*sin+data.sunZ;		
		int x = (int) Math.round(rawCentreX);
		int z = (int) Math.round(rawCentreZ); 
		int y = data.orbitY;
		return new int[]{x, y, z};
	}

	private void print(String msg) {
		getProxy().getLogger().info(chatPrefix+msg);
	}

	private void setupFromConfig() throws BadConfigException { 		
		saveDefaultConfig();				
		try {
			config = ConfigurationProvider.getProvider(YamlConfiguration.class).load(configFile);
		} catch (IOException e) {
			print("An error occurred loading the config file.");
			e.printStackTrace();
			return;
		}	
		
		hostName = getFromConfig("Host_Name");
		dbName = getFromConfig("Database_Name");
		port = getFromConfig("Port");
		userName = getFromConfig("Username");
		password = getFromConfig("Password");
		tableName = getFromConfig("TableName");
		
		LinkedHashMap<String, Object> planetSection = getFromConfig("Planets");
		Collection<String> planetNames = planetSection.keySet();
		
		for(String planetName : planetNames){
			PlanetData planet = new PlanetData();
			
			planet.sunX = getFromConfig("Planets."+planetName+".SunCoords.x");
			planet.sunZ = getFromConfig("Planets."+planetName+".SunCoords.z");
			
			planet.systemName = getFromConfig("Planets."+planetName+".SystemName");
			planet.worldName = getFromConfig("Planets."+planetName+".WorldName");
			planet.orbitY = getFromConfig("Planets."+planetName+".OrbitCenterY");
			planet.speed = getFromConfig("Planets."+planetName+".Speed");
			planet.blocksFromSun = getFromConfig("Planets."+planetName+".BlocksFromSun");
			
			planet.relativeRegionMinCornerX = getFromConfig("Planets."+planetName+".RelativeRegionMinCorner.x");
			planet.relativeRegionMinCornerY = getFromConfig("Planets."+planetName+".RelativeRegionMinCorner.y");
			planet.relativeRegionMinCornerZ = getFromConfig("Planets."+planetName+".RelativeRegionMinCorner.z");
			
			planet.relativeRegionMaxCornerX = getFromConfig("Planets."+planetName+".RelativeRegionMaxCorner.x");
			planet.relativeRegionMaxCornerY = getFromConfig("Planets."+planetName+".RelativeRegionMaxCorner.y");
			planet.relativeRegionMaxCornerZ = getFromConfig("Planets."+planetName+".RelativeRegionMaxCorner.z");
			
			planet.schematicPath = getFromConfig("Planets."+planetName+".SchematicPath");
			
			planet.rotation = getFromConfig("Planets."+planetName+".InitialRotation");	
			
			planetData.put(planetName, planet);
		}
		
		
	}
	
	@SuppressWarnings("unchecked")
	private <T extends Object> T getFromConfig(String configName) throws BadConfigException{
		Object fromConfig = config.get(configName);
		if(fromConfig==null){
			print("Error retrieving value from config field '"+configName+"': is null.");
			throw new BadConfigException();
		}	
		T tObject;
		try {
			tObject = (T)fromConfig;
		} catch (ClassCastException ex){
			print("Error retrieving value from config field '"+configName+"': invalid datatype.");
			throw new BadConfigException();
		}
		return tObject;		
	}

	private void connectToDB() throws SQLException{
		dbURL = "jdbc:mysql://" + hostName + ":" + port + "/" + dbName;
		if ((userName.equalsIgnoreCase("")) && (password.equalsIgnoreCase(""))) connection = DriverManager.getConnection(dbURL);
		else connection = DriverManager.getConnection(dbURL, userName, password);
		
	}
	
	private void saveDefaultConfig() {
		File dataFolder = getDataFolder();
		if(!dataFolder.exists()) dataFolder.mkdir();
		configFile = new File(dataFolder, "config.yml");
		if(!configFile.exists()){
			try {
				configFile.createNewFile();				
				ByteStreams.copy(getResourceAsStream("defaultConfig.yml"), new FileOutputStream(configFile));
			} catch (IOException e) {
				print("An error occurred creating the default config file.");
				e.printStackTrace();
			}
		}
	}

}