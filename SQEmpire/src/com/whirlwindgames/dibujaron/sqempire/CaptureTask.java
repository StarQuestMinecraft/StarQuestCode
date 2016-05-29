package com.whirlwindgames.dibujaron.sqempire;

import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitScheduler;

import com.whirlwindgames.dibujaron.sqempire.database.object.EmpirePlayer;

public class CaptureTask extends Thread {
	
	public void run() {
		
		BukkitScheduler scheduler = Bukkit.getServer().getScheduler();
		
		scheduler.scheduleSyncRepeatingTask(SQEmpire.getInstance(), new Runnable() {
			
			@Override
			public void run() {
				
				for (Territory territory : SQEmpire.territories) {
					
					for (CapturePoint point : territory.capturePoints) {
						
						if (point.beingCaptured) {
							
							point.timeLeft = point.timeLeft - 1;
													
							if (point.timeLeft == 0) {
								
								EmpirePlayer ep = EmpirePlayer.getOnlinePlayer(((List<Player>)point.health.keySet()).get(0));
								
								point.owner = ep.getEmpire();
								
					    		int xMultiplier = point.x /point. x;
					        	int zMultiplier = point.z / point.z;
								
								if (point.owner.equals(Empire.ARATOR)) {
									
									SQEmpire.spawnRectangle(point.x * 16 + (xMultiplier * 6), point.y + 1, point.z * 16 + (zMultiplier * 6), 4, 4, Material.STAINED_GLASS, 11);
									
								} else if (point.owner.equals(Empire.YAVARI)) {
									
									SQEmpire.spawnRectangle(point.x * 16 + (xMultiplier * 6), point.y + 1, point.z * 16 + (zMultiplier * 6), 4, 4, Material.STAINED_GLASS, 10);
									
								} else if (point.owner.equals(Empire.REQUIEM)) {
									
									SQEmpire.spawnRectangle(point.x * 16 + (xMultiplier * 6), point.y + 1, point.z * 16 + (zMultiplier * 6), 4, 4, Material.STAINED_GLASS, 14);
									
								} else {
									
									SQEmpire.spawnRectangle(point.x * 16 + (xMultiplier * 6), point.y + 1, point.z * 16 + (zMultiplier * 6), 4, 4, Material.STAINED_GLASS, 0);
									
								}
								
								point.beingCaptured = false;
								
								Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "ee sudojane " + point.owner.getName() + " has captured " + point.name);
								
								Bukkit.getWorlds().get(0).getBlockAt(new Location(Bukkit.getWorlds().get(0), point.x * 16 + (xMultiplier * 7), point.y + 2, point.z * 16 + (zMultiplier * 7))).setType(Material.AIR);
								point.text = null;
								
								
							}
							
						}
						
					}
					
				}
				
			}			
			
		}, 0, 0);
		
	}

}
