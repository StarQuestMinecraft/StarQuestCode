package com.dibujaron.worldrestore;

import java.util.ArrayList;

import org.bukkit.Bukkit;
import org.bukkit.World;

import com.earth2me.essentials.Essentials;
import com.earth2me.essentials.User;
import com.sk89q.worldedit.regions.Region;
import com.sk89q.worldguard.bukkit.WorldGuardPlugin;
import com.sk89q.worldguard.domains.DefaultDomain;
import com.sk89q.worldguard.protection.databases.ProtectionDatabaseException;
import com.sk89q.worldguard.protection.managers.RegionManager;
import com.sk89q.worldguard.protection.regions.ProtectedRegion;

public class OldWGRemover {
	public static void removeOldWGs(World w){
		
		RegionManager rm = ((WorldGuardPlugin) Bukkit.getPluginManager().getPlugin("WorldGuard")).getRegionManager(w);
		Essentials e = (Essentials) (Bukkit.getPluginManager().getPlugin("Essentials"));
		ArrayList<ProtectedRegion> removeLater = new ArrayList<ProtectedRegion>();
		for(ProtectedRegion r : rm.getRegions().values()){
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
			if(!found){
				removeLater.add(r);
			}
		}
		for(ProtectedRegion r : removeLater){
			rm.removeRegion(r.getId());
		}
		try{
			rm.save();
		} catch (Exception ex){
			ex.printStackTrace();
		}
	}
}
