package com.whirlwindgames.dibujaron.sqempire;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;

import com.whirlwindgames.dibujaron.sqempire.database.object.EmpirePlayer;

public class CapturePoint {

	public Empire owner;
	public int x;
	public int y;
	public int z;
	public String name;
	
	public int timeLeft;	
	public boolean beingCaptured = false;
	
	public ArmorStand text;
	
	public Map<Player, Integer> health = new HashMap<Player, Integer>();
	
	public CapturePoint() {
	
	}
	
	public boolean capture(Player player) {
		
		if (!health.containsKey(player)) {
			
			health.put(player, 10);
			
			if (health.size() == 1) {
				
				beingCaptured = true;
				
			}
			
			int xMultiplier = x / x;
        	int zMultiplier = z / z;
			
        	owner.getBanner(player.getWorld().getBlockAt(x * 16 + (xMultiplier * 7), y + 2, z * 16 + (zMultiplier * 7)));
			
        	int totalHealth = 0;
        	
        	for (int i = 0; i < health.size(); i ++) {
        		
        		totalHealth = totalHealth + health.get(((List<Player>)health.keySet()).get(i));
        		
        	}
        	
        	if (text == null) {
        		
        		text = (ArmorStand) player.getWorld().spawnEntity(new Location(player.getWorld(), x * 16 + (xMultiplier * 7), y + 4, z * 16 + (zMultiplier * 7)), EntityType.ARMOR_STAND);
        		text.setSmall(true);
        		text.setGravity(false);
        		text.setVisible(false);
            	text.setCustomNameVisible(true);
        		
        	}
        	
        	text.setCustomName("Health Left:" + totalHealth);
        	
			return true;
			
		}
		
		return false;

	}
	
	public void destroyBanner(Player player) {
		
		List<Player> players = new ArrayList<Player>();
		players.addAll(health.keySet());
		
		Player oldPlayer = players.get(0);
		
		int healthLeft = health.get(players.get(0));
		
		health.remove(players.get(0));
		
		if (healthLeft != 1) {
			
			health.put(players.get(0), healthLeft --);
			
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
			text = null;
			
		} else {
			
        	int totalHealth = 0;
        	
        	for (int i = 0; i < health.size(); i ++) {
        		
        		totalHealth = totalHealth + health.get(((List<Player>)health.keySet()).get(i));
        		
        	}
        	
        	text.setCustomName("Health Left:" + totalHealth);
			
		}
		
	}
	
}
