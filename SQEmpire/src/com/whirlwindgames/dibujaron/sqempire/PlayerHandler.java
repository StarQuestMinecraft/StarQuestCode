package com.whirlwindgames.dibujaron.sqempire;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerKickEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import com.gmail.nossr50.datatypes.skills.SkillType;
import com.gmail.nossr50.datatypes.skills.XPGainReason;
import com.gmail.nossr50.events.experience.McMMOPlayerXpGainEvent;
import com.massivecraft.factions.Rel;
import com.massivecraft.factions.event.EventFactionsChunkChangeType;
import com.massivecraft.factions.event.EventFactionsChunksChange;
import com.massivecraft.factions.event.EventFactionsRankChange;
import com.massivecraft.massivecore.ps.PS;
import com.sk89q.worldguard.protection.ApplicableRegionSet;
import com.sk89q.worldguard.protection.regions.ProtectedRegion;
import com.whirlwindgames.dibujaron.sqempire.database.object.EmpirePlayer;

public class PlayerHandler implements Listener{
	
	@EventHandler
	public void onPlayerJoin(PlayerJoinEvent event){
		
		EmpirePlayer.loadPlayerData(event.getPlayer());
		
	}
	
	@EventHandler
	public void onPlayerQuit(final PlayerQuitEvent event){
		
		final EmpirePlayer player = EmpirePlayer.getOnlinePlayer(event.getPlayer());
		
		EmpirePlayer.unloadPlayerData(event.getPlayer());
		
		Bukkit.getScheduler().runTaskAsynchronously(SQEmpire.getInstance(), new Runnable() {
			
			public void run() {
				
				player.publishData();
				
			}
			
		});
		
	}
	
	@EventHandler
	public void onPlayerKick(final PlayerKickEvent event){
		
		EmpirePlayer.getOnlinePlayer(event.getPlayer()).publishData();;
		
	}

	@EventHandler
	public void onBlockBreak(BlockBreakEvent event) {
		
		for (Territory territory : SQEmpire.territories) {
			
			for (CapturePoint capturePoint : territory.capturePoints) {
				
				int xMultiplier = capturePoint.x / capturePoint.x;
		        int zMultiplier = capturePoint.z / capturePoint.z;
					
		        if (event.getBlock().getLocation().equals(new Location(event.getPlayer().getWorld(), capturePoint.x * 16 + (xMultiplier * 7), capturePoint.y + 2, capturePoint.z * 16 + (zMultiplier * 7)))) {
		        		
		        	if (event.getBlock().getType().equals(Material.BANNER) || event.getBlock().getType().equals(Material.STANDING_BANNER)) {
		        			
			        	event.setCancelled(true);
			        		
			        	capturePoint.destroyBanner(event.getPlayer());
			        	return;
		        			
		        	}
		        	
				}
	        	
			}
				
		}
		
	}
	
	@EventHandler
	public void onPlayerInteract(PlayerInteractEvent event) {
		
		if (event.getAction().equals(Action.LEFT_CLICK_BLOCK)) {
			
			ApplicableRegionSet regions = SQEmpire.worldGuardPlugin.getRegionManager(Bukkit.getWorlds().get(0)).getApplicableRegions(event.getClickedBlock().getLocation());
			
			List<ProtectedRegion> protectedRegions = new ArrayList<ProtectedRegion>();
			protectedRegions.addAll(regions.getRegions());
			
			boolean slowBreak = false;
			
			for (ProtectedRegion region : protectedRegions) {
				
				for (Territory territory : SQEmpire.territories) {
					
					if (("SQEmpire-" + territory.name).equalsIgnoreCase(region.getId())) {
						
						if (!territory.owner.equals(Empire.NONE)) {
							
							Empire empire = EmpirePlayer.getOnlinePlayer(event.getPlayer()).getEmpire(); 
							
							if (!territory.owner.equals(empire)) {
								
								if (SQEmpire.isBattleConnected(territory, empire)) {
									
									slowBreak = true;
									
								}
								
							}
							
						}

					}
					
				}
				
			}
			
			for (Territory territory : SQEmpire.territories) {
				
				for (CapturePoint capturePoint : territory.capturePoints) {
					
					int xMultiplier = capturePoint.x / capturePoint.x;
			        int zMultiplier = capturePoint.z / capturePoint.z;
						
			        if (event.getClickedBlock().getLocation().equals(new Location(event.getPlayer().getWorld(), capturePoint.x * 16 + (xMultiplier * 7), capturePoint.y + 2, capturePoint.z * 16 + (zMultiplier * 7)))) {
			        		
			        	if (event.getClickedBlock().getType().equals(Material.BANNER) || event.getClickedBlock().getType().equals(Material.STANDING_BANNER)) {
			        		
			        		slowBreak = false;
			        			
			        	}
			        	
					}
		        	
				}
					
			}
			
			if (slowBreak) {
				
				event.getPlayer().addPotionEffect(new PotionEffect(PotionEffectType.SLOW_DIGGING, 2000, 1, false));
				
			} else {
				
				if (event.getPlayer().hasPotionEffect(PotionEffectType.SLOW_DIGGING)) {
					
					event.getPlayer().removePotionEffect(PotionEffectType.SLOW_DIGGING);

				}
				
			}
			
		} else {
			
			event.getPlayer().removePotionEffect(PotionEffectType.SLOW_DIGGING);
			
		}
		
	}
	
