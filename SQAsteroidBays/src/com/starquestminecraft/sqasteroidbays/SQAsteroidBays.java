package com.starquestminecraft.sqasteroidbays;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitTask;

public class SQAsteroidBays extends JavaPlugin {
	
	public String worldName = "world";
	public Double pasteX = 0.0;
	public Double pasteY = 0.0;
	public Double pasteZ = 0.0;
	public Integer lastSchematic = 1;
	public List<Integer> availableNumbers = new ArrayList<Integer>();
	
	SQAsteroidBays asteroidBaysInstance;
	
	@Override
	public void onEnable() {
		getLogger().info("SQAsteroidBays has been enabled");
		BukkitTask asteroidBaysTask = new SQAsteroidBayTask(this).runTaskLater(this, 200);
		asteroidBaysInstance = this;
		FileConfiguration config = this.getConfig();
		worldName = config.getString("world");
		double x = config.getDouble("x");
		double y = config.getDouble("y");
		double z = config.getDouble("z");
		
		pasteX = x;
		pasteY = y;
		pasteZ = z;
		
		availableNumbers.add(2);
		availableNumbers.add(3);
		availableNumbers.add(4);
		availableNumbers.add(5);
		
	}
	
	@Override
	public void onDisable() {
		getLogger().info("SQAsteroidBays has been disabled");
	}
	
}
