package com.starquestminecraft.sqtechfarm.Object;

import org.bukkit.configuration.file.FileConfiguration;

import com.starquestminecraft.sqtechfarm.SQTechFarm;
import com.starquestminecraft.sqtechfarm.harvester.Harvester;

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
	
	public int getHarvesterPlantingEnergy()
	{
		return config.getInt("harvester.energy usage.energy consumption.planting");
	}
	
	public int getHarvesterHarvestingEnergy()
	{
		return config.getInt("harvester.energy usage.energy consumption.harvesting");
	}
	
	public int getHarvesterSizeSmall()
	{
		return config.getInt("harvester.size.small");
	}
	
	public int getHarvesterSizeMedium()
	{
		return config.getInt("harvester.size.medium");
	}
	
	public int getHarvesterSizeLarge()
	{
		return config.getInt("harvester.size.large");
	}
	
	public int getHarvesterSpeed()
	{
		return config.getInt("harvester.speed");
	}
	
	/**
	 * Tries to get the default Harvester size from the config, if no default size was found the medium size is used.
	 */
	public Harvester.HarvesterSize getDefaultSize()
	{
		String defaultSizeStr = config.getString("harvester.size.default");
		if(defaultSizeStr == null)
			return Harvester.HarvesterSize.MEDIUM;
		
		for(Harvester.HarvesterSize sizes : Harvester.HarvesterSize.values())
		{
			if(sizes.toString().equalsIgnoreCase(defaultSizeStr))
				return sizes;
		}
		
		return Harvester.HarvesterSize.MEDIUM;
	}
}
