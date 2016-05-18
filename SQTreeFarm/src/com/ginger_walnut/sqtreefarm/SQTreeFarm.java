package com.ginger_walnut.sqtreefarm;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.logging.Logger;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.java.JavaPlugin;

public class SQTreeFarm extends JavaPlugin{

	public final Logger logger = Logger.getLogger("Minecraft");
	public static SQTreeFarm plugin;
	
	private static Plugin pluginMain;
	
	public static File[] treefarms;
	
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
		
		this.getServer().getPluginManager().registerEvents(new Events(), this);
		
		File farmsPath = new File(this.getDataFolder().getAbsolutePath() + "/farms");
		
		if (farmsPath.exists()) {
			
			treefarms = farmsPath.listFiles();
		
		} else {
			
			farmsPath.mkdirs();
			treefarms = farmsPath.listFiles();
			
		}
		
		//(new SaplingPlacer()).run();
		
	}
	
	public static Plugin getPluginMain() {
		
		return pluginMain;
		
	}
	
	private static void printHelp (Player player) {
		
		player.sendMessage(ChatColor.GOLD + "-----------------------------------------------------");
		player.sendMessage(ChatColor.GOLD + "/treefarm help" + ChatColor.BLUE + " - Shows this");
		player.sendMessage(ChatColor.GOLD + "/treefarm create <name> <x> <y> <z>" + ChatColor.BLUE + " - Creates a new treefarm from the current position ");
		player.sendMessage(ChatColor.GOLD + "/treefarm lists" + ChatColor.BLUE + " - Lists all of the treefarms");
		player.sendMessage(ChatColor.GOLD + "/treefarm destroy <name>" + ChatColor.BLUE + " - Destroys a treefarm");
		player.sendMessage(ChatColor.GOLD + "-----------------------------------------------------");
		
	}
	
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args) {
		
		Player player = (Player) sender;
		
		if (player.hasPermission("SQTreeFarm.createFarms") || player.hasPermission("SQTreeFarm.destroyFarms")) {
		
			if (commandLabel.equalsIgnoreCase("treefarm")) {
			
				if (args.length == 0) {
					
					printHelp(player);
					
				} else if (args.length == 1) {
					
					if (args[0].equalsIgnoreCase("help")) {
						
						printHelp(player);
						
					} else if (args[0].equalsIgnoreCase("list")) {
					
						File farmsPath = new File(this.getDataFolder().getAbsolutePath() + "/farms");
						
						if (farmsPath.exists()) {
							
							treefarms = farmsPath.listFiles();
							
							player.sendMessage(ChatColor.GOLD + "-----------------------------------------------------");
							
							for (File farm : treefarms) {
								
								player.sendMessage(ChatColor.BLUE + farm.getName().substring(0, farm.getName().length() - 4));
								
							}							
								
							player.sendMessage(ChatColor.GOLD + "-----------------------------------------------------");
							
						} else {
							
							player.sendMessage(ChatColor.RED + "No treefarms exist");
							
						}
							
					} else {
					
						
						player.sendMessage(ChatColor.RED + "Use /treefarm help on help on how to use SQTreeFarm");
						
					} 
				
				} else if (args.length == 2) {
					
					if (args[0].equals("destroy")) {
					
						File farms = new File(this.getDataFolder().getAbsolutePath() + "/farms");
						
						if (farms.exists()) {
							
							File newFarm = new File(farms.getAbsolutePath() + "/" + args[1] + ".txt");
							
							if (newFarm.exists()) {
							
								newFarm.delete();
								
								treefarms = farms.listFiles();
								
								player.sendMessage(ChatColor.GREEN + "The treefarm has been destroyed");
	
							} else {
								
								player.sendMessage(ChatColor.RED + "That treefarm does not exist");
								
							}
							
						} else {
							
							player.sendMessage(ChatColor.RED + "That treefarm does not exist");
							
						}
						
					} else {
						
						player.sendMessage(ChatColor.RED + "Use /treefarm help on help on how to use SQTreeFarm");
						
					}
					
				} else if (args.length == 5) {
					
					if (args[0].equalsIgnoreCase("create")) {
						
						File farms = new File(this.getDataFolder().getAbsolutePath() + "/farms");
						
						if (!farms.exists()) {
							
							farms.mkdirs();
							
						}
						
						File newFarm = new File(farms.getAbsolutePath() + "/" + args[1] + ".txt");
						
						if (newFarm.exists()) {
						
							player.sendMessage(ChatColor.RED + "A treefarm with that name already exists");

						} else {
							
							try {
								
								PrintWriter writer = new PrintWriter(newFarm);
								
								writer.println(player.getLocation().getBlockX());
								writer.println(player.getLocation().getBlockY());
								writer.println(player.getLocation().getBlockZ());
								writer.println(args[2]);
								writer.println(args[3]);
								writer.println(args[4]);
								
								writer.close();
								
								treefarms = farms.listFiles();
								
								player.sendMessage(ChatColor.GREEN + "A new treefarm has been created");
								
							} catch (IOException e) {
								
								player.sendMessage(ChatColor.RED + "An exception was encounterd while creating the treefarm");
								
								e.printStackTrace();
								
							}
							
						}
	
					} else {
						
						player.sendMessage(ChatColor.RED + "Use /treefarm help on help on how to use SQTreeFarm");
						
					} 
					
				} else {
					
					player.sendMessage(ChatColor.RED + "Use /treefarm help on help on how to use SQTreeFarm");
					
				}
				
			}
			
		}
		
		return false;
		
	}
	
}
	
	
