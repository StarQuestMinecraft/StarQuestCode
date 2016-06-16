package com.starquestminecraft.sqtechbase.util;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.block.Block;

public class BlockUtils {

	public static List<Block> getAdjacentBlocks(Block block) {
		
		List<Block> blocks = new ArrayList<Block>();
		
		blocks.add(block.getRelative(1, 0, 0));
		blocks.add(block.getRelative(-1, 0, 0));
		blocks.add(block.getRelative(0, 1, 0));
		blocks.add(block.getRelative(0, -1, 0));
		blocks.add(block.getRelative(0, 0, 1));
		blocks.add(block.getRelative(0, 0, -1));
		
		return blocks;
	
	}
	
}
