package com.martinjonsson01.sqtechpumps;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockFromToEvent;
import org.bukkit.event.block.BlockPhysicsEvent;
import org.bukkit.event.inventory.InventoryClickEvent;

import com.martinjonsson01.sqtechpumps.objects.LargeTank;
import com.martinjonsson01.sqtechpumps.objects.MediumTank;
import com.martinjonsson01.sqtechpumps.objects.Pump;
import com.martinjonsson01.sqtechpumps.objects.SmallTank;
import com.starquestminecraft.sqtechbase.SQTechBase;
import com.starquestminecraft.sqtechbase.objects.Fluid;
import com.starquestminecraft.sqtechbase.objects.GUIBlock;
import com.starquestminecraft.sqtechbase.objects.Machine;
import com.starquestminecraft.sqtechbase.util.ObjectUtils;

import net.countercraft.movecraft.craft.Craft;
import net.countercraft.movecraft.event.CraftReleaseEvent;
import net.countercraft.movecraft.event.CraftSyncTranslateEvent;
import net.countercraft.movecraft.utils.MovecraftLocation;
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
						//e.getWhoClicked().closeInventory();
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
						//e.getWhoClicked().closeInventory();
						machine.getGUI((Player) e.getWhoClicked()).open();
					}
				}

			} else if (e.getSlot() == 16) {
				//Water
				for (Fluid f : SQTechBase.fluids) {
					if (f.name.equals("Water")) {
						machine.maxLiquid.put(f, SQTechPumps.config.getInt("large tank max liquid"));
						machine.liquidImports.add(f);
						//e.getWhoClicked().closeInventory();
						machine.getGUI((Player) e.getWhoClicked()).open();
					}
				}

			}

		}

	}

	@EventHandler
	public void CraftMoveEvent(CraftSyncTranslateEvent e) {
		//CraftAsyncDetectEvent does not work
		//CraftAsyncBlockDetectEvent does not work
		//CraftSyncDetectEvent does not work
		//CraftSyncTranslateEvent works... I guess. When the ship moves, not detects.
		Craft c = e.getCraft();

		if (SQTechPumps.craftHasRemovedWaterInTanksBooleanMap.containsKey(c)) {
			if (SQTechPumps.craftHasRemovedWaterInTanksBooleanMap.get(c)) {
				return;
			}
		}

		for (MovecraftLocation mLoc : c.getBlockList()) {

			Block b = e.getCraft().getW().getBlockAt(mLoc.getX(), mLoc.getY(), mLoc.getZ());

			if (b.getType() == Material.LAPIS_BLOCK) {
				
				if (SQTechPumps.locationMachineMap.get(b.getLocation()) != null) {
					
					Machine m = SQTechPumps.locationMachineMap.get(b.getLocation());

					if (m.getMachineType().getName().equals("Pump")) {
						
						if (SQTechPumps.machineExtendingMap.containsKey(m)) {
							
							if (SQTechPumps.machineExtendingMap.get(m) != null) {
								e.setFailMessage(ChatColor.RED + "Error: Could not pilot craft - The pump is currently extending/retracting. Please wait.");
								e.setCancelled(true);
							}
						}
						
						if (SQTechPumps.pumpingLocationList.contains(m.getGUIBlock().getLocation())) {
							
							Pump.stopPumpingImmediately(m, e.getCraft().pilot);
							
							if (SQTechPumps.machineExtendingMap.containsKey(m)) {
								SQTechPumps.machineExtendingMap.remove(m);
							}
							
						}

					}
					
					if (m.getMachineType().name.equals("Small Tank")) {

						if (SQTechPumps.tankWaterBlocks.get(m) != null) {

							for (Block b2 : SQTechPumps.tankWaterBlocks.get(m)) {

								if (b2.getType() == Material.STATIONARY_LAVA || b2.getType() == Material.STATIONARY_WATER) {
									b2.setType(Material.AIR);
								}

							}
							SQTechPumps.craftHasRemovedWaterInTanksBooleanMap.put(c, true);

						}

					} else if (m.getMachineType().name.equals("Medium Tank")) {

						if (SQTechPumps.tankWaterBlocks.get(m) != null) {

							for (Block b2 : SQTechPumps.tankWaterBlocks.get(m)) {

								if (b2.getType() == Material.STATIONARY_LAVA || b2.getType() == Material.STATIONARY_WATER) {
									b2.setType(Material.AIR);
								}

							}
							SQTechPumps.craftHasRemovedWaterInTanksBooleanMap.put(c, true);

						}

					} else if (m.getMachineType().name.equals("Large Tank")) {

						if (SQTechPumps.tankWaterBlocks.get(m) != null) {
							Bukkit.getServer().broadcastMessage("size: " + SQTechPumps.tankWaterBlocks.get(m).size());
							for (Block b2 : SQTechPumps.tankWaterBlocks.get(m)) {

								if (b2.getType() == Material.STATIONARY_LAVA || b2.getType() == Material.STATIONARY_WATER) {
									b2.setType(Material.AIR);
								}

							}
							SQTechPumps.craftHasRemovedWaterInTanksBooleanMap.put(c, true);

						}

					}

				}

			}

		}

	}

	@EventHandler
	public void CraftReleaseEvent(CraftReleaseEvent e) {

		Craft c = e.getCraft();

		if (SQTechPumps.craftHasRemovedWaterInTanksBooleanMap.containsKey(c)) {
			SQTechPumps.craftHasRemovedWaterInTanksBooleanMap.put(c, false);
		}

		for (MovecraftLocation mLoc : c.getBlockList()) {

			Block b = e.getCraft().getW().getBlockAt(mLoc.getX(), mLoc.getY(), mLoc.getZ());

			if (b.getType() == Material.LAPIS_BLOCK) {

				GUIBlock guiBlock = new GUIBlock(b.getLocation(), true);

				if (ObjectUtils.getMachineFromGUIBlock(guiBlock) != null) {

					Machine m = ObjectUtils.getMachineFromGUIBlock(guiBlock);

					if (m.getMachineType().equals(SmallTank.class)) {

						SmallTank.updatePhysicalLiquid(m);
						return;

					} else if (m.getMachineType().equals(MediumTank.class)) {

						MediumTank.updatePhysicalLiquid(m);
						return;

					} else if (m.getMachineType().equals(LargeTank.class)) {

						LargeTank.updatePhysicalLiquid(m);
						return;

					}

				}

			}

		}


	}
	
	@EventHandler
	public void onBlockBreak(BlockBreakEvent e) {
		
		if (e.getBlock() != null) {
			
			if (SQTechPumps.ironBarList.contains(e.getBlock())) {
				
				e.setCancelled(true);
				
				e.getPlayer().sendMessage(ChatColor.RED + "You are not allowed to break the tube of the pump.");
				
			}
			
		}
		
	}

}
