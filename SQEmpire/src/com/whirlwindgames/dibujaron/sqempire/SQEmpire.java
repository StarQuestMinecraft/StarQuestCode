package com.whirlwindgames.dibujaron.sqempire;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import net.milkbowl.vault.economy.Economy;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Entity;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;
import org.dynmap.DynmapAPI;
import org.dynmap.markers.AreaMarker;
import org.dynmap.markers.Marker;
import org.dynmap.markers.MarkerAPI;
import org.dynmap.markers.MarkerSet;
import org.dynmap.markers.PolyLineMarker;

import com.gmail.nossr50.datatypes.skills.SkillType;
import com.massivecraft.factions.entity.BoardColl;
import com.massivecraft.factions.entity.Faction;
import com.massivecraft.factions.entity.FactionColl;
import com.massivecraft.massivecore.ps.PS;
import com.sk89q.worldedit.BlockVector;
import com.sk89q.worldedit.BlockVector2D;
import com.sk89q.worldguard.bukkit.WGBukkit;
import com.sk89q.worldguard.bukkit.WorldGuardPlugin;
import com.sk89q.worldguard.domains.DefaultDomain;
import com.sk89q.worldguard.protection.ApplicableRegionSet;
import com.sk89q.worldguard.protection.flags.DefaultFlag;
import com.sk89q.worldguard.protection.flags.StateFlag.State;
import com.sk89q.worldguard.protection.regions.ProtectedCuboidRegion;
import com.sk89q.worldguard.protection.regions.ProtectedPolygonalRegion;
import com.sk89q.worldguard.protection.regions.ProtectedRegion;
import com.whirlwindgames.dibujaron.sqempire.database.EmpireDB;
import com.whirlwindgames.dibujaron.sqempire.util.AsyncUtil;
import com.whirlwindgames.dibujaron.sqempire.util.SuperPS;

public class SQEmpire extends JavaPlugin{
	
	private static SQEmpire instance;
	private static Planet thisPlanet;
	public static Economy economy;
	public static WorldGuardPlugin worldGuardPlugin;
	public static FileConfiguration config;
	
	public static List<Territory> territories = new ArrayList<Territory>();
	public static List<BattleConnection> connections = new ArrayList<BattleConnection>();
	
	public static DynmapAPI dynmapAPI;
	public static MarkerAPI markerAPI;
	public static MarkerSet markerSet;
	
	public static Territory AratorBeachead = new Territory();
	public static Territory RequiemBeachead = new Territory();
	public static Territory YavariBeachead = new Territory();
	
	public static boolean automaticRestart = false;
	
	public static HashMap<String, Territory> territory1 = new HashMap<String, Territory>(); 
	public static HashMap<String, Integer> territoryX = new HashMap<String, Integer>(); 
	public static HashMap<String, Integer> territoryZ = new HashMap<String, Integer>(); 

	public static boolean isCorePlanet = false;

	private static boolean first = true;
	
	public static HashMap<String, List<SkillType>> mcmmoBoosters = new HashMap<String, List<SkillType>>();
	public static HashMap<String, Empire> dominantEmpires = new HashMap<String, Empire>();
	
