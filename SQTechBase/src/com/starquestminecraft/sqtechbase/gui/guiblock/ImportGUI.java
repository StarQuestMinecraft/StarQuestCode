package com.starquestminecraft.sqtechbase.gui.guiblock;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryAction;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import com.dibujaron.cardboardbox.CardboardBox;
import com.starquestminecraft.sqtechbase.SQTechBase;
import com.starquestminecraft.sqtechbase.gui.GUI;
import com.starquestminecraft.sqtechbase.objects.GUIBlock;
import com.starquestminecraft.sqtechbase.util.InventoryUtils;
import com.starquestminecraft.sqtechbase.util.ObjectUtils;

import net.md_5.bungee.api.ChatColor;

public class ImportGUI extends GUI {

	public ImportGUI (Player player, int id) {
		
		super(player, id);
		
	}
	
	@Override	
	public void open() {
		
		Inventory gui = Bukkit.createInventory(owner, 27, ChatColor.BLUE + "SQTech - Imports");
		
		GUIBlock guiBlock = ObjectUtils.getGUIBlockFromGUI(this);
		
		for (ItemStack itemStack : guiBlock.getImports()) {
			
			ItemStack newItemStack = itemStack.clone();
			ItemMeta itemMeta = newItemStack.getItemMeta();
			List<String> lore = new ArrayList<String>();
			
			if (itemMeta.hasLore()) {
				
				lore = itemMeta.getLore();
				
			}
			
			lore.add(ChatColor.RED + "" + ChatColor.MAGIC + "Contraband");
			itemMeta.setLore(lore);
			newItemStack.setItemMeta(itemMeta);
			
			gui.addItem(newItemStack);
			
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
								
				if (event.getSlot() != 26) {
					
					if (event.getClickedInventory().getItem(event.getSlot()) == null) {
						
						if (!event.getCursor().getType().equals(Material.AIR)) {
							
							GUIBlock guiBlock = ObjectUtils.getGUIBlockFromGUI(this);
							
							ItemStack itemStack = (new CardboardBox(event.getCursor())).unbox();
							itemStack.setAmount(1);
							
							if (!guiBlock.getImports().contains(itemStack)) {
								
								guiBlock.addImport(itemStack);
								
								ItemStack newItemStack = itemStack.clone();
								ItemMeta itemMeta = newItemStack.getItemMeta();
								List<String> lore = new ArrayList<String>();
								
								if (itemMeta.hasLore()) {
									
									lore = itemMeta.getLore();
									
								}
								
								lore.add(ChatColor.RED + "" + ChatColor.MAGIC + "Contraband");
								itemMeta.setLore(lore);
								newItemStack.setItemMeta(itemMeta);
								
								event.getClickedInventory().addItem(newItemStack);
								
							}
							
						}
						
					} else {
						
						GUIBlock guiBlock = ObjectUtils.getGUIBlockFromGUI(this);
						
						ItemStack itemStack = (new CardboardBox(event.getClickedInventory().getItem(event.getSlot()))).unbox();
						itemStack.setAmount(1);
						
						if (itemStack.hasItemMeta()) {
							
							if (itemStack.getItemMeta().hasLore()) {
								
								if (itemStack.getItemMeta().getLore().get(itemStack.getItemMeta().getLore().size() - 1).equals(ChatColor.RED + "" + ChatColor.MAGIC + "Contraband")) {
									
									List<String> lore = itemStack.getItemMeta().getLore();
									lore.remove(lore.size() - 1);
									
									ItemMeta itemMeta = itemStack.getItemMeta();
									itemMeta.setLore(lore);
									
									itemStack.setItemMeta(itemMeta);
									
								}
								
							}
							
						}
						
						if (guiBlock.getImports().contains(itemStack)) {
							
							guiBlock.removeImport(itemStack);
							
							event.getClickedInventory().setItem(event.getSlot(), new ItemStack(Material.AIR));
							
						}
						
					}
					
				} else {
					
					GUIBlock guiBlock = ObjectUtils.getGUIBlockFromGUI(this);
					
					guiBlock.getGUI(owner).open();
					
				}
				
			} else {
				
				if (event.getAction().equals(InventoryAction.MOVE_TO_OTHER_INVENTORY)) {
					
					event.setCancelled(true);
					
				}

			}
			
		}
		
	}
	
}
