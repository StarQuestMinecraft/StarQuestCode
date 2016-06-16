package com.starquestminecraft.sqtechbase;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Material;
import org.bukkit.block.Block;

public class Connection {

	List<Block> wire;
	
	GUIBlock endPoint1;
	GUIBlock endPoint2;
	
	public Connection (List<Block> wire, GUIBlock endPoint1, GUIBlock endPoint2) {
		
		this.wire = wire;
		
		this.endPoint1 = endPoint1;
		this.endPoint2 = endPoint2;
		
	}
	
	@SuppressWarnings("deprecation")
	public static List<Connection> detectConnections(GUIBlock startPoint) {
		
		List<Connection> connections = new ArrayList<Connection>();
		
		Block[] surrounding = getSurrounding(startPoint.getLocation().getBlock());
		
		for (Block block : surrounding) {
			
			if (block.getType().equals(Material.STAINED_GLASS)) {
	
				short data = block.getData();
				
				List<List<Block>> paths = new ArrayList<List<Block>>();
				
				paths.add(new ArrayList<Block>());

				paths.get(0).add(block);
				
				Block currentBlock = block;
				Block lastBlock = startPoint.getLocation().getBlock();
				
				boolean done = false;
				boolean first= true;
				
				int itteration = 0;
				
				int currentPath = 0;
				
				while (!done) {
					
					itteration ++;
					
					int numberOfSurrounding = getHowManySurrounding(currentBlock, data);
					
					if (numberOfSurrounding == 1 && first) {
						
						Block[] surrounding1 = getSurrounding(currentBlock);
						
						for (int i = 0; i < 6; i ++) {
							
							if (isWire(surrounding1[i], data)) {
								
								paths.get(currentPath).add(surrounding1[i]);
								
								lastBlock = currentBlock;
								currentBlock = surrounding1[i];
								
							}
							
						}
						
					} else if (numberOfSurrounding == 2 && !first) {
						
						Block[] surrounding1 = getSurrounding(currentBlock);
						
						for (int i = 0; i < 6; i ++) {
							
							if (!surrounding1[i].getLocation().equals(lastBlock.getLocation())) {
								
								if (isWire(surrounding1[i], data)) {
	
									paths.get(currentPath).add(surrounding1[i]);
									
									lastBlock = currentBlock;
									currentBlock = surrounding1[i];
									
								}
								
							}
							
						}
						
					} else if ((numberOfSurrounding == 1 && !first) || (numberOfSurrounding == 0 && first)) {
						
						paths.get(currentPath).add(currentBlock);
						
						done = true;
						
					} else {
					
												
						
					}
					
					first = false;
					
					if (itteration > 500) done = true;
					
				}
				
				for (int i = 0; i < paths.size(); i ++) {
					
					for (int j = 0; j < paths.get(i).size(); j ++) {
						
						paths.get(i).get(j).setType(Material.BEDROCK);
						
					}
					
				}
				
			}
			
		}
		
		return connections;
		
	}
	
	@SuppressWarnings("deprecation")
	public static boolean isWire(Block block, short data) {
		
		if (block.getType().equals(Material.STAINED_GLASS) && block.getData() == data) {
			
			return true;
			
		}
		
		return false;
		
	}
	
	public static Block[] getSurrounding(Block block) {
		
		return new Block[]{block.getRelative(1, 0, 0), block.getRelative(-1, 0, 0), block.getRelative(0, 1, 0), block.getRelative(0, -1, 0), block.getRelative(0, 0, 1), block.getRelative(0, 0, -1)};
				
	}
	

	public static int getHowManySurrounding(Block block, short data) {
		
		int howMany = 0;
		
		if (isWire(block.getRelative(1, 0, 0), data)) howMany ++;
		if (isWire(block.getRelative(-1, 0, 0), data)) howMany ++;
		if (isWire(block.getRelative(0, 1, 0), data)) howMany ++;
		if (isWire(block.getRelative(0, -1, 0), data)) howMany ++;
		if (isWire(block.getRelative(0, 0, 1), data)) howMany ++;
		if (isWire(block.getRelative(0, 0, -1), data)) howMany ++;
		
		return howMany;
		
	}
	
}
