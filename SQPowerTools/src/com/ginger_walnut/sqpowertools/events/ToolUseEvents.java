package com.ginger_walnut.sqpowertools.events;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Sheep;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerShearEntityEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;

import com.ginger_walnut.sqboosters.SQBoosters;
import com.ginger_walnut.sqpowertools.SQPowerTools;
import com.ginger_walnut.sqpowertools.objects.Attribute;
import com.ginger_walnut.sqpowertools.objects.Modifier;
import com.ginger_walnut.sqpowertools.objects.PowerTool;
import com.ginger_walnut.sqpowertools.objects.PowerToolType;
import com.ginger_walnut.sqpowertools.utils.EffectUtils;

import io.netty.util.internal.ThreadLocalRandom;

public class ToolUseEvents implements Listener {
	
	@EventHandler
	public static void onPlayerInteract(PlayerInteractEvent event) {
			
		if (event.getPlayer().getInventory().getItemInMainHand() != null) {
				
			ItemStack handItem = event.getPlayer().getInventory().getItemInMainHand();
				
			if (handItem.hasItemMeta()) {
					
				if (handItem.getItemMeta().hasLore()) {
						
					if (handItem.getItemMeta().getLore().size() >= 2) {
							
						if (handItem.getItemMeta().getLore().get(0).equals("Power Tool")) {
								
							for (PowerToolType type : SQPowerTools.powerTools) {
									
								if (type.name.equals(handItem.getItemMeta().getLore().get(1))) {
										
									PowerTool powerToolObject = new PowerTool(type);

									powerToolObject.setDisplayName(handItem.getItemMeta().getDisplayName());
									handItem = powerToolObject.getItem();
									event.getPlayer().getInventory().setItemInMainHand(handItem);
									
								}
									
							}
								
						}
							
					}

				}
					
			}
			
		}
		
	}
	
