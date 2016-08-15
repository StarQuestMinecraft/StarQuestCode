package com.starquestminecraft.sqtowerdefence;

import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;

public class SQTDTask extends BukkitRunnable {
	
	private final SQTowerDefence plugin;
	Turret turret;
	
	public SQTDTask(SQTowerDefence plugin, Turret t) {
		this.plugin = plugin;
		turret = t;
	}
	
	@Override
	public void run() {
		
		if(turret.getName().equals("EntityCheck")) {
			World world = Bukkit.getWorld(turret.worldName);
			List<Entity> entities = world.getEntities();
			
			if(!entities.isEmpty())	{
				
				for(int i=0; i<entities.size(); i++) {
					Entity entity = entities.get(i);
					
					if(entity.getCustomName() != null) {
						
						if(entity.getCustomName().equals("SQTDProjectile")) {
							
							if(entity.getType().equals(EntityType.ARROW) || entity.getType().equals(EntityType.SPLASH_POTION) || entity.getType().equals(EntityType.LINGERING_POTION) || entity.getType().equals(EntityType.FIREBALL)) {
								
								if(entity.getTicksLived() > 80) {
									BukkitTask task = new SQTDTask(plugin, turret).runTaskLater(plugin, 300);
									entity.remove();
								}
								
							}
							
						}
						
					}
					
				}
				
			}
			
		}
		else {
			turret.runTurret();
		}
		
		
	}

}
