package com.starquestminecraft.sqtechbase.gui.guiblock;

import net.md_5.bungee.api.ChatColor;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryAction;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;

import com.starquestminecraft.sqtechbase.SQTechBase;
import com.starquestminecraft.sqtechbase.gui.GUI;
import com.starquestminecraft.sqtechbase.objects.GUIBlock;
import com.starquestminecraft.sqtechbase.objects.Machine;
import com.starquestminecraft.sqtechbase.objects.PlayerOptions;
import com.starquestminecraft.sqtechbase.util.InventoryUtils;
import com.starquestminecraft.sqtechbase.util.ObjectUtils;

public class GUIBlockGUI extends GUI {
	
	public GUIBlockGUI(Player player, int id) {
		
		super(player, id);
		
	}
	
	@Override	
	public void open() {
		
		GUIBlock guiBlock = ObjectUtils.getGUIBlockFromGUI(this);

		Machine machine = ObjectUtils.getMachineFromGUIBlock(guiBlock);
		
		if (machine != null) {
			
			boolean fromNoWhere = false;
			
			if (SQTechBase.currentGui.get(owner) == null) {
				
				fromNoWhere = true;				
			}
			
			if (fromNoWhere) {
				
				if (!SQTechBase.currentOptions.containsKey(owner.getUniqueId())) {
					
					SQTechBase.currentOptions.put(owner.getUniqueId(), new PlayerOptions());
					
				}
				
				if (SQTechBase.currentOptions.get(owner.getUniqueId()).machineGUI) {
					
					machine.getGUI(owner).open();
					return;
					
				}
				
			} else if (SQTechBase.currentGui.get(owner).id != id) {
					
				if (!SQTechBase.currentOptions.containsKey(owner.getUniqueId())) {
					
					SQTechBase.currentOptions.put(owner.getUniqueId(), new PlayerOptions());
					
				}
				
				if (SQTechBase.currentOptions.get(owner.getUniqueId()).machineGUI) {
					
					machine.getGUI(owner).open();
					return;
					
				}
				
			}

		}
		
		Inventory gui = Bukkit.createInventory(owner, 27, ChatColor.BLUE + "SQTech");

		gui.setItem(0, InventoryUtils.createSpecialItem(Material.CHEST, (short) 0, "Exports", new String[] {ChatColor.RED + "" + ChatColor.MAGIC + "Contraband"}));
		gui.setItem(1, InventoryUtils.createSpecialItem(Material.CHEST, (short) 0, "Imports", new String[] {ChatColor.RED + "" + ChatColor.MAGIC + "Contraband"}));
		
		if (machine != null) {
			
			gui.setItem(26, InventoryUtils.createSpecialItem(Material.SPONGE, (short) 0, "Machine", new String[] {ChatColor.RED + "" + ChatColor.MAGIC + "Contraband"}));
			
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
			
			if (machine.maxLiquid.size() > 0) { 
				
				gui.setItem(18, InventoryUtils.createSpecialItem(Material.BUCKET, (short) 0, "Liquid Exports", new String[] {ChatColor.RED + "" + ChatColor.MAGIC + "Contraband"}));
				gui.setItem(19, InventoryUtils.createSpecialItem(Material.WATER_BUCKET, (short) 0, "Liquid Imports", new String[] {ChatColor.RED + "" + ChatColor.MAGIC + "Contraband"}));
				
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
		
		if (event.getClickedInventory() != null) {
			
			if (event.getClickedInventory().getTitle().startsWith(ChatColor.BLUE + "SQTech")) {
				
				if (normalItem(event.getInventory().getItem(event.getSlot()))) {
					
					event.setCancelled(false);
					
				} else {
					
					event.setCancelled(true);

					
				}
					
				if (event.getSlot() == 0) {
					
					new ExportGUI(owner, id).open();
					
				} else if (event.getSlot() == 1) {
					
					new ImportGUI(owner, id).open();
					
				} else if (event.getSlot() == 26) {
					
					if (event.getInventory().getItem(event.getSlot()) != null) {
						
						Machine machine = ObjectUtils.getMachineFromGUIBlock(ObjectUtils.getGUIBlockFromGUI(this));
						
						if (machine != null) {
							
							machine.getGUI(owner).open();
						
						}
							
					}
					
				} else if (event.getSlot() == 9) {
					
					GUIBlock guiBlock = ObjectUtils.getGUIBlockFromGUI(this);
					
					Machine machine = ObjectUtils.getMachineFromGUIBlock(guiBlock);
					
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
					
					GUIBlock guiBlock = ObjectUtils.getGUIBlockFromGUI(this);
					
					Machine machine = ObjectUtils.getMachineFromGUIBlock(guiBlock);
					
					if (machine != null) {
						
						machine.importsEnergy = !machine.importsEnergy;
						
						Inventory gui = event.getClickedInventory();
						
						if (machine.importsEnergy) {
							
							gui.setItem(10, InventoryUtils.createSpecialItem(Material.STAINED_GLASS, (short) 5, "Imports Energy: True", new String[] {ChatColor.RED + "" + ChatColor.MAGIC + "Contraband"}));
						
						} else {
							
							gui.setItem(10, InventoryUtils.createSpecialItem(Material.STAINED_GLASS, (short) 14, "Imports Energy: False", new String[] {ChatColor.RED + "" + ChatColor.MAGIC + "Contraband"}));
							
						}
						
					}
					
				} else if (event.getSlot() == 18) {
					
					Machine machine = ObjectUtils.getMachineFromGUIBlock(ObjectUtils.getGUIBlockFromGUI(this));
					
					if (machine.maxLiquid.size() > 0) { 
					
						new LiquidExportGUI(owner, id).open();
						
					}
					
				} else if (event.getSlot() == 19) {
					
					Machine machine = ObjectUtils.getMachineFromGUIBlock(ObjectUtils.getGUIBlockFromGUI(this));
					
					if (machine.maxLiquid.size() > 0) { 
					
						new LiquidImportGUI(owner, id).open();
						
					}
					
				}
					
			} else {
				
				if (event.getAction().equals(InventoryAction.MOVE_TO_OTHER_INVENTORY)) {
					
					event.setCancelled(true);
					
				}

			}
			
		}
		
	}
	
}
