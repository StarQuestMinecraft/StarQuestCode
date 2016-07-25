package me.ginger_walnut.sqsmoothcraft.tasks;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.craftbukkit.v1_10_R1.CraftWorld;
import org.bukkit.craftbukkit.v1_10_R1.entity.CraftPlayer;
import org.bukkit.entity.Player;

import me.ginger_walnut.sqsmoothcraft.SQSmoothCraft;
import me.ginger_walnut.sqsmoothcraft.objects.Ship;
import me.ginger_walnut.sqsmoothcraft.objects.ShipBlock;
import net.minecraft.server.v1_10_R1.PacketPlayOutEntity.PacketPlayOutRelEntityMove;
import net.minecraft.server.v1_10_R1.EntityArmorStand;
import net.minecraft.server.v1_10_R1.PacketPlayOutEntityDestroy;
import net.minecraft.server.v1_10_R1.PacketPlayOutSpawnEntityLiving;

public class ShipMovement extends Thread{

	public void run() {
		
		Bukkit.getScheduler().scheduleSyncRepeatingTask(SQSmoothCraft.getInstance(), new Runnable() {
			
			public void run() {
				
				List<Ship> ships = new ArrayList<Ship>();
				ships.addAll(SQSmoothCraft.activeShips);
				
				for (Ship ship : ships) {

					if (ship.mainBlock.stand.getBukkitEntity().getPassenger() != ship.pilot) {
						
						SQSmoothCraft.activeShips.remove(ship);
						
					} else {
						
						if (ship.playerInput > 0) {
							
							if (ship.speed != 0) {
								
								if (ship.speed < 0) {
									
									ship.speed = ship.speed + .01f;
									
									if (ship.speed > 0) {
										
										ship.speed = 0;
										
									}
									
								} else {
									
									ship.speed = ship.speed + .01f;
									
								}
								
							} else {
								
								if (ship.lastPlayerInput == 0) {
									
									ship.speed = ship.speed + .01f;
									
								}
								
							}

						} else if (ship.playerInput < 0) { 
							
							if (ship.speed != 0) {
								
								if (ship.speed > 0) {
									
									ship.speed = ship.speed - .01f;
									
									if (ship.speed < 0) {
										
										ship.speed = 0;
										
									}
									
								} else {
									
									ship.speed = ship.speed - .01f;
									
								}

							} else {
								
								if (ship.lastPlayerInput == 0) {
									
									ship.speed = ship.speed - .01f;
									
								}
								
							}
							
						}
						
						if (ship.speed > 1) {
							
							ship.speed = 1;
							
						}
						
						if (ship.speed < -1) {
							
							ship.speed = -1;
							
						}
						
						ship.location = ship.pilot.getLocation().getDirection().multiply(ship.speed).toLocation(ship.pilot.getWorld()).add(ship.location);

						ship.mainBlock.stand.setPosition(ship.location.getX(), ship.location.getY(), ship.location.getZ());
						
						for (Player player : Bukkit.getOnlinePlayers()) {
							
							int[] removal = new int[ship.shipBlocks.size()];
							
							for (int i = 0; i < ship.shipBlocks.size(); i ++) {
								
								removal[i] = ship.shipBlocks.get(i).stand.getId();
								
							}
							
							((CraftPlayer)player).getHandle().playerConnection.sendPacket(new PacketPlayOutEntityDestroy(removal));
							
						}
						
						for (ShipBlock shipBlock : ship.shipBlocks) {
							
							/*Location currentLocation = shipBlock.stand.getBukkitEntity().getLocation();
							Location newLocation = ship.pilot.getLocation().getDirection().multiply(ship.speed).toLocation(ship.pilot.getWorld()).add(shipBlock.stand.getBukkitEntity().getLocation());
							
							PacketPlayOutRelEntityMove fakeArmorStandMove = new PacketPlayOutRelEntityMove(shipBlock.stand.getId(), (short) ((newLocation.getX() * 32 - currentLocation.getX() * 32) * 128), (short) ((newLocation.getY() * 32 - currentLocation.getY() * 32) * 128), (short) ((newLocation.getZ() * 32 - currentLocation.getZ() * 32) * 128), false);*/
							
							
							Location location = shipBlock.shipLocation.toLocation(ship);
							
							shipBlock.stand.setLocation(location.getX(), location.getY(), location.getZ(), 0.0f, 0.0f);
							
							PacketPlayOutSpawnEntityLiving fakeArmorStandSpawn = new PacketPlayOutSpawnEntityLiving(shipBlock.stand);
							
							for (Player player : Bukkit.getOnlinePlayers()) {
								
								((CraftPlayer)player).getHandle().playerConnection.sendPacket(fakeArmorStandSpawn);
								
							}

						}
						
					}

				}
				
			}
			
		}, 0, 0);
		
	}
	
}
