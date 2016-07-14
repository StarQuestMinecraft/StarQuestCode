package com.starquestminecraft.sqtechbase;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;
import java.util.logging.Logger;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitScheduler;

import com.starquestminecraft.sqtechbase.database.DatabaseInterface;
import com.starquestminecraft.sqtechbase.database.SQLDatabase;
import com.starquestminecraft.sqtechbase.events.GUIBlockEvents;
import com.starquestminecraft.sqtechbase.events.MovecraftEvents;
import com.starquestminecraft.sqtechbase.events.PlayerEvents;
import com.starquestminecraft.sqtechbase.gui.GUI;
import com.starquestminecraft.sqtechbase.gui.options.OptionGUI;
import com.starquestminecraft.sqtechbase.objects.Machine;
import com.starquestminecraft.sqtechbase.objects.MachineType;
import com.starquestminecraft.sqtechbase.objects.Network;
import com.starquestminecraft.sqtechbase.objects.PlayerOptions;
import com.starquestminecraft.sqtechbase.tasks.DatabaseTask;
import com.starquestminecraft.sqtechbase.tasks.EnergyTask;
import com.starquestminecraft.sqtechbase.tasks.ItemMovingTask;
import com.starquestminecraft.sqtechbase.tasks.StructureTask;

public class SQTechBase extends JavaPlugin {
	
	public final Logger logger = Logger.getLogger("Minecraft");
	static SQTechBase plugin;
	
	public static List<Network> networks = new ArrayList<Network>();
	
	public static HashMap<UUID, PlayerOptions> currentOptions = new HashMap<UUID, PlayerOptions>();
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
	
	@SuppressWarnings("deprecation")
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
		
		this.getServer().getPluginManager().registerEvents(new GUIBlockEvents(), this);
		this.getServer().getPluginManager().registerEvents(new PlayerEvents(), this);
		
		if (Bukkit.getPluginManager().isPluginEnabled("Movecraft")) {
			
			this.getServer().getPluginManager().registerEvents(new MovecraftEvents(), this);
			
		}

		(new ItemMovingTask()).run();
		(new StructureTask()).run();
		(new EnergyTask()).run();
		
		new SQLDatabase();
		
		BukkitScheduler bukkitScheduler =  Bukkit.getScheduler();
		
		bukkitScheduler.scheduleAsyncDelayedTask(this, new Runnable() {
			
			public void run() {
				
				DatabaseInterface.readObjects();
				
				(new DatabaseTask()).run();
				
			}

		});
		
	}
	
	public static void printHelp (CommandSender sender) {
		
		sender.sendMessage(ChatColor.GOLD + "-----------------------------------------------------");
		sender.sendMessage(ChatColor.GOLD + "/sqtech help" + ChatColor.BLUE + " - Shows this");
		sender.sendMessage(ChatColor.GOLD + "/sqtech options" + ChatColor.BLUE + " - Shows a gui with options");
		sender.sendMessage(ChatColor.GOLD + "-----------------------------------------------------");
	}
	
	public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args) {
		
		if (commandLabel.equals("sqtech")) {
			
			if (args.length == 0) {
				
				printHelp(sender);
				
			} else {
				
				if (args[0].equals("help")) {
					
					printHelp(sender);
					
				} else if (args[0].equals("options") || args[0].equals("option")) {
					
					if (sender instanceof Player) {
						
						Player player = (Player) sender;
						
						OptionGUI optionGUI = new OptionGUI(player);
						optionGUI.open();		
						
					} else {
						
						sender.sendMessage(ChatColor.RED + "You must be a player to use this command");
							
					}		
					
				} else {
					
					sender.sendMessage(ChatColor.RED + args[0] + " is not a recognized command for SQTech");
					
				}
				
			}
			
		}
		
		return false;
		
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
