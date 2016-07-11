package com.starquestminecraft.sqtechenergy;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.java.JavaPlugin;

import com.starquestminecraft.sqtechbase.SQTechBase;
import com.starquestminecraft.sqtechenergy.tasks.GeneratorTask;

public class SQTechEnergy extends JavaPlugin{

	public final Logger logger = Logger.getLogger("Minecraft");
	static SQTechEnergy plugin;
	
	static FileConfiguration config = null;
	
	public static List<Fuel> fuels = new ArrayList<Fuel>();
	
	boolean enabled = false;
	
	@Override
	public void onEnable() {
		
		plugin = this;
		
		PluginDescriptionFile pdfFile = this.getDescription();
		this.logger.info(pdfFile.getName() + " has been enabled!");
		
		if (!new File(this.getDataFolder(), "config.yml").exists()) {
			
			saveDefaultConfig();
			saveConfig();
			
		}
		
		config = getConfig();
		
		for (String fuel : config.getConfigurationSection("basic generator.fuel").getKeys(false)) {
			
			String path = "basic generator.fuel." + fuel;
			
			Fuel fuelObject = new Fuel();
			
			String[] split = config.getString(path + ".id").split(":");
			
			if (split.length == 1) {
				
				fuelObject.id = Integer.parseInt(split[0]);
				fuelObject.data = (short) 0;
				
			} else {
				
				fuelObject.id = Integer.parseInt(split[0]);
				fuelObject.data = Short.parseShort(split[1]);
				
			}
			
			fuelObject.energyPerTick = config.getInt(path + ".energy per tick");
			fuelObject.burnTime = config.getInt(path + ".burn time");
			fuelObject.generator = "Basic Generator";
			
			fuels.add(fuelObject);
			
		}
		
		SQTechBase.addMachineType(new BasicGenerator(10000));
		
		(new GeneratorTask()).run();
		
	}
	
	@Override
	public void onDisable() {
		
		PluginDescriptionFile pdfFile = this.getDescription();
		this.logger.info(pdfFile.getName() + " has been disabled!");
		
		saveDefaultConfig();
		
	}
	
	public static SQTechEnergy getPluginMain() {
		
		return plugin;
		
	}
	
}
