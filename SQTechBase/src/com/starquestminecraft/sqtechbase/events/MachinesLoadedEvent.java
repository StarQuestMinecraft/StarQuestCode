package com.starquestminecraft.sqtechbase.events;

import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class MachinesLoadedEvent extends Event{

	private static final HandlerList handlers = new HandlerList();
	
	public MachinesLoadedEvent() {
		
	}
	
	public static HandlerList getHandlerList() {
		
		return handlers;
		
	}
	
	public HandlerList getHandlers() {
		
		return handlers;
		
	}
	
}
