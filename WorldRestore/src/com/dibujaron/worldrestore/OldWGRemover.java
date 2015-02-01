package com.dibujaron.worldrestore;

import java.util.Collection;

import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.scheduler.BukkitRunnable;

import com.earth2me.essentials.Essentials;
import com.earth2me.essentials.User;
import com.sk89q.worldguard.bukkit.WorldGuardPlugin;
import com.sk89q.worldguard.domains.DefaultDomain;
import com.sk89q.worldguard.protection.managers.RegionManager;
import com.sk89q.worldguard.protection.regions.ProtectedRegion;

public class OldWGRemover extends BukkitRunnable{
	
	private int runs = 0;
	private int index = 0;
	final static int MAX_PER_RUN = 20;
	private static Essentials e = null;
	private ProtectedRegion[] regions = null;
	private int totalDone = 0;
	private RegionManager rm;
	
	public static void removeOldWGs(Core c,World w){
		RegionManager rm = ((WorldGuardPlugin) Bukkit.getPluginManager().getPlugin("WorldGuard")).getRegionManager(w);
		e = (Essentials) (Bukkit.getPluginManager().getPlugin("Essentials"));
		Collection<ProtectedRegion> regions = rm.getRegions().values();
		OldWGRemover rev = new OldWGRemover();
		rev.regions = regions.toArray(new ProtectedRegion[1]);
		rev.rm = rm;
		rev.runTaskTimer(c, 1, 1);
	}
	
	public void run(){
		System.out.println("runs: " + runs);
		runs++;
		int processedThisRun = 0;
		while(processedThisRun < MAX_PER_RUN && index < regions.length){
			try{
				ProtectedRegion r = regions[index];
				process(r);
				processedThisRun++;
			} catch (Exception e){
				e.printStackTrace();
			}
			processedThisRun++;
			index++;
		}
		totalDone += processedThisRun;
		System.out.println("Completed run.");
 		if(index >= regions.length){
			System.out.println("Finished!");
			/*for(LivingEntity e : from.getEntitiesByClass){
				Location l = e.getLocation();
				e.teleport(new Location(to, l.getX(), l.getY(), l.getZ(), l.getYaw(), l.getPitch()));
			}*/
			this.cancel();
			try{
				rm.save();
			} catch (Exception ex){
				ex.printStackTrace();
			}
		}
	}
	
	private void process(ProtectedRegion r){
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
			rm.removeRegion(r.getId());
		}
	}
}
