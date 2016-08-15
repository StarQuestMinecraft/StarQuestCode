package com.starquestminecraft.sqtechbase.listeners;

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

import com.dibujaron.cardboardbox.CardboardBox;
import com.starquestminecraft.sqtechbase.SQTechBase;
import com.starquestminecraft.sqtechbase.objects.GUIBlock;
import com.starquestminecraft.sqtechbase.objects.Machine;
import com.starquestminecraft.sqtechbase.objects.MachineType;
import com.starquestminecraft.sqtechbase.objects.Network;
import com.starquestminecraft.sqtechbase.util.InventoryUtils;
import com.starquestminecraft.sqtechbase.util.ObjectUtils;

import net.md_5.bungee.api.ChatColor;

public class GUIBlockEvents implements Listener {
	
	@EventHandler
	public void onBlockPistonExtend(final BlockPistonExtendEvent event) {
		
		if (!event.isCancelled()) {

			final Block block = event.getBlock().getRelative(event.getDirection(), 2);
				
			BukkitScheduler bukkitScheduler = Bukkit.getScheduler();
				
			bukkitScheduler.scheduleSyncDelayedTask(SQTechBase.getPluginMain(), new Runnable() {
					
				public void run() {
					
					Block origBlock = event.getBlock().getRelative(event.getDirection(), 1);
					
					if (block.getType().equals(Material.LAPIS_BLOCK)) {

						for (Network network : SQTechBase.networks) {
							
							for (GUIBlock guiBlock : network.getGUIBlocks()) {
								
								if (origBlock.getLocation().equals(guiBlock.getLocation())) {
									
									guiBlock.changeLocation(block.getLocation());
									
								}
								
							}
							
						}
						
					} else {
						
						new Network(block, true);
						
						new Network(origBlock.getRelative(1, 0, 0), true);
						new Network(origBlock.getRelative(-1, 0, 0), true);
						new Network(origBlock.getRelative(0, 1, 0), true);
						new Network(origBlock.getRelative(0, -1, 0), true);
						new Network(origBlock.getRelative(0, 0, 1), true);
						new Network(origBlock.getRelative(0, 0, -1), true);
						
					}

				}
					
			}, 4);
			
		}
		
	}
	
	@EventHandler
	public void onBlockPistonRetract(final BlockPistonRetractEvent event) {
		
		if (!event.isCancelled()) {

			final Block block = event.getBlock().getRelative(event.getDirection().getOppositeFace(), 1);
			
			BukkitScheduler bukkitScheduler = Bukkit.getScheduler();
				
			bukkitScheduler.scheduleSyncDelayedTask(SQTechBase.getPluginMain(), new Runnable() {
					
				public void run() {
					
					Block origBlock = event.getBlock().getRelative(event.getDirection().getOppositeFace(), 2);
					
					if (block.getType().equals(Material.LAPIS_BLOCK)) {
						
						for (Network network : SQTechBase.networks) {
							
							for (GUIBlock guiBlock : network.getGUIBlocks()) {
								
								if (origBlock.getLocation().equals(guiBlock.getLocation())) {
									
									guiBlock.changeLocation(block.getLocation());
									
								}
								
							}
							
						}
						
					} else {
						
						new Network(block, true);
						
						new Network(origBlock.getRelative(1, 0, 0), true);
						new Network(origBlock.getRelative(-1, 0, 0), true);
						new Network(origBlock.getRelative(0, 1, 0), true);
						new Network(origBlock.getRelative(0, -1, 0), true);
						new Network(origBlock.getRelative(0, 0, 1), true);
						new Network(origBlock.getRelative(0, 0, -1), true);
						
					}
						
				}
					
			}, 4);
			
		}
		
	}
	
	@EventHandler
	public void onBlockPlace(BlockPlaceEvent event) {
		
		if (!event.isCancelled()) {
		
			Material type = event.getBlock().getType();
			
			if (type.equals(Material.LAPIS_BLOCK) || type.equals(Material.STAINED_GLASS) || type.equals(Material.GLASS) || type.equals(Material.END_ROD)) {
				
				new Network(event.getBlock(), true);
				
			}
			
		}
		
	}
	
