package com.ginger_walnut.sqsmoothcraft.ship;

import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.EntityType;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.EulerAngle;

import com.ginger_walnut.sqsmoothcraft.SQSmoothCraft;

public class ShipBlock {

	ArmorStand stand =  null;
	ShipBlock mainBlock = null;
	ShipLocation loc = null;
	World world = null;
	Ship ship = null;
	
	public boolean invincible = false;
	
	public double health = 1;
	public double weight = 1;
	
	public ShipBlock (Location location, ShipLocation shipLocation, ItemStack block) {
		
		loc = shipLocation;

		mainBlock = this;
		
		if (location != null) {
			
			world = location.getWorld();
			
			location.setYaw(0);
			location.setPitch(0);
			
			stand = (ArmorStand) location.getWorld().spawnEntity(location, EntityType.ARMOR_STAND);
			
			stand.setHelmet(block);
			stand.setVisible(false);
			stand.setBasePlate(false);

		} else {
			
			stand = (ArmorStand) shipLocation.getWorld().spawnEntity((shipLocation.toLocation(SQSmoothCraft.nextShipLocation, SQSmoothCraft.nextShipYawCos, SQSmoothCraft.nextShipYawSin, SQSmoothCraft.nextShipPitchCos, SQSmoothCraft.nextShipPitchSin)), EntityType.ARMOR_STAND);
			
			stand.setHelmet(block);
			stand.setVisible(false);
			stand.setBasePlate(false);
			
			stand.setHeadPose(mainBlock.getArmorStand().getHeadPose());
			
		}
		
		stand.setRightLegPose(new EulerAngle(3.1415, 0, 0));
		stand.setLeftLegPose(new EulerAngle(3.1415, 0, 0));
		
		for (int i = 0; i < SQSmoothCraft.shipBlockTypes.size(); i ++) {
		
			if (stand.getHelmet().getType().equals(SQSmoothCraft.shipBlockTypes.get(i))) {
				
				health = SQSmoothCraft.shipBlockHealths.get(i);
				weight = SQSmoothCraft.shipBlockWeights.get(i);
				
			}

		}
	
	}
	
	public ShipBlock (ShipLocation shipLocation, ItemStack block, ShipBlock mainShipBlock) {
		
		loc = shipLocation;
		
		mainBlock = mainShipBlock;
		
		stand = (ArmorStand) shipLocation.getWorld().spawnEntity((shipLocation.toLocation(SQSmoothCraft.nextShipLocation, SQSmoothCraft.nextShipYawCos, SQSmoothCraft.nextShipYawSin, SQSmoothCraft.nextShipPitchCos, SQSmoothCraft.nextShipPitchSin)), EntityType.ARMOR_STAND);
			
		stand.setHelmet(block);
		stand.setVisible(false);
		stand.setBasePlate(false);
			
		stand.setHeadPose(mainBlock.getArmorStand().getHeadPose());
		stand.setRightLegPose(new EulerAngle(3.1415, 0, 0));
		stand.setLeftLegPose(new EulerAngle(3.1415, 0, 0));
			
		for (int i = 0; i < SQSmoothCraft.shipBlockTypes.size(); i ++) {
			
			if (stand.getHelmet().getType().equals(SQSmoothCraft.shipBlockTypes.get(i))) {
				
				health = SQSmoothCraft.shipBlockHealths.get(i);
				weight = SQSmoothCraft.shipBlockWeights.get(i);
				
			}

		}
			
	}
	
	public ArmorStand getArmorStand () {
		
		return stand;
		
	}
	
	public void setArmorStand (ArmorStand newArmorStand) {
		
		stand = newArmorStand;
		
	}
	
	public void setShipLocation (ShipLocation location) {
		
		loc = location;
		
	}
	
	public ShipLocation getShipLocation () {
		
		return loc;
		
	}
	
	public void setLocation (Location location) {
		
		stand.teleport(location);
		
	}
	
	public Location getLocation () {
		
		return stand.getLocation();
		
	}
	
	public ShipBlock getMainBlock () {
		
		return mainBlock;
		
	}
	
	public World getWorld () {
		
		return world;
	
	}
	
	public void setWorld (World world) {
		
		Location location = stand.getLocation();
		
		location.setWorld(world);
		
		stand.teleport(location);
		
	}
	
	public Ship getShip () {
		
		return ship;
		
	}
	
	public void setShip(Ship newShip) {
		
		ship = newShip;
		
	}
	
}
