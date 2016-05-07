package com.ginger_walnut.sqpowertools.tasks;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.scheduler.BukkitScheduler;

import com.ginger_walnut.sqpowertools.SQPowerTools;
import com.ginger_walnut.sqpowertools.utils.EffectUtils;

public class HoldingTask extends Thread{
	
	public void run() {
		
		BukkitScheduler scheduler = Bukkit.getServer().getScheduler();
		
		scheduler.scheduleSyncRepeatingTask(SQPowerTools.getPluginMain(), new Runnable() {
			
			@Override
			public void run() {
				
				for (Player player : Bukkit.getOnlinePlayers()) {
					
					if (player.getInventory().getItemInMainHand() != null) {
						
						ItemStack handItem = player.getInventory().getItemInMainHand();
						
						if (handItem.hasItemMeta()) {
							
							if (handItem.getItemMeta().hasLore()) {
								
								List<String> lore = handItem.getItemMeta().getLore();
								
								if (lore.contains(ChatColor.DARK_PURPLE + "Power Tool")) {

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
										
										for (int j = 0; j < SQPowerTools.powerToolEffects.get(pos).size(); j ++) {
											
											if (SQPowerTools.powerToolEffects.get(pos).get(j) != null) {
												
												if (SQPowerTools.powerToolEffectCases.get(pos).get(j) == 5) {

													effects.add(new PotionEffect(EffectUtils.getEffectFromId(SQPowerTools.powerToolEffects.get(pos).get(j)), SQPowerTools.powerToolEffectDurations.get(pos).get(j) * 20 + 1, SQPowerTools.powerToolEffectLevels.get(pos).get(j)));
													
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
						
						if (armorContents[i] != null) {
							
							ItemStack armor = armorContents[i];
							
							if (armor.hasItemMeta()) {
								
								if (armor.getItemMeta().hasLore()) {
									
									if (armor.getItemMeta().getLore().contains(ChatColor.DARK_PURPLE + "Power Tool")) {
										
										int energy = SQPowerTools.getEnergy(armor);
										
										if (energy != 0) {
											
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
											
											for (int j = 0; j < SQPowerTools.powerToolEffects.get(pos).size(); j ++) {
												
												if (SQPowerTools.powerToolEffects.get(pos).get(j) != null) {
													
													if (SQPowerTools.powerToolEffectCases.get(pos).get(j) == 5) {

														effects.add(new PotionEffect(EffectUtils.getEffectFromId(SQPowerTools.powerToolEffects.get(pos).get(j)), SQPowerTools.powerToolEffectDurations.get(pos).get(j) * 20 + 1, SQPowerTools.powerToolEffectLevels.get(pos).get(j)));
														
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
