package com.martinjonsson01.sqtechpumps;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.block.Block;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.plugin.java.JavaPlugin;

import com.martinjonsson01.sqtechpumps.objects.LargeTank;
import com.martinjonsson01.sqtechpumps.objects.MediumTank;
import com.martinjonsson01.sqtechpumps.objects.Pump;
import com.martinjonsson01.sqtechpumps.objects.SmallTank;
import com.starquestminecraft.sqtechbase.SQTechBase;
import com.starquestminecraft.sqtechbase.objects.Machine;

public class SQTechPumps extends JavaPlugin {
	
	public static SQTechPumps plugin;
	
	public static FileConfiguration config = null;
	
	public static List<Machine> pumpingList = new ArrayList<Machine>();
	
	public static HashMap<Machine, List<Block>> waterBlocks = new HashMap<Machine, List<Block>>();
	
	//public static HashMap<Machine, ArrayList<Block>> resumeWaterBlocks = new HashMap<Machine, ArrayList<Block>>();
	
	public static List<Machine> smallTanks = new ArrayList<Machine>();
	
	public static HashMap<List<Block>, Machine> smallTankLapisBlocks = new HashMap<List<Block>, Machine>();
	
	public static HashMap<Machine, Player> machinePlayerMap = new HashMap<Machine, Player>();
	
	public static HashMap<Inventory, Machine> inventoryMap = new HashMap<Inventory, Machine>();
	
	public static HashMap<Player, Long> lastClick = new HashMap<Player, Long>();
	
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
		SQTechBase.addMachineType(new SmallTank(0));
		SQTechBase.addMachineType(new MediumTank(0));
		SQTechBase.addMachineType(new LargeTank(0));
		
	}
	
	@Override
	public void onDisable() {
		
		for (Machine m : SQTechPumps.pumpingList) {
			
			Pump.stopPumpingImmediately(m, machinePlayerMap.get(m));
			
		}
		
	}
	
}
