package com.starquestminecraft.sqtechbase.gui.guiblock;

import java.util.ArrayList;
import java.util.List;

import net.md_5.bungee.api.ChatColor;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryAction;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import com.starquestminecraft.sqtechbase.SQTechBase;
import com.starquestminecraft.sqtechbase.gui.GUI;
import com.starquestminecraft.sqtechbase.objects.Fluid;
import com.starquestminecraft.sqtechbase.objects.GUIBlock;
import com.starquestminecraft.sqtechbase.objects.Machine;
import com.starquestminecraft.sqtechbase.util.InventoryUtils;
import com.starquestminecraft.sqtechbase.util.ObjectUtils;

public class LiquidExportGUI extends GUI {
	
	public LiquidExportGUI(Player player, int id) {
		
		super(player, id);
		
	}
	
	@Override
	public void open() {
		
		Inventory gui = Bukkit.createInventory(owner, 27, ChatColor.BLUE + "SQTech - Liquid Exports");
		
		Machine machine = ObjectUtils.getMachineFromGUIBlock(ObjectUtils.getGUIBlockFromGUI(this));
		
		List<Fluid> fluids = new ArrayList<Fluid>();
		
		fluids.addAll(machine.maxLiquid.keySet());
		
		for (int i = 0; i < fluids.size(); i ++) {
				
			gui.setItem(i, fluids.get(i).getItem());
			gui.setItem(i + 9, Fluid.getOption(machine.liquidExports.contains(fluids.get(i))));
			
		}
		
		for (int i = 0; i < 8; i ++) {
			
			gui.setItem(i + 18, InventoryUtils.createSpecialItem(Material.IRON_FENCE, (short) 0, " ", new String[] {ChatColor.RED + "" + ChatColor.MAGIC + "Contraband"}));
			
		}
		
		gui.setItem(26, InventoryUtils.createSpecialItem(Material.WOOD_DOOR, (short) 0, "Back", new String[] {ChatColor.RED + "" + ChatColor.MAGIC + "Contraband"}));
		
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

				if (event.getSlot() > 8) {
					
					Machine machine = ObjectUtils.getMachineFromGUIBlock(ObjectUtils.getGUIBlockFromGUI(this));
					
					List<Fluid> fluids = new ArrayList<Fluid>();
					
					fluids.addAll(machine.maxLiquid.keySet());
					
					if (fluids.size() + 8 >= event.getSlot()) {

						if (machine.liquidExports.contains(fluids.get(event.getSlot() - 9))) {
							
							machine.liquidExports.remove(fluids.get(event.getSlot() - 9));
							
						} else {
							
							machine.liquidExports.add(fluids.get(event.getSlot() - 9));
							
						}
						
						event.getClickedInventory().setItem(event.getSlot(), Fluid.getOption(machine.liquidExports.contains(fluids.get(event.getSlot() - 9))));
						
					} else {
						
						if (event.getSlot() == 26) {
									
							GUIBlock guiBlock = ObjectUtils.getGUIBlockFromGUI(this);
									
							guiBlock.getGUI(owner).open();
							
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
	
}
