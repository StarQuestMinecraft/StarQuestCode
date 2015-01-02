package com.starquestminecraft.cryobounce;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.util.concurrent.TimeUnit;

import net.md_5.bungee.api.config.ServerInfo;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.connection.Server;
import net.md_5.bungee.api.event.PostLoginEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.api.plugin.Plugin;
import net.md_5.bungee.event.EventHandler;

public class CryoBounce extends Plugin implements Listener{
	
	@EventHandler
	public void onPostLogin(PostLoginEvent event){
		final ProxiedPlayer plr = event.getPlayer();
		
		getProxy().getScheduler().schedule(this, new Runnable(){
			public void run(){
				Server server = plr.getServer();
				if(server != null){
					ServerInfo info = server.getInfo();
					sendMessage(plr.getName(), info);
				}
			}
		}, 3, TimeUnit.SECONDS);
	}
	
	public void onEnable() {
        this.getProxy().registerChannel("cryoBounce");
        this.getProxy().getPluginManager().registerListener(this, this);
        System.out.println("CRYOBOUNCE ENABLED");
    }
 
    public void sendMessage(String message, ServerInfo server) {
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        DataOutputStream out = new DataOutputStream(stream);
        try{
        out.writeUTF(message);
        System.out.println("Sent Login!");
        } catch (Exception e){
        	e.printStackTrace();
        }
        System.out.println(server.getName());
        server.sendData("cryoBounce", stream.toByteArray());
        // Alright quick note here guys : ProxiedPlayer.sendData() [b]WILL NOT WORK[/b]. It will send it to the client, and not to the server the client is connected. See the difference ? You need to send to Server or ServerInfo.
    }

	
}
