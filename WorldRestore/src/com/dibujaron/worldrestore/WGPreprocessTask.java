package com.dibujaron.worldrestore;

import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.block.Block;

import com.earth2me.essentials.Essentials;
import com.earth2me.essentials.User;
import com.sk89q.worldedit.BlockVector;
import com.sk89q.worldguard.bukkit.WorldGuardPlugin;
import com.sk89q.worldguard.domains.DefaultDomain;
import com.sk89q.worldguard.protection.managers.RegionManager;
import com.sk89q.worldguard.protection.regions.ProtectedRegion;

public class WGPreprocessTask extends PreprocessTask{

	ProtectedRegion r;
	RegionManager rm = null;
	Essentials e = (Essentials) (Bukkit.getPluginManager().getPlugin("Essentials"));
	public WGPreprocessTask(ProtectedRegion r){
		this.r = r;
	}
	
	@Override
	public void process(World from, World to) {
		if(rm == null){
			rm = ((WorldGuardPlugin) Bukkit.getPluginManager().getPlugin("WorldGuard")).getRegionManager(to);
		}
		System.out.println("Preprocessing: WG Region " + r.getId());
		if (r.getId().equalsIgnoreCase("__global__")) {
			System.out.println("Global region, returning!");
			return;
		}
		DefaultDomain owners = r.getOwners();
		boolean found = false;
		for (String s : owners.getPlayers()) {
			User u = e.getOfflineUser(s);
			if(u != null){
				long time = System.currentTimeMillis() - u.getLastLogout();
				System.out.println("Milliseconds since last played: " + time);
				if (time < WorldRestore.month) {
					System.out.println("Found an owner played within last month.");
					found = true;
					break;
				}
			}
		}
		if (found) {
			BlockVector min = r.getMinimumPoint();
			BlockVector max = r.getMaximumPoint();

			System.out.println("min: " + min + "max: " + max);
			for (double x = min.getX(); x <= max.getX(); x++) {
				for (double y = min.getY(); y <= max.getY(); y++) {
					for (double z = min.getZ(); z <= max.getZ(); z++) {
						Block b = from.getBlockAt((int) x, (int) y, (int) z);
						WorldRestore.addToProcessQueues(b);
					}
				}
			}
			
		}
	}

}
