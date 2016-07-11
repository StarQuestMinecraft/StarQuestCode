package com.martinjonsson01.sqbeamtransporter.tasks;

import org.bukkit.scheduler.BukkitRunnable;

import com.martinjonsson01.sqbeamtransporter.objects.Beam;

public class BeamMoveTask extends BukkitRunnable{
	
	Beam myBeam;
	
	public BeamMoveTask(Beam beam) {
		myBeam = beam;
	}
	
	@Override
	public void run() {
		
		myBeam.taskMove();
		
	}
	
	
	
}
