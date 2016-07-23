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
	Machine machine;
	Player owner;
	
	public PumpTask(ArrayList<Block> waterBlocks, Machine machine, Player owner) {
		
		this.waterBlocks = waterBlocks;
		this.machine = machine;
		this.owner = owner;
		
	}
	
	@SuppressWarnings("deprecation")
	@Override
	public void run() {
		
		Pump.taskId.put(machine, Bukkit.getScheduler().scheduleSyncRepeatingTask(SQTechPumps.plugin, new BukkitRunnable() {

			int i = 0;

			@Override
			public void run() {

				if (i >= waterBlocks.size() - 1  && machine.getEnergy() < SQTechPumps.config.getInt("energy consumption")) {

					if (!(waterBlocks.get(0).getRelative(BlockFace.DOWN).getType() == Material.WATER) ||
							!(waterBlocks.get(0).getRelative(BlockFace.DOWN).getType() == Material.STATIONARY_WATER) ||
							!(waterBlocks.get(0).getRelative(BlockFace.DOWN).getType() == Material.LAVA) ||
							!(waterBlocks.get(0).getRelative(BlockFace.DOWN).getType() == Material.STATIONARY_LAVA)) {

						owner.sendMessage(ChatColor.GREEN + "Finished pumping.");
						Pump.stopPumping(machine, owner);
						Bukkit.getScheduler().cancelTask(Pump.taskId.get(machine));

					}

				} else if (i >= waterBlocks.size() - 1 && machine.getEnergy() >= SQTechPumps.config.getInt("energy consumption")) {

					if (waterBlocks.get(0).getRelative(BlockFace.DOWN).getType() == Material.WATER ||
							waterBlocks.get(0).getRelative(BlockFace.DOWN).getType() == Material.STATIONARY_WATER ||
							waterBlocks.get(0).getRelative(BlockFace.DOWN).getType() == Material.LAVA ||
							waterBlocks.get(0).getRelative(BlockFace.DOWN).getType() == Material.STATIONARY_LAVA) {

						Pump.startPumping(machine, owner);
						//Bukkit.getServer().broadcastMessage("Amount pumped CONTINUING: " + waterBlocks.size());
						Bukkit.getScheduler().cancelTask(Pump.taskId.get(machine));

					} else {

						owner.sendMessage(ChatColor.GREEN + "Finished pumping.");
						Pump.stopPumping(machine, owner);
						Bukkit.getScheduler().cancelTask(Pump.taskId.get(machine));

					}

				} else if (machine.getEnergy() < SQTechPumps.config.getInt("energy consumption")) {
					
					owner.sendMessage(ChatColor.GREEN + "Pump out of energy.");
					Pump.stopPumping(machine, owner);
					Bukkit.getScheduler().cancelTask(Pump.taskId.get(machine));
					
				}
				
				SQTechPumps.waterBlocks.addAll(waterBlocks);

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
							SQTechPumps.waterBlocks.remove(current);

							return;
							
						}
						
					} else {
						
						if (f.name == type) {
							
							machine.maxLiquid.put(f, SQTechPumps.config.getInt("max liquid"));
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
								SQTechPumps.waterBlocks.remove(current);
								
								return;
								
							}
							
						}
						
					}
					
				}

			}

		}, 0, SQTechPumps.config.getLong("pumping speed")));

	}
	
}
