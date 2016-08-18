package com.ginger_walnut.sqpowertools.objects;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.bukkit.ChatColor;
import org.bukkit.craftbukkit.v1_10_R1.inventory.CraftItemStack;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import com.ginger_walnut.sqpowertools.SQPowerTools;
import com.ginger_walnut.sqpowertools.enums.ProjectileType;
import com.ginger_walnut.sqpowertools.utils.AttributeUtils;
import com.ginger_walnut.sqpowertools.utils.EffectUtils;

import net.minecraft.server.v1_10_R1.NBTTagCompound;
import net.minecraft.server.v1_10_R1.NBTTagFloat;
import net.minecraft.server.v1_10_R1.NBTTagInt;
import net.minecraft.server.v1_10_R1.NBTTagList;
import net.minecraft.server.v1_10_R1.NBTTagString;

public class PowerTool {

	private ItemStack item;
	private PowerToolType type;
	
	private Map<Modifier, Integer> modifierMap = new HashMap<Modifier, Integer>();
	private List<Attribute> attributes = new ArrayList<Attribute>();
	
	private int energy;
	
	public PowerTool(ItemStack item) {
		
		this.type = SQPowerTools.getType(item);
		energy = SQPowerTools.getEnergy(item);

		setModifiers(SQPowerTools.getModifiers(item));
		
		List<Modifier> modifiers = new ArrayList<Modifier>();
		modifiers.addAll(modifierMap.keySet());
		
		List<Attribute> attributes = new ArrayList<Attribute>();
		
		for (Attribute attribute : type.attributes) {
			
			attributes.add(attribute.clone());
			
		}
		
		for (Modifier modifier : modifiers) {
			
			for (Attribute modifierAttribute : modifier.attributes) {
				
				boolean contains = false;
				
				for (Attribute attribute : attributes) {
					
					if (modifierAttribute.equalsExceptAmount(attribute)) {
						
						attribute.amount = attribute.amount + (modifierAttribute.amount * modifierMap.get(modifier));
						
						contains = true;
					}
					
				}
				
				if (!contains) {
					
					float oldAmount = modifierAttribute.amount;
					
					modifierAttribute.amount = modifierAttribute.amount * modifierMap.get(modifier);
					
					attributes.add(modifierAttribute.clone());
					
					modifierAttribute.amount = oldAmount;
					
				}
				
			}
			
		}	
		
		this.attributes = attributes;
		
		this.item = new ItemStack(type.material);
		
		ItemMeta itemMeta = this.item.getItemMeta();	
		itemMeta.setDisplayName(item.getItemMeta().getDisplayName());	
		this.item.setItemMeta(itemMeta);
		
		List<Enchantment> oldEnchants = new ArrayList<Enchantment>();
		oldEnchants.addAll(this.item.getEnchantments().keySet());
		
		for (Enchantment enchant : oldEnchants) {
			
			this.item.removeEnchantment(enchant);
			
		}
		
		this.item.setDurability(type.durability); 
		
		for (Modifier modifier : modifiers) {
			
			Map<Enchantment, Integer> enchants = new HashMap<Enchantment, Integer>();
			enchants.putAll(modifier.enchants);
			
			for (Enchantment enchant : enchants.keySet()) {
				
				enchants.replace(enchant, enchants.get(enchant) * modifierMap.get(modifier));
				
			}
			
			for (Enchantment enchant : type.enchants.keySet()) {
				
				if (enchants.containsKey(enchant)) {
					
					enchants.replace(enchant, enchants.get(enchant) + type.enchants.get(enchant));
					
				}
				
			}
			
			this.item.addUnsafeEnchantments(enchants);
			
		}
		
		for (Enchantment enchant : type.enchants.keySet()) {
			
			if (!this.item.getEnchantments().containsKey(enchant)) {
				
				this.item.addUnsafeEnchantment(enchant, type.enchants.get(enchant));
				
			}
			
		}
		
		addLore();
		addAttributes();

	}
	
