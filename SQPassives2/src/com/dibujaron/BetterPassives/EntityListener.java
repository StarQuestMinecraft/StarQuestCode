package com.dibujaron.BetterPassives;

import java.util.Arrays;
import java.util.List;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.world.ChunkLoadEvent;
import org.bukkit.event.world.ChunkUnloadEvent;
import org.bukkit.util.Vector;

public class ChunkListener implements Listener {
	BetterPassives p;

	static final List<Material> SPAWN_TYPES = Arrays.asList(new Material[] { Material.GRASS, Material.SNOW,
			Material.MYCEL, Material.SNOW_BLOCK, Material.LEAVES, Material.NETHERRACK });

	static final List<Material> PASSTHROUGH_TYPES = Arrays
			.asList(new Material[] { Material.LEAVES, Material.WOOL, Material.LOG });

	/*
	 * if ((baseLocBlock.getType() != Material.GRASS) && (baseLocBlock.getType()
	 * != Material.SNOW) && (baseLocBlock.getType() != Material.LONG_GRASS) &&
	 * (baseLocBlock.getType() != Material.MYCEL) && (baseLocBlock.getType() !=
	 * Material.SNOW_BLOCK) && (baseLocBlock.getType() != Material.LEAVES)) {
	 * return; }
	 */
	ChunkListener(BetterPassives plugin) {
		this.p = plugin;
	}

	@EventHandler(priority = EventPriority.HIGH)
	public void onChunkUnload(ChunkUnloadEvent event) {
		Entity[] entitiesInChunk = event.getChunk().getEntities();
		if (entitiesInChunk.length == 0)
			return;

		List<EntityType> passives = Settings.getAllPassives();
		List<EntityType> hostiles = Settings.getAllHostiles();

		for (Entity e : entitiesInChunk) {
			if ((hostiles != null && hostiles.contains(e.getType())
					|| (passives != null && passives.contains(e.getType())))) {
				LivingEntity le = (LivingEntity) e;
				if ((le.getCustomName() == null) && (!le.isCustomNameVisible())) {
					le.remove();
				}
			} else if (e.getType() == EntityType.WITHER_SKULL) {
				e.remove();
			}
		}
	}

	@EventHandler(priority = EventPriority.HIGH)
	public void onChunkLoad(ChunkLoadEvent event) {

		if (Math.random() * 10.0 < 2.0) {
			double chunkX = event.getChunk().getX() * 16;
			double chunkZ = event.getChunk().getZ() * 16;
			List<EntityType> types = Settings.getPassivesOfPlanet(event.getWorld().getName());
			if (types == null || types.size() == 0)
				return;
			if (FactionUtils.isInClaimedLand(new Location(event.getWorld(), chunkX, 0, chunkZ)))
				return;
			double X = 16.0D * Math.random();
			double Z = 16.0D * Math.random();

			Location baseLoc = this.p
					.getRealHighestBlockAt(new Location(event.getChunk().getWorld(), chunkX + X, 5.0D, chunkZ + Z))
					.getLocation();
			Block baseLocBlock = baseLoc.getBlock();

			if (!SPAWN_TYPES.contains(baseLocBlock.getType())) {
				return;
			}

			if (PASSTHROUGH_TYPES.contains(baseLocBlock.getType())) {
				for (int s = 30; s > 0; s--) {
					Block lowerBlock = baseLocBlock.getRelative(0, s * -1, 0);
					if (lowerBlock.getType() == Material.GRASS) {
						baseLoc = lowerBlock.getLocation();
						baseLocBlock = lowerBlock;
						break;
					}
				}
			}

			EntityType type = types.get((int) (Math.random() * types.size()));

			int numToSpawn = (int) (Math.random() * 4.0D);

			Location spawnLoc = new Location(baseLoc.getWorld(), baseLoc.getX(), baseLoc.getY() + 1.0D, baseLoc.getZ());
			for (int n = numToSpawn; n > 0; n--) {
				LivingEntity e = (LivingEntity) baseLoc.getWorld().spawnEntity(spawnLoc, type);
				Vector v = new Vector(Math.random() * 3.0D, 0.0D, Math.random());
				e.setVelocity(v);
			}
		}
	}
}

/*
 * Location: C:\Users\Drew\Desktop\SQPassives.jar Qualified Name:
 * com.dibujaron.BetterPassives.ChunkListener JD-Core Version: 0.6.2
 */
