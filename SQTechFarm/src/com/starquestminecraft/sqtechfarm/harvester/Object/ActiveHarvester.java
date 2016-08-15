package com.starquestminecraft.sqtechfarm.harvester.Object;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.Chest;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;

import com.starquestminecraft.sqtechbase.objects.Machine;
import com.starquestminecraft.sqtechfarm.SQTechFarm;
import com.starquestminecraft.sqtechfarm.Object.BlockData;
import com.starquestminecraft.sqtechfarm.Object.BlockTranslation;
import com.starquestminecraft.sqtechfarm.Object.Farmables;
import com.starquestminecraft.sqtechfarm.harvester.Harvester;
import com.starquestminecraft.sqtechfarm.util.DirectionUtils;

public class ActiveHarvester
{
	private final SQTechFarm plugin;
	private final Machine machine;
	private final HarvesterManagerTask manager;
	public final BlockFace forward;
	private Block anchor;
	public final Block guiBlock;
	private final Harvester harvester;
	public List<Block> anchorSupports; // fences
	public List<Block> farmHeadSupport; // iron bars
	private Chest chest;
	public Block harvesterHead; // the stained glass pane / end rod
	
	public ActiveHarvester(SQTechFarm plugin, Machine machine, HarvesterManagerTask manager)
	{
		this.manager = manager;
		this.plugin = plugin;
		this.machine = machine;
		harvester = plugin.getHarvesterType();
		guiBlock = machine.getGUIBlock().getLocation().getBlock();
		this.forward = Harvester.getHarvesterForward(guiBlock);
		this.anchorSupports = harvester.detectAnchorSupports(guiBlock, forward);
	}
	
	public boolean update()
	{
		anchor = harvester.detectFarmHeadAnchorBlock(anchorSupports, guiBlock);
		
		farmHeadSupport = harvester.detectFarmHeadSupports(anchor, forward);
		Block chestBlock = harvester.detectChest(guiBlock);
		harvesterHead = harvester.detectFarmHarvesterHead(farmHeadSupport);
		
		Integer rowPosition = harvester.getHarvestingRowLocation(guiBlock, forward);
		Integer headPosition = harvester.getHarvesterHeadLocation(guiBlock, forward, farmHeadSupport);
		
		if (rowPosition == null || anchorSupports.size() == 0 || harvesterHead == null || headPosition == null
				|| !plugin.isActive(machine) // stop if its not active
				|| (chestBlock.getState() instanceof Chest) == false)
			return false;
		
		chest = (Chest) chestBlock.getState();
		
		return true;
	}
	
	public boolean toPlant()
	{
		Block farmableBlock = harvesterHead.getRelative(BlockFace.DOWN);

		if (farmableBlock.isEmpty()) // its air
		{
			boolean planted = false;
			Block soilBlock = farmableBlock.getRelative(BlockFace.DOWN);
			
			if (soilBlock.getType() == Material.SOUL_SAND) // for nether warts
			{
				if(chest.getInventory().contains(Material.NETHER_STALK))
				{
					farmableBlock.setType(Material.NETHER_WARTS, false);
					chest.getInventory().removeItem(new ItemStack(Material.NETHER_STALK, 1));
					planted = true;
				}
			}
			else if(soilBlock.getType() == Material.SOIL)
			{
				for(Farmables farmable : Farmables.values())
				{
					if(farmable.seed() == null || farmable == Farmables.NETHER_WARTS)
						continue;
					
					if(chest.getInventory().contains(farmable.seed()))
					{
						farmableBlock.setType(farmable.material(), false);
						chest.getInventory().removeItem(new ItemStack(farmable.seed(), 1));
						planted = true;
						break;
					}
				}
			}
			
			if(planted == true)
			{
				if(machine.getEnergy() < plugin.getSettings().getHarvesterPlantingEnergy())
				{
					plugin.setInactive(machine);
					return false;
				}
			
				machine.setEnergy(machine.getEnergy() - plugin.getSettings().getHarvesterPlantingEnergy());
			}
			
			return true;
		}
		else return false;
	}
	
