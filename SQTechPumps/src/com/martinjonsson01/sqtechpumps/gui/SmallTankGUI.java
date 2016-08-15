package com.martinjonsson01.sqtechpumps.gui;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryAction;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import com.martinjonsson01.sqtechpumps.SQTechPumps;
import com.martinjonsson01.sqtechpumps.objects.SmallTank;
import com.starquestminecraft.sqtechbase.SQTechBase;
import com.starquestminecraft.sqtechbase.gui.GUI;
import com.starquestminecraft.sqtechbase.objects.Fluid;
import com.starquestminecraft.sqtechbase.objects.Machine;
import com.starquestminecraft.sqtechbase.util.InventoryUtils;
import com.starquestminecraft.sqtechbase.util.ObjectUtils;

import net.md_5.bungee.api.ChatColor;

public class SmallTankGUI extends GUI{
	
	public SmallTankGUI(Player player, int id) {
		super(player, id);
	}

	@Override
	public void open() {
		
		Inventory gui = Bukkit.createInventory(owner, 27, ChatColor.BLUE + "SQTech - Small Tank");

		Machine machine = ObjectUtils.getMachineFromMachineGUI(this);

		//Needed for SQTechPumps.java @ line 61
		SQTechPumps.machinePlayerMap.put(machine, owner);
		
		Boolean firstOpen = true;
		
		Fluid fluid = null;
		
		for (Fluid f : SQTechBase.fluids) {
			if (machine.getMaxLiquid(f) > 0) {
				firstOpen = false;
				fluid = f;
			}
		}
		
		if (firstOpen) {
			SmallTankTypeGUI tankTypeGUI = new SmallTankTypeGUI(machine, owner);
			tankTypeGUI.open();
			return;
		}
		
		//Energy button
		int amount = 0;
		String name = "none";
		
		if (fluid != null) {
			amount = machine.getLiquid(fluid);
			name = fluid.name;
		}
		
		String[] energyLore = new String[] {
				ChatColor.DARK_PURPLE + "Energy: Tanks do not require energy.",
				ChatColor.RED + "" + ChatColor.MAGIC + "Contraband"
		};
		gui.setItem(8, InventoryUtils.createSpecialItem(Material.REDSTONE, (short) 0, ChatColor.BLUE + "Tank Energy", energyLore));

		//Info button
		String[] infoLore = new String[] {
				ChatColor.BLUE + "This tank can store " + name,
				ChatColor.BLUE + "Amount in millibuckets: " + PumpGUI.format(amount) + "/" + PumpGUI.format(SQTechPumps.config.getInt("small tank max liquid")),
				ChatColor.BLUE + "Amount in buckets: " + PumpGUI.format(amount/1000) + "/" + PumpGUI.format(SQTechPumps.config.getInt("small tank max liquid")/1000),
				ChatColor.RED + "" + ChatColor.MAGIC + "Contraband"
		};
		gui.setItem(17, InventoryUtils.createSpecialItem(Material.BUCKET, (short) 0, ChatColor.BLUE + "Tank Info", infoLore));

		//Bucket button
		String[] bucketLore = new String[] {
				ChatColor.GOLD + "V Below V",
				ChatColor.RED + "" + ChatColor.MAGIC + "Contraband"
		};
		gui.setItem(4, InventoryUtils.createSpecialItem(Material.WATER_BUCKET, (short) 0, ChatColor.GOLD + "Place Bucket", bucketLore));

		//Iron bars
		gui.setItem(3, InventoryUtils.createSpecialItem(Material.IRON_FENCE, (short) 0, " ", new String[]{ChatColor.RED + "" + ChatColor.MAGIC + "Contraband"}));
		gui.setItem(5, InventoryUtils.createSpecialItem(Material.IRON_FENCE, (short) 0, " ", new String[]{ChatColor.RED + "" + ChatColor.MAGIC + "Contraband"}));
		gui.setItem(12, InventoryUtils.createSpecialItem(Material.IRON_FENCE, (short) 0, " ", new String[]{ChatColor.RED + "" + ChatColor.MAGIC + "Contraband"}));
		gui.setItem(14, InventoryUtils.createSpecialItem(Material.IRON_FENCE, (short) 0, " ", new String[]{ChatColor.RED + "" + ChatColor.MAGIC + "Contraband"}));
		gui.setItem(21, InventoryUtils.createSpecialItem(Material.IRON_FENCE, (short) 0, " ", new String[]{ChatColor.RED + "" + ChatColor.MAGIC + "Contraband"}));
		gui.setItem(22, InventoryUtils.createSpecialItem(Material.IRON_FENCE, (short) 0, " ", new String[]{ChatColor.RED + "" + ChatColor.MAGIC + "Contraband"}));
		gui.setItem(23, InventoryUtils.createSpecialItem(Material.IRON_FENCE, (short) 0, " ", new String[]{ChatColor.RED + "" + ChatColor.MAGIC + "Contraband"}));

		//Back button
		String[] doorLore = new String[] {
				ChatColor.RED + "Leave this menu.",
				ChatColor.RED + "" + ChatColor.MAGIC + "Contraband"
		};
		gui.setItem(26, InventoryUtils.createSpecialItem(Material.WOOD_DOOR, (short) 0, ChatColor.DARK_GRAY + "Back", doorLore));

		//Vaporize button
		String[] barrierLore = new String[] {
				ChatColor.RED + "WARNING: Deletes all liquid currently stored in tank.",
				ChatColor.RED + "" + ChatColor.MAGIC + "Contraband"
		};
		gui.setItem(10, InventoryUtils.createSpecialItem(Material.BARRIER, (short) 0, ChatColor.DARK_RED + "Vaporize stored liquid", barrierLore));

		owner.openInventory(gui);

		if (SQTechBase.currentGui.containsKey(owner)) {

			SQTechBase.currentGui.remove(owner);
			SQTechBase.currentGui.put(owner, this);

		} else {

			SQTechBase.currentGui.put(owner, this);

		}

	}

