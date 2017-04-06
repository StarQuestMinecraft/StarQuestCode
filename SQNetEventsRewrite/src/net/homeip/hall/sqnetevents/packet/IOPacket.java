package net.homeip.hall.sqnetevents.packet;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.nio.ByteBuffer;
import java.util.UUID;

public abstract class IOPacket implements Packet {
	private static final long serialVersionUID = 1L;
	private final UUID uid;
    private final String packetDestination;
    private final String packetOrigin;
    
    private IOPacket(UUID uid, String destination, String origin) {
    	this.uid = uid;
    	this.packetDestination = destination;
    	this.packetOrigin = origin;
    }
    
    public IOPacket(String destination, String origin) {
    	this(UUID.randomUUID(), destination, origin);
    }
    
	@Override
	public abstract void handle() throws IOException;

    //returns a bytebuffer representation of the object
	@Override
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
    //returns a packet read from the given bytebuffer
	public static Packet read(ByteBuffer buffer) throws IOException {
		buffer.position(0);
		System.out.println("[SQNetEvents] Remaining: " + buffer.remaining());
		ByteArrayInputStream bais = new ByteArrayInputStream(buffer.array(), buffer.arrayOffset(), buffer.remaining());
		ObjectInputStream ois = new ObjectInputStream(bais);
		Packet packet = null;
        try {
        	packet = (Packet) ois.readObject();
        }
        catch(Exception e) {
        	throw new IOException("Read object is not an implementation of Packet");
        }
        ois.close();
        return packet;
	}
	
	  //returns the uid of this packet
    public UUID getUid() {
        return uid;
    }
    //returns the destination server of this packet
    public String getPacketDestination() {
    	return packetDestination;
    }
    //returns the origin server of this packet
    public String getPacketOrigin() {
    	return packetOrigin;
    }
}
