package com.ginger_walnut.sqsmoothcraft.objects;

import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.craftbukkit.v1_10_R1.CraftWorld;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.EntityType;
import org.bukkit.event.entity.CreatureSpawnEvent.SpawnReason;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.EulerAngle;

import com.ginger_walnut.sqsmoothcraft.SQSmoothCraft;
import com.ginger_walnut.sqsmoothcraft.entites.MainArmorStand;
import com.ginger_walnut.sqsmoothcraft.enums.BlockType;

public class ShipBlock {

	public ArmorStand stand =  null;
	public ShipBlock mainBlock = null;
	public ShipLocation loc = null;
	public World world = null;
	public Ship ship = null;
	
	public boolean invincible = false;
	
	public double health = 1;
	public double weight = 1;
	
	public BlockType type;
	
	public Object data;
	
	public ShipBlock (Location location, ShipLocation shipLocation, ItemStack block, BlockType firstType) {
		
		loc = shipLocation;

		mainBlock = this;
		
		if (firstType.equals(BlockType.SLAB)) {
			
			data = block.getDurability();
			
			block.setDurability((short)0);
			
			if ((short) data >= 8) {
				
				loc.y = loc.y + .5;
				
			}
			
		}
		
		if (firstType.equals(BlockType.DIRECTIONAL)) {
			
			data = block.getDurability();
			
			block.setDurability((short)0);
			
		}
		
		type = firstType;
		
		if (location != null) {
			
			world = location.getWorld();
			
			location.setYaw(0);
			location.setPitch(0);
			
			net.minecraft.server.v1_10_R1.World mcWorld = ((CraftWorld)location.getWorld()).getHandle();
			MainArmorStand stand = new MainArmorStand(mcWorld);
			stand.setLocation(location.getX(), location.getY(), location.getZ(), 0.0f, 0.0f);
					
			mcWorld.addEntity(stand, SpawnReason.CUSTOM);
			
			this.stand = (ArmorStand) stand.getBukkitEntity();
			
			this.stand.setHelmet(block);
			this.stand.setVisible(false);
			this.stand.setBasePlate(false);

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
	
	public ShipBlock (ShipLocation shipLocation, ItemStack block, ShipBlock mainShipBlock, BlockType firstType) {
		
		loc = shipLocation;
		
		mainBlock = mainShipBlock;
		
		if (firstType.equals(BlockType.SLAB)) {
			
			data = block.getDurability();
			
			block.setDurability((short)0);
			
			if ((short) data >= 8) {
				
				loc.y = loc.y + .5;
				
			}
			
		}
		
		if (firstType.equals(BlockType.DIRECTIONAL)) {
			
			data = block.getDurability();
			
			block.setDurability((short)0);
			
		}
		
		type = firstType;
		
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
		
		if (stand != null) {
		
			return stand.getLocation();
		
		} else {
			
			return loc.toLocation(ship.getLocation(), ship.pointingDirection.yawCos, ship.pointingDirection.yawSin, ship.pointingDirection.pitchCos, ship.pointingDirection.pitchSin);
			
		}
		
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
