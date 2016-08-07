package com.martinjonsson01.sqtechpumps;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockFromToEvent;
import org.bukkit.event.block.BlockPhysicsEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import com.starquestminecraft.sqtechbase.SQTechBase;
import com.starquestminecraft.sqtechbase.objects.Fluid;
import com.starquestminecraft.sqtechbase.objects.Machine;

import net.md_5.bungee.api.ChatColor;


public class Events implements Listener{
	
	@EventHandler
	public void onLiquidFlow(BlockFromToEvent e) {
		
		if (SQTechPumps.waterBlocks != null) {
			
			for (Machine m : SQTechPumps.waterBlocks.keySet()) {
				
				if (SQTechPumps.waterBlocks.get(m).contains(e.getBlock()) || SQTechPumps.waterBlocks.get(m).contains(e.getToBlock())) {
					
					e.setCancelled(true);
					
				}
				
			}
			
		}
		
	}
	
	@EventHandler
	public void onTankWaterDisappear(BlockPhysicsEvent e) {
		
		if (e.getChangedType() == Material.SAND) return;
		
		if (e.getChangedType() == Material.GRAVEL) return;
		
		for (Machine m : SQTechPumps.tankWaterBlocks.keySet()) {
			
			if (SQTechPumps.tankWaterBlocks.get(m).contains(e.getBlock())) {
				
				e.setCancelled(true);
				
			}
			
		}
		
	}
	
	@EventHandler
	public void onInventoryClick(InventoryClickEvent e) {
		
		if (e.getInventory().getName().equals(ChatColor.BLUE + "SQTech - Choose small tank type")) {
			
			e.setCancelled(true);
			
			Machine machine = SQTechPumps.inventoryMap.get(e.getInventory());
			
			if (e.getSlot() == 10) {
				//Lava
				for (Fluid f : SQTechBase.fluids) {
					if (f.name.equals("Lava")) {
						machine.maxLiquid.put(f, SQTechPumps.config.getInt("small tank max liquid"));
						machine.liquidImports.add(f);
						e.getWhoClicked().closeInventory();
						machine.getGUI((Player) e.getWhoClicked()).open();
					}
				}
				
			} else if (e.getSlot() == 16) {
				//Water
				for (Fluid f : SQTechBase.fluids) {
					if (f.name.equals("Water")) {
						machine.maxLiquid.put(f, SQTechPumps.config.getInt("small tank max liquid"));
						machine.liquidImports.add(f);
						e.getWhoClicked().closeInventory();
						machine.getGUI((Player) e.getWhoClicked()).open();
					}
				}
				
			}
			
		} else if (e.getInventory().getName().equals(ChatColor.BLUE + "SQTech - Choose medium tank type")) {
			
			e.setCancelled(true);
			
			Machine machine = SQTechPumps.inventoryMap.get(e.getInventory());
			
			if (e.getSlot() == 10) {
				//Lava
				for (Fluid f : SQTechBase.fluids) {
					if (f.name.equals("Lava")) {
						machine.maxLiquid.put(f, SQTechPumps.config.getInt("medium tank max liquid"));
						machine.liquidImports.add(f);
						e.getWhoClicked().closeInventory();
						machine.getGUI((Player) e.getWhoClicked()).open();
					}
				}
				
			} else if (e.getSlot() == 16) {
				//Water
				for (Fluid f : SQTechBase.fluids) {
					if (f.name.equals("Water")) {
						machine.maxLiquid.put(f, SQTechPumps.config.getInt("medium tank max liquid"));
						machine.liquidImports.add(f);
						e.getWhoClicked().closeInventory();
						machine.getGUI((Player) e.getWhoClicked()).open();
					}
				}
				
			}
			
		} else if (e.getInventory().getName().equals(ChatColor.BLUE + "SQTech - Choose large tank type")) {
			
			e.setCancelled(true);
			
			Machine machine = SQTechPumps.inventoryMap.get(e.getInventory());
			
			if (e.getSlot() == 10) {
				//Lava
				for (Fluid f : SQTechBase.fluids) {
					if (f.name.equals("Lava")) {
						machine.maxLiquid.put(f, SQTechPumps.config.getInt("large tank max liquid"));
						machine.liquidImports.add(f);
						e.getWhoClicked().closeInventory();
						machine.getGUI((Player) e.getWhoClicked()).open();
					}
				}
				
			} else if (e.getSlot() == 16) {
				//Water
				for (Fluid f : SQTechBase.fluids) {
					if (f.name.equals("Water")) {
						machine.maxLiquid.put(f, SQTechPumps.config.getInt("large tank max liquid"));
						machine.liquidImports.add(f);
						e.getWhoClicked().closeInventory();
						machine.getGUI((Player) e.getWhoClicked()).open();
					}
				}
				
			}
			
		}
		
	}
	
}
