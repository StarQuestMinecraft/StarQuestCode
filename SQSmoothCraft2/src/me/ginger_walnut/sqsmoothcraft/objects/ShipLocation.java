package me.ginger_walnut.sqsmoothcraft.objects;

import org.bukkit.Location;


public class ShipLocation {

	double x = 0;
	double y = 0;
	double z = 0;
	
	public ShipLocation (int gridX, int gridY, int gridZ) {
		
		x = gridX;
		y = gridY;
		z = gridZ;

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
	
	public double getX () {
		
		return x;
		
	}
	
	public double getY () {
		
		return y;
		
	}
	
	public double getZ () {
		
		return z;
		
	}
	
	public Location toLocation (Ship ship) {
		
		Location location = null;
			
		location = new Location(ship.pilot.getWorld(),
			((ship.pointingDirection.yawCos * ship.pointingDirection.pitchSin * y * -1) - (ship.pointingDirection.yawSin * ship.pointingDirection.pitchCos * z) + (ship.pointingDirection.yawCos * x)) * .625, 
			((y * ship.pointingDirection.pitchCos) - (z * ship.pointingDirection.pitchSin)) * .625, 
			((ship.pointingDirection.yawCos * ship.pointingDirection.pitchSin * y) + (ship.pointingDirection.yawCos * ship.pointingDirection.pitchCos * z) + (ship.pointingDirection.yawSin * x)) * .625).add(ship.location);
				
		location.setYaw(0);
		location.setPitch(0);
		
		return location;
		
	}

}
