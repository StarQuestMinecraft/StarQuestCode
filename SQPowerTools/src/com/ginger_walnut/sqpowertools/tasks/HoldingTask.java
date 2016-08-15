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
import com.ginger_walnut.sqpowertools.objects.Modifier;
import com.ginger_walnut.sqpowertools.objects.PowerToolType;
import com.ginger_walnut.sqpowertools.utils.EffectUtils;

public class HoldingTask extends Thread{

	public void run() {
		
		BukkitScheduler scheduler = Bukkit.getServer().getScheduler();
		
		scheduler.scheduleSyncRepeatingTask(SQPowerTools.getPluginMain(), new Runnable() {
			
			@Override
			public void run() {
				
				for (Player player : Bukkit.getOnlinePlayers()) {
					
					if (player.isBlocking()) {
						
						if (player.getInventory().getItemInOffHand() != null) {
							
							ItemStack handItem = player.getInventory().getItemInOffHand();
							
							if (handItem.hasItemMeta()) {
								
								if (handItem.getItemMeta().hasLore()) {
									
									List<String> lore = handItem.getItemMeta().getLore();
									
									if (lore.contains(ChatColor.DARK_PURPLE + "Power Tool")) {

										PowerToolType type = SQPowerTools.getType(handItem);
										
										int energy = SQPowerTools.getEnergy(handItem);
										
										if (energy != 0) {
											
											List<PotionEffect> effects = new ArrayList<PotionEffect>();
											
											HashMap<Modifier, Integer> modifiers = SQPowerTools.getModifiers(handItem);
											
											for (int j = 0; j < type.modifiers.size(); j ++) {
												
												if (modifiers.containsKey(type.modifiers.get(j))) {
													
													for (int k = 0; k < type.modifiers.get(j).effects.size(); k ++) {
														
														if (type.modifiers.get(j).effects.get(k) != null) {
															
															if (type.modifiers.get(j).effects.get(k).effectCase == 5) {

																effects.add(new PotionEffect(EffectUtils.getEffectFromId(type.modifiers.get(j).effects.get(k).effect), type.modifiers.get(j).effects.get(k).duration * 20, type.modifiers.get(j).effects.get(k).level * modifiers.get(type.modifiers.get(j))));
																
															}
															
														}
														
													}
													
												}
												
											}
											
											for (int j = 0; j < type.effects.size(); j ++) {
												
												if (type.effects.get(j) != null) {
													
													if (type.effects.get(j).effectCase == 5) {

														effects.add(new PotionEffect(EffectUtils.getEffectFromId(type.effects.get(j).effect), type.effects.get(j).duration * 20, type.effects.get(j).level));
														
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
					
					if (player.getInventory().getItemInMainHand() != null) {
						
						ItemStack handItem = player.getInventory().getItemInMainHand();
						
						if (handItem.hasItemMeta()) {
							
							if (handItem.getItemMeta().hasLore()) {
								
								List<String> lore = handItem.getItemMeta().getLore();
								
								if (lore.contains(ChatColor.DARK_PURPLE + "Power Tool")) {

									PowerToolType type = SQPowerTools.getType(handItem);
									
									int energy = SQPowerTools.getEnergy(handItem);
									
									if (energy != 0) {
										
										List<PotionEffect> effects = new ArrayList<PotionEffect>();
										
										HashMap<Modifier, Integer> modifiers = SQPowerTools.getModifiers(handItem);
										
										for (int j = 0; j < type.modifiers.size(); j ++) {
											
											if (modifiers.containsKey(type.modifiers.get(j))) {
												
												for (int k = 0; k < type.modifiers.get(j).effects.size(); k ++) {
													
													if (type.modifiers.get(j).effects.get(k) != null) {
														
														if (type.modifiers.get(j).effects.get(k).effectCase == 5) {

															effects.add(new PotionEffect(EffectUtils.getEffectFromId(type.modifiers.get(j).effects.get(k).effect), type.modifiers.get(j).effects.get(k).duration * 20, type.modifiers.get(j).effects.get(k).level * modifiers.get(type.modifiers.get(j))));
															
														}
														
													}
													
												}
												
											}
											
										}
										
										for (int j = 0; j < type.effects.size(); j ++) {
											
											if (type.effects.get(j) != null) {
												
												if (type.effects.get(j).effectCase == 5) {

													effects.add(new PotionEffect(EffectUtils.getEffectFromId(type.effects.get(j).effect), type.effects.get(j).duration * 20, type.effects.get(j).level));
													
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
					
					ItemStack[] armorContents = player.getInventory().getArmorContents();
					
					for (int i = 0; i < armorContents.length; i ++) {
						
						if (armorContents[i] != null) {
							
							ItemStack armor = armorContents[i];
							
							if (armor.hasItemMeta()) {
								
								if (armor.getItemMeta().hasLore()) {
									
									if (armor.getItemMeta().getLore().contains(ChatColor.DARK_PURPLE + "Power Tool")) {
										
										PowerToolType type = SQPowerTools.getType(armor);
										
										int energy = SQPowerTools.getEnergy(armor);
										
										if (energy != 0) {
											
											List<PotionEffect> effects = new ArrayList<PotionEffect>();
											
											HashMap<Modifier, Integer> modifiers = SQPowerTools.getModifiers(armor);
											
											for (int j = 0; j < type.modifiers.size(); j ++) {
												
												if (modifiers.containsKey(type.modifiers.get(j))) {
													
													for (int k = 0; k < type.modifiers.get(j).effects.size(); k ++) {
														
														if (type.modifiers.get(j).effects.get(k) != null) {
															
															if (type.modifiers.get(j).effects.get(k).effectCase == 5) {

																effects.add(new PotionEffect(EffectUtils.getEffectFromId(type.modifiers.get(j).effects.get(k).effect), type.modifiers.get(j).effects.get(k).duration * 20, type.modifiers.get(j).effects.get(k).level * modifiers.get(type.modifiers.get(j))));
																
															}
															
														}
														
													}
													
												}
												
											}
											
											for (int j = 0; j < type.effects.size(); j ++) {
												
												if (type.effects.get(j) != null) {
													
													if (type.effects.get(j).effectCase == 5) {

														effects.add(new PotionEffect(EffectUtils.getEffectFromId(type.effects.get(j).effect), type.effects.get(j).duration * 20, type.effects.get(j).level));
														
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
