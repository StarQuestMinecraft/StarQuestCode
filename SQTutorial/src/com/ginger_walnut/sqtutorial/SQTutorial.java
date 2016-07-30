package com.ginger_walnut.sqtutorial;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;
import java.util.logging.Logger;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.java.JavaPlugin;

import com.ginger_walnut.sqtutorial.listeners.PlayerListener;
import com.ginger_walnut.sqtutorial.objects.Tutorial;
import com.ginger_walnut.sqtutorial.objects.TutorialProgress;
import com.ginger_walnut.sqtutorial.objects.TutorialStage;

import net.md_5.bungee.api.ChatColor;

public class SQTutorial extends JavaPlugin {

	public final Logger logger = Logger.getLogger("Minecraft");
	static SQTutorial plugin;

	public static FileConfiguration config = null;
	
	public static HashMap<UUID, TutorialProgress> progress = new HashMap<UUID, TutorialProgress>();
	public static List<Tutorial> tutorials = new ArrayList<Tutorial>();
	
	@Override
	public void onEnable() {
		
		plugin = this;

		if (!new File(this.getDataFolder(), "config.yml").exists()) {
			
			saveDefaultConfig();
			saveConfig();
			
		}
		
		config = getConfig();
		
		this.getServer().getPluginManager().registerEvents(new PlayerListener(), this);
		
		ConfigurationSection configurationSelection = config.getConfigurationSection("tutorials");
		
		if (configurationSelection != null) {
			
			for (String configTutorial : configurationSelection.getKeys(false)) {
				
				Tutorial tutorial = new Tutorial();
				
				tutorial.id = configTutorial;
				
				for (String configStage : config.getConfigurationSection("tutorials." + configTutorial + ".stages").getKeys(false)) {
					
					String path = "tutorials." + configTutorial + ".stages." + configStage;
					
					TutorialStage stage = new TutorialStage();
					
					stage.id = configStage;
					stage.message = parseMessage(config.getString(path + ".message"));
					stage.voicePath = config.getString(path + ".voice path");
					stage.wait = config.getInt(path + ".wait");
					stage.delay = config.getInt(path + ".delay");
					
					tutorial.stages.add(stage);
					
				}
				
				tutorials.add(tutorial);
				
			}
			
		}
		
		PluginDescriptionFile pdfFile = this.getDescription();
		this.logger.info(pdfFile.getName() + " has been enabled!");

	}
	
	@Override
	public void onDisable() {
		
		saveDefaultConfig();
		
		PluginDescriptionFile pdfFile = this.getDescription();
		this.logger.info(pdfFile.getName() + " has been disabled!");
		
	}
	
	public static void printHelp (CommandSender sender) {

	}
	
	public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args) {

		return false;
		
	}
	
	public static SQTutorial getInstance() {
		
		return plugin;
		
	}
	
	public static String parseMessage(String message) {
		
		String newMessage = "";
		
		for (int i = 0; i < message.toCharArray().length; i ++) {
			
			if (message.toCharArray()[i] == '&') {
				
				i ++;
				
				newMessage = newMessage + ChatColor.getByChar(message.toCharArray()[i]);
				
			} else {
				
				newMessage = newMessage + message.toCharArray()[i];
				
			}
			
		}
		
		return newMessage;
		
	}
	
}
