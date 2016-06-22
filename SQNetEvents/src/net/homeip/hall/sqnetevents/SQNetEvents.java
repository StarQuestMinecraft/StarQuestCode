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
	
	private HashMap<String, Sender> senders;
	
	private boolean isHub;
	
	@Override
	public void onEnable() {
		setInstance(this);
		saveDefaultConfig();
		String hubString = getConfig().getString("IsHub");
		boolean hub = Boolean.parseBoolean(hubString);
		setIsHub(hub);
		senders = new HashMap<String, Sender>();
		//server name and receiver address, separated by an @
		String nameAndListenAddress = getConfig().getString("ListenAt");
		String[] nala = nameAndListenAddress.split("@");
		String listenAddress = nala[1];
		String sName = nala[0];
		setServerName(sName);
		setUUID(new UUID(Long.parseLong(getConfig().getString("ServerID")), Long.parseLong(getConfig().getString("ServerID"))));
		setReceiver(new Receiver(listenAddress));
		//client name and address, separated by an @
		List<String> namesAndAddresses = getConfig().getStringList("SendTo");
		//splits the name and address
		for(String nameAndAddress : namesAndAddresses) {
			String[] naa = nameAndAddress.split("@");
			String name = naa[0];
			System.out.println(name);
			String address = naa[1];
			System.out.println("[NetEvents] Connect address: " + address);
			Sender sender = new Sender(address);
			//puts sender in hashmap with name as key
			addSender(sender, name);
		}
	}
	//closes all connections
	@Override
	public void onDisable() {
		try {
			getReceiver().close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		for(Sender sender: getSenders().values()) {
			try {
				sender.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	//sends packet to all client servers, not recommended
	public void send(Packet packet) {
		for(Sender sender : getSenders().values()) {
			sender.send(packet);
		}
	}
	//sends packet to a specific client server, or handles the packet if it's local
	public void send(Packet packet, String serverName) {
		//if local
		if(serverName.equals(getServerName())) {
			try {
				packet.handle();
			} catch (IOException e) {
				e.printStackTrace();
			}
			return;
		}
		System.out.println(serverName);
		System.out.println(getServerName());
		System.out.println(getSenders().get(serverName));
		System.out.println(packet);
		getSenders().get(serverName).send(packet);
	}
	//address to bind and listen on
	public Receiver getReceiver() {
		return receiver;
	}
	public void setReceiver(Receiver aReceiver) {
		receiver = aReceiver;
	}
	//addresses to send data to
	public HashMap<String, Sender> getSenders() {
		return senders;
	}
	public void addSender(Sender sender, String name) {
		senders.put(name, sender);
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
}
