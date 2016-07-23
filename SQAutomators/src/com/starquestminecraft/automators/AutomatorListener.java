package com.starquestminecraft.automators;

import java.util.ArrayList;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;

import net.md_5.bungee.api.ChatColor;

public class AutomatorListener implements Listener {
	
	private final SQAutomators plugin;
	public ArrayList<String> stringList = new ArrayList<String>();
	
	public AutomatorListener(SQAutomators plugin) {
		this.plugin = plugin;
		plugin.getServer().getPluginManager().registerEvents(this, plugin);
		stringList.add(ChatColor.GRAY + "AutoCrafter");
	}
	
	@EventHandler
	public void onItemPickup(InventoryClickEvent e) {		
		
		if(stringList.contains(e.getInventory().getName())) {
			e.setCancelled(true);
		}
		
	}
	
}
