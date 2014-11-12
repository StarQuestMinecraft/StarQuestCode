package com.dibujaron.messenger.client;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

public class Core extends JavaPlugin{
	
	private ConnectionHandler cHandler;
	
	private static Core instance;
	
	public void onEnable(){
		cHandler = new ConnectionHandler(Bukkit.getServerName());
		new Thread(cHandler).start();
	}
	
	public static Core getInstance(){
		return instance;
	}
	
	public ConnectionHandler getConnectionHandler(){
		return cHandler;
	}
}
