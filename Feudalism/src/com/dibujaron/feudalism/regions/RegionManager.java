package com.dibujaron.feudalism.regions;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;

import org.bukkit.World;
import org.bukkit.entity.Player;

import com.dibujaron.feudalism.kingdoms.Territory;

public class RegionManager {
	
	private static HashMap<World, RegionManager> regionManagers = new HashMap<World, RegionManager>();
	private ArrayList<Territory> regionsInWorld;
	private World myWorld;
	
	public static Collection<RegionManager> getRegionManagers(){
		return regionManagers.values();
	}
	
	public static RegionManager getManagerOfWorld(World w){
		return regionManagers.get(w);
	}
	
	public RegionManager(World w, ArrayList<Territory> terrs){
		//config load stuff
		regionsInWorld = terrs;
		myWorld = w;
	}
	
	public static void addToManagers(RegionManager rm){
		regionManagers.put(rm.getWorld(), rm);
	}
	
	public static Territory getRegionPlayerIn(Player p){
		RegionManager m = regionManagers.get(p.getWorld());
		for(Territory r : m.regionsInWorld){
			if(r.regionContains(p.getLocation())){
				return r;
			}
		}
		return null;
	}
	
	public ArrayList<Territory> getTerritories(){
		return regionsInWorld;
	}
	
	public World getWorld(){
		return myWorld;
	}
}
