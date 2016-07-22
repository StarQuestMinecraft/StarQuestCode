package com.martinjonsson01.sqtechpumps;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.logging.Level;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import com.martinjonsson01.sqtechpumps.gui.PumpGUI;
import com.starquestminecraft.sqtechbase.GUIBlock;
import com.starquestminecraft.sqtechbase.Machine;
import com.starquestminecraft.sqtechbase.MachineType;
import com.starquestminecraft.sqtechbase.SQTechBase;
import com.starquestminecraft.sqtechbase.gui.GUI;
import com.starquestminecraft.sqtechbase.util.InventoryUtils;

import net.md_5.bungee.api.ChatColor;

public class Pump extends MachineType {

	public static HashMap<Machine, Integer> taskId = new HashMap<Machine, Integer>();



	public Pump(int maxEnergy) {

		super(maxEnergy);
		name = "Pump";

	}

	@Override
	public boolean detectStructure(GUIBlock guiBlock) {

		Block block = guiBlock.getLocation().getBlock();

		if (block.getRelative(BlockFace.NORTH).getType().equals(Material.STAINED_GLASS_PANE) &&
				block.getRelative(BlockFace.EAST).getType().equals(Material.STAINED_GLASS_PANE) &&
				block.getRelative(BlockFace.WEST).getType().equals(Material.STAINED_GLASS_PANE) &&
				block.getRelative(BlockFace.SOUTH).getType().equals(Material.STAINED_GLASS_PANE) &&
				block.getRelative(BlockFace.DOWN).getRelative(BlockFace.NORTH).getType().equals(Material.STAINED_CLAY) &&
				block.getRelative(BlockFace.DOWN).getRelative(BlockFace.EAST).getType().equals(Material.STAINED_CLAY) &&
				block.getRelative(BlockFace.DOWN).getRelative(BlockFace.WEST).getType().equals(Material.STAINED_CLAY) &&
				block.getRelative(BlockFace.DOWN).getRelative(BlockFace.SOUTH).getType().equals(Material.STAINED_CLAY)) {

			return true;

		}

		return false;

	}

	@Override
	public GUI getGUI() {

		return new PumpGUI();

	}

	@Override
	public void updateEnergy(Machine machine) {

		for (Player player : SQTechBase.currentGui.keySet()) {

			if (SQTechBase.currentGui.get(player) == machine.getGUI()) {

				if (player.getOpenInventory() != null) {

					if (player.getOpenInventory().getTitle().equals(ChatColor.BLUE + "SQTech - Pump")) {

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

						String[] signLore = new String[] {
								ChatColor.BLUE + "Energy: " + PumpGUI.format(machine.getEnergy()) + "/" + PumpGUI.format(machine.getMachineType().getMaxEnergy()),
								ChatColor.BLUE + "Liquid Type: " + liquid,
								ChatColor.BLUE + "Amount in millibuckets: " + PumpGUI.format(amount) + "/" + PumpGUI.format(SQTechPumps.config.getInt("max liquid")),
								ChatColor.BLUE + "Amount in buckets: " + PumpGUI.format(amount/100) + "/" + PumpGUI.format(SQTechPumps.config.getInt("max liquid")/100),
								ChatColor.RED + "" + ChatColor.MAGIC + "Contraband"
						};
						player.getOpenInventory().setItem(8, InventoryUtils.createSpecialItem(Material.REDSTONE, (short) 0, ChatColor.BLUE + "Pump Info", signLore));

					}

				}

			}

		}

	}

	@SuppressWarnings("deprecation")
	public static void startPumping(Machine machine) {

		List<Block> tubeBlocks = new ArrayList<Block>();

		List<BlockFace> faces = new ArrayList<BlockFace>();
		faces.add(BlockFace.NORTH);
		faces.add(BlockFace.EAST);
		faces.add(BlockFace.WEST);
		faces.add(BlockFace.SOUTH);

		//Block waterBlock = null;

		Block next = machine.getGUIBlock().getLocation().getBlock().getRelative(BlockFace.DOWN);
		while (next.getType() == Material.AIR || next.getType() == Material.IRON_FENCE) {

			tubeBlocks.add(next);
			next = next.getRelative(BlockFace.DOWN);

		}

		if (next.getType() == Material.WATER ||
				next.getType() == Material.STATIONARY_WATER ||
				next.getType() == Material.STATIONARY_LAVA ||
				next.getType() == Material.LAVA) {

			//waterBlock = next.getRelative(BlockFace.DOWN);

			int i = 0;

			for (Block b : tubeBlocks) {

				Bukkit.getScheduler().scheduleSyncDelayedTask(SQTechPumps.plugin, new BukkitRunnable(){

					@Override
					public void run() {

						b.setType(Material.IRON_FENCE);
						b.getLocation().getWorld().playSound(b.getLocation(), Sound.BLOCK_PISTON_EXTEND, 1, 1);

						if (b.getRelative(BlockFace.DOWN).getType() == Material.WATER ||
								b.getRelative(BlockFace.DOWN).getType() == Material.LAVA) {

							Bukkit.getLogger().log(Level.INFO, "[SQTechPump] Before detection: " + System.currentTimeMillis());
							detect(b.getRelative(BlockFace.DOWN), machine);
							Bukkit.getLogger().log(Level.INFO, "[SQTechPump]  After detection: " + System.currentTimeMillis());


						}

					}

				}, i*7);

				i++;

			}

		}

	}

