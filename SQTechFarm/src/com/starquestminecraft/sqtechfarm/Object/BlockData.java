package com.starquestminecraft.sqtechfarm.Object;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;

public class BlockData
{
	Material blockMat;
	byte blockData;
	Location blockLoc;
	
	@SuppressWarnings("deprecation")
	public BlockData(Block block)
	{
		this.blockMat = block.getType();
		this.blockData = block.getData();
		this.blockLoc = new Location(block.getWorld(), block.getX(), block.getY(), block.getZ());
	}
	
	public Material getMaterial()
	{
		return blockMat;
	}
	
	public byte getData()
	{
		return blockData;
	}
	
	public Location getLocation()
	{
		return blockLoc;
	}
	
	public Block getBlock()
	{
		return blockLoc.getBlock();
	}
	
	public void changeLoc(Location newLoc)
	{
		this.blockLoc = newLoc;
	}
}
