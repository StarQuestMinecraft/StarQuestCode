package com.starquestminecraft.sqcontracts.util;

import java.util.ArrayList;
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
	
	static List<String> alphaStations;
	static List<String> betaStations;
	static List<String> gammaStations;
	static WorldGuardPlugin wg;
	
	public static void setUp(FileConfiguration c){
		alphaStations = c.getStringList("alphaStations");
		betaStations = c.getStringList("betaStations");
		gammaStations = c.getStringList("gammaStations");
		System.out.println("Stations:");
		for(String s : alphaStations){
			System.out.println("    " + s);
		}
		for(String s : betaStations){
			System.out.println("    " + s);
		}
		for(String s : gammaStations){
			System.out.println("    " + s);
		}
		wg = ((WorldGuardPlugin) Bukkit.getPluginManager().getPlugin("WorldGuard"));
	}
	
	public static double getModifierForStation(String station){
		if(station.toLowerCase().contains("beta")){
			return 1.5;
		}
		if(station.toLowerCase().contains("gamma")){
			return 2.5;
		}
		return 1;
	}
	public static String getStationAtLocation(Location l){
		System.out.println("Method called.");
		ApplicableRegionSet set = wg.getRegionManager(l.getWorld()).getApplicableRegions(l);
		System.out.println("Crazy test!");
		Set<ProtectedRegion> regions = set.getRegions();
		java.util.Iterator<ProtectedRegion> i = regions.iterator();
		while(i.hasNext()){
			ProtectedRegion r = i.next();
			List<String> stations = new ArrayList<String>();
			stations.addAll(alphaStations);
			stations.addAll(betaStations);
			stations.addAll(gammaStations);
			for(String s : stations){
				if(r.getId().equals(s.toLowerCase())){
					return s;
				}
			}
		}
		return null;
	}
	
	public static String getRandomStation(Random gen, String system){
		if (system.equals("alpha")){
			int index = gen.nextInt(alphaStations.size());
			return alphaStations.get(index);
		} else if (system.equals("beta")) {
			int index = gen.nextInt(betaStations.size());
			return betaStations.get(index);			
		} else if (system.equals("gamma")) {
			int index = gen.nextInt(gammaStations.size());
			return gammaStations.get(index);			
		} else {
			return null;
		}
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
