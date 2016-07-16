package me.dan14941.sqtechdrill.task;

import me.dan14941.sqtechdrill.Drill;
import me.dan14941.sqtechdrill.SQTechDrill;
import me.dan14941.sqtechdrill.movement.MovingDrill;

import org.bukkit.*;
import org.bukkit.block.*;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;

import com.starquestminecraft.sqtechbase.objects.Machine;

import java.util.*;

public class DrillMoveRunnable extends BukkitRunnable
{
	private Machine drill;
	private BlockFace forward;
	private MovingDrill md;
	private Player player;

	public DrillMoveRunnable(final Machine drill, final BlockFace forward, final MovingDrill md, final Player player) {
		this.drill = drill;
		this.md = md;
		this.forward = forward;
		this.player = player;
	}

	public void run() {
		if (!SQTechDrill.getMain().drill.detectStructure(this.drill.getGUIBlock()) || 
				this.drill.getGUIBlock().getLocation().getBlock().getType() != Material.LAPIS_BLOCK ||
				(boolean) drill.data.get("isActive") == false)
		{ // Make sure the drill is intact and active
			this.cancel(); // If not then stop the runnable
			return;
		}
		
		final List<Block> frontBlocks = MovingDrill.getBlocksInFront(this.drill); // gets the blocks in front of the drill
		List<Object> values = this.checkFrontBlocks(frontBlocks); // check the blocks in front and return a List of
		int air = 0;											  // Objects being the amount of air and if liquid is present
		boolean liquidPresent = false;
		
		for(Object value : values) // start casting the values in the list
		{
			if(value instanceof Integer)
				air = (Integer) value;
			else if(value instanceof Boolean)
				liquidPresent = (Boolean) value;
		}
		
		//System.out.println(" " + air  + liquidPresent);
		
		int energyPerBlock = SQTechDrill.getMain().getEnergyPerBlockMined();
		
		if (air == frontBlocks.size() && !liquidPresent) // checks if all block in front of the drill are air
		{
			MovingDrill.moveDrill(this.forward, this.drill); // move the drill one block forward
			restart(drill, SQTechDrill.getMain().getDefaultDrillSpeed()); // restart the drill with a delay
		}
		else if(liquidPresent) // or if there is a liquid in front of the drill
		{
			drill.data.put("isActive", false); // turn the drill off
			return;
		}
		else if(!liquidPresent) // or if liquid is not present and not all block in front are air
		{
			List<Block> blocksToRemove = new ArrayList<Block>(); // List to store blocks to set to air
			
			for(Block frontBlock : frontBlocks) // Enumerate through the front block
				if(!frontBlock.isEmpty() && !frontBlock.isLiquid()) // Make sure its not air and not liquid
					blocksToRemove.add(frontBlock); // add it to the List
			
			BukkitRunnable blockBreak = new BukkitRunnable() // new runnable to remove the blocks
			{
				int count = 1;
				
				@Override
				public void run()
				{
					if((Boolean)drill.data.get("isActive") == false)
					{
						cancel();
						return;
					}
					// System.out.println("Blocks to remove: " + blocksToRemove.size());
					// System.out.println("Count: " + count);
					
					if(blocksToRemove.size() == 0 || drill.detectStructure() == false)
						cancel();
					else if(count >= blocksToRemove.size())
					{
						recheck();
						cancel();
					}
					
					Block b = blocksToRemove.get(count-1);
					if(b.getType() == Material.BEDROCK)
					{
						player.sendMessage(ChatColor.RED + "A drill has hit bedrock.");
						drill.data.put("isActive", false); // turn the drill off
						cancel();
						return;
					}
					else if(drill.getEnergy() < energyPerBlock)
					{
						player.sendMessage(ChatColor.RED + "A drill has run out of energy.");
						drill.data.put("isActive", false); // turn the drill off
						cancel();
						return;
					}
					
					final Collection<ItemStack> drops = b.getDrops();
					b.setType(Material.AIR);
					count++;
					drill.setEnergy(drill.getEnergy() - energyPerBlock); // remove the energy
					
					Chest chest = (Chest) Drill.detectChest(drill.getGUIBlock().getLocation().getBlock()).getState();
					for(ItemStack item : drops)
					{
						HashMap<Integer, ItemStack> notAdded = chest.getInventory().addItem(item);
						if(notAdded.values().size() >= 1)
						{
							for(ItemStack itemNotAdded : notAdded.values())
							{
								chest.getBlock().getWorld().dropItemNaturally(chest.getLocation(), itemNotAdded);
							}
							player.sendMessage(ChatColor.RED + "The drills chest is full! Deactivating.");
							drill.data.put("isActive", false); // turn the drill off
							cancel();
						}
					}
				}
			};
			
			blockBreak.runTaskTimer(SQTechDrill.getMain(), 0, SQTechDrill.getMain().getDefaultDrillSpeed());
		}
	}
	
	/**
	 * Restarts the drilling
	 * @param drill the Machine
	 * @param delay the delay between restarting
	 */
	private void restart(final Machine drill, int delay)
	{
		if((boolean) drill.data.get("isActive") == true)
			this.md.restartDrillMove(delay, this.drill, player);
	}
	
	/**
	 * 
	 * @param frontBlocks a List of the Blocks in front of the drill
	 * @return a List with an Integer referring to the amount of air in front of the drill and a Boolean indicating if liquid is present
	 */
	private List<Object> checkFrontBlocks(final List<Block> frontBlocks)
	{	
		List<Object> result = new ArrayList<Object>();
		
		Integer air = 0;
		Boolean liquidPresent = false;

		for (final Block b : frontBlocks) // Every block in front of the drill
		{
			if (!b.getType().isSolid())  // if its not solid
			{
				if (b.getType() == Material.AIR) // checks if it air
					air++; // add to the air counter
				else 
					if (b.isLiquid()) // or if its liquid
						liquidPresent = true;
			}
		}
		
		result.add(air);
		result.add(liquidPresent);
		return result;
	}
	
	private void recheck()
	{
		final List<Block> frontBlocksCheck = MovingDrill.getBlocksInFront(this.drill);
		for(Block block : frontBlocksCheck)
		{
			if(block.isEmpty())
			{
				this.restart(drill, SQTechDrill.getMain().getDefaultDrillSpeed());
				return;
			}
		}
	}
}
