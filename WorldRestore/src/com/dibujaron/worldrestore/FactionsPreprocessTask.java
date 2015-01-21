package com.dibujaron.worldrestore;

import java.util.Set;

import org.bukkit.Chunk;
import org.bukkit.World;

import com.massivecraft.factions.entity.BoardColl;
import com.massivecraft.factions.entity.Faction;
import com.massivecraft.massivecore.ps.PS;

public class FactionsPreprocessTask extends PreprocessTask{

	Faction f;
	
	public FactionsPreprocessTask(Faction f){
		this.f = f;
	}
	@Override
	public void process(World from, World to) {
		System.out.println("Preprocessing: Faction " + f.getName());
		Set<PS> chunks = BoardColl.get().getChunks(f);
		for (PS ps : chunks) {
			if (ps.getWorld().equals(from.getName())) {
				Chunk c = ps.asBukkitChunk();
				int cx = c.getX() * 16;
				int cz = c.getZ() * 16;
				System.out.println("Preprocessing:      FactionBlock x=" + cx + ",z=" + cz);
				for (int x = cx; x < cx + 16; x++) {
					for (int z = cz; z < cz + 16; z++) {
						for (int y = 0; y < 256; y++) {
							WorldRestore.addToProcessQueues(from.getBlockAt(x, y, z));
						}
					}
				}
			}
		}
	}

}
