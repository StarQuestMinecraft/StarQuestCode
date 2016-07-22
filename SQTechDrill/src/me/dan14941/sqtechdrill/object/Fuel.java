package me.dan14941.sqtechdrill.object;

import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;

import me.dan14941.sqtechdrill.SQTechDrill;

public class Fuel
{
	final private Material fuelMaterial;
	final private FileConfiguration config = SQTechDrill.getMain().getConfig();
	
	public Fuel(Material material)
	{
		this.fuelMaterial = material;
	}
	
	public boolean isRegisteredFuel()
	{
		if(config.getInt("fuel." + this.fuelMaterial.toString().toUpperCase() + ".burn time") == 0)
			return false;
		else
			return true;
	}
	
	public int getBurnTime()
	{
		return config.getInt("fuel." + this.fuelMaterial.toString().toUpperCase() + ".burn time");
	}
	
	public int getEnergyPerTick()
	{
		return config.getInt("fuel." + this.fuelMaterial.toString().toUpperCase() + ".energy per tick");
	}
	
	public Material getMaterial()
	{
		return this.fuelMaterial;
	}
}
