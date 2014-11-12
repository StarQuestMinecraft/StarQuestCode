package com.dibujaron.feudalism.player;

import java.util.HashMap;
import java.util.Set;
import java.util.UUID;

import org.bukkit.entity.Player;

import com.dibujaron.feudalism.Feudalism;
import com.dibujaron.feudalism.kingdoms.Territory;
import com.dibujaron.feudalism.regions.RegionManager;

public class FPlayer {
	Player me;
	Territory myTerritory;
	Territory standingIn;
	
	private static HashMap<UUID, FPlayer> playerMap = new HashMap<UUID, FPlayer>();
	
	public FPlayer(Player me){
		this.me = me;
		
		myTerritory = Feudalism.getInstance().getDatabaseHandler().getTerritoryOfPlayer(me);
		
	}
	
	public static void map(UUID u, FPlayer p){
		playerMap.put(u, p);
	}
	
	public static void removeMapping(UUID u){
		playerMap.remove(u);
	}
	
	public static Set<UUID> getMappedUUIDs(){
		return playerMap.keySet();
	}
	
	public static FPlayer getFPlayer(Player p){
		return getFPlayer(p.getUniqueId());
	}
	
	public static FPlayer getFPlayer(UUID u){
		return playerMap.get(u);
	}
	
	public void moveCheck(){
		if(me.getWorld() == standingIn.getWorld() && standingIn.regionContains(me.getLocation())){
			return;
		} else {
			myTerritory = RegionManager.getRegionPlayerIn(me);
		}
	}
}
