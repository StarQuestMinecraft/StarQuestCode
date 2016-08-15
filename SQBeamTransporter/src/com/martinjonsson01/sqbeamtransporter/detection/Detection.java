package com.martinjonsson01.sqbeamtransporter.detection;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;

public class Detection {
	
	public static boolean detectTransporter(Block stainedGlass) {
		
		if (stainedGlass.getType() == Material.STAINED_GLASS) {
			
			if (stainedGlass.getRelative(BlockFace.NORTH).getRelative(BlockFace.NORTH).getType() == Material.WALL_SIGN || 
				stainedGlass.getRelative(BlockFace.EAST).getRelative(BlockFace.EAST).getType() == Material.WALL_SIGN|| 
				stainedGlass.getRelative(BlockFace.WEST).getRelative(BlockFace.WEST).getType() == Material.WALL_SIGN|| 
				stainedGlass.getRelative(BlockFace.SOUTH).getRelative(BlockFace.SOUTH).getType() == Material.WALL_SIGN) {

				if (stainedGlass.getRelative(BlockFace.NORTH).getType() == Material.STEP ||
						stainedGlass.getRelative(BlockFace.NORTH).getType() == Material.DOUBLE_STEP) {

					if (stainedGlass.getRelative(BlockFace.EAST).getType() == Material.STEP ||
							stainedGlass.getRelative(BlockFace.EAST).getType() == Material.DOUBLE_STEP) {

						if (stainedGlass.getRelative(BlockFace.WEST).getType() == Material.STEP ||
								stainedGlass.getRelative(BlockFace.WEST).getType() == Material.DOUBLE_STEP) {

							if (stainedGlass.getRelative(BlockFace.SOUTH).getType() == Material.STEP ||
									stainedGlass.getRelative(BlockFace.SOUTH).getType() == Material.DOUBLE_STEP) {

								Block sign = stainedGlass.getRelative(BlockFace.DOWN);
								
								if (sign.getRelative(BlockFace.NORTH).getType() == Material.LAPIS_BLOCK) {

									if (sign.getRelative(BlockFace.EAST).getType() == Material.LAPIS_BLOCK) {

										if (sign.getRelative(BlockFace.WEST).getType() == Material.LAPIS_BLOCK) {

											if (sign.getRelative(BlockFace.SOUTH).getType() == Material.LAPIS_BLOCK) {

												return true;
											}
										}
									}
								}
							}
						}
					}
				}
			}
		}
		
		return false;
	}
	
	public static BlockFace getSignDirection(Block sign) {
		if (detectTransporter(sign.getRelative(BlockFace.NORTH).getRelative(BlockFace.NORTH))) return BlockFace.NORTH;
		if (detectTransporter(sign.getRelative(BlockFace.EAST).getRelative(BlockFace.EAST))) return BlockFace.EAST;
		if (detectTransporter(sign.getRelative(BlockFace.WEST).getRelative(BlockFace.WEST))) return BlockFace.WEST;
		if (detectTransporter(sign.getRelative(BlockFace.SOUTH).getRelative(BlockFace.SOUTH))) return BlockFace.SOUTH;
		
		return null;
		
	}
	
	public static BlockFace getSignDirectionFromStainedGlass(Block stainedGlass) {
		if (stainedGlass.getRelative(BlockFace.NORTH).getRelative(BlockFace.NORTH).getType() == Material.WALL_SIGN) return BlockFace.NORTH;
		if (stainedGlass.getRelative(BlockFace.EAST).getRelative(BlockFace.EAST).getType() == Material.WALL_SIGN) return BlockFace.EAST;
		if (stainedGlass.getRelative(BlockFace.WEST).getRelative(BlockFace.WEST).getType() == Material.WALL_SIGN) return BlockFace.WEST;
		if (stainedGlass.getRelative(BlockFace.SOUTH).getRelative(BlockFace.SOUTH).getType() == Material.WALL_SIGN) return BlockFace.SOUTH;
		
		return null;
	}
	
	
}
