package com.ginger_walnut.sqpowertools.objects;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.bukkit.ChatColor;
import org.bukkit.craftbukkit.v1_9_R1.inventory.CraftItemStack;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import com.ginger_walnut.sqpowertools.SQPowerTools;
import com.ginger_walnut.sqpowertools.utils.AttributeUtils;

import net.minecraft.server.v1_9_R1.NBTTagCompound;
import net.minecraft.server.v1_9_R1.NBTTagFloat;
import net.minecraft.server.v1_9_R1.NBTTagInt;
import net.minecraft.server.v1_9_R1.NBTTagList;
import net.minecraft.server.v1_9_R1.NBTTagString;

public class PowerTool {

	private ItemStack item;
	private PowerToolType type;
	
	private Map<Modifier, Integer> modifierMap = new HashMap<Modifier, Integer>();
	private List<Attribute> attributes = new ArrayList<Attribute>();
	
	private int energy;
	private int maxEnergy;
	
	public PowerTool(PowerToolType type) {
		
		energy = type.energy;
		maxEnergy = type.energy;
		this.type = type;
		
		List<Modifier> modifiers = new ArrayList<Modifier>();
		modifiers.addAll(modifierMap.keySet());
		
		List<Attribute> attributes = new ArrayList<Attribute>();
		attributes.addAll(type.attributes);
		
		for (Modifier modifier : modifiers) {
			
			for (Attribute modifierAttribute : modifier.attributes) {
				
				boolean contains = false;
				
				for (Attribute attribute : attributes) {
					
					if (modifierAttribute.equalsExceptAmount(attribute)) {
						
						attribute.amount = attribute.amount + modifierAttribute.amount;
						
						contains = true;
						
					}
					
				}
				
				if (!contains) {
					
					attributes.add(modifierAttribute);
					
				}
				
			}
			
		}	
		
		item = new ItemStack(type.material);
		
		ItemMeta itemMeta = item.getItemMeta();	
		itemMeta.setDisplayName(type.name);	
		item.setItemMeta(itemMeta);
		
		item.addUnsafeEnchantments(type.enchants);
		item.setDurability(type.durability); 
				
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
		
		ItemMeta itemMeta = item.getItemMeta();
		
		itemMeta.setLore(new ArrayList<String>());
		
		List<String> lore = new ArrayList<String>();
		
		for (String line : type.extraLore) {
			
			lore.add(ChatColor.GRAY + line);
			
		}
		
		lore.add(ChatColor.DARK_PURPLE + "Power Tool");
		lore.add(ChatColor.DARK_PURPLE + type.name);
		
		List<Modifier> modifiers = new ArrayList<Modifier>();
		modifiers.addAll(modifierMap.keySet());
		
		lore.add(ChatColor.RED + "Energy: " + SQPowerTools.formatEnergy(energy) + "/" + SQPowerTools.formatEnergy(maxEnergy));
		
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
		
		itemMeta.setLore(lore);
		
		item.setItemMeta(itemMeta);
		
	}
	
	private void addAttributes(){
		  
		net.minecraft.server.v1_9_R1.ItemStack nmsStack = CraftItemStack.asNMSCopy(item);
	         
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
		attributes.addAll(type.attributes);
		
		for (Modifier modifier : modifiers) {
			
			for (Attribute modifierAttribute : modifier.attributes) {
				
				boolean contains = false;
				
				for (Attribute attribute : attributes) {
					
					if (modifierAttribute.equalsExceptAmount(attribute)) {
						
						attribute.amount = attribute.amount + modifierAttribute.amount;
						
						contains = true;
						
					}
					
				}
				
				if (!contains) {
					
					attributes.add(modifierAttribute);
					
				}
				
			}
			
		}	
		
		item = new ItemStack(type.material);
		
		ItemMeta itemMeta = item.getItemMeta();	
		itemMeta.setDisplayName(type.name);	
		item.setItemMeta(itemMeta);
		
		item.addUnsafeEnchantments(type.enchants);
		
		for (Modifier modifier : modifiers) {
			
			item.addUnsafeEnchantments(modifier.enchants);
			
		}
		
		item.setDurability(type.durability); 
				
		addLore();
		addAttributes();
		
	}
	
}
