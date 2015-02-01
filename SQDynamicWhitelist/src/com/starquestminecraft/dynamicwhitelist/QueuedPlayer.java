package com.starquestminecraft.dynamicwhitelist;

import java.util.UUID;

import net.md_5.bungee.api.config.ServerInfo;

public class QueuedPlayer {
	UUID u;
	ServerInfo reconnect;
	String name;
	
	public QueuedPlayer(String name, UUID u, ServerInfo reconnect){
		this.u = u;
		this.reconnect = reconnect;
		this.name = name;
	}
	
	public UUID getUniqueId(){
		return u;
	}
	
	public ServerInfo getReconnect(){
		return reconnect;
	}
	
	public String getName(){
		return name;
	}
}
