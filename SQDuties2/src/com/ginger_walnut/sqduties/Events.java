package com.ginger_walnut.sqduties;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.inventory.CraftItemEvent;
import org.bukkit.event.inventory.InventoryAction;
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
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitScheduler;

@SuppressWarnings("deprecation")
public class Events implements Listener{	

	@EventHandler
	public void dutyChat(AsyncPlayerChatEvent e) {

		if (e.getPlayer().hasPermission("commandspy.track")) {

			final String message = e.getMessage();
			final String name = e.getPlayer().getName();
			
			BukkitScheduler scheduler = Bukkit.getServer().getScheduler();
			scheduler.scheduleAsyncDelayedTask(SQDuties.getPluginMain(), new Runnable() {
		
				@Override
				public void run() {
					
					(new CommandSpyFile(name, "General", ("Chat: " + message))).run();
			
				}
				
			},1);

		}
		
	}


	@EventHandler
	public void dutyWorldChange(PlayerChangedWorldEvent e) {

		if (e.getPlayer().hasPermission("commandspy.track")) {

			final String name = e.getPlayer().getName();
			final World from = e.getFrom();
			final String to = SQDuties.getPluginMain().getServer().getName();
			
			BukkitScheduler scheduler = Bukkit.getServer().getScheduler();
			scheduler.scheduleAsyncDelayedTask(SQDuties.getPluginMain(), new Runnable() {
		
				@Override
				public void run() {
					
					(new CommandSpyFile(name, "General", ("Changed worlds from " + from + " to " + to))).run();
			
				}
				
			},1);
					
		}
		
	}

	@EventHandler
	public void dutyDrop(PlayerDropItemEvent e) {

		if (e.getPlayer().hasPermission("commandspy.track")) {

			final String item = e.getItemDrop().getItemStack().getType().toString();
			final String name = e.getPlayer().getName();
			final Location location = e.getPlayer().getLocation();
			
			BukkitScheduler scheduler = Bukkit.getServer().getScheduler();
			scheduler.scheduleAsyncDelayedTask(SQDuties.getPluginMain(), new Runnable() {
		
				@Override
				public void run() {
					
					(new CommandSpyFile(name, "Items", "Dropped Item " + item + " at " + locationToString(location))).run();
			
				}
				
			},1);
			
		}
		
	}

	@EventHandler
	public void dutyPickup(PlayerPickupItemEvent e) {

		if (e.getPlayer().hasPermission("commandspy.track")) {
			
			final String item = e.getItem().getItemStack().getType().toString();
			final String name = e.getPlayer().getName();
			final Location location = e.getPlayer().getLocation();
			
			BukkitScheduler scheduler = Bukkit.getServer().getScheduler();
			scheduler.scheduleAsyncDelayedTask(SQDuties.getPluginMain(), new Runnable() {
		
				@Override
				public void run() {
					
					(new CommandSpyFile(name, "Items", "Picked up Item " + item + " at " + locationToString(location))).run();
			
				}
				
			},1);
			
		}
		
	}

	@EventHandler
	public void invOpen(InventoryOpenEvent e) {

		if (e.getPlayer().hasPermission("commandspy.track")) {
			
			final String title = e.getInventory().getTitle();
			final String name = e.getPlayer().getName();
			final Location location = e.getPlayer().getLocation();
			
			BukkitScheduler scheduler = Bukkit.getServer().getScheduler();
			scheduler.scheduleAsyncDelayedTask(SQDuties.getPluginMain(), new Runnable() {
		
				@Override
				public void run() {
					
					(new CommandSpyFile(name, "Inventory", ("Opened " + title + "| Location: " + locationToString(location)))).run();
			
				}
				
			},1);
			
		}
		
	}

	@EventHandler
	public void clickInventory(InventoryClickEvent e) {

		if (e.getWhoClicked().hasPermission("commandspy.track")) {
			
			final String name = e.getWhoClicked().getName();
			final String invName = e.getInventory().getName();
			final InventoryAction invAction = e.getAction();
			final ItemStack item = e.getCurrentItem();
			
			BukkitScheduler scheduler = Bukkit.getServer().getScheduler();
			scheduler.scheduleAsyncDelayedTask(SQDuties.getPluginMain(), new Runnable() {
		
				@Override
				public void run() {
					
					(new CommandSpyFile(name, "Inventory", (invName + "|" + invAction + "|" + item))).run();
			
				}
				
			},1);
			
		}
		
	}

	@EventHandler
	public void creativeInventory(InventoryCreativeEvent e) {

		if (e.getWhoClicked().hasPermission("commandspy.track")) {
			
			final String name = e.getWhoClicked().getName();
			final InventoryAction invAction = e.getAction();
			final ItemStack item = e.getCurrentItem();
			
			BukkitScheduler scheduler = Bukkit.getServer().getScheduler();
			scheduler.scheduleAsyncDelayedTask(SQDuties.getPluginMain(), new Runnable() {
		
				@Override
				public void run() {
					
					(new CommandSpyFile(name, "Inventory", ("Own Inventory " + invAction + "|" + item))).run();
			
				}
				
			},1);
			
		}
		
	}

