package com.whirlwindgames.sqorbits.mover;

import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Map;

import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;
import org.dynmap.DynmapAPI;
import org.dynmap.markers.Marker;
import org.dynmap.markers.MarkerAPI;
import org.dynmap.markers.MarkerIcon;
import org.dynmap.markers.MarkerSet;

import com.sk89q.worldedit.BlockVector;
import com.sk89q.worldedit.EditSession;
import com.sk89q.worldedit.MaxChangedBlocksException;
import com.sk89q.worldedit.Vector;
import com.sk89q.worldedit.WorldEdit;
import com.sk89q.worldedit.blocks.BaseBlock;
import com.sk89q.worldedit.blocks.BlockID;
import com.sk89q.worldedit.bukkit.BukkitUtil;
import com.sk89q.worldedit.bukkit.WorldEditPlugin;
import com.sk89q.worldedit.world.DataException;
import com.sk89q.worldedit.regions.Polygonal2DRegion;
import com.sk89q.worldedit.regions.Region;
import com.sk89q.worldedit.schematic.SchematicFormat;
import com.sk89q.worldguard.bukkit.WorldGuardPlugin;
import com.sk89q.worldguard.protection.managers.RegionManager;
import com.sk89q.worldguard.protection.regions.ProtectedCuboidRegion;
import com.sk89q.worldguard.protection.regions.ProtectedRegion;
import com.whirlwindgames.sqorbits.mover.BadConfigException;


public class SQOrbitsPlanetMover extends JavaPlugin {
	
	private String chatPrefix = "[SQOrbitsPlanetMover]";
	private FileConfiguration config;
	private String hostName;
	private String dbName; 
	private Integer port;
	private String userName;
	private String password;
	protected String tableName;
	private Connection connection;
	
	private String systemName;
	private String worldName;
	private World world;
	
	private RegionManager rgMgr;
	
	private Map<String, Integer[]> centreCoordinates;
	private Map<String, Integer[]> minRegionCoordinates;
	private Map<String, Integer[]> maxRegionCoordinates;
	private Map<String, String> schematicPaths;

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
			
