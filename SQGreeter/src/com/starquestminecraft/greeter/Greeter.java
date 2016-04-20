package com.starquestminecraft.greeter;
import com.starquestminecraft.greeter.command.MaintenanceCommand;
import com.starquestminecraft.greeter.command.ReloadCommand;
import com.starquestminecraft.greeter.sqldb.CachingMySQLDB;

import java.io.File;
import java.net.InetSocketAddress;
import java.nio.file.CopyOption;
import java.nio.file.Files;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

import net.md_5.bungee.api.ServerPing;
import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.event.LoginEvent;
import net.md_5.bungee.api.event.PostLoginEvent;
import net.md_5.bungee.api.event.ProxyPingEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.api.plugin.Plugin;
import net.md_5.bungee.config.Configuration;
import net.md_5.bungee.config.ConfigurationProvider;
import net.md_5.bungee.config.YamlConfiguration;
import net.md_5.bungee.event.EventHandler;

public class Greeter extends Plugin implements Listener {
	private static Greeter instance;
	private CachingMySQLDB d;
	private Settings settings;

	public void onEnable() {
		instance = this;
		loadSettings();
		this.d = new CachingMySQLDB();
		d.initialize();
		getProxy().getPluginManager().registerListener(this, this);
		getProxy().getPluginManager().registerCommand(this, new ReloadCommand());
		getProxy().getPluginManager().registerCommand(this, new MaintenanceCommand());
		this.getProxy().registerChannel("cryoBounce");

	}
	
	public void onDisable(){
		d.shutDown();
	}

	public void loadSettings() {
		try {
			System.out.println("saving default config.");
			saveDefaultConfig();
			System.out.println("loading config");
			Configuration config = getConfig();
			System.out.println("saving settings.");
			this.settings = new Settings(config);
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

	@EventHandler
	public void onServerListPing(ProxyPingEvent event) {
		ServerPing ping = event.getResponse();
		String username = d.getUsername(event.getConnection().getAddress().getAddress().getHostAddress());
		String line2;
		if(username != null){
			line2 = "§fWelcome back, §6"+username+"§f!";
		} else {
			line2 = "§fWelcome to StarQuest!";
		}
		ping.setDescription("§1=====§6Star§9Quest §c4.0§1===== \n" + line2);
	}

	@EventHandler(priority = 64)
	public void onLogin(LoginEvent event) {
		if (!event.isCancelled()) {
			UUID u = event.getConnection().getUniqueId();
			if(MaintenanceMode.isEnabled()){
				if(!MaintenanceMode.isAllowed(u)){
					event.setCancelReason("StarQuest is in maintenance mode: " + MaintenanceMode.message);
					event.setCancelled(true);
					return;
				}
			}
		}
	}

	@EventHandler
	public void onPostLogin(PostLoginEvent event) {			
		CryoBounce.callCryoMessage(event.getPlayer(), 0);
		InetSocketAddress inet = event.getPlayer().getAddress();
		String ip = inet.getAddress().getHostAddress();
		String username = event.getPlayer().getName();
		d.updateIP(ip, username);
		return;
	}
	public static Greeter getInstance() {
		return instance;
	}

	private static BaseComponent[] createMessage(String s) {
		return new ComponentBuilder(s).create();
	}
	public Settings getSettings() {
		return this.settings;
	}
}