	public PowerTool(PowerToolType type) {
		
		energy = type.energy;
		this.type = type;
		
		List<Modifier> modifiers = new ArrayList<Modifier>();
		modifiers.addAll(modifierMap.keySet());
		
		List<Attribute> attributes = new ArrayList<Attribute>();
		
		for (Attribute attribute : type.attributes) {
			
			attributes.add(attribute.clone());
			
		}		
		
		for (Modifier modifier : modifiers) {
			
			for (Attribute modifierAttribute : modifier.attributes) {
				
				boolean contains = false;
				
				for (Attribute attribute : attributes) {
					
					if (modifierAttribute.equalsExceptAmount(attribute)) {
						
						attribute.amount = attribute.amount + (modifierAttribute.amount * modifierMap.get(modifier));
						
						contains = true;
						
					}
					
				}
				
				if (!contains) {
					
					float oldAmount = modifierAttribute.amount;
					
					modifierAttribute.amount = modifierAttribute.amount * modifierMap.get(modifier);
					
					attributes.add(modifierAttribute.clone());
					
					modifierAttribute.amount = oldAmount;
					
				}
				
			}
			
		}	
		
		this.attributes = attributes;
		
		item = new ItemStack(type.material);
		
		ItemMeta itemMeta = item.getItemMeta();	
		itemMeta.setDisplayName(type.name);	
		item.setItemMeta(itemMeta);
		
		List<Enchantment> oldEnchants = new ArrayList<Enchantment>();
		oldEnchants.addAll(item.getEnchantments().keySet());
		
		for (Enchantment enchant : oldEnchants) {
			
			this.item.removeEnchantment(enchant);
			
		}
		
		item.setDurability(type.durability); 
				
		for (Modifier modifier : modifiers) {
			
			Map<Enchantment, Integer> enchants = new HashMap<Enchantment, Integer>();
			enchants.putAll(modifier.enchants);
			
			for (Enchantment enchant : enchants.keySet()) {
				
				enchants.replace(enchant, enchants.get(enchant) * modifierMap.get(modifier));
				
			}
			
			for (Enchantment enchant : type.enchants.keySet()) {
				
				if (enchants.containsKey(enchant)) {
					
					enchants.replace(enchant, enchants.get(enchant) + type.enchants.get(enchant));
					
				}
				
			}
			
			item.addUnsafeEnchantments(enchants);
			
		}
		
		for (Enchantment enchant : type.enchants.keySet()) {
			
			if (!item.getEnchantments().containsKey(enchant)) {
				
				item.addUnsafeEnchantment(enchant, type.enchants.get(enchant));
				
			}
			
		}
		
		addLore();
		addAttributes();
		
	}
	
	public PowerTool(PowerToolType type, Map<Modifier, Integer> modifierMap) {
		
		energy = type.energy;
		this.type = type;
		
		setModifiers(modifierMap);
		
		List<Modifier> modifiers = new ArrayList<Modifier>();
		modifiers.addAll(modifierMap.keySet());
		
		List<Attribute> attributes = new ArrayList<Attribute>();

		for (Attribute attribute : type.attributes) {
			
			attributes.add(attribute.clone());
			
		}
		
		for (Modifier modifier : modifiers) {
			
			for (Attribute modifierAttribute : modifier.attributes) {
				
				boolean contains = false;
				
				for (Attribute attribute : attributes) {
					
					if (modifierAttribute.equalsExceptAmount(attribute)) {
											
						attribute.amount = attribute.amount + (modifierAttribute.amount * modifierMap.get(modifier));
						
						contains = true;
						
					}
					
				}
				
				if (!contains) {
					
					float oldAmount = modifierAttribute.amount;
					
					modifierAttribute.amount = modifierAttribute.amount * modifierMap.get(modifier);
					
					attributes.add(modifierAttribute.clone());
					
					modifierAttribute.amount = oldAmount;
										
				}
				
			}
			
		}	
		
		this.attributes = attributes;
		
		item = new ItemStack(type.material);
		
		ItemMeta itemMeta = item.getItemMeta();	
		itemMeta.setDisplayName(type.name);	
		item.setItemMeta(itemMeta);
		
		List<Enchantment> oldEnchants = new ArrayList<Enchantment>();
		oldEnchants.addAll(item.getEnchantments().keySet());
		
		for (Enchantment enchant : oldEnchants) {
			
			item.removeEnchantment(enchant);
			
		}
		
		item.setDurability(type.durability); 
		
		for (Modifier modifier : modifiers) {
			
			Map<Enchantment, Integer> enchants = new HashMap<Enchantment, Integer>();
			enchants.putAll(modifier.enchants);
			
			for (Enchantment enchant : enchants.keySet()) {
				
				enchants.replace(enchant, enchants.get(enchant) * modifierMap.get(modifier));
				
			}
			
			for (Enchantment enchant : type.enchants.keySet()) {
				
				if (enchants.containsKey(enchant)) {
					
					enchants.replace(enchant, enchants.get(enchant) + type.enchants.get(enchant));
					
				}
				
			}
			
			item.addUnsafeEnchantments(enchants);
			
		}
		
		for (Enchantment enchant : type.enchants.keySet()) {
			
			if (!item.getEnchantments().containsKey(enchant)) {
				
				item.addUnsafeEnchantment(enchant, type.enchants.get(enchant));
				
			}
			
		}
		
		addLore();
		addAttributes();

	}
	
