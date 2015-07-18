package com.starquestminecraft.sqapocalypse;

import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;

public class PlayerBurnTask extends BukkitRunnable{
	World w;
	int stage;
	public PlayerBurnTask(World w, int stage){
		this.w = w;
		this.stage = stage;
	}
	
	public void run(){
		long time = (w.getTime() % 24000);
		if(time > 12000) return;
		for(Player p : w.getPlayers()){
			if(p.getGameMode() == GameMode.CREATIVE) return;
			if(stage < 4){
				ItemStack helmet = p.getInventory().getHelmet();
				if(helmet != null && helmet.getType() == Material.PUMPKIN){
					continue;
				}
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
