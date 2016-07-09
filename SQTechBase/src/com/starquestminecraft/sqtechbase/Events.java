package com.starquestminecraft.sqtechbase;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPistonExtendEvent;
import org.bukkit.event.block.BlockPistonRetractEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitScheduler;

import net.md_5.bungee.api.ChatColor;

public class Events implements Listener {
	
	@EventHandler
	public void onBlockPistonExtend(final BlockPistonExtendEvent event) {
		
		if (!event.isCancelled()) {

			final Block block = event.getBlock().getRelative(event.getDirection(), 2);
				
			BukkitScheduler bukkitScheduler = Bukkit.getScheduler();
				
			bukkitScheduler.scheduleSyncDelayedTask(SQTechBase.getPluginMain(), new Runnable() {
					
				public void run() {
					
					new Network(block.getRelative(1, 0, 0));
					new Network(block.getRelative(-1, 0, 0));
					new Network(block.getRelative(0, 1, 0));
					new Network(block.getRelative(0, -1, 0));
					new Network(block.getRelative(0, 0, 1));
					new Network(block.getRelative(0, 0, -1));
						
				}
					
			}, 4);
			
		}
		
	}
	
	@EventHandler
	public void onBlockPistonRetract(final BlockPistonRetractEvent event) {
		
		if (!event.isCancelled()) {

			final Block block = event.getBlock().getRelative(event.getDirection().getOppositeFace(), 2);
			
			BukkitScheduler bukkitScheduler = Bukkit.getScheduler();
				
			bukkitScheduler.scheduleSyncDelayedTask(SQTechBase.getPluginMain(), new Runnable() {
					
				public void run() {
					
					new Network(block.getRelative(1, 0, 0));
					new Network(block.getRelative(-1, 0, 0));
					new Network(block.getRelative(0, 1, 0));
					new Network(block.getRelative(0, -1, 0));
					new Network(block.getRelative(0, 0, 1));
					new Network(block.getRelative(0, 0, -1));
						
				}
					
			}, 4);
			
		}
		
	}
	
	@EventHandler
	public void onBlockPlace(BlockPlaceEvent event) {
		
		if (!event.isCancelled()) {
		
			Material type = event.getBlock().getType();
			
			if (type.equals(Material.LAPIS_BLOCK) || type.equals(Material.STAINED_GLASS) || type.equals(Material.GLASS)) {
				
				new Network(event.getBlock());
				
			}
			
		}
		
	}
	
	@EventHandler
	public void onBlockBreak(final BlockBreakEvent event) {
		
		if (!event.isCancelled()) {
			
			Material type = event.getBlock().getType();
			
			if (type.equals(Material.LAPIS_BLOCK) || type.equals(Material.STAINED_GLASS) || type.equals(Material.GLASS)) {

				BukkitScheduler bukkitScheduler = Bukkit.getScheduler();
				
				bukkitScheduler.scheduleSyncDelayedTask(SQTechBase.getPluginMain(), new Runnable() {
					
					public void run() {
						
						new Network(event.getBlock().getRelative(1, 0, 0));
						new Network(event.getBlock().getRelative(-1, 0, 0));
						new Network(event.getBlock().getRelative(0, 1, 0));
						new Network(event.getBlock().getRelative(0, -1, 0));
						new Network(event.getBlock().getRelative(0, 0, 1));
						new Network(event.getBlock().getRelative(0, 0, -1));
						
					}
					
				}, 1);
				
			}
			
		}
		
	}
	
	@EventHandler
	public void onPlayerInteract(PlayerInteractEvent event) {
		
		if (event.getAction().equals(Action.RIGHT_CLICK_BLOCK)) {

			for(Network network : SQTechBase.networks) {
				
				for (int i = 0; i < network.GUIBlocks.size(); i ++) {
					
					if (network.GUIBlocks.get(i).getLocation().equals(event.getClickedBlock().getLocation())) {
						
						if (network.GUIBlocks.get(i).getLocation().getBlock().getType().equals(Material.LAPIS_BLOCK)) {
							
							if (!event.getPlayer().isSneaking()) {
								
								event.setCancelled(true);
								
								boolean isMachine = false;
								List<Machine> removeMachines = new ArrayList<Machine>();
								
								for (Machine machine : SQTechBase.machines) {
									
									if (machine.getGUIBlock().equals(network.GUIBlocks.get(i))) {
										
										if (machine.detectStructure()) {
											
											isMachine = true;
											machine.enabled = true;
											
										} else {
											
											removeMachines.add(machine);
											
										}
										
									}
									
								}
								
								SQTechBase.machines.removeAll(removeMachines);

								if (!isMachine) {
									
									for (MachineType machineType : SQTechBase.machineTypes) {
										
										if (machineType.autodetect) {
											
											if (machineType.detectStructure(network.GUIBlocks.get(i))) {
												
												Machine machine = new Machine(0, network.GUIBlocks.get(i), machineType);
												
												SQTechBase.machines.add(machine);
												
											}
											
										}
					
									}
									
								}
								
								network.GUIBlocks.get(i).getGUI().open(event.getPlayer());
								
							}
							
						}

					}
					
				}
				
			}
			
		}
		
	}
	
	@EventHandler
	public void onInventoryClick(InventoryClickEvent event) {
		
		if (SQTechBase.currentGui.containsKey(event.getWhoClicked())) {
			
			SQTechBase.currentGui.get(event.getWhoClicked()).click(event);
			
		}
		
	}
	
	@EventHandler
	public void onInventoryClose(InventoryCloseEvent event) {
		
		if (SQTechBase.currentGui.containsKey(event.getPlayer())) {
			
			for (ItemStack itemStack : event.getInventory().getContents()) {
				
				boolean normalItem = true;
				
				if (itemStack == null) {
					
					normalItem = false;
					
				} else {
					
					if (itemStack.hasItemMeta()) {
						
						if (itemStack.getItemMeta().hasLore()) {
							
							if (itemStack.getItemMeta().getLore().contains(ChatColor.RED + "" + ChatColor.MAGIC + "Contraband")) {
								
								normalItem = false;
								
							}
							
						}
						
					}
					
				}
				
				event.getInventory().remove(itemStack);
				
				if (normalItem) {
					
					event.getPlayer().getWorld().dropItem(event.getPlayer().getLocation(), itemStack);
					
				}
				
			}
			
			if (SQTechBase.currentGui.get(event.getPlayer()).close) {
				
				SQTechBase.currentGui.remove(event.getPlayer());
				
			}

		}
		
	}
	
}
