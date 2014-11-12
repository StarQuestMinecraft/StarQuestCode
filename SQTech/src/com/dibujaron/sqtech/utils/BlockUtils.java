package com.dibujaron.sqtech.utils;

import org.bukkit.block.Block;

public class BlockUtils {
	
	public static Block[] getEdges(Block b, boolean includeDiagnoals, boolean includeSelf, boolean includeTopBottom){
		int size;
		if(includeDiagnoals){
			size = 18;
		} else {
			size = 6;
		}
		
		if(includeSelf){
			size++;
		}
		if(!includeTopBottom){
			size -= 2;
			if(includeDiagnoals){
				size -= 8;
			}
		}
		Block[] retval = new Block[size];
		
		int index = 0;
		//block itself
		if(includeSelf){
			retval[index++] = b;
		}
		
		//faces
		retval[index++] = b.getRelative(0, 1, 0);
		retval[index++] = b.getRelative(0, -1, 0);
		if(includeTopBottom){
			retval[index++] = b.getRelative(1, 0, 0);
			retval[index++] = b.getRelative(-1, 0, 0);
		}
		retval[index++] = b.getRelative(0, 0, 1);
		retval[index++] = b.getRelative(0, 0, -1);
		
		if(includeDiagnoals){
			//edges on the upper side
			if(includeTopBottom){
				retval[index++] = b.getRelative(1, 1, 0);
				retval[index++] = b.getRelative(-1, 1, 0);
				retval[index++] = b.getRelative(0, 1, 1);
				retval[index++] = b.getRelative(0, 1, -1);
				
				//edges on the lower side
				retval[index++] = b.getRelative(1, -1, 0);
				retval[index++] = b.getRelative(-1, -1, 0);
				retval[index++] = b.getRelative(0, -1, 1);
				retval[index++] = b.getRelative(0, -1, -1);
			}
			
			//edges on the same plane
			retval[index++] = b.getRelative(1, 0, 1);
			retval[index++] = b.getRelative(-1, 0, 1);
			retval[index++] = b.getRelative(1, 0, -1);
			retval[index++] = b.getRelative(-1, 0, -1);
		}
		
		return retval;
	}
}
