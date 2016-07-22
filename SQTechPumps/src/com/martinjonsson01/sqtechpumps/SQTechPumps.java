package com.martinjonsson01.sqtechpumps;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.block.Block;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import com.starquestminecraft.sqtechbase.Machine;
import com.starquestminecraft.sqtechbase.SQTechBase;

public class SQTechPumps extends JavaPlugin {
	
	static SQTechPumps plugin;
	
	public static FileConfiguration config = null;
	
	public static List<Machine> pumpingList = new ArrayList<Machine>();
	
	public static List<Block> waterBlocks;
	
	@Override
	public void onEnable() {
		
		Bukkit.getServer().getPluginManager().registerEvents(new Events(), this);
		
		plugin = this;
		
		if (!new File(this.getDataFolder(), "config.yml").exists()) {
			
			saveDefaultConfig();
			saveConfig();
			
		}
		
		config = this.getConfig();
		
		SQTechBase.addMachineType(new Pump(config.getInt("maxEnergy")));
		
	}
	
	@Override
	public void onDisable() {
		
		for (Machine m : SQTechPumps.pumpingList) {
			
			Pump.stopPumpingImmediately(m);
			
		}
		
	}
	
}
