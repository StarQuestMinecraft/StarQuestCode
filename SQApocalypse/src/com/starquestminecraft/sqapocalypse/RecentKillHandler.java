package com.starquestminecraft.sqapocalypse;

import java.util.HashMap;
import java.util.HashSet;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;

public class RecentKillHandler {
	
	private static HashSet<RecentKill> recentKills = new HashSet<RecentKill>();
	private static final long HOUR = 1000 * 60 * 60;
	
	public static boolean hasKilledWithinHour(Player killer, Player killed){
		for(RecentKill k : recentKills){
			if(k.killer != killer.getUniqueId()) continue;
			if(k.killed != killed.getUniqueId()) continue;
			long time = k.time;
			long diff = System.currentTimeMillis() - time;
			if(diff > HOUR) continue;
			return true;
		}
		return false;
	}
	public static void addRecentKill(Player killer, Player killed){
		final RecentKill k = new RecentKill(killer, killed);
		recentKills.add(k);
		Bukkit.getScheduler().scheduleSyncDelayedTask(SQApocalypse.getInstance(), new Runnable(){
			public void run(){
				if(recentKills.contains(k)){
					recentKills.remove(k);
					Player p = Bukkit.getPlayer(k.killer);
					if(p != null){
						OfflinePlayer plrk = Bukkit.getOfflinePlayer(k.killed);
						p.sendMessage("It has been an hour since you killed " + plrk.getName() + ", you can now kill them again for points.");
					}
				}
			}
		}, 20 * 60 * 60);
	}
	
	private static class RecentKill{
		UUID killer;
		UUID killed;
		long time;
		
		public RecentKill(Player killer, Player killed){
			this.killer = killer.getUniqueId();
			this.killed = killed.getUniqueId();
			this.time = System.currentTimeMillis();
		}
		
	}
}
