package com.starquestminecraft.sqtechfarm.harvester.Object;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Player;

import com.starquestminecraft.sqtechbase.objects.Machine;
import com.starquestminecraft.sqtechfarm.SQTechFarm;
import com.starquestminecraft.sqtechfarm.Object.BlockTranslation;
import com.starquestminecraft.sqtechfarm.harvester.Harvester;
import com.starquestminecraft.sqtechfarm.util.DirectionUtils;

public class MovingHarvester
{
	final private SQTechFarm plugin;
	final private Machine machine;
	final private Player player;
	final private Harvester harvester;
	final private Block guiBlock;
	private List<Block> anchorSupports;
	final private BlockFace machineForward;
	final private int machineSize;

	public MovingHarvester(SQTechFarm plugin, Machine machine, Player player)
	{
		this.plugin = plugin;
		this.machine = machine;
		this.player = player;
		this.harvester = plugin.getHarvesterType();
		this.machineForward = Harvester.getHarvesterForward(machine);
		this.guiBlock = machine.getGUIBlock().getLocation().getBlock();
		this.anchorSupports = harvester.detectAnchorSupports(guiBlock, machineForward);
		this.machineSize = (Integer) machine.data.get("size");
	}

	public boolean startHarvester()
	{
		plugin.registerMovingHarvester(machine, this);
		plugin.setActive(machine);

		Integer rowLoc = harvester.getHarvestingRowLocation(guiBlock, machineForward);

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
		return true;
	}

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

}