	@SuppressWarnings("deprecation")
	public static void stopPumping(Machine machine) {

		Bukkit.getScheduler().cancelTask(taskId.get(machine));

		if (SQTechPumps.pumpingList.contains(machine)) {
			SQTechPumps.pumpingList.remove(machine);
		}

		machine.getGUI().close();

		Block lapis = machine.getGUIBlock().getLocation().getBlock();

		List<Block> tube = new ArrayList<Block>();

		int i = 1;
		while (lapis.getLocation().add(0, -i, 0).getBlock().getType() == Material.IRON_FENCE) {
			
			tube.add(lapis.getLocation().add(0, -i, 0).getBlock());
			i++;

		}

		i = 0;

		Collections.reverse(tube);

		for (Block b : tube) {

			if (b.getType() != Material.IRON_FENCE) continue;

			Bukkit.getScheduler().scheduleSyncDelayedTask(SQTechPumps.plugin, new BukkitRunnable() {

				@Override
				public void run() {

					b.setType(Material.AIR);
					b.getLocation().getWorld().playSound(b.getLocation(), Sound.BLOCK_PISTON_EXTEND, 1, 1);

				}

			}, i*10);

			i++;

		}

	}

	public static void stopPumpingImmediately(Machine machine) {

		Bukkit.getScheduler().cancelTask(taskId.get(machine));

		if (SQTechPumps.pumpingList.contains(machine)) {
			SQTechPumps.pumpingList.remove(machine);
		}

		machine.getGUI().close();

		Block lapis = machine.getGUIBlock().getLocation().getBlock();

		List<Block> tube = new ArrayList<Block>();

		int i = 1;
		while (lapis.getLocation().add(0, -i, 0).getBlock().getType() == Material.IRON_FENCE) {

			tube.add(lapis.getLocation().add(0, -i, 0).getBlock());
			i++;

		}

		for (Block b : tube) {

			b.setType(Material.AIR);

		}

	}

	public static ArrayList<Block> getSurrounding(Block b) {

		ArrayList<Block> blocks = new ArrayList<Block>();

		BlockFace[] faces = new BlockFace[] {

				BlockFace.NORTH,
				BlockFace.EAST,
				BlockFace.WEST,
				BlockFace.SOUTH

		};

		for (BlockFace face : faces) {

			Block s = b.getRelative(face);
			if (s.getType() == Material.LAVA || s.getType() == Material.WATER ||
					s.getType() == Material.STATIONARY_LAVA || s.getType() == Material.STATIONARY_WATER) {

				blocks.add(s);

			}

		}
		return blocks;

	}

	@SuppressWarnings("deprecation")
	public static void detect(Block waterBlock, Machine machine) {

		ArrayList<Block> found = new ArrayList<Block>();
		ArrayList<Block> search_blocks = new ArrayList<Block>();

		found.add(waterBlock);

		Bukkit.getScheduler().runTaskAsynchronously(SQTechPumps.plugin, new BukkitRunnable() {

			@Override
			public void run() {

				search_blocks.add(waterBlock);

				while (true) {

					ArrayList<Block> to_search = new ArrayList<Block>();

					for (Block b2 : search_blocks) {

						found.add(b2);
						ArrayList<Block> fetched = getSurrounding(b2);
						for (Block b3 : fetched) {

							if (found.contains(b3) || to_search.contains(b3)) continue;

							to_search.add(b3);

						}

					}

					if (to_search.size() == 0 || to_search.size() > 3000) {

						break;

					} else {

						search_blocks.clear();
						search_blocks.addAll(to_search);

						to_search.clear();

					}

				}

				Pump.pump(found, machine);
				Bukkit.getLogger().log(Level.INFO, "[SQTechPump] Found: " + (found.size()-1) + " water blocks from origin: " + waterBlock);
				return;

			}

		});

		return;

	}

