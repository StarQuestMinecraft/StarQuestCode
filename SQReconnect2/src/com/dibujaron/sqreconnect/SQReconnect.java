package com.dibujaron.sqreconnect;

import java.io.File;
import java.nio.file.CopyOption;
import java.nio.file.Files;
import java.util.List;

import net.md_5.bungee.api.ReconnectHandler;
import net.md_5.bungee.api.plugin.Plugin;
import net.md_5.bungee.config.Configuration;
import net.md_5.bungee.config.ConfigurationProvider;
import net.md_5.bungee.config.YamlConfiguration;
import net.md_5.bungee.module.reconnect.yaml.YamlReconnectHandler;

public class SQReconnect extends Plugin{
	
	static List<String> mainServers;
	private static SQReconnect instance;
	
	public void onEnable(){
		ReconnectHandler conventional = new YamlReconnectHandler();
		System.out.println("Conventional: " + conventional);
		loadSettings();
		getProxy().setReconnectHandler(new SQLReconnectHandler(conventional));
		Database.setUp();
		instance = this;
	}

	public static boolean isSQServer(String servername) {
		return mainServers.contains(servername);
	}
	

	public void loadSettings() {
		try {
			System.out.println("saving default config.");
			saveDefaultConfig();
			System.out.println("loading config");
			Configuration config = getConfig();
			System.out.println("saving settings.");
			mainServers = config.getStringList("mainServers");
			System.out.println("loaded " + mainServers.size() + " main servers.");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void saveDefaultConfig() throws Exception {
		if (!getDataFolder().exists()) {
			getDataFolder().mkdir();
		}
		File file = new File(getDataFolder(), "config.yml");
		System.out.println("file exists check.");
		if (!file.exists()) {
			System.out.println("attempting to save config");
			Files.copy(getResourceAsStream("config.yml"), file.toPath(), new CopyOption[0]);
			System.out.println("saved config.");
		}
	}

	private Configuration getConfig() throws Exception {
		return ConfigurationProvider.getProvider(YamlConfiguration.class).load(new File(getDataFolder(), "config.yml"));
	}

	public static Plugin getInstance() {
		return instance;
	}
}
