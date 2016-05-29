package com.whirlwindgames.dibujaron.sqempire;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.countercraft.movecraft.listener.BlockListener;
import net.countercraft.movecraft.listener.CommandListener;
import net.countercraft.movecraft.listener.EntityListener;
import net.countercraft.movecraft.listener.InteractListener;
import net.countercraft.movecraft.listener.InventoryListener;
import net.milkbowl.vault.economy.Economy;
import net.milkbowl.vault.permission.Permission;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitScheduler;
import org.dynmap.DynmapAPI;
import org.dynmap.markers.AreaMarker;
import org.dynmap.markers.Marker;
import org.dynmap.markers.MarkerAPI;
import org.dynmap.markers.MarkerIcon;
import org.dynmap.markers.MarkerSet;
import org.dynmap.markers.impl.MarkerAPIImpl;

import com.sk89q.worldedit.BlockVector2D;
import com.sk89q.worldguard.bukkit.WGBukkit;
import com.sk89q.worldguard.bukkit.WorldGuardPlugin;
import com.sk89q.worldguard.domains.DefaultDomain;
import com.sk89q.worldguard.protection.regions.ProtectedPolygonalRegion;

public class SQEmpire extends JavaPlugin{
	
	private static SQEmpire instance;
	private static Planet thisPlanet;
	public static Economy economy;
	public static Permission permission;
	public static WorldGuardPlugin worldGuardPlugin;
	public static FileConfiguration config;
	public static List<Territory> territories = new ArrayList<Territory>();
	public static DynmapAPI dynmapAPI;
	public static MarkerAPI markerAPI;
	public static MarkerSet markerSet;
	public static Map<Player, CapturePoint> capturing = new HashMap<Player, CapturePoint>();
	
