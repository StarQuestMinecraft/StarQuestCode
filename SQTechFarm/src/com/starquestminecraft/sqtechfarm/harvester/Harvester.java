package com.starquestminecraft.sqtechfarm.harvester;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.Chest;
import org.bukkit.entity.Player;
import com.starquestminecraft.sqtechbase.gui.GUI;
import com.starquestminecraft.sqtechbase.objects.GUIBlock;
import com.starquestminecraft.sqtechbase.objects.MachineType;
import com.starquestminecraft.sqtechfarm.SQTechFarm;
import com.starquestminecraft.sqtechfarm.harvester.GUI.HarvesterGUI;
import com.starquestminecraft.sqtechfarm.util.DirectionUtils;

public class Harvester extends MachineType
{

	private SQTechFarm plugin;
	
	public String name = "Harvester";
	final private BlockFace[] around = {BlockFace.NORTH, BlockFace.EAST, BlockFace.SOUTH, BlockFace.WEST};
	private BlockFace forward;
	
	public Harvester(int maxEnergy, SQTechFarm plugin)
	{
		super(maxEnergy);
		this.plugin = plugin;
	}
	
	/**
	 * @return boolean indicating if the detection was successful
	 */
	public boolean detectStructure(GUIBlock guiBlock)
	{
		
		Block base = guiBlock.getLocation().getBlock();
		
		Block ironFarmHeadAnchorBlock;
		Block chest;
		Block dropper;
		Block farmHarvesterHead;
		List<Block> anchorSupports;
		List<Block> farmHeadSupports;
		
		chest = this.detectChest(base); // also sets forward direction
		if(chest == null)
			return false;
		
		dropper = this.detectDropper(base);
		anchorSupports = this.detectAnchorSupports(base);
		if(anchorSupports.size() == 0)
			return false;
		
		ironFarmHeadAnchorBlock = this.detectFarmHeadAnchorBlock(anchorSupports, base);
		if(ironFarmHeadAnchorBlock == null)
			return false;
		
		farmHeadSupports = this.detectFarmHeadSupports(ironFarmHeadAnchorBlock);
		farmHarvesterHead = this.detectFarmHarvesterHead(farmHeadSupports);
		
		if(chest == null || dropper == null || ironFarmHeadAnchorBlock == null || farmHarvesterHead == null ||
				anchorSupports.isEmpty() || farmHeadSupports.isEmpty())
			return false;
		else return true;
		
	}
	
	public GUI getGUI(Player player, int id)
	{
		return new HarvesterGUI(player, id);	
	}
	
	@Override
	public String getName()
	{
		return name;	
	}
	
	/**
	 * Checks each block around the guiBlock for a chest and returns it or null if no chest was found. <br>
	 * The chest location also defines the forward direction of the machine.
	 * @param guiBlock the GUI Block
	 * @return the chest Block
	 */
	private Block detectChest(Block guiBlock)
	{
		for(BlockFace face : around)
		{
			Block relativeBlock = guiBlock.getRelative(face);
			if(relativeBlock.getState() instanceof Chest)
			{
				this.forward = DirectionUtils.getBlockFaceBack(face);
				return relativeBlock;
			}
		}
		
		return null;
	}
	
	/**
	 * Checks the block to the right of the guiBlock for a dropper and returns it or null if no dropper was found. <br>
	 * Requires the forward direction to be set previously.
	 * @param guiBlock the GUI Block
	 * @return the chest Block
	 */
	private Block detectDropper(Block guiBlock)
	{
		if(this.forward == null)
			return null;
		
		Block relativeBlock = guiBlock.getRelative(DirectionUtils.getBlockFaceRight(forward));
		
		if(relativeBlock.getType() == Material.DROPPER)
			return relativeBlock;
		else
			return null;
	}
	
	/**
	 * Checks the blocks above the anchor supports and checks if its an anchor block, returns null if none were found.
	 * @param anchorSupports the anchor support Blocks
	 * @return the farm head anchor Block
	 */
	private Block detectFarmHeadAnchorBlock(List<Block> anchorSupports, Block guiBlock)
	{
		if(guiBlock.getRelative(BlockFace.UP).getType() == Material.IRON_BLOCK)
			return guiBlock.getRelative(BlockFace.UP);
		
		for(Block anchorSupport : anchorSupports)
		{
			if(anchorSupport.getRelative(BlockFace.UP).getType() == Material.IRON_BLOCK)
				return anchorSupport.getRelative(BlockFace.UP);
		}
		
		return null;
	}
	
	/**
	 * 
	 * @param anchorBlock
	 * @return
	 */
	private Block detectFarmHarvesterHead(List<Block> farmHeadSupports)
	{
		for(Block farmHeadSupport : farmHeadSupports)
		{
			if(farmHeadSupport.getRelative(BlockFace.DOWN).getType() == Material.THIN_GLASS)
			{
				return farmHeadSupport.getRelative(BlockFace.DOWN);
			}
		}
		
		return null;
	}
	
	/**
	 * Gets a List of block that are used as an anchor support or an empty List if none were found. <br>
	 * Requires the forward direction to be set.
	 * @param guiBlock the GUI Block
	 * @return the List of blocks detected
	 */
	private List<Block> detectAnchorSupports(Block guiBlock)
	{
		List<Block> result = new ArrayList<Block>();
		
		/*new BukkitRunnable()
		{
			int length = 1;
			@Override
			public void run()
			{
				Block relative = guiBlock.getRelative(forward, length);
				if(plugin.fenceTypes.contains(relative.getType())) // its a correct fence
				{
					length++;
					result.add(guiBlock.getRelative(forward, length));
				}
				else // no fence was found or the line stopped
				{
					cancel();
					return;
				}
			}
			
		}.runTaskTimer(plugin, 0, 0); */
		
		boolean running = true;
		int length = 1;
		
		while(running == true)
		{
			Block relative = guiBlock.getRelative(forward, length);
			if(plugin.fenceTypes.contains(relative.getType())) // its a correct fence
			{
				length++;
				result.add(guiBlock.getRelative(forward, length));
			}
			else // no fence was found or the line stopped
			{
				running = false;
				break;
			}
		}
		
		return result;
	}
	
	private List<Block> detectFarmHeadSupports(Block anchor)
	{
		final List<Block> result = new ArrayList<>();
		final BlockFace anchorSupportDirection = DirectionUtils.getBlockFaceLeft(forward);
		/*new BukkitRunnable()
		{
			int length = 1;
			@Override
			public void run()
			{
				Block relative = anchor.getRelative(anchorSupportDirection, length);
				if(relative.getType() == Material.IRON_FENCE) // its a correct block
				{
					length++;
					result.add(relative);
				}
				else // no fence was found or the line stopped
				{
					cancel();
					return;
				}
			}
			
		}.runTaskTimer(plugin, 0, 0);*/
		
		boolean running = true;
		int length = 1;
		
		while(running == true)
		{
			Block relative = anchor.getRelative(anchorSupportDirection, length);
			if(relative.getType() == Material.IRON_FENCE) // its a correct block
			{
				length++;
				result.add(relative);
			}
			else // no fence was found or the line stopped
			{
				running = false;
				break;
			}
		}
		
		return result;
	}
	
}
