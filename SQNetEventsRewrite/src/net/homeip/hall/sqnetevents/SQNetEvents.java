package net.homeip.hall.sqnetevents;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.SocketAddress;

import org.bukkit.plugin.java.JavaPlugin;

import net.homeip.hall.sqnetevents.networking.ConnectionHolder;

public class SQNetEvents extends JavaPlugin {
	
	private static SQNetEvents instance;
	
	private ConnectionHolder connectionHolder;
	
	@Override
	public void onEnable() {
		saveDefaultConfig();
		instance = this;
		String hub = getConfig().getString("Hub");
		System.out.println("Hub: " + hub);
		String name = getConfig().getString("Name");
		setConnectionHolder(ConnectionHolder.create(name));
		String[] addressAndPort = hub.split(":");
		System.out.println("address: " + addressAndPort[0]);
		System.out.println("port: " + addressAndPort[1]);
		//adds hub to connectionholder's map
		try {
			SocketAddress address = new InetSocketAddress(addressAndPort[0], Integer.parseInt(addressAndPort[1]));
			System.out.println("address: " + (address == null));
			getConnectionHolder().addConnection(address, "Hub", name);
		} catch (NumberFormatException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public void onDisable() {
		try {
			getConnectionHolder().closeAll();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	public static SQNetEvents getInstance() {
		return instance;
	}

	public ConnectionHolder getConnectionHolder() {
		return connectionHolder;
	}

	public void setConnectionHolder(ConnectionHolder connectionHolder) {
		this.connectionHolder = connectionHolder;
	}
	
}