	@Override
	public void onEnable(){

		if (first) {
	        	
			if (!new File(this.getDataFolder(), "config.yml").exists()) {
				
				saveDefaultConfig();
				saveConfig();
				
			}
			
			
			worldGuardPlugin = WGBukkit.getPlugin();
	        dynmapAPI = (DynmapAPI) Bukkit.getServer().getPluginManager().getPlugin("dynmap");
	        markerAPI = dynmapAPI.getMarkerAPI();
	        markerSet = markerAPI.getMarkerSet("empires.markerset");
	        if (markerSet == null) {
	            	
	          	markerSet = markerAPI.createMarkerSet("empires.markerset", "Empires", null, false);
	            	
	        }

			instance = this;
			EmpireCommand e = new EmpireCommand();
			getCommand("empire").setExecutor(e);
			getCommand("playersendempire").setExecutor(e);
			getCommand("empirereset").setExecutor(e);
			getCommand("setdominantempire").setExecutor(e);
			thisPlanet = Planet.fromName(Bukkit.getServerName());
			PluginManager pm = getServer().getPluginManager();
			pm.registerEvents( new BetaListener(), this );
			pm.registerEvents( new PlayerHandler(), this );
		
	        RegisteredServiceProvider<Economy> economyProvider = getServer().getServicesManager().getRegistration(net.milkbowl.vault.economy.Economy.class);
	        if (economyProvider != null) {
	            economy = economyProvider.getProvider();
	        } else {
	        	System.out.println("No economy found!");
	        }
	        
	        config = this.getConfig();
			
		}

		for (Entity entity : Bukkit.getWorlds().get(0).getEntities()) {
			
			if (entity instanceof ArmorStand) {
				
				if (entity.getCustomName() != null) {
					
					if (entity.getCustomName().startsWith("Health Left: ")) {
						
						entity.remove();
						
					}
					
				}
				
			}
			
		}
		
		if (new File(this.getDataFolder().getAbsolutePath() + "/mcmmo.yml").exists()) {
			
			FileConfiguration config = YamlConfiguration.loadConfiguration(new File(this.getDataFolder().getAbsolutePath() + "/mcmmo.yml"));
			
			if (config.getConfigurationSection("mcmmo") != null) {
				
				for (String planet : config.getConfigurationSection("mcmmo").getKeys(false)) {
					
					List<SkillType> skills = new ArrayList<SkillType>();
					
					for (String skill : config.getStringList("mcmmo." + planet)) {
						
						skills.add(SkillType.getSkill(skill));
						
					}
					
					mcmmoBoosters.put(planet, skills);
					
				}
				
			}
			
		}
		
        if (config.getConfigurationSection("regions") != null) {
        	
        	for (String region : config.getConfigurationSection("regions").getKeys(false)) {

            	Territory territory = new Territory();
            	
            	territory.name = region.replace(' ', '_');
            	
            	territory.owner = Empire.fromString(config.getString("regions." + region + ".owner"));
            	territory.age = config.getInt("regions." + region + ".age");
            	
            	List<String> points = new ArrayList<String>();
            	if (config.getConfigurationSection("regions." + region + ".capture points") != null) {
            		
                	points.addAll(config.getConfigurationSection("regions." + region + ".capture points").getKeys(false));
            		
            	}

            	for (int i = 0; i < points.size(); i ++) {
            		
            		CapturePoint capturePoint = new CapturePoint();
            		
            		capturePoint.owner = Empire.fromString(config.getString("regions." + region + ".capture points." + points.get(i) + ".owner"));
            		capturePoint.configPath = "regions." + region + ".capture points." + points.get(i);   		
            		
            		String[] pos = config.getString("regions." + region + ".capture points." + points.get(i) + ".position").split(",");
            		
            		capturePoint.x = Integer.parseInt(pos[0]);
            		capturePoint.y = Integer.parseInt(pos[1]);
            		capturePoint.z = Integer.parseInt(pos[2]);
            		
            		capturePoint.name = territory.name + "-" + (i + 1);
            		
            		territory.capturePoints.add(capturePoint);
            		
            	}
            	
            	for (String point : config.getStringList("regions." + region + ".points")) {

            		String[] pos = point.split(",");
            		
            		String x = pos[0];
            		String z = pos[1];
            		
            		BlockVector2D vector = new BlockVector2D((int) Float.parseFloat(x), (int) Float.parseFloat(z));
            		
            		territory.points.add(vector);
            		
            	}
            	
            	
            	if (config.getBoolean("regions." + region + ".beachead")) {
            		
            		if (territory.owner.equals(Empire.ARATOR)) {
            			
            			AratorBeachead = territory;
            			
            		}
            		
            		if (territory.owner.equals(Empire.YAVARI)) {
            			
            			YavariBeachead = territory;
            			
            		}
            		
            		if (territory.owner.equals(Empire.REQUIEM)) {
            			
            			RequiemBeachead = territory;
            			
            		}
            		
            	}
            	
            	territories.add(territory);
            	
            }
        	
        }
        
        if (territories.size() > 0) {
        	
        	isCorePlanet = true;
        	
        }
        
        if (config.getConfigurationSection("connections") != null) {
        	
        	for (String connection : config.getConfigurationSection("connections").getKeys(false)) {
            	
            	BattleConnection battleConnection = new BattleConnection();
            	
            	String territory1 = config.getString("connections." + connection + ".region 1").replace(' ', '_');
            	String territory2 = config.getString("connections." + connection + ".region 2").replace(' ', '_');
            	
            	for (Territory territory : territories) {
            		
            		if (territory.name.equals(territory1)) {
            			
            			battleConnection.territory1 = territory;
            			
            		}
            		
            		if (territory.name.equals(territory2)) {
            			
            			battleConnection.territory2 = territory;
            			
            		}
            		
            	}
            	
            	battleConnection.x1 = config.getDouble("connections." + connection + ".x1");
            	battleConnection.z1 = config.getDouble("connections." + connection + ".z1");
            	battleConnection.x2 = config.getDouble("connections." + connection + ".x2");
            	battleConnection.z2 = config.getDouble("connections." + connection + ".z2");
            	
            	if (battleConnection.territory1 == null || battleConnection.territory2 == null) {
            		
            		System.out.print(connection + " had a null territory");
            		
            	} else {
            		
                	connections.add(battleConnection);
            		
            	}

            }
        	
        }

        for (String id : worldGuardPlugin.getRegionManager(Bukkit.getWorlds().get(0)).getRegions().keySet()) {
        	
        	if (id.startsWith("sqempire")) {
        		
            	worldGuardPlugin.getRegionManager(Bukkit.getWorlds().get(0)).removeRegion(id);
        		
        	}
        	
        }
        
        for (final Territory territory : territories) {
        	
            ProtectedPolygonalRegion region = (ProtectedPolygonalRegion) worldGuardPlugin.getRegionManager(Bukkit.getWorlds().get(0)).getRegion("SQEmpire-" + territory.name);
        	
            if (region != null) {
            	
            	if (!region.getPoints().equals(territory.points)) {
            		
            		worldGuardPlugin.getRegionManager(Bukkit.getWorlds().get(0)).removeRegion("SQEmpire-" + territory.name);
            		worldGuardPlugin.getRegionManager(Bukkit.getWorlds().get(0)).addRegion(new ProtectedPolygonalRegion("SQEmpire-" + territory.name, territory.points, 0, Bukkit.getWorlds().get(0).getMaxHeight()));
            		
            	}
            	
            } else {
            	
            	worldGuardPlugin.getRegionManager(Bukkit.getWorlds().get(0)).addRegion(new ProtectedPolygonalRegion("SQEmpire-" + territory.name, territory.points, 0, Bukkit.getWorlds().get(0).getMaxHeight()));
            	
            }
            
            if (!(AratorBeachead.equals(territory) || YavariBeachead.equals(territory) || RequiemBeachead.equals(territory))) {
            	
                region = (ProtectedPolygonalRegion) worldGuardPlugin.getRegionManager(Bukkit.getWorlds().get(0)).getRegion("SQEmpire-" + territory.name);
                
                region.setFlag(DefaultFlag.PVP, State.ALLOW);
                region.setFlag(DefaultFlag.INTERACT, State.ALLOW);
                region.setFlag(DefaultFlag.GHAST_FIREBALL, State.ALLOW);
                region.setFlag(DefaultFlag.OTHER_EXPLOSION, State.ALLOW);
                region.setFlag(DefaultFlag.TNT, State.ALLOW);
                
        		DefaultDomain domain = new DefaultDomain();
        		
        		if (territory.owner.equals(Empire.NONE)) {
        			
        			domain.addGroup("arator");
        			domain.addGroup("yavari");
        			domain.addGroup("requiem");
        			
        		} else {
        			
            		if (territory.owner.equals(Empire.ARATOR) || isBattleConnected(territory, Empire.ARATOR)) {
            			
                		domain.addGroup("arator");
            			
            		}
            		
            		if (territory.owner.equals(Empire.YAVARI) || isBattleConnected(territory, Empire.YAVARI)) {
            			
                		domain.addGroup("yavari");
            			
            		}
            		
            		if (territory.owner.equals(Empire.REQUIEM) || isBattleConnected(territory, Empire.REQUIEM)) {
            			
                		domain.addGroup("requiem");
            			
            		}
        			
        		}

        		region.setMembers(domain);
            	
            }
            
    		double[] xBoundaries = new double[territory.points.size()];
    		double[] zBoundaries = new double[territory.points.size()];
    		
    		for (int i = 0; i < territory.points.size(); i ++) {
    			
    			xBoundaries[i] = territory.points.get(i).getX();
    			zBoundaries[i] = territory.points.get(i).getZ();
    			
    		}
    		
            AreaMarker areaMarker = markerSet.findAreaMarker(territory.name.replace('_', ' '));
            territory.name.replace(' ', '_');
            
            if (areaMarker == null) {
            	
            	areaMarker = markerSet.createAreaMarker(territory.name, territory.name, false, Bukkit.getWorlds().get(0).getName(), xBoundaries, zBoundaries, false);
            	
            }
            
            System.out.print(areaMarker);
            
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
            		
            for (int i = 0; i < territory.capturePoints.size(); i ++) {
            	
            	CapturePoint capturePoint = territory.capturePoints.get(i);
            	
            	int xMultiplier = capturePoint.x / capturePoint.x;
            	int zMultiplier = capturePoint.z / capturePoint.z;
            	
            	Marker marker = markerSet.findMarker(territory.name.replace('_', ' ') + "-" + (i + 1));
            	
            	if (marker == null) {
            		
            		marker = markerSet.createMarker(territory.name.replace('_', ' ') + "-" + (i + 1), territory.name.replace('_', ' ') + "-" + (i + 1), Bukkit.getWorlds().get(0).getName(), (double) capturePoint.x * 16 + (xMultiplier * 7.5), (double) capturePoint.y, (double) capturePoint.z * 16 + (zMultiplier * 7.5), capturePoint.owner.pointIcon, false);
            		
            	}
                territory.name.replace(' ', '_');

            	marker.setDescription("Owner: " + capturePoint.owner.getName());
            	
            	ProtectedCuboidRegion pointRegion = (ProtectedCuboidRegion) worldGuardPlugin.getRegionManager(Bukkit.getWorlds().get(0)).getRegion("SQEmpire-" + capturePoint.name);
            	
                if (pointRegion != null) {
                	
                	List<BlockVector2D> points = new ArrayList<BlockVector2D>();
                	
                	points.add(new BlockVector2D(capturePoint.x, capturePoint.z));
                	points.add(new BlockVector2D(capturePoint.x + 16, capturePoint.z + 16));
                	
                	if (!pointRegion.getPoints().equals(points)) {
                		
                		worldGuardPlugin.getRegionManager(Bukkit.getWorlds().get(0)).removeRegion("SQEmpire-" + capturePoint.name);
                		worldGuardPlugin.getRegionManager(Bukkit.getWorlds().get(0)).addRegion(new ProtectedCuboidRegion("SQEmpire-" + capturePoint.name, new BlockVector(capturePoint.x * 16, 0, capturePoint.z * 16), new BlockVector((capturePoint.x * 16) + (xMultiplier * 16), Bukkit.getWorlds().get(0).getMaxHeight(), (capturePoint.z * 16) + (zMultiplier * 16))));
                		
                	}
                	
                } else {
                	
                	worldGuardPlugin.getRegionManager(Bukkit.getWorlds().get(0)).addRegion(new ProtectedCuboidRegion("SQEmpire-" + capturePoint.name, new BlockVector(capturePoint.x * 16, 0, capturePoint.z * 16), new BlockVector((capturePoint.x * 16) + (xMultiplier * 16), Bukkit.getWorlds().get(0).getMaxHeight(), (capturePoint.z * 16) + (zMultiplier * 16))));
                	
                }
                
                pointRegion = (ProtectedCuboidRegion) worldGuardPlugin.getRegionManager(Bukkit.getWorlds().get(0)).getRegion("SQEmpire-" + capturePoint.name);
                		
                pointRegion.setFlag(DefaultFlag.PVP, State.ALLOW);
                pointRegion.setFlag(DefaultFlag.GHAST_FIREBALL, State.DENY);
                pointRegion.setFlag(DefaultFlag.OTHER_EXPLOSION, State.DENY);
                pointRegion.setFlag(DefaultFlag.TNT, State.DENY);

            	spawnRectangle(capturePoint.x * 16 + (xMultiplier * 6), capturePoint.y, capturePoint.z * 16 + (zMultiplier * 6), 4, 4, Material.IRON_BLOCK, 0);
            	spawnRectangle(capturePoint.x * 16 + (xMultiplier * 6), capturePoint.y - 2, capturePoint.z * 16 + (zMultiplier * 6), 4, 4, Material.SLIME_BLOCK, 0);            	
            	
    			spawnBeacon(capturePoint.x * 16 + (xMultiplier * 7), capturePoint.y, capturePoint.z * 16 + (zMultiplier * 7));
    			spawnBeacon(capturePoint.x * 16 + (xMultiplier * 8), capturePoint.y, capturePoint.z * 16 + (zMultiplier * 7));
    			spawnBeacon(capturePoint.x * 16 + (xMultiplier * 7), capturePoint.y, capturePoint.z * 16 + (zMultiplier * 8));
    			spawnBeacon(capturePoint.x * 16 + (xMultiplier * 8), capturePoint.y, capturePoint.z * 16 + (zMultiplier * 8));
    			
    			if (capturePoint.owner.equals(Empire.ARATOR)) {
    				
                	spawnRectangle(capturePoint.x * 16 + (xMultiplier * 6), capturePoint.y + 1, capturePoint.z * 16 + (zMultiplier * 6), 4, 4, Material.STAINED_GLASS, 11);
    				
    			} else if (capturePoint.owner.equals(Empire.YAVARI)) {
    				
    				spawnRectangle(capturePoint.x * 16 + (xMultiplier * 6), capturePoint.y + 1, capturePoint.z * 16 + (zMultiplier * 6), 4, 4, Material.STAINED_GLASS, 10);
    				
    			} else if (capturePoint.owner.equals(Empire.REQUIEM)) {
    				
    				spawnRectangle(capturePoint.x * 16 + (xMultiplier * 6), capturePoint.y + 1, capturePoint.z * 16 + (zMultiplier * 6), 4, 4, Material.STAINED_GLASS, 14);
    				
    			} else {
    				
    				spawnRectangle(capturePoint.x * 16 + (xMultiplier * 6), capturePoint.y + 1, capturePoint.z * 16 + (zMultiplier * 6), 4, 4, Material.STAINED_GLASS, 0);
    				
    			}

            }
            
        }
        
        for (BattleConnection connection : connections) {
        	
        	PolyLineMarker marker = markerSet.findPolyLineMarker(connection.getName());
        	
        	if (marker == null) {
        		
        		marker = markerSet.createPolyLineMarker(connection.getName(), connection.getName(), false, Bukkit.getWorlds().get(0).getName(), new double[]{connection.x1, connection.x2}, new double[]{100.0, 100.0}, new double[]{connection.z1, connection.z2}, false);
        		
        	}

        	marker.setLabel("Battle Connection");
        	marker.setLineStyle(3, 1, Integer.parseInt("ffff00", 16));
        	
        }
        
        List<Empire> empires = new ArrayList<Empire>();
        empires.add(Empire.ARATOR);
        empires.add(Empire.YAVARI);
        empires.add(Empire.REQUIEM);
    
        if (isCorePlanet) {
        	
        	AsyncUtil.runAsync(new Runnable() {
        		
        		public void run () {
        			
        			int[] count = new int[]{0, -1, -1, -1};
        			
        			for (Territory territory : territories) {
        				
        				count[territory.owner.id] = count[territory.owner.id] + 1;
        				
        			}
        			
        			SuperPS ps = new SuperPS(EmpireDB.prepareStatement("INSERT INTO minecraft.empire_territories(planet, empire0, empire1, empire2, empire3) VALUES (?, ?, ?, ?, ?) ON DUPLICATE KEY UPDATE empire0 = VALUES (empire0), empire1 = VALUES (empire1), empire2 = VALUES (empire2), empire3 = VALUES (empire3)"),5);

        			ps.setString(1, Bukkit.getWorlds().get(0).getName());
        			ps.setInt(2, count[0]);
        			ps.setInt(3, count[1]);
        			ps.setInt(4, count[2]);
        			ps.setInt(5, count[3]);
        			
        			ps.executeAndClose();
        			
        		}
        		
        	});
        	
        }
        
        if (first) {
        	
            (new CaptureTask()).run();
            (new DatabaseTask()).run();
        	
        }
        
        first = false;

	}	
	
