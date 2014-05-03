package com.dibujaron.alliances;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

public class EventListener implements Listener{
	
	@EventHandler
	public void onPlayerChat(AsyncPlayerChatEvent event){
		boolean isAlliance = false;
		try{
		Core.lock.acquire();
		isAlliance = Core.playersAllianceChat.contains(event.getPlayer());
		Core.lock.release();
		}catch(Exception e){
			e.printStackTrace();
		}
		if(isAlliance){
			BungeeMessager.sendChat(event.getPlayer().getName(), event.getMessage());
			Core.chat(event.getPlayer().getName(), event.getMessage());
			event.setCancelled(true);
		}
	}
}
