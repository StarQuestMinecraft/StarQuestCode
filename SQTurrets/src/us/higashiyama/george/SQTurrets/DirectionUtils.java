
package us.higashiyama.george.SQTurrets;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.Sign;
import org.bukkit.entity.Player;

public class DirectionUtils {

	public static boolean playerOffsetLocation(Player p, Sign s) {

		int px = p.getLocation().getBlockX();

		int pz = p.getLocation().getBlockZ();
		if (px == ((int) s.getLocation().getX())) {

			if (((int) p.getLocation().getY()) == ((int) s.getLocation().getY()) || ((int) p.getLocation().getY() + 1) == ((int) s.getLocation().getY())) {
				if (pz == ((int) s.getLocation().getZ())) {
					return true;
				}
			}
		}
		return false;
	}

	public static double[] playerOffsetTeleport(Player p, Block b) {

		Location pLoc = p.getLocation();
		Location bLoc = b.getLocation();
		BlockFace rbf = null;
		BlockFace[] signFaces = { BlockFace.WEST, BlockFace.EAST, BlockFace.NORTH, BlockFace.SOUTH };
		for (BlockFace bf : signFaces) {
			if (b.getRelative(bf).getType() == Material.WALL_SIGN) {
				rbf = bf;
			}
		}
		double x = 0;
		double z = 0;
		switch (rbf) {
			case EAST:
				x += 1;
				break;
			case NORTH:
				z -= 1;
				break;
			case SOUTH:
				z += 1;
				break;
			case WEST:
				x -= 1;
				break;

		}
		double[] returnArray = { x, z };
		return returnArray;

	}

	public static Location locationFromSign(Player p, Block b) {

		Sign s = (Sign) b.getState();
		String[] unparsed = s.getLine(3).split(",");
		return new Location(p.getWorld(), s.getX() - Double.parseDouble(unparsed[0]), s.getY() - Double.parseDouble(unparsed[1]), s.getZ()
				- Double.parseDouble(unparsed[2]));

	}

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