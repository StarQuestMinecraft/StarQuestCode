package com.martinjonsson01.sqsmoothcraft.missile;

import org.bukkit.ChatColor;
import org.bukkit.block.Sign;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;

public class MissileListener implements Listener {
	
	@EventHandler
	public void onPlayerInteract(PlayerInteractEvent e) {
		
		if (e.getClickedBlock().getState() instanceof Sign) {
			
			Sign s = (Sign) e.getClickedBlock().getState();
			if (s.getLine(0).equalsIgnoreCase("[hsmissile]")) {
				s.setLine(0, "");
				s.setLine(1, ChatColor.LIGHT_PURPLE + "[" + ChatColor.GOLD + "Missile" + ChatColor.LIGHT_PURPLE + "]");
				s.setLine(2, ChatColor.LIGHT_PURPLE + "[" + ChatColor.RED + "Heat Seeking" + ChatColor.LIGHT_PURPLE + "]");
				s.setLine(3, "");
			}
		}
		
	}
	
}