	public static boolean isPowerTool(ItemStack item) {
		
		boolean isPowerTool = false;
		
		if (item != null) {
			
			if (item.hasItemMeta()) {
				
				if (item.getItemMeta().hasLore()) {
					
					if (item.getItemMeta().getLore().contains(ChatColor.DARK_PURPLE + "Power Tool")) {
						
						for (PowerToolType type : SQPowerTools.powerTools) {
							
							if (item.getItemMeta().getLore().contains(ChatColor.DARK_PURPLE + type.name)) {
							
								isPowerTool = true;
								
							}
								
						}
						
					}
					
				}
				
			}
			
		}
		
		return isPowerTool;
		
	}
	
	private void addLore() {
		
		int maxEnergy = type.energy;
		
		if (item.hasItemMeta()) {
			
			if (item.getItemMeta().hasLore()) {
				
				maxEnergy = SQPowerTools.getMaxEnergy(type, item);
				
			}
			
		}
		
		ItemMeta itemMeta = item.getItemMeta();
		
		itemMeta.setLore(new ArrayList<String>());
		
		List<String> lore = new ArrayList<String>();
		
		lore.add(ChatColor.DARK_PURPLE + "Power Tool");
		lore.add(ChatColor.DARK_PURPLE + type.name);
		
		List<Modifier> modifiers = new ArrayList<Modifier>();
		modifiers.addAll(modifierMap.keySet());
		
		for (String line : type.extraLore) {
			
			lore.add(line);
			
		}
		
		lore.add(ChatColor.RED + "Energy: " + SQPowerTools.formatEnergy(energy) + "/" + SQPowerTools.formatEnergy(maxEnergy));
		lore.add(ChatColor.RED + "Energy Per Use: " + SQPowerTools.formatEnergy(getEnergyPerUse()));
		
		if (modifiers.size() > 0) {
			
			lore.add(ChatColor.BLUE + "----------");
			lore.add(ChatColor.GOLD + "Mods");
			
			for (Modifier modifier : modifiers) {
				
				lore.add(ChatColor.GOLD + modifier.name + " Level " + modifierMap.get(modifier));
				
			}
			
		}
		
		lore.add(ChatColor.BLUE + "----------");
		
		for (Attribute attribute : attributes) {
			
			String attributeName = AttributeUtils.getDisplayName(attribute);
			
			float adjustment = AttributeUtils.getAdjustment(attribute);
			
			if (attribute.operation == 0) {
				
				lore.add(ChatColor.GOLD + attributeName + ": " + String.format("%.1f", attribute.amount + adjustment));
				
			} else {
				
				if (attribute.amount + adjustment >= 0) {
					
					lore.add(ChatColor.GOLD + attributeName + ": +" + String.format("%.1f", ((attribute.amount + adjustment) * 100)) + "%");
					
				} else {
					
					lore.add(ChatColor.GOLD + attributeName + ": " + String.format("%.1f", ((attribute.amount + adjustment) * 100)) + "%");
					
				}

			}
			
		}
		
		if (type.blasterStats != null) {
			
			BlasterStats blasterStats = getTotalBlasterStats();
			
			lore.add(ChatColor.GOLD + "Ranged Damage: " + blasterStats.damage);
			
			if (Double.toString(Double.parseDouble(Integer.toString(blasterStats.cooldown)) / 20.0).endsWith(".0")) {
				
				lore.add(ChatColor.GOLD + "Ranged Cooldown: " + (blasterStats.cooldown / 20.0) + " seconds");
				
			} else {
				
				lore.add(ChatColor.GOLD + "Ranged Cooldown: " + (Double.parseDouble(Integer.toString(blasterStats.cooldown)) / 20.0) + " seconds");
				
			}

			lore.add(ChatColor.GOLD + "Ranged Scope: " + (blasterStats.scope + 1));
			
			if (blasterStats.ammo > 0) {
				
				lore.add(ChatColor.GOLD + "Max Ammo: " + blasterStats.ammo);
				
				if (Double.toString(Double.parseDouble(Integer.toString(blasterStats.reload)) / 20.0).endsWith(".0")) {
					
					lore.add(ChatColor.GOLD + "Reload Time: " + (blasterStats.reload / 20.0) + " seconds");
					
				} else {
					
					lore.add(ChatColor.GOLD + "Reload Time: " + (Double.parseDouble(Integer.toString(blasterStats.reload)) / 20.0) + " seconds");
					
				}
				
			}
			
			lore.add(ChatColor.GOLD + "Ranged Ammo: " + blasterStats.ammoType.getName());
			lore.add(ChatColor.GOLD + "Ranged Projectile: " + blasterStats.projectileType.getName());
			
			if (blasterStats.projectileType.equals(ProjectileType.FIREBALL)) {
				
				lore.add(ChatColor.GOLD + "Explosion Size: " + blasterStats.explosionSize);
				
			}
			
			if (blasterStats.projectileType.equals(ProjectileType.SHOTGUN)) {
				
				lore.add(ChatColor.GOLD + "Shot Count: " + blasterStats.shotCount);
				
			}
			
		}
		
		if (type.effects.size() > 0) {
			
			lore.add(ChatColor.DARK_PURPLE + "Potion Effects:");
			
			for (Effect effect : type.effects) {
				
				lore.add(ChatColor.DARK_PURPLE + "  " + EffectUtils.getEffectName(effect.effect) + ":");
				lore.add(ChatColor.DARK_PURPLE + "    Level " + (effect.level + 1));
				lore.add(ChatColor.DARK_PURPLE + "    For " + effect.duration + " seconds");
				lore.add(ChatColor.DARK_PURPLE + "    Applies " + EffectUtils.getCaseName(effect.effectCase));
				
			}
			
		}
		
		itemMeta.setLore(lore);
		
		item.setItemMeta(itemMeta);
		
	}
	
