package com.dibujaron.feudalism;

import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.plugin.java.JavaPlugin;

import com.dibujaron.feudalism.database.Database;
import com.dibujaron.feudalism.files.TerritoryFile;
import com.dibujaron.feudalism.regions.RegionManager;
import com.dibujaron.feudalism.settings.Settings;
import com.dibujaron.feudalism.task.MoveCheckTask;

public class Feudalism extends JavaPlugin{
	
	Database d;
	
	private static Feudalism instance;
	
	public void onEnable(){
		
		instance = this;
		Settings.load();
		
		MoveCheckTask task = new MoveCheckTask();
		task.runTaskTimer(this, Settings.MOVE_CHECK_INTERVAL, Settings.MOVE_CHECK_INTERVAL);
		
		
		for(World w : Bukkit.getWorlds()){
			TerritoryFile f = new TerritoryFile(w, this);
			RegionManager rm = new RegionManager(w, f.loadTerritoryArray());
			RegionManager.addToManagers(rm);
		}
	}
	
	public static Feudalism getInstance(){
		return instance;
	}
	
	public Database getDatabaseHandler(){
		return d;
	}
	
}