	@EventHandler
	public static void onPlayerShearEntity(PlayerShearEntityEvent event) {
		
		if (!event.isCancelled()) {
			
			if (event.getEntity() instanceof Sheep) {
				
				Sheep sheep = (Sheep) event.getEntity();
				
				Player player = (Player) event.getPlayer();
				
				if (player.getInventory().getItemInMainHand() != null) {
					
					ItemStack handItem = player.getInventory().getItemInMainHand();			
					
					if (handItem.hasItemMeta()) {
						
						if (handItem.getItemMeta().hasLore()) {
							
							List<String> lore = handItem.getItemMeta().getLore();
							
							if (lore.contains(ChatColor.DARK_PURPLE + "Power Tool")) {
								
								handItem = SQPowerTools.fixPowerTool(handItem);
								player.getInventory().setItemInMainHand(handItem);
								
								int energy = SQPowerTools.getEnergy(handItem);
								int energyPerUse = (new PowerTool(handItem)).getEnergyPerUse();
								
								if (energy == 0) {
									
									event.setCancelled(true);
									
									player.sendMessage(ChatColor.RED + "Your Power Tool is out of energy");
									
								} else {
									
									List<Entity> entites = sheep.getNearbyEntities(1, 1, 1);
									
									for (Entity entity : entites) {
										
										if (entity instanceof Sheep) {
											
											Sheep sheep2 = (Sheep) entity;
											
											if (!sheep2.isSheared()) {
												
												if (sheep2.isAdult()) {
													
													sheep2.setSheared(true);
													
													int woolAmount = 0;
													
													for (int i = 0; i < SQBoosters.getSheepShearBooster(); i ++) {
														
														woolAmount = woolAmount + ThreadLocalRandom.current().nextInt(1, 4);
														
													}
													
													ItemStack wool = new ItemStack(Material.WOOL, woolAmount, sheep2.getColor().getData());
													
													player.getWorld().dropItem(player.getLocation(), wool);
													
												}
												
											}
											
										}
										
									}
									
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
										
									}
									
								}
								
							}
							
						}
						
					}
					
				}
				
			}
			
		}
		
	}
	
	@EventHandler
	public static void onBlockBreak(BlockBreakEvent event) {
		
		if (!event.isCancelled()) {
			
			Player player = event.getPlayer();
			
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
						
						if (!event.getEventName().equals("FakeBlockBreakEvent")) {
							
							if (lore.contains(ChatColor.DARK_PURPLE + "Power Tool")) {
								
								handItem = SQPowerTools.fixPowerTool(handItem);
								player.getInventory().setItemInMainHand(handItem);
								
								int energy = SQPowerTools.getEnergy(handItem);
								int energyPerUse = (new PowerTool(handItem)).getEnergyPerUse();
								
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
										
									}
									
								}
								
							}
							
						}
						
					}
					
				}
				
			}
			
		}
		
	}
	
	@EventHandler
	public static void onEntityDamageEntity(EntityDamageByEntityEvent event) {
		
		if (event.getDamager() instanceof Player) {
			
			Player player = (Player) event.getDamager();
			
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
							
							PowerToolType type = SQPowerTools.getType(handItem);
							
							int energy = SQPowerTools.getEnergy(handItem);
							int energyPerUse = (new PowerTool(handItem)).getEnergyPerUse();
							
							if (energy == 0) {
								
								event.setCancelled(true);
								
								player.sendMessage(ChatColor.RED + "Your Power Tool is out of energy");
								
							} else {
								
								List<PotionEffect> selfEffects = new ArrayList<PotionEffect>();
								List<PotionEffect> otherEffects = new ArrayList<PotionEffect>();
								
								HashMap<Modifier, Integer> modifiers = SQPowerTools.getModifiers(handItem);
								
								for (int i = 0; i < type.modifiers.size(); i ++) {
									
									if (modifiers.containsKey(type.modifiers.get(i))) {
										
										for (int j = 0; j < type.modifiers.get(i).effects.size(); j ++) {
											
											if (type.modifiers.get(i).effects.get(j) != null) {
												
												if (type.modifiers.get(i).effects.get(j).effectCase == 1) {
													
													selfEffects.add(new PotionEffect(EffectUtils.getEffectFromId(type.modifiers.get(i).effects.get(j).effect), type.modifiers.get(i).effects.get(j).duration * 20, type.modifiers.get(i).effects.get(j).level * modifiers.get(type.modifiers.get(i))));
													
												} else if (type.modifiers.get(i).effects.get(j).effectCase == 2) {
													
													otherEffects.add(new PotionEffect(EffectUtils.getEffectFromId(type.modifiers.get(i).effects.get(j).effect), type.modifiers.get(i).effects.get(j).duration * 20, type.modifiers.get(i).effects.get(j).level * modifiers.get(type.modifiers.get(i))));
													
												}
												
											}
											
										}
										
									}
									
								}
								
								for (int j = 0; j < type.effects.size(); j ++) {
									
									if (type.effects.get(j) != null) {
										
										if (type.effects.get(j).effectCase == 1) {
											
											selfEffects.add(new PotionEffect(EffectUtils.getEffectFromId(type.effects.get(j).effect), type.effects.get(j).duration * 20, type.effects.get(j).level));
											
										} else if (type.effects.get(j).effectCase == 2) {
											
											otherEffects.add(new PotionEffect(EffectUtils.getEffectFromId(type.effects.get(j).effect), type.effects.get(j).duration * 20, type.effects.get(j).level));
											
										}
										
									}
									
								}
								
								for (int j = 0; j < selfEffects.size(); j ++) {
									
									if (player.hasPotionEffect(selfEffects.get(j).getType())) {
										
										player.removePotionEffect(selfEffects.get(j).getType());
										
									}
									
								}
								
								player.addPotionEffects(selfEffects);
								
								if (event.getEntity() instanceof LivingEntity) {
									
									LivingEntity enemy = (LivingEntity) event.getEntity();
									
									for (int j = 0; j < otherEffects.size(); j ++) {
										
										if (!EffectUtils.isBadEffect(otherEffects.get(j).getType().getId()) || !event.isCancelled()) {
											
											if (enemy.hasPotionEffect(otherEffects.get(j).getType())) {
												
												enemy.removePotionEffect(otherEffects.get(j).getType());
												
											}
											
											enemy.addPotionEffect(otherEffects.get(j));
											
										}
										
									}
									
								}
								
								if (!event.isCancelled()) {
									
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
										
									}
									
								}
								
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
								
								armor = SQPowerTools.fixPowerTool(armor);
								
								armorContents[i] = armor;
								player.getInventory().setArmorContents(armorContents);		
								
								PowerToolType type = SQPowerTools.getType(armor);
								
								int energy = SQPowerTools.getEnergy(armor);
								
								if (energy == 0) {
									
									player.sendMessage(ChatColor.RED + "Your Power Tool is out of energy");
									
								} else {
									
									List<PotionEffect> selfEffects = new ArrayList<PotionEffect>();
									List<PotionEffect> otherEffects = new ArrayList<PotionEffect>();
									
									HashMap<Modifier, Integer> modifiers = SQPowerTools.getModifiers(armor);
									
									for (int j = 0; j < type.modifiers.size(); j ++) {
										
										if (modifiers.containsKey(type.modifiers.get(j))) {
											
											for (int k = 0; k < type.modifiers.get(j).effects.size(); k ++) {
												
												if (type.modifiers.get(j).effects.get(k) != null) {
													
													if (type.modifiers.get(j).effects.get(k).effectCase == 1) {
														
														selfEffects.add(new PotionEffect(EffectUtils.getEffectFromId(type.modifiers.get(j).effects.get(k).effect), type.modifiers.get(j).effects.get(k).duration * 20, type.modifiers.get(j).effects.get(k).level * modifiers.get(type.modifiers.get(j))));
														
													} else if (type.modifiers.get(j).effects.get(k).effectCase == 2) {
														
														otherEffects.add(new PotionEffect(EffectUtils.getEffectFromId(type.modifiers.get(j).effects.get(k).effect), type.modifiers.get(j).effects.get(k).duration * 20, type.modifiers.get(j).effects.get(k).level * modifiers.get(type.modifiers.get(j))));
														
													}
													
												}
												
											}
											
										}
										
									}
									
									for (int j = 0; j < type.effects.size(); j ++) {
										
										if (type.effects.get(j) != null) {
											
											if (type.effects.get(j).effectCase == 1) {
												
												selfEffects.add(new PotionEffect(EffectUtils.getEffectFromId(type.effects.get(j).effect), type.effects.get(j).duration * 20, type.effects.get(j).level));
												
											} else if (type.effects.get(j).effectCase == 2) {
												
												otherEffects.add(new PotionEffect(EffectUtils.getEffectFromId(type.effects.get(j).effect), type.effects.get(j).duration * 20, type.effects.get(j).level));
												
											}
											
										}
										
									}
									
									for (int j = 0; j < selfEffects.size(); j ++) {
										
										if (player.hasPotionEffect(selfEffects.get(j).getType())) {
											
											player.removePotionEffect(selfEffects.get(j).getType());
											
										}
										
									}
									
									player.addPotionEffects(selfEffects);
									
									if (event.getEntity() instanceof LivingEntity) {
										
										LivingEntity enemy = (LivingEntity) event.getEntity();
										
										for (int j = 0; j < otherEffects.size(); j ++) {
											
											if (!EffectUtils.isBadEffect(otherEffects.get(j).getType().getId()) || !event.isCancelled()) {
												
												if (enemy.hasPotionEffect(otherEffects.get(j).getType())) {
													
													enemy.removePotionEffect(otherEffects.get(j).getType());
													
												}
												
												enemy.addPotionEffect(otherEffects.get(j));
												
											}
											
										}
										
									}
									
								}
								
							}
							
						}
						
					}
					
				}
				
			}
			
		}
		
		if (event.getEntity() instanceof Player) {
			
			Player player = (Player) event.getEntity();
			
			ItemStack[] armorContents = player.getInventory().getArmorContents();
			
			for (int i = 0; i < armorContents.length; i ++) {
				
				if (armorContents[i] != null) {
					
					ItemStack armor = armorContents[i];
					
					if (armor.hasItemMeta()) {
						
						if (armor.getItemMeta().hasLore()) {
							
							if (armor.getItemMeta().getLore().contains(ChatColor.DARK_PURPLE + "Power Tool")) {
								
								armor = SQPowerTools.fixPowerTool(armor);
								
								armorContents[i] = armor;
								player.getInventory().setArmorContents(armorContents);		
								
								PowerToolType type = SQPowerTools.getType(armor);
								
								int energy = SQPowerTools.getEnergy(armor);
								int energyPerUse = (new PowerTool(armor)).getEnergyPerUse();
								
								if (energy == 0) {
									
									player.sendMessage(ChatColor.RED + "Your Power Tool is out of energy");
									
									PowerTool powerTool = new PowerTool(SQPowerTools.getType(armor));
									powerTool.setEnergy(0);
									powerTool.setModifiers(SQPowerTools.getModifiers(armor));
									
									powerTool.setAttributes(new ArrayList<Attribute>());
									armor = powerTool.getItem();
									
									armorContents[i] = armor;
									player.getInventory().setArmorContents(armorContents);		
									
								} else {
									
									List<PotionEffect> selfEffects = new ArrayList<PotionEffect>();
									List<PotionEffect> otherEffects = new ArrayList<PotionEffect>();
									
									HashMap<Modifier, Integer> modifiers = SQPowerTools.getModifiers(armor);
									
									for (int j = 0; j < type.modifiers.size(); j ++) {
										
										if (modifiers.containsKey(type.modifiers.get(j))) {
											
											for (int k = 0; k < type.modifiers.get(j).effects.size(); k ++) {
												
												if (type.modifiers.get(j).effects.get(k) != null) {
													
													if (type.modifiers.get(j).effects.get(k).effectCase == 3) {
														
														selfEffects.add(new PotionEffect(EffectUtils.getEffectFromId(type.modifiers.get(j).effects.get(k).effect), type.modifiers.get(j).effects.get(k).duration * 20, type.modifiers.get(j).effects.get(k).level * modifiers.get(type.modifiers.get(j))));
														
													} else if (type.modifiers.get(j).effects.get(k).effectCase == 4) {
														
														otherEffects.add(new PotionEffect(EffectUtils.getEffectFromId(type.modifiers.get(j).effects.get(k).effect), type.modifiers.get(j).effects.get(k).duration * 20, type.modifiers.get(j).effects.get(k).level * modifiers.get(type.modifiers.get(j))));
														
													}
													
												}
												
											}
											
										}
										
									}
									
									for (int j = 0; j < type.effects.size(); j ++) {
										
										if (type.effects.get(j) != null) {
											
											if (type.effects.get(j).effectCase == 3) {
												
												selfEffects.add(new PotionEffect(EffectUtils.getEffectFromId(type.effects.get(j).effect), type.effects.get(j).duration * 20, type.effects.get(j).level));
												
											} else if (type.effects.get(j).effectCase == 4) {
												
												otherEffects.add(new PotionEffect(EffectUtils.getEffectFromId(type.effects.get(j).effect), type.effects.get(j).duration * 20, type.effects.get(j).level));
												
											}
											
										}
										
									}
									
									for (int j = 0; j < selfEffects.size(); j ++) {
										
										if (player.hasPotionEffect(selfEffects.get(j).getType())) {
											
											player.removePotionEffect(selfEffects.get(j).getType());
											
										}
										
									}
									
									player.addPotionEffects(selfEffects);
									
									if (event.getDamager() instanceof LivingEntity) {
										
										LivingEntity enemy = (LivingEntity) event.getDamager();
										
										for (int j = 0; j < otherEffects.size(); j ++) {
											
											if (!EffectUtils.isBadEffect(otherEffects.get(j).getType().getId()) || !event.isCancelled()) {
												
												if (enemy.hasPotionEffect(otherEffects.get(j).getType())) {
													
													enemy.removePotionEffect(otherEffects.get(j).getType());
													
												}
												
												enemy.addPotionEffect(otherEffects.get(j));
												
											}
											
										}
										
									}
									
									if (!event.isCancelled()) {
										
										if (energy <= energyPerUse) {
											
											player.sendMessage(ChatColor.RED + "Your Power Tool has run out of energy");
											
											PowerTool powerTool = new PowerTool(SQPowerTools.getType(armor));
											powerTool.setEnergy(0);
											powerTool.setModifiers(SQPowerTools.getModifiers(armor));
											
											powerTool.setAttributes(new ArrayList<Attribute>());
											armor = powerTool.getItem();
											
											armorContents[i] = armor;
											player.getInventory().setArmorContents(armorContents);		
											
										} else {

											PowerTool powerTool = new PowerTool(armor);
											powerTool.setEnergy(energy - energyPerUse);
											
											armor = powerTool.getItem();
											
											armorContents[i] = armor;
											player.getInventory().setArmorContents(armorContents);
											
										}
										
									}
									
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
							
							handItem = SQPowerTools.fixPowerTool(handItem);
							player.getInventory().setItemInMainHand(handItem);
							
							PowerToolType type = SQPowerTools.getType(handItem);
							
							int energy = SQPowerTools.getEnergy(handItem);
							
							if (energy > 0) {
								
								List<PotionEffect> selfEffects = new ArrayList<PotionEffect>();
								List<PotionEffect> otherEffects = new ArrayList<PotionEffect>();
								
								HashMap<Modifier, Integer> modifiers = SQPowerTools.getModifiers(handItem);
								
								for (int j = 0; j < type.modifiers.size(); j ++) {
									
									if (modifiers.containsKey(type.modifiers.get(j))) {
										
										for (int k = 0; k < type.modifiers.get(j).effects.size(); k ++) {
											
											if (type.modifiers.get(j).effects.get(k) != null) {
												
												if (type.modifiers.get(j).effects.get(k).effectCase == 3) {
													
													selfEffects.add(new PotionEffect(EffectUtils.getEffectFromId(type.modifiers.get(j).effects.get(k).effect), type.modifiers.get(j).effects.get(k).duration * 20, type.modifiers.get(j).effects.get(k).level * modifiers.get(type.modifiers.get(j))));
													
												} else if (type.modifiers.get(j).effects.get(k).effectCase == 4) {
													
													otherEffects.add(new PotionEffect(EffectUtils.getEffectFromId(type.modifiers.get(j).effects.get(k).effect), type.modifiers.get(j).effects.get(k).duration * 20, type.modifiers.get(j).effects.get(k).level * modifiers.get(type.modifiers.get(j))));
													
												}
												
											}
											
										}
										
									}
									
								}
								
								for (int j = 0; j < type.effects.size(); j ++) {
									
									if (type.effects.get(j) != null) {
										
										if (type.effects.get(j).effectCase == 3) {
											
											selfEffects.add(new PotionEffect(EffectUtils.getEffectFromId(type.effects.get(j).effect), type.effects.get(j).duration * 20, type.effects.get(j).level));
											
										} else if (type.effects.get(j).effectCase == 4) {
											
											otherEffects.add(new PotionEffect(EffectUtils.getEffectFromId(type.effects.get(j).effect), type.effects.get(j).duration * 20, type.effects.get(j).level));
											
										}
										
									}
									
								}
								
								for (int j = 0; j < selfEffects.size(); j ++) {
									
									if (player.hasPotionEffect(selfEffects.get(j).getType())) {
										
										player.removePotionEffect(selfEffects.get(j).getType());
										
									}
									
								}
								
								player.addPotionEffects(selfEffects);
								
								if (event.getDamager() instanceof LivingEntity) {
									
									LivingEntity enemy = (LivingEntity) event.getDamager();
									
									for (int j = 0; j < otherEffects.size(); j ++) {
										
										if (!EffectUtils.isBadEffect(otherEffects.get(j).getType().getId()) || !event.isCancelled()) {
											
											if (enemy.hasPotionEffect(otherEffects.get(j).getType())) {
												
												enemy.removePotionEffect(otherEffects.get(j).getType());
												
											}
											
											enemy.addPotionEffect(otherEffects.get(j));
											
										}
										
									}
									
								}
								
							}	
							
						}
						
					}
					
				}
				
			}
			
		}
		
		if (event.getEntity() instanceof Player) {
			
			Player player = (Player) event.getEntity();
			
			if (player.isBlocking()) {
				
				if (player.getInventory().getItemInOffHand() != null) {
					
					ItemStack handItem = player.getInventory().getItemInOffHand();
					
					if (handItem.hasItemMeta()) {
						
						if (handItem.getItemMeta().hasLore()) {
							
							List<String> lore = handItem.getItemMeta().getLore();
							
							if (lore.contains(ChatColor.DARK_PURPLE + "Power Tool")) {
								
								handItem = SQPowerTools.fixPowerTool(handItem);
								player.getInventory().setItemInOffHand(handItem);
								
								PowerToolType type = SQPowerTools.getType(handItem);
								
								int energyPerUse = (new PowerTool(handItem)).getEnergyPerUse();
								int energy = SQPowerTools.getEnergy(handItem);
								
								if (energy == 0) {
									
									event.setCancelled(true);
									
									player.sendMessage(ChatColor.RED + "Your Power Tool is out of energy");
									
								} else {
									
									List<PotionEffect> selfEffects = new ArrayList<PotionEffect>();
									List<PotionEffect> otherEffects = new ArrayList<PotionEffect>();
									
									HashMap<Modifier, Integer> modifiers = SQPowerTools.getModifiers(handItem);
									
									for (int j = 0; j < type.modifiers.size(); j ++) {
										
										if (modifiers.containsKey(type.modifiers.get(j))) {
											
											for (int k = 0; k < type.modifiers.get(j).effects.size(); k ++) {
												
												if (type.modifiers.get(j).effects.get(k) != null) {
													
													if (type.modifiers.get(j).effects.get(k).effectCase == 3) {
														
														selfEffects.add(new PotionEffect(EffectUtils.getEffectFromId(type.modifiers.get(j).effects.get(k).effect), type.modifiers.get(j).effects.get(k).duration * 20, type.modifiers.get(j).effects.get(k).level));
														
													} else if (type.modifiers.get(j).effects.get(k).effectCase == 4) {
														
														otherEffects.add(new PotionEffect(EffectUtils.getEffectFromId(type.modifiers.get(j).effects.get(k).effect), type.modifiers.get(j).effects.get(k).duration * 20, type.modifiers.get(j).effects.get(k).level));
														
													}
													
												}
												
											}
											
										}
										
									}
									
									for (int j = 0; j < type.effects.size(); j ++) {
										
										if (type.effects.get(j) != null) {
											
											if (type.effects.get(j).effectCase == 3) {
												
												selfEffects.add(new PotionEffect(EffectUtils.getEffectFromId(type.effects.get(j).effect), type.effects.get(j).duration * 20, type.effects.get(j).level));
												
											} else if (type.effects.get(j).effectCase == 4) {
												
												otherEffects.add(new PotionEffect(EffectUtils.getEffectFromId(type.effects.get(j).effect), type.effects.get(j).duration * 20, type.effects.get(j).level));
												
											}
											
										}
										
									}
									
									for (int j = 0; j < selfEffects.size(); j ++) {
										
										if (player.hasPotionEffect(selfEffects.get(j).getType())) {
											
											player.removePotionEffect(selfEffects.get(j).getType());
											
										}
										
									}
									
									player.addPotionEffects(selfEffects);
									
									if (event.getDamager() instanceof LivingEntity) {
										
										LivingEntity enemy = (LivingEntity) event.getDamager();
										
										for (int j = 0; j < otherEffects.size(); j ++) {
											
											if (!EffectUtils.isBadEffect(otherEffects.get(j).getType().getId()) || !event.isCancelled()) {
												
												if (enemy.hasPotionEffect(otherEffects.get(j).getType())) {
													
													enemy.removePotionEffect(otherEffects.get(j).getType());
													
												}
												
												enemy.addPotionEffect(otherEffects.get(j));
												
											}
										}
										
									}
									
									if (!event.isCancelled()) {
										
										if (energy <= energyPerUse) {
											
											player.sendMessage(ChatColor.RED + "Your Power Tool has run out of energy");
											
											PowerTool powerTool = new PowerTool(handItem);
											powerTool.setEnergy(0);
											
											handItem = powerTool.getItem();
											
											player.getInventory().setItemInOffHand(handItem);
											
										} else {
											
											PowerTool powerTool = new PowerTool(handItem);
											powerTool.setEnergy(energy - energyPerUse);
											
											handItem = powerTool.getItem();
											
											player.getInventory().setItemInOffHand(handItem);
											
										}
										
									}
									
								}
								
							}
							
						}
						
					}
					
				}
				
			}
			
		}
		
	}
	
}
