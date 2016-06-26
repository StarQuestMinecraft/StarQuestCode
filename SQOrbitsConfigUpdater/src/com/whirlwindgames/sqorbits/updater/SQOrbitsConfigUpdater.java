package com.whirlwindgames.sqorbits.updater;

import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Map;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import com.whirlwindgames.sqorbits.updater.BadConfigException;

public class SQOrbitsConfigUpdater extends JavaPlugin {
	
	private String chatPrefix = "[SQOrbitsConfigUpdater]";
	private FileConfiguration config;
	private String hostName;
	private String dbName; 
	private Integer port;
	private String userName;
	private String password;
	private String tableName;
	private Connection connection;
	private Map<String, Integer[]> coordinates;
	private String movecraftConfigPath;

	@Override
	public void onEnable(){
		saveDefaultConfig();	
		config = getConfig();		
		
		try {
			hostName = getFromConfig("Host_Name");
			dbName = getFromConfig("Database_Name");
			port = getFromConfig("Port");
			userName = getFromConfig("Username");
			password = getFromConfig("Password");
			tableName = getFromConfig("TableName");
			movecraftConfigPath = getFromConfig("MovecraftConfigPath");
		} catch (BadConfigException e) {
			print("A critical error loading from the config means this plugin will disable itself.");
			setEnabled(false);
			closeConnection();
			return;
		}
		try {
			connectToDB();
			Statement selectStatement = connection.createStatement();
			selectStatement.execute("SELECT name, x, y, z FROM "+tableName);
			ResultSet results = selectStatement.getResultSet();
			coordinates = new HashMap<>();
			while(results.next()) coordinates.put(results.getString("name"), new Integer[]{
					Integer.valueOf(results.getInt("x")),
					Integer.valueOf(results.getInt("y")),
					Integer.valueOf(results.getInt("z"))
					});
			selectStatement.close();
		} catch (SQLException e) {
			print("An SQL exception occurred when accessing the database. Stack trace: ");
			e.printStackTrace();			
			print("The plugin will now disable itself.");
			setEnabled(false);
			closeConnection();
			return;
		}
		File movecraftConfigFile = new File(getDataFolder(), movecraftConfigPath);
		if(!movecraftConfigFile.exists()){
			print("Critical error! The movecraft config file specified in the config does not exist. Disabling...");
			setEnabled(false);
			closeConnection();
			return;
		}	//else
		YamlConfiguration movecraftConfig = YamlConfiguration.loadConfiguration(movecraftConfigFile);
		if(movecraftConfig==null){
			print("Critical error! The movecraft config file specified in the config is not a YAML configuration. Disabling...");
			setEnabled(false);
			closeConnection();
			return;
		}
		for(String planetName : coordinates.keySet()){
			movecraftConfig.set(planetName+".Xcoord", coordinates.get(planetName)[0]);
			movecraftConfig.set(planetName+".Ycoord", coordinates.get(planetName)[1]);
			movecraftConfig.set(planetName+".Zcoord", coordinates.get(planetName)[2]);
		}
		try {
			movecraftConfig.save(movecraftConfigFile);
		} catch (IOException e1) {
			print("An IOException occurred savign the movecraft config. Stack trace: ");
			e1.printStackTrace();
		}
		closeConnection();
		
	}
	
	private void closeConnection(){
		try {
			connection.close();
		} catch (SQLException e) {
			print("An error occurred closing the connection to the SQL database. Stack trace: ");
			e.printStackTrace();
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
		String dbURL = "jdbc:mysql://" + hostName + ":" + port + "/" + dbName;
		if ((userName.equalsIgnoreCase("")) && (password.equalsIgnoreCase(""))) connection = DriverManager.getConnection(dbURL);
		else connection = DriverManager.getConnection(dbURL, userName, password);
		
	}
	
	private void print(String msg) {
		getServer().getLogger().info(chatPrefix+" "+msg);
	}
}
