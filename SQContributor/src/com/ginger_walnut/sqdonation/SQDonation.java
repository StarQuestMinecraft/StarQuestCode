package com.ginger_walnut.sqdonation;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.logging.Logger;

import net.milkbowl.vault.permission.Permission;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.java.JavaPlugin;

import com.ginger_walnut.sqdonation.database.DatabaseInterface;

public class SQDonation extends JavaPlugin{
	
	public final Logger logger = Logger.getLogger("Minecraft");
	public static List<String> topDonaters = new ArrayList<String>();
	public static HashMap<String, Integer> donaterMap = new HashMap<String, Integer>();
	public static HashMap<Integer, String> groupMap = new HashMap<Integer, String>();
	public static boolean updatedLocalCopy = false;
	
	public static Plugin pluginMain;
	
	public static Permission permission = null;
	
	public FileConfiguration config = null;
	
	@Override
	public void onDisable() {
		
		pluginMain = null;
		
		PluginDescriptionFile pdfFile = this.getDescription();
		this.logger.info(pdfFile.getName() + " has been disabled!");
		
		saveDefaultConfig();
		
	}
	
	@Override
	public void onEnable() {
		
		pluginMain = this;
		
		PluginDescriptionFile pdfFile = this.getDescription();
		this.logger.info(pdfFile.getName() + " has been enabled!");
		
		if (!new File(this.getDataFolder(), "config.yml").exists()) {
			
			saveDefaultConfig();
			saveConfig();
			
		}
		
		config = getConfig();
		
		List<String> levels = new ArrayList<String>();
		levels.addAll(config.getConfigurationSection("groups").getKeys(false));
		
		for (String level : levels) {
			
			groupMap.put(config.getInt("groups." + level + ".amount"), config.getString("groups." + level + ".group"));
			
		}
		
		(new DatabaseChecker()).run();

	}
	
	public void printHelp(CommandSender sender){
		
		if (sender instanceof ConsoleCommandSender || sender.hasPermission("SQDonation.viewHelp")) {
			
			sender.sendMessage(ChatColor.GOLD + "-----------------------------------------------------");
			sender.sendMessage(ChatColor.GOLD + "Current top donator: " + ChatColor.BLUE + topDonaters.get(0));
			sender.sendMessage(ChatColor.GOLD + "/donation help" + ChatColor.BLUE + " - Shows this");
			sender.sendMessage(ChatColor.GOLD + "/donation view <player>" + ChatColor.BLUE + " - Shows how much someone has donated");
			sender.sendMessage(ChatColor.GOLD + "/donation top" + ChatColor.BLUE + " - Shows the top 10 donaters");
			sender.sendMessage(ChatColor.GOLD + "/donation set <player> <amount>" + ChatColor.BLUE + " - Sets how much someone has donated");
			sender.sendMessage(ChatColor.GOLD + "/donation add <player> <amount>" + ChatColor.BLUE + " - Adds to how much someone has donated");
			sender.sendMessage(ChatColor.GOLD + "-----------------------------------------------------");
			
		} else {
			
			sender.sendMessage(ChatColor.GOLD + "-----------------------------------------------------");
			sender.sendMessage(ChatColor.GOLD + "Current top donator: " + ChatColor.BLUE + topDonaters.get(0));
			sender.sendMessage(ChatColor.GOLD + "/donation help" + ChatColor.BLUE + " - Shows this");
			sender.sendMessage(ChatColor.GOLD + "/donation view <player>" + ChatColor.BLUE + " - Shows how much someone has donated");
			sender.sendMessage(ChatColor.GOLD + "/donation top" + ChatColor.BLUE + " - Shows the top 10 donaters");
			sender.sendMessage(ChatColor.GOLD + "-----------------------------------------------------");
			
		}
		
	}
	
