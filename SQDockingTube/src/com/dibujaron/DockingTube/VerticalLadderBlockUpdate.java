package com.dibujaron.DockingTube;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPhysicsEvent;
import org.bukkit.material.Ladder;

public class VerticalLadderBlockUpdate implements Listener
{
	// Allows ladders to be set on glass
	@EventHandler(priority = EventPriority.LOW) 
	public void onBlockBreak(BlockPhysicsEvent event)
	{	
		if(event.getBlock().getType() != Material.LADDER || event.isCancelled())
			return;
		
		Ladder lad = (Ladder) event.getBlock().getState().getData();
		Block ladderBack = event.getBlock().getRelative(lad.getAttachedFace());
		
		if(ladderBack.getType() == Material.GLASS || ladderBack.getType() == Material.STAINED_GLASS)
			event.setCancelled(true);
	}
}
