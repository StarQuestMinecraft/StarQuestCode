package com.ginger_walnut.sqboosters;

import java.sql.Time;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
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
import com.greatmancode.craftconomy3.Common;
import com.greatmancode.craftconomy3.tools.interfaces.Loader;

public class SQBoosters extends JavaPlugin {

	public final Logger logger = Logger.getLogger("Minecraft");
	public static SQBoosters plugin;
	public static int[] multipliers = new int[] {1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1};
	public static boolean[] enabledBoosters = new boolean[] {false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false};
	public static String[] configNames = new String[] {"expbooster", "mobdropbooster", "sheepshearingbooster", "shopbooster", "speedbooster", "mcmmo-miningbooster", "mcmmo-woodcuttingbooster", "mcmmo-herbalism", "mcmmo-fishing", "mcmmo-excavation", "mcmmo-unarmed", "mcmmo-archery", "mcmmo-swords", "mcmmo-axes", "mcmmo-repair", "mcmmo-acrobatics", "mcmmo-alchemy", "mcmmo-piloting"};
	public static String[] permissionName = new String[] {"SQExpBoost", "SQMobDropBoost", "SQSheepShearBoost", "SQShopBooster", "SQSpeedBooster", "SQMMO-MiningBooster", "SQMMO-WoodcuttingBooster", "SQMMO-HerbalismBooster", "SQMMO-FishingBooster", "SQMMO-ExcavationBooster", "SQMMO-UnarmedBooster", "SQMMO-ArcheryBooster", "SQMMO-SwordsBooster", "SQMMO-AxesBooster", "SQMMO-RepairBooster", "SQMMO-AcrobaticsBooster", "SQMMO-AlchemyBooster", "SQMMO-PilotingBooster"};
	public static String[] multiplierName = new String[] {"exp", "mob drop", "sheep shearing", "shop", "speed", "MCMMO-mining", "MCMMO-woodcutting", "MCMMO-herbalism", "MCMMO-fishing", "MCMMO-excavation", "MCMMO-unarmed", "MCMMO-archery", "MCMMO-swords", "MCMMO-axes", "MCMMO-repair", "MCMMO-acrobatics", "MCMMO-alchemy", "MCMMO-piloting"};
	
	public static List<Integer> databaseIDs = new ArrayList<Integer>();
	public static List<String> databaseBoosters = new ArrayList<String>();
	public static List<Integer> databaseMultipliers = new ArrayList<Integer>();
	public static List<String> databasePurchasers = new ArrayList<String>();
	public static List<Date> databaseExpirationDates = new ArrayList<Date>();
	public static List<Time> databaseExpirationTimes = new ArrayList<Time>();
	
	public static Common cc3;
	
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
			
