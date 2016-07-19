package com.martinjonsson01.sqtechpumps.gui;

import java.util.HashMap;
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

import com.martinjonsson01.sqtechpumps.Pump;
import com.martinjonsson01.sqtechpumps.SQTechPumps;
import com.starquestminecraft.sqtechbase.Machine;
import com.starquestminecraft.sqtechbase.SQTechBase;
import com.starquestminecraft.sqtechbase.gui.GUI;
import com.starquestminecraft.sqtechbase.util.InventoryUtils;

import net.md_5.bungee.api.ChatColor;

public class PumpGUI extends GUI{

	public PumpGUI() {

	}

	@Override
	public void open(Player player) {

		owner = player;

		Inventory gui = Bukkit.createInventory(owner, 27, ChatColor.BLUE + "SQTech - Pump");

		Machine machine = null;

		for (Machine machineList : SQTechBase.machines) {

			if (machineList.getGUI().equals(this)) {

				machine = machineList;

			}

		}
		//Info button
		String liquid = "none";
		int amount = 0;
		if (machine.data.containsKey("liquid")) {
			if (machine.data.get("liquid") instanceof HashMap<?, ?>) {
				@SuppressWarnings("unchecked")
				HashMap<String, Integer> hMap = (HashMap<String, Integer>) machine.data.get("liquid");
				if (hMap.containsKey("water")) {
					liquid = ChatColor.AQUA + "water";
					amount = hMap.get("water");
				} else if (hMap.containsKey("lava")) {
					liquid = ChatColor.RED + "lava";
					amount = hMap.get("lava");
				}
			}
		}
		
		String[] infoLore = new String[] {
				ChatColor.BLUE + "Energy: " + format(machine.getEnergy()) + "/" + format(machine.getMachineType().getMaxEnergy()),
				ChatColor.BLUE + "Liquid Type: " + liquid,
				ChatColor.BLUE + "Amount in millibuckets: " + format(amount) + "/" + format(SQTechPumps.config.getInt("max liquid")),
				ChatColor.BLUE + "Amount in buckets: " + format(amount/100) + "/" + format(SQTechPumps.config.getInt("max liquid")/100),
				ChatColor.RED + "" + ChatColor.MAGIC + "Contraband"
		};
		gui.setItem(8, InventoryUtils.createSpecialItem(Material.REDSTONE, (short) 0, ChatColor.BLUE + "Pump Info", infoLore));

		//Bucket button
		String[] bucketLore = new String[] {
				ChatColor.GOLD + "V Below V",
				ChatColor.RED + "" + ChatColor.MAGIC + "Contraband"
		};
		gui.setItem(4, InventoryUtils.createSpecialItem(Material.BUCKET, (short) 0, ChatColor.GOLD + "Place Bucket", bucketLore));

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

			if (event.getClickedInventory().getTitle().equals(ChatColor.BLUE + "SQTech - Pump")) {

				Machine machine = null;

				for (Machine machineList : SQTechBase.machines) {

					if (machineList.getGUI().equals(this)) {

						machine = machineList;

					}

				}

				if (event.getSlot() == 0) {

					if (SQTechPumps.pumpingList.contains(machine)) {
						
						String[] glassLore = new String[] {
								ChatColor.GRAY + "Click to " + ChatColor.GREEN + " ENABLE " + ChatColor.GRAY + "pump.",
								ChatColor.RED + "" + ChatColor.MAGIC + "Contraband"
						};
						event.getClickedInventory().setItem(0, InventoryUtils.createSpecialItem(Material.STAINED_GLASS, (short) 14, ChatColor.RED + "Pump DISABLED", glassLore));
						
						SQTechPumps.pumpingList.remove(machine);
						Pump.stopPumping(machine);
						event.getWhoClicked().sendMessage(ChatColor.GREEN + "Successfully disabled pump.");
						
					} else if (!SQTechPumps.pumpingList.contains(machine)) {
						
						if (machine.getEnergy() >= SQTechPumps.config.getInt("activate energy consumption")) {
							
							String[] glassLore = new String[] {
									ChatColor.GRAY + "Click to " + ChatColor.RED + " DISABLE " + ChatColor.GRAY + "pump.",
									ChatColor.RED + "" + ChatColor.MAGIC + "Contraband"
							};
							event.getClickedInventory().setItem(0, InventoryUtils.createSpecialItem(Material.STAINED_GLASS, (short) 13, ChatColor.GREEN + "Pump ENABLED", glassLore));
							
							SQTechPumps.pumpingList.add(machine);
							Pump.startPumping(machine);
							machine.setEnergy(machine.getEnergy() - SQTechPumps.config.getInt("activate energy consumption"));
						}
						
					}
					
				} else if (event.getSlot() == 8) {



				} else if (event.getSlot() == 13) {
					
					if (event.getClickedInventory().getItem(event.getSlot()) == null) return;
					
					//Check if there is an item there
					if (!event.getClickedInventory().getItem(event.getSlot()).getType().equals(Material.AIR)) {

						//If the item is a bucket
						if (event.getClickedInventory().getItem(event.getSlot()).getType() == Material.BUCKET) {

							if (machine.data.containsKey("liquid")) {

								if (machine.data.get("liquid") instanceof HashMap<?, ?>) {

									@SuppressWarnings("unchecked")
									HashMap<String, Integer> hMap = (HashMap<String, Integer>) machine.data.get("liquid");

									if (hMap.get("water") != null) {

										if (hMap.get("water") > 999) {

											hMap.put("water", hMap.get("water") - 1000);
											machine.data.putAll(hMap);

											event.getClickedInventory().setItem(event.getSlot(), new ItemStack(Material.WATER_BUCKET));

										} else {

											//Not enough millibuckets

										}

									} else if (hMap.get("lava") != null) {

										if (hMap.get("lava") > 999) {

											hMap.put("lava", hMap.get("lava") - 1000);
											machine.data.putAll(hMap);

											event.getClickedInventory().setItem(event.getSlot(), new ItemStack(Material.LAVA_BUCKET));

										} else {

											//Not enough millibuckets

										}

									}

								}

							} else {

								machine.data.put("liquid", new HashMap<String, Integer>());

							}

						}

					}

				} else if (event.getSlot() == 18) {
					
					if (machine.data.containsKey("liquid")) {
						
						if (machine.data.get("liquid") instanceof HashMap<?, ?>) {
							
							@SuppressWarnings("unchecked")
							HashMap<String, Integer> hMap = (HashMap<String, Integer>) machine.data.get("liquid");
							
							if (hMap.containsKey("water")) {
								
								World w = machine.getGUIBlock().getLocation().getWorld();
								w.playSound(machine.getGUIBlock().getLocation(), Sound.BLOCK_FIRE_EXTINGUISH, 1, 1);
								w.spawnParticle(Particle.SMOKE_LARGE, machine.getGUIBlock().getLocation(), 0, 1, 1, 1);
								hMap.put("water", 0);
								
							} else if (hMap.containsKey("lava")) {
								
								World w = machine.getGUIBlock().getLocation().getWorld();
								w.playSound(machine.getGUIBlock().getLocation(), Sound.BLOCK_FIRE_EXTINGUISH, 1, 1);
								w.spawnParticle(Particle.SMOKE_LARGE, machine.getGUIBlock().getLocation(), 0, 1, 1, 1);
								hMap.put("lava", 0);
								
							}
							
						}
						
					}
					
				} else if (event.getSlot() == 26) {

					machine.getGUIBlock().getGUI().open(owner);

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
