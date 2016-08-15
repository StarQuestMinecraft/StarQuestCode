package com.martinjonsson01.sqbeamtransporter.tasks;

import org.bukkit.scheduler.BukkitRunnable;

import com.martinjonsson01.sqbeamtransporter.objects.ParticleBeam;

public class ParticleBeamTask extends BukkitRunnable {
	
	ParticleBeam myBeam;
	
	public ParticleBeamTask(ParticleBeam myBeam) {
		
		this.myBeam = myBeam;
		
	}
	
	public void run() {
		
		myBeam.spawnHelix();
		
	}

}
