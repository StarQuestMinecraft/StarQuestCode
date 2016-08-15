package com.ginger_walnut.sqpowertools.gui;

import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import com.ginger_walnut.sqpowertools.SQPowerTools;
import com.ginger_walnut.sqpowertools.objects.PowerTool;
import com.ginger_walnut.sqpowertools.objects.PowerToolType;

import net.md_5.bungee.api.ChatColor;

public class ModifierListGUI extends GUI{

	public ModifierListGUI() {
		
	}
	
	@Override
	public void open(Player player) {
		
		owner = player;
		
		Inventory gui = Bukkit.createInventory(owner, 54, "Power Tool Mods");	
		
		for (PowerToolType type : SQPowerTools.powerTools) {
			
			ItemStack powerTool = (new PowerTool(type)).getItem();
				
			ItemMeta itemMeta = powerTool.getItemMeta();
				
			List<String> lore = itemMeta.getLore();
				
			lore.add(ChatColor.RED + "" + ChatColor.MAGIC + "Contraband");
				
			itemMeta.setLore(lore);
				
			powerTool.setItemMeta(itemMeta);
				
			gui.addItem(powerTool);

		}
		
		owner.openInventory(gui);
		
		if (SQPowerTools.currentGui.containsKey(owner)) {
			
			SQPowerTools.currentGui.remove(owner);
			SQPowerTools.currentGui.put(owner,  this);
			
		} else {
			
			SQPowerTools.currentGui.put(owner,  this);
			
		}
		
	}
	
	@Override
	public void click(InventoryClickEvent event) {
		
		event.setCancelled(true);
		
		if (event.getClickedInventory().getTitle().equals("Power Tool Mods")) {
			
			if (event.getClickedInventory().getItem(event.getSlot()) != null) {
				
				ModifiersGUI gui = new ModifiersGUI();
				gui.open(owner, event.getClickedInventory().getItem(event.getSlot()));
				
			}
			
		}
		
	}
	
}
