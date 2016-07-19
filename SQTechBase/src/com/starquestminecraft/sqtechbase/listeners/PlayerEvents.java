package com.starquestminecraft.sqtechbase.listeners;

import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerKickEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import com.starquestminecraft.sqtechbase.SQTechBase;
import com.starquestminecraft.sqtechbase.database.DatabaseInterface;

public class PlayerEvents implements Listener {

	@EventHandler
	public void onPlayerJoin(final PlayerJoinEvent event) {
		
		Bukkit.getScheduler().runTaskAsynchronously(SQTechBase.getPluginMain(), new Runnable() {	
			
			public void run() {
				
				DatabaseInterface.readOptions(event.getPlayer());
				
			}
			
		});

	}
	
	@EventHandler
	public void onPlayerQuit(final PlayerQuitEvent event) {

		Bukkit.getScheduler().runTaskAsynchronously(SQTechBase.getPluginMain(), new Runnable() {
			
			public void run() {

				DatabaseInterface.updateOptions(event.getPlayer());
				
			}
			
		});
		
	}
	
	@EventHandler
	public void onPlayerKick(final PlayerKickEvent event) {

		DatabaseInterface.updateOptions(event.getPlayer());

	}
	
}
