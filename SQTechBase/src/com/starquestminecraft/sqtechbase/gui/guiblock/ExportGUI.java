package com.starquestminecraft.sqtechbase.gui.guiblock;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryAction;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import com.starquestminecraft.sqtechbase.SQTechBase;
import com.starquestminecraft.sqtechbase.gui.GUI;
import com.starquestminecraft.sqtechbase.objects.GUIBlock;
import com.starquestminecraft.sqtechbase.util.InventoryUtils;
import com.starquestminecraft.sqtechbase.util.ObjectUtils;

import net.md_5.bungee.api.ChatColor;

public class ExportGUI extends GUI {

	public ExportGUI (Player player, int id) {
		
		super(player, id);
		
	}
	
	@Override	
	public void open() {
		
		Inventory gui = Bukkit.createInventory(owner, 27, ChatColor.BLUE + "SQTech - Exports");
		
		GUIBlock guiBlock = ObjectUtils.getGUIBlockFromGUI(this);
		
		for (ItemStack itemStack : guiBlock.getExports()) {
			
			gui.addItem(itemStack);
			
		}
		
		if (guiBlock.exportAll) {
			
			gui.setItem(25, InventoryUtils.createSpecialItem(Material.EYE_OF_ENDER, (short) 0, "Export All: True", new String[] {ChatColor.RED + "" + ChatColor.MAGIC + "Contraband"}));
			
		} else {
			
			gui.setItem(25, InventoryUtils.createSpecialItem(Material.ENDER_PEARL, (short) 0, "Export All: False", new String[] {ChatColor.RED + "" + ChatColor.MAGIC + "Contraband"}));
			
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

				if (normalItem(event.getInventory().getItem(event.getSlot()))) {
					
					event.setCancelled(false);
					
				} else {
					
					event.setCancelled(true);

				}
								
				if (event.getSlot() != 26 && event.getSlot() != 25) {
					
					if (event.getClickedInventory().getItem(event.getSlot()) == null) {
						
						if (!event.getCursor().getType().equals(Material.AIR)) {
							
							GUIBlock guiBlock = ObjectUtils.getGUIBlockFromGUI(this);
							
							ItemStack itemStack = InventoryUtils.createSpecialItem(event.getCursor().getType(), event.getCursor().getDurability(), "", new String[] {ChatColor.RED + "" + ChatColor.MAGIC + "Contraband"});
							
							if (!guiBlock.getExports().contains(itemStack)) {
								
								guiBlock.addExport(itemStack);
								
								event.getClickedInventory().addItem(itemStack);
								
							}
							
						}
						
					} else {
						
						GUIBlock guiBlock = ObjectUtils.getGUIBlockFromGUI(this);
						
						ItemStack itemStack = event.getClickedInventory().getItem(event.getSlot());
						
						if (guiBlock.getExports().contains(itemStack)) {
							
							guiBlock.removeExport(itemStack);
							
							event.getClickedInventory().setItem(event.getSlot(), new ItemStack(Material.AIR));
							
						}
						
					}
					
				} else {
					
					if (event.getSlot() == 26) {
						
						GUIBlock guiBlock = ObjectUtils.getGUIBlockFromGUI(this);
						
						guiBlock.getGUI(owner).open();
						
					} else if (event.getSlot() == 25) {
						
						GUIBlock guiBlock = ObjectUtils.getGUIBlockFromGUI(this);
						
						if (guiBlock.exportAll) {
							
							event.getClickedInventory().setItem(25, InventoryUtils.createSpecialItem(Material.ENDER_PEARL, (short) 0, "Export All: False", new String[] {ChatColor.RED + "" + ChatColor.MAGIC + "Contraband"}));
							
							guiBlock.exportAll = false;
							
						} else {
							
							event.getClickedInventory().setItem(25, InventoryUtils.createSpecialItem(Material.EYE_OF_ENDER, (short) 0, "Export All: True", new String[] {ChatColor.RED + "" + ChatColor.MAGIC + "Contraband"}));
							
							guiBlock.exportAll = true;
							
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