	@Override
	public void onDisable(){
		
		reloadConfig();
		config = this.getConfig();
		
		if (automaticRestart) {
			
			for (Territory territory : territories) {
				
				config.set("regions." + territory.name.replace('_', ' ') + ".age", territory.age + 1);
				
			}
			
			saveConfig();
			
		}
		
		for (Territory territory : territories) {
			
			for (CapturePoint capturePoint : territory.capturePoints) {
				
				if (capturePoint.text != null) {
					
					capturePoint.text.remove();
					
				}

				int xMultiplier = capturePoint.x / capturePoint.x;
	        	int zMultiplier = capturePoint.z / capturePoint.z;
				
				Bukkit.getWorlds().get(0).getBlockAt(capturePoint.x * 16 + (xMultiplier * 7), capturePoint.y + 2, capturePoint.z * 16 + (zMultiplier * 7)).setType(Material.AIR);
				
			}
			
		}
		
		saveDefaultConfig();

		for (Faction faction : BoardColl.get().getFactionToChunks().keySet()) {

			for (PS ps : BoardColl.get().getFactionToChunks().get(faction)) {
				
				if (ps.isWorldLoadedOnThisServer()) {
					
					int x = ps.asBukkitChunk().getX();
					int z = ps.asBukkitChunk().getZ();
					
					int xMultiplier;
					int zMultiplier;
					
					if (x == 0) {
						
						xMultiplier = 1;
						
					} else {
						
						xMultiplier = x / Math.abs(x);
						
					}
					
					if (z == 0) {
						
						zMultiplier = 1;
						
					} else {
						
						zMultiplier = z / Math.abs(z);
						
					}

					Location location = new Location(ps.asBukkitWorld(), (x * 16) + (xMultiplier * 7), 100, (z * 16) + (zMultiplier * 7));

					ApplicableRegionSet regions = SQEmpire.worldGuardPlugin.getRegionManager(Bukkit.getWorlds().get(0)).getApplicableRegions(location);
					
					List<ProtectedRegion> protectedRegions = new ArrayList<ProtectedRegion>();
					protectedRegions.addAll(regions.getRegions());
					
					for (ProtectedRegion region : protectedRegions) {
						
						for (Territory territory : SQEmpire.territories) {
							
							if (territory.name.equalsIgnoreCase(region.getId())) {
								
								if (territory.age >= 3) {
									
									Empire factionEmpire = Empire.NONE;
									
									if (faction.getFlag("arator")) {
										
										factionEmpire = Empire.ARATOR;
										
									}
									
									if (faction.getFlag("requiem")) {
										
										factionEmpire = Empire.REQUIEM;
										
									}
									
									if (faction.getFlag("yavari")) {
										
										factionEmpire = Empire.YAVARI;
										
									}
									
									if (factionEmpire != Empire.NONE) {
										
										if (!factionEmpire.equals(territory.owner)) {
												
											BoardColl.get().setFactionAt(ps, FactionColl.get().getNone());
											
										}
										
									}
									
								}
								
							}
							
						}
						
					}
					
				}
				
			}
			
		}
		
	}
	
