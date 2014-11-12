package com.dibujaron.feudalism.task;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import com.dibujaron.feudalism.player.FPlayer;

public class MoveCheckTask extends BukkitRunnable{

	public void run(){
		for(Player p : Bukkit.getServer().getOnlinePlayers()){
			FPlayer plr = FPlayer.getFPlayer(p);
			plr.moveCheck();
		}
	}
}
