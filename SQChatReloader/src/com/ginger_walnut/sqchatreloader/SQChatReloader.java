package com.ginger_walnut.sqchatreloader;

import java.util.logging.Logger;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.java.JavaPlugin;

public class SQChatReloader extends JavaPlugin {

	public final Logger logger = Logger.getLogger("Minecraft");
	public static SQChatReloader plugin;
	
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
		
		Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "reloadchat");
		
		(new Reloader()).run();

	}
	
	public static Plugin getPluginMain() {
		
		return pluginMain;
		
	}
	
}
	
	
