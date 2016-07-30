package com.martinjonsson01.sqbeamtransporter.objects;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Particle;

import com.martinjonsson01.sqbeamtransporter.tasks.ParticleBeamTask;

public class ParticleBeam {
	
	private Location bottom;
	private double height;
	public ParticleBeamTask myTask;
	
	public ParticleBeam(Location bottom, double height) {
		
		this.bottom = bottom.add(0.5, 0, 0.5); //So it's the middle of the block
		this.height = height;
		this.myTask = new ParticleBeamTask(this);
		
	}
	
	public void spawnHelix() {
		
		for (double y = 0; y <= this.height; y+= 0.2) {
			double adjustedX = 1 * Math.cos(y);
			double adjustedZ = 1 * Math.sin(y);
			Location loc = this.bottom;
			loc.add(adjustedX, y, adjustedZ);
			
			loc.getWorld().spawnParticle(Particle.REDSTONE, loc, 0, -10, 0, 1);
			
			loc.subtract(adjustedX, y, adjustedZ);
		}
		
	}
	
	public void remove() {
		
		myTask.cancel();
		
	}
	
}
