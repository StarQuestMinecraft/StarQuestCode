package com.ginger_walnut.sqpowertools.gui;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import com.ginger_walnut.sqpowertools.SQPowerTools;
import com.ginger_walnut.sqpowertools.objects.BlasterStats;
import com.ginger_walnut.sqpowertools.objects.Modifier;
import com.ginger_walnut.sqpowertools.objects.PowerToolType;
import com.ginger_walnut.sqpowertools.utils.EffectUtils;

import net.md_5.bungee.api.ChatColor;

public class ModifiersGUI extends GUI{

	public ModifiersGUI() {
		
	}
	
	@Override
	@Deprecated
	public void open(Player player) {
		
		System.out.print("Error: use open(player, itemStack)");
		
	}
	
	public void open(Player player, ItemStack clicked) {
		
		owner = player;
		
		Inventory gui = Bukkit.createInventory(owner, 54, "Power Tool Mod");	
		
		gui.setItem(53, clicked);
		
		PowerToolType toolType = SQPowerTools.getType(clicked);
		
		ItemStack maxMods = new ItemStack(Material.PAPER);
		
		List<String> maxModsLore = new ArrayList<String>();
		
		maxModsLore.add("Max Total Mod Levels: " + Integer.toString(toolType.maxMods));
		maxModsLore.add(ChatColor.RED + "" + ChatColor.MAGIC + "Contraband");
		
		ItemMeta itemMeta = maxMods.getItemMeta();
		
		itemMeta.setLore(maxModsLore);
		
		itemMeta.setDisplayName("Info");
		
		maxMods.setItemMeta(itemMeta);
		
		gui.setItem(45, maxMods);
		
		for (Modifier modifier : toolType.modifiers) {
			
			ItemStack mod = new ItemStack((modifier.material));
			
			mod.addUnsafeEnchantments(modifier.enchants);
			
			mod.setDurability(Short.parseShort(Integer.toString(modifier.durability))); 
			
			ItemMeta modItemMeta = mod.getItemMeta();
			
			List<String> modLore = new ArrayList<String>();
			
			modLore.add(modifier.name);
			modLore.add("Amount: " + modifier.number);
			modLore.add("Levels: " + modifier.levels);
			
			if (modifier.energy > 0) {
				
				modLore.add(ChatColor.RED + "+ " + modifier.energy + " Energy");
				
			} else if (modifier.energy < 0) {
				
				modLore.add(ChatColor.RED + "- " + modifier.energy + " Energy");
				
			}
			
			if (modifier.energyPerUse > 0) {
				
				modLore.add(ChatColor.RED + "+ " + modifier.energyPerUse + " Energy Per Use");
				
			} else if (modifier.energyPerUse < 0) {
				
				modLore.add(ChatColor.RED + "- " + modifier.energyPerUse + " Energy Per Use");
				
			}
			
			for (int j = 0; j < modifier.attributes.size(); j ++) {
				
				String attributeName = "";
				
				float adjustment = modifier.attributes.get(j).amount;
				
				if (modifier.attributes.get(j).attribute.equals("generic.attackSpeed")) {
					
					attributeName = "Attack Speed";
					
				} else if (modifier.attributes.get(j).attribute.equals("generic.attackDamage")) {
					
					attributeName = "Damage";
					
				} else if (modifier.attributes.get(j).attribute.equals("generic.movementSpeed")) {
					
					attributeName = "Movement Speed";
					
				} else if (modifier.attributes.get(j).attribute.equals("generic.armor")) {
					
					attributeName = "Armor";
					
				} else if (modifier.attributes.get(j).attribute.equals("generic.knockbackResistance")) {
					
					attributeName = "Knockback Resistance";
					
				} else if (modifier.attributes.get(j).attribute.equals("generic.luck")) {
					
					attributeName = "Luck";
					
				} else if (modifier.attributes.get(j).attribute.equals("generic.maxHealth")) {
					
					attributeName = "Health";
					
				} else if (modifier.attributes.get(j).attribute.equals("generic.armorToughness")) {
					
					attributeName = "Armor Toughness";
					
				} else {
					
					
					attributeName = modifier.attributes.get(j).attribute;
				}
				
				if (modifier.attributes.get(j).operation == 0) {
					
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
			
			if (modifier.blasterStats != null) {
				
				BlasterStats blasterStats = modifier.blasterStats;
				
				if (blasterStats.damage != 0) {
					
					if (blasterStats.damage > 0) {
						
						modLore.add(ChatColor.GOLD + "+ " + blasterStats.damage + " Ranged Damage");
						
						
					} else if (blasterStats.damage < 0) {
						
						modLore.add(ChatColor.GOLD + "- " + Math.abs(blasterStats.damage) + " Ranged Damage");
						
					}
					
				}
				
				if (blasterStats.cooldown != 0) {
				
					if (Double.toString(Double.parseDouble(Integer.toString(blasterStats.cooldown)) / 20.0).endsWith(".0")) {
						
						if (blasterStats.cooldown / 20.0 > 0) {
								
							modLore.add(ChatColor.GOLD + "+ " + (blasterStats.cooldown / 20.0) + " Ranged Cooldown");
								
								
						} else if (blasterStats.cooldown / 20.0 < 0) {
								
							modLore.add(ChatColor.GOLD + "- " + Math.abs(blasterStats.cooldown / 20.0) + " Ranged Cooldown");
								
						}
						
					} else {
						
						if (Double.parseDouble(Integer.toString(blasterStats.cooldown)) / 20.0 > 0) {
							
							modLore.add(ChatColor.GOLD + "+ " + (Double.parseDouble(Integer.toString(blasterStats.cooldown)) / 20.0) + " Ranged Cooldown");
								
								
						} else if (Double.parseDouble(Integer.toString(blasterStats.cooldown)) / 20.0 < 0) {
								
							modLore.add(ChatColor.GOLD + "- " + Math.abs(Double.parseDouble(Integer.toString(blasterStats.cooldown)) / 20.0) + " Ranged Cooldown");
								
						}
						
					}
				
				}

				if (blasterStats.scope != 0) {
					
					if (blasterStats.scope > 0) {
						
						modLore.add(ChatColor.GOLD + "+ " + blasterStats.scope + " Ranged Scope");
						
						
					} else if (blasterStats.scope < 0) {
						
						modLore.add(ChatColor.GOLD + "- " + Math.abs(blasterStats.scope) + " Ranged Scope");
						
					}
					
				}
				
				if (blasterStats.ammo != 0) {
					
					if (blasterStats.ammo > 0) {
						
						modLore.add(ChatColor.GOLD + "+ " + blasterStats.ammo + " Max Ammo");
						
						
					} else if (blasterStats.ammo < 0) {
						
						modLore.add(ChatColor.GOLD + "- " + Math.abs(blasterStats.ammo) + " Max Ammo");
						
					}
					
				}	
				
				if (blasterStats.reload != 0) {
					
					if (Double.toString(Double.parseDouble(Integer.toString(blasterStats.reload)) / 20.0).endsWith(".0")) {
						
						if (blasterStats.reload / 20.0 > 0) {
								
							modLore.add(ChatColor.GOLD + "+ " + (blasterStats.reload / 20.0) + " Reload Time");
								
								
						} else if (blasterStats.reload / 20.0 < 0) {
								
							modLore.add(ChatColor.GOLD + "- " + Math.abs(blasterStats.reload / 20.0) + " Reload Time");
								
						}
						
					} else {
						
						if (Double.parseDouble(Integer.toString(blasterStats.reload)) / 20.0 > 0) {
							
							modLore.add(ChatColor.GOLD + "+ " + (Double.parseDouble(Integer.toString(blasterStats.reload)) / 20.0) + " Reload Time");
								
								
						} else if (Double.parseDouble(Integer.toString(blasterStats.reload)) / 20.0 < 0) {
								
							modLore.add(ChatColor.GOLD + "- " + Math.abs(Double.parseDouble(Integer.toString(blasterStats.reload)) / 20.0) + " Reload Time");
								
						}
						
					}
				
				}
				
				if (blasterStats.ammoType != null) {
						
					modLore.add(ChatColor.GOLD + "Ammo Type: " + blasterStats.ammoType.getName());
					
				}
				
				if (blasterStats.projectileType != null) {
					
					modLore.add(ChatColor.GOLD + "Projectile Type: " + blasterStats.projectileType.getName());
					
				}
				
				if (blasterStats.explosionSize != 0) {
					
					if (blasterStats.explosionSize > 0) {
						
						modLore.add(ChatColor.GOLD + "+ " + blasterStats.explosionSize + " Explosion Size");
						
						
					} else if (blasterStats.explosionSize < 0) {
						
						modLore.add(ChatColor.GOLD + "- " + Math.abs(blasterStats.explosionSize) + " Explosion Size");
						
					}
					
				}
				
				if (blasterStats.shotCount != 0) {
					
					if (blasterStats.shotCount > 0) {
						
						modLore.add(ChatColor.GOLD + "+ " + blasterStats.shotCount + " Shot Count");
						
						
					} else if (blasterStats.shotCount < 0) {
						
						modLore.add(ChatColor.GOLD + "- " + Math.abs(blasterStats.shotCount) + "Shot Count");
						
					}
					
				}	
				
			}
			
			if (modifier.effects.size() > 0) {
				
				modLore.add(ChatColor.DARK_PURPLE + "Potion Effects:");
				
				for (int j = 0; j < modifier.effects.size(); j ++) {
					
					modLore.add(ChatColor.DARK_PURPLE + "  " + EffectUtils.getEffectName(modifier.effects.get(j).effect) + ":");
					modLore.add(ChatColor.DARK_PURPLE + "    Level " + (modifier.effects.get(j).level + 1));
					modLore.add(ChatColor.DARK_PURPLE + "    For " + modifier.effects.get(j).duration + " seconds");
					modLore.add(ChatColor.DARK_PURPLE + "    Applies " + EffectUtils.getCaseName(modifier.effects.get(j).effectCase));
					
				}
				
			}
			
			if (modifier.cannotCombines.size() > 0) {
				
				modLore.add(ChatColor.BLUE + "Cannot Combine With:");
				
				for (int j = 0; j < modifier.cannotCombines.size(); j ++) {
					
					modLore.add(ChatColor.BLUE + " - " + modifier.cannotCombines.get(j));
					
				}
				
			}
			
			modLore.add(ChatColor.RED + "" + ChatColor.MAGIC + "Contraband");
			
			modItemMeta.setLore(modLore);
			
			mod.setItemMeta(modItemMeta);
			
			gui.addItem(mod);
			
		}
		
		owner.openInventory(gui);
		
		if (SQPowerTools.currentGui.containsKey(owner)) {
			
			SQPowerTools.currentGui.remove(owner);
			SQPowerTools.currentGui.put(owner,  this);
			
		} else {
			
			SQPowerTools.currentGui.put(owner,  this);
			
		}
		
	}
	
	@Override
	public void click(InventoryClickEvent event) {
		
		if (event.getClickedInventory().getTitle().equals("Power Tool Mod")) {
			
			event.setCancelled(true);
			
		}
		
	}
	
}
