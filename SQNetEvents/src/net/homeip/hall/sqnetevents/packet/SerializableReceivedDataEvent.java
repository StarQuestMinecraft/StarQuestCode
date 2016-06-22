package net.homeip.hall.sqnetevents.packet;

import java.io.Serializable;

public class SerializableReceivedDataEvent implements Serializable {
	
	private static final long serialVersionUID = 1L;

    private final Data data;

    public SerializableReceivedDataEvent(ReceivedDataEvent event) {
        data = event.getData();
    }
    
    public ReceivedDataEvent toReceivedDataEvent() {
    	return new ReceivedDataEvent(getData());
    }
    //Returns the data object associated with this event
    public Data getData() {
        return data;
    }
}
