package com.starquestminecraft.sqcontracts.util;

import java.util.UUID;

import com.mojang.api.profiles.HttpProfileRepository;
import com.mojang.api.profiles.Profile;

public class UUIDUtils {
	
	private static HttpProfileRepository repo = new HttpProfileRepository("minecraft");
	
	public static UUID uuidFromUsername(String username) {
		Profile[] profiles = repo.findProfilesByNames(new String[] { username });
		if(profiles == null || profiles.length == 0) return null;
		String s = profiles[0].getId();
		String s2 = profiles[0].getName();
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
