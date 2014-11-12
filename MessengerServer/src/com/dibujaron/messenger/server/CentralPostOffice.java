package com.dibujaron.messenger.server;

import java.io.IOException;
import java.sql.ResultSet;

import com.dibujaron.messenger.envelope.Envelope;
import com.dibujaron.messenger.envelope.QueryBox;
import com.dibujaron.messenger.envelope.ResultBox;
import com.mysql.jdbc.PreparedStatement;

public class CentralPostOffice implements Runnable{
	@Override
	public void run() {
		while(true){
			try {
				Thread.sleep(50);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			
			
			for(Connection c : Core.getInstance().getConnections().values()){
				boolean done = false;
				while(!done){
					try {
						Object o = c.getObjectInput().readObject();
						if(o != null){
							Envelope e = (Envelope) o;
							processIncoming(e, c);
						} else {
							done = true;
						}
					} catch (IOException e){
						done = true;
					} catch (ClassNotFoundException e) {
						//this will never happen
						e.printStackTrace();
					}
				}
			}
			
			
		}
	}
	
	private void processIncoming(Envelope e, Connection from){
		String target = e.getTarget();
		if(target == "CENTRAL"){
			//it's an SQL query
			processIncomingQuery(e, from);
		} else {
			Connection c = Core.getInstance().getConnections().get(target);
			if(c != null){
				//forward the message to its intended target
				sendEnvelopeTo(e, c);
			} else {
				System.out.println("ERROR: invalid envelope target!");
			}
		}
	}
	
	private void sendEnvelopeTo(Envelope e, Connection c){
		try {
			c.getObjectOutput().writeObject(e);
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}
	
	private void processIncomingQuery(Envelope e, Connection from){
		QueryBox qbox = (QueryBox) e.getMessage();
		PreparedStatement statement = qbox.getQuery();
		ResultSet set = DatabaseHandler.executeQuery(statement);
		if(set != null){
			//send back an envelope with the set
			ResultBox rbox = new ResultBox(set, qbox.getId());
			Envelope outbound = new Envelope(from.getName(), e.getChannel(), rbox);
			sendEnvelopeTo(outbound, from);
		}
	}
}
