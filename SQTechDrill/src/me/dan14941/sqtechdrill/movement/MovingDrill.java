package me.dan14941.sqtechdrill.movement;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;

import com.starquestminecraft.sqtechbase.objects.Machine;

import me.dan14941.sqtechdrill.Drill;
import me.dan14941.sqtechdrill.EventSimulator;
import me.dan14941.sqtechdrill.SQTechDrill;

public class MovingDrill implements Listener
{
	private SQTechDrill main;
	
	public MovingDrill(SQTechDrill mainPlugin)
	{
		this.main = mainPlugin;
		main.getServer().getPluginManager().registerEvents(this, mainPlugin);
	}

	/**
	 * Gives a List of blocks in a 3 by 3 grid in front of the drill head
	 * @param drill
	 * @return a List of the blocks in front of the drill head arranged in a 3 by 3 grid
	 */
	public static List<Block> getBlocksInFront(Machine drill)
	{
		BlockFace forward = Drill.getDrillForward(drill.getGUIBlock().getLocation().getBlock());
		List<Block> blocksInfront = new ArrayList<Block>();

		Block blockInfrontOfHead = drill.getGUIBlock().getLocation().getBlock().getRelative(forward, 2);
		if(forward != BlockFace.DOWN)
		{
			blocksInfront.add(blockInfrontOfHead.getRelative(BlockFace.UP).getRelative(Drill.getBlockFaceLeft(forward)));
			blocksInfront.add(blockInfrontOfHead.getRelative(BlockFace.UP));
			blocksInfront.add(blockInfrontOfHead.getRelative(BlockFace.UP).getRelative(Drill.getBlockFaceRight(forward)));
			blocksInfront.add(blockInfrontOfHead.getRelative(Drill.getBlockFaceLeft(forward)));
			blocksInfront.add(blockInfrontOfHead);
			blocksInfront.add(blockInfrontOfHead.getRelative(Drill.getBlockFaceRight(forward)));
			blocksInfront.add(blockInfrontOfHead.getRelative(BlockFace.DOWN).getRelative(Drill.getBlockFaceLeft(forward)));
			blocksInfront.add(blockInfrontOfHead.getRelative(BlockFace.DOWN));
			blocksInfront.add(blockInfrontOfHead.getRelative(BlockFace.DOWN).getRelative(Drill.getBlockFaceRight(forward)));
		}
		else
		{
			blocksInfront.add(blockInfrontOfHead.getRelative(BlockFace.NORTH));
			blocksInfront.add(blockInfrontOfHead.getRelative(BlockFace.NORTH).getRelative(BlockFace.WEST));
			blocksInfront.add(blockInfrontOfHead.getRelative(BlockFace.NORTH).getRelative(BlockFace.EAST));
			blocksInfront.add(blockInfrontOfHead.getRelative(BlockFace.WEST));
			blocksInfront.add(blockInfrontOfHead);
			blocksInfront.add(blockInfrontOfHead.getRelative(BlockFace.EAST));
			blocksInfront.add(blockInfrontOfHead.getRelative(BlockFace.SOUTH));
			blocksInfront.add(blockInfrontOfHead.getRelative(BlockFace.SOUTH).getRelative(BlockFace.WEST));
			blocksInfront.add(blockInfrontOfHead.getRelative(BlockFace.SOUTH).getRelative(BlockFace.EAST));
		}

		return blocksInfront;
	}

	public boolean attemptDrill(Block block, Player player)
	{
		if(!EventSimulator.blockBreakPretend(block.getLocation(), player))
		{
			player.sendMessage(ChatColor.RED + "The drill can't break those blocks!");
			return false;
		}

		return true;
	}

	@EventHandler(priority = EventPriority.HIGHEST)
	public void blockBreak(BlockBreakEvent event)
	{
		if(event == EventSimulator.pretendEvent)
		{
			EventSimulator.pretendEventCancelled = event.isCancelled();
			EventSimulator.pretendEvent = null;
			event.setCancelled(true);
		}
	}
}
