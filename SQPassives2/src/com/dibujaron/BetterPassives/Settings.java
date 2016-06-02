package com.dibujaron.BetterPassives;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.bukkit.configuration.Configuration;
import org.bukkit.entity.EntityType;

public class Settings {

	private static List<EntityType> passives;
	private static List<EntityType> hostiles;
	private static HashMap<String, Planet> planets = new HashMap<String, Planet>();
	private static int tamedPerChunk = 16;

	public static void loadSettings(Configuration c) {
		if (c.contains("passivesperchunk")) {
			tamedPerChunk = c.getInt("passivesperchunk");
		}
		passives = new ArrayList<EntityType>();
		for (String s : c.getStringList("allPassives")) {
			EntityType t = EntityType.valueOf(s.toUpperCase());
			if (t == null) {
				System.out.println("No EntityType found for " + s + "!");
			} else {
				passives.add(EntityType.valueOf(s.toUpperCase()));
			}
		}
		hostiles = new ArrayList<EntityType>();
		for (String s : c.getStringList("allHostiles")) {
			EntityType t = EntityType.valueOf(s.toUpperCase());
			if (t == null) {
				System.out.println("No EntityType found for " + s + "!");
			} else {
				hostiles.add(EntityType.valueOf(s.toUpperCase()));
			}
		}

		for (String key : c.getConfigurationSection("planets").getKeys(false)) {
			List<String> passives = c.getStringList("planets." + key + ".passives");
			List<String> hostiles = c.getStringList("planets." + key + ".hostiles");
			System.out
					.println("Loaded " + passives.size() + " passives and " + hostiles.size() + " hostiles for " + key);
			Planet p = new Planet(key, hostiles, passives);
			planets.put(key.toLowerCase(), p);
		}
	}

	public static int getTamedPerChunk() {
		return tamedPerChunk;
	}

	public static List<EntityType> getAllHostiles() {
		return hostiles;
	}

	public static List<EntityType> getAllPassives() {
		return passives;
	}

	public static List<EntityType> getHostilesOfPlanet(String planet) {
		Planet p = planets.get(planet.toLowerCase());
		if (p != null) {
			if (p.hostiles().size() > 0) {
				return p.hostiles();
			}
		}
		return null;
	}

	public static List<EntityType> getPassivesOfPlanet(String planet) {
		Planet p = planets.get(planet.toLowerCase());
		if (p != null) {
			if (p.passives().size() > 0)
				return p.passives();
		}
		return null;
	}

	private static class Planet {

		String name;
		List<EntityType> hostiles;
		List<EntityType> passives;

		public Planet(String name, List<String> hostiles, List<String> passives) {
			this.name = name;
			this.hostiles = new ArrayList<EntityType>();
			for (String s : hostiles) {
				s = s.toUpperCase();
				EntityType t = EntityType.valueOf(s);
				if (t == null) {
					System.out.println("No EntityType found for " + s + "!");
				} else {
					this.hostiles.add(EntityType.valueOf(s));
				}
			}
			this.passives = new ArrayList<EntityType>();
			for (String s : passives) {
				s = s.toUpperCase();
				EntityType t = EntityType.valueOf(s);
				if (t == null) {
					System.out.println("No EntityType found for " + s + "!");
				} else {
					this.passives.add(EntityType.valueOf(s));
				}
			}
		}

		@SuppressWarnings("unused")
		public String getName() {
			return name;
		}

		public List<EntityType> hostiles() {
			return hostiles;
		}

		public List<EntityType> passives() {
			return passives;
		}
	}
}
