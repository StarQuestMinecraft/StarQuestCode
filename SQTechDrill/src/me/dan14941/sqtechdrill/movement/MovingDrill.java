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
import org.bukkit.scheduler.BukkitTask;

import com.starquestminecraft.sqtechbase.SQTechBase;
import com.starquestminecraft.sqtechbase.objects.Machine;

import me.dan14941.sqtechdrill.Drill;
import me.dan14941.sqtechdrill.EventSimulator;
import me.dan14941.sqtechdrill.SQTechDrill;
import me.dan14941.sqtechdrill.task.DrillMoveRunnable;

public class MovingDrill implements Listener
{
	private BukkitTask run;
	public SQTechDrill main;
	
	public MovingDrill(SQTechDrill mainPlugin)
	{
		this.main = mainPlugin;
		main.getServer().getPluginManager().registerEvents(this, mainPlugin);
	}

	public void activateDrill(Machine drill, Player player)
	{
		drill.data.put("isActive", true);
		this.startDrillMovement(drill, player);
	}
	
	public void deactivateDrill(final Machine drill, Player player)
	{
		drill.data.put("isActive", false);
	}

	private boolean startDrillMovement(Machine drill, Player player)
	{
		List<Block> frontBlocks = getBlocksInFront(drill);
		if(frontBlocks == null)
		{
			player.sendMessage(ChatColor.RED + "The drill could not start!");
			return false;
		}

		for (Block block : frontBlocks)
		{
			if(this.attemptDrill(block, player) == false)
			{
				drill.data.put("isActive", false);
				return false;
			}
		}

		if((boolean) drill.data.get("isActive") == false)
			return false;

		//this.moveDrill(Drill.getDrillForward(drill.getGUIBlock().getLocation().getBlock()), drill);
		this.startDrillMovementRuning(drill, player);
		return true;
	}
	
	private void startDrillMovementRuning(final Machine drill, final Player player)
	{
        final BlockFace forward = Drill.getDrillForward(drill.getGUIBlock().getLocation().getBlock());
        this.run = new DrillMoveRunnable(drill, forward, this, player, main).runTask(this.main);
    }
	
	public void restartDrillMove(final int delay, final Machine drill, final Player player)
	{
		List<Block> frontBlocks = getBlocksInFront(drill);
		for (Block block : frontBlocks)
		{
			if(this.attemptDrill(block, player) == false)
			{
				drill.data.put("isActive", false);
				return;
			}
		}
        final BlockFace forward = Drill.getDrillForward(drill.getGUIBlock().getLocation().getBlock());
        this.run.cancel();
        this.run = new DrillMoveRunnable(drill, forward, this, player, main).runTaskLater(this.main, (long)delay);
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

	private boolean attemptDrill(Block block, Player player)
	{
		if(!EventSimulator.blockBreakPretend(block.getLocation(), player))
		{
			player.sendMessage(ChatColor.RED + "The drill can't break those blocks!");
			return false;
		}

		return true;
	}

	/**
	 * Moves the drill one block forward in the direction set. Any blocks in the way will get replaced.
	 * @param forward
	 * @param drill
	 * @return
	 */
	public static boolean moveDrill(BlockFace forward, Machine drill)
	{
		List<Block> frontBlocks = getBlocksInFront(drill);
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
				drill.data.put("isActive", false);
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
