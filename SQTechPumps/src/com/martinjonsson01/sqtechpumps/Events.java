package com.martinjonsson01.sqtechpumps;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockFromToEvent;

public class Events implements Listener{
	
	@EventHandler
	public void onLiquidFlow(BlockFromToEvent e) {
		
		if (SQTechPumps.waterBlocks != null) {
			
			if (SQTechPumps.waterBlocks.contains(e.getBlock())) {
				
				e.setCancelled(true);
				
			}
			
		}
		
	}
	
}
