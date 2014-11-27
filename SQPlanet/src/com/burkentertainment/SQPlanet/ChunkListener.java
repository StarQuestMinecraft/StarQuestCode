package com.burkentertainment.SQPlanet;
//
//import java.util.Collections;
//import java.util.List;
//
//import org.bukkit.Location;
//import org.bukkit.Material;
//import org.bukkit.block.Biome;
//import org.bukkit.block.Block;
//import org.bukkit.entity.Ageable;
//import org.bukkit.entity.Entity;
//import org.bukkit.entity.EntityType;
//import org.bukkit.entity.LivingEntity;
//import org.bukkit.event.EventHandler;
//import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
//import org.bukkit.event.world.ChunkLoadEvent;
//import org.bukkit.event.world.ChunkUnloadEvent;
//import org.bukkit.util.Vector;
//
public class ChunkListener implements Listener{
	
	SQPlanetPlugin p;
	
	ChunkListener(SQPlanetPlugin plugin){
		p = plugin;
	}
	
//	@EventHandler(priority = EventPriority.HIGH)
//	public void onChunkUnload(ChunkUnloadEvent event){
//		
//		//get the Chunk and get its entities, if there are none then return.
//		Entity[] entitiesInChunk = event.getChunk().getEntities();
//		if (entitiesInChunk.length == 0 ) return;
//		//determine which entities are untamed passives and DESTROY THEM! :D
//		for (Entity e : entitiesInChunk){
//			if (p.passiveEntityTypes.contains(e.getType()) || e.getType() == EntityType.MUSHROOM_COW){
//				LivingEntity le = (LivingEntity) e;
//				if (le.getCustomName() == null){
//					if (le.isCustomNameVisible() == false){
//					le.remove();
//					}
//				}
//			}
//		}
//	}
//	@EventHandler(priority = EventPriority.HIGH)
//	public void onChunkLoad(ChunkLoadEvent event){
//		if ((Math.random() * 10) < 2){
//			
//			//get the Entity Type to spawn
//			List<EntityType> types = p.getAcceptableTypes(event.getWorld());
//			if (types == null) return;
//			
//			//get a Location in the chunk to spawn the creature at.
//			double X = 16 * Math.random();
//			double Z = 16 * Math.random();
//			double chunkX = event.getChunk().getX() * 16;
//			double chunkZ = event.getChunk().getZ() * 16;
//			Location baseLoc = p.getRealHighestBlockAt(new Location(event.getChunk().getWorld(), chunkX + X, 5, chunkZ + Z)).getLocation();
//			Block baseLocBlock = baseLoc.getBlock();
//			//check if it is grass or snow; if it isn't then we are out of luck. Deserts shouldn't spawn mobs. or jungles.
//			if (!(baseLocBlock.getType() == Material.GRASS)){
//				if (!(baseLocBlock.getType() == Material.SNOW)){
//					if (!(baseLocBlock.getType() == Material.LONG_GRASS)){
//						if (!(baseLocBlock.getType() == Material.MYCEL)){
//							if (!(baseLocBlock.getType() == Material.SNOW_BLOCK)){
//								if (!(baseLocBlock.getType() == Material.LEAVES)){
//									return;
//								}
//							}
//						}
//					}
//				}
//			}
//			//if the block is leaves then try for a grass block beneath it.
//			if (baseLocBlock.getType() == Material.LEAVES || baseLocBlock.getType() == Material.WOOL){
//				
//				for (short s = 30; s > 0; s--){
//					Block lowerBlock = baseLocBlock.getRelative(0, s * -1, 0);
//					if (lowerBlock.getType() == Material.GRASS){
//						baseLoc = lowerBlock.getLocation();
//						baseLocBlock = lowerBlock;
//						break;
//					}
//				}
//			}
//			//get a random creature type for the spawn
//			Collections.shuffle(types);
//			EntityType type = types.get(0);
//			
//			//got a creature type? now determine how many of those animals should spawn.
//			int numToSpawn = (int) (Math.random() * 4);
//			
//			//okay, got all that? now spawn a couple of other mobs.
//			Location spawnLoc = new Location(baseLoc.getWorld(), baseLoc.getX(), baseLoc.getY() + 1, baseLoc.getZ());
//			for (int n = numToSpawn; n > 0; n--){
//				Ageable e = (Ageable) baseLoc.getWorld().spawnEntity(spawnLoc, type);
//				
//				//Make it walk in a random direction so they spread out a bit.
//				Vector v = new Vector(Math.random() * 3, 0, Math.random());
//				e.setVelocity(v);
//			}
//		}
//	}
//
}
