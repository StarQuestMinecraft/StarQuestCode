package com.whirlwindgames.dibujaron.sqempire;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Entity;
import org.bukkit.scheduler.BukkitScheduler;
import org.dynmap.markers.AreaMarker;
import org.dynmap.markers.Marker;

import com.sk89q.worldguard.domains.DefaultDomain;
import com.sk89q.worldguard.protection.regions.ProtectedPolygonalRegion;
import com.whirlwindgames.dibujaron.sqempire.database.EmpireDB;
import com.whirlwindgames.dibujaron.sqempire.database.object.EmpirePlayer;
import com.whirlwindgames.dibujaron.sqempire.util.AsyncUtil;
import com.whirlwindgames.dibujaron.sqempire.util.SuperPS;

public class CaptureTask extends Thread {
	
	public void run() {
		
		BukkitScheduler scheduler = Bukkit.getServer().getScheduler();
		
		scheduler.scheduleSyncRepeatingTask(SQEmpire.getInstance(), new Runnable() {
			
			@Override
			public void run() {

				for (Territory territory : SQEmpire.territories) {
					
					for (CapturePoint point : territory.capturePoints) {
						
						if (point.beingCaptured) {
							
							if (((double) point.timeLeft) % 60 == 0) {
								
								List<Marker> markers = new ArrayList<Marker>();
								markers.addAll(SQEmpire.markerSet.getMarkers());
								
								for (Marker marker : markers) {
									
									if (marker.getMarkerID().equals(point.name.replace('_', ' '))) {
										
										List<EmpirePlayer> players = new ArrayList<EmpirePlayer>();
										players.addAll(point.health.keySet());
										
						            	marker.setDescription("Owner: " + point.owner.getName() + "<br/> Being captured by: " + players.get(0).getEmpire().getName() + "<br/> Capture time: " + (point.timeLeft / 60));
									
									}
									
								}
								
							}
							
							point.timeLeft = point.timeLeft - 1;

							
							if (point.timeLeft != 0) {
								
								if (((double) point.timeLeft) % 300 == 0) {
									
									List<EmpirePlayer> players = new ArrayList<EmpirePlayer>();
									players.addAll(point.health.keySet());
									
									Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "eb janesudo " + point.owner.getDarkColor() + point.name.replace("_", " " + point.owner.getDarkColor()) + " will be captured in " + (point.timeLeft / 60) + " minutes by " + players.get(0).getEmpire().getName() + " on " + Bukkit.getWorlds().get(0));
									
								}
								
							}
							
							if (point.timeLeft == 60) {
								
								List<EmpirePlayer> players = new ArrayList<EmpirePlayer>();
								players.addAll(point.health.keySet());
								
								Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "eb janesudo " + point.owner.getDarkColor() + point.name.replace("_", " " + point.owner.getDarkColor()) + " will be captured in 1 minute by " + players.get(0).getEmpire().getName() + " on " + Bukkit.getWorlds().get(0));
								
							}
							
							if (point.timeLeft <= 0) {
								
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
								
								if (!oldOwner.equals(Empire.NONE)) {
									
									Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "eb janesudo " + point.owner.getName() + " has captured " + point.name.replace('_', ' ') + " from " + oldOwner.getName() + " on " + Bukkit.getWorlds().get(0));
									
								} else {
									
									Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "eb janesudo " + point.owner.getName() + " has captured " + point.name.replace('_', ' ') + " on " + Bukkit.getWorlds().get(0));
									
								}							
								
								Bukkit.getWorlds().get(0).getBlockAt(new Location(Bukkit.getWorlds().get(0), point.x * 16 + (xMultiplier * 7), point.y + 2, point.z * 16 + (zMultiplier * 7))).setType(Material.AIR);
								
								List<Entity> entities = point.text.getNearbyEntities(1, 1, 1);
								
								for (Entity entity : entities) {
									
									if (entity instanceof ArmorStand) {
										
										if (entity.getCustomName().startsWith("Health")) {
			
											entity.remove();
			
										}
										
									}
									
								}
								
								point.text.remove();
								point.text = null;
								
								SQEmpire.config.set(point.configPath + ".owner", point.owner.getName());

								point.health.clear();
								
								List<Marker> markers = new ArrayList<Marker>();
								markers.addAll(SQEmpire.markerSet.getMarkers());
								
								for (Marker marker : markers) {
									
									if (marker.getMarkerID().equals(point.name.replace('_', ' '))) {
										
						            	marker.setDescription("Owner: " + point.owner.getName());
										
						            	marker.setMarkerIcon(point.owner.getPointIcon());
						            	
									}
									
								}
								
								point.captures.clear();
								point.captures.addAll(point.newCaptures);
								point.newCaptures.clear();
								
								boolean capturedAll = true;
								
								for (CapturePoint capturePoint : territory.capturePoints) {
									
									if (!capturePoint.owner.equals(point.owner)) {
										
										capturedAll = false;
										
									}
									
								}
								
								if (capturedAll) {
									
									SQEmpire.config.set("regions." + territory.name.replace('_', ' ') + ".owner", point.owner.getName());
									
									Empire oldTerritoryOwner = territory.owner;
									territory.owner = point.owner;
									
									for (Territory empireTerritory : SQEmpire.territories) { 
										
							            if (!(SQEmpire.AratorBeachead.equals(empireTerritory) || SQEmpire.YavariBeachead.equals(empireTerritory) || SQEmpire.RequiemBeachead.equals(empireTerritory))) {
							            	
							                ProtectedPolygonalRegion region = (ProtectedPolygonalRegion) SQEmpire.worldGuardPlugin.getRegionManager(Bukkit.getWorlds().get(0)).getRegion("SQEmpire-" + empireTerritory.name);
							                
							        		DefaultDomain domain = new DefaultDomain();
							        		
							        		if (empireTerritory.owner.equals(Empire.NONE)) {
							        			
							        			domain.addGroup("arator");
							        			domain.addGroup("yavari");
							        			domain.addGroup("requiem");
							        			
							        		} else {
							        			
							            		if (empireTerritory.owner.equals(Empire.ARATOR) || SQEmpire.isBattleConnected(empireTerritory, Empire.ARATOR)) {
							            			
							                		domain.addGroup("arator");
							            			
							            		}
							            		
							            		if (empireTerritory.owner.equals(Empire.YAVARI) || SQEmpire.isBattleConnected(empireTerritory, Empire.YAVARI)) {
							            			
							                		domain.addGroup("yavari");
							            			
							            		}
							            		
							            		if (empireTerritory.owner.equals(Empire.REQUIEM) || SQEmpire.isBattleConnected(empireTerritory, Empire.REQUIEM)) {
							            			
							                		domain.addGroup("requiem");
							            			
							            		}
							        			
							        		}
							                
							        		region.setMembers(domain);
							            	
							            }
										
									}
									
									List<AreaMarker> areaMarkers = new ArrayList<AreaMarker>();
									areaMarkers.addAll(SQEmpire.markerSet.getAreaMarkers());
									
									for (AreaMarker areaMarker : areaMarkers) {
										
										if (areaMarker.getMarkerID().equals(territory.name)) {
											
								            territory.name = territory.name.replace('_', ' ');
								            areaMarker.setLabel(territory.name, true);
								            areaMarker.setDescription("Name: " + territory.name + "<br/> Owner: " + territory.owner.getName());
								            territory.name = territory.name.replace(' ', '_');
								            
								            if (territory.owner.equals(Empire.ARATOR)) {
								            	
								            	areaMarker.setFillStyle(0.35, Integer.parseInt("001eff", 16));
								            	areaMarker.setLineStyle(3, 1, Integer.parseInt("001eff", 16));
								            	
								            } else if (territory.owner.equals(Empire.YAVARI)) {
								            	
								            	areaMarker.setFillStyle(0.35, Integer.parseInt("a800ff", 16));
								            	areaMarker.setLineStyle(3, 1, Integer.parseInt("a800ff", 16));
								            	
								            } else if (territory.owner.equals(Empire.REQUIEM)) {
								            	
								            	areaMarker.setFillStyle(0.35, Integer.parseInt("ff0000", 16));
								            	areaMarker.setLineStyle(3, 1, Integer.parseInt("ff0000", 16));
								            	
								            } else { 
								            	
								            	areaMarker.setFillStyle(0.35, Integer.parseInt("ffffff", 16));
								            	areaMarker.setLineStyle(3, 1, Integer.parseInt("ffffff", 16));
								            	
								            }
											
										}
										
									}
									
									if (!oldTerritoryOwner.equals(Empire.NONE)) {
										
										Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "eb janesudo " + point.owner.getName() + " has captured " + territory.name.replace('_', ' ') + " from " + oldTerritoryOwner.getName() + " on " + Bukkit.getWorlds().get(0));
										
									} else {
										
										Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "eb janesudo " + point.owner.getName() + " has captured " + territory.name.replace('_', ' ') + " on " + Bukkit.getWorlds().get(0));
										
									}
									
								}

								SQEmpire.getInstance().saveConfig();
								
								point.health.clear();
								
								AsyncUtil.runAsync(new Runnable() {
					        		
					        		public void run () {
					        			
					        			int[] count = new int[]{0, 0, 0, 0};
					        			
					        			for (Territory territory : SQEmpire.territories) {
					        				
					        				count[territory.owner.id] = count[territory.owner.id] ++;
					        				
					        			}
					        			
					        			SuperPS ps = new SuperPS(EmpireDB.prepareStatement("INSERT INTO minecraft.empire_territories(planet, empire0, empire1, empire2, empire3) VALUES (?, ?, ?, ?, ?) ON DUPLICATE KEY UPDATE empire0 = VALUES (empire0), empire1 = VALUES (empire1), empire2 = VALUES (empire2), empire3 = VALUES (empire3)"),5);

					        			ps.setString(1, Bukkit.getServerName());
					        			ps.setInt(1, count[0]);
					        			ps.setInt(2, count[1]);
					        			ps.setInt(3, count[2]);
					        			ps.setInt(4, count[3]);
					        			
					        			ps.executeAndClose();
					        			
					        		}
					        		
					        	});
								
							}
							
						}
						
					}
					
				}
				
			}			
			
		}, 0, 19);
		
	}

}
