package me.dan14941.sqtechdrill.task;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.scheduler.BukkitRunnable;

import com.starquestminecraft.sqtechbase.objects.Machine;

import me.dan14941.sqtechdrill.Drill;
import me.dan14941.sqtechdrill.SQTechDrill;
import me.dan14941.sqtechdrill.movement.BlockTranslation;
import me.dan14941.sqtechdrill.object.ActiveDrill;

public class DrillManager extends BukkitRunnable
{
	private final SQTechDrill plugin;
	private final List<Machine> activeDrills;
	final List<Machine> queueInactive;
	private BlockBreakManager breakManager;
	
	public DrillManager(SQTechDrill plugin)
	{
		this.plugin = plugin;
		activeDrills = new ArrayList<Machine>();
		queueInactive = new ArrayList<Machine>();
	}
	
	private static boolean faster = true;
	
	@Override
	public void cancel()
	{
		super.cancel();
		this.breakManager.cancel();
	}
	
	@Override
	public void run()
	{
		for(Machine machine : queueInactive)
		{
			this.activeDrills.remove(machine);
		}
		queueInactive.clear();
		
		for(Machine machine : activeDrills)
		{
			if((boolean) machine.data.get("isActive") == false)
				continue;
		
			if(plugin.drill.getDrillSpeed(machine) == plugin.getFastDrillSpeed() && faster == false)
				continue;
			
			ActiveDrill activeDrill = new ActiveDrill(plugin, machine, this);
			if(activeDrill.detect() == false)
			{
				activeDrill.stop();
				continue;
			}
			
			List<Object> values = activeDrill.checkFrontBlocks(activeDrill.frontBlocks); // check the blocks in front and return a List of
			int air = 0;											  // Objects being the amount of air and if liquid is present
			boolean liquidPresent = false;
			
			for(Object value : values) // start casting the values in the list
			{
				if(value instanceof Integer)
					air = (Integer) value;
				else if(value instanceof Boolean)
					liquidPresent = (Boolean) value;
			}
			
			if(this.breakManager == null)
			{
				this.breakManager = new BlockBreakManager(plugin);
				this.breakManager.runTaskTimer(plugin, plugin.getFastDrillSpeed(), plugin.getFastDrillSpeed());
			}
			if(breakManager.isBreakingBlock(activeDrill))
				continue;
			
			if (air == activeDrill.frontBlocks.size() && !liquidPresent) // checks if all block in front of the drill are air
			{
				//MovingDrill.moveDrill(activeDrill.getDrillForward(), machine); // move the drill one block forward
				this.moveDrill(activeDrill.getDrillForward(), machine, activeDrill);
			}
			else if(liquidPresent) // or if there is a liquid in front of the drill
			{
				activeDrill.stop(); // turn the drill off
				continue;
			}
			else if(!liquidPresent) // or if liquid is not present and not all block in front are air
			{
				List<Block> blocksToRemove = new ArrayList<Block>(); // List to store blocks to set to air
				
				for(Block frontBlock : activeDrill.frontBlocks) // Enumerate through the front block
					if(!frontBlock.isEmpty() && !frontBlock.isLiquid()) // Make sure its not air and not liquid
						blocksToRemove.add(frontBlock); // add it to the List
				
				/*
				BukkitRunnable blockBreak = new BukkitRunnable() // new runnable to remove the blocks
				{
					int count = 1; // blocks removed
					
					@Override
					public void run()
					{
						if((Boolean) machine.data.get("isActive") == false)
						{
							activeDrill.stop();
							return;
						}
						
						if(blocksToRemove.size() == 0 || machine.detectStructure() == false)
							cancel();
						else if(count > blocksToRemove.size())
						{
							return;
						}
						
						Block b = blocksToRemove.get(count-1);
						if(b.getType() == Material.BEDROCK)
						{
							// player.sendMessage(ChatColor.RED + "A drill has hit bedrock.");
							activeDrill.stop(); // turn the drill off
							return;
						}
						else if(machine.getEnergy() < energyPerBlock)
						{
							// player.sendMessage(ChatColor.RED + "A drill has run out of energy.");
							activeDrill.stop(); // turn the drill off
							return;
						}
						
						final Collection<ItemStack> drops = b.getDrops();
						b.setType(Material.AIR);
						count++;
						machine.setEnergy(machine.getEnergy() - energyPerBlock); // remove the energy
						
						Chest chest = (Chest) Drill.detectChest(machine.getGUIBlock().getLocation().getBlock()).getState();
						for(ItemStack item : drops)
						{
							HashMap<Integer, ItemStack> notAdded = chest.getInventory().addItem(item);
							if(notAdded.values().size() >= 1)
							{
								for(ItemStack itemNotAdded : notAdded.values())
								{
									chest.getBlock().getWorld().dropItemNaturally(chest.getLocation(), itemNotAdded);
								}
								// player.sendMessage(ChatColor.RED + "The drills chest is full! Deactivating."); 
								activeDrill.stop(); // turn the drill off
								cancel();
							}
						}
					}
				};
				
				blockBreak.runTaskTimer(SQTechDrill.getMain(), 0, plugin.drill.getDrillSpeed(machine));
				*/
				boolean slowDrill = true;
				if(plugin.drill.getDrillSpeed(machine) == plugin.getFastDrillSpeed())
					slowDrill = false;
				this.breakBlock(activeDrill, blocksToRemove, slowDrill);
			}
		}
		
		if(faster == true) // reset faster
			faster = false;
		else
			faster = true;
	}
	
