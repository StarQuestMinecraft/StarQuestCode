package com.martinjonsson01.sqtechpumps;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import org.bukkit.Bukkit;
import org.bukkit.Location;
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

import net.countercraft.movecraft.craft.Craft;


public class SQTechPumps extends JavaPlugin {
	
	public static SQTechPumps plugin;
	
	public static FileConfiguration config = null;
	
	public static List<Machine> pumpingList = new ArrayList<Machine>();
	
	public static List<Location> pumpingLocationList = new ArrayList<Location>();
	
	public static HashMap<Machine, List<Block>> waterBlocks = new HashMap<Machine, List<Block>>();
	
	public static HashMap<Machine, List<Block>> tankWaterBlocks = new HashMap<Machine, List<Block>>();
	
	public static List<Machine> smallTanks = new ArrayList<Machine>();
	
	public static HashMap<Machine, Player> machinePlayerMap = new HashMap<Machine, Player>();
	
	public static HashMap<Machine, Integer> machineLiquidTypeIdMap = new HashMap<Machine, Integer>();
	
	public static HashMap<Machine, Boolean> machineExtendingMap = new HashMap<Machine, Boolean>();
	
	public static HashMap<Location, Machine> locationMachineMap = new HashMap<Location, Machine>();
	
	public static HashMap<Craft, Boolean> craftHasRemovedWaterInTanksBooleanMap = new HashMap<Craft, Boolean>();
	
	public static HashMap<Inventory, Machine> inventoryMap = new HashMap<Inventory, Machine>();
	
	public static HashMap<Machine, Long> lastClick = new HashMap<Machine, Long>();
	
	public static HashMap<Machine, Block> waterBlockMap = new HashMap<Machine, Block>();
	
	public static List<Block> ironBarList = new ArrayList<Block>();
	
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
		
		/*for (Machine m : SQTechBase.machines) {
			
			if (m.getMachineType().getName().equals("Small Tank")) {
				
				Bukkit.getLogger().log(Level.INFO, "[SQTechPumps] Updated liquid in small tank at location: " + m.getGUIBlock().getLocation());
				SmallTank.updatePhysicalLiquid(m);
				
			} else if (m.getMachineType().getName().equals("Medium Tank")) {
				
				MediumTank.updatePhysicalLiquid(m);
				
			} else if (m.getMachineType().getName().equals("Large Tank")) {
				
				LargeTank.updatePhysicalLiquid(m);
				
			}
			
		}*/
		
	}
	
	@Override
	public void onDisable() {
		
		for (Machine m : SQTechPumps.pumpingList) {
			
			Pump.stopPumpingImmediately(m, machinePlayerMap.get(m));
			
		}
		
	}
	
}
