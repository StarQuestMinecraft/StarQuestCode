package com.dibujaron.alliances;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.messaging.PluginMessageListener;

public class BungeeMessager implements PluginMessageListener {
	
	//keys
	public static final String CHAT = "c";
	public static final String RELATION_WISH_CHANGE = "rwc";
	

	public void onPluginMessageReceived(String channel, Player player, byte[] message) {
		if (!channel.equals("BungeeCord")) {
			return;
		}
		DataInputStream in = new DataInputStream(new ByteArrayInputStream(message));
		try {
			String subchannel = in.readUTF();
			if (subchannel.equals("allianceUpdate")) {
				short len = in.readShort();
				byte[] msgbytes = new byte[len];
				in.readFully(msgbytes);

				DataInputStream msgin = new DataInputStream(new ByteArrayInputStream(msgbytes));
				String type = msgin.readUTF();
				if(type.equals(CHAT)){
					String sender = msgin.readUTF();
					String chat = msgin.readUTF();
					Core.chat(sender, chat);
				}/* else if (type.equals(RELATION_WISH_CHANGE)) {
					String sender = msgin.readUTF();
					String target = msgin.readUTF();
					Relation r = Relation.stringToRelation(msgin.readUTF());
					Alliances.handleRelationChange(sender, target, r);
				}*/
			}
		} catch (Exception e) {
			e.printStackTrace();
			return;
		}
	}
	
	public static void sendChat(String sender, String message){
		String[] msgarr = {sender, message};
		sendMessage(CHAT, msgarr);
	}
	/*public static void sendRelationWishChange(String senderOrg, String target, Relation newWish){
		String[] msgarr = {senderOrg, target, Relation.relationToString(newWish)};
		sendMessage(RELATION_WISH_CHANGE, msgarr);
	}*/
	
	public static void sendMessage(String type, String[] message){
		try{
		ByteArrayOutputStream b = new ByteArrayOutputStream();
		DataOutputStream out = new DataOutputStream(b);
		out.writeUTF("Forward"); // So bungeecord knows to forward it
		out.writeUTF("ALL");
		out.writeUTF("allianceUpdate"); // The channel name to check if this your data
		ByteArrayOutputStream msgbytes = new ByteArrayOutputStream();
		DataOutputStream msgout = new DataOutputStream(msgbytes);
		
		msgout.writeUTF(type);
		
		for(String s : message){
			msgout.writeUTF(s);
		}
		byte[] outmsg = msgbytes.toByteArray();
		out.writeShort(outmsg.length);
		out.write(outmsg);
		Player p = Bukkit.getOnlinePlayers()[0];
		p.sendPluginMessage(Core.getInstance(), "BungeeCord", b.toByteArray());
		} catch (Exception e){
			e.printStackTrace();
		}
	}
}
