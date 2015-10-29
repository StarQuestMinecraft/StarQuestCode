package com.ginger_walnut.sqblasters;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.scheduler.BukkitScheduler;

public class Timer extends Thread{

	public void run(final Player player, final int ammoPerPack, final String metaKey, int time) {
		
		//Setting timer Metadata
		player.setMetadata(metaKey, new FixedMetadataValue(Main.getPluginMain(), true));
		
		BukkitScheduler scheduler = Bukkit.getServer().getScheduler();
		
		//Scheduling a delayed task
		scheduler.scheduleSyncDelayedTask(Main.getPluginMain(), new Runnable() {
	
			@Override
			public void run() {
				
				//Removing timer Metadata
				player.removeMetadata(metaKey, Main.getPluginMain());
				
				Main.setDoneReloading(player, ammoPerPack);
				
			}
	
		}, time);
		
	}
	
}
