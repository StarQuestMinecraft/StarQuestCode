package com.starquestminecraft.sqapocalypse;

import java.util.Collection;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitRunnable;

import com.sk89q.worldguard.bukkit.WorldGuardPlugin;
import com.sk89q.worldguard.protection.ApplicableRegionSet;
import com.sk89q.worldguard.protection.flags.DefaultFlag;
import com.sk89q.worldguard.protection.managers.RegionManager;
import com.sk89q.worldguard.protection.regions.ProtectedRegion;

public class ScoreUpdateTask extends BukkitRunnable{

	Plugin core;
	
	RegionManager rm = null;
	WorldGuardPlugin wg = null;
	
	public ScoreUpdateTask(Plugin p){
		core = p;
	}
	
	@Override
	public void run() {
		if(wg == null){
			wg = WorldGuardPlugin.inst();
		}
		if(rm == null ){
			rm = wg.getRegionManager(Bukkit.getWorld(Bukkit.getServerName()));
		}
		System.out.println("Updating scores.");
		final Collection<? extends Player> players = Bukkit.getOnlinePlayers();
		for(Player p : players){
			if(p != null){
				ApplicableRegionSet set = rm.getApplicableRegions(p.getLocation());
				for(ProtectedRegion r : set){
					if(r.getId().equalsIgnoreCase("OriginStation")){
						p.sendMessage("Your score was not updated because you are in the spawn no-pvp zone.");
						continue;
					}
				}
				SQLDatabase.addScore(p.getUniqueId(), 1);
			}
		}
	}
}
