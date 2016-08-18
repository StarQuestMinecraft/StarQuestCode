package com.martinjonsson01.sqtechpumps.gui;

import java.util.Map.Entry;
import java.util.NavigableMap;
import java.util.TreeMap;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryAction;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import com.martinjonsson01.sqtechpumps.SQTechPumps;
import com.martinjonsson01.sqtechpumps.objects.Pump;
import com.starquestminecraft.sqtechbase.SQTechBase;
import com.starquestminecraft.sqtechbase.gui.GUI;
import com.starquestminecraft.sqtechbase.objects.Fluid;
import com.starquestminecraft.sqtechbase.objects.Machine;
import com.starquestminecraft.sqtechbase.util.InventoryUtils;
import com.starquestminecraft.sqtechbase.util.ObjectUtils;

import net.md_5.bungee.api.ChatColor;

public class PumpGUI extends GUI{

	public PumpGUI(Player player, int id) {

		super(player, id);

	}

	@Override
	public void open() {

		Inventory gui = Bukkit.createInventory(owner, 27, ChatColor.BLUE + "SQTech - Pump");

		Machine machine = ObjectUtils.getMachineFromMachineGUI(this);

		//Energy button
		Fluid fluid = null;
		int amount = 0;
		String name = "none";

		for (Fluid f : SQTechBase.fluids) {
			if (machine.getLiquid(f) > 0) {
				fluid = f;
			}
		}
		if (fluid != null) {
			amount = machine.getLiquid(fluid);
			name = fluid.name;
		}

		String[] infoLore = new String[] {
				ChatColor.DARK_PURPLE + PumpGUI.format(machine.getEnergy()) + "/" + PumpGUI.format(machine.getMachineType().getMaxEnergy()) + " (" +machine.getEnergy() + ")",
				ChatColor.RED + "" + ChatColor.MAGIC + "Contraband"
		};
		gui.setItem(8, InventoryUtils.createSpecialItem(Material.REDSTONE, (short) 0, ChatColor.BLUE + "Energy", infoLore));

		//Liquid info button
		String[] liquidLore = new String[] {
				ChatColor.BLUE + "Liquid Type: " + name,
				ChatColor.BLUE + "Amount in millibuckets: " + PumpGUI.format(amount) + "/" + PumpGUI.format(SQTechPumps.config.getInt("max liquid")),
				ChatColor.BLUE + "Amount in buckets: " + PumpGUI.format(amount/1000) + "/" + PumpGUI.format(SQTechPumps.config.getInt("max liquid")/1000),
				ChatColor.RED + "" + ChatColor.MAGIC + "Contraband"
		};
		gui.setItem(17, InventoryUtils.createSpecialItem(Material.BUCKET, (short) 0, ChatColor.BLUE + "Pump Liquid Info", liquidLore));

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

		//Enable/disable button
		if (SQTechPumps.pumpingList.contains(machine)) {

			String[] glassLore = new String[] {
					ChatColor.GRAY + "Click to " + ChatColor.RED + " DISABLE " + ChatColor.GRAY + "pump.",
					ChatColor.RED + "" + ChatColor.MAGIC + "Contraband"
			};
			gui.setItem(0, InventoryUtils.createSpecialItem(Material.STAINED_GLASS, (short) 13, ChatColor.GREEN + "Pump ENABLED", glassLore));

		} else if (!SQTechPumps.pumpingList.contains(machine)) {

			String[] glassLore = new String[] {
					ChatColor.GRAY + "Click to " + ChatColor.GREEN + " ENABLE " + ChatColor.GRAY + "pump.",
					ChatColor.RED + "" + ChatColor.MAGIC + "Contraband"
			};
			gui.setItem(0, InventoryUtils.createSpecialItem(Material.STAINED_GLASS, (short) 14, ChatColor.RED + "Pump DISABLED", glassLore));

		}

		//Back button
		String[] doorLore = new String[] {
				ChatColor.RED + "Leave this menu.",
				ChatColor.RED + "" + ChatColor.MAGIC + "Contraband"
		};
		gui.setItem(26, InventoryUtils.createSpecialItem(Material.WOOD_DOOR, (short) 0, ChatColor.DARK_GRAY + "Back", doorLore));

		//Vaporize button
		String[] barrierLore = new String[] {
				ChatColor.RED + "WARNING: Deletes all liquid currently stored in pump.",
				ChatColor.RED + "" + ChatColor.MAGIC + "Contraband"
		};
		gui.setItem(18, InventoryUtils.createSpecialItem(Material.BARRIER, (short) 0, ChatColor.DARK_RED + "Vaporize stored liquid", barrierLore));

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

			if (event.getClickedInventory().getTitle().equals(ChatColor.BLUE + "SQTech - Pump")) {

				Machine machine = ObjectUtils.getMachineFromMachineGUI(this);
				if (event.getSlot() == 0) {
					
					if (SQTechPumps.lastClick.containsKey(machine)) {
						if (System.currentTimeMillis() - SQTechPumps.lastClick.get(machine) < 3000) {
							owner.sendMessage(ChatColor.RED + "Don't spam the button. Please wait at least 3 seconds.");
							return;
						}
					}
					
					if (SQTechPumps.machineExtendingMap.get(machine) != null) {
						
						if (SQTechPumps.machineExtendingMap.get(machine) == true) {
							
							owner.sendMessage(ChatColor.RED + "The tube of the pump is currently extending, please wait.");
							return;
							
						}
						
					}
					
					SQTechPumps.lastClick.put(machine, System.currentTimeMillis());
					
					if (SQTechPumps.pumpingList.contains(machine)) {

						String[] glassLore = new String[] {
								ChatColor.GRAY + "Click to " + ChatColor.GREEN + " ENABLE " + ChatColor.GRAY + "pump.",
								ChatColor.RED + "" + ChatColor.MAGIC + "Contraband"
						};
						event.getClickedInventory().setItem(0, InventoryUtils.createSpecialItem(Material.STAINED_GLASS, (short) 14, ChatColor.RED + "Pump DISABLED", glassLore));

						SQTechPumps.pumpingList.remove(machine);
						SQTechPumps.pumpingLocationList.remove(machine.getGUIBlock().getLocation());
						Pump.stopPumping(machine, owner);
						event.getWhoClicked().sendMessage(ChatColor.GREEN + "Successfully disabled pump.");

					} else if (!SQTechPumps.pumpingList.contains(machine)) {

						if (machine.getEnergy() >= SQTechPumps.config.getInt("activate energy consumption")) {

							String[] glassLore = new String[] {
									ChatColor.GRAY + "Click to " + ChatColor.RED + " DISABLE " + ChatColor.GRAY + "pump.",
									ChatColor.RED + "" + ChatColor.MAGIC + "Contraband"
							};
							event.getClickedInventory().setItem(0, InventoryUtils.createSpecialItem(Material.STAINED_GLASS, (short) 13, ChatColor.GREEN + "Pump ENABLED", glassLore));

							SQTechPumps.pumpingList.add(machine);
							SQTechPumps.pumpingLocationList.add(machine.getGUIBlock().getLocation());
							Pump.startPumping(machine, owner);
							machine.setEnergy(machine.getEnergy() - SQTechPumps.config.getInt("activate energy consumption"));
							event.getWhoClicked().sendMessage(ChatColor.GREEN + "Successfully enabled pump.");
							event.getWhoClicked().closeInventory();
						} else {
							owner.sendMessage(ChatColor.RED + "The pump needs at least " + SQTechPumps.config.getInt("activate energy consumption") + " energy to start pumping.");
						}

					}

				} else if (event.getSlot() == 8) {



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

				} else if (event.getSlot() == 18) {

					for (Fluid f : SQTechBase.fluids) {

						if (machine.getLiquid(f) > 0) {

							World w = machine.getGUIBlock().getLocation().getWorld();
							w.playSound(machine.getGUIBlock().getLocation(), Sound.BLOCK_FIRE_EXTINGUISH, 1, 1);
							w.spawnParticle(Particle.SMOKE_LARGE, machine.getGUIBlock().getLocation(), 0, 1, 1, 1);
							machine.setLiquid(f, 0);

						}

					}

				} else if (event.getSlot() == 26) {

					machine.getGUIBlock().getGUI(owner).open();

				}

			} else {

				if (event.getAction().equals(InventoryAction.MOVE_TO_OTHER_INVENTORY)) {

					event.setCancelled(true);

				}

			}

		}

	}

	private static final NavigableMap<Long, String> suffixes = new TreeMap<> ();
	static {
		suffixes.put(1_000L, "k");
		suffixes.put(1_000_000L, "M");
		suffixes.put(1_000_000_000L, "G");
		suffixes.put(1_000_000_000_000L, "T");
		suffixes.put(1_000_000_000_000_000L, "P");
		suffixes.put(1_000_000_000_000_000_000L, "E");
	}

	public static String format(long value) {
		//Long.MIN_VALUE == -Long.MIN_VALUE so we need an adjustment here
		if (value == Long.MIN_VALUE) return format(Long.MIN_VALUE + 1);
		if (value < 0) return "-" + format(-value);
		if (value < 1000) return Long.toString(value); //deal with easy case

		Entry<Long, String> e = suffixes.floorEntry(value);
		Long divideBy = e.getKey();
		String suffix = e.getValue();

		long truncated = value / (divideBy / 10); //the number part of the output times 10
		boolean hasDecimal = truncated < 100 && (truncated / 10d) != (truncated / 10);
		return hasDecimal ? (truncated / 10d) + suffix : (truncated / 10) + suffix;
	}

}
