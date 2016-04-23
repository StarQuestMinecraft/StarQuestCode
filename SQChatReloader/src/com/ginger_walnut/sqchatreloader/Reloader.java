package com.ginger_walnut.sqchatreloader;

import org.bukkit.Bukkit;
import org.bukkit.scheduler.BukkitScheduler;

public class Reloader extends Thread{

	public void run() {
		
		BukkitScheduler scheduler = Bukkit.getServer().getScheduler();
		
		scheduler.scheduleSyncRepeatingTask(SQChatReloader.getPluginMain(), new Runnable() {
			
			@Override
			public void run() {
		
				Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "reloadchat");
				
			}
			
		}, 0, 600);
		
	}
	
}
