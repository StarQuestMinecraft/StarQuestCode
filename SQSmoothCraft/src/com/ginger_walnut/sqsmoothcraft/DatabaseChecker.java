package com.ginger_walnut.sqsmoothcraft;

import org.bukkit.Bukkit;
import org.bukkit.scheduler.BukkitScheduler;

import com.ginger_walnut.sqsmoothcraft.database.DatabaseInterface;

public class DatabaseChecker extends Thread {

	public void run() {
		
		BukkitScheduler scheduler = Bukkit.getServer().getScheduler();
		
		scheduler.scheduleSyncRepeatingTask(SQSmoothCraft.getPluginMain(), new Runnable() {
			
			@Override
			public void run() {
		
				//DatabaseInterface.updateLocalCopy();
				
			}
			
		}, 0, 600);
		
	}
	
}
