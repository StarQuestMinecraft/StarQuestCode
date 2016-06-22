package net.homeip.hall.sqnetevents.packet;

import java.io.Serializable;

import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class ReceivedDataEvent extends Event implements Serializable {
	
	private static final long serialVersionUID = 1L;

	private static final HandlerList handlers = new HandlerList();

    private final Data data;

    public ReceivedDataEvent(Data data) {
        this.data = data;
    }
    //Returns the data object associated with this event
    public Data getData() {
        return data;
    }
    public HandlerList getHandlers() {
        return handlers;
    }

    public static HandlerList getHandlerList() {
        return handlers;
    }

}