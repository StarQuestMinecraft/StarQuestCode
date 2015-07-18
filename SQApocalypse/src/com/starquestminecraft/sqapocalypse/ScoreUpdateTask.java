package com.starquestminecraft.sqapocalypse;

import java.util.Collection;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitRunnable;

public class ScoreUpdateTask extends BukkitRunnable{

	Plugin core;
	
	public ScoreUpdateTask(Plugin p){
		core = p;
	}
	@Override
	public void run() {
		System.out.println("Updating scores.");
		final Collection<? extends Player> players = Bukkit.getOnlinePlayers();
		Bukkit.getScheduler().runTaskAsynchronously(core, new Runnable(){
			public void run(){
				for(Player p : players){
					if(p != null)
						SQLDatabase.addScore(p.getUniqueId(), 1);
				}
			}
		});
		
	}

}