			systemName = getFromConfig("SystemName");
			worldName = getFromConfig("WorldName");
		} catch (BadConfigException e) {
			dieWithError("Critical error loading from config");
			return;
		}
		
		getCommand("whereis").setExecutor(new WhereisExecutor(this));
		
		world = Bukkit.getWorld(worldName);
		if(world == null)	{
			dieWithError("The world '"+worldName+"' was missing.");
			return;
		}		
		try {
			connection = getConnection();
			Statement selectStatement = connection.createStatement();
			String sqlString = "SELECT "
					+ "name, "
					+ "x, "
					+ "y, "
					+ "z, "
					+ "region_max_x, "
					+ "region_max_y, "
					+ "region_max_z, "
					+ "region_min_x, "
					+ "region_min_y, "
					+ "region_min_z, "					
					+ "schematic_path "
					+ "FROM "+tableName+" "
					+ "WHERE system_name='"+systemName+"'";	//note: injectable, assuming config is trustworthy
			selectStatement.execute(sqlString);				
			ResultSet results = selectStatement.getResultSet();
			minRegionCoordinates = new HashMap<>();
			maxRegionCoordinates = new HashMap<>();
			centreCoordinates = new HashMap<>();
			schematicPaths = new HashMap<>();
			while(results.next()) {
				String name = results.getString("name");
				centreCoordinates.put(name, new Integer[]{
						Integer.valueOf(results.getInt("x")),
						Integer.valueOf(results.getInt("y")),
						Integer.valueOf(results.getInt("z"))
						});
				minRegionCoordinates.put(name, new Integer[]{
					Integer.valueOf(results.getInt("region_min_x")),
					Integer.valueOf(results.getInt("region_min_y")),
					Integer.valueOf(results.getInt("region_min_z"))
					});
				maxRegionCoordinates.put(name, new Integer[]{
						Integer.valueOf(results.getInt("region_max_x")),
						Integer.valueOf(results.getInt("region_max_y")),
						Integer.valueOf(results.getInt("region_max_z"))
						});
				schematicPaths.put(name, results.getString("schematic_path"));
			}
			selectStatement.close();
		} catch (SQLException e) {
			print("An SQL exception occurred when accessing the database. Stack trace: ");
			e.printStackTrace();			
			dieWithError("");
			return;
		}
		closeConnection();
		
		Plugin wePlugin = Bukkit.getPluginManager().getPlugin("WorldEdit");		
		if(wePlugin == null || !(wePlugin instanceof WorldEditPlugin)) {
			print("Missing world edit, won't move planet stuctures.");
			return;
		} else {
			Plugin wgPlugin = Bukkit.getPluginManager().getPlugin("WorldGuard");
			if(wgPlugin == null || !(wgPlugin instanceof WorldGuardPlugin)) {
				print("Missing world guard, won't move planet regions and won't clear the old planet structure.");				
				pasteStructures();
				return;
			} else {
				rgMgr = ((WorldGuardPlugin)wgPlugin).getRegionManager(world);
				clearOldRegions();
				pasteStructures();
				createNewRegions();
			}
			
		}
		
		updateDynmap();
	}

	private void createNewRegions() {
		for (String planetName : centreCoordinates.keySet()){
			Integer[] minCoords = minRegionCoordinates.get(planetName);
			BlockVector minVector = new BlockVector(minCoords[0], minCoords[1], minCoords[2]);
			Integer[] maxCoords = maxRegionCoordinates.get(planetName);
			BlockVector maxVector = new BlockVector(maxCoords[0], maxCoords[1], maxCoords[2]);
			ProtectedCuboidRegion newRegion = new ProtectedCuboidRegion(planetName, minVector, maxVector);
			rgMgr.addRegion(newRegion);
		}	
	}
	
	//below methods deprecated because can't find method to retrieve new com.sk89q.worldedit.World
	@SuppressWarnings("deprecation")
	private void clearOldRegions() {
		for (String planetName : centreCoordinates.keySet()){
			ProtectedRegion wgRegion = rgMgr.getRegion(planetName);
			if(wgRegion == null){
				print("No WG region forund for planet '"+planetName+"', skipping region deletion.");
				continue;
			}
			com.sk89q.worldedit.LocalWorld weWorld = BukkitUtil.getLocalWorld(world);
			EditSession es = WorldEdit.getInstance().getEditSessionFactory().getEditSession(weWorld, -1);
			Region weRegion = new Polygonal2DRegion(weWorld,
					wgRegion.getPoints(), 
					wgRegion.getMinimumPoint().getBlockY(),
					wgRegion.getMaximumPoint().getBlockY());
			try {
				es.setBlocks(weRegion, new BaseBlock(BlockID.AIR));
			} catch (MaxChangedBlocksException e) {
				// should never happen as limit is -1
				print("Error deleting blocks with WE for planet '"+planetName+"', max block limit reached.");
			}
			rgMgr.removeRegion(planetName);
		}	
	}

	@SuppressWarnings("deprecation")
	private void pasteStructures() {
		for (String planetName : centreCoordinates.keySet()){
			File schematicFile = new File(schematicPaths.get(planetName));
			com.sk89q.worldedit.CuboidClipboard cc = null;			
			try {
				cc = SchematicFormat.getFormat(schematicFile).load(schematicFile);
			} catch (DataException | NullPointerException | IOException ex){
				print("Error occurred loading schematic for planet '"+planetName+"'. This schematic will not be pasted. "
						+"Are you sure that the path to the schematic is correct? Stack trace:");
				ex.printStackTrace();
				continue;
			}
			com.sk89q.worldedit.LocalWorld weWorld = BukkitUtil.getLocalWorld(world);
			EditSession es = WorldEdit.getInstance().getEditSessionFactory().getEditSession(weWorld, -1);
			Integer[] centreCoords = centreCoordinates.get(planetName);
			try {
				cc.paste(es, new Vector(centreCoords[0], centreCoords[1], centreCoords[2]), true);
			} catch (MaxChangedBlocksException ex) {
				// should never happen as limit is -1
				print("Error pasting schematic with WE for planet '"+planetName+"', max block limit reached.");
			}
		}
	}
	//closes DB connection, disables plugin, and says so at the end of the passed message.
	private void dieWithError(String string) {
		print(string+ " The plugin will now disable itself");
		setEnabled(false);
		closeConnection();		
	}

	private void updateDynmap() {
		
		Plugin dynmapPlugin = Bukkit.getServer().getPluginManager().getPlugin("dynmap");
		if (dynmapPlugin == null || !(dynmapPlugin instanceof DynmapAPI)){
			print("Critical error accessing the dynmap plugin, dynmap will not be used.");
			return;
		}	//dynmap contract assumes none of the below null
		DynmapAPI dynmapAPI = (DynmapAPI)dynmapPlugin;	
		MarkerAPI markerAPI = dynmapAPI.getMarkerAPI();
		MarkerIcon planetIcon = markerAPI.getMarkerIcon("world");
		
		MarkerSet markers = markerAPI.getMarkerSet("markers");
		for (String name : centreCoordinates.keySet()){
			Integer[] coords = centreCoordinates.get(name);
			Marker planetMarker = markers.findMarker(name);
			
			if (planetMarker == null){
				markers.createMarker(name, name, worldName, coords[0], coords[1], coords[2], planetIcon,true);
			} else {
				planetMarker.setLocation(worldName, coords[0], coords[1], coords[2]);
			}
		}	
		
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

	protected Connection getConnection() throws SQLException{
		String dbURL = "jdbc:mysql://" + hostName + ":" + port + "/" + dbName;
		if ((userName.equalsIgnoreCase("")) && (password.equalsIgnoreCase(""))) return DriverManager.getConnection(dbURL);
		else return DriverManager.getConnection(dbURL, userName, password);
		
	}
	
	private void print(String msg) {
		getServer().getLogger().info(chatPrefix+" "+msg);
	}
}