	private void addAttributes(){

		net.minecraft.server.v1_10_R1.ItemStack nmsStack = CraftItemStack.asNMSCopy(item);
	         
		HashMap<String, Integer> attributeUUIDs = new HashMap<String, Integer>();
		
	    NBTTagCompound compound = nmsStack.getTag();
	         
	    if (compound == null) {
	        	 
	    	compound = new NBTTagCompound();
	        nmsStack.setTag(compound);
	        compound = nmsStack.getTag();
	             
	    }
	         
	    NBTTagList modifiers = new NBTTagList();

	    for (Attribute attitrbute : attributes) {
	    	
		    int uuid = attitrbute.uuid + 1;
	    	
		    NBTTagCompound attribute = new NBTTagCompound();

		    if (attributeUUIDs.containsKey(attitrbute.attribute)) {
		    	
		    	int currentUUID = (int) attributeUUIDs.get(attitrbute.attribute);
		    	
		    	attributeUUIDs.remove(attitrbute.attribute);
		    	
		    	attributeUUIDs.put(attitrbute.attribute, currentUUID + 1);
		    	
		    	uuid = currentUUID + 1;
		    	
		    } else {
		    	
		    	attributeUUIDs.put(attitrbute.attribute, 1);
		    	
		    }
		    
	    	attribute.set("Slot", new NBTTagString(attitrbute.slot));
		    attribute.set("AttributeName", new NBTTagString(attitrbute.attribute));
		    attribute.set("Name", new NBTTagString(attitrbute.attribute));
		    attribute.set("Amount", new NBTTagFloat(attitrbute.amount));
		    attribute.set("Operation", new NBTTagInt(attitrbute.operation));
		    attribute.set("UUIDLeast", new NBTTagInt(uuid));
		    attribute.set("UUIDMost", new NBTTagInt(uuid));
	    	
		    modifiers.add(attribute);
		    
	    }

	    compound.set("HideFlags", new NBTTagInt(6));
	    compound.set("Unbreakable", new NBTTagInt(1));
	    compound.set("AttributeModifiers", modifiers);
	    
	    nmsStack.setTag(compound);
	    
	    item = CraftItemStack.asBukkitCopy(nmsStack);
	       
	}
	
