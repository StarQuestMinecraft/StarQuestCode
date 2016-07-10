package com.starquestminecraft.sqtechbase.gui;

import net.md_5.bungee.api.ChatColor;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryAction;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import com.starquestminecraft.sqtechbase.GUIBlock;
import com.starquestminecraft.sqtechbase.Machine;
import com.starquestminecraft.sqtechbase.Network;
import com.starquestminecraft.sqtechbase.PlayerOptions;
import com.starquestminecraft.sqtechbase.SQTechBase;
import com.starquestminecraft.sqtechbase.util.InventoryUtils;

public class GUIBlockGUI extends GUI {

	public GUIBlockGUI() {
		
	}
	
	@Override	
	public void open(Player player) {
		
		owner = player;
		
		GUIBlock guiBlock = null;
		
		for (Network listNetwork : SQTechBase.networks) {
			
			for (GUIBlock networkGUIBlock : listNetwork.getGUIBlocks()) {
				
				if (networkGUIBlock.getGUI() == this) {
					
					guiBlock = networkGUIBlock;
					
				}
				
			}
			
		}
		
		Machine machine = null;
		
		for (Machine listMachine : SQTechBase.machines) {
			
			if (listMachine.getGUIBlock().equals(guiBlock)) {
				
				machine = listMachine;
				
			}
			
		}
		
		if (machine != null) {
			
			if (SQTechBase.currentGui.get(player) != machine.getGUI()) {
				
				if (!SQTechBase.currentOptions.containsKey(player.getUniqueId())) {
					
					SQTechBase.currentOptions.put(owner.getUniqueId(), new PlayerOptions());
					
				}
				
				if (SQTechBase.currentOptions.get(player.getUniqueId()).machineGUI) {
					
					machine.getGUI().open(owner);
					return;
					
				}
				
			}
			
		}
		
		Inventory gui = Bukkit.createInventory(owner, 27, ChatColor.BLUE + "SQTech");

		gui.setItem(0, InventoryUtils.createSpecialItem(Material.CHEST, (short) 0, "Exports", new String[] {ChatColor.RED + "" + ChatColor.MAGIC + "Contraband"}));
		gui.setItem(1, InventoryUtils.createSpecialItem(Material.CHEST, (short) 0, "Imports", new String[] {ChatColor.RED + "" + ChatColor.MAGIC + "Contraband"}));
		
		if (machine != null) {
			
			gui.setItem(8, InventoryUtils.createSpecialItem(Material.REDSTONE, (short) 0, "Machine", new String[] {ChatColor.RED + "" + ChatColor.MAGIC + "Contraband"}));
			
			if (machine.getMachineType().getMaxEnergy() > 0) {
				
				if (machine.exportsEnergy) {
					
					gui.setItem(9, InventoryUtils.createSpecialItem(Material.STAINED_GLASS, (short) 5, "Exports Energy: True", new String[] {ChatColor.RED + "" + ChatColor.MAGIC + "Contraband"}));
				
				} else {
					
					gui.setItem(9, InventoryUtils.createSpecialItem(Material.STAINED_GLASS, (short) 14, "Exports Energy: False", new String[] {ChatColor.RED + "" + ChatColor.MAGIC + "Contraband"}));
					
				}
				
				if (machine.importsEnergy) {
					
					gui.setItem(10, InventoryUtils.createSpecialItem(Material.STAINED_GLASS, (short) 5, "Imports Energy: True", new String[] {ChatColor.RED + "" + ChatColor.MAGIC + "Contraband"}));
				
				} else {
					
					gui.setItem(10, InventoryUtils.createSpecialItem(Material.STAINED_GLASS, (short) 14, "Imports Energy: False", new String[] {ChatColor.RED + "" + ChatColor.MAGIC + "Contraband"}));
					
				}
				
			}
	
		}
		
		owner.openInventory(gui);
		
		if (SQTechBase.currentGui.containsKey(owner)) {
			
			SQTechBase.currentGui.remove(owner);
			SQTechBase.currentGui.put(owner,  this);
			
		} else {
			
			SQTechBase.currentGui.put(owner,  this);
			
		}
		
	}

