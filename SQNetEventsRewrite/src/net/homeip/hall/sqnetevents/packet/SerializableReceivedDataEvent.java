package net.homeip.hall.sqnetevents.packet;

import org.bukkit.event.Event;

public class SerializableReceivedDataEvent implements SerializableEvent {
	
	private static final long serialVersionUID = 1L;

    private final Data data;

    public SerializableReceivedDataEvent(ReceivedDataEvent event) {
        data = event.getData();
    }
    //Returns the data object associated with this event
    @Override
	public Event getEvent() {
        return new ReceivedDataEvent(data);
    }
}