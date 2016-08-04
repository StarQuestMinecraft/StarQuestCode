package me.dan14941.sqtechdrill.object;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;

import com.starquestminecraft.sqtechbase.objects.Machine;

import me.dan14941.sqtechdrill.Drill;
import me.dan14941.sqtechdrill.SQTechDrill;
import me.dan14941.sqtechdrill.movement.MovingDrill;
import me.dan14941.sqtechdrill.task.DrillManager;

public class ActiveDrill
{

	private final SQTechDrill plugin;
	private final Machine machine;
	private final DrillManager manager;
	private final BlockFace forward;
	private Block guiBlock;
	public final List<Block> frontBlocks;
	
	public ActiveDrill(SQTechDrill plugin, Machine machine, DrillManager manager)
	{
		this.plugin = plugin;
		this.machine = machine;
		this.manager = manager;
		this.guiBlock = machine.getGUIBlock().getLocation().getBlock();
		forward = Drill.getDrillForward(guiBlock);
		this.frontBlocks = MovingDrill.getBlocksInFront(machine);
	}
	
	public boolean detect()
	{
		if(!plugin.drill.detectStructure(machine.getGUIBlock()))
			return false;
		else
		{
			return true;
		}
	}
	
	public void stop()
	{
		plugin.deActivateDrill(machine);
		manager.addMachineToInactiveQueue(machine);
	}
	
	/**
	 * @param frontBlocks a List of the Blocks in front of the drill
	 * @return a List with an Integer referring to the amount of air in front of the drill and a Boolean indicating if liquid is present
	 */
	public List<Object> checkFrontBlocks(final List<Block> frontBlocks)
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
	
	public BlockFace getDrillForward()
	{
		return this.forward;
	}
	
	public Machine getMachine()
	{
		return this.machine;
	}
}
