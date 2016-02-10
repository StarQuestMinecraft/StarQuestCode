package com.ginger_walnut.sqboosters;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.logging.Logger;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.java.JavaPlugin;

import com.ginger_walnut.sqboosters.database.DatabaseInterface;

public class SQBoosters extends JavaPlugin {

	public final Logger logger = Logger.getLogger("Minecraft");
	public static SQBoosters plugin;
	public static int[] multipliers = new int[] {1, 1, 1};
	public static boolean[] enabledBoosters = new boolean[] {false, false, false};
	public static String[] configNames = new String[] {"expbooster", "mobdropbooster", "sheepshearingbooster"};
	public static String[] permissionName = new String[] {"SQExpBoost", "SQMobDropBoost", "SQSheepShearBoost"};
	public static String[] multiplierName = new String[] {"exp", "mob drop", "sheep shearing"};
	
	Timer timer = new Timer();
	
	private static Plugin pluginMain;
	
	@Override
	public void onDisable() {
		
		timer.cancel();
		timer.purge();
		
		pluginMain = null;
		
		PluginDescriptionFile pdfFile = this.getDescription();
		this.logger.info(pdfFile.getName() + " has been disabled!");
		
	}
	
	@Override
	public void onEnable() {
		
		pluginMain = this;
		
		PluginDescriptionFile pdfFile = this.getDescription();
		this.logger.info(pdfFile.getName() + " has been enabled!");

		(new ExpBooster()).run();
		
		this.getServer().getPluginManager().registerEvents(new Events(), this);
		
		List<Player> players = Bukkit.getServer().getWorlds().get(0).getPlayers();
		
		for (int i = 0; i < players.size(); i++) {
			
			Player player = players.get(i);
			
			player.setMetadata("lastExp", new FixedMetadataValue(this, player.getTotalExperience()));
			
		}
		
		getConfig().options().copyDefaults(true);
		saveConfig();
		
		FileConfiguration config = getConfig();
		
		for (int i = 0; i < enabledBoosters.length; i ++) {
			
			enabledBoosters[i] = config.getBoolean(configNames[i]);
			
		}
		
		for (int i = 0; i < multipliers.length; i ++) {
			
			DatabaseInterface di = new DatabaseInterface();
			
			multipliers[i] = di.getMultiplier(multiplierName[i]);
			
		}
		
		timer.scheduleAtFixedRate(new DatabaseChecker(), 0, 300000);
		
	}
	
	public static Plugin getPluginMain() {
		
		return pluginMain;
		
	}
	
	public static void printHelp (CommandSender sender, boolean permission, String multiplierName, int multiplierPosition, String commandName) {
		
		if (!permission) {
			
			sender.sendMessage(ChatColor.GOLD + "-----------------------------------------------------");
			sender.sendMessage(ChatColor.GOLD + "Current " + multiplierName + " multiplier: " + ChatColor.BLUE + multipliers[multiplierPosition]);
			sender.sendMessage(ChatColor.GOLD + "-----------------------------------------------------");
			
		} else {
			
			sender.sendMessage(ChatColor.GOLD + "-----------------------------------------------------");
			sender.sendMessage(ChatColor.GOLD + "Current " + multiplierName + " multiplier: " + ChatColor.BLUE + multipliers[multiplierPosition]);
			sender.sendMessage(ChatColor.GOLD + "/" + commandName + " help" + ChatColor.BLUE + " - Shows this");
			sender.sendMessage(ChatColor.GOLD + "/" + commandName + " set <multiplier>" + ChatColor.BLUE + " - Sets the exp multiplier ");
			sender.sendMessage(ChatColor.GOLD + "/" + commandName + " add <multiplier>" + ChatColor.BLUE + " - Adds an amount to the multiplier");
			sender.sendMessage(ChatColor.GOLD + "/" + commandName + " subtract <multiplier>" + ChatColor.BLUE + " - Subtracts an amount from the multiplier");
			sender.sendMessage(ChatColor.GOLD + "-----------------------------------------------------");
				
		}		
		
	}
	
