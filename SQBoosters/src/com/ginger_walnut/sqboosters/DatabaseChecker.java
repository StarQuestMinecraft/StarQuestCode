package com.ginger_walnut.sqboosters;

import java.util.TimerTask;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;

import com.ginger_walnut.sqboosters.database.DatabaseInterface;

public class DatabaseChecker extends TimerTask {

	public void run() {
		
		for (int i = 0; i < SQBoosters.multipliers.length; i ++) {
			
			DatabaseInterface di = new DatabaseInterface();
			
			int multiplier = di.getMultiplier(SQBoosters.multiplierName[i]);
			
			if (multiplier != SQBoosters.multipliers[i]) {
				
				SQBoosters.multipliers[i] = multiplier;
				
				Bukkit.getServer().broadcastMessage(ChatColor.GOLD + SQBoosters.permissionName[i] + ": The " + SQBoosters.multiplierName[i] + " multiplier has been set to " + multiplier);
				
			}
	
		}
		
	}
	
}
