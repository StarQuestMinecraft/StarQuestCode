package com.ginger_walnut.sqsheepshearboost;

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
	public static int sheepShearMultiplier = 1;
	
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
		
		sheepShearMultiplier = getConfig().getInt("sheep shearing multiplier");
		
	}
	
	public static Plugin getPluginMain() {
		
		return pluginMain;
		
	}
	
	public static void printHelp (CommandSender sender, int type) {
		
		if (type == 0) {
			
			sender.sendMessage(ChatColor.GOLD + "-----------------------------------------------------");
			sender.sendMessage(ChatColor.GOLD + "Current sheep shearing multiplier: " + ChatColor.BLUE + sheepShearMultiplier);
			sender.sendMessage(ChatColor.GOLD + "-----------------------------------------------------");
			
		} else if (type == 1) {
			
			sender.sendMessage(ChatColor.GOLD + "-----------------------------------------------------");
			sender.sendMessage(ChatColor.GOLD + "Current sheep shearing multiplier: " + ChatColor.BLUE + sheepShearMultiplier);
			sender.sendMessage(ChatColor.GOLD + "/sheepshearboost help" + ChatColor.BLUE + " - Shows this");
			sender.sendMessage(ChatColor.GOLD + "/sheepshearboost set <multiplier>" + ChatColor.BLUE + " - Sets the sheep shearing multiplier ");
			sender.sendMessage(ChatColor.GOLD + "/sheepshearboost add <multiplier>" + ChatColor.BLUE + " - Adds an amount to the multiplier");
			sender.sendMessage(ChatColor.GOLD + "/sheepshearboost subtract <multiplier>" + ChatColor.BLUE + " - Subtracts an amount from the multiplier");
			sender.sendMessage(ChatColor.GOLD + "-----------------------------------------------------");
			
		}		
		
	}
	
	public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args) {
		
		if (commandLabel.equalsIgnoreCase("sheepshearboost")) {
				
			if (args.length == 0) {
			
				if (sender.hasPermission("SQSheepShearBoost.viewHelp") || sender instanceof ConsoleCommandSender) {
					
					printHelp(sender, 1);
					
				} else {
					
					printHelp(sender, 0);
						
				}
					
			} else if (args.length == 1) {
					
				if (args[0].equalsIgnoreCase("help")) {

					if (sender.hasPermission("SQSheepShearBoost.viewHelp") || sender instanceof ConsoleCommandSender) {
							
						printHelp(sender, 1);
							
					} else {
							
						printHelp(sender, 0);
							
					}
					
				} else if (args[0].equalsIgnoreCase("set")) {
						
					if (sender.hasPermission("SQSheepShearBoost.setMultiplier") || sender instanceof ConsoleCommandSender) {
							
						sender.sendMessage(ChatColor.RED + "Correct usage: /sheepshearboost set <multiplier>");
							
					} else {
							
						sender.sendMessage(ChatColor.RED + "You do not have premission to set the sheep shearing multiplier");
							
					}
						
				} else if (args[0].equalsIgnoreCase("add")) {
					
					if (sender.hasPermission("SQSheepShearBoost.addMultiplier") || sender instanceof ConsoleCommandSender) {
						
						sender.sendMessage(ChatColor.RED + "Correct usage: /sheepshearboost add <multiplier>");
						
					} else {
						
						sender.sendMessage(ChatColor.RED + "You do not have premission to add to the sheep shearing multiplier");
						
					}
										
				} else if (args[0].equalsIgnoreCase("subtract")) {
					
					if (sender.hasPermission("SQSheepShearBoost.subtractMultiplier") || sender instanceof ConsoleCommandSender) {
						
						sender.sendMessage(ChatColor.RED + "Correct usage: /sheepshearboost subtract <multiplier>");
						
					} else {
						
						sender.sendMessage(ChatColor.RED + "You do not have premission to subtract to the sheep shearing multiplier");
						
					}
					
				} else {
						
					sender.sendMessage(ChatColor.RED + " use /sheepshearboost help for help on how to use SQSheepShearBoost");
						
				}
										
			} else if (args.length == 2) {
						
				if (args[0].equalsIgnoreCase("set")) {
						
					if (sender.hasPermission("SQSheepShearBoost.setMultiplier") || sender instanceof ConsoleCommandSender) {
						
						try {
							
							sheepShearMultiplier = Integer.parseInt(args[1]);
							
							getConfig().set("sheep shearing multiplier", sheepShearMultiplier);
							this.saveConfig();
								
							Bukkit.getServer().broadcastMessage(ChatColor.GOLD + "SQSheepShearBoost: The sheep shearing multiplier has been set to " + sheepShearMultiplier);
															
						} catch (NumberFormatException error) {
								
							sender.sendMessage(ChatColor.RED + "Please input an Integer");
								
						}
							
					} else {
							
						sender.sendMessage(ChatColor.RED + "You do not have premission to set the sheep shearing multiplier");
							
					}
					
				}  else if (args[0].equalsIgnoreCase("add")) {
					
					if (sender.hasPermission("SQSheepShearBoost.addMultiplier") || sender instanceof ConsoleCommandSender) {
						
						try {
							
							sheepShearMultiplier = sheepShearMultiplier + Integer.parseInt(args[1]);
							
							getConfig().set("sheep shearing multiplier", sheepShearMultiplier);
							this.saveConfig();
								
							Bukkit.getServer().broadcastMessage(ChatColor.GOLD + "SQSheepShearBoost: The sheep shearing multiplier has been set to " + sheepShearMultiplier);
															
						} catch (NumberFormatException error) {
								
							sender.sendMessage(ChatColor.RED + "Please input an Integer");
								
						}
						
					} else {
						
						sender.sendMessage(ChatColor.RED + "You do not have premission to add to the sheep shearing multiplier");
						
					}
										
				} else if (args[0].equalsIgnoreCase("subtract")) {
					
					if (sender.hasPermission("SQSheepShearBoost.subtractMultiplier") || sender instanceof ConsoleCommandSender) {
						
						try {
							
							sheepShearMultiplier = sheepShearMultiplier - Integer.parseInt(args[1]);
							
							getConfig().set("sheep shearing multiplier", sheepShearMultiplier);
							this.saveConfig();
								
							Bukkit.getServer().broadcastMessage(ChatColor.GOLD + "SQSheepShearBoost: The sheep shearing multiplier has been set to " + sheepShearMultiplier);
															
						} catch (NumberFormatException error) {
								
							sender.sendMessage(ChatColor.RED + "Please input an Integer");
								
						}
						
					} else {
						
						sender.sendMessage(ChatColor.RED + "You do not have premission to subtract from the sheep shearing multiplier");
						
					}
					
				} else {
					
					sender.sendMessage(ChatColor.RED + "Use /sheepshearboost help for help on how to use SQSheepShearBoost");
						
				}
				
			}
			
		}

		return false;	
		
	}
	
}
	
	
