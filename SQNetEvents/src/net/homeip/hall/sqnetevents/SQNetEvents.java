package net.homeip.hall.sqnetevents;

import org.bukkit.plugin.java.JavaPlugin;

import net.homeip.hall.sqnetevents.networking.Receiver;
import net.homeip.hall.sqnetevents.networking.Sender;
import net.homeip.hall.sqnetevents.packet.Packet;

import java.util.List;
import java.util.UUID;
import java.io.IOException;
import java.util.HashMap;

public class SQNetEvents extends JavaPlugin {
	
	private static SQNetEvents instance;
	
	private Receiver receiver;
	
	private String serverName;
	
	private UUID uuid;
	
	private HashMap<String, Sender> clients;
	
	private boolean isHub;
	
	private Sender hub;
	
	@Override
	public void onEnable() {
		setInstance(this);
		saveDefaultConfig();
		String hubString = getConfig().getString("IsHub");
		clients = new HashMap<String, Sender>();
		//server name and receiver address, separated by an @
		String nameAndListenAddress = getConfig().getString("ListenAt");
		String[] nala = nameAndListenAddress.split("@");
		String listenAddress = nala[1];
		String sName = nala[0];
		setServerName(sName);
		setUUID(new UUID(Long.parseLong(getConfig().getString("ServerID")), Long.parseLong(getConfig().getString("ServerID"))));
		setReceiver(new Receiver(listenAddress));
		boolean hubBoolean = Boolean.parseBoolean(hubString);
		setIsHub(hubBoolean);
		if(!isHub()) {
			String hubLocation = getConfig().getString("Hub");
			String[] nameAndAddress= hubLocation.split("@");
			System.out.println(nameAndAddress[1]);
			setHub(new Sender(nameAndAddress[1]));
		}
		else {
			//client name and address, separated by an @
			List<String> namesAndAddresses = getConfig().getStringList("Clients");
			//splits the name and address
			for(String nameAndAddress : namesAndAddresses) {
				String[] naa = nameAndAddress.split("@");
				String name = naa[0];
				System.out.println(name);
				String address = naa[1];
				System.out.println("[NetEvents] Connect address: " + address);
				Sender client = new Sender(address);
				//puts sender in hashmap with name as key
				addClient(client, name);
			}
		}
	}
	//closes all connections
	@Override
	public void onDisable() {
		try {
			getReceiver().close();
			getHub().close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		for(Sender sender: getClients().values()) {
			try {
				sender.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	//sends packet to all client servers, not recommended
	public void send(Packet packet) {
		for(Sender sender : getClients().values()) {
			sender.send(packet);
		}
	}
	//sends packet to a specific client server, or handles the packet if it's local
	public void send(Packet packet, String destination) {
		//if local
		System.out.println("Packet is null: " + packet == null);
		if(destination.equals(getServerName())) {
			try {
				packet.handle();
			} catch (IOException e) {
				e.printStackTrace();
			}
			return;
		}
		else if(isHub()) {
			System.out.println("Destination name: " + destination);
			System.out.println("Server name: " + getServerName());
			System.out.println("getClients().get(destination): " + getClients().get(destination));
			getClients().get(destination).send(packet);
		}
		else {
			getHub().send(packet);
		}
	}
	//address to bind and listen on
	public Receiver getReceiver() {
		return receiver;
	}
	public void setReceiver(Receiver aReceiver) {
		receiver = aReceiver;
	}
	//addresses to send data to
	public HashMap<String, Sender> getClients() {
		return clients;
	}
	public void addClient(Sender client, String name) {
		clients.put(name, client);
	}
	//gets the identifier for this server
	public String getServerName() {
		return serverName;
	}
	public void setServerName(String name) {
		serverName = name;
	}
	public static SQNetEvents getInstance() {
		return instance;
	}
	public static void setInstance(SQNetEvents anInstance) {
		instance = anInstance;
	}
	public UUID getUUID() {
		return uuid;
	}
	public void setUUID(UUID uuid) {
		this.uuid = uuid;
	}
	public boolean isHub() {
		return isHub;
	}
	public void setIsHub(boolean hub) {
		isHub = hub;
	}
	public Sender getHub() {
		return hub;
	}
	public void setHub(Sender hub) {
		this.hub = hub;
	}
}