	public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args) {
		
		if (commandLabel.equals("donation") || commandLabel.equals("donations") || commandLabel.equals("donater") || commandLabel.equals("donaters") || commandLabel.equals("don")) {
			
			if (!updatedLocalCopy) {
				
				DatabaseInterface.updateLocalCopy();
				
				updatedLocalCopy = true;
				
			}
			
			if (args.length == 0) {
				
				printHelp(sender);
				
			} else if (args.length == 1) {
				
				if (args[0].equals("help")) {
					
					printHelp(sender);
					
				} else if (args[0].equals("view")) {
					
					if (sender instanceof Player) {
						
						Player player = (Player) sender;
						
						if (donaterMap.containsKey(player.getUniqueId())) {
							
							player.sendMessage(ChatColor.GREEN + "You have donated $" + donaterMap.get(player.getUniqueId()));
							
						} else {
							
							player.sendMessage(ChatColor.RED + "You have not donated");
							
						}
						
					} else {
						
						sender.sendMessage(ChatColor.RED + "You must be a player to use this command");
						
					}
	
				} else if (args[0].equals("top")) {
					
					sender.sendMessage(ChatColor.GOLD + "-----------------------------------------------------");

					for (int i = 0; i < topDonaters.size(); i ++) {
						
						sender.sendMessage(ChatColor.GOLD + "" + (i + 1) + ". " + topDonaters.get(i) + " - " + ChatColor.BLUE + "$" + donaterMap.get(topDonaters.get(i)));
						
					}
					
					sender.sendMessage(ChatColor.GOLD + "-----------------------------------------------------");
	
				} else {
					
					sender.sendMessage(ChatColor.RED + "Use /donation help to see how to use SQContributor");
					
				}
				
			} else if (args.length == 2) {
					
				if (args[0].equals("view")) {
					
					if (Bukkit.getOfflinePlayer(args[1]) != null) {
						
						OfflinePlayer player = (OfflinePlayer) Bukkit.getOfflinePlayer(args[1]);
						
						if (donaterMap.containsKey(player.getName())) {
							
							sender.sendMessage(ChatColor.GREEN + "They have donated $" + donaterMap.get(player.getName()));
							
						} else {
							
							sender.sendMessage(ChatColor.RED + "They have not donated");
							
						}
						
					} else {
						
						sender.sendMessage(ChatColor.RED + "You must input a player");
						
					}
					
				} else {
					
					sender.sendMessage(ChatColor.RED + "Use /donation help to see how to use SQContributor");
					
				}
				
			} else if (args.length == 3) {
				
				 if (args[0].equals("set")) {
						
					if (sender.hasPermission("SQDonation.setAmount") || sender instanceof ConsoleCommandSender) {
						
						if (Bukkit.getOfflinePlayer(args[1]) != null) {
							
							OfflinePlayer player = (OfflinePlayer) Bukkit.getOfflinePlayer(args[1]);
								
							int amount  = Integer.parseInt(args[2]);
							
							DatabaseInterface.setAmount(player.getUniqueId().toString(), amount);
								
						} else {
								
							sender.sendMessage(ChatColor.RED + "You must input a player");
								
						}
						
					}
					
				} else if(args[0].equals("add")) {
					
					if (sender.hasPermission("SQDonation.setAmount") || sender instanceof ConsoleCommandSender) {
					
						if (Bukkit.getOfflinePlayer(args[1]) != null) {
							
							OfflinePlayer player = (OfflinePlayer) Bukkit.getOfflinePlayer(args[1]);
								
							int amount  = Integer.parseInt(args[2]);
							
							DatabaseInterface.addAmount(player.getUniqueId().toString(), amount);
								
						} else {
								
							sender.sendMessage(ChatColor.RED + "You must input a player");
								
						}
						
					}
					
				} else {
					
					sender.sendMessage(ChatColor.RED + "Use /donation help to see how to use SQContributor");
					
				}
				
			} else {
				
				sender.sendMessage(ChatColor.RED + "Use /donation help to see how to use SQContributor");
				
			}
			
		}
		
		return false;
		
	}
	
	public static Plugin getPluginMain() {
		
		return pluginMain;
		
	}
	
}
