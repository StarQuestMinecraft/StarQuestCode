package com.dibujaron.messenger.envelope;

import java.io.Serializable;

public class Envelope implements Serializable{

	private static final long serialVersionUID = 1L;
	
	private String target;
	private String channel;
	private Serializable message;
	
	public Envelope(String target, String channel, Serializable message){
		this.target = target;
		this.channel = channel;
		this.message = message;
	}
	
	public String getTarget(){
		return target;
	}
	
	public String getChannel(){
		return channel;
	}
	
	public Serializable getMessage(){
		return message;
	}
}
