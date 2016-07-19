package com.ginger_walnut.sqpowertools.events;

import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.Sound;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerPickupItemEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.metadata.MetadataValue;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import com.ginger_walnut.sqpowertools.SQPowerTools;
import com.ginger_walnut.sqpowertools.objects.BlasterStats;
import com.ginger_walnut.sqpowertools.objects.PowerTool;
import com.ginger_walnut.sqpowertools.objects.PowerToolType;
import com.ginger_walnut.sqpowertools.tasks.BlasterTask;

public class BlasterEvents implements Listener {

	@EventHandler
	public static void onPlayerInteract(PlayerInteractEvent event) {
		
		if (event.getAction().equals(Action.RIGHT_CLICK_AIR) || event.getAction().equals(Action.RIGHT_CLICK_BLOCK)) {
				
			Player player = event.getPlayer();
			
			if (player.getInventory().getItemInMainHand() != null) {
				
				ItemStack handItem = player.getInventory().getItemInMainHand();			
				
				if (handItem.hasItemMeta()) {
					
					if (handItem.getItemMeta().hasLore()) {
						
						List<String> lore = handItem.getItemMeta().getLore();
							
						if (lore.contains(ChatColor.DARK_PURPLE + "Power Tool")) {
							
							PowerToolType type = SQPowerTools.getType(handItem);
							
							if (type.blasterStats != null) {
								
								handItem = SQPowerTools.fixPowerTool(handItem);
								player.getInventory().setItemInMainHand(handItem);
								
								event.setCancelled(true);
								
								boolean onCooldown = false;
								
								if (player.hasMetadata("blaster_cooldown" + "_" + type.name.toLowerCase())) {
									
									if (player.getMetadata("blaster_cooldown" + "_" + type.name.toLowerCase()).get(0).asInt() > 0) {
										
										onCooldown = true;
										
									}
									
								}

								if (!onCooldown) {
									
									int energy = SQPowerTools.getEnergy(handItem);
									int energyPerUse = SQPowerTools.getType(handItem).energyPerUse;
										
									if (energy == 0) {
											
										event.setCancelled(true);
											
										player.sendMessage(ChatColor.RED + "Your Power Tool is out of energy");
											
									} else {
											
										if (energy <= energyPerUse) {
												
											PowerTool powerTool = new PowerTool(handItem);
											powerTool.setEnergy(0);
												
											handItem = powerTool.getItem();
												
											player.getInventory().setItemInMainHand(handItem);
												
											player.sendMessage(ChatColor.RED + "Your Power Tool has run out of energy");
												
										} else {

											PowerTool powerTool = new PowerTool(handItem);
											powerTool.setEnergy(energy - energyPerUse);
												
											handItem = powerTool.getItem();
												
											player.getInventory().setItemInMainHand(handItem);
											
											BlasterStats blasterStates = powerTool.getTotalBlasterStats();
											
											player.setMetadata("blaster_cooldown" + "_" + type.name.toLowerCase(), new FixedMetadataValue(SQPowerTools.getPluginMain(), blasterStates.cooldown));
											
											player.getWorld().playSound(player.getLocation(), Sound.ENTITY_ARROW_SHOOT, 1, 1);
											
											Arrow arrow = player.launchProjectile(Arrow.class);
											
											arrow.setMetadata("damage", new FixedMetadataValue(SQPowerTools.getPluginMain(), blasterStates.damage));
											arrow.setMetadata("no_pickup", new FixedMetadataValue(SQPowerTools.getPluginMain(), true));
											arrow.setShooter(player);
											
											BlasterTask.arrows.put(arrow, 200);
											BlasterTask.velocities.put(arrow, arrow.getVelocity());
												
										}
											
									}
									
								}
								
							}
	
						}
							
					}
					
				}
				
			}
			
		} else if (event.getAction().equals(Action.LEFT_CLICK_AIR) || event.getAction().equals(Action.LEFT_CLICK_BLOCK)) {
			
			Player player = event.getPlayer();
			
			if (player.getInventory().getItemInMainHand() != null) {
				
				ItemStack handItem = player.getInventory().getItemInMainHand();			
				
				if (handItem.hasItemMeta()) {
					
					if (handItem.getItemMeta().hasLore()) {
						
						List<String> lore = handItem.getItemMeta().getLore();
							
						if (lore.contains(ChatColor.DARK_PURPLE + "Power Tool")) {
								
							handItem = SQPowerTools.fixPowerTool(handItem);
							player.getInventory().setItemInMainHand(handItem);
							
							PowerToolType type = SQPowerTools.getType(handItem);
							
							if (type.blasterStats != null) {
								
								if (player.hasMetadata("scoped")) {
									
									player.removeMetadata("scoped", SQPowerTools.getPluginMain());
									player.removePotionEffect(PotionEffectType.SLOW);
									
								} else {
										
									PowerTool powerTool = new PowerTool(handItem);
									
									player.setMetadata("scoped", new FixedMetadataValue(SQPowerTools.getPluginMain(), type.name));
									player.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, 1000000000, powerTool.getTotalBlasterStats().scope));
									
								}
								
							}
							
						}
						
					}
					
				}

			}
			
		}
		
	}
	
	@EventHandler
	public void onPlayerPickupItem(PlayerPickupItemEvent event) {
		
		if (event.getItem().hasMetadata("no_pickup")) {
			
			event.setCancelled(true);
			
		}
		
	}
	
	@EventHandler
	public void onEntityDamageByEntity(EntityDamageByEntityEvent event) {		
		
		if (event.getDamager().getType().equals(EntityType.ARROW)) {
			
			Arrow arrow = (Arrow) event.getDamager();

			if (arrow.hasMetadata("damage")) {
				
				double damage = 0;
				
				boolean anyDamage = false;
				
				List<MetadataValue> values = arrow.getMetadata("damage");
				
				for (MetadataValue value : values) {
					
					if (value.getOwningPlugin() == SQPowerTools.getPluginMain()) {
						
						damage = Double.parseDouble(value.value().toString());
						
						anyDamage = true;
						
					}
					
				}
				
				if (anyDamage) {
					
					event.setDamage(damage);
					
				}
				
				arrow.remove();
				
			}
			
		}
		
	}
	
}
