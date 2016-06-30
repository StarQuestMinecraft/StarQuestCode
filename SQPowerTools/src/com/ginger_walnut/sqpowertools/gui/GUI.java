package com.ginger_walnut.sqpowertools.gui;

import net.md_5.bungee.api.ChatColor;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;

import com.ginger_walnut.sqpowertools.SQPowerTools;

public class GUI {

	public Player owner;
	public boolean close = true;
	
	public GUI() {
		
	}
	
	public void open(Player player) {

		owner = player;
		
		Inventory gui = Bukkit.createInventory(owner, 27, ChatColor.BLUE + "SQPowerTools");	
		
		owner.openInventory(gui);
		
		if (SQPowerTools.currentGui.containsKey(owner)) {
			
			SQPowerTools.currentGui.remove(owner);
			SQPowerTools.currentGui.put(owner,  this);
			
		} else {
			
			SQPowerTools.currentGui.put(owner,  this);
			
		}
		
	}
	
	public void click(InventoryClickEvent event) {
		
	}
	
	public void close() {
		
		if (owner != null) {
			
			owner.closeInventory();
			
			if (SQPowerTools.currentGui.containsKey(owner)) {
				
				SQPowerTools.currentGui.remove(owner);
				
			}
			
			owner = null;
			
		}
		
	}

}
