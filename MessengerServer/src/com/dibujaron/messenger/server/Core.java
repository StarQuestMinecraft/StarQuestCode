package com.dibujaron.messenger.server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.concurrent.TimeUnit;

import net.md_5.bungee.api.plugin.Plugin;

public class Core extends Plugin {
	// TODO configuration
	static final int PORT = 6606;
	protected static ServerSocket socket;
	private static HashMap<String, Connection> connections = new HashMap<String, Connection>();
	protected static Core instance;
	@Override
	public void onEnable() {
		
		instance = this;
		
		try {
			socket = new ServerSocket(PORT);
		} catch (IOException e) {
			log("Failed to initialize Messenger with given port!");
			e.printStackTrace();
		}
		log("Messenger Server Enabled!");
		
		ConnectionAcceptor a = new ConnectionAcceptor();
		getProxy().getScheduler().runAsync(this, a);
		
		CentralPostOffice m = new CentralPostOffice();
		getProxy().getScheduler().runAsync(this, m);
	}
	
	public static Core getInstance(){
		return instance;
	}
	
	public static void log(String s){
		instance.getLogger().info(s);
	}
	
	public HashMap<String, Connection> getConnections(){
		return connections;
	}
}
