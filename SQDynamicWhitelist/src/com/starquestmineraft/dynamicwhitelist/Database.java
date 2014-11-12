package com.starquestmineraft.dynamicwhitelist;

import java.util.UUID;

import com.mojang.api.profiles.HttpProfileRepository;
import com.mojang.api.profiles.Profile;

public abstract class Database {
	
	HttpProfileRepository repo = new HttpProfileRepository("minecraft");
	
	public boolean isPremium(UUID u){
		if(isPermanent(u)){
			return true;
		}
		int time = getRemainingTime(u);
		System.out.println("Remaining time: " + time);
		if(time == 0) return false;
		else return true;
	}
	
	//must execute async
	//adds the specified amount of time in hours to their remaining time
	public abstract void addPremiumTime(UUID u, int hours);
	
	//must execute async
	//adds the specified amount of time in hours to their remaining time
	//to be called from buycraft
	public abstract void addPremiumTime(String playername, int hours);
	
	//not async
	public abstract int getRemainingTime(UUID u);
	
	public abstract boolean hasPlayedBefore(UUID u);
	
	public abstract void registerNewPlayer(UUID u);
	
	public abstract void setPermanent(UUID u, boolean permanent);
	
	public abstract boolean isPermanent(UUID u);
	
	//MUST BE CALLED ASYNC
	public UUID uuidFromUsername(String username){
		Profile[] profiles = repo.findProfilesByNames(new String[] {username});
		String s = profiles[0].getId();
		String s2 = profiles[0].getName();
		System.out.println(s);
		System.out.println(s2);
		UUID u = uncanonicalizeUUID(s);
		return u;
	}
	
	//stolen from zPermissions
	public UUID uncanonicalizeUUID(String shortUUID){
		return UUID.fromString(shortUUIDToLong(shortUUID));
	}
	
	//stolen from zPermissions
	public String shortUUIDToLong(String uuidString){
		 if (uuidString.length() != 32) throw new IllegalArgumentException("Wrong length");
	     return uuidString.substring(0, 8) + "-" + uuidString.substring(8, 12) + "-" + uuidString.substring(12, 16) + "-" + uuidString.substring(16, 20) + "-" + uuidString.substring(20, 32);
	}
}
