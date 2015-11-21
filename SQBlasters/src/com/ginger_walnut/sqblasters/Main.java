package com.ginger_walnut.sqblasters;

import java.util.logging.Logger;

import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin{

	public final Logger logger = Logger.getLogger("Minecraft");
	public static Main plugin;
	
	private static Plugin pluginGrav;
	private static FileConfiguration pluginConfig;
	
	@Override
	public void onDisable() {
		
		pluginGrav = null;
		pluginConfig = null;
		
		PluginDescriptionFile pdfFile = this.getDescription();
		this.logger.info(pdfFile.getName() + " has been disabled!");
		
	}
	
	@Override
	public void onEnable() {
		
		pluginGrav = this;
		pluginConfig = getConfig();
		
		PluginDescriptionFile pdfFile = this.getDescription();
		this.logger.info(pdfFile.getName() + " has been enabled!");
		
		this.getServer().getPluginManager().registerEvents(new Events(), this);
		
		getConfig().options().copyDefaults(true);
		saveConfig();
		
	}
	
	public static Plugin getPluginMain() {
		
		return pluginGrav;
		
	}
	
	public static FileConfiguration getPluginConfig() {
		
		return pluginConfig;
		
	}
	
	public static void setDoneReloading (Player player, int ammoPerPack) {
		
		ItemMeta itemMeta = player.getItemInHand().getItemMeta();
		
		if (itemMeta.getDisplayName().equals(ChatColor.GREEN + "Reloading...")) {
			
			itemMeta.setDisplayName("Blaster " + ChatColor.GOLD +  "(" + ammoPerPack + "/" + ammoPerPack + ")");
			
			player.getItemInHand().setItemMeta(itemMeta);
			
		}
		
	}
	
}
	
	
