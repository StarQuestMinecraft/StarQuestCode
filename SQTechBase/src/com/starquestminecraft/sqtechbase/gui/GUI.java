package com.starquestminecraft.sqtechbase.gui;

import net.md_5.bungee.api.ChatColor;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;

import com.starquestminecraft.sqtechbase.SQTechBase;

public class GUI {

	public Player owner;
	public boolean close = true;
	
	public GUI() {
		
	}
	
	public void open(Player player) {

		owner = player;
		
		Inventory gui = Bukkit.createInventory(owner, 27, ChatColor.BLUE + "SQTech");	
		
		owner.openInventory(gui);
		
		if (SQTechBase.currentGui.containsKey(owner)) {
			
			SQTechBase.currentGui.remove(owner);
			SQTechBase.currentGui.put(owner,  this);
			
		} else {
			
			SQTechBase.currentGui.put(owner,  this);
			
		}
		
	}
	
	public void click(InventoryClickEvent event) {
		
	}
	
	public void close() {
		
		if (owner != null) {
			
			owner.closeInventory();
			
			if (SQTechBase.currentGui.containsKey(owner)) {
				
				SQTechBase.currentGui.remove(owner);
				
			}
			
			owner = null;
			
		}
		
	}

}
