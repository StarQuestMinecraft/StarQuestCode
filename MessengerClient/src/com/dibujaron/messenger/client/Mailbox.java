package com.dibujaron.messenger.client;

import java.io.Serializable;

import com.mysql.jdbc.PreparedStatement;

public abstract class Mailbox {
	
	private String channel;
	
	//Mailboxes are implemented by plugins to serve as locations to send and recieve messages
	
	public Mailbox(String channel){
		this.channel = channel;
	}
	public abstract void recieveMessage(Serializable message);
	
	public void sendMessage(String target, Serializable message){
		PostOffice.sendMessage(target, getChannel(), message);
	}
	
	public void sendQuery(PreparedStatement query, ResultSetRunnable runnable){
		PostOffice.sendQuery(this, query, runnable);
	}
	
	public String getChannel(){
		return channel;
	}
}
