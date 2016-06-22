package net.homeip.hall.sqnetevents.packet;

import net.homeip.hall.sqnetevents.SQNetEvents;
import org.bukkit.event.Event;

import java.io.*;
import java.nio.ByteBuffer;
import java.util.UUID;

public class EventPacket implements Packet {
	private static final long serialVersionUID = 1L;
	private final UUID uid;
    private final SerializableReceivedDataEvent sendEvent;

    public EventPacket(UUID uid, ReceivedDataEvent sendEvent) {
        this.uid = uid;
        this.sendEvent = new SerializableReceivedDataEvent(sendEvent);
    }
    //Called externally
    public EventPacket(ReceivedDataEvent sendEvent) {
        this(UUID.randomUUID(), sendEvent);
    }
    //returns the uid of this eventpacket
    public UUID getUid() {
        return uid;
    }
    //gets the event
    public ReceivedDataEvent getSendEvent() {
        return sendEvent.toReceivedDataEvent();
    }
    //returns object read from bytebuffer
    public static EventPacket read(ByteBuffer byteBuffer) throws IOException {
    	byteBuffer.position(0);
        ObjectInputStream ois = new ObjectInputStream(new ByteArrayInputStream(byteBuffer.array(), byteBuffer.arrayOffset(), byteBuffer.remaining()));
        EventPacket eventPacket;
        try {
        	eventPacket = (EventPacket) ois.readObject();
        }
        catch(Exception e) {
        	throw new IOException("Read object is not an instance of EventPacket");
        }
        ois.close();
        return eventPacket;
    }
    //fires the event
    @Override
    public void handle() {
    	System.out.println("[NetEvents] Firing event...");
        SQNetEvents.getInstance().getServer().getPluginManager().callEvent(this.getSendEvent());
    }
    //returns a bytebuffer representation of the object
    public ByteBuffer write() throws IOException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ObjectOutputStream oos = new ObjectOutputStream(baos);
        oos.writeObject(this);
        oos.flush();
        oos.close();
        ByteBuffer buf = ByteBuffer.wrap(baos.toByteArray());
        buf.position(0);
        return buf;
    }
    //don't know why you would ever use this
    @Override
    public String toString() {
        return "EventPacket{" +
                "uid=" + uid +
                ", sendEvent=" + getSendEvent() +
                '}';
    }
}
