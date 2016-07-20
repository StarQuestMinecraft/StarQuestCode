package com.starquestminecraft.sqtechfarm.util;

import org.bukkit.block.BlockFace;

public class DirectionUtils extends com.starquestminecraft.sqtechbase.util.DirectionUtils
{

	/**
	 * Gets the opposite BlockFace to a direction or null if the BlockFace is
	 * invalid.
	 * 
	 * @param forward
	 *            the forward direction
	 * @return the opposite BlockFace
	 */
	public static BlockFace getBlockFaceBack(BlockFace forward)
	{
		switch (forward)
		{
			case NORTH:
				return BlockFace.SOUTH;
			case EAST:
				return BlockFace.WEST;
			case SOUTH:
				return BlockFace.NORTH;
			case WEST:
				return BlockFace.EAST;
			case UP:
				return BlockFace.DOWN;
			case DOWN:
				return BlockFace.UP;
			default:
				return null;
		}
	}

}
