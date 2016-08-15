package com.ginger_walnut.sqsmoothcraft.tasks;

import org.bukkit.Bukkit;
import org.bukkit.Color;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Projectile;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;
import org.inventivetalent.particle.ParticleEffect;


public class ProjectileSmoother extends BukkitRunnable {

	Projectile projectile = null;
	Vector velocity = null;
	
	int counter = 0;
	
	public ProjectileSmoother(Projectile firstProjectile, Vector firstVelocity) {
		
		projectile = firstProjectile;
		velocity = firstVelocity;

	}

	@Override
	public void run() {
		counter ++;
		
		if (counter < 20) {
			
			if (projectile instanceof Arrow) {
				
				ParticleEffect.REDSTONE.sendColor(Bukkit.getOnlinePlayers(), projectile.getLocation().getX(), projectile.getLocation().getY(), projectile.getLocation().getZ(), Color.RED);
				
			}
			
			projectile.setVelocity(velocity);	
			
		} else {
			
			this.cancel();
			projectile.remove();
			
		}
	
	}
	
}