	public static SQEmpire getInstance(){
		return instance;
	}

	public static void debug(String s){
		instance.getLogger().info(s);
	}
	
	public static Planet thisPlanet(){
		return thisPlanet;
	}
	
	public static void echo(CommandSender s, String... msg){
		msg[0] = ChatColor.WHITE + "[" + ChatColor.GREEN + "SQEmpire" + ChatColor.WHITE + "] " + msg[0];
		s.sendMessage(msg);
	}
	
	public static void spawnBeacon(int x, int y, int z) {
    	
		Bukkit.getWorlds().get(0).getBlockAt(x, y, z).setType(Material.BEACON);
		
		Bukkit.getWorlds().get(0).getBlockAt(x, y - 1, z).setType(Material.IRON_BLOCK);
		Bukkit.getWorlds().get(0).getBlockAt(x + 1, y - 1, z).setType(Material.IRON_BLOCK);
		Bukkit.getWorlds().get(0).getBlockAt(x, y - 1, z + 1).setType(Material.IRON_BLOCK);
		Bukkit.getWorlds().get(0).getBlockAt(x - 1, y - 1, z - 1).setType(Material.IRON_BLOCK);
		Bukkit.getWorlds().get(0).getBlockAt(x - 1, y - 1, z + 1).setType(Material.IRON_BLOCK);
		Bukkit.getWorlds().get(0).getBlockAt(x + 1, y - 1, z - 1).setType(Material.IRON_BLOCK);
		Bukkit.getWorlds().get(0).getBlockAt(x + 1, y - 1, z + 1).setType(Material.IRON_BLOCK);
		Bukkit.getWorlds().get(0).getBlockAt(x - 1, y - 1, z - 1).setType(Material.IRON_BLOCK);
		Bukkit.getWorlds().get(0).getBlockAt(x, y - 1, z - 1).setType(Material.IRON_BLOCK);
		
	}
	
	public static void spawnRectangle(int x, int y, int z, int width, int length, Material material, int id) {
		
		for (int i = 0; i < width; i ++) {
			
			for (int j = 0; j < length; j ++) {
				
				Bukkit.getWorlds().get(0).getBlockAt(x + i, y, z + j).setType(material);
				Bukkit.getWorlds().get(0).getBlockAt(x + i, y, z + j).setData((byte) id);
				
			}
			
		}
		
	}
	
	public static boolean isBattleConnected(Territory territory, Empire empire) {
		
		for (BattleConnection connection : connections) {
			
			if (connection.territory1.equals(territory)) {
				
				if (connection.territory2.owner.equals(empire)) {
					
					return true;
					
				}
				
			} else if (connection.territory2.equals(territory)) {
				
				if (connection.territory1.owner.equals(empire)) {
					
					return true;
					
				}
				
			}
			
		}
		
		return false;
		
	}
	
}