	@EventHandler
	public void onBlockBreak(final BlockBreakEvent event) {
		
		if (!event.isCancelled()) {
			
			Material type = event.getBlock().getType();
			
			if (type.equals(Material.LAPIS_BLOCK)) {
				
				for (Network network : SQTechBase.networks) {
					
					for (int i = 0; i < network.getGUIBlocks().size(); i ++) {
						
						if (network.getGUIBlocks().get(i).getLocation().getBlock().equals(event.getBlock())) {
							
							List<GUIBlock> guiBlocks = network.getGUIBlocks();
							guiBlocks.remove(i);
							network.setGUIBlocks(guiBlocks);
							
							if (event.getBlock().hasMetadata("guiblock")) {
								
								event.getBlock().removeMetadata("guiblock", SQTechBase.getPluginMain());
								
							}
							
						}
						
					}
					
				}
				
			}
			
			if (type.equals(Material.LAPIS_BLOCK) || type.equals(Material.STAINED_GLASS) || type.equals(Material.GLASS) || type.equals(Material.END_ROD)) {

				BukkitScheduler bukkitScheduler = Bukkit.getScheduler();
				
				bukkitScheduler.scheduleSyncDelayedTask(SQTechBase.getPluginMain(), new Runnable() {
					
					public void run() {
						
						new Network(event.getBlock().getRelative(1, 0, 0), true);
						new Network(event.getBlock().getRelative(-1, 0, 0), true);
						new Network(event.getBlock().getRelative(0, 1, 0), true);
						new Network(event.getBlock().getRelative(0, -1, 0), true);
						new Network(event.getBlock().getRelative(0, 0, 1), true);
						new Network(event.getBlock().getRelative(0, 0, -1), true);
						
					}
					
				}, 1);
				
			}
			
		}
		
	}
	
	@EventHandler
	public void onPlayerInteract(PlayerInteractEvent event) {
		
		if (event.getAction().equals(Action.RIGHT_CLICK_BLOCK)) {

			for(Network network : SQTechBase.networks) {
				
				for (int i = 0; i < network.getGUIBlocks().size(); i ++) {
					
					if (network.getGUIBlocks().get(i).getLocation().equals(event.getClickedBlock().getLocation())) {
					
						if (network.getGUIBlocks().get(i).getLocation().getBlock().getType().equals(Material.LAPIS_BLOCK)) {
							
							if (!event.getPlayer().isSneaking()) {
								
								event.setCancelled(true);
								
								boolean isMachine = false;
								List<Machine> removeMachines = new ArrayList<Machine>();
								
								for (Machine machine : SQTechBase.machines) {
									
									if (machine.getGUIBlock().id == network.getGUIBlocks().get(i).id) {
										
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

									for (Machine machine : SQTechBase.machines) {
												
										if (machine.detectStructure() && machine.getGUIBlock().id != network.getGUIBlocks().get(i).id) {
										
											if (machine.guiBlock.getLocation().equals(network.getGUIBlocks().get(i).getLocation())) {
												
												machine.guiBlock = network.getGUIBlocks().get(i);
												
												if (!SQTechBase.currentGui.containsKey(event.getPlayer())) {
													
													network.getGUIBlocks().get(i).getGUI(event.getPlayer()).open();
													
													return;
													
												}
												
											}
																						
										}
										
									}

									for (MachineType machineType : SQTechBase.machineTypes) {
										
										if (machineType.autodetect) {
											
											if (machineType.detectStructure(network.getGUIBlocks().get(i))) {
												
												Machine machine = new Machine(0, network.getGUIBlocks().get(i), machineType);
												
												SQTechBase.machines.add(machine);
												
											}
											
										}
					
									}
									
								}
								
								if (!SQTechBase.currentGui.containsKey(event.getPlayer())) {
									
									network.getGUIBlocks().get(i).getGUI(event.getPlayer()).open();
									
									return;
									
								}

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
					
					if (event.getInventory().getTitle().equals(ChatColor.BLUE + "SQTech - Imports")) {
						
						GUIBlock guiBlock = ObjectUtils.getGUIBlockFromGUI(SQTechBase.currentGui.get(event.getPlayer()));
						
						ItemStack newItemStack = (new CardboardBox(itemStack)).unbox();
						newItemStack.setAmount(1);
						
						if (!guiBlock.getImports().contains(newItemStack)) {
							
							guiBlock.addImport(newItemStack);
							
						}
						
					} else if (event.getInventory().getTitle().equals(ChatColor.BLUE + "SQTech - Exports")) {
						
						GUIBlock guiBlock = ObjectUtils.getGUIBlockFromGUI(SQTechBase.currentGui.get(event.getPlayer()));
						
						ItemStack newItemStack = (new CardboardBox(itemStack)).unbox();
						newItemStack.setAmount(1);
						
						if (!guiBlock.getExports().contains(newItemStack)) {
							
							guiBlock.addExport(newItemStack);
							
						}
						
					}
					
				}
				
			}
			
			if (SQTechBase.currentGui.get(event.getPlayer()).close) {
				
				SQTechBase.currentGui.remove(event.getPlayer());
				
			}

		}
		
	}
	
}
