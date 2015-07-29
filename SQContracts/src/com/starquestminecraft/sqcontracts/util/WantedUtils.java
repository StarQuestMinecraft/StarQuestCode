package com.starquestminecraft.sqcontracts.util;

import java.util.HashMap;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import com.starquestminecraft.sqcontracts.SQContracts;

public class WantedUtils {
	public static HashMap<UUID, Integer> delayedWantedPlayers = new HashMap<UUID, Integer>();
	
	public static void addDelayedWanted(final UUID u){
		//create the new removal task
		int taskid = Bukkit.getScheduler().scheduleSyncDelayedTask(SQContracts.get(), new Runnable(){
			public void run(){
				delayedWantedPlayers.remove(u);
				Player p = Bukkit.getPlayer(u);
				if(p != null && p.isOnline()){
					p.sendMessage("it has been an hour, the wanted status from your removed contract has expired.");
				}
			}
		}, 20 * 60 * 60L);
		
		//if they're already there, remove the old one
		Integer taskID = delayedWantedPlayers.get(u);
		if(taskID != null){
			int task = taskID;
			Bukkit.getScheduler().cancelTask(task);
			delayedWantedPlayers.remove(u);
		}
		delayedWantedPlayers.put(u, taskid);
	}
}
