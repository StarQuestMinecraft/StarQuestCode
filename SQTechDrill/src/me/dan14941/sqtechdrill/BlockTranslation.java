package me.dan14941.sqtechdrill;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.Chest;
import org.bukkit.block.Dropper;
import org.bukkit.inventory.ItemStack;

public class BlockTranslation
{
	final List<Block> blocks;
	final BlockFace direction;
	ItemStack[] chestItems;
	ItemStack[] dropperItems;
	
	final List<BlockData> blocksData;
	
	public BlockTranslation(List<Block> blocks, final BlockFace direction)
	{
		this.blocks = blocks;
		this.direction = direction;
		
		blocksData = new ArrayList<BlockData>();
		
		for(Block b : blocks)
		{
			blocksData.add(new BlockData(b));
			if(b.getType() == Material.CHEST || b.getType() == Material.TRAPPED_CHEST)
			{
				this.chestItems = ((Chest) b.getState()).getInventory().getContents().clone();
				((Chest) b.getState()).getInventory().clear();
			}
			else if(b.getState() instanceof Dropper)
			{
				this.dropperItems = ((Dropper) b.getState()).getInventory().getContents().clone();
				((Dropper) b.getState()).getInventory().clear();
			}
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
			
			if(newloc.getBlock().getType() == Material.CHEST || newloc.getBlock().getType() == Material.TRAPPED_CHEST)
				for(ItemStack items : this.chestItems)
				{
					if(items != null)
						((Chest) newloc.getBlock().getState()).getInventory().addItem(items);
				}
			else if(newloc.getBlock().getType() == Material.DROPPER)
				for(ItemStack items : this.dropperItems)
				{
					if(items != null)
						((Dropper) newloc.getBlock().getState()).getInventory().addItem(items);
				}
		}
	}
}
