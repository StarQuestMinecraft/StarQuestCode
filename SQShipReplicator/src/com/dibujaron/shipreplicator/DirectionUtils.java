package com.dibujaron.shipreplicator;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;

public class DirectionUtils {
	@SuppressWarnings("deprecation")
	public static BlockFace getSignDirection(Block sign) {
		if (sign.getType().equals(Material.WALL_SIGN)){
			switch (sign.getData()) {
			case 2:
				return BlockFace.SOUTH;
			case 3:
				return BlockFace.NORTH;
			case 4:
				return BlockFace.EAST;
			case 5:
				return BlockFace.WEST;
			default:
				return null;
			}
		}
		return null;
	}
	public static BlockFace getBlockFaceRight(BlockFace forward){
		switch (forward){
		case NORTH:
			return BlockFace.EAST;
		case EAST:
			return BlockFace.SOUTH;
		case SOUTH:
			return BlockFace.WEST;
		case WEST:
			return BlockFace.NORTH;
		default:
			return null;
		}
	}
	public static BlockFace getBlockFaceLeft(BlockFace forward){
		switch (forward){
		case NORTH:
			return BlockFace.WEST;
		case EAST:
			return BlockFace.NORTH;
		case SOUTH:
			return BlockFace.EAST;
		case WEST:
			return BlockFace.SOUTH;
		default:
			return null;
		}
	}
	public static BlockFace getBlockFaceBack(BlockFace forward){
		switch (forward){
		case NORTH:
			return BlockFace.SOUTH;
		case EAST:
			return BlockFace.WEST;
		case SOUTH:
			return BlockFace.NORTH;
		case WEST:
			return BlockFace.EAST;
		default:
			return null;
		}
	}
	public static byte getButtonDataAttatchThisBlockFace(BlockFace side){
		switch(side){
		case NORTH:
			return 4;
		case EAST:
			return 1;
		case SOUTH:
			return 3;
		case WEST:
			return 2;
		default:
			return 1;
		}
	}
}

