package com.dibujaron.sqtech.pattern;

import java.util.HashMap;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;

import com.dibujaron.sqtech.utils.BlockUtils;

public class BlockPattern {
	
	Material[][][] data;
	int length, width, height;
	int anchorLength, anchorWidth, anchorHeight;
	HashMap<String, Material> materialIndex = new HashMap<String, Material>();
	private boolean finalized = false;
	public BlockPattern(int length, int width, int height){
		data = new Material[height][length][width];
		this.length = length;
		this.width = width;
		this.height = height;
	}
	
	public void setValue(String key, Material type){
		if(!finalized)
		materialIndex.put(key, type);
	}
	
	public void setAnchor(int lengthIndex, int widthIndex, int heightIndex){
		anchorLength = lengthIndex;
		anchorWidth = widthIndex;
		anchorHeight = heightIndex;
	}

	public void addLayer(int layernum, String[] layerDat) {
		if(finalized) return;
		Material[][] retval = new Material[width][height];
		for(int i = 0; i < layerDat.length; i++){
			String[] datum = layerDat[i].split(",");
			Material[] otherDatum = retval[i];
			for(int j = 0; j < datum.length; j++){
				otherDatum[i] = materialIndex.get(datum[i]);
			}
		}
		
		this.data[layernum] = retval;
	}
	
	public void finalize(){
		finalized = true;
	}
	
	public boolean detect(Block anchor){
		//first check if the anchor type is the same as the anchor type
		if(!(anchor.getType() == data[anchorHeight][anchorLength][anchorWidth])) return false;
		
		//check the blocks around the anchor to see if they match a block in the pattern.
		Material beside;
		BlockFace patternUp;
		BlockFace bdp; //beside direction patternwise
		
		if(anchorLength < data[0].length){
			beside = data[anchorHeight][anchorLength + 1][anchorWidth];
			bdp = BlockFace.SOUTH;
		}
		else if(anchorLength > 0){
			beside = data[anchorHeight][anchorLength - 1][anchorWidth];
			bdp = BlockFace.NORTH;
		}
		else if(anchorWidth < data[0][0].length){
			beside = data[anchorHeight][anchorLength][anchorWidth];
			bdp = BlockFace.EAST;
		}
		else if(anchorWidth > 0){
			beside = data[anchorHeight][anchorLength][anchorWidth -1];
			bdp = BlockFace.WEST;
		}
		else return false;
		
		Block[] edges = BlockUtils.getEdges(anchor, false, false, false);
		for(Block b : edges){
			if(b.getType() == beside){
				BlockFace dir = anchor.getFace(b);
				patternUp = calculatePatternUp(bdp, dir);
				boolean success = detectPattern(anchor, patternUp);
				if(success) return true;
			}
		}
		return false;
	}
	
	private boolean detectPattern(Block anchor, BlockFace patternUp){
		return false;
	}
	
	private BlockFace calculatePatternUp(BlockFace bdp, BlockFace dir){
		return null;
	}
}
