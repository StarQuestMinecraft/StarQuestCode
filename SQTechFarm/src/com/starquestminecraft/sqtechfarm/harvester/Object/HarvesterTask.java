package com.starquestminecraft.sqtechfarm.harvester.Object;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.logging.Level;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.Chest;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;

import com.starquestminecraft.sqtechbase.objects.Machine;
import com.starquestminecraft.sqtechfarm.SQTechFarm;
import com.starquestminecraft.sqtechfarm.Object.BlockData;
import com.starquestminecraft.sqtechfarm.Object.BlockTranslation;
import com.starquestminecraft.sqtechfarm.Object.Farmables;
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
	private Chest chest;
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
	
	@SuppressWarnings("deprecation")
	@Override
	public void run()
	{
		
		if(forward == null || !machine.detectStructure())
			movingHarvester.stopHarvester();
		if(this.cancelled == true)
			return;
		
		anchor = harvesterObj.detectFarmHeadAnchorBlock(anchorSupports, guiBlock);
		
		Integer rowPosition = harvesterObj.getHarvestingRowLocation(guiBlock, forward);
		
		farmHeadSupport = harvesterObj.detectFarmHeadSupports(anchor, forward);
		Block chestBlock = harvesterObj.detectChest(guiBlock);
		harvesterHead = harvesterObj.detectFarmHarvesterHead(farmHeadSupport);
		
		Integer headPosition = harvesterObj.getHarvesterHeadLocation(guiBlock, forward, farmHeadSupport);
		
		if(rowPosition == null || anchorSupports.size() == 0 
				|| harvesterHead == null || headPosition == null 
				|| !plugin.isActive(machine) // stop if its not active
				|| !(chestBlock.getState() instanceof Chest))
			movingHarvester.stopHarvester();
		if(this.cancelled == true)
			return;
		
		this.chest = (Chest) chestBlock.getState();
		
		Boolean headAtEnd = false;
		
		if((rowPosition % 2 != 0 && headPosition == 1) || (rowPosition % 2 == 0 && headPosition == farmHeadSupport.size())) // detect if head is at end
			headAtEnd = true;
		
		if(rowPosition == anchorSupports.size() && headAtEnd) // row is at end, move back to start then stop machine
		{
			if(this.cancelled == true)
				return;
			this.moveRowToStart(this.movingHarvester, headPosition);
			movingHarvester.stopHarvester();
			return;
		}
		else if(headAtEnd == true) // when the end rod reaches the end of the iron supports
		{
			if(this.cancelled == true || !plugin.isActive(machine))
				return;
			movingHarvester.moveRow(forward);
			
			movingHarvester.restartTask(plugin.getSettings().getHarvesterSpeed());
			return;
		}
		else
		{
			BlockFace headForwards;
			
			if(rowPosition % 2 == 0) // if its even
				headForwards = DirectionUtils.getLeft(forward);
			else
				headForwards = DirectionUtils.getRight(forward);
			
			Block farmableBlock = harvesterHead.getRelative(BlockFace.DOWN, 2);
			boolean farmBlock = false;
			
			for(Farmables farmable : Farmables.values())
			{
				if(farmableBlock.getType() == farmable.material() && (int)farmableBlock.getData() == farmable.getmaxAge()) // ready to be farmed!
					farmBlock = true;
			}
			
			if(this.cancelled == true || !plugin.isActive(machine))
				return;
			
			if(farmBlock == true)
			{
				new BukkitRunnable()
				{
					@Override
					public void run()
					{
						Collection<ItemStack> drops = farmableBlock.getDrops(new ItemStack(Material.WOOD_HOE));
						System.out.println(drops);
						for(ItemStack drop : drops)
						{
							if(!chest.getInventory().addItem(drop).keySet().isEmpty())
							{
								plugin.setInactive(machine);
								return;
							}
						}
						farmableBlock.setType(Material.AIR, false);
					}	
				}.runTaskLater(plugin, (long) plugin.getSettings().getHarvesterSpeed());
			}
			
			if(this.cancelled == true || !plugin.isActive(machine) || !machine.detectStructure())
			{
				movingHarvester.stopHarvester();
				return;
			}
			
			BlockTranslation headMove = new BlockTranslation(Arrays.asList(harvesterHead), headForwards);
			headMove.cut();
			
			movingHarvester.restartTask(plugin.getSettings().getHarvesterSpeed());
			return;
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
				
				machine.data.put("blocked", false);	
			}
		}.runTaskTimer(plugin, plugin.getSettings().getHarvesterSpeed(), plugin.getSettings().getHarvesterSpeed());
	}
	
	/**
	 * Generates a random number of drops for a specific farmable drop type.
	 * @param farmable the Farmable
	 * @return the drop amount or 0 if the farmable doesn't have random drop amounts
	 */
	private int getDropAmount(Farmables farmable)
	{
		int random = (int)(Math.random()*100);
		
		if(farmable == Farmables.CROPS)
		{
			if(random <= 25)
				return 0;
			else if(random <= 50)
				return 1;
			else if(random <= 75)
				return 2;
			else
				return 3;
		}
		else if(farmable == Farmables.NETHER_WARTS)
		{ // nether warts drop 2 - 4 per break
			if(random <= 33) // 100/3 == 33.333...
				return 2;
			else if(random <= 66)
				return 3;
			else
				return 4;
		}
		else
			return 0;
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
