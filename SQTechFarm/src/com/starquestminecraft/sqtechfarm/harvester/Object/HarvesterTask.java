package com.starquestminecraft.sqtechfarm.harvester.Object;

import java.util.Arrays;
import java.util.List;
import java.util.logging.Level;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import com.starquestminecraft.sqtechbase.objects.Machine;
import com.starquestminecraft.sqtechfarm.SQTechFarm;
import com.starquestminecraft.sqtechfarm.Object.BlockData;
import com.starquestminecraft.sqtechfarm.Object.BlockTranslation;
import com.starquestminecraft.sqtechfarm.harvester.Harvester;
import com.starquestminecraft.sqtechfarm.util.DirectionUtils;

public class HarvesterTask extends BukkitRunnable
{

	private SQTechFarm plugin;
	private final MovingHarvester movingHarvester;
	//private Player player;
	private Machine machine;
	private final BlockFace forward;
	private Block anchor;
	private final Block guiBlock;
	private Boolean cancelled = false;
	private final Harvester harvesterObj;
	private List<Block> anchorSupports; // fences
	private List<Block> farmHeadSupport; // iron bars
	private Block chest;
	private Block harvesterHead; // the stained glass pane / end rod
	
	/**
	 * This assumes that the MovingHarvester was already registered to the SQTechFarm hashmap.
	 */
	public HarvesterTask(SQTechFarm plugin, MovingHarvester movingHarvester, Player player, Machine machine, Harvester harvester)
	{
		this.machine = machine;
		//this.player = player;
		this.plugin = plugin;
		this.movingHarvester = movingHarvester;
		harvesterObj = harvester;
		guiBlock = machine.getGUIBlock().getLocation().getBlock();
		this.forward = Harvester.getHarvesterForward(guiBlock);
		this.anchorSupports = harvester.detectAnchorSupports(guiBlock, forward);
	}
	
	@Override
	public void run()
	{
		
		if(forward == null)
			movingHarvester.stopHarvester();
		if(this.cancelled == true)
			return;
		
		anchor = harvesterObj.detectFarmHeadAnchorBlock(anchorSupports, guiBlock);
		
		Integer rowPosition = harvesterObj.getHarvestingRowLocation(guiBlock, forward);
		
		farmHeadSupport = harvesterObj.detectFarmHeadSupports(anchor, forward);
		chest = harvesterObj.detectChest(guiBlock);
		harvesterHead = harvesterObj.detectFarmHarvesterHead(farmHeadSupport);
		
		Integer headPosition = harvesterObj.getHarvesterHeadLocation(guiBlock, forward, farmHeadSupport);
		
		if(rowPosition == null || anchorSupports.size() == 0 
				|| chest == null || harvesterHead == null
				|| headPosition == null || !plugin.isActive(machine)) // stop if its not active
			movingHarvester.stopHarvester();
		if(this.cancelled == true)
			return;
		
		Boolean headAtEnd = false;
		
		if((rowPosition % 2 == 0 && headPosition == 1) || (rowPosition % 2 != 0 && headPosition == farmHeadSupport.size())) // detect if head is at end
			headAtEnd = true;
		
		if(rowPosition == anchorSupports.size() && headAtEnd) // row is at end, move back to start then stop machine
		{
			this.moveRowToStart(this.movingHarvester, headPosition);
			movingHarvester.stopHarvester();
			return;
		}
		
		if(headAtEnd == true)
		{
			movingHarvester.moveRow(forward);
		}
		
	}
	
	@Override
	public void cancel()
	{
		try
		{
			super.cancel();
		}
		catch(IllegalStateException e)
		{
			Bukkit.getLogger().log(Level.INFO, "[SQTechFarm] Someone attempted to turn a harvester off but it was already off.");
		}
		this.cancelled = true;
	}
	
	private void moveRowToStart(final MovingHarvester mh, final Integer headPosition)
	{
		BlockFace direction = DirectionUtils.getBlockFaceBack(forward);
		final HarvesterTask task = this;
		new BukkitRunnable()
		{
			@Override
			public void run()
			{
				Integer rowPos = harvesterObj.getHarvestingRowLocation(guiBlock, forward);
				
				if(rowPos == null)
				{
					cancel();
					return;
				}
				
				machine.data.put("blocked", true);
				
				mh.moveRow(direction);
				
				if(rowPos == 1)
				{
					task.moveHeadToStart(mh, headPosition);
					cancel();
					return;
				}
			}
		}.runTaskTimer(plugin, plugin.getSettings().getHarvesterSpeed(), plugin.getSettings().getHarvesterSpeed());
	}
	
	public void moveHeadToStart(final MovingHarvester mh, int headPosition)
	{
		
		final Block head = this.guiBlock.getRelative(BlockFace.UP, 2).getRelative(com.starquestminecraft.sqtechbase.util.DirectionUtils.getLeft(forward), headPosition);
		final List<Block> headSupports = this.harvesterObj.detectFarmHeadSupports(this.guiBlock.getRelative(BlockFace.UP), forward);
		Integer headPos = harvesterObj.getHarvesterHeadLocation(head, forward, headSupports);
		
		if(headPos == null || headPos == 1)
			return;
		
		final BlockFace right = DirectionUtils.getRight(forward);
		final BlockData farmHead = new BlockData(head);
		
		new BukkitRunnable()
		{

			@Override
			public void run()
			{
				Integer headPos = harvesterObj.getHarvesterHeadLocation(farmHead.getBlock(), forward, headSupports);
				
				if(headPos == null || headPos == 1)
				{
					this.cancel();
					return;
				}
				
				BlockTranslation movement = new BlockTranslation(Arrays.asList(farmHead.getBlock()), right);
				movement.cut(); //paste the block
				
				Location newLoc = farmHead.getBlock().getRelative(right).getLocation();
				farmHead.changeLoc(newLoc);
			}
			
			@Override
			public void cancel()
			{
				super.cancel();
				machine.data.put("blocked", false);
			}
			
		}.runTaskTimer(plugin, plugin.getSettings().getHarvesterSpeed(), plugin.getSettings().getHarvesterSpeed());
	}

}
