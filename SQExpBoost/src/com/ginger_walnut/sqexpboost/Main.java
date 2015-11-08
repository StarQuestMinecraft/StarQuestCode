package com.ginger_walnut.sqexpboost;

import java.util.List;
import java.util.logging.Logger;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin {

	public final Logger logger = Logger.getLogger("Minecraft");
	public static Main plugin;
	public static int expBoost = 1;
	
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

		(new ExpBooster()).run();
		
		this.getServer().getPluginManager().registerEvents(new Events(), this);
		
		List<Player> players = Bukkit.getServer().getWorlds().get(0).getPlayers();
		
		for (int i = 0; i < players.size(); i++) {
			
			Player player = players.get(i);
			
			player.setMetadata("lastExp", new FixedMetadataValue(Main.getPluginMain(), player.getTotalExperience()));
			
		}
		
		getConfig().options().copyDefaults(true);
		saveConfig();
		
		expBoost = getConfig().getInt("exp multiplier");
		
	}
	
	public static Plugin getPluginMain() {
		
		return pluginMain;
		
	}
	
	public static void printHelp (CommandSender sender, int type) {
		
		if (type == 0) {
			
			sender.sendMessage(ChatColor.GOLD + "-----------------------------------------------------");
			sender.sendMessage(ChatColor.GOLD + "Current exp multiplier: " + ChatColor.BLUE + expBoost);
			sender.sendMessage(ChatColor.GOLD + "-----------------------------------------------------");
			
		} else if (type == 1){
			
			sender.sendMessage(ChatColor.GOLD + "-----------------------------------------------------");
			sender.sendMessage(ChatColor.GOLD + "Current exp multiplier: " + ChatColor.BLUE + expBoost);
			sender.sendMessage(ChatColor.GOLD + "/expboost help" + ChatColor.BLUE + " - Shows this");
			sender.sendMessage(ChatColor.GOLD + "/expboost set <multiplier>" + ChatColor.BLUE + " - Sets the exp multiplier ");
			sender.sendMessage(ChatColor.GOLD + "/expboost add <multiplier>" + ChatColor.BLUE + " - Adds an amount to the multiplier");
			sender.sendMessage(ChatColor.GOLD + "/expboost subtract <multiplier>" + ChatColor.BLUE + " - Subtracts an amount from the multiplier");
			sender.sendMessage(ChatColor.GOLD + "-----------------------------------------------------");
				
		}		
		
	}
	
	public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args) {
		
		if (commandLabel.equalsIgnoreCase("expboost")) {
				
			if (args.length == 0) {
			
				if (sender.hasPermission("SQExpBoost.viewHelp") || sender instanceof ConsoleCommandSender) {
					
					printHelp(sender, 1);
					
				} else {
					
					printHelp(sender, 0);
						
				}
					
			} else if (args.length == 1) {
					
				if (args[0].equalsIgnoreCase("help")) {

					if (sender.hasPermission("SQExpBoost.viewHelp") || sender instanceof ConsoleCommandSender) {
							
						printHelp(sender, 1);
							
					} else {
							
						printHelp(sender, 0);
							
					}
					
				} else if (args[0].equalsIgnoreCase("set")) {
						
					if (sender.hasPermission("SQExpBoost.setMultiplier") || sender instanceof ConsoleCommandSender) {
							
						sender.sendMessage(ChatColor.RED + "Correct usage: /expboost set <multiplier>");
							
					} else {
							
						sender.sendMessage(ChatColor.RED + "You do not have premission to set the exp multiplier");
							
					}
						
				} else if (args[0].equalsIgnoreCase("add")) {
					
					if (sender.hasPermission("SQExpBoost.addMultiplier") || sender instanceof ConsoleCommandSender) {
						
						sender.sendMessage(ChatColor.RED + "Correct usage: /expboost add <multiplier>");
						
					} else {
						
						sender.sendMessage(ChatColor.RED + "You do not have premission to add to the exp multiplier");
						
					}
										
				} else if (args[0].equalsIgnoreCase("subtract")) {
					
					if (sender.hasPermission("SQExpBoost.subtractMultiplier") || sender instanceof ConsoleCommandSender) {
						
						sender.sendMessage(ChatColor.RED + "Correct usage: /expboost subtract <multiplier>");
						
					} else {
						
						sender.sendMessage(ChatColor.RED + "You do not have premission to subtract to the exp multiplier");
						
					}
					
				} else {
						
					sender.sendMessage(ChatColor.RED + " use /expboost help for help on how to use SQExpBoost");
						
				}
										
			} else if (args.length == 2) {
						
				if (args[0].equalsIgnoreCase("set")) {
						
					if (sender.hasPermission("SQExpBoost.setMultiplier") || sender instanceof ConsoleCommandSender) {
						
						try {
							
							expBoost = Integer.parseInt(args[1]);
							
							getConfig().set("exp multiplier", expBoost);
							this.saveConfig();
								
							Bukkit.getServer().broadcastMessage(ChatColor.GOLD + "ExpBoost: The experince multiplier has been set to " + expBoost);
															
						} catch (NumberFormatException error) {
								
							sender.sendMessage(ChatColor.RED + "Please input an Integer");
								
						}
							
					} else {
							
						sender.sendMessage(ChatColor.RED + "You do not have premission to set the exp multiplier");
							
					}
					
				}  else if (args[0].equalsIgnoreCase("add")) {
					
					if (sender.hasPermission("SQExpBoost.addMultiplier") || sender instanceof ConsoleCommandSender) {
						
						try {
							
							expBoost = expBoost + Integer.parseInt(args[1]);
							
							getConfig().set("exp multiplier", expBoost);
							this.saveConfig();
								
							Bukkit.getServer().broadcastMessage(ChatColor.GOLD + "ExpBoost: The experince multiplier has been set to " + expBoost);
															
						} catch (NumberFormatException error) {
								
							sender.sendMessage(ChatColor.RED + "Please input an Integer");
								
						}
						
					} else {
						
						sender.sendMessage(ChatColor.RED + "You do not have premission to add to the exp multiplier");
						
					}
										
				} else if (args[0].equalsIgnoreCase("subtract")) {
					
					if (sender.hasPermission("SQExpBoost.subtractMultiplier") || sender instanceof ConsoleCommandSender) {
						
						try {
							
							expBoost = expBoost - Integer.parseInt(args[1]);
							
							getConfig().set("exp multiplier", expBoost);
							this.saveConfig();
								
							Bukkit.getServer().broadcastMessage(ChatColor.GOLD + "ExpBoost: The experince multiplier has been set to " + expBoost);
															
						} catch (NumberFormatException error) {
								
							sender.sendMessage(ChatColor.RED + "Please input an Integer");
								
						}
						
					} else {
						
						sender.sendMessage(ChatColor.RED + "You do not have premission to subtract from the exp multiplier");
						
					}
					
				} else {
					
					sender.sendMessage(ChatColor.RED + "Use /expboost help for help on how to use SQExpBoost");
						
				}
				
			}
			
		}

		return false;	
		
	}
	
}
	
	
