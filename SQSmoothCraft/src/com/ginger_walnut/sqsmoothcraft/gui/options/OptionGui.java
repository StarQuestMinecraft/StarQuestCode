package com.ginger_walnut.sqsmoothcraft.gui.options;

import java.util.ArrayList;
import java.util.List;

import net.md_5.bungee.api.ChatColor;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import com.ginger_walnut.sqsmoothcraft.PlayerOptions;
import com.ginger_walnut.sqsmoothcraft.SQSmoothCraft;
import com.ginger_walnut.sqsmoothcraft.gui.Gui;
import com.ginger_walnut.sqsmoothcraft.gui.MainGui;
import com.ginger_walnut.sqsmoothcraft.utils.ShipUtils;

public class OptionGui extends Gui{

	List<Option> options = new ArrayList<Option>();
	List<String> optionNames = new ArrayList<String>();
	
	public OptionGui(Player player) {
		
		super(player);
		
		if (!SQSmoothCraft.currentOptions.containsKey(owner.getUniqueId())) {
			
			SQSmoothCraft.currentOptions.put(owner.getUniqueId(), new PlayerOptions(owner.getUniqueId()));
			
		}
		
		List<String> lore = new ArrayList<String>();
		lore.add("If this is enabled, the direction lock");
		lore.add("is on by default");
		lore.add(ChatColor.RED + "" + ChatColor.MAGIC + "Contraband");
		
		if (SQSmoothCraft.currentOptions.get(owner.getUniqueId()).lockedDirection) {
			
			options.add(new Option(OptionType.BOOLEANTRUE, "Direction Lock", lore));
			
		} else {
			
			options.add(new Option(OptionType.BOOLEANFALSE, "Direction Lock", lore));
			
		}
		
		optionNames.add("Direction Lock");
		
	}
	
	@Override
	public void open() {
		
		Inventory gui = Bukkit.createInventory(owner, 27, ChatColor.BLUE + "SQSmoothCraft - Options");
		
		ItemStack exit = new ItemStack(Material.STAINED_GLASS_PANE);
		exit.setDurability((short) 14);
		
		List<String> exitLore = new ArrayList<String>();
		exitLore.add(ChatColor.RED + "" + ChatColor.MAGIC + "Contraband");
		
		exit = ShipUtils.createSpecialItem(exit, exitLore, "Back");
		
		gui.setItem(22, exit);
		
		ItemStack bars = new ItemStack(Material.IRON_FENCE);
		
		List<String> barLore = new ArrayList<String>();
		barLore.add(ChatColor.RED + "" + ChatColor.MAGIC + "Contraband");
		
		bars = ShipUtils.createSpecialItem(bars, barLore, " ");
		
		gui.setItem(18, bars);
		gui.setItem(19, bars);
		gui.setItem(20, bars);
		gui.setItem(21, bars);
		gui.setItem(23, bars);
		gui.setItem(24, bars);
		gui.setItem(25, bars);
		
		if (options.size() > 10) {
			
			//gui.setItem(26, exit);
			
		} else {
			
			for (int i = 0; i < options.size(); i ++) {
				
				gui.setItem(i, options.get(i).getItem());
				gui.setItem(i + 9, options.get(i).getOption());
				
			}
			
			gui.setItem(26, bars);
			
		}
		
		owner.openInventory(gui);
		
		if (SQSmoothCraft.currentGui.containsKey(owner)) {
			
			SQSmoothCraft.currentGui.remove(owner);
			SQSmoothCraft.currentGui.put(owner,  this);
			
		} else {
			
			SQSmoothCraft.currentGui.put(owner,  this);
			
		}
		
	}
	
	@Override
	public void clicked(String itemName, int slot) {
		
		if (itemName.equals("Back")) {
			
			MainGui gui = new MainGui(owner);
			gui.open();
			
		} else if (!(itemName.equals(" ") || optionNames.contains(itemName))) {
			
			if (options.get(slot - 9).type.equals(OptionType.BOOLEANFALSE)) {
				
				options.get(slot - 9).type = OptionType.BOOLEANTRUE;
				
				if (slot == 9) {
					
					SQSmoothCraft.currentOptions.get(owner.getUniqueId()).lockedDirection = true;
					
				}
	
			} else if (options.get(slot - 9).type.equals(OptionType.BOOLEANTRUE)) {
				
				options.get(slot - 9).type = OptionType.BOOLEANFALSE;
				
				if (slot == 9) {
					
					SQSmoothCraft.currentOptions.get(owner.getUniqueId()).lockedDirection = false;
					
				}

			}
			
			this.open();
			
		}
		
	}
	
}
