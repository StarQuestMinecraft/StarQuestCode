package com.starquestminecraft.sqtechbase;

import net.countercraft.movecraft.event.CraftAsyncTranslateEvent;
import net.countercraft.movecraft.event.CraftSyncTranslateEvent;
import net.md_5.bungee.api.ChatColor;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.metadata.MetadataValue;
import org.bukkit.scheduler.BukkitScheduler;

import com.starquestminecraft.sqtechbase.gui.GUI;

public class Events implements Listener {

	/*@EventHandler 
	public void onCraftAsyncTranslate(CraftAsyncTranslateEvent event) {

		/*if (!event.isCancelled()) {
		
			for (int i = 0; i < event.getData().getBlockList().length; i ++) {
				
				Location location = new Location(event.getCraft().getW(), event.getData().getBlockList()[i].getX(), event.getData().getBlockList()[i].getY(), event.getData().getBlockList()[i].getZ());

				for (int j = 0; j < SQTechBase.GUIBlocks.size(); j ++) {
					
					if (SQTechBase.GUIBlocks.get(j).getLocation().equals(location)) {
						
						GUIBlock guiBlock = SQTechBase.GUIBlocks.get(j);
						guiBlock.setLocation(guiBlock.getLocation().add(event.getData().getDx(), event.getData().getDy(), event.getData().getDz()));
						SQTechBase.GUIBlocks.remove(i);
						SQTechBase.GUIBlocks.add(guiBlock);
						
					}
					
				}
				
			}
			
		}
		
	}
	
	@EventHandler 
	public void onCraftSyncTranslate(CraftSyncTranslateEvent event) {

		if (!event.isCancelled()) {
			
			for (int i = 0; i < event.getData().getBlockList().length; i ++) {
				
				Location location = new Location(event.getCraft().getW(), event.getData().getBlockList()[i].getX(), event.getData().getBlockList()[i].getY(), event.getData().getBlockList()[i].getZ());
				
				for (int j = 0; j < SQTechBase.GUIBlocks.size(); j ++) {
					
					if (SQTechBase.GUIBlocks.get(j).getLocation().equals(location)) {
						
						GUIBlock guiBlock = SQTechBase.GUIBlocks.get(j);
						guiBlock.setLocation(guiBlock.getLocation().add(event.getData().getDx(), event.getData().getDy(), event.getData().getDz()));
						SQTechBase.GUIBlocks.remove(i);
						SQTechBase.GUIBlocks.add(guiBlock);
						
					}
					
				}
				
			}
			
		}

	}*/
	
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
											
										} else {
											
											removeMachines.add(machine);
											
										}
										
									}
									
								}
								
								SQTechBase.machines.removeAll(removeMachines);

								
								if (!isMachine) {
									
									for (MachineType machineType : SQTechBase.machineTypes) {
										
										if (machineType.detectStructure(network.GUIBlocks.get(i))) {
											
											Machine machine = new Machine(0, network.GUIBlocks.get(i), machineType);
											
											SQTechBase.machines.add(machine);
											
										}
															
									}
									
								}
								
								network.GUIBlocks.get(i).getGUI().open(event.getPlayer());
								
							}
							
						} else {
							
							network.GUIBlocks.remove(i);
							
						}

					}
					
				}
				
			}
			
		}
		
	}
	
	@EventHandler
	public void onInventoryClick(InventoryClickEvent event) {
		
		if (SQTechBase.currentGui.containsKey(event.getWhoClicked())) {
			
			event.setCancelled(true);
			
			SQTechBase.currentGui.get(event.getWhoClicked()).click(event);
			
		}
		
	}
	
	@EventHandler
	public void onInventoryClose(InventoryCloseEvent event) {
		
		if (SQTechBase.currentGui.containsKey(event.getPlayer())) {
			
			if (SQTechBase.currentGui.get(event.getPlayer()).close) {
				
				SQTechBase.currentGui.remove(event.getPlayer());
				
			}

		}
		
	}
	
}
