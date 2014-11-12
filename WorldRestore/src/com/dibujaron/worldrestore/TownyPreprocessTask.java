package com.dibujaron.worldrestore;

import org.bukkit.World;

import com.palmergames.bukkit.towny.object.Town;
import com.palmergames.bukkit.towny.object.TownBlock;

public class TownyPreprocessTask extends PreprocessTask{

	Town t;
	
	public TownyPreprocessTask(Town t){
		this.t = t;
	}
	@Override
	public void process(World from, World to) {
		System.out.println("Preprocessing: Town " + t.getName());
		for (TownBlock tb : t.getTownBlocks()) {
			if (tb.getWorld().getName().equals(from.getName())) {
				int tbx = tb.getX() * 16;
				int tbz = tb.getZ() * 16;
				System.out.println("Preprocessing:      TownBlock x=" + tbx + ",z=" + tbz);
				for (int x = tbx; x < tbx + 16; x++) {
					for (int z = tbz; z < tbz + 16; z++) {
						for (int y = 0; y < 256; y++) {
							WorldRestore.addToProcessQueues(from.getBlockAt(x, y, z));
						}
					}
				}
			}
		}
	}

}