	public void setModifiers(Map<Modifier, Integer> modifierMap) {
	
		this.modifierMap = modifierMap;
		
		List<Modifier> modifiers = new ArrayList<Modifier>();
		modifiers.addAll(modifierMap.keySet());
		
		List<Attribute> attributes = new ArrayList<Attribute>();
		
		for (Attribute attribute : type.attributes) {
			
			attributes.add(attribute.clone());
			
		}
		
		for (Modifier modifier : modifiers) {
			
			for (Attribute modifierAttribute : modifier.attributes) {
				
				boolean contains = false;
				
				for (Attribute attribute : attributes) {
					
					if (modifierAttribute.equalsExceptAmount(attribute)) {
						
						attribute.amount = attribute.amount + (modifierAttribute.amount * modifierMap.get(modifier));
						
						contains = true;
						
					}
					
				}
				
				if (!contains) {
					
					float oldAmount = modifierAttribute.amount;
					
					modifierAttribute.amount = modifierAttribute.amount * modifierMap.get(modifier);
					
					attributes.add(modifierAttribute.clone());
					
					modifierAttribute.amount = oldAmount;
					
				}
				
			}
			
		}	
		
		this.attributes = attributes;
		
		item = new ItemStack(type.material);
		
		ItemMeta itemMeta = item.getItemMeta();	
		itemMeta.setDisplayName(type.name);	
		item.setItemMeta(itemMeta);
		
		List<Enchantment> oldEnchants = new ArrayList<Enchantment>();
		oldEnchants.addAll(item.getEnchantments().keySet());
		
		for (Enchantment enchant : oldEnchants) {
			
			item.removeEnchantment(enchant);
			
		}
		
		for (Modifier modifier : modifiers) {
			
			Map<Enchantment, Integer> enchants = new HashMap<Enchantment, Integer>();
			enchants.putAll(modifier.enchants);
			
			for (Enchantment enchant : enchants.keySet()) {
				
				enchants.replace(enchant, enchants.get(enchant) * modifierMap.get(modifier));
				
			}
			
			for (Enchantment enchant : type.enchants.keySet()) {
				
				if (enchants.containsKey(enchant)) {
					
					enchants.replace(enchant, enchants.get(enchant) + type.enchants.get(enchant));
					
				}

			}
			
			item.addUnsafeEnchantments(enchants);
			
		}
		
		for (Enchantment enchant : type.enchants.keySet()) {
			
			if (!item.getEnchantments().containsKey(enchant)) {
				
				item.addUnsafeEnchantment(enchant, type.enchants.get(enchant));
				
			}
			
		}
		
		item.setDurability(type.durability); 
				
		addLore();
		addAttributes();
		
	}
	
