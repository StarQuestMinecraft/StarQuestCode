package com.ginger_walnut.sqsmoothcraft.events;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

import net.minecraft.server.v1_10_R1.EntityHuman;

public class ShipExitEvent extends Event{

	private static final HandlerList handlers = new HandlerList();
	
	private boolean cancelled;
	public EntityHuman player;
	
	public ShipExitEvent(EntityHuman player) {
		
		this.player = player;
		
	}
	
	public static HandlerList getHandlerList() {
		
		return handlers;
		
	}
	
	public HandlerList getHandlers() {
		
		return handlers;
		
	}
	
	public boolean isCancelled() {
		
		return cancelled;
		
	}
	
	public void setCancelled(boolean cancelled) {
		
		this.cancelled = cancelled;
		
	}
	
	
	
}
