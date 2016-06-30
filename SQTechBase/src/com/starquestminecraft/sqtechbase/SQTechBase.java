package com.starquestminecraft.sqtechbase;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.logging.Logger;

import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitScheduler;

import com.starquestminecraft.sqtechbase.database.DatabaseInterface;
import com.starquestminecraft.sqtechbase.database.SQLDatabase;
import com.starquestminecraft.sqtechbase.gui.GUI;
import com.starquestminecraft.sqtechbase.tasks.EnergyTask;
import com.starquestminecraft.sqtechbase.tasks.ItemMovingTask;
import com.starquestminecraft.sqtechbase.tasks.StructureTask;

public class SQTechBase extends JavaPlugin {
	
	public final Logger logger = Logger.getLogger("Minecraft");
	static SQTechBase plugin;
	
	public static List<Network> networks = new ArrayList<Network>();
	
    public static HashMap<Player, GUI> currentGui = new HashMap<Player, GUI>();
	
    public static List<MachineType> machineTypes = new ArrayList<MachineType>();
    public static List<Machine> machines = new ArrayList<Machine>();
	
	public static FileConfiguration config = null;
	
	public static List<String> energyItemNames = new ArrayList<String>();
	
	boolean enabled = false;
	
	@Override
	public void onDisable() {
		
		PluginDescriptionFile pdfFile = this.getDescription();
		this.logger.info(pdfFile.getName() + " has been disabled!");
		
		saveDefaultConfig();
		
		DatabaseInterface.saveObjects();
		
	}
	
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
		
		this.getServer().getPluginManager().registerEvents(new Events(), this);
		
		if (Bukkit.getPluginManager().isPluginEnabled("Movecraft")) {
			
			this.getServer().getPluginManager().registerEvents(new MovecraftEvents(), this);
			
		}

		(new ItemMovingTask()).run();
		(new StructureTask()).run();
		(new EnergyTask()).run();
		
		new SQLDatabase();
		
		BukkitScheduler bukkitScheduler =  Bukkit.getScheduler();
		
		bukkitScheduler.scheduleSyncDelayedTask(this, new Runnable() {
			
			public void run() {
				
				
				DatabaseInterface.readObjects();
				
			}

		}, 1);
		
	}
	
	public static SQTechBase getPluginMain() {
		
		return plugin;
		
	}
	
	public static void addMachineType(MachineType machineType) {
		
		machineTypes.add(machineType);
		
	}
	
	public static void addEnergyItem(String name) {
		
		energyItemNames.add(name);
		
	}

}
