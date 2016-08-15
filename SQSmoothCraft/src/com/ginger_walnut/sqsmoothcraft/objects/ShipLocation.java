package com.ginger_walnut.sqsmoothcraft.objects;

import org.bukkit.Location;
import org.bukkit.World;


public class ShipLocation {

	public double x = 0;
	public double y = 0;
	public double z = 0;
	
	public ShipBlock mainShipBlock = null;
	
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
	
	public double getX () {
		
		return x;
		
	}
	
	public double getY () {
		
		return y;
		
	}
	
	public double getZ () {
		
		return z;
		
	}
	
	public Location toLocation (Location shipLocation, double yawCos, double yawSin, double pitchCos, double pitchSin) {
		
		Location location = null;
			
		//location = new Location(ship.getLocation().getWorld(), ((x * yawCos) - (z * yawSin)) * .625, (y * .625), ((z * yawCos) + (x * yawSin)) * .625).add(ship.getLocation());
		//location = new Location(ship.getLocation().getWorld(),
				//(((x * yawCos) - (z * yawSin)) - (yawSin * ((y * pitchSin) - (z * Math.abs(pitchSin))))) * .625, 
				//((y * pitchCos) - (z * pitchSin)) * .625, 
				//(((z * yawCos) + (x * yawSin)) + (yawCos * ((y * pitchSin) - (z * Math.abs(pitchSin))))) * .625).add(ship.getLocation());
		location = new Location(shipLocation.getWorld(),
			((yawSin * pitchSin * y * -1) - (yawSin * pitchCos * z) + (yawCos * x)) * .625, 
			((y * pitchCos) - (z * pitchSin)) * .625, 
			((yawCos * pitchSin * y) + (yawCos * pitchCos * z) + (yawSin * x)) * .625).add(shipLocation);
				
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