	@Override
	public void click(final InventoryClickEvent event) {

		if (event.getClickedInventory() == null) return;

		if (event.getClickedInventory().getTitle().startsWith(ChatColor.BLUE + "SQTech")) {

			if (event.getSlot() != 13) event.setCancelled(true);

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

			if (event.getClickedInventory().getTitle().equals(ChatColor.BLUE + "SQTech - Small Tank")) {

				Machine machine = ObjectUtils.getMachineFromMachineGUI(this);

				if (event.getSlot() == 10) {

					for (Fluid f : SQTechBase.fluids) {

						if (machine.getLiquid(f) > 0) {

							Block waterBlock = SmallTank.getMiddleBlock(machine.getGUIBlock());
							World w = machine.getGUIBlock().getLocation().getWorld();
							
							w.playSound(machine.getGUIBlock().getLocation(), Sound.BLOCK_FIRE_EXTINGUISH, 1, 1);
							w.spawnParticle(Particle.SMOKE_LARGE, machine.getGUIBlock().getLocation(), 100, 1, 1, 1);
							machine.setLiquid(f, 0);
							if (waterBlock.getType() == Material.STATIONARY_LAVA ||
									waterBlock.getType() == Material.STATIONARY_WATER) {
								waterBlock.setType(Material.AIR);
							}
							
							if (SQTechPumps.tankWaterBlocks.get(machine) != null) {
								
								SQTechPumps.tankWaterBlocks.get(machine).remove(waterBlock);
								
							}
							
							event.getWhoClicked().closeInventory();
							
						}

					}

				} else if (event.getSlot() == 13) {
					
					if (event.getClickedInventory().getItem(event.getSlot()) == null) return;

					//Check if the item is a bucket
					if (event.getClickedInventory().getItem(event.getSlot()).getType().equals(Material.BUCKET)) {
						
						for (Fluid f : SQTechBase.fluids) {
							
							//If there is 1000 of a liquid in the tank
							if (machine.getLiquid(f) >= 1000) {
								
								//Fill up the bucket
								event.getClickedInventory().getItem(event.getSlot()).setType(f.material);
								//Take out one buckets worth of liquid
								machine.setLiquid(f, machine.getLiquid(f) - 1000);
								
							}
							
						}

					} else if (event.getClickedInventory().getItem(event.getSlot()).getType().equals(Material.WATER_BUCKET)) {
						
						for (Fluid f : SQTechBase.fluids) {
							//If f is the fluid 'Water'
							if (f.name.equals("Water")) {
								//Check if machine can store 'Water'
								if (machine.getMaxLiquid(f) > 0) {
									
									//Empty bucket
									event.getClickedInventory().getItem(event.getSlot()).setType(Material.BUCKET);
									//Put bucket contents into tank
									machine.setLiquid(f, machine.getLiquid(f) + 1000);
									
								}
								
							}
							
						}
						
					} else if (event.getClickedInventory().getItem(event.getSlot()).getType().equals(Material.LAVA_BUCKET)) {
						
						for (Fluid f : SQTechBase.fluids) {
							//If f is the fluid 'Lava'
							if (f.name.equals("Lava")) {
								//Check if machine can store 'Lava'
								if (machine.getMaxLiquid(f) > 0) {
									
									//Empty bucket
									event.getClickedInventory().getItem(event.getSlot()).setType(Material.BUCKET);
									//Put bucket contents into tank
									machine.setLiquid(f, machine.getLiquid(f) + 1000);
									
								}
								
							}
							
						}
						
					}
					
				} else if (event.getSlot() == 26) {
					
					machine.getGUIBlock().getGUI(owner).open();

				}

			}  else {

				if (event.getAction().equals(InventoryAction.MOVE_TO_OTHER_INVENTORY)) {
					
					event.setCancelled(true);

				}

			}

		}

	}

}
