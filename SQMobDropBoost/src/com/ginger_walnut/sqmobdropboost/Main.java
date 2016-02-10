package com.ginger_walnut.sqmobdropboost;

import java.util.logging.Logger;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin {

	public final Logger logger = Logger.getLogger("Minecraft");
	public static Main plugin;
	public static int mobDropMultiplier = 1;
	
	private static Plugin pluginMain;
	
	@Override
	public void onDisable() {
		
		pluginMain = null;
		
		PluginDescriptionFile pdfFile = this.getDescription();
		this.logger.info(pdfFile.getName() + " has been disabled!");
		
	}
	
	@Override
	public void onEnable() {
		
		pluginMain = this;
		
		PluginDescriptionFile pdfFile = this.getDescription();
		this.logger.info(pdfFile.getName() + " has been enabled!");
		
		getConfig().options().copyDefaults(true);
		saveConfig();
		
		this.getServer().getPluginManager().registerEvents(new Events(), this);
		
		mobDropMultiplier = getConfig().getInt("mob drop multiplier");
		
	}
	
	public static Plugin getPluginMain() {
		
		return pluginMain;
		
	}
	
	public static void printHelp (CommandSender sender, int type) {
		
		if (type == 0) {
			
			sender.sendMessage(ChatColor.GOLD + "-----------------------------------------------------");
			sender.sendMessage(ChatColor.GOLD + "Current mob drop multiplier: " + ChatColor.BLUE + mobDropMultiplier);
			sender.sendMessage(ChatColor.GOLD + "-----------------------------------------------------");
			
		} else if (type == 1) {
			
			sender.sendMessage(ChatColor.GOLD + "-----------------------------------------------------");
			sender.sendMessage(ChatColor.GOLD + "Current mob drop multiplier: " + ChatColor.BLUE + mobDropMultiplier);
			sender.sendMessage(ChatColor.GOLD + "/mobdropboost help" + ChatColor.BLUE + " - Shows this");
			sender.sendMessage(ChatColor.GOLD + "/mobdropboost set <multiplier>" + ChatColor.BLUE + " - Sets the mob drop multiplier ");
			sender.sendMessage(ChatColor.GOLD + "/mobdropboost add <multiplier>" + ChatColor.BLUE + " - Adds an amount to the multiplier");
			sender.sendMessage(ChatColor.GOLD + "/mobdropboost subtract <multiplier>" + ChatColor.BLUE + " - Subtracts an amount from the multiplier");
			sender.sendMessage(ChatColor.GOLD + "-----------------------------------------------------");
			
		}		
		
	}
	
	public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args) {
		
		if (commandLabel.equalsIgnoreCase("mobdropboost")) {
				
			if (args.length == 0) {
			
				if (sender.hasPermission("SQMobDropBoost.viewHelp") || sender instanceof ConsoleCommandSender) {
					
					printHelp(sender, 1);
					
				} else {
					
					printHelp(sender, 0);
						
				}
					
			} else if (args.length == 1) {
					
				if (args[0].equalsIgnoreCase("help")) {

					if (sender.hasPermission("SQMobDropBoost.viewHelp") || sender instanceof ConsoleCommandSender) {
							
						printHelp(sender, 1);
							
					} else {
							
						printHelp(sender, 0);
							
					}
					
				} else if (args[0].equalsIgnoreCase("set")) {
						
					if (sender.hasPermission("SQMobDropBoost.setMultiplier") || sender instanceof ConsoleCommandSender) {
							
						sender.sendMessage(ChatColor.RED + "Correct usage: /mobdropboost set <multiplier>");
							
					} else {
							
						sender.sendMessage(ChatColor.RED + "You do not have premission to set the mob drop multiplier");
							
					}
						
				} else if (args[0].equalsIgnoreCase("add")) {
					
					if (sender.hasPermission("SQMobDropBoost.addMultiplier") || sender instanceof ConsoleCommandSender) {
						
						sender.sendMessage(ChatColor.RED + "Correct usage: /mobdropboost add <multiplier>");
						
					} else {
						
						sender.sendMessage(ChatColor.RED + "You do not have premission to add to the mob drop multiplier");
						
					}
										
				} else if (args[0].equalsIgnoreCase("subtract")) {
					
					if (sender.hasPermission("SQMobDropBoost.subtractMultiplier") || sender instanceof ConsoleCommandSender) {
						
						sender.sendMessage(ChatColor.RED + "Correct usage: /mobdropboost subtract <multiplier>");
						
					} else {
						
						sender.sendMessage(ChatColor.RED + "You do not have premission to subtract to the mob drop multiplier");
						
					}
					
				} else {
						
					sender.sendMessage(ChatColor.RED + " use /mobdropboost help for help on how to use SQMobDropBoost");
						
				}
										
			} else if (args.length == 2) {
						
				if (args[0].equalsIgnoreCase("set")) {
						
					if (sender.hasPermission("SQMobDropBoost.setMultiplier") || sender instanceof ConsoleCommandSender) {
						
						try {
							
							mobDropMultiplier = Integer.parseInt(args[1]);
							
							getConfig().set("mob drop multiplier", mobDropMultiplier);
							this.saveConfig();
								
							Bukkit.getServer().broadcastMessage(ChatColor.GOLD + "SQMobDropBoost: The mob drop multiplier has been set to " + mobDropMultiplier);
															
						} catch (NumberFormatException error) {
								
							sender.sendMessage(ChatColor.RED + "Please input an Integer");
								
						}
							
					} else {
							
						sender.sendMessage(ChatColor.RED + "You do not have premission to set the mob drop multiplier");
							
					}
					
				}  else if (args[0].equalsIgnoreCase("add")) {
					
					if (sender.hasPermission("SQMobDropBoost.addMultiplier") || sender instanceof ConsoleCommandSender) {
						
						try {
							
							mobDropMultiplier = mobDropMultiplier + Integer.parseInt(args[1]);
							
							getConfig().set("mob drop multiplier", mobDropMultiplier);
							this.saveConfig();
								
							Bukkit.getServer().broadcastMessage(ChatColor.GOLD + "SQMobDropBoost: The mob drop multiplier has been set to " + mobDropMultiplier);
															
						} catch (NumberFormatException error) {
								
							sender.sendMessage(ChatColor.RED + "Please input an Integer");
								
						}
						
					} else {
						
						sender.sendMessage(ChatColor.RED + "You do not have premission to add to the mob drop multiplier");
						
					}
										
				} else if (args[0].equalsIgnoreCase("subtract")) {
					
					if (sender.hasPermission("SQMobDropBoost.subtractMultiplier") || sender instanceof ConsoleCommandSender) {
						
						try {
							
							mobDropMultiplier = mobDropMultiplier - Integer.parseInt(args[1]);
							
							getConfig().set("mob drop multiplier", mobDropMultiplier);
							this.saveConfig();
								
							Bukkit.getServer().broadcastMessage(ChatColor.GOLD + "SQMobDropBoost: The mob drop multiplier has been set to " + mobDropMultiplier);
															
						} catch (NumberFormatException error) {
								
							sender.sendMessage(ChatColor.RED + "Please input an Integer");
								
						}
						
					} else {
						
						sender.sendMessage(ChatColor.RED + "You do not have premission to subtract from the mob drop multiplier");
						
					}
					
				} else {
					
					sender.sendMessage(ChatColor.RED + "Use /mobdropboost help for help on how to use SQMobDropBoost");
						
				}
				
			}
			
		}

		return false;	
		
	}
	
}
	
	