	/**
	 * Moves the drill one block forward in the direction set. Any blocks in the way will get replaced.
	 * @param forward
	 * @param drill
	 * @return
	 */
	private boolean moveDrill(BlockFace forward, Machine drill, ActiveDrill activeDrill)
	{
		List<Block> frontBlocks = activeDrill.frontBlocks;
		for(Block block : frontBlocks)
		{
			if(block.getType() == null)
				return false;
		}
		
		if(forward != BlockFace.DOWN)
		{
			Block support = drill.getGUIBlock().getLocation().getBlock().getRelative(BlockFace.DOWN, 2).getRelative(forward);
			if(support.isEmpty() || support.isLiquid())
			{
				activeDrill.stop();
				return false;
			}
		}
		Block guiBlock = drill.getGUIBlock().getLocation().getBlock();

		List<Block> machineBlocks = new ArrayList<Block>();
		machineBlocks.add(guiBlock.getRelative(forward));
		machineBlocks.add(guiBlock);
		machineBlocks.add(Drill.detectChest(guiBlock));
		machineBlocks.add(SQTechDrill.getMain().drill.detectFuelInventory(guiBlock, forward));
		for(Block baseBlock : SQTechDrill.getMain().drill.detectBase(guiBlock, forward))
			if(baseBlock != null)
				machineBlocks.add(baseBlock);
		
		drill.getGUIBlock().changeLocation(guiBlock.getRelative(forward).getLocation());
		
		BlockTranslation movement = new BlockTranslation(machineBlocks, forward);
		movement.cut();
		
		drill.setEnergy(drill.getEnergy() - 10);
		SQTechDrill.getMain().drill.updateEnergy(drill);

		return true;
	}
	
	public void addMachineToInactiveQueue(Machine machine)
	{
		this.queueInactive.add(machine);
	}
	
	public void removeActiveMachine(Machine machine)
	{
		this.activeDrills.remove(machine);
	}
	
	public int activeDrills()
	{
		return activeDrills.size();
	}
	
	public void addDrill(Machine drill)
	{
		this.activeDrills.add(drill);
	}
	
	public void breakBlock(ActiveDrill drill, List<Block> blocks, boolean slow)
	{	
		if(slow == true)
			this.breakManager.addBlocksToRemoveSlow(drill, blocks);
		else
			this.breakManager.addBlocksToRemoveFast(drill, blocks);
	}

}