	public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args) {
		
		List<String> commandNames = new ArrayList<String>(); 
		commandNames.add("expboost");
		commandNames.add("mobdropboost");
		commandNames.add("sheepshearboost");
		
		if (commandNames.contains(commandLabel)) {
			
			int arrayPos = 0;
			
			for (int i = 0; i < commandNames.size(); i ++) {
				
				if (commandNames.get(i).equals(commandLabel)) {
					
					arrayPos = i;					
					
				}
				
			}
			
			if (args.length == 0) {
				
				if (sender.hasPermission(permissionName[arrayPos] + ".viewHelp") || sender instanceof ConsoleCommandSender) {
					
					printHelp(sender, true, multiplierName[arrayPos], arrayPos, commandNames.get(arrayPos));
					
				} else {
					
					printHelp(sender, false, multiplierName[arrayPos], arrayPos, commandNames.get(arrayPos));
						
				}
					
			} else if (args.length == 1) {
					
				if (args[0].equalsIgnoreCase("help")) {

					if (sender.hasPermission(permissionName[arrayPos] + ".viewHelp") || sender instanceof ConsoleCommandSender) {
						
						printHelp(sender, true, multiplierName[arrayPos], arrayPos, commandNames.get(arrayPos));
						
					} else {
						
						printHelp(sender, false, multiplierName[arrayPos], arrayPos, commandNames.get(arrayPos));
							
					}
					
				} else if (args[0].equalsIgnoreCase("set")) {
						
					if (sender.hasPermission(permissionName[arrayPos] + ".setMultiplier") || sender instanceof ConsoleCommandSender) {
							
						sender.sendMessage(ChatColor.RED + "Correct usage: /" + commandNames.get(arrayPos)  + " set <multiplier>");
							
					} else {
							
						sender.sendMessage(ChatColor.RED + "You do not have premission to set the " + multiplierName[arrayPos] + " multiplier");
							
					}
						
				} else if (args[0].equalsIgnoreCase("add")) {
					
					if (sender.hasPermission(permissionName[arrayPos] + ".addMultiplier") || sender instanceof ConsoleCommandSender) {
						
						sender.sendMessage(ChatColor.RED + "Correct usage: /" + commandNames.get(arrayPos)  + " add <multiplier>");
						
					} else {
						
						sender.sendMessage(ChatColor.RED + "You do not have premission to add to the " + multiplierName[arrayPos] + " multiplier");
						
					}
										
				} else if (args[0].equalsIgnoreCase("subtract")) {
					
					if (sender.hasPermission(permissionName[arrayPos] + ".subtractMultiplier") || sender instanceof ConsoleCommandSender) {
						
						sender.sendMessage(ChatColor.RED + "Correct usage: /" + commandNames.get(arrayPos)  + " subtract <multiplier>");
						
					} else {
						
						sender.sendMessage(ChatColor.RED + "You do not have premission to subtract to the " + multiplierName[arrayPos] + " multiplier");
						
					}
					
				} else {
						
					sender.sendMessage(ChatColor.RED + " use /" + commandNames.get(arrayPos)  + " help for help on how to use " + permissionName[arrayPos]);
						
				}
										
			} else if (args.length == 2) {
						
				if (args[0].equalsIgnoreCase("set")) {
						
					if (sender.hasPermission(permissionName[arrayPos] + ".setMultiplier") || sender instanceof ConsoleCommandSender) {
						
						try {
							
							multipliers[arrayPos] = Integer.parseInt(args[1]);
							
							DatabaseInterface di = new DatabaseInterface();
							
							di.setMultiplier(multiplierName[arrayPos], multipliers[arrayPos]);
	
							Bukkit.getServer().broadcastMessage(ChatColor.GOLD + permissionName[arrayPos] + ": The " + multiplierName[arrayPos] + " multiplier has been set to " + multipliers[arrayPos]);
															
						} catch (NumberFormatException error) {
								
							sender.sendMessage(ChatColor.RED + "Please input an Integer");
								
						}
							
					} else {
							
						sender.sendMessage(ChatColor.RED + "You do not have premission to set the " + multiplierName[arrayPos] + " multiplier");
							
					}
					
				}  else if (args[0].equalsIgnoreCase("add")) {
					
					if (sender.hasPermission(permissionName[arrayPos] + ".addMultiplier") || sender instanceof ConsoleCommandSender) {
						
						try {
							
							multipliers[arrayPos] = multipliers[arrayPos] + Integer.parseInt(args[1]);
							
							DatabaseInterface di = new DatabaseInterface();
							
							di.setMultiplier(multiplierName[arrayPos], multipliers[arrayPos]);
								
							Bukkit.getServer().broadcastMessage(ChatColor.GOLD + permissionName[arrayPos] + ": The " + multiplierName[arrayPos] + " multiplier has been set to " + multipliers[arrayPos]);
															
						} catch (NumberFormatException error) {
								
							sender.sendMessage(ChatColor.RED + "Please input an Integer");
								
						}
						
					} else {
						
						sender.sendMessage(ChatColor.RED + "You do not have premission to add to the " + multiplierName[arrayPos] + " multiplier");
						
					}
										
				} else if (args[0].equalsIgnoreCase("subtract")) {
					
					if (sender.hasPermission(permissionName[arrayPos] + ".subtractMultiplier") || sender instanceof ConsoleCommandSender) {
						
						try {
							
							multipliers[arrayPos] = multipliers[arrayPos] - Integer.parseInt(args[1]);
							
							if (multipliers[arrayPos] <= 0) {
								
								multipliers[arrayPos] = 1;
								
							}		
							
							DatabaseInterface di = new DatabaseInterface();
							
							di.setMultiplier(multiplierName[arrayPos], multipliers[arrayPos]);
								
							Bukkit.getServer().broadcastMessage(ChatColor.GOLD + permissionName[arrayPos] + ": The " + multiplierName[arrayPos] + " multiplier has been set to " + multipliers[arrayPos]);
															
						} catch (NumberFormatException error) {
								
							sender.sendMessage(ChatColor.RED + "Please input an Integer");
								
						}
						
					} else {
						
						sender.sendMessage(ChatColor.RED + "You do not have premission to subtract from the " + multiplierName[arrayPos] + " multiplier");
						
					}
					
				} else {
					
					sender.sendMessage(ChatColor.RED + "Use /" + commandNames.get(arrayPos)  + " help for help on how to use " + permissionName[arrayPos]);
						
				}
				
			}
			
		}

		return false;	
		
	}
	
}
	
	
