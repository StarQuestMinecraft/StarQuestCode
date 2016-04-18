package com.ginger_walnut.sqsmoothcraft.gui;

import net.md_5.bungee.api.ChatColor;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

import com.ginger_walnut.sqsmoothcraft.SQSmoothCraft;

public class Gui {

	public Player owner;
	
	public Gui(Player player) {
		
		owner = player;
		
	}
	
	public void open() {

		Inventory gui = Bukkit.createInventory(owner, 27, ChatColor.BLUE + "SQSmoothCraft");
		
		owner.openInventory(gui);
		
		if (SQSmoothCraft.currentGui.containsKey(owner)) {
			
			SQSmoothCraft.currentGui.remove(owner);
			SQSmoothCraft.currentGui.put(owner,  this);
			
		} else {
			
			SQSmoothCraft.currentGui.put(owner,  this);
			
		}
		
	}
	
	public void clicked(String itemName, int slot) {
		
	}
	
	public void close() {
		
		owner.closeInventory();
		
		if (SQSmoothCraft.currentGui.containsKey(owner)) {
			
			SQSmoothCraft.currentGui.remove(owner);
			
		}
		
	}

}
