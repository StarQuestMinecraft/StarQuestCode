package com.ginger_walnut.sqsmoothcraft.tasks;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.BlockFace;
import org.bukkit.boss.BarColor;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitScheduler;
import org.bukkit.util.EulerAngle;
import org.bukkit.util.Vector;
import com.ginger_walnut.sqsmoothcraft.SQSmoothCraft;
import com.ginger_walnut.sqsmoothcraft.objects.Ship;
import com.ginger_walnut.sqsmoothcraft.objects.ShipBlock;
import com.ginger_walnut.sqsmoothcraft.objects.ShipLocation;

public class ShipMovement extends Thread {
	
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
				
				for (int i = 0; i < SQSmoothCraft.stoppedShipMap.size(); i ++) {
					
					mainBlocks.add(SQSmoothCraft.stoppedShipMap.get(i).getMainBlock());
					
				}
				
				for (int i = 0; i < mainBlocks.size(); i ++) {
					
					ShipBlock mainBlock = mainBlocks.get(i);

					if (SQSmoothCraft.shipMap.containsValue(mainBlock.getShip())) {
						
						Player pilot = (Player) mainBlock.getShip().getCaptain();
						
						Ship ship = SQSmoothCraft.shipMap.get(pilot.getUniqueId());
						
						float shipYaw = (float) ship.movingDirection.yaw;
						float shipDirectionYaw = (float) ship.pointingDirection.yaw;
						float pilotYaw = pilot.getLocation().getYaw();
						
						float shipPitch = (float) ship.movingDirection.pitch;
						float shipDirectionPitch = (float) ship.pointingDirection.pitch;
						float pilotPitch = pilot.getLocation().getPitch();
							
						Location location = ship.getLocation();
						
						location.setYaw(shipYaw);
						location.setPitch(shipPitch);
						
						Location directionLocation = ship.getLocation();
						
						directionLocation.setYaw((float) ship.pointingDirection.yaw);
						directionLocation.setPitch((float) ship.pointingDirection.pitch);
							
						if (pilot != null) {
								
							if ((ship.speed / ship.maxSpeed) < 1.0) {
								
								if (ship.speed > 0) {
									
									ship.speedBar.setColor(BarColor.BLUE);
									
									ship.speedBar.setProgress(ship.speed / ship.maxSpeed);
									
								} else if (ship.speed < 0) {
									
									ship.speedBar.setColor(BarColor.RED);
									
									ship.speedBar.setProgress(Math.abs(ship.speed) / ship.maxSpeed);
									
								} else {
									
									ship.speedBar.setColor(BarColor.WHITE);
									
									ship.speedBar.setProgress(0.0f);
									
								}
								
							}

							ship.fuel = ship.fuel - ((((float) SQSmoothCraft.config.getDouble("utilites.reactor.fuel per second") / 20) * (Math.abs(ship.speed) / ship.maxSpeed)) * ship.reactorList.size() * ship.reactorList.size());
							
							if (ship.fuel < 0.0f) {
								
								ship.fuel = 0.0f;
								
							}

							if (ship.startingFuel > 0) {
								
								ship.fuelBar.setProgress((ship.fuel / ship.startingFuel));
								
							} else {
								
								ship.fuelBar.setProgress(0.0);
								
							}
							
							if (ship.fuel > 0.0f) {

								if (pilot.getInventory().getItemInMainHand().getType().equals(Material.WATCH)) {
								
									location.setYaw(slowRotation(shipYaw, pilotYaw, ship.getMaxYawRate()));
									location.setPitch(slowRotation(shipPitch, pilotPitch, ship.getMaxYawRate()));
									directionLocation.setYaw(slowRotation(shipDirectionYaw, pilotYaw, ship.getMaxYawRate()));
									directionLocation.setPitch(slowRotation(shipDirectionPitch, pilotPitch, ship.getMaxYawRate()));
									
								}
									
							} else { 
								
								ship.decelerate(.2f);
									
							}
						
						}
						
						double yawSin = ship.movingDirection.adjustedYawSin;
						double yawCos = ship.movingDirection.adjustedYawCos;
						
						double pitchSin = ship.movingDirection.adjustedPitchSin;
						double pitchCos = ship.movingDirection.adjustedPitchCos;

						ship.setLastDirections();
						
						ship.lastLocation.setX(ship.location.getX());
						ship.lastLocation.setY(ship.location.getY());
						ship.lastLocation.setZ(ship.location.getZ());
						
						Vector shipDirection = new Vector(pitchSin * yawCos, pitchCos, pitchSin * yawSin);

						shipDirection.multiply(ship.getSpeed());
							
						Location newLocation = ship.location.toVector().add(shipDirection).toLocation(pilot.getWorld());

						ship.setLocation(newLocation);
						
						ship.movingDirection.setYaw(location.getYaw());
						ship.movingDirection.setPitch(location.getPitch());
						
						if (!ship.lockedDirection) {				
							
							ship.pointingDirection.setYaw(directionLocation.getYaw());
							ship.pointingDirection.setPitch(directionLocation.getPitch());
							
						}
							
					} else {
						
						Ship ship = mainBlock.getShip();
						
						ship.decelerate(.2f);
							
						double yawSin = ship.movingDirection.adjustedYawSin;
						double yawCos = ship.movingDirection.adjustedYawCos;
						
						double pitchSin = ship.movingDirection.adjustedPitchSin;
						double pitchCos = ship.movingDirection.adjustedPitchCos;
						
						ship.setLastDirections();
						
						ship.lastLocation.setX(ship.location.getX());
						ship.lastLocation.setY(ship.location.getY());
						ship.lastLocation.setZ(ship.location.getZ());
						
						Vector shipDirection = new Vector(pitchSin * yawCos, pitchCos, pitchSin * yawSin);
						
						shipDirection.multiply(ship.getSpeed());
							
						Location newLocation = ship.getLocation().toVector().add(shipDirection).toLocation(ship.getMainBlock().getShipLocation().getWorld());
						
						ship.setLocation(newLocation);
						
					}
					
				}
				
