package com.ginger_walnut.sqsmoothcraft.tasks;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.scheduler.BukkitScheduler;

import com.ginger_walnut.sqsmoothcraft.SQSmoothCraft;
import com.ginger_walnut.sqsmoothcraft.objects.Ship;
import com.ginger_walnut.sqsmoothcraft.objects.ShipBlock;

public class ShipTasks extends Thread {
	
	public void run() {
		
		BukkitScheduler scheduler = Bukkit.getServer().getScheduler();
		
		scheduler.scheduleSyncRepeatingTask(SQSmoothCraft.getPluginMain(), new Runnable() {
			
			@Override
			public void run() {
				
				List<ShipBlock> mainBlocks = new ArrayList<ShipBlock>();
				
				for (int i = 0; i < SQSmoothCraft.shipMap.size(); i ++) {
					
					Ship ship = (Ship) SQSmoothCraft.shipMap.values().toArray()[i];
					
					mainBlocks.add(ship.getMainBlock());
					
				}
				
				for (int i = 0; i < mainBlocks.size(); i ++) {
					
					ShipBlock mainBlock = mainBlocks.get(i);
					
					if (mainBlock.stand != null) {
						
						if (mainBlock.getShip().thirdPersonBlock != null) {
														
							if (mainBlock.getShip().thirdPersonBlock.stand.getPassenger() != mainBlock.getShip().getCaptain()) {
								
								mainBlock.getShip().thirdPersonBlock.stand.setPassenger(mainBlock.getShip().getCaptain());
								
							}
							
						} else {

							if (mainBlock.stand.getPassenger() != mainBlock.getShip().getCaptain()) {
								
								mainBlock.stand.setPassenger(mainBlock.getShip().getCaptain());
								
							}
							
						}

					} else {
						
						mainBlock.ship.exit(true);
							
					}
					
				}
				
			}			
			
		}, 0, 0);
		
	}

}
