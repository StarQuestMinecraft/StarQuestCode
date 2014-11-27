package com.burkentertainment.SQPlanet;

import org.bukkit.World;
import org.bukkit.block.Biome;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Entity;
import org.bukkit.event.entity.CreatureSpawnEvent;

public class PlanetSettings {
	private static PlanetSettings instance = null;
	private FileConfiguration config = null;
	
	// Set up the Settings as a Singleton
	protected PlanetSettings () {}
	public static PlanetSettings getInstance() {
		if (null == instance) {
			instance = new PlanetSettings();
		}
		return instance;
	}

	public void loadSettings(FileConfiguration c) {
		config = c;
	}
	
	public Object getValue(World w, Biome b, String key) {
		ConfigurationSection worldSettings = null;
		ConfigurationSection biomeSettings = null;
		
		// If set World / Biome specific setting, return that
		if (this.config.contains(w.getName())) {
			worldSettings = this.config.getConfigurationSection(w.getName());
			if (worldSettings.contains(b.name())) {
				biomeSettings = worldSettings.getConfigurationSection(b.name());
				
				if (biomeSettings.contains(key)) {
					return biomeSettings.get(key);
				}
			}
			
			if (worldSettings.contains(key)) {
				return worldSettings.get(key);
			}
		}
		// Is there a global setting to return?
		StringBuilder sb = new StringBuilder("Global.").append(key);
		String globalKey = sb.toString();
		if (this.config.contains(globalKey)) {
			return this.config.get(globalKey);
		}
		
		return null;
	}
	
	public boolean isAllowed(CreatureSpawnEvent event) {
		World world = event.getEntity().getWorld();
		Biome biome = event.getLocation().getBlock().getBiome();

		return false;
	}
	
	public void applyPlanetChanges(Entity e) {
		
	}
}

//if (event.getEntity().getType() == EntityType.SQUID) {
//if (event.getEntity().getWorld().getName().equalsIgnoreCase("Boskevine")) {
//	return;
//} else {
//	event.setCancelled(true);
//	return;
//}
//}
//if (event.getEntity().getType() == EntityType.WITHER || event.getEntity().getType() == EntityType.WITHER_SKULL) {
//return;
//}
//
//} else if (event.getSpawnReason() == SpawnReason.EGG) {
//if (!event.getEntity().getWorld().getName().equals("Kelakaria"))
//	event.setCancelled(true);
