package com.ginger_walnut.sqblasters;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitScheduler;

public class RepeatingTasks extends Thread{
	
	public void run() {
		
		BukkitScheduler scheduler = Bukkit.getServer().getScheduler();
		
		scheduler.scheduleSyncRepeatingTask(Main.getPluginMain(), new Runnable() {
			
			@Override
			public void run() {

				List<Player> players = new ArrayList<Player>();
				
				players.addAll(Bukkit.getOnlinePlayers());
				
				for (int i = 0; i < players.size(); i ++) {
					
					Player player = players.get(i);		
					
					if (player.hasMetadata("automatic")) {
						
						ItemStack handItem = player.getItemInHand();
						
						if (handItem.getItemMeta().hasLore() && handItem.getType().equals(Material.BOW)) {
							
							List<String> blasterLore = handItem.getItemMeta().getLore();
							
							if (blasterLore.get(0).substring(10).equals("automatic")) {
								
								Events.fireBlaster(player);
								
							} else {
								
								player.removeMetadata("automatic", Main.getPluginMain());
								
							}
														
						} else {
							
							player.removeMetadata("automatic", Main.getPluginMain());
							
						}
						
					}
					
					if (player.hasMetadata("scoped")) {
						
						ItemStack handItem = player.getItemInHand();
						
						if (handItem.getItemMeta().hasLore() && handItem.getType().equals(Material.BOW)) {
							
							int scopeLevel = Main.getPluginConfig().getInt(player.getItemInHand().getItemMeta().getLore().get(0).substring(10) + ".scope");
							
							for (PotionEffect pe: player.getActivePotionEffects()) {
								
								if (pe.getType().equals(PotionEffectType.SLOW)) {
									
									if (scopeLevel != pe.getAmplifier()) {
										
										player.removeMetadata("automatic", Main.getPluginMain());
										player.removePotionEffect(PotionEffectType.SLOW);
										
									}
									
								}
								
							}
																					
						} else {
							
							player.removeMetadata("scoped", Main.getPluginMain());
							player.removePotionEffect(PotionEffectType.SLOW);
							
						}	
						
					}
					
				}
				
			}			
			
		}, 0, 1);
		
	}
	
}
