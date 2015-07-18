package com.dibujaron.sqports;

import java.util.HashSet;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import com.sk89q.worldguard.bukkit.WorldGuardPlugin;
import com.sk89q.worldguard.protection.managers.RegionManager;

public class SQPorts extends JavaPlugin{
	
	WorldGuardPlugin wg;
	RegionManager rm;
	static HashSet<Port> ports = new HashSet<Port>();
	public void onEnable(){
		saveDefaultConfig();
		wg = WorldGuardPlugin.inst();
		rm = wg.getRegionManager(Bukkit.getWorld(Bukkit.getServerName()));
		for(String key : getConfig().getKeys(false)){
			Port p = new Port(getConfig(), key, rm);
			if(p.region != null){
				ports.add(p);
				System.out.println("port added: " + p.name);
			}
		}
		PortTask t = new PortTask();
		int delay = PortTask.getIntervalShipLeavesEvery(20 * 60);
		t.runTaskTimer(this, delay, delay);
	}
}
