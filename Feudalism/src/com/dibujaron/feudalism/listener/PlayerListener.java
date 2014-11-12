package com.dibujaron.feudalism.listener;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.plugin.java.JavaPlugin;

import com.dibujaron.feudalism.player.FPlayer;

public class PlayerListener implements Listener{
	
	public PlayerListener(JavaPlugin p){
		p.getServer().getPluginManager().registerEvents(this, p);
	}
	
	@EventHandler
	public void onPlayerJoin(PlayerJoinEvent event){
		FPlayer p = new FPlayer(event.getPlayer());
		FPlayer.map(event.getPlayer().getUniqueId(), p);
	}
	
	@EventHandler
	public void onPlayerQuit(PlayerQuitEvent event){
		FPlayer.removeMapping(event.getPlayer().getUniqueId());
	}
	@EventHandler
	public void onPlayerMove(PlayerMoveEvent e){
	}
}
