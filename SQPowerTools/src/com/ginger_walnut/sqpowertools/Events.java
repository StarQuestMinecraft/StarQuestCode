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
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.inventory.InventoryAction;
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

import com.ginger_walnut.sqpowertools.objects.BlasterStats;
import com.ginger_walnut.sqpowertools.objects.Modifier;
import com.ginger_walnut.sqpowertools.objects.PowerTool;
import com.ginger_walnut.sqpowertools.objects.PowerToolType;
import com.ginger_walnut.sqpowertools.utils.EffectUtils;

public class Events implements Listener {	
	
	@EventHandler
	public void onGUIClick(InventoryClickEvent event) {
		
		if (SQPowerTools.currentGui.containsKey(event.getWhoClicked())) {
			
			event.setCancelled(true);
			
			SQPowerTools.currentGui.get(event.getWhoClicked()).click(event);
			
		}
		
	}
	
	@EventHandler
	public void onGUIClose(InventoryCloseEvent event) {
		
		if (SQPowerTools.currentGui.containsKey(event.getPlayer())) {
			
			if (SQPowerTools.currentGui.get(event.getPlayer()).close) {
				
				SQPowerTools.currentGui.remove(event.getPlayer());
				
			}

		}
		
	}
	
	@EventHandler
	public static void onInventoryClick(InventoryClickEvent event) {
		
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
			
			if (inventory.getTitle().equals("Power Tool Recipes") || inventory.getTitle().equals("Power Tool Recipe") || inventory.getTitle().equals("Charger Fuel") || inventory.getTitle().equals("Power Tool Mods") || inventory.getTitle().equals("Power Tool Mod") || inventory.getTitle().equals("Power Tool Addmods") || inventory.getTitle().equals("Power Tools")) {
				
				event.setCancelled(true);
				
			}
			
			if (inventory.getTitle().equals("Power Tool Addmods")) {
				
				ItemStack powerTool = inventory.getItem(53);
				
				PowerToolType toolType = SQPowerTools.getType(powerTool);
				
				Inventory modifierInventory = Bukkit.createInventory(player, 54, "Power Tool Addmods");
				
				boolean modifier = true;
				
				if (clicked.getItemMeta().getDisplayName() != null) {
					
					if (clicked.getItemMeta().getDisplayName().equals("Info")) {
						
						modifier = false;
						
					}
					
				}
				
				if (modifier) {
					
					if (!clicked.getItemMeta().getLore().get(0).equals(ChatColor.DARK_PURPLE + "Power Tool")) {
						
						HashMap<Modifier, Integer> modifiers = SQPowerTools.getModifiers(powerTool);
						
						boolean contains = false;
						
						for (Modifier modifierName : modifiers.keySet()) {
							
							if (modifierName.name.equals(clicked.getItemMeta().getLore().get(0))) {
								
								contains = true;

								Modifier itemModifier = null;
								
								for (Modifier typeModifier : SQPowerTools.getType(powerTool).modifiers) {
									
									if (typeModifier.name.equals(clicked.getItemMeta().getLore().get(0))) {
										
										itemModifier = typeModifier;
										
									}
									
								}
								
								int oldLevel = modifiers.get(itemModifier);
								
								modifiers.replace(itemModifier, oldLevel + 1);
								
								PowerTool pt = new PowerTool(SQPowerTools.getType(powerTool), modifiers);
								powerTool = pt.getItem();
								
							}

						}
						
						if (!contains) {
							
							Modifier itemModifier = null;
							
							for (Modifier typeModifier : SQPowerTools.getType(powerTool).modifiers) {
								
								if (typeModifier.name.equals(clicked.getItemMeta().getLore().get(0))) {
									
									itemModifier = typeModifier;
									
								}
								
							}
							
							
							if (itemModifier != null) {
								
								modifiers.put(itemModifier, 1);
								
							}
							
							PowerTool pt = new PowerTool(SQPowerTools.getType(powerTool), modifiers);
							powerTool = pt.getItem();
							
						}
						
					}
					
				}
				
				player.getInventory().setItemInMainHand(powerTool);
				
				modifierInventory.setItem(53, powerTool);
				
				ItemStack maxMods = new ItemStack(Material.PAPER);
				
				List<String> maxModsLore = new ArrayList<String>();
				
				maxModsLore.add("Max Total Mod Levels: " + Integer.toString(toolType.maxMods));
				maxModsLore.add(ChatColor.RED + "" + ChatColor.MAGIC + "Contraband");
				
				ItemMeta itemMeta = maxMods.getItemMeta();
				
				itemMeta.setLore(maxModsLore);
				
				itemMeta.setDisplayName("Info");
				
				maxMods.setItemMeta(itemMeta);
				
				modifierInventory.setItem(45, maxMods);
				
				for (int i = 0; i < toolType.modifiers.size(); i ++) {
					
					ItemStack mod = new ItemStack((toolType.modifiers.get(i).material));
					
					mod.addUnsafeEnchantments(toolType.modifiers.get(i).enchants);
					
					mod.setDurability(Short.parseShort(Integer.toString(toolType.modifiers.get(i).durability))); 
					
					ItemMeta modItemMeta = mod.getItemMeta();
					
					List<String> modLore = new ArrayList<String>();
					
					modLore.add(toolType.modifiers.get(i).name);
					modLore.add("Amount: " + toolType.modifiers.get(i).number);
					modLore.add("Levels: " + toolType.modifiers.get(i).levels);
					
					if (toolType.modifiers.get(i).energy > 0) {
						
						modLore.add(ChatColor.RED + "+ " + toolType.modifiers.get(i).energy + " Energy");
						
					} else if (toolType.modifiers.get(i).energy < 0) {
						
						modLore.add(ChatColor.RED + "- " + toolType.modifiers.get(i).energy + " Energy");
						
					}
					
					for (int j = 0; j < toolType.modifiers.get(i).attributes.size(); j ++) {
						
						String attributeName = "";
						
						float adjustment = toolType.modifiers.get(i).attributes.get(j).amount;
						
						if (toolType.modifiers.get(i).attributes.get(j).attribute.equals("generic.attackSpeed")) {
							
							attributeName = "Attack Speed";
							
						} else if (toolType.modifiers.get(i).attributes.get(j).attribute.equals("generic.attackDamage")) {
							
							attributeName = "Damage";
							
						} else if (toolType.modifiers.get(i).attributes.get(j).attribute.equals("generic.movementSpeed")) {
							
							attributeName = "Movement Speed";
							
						} else if (toolType.modifiers.get(i).attributes.get(j).attribute.equals("generic.armor")) {
							
							attributeName = "Armor";
							
						} else if (toolType.modifiers.get(i).attributes.get(j).attribute.equals("generic.knockbackResistance")) {
							
							attributeName = "Knockback Resistance";
							
						} else if (toolType.modifiers.get(i).attributes.get(j).attribute.equals("generic.luck")) {
							
							attributeName = "Luck";
							
						} else if (toolType.modifiers.get(i).attributes.get(j).attribute.equals("generic.maxHealth")) {
							
							attributeName = "Health";
							
						} else if (toolType.modifiers.get(i).attributes.get(j).attribute.equals("generic.armorToughness")) {
							
							attributeName = "Armor Toughness";
							
						} else {
							
							attributeName = toolType.modifiers.get(i).attributes.get(j).attribute;
						}
						
						if (toolType.modifiers.get(i).attributes.get(j).operation == 0) {
							
							if (adjustment > 0) {
								
								modLore.add(ChatColor.GOLD + "+ " + adjustment + " " + attributeName);
								
							} else if (adjustment < 0) {
								
								modLore.add(ChatColor.GOLD + "- " + Math.abs(adjustment) + " " + attributeName);
								
							}
							
						} else {
							
							if (adjustment > 0) {
								
								modLore.add(ChatColor.GOLD + "+ " + (adjustment * 100) + "% " + attributeName);
								
							} else if (adjustment < 0) {
								
								modLore.add(ChatColor.GOLD + "- " + Math.abs(adjustment * 100) + "% " + attributeName);
								
							}
							
						}
						
					}
					
					if (toolType.modifiers.get(i).blasterStats != null) {
						
						BlasterStats blasterStats = toolType.modifiers.get(i).blasterStats;
						
						if (blasterStats.damage != 0) {
							
							if (blasterStats.damage > 0) {
								
								modLore.add(ChatColor.GOLD + "+ " + blasterStats.damage + " Ranged Damage");
								
								
							} else if (blasterStats.damage < 0) {
								
								modLore.add(ChatColor.GOLD + "- " + blasterStats.damage + " Ranged Damage");
								
							}
							
						}
						
						if (blasterStats.cooldown != 0) {
						
							if (Double.toString(Double.parseDouble(Integer.toString(blasterStats.cooldown)) / 20.0).endsWith(".0")) {
								
								if (blasterStats.cooldown / 20.0 > 0) {
										
									modLore.add(ChatColor.GOLD + "+ " + (blasterStats.cooldown / 20.0) + " Ranged Cooldown");
										
										
								} else if (blasterStats.cooldown / 20.0 < 0) {
										
									modLore.add(ChatColor.GOLD + "- " + (blasterStats.cooldown / 20.0) + " Ranged Cooldown");
										
								}
								
							} else {
								
								if (Double.parseDouble(Integer.toString(blasterStats.cooldown)) / 20.0 > 0) {
									
									modLore.add(ChatColor.GOLD + "+ " + (Double.parseDouble(Integer.toString(blasterStats.cooldown)) / 20.0) + " Ranged Cooldown");
										
										
								} else if (Double.parseDouble(Integer.toString(blasterStats.cooldown)) / 20.0 < 0) {
										
									modLore.add(ChatColor.GOLD + "- " + (Double.parseDouble(Integer.toString(blasterStats.cooldown)) / 20.0) + " Ranged Cooldown");
										
								}
								
							}
						
						}

						if (blasterStats.scope != 0) {
							
							if (blasterStats.scope > 0) {
								
								modLore.add(ChatColor.GOLD + "+ " + (blasterStats.scope + 1) + " Ranged Scope");
								
								
							} else if (blasterStats.scope < 0) {
								
								modLore.add(ChatColor.GOLD + "- " + (blasterStats.scope + 1) + " Ranged Scope");
								
							}
							
						}
						
					}
					
					if (toolType.modifiers.get(i).effects.size() > 0) {
						
						modLore.add(ChatColor.DARK_PURPLE + "Potion Effects:");
						
						for (int j = 0; j < toolType.modifiers.get(i).effects.size(); j ++) {
							
							modLore.add(ChatColor.DARK_PURPLE + "  " + EffectUtils.getEffectName(toolType.modifiers.get(i).effects.get(j).effect) + ":");
							modLore.add(ChatColor.DARK_PURPLE + "    Level " + (toolType.modifiers.get(i).effects.get(j).level + 1));
							modLore.add(ChatColor.DARK_PURPLE + "    For " + toolType.modifiers.get(i).effects.get(j).duration + " seconds");
							modLore.add(ChatColor.DARK_PURPLE + "    Applies " + EffectUtils.getCaseName(toolType.modifiers.get(i).effects.get(j).effectCase));
							
						}
						
					}
					
					if (toolType.modifiers.get(i).cannotCombines.size() > 0) {
						
						modLore.add(ChatColor.BLUE + "Cannot Combine With:");
						
						for (int j = 0; j < toolType.modifiers.get(i).cannotCombines.size(); j ++) {
							
							modLore.add(ChatColor.BLUE + " - " + toolType.modifiers.get(i).cannotCombines.get(j));
							
						}
						
					}
					
					modLore.add(ChatColor.RED + "" + ChatColor.MAGIC + "Contraband");
					
					modItemMeta.setLore(modLore);
					
					mod.setItemMeta(modItemMeta);
					
					modifierInventory.addItem(mod);
					
				}
				
				player.openInventory(modifierInventory);
				
			}
			
			if (clicked.hasItemMeta()) {
				
				if (clicked.getItemMeta().hasLore()) {
					
					if (clicked.getItemMeta().getLore().contains(ChatColor.DARK_PURPLE + "Power Tool")) {
						
						if (inventory.getType().equals(InventoryType.ANVIL) || inventory.getType().equals(InventoryType.ENCHANTING)) {
							
							player.sendMessage(ChatColor.RED + "You cannot put a power tool into this inventory");
							
							event.setCancelled(true);
							
						}
						
						if (inventory.getTitle().equals("Modifier")) {
							
							if (event.getSlot() == 16) {
								
								event.setCancelled(true);
								
								ItemStack powerTool = inventory.getItem(10);
								
								if (powerTool != null) {
									
									if (powerTool.hasItemMeta()) {
										
										if (powerTool.getItemMeta().hasLore()) {
											
											if (powerTool.getItemMeta().getLore().contains(ChatColor.DARK_PURPLE + "Power Tool")) {
												
												List<Material> modifiers = new ArrayList<Material>();
												
												for (int i = 0; i < SQPowerTools.powerTools.size(); i ++) {
													
													if (SQPowerTools.powerTools.get(i).equals(SQPowerTools.getType(powerTool))) {
														
														for (int j = 0; j < SQPowerTools.powerTools.get(i).modifiers.size(); j ++) {
															
															modifiers.add(SQPowerTools.powerTools.get(i).modifiers.get(j).material);
															
														}
														
													}
													
												}
												
												if (inventory.getItem(13) != null) {
													
													if (modifiers.contains(inventory.getItem(13).getType())) {
														
														inventory.setItem(10, new ItemStack(Material.AIR));
														
														List<Material> modifierMaterials = new ArrayList<Material>();
														List<Integer> modifierAmounts = new ArrayList<Integer>();
														
														for (int i = 0; i < SQPowerTools.powerTools.size(); i ++) {
															
															if (SQPowerTools.powerTools.get(i).equals(SQPowerTools.getType(powerTool))) {
																
																for (int j = 0; j < SQPowerTools.powerTools.get(i).modifiers.size(); j ++) {
																	
																	modifierMaterials.add(SQPowerTools.powerTools.get(i).modifiers.get(j).material);
																	modifierAmounts.add(SQPowerTools.powerTools.get(i).modifiers.get(j).number);
																	
																}
																
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
									PowerToolType toolType = SQPowerTools.getType(clicked);
									
									List<String> recipe = toolType.recipe;
									List<Material> ingredients = toolType.ingredients;
									List<String> ingredientNames = toolType.ingredientNames;

									Inventory recipeInventory = Bukkit.createInventory(player, InventoryType.WORKBENCH, "Power Tool Recipe");
										
									for (int i = 0; i < (recipe.size() * 3); i ++) {
											
										if (i == 0 || i == 1 || i == 2) {
											
											if (recipe.get(0).toCharArray()[i] == ' ') {
												
												recipeInventory.setItem(i + 1, new ItemStack(Material.AIR));
												
											} else {
												
												for (int j = 0; j < ingredients.size(); j ++) {
													
													if (recipe.get(0).toCharArray()[i] == ingredientNames.get(j).toCharArray()[0]) {
														
														ItemStack ingredient = new ItemStack(ingredients.get(j));
														
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
													
													if (recipe.get(1).toCharArray()[i - 3] == ingredientNames.get(j).toCharArray()[0]) {
														
														ItemStack ingredient = new ItemStack(ingredients.get(j));
														
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
													
													if (recipe.get(2).toCharArray()[i - 6] == ingredientNames.get(j).toCharArray()[0]) {
														
														ItemStack ingredient = new ItemStack(ingredients.get(j));
														
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
										
										ItemStack result = (new PowerTool(SQPowerTools.getType(clicked))).getItem();
										
										ItemMeta itemMeta = result.getItemMeta();
										
										List<String> lore = itemMeta.getLore();
										
										lore.add(ChatColor.RED + "" + ChatColor.MAGIC + "Contraband");
										
										itemMeta.setLore(lore);
										
										result.setItemMeta(itemMeta);
										
										recipeInventory.setItem(0, result);
										
										player.openInventory(recipeInventory);
										
									}
									
								}
								
							/*} else if (inventory.getTitle().equals("Power Tool Mods")) {
								
								Inventory modifierInventory = Bukkit.createInventory(player, 54, "Power Tool Mod");
								
								modifierInventory.setItem(53, clicked);
								
								PowerToolType toolType = SQPowerTools.getType(clicked);
								
								ItemStack maxMods = new ItemStack(Material.PAPER);
								
								List<String> maxModsLore = new ArrayList<String>();
								
								maxModsLore.add("Max Total Mod Levels: " + Integer.toString(toolType.maxMods));
								maxModsLore.add(ChatColor.RED + "" + ChatColor.MAGIC + "Contraband");
								
								ItemMeta itemMeta = maxMods.getItemMeta();
								
								itemMeta.setLore(maxModsLore);
								
								itemMeta.setDisplayName("Info");
								
								maxMods.setItemMeta(itemMeta);
								
								modifierInventory.setItem(45, maxMods);
								
								for (int i = 0; i < toolType.modifiers.size(); i ++) {
									
									ItemStack mod = new ItemStack((toolType.modifiers.get(i).material));
									
									mod.addUnsafeEnchantments(toolType.modifiers.get(i).enchants);
									
									mod.setDurability(Short.parseShort(Integer.toString(toolType.modifiers.get(i).durability))); 
									
									ItemMeta modItemMeta = mod.getItemMeta();
									
									List<String> modLore = new ArrayList<String>();
									
									modLore.add(toolType.modifiers.get(i).name);
									modLore.add("Amount: " + toolType.modifiers.get(i).number);
									modLore.add("Levels: " + toolType.modifiers.get(i).levels);
									
									if (toolType.modifiers.get(i).energy > 0) {
										
										modLore.add(ChatColor.RED + "+ " + toolType.modifiers.get(i).energy + " Energy");
										
									} else if (toolType.modifiers.get(i).energy < 0) {
										
										modLore.add(ChatColor.RED + "- " + toolType.modifiers.get(i).energy + " Energy");
										
									}
									
									for (int j = 0; j < toolType.modifiers.get(i).attributes.size(); j ++) {
										
										String attributeName = "";
										
										float adjustment = toolType.modifiers.get(i).attributes.get(j).amount;
										
										if (toolType.modifiers.get(i).attributes.get(j).attribute.equals("generic.attackSpeed")) {
											
											attributeName = "Attack Speed";
											
										} else if (toolType.modifiers.get(i).attributes.get(j).attribute.equals("generic.attackDamage")) {
											
											attributeName = "Damage";
											
										} else if (toolType.modifiers.get(i).attributes.get(j).attribute.equals("generic.movementSpeed")) {
											
											attributeName = "Movement Speed";
											
										} else if (toolType.modifiers.get(i).attributes.get(j).attribute.equals("generic.armor")) {
											
											attributeName = "Armor";
											
										} else if (toolType.modifiers.get(i).attributes.get(j).attribute.equals("generic.knockbackResistance")) {
											
											attributeName = "Knockback Resistance";
											
										} else if (toolType.modifiers.get(i).attributes.get(j).attribute.equals("generic.luck")) {
											
											attributeName = "Luck";
											
										} else if (toolType.modifiers.get(i).attributes.get(j).attribute.equals("generic.maxHealth")) {
											
											attributeName = "Health";
											
										} else if (toolType.modifiers.get(i).attributes.get(j).attribute.equals("generic.armorToughness")) {
											
											attributeName = "Armor Toughness";
											
										} else {
											
											
											attributeName = toolType.modifiers.get(i).attributes.get(j).attribute;
										}
										
										if (toolType.modifiers.get(i).attributes.get(j).operation == 0) {
											
											if (adjustment > 0) {
												
												modLore .add(ChatColor.GOLD + "+ " + adjustment + " " + attributeName);
												
											} else if (adjustment < 0) {
												
												modLore .add(ChatColor.GOLD + "- " + Math.abs(adjustment) + " " + attributeName);
												
											}
											
										} else {
											
											if (adjustment > 0) {
												
												modLore .add(ChatColor.GOLD + "+ " + (adjustment * 100) + "% " + attributeName);
												
											} else if (adjustment < 0) {
												
												modLore .add(ChatColor.GOLD + "- " + Math.abs(adjustment * 100) + "% " + attributeName);
												
											}
											
										}
										
									}
									
									if (toolType.modifiers.get(i).effects.size() > 0) {
										
										modLore.add(ChatColor.DARK_PURPLE + "Potion Effects:");
										
										for (int j = 0; j < toolType.modifiers.get(i).effects.size(); j ++) {
											
											modLore.add(ChatColor.DARK_PURPLE + "  " + EffectUtils.getEffectName(toolType.modifiers.get(i).effects.get(j).effect) + ":");
											modLore.add(ChatColor.DARK_PURPLE + "    Level " + (toolType.modifiers.get(i).effects.get(j).level + 1));
											modLore.add(ChatColor.DARK_PURPLE + "    For " + toolType.modifiers.get(i).effects.get(j).duration + " seconds");
											modLore.add(ChatColor.DARK_PURPLE + "    Applies " + EffectUtils.getCaseName(toolType.modifiers.get(i).effects.get(j).effectCase));
											
										}
										
									}
									
									if (toolType.modifiers.get(i).cannotCombines.size() > 0) {
										
										modLore.add(ChatColor.BLUE + "Cannot Combine With:");
										
										for (int j = 0; j < toolType.modifiers.get(i).cannotCombines.size(); j ++) {
											
											modLore.add(ChatColor.BLUE + " - " + toolType.modifiers.get(i).cannotCombines.get(j));
											
										}
										
									}
									
									modLore.add(ChatColor.RED + "" + ChatColor.MAGIC + "Contraband");
									
									modItemMeta.setLore(modLore);
									
									mod.setItemMeta(modItemMeta);
									
									modifierInventory.addItem(mod);
									
								}
								
								player.openInventory(modifierInventory);*/
								
							} else if (inventory.getTitle().equals("Power Tools")) {
								
								ItemStack powerTool = (new PowerTool(SQPowerTools.powerTools.get(event.getSlot()))).getItem();
								
								player.getInventory().addItem(powerTool);
								
								player.closeInventory();
								
							} 
							
						} else if (event.getInventory().getType().equals(InventoryType.FURNACE)) {
							
						} else if (event.getSlotType().equals(SlotType.CONTAINER) || event.getSlotType().equals(SlotType.RESULT) || event.getSlotType().equals(SlotType.QUICKBAR) || event.getSlotType().equals(SlotType.ARMOR) || event.getSlotType().equals(SlotType.OUTSIDE)) {
								
						} else {
							
							event.setCancelled(true);
						
							if (event.getAction().equals(InventoryAction.MOVE_TO_OTHER_INVENTORY)) {
								
								event.setCancelled(false);
								
							}
							
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
								
								List<Modifier> modifierObjects = new ArrayList<Modifier>();
								List<Integer> modifierLevels = new ArrayList<Integer>();
								List<Integer> modifierAmounts = new ArrayList<Integer>();
								
								PowerToolType toolType = SQPowerTools.getType(powerTool);
								
								for (int i = 0; i < toolType.modifiers.size(); i ++) {
									
									modifiers.add(toolType.modifiers.get(i).material);
									datas.add(toolType.modifiers.get(i).durability);
									modifierObjects.add(toolType.modifiers.get(i));							
									modifierLevels.add(toolType.modifiers.get(i).levels);
									modifierAmounts.add(toolType.modifiers.get(i).number);
									
								}
								
								if (inventory.getItem(13) != null) {
									
									if (event.getSlot() != 13) {
										
										for (int i = 0; i < modifiers.size(); i ++) {
											
											if (modifiers.get(i).equals(inventory.getItem(13).getType()) && datas.get(i).equals((int) inventory.getItem(13).getDurability())) {
												
												if (modifierAmounts.get(i) <= inventory.getItem(13).getAmount()) {
													
													HashMap<Modifier,Integer> modifierMap = SQPowerTools.getModifiers(powerTool);
													
													List<String> errorLore = new ArrayList<String>();
													
													boolean error = false;
													
													if (modifierMap.containsKey(modifierObjects.get(i))) {
														
														if (modifierMap.get(modifierObjects.get(i)) < modifierLevels.get(i)) {
															
															modifierMap.put(modifierObjects.get(i), modifierMap.get(modifierObjects.get(i)) + 1);
															
														} else {  
															
															error = true;
															
															errorLore.add(ChatColor.RED + "The max level for this modifier on the");
															errorLore.add(ChatColor.RED + "power tool has been reached.");
															errorLore.add(ChatColor.RED + "" + ChatColor.MAGIC + "Contraband");
															
														}
														
													} else {
														
														modifierMap.put(modifierObjects.get(i), 1);
														
													}
													
													int totalLevels = 0;
													
													List<Integer> currentModifierLevels = new ArrayList<Integer>();
													
													List<Modifier> currentModifierNames = new ArrayList<Modifier>();
													
													currentModifierNames.addAll(modifierMap.keySet());
													
													for (int j = 0; j < modifierMap.size(); j ++) {
														
														currentModifierLevels.add(modifierMap.get(currentModifierNames.get(j)));
														
														totalLevels = totalLevels + modifierMap.get(currentModifierNames.get(j));
														
													}
													
													if (totalLevels > toolType.maxMods) {
														
														error = true;
														
														errorLore.add(ChatColor.RED + "The max levels of all modifiers");
														errorLore.add(ChatColor.RED + "on the power tool has been reached.");
														errorLore.add(ChatColor.RED + "" + ChatColor.MAGIC + "Contraband");
														
													}
													
													List<Modifier> oldModifierObjectss = new ArrayList<Modifier>();
													
													oldModifierObjectss.addAll(SQPowerTools.getModifiers(powerTool).keySet());
													
													for (int j = 0; j < oldModifierObjectss.size(); j ++) {
														
														for (int k = 0; k < toolType.modifiers.size(); k ++) {
															
															if (oldModifierObjectss.get(j).equals(toolType.modifiers.get(k))) {
																
																for (String cannotCombine : toolType.modifiers.get(k).cannotCombines) {
																	
																	if (cannotCombine.equals(modifierObjects.get(i).name)) {
																		
																		error = true;
																		
																		errorLore.add(ChatColor.RED + "This modifier conflicts with a");
																		errorLore.add(ChatColor.RED + "modifier that is on this power tool.");
																		errorLore.add(ChatColor.RED + "" + ChatColor.MAGIC + "Contraband");
																		
																	}
																	
																}
																
															}
															
														}
														
													}
													
													if (!error) {
														
														List<String> lore = powerTool.getItemMeta().getLore();
														
														PowerTool powerToolObject = new PowerTool(toolType, modifierMap);
														powerToolObject.setEnergy(SQPowerTools.getEnergy(powerTool));
														
														ItemStack resultPowerTool = powerToolObject.getFakeItem();
														
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
													
													HashMap<Modifier,Integer> modifierMap = SQPowerTools.getModifiers(powerTool);
													
													List<String> errorLore = new ArrayList<String>();
													
													boolean error = false;
													
													if (modifierMap.containsKey(modifierObjects.get(i))) {
														
														if (modifierMap.get(modifierObjects.get(i)) < modifierLevels.get(i)) {
															
															modifierMap.put(modifierObjects.get(i), modifierMap.get(modifierObjects.get(i)) + 1);
															
														} else {
															
															error = true;
															
															errorLore.add(ChatColor.RED + "The max level for this modifier");
															errorLore.add(ChatColor.RED + "on the power tool has been reached.");
															errorLore.add(ChatColor.RED + "" + ChatColor.MAGIC + "Contraband");
															
														}
														
													} else {
														
														modifierMap.put(modifierObjects.get(i), 1);
														
													}
													
													int totalLevels = 0;
													
													List<Integer> currentModifierLevels = new ArrayList<Integer>();
													
													List<Modifier> currentModifierObjects = new ArrayList<Modifier>();
													
													currentModifierObjects.addAll(modifierMap.keySet());
													
													for (int j = 0; j < modifierMap.size(); j ++) {
														
														currentModifierLevels.add(modifierMap.get(currentModifierObjects.get(j)));
														
														totalLevels = totalLevels + modifierMap.get(currentModifierObjects.get(j));
														
													}
													
													if (totalLevels > toolType.maxMods) {
														
														error = true;
														
														errorLore.add(ChatColor.RED + "The max levels of all modifiers");
														errorLore.add(ChatColor.RED + "on the power tool has been reached.");
														errorLore.add(ChatColor.RED + "" + ChatColor.MAGIC + "Contraband");
														
													}
													
													List<Modifier> oldModifierObjects = new ArrayList<Modifier>();
													
													oldModifierObjects.addAll(SQPowerTools.getModifiers(powerTool).keySet());
													
													/*Modifier newModifier = null;
													
													for (Modifier modifier : toolType.modifiers) {
														
														if (modifier.material.equals(modifiers.get(i)) && modifier.durability == datas.get(i)) {
															
															newModifier = modifier;
															
														}
														
													}
													
													for (Modifier modifier : oldModifierObjects) {
														
														for (String cannotCombine : modifier.cannotCombines) {
															
															if (newModifier.name.equals(cannotCombine)) {
																
																error = true;
																
																errorLore.add(ChatColor.RED + "This modifier conflicts with a");
																errorLore.add(ChatColor.RED + "modifier that is on this power tool.");
																errorLore.add(ChatColor.RED + "" + ChatColor.MAGIC + "Contraband");
																
															}
															
														}
														
													}*/
													
													for (int j = 0; j < oldModifierObjects.size(); j ++) {
														
														for (int k = 0; k < toolType.modifiers.size(); k ++) {
															
															if (oldModifierObjects.get(j).equals(toolType.modifiers.get(k))) {
																
																for (String cannotCombine : toolType.modifiers.get(k).cannotCombines) {
																	
																	if (cannotCombine.equals(oldModifierObjects.get(j).name)) {
																		
																		error = true;
																		
																		errorLore.add(ChatColor.RED + "This modifier conflicts with a");
																		errorLore.add(ChatColor.RED + "modifier that is on this power tool.");
																		errorLore.add(ChatColor.RED + "" + ChatColor.MAGIC + "Contraband");
																		
																	}
																	
																}
																
															}
															
														}
														
													}
													
													if (!error) {
														
														List<String> lore = powerTool.getItemMeta().getLore();
														
														PowerTool powerToolObject = new PowerTool(toolType, modifierMap);
														powerToolObject.setEnergy(SQPowerTools.getEnergy(powerTool));
														
														ItemStack resultPowerTool = powerToolObject.getFakeItem();
														
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
				
				if (sign.getLine(0).equalsIgnoreCase("[modifier]")) {
					
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
