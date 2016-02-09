package com.ginger_walnut.sqboosters;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.metadata.MetadataValue;
import org.bukkit.scheduler.BukkitScheduler;

public class ExpBooster extends Thread{

	public void run() {

		BukkitScheduler scheduler = Bukkit.getServer().getScheduler();

		scheduler.scheduleSyncRepeatingTask(SQBoosters.getPluginMain(), new Runnable() {
	
			@Override
			public void run() {
				
				if (SQBoosters.multipliers[0] != 1) {

					Collection<? extends Player> players = Bukkit.getServer().getOnlinePlayers();
					List<Player> playersList = new ArrayList<Player>();				
					
					playersList.addAll(players);
					
					for (int i = 0; i < playersList.size(); i++) {
						
						Player player = playersList.get(i);
						
						if (player.hasMetadata("lastExp")) {
							
							float lastExp = 0;
							
							for (MetadataValue value : player.getMetadata("lastExp")) {
								
								if (value.getOwningPlugin() == SQBoosters.getPluginMain()) {
									
									lastExp = Float.parseFloat(value.value().toString());
									
								}
								
							}
							
							if (lastExp < player.getTotalExperience()) {							
								
								float expGained  = player.getTotalExperience() - lastExp;
								int expGive = (int) (expGained * (SQBoosters.multipliers[0] - 1));
									
								player.giveExp(expGive);
								
							}
							
						}
						
						player.setMetadata("lastExp", new FixedMetadataValue(SQBoosters.getPluginMain(), player.getTotalExperience()));
						
					}
					
				}
				
			}
	
		}, 0, 1);
		
	}
	
}
