package com.ginger_walnut.sqsmoothcraft.tasks;

import org.bukkit.entity.Player;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.scheduler.BukkitRunnable;

import com.ginger_walnut.sqsmoothcraft.SQSmoothCraft;

public class CooldownHandler extends BukkitRunnable {

	String metadata = "";
	
	Player player;
	
	int counter = 0;
	int cooldown = 0;
	
	
	public CooldownHandler(Player firstPlayer, String firstMetadata, int firstCooldown) {
		
		player = firstPlayer;	
		metadata = firstMetadata;
		cooldown = firstCooldown;

	}

	@Override
	public void run() {
		
		if (counter == 0) {
			
			player.setMetadata(metadata, new FixedMetadataValue(SQSmoothCraft.getPluginMain(), metadata));
			
		}
		
		counter ++;
		
		if (counter >= cooldown) {
			
			this.cancel();
			player.removeMetadata(metadata, SQSmoothCraft.getPluginMain());
			
		}
	
	}
	
}
