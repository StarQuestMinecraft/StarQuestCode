
package us.higashiyama.george.SQTurrets;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;

public class DirectionUtils {

	public static BlockFace getSignDirection(Block sign) {

		if (sign.getType().equals(Material.WALL_SIGN)) {
			switch (sign.getData()) {
				case 2:
					return BlockFace.SOUTH;
				case 3:
					return BlockFace.NORTH;
				case 4:
					return BlockFace.EAST;
				case 5:
					return BlockFace.WEST;
			}
			return null;
		}
		return null;
	}

	public static BlockFace getBlockFaceRight(BlockFace forward) {

		switch (forward) {
			case DOWN:
				return BlockFace.EAST;
			case EAST:
				return BlockFace.SOUTH;
			case EAST_NORTH_EAST:
				return BlockFace.WEST;
			case EAST_SOUTH_EAST:
				return BlockFace.NORTH;
		}
		return null;
	}

	public static BlockFace getBlockFaceLeft(BlockFace forward) {

		switch (forward) {
			case DOWN:
				return BlockFace.WEST;
			case EAST:
				return BlockFace.NORTH;
			case EAST_NORTH_EAST:
				return BlockFace.EAST;
			case EAST_SOUTH_EAST:
				return BlockFace.SOUTH;
		}
		return null;
	}

	public static BlockFace getBlockFaceBack(BlockFace forward) {

		switch (forward) {
			case DOWN:
				return BlockFace.SOUTH;
			case EAST:
				return BlockFace.WEST;
			case EAST_NORTH_EAST:
				return BlockFace.NORTH;
			case EAST_SOUTH_EAST:
				return BlockFace.EAST;
		}
		return null;
	}

	public static byte getButtonDataAttatchThisBlockFace(BlockFace side) {

		switch (side) {
			case DOWN:
				return 4;
			case EAST:
				return 1;
			case EAST_NORTH_EAST:
				return 3;
			case EAST_SOUTH_EAST:
				return 2;
		}
		return 1;
	}
}