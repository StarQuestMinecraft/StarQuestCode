package com.ginger_walnut.sqpowertools.tasks;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitScheduler;
import org.bukkit.util.Vector;

import com.ginger_walnut.sqpowertools.SQPowerTools;
import com.ginger_walnut.sqpowertools.objects.PowerToolType;

public class BlasterTask extends Thread {

	public static HashMap<Arrow, Integer> arrows = new HashMap<Arrow, Integer>();
	public static HashMap<Arrow, Vector> velocities = new HashMap<Arrow, Vector>();
	private static List<Arrow> removeArrows = new ArrayList<Arrow>();
	
	public void run() {
		
		BukkitScheduler scheduler = Bukkit.getServer().getScheduler();
		
		scheduler.scheduleSyncRepeatingTask(SQPowerTools.getPluginMain(), new Runnable() {
			
			@Override
			public void run() {

				for (Player player : Bukkit.getOnlinePlayers()) {
					
					if (player.hasMetadata("scoped")) {
						
						boolean correctTool = false;
						
						if (player.getInventory().getItemInMainHand() != null) {
							
							ItemStack handItem = player.getInventory().getItemInMainHand();			
							
							if (handItem.hasItemMeta()) {
								
								if (handItem.getItemMeta().hasLore()) {
									
									List<String> lore = handItem.getItemMeta().getLore();
										
									if (lore.contains(ChatColor.DARK_PURPLE + "Power Tool")) {
										
										PowerToolType type = SQPowerTools.getType(handItem);
										
										if (type.name == player.getMetadata("scoped").get(0).asString()) {
											
											correctTool = true;
											
										}
										
									}
									
								}
								
							}
							
						}
						
						if(!correctTool) {
							
							player.removeMetadata("scoped", SQPowerTools.getPluginMain());
							player.removePotionEffect(PotionEffectType.SLOW);
							
						}
						
					}
					
				}
				
				for (Arrow arrow : arrows.keySet()) {
					
					if (arrow.isDead()) {
						
						if (arrows.get(arrow) <= 0 ) {
							
							removeArrows.add(arrow);
							
						}
						
					} else {
						
						arrow.setVelocity(velocities.get(arrow));
						
						arrows.replace(arrow, arrows.get(arrow) - 1);
						
						if (arrows.get(arrow) <= 0 ) {
							
							removeArrows.add(arrow);
							
							arrow.remove();
							
						}
						
					}

				}
				
				for (Arrow arrow : removeArrows) {
					
					arrows.remove(arrow);
					velocities.remove(arrow);
					
				}
				
			}
			
		}, 0, 0);
		
	}
	
}