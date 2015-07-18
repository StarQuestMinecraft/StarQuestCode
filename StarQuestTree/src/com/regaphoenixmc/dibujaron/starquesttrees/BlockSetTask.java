package com.regaphoenixmc.dibujaron.starquesttrees;

import org.bukkit.block.Block;
import org.bukkit.scheduler.BukkitRunnable;

public class BlockSetTask extends BukkitRunnable{
	Block b;
	int type;
	byte data;
	
	BlockSetTask(Block b,int type,byte data){
		this.b = b;
		this.type = type;
		this.data = data;
	}
	@Override
	public void run() {
		b.setTypeIdAndData(type, data, false);
		
	}

}