				List<ShipBlock> shipBlocks = new ArrayList<ShipBlock>();
				
				for (int i = 0; i < SQSmoothCraft.shipMap.size(); i ++) {
					
					Ship ship = (Ship) SQSmoothCraft.shipMap.values().toArray()[i];
					
					shipBlocks.addAll(ship.getShipBlocks());

					double yaw = Math.toRadians(ship.pointingDirection.yaw);
					double pitch = Math.toRadians(ship.pointingDirection.pitch);
					
					double yawCos = ship.pointingDirection.yawCos;
					double yawSin = ship.pointingDirection.yawSin;
					
					double pitchCos = ship.pointingDirection.pitchCos;
					double pitchSin = ship.pointingDirection.pitchSin;
					
					EulerAngle eulerAngle = null;
	
					if (ship.alternatingBlockDirection) {
						
						eulerAngle = new EulerAngle(pitch, yaw, 0);
						
						ship.alternatingBlockDirection = false;
						
					} else {
						
						eulerAngle = new EulerAngle(pitch, yaw + 6.283185, 0);
						
						ship.alternatingBlockDirection = true;
						
					}
					
					double arg1 = yawSin * pitchSin * -1;
					double arg2 = yawSin * pitchCos;
					double arg3 = yawCos * pitchSin;
					double arg4 = yawCos * pitchCos;
						
					for (int j = 0; j < shipBlocks.size(); j ++) {

						ArmorStand stand = shipBlocks.get(j).getArmorStand();
						
						ShipLocation shipLocation = shipBlocks.get(j).getShipLocation();
							
						//Location locationShip = shipLocation.toLocation(ship.getLocation(), yawCos, yawSin, pitchCos, pitchSin);
						
						Location locationShip = new Location(shipLocation.getWorld(),
								((arg1 * shipLocation.y) - (arg2 * shipLocation.z) + (yawCos * shipLocation.x)) * .625, 
								((shipLocation.y * pitchCos) - (shipLocation.z * pitchSin)) * .625, 
								((arg3 * shipLocation.y) + (arg4 * shipLocation.z) + (yawSin * shipLocation.x)) * .625).add(ship.location);
									
						locationShip.setYaw(0);
						locationShip.setPitch(0);
						
						double x = locationShip.getX();
						double y = locationShip.getY();
						double z = locationShip.getZ();
						
						Location detectionLocation = locationShip;
						detectionLocation.add(0, 1.625, 0);
						
/*						Location detectionLocation = locationShip;
						detectionLocation.add(.3125, 1.3125, .3125);
						
						List<Location> detectionLocations = new ArrayList<Location>();
						
						detectionLocations.add(detectionLocation);
						
						detectionLocation = locationShip;
						detectionLocation.add(-.3125, 1.3125, .3125);
						detectionLocations.add(detectionLocation);
						
						detectionLocation = locationShip;
						detectionLocation.add(.3125, 1.3125, -.3125);
						detectionLocations.add(detectionLocation);
						
						detectionLocation = locationShip;
						detectionLocation.add(-.3125, 1.3125, -.3125);
						detectionLocations.add(detectionLocation);
						
						detectionLocation = locationShip;
						detectionLocation.add(.3125, 1.9375, .3125);
						detectionLocations.add(detectionLocation);
						
						detectionLocation = locationShip;
						detectionLocation.add(-.3125, 1.9375, .3125);
						detectionLocations.add(detectionLocation);
						
						detectionLocation = locationShip;
						detectionLocation.add(.3125, 1.9375, -.3125);
						detectionLocations.add(detectionLocation);
						
						detectionLocation = locationShip;
						detectionLocation.add(-.3125, 1.9375, -.3125);
						detectionLocations.add(detectionLocation);
						
						boolean clear = true;
						
						for (Location detectLocation : detectionLocations) {
							
							if (!detectLocation.getWorld().getBlockAt(detectLocation).getType().equals(Material.AIR)) {
							
								clear = false;
								
							}
							
						}*/
						
						//if (clear) {			
						
						List<Material> flyableBlocks = new ArrayList<Material>();
						
						flyableBlocks.add(Material.AIR);
						//flyableBlocks.add(Material.WATER);
						flyableBlocks.add(Material.YELLOW_FLOWER);
						flyableBlocks.add(Material.LONG_GRASS);
						//flyableBlocks.add(Material.STATIONARY_WATER);
						
						if (flyableBlocks.contains(detectionLocation.getWorld().getBlockAt(detectionLocation).getType())) {

							locationShip.setX(x);
							locationShip.setY(y);
							locationShip.setZ(z);
							
							stand.teleport(locationShip);
							
							if (flyableBlocks.contains(locationShip.getWorld().getBlockAt(locationShip).getType())) {

								stand.setVelocity(locationShip.toVector().subtract(stand.getLocation().toVector()));

							} else {
								
								if (shipBlocks.get(j).mainBlock.equals(shipBlocks.get(j))) {
								
									stand.setVelocity(locationShip.toVector().subtract(stand.getLocation().toVector()));
								
								} else {
									
									stand.setVelocity(new Vector(0, 0, 0));
									
								}
								
							}

						} else {
							
							ship.location.setX(ship.lastLocation.getX());
							ship.location.setY(ship.lastLocation.getY());
							ship.location.setZ(ship.lastLocation.getZ());
							
							ship.revetDirections();

							double health = shipBlocks.get(j).health;
							double speed = ship.speed;
								
							if (health < (ship.speed * 500)) {
									
								ship.speed = (float) (ship.speed - (health / 1000));

							} else {
									
								ship.speed = 0;
									
							}
								
							shipBlocks.get(j).ship.damage(shipBlocks.get(j), speed * 500, false, null);
								
						}
						
						stand.setHeadPose(eulerAngle);

					}
					
					shipBlocks.clear();
					
				}
				
