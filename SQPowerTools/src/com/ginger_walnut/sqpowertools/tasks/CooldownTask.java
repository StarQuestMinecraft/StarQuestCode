package com.ginger_walnut.sqpowertools.tasks;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.scheduler.BukkitScheduler;

import com.ginger_walnut.sqpowertools.SQPowerTools;
import com.ginger_walnut.sqpowertools.objects.PowerToolType;

public class CooldownTask extends Thread {

	public static List<String> reloading = new ArrayList<String>();
	
	public void run() {
		
		BukkitScheduler scheduler = Bukkit.getServer().getScheduler();
		
		scheduler.scheduleSyncRepeatingTask(SQPowerTools.getPluginMain(), new Runnable() {
			
			@Override
			public void run() {
				
				for (Player player : Bukkit.getOnlinePlayers()) {
					
					for (PowerToolType type : SQPowerTools.powerTools) {
						
						if (type.blasterStats != null) {
							
							if (player.hasMetadata("blaster_cooldown" + "_" + type.name.toLowerCase())) {
								
								int currentCooldown = player.getMetadata("blaster_cooldown" + "_" + type.name.toLowerCase()).get(0).asInt();
								
								player.setMetadata("blaster_cooldown" + "_" + type.name.toLowerCase(), new FixedMetadataValue(SQPowerTools.getPluginMain(), currentCooldown - 1));
								
								currentCooldown --;
								
								if (currentCooldown <= 0) {

									player.removeMetadata("blaster_cooldown" + "_" + type.name.toLowerCase(), SQPowerTools.getPluginMain());
									
									if (reloading.contains(player.getName())) {
										
										reloading.remove(player.getName());
										player.sendMessage(ChatColor.GREEN + "Reloading Complete");
										
									}
									
								}
								
							}
							
						}
						
					}
					
				}
				
			}
			
		}, 0, 0);
		
	}
	
}
