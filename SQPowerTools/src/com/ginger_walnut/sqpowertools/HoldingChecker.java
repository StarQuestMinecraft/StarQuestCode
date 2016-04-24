package com.ginger_walnut.sqpowertools;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.scheduler.BukkitScheduler;

public class HoldingChecker extends Thread{
	
	public void run() {
		
		BukkitScheduler scheduler = Bukkit.getServer().getScheduler();
		
		scheduler.scheduleSyncRepeatingTask(SQPowerTools.getPluginMain(), new Runnable() {
			
			@Override
			public void run() {
				
				for (Player player : Bukkit.getOnlinePlayers()) {
					
					if (player.getInventory().getItemInMainHand() != null) {
						
						ItemStack handItem = player.getInventory().getItemInMainHand();
						
						if (handItem.getType().equals(Material.DIAMOND_PICKAXE) || handItem.getType().equals(Material.DIAMOND_SPADE) || handItem.getType().equals(Material.DIAMOND_SWORD) || handItem.getType().equals(Material.DIAMOND_AXE) || handItem.getType().equals(Material.DIAMOND_HOE)) {
							
							boolean unbreakable = false;
							
							if (handItem.hasItemMeta()) {
								
								unbreakable = handItem.getItemMeta().spigot().isUnbreakable();
								
							}
							
							if (!unbreakable) {
								
								if (handItem.getDurability() >= 1541) {
									
									if (handItem.getDurability() >= 1551) {
										
										player.sendMessage(ChatColor.RED + "Your tool has broken");
										
										player.setItemInHand(new ItemStack(Material.AIR));
										
									} else {
										
										player.sendMessage(ChatColor.RED + "Your tool will break in " + (1551 - handItem.getDurability()) + " uses");
										
									}

								}
								
							}
							
						}
						
						if (handItem.hasItemMeta()) {
							
							if (handItem.getItemMeta().hasLore()) {
								
								List<String> lore = handItem.getItemMeta().getLore();
								
								if (lore.contains(ChatColor.DARK_PURPLE + "Power Tool")) {

									handItem = SQPowerTools.fixPowerTool(handItem);
									player.getInventory().setItemInMainHand(handItem);

									int energy = SQPowerTools.getEnergy(handItem);
									
									if (energy != 0) {
										
										List<PotionEffect> effects = new ArrayList<PotionEffect>();
										int pos = 0;
										
										HashMap<String, Integer> modifiers = SQPowerTools.getModifiers(handItem);
												
										for (int i = 0; i < SQPowerTools.powerToolNames.size(); i ++) {
											
											if (SQPowerTools.powerToolNames.get(i).equals(SQPowerTools.getName(handItem))) {
												
												pos = i;
												
											}
											
										}
										
										for (int i = 0; i < SQPowerTools.powerToolModNames.get(pos).size(); i ++) {
											
											if (modifiers.containsKey(SQPowerTools.powerToolModNames.get(pos).get(i))) {
												
												for (int j = 0; j < SQPowerTools.powerToolModEffects.get(pos).get(i).size(); j ++) {
													
													if (SQPowerTools.powerToolModEffects.get(pos).get(i).get(j) != null) {
														
														if (SQPowerTools.powerToolModEffectCases.get(pos).get(i).get(j) == 5) {

															effects.add(new PotionEffect(EffectUtils.getEffectFromId(SQPowerTools.powerToolModEffects.get(pos).get(i).get(j)), SQPowerTools.powerToolModEffectDurations.get(pos).get(i).get(j) * 20 + 1, SQPowerTools.powerToolModEffectLevels.get(pos).get(i).get(j)));
															
														}
														
													}
													
												}
												
											}
											
										}
										
										for (int i = 0; i < effects.size(); i ++) {
											
											if (player.hasPotionEffect(effects.get(i).getType())) {
												
												player.removePotionEffect(effects.get(i).getType());
												
											}
											
										}
										
										player.addPotionEffects(effects);
	
										
									}
									
								}
								
							}
							
						}
						
					}
					
					ItemStack[] armorContents = player.getInventory().getArmorContents();
					
					for (int i = 0; i < armorContents.length; i ++) {
						
						if (armorContents[i].getAmount() != 0) {
							
							ItemStack armor = armorContents[i];
							
							if (armor.hasItemMeta()) {
								
								if (armor.getItemMeta().hasLore()) {
									
									if (armor.getItemMeta().getLore().contains(ChatColor.DARK_PURPLE + "Power Tool")) {
										
										armor = SQPowerTools.fixPowerTool(armor);
										
										armorContents[i] = armor;
										player.getInventory().setArmorContents(armorContents);		
										
										int energy = SQPowerTools.getEnergy(armor);
										
										if (energy == 0) {
											
											player.sendMessage(ChatColor.RED + "Your Power Tool is out of energy");
											
										} else {
											
											List<PotionEffect> effects = new ArrayList<PotionEffect>();
											
											int pos = 0;
											
											HashMap<String, Integer> modifiers = SQPowerTools.getModifiers(armor);
													
											for (int j = 0; j < SQPowerTools.powerToolNames.size(); j ++) {
												
												if (SQPowerTools.powerToolNames.get(j).equals(SQPowerTools.getName(armor))) {
													
													pos = j;
													
												}
												
											}
											
											for (int j = 0; j < SQPowerTools.powerToolModNames.get(pos).size(); j ++) {
												
												if (modifiers.containsKey(SQPowerTools.powerToolModNames.get(pos).get(j))) {
													
													for (int k = 0; k < SQPowerTools.powerToolModEffects.get(pos).get(j).size(); k ++) {
														
														if (SQPowerTools.powerToolModEffects.get(pos).get(j).get(k) != null) {
															
															if (SQPowerTools.powerToolModEffectCases.get(pos).get(j).get(k) == 5) {

																effects.add(new PotionEffect(EffectUtils.getEffectFromId(SQPowerTools.powerToolModEffects.get(pos).get(j).get(k)), SQPowerTools.powerToolModEffectDurations.get(pos).get(j).get(k) * 20 + 1, SQPowerTools.powerToolModEffectLevels.get(pos).get(j).get(k)));
																
															}
															
														}
														
													}
													
												}
												
											}
											
											for (int j = 0; j < effects.size(); j ++) {
												
												if (player.hasPotionEffect(effects.get(j).getType())) {
													
													player.removePotionEffect(effects.get(j).getType());
													
												}
												
											}

											player.addPotionEffects(effects);
											
										}
										
									}
									
								}
								
							}
							
						}
						
					}
					
				}
				
			}
			
		}, 0, 20);
		
	}

}
