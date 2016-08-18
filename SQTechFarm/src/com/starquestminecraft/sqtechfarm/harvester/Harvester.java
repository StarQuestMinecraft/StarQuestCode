package com.starquestminecraft.sqtechfarm.harvester;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.Chest;
import org.bukkit.entity.Player;

import com.starquestminecraft.sqtechbase.SQTechBase;
import com.starquestminecraft.sqtechbase.gui.GUI;
import com.starquestminecraft.sqtechbase.objects.GUIBlock;
import com.starquestminecraft.sqtechbase.objects.Machine;
import com.starquestminecraft.sqtechbase.objects.MachineType;
import com.starquestminecraft.sqtechbase.util.InventoryUtils;
import com.starquestminecraft.sqtechfarm.SQTechFarm;
import com.starquestminecraft.sqtechfarm.harvester.GUI.HarvesterGUI;
import com.starquestminecraft.sqtechfarm.util.DirectionUtils;

public class Harvester extends MachineType
{
	public static enum HarvesterSize
	{
		SMALL,
		MEDIUM,
		LARGE
	};
	
	private SQTechFarm plugin;
	
	public String name = "Harvester";
	final private static BlockFace[] around = {BlockFace.NORTH, BlockFace.EAST, BlockFace.SOUTH, BlockFace.WEST};
	private BlockFace forward;
	
	public Harvester(int maxEnergy, SQTechFarm plugin)
	{
		super(maxEnergy);
		this.plugin = plugin;
	}
	
	/**
	 * Return a default size of 10 if no size in the config could be found.
	 */
	public int getSizeForHarvesterSize(HarvesterSize size)
	{
		switch(size)
		{
			case SMALL: return plugin.getSettings().getHarvesterSizeSmall();
			case MEDIUM: return plugin.getSettings().getHarvesterSizeMedium();
			case LARGE: return plugin.getSettings().getHarvesterSizeLarge();
			default:
				return 10;
		}
	}
	
	/**
	 * @return boolean indicating if the detection was successful
	 */
	public boolean detectStructure(GUIBlock guiBlock)
	{
		Block base = guiBlock.getLocation().getBlock();
		
		Block ironFarmHeadAnchorBlock; // the iron block
		Block chest;
		Block dropper;
		Block farmHarvesterHead; // the stained glass pane / end rod
		List<Block> anchorSupports; // the wood fence
		List<Block> farmHeadSupports; // the stained glass panes
		
		this.forward = Harvester.getHarvesterForward(base);
		if(forward == null)
			return false;
		
		chest = this.detectChest(base);
		if(chest == null)
			return false;
		
		dropper = this.detectDropper(base, forward);
		anchorSupports = this.detectAnchorSupports(base, forward);
		if(anchorSupports.size() == 0)
			return false;
		
		ironFarmHeadAnchorBlock = this.detectFarmHeadAnchorBlock(anchorSupports, base);
		if(ironFarmHeadAnchorBlock == null)
			return false;
		
		farmHeadSupports = this.detectFarmHeadSupports(ironFarmHeadAnchorBlock, forward);
		farmHarvesterHead = this.detectFarmHarvesterHead(farmHeadSupports);
		
		if(chest == null || dropper == null || ironFarmHeadAnchorBlock == null || farmHarvesterHead == null ||
				anchorSupports.isEmpty() || farmHeadSupports.isEmpty())
			return false;
		else
		{
			return true;
		}
		
	}
	
