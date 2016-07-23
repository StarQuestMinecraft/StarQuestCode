package com.ginger_walnut.sqsmoothcraft.entites;

import java.lang.reflect.Field;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.craftbukkit.v1_10_R1.entity.CraftEntity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Vehicle;
import org.bukkit.event.vehicle.VehicleExitEvent;
import org.spigotmc.event.entity.EntityDismountEvent;

import com.ginger_walnut.sqsmoothcraft.SQSmoothCraft;
import com.ginger_walnut.sqsmoothcraft.events.ShipExitEvent;
import com.ginger_walnut.sqsmoothcraft.objects.Ship;
import net.minecraft.server.v1_10_R1.Entity;
import net.minecraft.server.v1_10_R1.EntityArmorStand;
import net.minecraft.server.v1_10_R1.EntityHuman;
import net.minecraft.server.v1_10_R1.World;

public class MainArmorStand extends EntityArmorStand {

	public MainArmorStand(World world) {
		
		super(world);

	}

	@Override
	public void g (float sideMot, float forMot) {
		
		try {
			
			if (this.passengers.size() > 0) {
				
				if (this.passengers.get(0) != null && this.passengers.get(0) instanceof EntityHuman) {
					
					Entity passenger = this.passengers.get(0);
					
					EntityHuman player = (EntityHuman) passenger;

					for (UUID uuid : SQSmoothCraft.shipMap.keySet()) {
						
						Ship ship = SQSmoothCraft.shipMap.get(uuid);
						
						if (ship.captain.getName().equals(player.getBukkitEntity().getName())) {
							
							ship.lastPlayerInput = ship.playerInput;
							
							ship.playerInput = player.bg;
							
							if (ship.fuel > 0) {

								if (ship.playerInput > 0) {
									
									if (ship.speed != 0) {
										
										if (ship.speed < 0) {
											
											ship.speed = ship.speed + (ship.acceleration * .2f);
											
											if (ship.speed > 0) {
												
												ship.speed = 0;
												
											}
											
										} else {
											
											ship.speed = ship.speed + (ship.acceleration * .2f);
											
										}
										
									} else {
										
										if (ship.lastPlayerInput == 0) {
											
											ship.speed = ship.speed + (ship.acceleration * .2f);
											
										}
										
									}

								} else if (ship.playerInput < 0) { 
									
									if (ship.speed != 0) {
										
										if (ship.speed > 0) {
											
											ship.speed = ship.speed - (ship.acceleration * .2f);
											
											if (ship.speed < 0) {
												
												ship.speed = 0;
												
											}
											
										} else {
											
											ship.speed = ship.speed - (ship.acceleration * .2f);
											
										}

									} else {
										
										if (ship.lastPlayerInput == 0) {
											
											ship.speed = ship.speed - (ship.acceleration * .2f);
											
										}
										
									}
									
								}
								
								if (ship.speed > ship.maxSpeed) {
									
									ship.speed = ship.maxSpeed;
									
								}
								
								if (ship.speed < -ship.maxSpeed) {
									
									ship.speed = -ship.maxSpeed;
									
								}
								
							}
							
						}
						
					}		
					
					this.aS = 0.02f;
					super.g(sideMot,  forMot);
					
				}
				
			}
			
		} catch (Exception e) {
			
			e.printStackTrace();
			
		}
		
	}
	
	@Override
	protected void p(Entity entity) {

		try  {
			
			if (entity.bB() == this) {
				throw new IllegalStateException("Use x.stopRiding(y), not y.removePassenger(x)");
			}
			
			if (entity instanceof EntityHuman) {
				
				EntityHuman player = (EntityHuman) entity;
				
				ShipExitEvent event = new ShipExitEvent(player);
				
				Bukkit.getPluginManager().callEvent(event);
				
				//CraftEntity craftn = (CraftEntity) entity.getBukkitEntity().getVehicle();
				//Entity n = craftn == null ? null : craftn.getHandle();
				if (event.isCancelled()) {
						//|| (n != orig)) {
					
					/*Field auField = Entity.class.getField("au");
					
					auField.setAccessible(true);
					auField.set(entity, this);
					auField.setAccessible(false);*/
					
					entity.a(this, true);
					
					return;
				}
				
				super.p(entity);
				
			}
			
		} catch (Exception e) {
			
			e.printStackTrace();
			
		}

	}

	
}