				for (int i = 0; i < SQSmoothCraft.stoppedShipMap.size(); i ++) {
				
					if (!SQSmoothCraft.shipMap.containsValue(SQSmoothCraft.stoppedShipMap.get(i))) {
					
						Ship ship = (Ship) SQSmoothCraft.stoppedShipMap.get(i);
					
						shipBlocks.addAll(ship.getShipBlocks());

						double yaw = Math.toRadians(ship.pointingDirection.yaw);
						double pitch = Math.toRadians(ship.pointingDirection.pitch);
						
						double yawCos = ship.pointingDirection.yawCos;
						double yawSin = ship.pointingDirection.yawSin;
						
						double pitchCos = ship.pointingDirection.pitchCos;
						double pitchSin = ship.pointingDirection.pitchSin;
					
						for (int j = 0; j < shipBlocks.size(); j ++) {
						
							ArmorStand stand = shipBlocks.get(j).getArmorStand();
						
							EulerAngle eulerAngle = null;

							eulerAngle = new EulerAngle(pitch, yaw, 0);
							
							ShipLocation shipLocation = shipBlocks.get(j).getShipLocation();
							
							Location locationShip = shipLocation.toLocation(ship.getLocation(), yawCos, yawSin, pitchCos, pitchSin);
							
							double x = locationShip.getX();
							double y = locationShip.getY();
							double z = locationShip.getZ();
							
							Location detectionLocation = locationShip;
							detectionLocation.add(0, 1.625, 0);
							
	/*						Location detectionLocation = locationShip;
							detectionLocation.add(.3125, 1.3125, .3125);
							
							List<Location> detectionLocations = new ArrayList<Location>();
							
							detectionLocations.add(detectionLocation);
							
							detectionLocation = locationShip;
							detectionLocation.add(-.3125, 1.3125, .3125);
							detectionLocations.add(detectionLocation);
							
							detectionLocation = locationShip;
							detectionLocation.add(.3125, 1.3125, -.3125);
							detectionLocations.add(detectionLocation);
							
							detectionLocation = locationShip;
							detectionLocation.add(-.3125, 1.3125, -.3125);
							detectionLocations.add(detectionLocation);
							
							detectionLocation = locationShip;
							detectionLocation.add(.3125, 1.9375, .3125);
							detectionLocations.add(detectionLocation);
							
							detectionLocation = locationShip;
							detectionLocation.add(-.3125, 1.9375, .3125);
							detectionLocations.add(detectionLocation);
							
							detectionLocation = locationShip;
							detectionLocation.add(.3125, 1.9375, -.3125);
							detectionLocations.add(detectionLocation);
							
							detectionLocation = locationShip;
							detectionLocation.add(-.3125, 1.9375, -.3125);
							detectionLocations.add(detectionLocation);
							
							boolean clear = true;
							
							for (Location detectLocation : detectionLocations) {
								
								if (!detectLocation.getWorld().getBlockAt(detectLocation).getType().equals(Material.AIR)) {
								
									clear = false;
									
								}
								
							}*/
							
							//if (clear) {
							
							List<Material> flyableBlocks = new ArrayList<Material>();
							
							flyableBlocks.add(Material.AIR);
							//flyableBlocks.add(Material.WATER);
							flyableBlocks.add(Material.YELLOW_FLOWER);
							flyableBlocks.add(Material.LONG_GRASS);
							//flyableBlocks.add(Material.STATIONARY_WATER);
							
							if (flyableBlocks.contains(detectionLocation.getWorld().getBlockAt(detectionLocation).getType())) {
								
								locationShip.setX(x);
								locationShip.setY(y);
								locationShip.setZ(z);
								
								stand.teleport(locationShip);
								
								if (flyableBlocks.contains(locationShip.getWorld().getBlockAt(locationShip).getType())) {

									stand.setVelocity(locationShip.toVector().subtract(stand.getLocation().toVector()));

								} else {
									
									if (shipBlocks.get(j).mainBlock.equals(shipBlocks.get(j))) {
									
										stand.setVelocity(locationShip.toVector().subtract(stand.getLocation().toVector()));
									
									} else {
										
										stand.setVelocity(new Vector(0, 0, 0));
										
									}
									
								}

							} else {
								
								ship.location.setX(ship.lastLocation.getX());
								ship.location.setY(ship.lastLocation.getY());
								ship.location.setZ(ship.lastLocation.getZ());
								
								ship.revetDirections();

								double health = shipBlocks.get(j).health;
								double speed = ship.speed;
									
								if (health < (ship.speed * 500)) {
										
									ship.speed = (float) (ship.speed - (health / 1000));

								} else {
										
									ship.speed = 0;
										
								}
									
								shipBlocks.get(j).ship.damage(shipBlocks.get(j), speed * 500, false, null);
									
							}

							stand.setHeadPose(eulerAngle);
						
						}
						
						for (Player player : Bukkit.getOnlinePlayers()) {
							
							player.hidePlayer(ship.captain);
							player.showPlayer(ship.captain);
							
						}
					
						shipBlocks.clear();
						
					}
					
				}
				
			}			
			
		}, 0, 0);
		
	}
	
	static float slowRotation(float currentDegree, float targetDegree, float maxChange) {

		float newDegree = currentDegree;
		
		if (currentDegree != targetDegree) {
			
			if (targetDegree < 0) {
				
				targetDegree = targetDegree * -1;
					
				targetDegree = 360 - targetDegree;
					
			}
				
			if (currentDegree < 0) {
					
				currentDegree = currentDegree * -1;
					
				currentDegree = 360 - currentDegree;
					
			}

			if (currentDegree > targetDegree) {
					
				float way1 = (360 - currentDegree) + targetDegree;
				float way2 = currentDegree - targetDegree;
					
				if (way1 > way2) {
						
					if (way2 >= maxChange) {

						newDegree = currentDegree - maxChange;
							
						if (newDegree < 0) {
								
							newDegree = 360 - newDegree;
								
						}

					} else {

						newDegree = currentDegree - way2;
							
						if (newDegree < 0) {
							
							newDegree = 360 - newDegree;
								
						}

					}
						
				} else {
						
					if (way1 >= maxChange) {

						newDegree = currentDegree + maxChange;

					} else {

						newDegree = currentDegree + way1;

					}
						
				}
					
			} else {
					
				float way1 = (360 - targetDegree) + currentDegree;
				float way2 = targetDegree - currentDegree;
					
				if (way1 > way2) {
						
					if (way2 >= maxChange) {

						newDegree = currentDegree + maxChange;

					} else {

						newDegree = currentDegree + way2;

					}
						
				} else {
						
					if (way1 >= maxChange) {

						newDegree = currentDegree - maxChange;
							
						if (newDegree < 0) {
							
							newDegree = 360 - newDegree;
								
						}

					} else {

						newDegree = currentDegree - way1;
							
						if (newDegree < 0) {
							
							newDegree = 360 - newDegree;
								
						}

					}
						
				}
					
			}
			
		}
		
		return newDegree;
		
	}

}
