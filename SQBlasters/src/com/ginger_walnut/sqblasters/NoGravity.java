package com.ginger_walnut.sqblasters;

import org.bukkit.Bukkit;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Entity;
import org.bukkit.scheduler.BukkitScheduler;
import org.bukkit.util.Vector;

public class NoGravity extends Thread{

	boolean canceled = false;
	
	public void run(Entity noGravEntity) {
		
		final Arrow entity = (Arrow) noGravEntity;
		final double velocityY = entity.getVelocity().getY();
		final double velocityX = entity.getVelocity().getX();
		final double velocityZ = entity.getVelocity().getZ();
		
		BukkitScheduler scheduler = Bukkit.getServer().getScheduler();
		
		scheduler.scheduleSyncRepeatingTask(Main.getPluginMain(), new Runnable() {
			
			@Override
			public void run() {
				
				if (!canceled) {
					
					//Making the arrow fly in the same direction
					entity.setVelocity(new Vector(velocityX * 3, velocityY * 3, velocityZ * 3));
					
					//Checking to see if the arrow has lived for 5 seconds + to stop infinity arrows
					if (entity.getTicksLived() >= 100) {
						
						//Removing the arrow and stopping the thread
						entity.remove();
						setCanceled(true);
						
					}
					
				}

			}
			
			public void setCanceled(boolean newCanceled) {
				
				canceled = newCanceled;
				
			}
			
		}, 0, 1);
		
	}
	
}
