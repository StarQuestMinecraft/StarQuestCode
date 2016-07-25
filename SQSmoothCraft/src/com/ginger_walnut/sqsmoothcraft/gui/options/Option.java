package com.ginger_walnut.sqsmoothcraft.gui.options;

import java.util.ArrayList;
import java.util.List;

import net.md_5.bungee.api.ChatColor;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import com.ginger_walnut.sqsmoothcraft.utils.ShipUtils;

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
			
			ItemStack item = new ItemStack(Material.STAINED_GLASS_PANE);
			item.setDurability((short) 5);
			
			List<String> itemLore = new ArrayList<String>();
			itemLore.add(ChatColor.RED + "" + ChatColor.MAGIC + "Contraband");
			
			item = ShipUtils.createSpecialItem(item, itemLore, "Enabled");
			
			return item;
			
		} else if (type.equals(OptionType.BOOLEANFALSE)) {
			
			ItemStack item = new ItemStack(Material.STAINED_GLASS_PANE);
			item.setDurability((short) 14);
			
			List<String> itemLore = new ArrayList<String>();
			itemLore.add(ChatColor.RED + "" + ChatColor.MAGIC + "Contraband");
			
			item = ShipUtils.createSpecialItem(item, itemLore, "Disabled");
			
			return item;
			
		}
		
		return new ItemStack(Material.AIR);
		
	}
	
}
