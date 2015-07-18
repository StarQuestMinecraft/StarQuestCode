package com.starquestminecraft.sqapocalypse;

import org.bukkit.Chunk;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.scheduler.BukkitRunnable;

import static org.bukkit.Material.*;

public class DestroyTask extends BukkitRunnable{
	World w;
	int stage;
	
	public DestroyTask(World w, int stage){
		this.w = w;
		this.stage = stage;
	}


	@Override
	public void run() {
		
		if(Math.random() < 0.1){
			Chunk c = getRandomChunk(w);
			Block b = getBlockInChunk(c);
			b.getWorld().strikeLightning(b.getLocation());
			b.getWorld().createExplosion(b.getLocation(), 4.0F);
		}
		
	}


	private Block getBlockInChunk(Chunk c2) {
		int xMod = (int) (Math.random() * 16);
		int zMod = (int) (Math.random() * 16);
		int x = c2.getX() + xMod;
		int z = c2.getZ() + zMod;
		return getHighestBlockAt(c2.getWorld(), x,z);
	}
	
	private Block getHighestBlockAt(World world, int x, int z) {
		for(int y = 255; y > 0; y--){
			int id = world.getBlockTypeIdAt(x, y, z);
			if(id != 0){
				return world.getBlockAt(x,y,z);
			}
		}
		return world.getBlockAt(x,0,z);
	}


	private Chunk getRandomChunk(World w2) {
		if(Math.random() > 0.5){
			int radius = SQApocalypse.radius;
			int x = (int) (Math.random() * radius * 2) - radius;
			int z = (int) (Math.random() * radius * 2) - radius;
			Chunk c = w2.getChunkAt(x,z);
			return c;
		} else {
			Chunk[] chunks = w2.getLoadedChunks();
			Chunk c = chunks[(int) (Math.random() * chunks.length)];
			if(Math.abs(c.getX()) < 100 && Math.abs(c.getZ()) < 100){
				//it's a spawn chunk pick again
				int radius = SQApocalypse.radius;
				int x = (int) (Math.random() * radius * 2) - radius;
				int z = (int) (Math.random() * radius * 2) - radius;
				c = w2.getChunkAt(x,z);
			}
			return c;
		}
	}

	
}
