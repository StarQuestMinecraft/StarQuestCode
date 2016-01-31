package me.Tongonto;

import java.util.ArrayList;
import java.util.HashMap;

import org.bukkit.Location;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

public class SQRecycler extends JavaPlugin {
	
	protected HashMap <Location, RecyclerMachine> activeRecyclerList = new HashMap <Location, RecyclerMachine>();
	protected ArrayList <Location> recyclerLocationList = new ArrayList <Location>();
	protected FileConfiguration recyclerConfig = this.getConfig();
	
	SQRecycler recyclerInstance;
	
	@Override
	public void onEnable(){
		getLogger().info("SQRecycler has been enabled!");
		new RecyclerListener(this);
		recyclerInstance = this;
	}
	
	@Override
	public void onDisable(){
		getLogger().info("SQRecycler has been disabled!");
	}
	
}