	public ItemStack getItem() {
		
		addLore();
		
		return item;
		
	}
	
	public int getEnergy() {
		
		return energy;
		
	}
	
	public void setEnergy(int energy) {
		
		this.energy = energy;
		
	}
	
	public void setAttributes(List<Attribute> attributes) {
		
		this.attributes = attributes;
		
		addAttributes();
		
	}
	
	public ItemStack getFakeItem() {
		
		addLore();
		
		ItemMeta itemMeta = item.getItemMeta();
		List<String> lore = itemMeta.getLore();
		lore.add(ChatColor.RED + "" + ChatColor.MAGIC + "Contraband");
		itemMeta.setLore(lore);
		item.setItemMeta(itemMeta);
		
		return item;
		
	}
	
	public BlasterStats getTotalBlasterStats() {
		
		BlasterStats blasterStats = new BlasterStats();
		
		if (type.blasterStats != null) {
			
			blasterStats.cooldown = type.blasterStats.cooldown;
			blasterStats.damage = type.blasterStats.damage;
			blasterStats.scope = type.blasterStats.scope;
			blasterStats.ammo = type.blasterStats.ammo;
			blasterStats.reload = type.blasterStats.reload;
			blasterStats.ammoType = type.blasterStats.ammoType;
			blasterStats.projectileType = type.blasterStats.projectileType;
			blasterStats.explosionSize = type.blasterStats.explosionSize;
			blasterStats.shotCount = type.blasterStats.shotCount;
			
		}
		
		for (Modifier modifier : modifierMap.keySet()) {
			
			if (modifier.blasterStats != null) {
				
				blasterStats.cooldown = blasterStats.cooldown + (modifier.blasterStats.cooldown * modifierMap.get(modifier));
				blasterStats.damage = blasterStats.damage + (modifier.blasterStats.damage * modifierMap.get(modifier));
				blasterStats.scope = blasterStats.scope + (modifier.blasterStats.scope * modifierMap.get(modifier));
				blasterStats.ammo = blasterStats.ammo + (modifier.blasterStats.ammo * modifierMap.get(modifier));
				blasterStats.reload = blasterStats.reload + (modifier.blasterStats.reload * modifierMap.get(modifier)); 
				
				if (modifier.blasterStats.ammoType != null) {
					
					blasterStats.ammoType = modifier.blasterStats.ammoType;
					
				}
				
				if (modifier.blasterStats.projectileType != null) {
					
					blasterStats.projectileType = modifier.blasterStats.projectileType;
					
				}
				
				blasterStats.explosionSize = blasterStats.explosionSize + (modifier.blasterStats.explosionSize * modifierMap.get(modifier)); 
				blasterStats.shotCount = blasterStats.shotCount + (modifier.blasterStats.shotCount * modifierMap.get(modifier));
				
			}
			
		}
		
		return blasterStats;
		
	}
	
	public int getAmmo() {
		
		boolean start = false;
		
		String amount = "";
		
		for (char letter : item.getItemMeta().getDisplayName().toCharArray()) {
			
			if (start) {
				
				if (letter != ' ') {
					
					if (letter == '/') {
						
						try {
							
							return Integer.parseInt(amount);
							
						} catch (Exception e) {
							
							e.printStackTrace();
							
						}

					} else {
						
						amount = amount + letter;
						
					}

				}
				
			}
			
			if (letter == '-') {
				
				start = true;
				
			}
			
		}
		
		return 0;
		
	}
	
	public void setDisplayName(String name) {
		
		ItemMeta itemMeta = item.getItemMeta();
		itemMeta.setDisplayName(name);
		item.setItemMeta(itemMeta);
		
	}
	
	public int getEnergyPerUse() {
		
		int energyPerUse = type.energyPerUse;
		
		for (Modifier modifier : modifierMap.keySet()) {
			
			energyPerUse = energyPerUse + (modifier.energyPerUse * modifierMap.get(modifier));
			
		}
		
		return energyPerUse;
		
	}
	
}