			player.setMetadata("lastExp", new FixedMetadataValue(this, player.getTotalExperience()));
			
		}
		
		getConfig().options().copyDefaults(true);
		saveConfig();
		
		FileConfiguration config = getConfig();
		
		DatabaseInterface.updateLocalCopy();
		
		for (int i = 0; i < enabledBoosters.length; i ++) {
			
			enabledBoosters[i] = config.getBoolean(configNames[i]);
			
		}
		
		for (int i = 0; i < multipliers.length; i ++) {
			
			multipliers[i] = DatabaseInterface.getMultiplier(configNames[i]);
			
		}
		
		Plugin cc3Plugin = Bukkit.getPluginManager().getPlugin("Craftconomy3");
		
		if (cc3Plugin != null) {
			
			cc3 = (Common) ((Loader) cc3Plugin).getCommon();
			
		}
		
		(new DatabaseChecker()).run();
		
	}
	
	public static Plugin getPluginMain() {
		
		return pluginMain;
		
	}
	
	public static void printHelp (CommandSender sender, boolean permission, String multiplierName, int multiplierPosition, String commandName) {
		
		if (!permission) {
			
			sender.sendMessage(ChatColor.GOLD + "-----------------------------------------------------");
			sender.sendMessage(ChatColor.GOLD + "Current " + multiplierName + " multiplier: " + ChatColor.BLUE + multipliers[multiplierPosition]);
			sender.sendMessage(ChatColor.GOLD + "/" + commandName + " help" + ChatColor.BLUE + " - Shows this");
			sender.sendMessage(ChatColor.GOLD + "/" + commandName + " view" + ChatColor.BLUE + " - Shows a brekadown of the current booster");
			sender.sendMessage(ChatColor.GOLD + "-----------------------------------------------------");
			
		} else {
			
			sender.sendMessage(ChatColor.GOLD + "-----------------------------------------------------");
			sender.sendMessage(ChatColor.GOLD + "Current " + multiplierName + " multiplier: " + ChatColor.BLUE + multipliers[multiplierPosition]);
			sender.sendMessage(ChatColor.GOLD + "/" + commandName + " help" + ChatColor.BLUE + " - Shows this");
			sender.sendMessage(ChatColor.GOLD + "/" + commandName + " add <multiplier> <purchaser>" + ChatColor.BLUE + " - Adds a booster with the specified purchaser ");
			sender.sendMessage(ChatColor.GOLD + "/" + commandName + " add <multiplier>" + ChatColor.BLUE + " - Adds a booster with no purchaser");
			sender.sendMessage(ChatColor.GOLD + "/" + commandName + " view" + ChatColor.BLUE + " - Shows a brekadown of the current booster");
			sender.sendMessage(ChatColor.GOLD + "-----------------------------------------------------");
				
		}		
		
	}
	
	public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args) {
		
		List<String> commandNames = new ArrayList<String>(); 
		commandNames.add("expboost");
		commandNames.add("mobdropboost");
		commandNames.add("sheepshearboost");
		commandNames.add("shopboost");
		commandNames.add("speedboost");
		commandNames.add("mcmmo-miningboost");
		commandNames.add("mcmmo-woodcuttingboost");
		commandNames.add("mcmmo-herbalismboost");
		commandNames.add("mcmmo-fishingboost");
		commandNames.add("mcmmo-excavationboost");
		commandNames.add("mcmmo-unarmedboost");
		commandNames.add("mcmmo-archeryboost");
		commandNames.add("mcmmo-swordsboost");
		commandNames.add("mcmmo-axesboost");
		commandNames.add("mcmmo-reapirboost");
		commandNames.add("mcmmo-acrobaticsboost");
		commandNames.add("mcmmo-alchemyboost");
		commandNames.add("mcmmo-pilotingboost");
		
		if (commandNames.contains(commandLabel)) {
			
			int arrayPos = 0;
			
			for (int i = 0; i < commandNames.size(); i ++) {
				
				if (commandNames.get(i).equals(commandLabel)) {
					
					arrayPos = i;					
					
				}
				
			}
			
			if (enabledBoosters[arrayPos]) {
				
				if (args.length == 0) {
					
					if (sender.hasPermission("SQBoosters.viewHelp") || sender instanceof ConsoleCommandSender) {
						
						printHelp(sender, true, multiplierName[arrayPos], arrayPos, commandNames.get(arrayPos));
						
					} else {
						
						printHelp(sender, false, multiplierName[arrayPos], arrayPos, commandNames.get(arrayPos));
							
					}
						
				} else if (args.length == 1) {
						
					if (args[0].equalsIgnoreCase("help")) {

						if (sender.hasPermission("SQBoosters.viewHelp") || sender instanceof ConsoleCommandSender) {
							
							printHelp(sender, true, multiplierName[arrayPos], arrayPos, commandNames.get(arrayPos));
							
						} else {
							
							printHelp(sender, false, multiplierName[arrayPos], arrayPos, commandNames.get(arrayPos));
								
						}
						
					} else if (args[0].equalsIgnoreCase("view")) {
						
						sender.sendMessage(ChatColor.GOLD + "-----------------------------------------------------");
						sender.sendMessage(ChatColor.GOLD + "Current " + multiplierName[arrayPos] + " multiplier: " + ChatColor.BLUE + multipliers[arrayPos]);
						sender.sendMessage(ChatColor.BLUE + "multiplier - purchaser - expires in");
						
						for (int i = 0; i < databaseBoosters.size(); i ++) {
							
							if (databaseBoosters.get(i).equals(configNames[arrayPos])) {
								
								if (databasePurchasers.get(i) != null) {
									
									if (databaseExpirationDates.get(i).getDay() > new Date().getDay()) {
										
										if (databaseExpirationTimes.get(i).getMinutes() > new Date().getMinutes()) {
											
											sender.sendMessage(ChatColor.BLUE + Integer.toString(databaseMultipliers.get(i)) + " - " + databasePurchasers.get(i) + " - " + Integer.toString(databaseExpirationTimes.get(i).getHours() - new Date().getHours() + 24) + " hours and " + Integer.toString(databaseExpirationTimes.get(i).getMinutes() - new Date().getMinutes()) + " minutes");
											
										} else {
											
											sender.sendMessage(ChatColor.BLUE + Integer.toString(databaseMultipliers.get(i)) + " - " + databasePurchasers.get(i) + " - " + Integer.toString(databaseExpirationTimes.get(i).getHours() - new Date().getHours() + 23) + " hours and " + Integer.toString(databaseExpirationTimes.get(i).getMinutes() - new Date().getMinutes() + 60) + " minutes");
											
										}

										
									} else if (databaseExpirationDates.get(i).getDay() == new Date().getDay()) {
										
										if (databaseExpirationTimes.get(i).getMinutes() > new Date().getMinutes()) {
										
											sender.sendMessage(ChatColor.BLUE + Integer.toString(databaseMultipliers.get(i)) + " - " + databasePurchasers.get(i) + " - " + Integer.toString(databaseExpirationTimes.get(i).getHours() - new Date().getHours()) + " hours and " + Integer.toString(databaseExpirationTimes.get(i).getMinutes() - new Date().getMinutes()) + " minutes");
											
										} else {
											
											sender.sendMessage(ChatColor.BLUE + Integer.toString(databaseMultipliers.get(i)) + " - " + databasePurchasers.get(i) + " - " + Integer.toString(databaseExpirationTimes.get(i).getHours() - new Date().getHours() - 1) + " hours and " + Integer.toString(databaseExpirationTimes.get(i).getMinutes() - new Date().getMinutes() + 60) + " minutes");
											
										}
										
									} else {
										
										sender.sendMessage(ChatColor.BLUE + Integer.toString(databaseMultipliers.get(i)) + " - " + databasePurchasers.get(i) + " - soon");
										
									}
													
								} else {
									
									if (databaseExpirationDates.get(i).getDay() > new Date().getDay()) {
										
										if (databaseExpirationTimes.get(i).getMinutes() > new Date().getMinutes()) {
											
											sender.sendMessage(ChatColor.BLUE + Integer.toString(databaseMultipliers.get(i)) + " - anonymous person - " + Integer.toString(databaseExpirationTimes.get(i).getHours() - new Date().getHours() + 24) + " hours and " + Integer.toString(databaseExpirationTimes.get(i).getMinutes() - new Date().getMinutes()) + " minutes");
											
										} else {
											
											sender.sendMessage(ChatColor.BLUE + Integer.toString(databaseMultipliers.get(i)) + " - anonymous person - " + Integer.toString(databaseExpirationTimes.get(i).getHours() - new Date().getHours() + 23) + " hours and " + Integer.toString(databaseExpirationTimes.get(i).getMinutes() - new Date().getMinutes() + 60) + " minutes");
											
										}

										
									} else if (databaseExpirationDates.get(i).getDay() == new Date().getDay()) {
										
										if (databaseExpirationTimes.get(i).getMinutes() > new Date().getMinutes()) {
										
											sender.sendMessage(ChatColor.BLUE + Integer.toString(databaseMultipliers.get(i)) + " - anonymous person - " + Integer.toString(databaseExpirationTimes.get(i).getHours() - new Date().getHours()) + " hours and " + Integer.toString(databaseExpirationTimes.get(i).getMinutes() - new Date().getMinutes()) + " minutes");
											
										} else {
											
											sender.sendMessage(ChatColor.BLUE + Integer.toString(databaseMultipliers.get(i)) + " - anonymous person - " + Integer.toString(databaseExpirationTimes.get(i).getHours() - new Date().getHours() - 1) + " hours and " + Integer.toString(databaseExpirationTimes.get(i).getMinutes() - new Date().getMinutes() + 60) + " minutes");
											
										}
										
									} else {
										
										sender.sendMessage(ChatColor.BLUE + Integer.toString(databaseMultipliers.get(i)) + " - anonymous person - soon");
										
									}
									
								}
								
							}
							
						}
						
						sender.sendMessage(ChatColor.GOLD + "-----------------------------------------------------");
						
					} else if (args[0].equalsIgnoreCase("add")) {
						
						if (sender.hasPermission("SQBoosters.addMultiplier") || sender instanceof ConsoleCommandSender) {
							
							sender.sendMessage(ChatColor.RED + "Correct usage: /" + commandNames.get(arrayPos)  + " add <multiplier>");
							
						} else {
							
							sender.sendMessage(ChatColor.RED + "You do not have premission to add to the " + multiplierName[arrayPos] + " multiplier");
							
						}
											
					} else {
							
						sender.sendMessage(ChatColor.RED + " use /" + commandNames.get(arrayPos)  + " help for help on how to use " + permissionName[arrayPos]);
							
					}
											
				} else if (args.length == 2) {
							
					if (args[0].equalsIgnoreCase("add")) {
						
						if (sender.hasPermission("SQBoosters.addMultiplier") || sender instanceof ConsoleCommandSender) {
							
							try {
		
								DatabaseInterface.addMultiplier(configNames[arrayPos], Integer.parseInt(args[1]), null);
								
								sender.sendMessage(ChatColor.GREEN + "The booster may take up to five minutes to register across every server");
																
							} catch (NumberFormatException error) {
									
								sender.sendMessage(ChatColor.RED + "Please input an Integer");
									
							}
							
						} else {
							
							sender.sendMessage(ChatColor.RED + "Use /" + commandNames.get(arrayPos)  + " help for help on how to use " + permissionName[arrayPos]);
									
						} 
											
					} else {
					
						sender.sendMessage(ChatColor.RED + "Use /" + commandNames.get(arrayPos)  + " help for help on how to use " + permissionName[arrayPos]);
					
					}
						
				} else if (args.length == 3) {
					
					if (args[0].equalsIgnoreCase("add")) {
				
						if (sender.hasPermission("SQBoosters.addMultiplier") || sender instanceof ConsoleCommandSender) {
					
							try {
						
								DatabaseInterface.addMultiplier(configNames[arrayPos],Integer.parseInt(args[1]), args[2]);
							
								sender.sendMessage(ChatColor.GREEN + "The booster may take up to five minutes to register across every server");
														
							} catch (NumberFormatException error) {
								
								sender.sendMessage(ChatColor.RED + "Please input an Integer");
								
								return false;
							
							}
					
						} else {
					
							sender.sendMessage(ChatColor.RED + "You do not have premission to add to the " + multiplierName[arrayPos] + " multiplier");
					
						}
									
					} else {
						
					sender.sendMessage(ChatColor.RED + "Use /" + commandNames.get(arrayPos)  + " help for help on how to use " + permissionName[arrayPos]);
							
					} 
				
				} else {
						
					sender.sendMessage(ChatColor.RED + "Use /" + commandNames.get(arrayPos)  + " help for help on how to use " + permissionName[arrayPos]);
							
				}
				
			} else {
				
				sender.sendMessage(ChatColor.RED + "That booster is not enabled");
				
			}
	
		} else if (commandLabel.equalsIgnoreCase("thank"))  {
			
			if (sender instanceof Player) {
				
				Player player = (Player) sender;

				if (args.length == 0) {
					
					player.sendMessage(ChatColor.RED + "The correct use is /thank <person> <amount>");
					
				} else if (args.length == 1) {
					
					player.sendMessage(ChatColor.RED + "The correct use is /thank <person> <amount>");
					
				} else if (args.length == 2) {
					
					int amount = 0;
					
					if (!databasePurchasers.contains(args[0])) {
						
						player.sendMessage(ChatColor.RED + "That person must have an active booster");
						
					}
					
					try {
						
						amount = Integer.parseInt(args[1]);
						
					} catch (NumberFormatException e) {
						
						player.sendMessage(ChatColor.RED + "You must input a number");
						
						return false;
						
					}
					
					if (amount < 0) {
						
						amount = amount * -1;
						
					}
					
					if (SQBoosters.cc3.getAccountManager().getAccount(player.getName()).hasEnough(amount, player.getWorld().getName(), "credit")) {
							
						SQBoosters.cc3.getAccountManager().getAccount(player.getName()).withdraw(amount, player.getWorld().getName(), "credit");
						SQBoosters.cc3.getAccountManager().getAccount(args[0]).deposit(amount, player.getWorld().getName(), "credit");
						
						Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "ee say " + ChatColor.GOLD + player.getName() + " has thanked " + args[0] + " with " + args[1] + " credits");
								
					} else {
						
						player.sendMessage(ChatColor.RED + "You do not have enough money");
						
						
					}
					
				} else {
					
					player.sendMessage(ChatColor.RED + "The correct use is /thank <person> <amount>");
					
				}
				
			}
			
		}

		return false;	
		
	}
	
	public static int getSellBooster() {
		
		return multipliers[3];
		
	}
	
	public static int getSpeedBooster() {
		
		return multipliers[4];
		
	}
	
	public static int getMCMMOBooster(String skill) {
		
		switch(skill) {
		
			case "mining": return multipliers[5];
			case "woodcutting": return multipliers[6];
			case "herbalism": return multipliers[7];
			case "fishing": return multipliers[8];
			case "excavation": return multipliers[9];
			case "unarmed": return multipliers[10];
			case "archery": return multipliers[11];
			case "swords": return multipliers[12];
			case "axes": return multipliers[13];
			case "repair": return multipliers[14];
			case "acrobatics": return multipliers[15];
			case "alchemy": return multipliers[16];
			case "piloting": return multipliers[17];
			default: return 1;
		
		}
		
	}
	
}
	
	
