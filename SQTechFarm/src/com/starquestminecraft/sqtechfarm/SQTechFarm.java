package com.starquestminecraft.sqtechfarm;

import java.util.Arrays;
import java.util.List;

import org.bukkit.Material;
import org.bukkit.plugin.java.JavaPlugin;

import com.starquestminecraft.sqtechbase.SQTechBase;
import com.starquestminecraft.sqtechfarm.Object.Settings;
import com.starquestminecraft.sqtechfarm.harvester.Harvester;

public class SQTechFarm extends JavaPlugin
{
	private SQTechFarm plugin;
	private Settings settings;
	
	final public List<Material> fenceTypes = Arrays.asList(Material.FENCE, Material.ACACIA_FENCE, 
			Material.BIRCH_FENCE, Material.DARK_OAK_FENCE, Material.IRON_FENCE, Material.JUNGLE_FENCE,
			Material.SPRUCE_FENCE);
	
	public void onEnable()
	{
		this.plugin = this;
		this.settings = new Settings(this);
		SQTechBase.addMachineType(new Harvester(settings.getHarvesterMaxEnergy(), this));
	}
	
	public Settings getSettings()
	{
		return this.settings;
	}
}