	@Override
	public void click(InventoryClickEvent event) {
		
		if (event.getClickedInventory().getTitle().startsWith(ChatColor.BLUE + "SQTech")) {
			
			event.setCancelled(true);
			
			ItemStack clickedItem = event.getInventory().getItem(event.getSlot());
			
			boolean normalItem = true;
			
			if (clickedItem == null) {
				
				normalItem = false;
				
			} else {
				
				if (clickedItem.hasItemMeta()) {
					
					if (clickedItem.getItemMeta().hasLore()) {
						
						if (clickedItem.getItemMeta().getLore().contains(ChatColor.RED + "" + ChatColor.MAGIC + "Contraband")) {
							
							normalItem = false;
							
						}
						
					}
					
				}
				
			}
			
			if (normalItem) {
				
				event.setCancelled(false);
				
			}
			
			if (event.getClickedInventory().getTitle().equals(ChatColor.BLUE + "SQTech")) {
				
				if (event.getSlot() == 0) {
					
					close = false;
					
					Inventory gui = Bukkit.createInventory(owner, 27, ChatColor.BLUE + "SQTech - Exports");
					
					GUIBlock guiBlock = null;
					
					for (Network listNetwork : SQTechBase.networks) {
						
						for (GUIBlock guiBlock2 : listNetwork.getGUIBlocks()) {
							
							if (guiBlock2.getGUI() == this) {
								
								guiBlock = guiBlock2;
								
							}
							
						}
						
					}
					
					for (ItemStack itemStack : guiBlock.getExports()) {
						
						gui.addItem(itemStack);
						
					}
					
					owner.openInventory(gui);
					
					close = true;
					
				} else if (event.getSlot() == 1) {
					
					close = false;
					
					Inventory gui = Bukkit.createInventory(owner, 27, ChatColor.BLUE + "SQTech - Imports");
					
					GUIBlock guiBlock = null;
					
					for (Network listNetwork : SQTechBase.networks) {
						
						for (GUIBlock guiBlock2 : listNetwork.getGUIBlocks()) {
							
							if (guiBlock2.getGUI() == this) {
								
								guiBlock = guiBlock2;
								
							}
							
						}
						
					}
					
					for (ItemStack itemStack : guiBlock.getImports()) {
						
						gui.addItem(itemStack);
						
					}
					
					owner.openInventory(gui);
					
					close = true;
					
				} else if (event.getSlot() == 8) {
					
					if (event.getInventory().getItem(event.getSlot()) != null) {
						
						Machine machine = null;
						
						for (Machine listMachine : SQTechBase.machines) {
							
							if (listMachine.getGUIBlock().getGUI() == this) {
								
								machine = listMachine;
								
							}
							
						}
						
						if (machine != null) {
							
							machine.getGUI().open(owner);
						
						}
							
					}
					
				} else if (event.getSlot() == 9) {
					
					GUIBlock guiBlock = null;
					
					for (Network listNetwork : SQTechBase.networks) {
						
						for (GUIBlock networkGUIBlock : listNetwork.getGUIBlocks()) {
							
							if (networkGUIBlock.getGUI() == this) {
								
								guiBlock = networkGUIBlock;
								
							}
							
						}
						
					}
					
					Machine machine = null;
					
					for (Machine listMachine : SQTechBase.machines) {
						
						if (listMachine.getGUIBlock().equals(guiBlock)) {
							
							machine = listMachine;
							
						}
						
					}
					
					if (machine != null) {
						
						machine.exportsEnergy = !machine.exportsEnergy;
						
						Inventory gui = event.getClickedInventory();
						
						if (machine.exportsEnergy) {
							
							gui.setItem(9, InventoryUtils.createSpecialItem(Material.STAINED_GLASS, (short) 5, "Exports Energy: True", new String[] {ChatColor.RED + "" + ChatColor.MAGIC + "Contraband"}));
						
						} else {
							
							gui.setItem(9, InventoryUtils.createSpecialItem(Material.STAINED_GLASS, (short) 14, "Exports Energy: False", new String[] {ChatColor.RED + "" + ChatColor.MAGIC + "Contraband"}));
							
						}
						
					}
					
				} else if (event.getSlot() == 10) {
					
					GUIBlock guiBlock = null;
					
					for (Network listNetwork : SQTechBase.networks) {
						
						for (GUIBlock networkGUIBlock : listNetwork.getGUIBlocks()) {
							
							if (networkGUIBlock.getGUI() == this) {
								
								guiBlock = networkGUIBlock;
								
							}
							
						}
						
					}
					
					Machine machine = null;
					
					for (Machine listMachine : SQTechBase.machines) {
						
						if (listMachine.getGUIBlock().equals(guiBlock)) {
							
							machine = listMachine;
							
						}
						
					}
					
					if (machine != null) {
						
						machine.importsEnergy = !machine.importsEnergy;
						
						Inventory gui = event.getClickedInventory();
						
						if (machine.importsEnergy) {
							
							gui.setItem(10, InventoryUtils.createSpecialItem(Material.STAINED_GLASS, (short) 5, "Imports Energy: True", new String[] {ChatColor.RED + "" + ChatColor.MAGIC + "Contraband"}));
						
						} else {
							
							gui.setItem(10, InventoryUtils.createSpecialItem(Material.STAINED_GLASS, (short) 14, "Imports Energy: False", new String[] {ChatColor.RED + "" + ChatColor.MAGIC + "Contraband"}));
							
						}
						
					}
					
				}
				
			} else if (event.getClickedInventory().getTitle().equals(ChatColor.BLUE + "SQTech - Exports")) {
				
				if (event.getSlot() != 260) {
					
					if (event.getClickedInventory().getItem(event.getSlot()) == null) {
						
						if (!event.getCursor().getType().equals(Material.AIR)) {
							
							GUIBlock guiBlock = null;
							
							for (Network listNetwork : SQTechBase.networks) {
								
								for (GUIBlock guiBlock2 : listNetwork.getGUIBlocks()) {
									
									if (guiBlock2.getGUI() == this) {
										
										guiBlock = guiBlock2;
										
									}
									
								}
								
							}
							
							ItemStack itemStack = InventoryUtils.createSpecialItem(event.getCursor().getType(), event.getCursor().getDurability(), "", new String[] {ChatColor.RED + "" + ChatColor.MAGIC + "Contraband"});
							
							if (!guiBlock.getExports().contains(itemStack)) {
								
								guiBlock.addExport(itemStack);
								
								event.getClickedInventory().addItem(itemStack);
								
							}
							
						}
						
					} else {
						
						GUIBlock guiBlock = null;
						
						for (Network listNetwork : SQTechBase.networks) {
							
							for (GUIBlock guiBlock2 : listNetwork.getGUIBlocks()) {
								
								if (guiBlock2.getGUI() == this) {
									
									guiBlock = guiBlock2;
									
								}
								
							}
							
						}
						
						ItemStack itemStack = event.getClickedInventory().getItem(event.getSlot());
						
						if (guiBlock.getExports().contains(itemStack)) {
							
							guiBlock.removeExport(itemStack);
							
							event.getClickedInventory().setItem(event.getSlot(), new ItemStack(Material.AIR));
							
						}
						
					}
					
				}
				
			} else if (event.getClickedInventory().getTitle().equals(ChatColor.BLUE + "SQTech - Imports")) {
				
				if (event.getSlot() != 260) {
					
					if (event.getClickedInventory().getItem(event.getSlot()) == null) {
						
						if (!event.getCursor().getType().equals(Material.AIR)) {
							
							GUIBlock guiBlock = null;
							
							for (Network listNetwork : SQTechBase.networks) {
								
								for (GUIBlock guiBlock2 : listNetwork.getGUIBlocks()) {
									
									if (guiBlock2.getGUI() == this) {
										
										guiBlock = guiBlock2;
										
									}
									
								}
								
							}
							
							ItemStack itemStack = InventoryUtils.createSpecialItem(event.getCursor().getType(), event.getCursor().getDurability(), "", new String[] {ChatColor.RED + "" + ChatColor.MAGIC + "Contraband"});
							
							if (!guiBlock.getImports().contains(itemStack)) {
								
								guiBlock.addImport(itemStack);
								
								event.getClickedInventory().addItem(itemStack);
								
							}
							
						}

					} else {
					        
						GUIBlock guiBlock = null;
					
						for (Network listNetwork : SQTechBase.networks) {
							
							for (GUIBlock guiBlock2 : listNetwork.getGUIBlocks()) {
								
								if (guiBlock2.getGUI() == this) {
									
									guiBlock = guiBlock2;
									
								}
								
							}
							
						}
						
						ItemStack itemStack = event.getClickedInventory().getItem(event.getSlot());
						
						if (guiBlock.getImports().contains(itemStack)) {
							
							guiBlock.removeImport(itemStack);
							
							event.getClickedInventory().clear();
							
							for (ItemStack importItem : guiBlock.getImports()) {
								
								event.getClickedInventory().addItem(importItem);
								
							}
							
							
						}
						
					}
					
				}
				
			}
			
		} else {
			
			if (event.getAction().equals(InventoryAction.MOVE_TO_OTHER_INVENTORY)) {
				
				event.setCancelled(true);
				
			}

		}
		
	}
	
}
