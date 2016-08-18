package com.starquestminecraft.sqtechbase.util;

import org.bukkit.block.BlockFace;

public class DirectionUtils {

	public static BlockFace getRight(BlockFace blockFace) {
		
		switch(blockFace) {
		
			case NORTH: return BlockFace.EAST;
			case EAST: return BlockFace.SOUTH;
			case SOUTH: return BlockFace.WEST;
			case WEST: return BlockFace.NORTH;
			default: return BlockFace.NORTH;
		
		}
		
	}
	
	public static BlockFace getLeft(BlockFace blockFace) {
		
		switch(blockFace) {
		
			case NORTH: return BlockFace.WEST;
			case WEST: return BlockFace.SOUTH;
			case SOUTH: return BlockFace.EAST;
			case EAST: return BlockFace.NORTH;
			default: return BlockFace.NORTH;
	
		}
		
	}
	
	public static BlockFace getOpposite(BlockFace blockFace) {
		
		switch(blockFace) {
		
			case NORTH: return BlockFace.SOUTH;
			case WEST: return BlockFace.EAST;
			case SOUTH: return BlockFace.NORTH;
			case EAST: return BlockFace.WEST;
			default: return BlockFace.NORTH;
		
		}
		
	}
	
}
