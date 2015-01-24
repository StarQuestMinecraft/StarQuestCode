package com.starquestminecraft.sqcontracts.util;

import java.util.List;
import java.util.Random;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.configuration.file.FileConfiguration;

import com.sk89q.worldguard.bukkit.WorldGuardPlugin;
import com.sk89q.worldguard.protection.ApplicableRegionSet;
import com.sk89q.worldguard.protection.regions.ProtectedRegion;

public class StationUtils {
	
	static List<String> stations;
	static WorldGuardPlugin wg;
	
	public static void setUp(FileConfiguration c){
		stations = c.getStringList("stations");
		wg = ((WorldGuardPlugin) Bukkit.getPluginManager().getPlugin("WorldGuard"));
	}
	
	public static String getStationAtLocation(Location l){
		ApplicableRegionSet set = wg.getRegionManager(l.getWorld()).getApplicableRegions(l);
		for(ProtectedRegion r : set){
			for(String s : stations){
				if(r.getId().equals(s)){
					return s;
				}
			}
		}
		return null;
	}
	
	public static String getRandomStation(Random gen){
		int index = gen.nextInt(stations.size());
		return stations.get(index);
	}
	
	
	private static Location stringToLoc(String string){
		String[] loc = string.split(":");
		World world = Bukkit.getWorld(loc[0]);
		Double x = Double.parseDouble(loc[1]);
		Double y = Double.parseDouble(loc[2]);
		Double z = Double.parseDouble(loc[3]);
		
		return new Location(world, x, y, z);
	}
}
