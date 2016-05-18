package com.ginger_walnut.sqpowertools.objects;

import org.bukkit.ChatColor;
import org.bukkit.inventory.ItemStack;

import com.ginger_walnut.sqpowertools.SQPowerTools;

public class PowerTool {

	public ItemStack itemStack;
	
	public PowerTool(ItemStack itemStack) {
		
		this.itemStack = itemStack;
		
	}
	
	public boolean isPowerTool() {
		
		boolean isPowerTool = false;
		
		if (itemStack != null) {
			
			if (itemStack.hasItemMeta()) {
				
				if (itemStack.getItemMeta().hasLore()) {
					
					if (itemStack.getItemMeta().getLore().contains(ChatColor.DARK_PURPLE + "Power Tool")) {
						
						for (PowerToolType type : SQPowerTools.powerTools) {
							
							if (itemStack.getItemMeta().getLore().contains(ChatColor.DARK_PURPLE + type.name)) {
							
								isPowerTool = true;
								
							}
								
						}
						
					}
					
				}
				
			}
			
		}
		
		return isPowerTool;
		
	}
	
}
