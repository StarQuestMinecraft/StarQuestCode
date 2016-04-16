package com.martinjonsson01.sqsmoothcraft.missile;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.util.Vector;

public class MissileDetection {
	
	public static boolean detectLauncher(Block sign) {
		
		if (sign.getType() == Material.WALL_SIGN) {
			BlockFace direction = null;
			BlockFace launcherDirection = null;
			
			if (sign.getRelative(BlockFace.NORTH).getType() == Material.SPONGE)
				direction = BlockFace.NORTH;
			if (sign.getRelative(BlockFace.EAST).getType() == Material.SPONGE)
				direction = BlockFace.EAST;
			if (sign.getRelative(BlockFace.WEST).getType() == Material.SPONGE)
				direction = BlockFace.WEST;
			if (sign.getRelative(BlockFace.SOUTH).getType() == Material.SPONGE)
				direction = BlockFace.SOUTH;
				
			if (sign.getRelative(direction).getRelative(BlockFace.NORTH).getType() == Material.DISPENSER)
				launcherDirection = BlockFace.NORTH;
			if (sign.getRelative(direction).getRelative(BlockFace.EAST).getType() == Material.DISPENSER)
				launcherDirection = BlockFace.EAST;
			if (sign.getRelative(direction).getRelative(BlockFace.WEST).getType() == Material.DISPENSER)
				launcherDirection = BlockFace.WEST;
			if (sign.getRelative(direction).getRelative(BlockFace.SOUTH).getType() == Material.DISPENSER)
				launcherDirection = BlockFace.SOUTH;
				
			if (direction == null)
				return false;
			if (launcherDirection == null)
				return false;
				
			if (sign.getRelative(direction).getRelative(launcherDirection).getType() == Material.DISPENSER) {
				if (sign.getRelative(direction).getRelative(launcherDirection).getRelative(BlockFace.UP).getType() == Material.DISPENSER) {
					if (sign.getRelative(direction).getRelative(BlockFace.UP).getType() == Material.WOOL) {
						return true;
					}
				}
			}
		}
		return false;
		
	}
	
	public static Block getAmmoDispenser(Block sign) {
		
		BlockFace direction = null;
		BlockFace launcherDirection = null;
		Block ammoDispenser = null;
		
		if (sign.getRelative(BlockFace.NORTH).getType() == Material.SPONGE)
			direction = BlockFace.NORTH;
		if (sign.getRelative(BlockFace.EAST).getType() == Material.SPONGE)
			direction = BlockFace.EAST;
		if (sign.getRelative(BlockFace.WEST).getType() == Material.SPONGE)
			direction = BlockFace.WEST;
		if (sign.getRelative(BlockFace.SOUTH).getType() == Material.SPONGE)
			direction = BlockFace.SOUTH;
			
		if (sign.getRelative(direction).getRelative(BlockFace.NORTH).getType() == Material.DISPENSER)
			launcherDirection = BlockFace.NORTH;
		if (sign.getRelative(direction).getRelative(BlockFace.EAST).getType() == Material.DISPENSER)
			launcherDirection = BlockFace.EAST;
		if (sign.getRelative(direction).getRelative(BlockFace.WEST).getType() == Material.DISPENSER)
			launcherDirection = BlockFace.WEST;
		if (sign.getRelative(direction).getRelative(BlockFace.SOUTH).getType() == Material.DISPENSER)
			launcherDirection = BlockFace.SOUTH;
			
		if (sign.getRelative(direction).getRelative(launcherDirection).getType() == Material.DISPENSER) {
			ammoDispenser = sign.getRelative(direction).getRelative(launcherDirection);
		}
		return ammoDispenser;
		
	}
	
	public static Block inFrontOfDispenser(Block sign) {
		
		BlockFace direction = null;
		BlockFace launcherDirection = null;
		
		if (sign.getRelative(BlockFace.NORTH).getType() == Material.SPONGE)
			direction = BlockFace.NORTH;
		if (sign.getRelative(BlockFace.EAST).getType() == Material.SPONGE)
			direction = BlockFace.EAST;
		if (sign.getRelative(BlockFace.WEST).getType() == Material.SPONGE)
			direction = BlockFace.WEST;
		if (sign.getRelative(BlockFace.SOUTH).getType() == Material.SPONGE)
			direction = BlockFace.SOUTH;
			
		if (sign.getRelative(direction).getRelative(BlockFace.NORTH).getType() == Material.DISPENSER)
			launcherDirection = BlockFace.NORTH;
		if (sign.getRelative(direction).getRelative(BlockFace.EAST).getType() == Material.DISPENSER)
			launcherDirection = BlockFace.EAST;
		if (sign.getRelative(direction).getRelative(BlockFace.WEST).getType() == Material.DISPENSER)
			launcherDirection = BlockFace.WEST;
		if (sign.getRelative(direction).getRelative(BlockFace.SOUTH).getType() == Material.DISPENSER)
			launcherDirection = BlockFace.SOUTH;
			
		return sign.getRelative(direction).getRelative(launcherDirection).getRelative(launcherDirection).getRelative(launcherDirection);
		
	}
	
	public static Vector getDirectionVector(Block sign) {
		
		BlockFace direction = null;
		BlockFace launcherDirection = null;
		Vector returnVector = null;
		
		if (sign.getRelative(BlockFace.NORTH).getType() == Material.SPONGE)
			direction = BlockFace.NORTH;
		if (sign.getRelative(BlockFace.EAST).getType() == Material.SPONGE)
			direction = BlockFace.EAST;
		if (sign.getRelative(BlockFace.WEST).getType() == Material.SPONGE)
			direction = BlockFace.WEST;
		if (sign.getRelative(BlockFace.SOUTH).getType() == Material.SPONGE)
			direction = BlockFace.SOUTH;
			
		if (sign.getRelative(direction).getRelative(BlockFace.NORTH).getType() == Material.DISPENSER)
			launcherDirection = BlockFace.NORTH;
		if (sign.getRelative(direction).getRelative(BlockFace.EAST).getType() == Material.DISPENSER)
			launcherDirection = BlockFace.EAST;
		if (sign.getRelative(direction).getRelative(BlockFace.WEST).getType() == Material.DISPENSER)
			launcherDirection = BlockFace.WEST;
		if (sign.getRelative(direction).getRelative(BlockFace.SOUTH).getType() == Material.DISPENSER)
			launcherDirection = BlockFace.SOUTH;
			
		if (launcherDirection == BlockFace.NORTH) {
			return new Vector(0, 0, -1);
		}
		if (launcherDirection == BlockFace.EAST) {
			return new Vector(1, 0, 0);
		}
		if (launcherDirection == BlockFace.WEST) {
			return new Vector(-1, 0, 0);
		}
		if (launcherDirection == BlockFace.SOUTH) {
			return new Vector(0, 0, 1);
		}
		
		return returnVector;
		
	}
	
}
