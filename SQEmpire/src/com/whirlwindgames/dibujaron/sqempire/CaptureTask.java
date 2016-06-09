package com.whirlwindgames.dibujaron.sqempire;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitScheduler;
import org.dynmap.markers.AreaMarker;
import org.dynmap.markers.Marker;

import com.sk89q.worldguard.domains.DefaultDomain;
import com.sk89q.worldguard.protection.regions.ProtectedPolygonalRegion;
import com.whirlwindgames.dibujaron.sqempire.database.object.EmpirePlayer;

public class CaptureTask extends Thread {
	
	public void run() {
		
		BukkitScheduler scheduler = Bukkit.getServer().getScheduler();
		
		scheduler.scheduleSyncRepeatingTask(SQEmpire.getInstance(), new Runnable() {
			
			@Override
			public void run() {

				for (Territory territory : SQEmpire.territories) {
					
					for (CapturePoint point : territory.capturePoints) {
						
						if (point.beingCaptured) {
							
							System.out.print(point.timeLeft);	
							
							point.timeLeft = point.timeLeft - 1;
								
							if (point.timeLeft == 600) {
								
								Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "ee sudojane " + point.owner.getDarkColor() + point.name + " will be captured in 10 minutes");
								
							}
							
							if (point.timeLeft == 300) {
								
								Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "ee sudojane " + point.owner.getDarkColor() + point.name + " will be captured in 5 minutes");
								
							}
							
							if (point.timeLeft == 60) {
								
								Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "ee sudojane " + point.owner.getDarkColor() + point.name + " will be captured in 1 minute");
								
							}
							
							if (point.timeLeft == 0) {
								
								List<EmpirePlayer> players = new ArrayList<EmpirePlayer>();
								players.addAll(point.health.keySet());
								
								Empire oldOwner = point.owner;
								
								point.owner = players.get(0).getEmpire();
								
					    		int xMultiplier = point.x /point. x;
					        	int zMultiplier = point.z / point.z;
								
								if (point.owner.equals(Empire.ARATOR)) {
									
									SQEmpire.spawnRectangle(point.x * 16 + (xMultiplier * 6), point.y + 1, point.z * 16 + (zMultiplier * 6), 4, 4, Material.STAINED_GLASS, 11);
									
								} else if (point.owner.equals(Empire.YAVARI)) {
									
									SQEmpire.spawnRectangle(point.x * 16 + (xMultiplier * 6), point.y + 1, point.z * 16 + (zMultiplier * 6), 4, 4, Material.STAINED_GLASS, 10);
									
								} else if (point.owner.equals(Empire.REQUIEM)) {
									
									SQEmpire.spawnRectangle(point.x * 16 + (xMultiplier * 6), point.y + 1, point.z * 16 + (zMultiplier * 6), 4, 4, Material.STAINED_GLASS, 14);
									
								} else {
									
									SQEmpire.spawnRectangle(point.x * 16 + (xMultiplier * 6), point.y + 1, point.z * 16 + (zMultiplier * 6), 4, 4, Material.STAINED_GLASS, 0);
									
								}
								
								point.beingCaptured = false;
								
								Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "ee sudojane " + point.owner.getName() + " has captured " + point.name + " from " + oldOwner.getName());
								
								Bukkit.getWorlds().get(0).getBlockAt(new Location(Bukkit.getWorlds().get(0), point.x * 16 + (xMultiplier * 7), point.y + 2, point.z * 16 + (zMultiplier * 7))).setType(Material.AIR);
								
								point.text.remove();
								point.text = null;
								
								SQEmpire.config.set(point.configPath + ".owner", point.owner.getName());

								List<Marker> markers = new ArrayList<Marker>();
								markers.addAll(SQEmpire.markerSet.getMarkers());
								
								for (Marker marker : markers) {
									
									if (marker.getMarkerID().equals(point.name)) {
										
						            	marker.setDescription("Owner: " + point.owner.getName());
										
									}
									
								}
								
								boolean capturedAll = true;
								
								for (CapturePoint capturePoint : territory.capturePoints) {
									
									if (!capturePoint.owner.equals(point.owner)) {
										
										capturedAll = false;
										
									}
									
								}
								
								if (capturedAll) {
									
									SQEmpire.config.set("regions." + territory.name + ".owner", point.owner.getName());
									
									Empire oldTerritoryOwner = territory.owner;
									territory.owner = point.owner;
									
									for (Territory empireTerritory : SQEmpire.territories) { 
										
							            if (!(SQEmpire.AratorBeachead.equals(territory) || SQEmpire.YavariBeachead.equals(territory) || SQEmpire.RequiemBeachead.equals(territory))) {
							            	
							                ProtectedPolygonalRegion region = (ProtectedPolygonalRegion) SQEmpire.worldGuardPlugin.getRegionManager(Bukkit.getWorlds().get(0)).getRegion(territory.name);
							                
							        		DefaultDomain domain = new DefaultDomain();
							        		
							        		if (territory.owner.equals(Empire.ARATOR) || SQEmpire.isBattleConnected(territory, Empire.ARATOR)) {
							        			
							            		domain.addGroup("arator");
							        			
							        		}
							        		
							        		if (territory.owner.equals(Empire.YAVARI) || SQEmpire.isBattleConnected(territory, Empire.YAVARI)) {
							        			
							            		domain.addGroup("yavari");
							        			
							        		}
							        		
							        		if (territory.owner.equals(Empire.REQUIEM) || SQEmpire.isBattleConnected(territory, Empire.REQUIEM)) {
							        			
							            		domain.addGroup("requiem");
							        			
							        		}
							                
							        		region.setMembers(domain);
							            	
							            }
										
									}
									
									List<AreaMarker> areaMarkers = new ArrayList<AreaMarker>();
									areaMarkers.addAll(SQEmpire.markerSet.getAreaMarkers());
									
									for (AreaMarker areaMarker : areaMarkers) {
										
										if (areaMarker.getMarkerID().equals(territory.name)) {
											
								            areaMarker.setLabel(territory.name, true);
								            areaMarker.setDescription("Name: " + territory.name + "<br/> Owner: " + territory.owner.getName());
								            
								            if (territory.owner.equals(Empire.ARATOR)) {
								            	
								            	areaMarker.setFillStyle(0.5, Integer.parseInt("001eff", 16));
								            	areaMarker.setLineStyle(3, 1, Integer.parseInt("001eff", 16));
								            	
								            } else if (territory.owner.equals(Empire.YAVARI)) {
								            	
								            	areaMarker.setFillStyle(0.5, Integer.parseInt("a800ff", 16));
								            	areaMarker.setLineStyle(3, 1, Integer.parseInt("a800ff", 16));
								            	
								            } else if (territory.owner.equals(Empire.REQUIEM)) {
								            	
								            	areaMarker.setFillStyle(0.5, Integer.parseInt("ff0000", 16));
								            	areaMarker.setLineStyle(3, 1, Integer.parseInt("ff0000", 16));
								            	
								            } else { 
								            	
								            	areaMarker.setFillStyle(0.5, Integer.parseInt("ffffff", 16));
								            	areaMarker.setLineStyle(3, 1, Integer.parseInt("ffffff", 16));
								            	
								            }
											
										}
										
									}

									Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "ee sudojane " + point.owner.getName() + " has captured " + territory.name + " from " + oldTerritoryOwner.getName());

								}

								SQEmpire.getInstance().saveConfig();
								
								point.health.clear();
								
							}
							
						}
						
					}
					
				}
				
			}			
			
		}, 0, 19);
		
	}

}
