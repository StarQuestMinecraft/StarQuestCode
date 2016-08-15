package com.whirlwindgames.dibujaron.sqempire;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import net.countercraft.movecraft.bungee.BungeePlayerHandler;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.BlockCommandSender;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitScheduler;
import org.dynmap.markers.Marker;
import org.dynmap.markers.PolyLineMarker;

import com.gmail.nossr50.datatypes.skills.SkillType;
import com.massivecraft.factions.entity.FactionColl;
import com.massivecraft.factions.entity.MPlayer;
import com.sk89q.worldedit.BlockVector;
import com.sk89q.worldedit.BlockVector2D;
import com.sk89q.worldguard.protection.ApplicableRegionSet;
import com.sk89q.worldguard.protection.flags.DefaultFlag;
import com.sk89q.worldguard.protection.flags.StateFlag.State;
import com.sk89q.worldguard.protection.regions.ProtectedCuboidRegion;
import com.sk89q.worldguard.protection.regions.ProtectedRegion;
import com.whirlwindgames.dibujaron.sqempire.database.object.EmpirePercentage;
import com.whirlwindgames.dibujaron.sqempire.database.object.EmpirePlayer;
import com.whirlwindgames.dibujaron.sqempire.util.AsyncUtil;

public class EmpireCommand implements CommandExecutor{
	
