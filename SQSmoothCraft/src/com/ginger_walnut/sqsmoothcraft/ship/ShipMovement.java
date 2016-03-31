package com.ginger_walnut.sqsmoothcraft.ship;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitScheduler;
import org.bukkit.util.EulerAngle;
import org.bukkit.util.Vector;

import com.ginger_walnut.sqsmoothcraft.SQSmoothCraft;

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
						
						float shipYaw = ship.getLocation().getYaw();
						float shipDirectionYaw = (float) ship.yawDirection;
						float pilotYaw = pilot.getLocation().getYaw();
						
						float shipPitch = ship.getLocation().getPitch();
						float shipDirectionPitch = (float) ship.pitchDirection;
						float pilotPitch = pilot.getLocation().getPitch();
							
						Location location = ship.getLocation();
						Location directionLocation = ship.getLocation();
						
						directionLocation.setYaw((float) ship.yawDirection);
						directionLocation.setPitch((float) ship.pitchDirection);
							
						if (pilot != null) {
								
							ship.speedBar.setProgress(ship.speed / ship.maxSpeed);
							
							ship.fuel = ship.fuel - ((((float) SQSmoothCraft.config.getDouble("utilities.reactor.fuel per second") / 20) * (ship.speed / ship.maxSpeed)) * ship.reactorList.size() * ship.reactorList.size());
							
							if (ship.fuel < 0.0f) {
								
								ship.fuel = 0.0f;
								
							}
							
							ship.fuelBar.setProgress(ship.fuel / ship.startingFuel);
							
							if (ship.fuel > 0.0f) {
							
								if (pilot.getInventory().getItemInMainHand().getType().equals(Material.WATCH)) {
								
									if (!(shipYaw == pilotYaw)) {
										
										if (pilotYaw < 0) {
												
											pilotYaw = pilotYaw * -1;
												
											pilotYaw = 360 - pilotYaw;
												
										}
											
										if (shipYaw < 0) {
												
											shipYaw = shipYaw * -1;
												
											shipYaw = 360 - shipYaw;
												
										}
		
										if (shipYaw > pilotYaw) {
												
											float way1 = (360 - shipYaw) + pilotYaw;
											float way2 = shipYaw - pilotYaw;
												
											if (way1 > way2) {
													
												if (way2 >= ship.getMaxYawRate()) {
		
													location.setYaw(shipYaw - ship.getMaxYawRate());
														
													if (location.getYaw() < 0) {
															
														location.setYaw(360 - location.getYaw());
															
													}
					
												} else {
		
													location.setYaw(shipYaw - way2);
														
													if (location.getYaw() < 0) {
															
														location.setYaw(360 -location.getYaw());
															
													}
		
												}
													
											} else {
													
												if (way1 >= ship.getMaxYawRate()) {
		
													location.setYaw(shipYaw + ship.getMaxYawRate());
					
												} else {
		
													location.setYaw(shipYaw + way1);
		
												}
													
											}
												
										} else {
												
											float way1 = (360 - pilotYaw) + shipYaw;
											float way2 = pilotYaw - shipYaw;
												
											if (way1 > way2) {
													
												if (way2 >= ship.getMaxYawRate()) {
		
													location.setYaw(shipYaw + ship.getMaxYawRate());
					
												} else {
		
													location.setYaw(shipYaw + way2);
		
												}
													
											} else {
													
												if (way1 >= ship.getMaxYawRate()) {
		
													location.setYaw(shipYaw - ship.getMaxYawRate());
														
													if (location.getYaw() < 0) {
															
														location.setYaw(360 -location.getYaw());
															
													}
					
												} else {
		
													location.setYaw(shipYaw - way1);
														
													if (location.getYaw() < 0) {
															
														location.setYaw(360 -location.getYaw());
															
													}
		
												}
													
											}
												
										}
											
									}
									
									if (!(shipPitch == pilotPitch)) {
	
										if (pilotPitch < 0) {
												
											pilotPitch = pilotPitch * -1;
												
											pilotPitch = 360 - pilotPitch;
												
										}
											
										if (shipPitch < 0) {
												
											shipPitch = shipPitch * -1;
												
											shipPitch = 360 - shipPitch;
												
										}
		
										if (shipPitch > pilotPitch) {
												
											float way1 = (360 - shipPitch) + pilotPitch;
											float way2 = shipPitch - pilotPitch;
												
											if (way1 > way2) {
													
												if (way2 >= ship.getMaxYawRate()) {
		
													location.setPitch(shipPitch - ship.getMaxYawRate());
														
													if (location.getPitch() < 0) {
															
														location.setPitch(360 - location.getPitch());
															
													}
					
												} else {
		
													location.setPitch(shipPitch - way2);
														
													if (location.getPitch() < 0) {
															
														location.setPitch(360 -location.getPitch());
															
													}
		
												}
													
											} else {
													
												if (way1 >= ship.getMaxYawRate()) {
		
													location.setPitch(shipPitch + ship.getMaxYawRate());
					
												} else {
		
													location.setPitch(shipPitch + way1);
		
												}
													
											}
												
										} else {
												
											float way1 = (360 - pilotPitch) + shipPitch;
											float way2 = pilotPitch - shipPitch;
												
											if (way1 > way2) {
													
												if (way2 >= ship.getMaxYawRate()) {
		
													location.setPitch(shipPitch + ship.getMaxYawRate());
					
												} else {
		
													location.setPitch(shipPitch + way2);
		
												}
													
											} else {
													
												if (way1 >= ship.getMaxYawRate()) {
		
													location.setPitch(shipPitch - ship.getMaxYawRate());
														
													if (location.getPitch() < 0) {
															
														location.setPitch(360 -location.getPitch());
															
													}
					
												} else {
		
													location.setPitch(shipPitch - way1);
														
													if (location.getPitch() < 0) {
															
														location.setPitch(360 -location.getPitch());
															
													}
		
												}
													
											}
												
										}
											
									}
									
									if (!(shipDirectionYaw == pilotYaw)) {
										
										if (pilotYaw < 0) {
												
											pilotYaw = pilotYaw * -1;
												
											pilotYaw = 360 - pilotYaw;
												
										}
											
										if (shipDirectionYaw < 0) {
												
											shipDirectionYaw = shipDirectionYaw * -1;
												
											shipDirectionYaw = 360 - shipDirectionYaw;
												
										}
		
										if (shipDirectionYaw > pilotYaw) {
												
											float way1 = (360 - shipDirectionYaw) + pilotYaw;
											float way2 = shipDirectionYaw - pilotYaw;
												
											if (way1 > way2) {
													
												if (way2 >= ship.getMaxYawRate()) {
		
													directionLocation.setYaw(shipDirectionYaw - ship.getMaxYawRate());
														
													if (directionLocation.getYaw() < 0) {
															
														directionLocation.setYaw(360 - directionLocation.getYaw());
															
													}
					
												} else {
		
													directionLocation.setYaw(shipDirectionYaw - way2);
														
													if (directionLocation.getYaw() < 0) {
															
														directionLocation.setYaw(360 - directionLocation.getYaw());
															
													}
		
												}
													
											} else {
													
												if (way1 >= ship.getMaxYawRate()) {
		
													directionLocation.setYaw(shipDirectionYaw + ship.getMaxYawRate());
					
												} else {
		
													directionLocation.setYaw(shipDirectionYaw + way1);
		
												}
													
											}
												
										} else {
												
											float way1 = (360 - pilotYaw) + shipDirectionYaw;
											float way2 = pilotYaw - shipDirectionYaw;
												
											if (way1 > way2) {
													
												if (way2 >= ship.getMaxYawRate()) {
		
													directionLocation.setYaw(shipDirectionYaw + ship.getMaxYawRate());
					
												} else {
		
													directionLocation.setYaw(shipDirectionYaw + way2);
		
												}
													
											} else {
													
												if (way1 >= ship.getMaxYawRate()) {
		
													directionLocation.setYaw(shipDirectionYaw - ship.getMaxYawRate());
														
													if (directionLocation.getYaw() < 0) {
															
														directionLocation.setYaw(360 - directionLocation.getYaw());
															
													}
					
												} else {
		
													directionLocation.setYaw(shipDirectionYaw - way1);
														
													if (directionLocation.getYaw() < 0) {
															
														directionLocation.setYaw(360 - directionLocation.getYaw());
															
													}
		
												}
													
											}
												
										}
											
									}
									
									if (!(shipDirectionPitch == pilotPitch)) {
	
										if (pilotPitch < 0) {
												
											pilotPitch = pilotPitch * -1;
												
											pilotPitch = 360 - pilotPitch;
												
										}
											
										if (shipDirectionPitch < 0) {
												
											shipDirectionPitch = shipDirectionPitch * -1;
												
											shipDirectionPitch = 360 - shipDirectionPitch;
												
										}
		
										if (shipPitch > pilotPitch) {
												
											float way1 = (360 - shipDirectionPitch) + pilotPitch;
											float way2 = shipDirectionPitch - pilotPitch;
												
											if (way1 > way2) {
													
												if (way2 >= ship.getMaxYawRate()) {
		
													directionLocation.setPitch(shipDirectionPitch - ship.getMaxYawRate());
														
													if (directionLocation.getPitch() < 0) {
															
														directionLocation.setPitch(360 - directionLocation.getPitch());
															
													}
					
												} else {
		
													directionLocation.setPitch(shipDirectionPitch - way2);
														
													if (directionLocation.getPitch() < 0) {
															
														directionLocation.setPitch(360 - directionLocation.getPitch());
															
													}
		
												}
													
											} else {
													
												if (way1 >= ship.getMaxYawRate()) {
		
													directionLocation.setPitch(shipDirectionPitch + ship.getMaxYawRate());
					
												} else {
		
													directionLocation.setPitch(shipDirectionPitch + way1);
		
												}
													
											}
												
										} else {
												
											float way1 = (360 - pilotPitch) + shipDirectionPitch;
											float way2 = pilotPitch - shipDirectionPitch;
												
											if (way1 > way2) {
													
												if (way2 >= ship.getMaxYawRate()) {
		
													directionLocation.setPitch(shipDirectionPitch + ship.getMaxYawRate());
					
												} else {
		
													directionLocation.setPitch(shipDirectionPitch + way2);
		
												}
													
											} else {
													
												if (way1 >= ship.getMaxYawRate()) {
		
													directionLocation.setPitch(shipDirectionPitch - ship.getMaxYawRate());
														
													if (directionLocation.getPitch() < 0) {
															
														directionLocation.setPitch(360 - directionLocation.getPitch());
															
													}
					
												} else {
		
													directionLocation.setPitch(shipDirectionPitch - way1);
														
													if (directionLocation.getPitch() < 0) {
															
														directionLocation.setPitch(360 - directionLocation.getPitch());
															
													}
		
												}
													
											}
												
										}
											
									}
									
									ship.setLocation(location);
									
								}
									
							} else { 
								
								ship.decelerate(.2f);
									
							}
						
						}
						
						double yawSin = ship.getAdjustedYawSin();
						double yawCos = ship.getAdjustedYawCos();
						
						double pitchSin = ship.getAdjustedPitchSin();
						double pitchCos = ship.getAdjustedPitchCos();
						
						Vector shipDirection = new Vector(pitchSin * yawCos, pitchCos, pitchSin * yawSin);
						
						shipDirection.multiply(ship.getSpeed());
							
						Location newLocation = ship.location.toVector().add(shipDirection).toLocation(pilot.getWorld());
						
						ship.setLocation(newLocation);
						
						ship.setMovingYaw(location.getYaw());
						ship.setMovingPitch(location.getPitch());
						
						if (!ship.lockedDirection) {				
							
							ship.setDirectionYaw(directionLocation.getYaw());
							ship.setDirectionPitch(directionLocation.getPitch());
							
						}
						
						ship.lastLocation = ship.location;
						
