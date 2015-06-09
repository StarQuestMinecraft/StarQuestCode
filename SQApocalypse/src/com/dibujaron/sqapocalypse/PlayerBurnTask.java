package com.dibujaron.sqapocalypse;

import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

public class PlayerBurnTask extends BukkitRunnable{
	World w;
	int stage;
	public PlayerBurnTask(World w, int stage){
		this.w = w;
		this.stage = stage;
	}
	
	public void run(){
		for(Player p : w.getPlayers()){
			if(p.getGameMode() == GameMode.CREATIVE) return;
			if(stage < 4 && p.getInventory().getHelmet().getType() == Material.PUMPKIN){
				continue;
			}
			Location l = p.getLocation();
			int fromSky = l.getBlock().getLightFromSky();
			if(fromSky < 13){
				continue;
			} else {
				p.setFireTicks(40);
			}
		}
	}
}
