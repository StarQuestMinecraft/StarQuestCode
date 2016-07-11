package com.ginger_walnut.sqboosters;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.scheduler.BukkitScheduler;

import com.ginger_walnut.sqboosters.database.DatabaseInterface;

public class DatabaseChecker extends Thread {

	@SuppressWarnings("deprecation")
	public void run() {
		
		BukkitScheduler scheduler = Bukkit.getServer().getScheduler();
		
		scheduler.scheduleAsyncRepeatingTask(SQBoosters.getPluginMain(), new Runnable() {
			
			@Override
			public void run() {
		
				List<Integer> oldDatabaseIDs = new ArrayList<Integer>();
				List<Integer> oldDatabaseMultipliers = new ArrayList<Integer>();
				List<String> oldDatabaseBoosters = new ArrayList<String>();
				List<String> oldDatabasePurchasers = new ArrayList<String>();		
				
				oldDatabaseIDs.addAll(SQBoosters.databaseIDs);
				oldDatabaseMultipliers.addAll(SQBoosters.databaseMultipliers);
				oldDatabaseBoosters.addAll(SQBoosters.databaseBoosters);
				oldDatabasePurchasers.addAll(SQBoosters.databasePurchasers);			
		
				DatabaseInterface.updateLocalCopy();
		
				for (int i = 0; i < SQBoosters.multipliers.length; i ++) {
			
					SQBoosters.multipliers[i] = DatabaseInterface.getMultiplier(SQBoosters.configNames[i]);	
				
				}
				
				for (int j = 0; j < SQBoosters.databaseIDs.size(); j ++) {
					
					if (!oldDatabaseIDs.contains(SQBoosters.databaseIDs.get(j))) {
					
						String boosterName = "";
						String multiplierName = "";
						int multiplier = 0;
					
						for (int k = 0; k < SQBoosters.configNames.length; k ++) {
						
							if (SQBoosters.configNames[k].equals(SQBoosters.databaseBoosters.get(j))) {
							
								boosterName = SQBoosters.permissionName[k];
								multiplierName = SQBoosters.multiplierName[k];
							
								multiplier = SQBoosters.multipliers[k];
							
							}
						
						}
					
						if (multiplierName.equals("shop")) {
							
							double booster = multiplier;

							if (booster != 1) {
								
								booster =  Math.abs(SQBoosters.square(.5, (booster - 1)) - 2);
								
							}
							
							if (SQBoosters.databasePurchasers.get(j) != null) {
								
								Bukkit.getServer().broadcastMessage(ChatColor.GOLD + boosterName + ": " + SQBoosters.databasePurchasers.get(j) + " has purchased a " + multiplierName + " booster for " + getTimeLeft(j) + " and the multiplier is now " + booster);
								Bukkit.getServer().broadcastMessage(ChatColor.GOLD + "You can thank them by giving them money using /thank " + SQBoosters.databasePurchasers.get(j) + " <amount>");
							
							} else {
							
								Bukkit.getServer().broadcastMessage(ChatColor.GOLD + boosterName + ": An anonymous person has purchased a " + multiplierName + " booster for " + getTimeLeft(j) + " and the multiplier is now " + booster);
							
							}	
							
						} else {
							
							if (SQBoosters.databasePurchasers.get(j) != null) {
								
								Bukkit.getServer().broadcastMessage(ChatColor.GOLD + boosterName + ": " + SQBoosters.databasePurchasers.get(j) + " has purchased a " + multiplierName + " booster for " + getTimeLeft(j) + " and the multiplier is now " + multiplier);
								Bukkit.getServer().broadcastMessage(ChatColor.GOLD + "You can thank them by giving them money using /thank " + SQBoosters.databasePurchasers.get(j) + " <amount>");
							
							} else {
							
								Bukkit.getServer().broadcastMessage(ChatColor.GOLD + boosterName + ": An anonymous person has purchased a " + multiplierName + " booster for " + getTimeLeft(j) + " and the multiplier is now " + multiplier);
							
							}	
							
						}
				
					}
			
				}
				
				for (int j = 0; j < oldDatabaseIDs.size(); j ++) {
					
					if (!SQBoosters.databaseIDs.contains(oldDatabaseIDs.get(j))) {
					
						String boosterName = "";
						String multiplierName = "";
						int multiplier = 0;
					
						for (int k = 0; k < SQBoosters.configNames.length; k ++) {
						
							if (SQBoosters.configNames[k].equals(oldDatabaseBoosters.get(j))) {
							
								boosterName = SQBoosters.permissionName[k];
								multiplierName = SQBoosters.multiplierName[k];
							
								multiplier = SQBoosters.multipliers[k];
							
							}
						
						}
					
						if (multiplierName.equals("shop")) {
							
							double booster = multiplier;

							if (booster != 1) {
								
								booster =  Math.abs(SQBoosters.square(.5, (booster - 1)) - 2);
								
							}
							
							if (oldDatabasePurchasers.get(j) != null) {
								
								Bukkit.getServer().broadcastMessage(ChatColor.GOLD + boosterName + ": The "+ multiplierName + " booster purchased by " + oldDatabasePurchasers.get(j) + " has expired and the multiplier is now " + booster);
								Bukkit.getServer().broadcastMessage(ChatColor.GOLD + "You can nolonger thank " + oldDatabasePurchasers.get(j));
							
							} else {
							
								Bukkit.getServer().broadcastMessage(ChatColor.GOLD + boosterName + ": The "+ multiplierName + " booster purchased by an anonymous person has expired and the multiplier is now " + booster);
							
							}
							
						} else {
						
							if (oldDatabasePurchasers.get(j) != null) {
							
								Bukkit.getServer().broadcastMessage(ChatColor.GOLD + boosterName + ": The "+ multiplierName + " booster purchased by " + oldDatabasePurchasers.get(j) + " has expired and the multiplier is now " + multiplier);
								Bukkit.getServer().broadcastMessage(ChatColor.GOLD + "You can nolonger thank " + oldDatabasePurchasers.get(j));
							
							} else {
							
								Bukkit.getServer().broadcastMessage(ChatColor.GOLD + boosterName + ": The "+ multiplierName + " booster purchased by an anonymous person has expired and the multiplier is now " + multiplier);
							
							}
							
						}
				
					}
			
				}
				
			}
			
		}, 0, 600);
		
	}
	
