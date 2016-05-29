package com.whirlwindgames.dibujaron.sqempire;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import net.countercraft.movecraft.bungee.BungeePlayerHandler;
import net.milkbowl.vault.economy.Economy;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.BlockCommandSender;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitScheduler;
import org.dynmap.markers.Marker;

import com.sk89q.worldguard.protection.ApplicableRegionSet;
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
			Player p = Bukkit.getPlayer(args[0]);
			EmpirePlayer ep = EmpirePlayer.getOnlinePlayer(p);
			ep.setEmpire(Empire.NONE);
			ep.publishData();
			SQEmpire.economy.withdrawPlayer(p, SQEmpire.economy.getBalance(p));
			SQEmpire.permission.playerAddGroup(p,"Guest");
			Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "pp user " + p.getName() + " removegroup Yavari0");
			Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "pp user " + p.getName() + " removegroup Arator0");
			Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "pp user " + p.getName() + " removegroup Requiem0");
			BungeePlayerHandler.sendPlayer(p, "CoreSystem", "CoreSystem", 0, 102, 0);
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
			p.sendMessage(ChatColor.GOLD + "/empire join <empire> - Join an empire");
			
			if (p.hasPermission("SQEmpire.manageControlPoints")) {
				
				p.sendMessage(ChatColor.GOLD + "/empire point - manages control points");
				
			}
			
			if (p.hasPermission("SQEmpire.capture")) {
				
				p.sendMessage(ChatColor.GOLD + "/empire capture - captures a control point");
				
			}
			
		} else{
			
			if (args[0].equalsIgnoreCase("help")) {

				EmpirePlayer ep = EmpirePlayer.getOnlinePlayer(p);
				
				p.sendMessage(ChatColor.GOLD + "Your empire is " + ep.getEmpire().getName() + ".");				
				p.sendMessage(ChatColor.GOLD + "/empire help - Displays this");
				p.sendMessage(ChatColor.GOLD + "/empire join <empire> - Join an empire");
				
				if (p.hasPermission("SQEmpire.manageControlPoints")) {
					
					p.sendMessage(ChatColor.GOLD + "/empire point - manages control points");
					
				}
				
				if (p.hasPermission("SQEmpire.capture")) {
					
					p.sendMessage(ChatColor.GOLD + "/empire capture - captures a control point");
					
				}
				
			} else if (args[0].equalsIgnoreCase("join")) {
				
				cmdJoin(p, args);
				
			} else if (args[0].equalsIgnoreCase("point")) {
				
				if (p.hasPermission("SQEmpire.manageControlPoints")) {
					
					if (args.length == 1) {
						
						p.sendMessage(ChatColor.GOLD + "/empire point add - adds a control point");
						
					} else {
						
						if (args[1].equalsIgnoreCase("add")) {
							
							ApplicableRegionSet regions = SQEmpire.worldGuardPlugin.getRegionManager(Bukkit.getWorlds().get(0)).getApplicableRegions(p.getLocation());
							
							if (regions.size() == 0) {
								
								p.sendMessage(ChatColor.RED + "You must be in a region to do that");
								
								return;
								
							} else if (regions.size() == 1) {
								
								List<ProtectedRegion> protectedRegions = new ArrayList<ProtectedRegion>();
								protectedRegions.addAll(regions.getRegions());
								
								for (Territory territory : SQEmpire.territories) {
									
									if (territory.name.equals(protectedRegions.get(0).getId())) {
										
										int capturePoints = SQEmpire.config.getConfigurationSection("regions." + territory.name + ".capture points").getKeys(false).size();
										
										SQEmpire.config.set("regions." + territory.name + ".capture points." + "point" + (capturePoints + 1) + ".owner", "None");
										SQEmpire.config.set("regions." + territory.name + ".capture points." + "point" + (capturePoints + 1) + ".position", p.getLocation().getChunk().getX() + "," + p.getLocation().add(0, -2, 0).getBlockY() + "," + p.getLocation().getChunk().getZ());
										
										SQEmpire.getInstance().saveConfig();
										
										final CapturePoint capturePoint = new CapturePoint();
						        		
						        		capturePoint.owner = Empire.fromString("None");
						        		
						        		capturePoint.x = p.getLocation().getChunk().getX();
						        		capturePoint.y = p.getLocation().add(0, -2, 0).getBlockY();
						        		capturePoint.z = p.getLocation().getChunk().getZ();
						        		
						        		territory.capturePoints.add(capturePoint);
						        		
						        		final int xMultiplier = capturePoint.x / capturePoint.x;
						            	final int zMultiplier = capturePoint.z / capturePoint.z;
						            	
						            	Marker marker = SQEmpire.markerSet.createMarker(territory.name + "-" + (capturePoints + 1), territory.name + "-" + (capturePoints + 1), Bukkit.getWorlds().get(0).getName(), (double) capturePoint.x * 16 + (xMultiplier * 7.5), (double) capturePoint.y, (double) capturePoint.z * 16 + (zMultiplier * 7.5), SQEmpire.markerAPI.getMarkerIcon("temple"), false);

						            	BukkitScheduler scheduler = Bukkit.getScheduler();
						            	
						            	scheduler.scheduleSyncDelayedTask(SQEmpire.getInstance(), new Runnable() {
						            		
						            		public void run() {
						            			
						            			SQEmpire.spawnRectangle(capturePoint.x * 16 + (xMultiplier * 6), capturePoint.y, capturePoint.z * 16 + (zMultiplier * 6), 4, 4, Material.IRON_BLOCK, 0);
								            	
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
						            			
						            		}
						            		
						            	});

						    			p.sendMessage(ChatColor.GOLD + "Capture Point created");
						    			
						    			return;
										
									}
									
								}
								
							} else {
								
								List<ProtectedRegion> protectedRegions = new ArrayList<ProtectedRegion>();
								protectedRegions.addAll(regions.getRegions());
								
								for (ProtectedRegion region : protectedRegions) {
									
									for (Territory territory : SQEmpire.territories) {
										
										if (territory.name.equals(region.getId())) {
											
											int capturePoints = SQEmpire.config.getConfigurationSection("regions." + territory.name + ".capture points").getKeys(false).size();
											
											SQEmpire.config.set("regions." + territory.name + ".capture points." + "point" + (capturePoints + 1) + ".owner", "None");
											SQEmpire.config.set("regions." + territory.name + ".capture points." + "point" + (capturePoints + 1) + ".position", p.getLocation().getChunk().getX() + "," + p.getLocation().add(0, -2, 0).getBlockY() + "," + p.getLocation().getChunk().getZ());
											
											final CapturePoint capturePoint = new CapturePoint();
							        		
							        		capturePoint.owner = Empire.fromString("None");
							        		
							        		capturePoint.x = p.getLocation().getChunk().getX();
							        		capturePoint.y = p.getLocation().add(0, -2, 0).getBlockY();
							        		capturePoint.z = p.getLocation().getChunk().getZ();
							        		
							        		territory.capturePoints.add(capturePoint);
							        		
							        		final int xMultiplier = capturePoint.x / capturePoint.x;
							            	final int zMultiplier = capturePoint.z / capturePoint.z;
							            	
							            	Marker marker = SQEmpire.markerSet.createMarker(territory.name + "-" + (capturePoints + 1), territory.name + "-" + (capturePoints + 1), Bukkit.getWorlds().get(0).getName(), (double) capturePoint.x * 16 + (xMultiplier * 7.5), (double) capturePoint.y, (double) capturePoint.z * 16 + (zMultiplier * 7.5), SQEmpire.markerAPI.getMarkerIcon("temple"), false);

							            	BukkitScheduler scheduler = Bukkit.getScheduler();
							            	
							            	scheduler.scheduleSyncDelayedTask(SQEmpire.getInstance(), new Runnable() {
							            		
							            		public void run() {
							            			
							            			SQEmpire.spawnRectangle(capturePoint.x * 16 + (xMultiplier * 6), capturePoint.y, capturePoint.z * 16 + (zMultiplier * 6), 4, 4, Material.IRON_BLOCK, 0);
									            	
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
							            			
							            		}
							            		
							            	});
							            	
							    			p.sendMessage(ChatColor.GOLD + "Capture Point created");
							    			
							    			return;
											
										}
										
									}
									
								}
								
							}
							
							p.sendMessage(ChatColor.RED + "You must be in a region to do that");
							
						}
						
					}
					
				}
				
			} else if (args[0].equalsIgnoreCase("capture") || args[0].equalsIgnoreCase("claim")) {
				
				if (p.hasPermission("SQEmpire.capture")) {
					
					if (SQEmpire.capturing.containsKey(p)) {
						
						p.sendMessage(ChatColor.RED + "You cannot capture more than one control point at a time");
						
					} else {
						

						ApplicableRegionSet regions = SQEmpire.worldGuardPlugin.getRegionManager(Bukkit.getWorlds().get(0)).getApplicableRegions(p.getLocation());
						
						if (regions.size() == 0) {
							
							p.sendMessage(ChatColor.RED + "You must be on a control point to do that");
							
							return;
							
						} else if (regions.size() == 1) {
							
							List<ProtectedRegion> protectedRegions = new ArrayList<ProtectedRegion>();
							protectedRegions.addAll(regions.getRegions());
							
							for (Territory territory : SQEmpire.territories) {
								
								if (territory.name.equals(protectedRegions.get(0).getId())) {
									
									int chunkX = p.getLocation().getChunk().getX();
									int chunkZ = p.getLocation().getChunk().getZ();
									
									int y = p.getLocation().getBlockY();
									
									for (CapturePoint capturePoint : territory.capturePoints) {
										
										if (chunkX == capturePoint.x && chunkZ == capturePoint.z && y > capturePoint.y + 1 && y < capturePoint.y + 3) {
											
											EmpirePlayer ep = EmpirePlayer.getOnlinePlayer(p);
											
											if (!capturePoint.owner.equals(ep.getEmpire())) {

								    			if (capturePoint.capture(p)) {
								    				
								    				p.sendMessage(ChatColor.GOLD + "Capturing point");
								    				
								    			} else {
								    				
								    				p.sendMessage(ChatColor.GOLD + "You are already captureing this point");
								    				
								    			}
								    			
								    			return;
												
											} else {
												
												p.sendMessage(ChatColor.RED + "You cannot capture your own control point");
												
												return;
												
											}
											
										}
										
									}
									
								}
								
							}
							
						} else {
							
							List<ProtectedRegion> protectedRegions = new ArrayList<ProtectedRegion>();
							protectedRegions.addAll(regions.getRegions());
							
							for (ProtectedRegion region : protectedRegions) {
								
								for (Territory territory : SQEmpire.territories) {
									
									if (territory.name.equals(region.getId())) {
										
										int capturePoints = SQEmpire.config.getConfigurationSection("regions." + territory.name + ".capture points").getKeys(false).size();
										
										SQEmpire.config.set("regions." + territory.name + ".capture points." + "point" + (capturePoints + 1) + ".owner", "None");
										SQEmpire.config.set("regions." + territory.name + ".capture points." + "point" + (capturePoints + 1) + ".position", p.getLocation().getChunk().getX() + "," + p.getLocation().add(0, -2, 0).getBlockY() + "," + p.getLocation().getChunk().getZ());
										
										final CapturePoint capturePoint = new CapturePoint();
						        		
						        		capturePoint.owner = Empire.fromString("None");
						        		
						        		capturePoint.x = p.getLocation().getChunk().getX();
						        		capturePoint.y = p.getLocation().add(0, -2, 0).getBlockY();
						        		capturePoint.z = p.getLocation().getChunk().getZ();
						        		
						        		territory.capturePoints.add(capturePoint);
						        		
						        		final int xMultiplier = capturePoint.x / capturePoint.x;
						            	final int zMultiplier = capturePoint.z / capturePoint.z;
						            	
						            	Marker marker = SQEmpire.markerSet.createMarker(territory.name + "-" + (capturePoints + 1), territory.name + "-" + (capturePoints + 1), Bukkit.getWorlds().get(0).getName(), (double) capturePoint.x * 16 + (xMultiplier * 7.5), (double) capturePoint.y, (double) capturePoint.z * 16 + (zMultiplier * 7.5), SQEmpire.markerAPI.getMarkerIcon("temple"), false);

						            	BukkitScheduler scheduler = Bukkit.getScheduler();
						            	
						            	scheduler.scheduleSyncDelayedTask(SQEmpire.getInstance(), new Runnable() {
						            		
						            		public void run() {
						            			
						            			SQEmpire.spawnRectangle(capturePoint.x * 16 + (xMultiplier * 6), capturePoint.y, capturePoint.z * 16 + (zMultiplier * 6), 4, 4, Material.IRON_BLOCK, 0);
								            	
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
						            			
						            		}
						            		
						            	});
						            	
						    			p.sendMessage(ChatColor.GOLD + "Capture Point created");
						    			
						    			return;
										
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
		if(ep.getEmpire() != Empire.NONE){
			p.sendMessage("You are already in an Empire, you cannot join one.");
			return;
		}
		if(args.length < 2){
			p.sendMessage("You must specify an empire to join, please choose Arator, Requiem, or Yavari.");
		}
		if(args.length == 2){
			String empire = args[1];
			Empire e = Empire.fromString(empire);
			if(e == Empire.NONE){
				p.sendMessage("No empire found with name " + empire + ", please choose Arator, Requiem, or Yavari.");
			}
			int[] pop = EmpirePercentage.getEmpirePopulationDistribution();
			System.out.println("Populations are " + pop[0] + "," + pop[1] + "," + pop[2]);
			double total = pop[0] + pop[1] + pop[2];
			int theirEmpire = e.getID();
			double theirPercentage = pop[theirEmpire-1] / total;
			System.out.println("percentage for empire " + theirEmpire + " is " + theirPercentage);
			if(theirPercentage > 0.36){
				p.sendMessage("This empire cannot be joined at this time, it has more than 36% of the player population. Please pick again.");
				return;
			}
			if(theirPercentage > 0.35){
				p.sendMessage("This empire currently has 35% of the playerbase and may soon not allow new members.");
				p.sendMessage(ChatColor.RED + "If you are joining with friends, it is possible that only some of you will get into this empire.");
				p.sendMessage("To avoid being split up, we recommend that you join another empire.");
			} else if (theirPercentage > 0.345){
				p.sendMessage("This empire is overpopulated and as such you will recieve 25% less starting cash if you join it.");
			} else if (theirPercentage > 0.30){
				p.sendMessage("This empire has normal population and so you will recieve normal starting cash if you join it.");
			} else {
				p.sendMessage("This empire is underpopulated and you will recieve 25% additional starting cash if you join it.");
			}
			p.sendMessage("Are you sure you want to join " + e.getName() + "? Type / empire join " + e.getName() + " confirm to confirm.");
		} else if(args.length == 3){
			if(args[2].equalsIgnoreCase("confirm")){
				String empire = args[1];
				Empire e = Empire.fromString(empire);
				if(e == Empire.NONE){
					p.sendMessage("No empire found with name " + empire + ", please choose Arator, Requiem, or Yavari.");
				}
				int[] pop = EmpirePercentage.getEmpirePopulationDistribution();
				double total = pop[0] + pop[1] + pop[2];
				int theirEmpire = e.getID();
				double theirPercentage = pop[theirEmpire-1] / total;
				if(theirPercentage > 0.36){
					p.sendMessage("This empire cannot be joined at this time, it has more than 36% of the player population. Please pick again.");
					return;
				} else {
					ep.setEmpire(e);
					if(theirPercentage > 0.345)
						SQEmpire.economy.depositPlayer(p, 7500);
					else if(theirPercentage > 0.30){
						SQEmpire.economy.depositPlayer(p, 10000);
					} else {
						SQEmpire.economy.depositPlayer(p, 12500);
					}
					p.sendMessage("You have succesfully joined empire " + e + "!");
					if(e == Empire.ARATOR){
						SQEmpire.permission.playerAddGroup(p,"Arator0");
						BungeePlayerHandler.sendPlayer(p, "AratorSystem", "AratorSystem", 2598, 100, 1500);
						Bukkit.dispatchCommand(Bukkit.getConsoleSender(), 
								"eb janesudo Aratorians, please welcome your newest member " + p.getName() + "!");
					} else if(e == Empire.REQUIEM){
						SQEmpire.permission.playerAddGroup(p,"Requiem0");
						BungeePlayerHandler.sendPlayer(p, "QuillonSystem", "QuillonSystem", 1375, 100, -2381);
						Bukkit.dispatchCommand(Bukkit.getConsoleSender(), 
								"eb janesudo Requiem, please welcome your newest member " + p.getName() + "!");
					} else if(e == Empire.YAVARI){
						SQEmpire.permission.playerAddGroup(p,"Yavari0");
						BungeePlayerHandler.sendPlayer(p, "YavarSystem", "YavarSystem", 0, 231, 2500);
						Bukkit.dispatchCommand(Bukkit.getConsoleSender(), 
								"eb janesudo Yavari, please welcome your newest member " + p.getName() + "!");
					}
					Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "bungeeperms user " + p.getName() + " removegroup Guest");
				}
			} else {
				p.sendMessage("Incorrect usage, type /empire join <name>");
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
