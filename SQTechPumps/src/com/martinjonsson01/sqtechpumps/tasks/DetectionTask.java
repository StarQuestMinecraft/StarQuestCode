package com.martinjonsson01.sqtechpumps.tasks;

import java.util.ArrayList;
import java.util.logging.Level;

import org.bukkit.Bukkit;
import org.bukkit.block.Block;
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
						ArrayList<Block> fetched = Pump.getSurrounding(b2);
						for (Block b3 : fetched) {

							if (found.contains(b3) || to_search.contains(b3)) continue;

							to_search.add(b3);

						}

					}

					if (to_search.size() == 0 || to_search.size() > 10000) {

						break;

					} else {

						search_blocks.clear();
						search_blocks.addAll(to_search);

						to_search.clear();

					}

				}
				
				Pump.pump(found, machine, owner);
				Bukkit.getLogger().log(Level.INFO, "[SQTechPump] Found: " + (found.size()-1) + " water blocks from origin: " + waterBlock);
				return;

			}

		});

		return;
		
	}
	
}
