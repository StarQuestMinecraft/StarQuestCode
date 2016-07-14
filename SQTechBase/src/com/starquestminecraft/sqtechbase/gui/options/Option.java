package com.starquestminecraft.sqtechbase.gui.options;


import java.util.List;

import net.md_5.bungee.api.ChatColor;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import com.starquestminecraft.sqtechbase.util.InventoryUtils;

public class Option {

	public OptionType type;
	public String name;
	public List<String> lore;
	
	public Option(OptionType optionType, String optionName, List<String> optionLore) {
		
		type = optionType;
		name = optionName;
		lore = optionLore;
		
	}
	
	public ItemStack getItem() {
		
		ItemStack item = new ItemStack(Material.PAPER);
		
		ItemMeta itemMeta = item.getItemMeta();
		itemMeta.setDisplayName(name);
		itemMeta.setLore(lore);
		item.setItemMeta(itemMeta);
		
		return item;
		
	}
	
	public ItemStack getOption() {
		
		if (type.equals(OptionType.BOOLEANTRUE)) {
			
			ItemStack item = InventoryUtils.createSpecialItem(Material.STAINED_GLASS_PANE, (short) 5, "Enabled", new String[] {ChatColor.RED + "" + ChatColor.MAGIC + "Contraband"});
			
			return item;
			
		} else if (type.equals(OptionType.BOOLEANFALSE)) {
			
			ItemStack item = InventoryUtils.createSpecialItem(Material.STAINED_GLASS_PANE, (short) 14, "Disabled", new String[] {ChatColor.RED + "" + ChatColor.MAGIC + "Contraband"});
			
			return item;
			
		}
		
		return new ItemStack(Material.AIR);
		
	}
	
}
