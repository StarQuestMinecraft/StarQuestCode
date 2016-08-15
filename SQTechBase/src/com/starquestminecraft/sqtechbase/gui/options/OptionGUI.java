package com.starquestminecraft.sqtechbase.gui.options;

import java.util.ArrayList;
import java.util.List;

import net.md_5.bungee.api.ChatColor;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryAction;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import com.starquestminecraft.sqtechbase.SQTechBase;
import com.starquestminecraft.sqtechbase.gui.GUI;
import com.starquestminecraft.sqtechbase.objects.PlayerOptions;

public class OptionGUI extends GUI {

	List<Option> options = new ArrayList<Option>();
	List<String> optionNames = new ArrayList<String>();
	
	public OptionGUI(Player player) {
		
		super(player, 0);
	
		if (!SQTechBase.currentOptions.containsKey(owner.getUniqueId())) {
			
			SQTechBase.currentOptions.put(owner.getUniqueId(), new PlayerOptions());
			
		}
		
		List<String> lore = new ArrayList<String>();
		lore.add("If this is enabled, the machine GUI");
		lore.add("shows first");
		lore.add(ChatColor.RED + "" + ChatColor.MAGIC + "Contraband");
		
		if (SQTechBase.currentOptions.get(owner.getUniqueId()).machineGUI) {
			
			options.add(new Option(OptionType.BOOLEANTRUE, "Machine GUI", lore));
			
		} else {
			
			options.add(new Option(OptionType.BOOLEANFALSE, "Machine GUI", lore));
			
		}
		
		optionNames.add("Machine GUI");
		
	}
	
	@Override
	public void open() {
		
		Inventory gui = Bukkit.createInventory(owner, 18, ChatColor.BLUE + "SQTech - Options");
		
		if (options.size() > 10) {
			
		} else {
			
			for (int i = 0; i < options.size(); i ++) {
				
				gui.setItem(i, options.get(i).getItem());
				gui.setItem(i + 9, options.get(i).getOption());
				
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
				
				String itemName = "";
				int slot = event.getSlot();
				
				if (event.getCurrentItem().hasItemMeta()) {
					
					itemName = event.getCurrentItem().getItemMeta().getDisplayName();
					
				}

				if (!(itemName.equals(" ") || optionNames.contains(itemName))) {
					
					if (options.get(slot - 9).type.equals(OptionType.BOOLEANFALSE)) {
						
						options.get(slot - 9).type = OptionType.BOOLEANTRUE;
						
						if (slot == 9) {
							
							SQTechBase.currentOptions.get(owner.getUniqueId()).machineGUI = true;
							
						}
			
					} else if (options.get(slot - 9).type.equals(OptionType.BOOLEANTRUE)) {
						
						options.get(slot - 9).type = OptionType.BOOLEANFALSE;
						
						if (slot == 9) {
							
							SQTechBase.currentOptions.get(owner.getUniqueId()).machineGUI = false;
							
						}

					}
					
					this.open();
					
				}
				
			} else {
				
				if (event.getAction().equals(InventoryAction.MOVE_TO_OTHER_INVENTORY)) {
					
					event.setCancelled(true);
					
				}

			}
			
		}
		
	}
	
}