	public GUI getGUI(Player player, int id)
	{
		return new HarvesterGUI(player, id, this.plugin);	
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
	public Block detectChest(Block guiBlock)
	{
		for(BlockFace face : around)
		{
			Block relativeBlock = guiBlock.getRelative(face);
			if(relativeBlock.getState() instanceof Chest)
			{
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
	public Block detectDropper(Block guiBlock, BlockFace forward)
	{
		if(forward == null)
			return null;
		
		Block relativeBlock = guiBlock.getRelative(DirectionUtils.getRight(forward));
		
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
	public Block detectFarmHeadAnchorBlock(List<Block> anchorSupports, Block guiBlock)
	{
		if(guiBlock.getRelative(BlockFace.UP, 2).getType() == Material.IRON_BLOCK &&
				plugin.fenceTypes.contains(guiBlock.getRelative(BlockFace.UP).getType()))
			return guiBlock.getRelative(BlockFace.UP, 2);
		
		for(Block anchorSupport : anchorSupports)
		{
			if(anchorSupport.getRelative(BlockFace.UP, 2).getType() == Material.IRON_BLOCK &&
					plugin.fenceTypes.contains(anchorSupport.getRelative(BlockFace.UP).getType()))
				return anchorSupport.getRelative(BlockFace.UP, 2);
		}
		
		return null;
	}
	
	/**
	 * 
	 * @param anchorBlock
	 * @return
	 */
	public Block detectFarmHarvesterHead(List<Block> farmHeadSupports)
	{
		Block result = null;
		for(Block farmHeadSupport : farmHeadSupports)
		{
			result = farmHeadSupport.getRelative(BlockFace.DOWN);
			if(result.getType() == Material.STAINED_GLASS_PANE || result.getType() == Material.END_ROD)
			{
				return result;
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
	public List<Block> detectAnchorSupports(Block guiBlock, BlockFace forward)
	{
		List<Block> result = new ArrayList<Block>();
		
		if(forward == null)
			return result;
		
		boolean running = true;
		int length = 1;
		
		while(running == true)
		{
			Block relative = guiBlock.getRelative(forward, length);
			if(plugin.fenceTypes.contains(relative.getType())) // its a correct fence
			{
				result.add(guiBlock.getRelative(forward, length));
				length++;
			}
			else // no fence was found or the line stopped
			{
				running = false;
				break;
			}
		}
		
		return result;
	}
	
	/**
	 * Gets the length of the fences on a Harvester.
	 * @param guiBlock the GUI Block
	 * @param forward the forward Direction of the machine
	 * @return an int of the length of the Harvester
	 */
	public int getHarvesterSize(Block guiBlock, BlockFace forward)
	{
		int averageSize = (this.detectAnchorSupports(guiBlock, forward).size() + this.detectFarmHeadSupports(guiBlock.getRelative(BlockFace.UP, 2), forward).size())/2;
		return averageSize;
	}
	
	/**
	 * Gets a List of Blocks that are part of the farm head support structure.
	 * @param anchor the iron anchor Block
	 * @param forward the forward direction
	 * @return a List of Blocks that are part of the farm head support structure
	 */
	public List<Block> detectFarmHeadSupports(Block anchor, BlockFace forward)
	{
		final List<Block> result = new ArrayList<>();
		final BlockFace anchorSupportDirection = DirectionUtils.getLeft(forward);
		
		boolean running = true;
		int length = 1;
		
		while(running == true)
		{
			Block relative = anchor.getRelative(anchorSupportDirection, length);
			if(relative.getType() == Material.STAINED_GLASS_PANE) // its a correct block
			{
				result.add(relative);
				length++;
			}
			else // no fence was found or the line stopped
			{
				running = false;
				break;
			}
		}
		
		return result;
	}
	
	/**
	 * Gets the location of the harvesting row on the farm supports or null if something went wrong.
	 * @param forward the forward direction of a harvester
	 * @return an Integer representing the location of the harvesting row location
	 */
	public Integer getHarvestingRowLocation(Block guiBlock, BlockFace forward)
	{
		List<Block> farmAnchorSupports = this.detectAnchorSupports(guiBlock, forward);
		if(farmAnchorSupports.size() == 0)
			return null;
		
		if(guiBlock.getRelative(BlockFace.UP, 2).getType() == Material.IRON_BLOCK)
		{
			if(this.detectFarmHeadSupports(guiBlock.getRelative(BlockFace.UP, 2), forward).size() > 0)
			{
				return 0;
			}
		}
		
		int count = 1;
		
		for(Block farmAnchorSupport : farmAnchorSupports)
		{
			if(farmAnchorSupport.getRelative(BlockFace.UP, 2).getType() == Material.IRON_BLOCK)
			{
				if(this.detectFarmHeadSupports(farmAnchorSupport.getRelative(BlockFace.UP, 2), forward).size() > 0)
				{
					return count;
				}
			}
			count++;
		}
		
		return null;
	}
	
	/**
	 * Gets the location of the harvesting head on the supports or null if something went wrong.
	 * @param forward the forward direction of a harvester
	 * @return an Integer representing the location of the harvesting head location
	 */
	public Integer getHarvesterHeadLocation(Block guiBlock, BlockFace forward, List<Block> farmHeadSupport)
	{
		if(farmHeadSupport.size() == 0)
			return null;
		
		Integer count = 1;
		
		for(Block support : farmHeadSupport)
		{
			if(support.getRelative(BlockFace.DOWN).getType() == Material.STAINED_GLASS_PANE 
					|| support.getRelative(BlockFace.DOWN).getType() == Material.END_ROD)
				return count;
			else
				count++;
		}
		
		return null;
	}
	
	@Override
	public void updateEnergy(Machine machine)
	{	
		for (Player player : SQTechBase.currentGui.keySet())
		{
			if (SQTechBase.currentGui.get(player).id == machine.getGUIBlock().id)
				if (player.getOpenInventory() != null)
					if (player.getOpenInventory().getTitle().equals(ChatColor.BLUE + "Harvester"))
						player.getOpenInventory().setItem(7, InventoryUtils.createSpecialItem(Material.REDSTONE, (short) 0, ChatColor.GREEN + "Energy: " + machine.getEnergy(), new String[] {ChatColor.RED + "" + ChatColor.MAGIC + "Contraband"}));	
		}
	}
	
	/**
	 * Gets the forward BlockFace direction of a Harvester and return it or null if the forward direction could not be found
	 * @param machine the Harvester
	 * @return the forward BlockFace direction of a Harvester
	 */
	public static BlockFace getHarvesterForward(Machine machine)
	{
		for(BlockFace face : around)
		{
			Block relativeBlock = machine.getGUIBlock().getLocation().getBlock().getRelative(face);
			if(relativeBlock.getState() instanceof Chest)
				return DirectionUtils.getBlockFaceBack(face);
		}
		
		return null;
	}
	
	/**
	 * Gets the forward BlockFace direction of a Harvester and return it or null if the forward direction could not be found
	 * @param base the GuiBlock of a Harvester
	 * @return the forward BlockFace direction of a Harvester
	 */
	public static BlockFace getHarvesterForward(Block base)
	{
		for(BlockFace face : around)
		{
			Block relativeBlock = base.getRelative(face);
			if(relativeBlock.getState() instanceof Chest)
				return DirectionUtils.getBlockFaceBack(face);
		}
		
		return null;
	}
	
}
