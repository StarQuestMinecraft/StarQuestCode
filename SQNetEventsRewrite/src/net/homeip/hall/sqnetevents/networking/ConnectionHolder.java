package net.homeip.hall.sqnetevents.networking;

import java.io.IOException;
import java.net.SocketAddress;
import java.util.HashMap;
import java.util.Map;

import net.homeip.hall.sqnetevents.packet.IOPacketWrapper;
import net.homeip.hall.sqnetevents.packet.Packet;

public class ConnectionHolder {
	
	private static ConnectionHolder instance;
	
	private String name;
	
	private Map<String, Connection> connectionMap;
	
	public static ConnectionHolder create(String name) {
		if(instance == null) {
			instance = new ConnectionHolder(name);
		}
		System.out.println("[SQNetEvents] Passing ConnectionHolder singleton");
		return instance;
	}
	
	public static ConnectionHolder getInstance() {
		return instance;
	}
	public void addConnection(SocketAddress address, String remoteName, String localName) throws IOException {
		addConnection(new Connection(address, remoteName, localName), remoteName);
	}
	
	public void addConnection(Connection connection, String remoteName) throws IOException {
		System.out.println("[SQNetEvents] Adding new Connection to map");
		connectionMap.put(remoteName, connection);
	}
	
	public void send(Packet packet, String destination) throws IOException {
		System.out.println("destination: " + destination);
		if(destination.equals(getName()) && packet != null) {
			packet.handle();
		}
		else if(connectionMap.containsKey(destination)) {
			connectionMap.get(destination).sendPacket(packet);
		}
	}
	public void send(IOPacketWrapper wrapper, String destination) throws IOException {
		if(connectionMap.containsKey(destination)) {
			connectionMap.get(destination).sendPacket(wrapper);
		}
	}
	private ConnectionHolder(String name) {
		this.connectionMap = new HashMap<String, Connection>();
		this.name = name;
	}
	
	public void closeAll() throws IOException {
		for(Connection connection : connectionMap.values()) {
			connection.close();
		}
	}
	
	public String getName() {
		return name;
	}
}
