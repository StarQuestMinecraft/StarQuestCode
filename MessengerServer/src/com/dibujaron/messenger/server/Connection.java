package com.dibujaron.messenger.server;

import java.io.*;
import java.net.Socket;

public class Connection {

	private String name;
	private DataInputStream din;
	private DataOutputStream dout;
	private ObjectInputStream oin;
	private ObjectOutputStream oout;

	public Connection(Socket s) {
		try {
			InputStream i = s.getInputStream();
			OutputStream o = s.getOutputStream();

			din = new DataInputStream(i);
			dout = new DataOutputStream(o);
			oin = new ObjectInputStream(i);
			oout = new ObjectOutputStream(o);

			name = din.readUTF();
			
			Core.log("Recieved new connection from server " + name + "!");
		} catch (Exception e) {
			Core.log("[Messenger] Could not initialize connection!");
		}
	}
	
	protected DataInputStream getDataInput(){
		return din;
	}
	
	protected ObjectInputStream getObjectInput(){
		return oin;
	}
	
	protected DataOutputStream getDataOutput(){
		return dout;
	}
	
	protected ObjectOutputStream getObjectOutput(){
		return oout;
	}
	
	public String getName(){
		return name;
	}
}