	@Override
	public void onEnable(){

		instance = this;
		EmpireCommand e = new EmpireCommand();
		getCommand("empire").setExecutor(e);
		getCommand("playersendempire").setExecutor(e);
		getCommand("empirereset").setExecutor(e);
		thisPlanet = Planet.fromName(Bukkit.getServerName());
		PluginManager pm = getServer().getPluginManager();
		pm.registerEvents( new BetaListener(), this );
		pm.registerEvents( new PlayerHandler(), this );
	
        RegisteredServiceProvider<Economy> economyProvider = getServer().getServicesManager().getRegistration(net.milkbowl.vault.economy.Economy.class);
        if (economyProvider != null) {
            economy = economyProvider.getProvider();
        } else {
        	System.out.println("No economy found!");
        }
        RegisteredServiceProvider<Permission> permissionProvider = getServer().getServicesManager().getRegistration(net.milkbowl.vault.permission.Permission.class);
        if (permissionProvider != null) {
            permission = permissionProvider.getProvider();
        } else {
        	System.out.println("No permissions found!");
        }
        
        config = this.getConfig();
        
		saveDefaultConfig();
		saveConfig();
        
        ConfigurationSection selection = config.getConfigurationSection("regions");
        
        for (String region : config.getConfigurationSection("regions").getKeys(false)) {

        	Territory territory = new Territory();
        	
        	territory.name = region;
        	
        	territory.owner = Empire.fromString(config.getString("regions." + region + ".owner"));
        	territory.connections = config.getStringList("regions." + region + ".connections");
        	
        	List<String> points = (List<String>) config.getConfigurationSection("regions." + region + ".capture points").getKeys(false);
        	
        	for (int i = 0; i < points.size(); i ++) {
        		
        		CapturePoint capturePoint = new CapturePoint();
        		
        		capturePoint.owner = Empire.fromString(config.getString("regions." + region + ".capture points." + points.get(i) + ".owner"));
        		
        		String[] pos = config.getString("regions." + region + ".capture points." + points.get(i) + ".position").split(",");
        		
        		capturePoint.x = Integer.parseInt(pos[0]);
        		capturePoint.y = Integer.parseInt(pos[1]);
        		capturePoint.z = Integer.parseInt(pos[2]);
        		
        		capturePoint.name = territory.name + "-" + (i + 1);
        		
        		territory.capturePoints.add(capturePoint);
        		
        	}
        	
        	for (String point : config.getStringList("regions." + region + ".points")) {

        		String[] pos = point.split(",");
        		
        		BlockVector2D vector = new BlockVector2D(Integer.parseInt(pos[0]), Integer.parseInt(pos[1]));
        		
        		territory.points.add(vector);
        		
        	}
        	
        	territories.add(territory);
        	
        }

        worldGuardPlugin = WGBukkit.getPlugin();
        dynmapAPI = (DynmapAPI) Bukkit.getServer().getPluginManager().getPlugin("dynmap");
        markerAPI = dynmapAPI.getMarkerAPI();
        markerSet = markerAPI.getMarkerSet("empires.markerset");
        if (markerSet == null) {
        	
        	markerSet = markerAPI.createMarkerSet("empires.markerset", "Empires", null, false);
        	
        }
        
        for (final Territory territory : territories) {
        	
            ProtectedPolygonalRegion region = (ProtectedPolygonalRegion) worldGuardPlugin.getRegionManager(Bukkit.getWorlds().get(0)).getRegion(territory.name);
        	
            if (region != null) {
            	
            	if (!region.getPoints().equals(territory.points)) {
            		
            		worldGuardPlugin.getRegionManager(Bukkit.getWorlds().get(0)).removeRegion(territory.name);
            		worldGuardPlugin.getRegionManager(Bukkit.getWorlds().get(0)).addRegion(new ProtectedPolygonalRegion(territory.name, territory.points, 0, Bukkit.getWorlds().get(0).getMaxHeight()));
            		
            	}
            	
            } else {
            	
            	worldGuardPlugin.getRegionManager(Bukkit.getWorlds().get(0)).addRegion(new ProtectedPolygonalRegion(territory.name, territory.points, 0, Bukkit.getWorlds().get(0).getMaxHeight()));
            	
            }
            
            region = (ProtectedPolygonalRegion) worldGuardPlugin.getRegionManager(Bukkit.getWorlds().get(0)).getRegion(territory.name);
            
    		DefaultDomain domain = new DefaultDomain();
    		domain.addGroup("arator");
    		domain.addGroup("yavari");
    		domain.addGroup("requiem");
            
    		region.setMembers(domain);
    		
    		double[] xBoundaries = new double[territory.points.size()];
    		double[] zBoundaries = new double[territory.points.size()];
    		
    		for (int i = 0; i < territory.points.size(); i ++) {
    			
    			xBoundaries[i] = territory.points.get(i).getX();
    			zBoundaries[i] = territory.points.get(i).getZ();
    			
    		}
    		
            AreaMarker areaMarker = markerSet.createAreaMarker(territory.name, territory.name, false, Bukkit.getWorlds().get(0).getName(), xBoundaries, zBoundaries, false);
            
            areaMarker.setLabel(territory.name, true);
            areaMarker.setDescription("Name: " + territory.name + "<br/> Owner: " + territory.owner.getName());
            
            if (territory.owner.equals(Empire.ARATOR)) {
            	
            	areaMarker.setFillStyle(0.5, Integer.parseInt("001eff", 16));
            	areaMarker.setLineStyle(3, 1, Integer.parseInt("001eff", 16));
            	
            } else if (territory.owner.equals(Empire.YAVARI)) {
            	
            	areaMarker.setFillStyle(0.5, Integer.parseInt("a800ff", 16));
            	areaMarker.setLineStyle(3, 1, Integer.parseInt("a800ff", 16));
            	
            } else if (territory.owner.equals(Empire.REQUIEM)) {
            	
            	areaMarker.setFillStyle(0.5, Integer.parseInt("ff0000", 16));
            	areaMarker.setLineStyle(3, 1, Integer.parseInt("ff0000", 16));
            	
            }
            		
            for (int i = 0; i < territory.capturePoints.size(); i ++) {
            	
            	CapturePoint capturePoint = territory.capturePoints.get(i);
            	
            	int xMultiplier = capturePoint.x / capturePoint.x;
            	int zMultiplier = capturePoint.z / capturePoint.z;
            	
            	Marker marker = markerSet.createMarker(territory.name + "-" + (i + 1), territory.name + "-" + (i + 1), Bukkit.getWorlds().get(0).getName(), (double) capturePoint.x * 16 + (xMultiplier * 7.5), (double) capturePoint.y, (double) capturePoint.z * 16 + (zMultiplier * 7.5), markerAPI.getMarkerIcon("temple"), false);

            	spawnRectangle(capturePoint.x * 16 + (xMultiplier * 6), capturePoint.y, capturePoint.z * 16 + (zMultiplier * 6), 4, 4, Material.IRON_BLOCK, 0);
            	
    			spawnBeacon(capturePoint.x * 16 + (xMultiplier * 7), capturePoint.y, capturePoint.z * 16 + (zMultiplier * 7));
    			spawnBeacon(capturePoint.x * 16 + (xMultiplier * 8), capturePoint.y, capturePoint.z * 16 + (zMultiplier * 7));
    			spawnBeacon(capturePoint.x * 16 + (xMultiplier * 7), capturePoint.y, capturePoint.z * 16 + (zMultiplier * 8));
    			spawnBeacon(capturePoint.x * 16 + (xMultiplier * 8), capturePoint.y, capturePoint.z * 16 + (zMultiplier * 8));
    			
    			if (capturePoint.owner.equals(Empire.ARATOR)) {
    				
                	spawnRectangle(capturePoint.x * 16 + (xMultiplier * 6), capturePoint.y + 1, capturePoint.z * 16 + (zMultiplier * 6), 4, 4, Material.STAINED_GLASS, 11);
    				
    			} else if (capturePoint.owner.equals(Empire.YAVARI)) {
    				
    				spawnRectangle(capturePoint.x * 16 + (xMultiplier * 6), capturePoint.y + 1, capturePoint.z * 16 + (zMultiplier * 6), 4, 4, Material.STAINED_GLASS, 10);
    				
    			} else if (capturePoint.owner.equals(Empire.REQUIEM)) {
    				
    				spawnRectangle(capturePoint.x * 16 + (xMultiplier * 6), capturePoint.y + 1, capturePoint.z * 16 + (zMultiplier * 6), 4, 4, Material.STAINED_GLASS, 14);
    				
    			} else {
    				
    				spawnRectangle(capturePoint.x * 16 + (xMultiplier * 6), capturePoint.y + 1, capturePoint.z * 16 + (zMultiplier * 6), 4, 4, Material.STAINED_GLASS, 0);
    				
    			}

            }
            
        }
        
        (new CaptureTask()).run();

	}	
	
