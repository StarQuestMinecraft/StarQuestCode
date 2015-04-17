package com.starquestminecraft.sqcontracts.util;

import java.util.List;
import java.util.Random;
import java.util.Set;

import javax.swing.text.html.HTMLDocument.Iterator;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.configuration.file.FileConfiguration;

import com.sk89q.worldedit.regions.Region;
import com.sk89q.worldguard.bukkit.WorldGuardPlugin;
import com.sk89q.worldguard.protection.ApplicableRegionSet;
import com.sk89q.worldguard.protection.regions.ProtectedRegion;

public class StationUtils {
	
	static List<String> stations;
	static WorldGuardPlugin wg;
	
	public static void setUp(FileConfiguration c){
		stations = c.getStringList("stations");
		System.out.println("Stations:");
		for(String s : stations){
			System.out.println("    " + s);
		}
		wg = ((WorldGuardPlugin) Bukkit.getPluginManager().getPlugin("WorldGuard"));
	}
	
	public static String getStationAtLocation(Location l){
		System.out.println("Method called.");
		ApplicableRegionSet set = wg.getRegionManager(l.getWorld()).getApplicableRegions(l);
		System.out.println("Crazy test!");
		Set<ProtectedRegion> regions = set.getRegions();
		java.util.Iterator<ProtectedRegion> i = regions.iterator();
		while(i.hasNext()){
			ProtectedRegion r = i.next();
			for(String s : stations){
				if(r.getId().equals(s.toLowerCase())){
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
