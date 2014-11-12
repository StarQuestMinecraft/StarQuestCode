package com.dibujaron.messenger.client;

import java.io.Serializable;
import java.sql.ResultSet;
import java.util.HashMap;

import com.dibujaron.messenger.envelope.*;
import com.mysql.jdbc.PreparedStatement;

public class PostOffice {
	
	//Post Office is the central distribution point of a server's mail system
	//messages can be sent from the post office methods or the Mailbox methods.
	//messages can only be recieved at mailboxes
	//queries can only go out from mailboxes
	
	private static HashMap<String, Mailbox> addresses = new HashMap<String, Mailbox>();
	private static HashMap<Integer, ResultSetRunnable> resultSetMap = new HashMap<Integer, ResultSetRunnable>();
	private static int nextQueryId = 0;
	
	public static void sendMessage(String target, String channel, Serializable message){
		Envelope e = new Envelope(target, channel, message);
		Core.getInstance().getConnectionHandler().send(e);
		//TODO send message out
	}
	
	static void sendQuery(Mailbox sender, PreparedStatement query, ResultSetRunnable runnable){
		QueryBox box = new QueryBox(query, nextQueryId++);
		sendMessage("CENTRAL", "SQL", box);
		resultSetMap.put(box.getId(), runnable);
	}
	
	public static void registerChannelMailbox(Mailbox mailbox){
		addresses.put(mailbox.getChannel(), mailbox);
	}
	
	protected static void distributeMessage(Envelope e){
		
		if(e.getChannel().equals("SQL")){
			processIncomingQuery((ResultBox) e.getMessage());
		} else {
			Mailbox m = addresses.get(e.getChannel());
			m.recieveMessage(e.getMessage());
		}
	}

	private static void processIncomingQuery(ResultBox message) {
		ResultSet set = message.getResultSet();
		int id = message.getId();
		ResultSetRunnable r = resultSetMap.get(id);
		r.run(set);
		resultSetMap.remove(id);
	}
}