	@SuppressWarnings("deprecation")
	public static void pump(ArrayList<Block> waterBlocks, Machine machine) {

		taskId.put(machine, Bukkit.getScheduler().scheduleSyncRepeatingTask(SQTechPumps.plugin, new BukkitRunnable() {

			int i = 0;

			@Override
			public void run() {

				if (i >= waterBlocks.size() - 1  && machine.getEnergy() < 1000) {

					if (!(waterBlocks.get(0).getRelative(BlockFace.DOWN).getType() == Material.WATER) ||
							!(waterBlocks.get(0).getRelative(BlockFace.DOWN).getType() == Material.STATIONARY_WATER) ||
							!(waterBlocks.get(0).getRelative(BlockFace.DOWN).getType() == Material.LAVA) ||
							!(waterBlocks.get(0).getRelative(BlockFace.DOWN).getType() == Material.STATIONARY_LAVA)) {

						Bukkit.getServer().broadcastMessage(ChatColor.GREEN + "Finished pumping.");
						Pump.stopPumping(machine);
						Bukkit.getScheduler().cancelTask(taskId.get(machine));

					}

				} else if (i >= waterBlocks.size() - 1 && machine.getEnergy() >= 1000) {

					if (waterBlocks.get(0).getRelative(BlockFace.DOWN).getType() == Material.WATER ||
							waterBlocks.get(0).getRelative(BlockFace.DOWN).getType() == Material.STATIONARY_WATER ||
							waterBlocks.get(0).getRelative(BlockFace.DOWN).getType() == Material.LAVA ||
							waterBlocks.get(0).getRelative(BlockFace.DOWN).getType() == Material.STATIONARY_LAVA) {

						Pump.startPumping(machine);
						//Bukkit.getServer().broadcastMessage("Amount pumped CONTINUING: " + waterBlocks.size());
						Bukkit.getScheduler().cancelTask(taskId.get(machine));

					} else {

						Bukkit.getServer().broadcastMessage(ChatColor.GREEN + "Finished pumping.");
						Pump.stopPumping(machine);
						Bukkit.getScheduler().cancelTask(taskId.get(machine));

					}

				}


				if (machine == null) {
					Bukkit.getLogger().log(Level.INFO, "[SQTechPump] Machine is null");
					return;
				}

				SQTechPumps.waterBlocks = waterBlocks;

				Block current = waterBlocks.get(i);
				i++;

				if (machine.data.containsKey("liquid")) {

					if (machine.data.get("liquid") instanceof HashMap<?, ?>) {

						@SuppressWarnings("unchecked")
						HashMap<String, Integer> hMap = (HashMap<String, Integer>) machine.data.get("liquid");
						String liquid = "";

						if (current.getType() == Material.WATER  || current.getType() == Material.STATIONARY_WATER) {
							liquid = "water";
						} else if (current.getType() == Material.LAVA || current.getType() == Material.STATIONARY_LAVA) {
							liquid = "lava";
						}

						if (hMap.get(liquid) != null) {

							if (!(hMap.get(liquid) >= SQTechPumps.config.getInt("max liquid"))) {

								//Add 1000mb to the liquid
								hMap.put(liquid, hMap.get(liquid) + 1000);
								machine.setEnergy(machine.getEnergy() - SQTechPumps.config.getInt("energy consumption"));
								//Bukkit.getLogger().log(Level.INFO, "[SQTechPump] Pumped up block " + current); DEBUG 
								current.setType(Material.AIR);

								return;

							} else {

								Bukkit.getServer().broadcastMessage(ChatColor.GREEN + "Pump is filled up, please empty the pump to continue.");
								Pump.stopPumping(machine);
								Bukkit.getScheduler().cancelTask(taskId.get(machine));
								return;

							}

						} else {

							hMap.put(liquid, 0);
							//Add 1000mb to the liquid
							hMap.put(liquid, hMap.get(liquid) + 1000);
							machine.setEnergy(machine.getEnergy() - SQTechPumps.config.getInt("energy consumption"));
							Bukkit.getLogger().log(Level.INFO, "[SQTechPump] Pumped up block " + current);
							current.setType(Material.AIR);

							return;

						}

					}

				} else {

					machine.data.put("liquid", new HashMap<String, Integer>());

				}

			}

		}, 0, SQTechPumps.config.getLong("pumping speed")));

	}


}
