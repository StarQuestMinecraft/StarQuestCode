package com.starquestminecraft.sqtechbase.objects;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import com.starquestminecraft.sqtechbase.util.EnergyUtils;

import net.md_5.bungee.api.ChatColor;

public class EnergyItem {

	private ItemStack item;
	private int energy;
	private int maxEnergy;
	
	public EnergyItem(ItemStack item) {
		
		this.item = new ItemStack(Material.REDSTONE);
		
		energy = EnergyUtils.getEnergy(item);
		maxEnergy = 1000;
		
	}
	
	public EnergyItem() {
		
		item = new ItemStack(Material.REDSTONE);
		
		energy = 1000;
		maxEnergy = 1000;
		
	}
	
	private void addLore() {
		
		ItemMeta itemMeta = item.getItemMeta();
		
		List<String> lore = new ArrayList<String>();
		
		lore.add(ChatColor.DARK_PURPLE + "Energy Item");
		lore.add(ChatColor.RED + "Energy: " + EnergyUtils.formatEnergy(energy) + "/" + EnergyUtils.formatEnergy(maxEnergy));
		
		itemMeta.setLore(lore);
		
		item.setItemMeta(itemMeta);
		
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
	
}
