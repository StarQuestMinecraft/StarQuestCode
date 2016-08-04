package com.starquestminecraft.sqtechbase;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;
import java.util.logging.Logger;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.java.JavaPlugin;

import com.starquestminecraft.sqtechbase.database.DatabaseInterface;
import com.starquestminecraft.sqtechbase.database.SQLDatabase;
import com.starquestminecraft.sqtechbase.gui.GUI;
import com.starquestminecraft.sqtechbase.gui.options.OptionGUI;
import com.starquestminecraft.sqtechbase.listeners.GUIBlockEvents;
import com.starquestminecraft.sqtechbase.listeners.MovecraftEvents;
import com.starquestminecraft.sqtechbase.objects.Fluid;
import com.starquestminecraft.sqtechbase.objects.Machine;
import com.starquestminecraft.sqtechbase.objects.MachineType;
import com.starquestminecraft.sqtechbase.objects.Network;
import com.starquestminecraft.sqtechbase.objects.PlayerOptions;

public class SQTechBase extends JavaPlugin {
	
	public final Logger logger = Logger.getLogger("Minecraft");
	static SQTechBase plugin;
	
	public static List<Network> networks = new ArrayList<Network>();
	
	public static HashMap<UUID, PlayerOptions> currentOptions = new HashMap<UUID, PlayerOptions>();
    public static HashMap<Player, GUI> currentGui = new HashMap<Player, GUI>();
	
    public static List<MachineType> machineTypes = new ArrayList<MachineType>();
    public static List<Machine> machines = new ArrayList<Machine>();
	
	public static FileConfiguration config = null;
	
	public static List<Fluid> fluids = new ArrayList<Fluid>();
	
	boolean enabled = false;
	
	public static int highestId = -1;
	
	@Override
	public void onDisable() {
		
		PluginDescriptionFile pdfFile = this.getDescription();
		this.logger.info(pdfFile.getName() + " has been disabled!");
		
		saveDefaultConfig();
		
		try {
			
			SQLDatabase.clearMachines(SQLDatabase.con.getConnection(), SQTechBase.config.getString("server name"));
			SQLDatabase.clearGUIBlocks(SQLDatabase.con.getConnection(), SQTechBase.config.getString("server name"));
			
		} catch (Exception e) {
			
			e.printStackTrace();
			
		}
		
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

		if (Bukkit.getPluginManager().isPluginEnabled("Movecraft")) {
			
			this.getServer().getPluginManager().registerEvents(new MovecraftEvents(), this);
			
		}

		fluids.add(new Fluid(0, Material.WATER_BUCKET, 0, "Water"));
		fluids.add(new Fluid(1, Material.LAVA_BUCKET, 0, "Lava"));
		
		Bukkit.getScheduler().scheduleAsyncDelayedTask(this, new Runnable() {
			
			public void run() {
				
				try {
					
					new SQLDatabase();
					
				} catch (Exception e) {
					
					e.printStackTrace();
					
				}

				DatabaseInterface.readObjects();
						
			}
			
		});
		
	}
	
	public static void printHelp (CommandSender sender) {
		
		sender.sendMessage(ChatColor.GOLD + "---------------------------------------------");
		sender.sendMessage(ChatColor.GOLD + "/sqtech help" + ChatColor.BLUE + " - Shows this");
		sender.sendMessage(ChatColor.GOLD + "/sqtech options" + ChatColor.BLUE + " - Shows a gui with options");
		sender.sendMessage(ChatColor.GOLD + "---------------------------------------------");
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

}
