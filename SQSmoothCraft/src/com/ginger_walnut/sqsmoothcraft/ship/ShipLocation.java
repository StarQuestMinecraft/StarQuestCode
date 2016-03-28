package com.ginger_walnut.sqsmoothcraft.ship;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;


public class ShipLocation {

	int x = 0;
	int y = 0;
	int z = 0;
	
	ShipBlock mainShipBlock = null;
	
	public ShipLocation (int gridX, int gridY, int gridZ, ShipBlock mainBlock) {
		
		x = gridX;
		y = gridY;
		z = gridZ;
		
		mainShipBlock = mainBlock;

	}
	
	public void setX (int newX) {
		
		x = newX;
		
	}
	
	public void setY (int newY) {
		
		y = newY;
		
	}
	
	public void setZ (int newZ) {
		
		z = newZ;
		
	}
	
	public void setLocation (int newX, int newY, int newZ) {
		
		x = newX;
		y = newY;
		z = newZ;
		
	}
	
	public int getX () {
		
		return x;
		
	}
	
	public int getY () {
		
		return y;
		
	}
	
	public int getZ () {
		
		return z;
		
	}
	
	public Location toLocation () {
		
		Location location = null;
		float yaw = 0.0f;
		
		if (mainShipBlock != null) {
			
			if (mainShipBlock.getArmorStand() != null) {
				
				if (mainShipBlock.getArmorStand().getPassenger() != null) {
					
					yaw = mainShipBlock.getArmorStand().getPassenger().getLocation().getYaw();
					
				} else {
					
					yaw = 0.0f;
					
				}

				
			} else {
				
				yaw = 0.0f;
				
			}
			
		} else {
			
			yaw = 0.0f;
			
		}
			
		if (mainShipBlock != null) {
				
			double yawRad = Math.toRadians(yaw);
			double yawCos = Math.cos(yawRad);
			double yawSin = Math.sin(yawRad);
			
//			location = new Location(mainShipBlock.getArmorStand().getWorld(), (0 + (x * Math.cos(Math.toRadians(yaw)))) * .625, (y * .625), (0 + (x * Math.sin(Math.toRadians(yaw)))) * .625).add(mainShipBlock.getLocation());
			
			location = new Location(mainShipBlock.getArmorStand().getWorld(), ((x * yawCos) - (z * yawSin)) * .625, (y * .625), ((z * yawCos) + (x * yawSin)) * .625).add(mainShipBlock.getLocation());
			
//			Location blockLocation = mainShipBlock.getLocation();
//			location = blockLocation.toVector().add(player.getLocation().getDirection().multiply(4)).toLocation(player.getWorld(), player.getLocation().getYaw(), player.getLocation().getPitch());
			

			
		} else {
			
			location = new Location(Bukkit.getWorlds().get(0), (((int) (x + (z) * Math.cos(yaw))) * .625), (y * .625), (((int) (z + (z * Math.sin(yaw)))) * .625)).add(new Location(Bukkit.getWorlds().get(0),0,0,0));
			
		}
			
		location.setYaw(0);
		
		return location;
		
	}
	
	public Location toLocation (Ship ship, double yawCos, double yawSin, double pitchCos, double pitchSin) {
		
		Location location = null;
			
		//location = new Location(ship.getLocation().getWorld(), ((x * yawCos) - (z * yawSin)) * .625, (y * .625), ((z * yawCos) + (x * yawSin)) * .625).add(ship.getLocation());
		//location = new Location(ship.getLocation().getWorld(),
				//(((x * yawCos) - (z * yawSin)) - (yawSin * ((y * pitchSin) - (z * Math.abs(pitchSin))))) * .625, 
				//((y * pitchCos) - (z * pitchSin)) * .625, 
				//(((z * yawCos) + (x * yawSin)) + (yawCos * ((y * pitchSin) - (z * Math.abs(pitchSin))))) * .625).add(ship.getLocation());
		location = new Location(ship.getLocation().getWorld(),
			((yawSin * pitchSin * y * -1) - (yawSin * pitchCos * z) + (yawCos * x)) * .625, 
			((y * pitchCos) - (z * pitchSin)) * .625, 
			((yawCos * pitchSin * y) + (yawCos * pitchCos * z) + (yawSin * x)) * .625).add(ship.getLocation());
				
		location.setYaw(0);
		location.setPitch(0);
		
		return location;
		
	}
	
	public void setMainBlock (ShipBlock newMainBlock) {
		
		mainShipBlock = newMainBlock;
		
	}
	
	public World getWorld () {
		
		return mainShipBlock.getWorld();
	
	}

}
