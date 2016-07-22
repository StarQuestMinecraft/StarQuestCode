package com.starquestminecraft.sqtechbase.util;

import java.util.List;

import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import net.md_5.bungee.api.ChatColor;

public class EnergyUtils {

	public static String formatEnergy(float energy) {
		
		String formattedEnergy = "";
		
		if (energy >= 0 && energy < 1000) {
			
			formattedEnergy = Float.toString(energy);
			
		} else if (energy >= 1000 && energy < 1000000) {
			
			formattedEnergy = Float.toString(energy / 1000) + "k";
			
		} else if (energy >= 1000000) {
			
			formattedEnergy = Float.toString(energy / 1000000) + "m";
			
		} else {
			
			formattedEnergy = "0";
			
		}
		
		return formattedEnergy;
		
	}
	
	public static float unformatEnergy(String formattedEnergy) {
		
		if (formattedEnergy.endsWith("k")) {
			
			return Float.parseFloat(formattedEnergy.substring(0, formattedEnergy.toCharArray().length - 1)) * 1000;
			
		} else if (formattedEnergy.endsWith("m")) {
			
			return Float.parseFloat(formattedEnergy.substring(0, formattedEnergy.toCharArray().length - 1)) * 1000000;
			
		} else {
			
			return Float.parseFloat(formattedEnergy);
			
		}
		
	}
	
	public static int getEnergy(ItemStack energyItem) {
		
		List<String> lore = energyItem.getItemMeta().getLore();
		
		for (String line : lore) {
			
			if (line.startsWith(ChatColor.RED + "Energy:")) {
				
				char[] lineAsCharArray = line.toCharArray();
				
				String energy = "";
				
				for (int i = 0; i < lineAsCharArray.length; i ++) {

					if (i > 9) {
						
						if (lineAsCharArray[i] != '/') {
							
							energy = energy + lineAsCharArray[i];
							
						} else {
							
							return (int) unformatEnergy(energy);
							
						}
						
					}
					
				}
				
			}
			
		}
		
		return 0;
		
	}
	
	public static int getMaxEnergy(ItemStack energyItem) {
		
		List<String> lore = energyItem.getItemMeta().getLore();
		
		for (String line : lore) {
			
			if (line.startsWith(ChatColor.RED + "Energy:")) {
				
				char[] lineAsCharArray = line.toCharArray();
				
				String energy = "";
				
				boolean after = false;
				
				for (int i = 0; i < lineAsCharArray.length; i ++) {

					if (i > 9) {
						
						if (lineAsCharArray[i] == '/') {
							
							after = true;
							
						} else {
							
							if (after) {
								
								energy = energy + lineAsCharArray[i];								
							
							}

						}
						
					}
					
				}
				
				return (int) unformatEnergy(energy);
				
			}
			
		}
		
		return 0;
		
	}
	
	public static ItemStack setEnergy(ItemStack energyItem, int energy) {
		
		int maxEnergy = getMaxEnergy(energyItem);
		
		List<String> lore = energyItem.getItemMeta().getLore();
		
		int energyPosition = 0;
		
		for (int i = 0; i < lore.size(); i ++) {
			
			if (lore.get(i).startsWith(ChatColor.RED + "Energy:")) {
				
				energyPosition = i;
				
			}
			
		}
		
		lore.set(energyPosition, ChatColor.RED + "Energy: " + formatEnergy(energy) + "/" + formatEnergy(maxEnergy));
		
		ItemMeta itemMeta = energyItem.getItemMeta();
		itemMeta.setLore(lore);
		
		energyItem.setItemMeta(itemMeta);
		
		return energyItem;
		
	}
	
}
