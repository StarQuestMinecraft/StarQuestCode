package com.starquestminecraft.greeter;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.util.concurrent.TimeUnit;

import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.config.ServerInfo;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.connection.Server;
import net.md_5.bungee.api.event.PostLoginEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.api.plugin.Plugin;
import net.md_5.bungee.event.EventHandler;

public class CryoBounce extends Plugin implements Listener{
	
	public static void callCryoMessage(final ProxiedPlayer plr, int iteration){
		
		Server server = plr.getServer();
		if(server != null){
			ServerInfo info = server.getInfo();
			sendMessage(plr.getName(), info);
		} else if(iteration < 15){
			final int itr2 = iteration + 1;
			Greeter.getInstance().getProxy().getScheduler().schedule(Greeter.getInstance(), new Runnable(){
				public void run(){
					callCryoMessage(plr, itr2);
				}
			}, 1, TimeUnit.SECONDS);
		} else {
			plr.sendMessage(createMessage("Took more than 15 seconds to connect to server, active cryopod message failed!"));
			return;
		}
	}
	
	public static void fakeCryopodLogin(final ProxiedPlayer plr, ServerInfo target){
		sendMessage(plr.getName(), target);
	}
 
    public static void sendMessage(String message, ServerInfo server) {
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        DataOutputStream out = new DataOutputStream(stream);
        try{
        out.writeUTF(message);
        } catch (Exception e){
        	e.printStackTrace();
        }
        server.sendData("cryoBounce", stream.toByteArray());
        // Alright quick note here guys : ProxiedPlayer.sendData() [b]WILL NOT WORK[/b]. It will send it to the client, and not to the server the client is connected. See the difference ? You need to send to Server or ServerInfo.
    }

    private static BaseComponent[] createMessage(String s){
		return new ComponentBuilder(s).create();
	}
}
