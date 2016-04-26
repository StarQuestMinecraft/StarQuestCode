package com.ginger_walnut.sqduties;

import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.inventory.CraftItemEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCreativeEvent;
import org.bukkit.event.inventory.InventoryOpenEvent;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerChangedWorldEvent;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerPickupItemEvent;
import org.bukkit.Location;
import org.bukkit.entity.Player;

public class Events implements Listener{	

	@EventHandler
	public void dutyChat(AsyncPlayerChatEvent e) {

		if (e.getPlayer().hasPermission("commandspy.track")) {

			Runnable task = new CommandSpyFile(e.getPlayer().getName(), "General", ("Chat: " + e.getMessage()));
			new Thread(task, "CommandSpy").start();

		}
		
	}

	@EventHandler
	public void dutyWorldChange(PlayerChangedWorldEvent e) {

		if (e.getPlayer().hasPermission("commandspy.track")) {

			Runnable task = new CommandSpyFile(e.getPlayer().getName(), "General", ("Changed worlds from " + e.getFrom() + " to " + SQDuties.getPluginMain().getServer().getName()));
			new Thread(task, "CommandSpy").start();
			
		}
		
	}

	@EventHandler
	public void dutyDrop(PlayerDropItemEvent e) {

		if (e.getPlayer().hasPermission("commandspy.track")) {

			Runnable task = new CommandSpyFile(e.getPlayer().getName(), "Items", "Dropped item " + e.getItemDrop().getItemStack().getType().toString() + " at " + locationToString(e.getPlayer().getLocation()));
			new Thread(task, "CommandSpy").start();
			
		}
		
	}

	@EventHandler
	public void dutyPickup(PlayerPickupItemEvent e) {

		if (e.getPlayer().hasPermission("commandspy.track")) {
			
			Runnable task = new CommandSpyFile(e.getPlayer().getName(), "Items", "Picked up item " + e.getItem().getItemStack().getType().toString() + " at " + locationToString(e.getPlayer().getLocation()));
			new Thread(task, "CommandSpy").start();
			
		}
		
	}

	@EventHandler
	public void invOpen(InventoryOpenEvent e) {

		if (e.getPlayer().hasPermission("commandspy.track")) {
			
			Runnable task = new CommandSpyFile(((Player) e.getPlayer()).getName(), "Inventory", "Opened " + e.getInventory().getTitle() + "| Location: " + locationToString(e.getPlayer().getLocation()));
			new Thread(task, "CommandSpy").start();
			
		}
		
	}

	@EventHandler
	public void clickInventory(InventoryClickEvent e) {

		if (e.getWhoClicked().hasPermission("commandspy.track")) {
			
			Runnable task = new CommandSpyFile(((Player) e.getWhoClicked()).getName(), "Inventory", e.getInventory().getName() + "|" + e.getAction() + "|" + e.getCurrentItem());
			new Thread(task, "CommandSpy").start();
			
		}
		
	}

	@EventHandler
	public void creativeInventory(InventoryCreativeEvent e) {

		if (e.getWhoClicked().hasPermission("commandspy.track")) {
			
			Runnable task = new CommandSpyFile(((Player) e.getWhoClicked()).getName(), "Inventory", "Own Inventory" + e.getAction() + "|" + e.getCurrentItem());
			new Thread(task, "CommandSpy").start();
			
		}
		
	}

	@EventHandler
	public void command(PlayerCommandPreprocessEvent e) {

		if (e.getPlayer().hasPermission("commandspy.track")) {
			
			Runnable task = new CommandSpyFile(e.getPlayer().getName(), "General", "Command: " + e.getMessage());
			new Thread(task, "CommandSpy").start();
			
		}
		
	}

	@EventHandler
	public void blockBreak(BlockBreakEvent e) {

		if (e.getPlayer().hasPermission("commandspy.track")) {
			
			Runnable task = new CommandSpyFile(((Player) e.getPlayer()).getName(), "Block", "BlockBreak: " + e.getBlock().getType() + " Location: " + locationToString(e.getBlock().getLocation()));
			new Thread(task, "CommandSpy").start();
			
		} else if (e.getBlock().getType() == Material.DIAMOND_BLOCK 
				|| e.getBlock().getType() == Material.GOLD_BLOCK
				|| e.getBlock().getType() == Material.EMERALD_BLOCK) {
			
			// Adding support for global tracking
			Runnable task = new CommandSpyFile("Global", "Block", "Broken by " + e.getPlayer().getName() + ": " + e.getBlock().getType() + locationToString(e.getBlock().getLocation()));
			new Thread(task, "CommandSpy").start();
			
		}
		
	}

	@EventHandler
	public void blockPlace(BlockPlaceEvent e) {

		if (e.getPlayer().hasPermission("commandspy.track")) {
			
			Runnable task = new CommandSpyFile(((Player) e.getPlayer()).getName(), "Block", "BlockPlace: " + e.getBlock().getType() + " Location: " + locationToString(e.getBlock().getLocation()));
			new Thread(task, "CommandSpy").start();
			
		} else if (e.getBlock().getType() == Material.DIAMOND_BLOCK 
				|| e.getBlock().getType() == Material.GOLD_BLOCK
				|| e.getBlock().getType() == Material.EMERALD_BLOCK) {
			
			// Adding support for global tracking
			Runnable task = new CommandSpyFile("Global", "Block", "Placed by " + e.getPlayer().getName() + ": " + e.getBlock().getType() + locationToString(e.getBlock().getLocation()));
			new Thread(task, "CommandSpy").start();
			
		}
		
	}

	@EventHandler
	public void craftEvent(CraftItemEvent e) {

		if (e.getWhoClicked().hasPermission("commandspy.track")) {
			
			Runnable task = new CommandSpyFile(((Player) e.getWhoClicked()).getName(), "Craft", "Crafted: " + e.getCurrentItem().getType());
			new Thread(task, "CommandSpy").start();
			
		} else if (e.getCurrentItem().getType() == Material.DIAMOND_BLOCK 
				|| e.getCurrentItem().getType() == Material.GOLD_BLOCK
				|| e.getCurrentItem().getType() == Material.EMERALD_BLOCK) {
			
			// Adding support for global tracking
			Runnable task = new CommandSpyFile("Global", "Craft", "Crafted by " + e.getWhoClicked().getName() + ": " + e.getCurrentItem().getType());
			new Thread(task, "CommandSpy").start();
			
		}

	}
	
	public static String locationToString(Location l) {

		String location = ("X: " + Math.round(l.getX()) + " Y: " + Math.round(l.getY()) + " Z: " + Math.round(l.getZ()) + " World: " + l.getWorld().getName());

		return location;
		
	}
	
}
