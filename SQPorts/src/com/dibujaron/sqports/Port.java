package com.dibujaron.sqports;

import org.bukkit.configuration.file.FileConfiguration;

import com.sk89q.worldguard.protection.managers.RegionManager;
import com.sk89q.worldguard.protection.regions.ProtectedRegion;

public class Port {
	String name;
	String target;
	int x, y, z;
	int tx, ty, tz;
	ProtectedRegion region;
	
	public Port(FileConfiguration config, String key, RegionManager rm){
		name = key;
		target = config.getString(key + ".target");
		x = config.getInt(key + ".x");
		tx = config.getInt(key + ".tx");
		y = config.getInt(key + ".y");
		ty = config.getInt(key + ".ty");
		z = config.getInt(key + ".z");
		tz = config.getInt(key + ".tz");
		region = rm.getRegion("port-" + name);
	}
}
