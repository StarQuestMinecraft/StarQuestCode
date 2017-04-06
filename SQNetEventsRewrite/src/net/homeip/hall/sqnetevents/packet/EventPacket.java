package net.homeip.hall.sqnetevents.packet;

import org.bukkit.event.Event;

import net.homeip.hall.sqnetevents.SQNetEvents;

public class EventPacket extends IOPacket {

	private static final long serialVersionUID = 1L;
	
	private final SerializableEvent sendEvent;

    //Called externally
    public EventPacket(SerializableEvent sendEvent, String destination, String origin) {
    	super(destination, origin);
    	this.sendEvent = sendEvent;
    }
    //gets the event
    public Event getSendEvent() {
        return sendEvent.getEvent();
    }
    //fires the event
    @Override
	public void handle() {
    	System.out.println("[NetEvents] Firing event...");
        SQNetEvents.getInstance().getServer().getPluginManager().callEvent(this.getSendEvent());
    }
    //don't know why you would ever use this
    @Override
    public String toString() {
        return "EventPacket{" +
                "uid=" + getUid() +
                ", sendEvent=" + getSendEvent() +
                '}';
    }
}