	static String getTimeLeft(int i) {
		
		if (SQBoosters.databaseExpirationDates.get(i).getDay() > new Date().getDay()) {
			
			if (SQBoosters.databaseExpirationTimes.get(i).getMinutes() > new Date().getMinutes()) {
				
				return Integer.toString(SQBoosters.databaseMultipliers.get(i)) + " - " + SQBoosters.databasePurchasers.get(i) + " - " + Integer.toString(SQBoosters.databaseExpirationTimes.get(i).getHours() - new Date().getHours() + 24) + " hours and " + Integer.toString(SQBoosters.databaseExpirationTimes.get(i).getMinutes() - new Date().getMinutes()) + " minutes";
				
			} else {
				
				return Integer.toString(SQBoosters.databaseMultipliers.get(i)) + " - " + SQBoosters.databasePurchasers.get(i) + " - " + Integer.toString(SQBoosters.databaseExpirationTimes.get(i).getHours() - new Date().getHours() + 23) + " hours and " + Integer.toString(SQBoosters.databaseExpirationTimes.get(i).getMinutes() - new Date().getMinutes() + 60) + " minutes";
				
			}

			
		} else if (SQBoosters.databaseExpirationDates.get(i).getDay() == new Date().getDay()) {
			
			if (SQBoosters.databaseExpirationTimes.get(i).getMinutes() > new Date().getMinutes()) {
			
				return Integer.toString(SQBoosters.databaseMultipliers.get(i)) + " - " + SQBoosters.databasePurchasers.get(i) + " - " + Integer.toString(SQBoosters.databaseExpirationTimes.get(i).getHours() - new Date().getHours()) + " hours and " + Integer.toString(SQBoosters.databaseExpirationTimes.get(i).getMinutes() - new Date().getMinutes()) + " minutes";
				
			} else {
				
				return Integer.toString(SQBoosters.databaseMultipliers.get(i)) + " - " + SQBoosters.databasePurchasers.get(i) + " - " + Integer.toString(SQBoosters.databaseExpirationTimes.get(i).getHours() - new Date().getHours() - 1) + " hours and " + Integer.toString(SQBoosters.databaseExpirationTimes.get(i).getMinutes() - new Date().getMinutes() + 60) + " minutes";
				
			}
			
		}
		
		return "an unkown amount of time";
		
	}
	
}
