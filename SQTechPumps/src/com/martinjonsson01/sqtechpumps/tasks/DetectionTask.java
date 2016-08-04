package com.martinjonsson01.sqtechpumps.tasks;

import java.util.ArrayList;
import java.util.logging.Level;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import com.martinjonsson01.sqtechpumps.SQTechPumps;
import com.martinjonsson01.sqtechpumps.objects.Pump;
import com.starquestminecraft.sqtechbase.objects.Machine;

public class DetectionTask extends BukkitRunnable{

	Block waterBlock;
	Machine machine;
	Player owner;

	public DetectionTask(Block waterBlock, Machine machine, Player owner) {

		this.waterBlock = waterBlock;
		this.machine = machine;
		this.owner = owner;

	}

	@SuppressWarnings("deprecation")
	@Override
	public void run() {

		ArrayList<Block> found = new ArrayList<Block>();
		ArrayList<Block> aboveFound = new ArrayList<Block>();
		ArrayList<Block> search_blocks = new ArrayList<Block>();

		found.add(waterBlock);

		Bukkit.getScheduler().runTaskAsynchronously(SQTechPumps.plugin, new BukkitRunnable() {

			@Override
			public void run() {

				search_blocks.add(waterBlock);

				/*Block current = waterBlock.getWorld().getBlockAt(waterBlock.getX(), 256, waterBlock.getZ());

				while (current.getType() != Material.WATER ||
						current.getType() != Material.STATIONARY_WATER ||
						current.getType() != Material.LAVA ||
						current.getType() != Material.STATIONARY_LAVA) {

					current = current.getRelative(BlockFace.DOWN);

				}
				search_blocks.add(current);*/

				while (true) {

					ArrayList<Block> to_search = new ArrayList<Block>();

					/*if (to_search.size() >= 200 || found.size() >= 200 || search_blocks.size() >= 200) {
					
						break;
					
					}*/

					for (Block b2 : search_blocks) {
						
						if (Math.abs(b2.getLocation().getX() - machine.getGUIBlock().getLocation().getX()) > 10 ||
								Math.abs(b2.getLocation().getZ() - machine.getGUIBlock().getLocation().getZ()) > 10) {
							
							continue;
							
						}
						
						/*if (b2.getLocation().distance(machine.getGUIBlock().getLocation()) > 10) {
							
							continue;
							
						}*/
						
						if (b2.getRelative(BlockFace.UP).getType() == Material.WATER ||
								b2.getRelative(BlockFace.UP).getType() == Material.STATIONARY_WATER ||
								b2.getRelative(BlockFace.UP).getType() == Material.LAVA ||
								b2.getRelative(BlockFace.UP).getType() == Material.STATIONARY_LAVA) {

							aboveFound.add(b2.getRelative(BlockFace.UP));

						}
						
						found.add(b2);
						ArrayList<Block> fetched = Pump.getSurrounding(b2);
						for (Block b3 : fetched) {

							if (found.contains(b3) || to_search.contains(b3)) continue;

							to_search.add(b3);

						}

					}

					if (to_search.size() == 0) {

						break;

					} else {

						search_blocks.clear();
						search_blocks.addAll(to_search);

						to_search.clear();

					}

				}

				/*for (Block b3 : found) {

					int i = 256;
					while (i > 0) {

						if (b3.getWorld().getBlockAt(b3.getX(), i, b3.getZ()).getType() == b3.getType()) {

							new DetectionTask(b3.getWorld().getBlockAt(b3.getX(), i, b3.getZ()), machine, owner);
							return;

						}


						i--;
					}

				}*/

				if (!aboveFound.isEmpty()) {

					Pump.pump(aboveFound, machine, owner);
					Bukkit.getLogger().log(Level.INFO, "[SQTechPump] Found: " + (aboveFound.size()-1) + " water blocks from origin: " + waterBlock);
					return;

				}

				Pump.pump(found, machine, owner);
				Bukkit.getLogger().log(Level.INFO, "[SQTechPump] Found: " + (found.size()-1) + " water blocks from origin: " + waterBlock);
				return;

			}

		});

		return;

	}

}
