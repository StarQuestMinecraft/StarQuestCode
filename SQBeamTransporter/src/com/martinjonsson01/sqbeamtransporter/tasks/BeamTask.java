package com.martinjonsson01.sqbeamtransporter.tasks;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.scheduler.BukkitScheduler;
import org.bukkit.util.Vector;

import com.martinjonsson01.sqbeamtransporter.SQBeamTransporter;
import com.martinjonsson01.sqbeamtransporter.objects.BeamTransporter;


public class BeamTask extends Thread{

	private List<Entity> removeList = new ArrayList<Entity>();
	
	public void run(){

		BukkitScheduler scheduler = Bukkit.getServer().getScheduler();

		scheduler.scheduleSyncRepeatingTask(SQBeamTransporter.getPluginMain(), new Runnable() {
			
			@Override
			public void run() {

				Iterator<Entity> entityIt = SQBeamTransporter.beamEntities.iterator();
				while (entityIt.hasNext()) {
					
					Entity passenger = entityIt.next();
					
					Location holders;

					BeamTransporter beamTransporter = SQBeamTransporter.transporterMap.get(passenger);
					
					if (!SQBeamTransporter.timeoutMap.containsKey(beamTransporter)) {
						SQBeamTransporter.timeoutMap.put(beamTransporter, System.currentTimeMillis());
					}
					
					if (!SQBeamTransporter.currentlyBeaming.contains(beamTransporter)) {
						//Bukkit.getServer().broadcastMessage(ChatColor.GREEN + "Added beamtransporter to list " + beamTransporter);
						SQBeamTransporter.currentlyBeaming.add(beamTransporter);
					}
					
					if (beamTransporter.destFloor.getFloor() > beamTransporter.startFloor.getFloor()) {
						passenger.setVelocity(new Vector(0.0D, beamTransporter.speed, 0.0D));
					} else {
						passenger.setVelocity(new Vector(0.0D, -beamTransporter.speed, 0.0D));
					}
					
					if (passenger.getLocation().getBlockX() != beamTransporter.startFloor.getStainedGlass().getX()) {
						passenger.sendMessage(ChatColor.RED + "Please do not exit the beam.");
						Location beamLoc = new Location(beamTransporter.startFloor.getWorld(), beamTransporter.startFloor.getStainedGlass().getLocation().getX()+0.5, passenger.getLocation().getY(), beamTransporter.startFloor.getStainedGlass().getLocation().getZ()+0.5, passenger.getLocation().getYaw(), passenger.getLocation().getPitch());
						passenger.teleport(beamLoc);
					}
					if (passenger.getLocation().getBlockZ() != beamTransporter.startFloor.getStainedGlass().getZ()) {
						passenger.sendMessage(ChatColor.RED + "Please do not exit the beam.");
						Location beamLoc = new Location(beamTransporter.startFloor.getWorld(), beamTransporter.startFloor.getStainedGlass().getLocation().getX()+0.5, passenger.getLocation().getY(), beamTransporter.startFloor.getStainedGlass().getLocation().getZ()+0.5, passenger.getLocation().getYaw(), passenger.getLocation().getPitch());
						passenger.teleport(beamLoc);
					}
					
					passenger.setGravity(false);
					
					passenger.setFallDistance(0.0F);
					//Bukkit.getServer().broadcastMessage("Player: " + passenger.getLocation().getY() + " Goal: " + beamTransporter.destFloor.getY() + 1.1D);
					if (beamTransporter.goingUp && passenger.getLocation().getY() > (double) beamTransporter.destFloor.getY() + 1.1D
							|| !beamTransporter.goingUp
							&& passenger.getLocation().getY() < (double) beamTransporter.destFloor.getY() - 0.9D ||
							passenger.isDead() ||
							(System.currentTimeMillis() - SQBeamTransporter.timeoutMap.get(beamTransporter)) >= 100000) {

						SQBeamTransporter.currentlyBeaming.remove(beamTransporter);
						//Bukkit.getServer().broadcastMessage(ChatColor.RED + "Removed beamtransporter from list " + beamTransporter);
						
						if (System.currentTimeMillis() - SQBeamTransporter.timeoutMap.get(beamTransporter) >= 100000) {
							passenger.sendMessage(ChatColor.RED + "Error - The beam of the beam transporter has timed out.");
						}
						
						passenger.setVelocity(new Vector(0, 0, 0));
						holders = passenger.getLocation().clone();
						if (beamTransporter.goingUp) {
							holders.setY((double) beamTransporter.destFloor.getY() + 1.9D);
						} else {
							holders.setY((double) beamTransporter.destFloor.getY() - 0.9D);
						}
						passenger.teleport(holders);
						removeList.add(passenger);
						entityIt.remove();
						if (beamTransporter.beam != null) {
							beamTransporter.beam.remove();
						}
						if (beamTransporter.pBeam != null){
							beamTransporter.pBeam.remove();
						}
						beamTransporter.setStainedGlass();
						beamTransporter.setStainedGlass();
						beamTransporter.setStainedGlass();
						
						passenger.setGravity(true);
						
					}
					
				}
				
			}



		}, 0, 0);

	}
	
	/*private void removePlayer(BeamTransporter bt) {
		
		SQBeamTransporter.playerMap.remove(bt);
		
	}*/

}
