package com.ginger_walnut.sqexpboost;

import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.metadata.MetadataValue;
import org.bukkit.scheduler.BukkitScheduler;

public class ExpBooster extends Thread{

	public void run() {

		BukkitScheduler scheduler = Bukkit.getServer().getScheduler();

		scheduler.scheduleSyncRepeatingTask(Main.getPluginMain(), new Runnable() {
	
			@Override
			public void run() {
				
				if (Main.expBoost != 1) {

					List<Player> players = Bukkit.getServer().getWorlds().get(0).getPlayers();
					
					for (int i = 0; i < players.size(); i++) {
						
						Player player = players.get(i);
						
						if (player.hasMetadata("lastExp")) {
							
							float lastExp = 0;
							
							for (MetadataValue value : player.getMetadata("lastExp")) {
								
								if (value.getOwningPlugin() == Main.getPluginMain()) {
									
									lastExp = Float.parseFloat(value.value().toString());
									
								}
								
							}
							
							if (lastExp < player.getTotalExperience()) {
								
								System.out.print(4);								
								
								float expGained  = player.getTotalExperience() - lastExp;
								int expGive = (int) (expGained * (Main.expBoost - 1));
									
								player.giveExp(expGive);
								
							}
							
						}
						
						player.setMetadata("lastExp", new FixedMetadataValue(Main.getPluginMain(), player.getTotalExperience()));
						
					}
					
				}
				
			}
	
		}, 0, 1);
		
	}
	
}
