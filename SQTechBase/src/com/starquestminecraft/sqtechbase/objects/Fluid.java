package com.starquestminecraft.sqtechbase.objects;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import com.starquestminecraft.sqtechbase.util.InventoryUtils;

import net.md_5.bungee.api.ChatColor;

public class Fluid {

	public int id = 0;
	public Material material;
	public short durability;
	public String name;
	
	public Fluid(int id, Material material, int durability, String name) {
		
		this.id = id;
		this.material = material;
		this.durability = (short) durability;
		this.name = name;
		
	}
	
	public ItemStack getItem() {
		
		return InventoryUtils.createSpecialItem(material, durability, name, new String[] {ChatColor.RED + "" + ChatColor.MAGIC + "Contraband"});
		
	}
	
	public static ItemStack getOption(boolean bool) {
		
		if (bool) {
			
			ItemStack item = InventoryUtils.createSpecialItem(Material.STAINED_GLASS_PANE, (short) 5, "Enabled", new String[] {ChatColor.RED + "" + ChatColor.MAGIC + "Contraband"});
			
			return item;
			
		} else {
			
			
			ItemStack item = InventoryUtils.createSpecialItem(Material.STAINED_GLASS_PANE, (short) 14, "Disabled", new String[] {ChatColor.RED + "" + ChatColor.MAGIC + "Contraband"});
			
			return item;
			
		}
		
	}
	
}
