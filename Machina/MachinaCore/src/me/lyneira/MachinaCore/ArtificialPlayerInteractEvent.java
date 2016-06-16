package me.lyneira.MachinaCore;

import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Player;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

public class ArtificialPlayerInteractEvent extends PlayerInteractEvent
{
	public ArtificialPlayerInteractEvent(Player who, Action action, ItemStack item, Block clickedBlock, BlockFace clickedFace)
	{
		super(who, action, item, clickedBlock, clickedFace);
	}
}
