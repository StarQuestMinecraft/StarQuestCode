package com.ginger_walnut.sqexpboost;


import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.metadata.FixedMetadataValue;

public class Events implements Listener{	

	@EventHandler
	public void onPlayerJoin (PlayerJoinEvent event) {
		
		Player player = event.getPlayer();
		
		player.sendMessage(ChatColor.GOLD + "ExpBoost: The experience multiplier is currently " + Main.expBoost);
		player.setMetadata("lastExp", new FixedMetadataValue(Main.getPluginMain(), player.getTotalExperience()));
		
	}
	
}
