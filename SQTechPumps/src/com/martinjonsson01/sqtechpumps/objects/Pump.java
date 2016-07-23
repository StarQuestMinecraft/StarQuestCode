package com.martinjonsson01.sqtechpumps.objects;

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

import com.martinjonsson01.sqtechpumps.SQTechPumps;
import com.martinjonsson01.sqtechpumps.gui.PumpGUI;
import com.martinjonsson01.sqtechpumps.tasks.DetectionTask;
import com.martinjonsson01.sqtechpumps.tasks.PumpTask;
import com.starquestminecraft.sqtechbase.SQTechBase;
import com.starquestminecraft.sqtechbase.gui.GUI;
import com.starquestminecraft.sqtechbase.objects.Fluid;
import com.starquestminecraft.sqtechbase.objects.GUIBlock;
import com.starquestminecraft.sqtechbase.objects.Machine;
import com.starquestminecraft.sqtechbase.objects.MachineType;
import com.starquestminecraft.sqtechbase.util.InventoryUtils;

import net.md_5.bungee.api.ChatColor;

public class Pump extends MachineType {

	public static HashMap<Machine, Integer> taskId = new HashMap<Machine, Integer>();



	public Pump(int maxEnergy) {

		super(maxEnergy);
		name = "Pump";
		this.defaultImport = true;

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
	public GUI getGUI(Player player, int id) {

		return new PumpGUI(player, id);

	}

	@Override
	public void updateEnergy(Machine machine) {
		
		for (Player player : SQTechBase.currentGui.keySet()) {
			
			if (SQTechBase.currentGui.get(player).id == machine.getGUI(player).id) {
				
				if (player.getOpenInventory() != null) {
					
					if (player.getOpenInventory().getTitle().equals(ChatColor.BLUE + "SQTech - Pump")) {
						
						String[] infoLore = new String[] {
								ChatColor.DARK_PURPLE + PumpGUI.format(machine.getEnergy()) + "/" + PumpGUI.format(machine.getMachineType().getMaxEnergy()) + " (" +machine.getEnergy() + ")",
								ChatColor.RED + "" + ChatColor.MAGIC + "Contraband"
						};
						player.getOpenInventory().setItem(8, InventoryUtils.createSpecialItem(Material.REDSTONE, (short) 0, ChatColor.BLUE + "Energy", infoLore));

					}

				}

			}

		}

	}

	@Override
	public void updateLiquid(Machine machine) {
		
		for (Player player : SQTechBase.currentGui.keySet()) {

			if (SQTechBase.currentGui.get(player).id == machine.getGUI(player).id) {

				if (player.getOpenInventory() != null) {

					if (player.getOpenInventory().getTitle().equals(ChatColor.BLUE + "SQTech - Pump")) {

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

						String[] liquidLore = new String[] {
								ChatColor.BLUE + "Liquid Type: " + name,
								ChatColor.BLUE + "Amount in millibuckets: " + PumpGUI.format(amount) + "/" + PumpGUI.format(SQTechPumps.config.getInt("max liquid")),
								ChatColor.BLUE + "Amount in buckets: " + PumpGUI.format(amount/1000) + "/" + PumpGUI.format(SQTechPumps.config.getInt("max liquid")/1000),
								ChatColor.RED + "" + ChatColor.MAGIC + "Contraband"
						};
						player.getOpenInventory().setItem(17, InventoryUtils.createSpecialItem(Material.BUCKET, (short) 0, ChatColor.BLUE + "Pump Liquid Info", liquidLore));
						
					}

				}

			}

		}

	}

	@SuppressWarnings("deprecation")
	public static void startPumping(Machine machine, Player owner) {

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

							Long before = System.currentTimeMillis();
							detect(b.getRelative(BlockFace.DOWN), machine, owner);
							Bukkit.getLogger().log(Level.INFO, "[SQTechPump] Detection took " + (System.currentTimeMillis() - before) + " ms");


						}

					}

				}, i*7);

				i++;

			}

		}

	}

	@SuppressWarnings("deprecation")
	public static void stopPumping(Machine machine, Player owner) {

		Bukkit.getScheduler().cancelTask(taskId.get(machine));

		if (SQTechPumps.pumpingList.contains(machine)) {
			SQTechPumps.pumpingList.remove(machine);
		}

		machine.getGUI(owner).close();

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

	public static void stopPumpingImmediately(Machine machine, Player owner) {

		Bukkit.getScheduler().cancelTask(taskId.get(machine));

		if (SQTechPumps.pumpingList.contains(machine)) {
			SQTechPumps.pumpingList.remove(machine);
		}

		machine.getGUI(owner).close();

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

	public static void detect(Block waterBlock, Machine machine, Player owner) {

		DetectionTask task = new DetectionTask(waterBlock, machine, owner);
		task.runTaskAsynchronously(SQTechPumps.plugin);

	}

	public static void pump(ArrayList<Block> waterBlocks, Machine machine, Player owner) {

		PumpTask task = new PumpTask(waterBlocks, machine, owner);
		task.runTask(SQTechPumps.plugin);
		
	}


}