	@EventHandler
	public void onFactionChunkChange(EventFactionsChunksChange event) {
		
		List<EventFactionsChunkChangeType> changes = new ArrayList<EventFactionsChunkChangeType>();
		List<PS> chunks = new ArrayList<PS>();
		
		for (PS ps : event.getChunkType().keySet()) {
			
			changes.add(event.getChunkType().get(ps));
			chunks.add(ps);
			
		}
		
		for(int i = 0; i < changes.size(); i ++) {
			
			if (changes.get(i).equals(EventFactionsChunkChangeType.BUY)) {
				
				int x = chunks.get(i).asBukkitChunk().getX();
				int z = chunks.get(i).asBukkitChunk().getZ();
				
				int xMultiplier = x / x;
				int zMultiplier = z / z;
				
				Location location = new Location(chunks.get(i).asBukkitWorld(), (x * 16) + (xMultiplier * 7), 100, (z * 16) + (zMultiplier * 7));

				ApplicableRegionSet regions = SQEmpire.worldGuardPlugin.getRegionManager(Bukkit.getWorlds().get(0)).getApplicableRegions(location);
				
				List<ProtectedRegion> protectedRegions = new ArrayList<ProtectedRegion>();
				protectedRegions.addAll(regions.getRegions());
				
				for (ProtectedRegion region : protectedRegions) {
					
					for (Territory territory : SQEmpire.territories) {
						
						if (("SQEmpire-" + territory.name).equalsIgnoreCase(region.getId())) {
							
							if (event.getSender() instanceof Player) {
								
								Player player = (Player) event.getSender();								
								
								Empire empire = EmpirePlayer.getOnlinePlayer(player).getEmpire(); 
								
								if (!territory.owner.equals(empire)) {
									
									event.setCancelled(true);
									return;	
									
								}
								
							}
							
						}
						
					}
					
				}
				
			}
			
		}
		
	}
	
    @EventHandler
	public void onFactionRankChange(EventFactionsRankChange event) {
		
		if (event.getNewRank().equals(Rel.LEADER)) {
			
			if (!event.getMPlayer().getPlayer().hasPermission("factions.create")) {
				
				event.setCancelled(true);
				
				event.getSender().sendMessage(ChatColor.RED + "That player cannot own a faction");
				
			}
			
		}
		
	}
    
	@EventHandler(priority = EventPriority.LOW)
	public void onMCMMOPlayerXpGain(McMMOPlayerXpGainEvent event) {
		
		if (!event.getXpGainReason().equals(XPGainReason.COMMAND)) {
			
			float multiplier = 1;
			
			if (SQEmpire.isCorePlanet) {
				
				multiplier = multiplier + .25f;
				
			}
			
			Empire empire = EmpirePlayer.getOnlinePlayer(event.getPlayer()).getEmpire();
			
			for (String planet : SQEmpire.dominantEmpires.keySet()) {
				
				if (SQEmpire.dominantEmpires.get(planet) == empire) {
					
					for (SkillType skill : SQEmpire.mcmmoBoosters.get(planet)) {
						
						if (event.getSkill().equals(skill)) {

							multiplier = multiplier + .5f;

						}
						
					}
					
				}
				
			}
			
			if (SQEmpire.mcmmoBoosters.containsKey(Bukkit.getWorlds().get(0).getName())) {
				
				for (SkillType skill : SQEmpire.mcmmoBoosters.get(Bukkit.getWorlds().get(0).getName())) {
					
					if (event.getSkill().equals(skill)) {

						for (Territory territory : SQEmpire.territories) {
							
							if (territory.owner == empire) {
								
								multiplier = multiplier + .05f;
								
							}
							
						}

					}
					
				}

			}
			
			event.setRawXpGained(event.getRawXpGained() * multiplier);
			
		}
		
	}
	
}
