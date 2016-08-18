package com.starquestminecraft.sqtechfarm.harvester.Object;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.scheduler.BukkitRunnable;

import com.starquestminecraft.sqtechbase.objects.Machine;
import com.starquestminecraft.sqtechfarm.SQTechFarm;
import com.starquestminecraft.sqtechfarm.Object.BlockTranslation;
import com.starquestminecraft.sqtechfarm.Object.Farmables;
import com.starquestminecraft.sqtechfarm.harvester.Harvester;
import com.starquestminecraft.sqtechfarm.util.DirectionUtils;

public class HarvesterManagerTask extends BukkitRunnable
{
	private final SQTechFarm plugin;
	private final List<Machine> activeHarvesters;
	final List<Machine> queueInactive;
	private final Harvester harvester;
	
	public HarvesterManagerTask(SQTechFarm main)
	{
		plugin = main;
		harvester = plugin.getHarvesterType();
		activeHarvesters = new ArrayList<Machine>();
		queueInactive = new ArrayList<Machine>();
	}
	
	@Override
	public void run()
	{
		for(Machine machine : queueInactive)
		{
			plugin.stopHarvester(machine);
		}
		queueInactive.clear();
		
		for(Machine machine : activeHarvesters)
		{
			if(machine.getMachineType() == this.plugin.getHarvesterType())
			{
				if(!plugin.isActive(machine))
					continue;
				
				ActiveHarvester activeHarvester = new ActiveHarvester(plugin, machine, this);
				if(activeHarvester.update() == false)
				{
					activeHarvester.stop();
					continue;
				}
				
				Integer rowPosition = harvester.getHarvestingRowLocation(activeHarvester.guiBlock, activeHarvester.forward);
				Integer headPosition = harvester.getHarvesterHeadLocation(activeHarvester.guiBlock, activeHarvester.forward, activeHarvester.farmHeadSupport);
				
				Boolean headAtEnd = false;

				if ((rowPosition % 2 != 0 && headPosition == 1)
						|| (rowPosition % 2 == 0 && headPosition == activeHarvester.farmHeadSupport.size())) // detect if head is at end
					headAtEnd = true;
				
				if(!activeHarvester.toPlant())
				{
					if (!plugin.isActive(machine))
					{
						this.queueInactive.add(machine);
						continue;
					}
					activeHarvester.farm();
					if (!plugin.isActive(machine))
					{
						this.queueInactive.add(machine);
						continue;
					}
				}
				
				if (rowPosition == activeHarvester.anchorSupports.size() && headAtEnd) // row is at end, move back to start then stop machine
				{
					if (!plugin.isActive(machine))
					{
						activeHarvester.stop();
						continue;
					}
					activeHarvester.moveRowToStart(headPosition);
					this.queueInactive.add(machine);
					plugin.setInactive(machine);
					continue;
				}
				
				if(!machine.detectStructure() || !plugin.isActive(machine))
				{
					activeHarvester.stop();
					continue;
				}
				else if (headAtEnd == true) // when the end rod reaches the end of the iron supports
				{	
					if(moveRow(activeHarvester.forward, activeHarvester.anchorSupports, activeHarvester.guiBlock, activeHarvester.forward) == false)
					{
						activeHarvester.stop();
						continue;
					}
					
					continue;
				}
				else
				{
					BlockFace headForwards;

					if (rowPosition % 2 == 0) // if its even
						headForwards = DirectionUtils.getLeft(activeHarvester.forward);
					else
						headForwards = DirectionUtils.getRight(activeHarvester.forward);
					
					BlockTranslation headMove = new BlockTranslation(Arrays.asList(activeHarvester.harvesterHead), headForwards);
					if(headMove.detectObstruction())
					{
						activeHarvester.stop();
						continue;
					}
					else
						headMove.cut();
				}
			}
		}
	}
	
	public void addHarvester(Machine harvester)
	{
		activeHarvesters.add(harvester);
	}
	
	/**
	 * This moves the row of a harvester in the selected direction, this does not check if it will go off the end.
	 * @param direction the BlockFace direction of the row movement
	 * @return a boolean indicating if the operation was performed successfully
	 */
	public boolean moveRow(BlockFace direction, List<Block> anchorSupports, Block guiBlock, BlockFace machineForward)
	{
		Block anchorBlock = harvester.detectFarmHeadAnchorBlock(anchorSupports, guiBlock);
		if(anchorBlock == null)
			return false;
		
		Block anchorSupportBlock = anchorBlock.getRelative(BlockFace.DOWN);
		if(anchorSupportBlock == null ||
				!plugin.fenceTypes.contains(anchorSupportBlock.getType()))
			return false;

		List<Block> harvestingRow = harvester.detectFarmHeadSupports(anchorBlock, machineForward);
		if(harvestingRow == null)
			return false;

		List<Block> blocksToMove = new ArrayList<Block>();
		blocksToMove.add(anchorBlock);
		blocksToMove.add(anchorSupportBlock);
		blocksToMove.add(harvester.detectFarmHarvesterHead(harvestingRow));
		for(Block block : harvestingRow)
			blocksToMove.add(block);

		BlockTranslation translation = new BlockTranslation(blocksToMove, direction);
		if(translation.detectObstruction() == true)
			return false;

		translation.cut();

		return true;
	}
	
	/**
	 * Generates a random number of drops for a specific farmable drop type.
	 * 
	 * @param farmable
	 *            the Farmable
	 * @return the drop amount or 0 if the farmable doesn't have random drop
	 *         amounts
	 */
	public int getDropAmount(Farmables farmable)
	{
		int random = (int) (Math.random() * 100);

		if (farmable == Farmables.CROPS)
		{
			if (random <= 25)
				return 0;
			else if (random <= 50)
				return 1;
			else if (random <= 75)
				return 2;
			else
				return 3;
		} else if (farmable == Farmables.NETHER_WARTS)
		{ // nether warts drop 2 - 4 per break
			if (random <= 33) // 100/3 == 33.333...
				return 2;
			else if (random <= 66)
				return 3;
			else
				return 4;
		} else
			return 0;
	}
	
	public int activeHarvesters()
	{
		return this.activeHarvesters.size();
	}
	
	public void removeActiveHarvester(Machine machine)
	{
		this.activeHarvesters.remove(machine);
	}

}
