package net.homeip.hall.sqnetevents.packet;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.nio.ByteBuffer;

public class IOPacketWrapper implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private final String packetDestination;
	private final String packetOrigin;
	
	private byte[] bytes;
	
	public static IOPacketWrapper wrap(IOPacket packet) throws IOException {
		return new IOPacketWrapper(packet);
	}
	
	private IOPacketWrapper(IOPacket packet) throws IOException {
		this.packetDestination = packet.getPacketDestination();
		this.packetOrigin = packet.getPacketOrigin();
		bytes = packet.write().array();
	}
	
	public IOPacket unwrap() throws IOException {
		return (IOPacket) IOPacket.read(ByteBuffer.wrap(bytes));
	}
	
	public static IOPacketWrapper read(ByteBuffer buffer) throws IOException {
		ByteArrayInputStream bais = new ByteArrayInputStream(buffer.array(), buffer.arrayOffset(), buffer.remaining());
		ObjectInputStream ois = new ObjectInputStream(bais);
		try {
			IOPacketWrapper wrapper = (IOPacketWrapper) ois.readObject();
			return wrapper;
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		return null;
	}
	
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
	public String getPacketDestination() {
		return packetDestination;
	}
	
	public String getPacketOrigin() {
		return packetOrigin;
	}
}
