package com.starquestminecraft.sqtechbase.util;

import java.util.Arrays;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class InventoryUtils {

	public static ItemStack createSpecialItem(Material material, short durability, String name, String[] lore) {
		
		ItemStack item = new ItemStack(material);
		ItemMeta itemMeta = item.getItemMeta();
		
		itemMeta.setDisplayName(name);
		itemMeta.setLore(Arrays.asList(lore));
		
		item.setItemMeta(itemMeta);
		
		item.setDurability(durability);
		
		return item;
		
	}
	
}