	public static int STARTING_CASH = 10000;

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, final String[] args) {
		if(cmd.getName().equalsIgnoreCase("playerSendEmpire") && (sender instanceof ConsoleCommandSender || sender instanceof BlockCommandSender)){
			Player p = Bukkit.getPlayer(args[0]);
			EmpirePlayer ep = EmpirePlayer.getOnlinePlayer(p);
			Empire e = ep.getEmpire();
			if(e == Empire.ARATOR){
				//SQEmpire.permission.playerAddGroup(p,"Arator0");
				BungeePlayerHandler.sendPlayer(p, "AratorSystem", "AratorSystem", 2598, 100, 1500);
				//Bukkit.dispatchCommand(Bukkit.getConsoleSender(), 
						//"eb janesudo Aratorians, please welcome your newest member " + p.getName() + "!");
			} else if(e == Empire.REQUIEM){
				//SQEmpire.permission.playerAddGroup(p,"Requiem0");
				BungeePlayerHandler.sendPlayer(p, "QuillonSystem", "QuillonSystem", 1375, 100, -2381);
				//Bukkit.dispatchCommand(Bukkit.getConsoleSender(), 
						//"eb janesudo Requiem, please welcome your newest member " + p.getName() + "!");
			} else if(e == Empire.YAVARI){
				//SQEmpire.permission.playerAddGroup(p,"Yavari0");
				BungeePlayerHandler.sendPlayer(p, "YavarSystem", "YavarSystem", 0, 231, 2500);
				//Bukkit.dispatchCommand(Bukkit.getConsoleSender(), 
				//		"eb janesudo Yavari, please welcome your newest member " + p.getName() + "!");
			} else if(e == Empire.NONE){
				p.teleport(p.getWorld().getSpawnLocation());
			}
			return true;
		} else if(cmd.getName().equalsIgnoreCase("empirereset") && sender.hasPermission("sqempire.reset")){
			
			boolean full = false;
			
			if (args.length > 1) {
				
				if (args[1].equals("full")) {
					
					full = true;
					
				}
				
			}
			
			Player p = Bukkit.getPlayer(args[0]);
			EmpirePlayer ep = EmpirePlayer.getOnlinePlayer(p);
			
			Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "ee pp user " + p.getUniqueId().toString() + " removegroup " + ep.getEmpire().getName() + "0");
			Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "ee pp user " + p.getUniqueId().toString() + " removegroup " + ep.getEmpire().getName() + "1");
			Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "ee pp user " + p.getUniqueId().toString() + " removegroup " + ep.getEmpire().getName() + "2");
			Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "ee pp user " + p.getUniqueId().toString() + " removegroup " + ep.getEmpire().getName() + "3");
			Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "ee pp user " + p.getUniqueId().toString() + " removegroup " + ep.getEmpire().getName() + "4");
			Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "ee pp user " + p.getUniqueId().toString() + " removegroup " + ep.getEmpire().getName() + "5");

			Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "ee pp user " + p.getUniqueId().toString() + " addgroup Guest");
			
			ep.setEmpire(Empire.NONE);
			ep.publishData();
			
			BungeePlayerHandler.sendPlayer(p, "CoreSystem", "CoreSystem", 0, 102, 0);
			
			if (full) {
				
				SQEmpire.economy.withdrawPlayer(p, SQEmpire.economy.getBalance(p));
				BungeePlayerHandler.wipePlayerInventory(p);
				
				MPlayer.get(p).setFaction(FactionColl.get().getNone());
				
			}

		} else {
			
			if(!(sender instanceof Player)){
				sender.sendMessage("The empire command can only be run ingame.");
			}
			final Player p = (Player) sender;
			AsyncUtil.runAsync(new Runnable(){
				public void run(){
					processEmpireCommand(p, args);
				}
			});
			
		}
		
		return true;
		
	}
	
	private void processEmpireCommand(Player p, String[] args){
		
		if(args.length == 0){
			
			EmpirePlayer ep = EmpirePlayer.getOnlinePlayer(p);
			
			p.sendMessage(ChatColor.GOLD + "Your empire is " + ep.getEmpire().getName() + ".");
			
			p.sendMessage(ChatColor.GOLD + "/empire help - Displays this");
			p.sendMessage(ChatColor.GOLD + "/empire stats <empire> - Displays the stats of an empire");
			p.sendMessage(ChatColor.GOLD + "/empire join <empire> - Join an empire");
			
			if (p.hasPermission("SQEmpire.manageControlPoints")) {
				
				p.sendMessage(ChatColor.GOLD + "/empire point - manages control points");
				p.sendMessage(ChatColor.GOLD + "/empire connection - manages battle connections");
				
			}
			
			if (p.hasPermission("SQEmpire.capture")) {
				
				p.sendMessage(ChatColor.GOLD + "/empire capture - captures a control point");
				
			}
			
			if (p.hasPermission("SQEmpire.checkEmpire")) {
				
				p.sendMessage(ChatColor.GOLD + "/empire check <player> - checks the empire of a player");
				
			}
			
			if (p.hasPermission("SQEmpire.reloadEmpire")) {
				
				p.sendMessage(ChatColor.GOLD + "/empire reload - reloads the config");
				
			}
			
			p.sendMessage(ChatColor.GOLD + "---------------");
			
		} else {
			
			if (args[0].equalsIgnoreCase("help")) {

				EmpirePlayer ep = EmpirePlayer.getOnlinePlayer(p);
				
				p.sendMessage(ChatColor.GOLD + "Your empire is " + ep.getEmpire().getName() + ".");
				
				p.sendMessage(ChatColor.GOLD + "/empire help - Displays this");
				p.sendMessage(ChatColor.GOLD + "/empire stats <empire> - Displays the stats of an empire");
				p.sendMessage(ChatColor.GOLD + "/empire join <empire> - Join an empire");
				
				if (p.hasPermission("SQEmpire.manageControlPoints")) {
					
					p.sendMessage(ChatColor.GOLD + "/empire point - manages control points");
					p.sendMessage(ChatColor.GOLD + "/empire connection - manages battle connections");
					
				}
				
				if (p.hasPermission("SQEmpire.capture")) {
					
					p.sendMessage(ChatColor.GOLD + "/empire capture - captures a control point");
					
				}
				
				if (p.hasPermission("SQEmpire.checkEmpire")) {
					
					p.sendMessage(ChatColor.GOLD + "/empire check <player> - checks the empire of a player");
					
				}
				
				if (p.hasPermission("SQEmpire.reloadEmpire")) {
					
					p.sendMessage(ChatColor.GOLD + "/empire reload - reloads the config");
					
				}
				
				p.sendMessage(ChatColor.GOLD + "---------------");
				
			} else if (args[0].equalsIgnoreCase("stats")) {
				
				if (args.length == 1) {
					
					EmpirePlayer ep = EmpirePlayer.getOnlinePlayer(p);
					Empire empire = ep.getEmpire();
					
					if (empire != Empire.NONE) {
						
						p.sendMessage(empire.light + "Empire: " + empire.dark + empire.name);
						p.sendMessage(empire.light + "Current Boosters:");
						
						for (String planet : SQEmpire.dominantEmpires.keySet()) {
							
							if (SQEmpire.dominantEmpires.get(planet).equals(empire)) {
								
								for (SkillType skill : SQEmpire.mcmmoBoosters.get(planet)) {
									
									p.sendMessage(empire.dark + " - " + skill.getName());
									
								}
								
							}
							
						}
						
						p.sendMessage(empire.light + "Owns Planets:");
						
						for (String planet : SQEmpire.dominantEmpires.keySet()) {
							
							if (SQEmpire.dominantEmpires.get(planet).equals(empire)) {
								
								p.sendMessage(empire.dark + " - " + planet);
								
							}
							
						}
						
						p.sendMessage(ChatColor.GOLD + "---------------");
						
					} else {
						
						p.sendMessage(ChatColor.RED + "You are not in an empire");
						
					}

				} else {
					
					Empire empire = Empire.fromString(args[1]);
					
					if (empire != Empire.NONE) {
						
						p.sendMessage(empire.light + "Empire: " + empire.dark + empire.name);
						p.sendMessage(empire.light + "Current Boosters:");
						
						for (String planet : SQEmpire.dominantEmpires.keySet()) {
							
							if (SQEmpire.dominantEmpires.get(planet).equals(empire)) {
								
								for (SkillType skill : SQEmpire.mcmmoBoosters.get(planet)) {
									
									p.sendMessage(empire.dark + " - " + skill.getName());
									
								}
								
							}
							
						}
						
						p.sendMessage(empire.light + "Owns Planets:");
						
						for (String planet : SQEmpire.dominantEmpires.keySet()) {
							
							if (SQEmpire.dominantEmpires.get(planet).equals(empire)) {
								
								p.sendMessage(empire.dark + " - " + planet);
								
							}
							
						}
						
						p.sendMessage(ChatColor.GOLD + "---------------");
						
					} else {
						
						p.sendMessage(ChatColor.RED + "That is not an empire");
						
					}
					
				}
				
			} else if (args[0].equalsIgnoreCase("join")) {
			
				cmdJoin(p, args);
			
			} else if (args[0].equalsIgnoreCase("check")) {
				
				if (p.hasPermission("SQEmpire.checkEmpire")) {
					
					if (args.length == 1) {
						
						p.sendMessage(ChatColor.RED + "Please input a player to check");
						
					} else {
						
						OfflinePlayer player = Bukkit.getOfflinePlayer(args[1]);
						
						if (player.hasPlayedBefore()) {
							
							p.sendMessage(ChatColor.GOLD + "That player is in " + EmpirePlayer.getFromUUID(player.getUniqueId()).getEmpire());
							
						} else {
							
							p.sendMessage(ChatColor.RED + "That player does not exsist or has not been to this server before");
							
						}

					}
					
				}
				
			} else if (args[0].equalsIgnoreCase("reload")) {
				
				if (p.hasPermission("SQEmpire.reloadEmpire")) {
					
					SQEmpire.getInstance().reloadConfig();
					SQEmpire.config = SQEmpire.getInstance().getConfig();
					
					SQEmpire.getInstance().onEnable();;
					
					p.sendMessage(ChatColor.GOLD + "Config reloaded");
					
				}
				
			} else if (args[0].equalsIgnoreCase("point")) {
				
				if (p.hasPermission("SQEmpire.manageControlPoints")) {
					
					if (args.length == 1) {
						
						p.sendMessage(ChatColor.GOLD + "/empire point list - lists all control points in the current territory");
						p.sendMessage(ChatColor.GOLD + "/empire point add - adds a control point");
						p.sendMessage(ChatColor.GOLD + "/empire point remove <number> - removes a control point");
						p.sendMessage(ChatColor.GOLD + "---------------");
						
					} else {
						
						if (args[1].equalsIgnoreCase("add")) {
							
							ApplicableRegionSet regions = SQEmpire.worldGuardPlugin.getRegionManager(Bukkit.getWorlds().get(0)).getApplicableRegions(p.getLocation());
							
							if (regions.size() == 0) {
								
								p.sendMessage(ChatColor.RED + "You must be in a region to do that");
								
								return;
									
							} else {
								
								List<ProtectedRegion> protectedRegions = new ArrayList<ProtectedRegion>();
								protectedRegions.addAll(regions.getRegions());
								
								for (ProtectedRegion region : protectedRegions) {
									
									for (Territory territory : SQEmpire.territories) {
										
										if (("SQEmpire-" + territory.name).equalsIgnoreCase(region.getId())) {
											
											int capturePoints = 0;
											
											if (SQEmpire.config.getConfigurationSection("regions." + territory.name.replace('_', ' ') + ".capture points") != null) {
												
												capturePoints = SQEmpire.config.getConfigurationSection("regions." + territory.name.replace('_', ' ') + ".capture points").getKeys(false).size();
												
											}
											
											territory.name = territory.name.replace('_', ' ');											
											SQEmpire.config.set("regions." + territory.name + ".capture points.point" + (capturePoints + 1) + ".owner", "None");
											SQEmpire.config.set("regions." + territory.name + ".capture points.point" + (capturePoints + 1) + ".position", p.getLocation().getChunk().getX() + "," + p.getLocation().add(0, -2, 0).getBlockY() + "," + p.getLocation().getChunk().getZ());
											territory.name = territory.name.replace(' ', '_');	
											SQEmpire.getInstance().saveConfig();
											
											final CapturePoint capturePoint = new CapturePoint();
							        		
							        		capturePoint.owner = Empire.fromString("None");
							        		capturePoint.name = territory.name + "-" + (capturePoints + 1);
							        		
							        		capturePoint.configPath = "regions." + territory.name.replace('_', ' ') + ".capture points.point" + (capturePoints + 1);
							        		
							        		capturePoint.x = p.getLocation().getChunk().getX();
							        		capturePoint.y = p.getLocation().add(0, -2, 0).getBlockY();
							        		capturePoint.z = p.getLocation().getChunk().getZ();
							        		
							        		territory.capturePoints.add(capturePoint);
							        		
							        		final int xMultiplier = capturePoint.x / capturePoint.x;
							            	final int zMultiplier = capturePoint.z / capturePoint.z;
							            	
							            	ProtectedCuboidRegion pointRegion = (ProtectedCuboidRegion) SQEmpire.worldGuardPlugin.getRegionManager(Bukkit.getWorlds().get(0)).getRegion("SQEmpire-" + capturePoint.name);
							            	
							                if (pointRegion != null) {
							                	
							                	List<BlockVector2D> points = new ArrayList<BlockVector2D>();
							                	
							                	points.add(new BlockVector2D(capturePoint.x, capturePoint.z));
							                	points.add(new BlockVector2D(capturePoint.x + 16, capturePoint.z + 16));
							                	
							                	if (!pointRegion.getPoints().equals(points)) {
							                		
							                		SQEmpire.worldGuardPlugin.getRegionManager(Bukkit.getWorlds().get(0)).removeRegion(capturePoint.name);
							                		SQEmpire.worldGuardPlugin.getRegionManager(Bukkit.getWorlds().get(0)).addRegion(new ProtectedCuboidRegion("SQEmpire-" + capturePoint.name, new BlockVector(capturePoint.x * 16, 0, capturePoint.z * 16), new BlockVector((capturePoint.x * 16) + (xMultiplier * 16), Bukkit.getWorlds().get(0).getMaxHeight(), (capturePoint.z * 16) + (zMultiplier * 16))));
							                		
							                	}
							                	
							                } else {
							                	
							                	SQEmpire.worldGuardPlugin.getRegionManager(Bukkit.getWorlds().get(0)).addRegion(new ProtectedCuboidRegion("SQEmpire-" + capturePoint.name, new BlockVector(capturePoint.x * 16, 0, capturePoint.z * 16), new BlockVector((capturePoint.x * 16) + (xMultiplier * 16), Bukkit.getWorlds().get(0).getMaxHeight(), (capturePoint.z * 16) + (zMultiplier * 16))));
							                	
							                }
							                
							                pointRegion = (ProtectedCuboidRegion) SQEmpire.worldGuardPlugin.getRegionManager(Bukkit.getWorlds().get(0)).getRegion("SQEmpire-" + capturePoint.name);
					                		
							                pointRegion.setFlag(DefaultFlag.PVP, State.ALLOW);
							                pointRegion.setFlag(DefaultFlag.GHAST_FIREBALL, State.DENY);
							                pointRegion.setFlag(DefaultFlag.OTHER_EXPLOSION, State.DENY);
							                pointRegion.setFlag(DefaultFlag.TNT, State.DENY);
							            	
							            	Marker marker = SQEmpire.markerSet.createMarker(territory.name.replace('_', ' ') + "-" + (capturePoints + 1), territory.name.replace('_', ' ') + "-" + (capturePoints + 1), Bukkit.getWorlds().get(0).getName(), (double) capturePoint.x * 16 + (xMultiplier * 7.5), (double) capturePoint.y, (double) capturePoint.z * 16 + (zMultiplier * 7.5), SQEmpire.markerAPI.getMarkerIcon("temple"), false);
							            	marker.setDescription("Owner: " + capturePoint.owner.getName());
							            	
							            	BukkitScheduler scheduler = Bukkit.getScheduler();
							            	
							            	scheduler.scheduleSyncDelayedTask(SQEmpire.getInstance(), new Runnable() {
							            		
							            		public void run() {
							            			
							            			SQEmpire.spawnRectangle(capturePoint.x * 16 + (xMultiplier * 6), capturePoint.y, capturePoint.z * 16 + (zMultiplier * 6), 4, 4, Material.IRON_BLOCK, 0);
							                    	SQEmpire.spawnRectangle(capturePoint.x * 16 + (xMultiplier * 6), capturePoint.y - 2, capturePoint.z * 16 + (zMultiplier * 6), 4, 4, Material.SLIME_BLOCK, 0); 
							            			
									            	SQEmpire.spawnBeacon(capturePoint.x * 16 + (xMultiplier * 7), capturePoint.y, capturePoint.z * 16 + (zMultiplier * 7));
									            	SQEmpire.spawnBeacon(capturePoint.x * 16 + (xMultiplier * 8), capturePoint.y, capturePoint.z * 16 + (zMultiplier * 7));
									            	SQEmpire.spawnBeacon(capturePoint.x * 16 + (xMultiplier * 7), capturePoint.y, capturePoint.z * 16 + (zMultiplier * 8));
									            	SQEmpire.spawnBeacon(capturePoint.x * 16 + (xMultiplier * 8), capturePoint.y, capturePoint.z * 16 + (zMultiplier * 8));
									    			
									    			if (capturePoint.owner.equals(Empire.ARATOR)) {
									    				
									    				SQEmpire.spawnRectangle(capturePoint.x * 16 + (xMultiplier * 6), capturePoint.y + 1, capturePoint.z * 16 + (zMultiplier * 6), 4, 4, Material.STAINED_GLASS, 11);
									    				
									    			} else if (capturePoint.owner.equals(Empire.YAVARI)) {
									    				
									    				SQEmpire.spawnRectangle(capturePoint.x * 16 + (xMultiplier * 6), capturePoint.y + 1, capturePoint.z * 16 + (zMultiplier * 6), 4, 4, Material.STAINED_GLASS, 10);
									    				
									    			} else if (capturePoint.owner.equals(Empire.REQUIEM)) {
									    				
									    				SQEmpire.spawnRectangle(capturePoint.x * 16 + (xMultiplier * 6), capturePoint.y + 1, capturePoint.z * 16 + (zMultiplier * 6), 4, 4, Material.STAINED_GLASS, 14);
									    				
									    			} else {
									    				
									    				SQEmpire.spawnRectangle(capturePoint.x * 16 + (xMultiplier * 6), capturePoint.y + 1, capturePoint.z * 16 + (zMultiplier * 6), 4, 4, Material.STAINED_GLASS, 0);
									    				
									    			}
									    			
									    			SQEmpire.spawnRectangle(capturePoint.x * 16 + (xMultiplier * 6), capturePoint.y + 2, capturePoint.z * 16 + (zMultiplier * 6), 4, 4, Material.AIR, 0);
									    			SQEmpire.spawnRectangle(capturePoint.x * 16 + (xMultiplier * 6), capturePoint.y + 3, capturePoint.z * 16 + (zMultiplier * 6), 4, 4, Material.AIR, 0);
									    			SQEmpire.spawnRectangle(capturePoint.x * 16 + (xMultiplier * 6), capturePoint.y + 4, capturePoint.z * 16 + (zMultiplier * 6), 4, 4, Material.AIR, 0);
									    			
							            		}
							            		
							            	});
							            	
							    			p.sendMessage(ChatColor.GOLD + "Capture Point created");
							    			
							    			return;
											
										}
										
									}
									
								}
								
							}
							
							p.sendMessage(ChatColor.RED + "You must be in a region to do that");
							
						} else if (args[1].equalsIgnoreCase("list")) {
							
							ApplicableRegionSet regions = SQEmpire.worldGuardPlugin.getRegionManager(Bukkit.getWorlds().get(0)).getApplicableRegions(p.getLocation());
							
							if (regions.size() == 0) {
								
								p.sendMessage(ChatColor.RED + "You must be in a region to do that");
								
								return;
									
							} else {
								
								List<ProtectedRegion> protectedRegions = new ArrayList<ProtectedRegion>();
								protectedRegions.addAll(regions.getRegions());
								
								for (ProtectedRegion region : protectedRegions) {
									
									for (Territory territory : SQEmpire.territories) {
										
										if (("SQEmpire-" + territory.name).equalsIgnoreCase(region.getId())) {
											
											for (int i = 0; i < territory.capturePoints.size(); i ++) {
												
												CapturePoint capturePoint = territory.capturePoints.get(i);
												
								        		final int xMultiplier = capturePoint.x / capturePoint.x;
								            	final int zMultiplier = capturePoint.z / capturePoint.z;
												
												p.sendMessage(ChatColor.GOLD + "" + (i + 1) + "-" + "x:" + (capturePoint.x * 16 + (xMultiplier * 6)) + ", y:" + capturePoint.y + ", z:" + (capturePoint.z * 16 + (zMultiplier * 6)));

											}
											
											p.sendMessage(ChatColor.GOLD + "---------------");
							    			
							    			return;
											
										}
										
									}
									
								}
								
							}
							
							p.sendMessage(ChatColor.RED + "You must be in a region to do that");
							
						} else if (args[1].equalsIgnoreCase("remove")) {
							
							ApplicableRegionSet regions = SQEmpire.worldGuardPlugin.getRegionManager(Bukkit.getWorlds().get(0)).getApplicableRegions(p.getLocation());
							
							if (regions.size() == 0) {
								
								p.sendMessage(ChatColor.RED + "You must be in a region to do that");
								
								return;
									
							} else {
								
								List<ProtectedRegion> protectedRegions = new ArrayList<ProtectedRegion>();
								protectedRegions.addAll(regions.getRegions());
								
								for (ProtectedRegion region : protectedRegions) {
									
									for (Territory territory : SQEmpire.territories) {
										
										if (("SQEmpire-" + territory.name).equalsIgnoreCase(region.getId())) {
											
											for (int i = 0; i < territory.capturePoints.size(); i ++) {
												
												if ((i + 1) == Integer.parseInt(args[2])) {
													
													final CapturePoint capturePoint = territory.capturePoints.get(i);
													
													SQEmpire.worldGuardPlugin.getRegionManager(Bukkit.getWorlds().get(0)).removeRegion("SQEmpire-" + capturePoint.name);

													SQEmpire.config.set(capturePoint.configPath, null);
													SQEmpire.getInstance().saveConfig();
													
													List<Marker> markers = new ArrayList<Marker>();
													markers.addAll(SQEmpire.markerSet.getMarkers());
													
													for (Marker marker : markers) {
														
														if (marker.getMarkerID().equals(territory.name.replace('_', ' ') + "-" + (i + 1))) {
															
															marker.deleteMarker();
															
														}
														
													}
													
													territory.capturePoints.remove(capturePoint);
														
									        		final int xMultiplier = capturePoint.x / capturePoint.x;
									            	final int zMultiplier = capturePoint.z / capturePoint.z;
													
													Bukkit.getScheduler().scheduleSyncDelayedTask(SQEmpire.getInstance(), new Runnable() {
									            		
									            		public void run() {
									            			
									            			SQEmpire.spawnRectangle(capturePoint.x * 16 + (xMultiplier * 6), capturePoint.y - 2, capturePoint.z * 16 + (zMultiplier * 6), 4, 4, Material.AIR, 0);
									                    	SQEmpire.spawnRectangle(capturePoint.x * 16 + (xMultiplier * 6), capturePoint.y - 1, capturePoint.z * 16 + (zMultiplier * 6), 4, 4, Material.AIR, 0); 
									                    	SQEmpire.spawnRectangle(capturePoint.x * 16 + (xMultiplier * 6), capturePoint.y, capturePoint.z * 16 + (zMultiplier * 6), 4, 4, Material.AIR, 0); 
									                    	SQEmpire.spawnRectangle(capturePoint.x * 16 + (xMultiplier * 6), capturePoint.y + 1, capturePoint.z * 16 + (zMultiplier * 6), 4, 4, Material.AIR, 0); 
									                    	
									            		}
									            		
									            	});
													
													p.sendMessage(ChatColor.GOLD + "The control point has been removed");
													return;
													
												}
												
											}
												
											p.sendMessage(ChatColor.RED + "That is not a valid control point");
							    			return;
											
										}
										
									}
									
								}
								
							}
							
							p.sendMessage(ChatColor.RED + "You must be in a region to do that");
							
						}
						
					}
					
				}
			
			} else if (args[0].equalsIgnoreCase("connection")) {
						
				if (p.hasPermission("SQEmpire.manageControlPoints")) {
						
					if (args.length == 1) {
						
						p.sendMessage(ChatColor.GOLD + "/empire connection list - lists all of the connections");
						p.sendMessage(ChatColor.GOLD + "/empire connection add - adds or completes a connection");
						p.sendMessage(ChatColor.GOLD + "/empire connection remove <name> - removes a connection");
						p.sendMessage(ChatColor.GOLD + "---------------");
						
					} else {
						
						if (args[1].equalsIgnoreCase("add")) {
							
							ApplicableRegionSet regions = SQEmpire.worldGuardPlugin.getRegionManager(Bukkit.getWorlds().get(0)).getApplicableRegions(p.getLocation());
							
							if (regions.size() == 0) {
								
								p.sendMessage(ChatColor.RED + "You must be in a region to do that");
								
								return;
									
							} else {
								
								List<ProtectedRegion> protectedRegions = new ArrayList<ProtectedRegion>();
								protectedRegions.addAll(regions.getRegions());
								
								for (ProtectedRegion region : protectedRegions) {
									
									for (Territory territory : SQEmpire.territories) {
										
										if (("SQEmpire-" + territory.name).equalsIgnoreCase(region.getId())) {
											
											if (!SQEmpire.territory1.containsKey(p.getName())) {
												
												SQEmpire.territory1.put(p.getName(), territory);
												SQEmpire.territoryX.put(p.getName(), p.getLocation().getBlockX());
												SQEmpire.territoryZ.put(p.getName(), p.getLocation().getBlockZ());
												
												p.sendMessage(ChatColor.GOLD + "Use '/empire connection add' at the endpoint");
												
												return;
												
											} else {
												
												if (SQEmpire.territory1.get(p.getName()).equals(territory)) {
													
													p.sendMessage(ChatColor.RED + "You must select another territory");
													
													return;
													
												} else {
													
													BattleConnection connection = new BattleConnection();
													
													connection.territory1 = SQEmpire.territory1.get(p.getName());
													connection.territory2 = territory;
													
													connection.x1 = SQEmpire.territoryX.get(p.getName());
													connection.z1 = SQEmpire.territoryZ.get(p.getName());
													
													connection.x2 = p.getLocation().getBlockX();
													connection.z2 = p.getLocation().getBlockZ();
													
													SQEmpire.config.set("connections." + connection.getName() + ".region 1", connection.territory1.name.replace('_', ' '));
													SQEmpire.config.set("connections." + connection.getName() + ".region 2", connection.territory2.name.replace('_', ' '));
													SQEmpire.config.set("connections." + connection.getName() + ".x1", connection.x1);
													SQEmpire.config.set("connections." + connection.getName() + ".z1", connection.z1);
													SQEmpire.config.set("connections." + connection.getName() + ".x2", connection.x2);
													SQEmpire.config.set("connections." + connection.getName() + ".z2", connection.z2);
													
													SQEmpire.getInstance().saveConfig();
													
													SQEmpire.connections.add(connection);
													
										        	PolyLineMarker marker = SQEmpire.markerSet.createPolyLineMarker(connection.getName(), connection.getName(), false, Bukkit.getWorlds().get(0).getName(), new double[]{connection.x1, connection.x2}, new double[]{100.0, 100.0}, new double[]{connection.z1, connection.z2}, false);
										        	
										        	marker.setLabel("Battle Connection");
										        	marker.setLineStyle(3, 1, Integer.parseInt("ffff00", 16));
												
													SQEmpire.territory1.remove(p.getName());
													SQEmpire.territoryX.remove(p.getName());
													SQEmpire.territoryZ.remove(p.getName());
										        	
										        	p.sendMessage(ChatColor.GOLD + "Battle Connection created");
										        	
										        	return;
										        	
												}

											}
											
										}
										
									}
									
								}
								
							}
							
							p.sendMessage(ChatColor.RED + "You must be in a territory to do that");
							
						} else if (args[1].equalsIgnoreCase("list")) {
							
							for (BattleConnection connection : SQEmpire.connections) {
							
								p.sendMessage(connection.getName());

								
							}
							
							p.sendMessage(ChatColor.GOLD + "---------------");
							
						} else if (args[1].equalsIgnoreCase("remove")) {
									
							String name = "";
							
							for (int i = 2; i < args.length; i ++) {
								
								if (i != 2) {
									
									name = name + " ";
									
								}
								
								name = name + args[i];
								
							}
							
							for (BattleConnection connection : SQEmpire.connections) {
										
								if (connection.getName().equalsIgnoreCase(name)) {
								
									SQEmpire.config.set("connections." + connection.getName(), null);
									SQEmpire.getInstance().saveConfig();
									
									List<PolyLineMarker> markers = new ArrayList<PolyLineMarker>();
									markers.addAll(SQEmpire.markerSet.getPolyLineMarkers());
									
									for (PolyLineMarker marker : markers) {
										
										if (marker.getMarkerID().equals(connection.getName())) {
											
											marker.deleteMarker();
											
										}
										
									}
									
									SQEmpire.connections.remove(connection);
									connection = null;
									
									p.sendMessage(ChatColor.GOLD + "The battle connection has been removed");
									return;
									
								}
								
							}
							
							p.sendMessage(ChatColor.RED + "That is not a valid battle connection");
			    			return;
			    			
						}
						
					}
							
				}
				
			} else if (args[0].equalsIgnoreCase("capture") || args[0].equalsIgnoreCase("claim")) {
				
				if (p.hasPermission("SQEmpire.capture")) {
					
					boolean capturing = false;
					
					for (Territory territory : SQEmpire.territories) {
						
						for (CapturePoint point : territory.capturePoints) {
							
							for (EmpirePlayer ep : point.health.keySet()) {
								
								if (ep.getUUID().equals(p.getUniqueId())) {
									
									capturing = true;
									
								}
								
							}
							
						}
						
					}
					
					if (capturing) {
						
						p.sendMessage(ChatColor.RED + "You cannot capture more than one control point at a time");
						
					} else {
						
						ApplicableRegionSet regions = SQEmpire.worldGuardPlugin.getRegionManager(Bukkit.getWorlds().get(0)).getApplicableRegions(p.getLocation());
						
						if (regions.size() == 0) {
							
							p.sendMessage(ChatColor.RED + "You must be on a control point to do that");
							
							return;
							
						} else {
							
							List<ProtectedRegion> protectedRegions = new ArrayList<ProtectedRegion>();
							protectedRegions.addAll(regions.getRegions());
							
							for (ProtectedRegion region : protectedRegions) {
								
								for (Territory territory : SQEmpire.territories) {
									
									if (("SQEmpire-" + territory.name).equalsIgnoreCase(region.getId())) {
										
										if (SQEmpire.AratorBeachead.equals(territory) || SQEmpire.YavariBeachead.equals(territory) || SQEmpire.RequiemBeachead.equals(territory)) {
											
											p.sendMessage(ChatColor.RED + "You cannot capture a beachead");
											
											return;
											
										} else {
										
											EmpirePlayer ep = EmpirePlayer.getOnlinePlayer(p);
											
											if (SQEmpire.isBattleConnected(territory, ep.getEmpire()) || territory.owner.equals(ep.getEmpire())) {
												
												int chunkX = p.getLocation().getChunk().getX();
												int chunkZ = p.getLocation().getChunk().getZ();
												
												int y = p.getLocation().getBlockY();
												
												for (CapturePoint capturePoint : territory.capturePoints) {
													
													if (chunkX == capturePoint.x && chunkZ == capturePoint.z && y > capturePoint.y + 1 && y < capturePoint.y + 3) {
	
														if (!capturePoint.owner.equals(ep.getEmpire())) {
	
											    			if (capturePoint.capture(p)) {
											    				
											    				p.sendMessage(ChatColor.GOLD + "Capturing point");
											    				
											    			} else {
											    				
											    				p.sendMessage(ChatColor.GOLD + "You cannot capture anymore capture points");
											    				
											    			}
											    			
											    			return;
															
														} else {
															
															p.sendMessage(ChatColor.RED + "You cannot capture your own control point");
															
															return;
															
														}
														
													}
													
												}
												
											} else {
												
												p.sendMessage(ChatColor.RED + "This territory is not battle connected to your empire");
												
												return;
												
											}
											
										}
										
									}
									
								}
								
							}
							
						}
						
						p.sendMessage(ChatColor.RED + "You must be in a region to do that");
						
					}
					
				}
	
			} else {
				
				p.sendMessage("Do /empire help");
			
			}
			
		}
		return;
		/*if(args.length == 0){
			cmdHelp(p,args);
		} else {
			String argKey = args[0];
			switch(argKey){
			case "stats":
			case "statistics":
			case "power":
			case "s":
			case "p":
				cmdStats(p, args);
			case "join":
			case "j":
				cmdJoin(sender, args);
			case "claim":
			case "c":
				cmdClaim(sender, args);
			case "leave":
			case "l":
				cmdLeave(sender, args);
			default:
				cmdHelp(sender);
			}
		}*/
	}
	
	private void cmdJoin(Player p, String[] args){
		EmpirePlayer ep = EmpirePlayer.getOnlinePlayer(p);
		
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.MONTH, -1);
		
		if (ep.getEmpire() != Empire.NONE && ep.lastChanged.after(cal.getTime())) {		
			
			p.sendMessage(ChatColor.RED + "You have changed empires in the last month");
			
			return;
		
		}
		
		if(args.length < 2){
			p.sendMessage(ChatColor.RED + "You must specify an empire to join, please choose Arator, Requiem, or Yavari.");
		}
		if(args.length == 2){
			String empire = args[1];
			Empire e = Empire.fromString(empire);
			if(e == Empire.NONE){
				p.sendMessage(ChatColor.RED + "No empire found with name " + empire + ", please choose Arator, Requiem, or Yavari.");
			}
			int[] pop = EmpirePercentage.getEmpirePopulationDistribution();
			System.out.println("Populations are " + pop[0] + "," + pop[1] + "," + pop[2]);
			double total = pop[0] + pop[1] + pop[2];
			int theirEmpire = e.getID();
			double theirPercentage = pop[theirEmpire-1] / total;
			System.out.println("percentage for empire " + theirEmpire + " is " + theirPercentage);
			if(theirPercentage > 0.36){
				p.sendMessage(ChatColor.RED + "This empire cannot be joined at this time, it has more than 36% of the player population. Please pick again.");
				return;
			}
			if(theirPercentage > 0.35){
				p.sendMessage(ChatColor.GOLD + "This empire currently has 35% of the playerbase and may soon not allow new members.");
				p.sendMessage(ChatColor.RED + "If you are joining with friends, it is possible that only some of you will get into this empire.");
				p.sendMessage(ChatColor.GOLD + "To avoid being split up, we recommend that you join another empire.");
				p.sendMessage(ChatColor.RED + "This empire is overpopulated and as such you will recieve 25% less starting cash if you join it.");
			} else if (theirPercentage > 0.345){
				p.sendMessage(ChatColor.RED + "This empire is overpopulated and as such you will recieve 25% less starting cash if you join it.");
			} else if (theirPercentage > 0.30){
				p.sendMessage(ChatColor.YELLOW + "This empire has normal population and so you will recieve normal starting cash if you join it.");
			} else {
				p.sendMessage(ChatColor.GREEN + "This empire is underpopulated and you will recieve 25% additional starting cash if you join it.");
			}
			
			if(ep.getEmpire() != Empire.NONE){
				p.sendMessage(ChatColor.RED + "Since you are already in an empire you, will lose your inventory, be removed from your faction, and will lose all of your current money. You will not be able to change empires for a month if you change.");
			}
			
			p.sendMessage(ChatColor.GOLD + "Are you sure you want to join " + e.getDarkColor() + e.getName() + ChatColor.GOLD + "? Type / empire join " + e.getDarkColor() + e.getName() + ChatColor.GOLD + " confirm to confirm.");
		} else if(args.length == 3){
			if(args[2].equalsIgnoreCase("confirm")){
				String empire = args[1];
				Empire e = Empire.fromString(empire);
				if(e == Empire.NONE){
					p.sendMessage(ChatColor.RED + "No empire found with name " + empire + ", please choose Arator, Requiem, or Yavari.");
				}
				int[] pop = EmpirePercentage.getEmpirePopulationDistribution();
				double total = pop[0] + pop[1] + pop[2];
				int theirEmpire = e.getID();
				double theirPercentage = pop[theirEmpire-1] / total;
				if(theirPercentage > 0.36){
					p.sendMessage(ChatColor.RED + "This empire cannot be joined at this time, it has more than 36% of the player population. Please pick again.");
					return;
				} else {
					
					if (ep.getEmpire() != Empire.NONE) {
						
						Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "pp user " + p.getUniqueId().toString() + " removegroup " + ep.getEmpire().getName() + "0");
						Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "pp user " + p.getUniqueId().toString() + " removegroup " + ep.getEmpire().getName() + "1");
						Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "pp user " + p.getUniqueId().toString() + " removegroup " + ep.getEmpire().getName() + "2");
						Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "pp user " + p.getUniqueId().toString() + " removegroup " + ep.getEmpire().getName() + "3");
						Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "pp user " + p.getUniqueId().toString() + " removegroup " + ep.getEmpire().getName() + "4");
						Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "pp user " + p.getUniqueId().toString() + " removegroup " + ep.getEmpire().getName() + "5");
						
						SQEmpire.economy.withdrawPlayer(p, SQEmpire.economy.getBalance(p));
						BungeePlayerHandler.wipePlayerInventory(p);
							
						MPlayer.get(p).setFaction(FactionColl.get().getNone());
					
						ep.lastChanged = Calendar.getInstance().getTime();
						
					}
					
					ep.setEmpire(e);
					if(theirPercentage > 0.345)
						SQEmpire.economy.depositPlayer(p, 7500);
					else if(theirPercentage > 0.30){
						SQEmpire.economy.depositPlayer(p, 10000);
					} else {
						SQEmpire.economy.depositPlayer(p, 12500);
					}
					p.sendMessage(ChatColor.GREEN + "You have succesfully joined empire " + e + "!");
					if(e == Empire.ARATOR){
						Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "ee pp user " + p.getUniqueId().toString() + " addgroup Arator0");
						BungeePlayerHandler.sendPlayer(p, "AratorSystem", "AratorSystem", 2598, 100, 1500);
						Bukkit.dispatchCommand(Bukkit.getConsoleSender(), 
								"eb janesudo Aratorians, please welcome your newest member " + p.getName() + "!");
					} else if(e == Empire.REQUIEM){
						Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "ee pp user " + p.getUniqueId().toString() + " addgroup Requiem0");
						BungeePlayerHandler.sendPlayer(p, "QuillonSystem", "QuillonSystem", 1375, 100, -2381);
						Bukkit.dispatchCommand(Bukkit.getConsoleSender(), 
								"eb janesudo Requiem, please welcome your newest member " + p.getName() + "!");
					} else if(e == Empire.YAVARI){
						Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "ee pp user " + p.getUniqueId().toString() + " addgroup Yavari0");
						BungeePlayerHandler.sendPlayer(p, "YavarSystem", "YavarSystem", 0, 231, 2500);
						Bukkit.dispatchCommand(Bukkit.getConsoleSender(), 
								"eb janesudo Yavari, please welcome your newest member " + p.getName() + "!");
					}
					Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "ee pp user " + p.getUniqueId().toString() + " removegroup Guest");
				}
			} else {
				p.sendMessage(ChatColor.RED + "Incorrect usage, type /empire join <name>");
			}
		}
	}
	private void cmdHelp(Player p, String[] args){
		SQEmpire.echo(p, "SQEmpire commands:",
				"==================================",
				"/empire stats <empire/player>: displays statistics about the named empire or player",
				"/empire join <empire>: join the named empire.",
				"/empire lore <empire>: displays lore about the named empire.",
				"/empire leave: leave your empire.",
				"/empire claim: claims the chunk you are standing in for your empire.",
				"/empire map: displays a link to the interative galaxy map.",
				"/empire help: displays this dialogue.");
	}
	
	/*private void cmdClaim(Player p, String[] args){
		p.sendMessage("Attempting to claim, please wait...");
		
		boolean canClaim - EmpirePower.
	}*/
}
