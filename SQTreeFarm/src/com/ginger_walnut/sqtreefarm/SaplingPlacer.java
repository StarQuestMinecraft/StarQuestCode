package com.ginger_walnut.sqtreefarm;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.scheduler.BukkitScheduler;

public class SaplingPlacer extends Thread{

	public void run() {
		
		BukkitScheduler scheduler = Bukkit.getServer().getScheduler();
		
		scheduler.scheduleSyncRepeatingTask(SQTreeFarm.getPluginMain(), new Runnable() {
			
			@Override
			public void run() {
				
				for (File file : SQTreeFarm.treefarms) {
					
					int width;
					int height;
					int length;
					
					List<String> lines;
					
					try {
						
						lines = Files.readAllLines(file.toPath());
						
						int x1 = Integer.parseInt(lines.get(0));
						int y1 = Integer.parseInt(lines.get(1));
						int z1 = Integer.parseInt(lines.get(2));
						int x2 = Integer.parseInt(lines.get(3));
						int y2 = Integer.parseInt(lines.get(4));
						int z2 = Integer.parseInt(lines.get(5));
						
						width = Math.abs(x1 - x2) + 1;
						height = Math.abs(y1 - y2) + 1;
						length = Math.abs(z1 - z2) + 1;
						
						for (int x = 0; x < width; x ++) {
							
							for (int y = 0; y < height; y ++) {
								
								for (int z = 0; z < length; z ++) {
									
									if (x1 > x2) {
										
										if (z1 > z2) {
											
											if (Bukkit.getWorlds().get(0).getBlockAt(new Location(Bukkit.getWorlds().get(0), x1 - x, y1 + y - 1, z1 - z)).getType().equals(Material.DIRT)) {
												
												if (Bukkit.getWorlds().get(0).getBlockAt(new Location(Bukkit.getWorlds().get(0), x1 - x, y1 + y, z1 - z)).getType().equals(Material.AIR)) {
													
													Bukkit.getWorlds().get(0).getBlockAt(new Location(Bukkit.getWorlds().get(0), x1 - x, y1 + y, z1 - z)).setType(Material.SAPLING);
													Bukkit.getWorlds().get(0).getBlockAt(new Location(Bukkit.getWorlds().get(0), x1 - x, y1 + y, z1 - z)).setData((byte)2);
													
												}
												
											}
											
										} else {
											
											if (Bukkit.getWorlds().get(0).getBlockAt(new Location(Bukkit.getWorlds().get(0), x1 - x, y1 + y - 1, z1 + z)).getType().equals(Material.DIRT)) {
												
												if (Bukkit.getWorlds().get(0).getBlockAt(new Location(Bukkit.getWorlds().get(0), x1 - x, y1 + y, z1 + z)).getType().equals(Material.AIR)) {
													
													Bukkit.getWorlds().get(0).getBlockAt(new Location(Bukkit.getWorlds().get(0), x1 - x, y1 + y, z1 + z)).setType(Material.SAPLING);
													Bukkit.getWorlds().get(0).getBlockAt(new Location(Bukkit.getWorlds().get(0), x1 - x, y1 + y, z1 + z)).setData((byte)2);
													
												}
												
											}
											
										}
										
									} else {
										
										if (z1 > z2) {

											if (Bukkit.getWorlds().get(0).getBlockAt(new Location(Bukkit.getWorlds().get(0), x1 + x, y1 + y - 1, z1 - z)).getType().equals(Material.DIRT)) {
												
												if (Bukkit.getWorlds().get(0).getBlockAt(new Location(Bukkit.getWorlds().get(0), x1 + x, y1 + y, z1 - z)).getType().equals(Material.AIR)) {
													
													Bukkit.getWorlds().get(0).getBlockAt(new Location(Bukkit.getWorlds().get(0), x1 + x, y1 + y, z1 - z)).setType(Material.SAPLING);
													Bukkit.getWorlds().get(0).getBlockAt(new Location(Bukkit.getWorlds().get(0), x1 + x, y1 + y, z1 - z)).setData((byte)2);
													
												}
												
											}
											
										} else {
											
											if (Bukkit.getWorlds().get(0).getBlockAt(new Location(Bukkit.getWorlds().get(0), x1 + x, y1 + y - 1, z1 + z)).getType().equals(Material.DIRT)) {
												
												if (Bukkit.getWorlds().get(0).getBlockAt(new Location(Bukkit.getWorlds().get(0), x1 + x, y1 + y, z1 + z)).getType().equals(Material.AIR)) {
													
													Bukkit.getWorlds().get(0).getBlockAt(new Location(Bukkit.getWorlds().get(0), x1 + x, y1 + y, z1 + z)).setType(Material.SAPLING);
													Bukkit.getWorlds().get(0).getBlockAt(new Location(Bukkit.getWorlds().get(0), x1 + x, y1 + y, z1 + z)).setData((byte)2);
													
												}
												
											}
											
										}
										
									}
									
								}
								
							}
							
						}
						
					} catch (IOException e) {
						
						e.printStackTrace();
						
					}

				}
				
			}
			
		},0, 100);
		
	}
	
}
