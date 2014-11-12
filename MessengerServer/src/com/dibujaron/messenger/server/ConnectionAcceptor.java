package com.dibujaron.messenger.server;

import java.net.Socket;

public class ConnectionAcceptor implements Runnable{
	//waits for connections and adds them to Core.
	
	public void run(){
		while(true){
			try{
			Socket client = null;
			client = Core.socket.accept();
			if(client != null){
				Connection c = new Connection(client);
				Core.getInstance().getConnections().put(c.getName(), c);
				System.out.println("Accepted a new connection.");
			}
			} catch (Exception e){
				Core.log("Exception in client acceptor!");
			}
		}
	}
}
