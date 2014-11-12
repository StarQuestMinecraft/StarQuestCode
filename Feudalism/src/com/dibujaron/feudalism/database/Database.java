package com.dibujaron.feudalism.database;
import org.bukkit.World;
import org.bukkit.entity.Player;

import com.dibujaron.feudalism.kingdoms.Kingdom;
import com.dibujaron.feudalism.kingdoms.Territory;

public interface Database {

	/**
	 * @param t the territory to modify
	 */
	public Territory[] getTerritoriesInWorld(World w);
	public void removeTerritory(Territory t);
	public void addTerritory(Territory t);
	public void updateTerritory(Territory t);
	
	
	/**
	 * @param k the kingdom to modify
	 */
	public Kingdom[] getKingdomsInWorld(World w);
	public void removeKingdom(Kingdom k);
	public void addKingdom(Kingdom k);
	public void updateKingdom(Kingdom k);
	
	public void setTerritoryOfPlayer(Player p);
	public Territory getTerritoryOfPlayer(Player p);
}
