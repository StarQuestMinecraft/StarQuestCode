package com.whirlwindgames.dibujaron.sqempire;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.entity.Wither;
import org.bukkit.scheduler.BukkitScheduler;
import org.dynmap.markers.Marker;

import com.whirlwindgames.dibujaron.sqempire.database.object.EmpirePlayer;

import net.md_5.bungee.api.ChatColor;

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
	
	public List<UUID> captures = new ArrayList<UUID>();
	public List<UUID> newCaptures = new ArrayList<UUID>();
	
	public CapturePoint() {
	
	}
	
	public boolean capture(final Player player) {
		
		int captures = 0;
			
		for (Territory territory : SQEmpire.territories) {
				
			for (CapturePoint point : territory.capturePoints) {
					
				if (point.captures.contains(player.getName())) {
						
					captures ++;
						
				}
					
			}
				
		}
			
		if (captures >= 8) {
			
			return false;
			
		} else {
			
			BukkitScheduler scheduler = Bukkit.getScheduler();
			
			scheduler.scheduleSyncDelayedTask(SQEmpire.getInstance(), new Runnable() {
				
				public void run() {
			
					try {
						
						if (owner != Empire.NONE) {
							
							int xMultiplier = x / x;
				        	int zMultiplier = z / z;
							
							Wither wither = (Wither) player.getWorld().spawnEntity(new Location(player.getWorld(), x * 16 + (xMultiplier * 7), y + 6, z * 16 + (zMultiplier * 7)), EntityType.WITHER);
							wither.setGravity(false);
							
						}

					} catch (Exception e) {
						
						e.printStackTrace();
						
					}
					
					EmpirePlayer ep = EmpirePlayer.getOnlinePlayer(player);
					
					health.put(ep, 30);
					
					if (health.size() == 1) {
						
						beingCaptured = true;
						
						timeLeft = 900;
						
						Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "eb janesudo " + ep.getEmpire().getName() + " is capturing " + owner.getDarkColor() + name.replace("_", " " + owner.getDarkColor()) + " on " + Bukkit.getWorlds().get(0));
						
					}
					
					int xMultiplier = x / x;
		        	int zMultiplier = z / z;
					
		        	Block banner = player.getWorld().getBlockAt(x * 16 + (xMultiplier * 7), y + 2, z * 16 + (zMultiplier * 7));
		        	banner.setType(Material.AIR);
		        	banner.setType(Material.STANDING_BANNER);
		        	
		        	ep.getEmpire().setBanner(banner);
					
		        	int totalHealth = 0;
		        	
		        	List<EmpirePlayer> players = new ArrayList<EmpirePlayer>();
		        	players.addAll(health.keySet());
		        	
		        	for (int i = 0; i < health.size(); i ++) {
		        		
		        		totalHealth = totalHealth + health.get(players.get(i));
		        		
		        	}
		        	
		        	if (text == null) {
		        		
						List<Entity> entities = new ArrayList<Entity>();
						entities.addAll(player.getWorld().getNearbyEntities(new Location(player.getWorld(), x * 16 + (xMultiplier * 7.5), y + 2, z * 16 + (zMultiplier * 7.5)), 1, 1, 1));
						
						for (Entity entity : entities) {
							
							if (entity instanceof ArmorStand) {
								
								if (entity.getCustomName().startsWith("Health")) {

									entity.remove();

								}
								
							}
							
						}
		        		
		        		text = (ArmorStand) player.getWorld().spawnEntity(new Location(player.getWorld(), x * 16 + (xMultiplier * 7.5), y + 2, z * 16 + (zMultiplier * 7.5)), EntityType.ARMOR_STAND);
		        		text.setSmall(true);
		        		text.setGravity(false);
		        		text.setVisible(false);
		            	text.setCustomNameVisible(true);
		        		
		        	}
		        	
		        	text.setCustomName("Health Left: " + totalHealth);
		        	
		        	newCaptures.add(player.getUniqueId());
	        	
				}
				
			});
	    	
			return true;
			
		}

	}
	
	public void destroyBanner(Player player) {
		
		List<EmpirePlayer> players = new ArrayList<EmpirePlayer>();
		players.addAll(health.keySet());
		
		if (!EmpirePlayer.getOnlinePlayer(player).getEmpire().equals(players.get(0).getEmpire())) {
			
			int healthLeft = health.get(players.get(0));
			
			health.remove(players.get(0));
			
			healthLeft = healthLeft - 1;
			
			if (healthLeft != 0) {
				
				health.put(players.get(0), healthLeft);
				
			}
			
			if (health.size() == 0) {
				
	    		int xMultiplier = x / x;
	        	int zMultiplier = z / z;
	        	
				beingCaptured = false;
				
				List<Marker> markers = new ArrayList<Marker>();
				markers.addAll(SQEmpire.markerSet.getMarkers());
				
				for (Marker marker : markers) {
					
					if (marker.getMarkerID().equals(name.replace('_', ' '))) {
						
		            	marker.setDescription("Owner: " + owner.getName());
					
					}
					
				}
				
				Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "eb janesudo " + EmpirePlayer.getOnlinePlayer(player).getEmpire().getName() + " has successfully prevented the capture of " + name.replace('_', ' ') + " on " + player.getWorld().getName());
				
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
	        	
	        	player.sendMessage(ChatColor.GOLD + "Flag health is " + totalHealth);
	        	
	        	text.setCustomName("Health Left: " + totalHealth);
				
			}
			
		}
		
	}
	
}
