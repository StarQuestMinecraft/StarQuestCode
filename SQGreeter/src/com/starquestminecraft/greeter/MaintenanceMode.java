package com.starquestminecraft.greeter;

import java.util.ArrayList;
import java.util.UUID;

import com.mojang.api.profiles.HttpProfileRepository;
import com.mojang.api.profiles.Profile;

public class MaintenanceMode {
	
	private static boolean active = false;
	private static ArrayList<UUID> allowedPlayers = new ArrayList<UUID>();
	static HttpProfileRepository repo = new HttpProfileRepository("minecraft");

	public static String message;
	public static boolean isEnabled(){
		return active;
	}
	
	public static void toggleEnabled(String m){
		if(m == null || m.equals("")){
			message = "See http://starquestminecraft.com for more information.";
		} else {
			message = m;
		}
		active = !active;
		if(active == false){
			allowedPlayers.clear();
		}
	}
	
	public static boolean addPlayer(String player){
		UUID u = uuidFromUsername(player);
		if(u == null) return false;
		allowedPlayers.add(u);
		return true;
	}
	
	public static boolean isAllowed(UUID u){
		return allowedPlayers.contains(u);
	}
	
	public static UUID uuidFromUsername(String username) {
		Profile[] profiles = repo.findProfilesByNames(new String[] { username });
		String s = profiles[0].getId();
		String s2 = profiles[0].getName();
		System.out.println(s);
		System.out.println(s2);
		UUID u = uncanonicalizeUUID(s);
		return u;
	}

	public static UUID uncanonicalizeUUID(String shortUUID) {
		return UUID.fromString(shortUUIDToLong(shortUUID));
	}

	public static String shortUUIDToLong(String uuidString) {
		if (uuidString.length() != 32)
			throw new IllegalArgumentException("Wrong length");
		return uuidString.substring(0, 8) + "-" + uuidString.substring(8, 12) + "-" + uuidString.substring(12, 16) + "-" + uuidString.substring(16, 20) + "-" + uuidString.substring(20, 32);
	}
}
