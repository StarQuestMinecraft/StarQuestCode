package com.minecraftdimensions.bungeesuitechat.listeners;


import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import com.minecraftdimensions.bungeesuitechat.managers.PlayerManager;
import com.minecraftdimensions.bungeesuitechat.objects.BSPlayer;

public class AFKListener implements Listener {

	@EventHandler
	public void playerCommand(PlayerCommandPreprocessEvent e) {
		BSPlayer p =PlayerManager.getPlayer(e.getPlayer());
		if(p==null){
			return;
		}
		if(p.isAFK() && e.getMessage().equalsIgnoreCase("/afk") || e.getMessage().equalsIgnoreCase("/away") || e.getMessage().equalsIgnoreCase("/brb")){
			PlayerManager.setPlayerAFK(e.getPlayer());
			e.setCancelled(true);
		}
	}
	
	@EventHandler
	public void playerChat(AsyncPlayerChatEvent e) {
		BSPlayer p =PlayerManager.getPlayer(e.getPlayer());
		if(p==null){
			return;
		}
		if(p.isAFK()){
			PlayerManager.setPlayerAFK(e.getPlayer());
		}
	}
	
	@EventHandler
	public void playerMove(PlayerMoveEvent e) {
		if(!e.getTo().getBlock().equals(e.getFrom().getBlock())){
			BSPlayer p =PlayerManager.getPlayer(e.getPlayer());
			if(p==null){
				return;
			}
			if(p.isAFK()){
				PlayerManager.setPlayerAFK(e.getPlayer());
			}
		}
	}
	
    @EventHandler( priority = EventPriority.LOWEST )
	public void playerMove(PlayerQuitEvent e) {
    		BSPlayer p =PlayerManager.getPlayer(e.getPlayer());
    		if(p==null){
    			return;
    		}
			if(p.isAFK()){
				PlayerManager.setPlayerAFK(e.getPlayer());
			}
		}
	}

