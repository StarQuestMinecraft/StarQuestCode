package com.martinjonsson01.sqtechpumps.tasks;

import java.util.ArrayList;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import com.martinjonsson01.sqtechpumps.SQTechPumps;
import com.martinjonsson01.sqtechpumps.objects.Pump;
import com.starquestminecraft.sqtechbase.SQTechBase;
import com.starquestminecraft.sqtechbase.objects.Fluid;
import com.starquestminecraft.sqtechbase.objects.Machine;

import net.md_5.bungee.api.ChatColor;

public class PumpTask extends BukkitRunnable{

	ArrayList<Block> waterBlocks;
	ArrayList<Block> waterBlocksCOPY;
	int waterBlocksSize;
	Machine machine;
	Player owner;

	@SuppressWarnings("unchecked")
	public PumpTask(ArrayList<Block> waterBlocks, Machine machine, Player owner) {

		this.waterBlocks = waterBlocks;
		this.machine = machine;
		this.owner = owner;
		this.waterBlocksCOPY = (ArrayList<Block>) waterBlocks.clone();
		this.waterBlocksSize = waterBlocksCOPY.size();

	}

	@SuppressWarnings("deprecation")
	@Override
	public void run() {

		Pump.taskId.put(machine, Bukkit.getScheduler().scheduleSyncRepeatingTask(SQTechPumps.plugin, new BukkitRunnable() {

			int i = 0;

			@Override
			public void run() {
				
				if (SQTechPumps.machineExtendingMap.get(machine) == null) {
					SQTechPumps.machineExtendingMap.put(machine, false);
				}
				
				if (SQTechPumps.machineExtendingMap.get(machine) == true) {
					
					SQTechPumps.machineExtendingMap.put(machine, false);
					
				}
				
				if (i >= waterBlocksSize  && machine.getEnergy() < SQTechPumps.config.getInt("energy consumption")) {

					if (waterBlocks.size() <= 1) {

						owner.sendMessage(ChatColor.GREEN + "Finished pumping.");
						Pump.stopPumping(machine, owner);
						Bukkit.getScheduler().cancelTask(Pump.taskId.get(machine));
						return;

					}
					owner.sendMessage("waterBlocks(0): " + waterBlocks.get(0));
					if (!(waterBlocks.get(0).getRelative(BlockFace.DOWN).getType() == Material.WATER) ||
							!(waterBlocks.get(0).getRelative(BlockFace.DOWN).getType() == Material.STATIONARY_WATER) ||
							!(waterBlocks.get(0).getRelative(BlockFace.DOWN).getType() == Material.LAVA) ||
							!(waterBlocks.get(0).getRelative(BlockFace.DOWN).getType() == Material.STATIONARY_LAVA)) {

						owner.sendMessage(ChatColor.GREEN + "Finished pumping.");
						Pump.stopPumping(machine, owner);
						Bukkit.getScheduler().cancelTask(Pump.taskId.get(machine));

					}

				} else if (i >= waterBlocksSize && machine.getEnergy() >= SQTechPumps.config.getInt("energy consumption")) {

					if (waterBlocks.size() <= 1) {

						owner.sendMessage(ChatColor.GREEN + "Finished pumping.");
						Pump.stopPumping(machine, owner);
						Bukkit.getScheduler().cancelTask(Pump.taskId.get(machine));
						return;

					}

					if (waterBlocks.get(0).getRelative(BlockFace.DOWN).getType() == Material.WATER ||
							waterBlocks.get(0).getRelative(BlockFace.DOWN).getType() == Material.STATIONARY_WATER ||
							waterBlocks.get(0).getRelative(BlockFace.DOWN).getType() == Material.LAVA ||
							waterBlocks.get(0).getRelative(BlockFace.DOWN).getType() == Material.STATIONARY_LAVA) {
						
						//Continuing pumping downwards
						SQTechPumps.waterBlocks.get(machine).clear();
						Pump.startPumping(machine, owner);
						Bukkit.getScheduler().cancelTask(Pump.taskId.get(machine));

					} else {

						owner.sendMessage(ChatColor.GREEN + "Finished pumping.");
						Pump.stopPumping(machine, owner);
						Bukkit.getScheduler().cancelTask(Pump.taskId.get(machine));

					}

				} else if (machine.getEnergy() < SQTechPumps.config.getInt("energy consumption")) {

					owner.sendMessage(ChatColor.RED + "Pump is out of energy.");
					//SQTechPumps.resumeWaterBlocks.put(machine, waterBlocks);
					//SQTechPumps.waterBlocks.get(machine).clear();
					Pump.stopPumping(machine, owner);
					Bukkit.getScheduler().cancelTask(Pump.taskId.get(machine));

				}

				if (i >= waterBlocksSize) {

					if (waterBlocks.size() <= 1) {
						
						return;

					}

					/*if (waterBlocks.get(0).getRelative(BlockFace.DOWN).getType() == Material.WATER ||
							waterBlocks.get(0).getRelative(BlockFace.DOWN).getType() == Material.STATIONARY_WATER ||
							waterBlocks.get(0).getRelative(BlockFace.DOWN).getType() == Material.LAVA ||
							waterBlocks.get(0).getRelative(BlockFace.DOWN).getType() == Material.STATIONARY_LAVA) {
						
						SQTechPumps.waterBlocks.get(machine).clear();
						Pump.startPumping(machine, owner);
						Bukkit.getScheduler().cancelTask(Pump.taskId.get(machine));

					} else if (!(waterBlocks.get(0).getRelative(BlockFace.DOWN).getType() == Material.WATER) ||
							!(waterBlocks.get(0).getRelative(BlockFace.DOWN).getType() == Material.STATIONARY_WATER) ||
							!(waterBlocks.get(0).getRelative(BlockFace.DOWN).getType() == Material.LAVA) ||
							!(waterBlocks.get(0).getRelative(BlockFace.DOWN).getType() == Material.STATIONARY_LAVA)) {
						
						owner.sendMessage(ChatColor.GREEN + "Finished pumping.");
						Pump.stopPumping(machine, owner);
						Bukkit.getScheduler().cancelTask(Pump.taskId.get(machine));

					} else {

						Bukkit.getLogger().log(Level.INFO, "[SQTechPumps] - 'i >= waterBlocksSize'---*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-");

					}*/

				} 


				if (SQTechPumps.waterBlocks.containsKey(owner)) {
					SQTechPumps.waterBlocks.get(machine).addAll(waterBlocks);
				} else {
					SQTechPumps.waterBlocks.put(machine, waterBlocks);
				}

				
				Block current = waterBlocks.get(i);
				i++;


				String type = "";

				if (current.getType() == Material.WATER || current.getType() == Material.STATIONARY_WATER) {
					type = "Water";
				} else if (current.getType() == Material.LAVA || current.getType() == Material.STATIONARY_LAVA) {
					type = "Lava";
				}

				for (Fluid f : SQTechBase.fluids) {

					if (machine.getLiquid(f) > 0) {

						if (machine.getLiquid(f) >= SQTechPumps.config.getInt("max liquid")) {

							owner.sendMessage(ChatColor.RED + "Pump is filled up, please empty the pump to continue.");
							Pump.stopPumping(machine, owner);
							Bukkit.getScheduler().cancelTask(Pump.taskId.get(machine));
							return;


						} else {

							//Add 1000mb to the liquid
							machine.setLiquid(f, machine.getLiquid(f) + 1000);
							machine.setEnergy(machine.getEnergy() - SQTechPumps.config.getInt("energy consumption"));
							current.setType(Material.AIR);
							//SQTechPumps.waterBlocks.get(machine).remove(current);

							return;

						}

					} else {

						if (f.name == type) {

							machine.maxLiquid.put(f, SQTechPumps.config.getInt("max liquid"));
							machine.liquidExports.add(f);
							machine.liquid.put(f, 0);

							if (machine.getLiquid(f) >= SQTechPumps.config.getInt("max liquid")) {

								owner.sendMessage(ChatColor.GREEN + "Pump is filled up, please empty the pump to continue.");
								Pump.stopPumping(machine, owner);
								Bukkit.getScheduler().cancelTask(Pump.taskId.get(machine));
								return;

							} else {

								//Add 1000mb to the liquid
								machine.setLiquid(f, machine.getLiquid(f) + 1000);
								machine.setEnergy(machine.getEnergy() - SQTechPumps.config.getInt("energy consumption"));
								current.setType(Material.AIR);
								//SQTechPumps.waterBlocks.get(machine).remove(current);

								return;

							}

						}

					}

				}

			}

		}, 0, SQTechPumps.config.getLong("pumping speed")));

	}

}
