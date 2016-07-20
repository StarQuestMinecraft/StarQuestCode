package com.starquestminecraft.sqtechfarm.Object;

import org.bukkit.configuration.file.FileConfiguration;

import com.starquestminecraft.sqtechfarm.SQTechFarm;

public class Settings
{
	private FileConfiguration config;
	
	public Settings(SQTechFarm plugin)
	{
		this.config = plugin.getConfig();
	}
	
	public int getHarvesterMaxEnergy()
	{
		return config.getInt("harvester.max energy");
	}
	
	public int getHarvesterSizeSmall()
	{
		return config.getInt("harvester.size.small");
	}
}