	@SuppressWarnings("deprecation")
	public void farm()
	{
		Block farmableBlock = harvesterHead.getRelative(BlockFace.DOWN);
		boolean farmBlock = false;
		
		for (Farmables farmable : Farmables.values())
		{
			if (farmableBlock.getType() == farmable.material()
					&& (int) farmableBlock.getData() == farmable.getmaxAge()) // ready to be farmed!
				farmBlock = true;
			else if (farmableBlock.getType() == farmable.material() && farmable.getmaxAge() == -1)
				farmBlock = true;
		}

		if (farmBlock == true)
		{
			if(machine.getEnergy() < plugin.getSettings().getHarvesterHarvestingEnergy())
			{
				plugin.setInactive(machine);
				return;
			}
		
			machine.setEnergy(machine.getEnergy() - plugin.getSettings().getHarvesterHarvestingEnergy());
			
			new BukkitRunnable()
			{
				@Override
				public void run()
				{
					Collection<ItemStack> drops = farmableBlock.getDrops(new ItemStack(Material.WOOD_HOE));
					// Support random item amounts
					if (farmableBlock.getType() == Material.NETHER_WARTS
							&& farmableBlock.getData() == Farmables.NETHER_WARTS.getmaxAge())
						drops.add(new ItemStack(Material.NETHER_STALK, manager.getDropAmount(Farmables.NETHER_WARTS)));
					else if (farmableBlock.getType() == Material.CROPS
							&& farmableBlock.getData() == Farmables.CROPS.getmaxAge())
						drops.add(new ItemStack(Material.SEEDS, manager.getDropAmount(Farmables.CROPS)));

					for (ItemStack drop : drops)
					{
						if (drop.getAmount() != 0)
							if (!chest.getInventory().addItem(drop).keySet().isEmpty())
							{
								plugin.setInactive(machine);
								stop();
								return;
							}
					}
					farmableBlock.setType(Material.AIR, false);
				}
			}.runTask(plugin);
		}
	}
	
	public void moveHeadToStart(int headPosition)
	{
		final Block head = this.guiBlock.getRelative(BlockFace.UP, 1).getRelative(com.starquestminecraft.sqtechbase.util.DirectionUtils.getLeft(forward), headPosition);
		update();
		Integer headPos = harvester.getHarvesterHeadLocation(head, forward, farmHeadSupport);
		
		if (headPos == null || headPos == 1)
			return;

		final BlockFace right = DirectionUtils.getRight(forward);
		final BlockData farmHead = new BlockData(head);

		new BukkitRunnable()
		{

			@Override
			public void run()
			{
				machine.data.put("blocked", true);
				Integer headPos = harvester.getHarvesterHeadLocation(farmHead.getBlock(), forward, farmHeadSupport);

				if (headPos == null || headPos == 1)
				{
					this.cancel();
					return;
				}

				BlockTranslation movement = new BlockTranslation(Arrays.asList(farmHead.getBlock()), right);
				if(movement.detectObstruction())
				{
					stop();
					return;
				}
				else
					movement.cut(); // paste the block

				Location newLoc = farmHead.getBlock().getRelative(right).getLocation();
				farmHead.changeLoc(newLoc);
				stop();
			}
			
			@Override
			public void cancel()
			{
				super.cancel();
				machine.data.put("blocked", false);
			}
		}.runTaskTimer(plugin, plugin.getSettings().getHarvesterSpeed(), plugin.getSettings().getHarvesterSpeed());
	}
	
	public void moveRowToStart(final Integer headPosition)
	{
		BlockFace direction = DirectionUtils.getBlockFaceBack(forward);
		update();
		new BukkitRunnable()
		{
			@Override
			public void run()
			{
				Integer rowPos = harvester.getHarvestingRowLocation(guiBlock, forward);

				if (rowPos == null)
				{
					cancel();
					return;
				}
				
				machine.data.put("blocked", true);

				if (rowPos == 0)
				{
					moveHeadToStart(headPosition);
					cancel();
					return;
				}
				
				if(manager.moveRow(direction, anchorSupports, guiBlock, forward) == false)
				{
					stop();
					cancel();
					return;
				}
			}
		}.runTaskTimer(plugin, plugin.getSettings().getHarvesterSpeed(), plugin.getSettings().getHarvesterSpeed());
	}
	
	public void stop()
	{
		//plugin.stopHarvester(machine);
		manager.queueInactive.add(machine);
	}
}
