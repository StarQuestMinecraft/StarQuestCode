package com.whirlwindgames.dibujaron.sqempire;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitScheduler;

import com.whirlwindgames.dibujaron.sqempire.database.object.EmpirePlayer;

public class CapturePoint {

	public Empire owner;
	public int x;
	public int y;
	public int z;
	public String name;
	public String configPath;
	
	public int timeLeft;	
	public boolean beingCaptured = false;
	
	public ArmorStand text;
	
	public Map<EmpirePlayer, Integer> health = new HashMap<EmpirePlayer, Integer>();
	
	public CapturePoint() {
	
	}
	
	public boolean capture(final Player player) {
		
		if (!health.containsKey(player)) {
			
			BukkitScheduler scheduler = Bukkit.getScheduler();
			
			scheduler.scheduleSyncDelayedTask(SQEmpire.getInstance(), new Runnable() {
				
				public void run() {
			
					EmpirePlayer ep = EmpirePlayer.getOnlinePlayer(player);
					
					health.put(ep, 10);
					
					if (health.size() == 1) {
						
						beingCaptured = true;
						
						timeLeft = 10;
						
						Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "ee sudojane " + ep.getEmpire().getName() + " is capturing " + name);
						
					}
					
					int xMultiplier = x / x;
		        	int zMultiplier = z / z;
					
		        	Block banner = player.getWorld().getBlockAt(x * 16 + (xMultiplier * 7), y + 2, z * 16 + (zMultiplier * 7));
		        	banner.setType(Material.STANDING_BANNER);
		        	
		        	ep.getEmpire().setBanner(banner);
					
		        	int totalHealth = 0;
		        	
		        	List<EmpirePlayer> players = new ArrayList<EmpirePlayer>();
		        	players.addAll(health.keySet());
		        	
		        	for (int i = 0; i < health.size(); i ++) {
		        		
		        		totalHealth = totalHealth + health.get(players.get(i));
		        		
		        	}
		        	
		        	if (text == null) {
		        		
		        		text = (ArmorStand) player.getWorld().spawnEntity(new Location(player.getWorld(), x * 16 + (xMultiplier * 7.5), y + 2, z * 16 + (zMultiplier * 7.5)), EntityType.ARMOR_STAND);
		        		text.setSmall(true);
		        		text.setGravity(false);
		        		text.setVisible(false);
		            	text.setCustomNameVisible(true);
		        		
		        	}
		        	
		        	text.setCustomName("Health Left: " + totalHealth);
	        	
				}
				
			});
        	
			return true;
			
		}
		
		return false;

	}
	
	public void destroyBanner(Player player) {
		
		List<EmpirePlayer> players = new ArrayList<EmpirePlayer>();
		players.addAll(health.keySet());
		
		int healthLeft = health.get(players.get(0));
		
		health.remove(players.get(0));
		
		healthLeft = healthLeft - 1;
		
		if (healthLeft != 0) {
			
			health.put(players.get(0), healthLeft);
			
		}
		
		if (health.size() == 0) {
			
    		int xMultiplier = x / x;
        	int zMultiplier = z / z;
			
			/**EmpirePlayer ep = EmpirePlayer.getOnlinePlayer(player);
			
			owner = ep.getEmpire();

			if (owner.equals(Empire.ARATOR)) {
				
				SQEmpire.spawnRectangle(x * 16 + (xMultiplier * 6), y + 1, z * 16 + (zMultiplier * 6), 4, 4, Material.STAINED_GLASS, 11);
				
			} else if (owner.equals(Empire.YAVARI)) {
				
				SQEmpire.spawnRectangle(x * 16 + (xMultiplier * 6), y + 1, z * 16 + (zMultiplier * 6), 4, 4, Material.STAINED_GLASS, 10);
				
			} else if (owner.equals(Empire.REQUIEM)) {
				
				SQEmpire.spawnRectangle(x * 16 + (xMultiplier * 6), y + 1, z * 16 + (zMultiplier * 6), 4, 4, Material.STAINED_GLASS, 14);
				
			} else {
				
				SQEmpire.spawnRectangle(x * 16 + (xMultiplier * 6), y + 1, z * 16 + (zMultiplier * 6), 4, 4, Material.STAINED_GLASS, 0);
				
			}**/
			
			beingCaptured = false;
			
			Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "ee sudojane " + owner.getName() + " has successfully prevented the capture of the capture point " + name);
			
			player.getWorld().getBlockAt(new Location(player.getWorld(), x * 16 + (xMultiplier * 7), y + 2, z * 16 + (zMultiplier * 7))).setType(Material.AIR);
			text.remove();
			text = null;
			
		} else {
			
			int xMultiplier = x / x;
        	int zMultiplier = z / z;
			
        	Block banner = player.getWorld().getBlockAt(x * 16 + (xMultiplier * 7), y + 2, z * 16 + (zMultiplier * 7));
        	banner.setType(Material.STANDING_BANNER);

        	players.get(0).getEmpire().setBanner(banner);
        	
        	int totalHealth = 0;
        	
        	for (int i = 0; i < health.size(); i ++) {
        		
        		totalHealth = totalHealth + health.get(players.get(i));
        		
        	}
        	
        	text.setCustomName("Health Left: " + totalHealth);
			
		}
		
	}
	
}