//						ship.lastLocation.setYaw(ship.location.getYaw());
//						ship.lastLocation.setPitch(ship.location.getPitch());
							
					} else {
						
						Ship ship = mainBlock.getShip();
						
						if (ship.getSpeed() == 0.0f) {
							
						} else if (ship.getSpeed() < 0.0f) {
								
							ship.setSpeed(0.0f);
								
						} else {
								
							ship.setSpeed(ship.getSpeed() - (ship.getAcceleration() / 5));
								
						}
						
						if (ship.getSpeed() < 0.0f) {
							
							ship.setSpeed(0.0f);
							
						}
							
						double yawSin = ship.getAdjustedYawSin();
						double yawCos = ship.getAdjustedYawCos();
						
						double pitchSin = ship.getAdjustedPitchSin();
						double pitchCos = ship.getAdjustedPitchCos();
						
						Vector shipDirection = new Vector(pitchSin * yawCos, pitchCos, pitchSin * yawSin);
						
						shipDirection.multiply(ship.getSpeed());
							
						Location newLocation = ship.getLocation().toVector().add(shipDirection).toLocation(ship.getMainBlock().getShipLocation().getWorld());
							
						newLocation.setYaw(ship.getLocation().getYaw());
						newLocation.setPitch(ship.getLocation().getPitch());	
						
						ship.setLocation(newLocation);
						
						ship.lastLocation = ship.location;
						
//						ship.lastLocation.setYaw(ship.location.getYaw());
//						ship.lastLocation.setPitch(ship.location.getPitch());
						
					}
					
				}
						
				List<ShipBlock> shipBlocks = new ArrayList<ShipBlock>();
				
				for (int i = 0; i < SQSmoothCraft.shipMap.size(); i ++) {
				
					Ship ship = (Ship) SQSmoothCraft.shipMap.values().toArray()[i];
					
					shipBlocks.addAll(ship.getShipBlocks());

					double yaw = Math.toRadians(ship.yawDirection);
					double pitch = Math.toRadians(ship.pitchDirection);
					
					double yawCos = ship.yawCos;
					double yawSin = ship.yawSin;
					
					double pitchCos = ship.pitchCos;
					double pitchSin = ship.pitchSin;
					
					EulerAngle eulerAngle = null;
	
//					if (ship.alternatingBlockDirection) {
						
						eulerAngle = new EulerAngle(pitch, yaw, 0);
						
//						ship.alternatingBlockDirection = false;
//						
//					} else {
//						
//						eulerAngle = new EulerAngle(pitch * -1, yaw + 3.1415, 0);
//						
//						ship.alternatingBlockDirection = true;
//						
//					}
					
					for (int j = 0; j < shipBlocks.size(); j ++) {
						
						ArmorStand stand = shipBlocks.get(j).getArmorStand();
						
						ShipLocation shipLocation = shipBlocks.get(j).getShipLocation();
							
						Location locationShip = shipLocation.toLocation(ship, yawCos, yawSin, pitchCos, pitchSin);
							
						if (locationShip.getWorld().getBlockAt(locationShip).getRelative(0, 1, 0).getType().equals(Material.AIR)) {
								
							stand.teleport(locationShip);
							stand.setVelocity(locationShip.toVector().subtract(stand.getLocation().toVector()));
								
						} else {
							
							ship.location = ship.lastLocation;
							
//							ship.setYaw(ship.lastLocation.getYaw());
//							ship.setPitch(ship.lastLocation.getPitch());
//							
//							yawCos = ship.yawCos;
//							yawSin = ship.yawSin;
//							
//							pitchCos = ship.pitchCos;
//							pitchSin = ship.pitchSin;

							double health = shipBlocks.get(j).health;
							double speed = ship.speed;
								
							if (health < (ship.speed * 500)) {
									
								ship.speed = (float) (ship.speed - (health / 1000));

							} else {
									
								ship.speed = 0;
									
							}
								
							shipBlocks.get(j).ship.damage(shipBlocks.get(j), speed * 500, false);
								
						}
						
						stand.setHeadPose(eulerAngle);

					}
					
					shipBlocks.clear();
					
				}
				
				for (int i = 0; i < SQSmoothCraft.stoppedShipMap.size(); i ++) {
				
					if (!SQSmoothCraft.shipMap.containsValue(SQSmoothCraft.stoppedShipMap.get(i))) {
					
						Ship ship = (Ship) SQSmoothCraft.stoppedShipMap.get(i);
					
						shipBlocks.addAll(ship.getShipBlocks());

						double yaw = Math.toRadians(ship.yawDirection);
						double pitch = Math.toRadians(ship.pitchDirection);
						
						double yawCos = ship.getYawCos();
						double yawSin = ship.getYawSin();
						
						double pitchCos = ship.pitchCos;
						double pitchSin = ship.pitchSin;
					
						for (int j = 0; j < shipBlocks.size(); j ++) {
						
							ArmorStand stand = shipBlocks.get(j).getArmorStand();
						
							EulerAngle eulerAngle = null;

							eulerAngle = new EulerAngle(pitch, yaw, 0);
							
							ShipLocation shipLocation = shipBlocks.get(j).getShipLocation();
							
							stand.teleport(shipLocation.toLocation(ship, yawCos, yawSin, pitchCos, pitchSin));
							stand.setVelocity(new Vector(0, 0, 0));

							stand.setHeadPose(eulerAngle);
						
						}
					
						shipBlocks.clear();
						
					}
					
				}
				
			}			
			
		}, 0, 0);
		
	}

}