	@Override
	public void onDisable(){
		saveDefaultConfig();
	}
	
	public static SQEmpire getInstance(){
		return instance;
	}

	public static void debug(String s){
		instance.getLogger().info(s);
	}
	
	public static Planet thisPlanet(){
		return thisPlanet;
	}
	
	public static void echo(CommandSender s, String... msg){
		msg[0] = ChatColor.WHITE + "[" + ChatColor.GREEN + "SQEmpire" + ChatColor.WHITE + "] " + msg[0];
		s.sendMessage(msg);
	}
	
	public static void spawnBeacon(int x, int y, int z) {
    	
		Bukkit.getWorlds().get(0).getBlockAt(x, y, z).setType(Material.BEACON);
		
		Bukkit.getWorlds().get(0).getBlockAt(x, y - 1, z).setType(Material.IRON_BLOCK);
		Bukkit.getWorlds().get(0).getBlockAt(x + 1, y - 1, z).setType(Material.IRON_BLOCK);
		Bukkit.getWorlds().get(0).getBlockAt(x, y - 1, z + 1).setType(Material.IRON_BLOCK);
		Bukkit.getWorlds().get(0).getBlockAt(x - 1, y - 1, z - 1).setType(Material.IRON_BLOCK);
		Bukkit.getWorlds().get(0).getBlockAt(x - 1, y - 1, z + 1).setType(Material.IRON_BLOCK);
		Bukkit.getWorlds().get(0).getBlockAt(x + 1, y - 1, z - 1).setType(Material.IRON_BLOCK);
		Bukkit.getWorlds().get(0).getBlockAt(x + 1, y - 1, z + 1).setType(Material.IRON_BLOCK);
		Bukkit.getWorlds().get(0).getBlockAt(x - 1, y - 1, z - 1).setType(Material.IRON_BLOCK);
		Bukkit.getWorlds().get(0).getBlockAt(x, y - 1, z - 1).setType(Material.IRON_BLOCK);
		
	}
	
	public static void spawnRectangle(int x, int y, int z, int width, int length, Material material, int id) {
		
		for (int i = 0; i < width; i ++) {
			
			for (int j = 0; j < length; j ++) {
				
				Bukkit.getWorlds().get(0).getBlockAt(x + i, y, z + j).setType(material);
				Bukkit.getWorlds().get(0).getBlockAt(x + i, y, z + j).setData((byte) id);
				
			}
			
		}
		
	}
	
}


