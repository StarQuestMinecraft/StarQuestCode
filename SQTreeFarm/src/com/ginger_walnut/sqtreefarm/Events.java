package com.ginger_walnut.sqtreefarm;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.TreeType;
import org.bukkit.block.Block;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.world.StructureGrowEvent;
import org.bukkit.scheduler.BukkitScheduler;

public class Events implements Listener{
	
	@EventHandler(priority = EventPriority.LOWEST)
	public void onBlockBreak(final BlockBreakEvent event) {
				
		if (event.getPlayer().hasPermission("SQTreeFarm.useFarms")) {
		
			boolean withinFarm = false;
			
			for (File farm : SQTreeFarm.treefarms) {
				
				if (isWithinFarm(farm, event.getBlock().getLocation())) {
					
					withinFarm = true;
					
				}
				
			}
			
			if (withinFarm) {

				if (event.getBlock().getType().equals(Material.LOG) || event.getBlock().getType().equals(Material.LEAVES) || event.getBlock().getType().equals(Material.STONE) || event.getBlock().getType().equals(Material.COAL_ORE) || event.getBlock().getType().equals(Material.IRON_ORE)) {
					
					event.setCancelled(true);
					
					final Block block = event.getBlock();
					
					block.breakNaturally();

					if (event.getBlock().getRelative(0, -1, 0).getType().equals(Material.DIRT)) {

						BukkitScheduler scheduler = Bukkit.getServer().getScheduler();
						
						scheduler.scheduleSyncDelayedTask(SQTreeFarm.getPluginMain(), new Runnable() {
						
							@Override
							public void run()
							{
							
								block.setType(Material.SAPLING);
								block.setData((byte) 2);
								
							}
							
						}, 1);
						
					}
					
				}
				
			}
			
		}
		
	}
	
	@EventHandler
	public void onTreeGrow(StructureGrowEvent event) {
		
		if (!(event.getSpecies().equals(TreeType.RED_MUSHROOM) || !event.getSpecies().equals(TreeType.BROWN_MUSHROOM))) {
			
			boolean withinFarm = false;
			
			for (File farm : SQTreeFarm.treefarms) {
				
				if (isWithinFarm(farm, event.getLocation())) {
					
					withinFarm = true;
					
				}
				
			}
			
			if (withinFarm) {

				event.setCancelled(false);
				
			}
			
		}
		
	}
	
	public boolean isWithinFarm(File farm, Location location) {
		
		boolean isWithin = true;
		
		List<String> lines;
		
		try {
			
			lines = Files.readAllLines(farm.toPath());
			
			int x1 = Integer.parseInt(lines.get(0));
			int y1 = Integer.parseInt(lines.get(1));
			int z1 = Integer.parseInt(lines.get(2));
			int x2 = Integer.parseInt(lines.get(3));
			int y2 = Integer.parseInt(lines.get(4));
			int z2 = Integer.parseInt(lines.get(5));
			
			if (x1 > x2) {
				
				if (!(location.getBlockX() >= x2 && location.getBlockX() <= x1)) {
					
					isWithin = false;
					
				}
				
			} else {
				
				if (!(location.getBlockX() >= x1 && location.getBlockX() <= x2)) {
					
					isWithin = false;
					
				}
				
			}
			
			if (y1 > y2) {
				
				if (!(location.getBlockY() >= y2 && location.getBlockY() <= y1)) {
					
					isWithin = false;
					
				}
				
			} else {
				
				if (!(location.getBlockY() >= y1 && location.getBlockY() <= y2)) {
					
					isWithin = false;
					
				}
				
			}
			
			if (z1 > z2) {
				
				if (!(location.getBlockZ() >= z2 && location.getBlockZ() <= z1)) {
					
					isWithin = false;
					
				}
				
			} else {
				
				if (!(location.getBlockZ() >= z1 && location.getBlockZ() <= z2)) {
					
					isWithin = false;
					
				}
				
			}
			
		} catch (IOException e) {
			
			e.printStackTrace();
			
			isWithin = false;
			
		}

		return isWithin;
		
	}

}
