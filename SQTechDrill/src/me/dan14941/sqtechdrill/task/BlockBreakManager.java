package me.dan14941.sqtechdrill.task;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.Chest;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;

import com.starquestminecraft.sqtechbase.objects.Machine;

import me.dan14941.sqtechdrill.Drill;
import me.dan14941.sqtechdrill.SQTechDrill;
import me.dan14941.sqtechdrill.object.ActiveDrill;

public class BlockBreakManager extends BukkitRunnable
{
	private final SQTechDrill plugin;
	private HashMap<ActiveDrill, List<Block>> queueToRemoveFast;
	private HashMap<ActiveDrill, List<Block>> queueToRemoveSlow;
	private List<ActiveDrill> queueInactiveMachine = new ArrayList<>();

	public BlockBreakManager(SQTechDrill plugin)
	{
		this.plugin = plugin;
		
		queueToRemoveFast = new HashMap<>();
		queueToRemoveSlow = new HashMap<>();
	}

	public void addBlocksToRemoveFast(ActiveDrill drill, List<Block> blocks)
	{
		queueToRemoveFast.put(drill, blocks);
	}
	
	public void addBlocksToRemoveSlow(ActiveDrill drill, List<Block> blocks)
	{
		queueToRemoveSlow.put(drill, blocks);
	}

	private static boolean fast = true;
	
	@Override
	public void run()
	{
		List<ActiveDrill> queueRemoveFromDrilling = new ArrayList<>();
		for (ActiveDrill activeDrill : queueToRemoveFast.keySet())
		{
			if(queueToRemoveFast.get(activeDrill).isEmpty())
			{
				queueRemoveFromDrilling.add(activeDrill);
				continue;
			}
			
			if((boolean) activeDrill.getMachine().data.get("isActive") == false)
			{
				queueRemoveFromDrilling.add(activeDrill);
				continue;
			}
			
			List<Block> blocks = queueToRemoveFast.get(activeDrill);
			if(this.drill(activeDrill, blocks) == false)
				continue;
		}
		
		for(List<Block> blocks : queueToRemoveFast.values())
		{
			if(blocks.isEmpty())
				continue;
			blocks.remove(blocks.get(0));
		}
		
		if(fast == true)
			fast = false;
		else
			fast = true;
		
		if(fast == false)
		{
			for (ActiveDrill activeDrill : queueToRemoveSlow.keySet())
			{
				if(queueToRemoveSlow.get(activeDrill).isEmpty())
				{
					queueRemoveFromDrilling.add(activeDrill);
					continue;
				}
				
				if((boolean) activeDrill.getMachine().data.get("isActive") == false)
				{
					queueRemoveFromDrilling.add(activeDrill);
					continue;
				}
				
				List<Block> blocks = queueToRemoveSlow.get(activeDrill);
				if(this.drill(activeDrill, blocks) == false)
					continue;
			}
			
			for(List<Block> blocks : queueToRemoveSlow.values())
			{
				if(blocks.isEmpty())
					continue;
				blocks.remove(blocks.get(0));
			}
		}
		
		for(ActiveDrill drill : queueInactiveMachine)
		{
			queueToRemoveFast.remove(drill);
			queueToRemoveSlow.remove(drill);
		}
		
		for(ActiveDrill drill : queueRemoveFromDrilling)
		{
			queueToRemoveFast.remove(drill);
			queueToRemoveSlow.remove(drill);
		}
	}
	
	private boolean drill(ActiveDrill activeDrill, List<Block> blocks)
	{
		Block toRemove = blocks.get(0);
		Machine machine = activeDrill.getMachine();
		
		int energyPerBlock = plugin.getEnergyPerBlockMined();

		if ((Boolean) machine.data.get("isActive") == false)
		{
			queueInactiveMachine.add(activeDrill);
			activeDrill.stop();
			return false;
		}

		if (blocks.size() == 0 || machine.detectStructure() == false)
			return false;
		
		if (toRemove.getType() == Material.BEDROCK)
		{
			// player.sendMessage(ChatColor.RED + "A drill has hit bedrock.");
			activeDrill.stop(); // turn the drill off
			return false;
		}
		else if (machine.getEnergy() < energyPerBlock)
		{
			// player.sendMessage(ChatColor.RED + "A drill has run out of energy.");
			activeDrill.stop(); // turn the drill off
			return false;
		}

		final Collection<ItemStack> drops = toRemove.getDrops();
		toRemove.setType(Material.AIR);
		machine.setEnergy(machine.getEnergy() - energyPerBlock); // remove the energy

		Chest chest = (Chest) Drill.detectChest(machine.getGUIBlock().getLocation().getBlock()).getState();
		for (ItemStack item : drops)
		{
			HashMap<Integer, ItemStack> notAdded = chest.getInventory().addItem(item);
			if (notAdded.values().size() >= 1)
			{
				for (ItemStack itemNotAdded : notAdded.values())
				{
					chest.getBlock().getWorld().dropItemNaturally(chest.getLocation(), itemNotAdded);
				}
				// player.sendMessage(ChatColor.RED + "The drills chest is full! Deactivating."); 
				activeDrill.stop(); // turn the drill off
				return false;
			}
		}
		
		return true;
	}
	
	public boolean isBreakingBlock(ActiveDrill drill)
	{
		if(this.queueToRemoveFast.containsKey(drill) || this.queueToRemoveSlow.containsKey(drill))
			return true;
		else
			return false;
	}
}
