package com.starquestminecraft.sqtechfarm.Object;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;

public class BlockTranslation
{
	final List<Block> blocks;
	final BlockFace direction;
	
	final List<BlockData> blocksData;
	
	public BlockTranslation(List<Block> blocks, final BlockFace direction)
	{
		this.blocks = blocks;
		this.direction = direction;
		
		blocksData = new ArrayList<BlockData>();
		
		for(Block b : blocks)
		{
			blocksData.add(new BlockData(b));
		}
	}
	
	public void cut()
	{
		this.deleteBlocks();
		this.pasteBlocks();
	}
	
	private void deleteBlocks()
	{
		for(final Block b : blocks)
			b.setType(Material.AIR);
	}
	
	@SuppressWarnings("deprecation")
	private void pasteBlocks()
	{
		Location newloc;
		for(BlockData bd : blocksData)
		{
			newloc = bd.getLocation().getBlock().getRelative(direction).getLocation();
			newloc.getBlock().setType(bd.getMaterial());
			newloc.getBlock().setData(bd.getData());
		}
	}
	
	/**
	 * Checks if there are any blocks in the way of a blocks movement path.
	 * @return a boolean indicating if a block is in the pay of movement. true meaning a block is in the way.
	 */
	public boolean detectObstruction()
	{
		for(BlockData blockData : blocksData)
		{
			if(!(blockData.getLocation().getBlock().getRelative(direction).isEmpty()))
					return true;
		}
		
		return false;
	}
}
