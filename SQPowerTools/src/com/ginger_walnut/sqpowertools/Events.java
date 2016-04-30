package com.ginger_walnut.sqpowertools;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.Sign;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.event.inventory.InventoryType.SlotType;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.material.Attachable;
import org.bukkit.material.MaterialData;
import org.bukkit.potion.PotionEffect;

public class Events implements Listener{	
	
	@EventHandler
	public static void onBlockBreak(BlockBreakEvent event) {
		
		Player player = event.getPlayer();

		if (player.getInventory().getItemInMainHand() != null) {
			
			ItemStack handItem = player.getInventory().getItemInMainHand();			
			
			if (handItem.getType().equals(Material.DIAMOND_PICKAXE) || handItem.getType().equals(Material.DIAMOND_SPADE) || handItem.getType().equals(Material.DIAMOND_SWORD) || handItem.getType().equals(Material.DIAMOND_AXE) || handItem.getType().equals(Material.DIAMOND_HOE)) {
				
				boolean unbreakable = false;
				
				if (handItem.hasItemMeta()) {
					
					unbreakable = handItem.getItemMeta().spigot().isUnbreakable();
					
				}
				
				if (!unbreakable) {
					
					System.out.print(handItem.getDurability());
					
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
						int energyPerUse = SQPowerTools.getEnergyPerUse(SQPowerTools.getName(handItem));
						
						if (energy == 0) {
							
							event.setCancelled(true);
							
							player.sendMessage(ChatColor.RED + "Your Power Tool is out of energy");
							
						} else {
							
							if (energy <= energyPerUse) {
								
								handItem = SQPowerTools.setEnergy(handItem, 0);
								player.getInventory().setItemInMainHand(handItem);
								
								player.sendMessage(ChatColor.RED + "Your Power Tool has run out of energy");
								
							} else {
								
								handItem = SQPowerTools.setEnergy(handItem, energy - energyPerUse);
								player.getInventory().setItemInMainHand(handItem);
								
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

							int energy = SQPowerTools.getEnergy(handItem);
							int energyPerUse = SQPowerTools.getEnergyPerUse(SQPowerTools.getName(handItem));
							
							if (energy == 0) {
								
								event.setCancelled(true);
								
								player.sendMessage(ChatColor.RED + "Your Power Tool is out of energy");
								
							} else {
								
								List<PotionEffect> selfEffects = new ArrayList<PotionEffect>();
								List<PotionEffect> otherEffects = new ArrayList<PotionEffect>();
								
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
												
												if (SQPowerTools.powerToolModEffectCases.get(pos).get(i).get(j) == 1) {

													selfEffects.add(new PotionEffect(EffectUtils.getEffectFromId(SQPowerTools.powerToolModEffects.get(pos).get(i).get(j)), SQPowerTools.powerToolModEffectDurations.get(pos).get(i).get(j) * 20, SQPowerTools.powerToolModEffectLevels.get(pos).get(i).get(j)));
													
												} else if (SQPowerTools.powerToolModEffectCases.get(pos).get(i).get(j) == 2) {

													otherEffects.add(new PotionEffect(EffectUtils.getEffectFromId(SQPowerTools.powerToolModEffects.get(pos).get(i).get(j)), SQPowerTools.powerToolModEffectDurations.get(pos).get(i).get(j) * 20, SQPowerTools.powerToolModEffectLevels.get(pos).get(i).get(j)));
													
												}
												
											}
											
										}
										
									}
									
								}
								
								for (int j = 0; j < SQPowerTools.powerToolEffects.get(pos).size(); j ++) {
									
									if (SQPowerTools.powerToolEffects.get(pos).get(j) != null) {
										
										if (SQPowerTools.powerToolEffectCases.get(pos).get(j) == 1) {

											selfEffects.add(new PotionEffect(EffectUtils.getEffectFromId(SQPowerTools.powerToolEffects.get(pos).get(j)), SQPowerTools.powerToolEffectDurations.get(pos).get(j) * 20, SQPowerTools.powerToolEffectLevels.get(pos).get(j)));
											
										} else if (SQPowerTools.powerToolEffectCases.get(pos).get(j) == 2) {

											otherEffects.add(new PotionEffect(EffectUtils.getEffectFromId(SQPowerTools.powerToolEffects.get(pos).get(j)), SQPowerTools.powerToolEffectDurations.get(pos).get(j) * 20, SQPowerTools.powerToolEffectLevels.get(pos).get(j)));
											
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
										
										if (enemy.hasPotionEffect(otherEffects.get(j).getType())) {
											
											enemy.removePotionEffect(otherEffects.get(j).getType());
											
										}
										
									}
									
									enemy.addPotionEffects(otherEffects);
									
								}

								if (energy <= energyPerUse) {
									
									handItem = SQPowerTools.setEnergy(handItem, 0);
									player.getInventory().setItemInMainHand(handItem);
									
									player.sendMessage(ChatColor.RED + "Your Power Tool has run out of energy");
									
								} else {
									
									handItem = SQPowerTools.setEnergy(handItem, energy - energyPerUse);
									player.getInventory().setItemInMainHand(handItem);
									
								}
								
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
									
									List<PotionEffect> selfEffects = new ArrayList<PotionEffect>();
									List<PotionEffect> otherEffects = new ArrayList<PotionEffect>();
									
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
													
													if (SQPowerTools.powerToolModEffectCases.get(pos).get(j).get(k) == 1) {

														selfEffects.add(new PotionEffect(EffectUtils.getEffectFromId(SQPowerTools.powerToolModEffects.get(pos).get(j).get(k)), SQPowerTools.powerToolModEffectDurations.get(pos).get(j).get(k) * 20, SQPowerTools.powerToolModEffectLevels.get(pos).get(j).get(k)));
														
													} else if (SQPowerTools.powerToolModEffectCases.get(pos).get(j).get(k) == 2) {

														otherEffects.add(new PotionEffect(EffectUtils.getEffectFromId(SQPowerTools.powerToolModEffects.get(pos).get(j).get(k)), SQPowerTools.powerToolModEffectDurations.get(pos).get(j).get(k) * 20, SQPowerTools.powerToolModEffectLevels.get(pos).get(j).get(k)));
														
													}
													
												}
												
											}
											
										}
										
									}
									
