package net.countercraft.movecraft.sail;

import java.util.ArrayList;
import java.util.Stack;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;

public class SailGenerator {
	public ArrayList<Block> toggleSail(Block main, BlockFace forwards, boolean wool){
		System.out.println(main.getType());
		int height = getMastHeight(main);
		System.out.println("Mast height: " + height);
		ArrayList<Block> spar = getSpar(main);
		System.out.println("Spar length: " + spar.size());
		ArrayList<Block> sailWool = new ArrayList<Block>();
		for(Block b : spar){
			sailWool.addAll(createSailBlocks(b, height, forwards, wool));
		}
		System.out.println("Sail wool: " + sailWool.size());
		return sailWool;
	}
	
	private ArrayList<Block> createSailBlocks(Block sparBlock, int height, BlockFace forwards, boolean wool){
		
		Material type;
		if(wool) type = Material.WOOL;
		else type = Material.AIR;
		int topHeight = height/3;
		int bottomHeight = topHeight - 1;
		int midHeight = height - (topHeight + bottomHeight);
		ArrayList<Block> retval = new ArrayList<Block>();
		Block workingBlock = sparBlock;
		BlockFace back = getReverse(forwards);
		for(int i = 0; i < topHeight; i++){
			workingBlock = workingBlock.getRelative(BlockFace.DOWN).getRelative(forwards);
			workingBlock.setType(type);
			retval.add(workingBlock);
		}
		for(int i = 0; i < midHeight; i++){
			workingBlock = workingBlock.getRelative(BlockFace.DOWN);
			workingBlock.setType(type);
			retval.add(workingBlock);
		}
		for(int i = 0; i < bottomHeight; i++){
			workingBlock = workingBlock.getRelative(BlockFace.DOWN).getRelative(back);
			workingBlock.setType(type);
			retval.add(workingBlock);
		}
		return retval;
	}
	
	private int pythag(int a, int b){
		return (int) Math.sqrt(a * a + b * b);
	}
	
	private BlockFace getReverse(BlockFace cardinal){
		switch(cardinal){
		case NORTH:
			return BlockFace.SOUTH;
		case SOUTH:
			return BlockFace.NORTH;
		case EAST:
			return BlockFace.WEST;
		case WEST:
			return BlockFace.EAST;
		default:
			return BlockFace.UP;
		}
	}
	
	private ArrayList<Block> getSpar(Block main){
		//get the blocks that are the spar block
		ArrayList<Block> retval = new ArrayList<Block>();
		Stack<Block> blockStack = new Stack<Block>();
		blockStack.push(main);
		Material mainType = main.getType();
		System.out.println(mainType.toString());
		do {
			detectSurrounding(blockStack.pop(), mainType, blockStack, retval);
		} while (!blockStack.isEmpty());
		
		return retval;
	}
	
	private void detectSurrounding(Block b, Material mainType, Stack<Block> blockStack, ArrayList<Block> retval){
		//off by one?
		for(Block relative : getBlocksSurroundingHorizontal(b)){
				if(!retval.contains(relative)){
					detectBlock(relative, mainType, blockStack, retval);
				}
		}
	}
	
	private void detectBlock(Block b, Material mainType, Stack<Block> blockStack, ArrayList<Block> retval){
		if(b.getType().equals(mainType)){
			Block[] adjacents = getBlocksSurroundingHorizontal(b);
			int num = getNumContains(adjacents, mainType);
			if(num >= 2){
				blockStack.push(b);
				retval.add(b);
			}
		}
	}
	
	private int getNumContains(Block[] blocks, Material type){
		int retval = 0;
		for(Block b : blocks){
			if(b.getType().equals(type)){
				retval++;
			}
		}
		System.out.println("Returning " + retval + " from getNumContains()");
		return retval;
	}
	
	private Block[] getBlocksSurroundingHorizontal(Block b){
		Block[] retval = new Block[8];
		int index = 0;
		for(int x = -1; x < 2; x++){
			for(int z = -1; z < 2; z++){
				if(!(x == 0 && z == 0)){
					retval[index] = b.getRelative(x, 0, z);
					index++;
				}
			}
		}
		return retval;
	}
	private int getMastHeight(Block main){
		//the mast height is the distance from the main block to the next solid block below
		Block test = main.getRelative(0,-1,0);
		int height = 0;
		while(test.getType() == Material.AIR){
			height++;
			test = test.getRelative(0,-1,0);
		}
		return height;
	}
}
