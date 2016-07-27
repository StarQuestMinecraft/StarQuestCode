package com.starquestminecraft.sqtechfarm.harvester.Object;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Player;

import com.starquestminecraft.sqtechbase.objects.Machine;
import com.starquestminecraft.sqtechfarm.SQTechFarm;
import com.starquestminecraft.sqtechfarm.Object.BlockTranslation;
import com.starquestminecraft.sqtechfarm.harvester.Harvester;

public class MovingHarvester
{
	final private SQTechFarm plugin;
	final private Machine machine;
	final private Player player;
	final private Harvester harvester;
	final private Block guiBlock;
	private List<Block> anchorSupports;
	final private BlockFace machineForward;
	private HarvesterTask task;

	public MovingHarvester(SQTechFarm plugin, Machine machine, Player player)
	{
		this.plugin = plugin;
		this.machine = machine;
		this.player = player;
		this.harvester = plugin.getHarvesterType();
		this.machineForward = Harvester.getHarvesterForward(machine);
		this.guiBlock = machine.getGUIBlock().getLocation().getBlock();
		this.anchorSupports = harvester.detectAnchorSupports(guiBlock, machineForward);
	}
	
	/**
	 * Starts a HarvesterTask and registers the MovingHarvester.
	 */
	public void startHarvester()
	{
		plugin.registerMovingHarvester(machine, this); // register this MovingHarvester
		plugin.setActive(machine);

		// Integer rowLoc = harvester.getHarvestingRowLocation(guiBlock, machineForward);
		
		this.task = new HarvesterTask(plugin, this, player, machine, harvester); // moves head and harvests foods
		task.runTaskLater(plugin, plugin.getSettings().getHarvesterSpeed());
		
		/*
		if(rowLoc == null || rowLoc > machineSize)
		{
			player.sendMessage(ChatColor.RED + "The harvester could not start.");
			return false;
		}

		if(this.moveRow(machineForward) == false)
		{
			player.sendMessage(ChatColor.RED + "The harvester could not move.");
			return false;
		}
		*/
	}
	
	public void stopHarvester()
	{
		plugin.unRegisterMovingHarvester(machine);
		task.cancel();
		plugin.setInactive(machine);
	}

	/**
	 * This moves the row of a harvester in the selected direction, this does not check if it will go off the end.
	 * @param direction the BlockFace direction of the row movement
	 * @return a boolean indicating if the operation was performed successfully
	 */
	public boolean moveRow(BlockFace direction)
	{
		Block anchorBlock = harvester.detectFarmHeadAnchorBlock(anchorSupports, guiBlock);
		if(anchorBlock == null)
			return false;

		List<Block> harvestingRow = harvester.detectFarmHeadSupports(anchorBlock, machineForward);
		if(harvestingRow == null)
			return false;

		List<Block> blocksToMove = new ArrayList<Block>();
		blocksToMove.add(anchorBlock);
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
	 * Restarts the HarvesterTask with a delay.
	 * @param delay the delay
	 */
	void restartTask(int delay)
	{
		this.task = new HarvesterTask(plugin, this, player, machine, harvester); // moves head and harvests foods
		task.runTaskLater(plugin, delay);
	}

}