									for (int j = 0; j < SQPowerTools.powerToolEffects.get(pos).size(); j ++) {
										
										if (SQPowerTools.powerToolEffects.get(pos).get(j) != null) {
											
											if (SQPowerTools.powerToolEffectCases.get(pos).get(j) == 1) {

												selfEffects.add(new PotionEffect(EffectUtils.getEffectFromId(SQPowerTools.powerToolEffects.get(pos).get(j)), SQPowerTools.powerToolEffectDurations.get(pos).get(j) * 20, SQPowerTools.powerToolEffectLevels.get(pos).get(j)));
												
											} else if (SQPowerTools.powerToolEffectCases.get(pos).get(j) == 2) {

												otherEffects.add(new PotionEffect(EffectUtils.getEffectFromId(SQPowerTools.powerToolEffects.get(pos).get(j)), SQPowerTools.powerToolEffectDurations.get(pos).get(j) * 20, SQPowerTools.powerToolEffectLevels.get(pos).get(j)));
												
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
											
											if (enemy.hasPotionEffect(otherEffects.get(j).getType())) {
												
												enemy.removePotionEffect(otherEffects.get(j).getType());
												
											}
											
										}
										
										enemy.addPotionEffects(otherEffects);
										
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
				
				if (armorContents[i].getAmount() != 0) {
					
					ItemStack armor = armorContents[i];
					
					if (armor.hasItemMeta()) {
						
						if (armor.getItemMeta().hasLore()) {
							
							if (armor.getItemMeta().getLore().contains(ChatColor.DARK_PURPLE + "Power Tool")) {
								
								armor = SQPowerTools.fixPowerTool(armor);
								
								armorContents[i] = armor;
								player.getInventory().setArmorContents(armorContents);		
								
								int energy = SQPowerTools.getEnergy(armor);
								int energyPerUse = SQPowerTools.getEnergyPerUse(SQPowerTools.getName(armor));
								
								if (energy == 0) {
									
									player.sendMessage(ChatColor.RED + "Your Power Tool is out of energy");
									
								} else {
									
									List<PotionEffect> selfEffects = new ArrayList<PotionEffect>();
									List<PotionEffect> otherEffects = new ArrayList<PotionEffect>();
									
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
													
													if (SQPowerTools.powerToolModEffectCases.get(pos).get(j).get(k) == 3) {

														selfEffects.add(new PotionEffect(EffectUtils.getEffectFromId(SQPowerTools.powerToolModEffects.get(pos).get(j).get(k)), SQPowerTools.powerToolModEffectDurations.get(pos).get(j).get(k) * 20, SQPowerTools.powerToolModEffectLevels.get(pos).get(j).get(k)));
														
													} else if (SQPowerTools.powerToolModEffectCases.get(pos).get(j).get(k) == 4) {

														otherEffects.add(new PotionEffect(EffectUtils.getEffectFromId(SQPowerTools.powerToolModEffects.get(pos).get(j).get(k)), SQPowerTools.powerToolModEffectDurations.get(pos).get(j).get(k) * 20, SQPowerTools.powerToolModEffectLevels.get(pos).get(j).get(k)));
														
													}
													
												}
												
											}
											
										}
										
									}
									
									for (int j = 0; j < SQPowerTools.powerToolEffects.get(pos).size(); j ++) {
										
										if (SQPowerTools.powerToolEffects.get(pos).get(j) != null) {
											
											if (SQPowerTools.powerToolEffectCases.get(pos).get(j) == 3) {

												selfEffects.add(new PotionEffect(EffectUtils.getEffectFromId(SQPowerTools.powerToolEffects.get(pos).get(j)), SQPowerTools.powerToolEffectDurations.get(pos).get(j) * 20, SQPowerTools.powerToolEffectLevels.get(pos).get(j)));
												
											} else if (SQPowerTools.powerToolEffectCases.get(pos).get(j) == 4) {

												otherEffects.add(new PotionEffect(EffectUtils.getEffectFromId(SQPowerTools.powerToolEffects.get(pos).get(j)), SQPowerTools.powerToolEffectDurations.get(pos).get(j) * 20, SQPowerTools.powerToolEffectLevels.get(pos).get(j)));
												
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
											
											if (enemy.hasPotionEffect(otherEffects.get(j).getType())) {
												
												enemy.removePotionEffect(otherEffects.get(j).getType());
												
											}
											
										}
										
										enemy.addPotionEffects(otherEffects);
										
									}
									
									if (energy <= energyPerUse) {
										
										player.sendMessage(ChatColor.RED + "Your Power Tool has run out of energy");
										
										armor = SQPowerTools.setEnergy(armor, 0);
										
										armor = SQPowerTools.addAttributes(armor, new ArrayList<String>(), new ArrayList<Float>(), new ArrayList<Integer>(), new ArrayList<String>());
										
										armorContents[i] = armor;
										player.getInventory().setArmorContents(armorContents);		
										
									} else {
										
										armor = SQPowerTools.setEnergy(armor, energy - energyPerUse);
										
										
										
										armorContents[i] = armor;
										player.getInventory().setArmorContents(armorContents);
										
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

							int energy = SQPowerTools.getEnergy(handItem);
							
							if (energy == 0) {
								
								event.setCancelled(true);
								
								player.sendMessage(ChatColor.RED + "Your Power Tool is out of energy");
								
							} else {
								
								List<PotionEffect> selfEffects = new ArrayList<PotionEffect>();
								List<PotionEffect> otherEffects = new ArrayList<PotionEffect>();
								
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
												
												if (SQPowerTools.powerToolModEffectCases.get(pos).get(i).get(j) == 3) {

													selfEffects.add(new PotionEffect(EffectUtils.getEffectFromId(SQPowerTools.powerToolModEffects.get(pos).get(i).get(j)), SQPowerTools.powerToolModEffectDurations.get(pos).get(i).get(j) * 20 + 5, SQPowerTools.powerToolModEffectLevels.get(pos).get(i).get(j)));
													
												} else if (SQPowerTools.powerToolModEffectCases.get(pos).get(i).get(j) == 4) {

													otherEffects.add(new PotionEffect(EffectUtils.getEffectFromId(SQPowerTools.powerToolModEffects.get(pos).get(i).get(j)), SQPowerTools.powerToolModEffectDurations.get(pos).get(i).get(j) * 20 + 5, SQPowerTools.powerToolModEffectLevels.get(pos).get(i).get(j)));
													
												}
												
											}
											
										}
										
									}
									
								}
								
								for (int j = 0; j < SQPowerTools.powerToolEffects.get(pos).size(); j ++) {
									
									if (SQPowerTools.powerToolEffects.get(pos).get(j) != null) {
										
										if (SQPowerTools.powerToolEffectCases.get(pos).get(j) == 3) {

											selfEffects.add(new PotionEffect(EffectUtils.getEffectFromId(SQPowerTools.powerToolEffects.get(pos).get(j)), SQPowerTools.powerToolEffectDurations.get(pos).get(j) * 20, SQPowerTools.powerToolEffectLevels.get(pos).get(j)));
											
										} else if (SQPowerTools.powerToolEffectCases.get(pos).get(j) == 4) {

											otherEffects.add(new PotionEffect(EffectUtils.getEffectFromId(SQPowerTools.powerToolEffects.get(pos).get(j)), SQPowerTools.powerToolEffectDurations.get(pos).get(j) * 20, SQPowerTools.powerToolEffectLevels.get(pos).get(j)));
											
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
										
										if (enemy.hasPotionEffect(otherEffects.get(j).getType())) {
											
											enemy.removePotionEffect(otherEffects.get(j).getType());
											
										}
										
									}
									
									enemy.addPotionEffects(otherEffects);
									
								}

							}
							
						}
						
					}
					
				}
			
			}
				
		}
		
	}
	
	@EventHandler
	public static void onInventroyClick(InventoryClickEvent event) {
		
		Player player = (Player) event.getWhoClicked();		
		
		Inventory inventory = event.getInventory();
		
		ItemStack clicked = null;
		
		int type = 0;
		
		if (event.getCurrentItem() != null) {
			
			if (event.getCurrentItem().getAmount() != 0) {
				
				type = 1;
				
				clicked = event.getCurrentItem();
				
			}
			
		}
		
		if (clicked == null) {
			
			if (event.getCursor() != null) {
				
				if (event.getCursor().getAmount() != 0) {
					
					type = 2;
					
					clicked = event.getCursor();
					
				}
				
			}
			
		}
		
		if (clicked != null) {
			
			if (inventory.getLocation() != null) {
				
				if (isCharger(player.getWorld().getBlockAt(inventory.getLocation()))) {
					
					if (event.getSlot() == 1) {
						
						if (type == 2) {
							
							event.setCursor(new ItemStack(Material.AIR));
							ChargerTask.setChargerFuel(inventory, clicked);
							
						}

					}
					
				}
				
			}
			
			if (inventory.getTitle().equals("Power Tool Recipes") || inventory.getTitle().equals("Power Tool Recipe") || inventory.getTitle().equals("Charger Fuel") || inventory.getTitle().equals("Power Tool Mods") || inventory.getTitle().equals("Power Tool Mod")) {
				
				event.setCancelled(true);
				
			}
			
			if (clicked.hasItemMeta()) {

				if (clicked.getItemMeta().hasLore()) {
					
					if (clicked.getItemMeta().getLore().contains(ChatColor.DARK_PURPLE + "Power Tool")) {
						
						if (inventory.getTitle().equals("Modifier")) {
							
							if (event.getSlot() == 16) {
								
								ItemStack powerTool = inventory.getItem(10);
								
								if (powerTool != null) {
									
									if (powerTool.hasItemMeta()) {
										
										if (powerTool.getItemMeta().hasLore()) {
											
											if (powerTool.getItemMeta().getLore().contains(ChatColor.DARK_PURPLE + "Power Tool")) {
												
												List<Material> modifiers = new ArrayList<Material>();
												
												for (int i = 0; i < SQPowerTools.powerToolNames.size(); i ++) {
													
													if (SQPowerTools.powerToolNames.get(i).equals(SQPowerTools.getName(powerTool))) {
														
														modifiers = SQPowerTools.powerToolModMaterials.get(i);
														
													}
													
												}
												
												if (inventory.getItem(13) != null) {
													
													if (modifiers.contains(inventory.getItem(13).getType())) {

														inventory.setItem(10, new ItemStack(Material.AIR));
														
														List<Material> modifierMaterials = new ArrayList<Material>();
														List<Integer> modifierAmounts = new ArrayList<Integer>();
														
														for (int i = 0; i < SQPowerTools.powerToolNames.size(); i ++) {
															
															if (SQPowerTools.powerToolNames.get(i).equals(SQPowerTools.getName(powerTool))) {
																
																modifierMaterials = SQPowerTools.powerToolModMaterials.get(i);
																modifierAmounts = SQPowerTools.powerToolModItemNumbers.get(i);
																
															}
															
														}
														
														for (int i = 0; i < modifierMaterials.size(); i ++) {
															
															if (modifierMaterials.get(i).equals(inventory.getItem(13).getType())) {
	
																ItemStack newItem = inventory.getItem(13);
																
																newItem.setAmount(newItem.getAmount() - modifierAmounts.get(i));
																
																if (newItem.getAmount() == 0) {
																	
																	newItem = new ItemStack(Material.AIR);
																	
																}
																
																inventory.setItem(13, newItem);
																
																i = modifierMaterials.size();
																
															}
															
														}
														
														inventory.setItem(0, new ItemStack(Material.AIR));
														
														player.closeInventory();
														
														event.setCancelled(true);
														
													}
													
												}
													
											}
											
										}
										
									}
									
								}
								
							}
							
						} else if (inventory.getLocation() == null) {
							
							if (inventory.getTitle().equals("Power Tool Recipes")) {
								
								event.setCancelled(true);
								
								if (clicked.getItemMeta().getLore().contains(ChatColor.RED + "" + ChatColor.MAGIC + "Contraband")) {
									
									FileConfiguration config = SQPowerTools.getPluginMain().getConfig();
									
									String powerTool = "";
									
									String name = SQPowerTools.getName(clicked);
									
									for (int i = 0; i < SQPowerTools.powerToolNames.size(); i ++) {
										
										if (SQPowerTools.powerToolNames.get(i).equals(name)) {
											
											powerTool = SQPowerTools.powerToolConfigNames.get(i);
											
										}
										
									}
									
									List<String> recipe = new ArrayList<String>();
									
									if (config.contains("power tools." + powerTool + ".recipe")) {
										
										if (config.getString("power tools." + powerTool + ".recipe.line1").length() == 2) {
											
											recipe.add(config.getString("power tools." + powerTool + ".recipe.line1") + " ");
											
										} else if (config.getString("power tools." + powerTool + ".recipe.line1").length() == 1) {
											
											recipe.add(config.getString("power tools." + powerTool + ".recipe.line1") + "  ");
											
										} else {
											
											recipe.add(config.getString("power tools." + powerTool + ".recipe.line1"));
											
										}

										if (config.contains("power tools." + powerTool + ".recipe.line2")) {
											
											if (config.getString("power tools." + powerTool + ".recipe.line2").length() == 2) {
												
												recipe.add(config.getString("power tools." + powerTool + ".recipe.line2") + " ");
												
											} else if (config.getString("power tools." + powerTool + ".recipe.line2").length() == 1) {
												
												recipe.add(config.getString("power tools." + powerTool + ".recipe.line2") + "  ");
												
											} else {
												
												recipe.add(config.getString("power tools." + powerTool + ".recipe.line2"));
												
											}
											
											if (config.contains("power tools." + powerTool + ".recipe.line3")) {
												
												if (config.getString("power tools." + powerTool + ".recipe.line3").length() == 2) {
													
													recipe.add(config.getString("power tools." + powerTool + ".recipe.line3") + " ");
													
												} else if (config.getString("power tools." + powerTool + ".recipe.line3").length() == 1) {
													
													recipe.add(config.getString("power tools." + powerTool + ".recipe.line3") + "  ");
													
												} else {
													
													recipe.add(config.getString("power tools." + powerTool + ".recipe.line3"));
													
												}
												
											}
											
										}
										
										List<String> ingredients = new ArrayList<String>();
										
										ingredients.addAll(config.getConfigurationSection("power tools." + powerTool + ".recipe.ingredients").getKeys(false));
										
										Inventory recipeInventory = Bukkit.createInventory(player, InventoryType.WORKBENCH, "Power Tool Recipe");
										
										for (int i = 0; i < (recipe.size() * 3); i ++) {
											
											if (i == 0 || i == 1 || i == 2) {
												
												if (recipe.get(0).toCharArray()[i] == ' ') {
													
													recipeInventory.setItem(i + 1, new ItemStack(Material.AIR));
													
												} else {
													
													for (int j = 0; j < ingredients.size(); j ++) {
														
														if (recipe.get(0).toCharArray()[i] == ingredients.get(j).toCharArray()[0]) {
															
															ItemStack ingredient = new ItemStack(Material.getMaterial(config.getInt("power tools." + powerTool + ".recipe.ingredients." + ingredients.get(j))));
															
															ItemMeta itemMeta = ingredient.getItemMeta();
															
															List<String> lore = new ArrayList<String>();
															
															lore.add(ChatColor.RED + "" + ChatColor.MAGIC + "Contraband");
															
															itemMeta.setLore(lore);
															
															ingredient.setItemMeta(itemMeta);
															
															recipeInventory.setItem(i + 1, ingredient);
															
														}
														
													}
													
												}
												
											}
											
											if (i == 3 || i == 4 || i == 5) {
												
												if (recipe.get(1).toCharArray()[i - 3] == ' ') {
													
													recipeInventory.setItem(i + 1, new ItemStack(Material.AIR));
													
												} else {
													
													for (int j = 0; j < ingredients.size(); j ++) {
														
														if (recipe.get(1).toCharArray()[i - 3] == ingredients.get(j).toCharArray()[0]) {
															
															ItemStack ingredient = new ItemStack(Material.getMaterial(config.getInt("power tools." + powerTool + ".recipe.ingredients." + ingredients.get(j))));
															
															ItemMeta itemMeta = ingredient.getItemMeta();
															
															List<String> lore = new ArrayList<String>();
															
															lore.add(ChatColor.RED + "" + ChatColor.MAGIC + "Contraband");
															
															itemMeta.setLore(lore);
															
															ingredient.setItemMeta(itemMeta);
															
															recipeInventory.setItem(i + 1, ingredient);
															
														}
														
													}
													
												}
												
											}
											
											if (i == 6 || i == 7 || i == 8) {
												
												if (recipe.get(2).toCharArray()[i - 6] == ' ') {
													
													recipeInventory.setItem(i + 1, new ItemStack(Material.AIR));
													
												} else {
													
													for (int j = 0; j < ingredients.size(); j ++) {
														
														if (recipe.get(2).toCharArray()[i - 6] == ingredients.get(j).toCharArray()[0]) {
															
															ItemStack ingredient = new ItemStack(Material.getMaterial(config.getInt("power tools." + powerTool + ".recipe.ingredients." + ingredients.get(j))));
															
															ItemMeta itemMeta = ingredient.getItemMeta();
															
															List<String> lore = new ArrayList<String>();
															
															lore.add(ChatColor.RED + "" + ChatColor.MAGIC + "Contraband");
															
															itemMeta.setLore(lore);
															
															ingredient.setItemMeta(itemMeta);
															
															recipeInventory.setItem(i + 1, ingredient);
															
														}
														
													}
													
												}
												
											}
											
										}
										
										ItemStack result = SQPowerTools.getPowerTool(name);
										
										ItemMeta itemMeta = result.getItemMeta();
										
										List<String> lore = itemMeta.getLore();
										
										lore.add(ChatColor.RED + "" + ChatColor.MAGIC + "Contraband");
										
										itemMeta.setLore(lore);
										
										result.setItemMeta(itemMeta);
										
										recipeInventory.setItem(0, result);
										
										player.openInventory(recipeInventory);
										
									}
									
								}
								
							} else if (inventory.getTitle().equals("Power Tool Mods")) {
								
								Inventory modifierInventory = Bukkit.createInventory(player, 54, "Power Tool Mod");
								
								modifierInventory.setItem(53, clicked);
								
								int pos = 0;
								
								for (int i = 0; i < SQPowerTools.powerToolNames.size(); i ++) {
									
									if (SQPowerTools.powerToolNames.get(i).equals(SQPowerTools.getName(clicked))) {
										
										pos = i;
										
									}
									
								}
								
								ItemStack maxMods = new ItemStack(Material.PAPER);
								
								List<String> maxModsLore = new ArrayList<String>();
								
								maxModsLore.add("Max Total Mod Levels: " + Integer.toString(SQPowerTools.powerToolMaxLevels.get(pos)));
								maxModsLore.add(ChatColor.RED + "" + ChatColor.MAGIC + "Contraband");

								ItemMeta itemMeta = maxMods.getItemMeta();
								
								itemMeta.setLore(maxModsLore);
								
								itemMeta.setDisplayName("Info");
								
								maxMods.setItemMeta(itemMeta);
								
								modifierInventory.setItem(45, maxMods);
								
								for (int i = 0; i < SQPowerTools.powerToolModNames.get(pos).size(); i ++) {
									
									ItemStack mod = new ItemStack((SQPowerTools.powerToolModMaterials.get(pos).get(i)));
									
									mod.addUnsafeEnchantments(SQPowerTools.powerToolModEnchants.get(pos).get(i));
									
									mod.setDurability(Short.parseShort(Integer.toString(SQPowerTools.powerToolModDatas.get(pos).get(i)))); 
									
									ItemMeta modItemMeta = mod.getItemMeta();
									
									List<String> modLore = new ArrayList<String>();
									
									modLore.add(SQPowerTools.powerToolModNames.get(pos).get(i));
									modLore.add("Amount: " + SQPowerTools.powerToolModItemNumbers.get(pos).get(i));
									modLore.add("Levels: " + SQPowerTools.powerToolModLevels.get(pos).get(i));
									
									if (SQPowerTools.powerToolModEnergies.get(pos).get(i) > 0) {
						
										modLore.add(ChatColor.RED + "+ " + SQPowerTools.powerToolModEnergies.get(pos).get(i) + " Energy");
										
									} else if (SQPowerTools.powerToolModEnergies.get(pos).get(i) < 0) {
										
										modLore.add(ChatColor.RED + "- " + SQPowerTools.powerToolModEnergies.get(pos).get(i) + " Energy");
										
									}
									
									for (int j = 0; j < SQPowerTools.powerToolModAttributes.get(pos).get(i).size(); j ++) {
										
										String attributeName = "";
										
										Float adjustment = SQPowerTools.powerToolModAmounts.get(pos).get(i).get(j);;
										
										if (SQPowerTools.powerToolModAttributes.get(pos).get(i).get(j).equals("generic.attackSpeed")) {
											
											attributeName = "Attack Speed";

										} else if (SQPowerTools.powerToolModAttributes.get(pos).get(i).get(j).equals("generic.attackDamage")) {
											
											attributeName = "Damage";
											
										} else if (SQPowerTools.powerToolModAttributes.get(pos).get(i).get(j).equals("generic.movementSpeed")) {
											
											attributeName = "Movement Speed";
											
										} else if (SQPowerTools.powerToolModAttributes.get(pos).get(i).get(j).equals("generic.armor")) {
											
											attributeName = "Armor";
											
										} else if (SQPowerTools.powerToolModAttributes.get(pos).get(i).get(j).equals("generic.knockbackResistance")) {
											
											attributeName = "Knockback Resistance";
											
										} else if (SQPowerTools.powerToolModAttributes.get(pos).get(i).get(j).equals("generic.luck")) {
											
											attributeName = "Luck";
											
										} else if (SQPowerTools.powerToolModAttributes.get(pos).get(i).get(j).equals("generic.maxHealth")) {
											
											attributeName = "Health";
											
										} else {
											
											attributeName = SQPowerTools.powerToolModAttributes.get(pos).get(i).get(j);
										}
										
										if (SQPowerTools.powerToolModOperations.get(pos).get(i).get(j) == 0) {
											
											if (adjustment > 0) {
												
												modLore .add(ChatColor.GOLD + "+ " + adjustment + " " + attributeName);
												
											} else if (adjustment < 0) {
												
												modLore .add(ChatColor.GOLD + "- " + adjustment + " " + attributeName);
												
											}
											
										} else {
											
											if (adjustment > 0) {
												
												modLore .add(ChatColor.GOLD + "+ " + adjustment + "% " + attributeName);
												
											} else if (adjustment < 0) {
												
												modLore .add(ChatColor.GOLD + "- " + adjustment + "% " + attributeName);
												
											}
											
										}
										
									}
									
									if (SQPowerTools.powerToolModEffects.get(pos).get(i).size() > 0) {
									
										modLore.add(ChatColor.DARK_PURPLE + "Potion Effects:");
										
										for (int j = 0; j < SQPowerTools.powerToolModEffects.get(pos).get(i).size(); j ++) {
											
											modLore.add(ChatColor.DARK_PURPLE + "  " + EffectUtils.getEffectName(SQPowerTools.powerToolModEffects.get(pos).get(i).get(j)) + ":");
											modLore.add(ChatColor.DARK_PURPLE + "    Level " + SQPowerTools.powerToolModEffectLevels.get(pos).get(i).get(j));
											modLore.add(ChatColor.DARK_PURPLE + "    For " + SQPowerTools.powerToolModEffectDurations.get(pos).get(i).get(j) + " seconds");
											modLore.add(ChatColor.DARK_PURPLE + "    Applies " + EffectUtils.geCaseName(SQPowerTools.powerToolModEffectCases.get(pos).get(i).get(j)));
											
										}
									
									}
									
									if (SQPowerTools.powerToolModCannotCombines.get(pos).get(i).size() > 0) {
										
										modLore.add(ChatColor.BLUE + "Cannot Combine With:");
										
										for (int j = 0; j < SQPowerTools.powerToolModCannotCombines.get(pos).get(i).size(); j ++) {
											
											modLore.add(ChatColor.BLUE + " - " + SQPowerTools.powerToolModCannotCombines.get(pos).get(i).get(j));
											
										}
										
									}
									
									modLore.add(ChatColor.RED + "" + ChatColor.MAGIC + "Contraband");
									
									modItemMeta.setLore(modLore);
									
									mod.setItemMeta(modItemMeta);
									
									modifierInventory.addItem(mod);
									
								}
								
								player.openInventory(modifierInventory);
								
							}
														
						} else if (isCharger(player.getWorld().getBlockAt(inventory.getLocation()))) {
							


						} else if (event.getSlotType().equals(SlotType.CONTAINER) || event.getSlotType().equals(SlotType.RESULT) || event.getSlotType().equals(SlotType.QUICKBAR) || event.getSlotType().equals(SlotType.ARMOR)) {

						} else {
													
							event.setCancelled(true);
							
							player.sendMessage(ChatColor.RED + "You cannot put a power tool into this inventory");
														
						}
					
					}
					
				}
				
			}
			
			if (inventory.getTitle().equals("Modifier")) {
				
				boolean needToRemove = true;
				
				if (clicked.hasItemMeta()) {
					
					if (clicked.getItemMeta().hasLore()) {
						
						if  (clicked.getItemMeta().getLore().contains(ChatColor.RED + "" + ChatColor.MAGIC + "Contraband")) {
							
							if (clicked.getItemMeta().getLore().contains(ChatColor.DARK_PURPLE + "Power Tool")) {
								
								needToRemove = false;
								
							} else {
								
								event.setCancelled(true);
								
							}

						}
						
					}
					
				}
				
				ItemStack powerTool = inventory.getItem(10);

				if (powerTool != null) {
					
					if (powerTool.hasItemMeta()) {
						
						if (powerTool.getItemMeta().hasLore()) {
							
							if (powerTool.getItemMeta().getLore().contains(ChatColor.DARK_PURPLE + "Power Tool")) {
								
								List<Material> modifiers = new ArrayList<Material>();
								List<Integer> datas = new ArrayList<Integer>();
								
								List<String> modifierNames = new ArrayList<String>();
								List<Integer> modifierLevels = new ArrayList<Integer>();
								List<Integer> modifierAmounts = new ArrayList<Integer>();
								
								for (int i = 0; i < SQPowerTools.powerToolNames.size(); i ++) {
									
									if (SQPowerTools.powerToolNames.get(i).equals(SQPowerTools.getName(powerTool))) {
										
										modifiers = SQPowerTools.powerToolModMaterials.get(i);
										datas = SQPowerTools.powerToolModDatas.get(i);
										modifierNames = SQPowerTools.powerToolModNames.get(i);									
										modifierLevels = SQPowerTools.powerToolModLevels.get(i);
										modifierAmounts = SQPowerTools.powerToolModItemNumbers.get(i);
										
									}
									
								}
								
								if (inventory.getItem(13) != null) {
									
									if (event.getSlot() != 13) {
										
										for (int i = 0; i < modifiers.size(); i ++) {
											
											if (modifiers.get(i).equals(inventory.getItem(13).getType()) && datas.get(i).equals((int) inventory.getItem(13).getDurability())) {
												
												if (modifierAmounts.get(i) <= inventory.getItem(13).getAmount()) {
													
													HashMap<String,Integer> modifierMap = SQPowerTools.getModifiers(powerTool);
													
													List<String> errorLore = new ArrayList<String>();
													
													boolean error = false;
													
													if (modifierMap.containsKey(modifierNames.get(i))) {
														
														if (modifierMap.get(modifierNames.get(i)) < modifierLevels.get(i)) {
															
															modifierMap.put(modifierNames.get(i), modifierMap.get(modifierNames.get(i)) + 1);
															
														} else {  
															
															error = true;
															
															errorLore.add(ChatColor.RED + "The max level for this modifier on the");
															errorLore.add(ChatColor.RED + "power tool has been reached.");
															errorLore.add(ChatColor.RED + "" + ChatColor.MAGIC + "Contraband");
															
														}
														
													} else {
														
														modifierMap.put(modifierNames.get(i), 1);
														
													}
													
													int pos = 0;
													
													for (int j = 0; j < SQPowerTools.powerToolNames.size(); j ++) {
														
														if (SQPowerTools.powerToolNames.get(j).equals(SQPowerTools.getName(powerTool))) {
															
															pos = j;
															
														}
														
													}
													
													int totalLevels = 0;
													
													List<Integer> currentModifierLevels = new ArrayList<Integer>();
													
													List<String> currentModifierNames = new ArrayList<String>();
													
													currentModifierNames.addAll(modifierMap.keySet());
													
													for (int j = 0; j < modifierMap.size(); j ++) {
														
														currentModifierLevels.add(modifierMap.get(currentModifierNames.get(j)));
														
														totalLevels = totalLevels + modifierMap.get(currentModifierNames.get(j));
														
													}
													
													if (totalLevels > SQPowerTools.powerToolMaxLevels.get(pos)) {
														
														error = true;
														
														errorLore.add(ChatColor.RED + "The max levels of all modifiers");
														errorLore.add(ChatColor.RED + "on the power tool has been reached.");
														errorLore.add(ChatColor.RED + "" + ChatColor.MAGIC + "Contraband");
														
													}
													
													List<String> oldModifierNames = new ArrayList<String>();
													
													oldModifierNames.addAll(SQPowerTools.getModifiers(powerTool).keySet());
													
													for (int j = 0; j < oldModifierNames.size(); j ++) {
														
														for (int k = 0; k < SQPowerTools.powerToolModNames.get(pos).size(); k ++) {
															
															if (oldModifierNames.get(j).equals(SQPowerTools.powerToolModNames.get(pos).get(k))) {
																
																if (SQPowerTools.powerToolModCannotCombines.get(pos).get(k).contains(modifierNames.get(i))) {
																	
																	error = true;
																	
																	errorLore.add(ChatColor.RED + "This modifier conflicts with a");
																	errorLore.add(ChatColor.RED + "modifier that is on this power tool.");
																	errorLore.add(ChatColor.RED + "" + ChatColor.MAGIC + "Contraband");
																	
																}
																
															}
															
														}
														
													}
													
													if (!error) {
														
														List<String> lore = powerTool.getItemMeta().getLore();
														
														ItemStack resultPowerTool = SQPowerTools.getPowerTool(SQPowerTools.getName(powerTool));
																
														resultPowerTool = SQPowerTools.addLore(resultPowerTool, pos, SQPowerTools.getName(powerTool), currentModifierNames, currentModifierLevels, true);
														
														resultPowerTool = SQPowerTools.addModifiers(resultPowerTool, modifierMap);
														
														resultPowerTool = SQPowerTools.setEnergy(resultPowerTool, SQPowerTools.getEnergy(powerTool));
														
														if (inventory.getItem(16) != null) {
															
															boolean drop = true;
														
															if (inventory.getItem(16).hasItemMeta()) {
																
																if (inventory.getItem(16).getItemMeta().hasLore()) {
																	
																	if (inventory.getItem(16).getItemMeta().getLore().contains(ChatColor.RED + "" + ChatColor.MAGIC + "Contraband")) {
																		
																		drop = false;
																		
																	}
																	
																}
																
															}
															
															if (drop) {
																
																player.getLocation().getWorld().dropItem(player.getLocation(), inventory.getItem(16));
																
															}
															
														}
														
														inventory.setItem(16, resultPowerTool);
														
														ItemMeta itemMeta = powerTool.getItemMeta();
														
														itemMeta.setLore(lore);
														
														powerTool.setItemMeta(itemMeta);
														
														inventory.setItem(10, powerTool);
														
														needToRemove = false;
														
													} else {
														
														ItemStack errorItem = new ItemStack(Material.PAPER);
														
														ItemMeta errorItemMeta = errorItem.getItemMeta();
														
														errorItemMeta.setDisplayName("Error");
														errorItemMeta.setLore(errorLore);
														
														errorItem.setItemMeta(errorItemMeta);
														
														if (inventory.getItem(16) != null) {
															
															boolean drop = true;
															
															if (inventory.getItem(16).hasItemMeta()) {
																
																if (inventory.getItem(16).getItemMeta().hasLore()) {
																	
																	if (inventory.getItem(16).getItemMeta().getLore().contains(ChatColor.RED + "" + ChatColor.MAGIC + "Contraband")) {
																		
																		drop = false;
																		
																	}
																	
																}
																
															}
															
															if (drop) {
																
																player.getLocation().getWorld().dropItem(player.getLocation(), inventory.getItem(16));
																
															}
															
														}
														
														inventory.setItem(16, errorItem);
														
														needToRemove = false;
														
													}									

												}

											}
																						
										}
										
									} else if (event.getSlot() == 13) {
										
										for (int i = 0; i < modifiers.size(); i ++) {
											
											if (modifiers.get(i).equals(clicked.getType()) && datas.get(i).equals((int) clicked.getDurability())) {
												
												if (modifierAmounts.get(i) <= (clicked.getAmount() + inventory.getItem(13).getAmount())) {
													
													HashMap<String,Integer> modifierMap = SQPowerTools.getModifiers(powerTool);
													
													List<String> errorLore = new ArrayList<String>();
													
													boolean error = false;
													
													if (modifierMap.containsKey(modifierNames.get(i))) {
														
														if (modifierMap.get(modifierNames.get(i)) < modifierLevels.get(i)) {
															
															modifierMap.put(modifierNames.get(i), modifierMap.get(modifierNames.get(i)) + 1);
															
														} else {
															
															error = true;
															
															errorLore.add(ChatColor.RED + "The max level for this modifier");
															errorLore.add(ChatColor.RED + "on the power tool has been reached.");
															errorLore.add(ChatColor.RED + "" + ChatColor.MAGIC + "Contraband");
															
														}
														
													} else {
														
														modifierMap.put(modifierNames.get(i), 1);
														
													}
													
													int pos = 0;
													
													for (int j = 0; j < SQPowerTools.powerToolNames.size(); j ++) {
														
														if (SQPowerTools.powerToolNames.get(j).equals(SQPowerTools.getName(powerTool))) {
															
															pos = j;
															
														}
														
													}
													
													int totalLevels = 0;
													
													List<Integer> currentModifierLevels = new ArrayList<Integer>();
													
													List<String> currentModifierNames = new ArrayList<String>();
													
													currentModifierNames.addAll(modifierMap.keySet());
													
													for (int j = 0; j < modifierMap.size(); j ++) {
														
														currentModifierLevels.add(modifierMap.get(currentModifierNames.get(j)));
														
														totalLevels = totalLevels + modifierMap.get(currentModifierNames.get(j));
														
													}
													
													if (totalLevels > SQPowerTools.powerToolMaxLevels.get(pos)) {
														
														error = true;
														
														errorLore.add(ChatColor.RED + "The max levels of all modifiers");
														errorLore.add(ChatColor.RED + "on the power tool has been reached.");
														errorLore.add(ChatColor.RED + "" + ChatColor.MAGIC + "Contraband");
														
													}
													
													List<String> oldModifierNames = new ArrayList<String>();
													
													oldModifierNames.addAll(SQPowerTools.getModifiers(powerTool).keySet());
													
													for (int j = 0; j < oldModifierNames.size(); j ++) {
														
														for (int k = 0; k < SQPowerTools.powerToolModNames.get(pos).size(); k ++) {
															
															if (oldModifierNames.get(j).equals(SQPowerTools.powerToolModNames.get(pos).get(k))) {
																
																if (SQPowerTools.powerToolModCannotCombines.get(pos).get(k).contains(modifierNames.get(i))) {
																	
																	error = true;
																	
																	errorLore.add(ChatColor.RED + "This modifier conflicts with a");
																	errorLore.add(ChatColor.RED + "modifier that is on this power tool.");
																	errorLore.add(ChatColor.RED + "" + ChatColor.MAGIC + "Contraband");
																	
																}
																
															}
															
														}
														
													}
													
													if (!error) {

														List<String> lore = powerTool.getItemMeta().getLore();
														
														ItemStack resultPowerTool = SQPowerTools.getPowerTool(SQPowerTools.getName(powerTool));
														
														resultPowerTool = SQPowerTools.addLore(resultPowerTool, pos, SQPowerTools.getName(powerTool), currentModifierNames, currentModifierLevels, true);
														
														resultPowerTool = SQPowerTools.addModifiers(resultPowerTool, modifierMap);
														
														resultPowerTool = SQPowerTools.setEnergy(resultPowerTool, SQPowerTools.getEnergy(powerTool));
														
														if (inventory.getItem(16) != null) {
															
															boolean drop = true;
															
															if (inventory.getItem(16).hasItemMeta()) {
																
																if (inventory.getItem(16).getItemMeta().hasLore()) {
																	
																	if (inventory.getItem(16).getItemMeta().getLore().contains(ChatColor.RED + "" + ChatColor.MAGIC + "Contraband")) {
																		
																		drop = false;
																		
																	}
																	
																}
																
															}
															
															if (drop) {
																
																player.getLocation().getWorld().dropItem(player.getLocation(), inventory.getItem(16));
																
															}
															
														}
														
														inventory.setItem(16, resultPowerTool);
														
														ItemMeta itemMeta = powerTool.getItemMeta();
														
														itemMeta.setLore(lore);
														
														powerTool.setItemMeta(itemMeta);
														
														inventory.setItem(10, powerTool);
														
														needToRemove = false;
														
													} else {
														
														ItemStack errorItem = new ItemStack(Material.PAPER);
														
														ItemMeta errorItemMeta = errorItem.getItemMeta();
														
														errorItemMeta.setDisplayName("Error");
														errorItemMeta.setLore(errorLore);
														
														errorItem.setItemMeta(errorItemMeta);
														
														if (inventory.getItem(16) != null) {
															
															boolean drop = true;
															
															if (inventory.getItem(16).hasItemMeta()) {
																
																if (inventory.getItem(16).getItemMeta().hasLore()) {
																	
																	if (inventory.getItem(16).getItemMeta().getLore().contains(ChatColor.RED + "" + ChatColor.MAGIC + "Contraband")) {
																		
																		drop = false;
																		
																	}
																	
																}
																
															}
															
															if (drop) {
																
																player.getLocation().getWorld().dropItem(player.getLocation(), inventory.getItem(16));
																
															}
															
														}
														
														inventory.setItem(16, errorItem);
														
														needToRemove = false;
														
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
				
				if (needToRemove) {
					
					if (inventory.getItem(16) != null) {
						
						if (inventory.getItem(16).hasItemMeta()) {
							
							if (inventory.getItem(16).getItemMeta().hasLore()) {
							
								if (inventory.getItem(16).getItemMeta().getLore().contains(ChatColor.RED + "" + ChatColor.MAGIC + "Contraband")) {
									
									inventory.setItem(16, new ItemStack(Material.AIR));
									
								}
								
							}
							
						}
						
					}
					
				}

			}
			
		}
		
	}
	
	@EventHandler
	public static void onPlayerInteract (PlayerInteractEvent event) {
		
		Player player = event.getPlayer();
		
		if (event.getAction().equals(Action.RIGHT_CLICK_BLOCK)) {
			
			if (event.getClickedBlock().getType().equals(Material.WALL_SIGN)) {
				
				Sign sign = (Sign) event.getClickedBlock().getState();
				
				if (sign.getLine(0).equalsIgnoreCase("[charger]")) {
					
					if (getAttachedBlock(sign).getType().equals(Material.FURNACE) || getAttachedBlock(sign).getType().equals(Material.BURNING_FURNACE)) {
						
						sign.setLine(0, ChatColor.BLUE + "Charger");
						sign.setLine(1, ChatColor.RED + "[Disabled]");
						sign.update();
						
					} else {
						
						player.sendMessage(ChatColor.RED + "The charger sign must be placed on a furnace");
						
					}
					
				} else if (sign.getLine(0).equals(ChatColor.BLUE + "Charger")) {
					
					if (sign.getLine(1).equals(ChatColor.RED + "[Disabled]")) {
						
						if (getAttachedBlock(sign).getType().equals(Material.FURNACE) || getAttachedBlock(sign).getType().equals(Material.BURNING_FURNACE)) {
						
							sign.setLine(1, ChatColor.GREEN + "[Enabled]");
							sign.update();
							
							SQPowerTools.chargerLocations.add(getAttachedBlock(sign).getLocation());

						} else {
							
							player.sendMessage(ChatColor.RED + "The charger sign must be placed on a furnace");
							
						}
							
					} else if (sign.getLine(1).equals(ChatColor.GREEN + "[Enabled]")) {
						
						if (getAttachedBlock(sign).getType().equals(Material.FURNACE) || getAttachedBlock(sign).getType().equals(Material.BURNING_FURNACE)) {
						
							sign.setLine(1, ChatColor.RED + "[Disabled]");
							sign.update();
							
							if (SQPowerTools.chargerLocations.contains(getAttachedBlock(sign).getLocation())) {
								
								SQPowerTools.chargerLocations.remove(getAttachedBlock(sign).getLocation());
								
							}
							
						} else {
							
							player.sendMessage(ChatColor.RED + "The charger sign must be placed on a furnace");
							
						}
						
					}
					
				} else if (sign.getLine(0).equalsIgnoreCase("[modifier]")) {
					
					if (getAttachedBlock(sign).getType().equals(Material.IRON_BLOCK)) {
						
						sign.setLine(0, ChatColor.BLUE + "Modifier");
						sign.update();
						
					} else {
						
						player.sendMessage(ChatColor.RED + "The modifier sign must be placed on an anvil");
						
					}
					
				}
				
			} else if (isModifier(event.getClickedBlock())) {
				
				event.setCancelled(true);
				
				Inventory modifierInventory = Bukkit.createInventory(player, 27, "Modifier");
				
				ItemStack e = new ItemStack(Material.AIR);
				
				List<String> lore = new ArrayList<String>();
				
				ItemStack i = new ItemStack(Material.IRON_FENCE);
				
				ItemMeta itemMeta = i.getItemMeta();
				
				itemMeta.setDisplayName("Barrier");
				lore.add(ChatColor.RED + "" + ChatColor.MAGIC + "Contraband");
				itemMeta.setLore(lore);
				i.setItemMeta(itemMeta);
				
				ItemStack paper1 = new ItemStack(Material.PAPER);
				
				itemMeta = paper1.getItemMeta();
				
				itemMeta.setDisplayName("Info");
				lore.clear();				
				lore.add("This slot is where you");
				lore.add("put the power tool");
				lore.add(ChatColor.RED + "" + ChatColor.MAGIC + "Contraband");
				itemMeta.setLore(lore);
				paper1.setItemMeta(itemMeta);
				
				ItemStack paper2 = new ItemStack(Material.PAPER);

				itemMeta = paper2.getItemMeta();
				
				itemMeta.setDisplayName("Info");
				lore.clear();
				lore.add("This slot is where you");
				lore.add("put the modifier items");
				lore.add(ChatColor.RED + "" + ChatColor.MAGIC + "Contraband");
				itemMeta.setLore(lore);
				paper2.setItemMeta(itemMeta);
				
				ItemStack paper3 = new ItemStack(Material.PAPER);

				itemMeta = paper3.getItemMeta();
				
				itemMeta.setDisplayName("Info");
				lore.clear();
				lore.add("This slot is where you");
				lore.add("get the modified power tool");
				lore.add(ChatColor.RED + "" + ChatColor.MAGIC + "Contraband");
				itemMeta.setLore(lore);
				paper3.setItemMeta(itemMeta);
				
				ItemStack refresh = new ItemStack(Material.DOUBLE_PLANT);

				itemMeta = refresh.getItemMeta();
				
				itemMeta.setDisplayName("Refresh");
				lore.clear();
				lore.add("Click this to refresh the");
				lore.add("modified power tool");
				lore.add("This does not affect the" );
				lore.add("power tool you get");
				lore.add(ChatColor.RED + "" + ChatColor.MAGIC + "Contraband");
				itemMeta.setLore(lore);
				refresh.setItemMeta(itemMeta);
				
				ItemStack[] defaultContents = new ItemStack[]{i, paper1, i, i, paper2, i, i, paper3, i, i, e, i, i, e, i, i, e, i, i, i, i, i, i, i, i, refresh, i};
				
				modifierInventory.setContents(defaultContents);
				
				player.closeInventory();
				
				player.openInventory(modifierInventory);
				
			}
			
		}
		
	}
	
	@EventHandler
	public static void onInventoryClose(InventoryCloseEvent event) {
		
		Player player = (Player) event.getPlayer();
		
		Inventory inventory = event.getInventory();
		
		if (inventory.getTitle().equals("Modifier")) {
			
			if (inventory.getItem(0) != null) {
				
				if (inventory.getItem(10) != null) {
					
					givePlayerItem(player, inventory.getItem(10));
					
				}
				
				if (inventory.getItem(13) != null) {
					
					givePlayerItem(player, inventory.getItem(13));
					
				}
				
				boolean isResult = true;
				
				ItemStack result = inventory.getItem(16);
				
				if (result != null) {
					
					isResult = false;
					
					if (result.hasItemMeta()) {
						
						if (result.getItemMeta().hasLore()) {
							
							if (result.getItemMeta().getLore().contains(ChatColor.RED + "" + ChatColor.MAGIC + "Contraband")) {
								
								isResult = true;
								
							}
							
						}
						
					}
					
				}
				
				if (!isResult) {
					
					givePlayerItem(player, result);
					
				}
				
			} else {
				
				if (inventory.getItem(10) != null) {
					
					givePlayerItem(player, inventory.getItem(10));
					
				}
				
				if (inventory.getItem(13) != null) {
					
					givePlayerItem(player, inventory.getItem(13));
					
				}
				
				if (inventory.getItem(16) != null) {
					
					ItemStack powerTool = inventory.getItem(16);
					
					List<String> lore = powerTool.getItemMeta().getLore();
					lore.remove(lore.size() - 1);
					ItemMeta itemMeta = powerTool.getItemMeta();
					itemMeta.setLore(lore);
					powerTool.setItemMeta(itemMeta);
					
					givePlayerItem(player, powerTool);
					
				}
				
			}
			
		}
		
	}

	public static Block getAttachedBlock(Sign sign) {

		MaterialData materialData = sign.getBlock().getState().getData();
		BlockFace face = ((Attachable) materialData).getAttachedFace();

		return sign.getBlock().getRelative(face);

	}
	
	public static boolean isCharger(Block block) {
		
		if (block.getType().equals(Material.FURNACE) || block.getType().equals(Material.BURNING_FURNACE)) {
			
			if (block.getRelative(BlockFace.EAST).getType().equals(Material.WALL_SIGN)) {
				
				Sign sign = (Sign) block.getRelative(BlockFace.EAST).getState();
				
				if (sign.getLine(0).equals(ChatColor.BLUE + "Charger")) {
					
					return true;
					
				}
				
			}
			
			if (block.getRelative(BlockFace.WEST).getType().equals(Material.WALL_SIGN)) {
				
				Sign sign = (Sign) block.getRelative(BlockFace.WEST).getState();
				
				if (sign.getLine(0).equals(ChatColor.BLUE + "Charger")) {
					
					return true;
					
				}
				
			}
			
			if (block.getRelative(BlockFace.NORTH).getType().equals(Material.WALL_SIGN)) {
				
				Sign sign = (Sign) block.getRelative(BlockFace.NORTH).getState();
				
				if (sign.getLine(0).equals(ChatColor.BLUE + "Charger")) {
					
					return true;
					
				}
				
			}
			
			if (block.getRelative(BlockFace.SOUTH).getType().equals(Material.WALL_SIGN)) {
				
				Sign sign = (Sign) block.getRelative(BlockFace.SOUTH).getState();
				
				if (sign.getLine(0).equals(ChatColor.BLUE + "Charger")) {
					
					return true;
					
				}
				
			}
			
		}
			
		return false;
		
	}
	
	public static boolean isModifier(Block block) {
		
		if (block.getType().equals(Material.IRON_BLOCK)) {
			
			if (block.getRelative(BlockFace.EAST).getType().equals(Material.WALL_SIGN)) {
				
				Sign sign = (Sign) block.getRelative(BlockFace.EAST).getState();
				
				if (sign.getLine(0).equals(ChatColor.BLUE + "Modifier")) {
					
					return true;
					
				}
				
			}
			
			if (block.getRelative(BlockFace.WEST).getType().equals(Material.WALL_SIGN)) {
				
				Sign sign = (Sign) block.getRelative(BlockFace.WEST).getState();
				
				if (sign.getLine(0).equals(ChatColor.BLUE + "Modifier")) {
					
					return true;
					
				}
				
			}
			
			if (block.getRelative(BlockFace.NORTH).getType().equals(Material.WALL_SIGN)) {
				
				Sign sign = (Sign) block.getRelative(BlockFace.NORTH).getState();
				
				if (sign.getLine(0).equals(ChatColor.BLUE + "Modifier")) {
					
					return true;
					
				}
				
			}
			
			if (block.getRelative(BlockFace.SOUTH).getType().equals(Material.WALL_SIGN)) {
				
				Sign sign = (Sign) block.getRelative(BlockFace.SOUTH).getState();
				
				if (sign.getLine(0).equals(ChatColor.BLUE + "Modifier")) {
					
					return true;
					
				}
				
			}
			
		}
			
		return false;
		
	}
	
	public static void givePlayerItem(Player player, ItemStack itemStack) {
		
		boolean full = true;
		
		for (int i = 0; i < 36; i ++) {
			
			if (player.getInventory().getItem(i) == null) {
				
				full = false;
				
			}
			
		}
		
		if (full) {
			
			player.getLocation().getWorld().dropItem(player.getLocation(), itemStack);
			
		} else {
			
			player.getInventory().addItem(itemStack);
			
		}
		
	}
	
}
