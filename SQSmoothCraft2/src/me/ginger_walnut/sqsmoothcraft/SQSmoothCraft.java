package me.ginger_walnut.sqsmoothcraft;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.java.JavaPlugin;

import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.ProtocolManager;

import me.ginger_walnut.sqsmoothcraft.entites.CustomEntityType;
import me.ginger_walnut.sqsmoothcraft.objects.Ship;
import me.ginger_walnut.sqsmoothcraft.tasks.ShipMovement;
import net.md_5.bungee.api.ChatColor;

public class SQSmoothCraft extends JavaPlugin {

	private static Plugin plugin;
	public final Logger logger = Logger.getLogger("Minecraft");
	
	public static FileConfiguration config;
	
	public static List<Ship> activeShips = new ArrayList<Ship>();
	
	public static ProtocolManager protocolManager;
	
	@Override
	public void onEnable() {
		
		plugin = this;
		
		if (!new File(this.getDataFolder(), "config.yml").exists()) {
			
			saveDefaultConfig();
			saveConfig();
			
		}		
		
		config = getConfig();

		CustomEntityType.registerEntities();
		
		(new ShipMovement()).run();
		
		protocolManager = ProtocolLibrary.getProtocolManager();
		
		PluginDescriptionFile pdfFile = this.getDescription();
		this.logger.info(pdfFile.getName() + " has been enabled!");
		
	}
	
	
	@Override
	public void onDisable() {
		
		plugin = null;

		PluginDescriptionFile pdfFile = this.getDescription();
		this.logger.info(pdfFile.getName() + " has been disabled!");
		
		saveDefaultConfig();
		
	}
	
	public void printHelp(Player player) {
		
		player.sendMessage(ChatColor.GOLD + "----------");
		player.sendMessage(ChatColor.GOLD + "/ship help - " + ChatColor.BLUE + "Displays this");
		player.sendMessage(ChatColor.GOLD + "----------");
		
	}
	
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args) {
		
		if (sender instanceof Player) {
			
			Player player = (Player) sender;
			
			if (commandLabel.equalsIgnoreCase("ship")) {
				
				if (args.length > 0) {
					
					if (args[0].equalsIgnoreCase("help")) {
						
						printHelp(player);
						
					} else if (args[0].equalsIgnoreCase("spawn")) {
						
						boolean in = false;
						
						for (Ship ship : activeShips) {
							
							if (ship.pilot.equals(player)) {
								
								in = true;
								
							}
							
						}
						
						if (!in) {
							
							new Ship(player);
							
						}

					}
					
				} else {
					
					printHelp(player);
					
				}
				
			}
			
		} else {
			
			sender.sendMessage(ChatColor.RED + "You must be a player to use this command");
			
		}
		
		return false;
		
	}
	
	public static Plugin getInstance() {
		
		return plugin;
		
	}
	
}
