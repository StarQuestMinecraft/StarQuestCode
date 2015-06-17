package com.dibujaron.sqapocalypse;

import org.bukkit.Chunk;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.scheduler.BukkitRunnable;

import static org.bukkit.Material.*;

public class DestroyTask extends BukkitRunnable{
	World w;
	int stage;
	Chunk c;
	int numBlocksInChunk = 0;
	
	public DestroyTask(World w, int stage){
		this.w = w;
		this.stage = stage;
		c = getRandomChunk(w);
	}


	@Override
	public void run() {
		numBlocksInChunk++;
		Block b = getBlockInChunk(c);
		process(b);
		if(numBlocksInChunk > 20){
			c = getRandomChunk(w);
			numBlocksInChunk = 0;
		}
	}
	private void process(Block b) {

		Material type = b.getType();
		if(stage > 0){
			if(type == LEAVES || type == LEAVES_2 || type == SUGAR_CANE || type == MELON_BLOCK || type == PUMPKIN || type == WATER_LILY || type == WATER || type == STATIONARY_WATER || type == SNOW || type == SNOW_BLOCK || type == ICE || type == PACKED_ICE){
				b.setType(AIR);
				return;
			}
			if(type == CROPS || type == CARROT || type == POTATO || type == SAPLING || type == YELLOW_FLOWER || type == RED_ROSE || type == PUMPKIN_STEM || type == MELON_STEM || type == LONG_GRASS){
				b.setType(DEAD_BUSH);
				if(b.getData() != 0) b.setData((byte) 0);
				return;
			}
			if(type == DEAD_BUSH && b.getData() != 0){
				b.setData((byte) 0);
				return;
			}
			if(type == GRASS){
				b.setType(DIRT);
				b.setData((byte) 1);
			}
			if(type == STONE){
				b.setType(NETHERRACK);
			}
		}
		if(stage > 1){
			if(Math.random() < 0.2){
				if(b.getType() == AIR){
					b.setType(FIRE);
				} else {
					b.getRelative(0,1,0).setType(FIRE);
				}
			}
		}
		if(stage > 2){
			if(Math.random() < 0.001){
				b.getWorld().strikeLightning(b.getLocation());
				b.getWorld().createExplosion(b.getLocation(), 4.0F);
			}
		}
		if(stage > 3){
			if(type == DIRT){
				b.setType(SAND);
				b.setData((byte) 1);
			}
		}
		if(stage > 4){
			if(type == SAND){
				b.setType(LAVA);
			}
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