	@EventHandler
	public void command(PlayerCommandPreprocessEvent e) {

		if (e.getPlayer().hasPermission("commandspy.track")) {
			
			final String message = e.getMessage();
			final String name = e.getPlayer().getName();
			
			BukkitScheduler scheduler = Bukkit.getServer().getScheduler();
			scheduler.scheduleAsyncDelayedTask(SQDuties.getPluginMain(), new Runnable() {
		
				@Override
				public void run() {
					
					(new CommandSpyFile(name, "General", ("Command: " + message))).run();
			
				}
				
			},1);
			
		}
		
	}

	@EventHandler
	public void blockBreak(BlockBreakEvent e) {

		if (e.getPlayer().hasPermission("commandspy.track")) {
			
			final String name = e.getPlayer().getName();
			final Material type = e.getBlock().getType();
			final Location location = e.getPlayer().getLocation();
			
			BukkitScheduler scheduler = Bukkit.getServer().getScheduler();
			scheduler.scheduleAsyncDelayedTask(SQDuties.getPluginMain(), new Runnable() {
		
				@Override
				public void run() {
					
					(new CommandSpyFile(name, "Block", ("BlockBreak: " + type + " Location " + locationToString(location)))).run();
			
				}
				
			},1);
			
		} else if (e.getBlock().getType() == Material.DIAMOND_BLOCK 
				|| e.getBlock().getType() == Material.GOLD_BLOCK
				|| e.getBlock().getType() == Material.EMERALD_BLOCK) {
			
			final String name = e.getPlayer().getName();
			final Material type = e.getBlock().getType();
			final Location location = e.getPlayer().getLocation();
			
			BukkitScheduler scheduler = Bukkit.getServer().getScheduler();
			scheduler.scheduleAsyncDelayedTask(SQDuties.getPluginMain(), new Runnable() {
		
				@Override
				public void run() {
					
					(new CommandSpyFile("Global", "Block", ("Broken by " + name + ":" + type + " Location " + locationToString(location)))).run();
			
				}
				
			},1);
			
		}
		
	}

	@EventHandler
	public void blockPlace(BlockPlaceEvent e) {

		if (e.getPlayer().hasPermission("commandspy.track")) {
			
			final String name = e.getPlayer().getName();
			final Material type = e.getBlock().getType();
			final Location location = e.getPlayer().getLocation();
			
			BukkitScheduler scheduler = Bukkit.getServer().getScheduler();
			scheduler.scheduleAsyncDelayedTask(SQDuties.getPluginMain(), new Runnable() {
		
				@Override
				public void run() {
					
					(new CommandSpyFile(name, "Block", ("BlockPlace: " + type + " Location " + locationToString(location)))).run();
			
				}
				
			},1);
			
		} else if (e.getBlock().getType() == Material.DIAMOND_BLOCK 
				|| e.getBlock().getType() == Material.GOLD_BLOCK
				|| e.getBlock().getType() == Material.EMERALD_BLOCK) {
			
			final String name = e.getPlayer().getName();
			final Material type = e.getBlock().getType();
			final Location location = e.getPlayer().getLocation();
			
			BukkitScheduler scheduler = Bukkit.getServer().getScheduler();
			scheduler.scheduleAsyncDelayedTask(SQDuties.getPluginMain(), new Runnable() {
		
				@Override
				public void run() {
					
					(new CommandSpyFile("Global", "Block", ("Placed by " + name + ":" + type + " Location " + locationToString(location)))).run();
			
				}
				
			},1);
			
		}
		
	}

	@EventHandler
	public void craftEvent(CraftItemEvent e) {

		if (e.getWhoClicked().hasPermission("commandspy.track")) {
			
			final String name = e.getWhoClicked().getName();
			final Material type = e.getCurrentItem().getType();
			
			BukkitScheduler scheduler = Bukkit.getServer().getScheduler();
			scheduler.scheduleAsyncDelayedTask(SQDuties.getPluginMain(), new Runnable() {
		
				@Override
				public void run() {
					
					(new CommandSpyFile(name, "Craft", ("Crafted: " + type))).run();
			
				}
				
			},1);
			
		} else if (e.getCurrentItem().getType() == Material.DIAMOND_BLOCK 
				|| e.getCurrentItem().getType() == Material.GOLD_BLOCK
				|| e.getCurrentItem().getType() == Material.EMERALD_BLOCK) {
			
			final String name = e.getWhoClicked().getName();
			final Material type = e.getCurrentItem().getType();
			
			BukkitScheduler scheduler = Bukkit.getServer().getScheduler();
			scheduler.scheduleAsyncDelayedTask(SQDuties.getPluginMain(), new Runnable() {
		
				@Override
				public void run() {
					
					(new CommandSpyFile("Global", "Craft", ("Crafted by  " + name + ":" + type))).run();
			
				}
				
			},1);
			
			Runnable task = new CommandSpyFile("Global", "Craft", "Crafted by " + e.getWhoClicked().getName() + ": " + e.getCurrentItem().getType());
			new Thread(task, "CommandSpy").start();
			
		}

	}
	
	public static String locationToString(Location l) {

		String location = ("X: " + Math.round(l.getX()) + " Y: " + Math.round(l.getY()) + " Z: " + Math.round(l.getZ()) + " World: " + l.getWorld().getName());

		return location;
		
	}
	
}
