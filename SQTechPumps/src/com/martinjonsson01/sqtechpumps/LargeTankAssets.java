package com.martinjonsson01.sqtechpumps;

import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;

import com.starquestminecraft.sqtechbase.objects.Machine;

public class LargeTankAssets {
	
	@SuppressWarnings("deprecation")
	public static boolean waterBlock(Block waterBlock, int current, double oneAndAHalfPercent, Machine machine) {
		
		if (current >= oneAndAHalfPercent && current < oneAndAHalfPercent*2) {
			
			if (SQTechPumps.machineLiquidTypeIdMap.get(machine) != null) {
				
				waterBlock.setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 7, false);
				waterBlock.getRelative(BlockFace.NORTH).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 7, false);
				waterBlock.getRelative(BlockFace.NORTH).getRelative(BlockFace.NORTH).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 7, false);
				waterBlock.getRelative(BlockFace.NORTH).getRelative(BlockFace.NORTH).getRelative(BlockFace.WEST).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 7, false);
				waterBlock.getRelative(BlockFace.NORTH).getRelative(BlockFace.NORTH).getRelative(BlockFace.WEST).getRelative(BlockFace.WEST).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 7, false);
				waterBlock.getRelative(BlockFace.NORTH).getRelative(BlockFace.NORTH).getRelative(BlockFace.EAST).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 7, false);
				waterBlock.getRelative(BlockFace.NORTH).getRelative(BlockFace.NORTH).getRelative(BlockFace.EAST).getRelative(BlockFace.EAST).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 7, false);
				waterBlock.getRelative(BlockFace.SOUTH).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 7, false);
				waterBlock.getRelative(BlockFace.SOUTH).getRelative(BlockFace.SOUTH).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 7, false);
				waterBlock.getRelative(BlockFace.SOUTH).getRelative(BlockFace.SOUTH).getRelative(BlockFace.WEST).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 7, false);
				waterBlock.getRelative(BlockFace.SOUTH).getRelative(BlockFace.SOUTH).getRelative(BlockFace.WEST).getRelative(BlockFace.WEST).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 7, false);
				waterBlock.getRelative(BlockFace.SOUTH).getRelative(BlockFace.SOUTH).getRelative(BlockFace.EAST).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 7, false);
				waterBlock.getRelative(BlockFace.SOUTH).getRelative(BlockFace.SOUTH).getRelative(BlockFace.EAST).getRelative(BlockFace.EAST).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 7, false);
				waterBlock.getRelative(BlockFace.WEST).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 7, false);
				waterBlock.getRelative(BlockFace.WEST).getRelative(BlockFace.WEST).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 7, false);
				waterBlock.getRelative(BlockFace.NORTH).getRelative(BlockFace.WEST).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 7, false);
				waterBlock.getRelative(BlockFace.NORTH).getRelative(BlockFace.WEST).getRelative(BlockFace.WEST).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 7, false);
				waterBlock.getRelative(BlockFace.SOUTH).getRelative(BlockFace.WEST).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 7, false);
				waterBlock.getRelative(BlockFace.SOUTH).getRelative(BlockFace.WEST).getRelative(BlockFace.WEST).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 7, false);
				waterBlock.getRelative(BlockFace.EAST).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 7, false);
				waterBlock.getRelative(BlockFace.EAST).getRelative(BlockFace.EAST).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 7, false);
				waterBlock.getRelative(BlockFace.NORTH).getRelative(BlockFace.EAST).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 7, false);
				waterBlock.getRelative(BlockFace.NORTH).getRelative(BlockFace.EAST).getRelative(BlockFace.EAST).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 7, false);
				waterBlock.getRelative(BlockFace.SOUTH).getRelative(BlockFace.EAST).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 7, false);
				waterBlock.getRelative(BlockFace.SOUTH).getRelative(BlockFace.EAST).getRelative(BlockFace.EAST).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 7, false);
				
				return true;
			}
			
		} else if (current >= oneAndAHalfPercent*2 && current < oneAndAHalfPercent*3) {
			
			if (SQTechPumps.machineLiquidTypeIdMap.get(machine) != null) {
				waterBlock.setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 6, false);
				waterBlock.getRelative(BlockFace.NORTH).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 6, false);
				waterBlock.getRelative(BlockFace.NORTH).getRelative(BlockFace.NORTH).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 6, false);
				waterBlock.getRelative(BlockFace.NORTH).getRelative(BlockFace.NORTH).getRelative(BlockFace.WEST).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 6, false);
				waterBlock.getRelative(BlockFace.NORTH).getRelative(BlockFace.NORTH).getRelative(BlockFace.WEST).getRelative(BlockFace.WEST).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 6, false);
				waterBlock.getRelative(BlockFace.NORTH).getRelative(BlockFace.NORTH).getRelative(BlockFace.EAST).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 6, false);
				waterBlock.getRelative(BlockFace.NORTH).getRelative(BlockFace.NORTH).getRelative(BlockFace.EAST).getRelative(BlockFace.EAST).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 6, false);
				waterBlock.getRelative(BlockFace.SOUTH).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 6, false);
				waterBlock.getRelative(BlockFace.SOUTH).getRelative(BlockFace.SOUTH).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 6, false);
				waterBlock.getRelative(BlockFace.SOUTH).getRelative(BlockFace.SOUTH).getRelative(BlockFace.WEST).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 6, false);
				waterBlock.getRelative(BlockFace.SOUTH).getRelative(BlockFace.SOUTH).getRelative(BlockFace.WEST).getRelative(BlockFace.WEST).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 6, false);
				waterBlock.getRelative(BlockFace.SOUTH).getRelative(BlockFace.SOUTH).getRelative(BlockFace.EAST).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 6, false);
				waterBlock.getRelative(BlockFace.SOUTH).getRelative(BlockFace.SOUTH).getRelative(BlockFace.EAST).getRelative(BlockFace.EAST).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 6, false);
				waterBlock.getRelative(BlockFace.WEST).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 6, false);
				waterBlock.getRelative(BlockFace.WEST).getRelative(BlockFace.WEST).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 6, false);
				waterBlock.getRelative(BlockFace.NORTH).getRelative(BlockFace.WEST).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 6, false);
				waterBlock.getRelative(BlockFace.NORTH).getRelative(BlockFace.WEST).getRelative(BlockFace.WEST).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 6, false);
				waterBlock.getRelative(BlockFace.SOUTH).getRelative(BlockFace.WEST).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 6, false);
				waterBlock.getRelative(BlockFace.SOUTH).getRelative(BlockFace.WEST).getRelative(BlockFace.WEST).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 6, false);
				waterBlock.getRelative(BlockFace.EAST).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 6, false);
				waterBlock.getRelative(BlockFace.EAST).getRelative(BlockFace.EAST).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 6, false);
				waterBlock.getRelative(BlockFace.NORTH).getRelative(BlockFace.EAST).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 6, false);
				waterBlock.getRelative(BlockFace.NORTH).getRelative(BlockFace.EAST).getRelative(BlockFace.EAST).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 6, false);
				waterBlock.getRelative(BlockFace.SOUTH).getRelative(BlockFace.EAST).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 6, false);
				waterBlock.getRelative(BlockFace.SOUTH).getRelative(BlockFace.EAST).getRelative(BlockFace.EAST).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 6, false);
				
				return true;
			}
			
		} else if (current >= oneAndAHalfPercent*3 && current < oneAndAHalfPercent*4) {
			
			if (SQTechPumps.machineLiquidTypeIdMap.get(machine) != null) {
				waterBlock.setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 5, false);
				waterBlock.getRelative(BlockFace.NORTH).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 5, false);
				waterBlock.getRelative(BlockFace.NORTH).getRelative(BlockFace.NORTH).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 5, false);
				waterBlock.getRelative(BlockFace.NORTH).getRelative(BlockFace.NORTH).getRelative(BlockFace.WEST).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 5, false);
				waterBlock.getRelative(BlockFace.NORTH).getRelative(BlockFace.NORTH).getRelative(BlockFace.WEST).getRelative(BlockFace.WEST).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 5, false);
				waterBlock.getRelative(BlockFace.NORTH).getRelative(BlockFace.NORTH).getRelative(BlockFace.EAST).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 5, false);
				waterBlock.getRelative(BlockFace.NORTH).getRelative(BlockFace.NORTH).getRelative(BlockFace.EAST).getRelative(BlockFace.EAST).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 5, false);
				waterBlock.getRelative(BlockFace.SOUTH).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 5, false);
				waterBlock.getRelative(BlockFace.SOUTH).getRelative(BlockFace.SOUTH).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 5, false);
				waterBlock.getRelative(BlockFace.SOUTH).getRelative(BlockFace.SOUTH).getRelative(BlockFace.WEST).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 5, false);
				waterBlock.getRelative(BlockFace.SOUTH).getRelative(BlockFace.SOUTH).getRelative(BlockFace.WEST).getRelative(BlockFace.WEST).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 5, false);
				waterBlock.getRelative(BlockFace.SOUTH).getRelative(BlockFace.SOUTH).getRelative(BlockFace.EAST).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 5, false);
				waterBlock.getRelative(BlockFace.SOUTH).getRelative(BlockFace.SOUTH).getRelative(BlockFace.EAST).getRelative(BlockFace.EAST).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 5, false);
				waterBlock.getRelative(BlockFace.WEST).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 5, false);
				waterBlock.getRelative(BlockFace.WEST).getRelative(BlockFace.WEST).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 5, false);
				waterBlock.getRelative(BlockFace.NORTH).getRelative(BlockFace.WEST).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 5, false);
				waterBlock.getRelative(BlockFace.NORTH).getRelative(BlockFace.WEST).getRelative(BlockFace.WEST).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 5, false);
				waterBlock.getRelative(BlockFace.SOUTH).getRelative(BlockFace.WEST).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 5, false);
				waterBlock.getRelative(BlockFace.SOUTH).getRelative(BlockFace.WEST).getRelative(BlockFace.WEST).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 5, false);
				waterBlock.getRelative(BlockFace.EAST).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 5, false);
				waterBlock.getRelative(BlockFace.EAST).getRelative(BlockFace.EAST).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 5, false);
				waterBlock.getRelative(BlockFace.NORTH).getRelative(BlockFace.EAST).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 5, false);
				waterBlock.getRelative(BlockFace.NORTH).getRelative(BlockFace.EAST).getRelative(BlockFace.EAST).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 5, false);
				waterBlock.getRelative(BlockFace.SOUTH).getRelative(BlockFace.EAST).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 5, false);
				waterBlock.getRelative(BlockFace.SOUTH).getRelative(BlockFace.EAST).getRelative(BlockFace.EAST).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 5, false);
				
				return true;
			}
			
		} else if (current >= oneAndAHalfPercent*4 && current < oneAndAHalfPercent*5) {
			
			if (SQTechPumps.machineLiquidTypeIdMap.get(machine) != null) {
				waterBlock.setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 4, false);
				waterBlock.getRelative(BlockFace.NORTH).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 4, false);
				waterBlock.getRelative(BlockFace.NORTH).getRelative(BlockFace.NORTH).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 4, false);
				waterBlock.getRelative(BlockFace.NORTH).getRelative(BlockFace.NORTH).getRelative(BlockFace.WEST).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 4, false);
				waterBlock.getRelative(BlockFace.NORTH).getRelative(BlockFace.NORTH).getRelative(BlockFace.WEST).getRelative(BlockFace.WEST).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 4, false);
				waterBlock.getRelative(BlockFace.NORTH).getRelative(BlockFace.NORTH).getRelative(BlockFace.EAST).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 4, false);
				waterBlock.getRelative(BlockFace.NORTH).getRelative(BlockFace.NORTH).getRelative(BlockFace.EAST).getRelative(BlockFace.EAST).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 4, false);
				waterBlock.getRelative(BlockFace.SOUTH).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 4, false);
				waterBlock.getRelative(BlockFace.SOUTH).getRelative(BlockFace.SOUTH).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 4, false);
				waterBlock.getRelative(BlockFace.SOUTH).getRelative(BlockFace.SOUTH).getRelative(BlockFace.WEST).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 4, false);
				waterBlock.getRelative(BlockFace.SOUTH).getRelative(BlockFace.SOUTH).getRelative(BlockFace.WEST).getRelative(BlockFace.WEST).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 4, false);
				waterBlock.getRelative(BlockFace.SOUTH).getRelative(BlockFace.SOUTH).getRelative(BlockFace.EAST).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 4, false);
				waterBlock.getRelative(BlockFace.SOUTH).getRelative(BlockFace.SOUTH).getRelative(BlockFace.EAST).getRelative(BlockFace.EAST).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 4, false);
				waterBlock.getRelative(BlockFace.WEST).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 4, false);
				waterBlock.getRelative(BlockFace.WEST).getRelative(BlockFace.WEST).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 4, false);
				waterBlock.getRelative(BlockFace.NORTH).getRelative(BlockFace.WEST).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 4, false);
				waterBlock.getRelative(BlockFace.NORTH).getRelative(BlockFace.WEST).getRelative(BlockFace.WEST).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 4, false);
				waterBlock.getRelative(BlockFace.SOUTH).getRelative(BlockFace.WEST).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 4, false);
				waterBlock.getRelative(BlockFace.SOUTH).getRelative(BlockFace.WEST).getRelative(BlockFace.WEST).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 4, false);
				waterBlock.getRelative(BlockFace.EAST).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 4, false);
				waterBlock.getRelative(BlockFace.EAST).getRelative(BlockFace.EAST).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 4, false);
				waterBlock.getRelative(BlockFace.NORTH).getRelative(BlockFace.EAST).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 4, false);
				waterBlock.getRelative(BlockFace.NORTH).getRelative(BlockFace.EAST).getRelative(BlockFace.EAST).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 4, false);
				waterBlock.getRelative(BlockFace.SOUTH).getRelative(BlockFace.EAST).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 4, false);
				waterBlock.getRelative(BlockFace.SOUTH).getRelative(BlockFace.EAST).getRelative(BlockFace.EAST).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 4, false);
				
				return true;
			}
			
		} else if (current >= oneAndAHalfPercent*5 && current < oneAndAHalfPercent*6) {
			
			if (SQTechPumps.machineLiquidTypeIdMap.get(machine) != null) {
				waterBlock.setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 3, false);
				waterBlock.getRelative(BlockFace.NORTH).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 3, false);
				waterBlock.getRelative(BlockFace.NORTH).getRelative(BlockFace.NORTH).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 3, false);
				waterBlock.getRelative(BlockFace.NORTH).getRelative(BlockFace.NORTH).getRelative(BlockFace.WEST).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 3, false);
				waterBlock.getRelative(BlockFace.NORTH).getRelative(BlockFace.NORTH).getRelative(BlockFace.WEST).getRelative(BlockFace.WEST).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 3, false);
				waterBlock.getRelative(BlockFace.NORTH).getRelative(BlockFace.NORTH).getRelative(BlockFace.EAST).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 3, false);
				waterBlock.getRelative(BlockFace.NORTH).getRelative(BlockFace.NORTH).getRelative(BlockFace.EAST).getRelative(BlockFace.EAST).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 3, false);
				waterBlock.getRelative(BlockFace.SOUTH).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 3, false);
				waterBlock.getRelative(BlockFace.SOUTH).getRelative(BlockFace.SOUTH).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 3, false);
				waterBlock.getRelative(BlockFace.SOUTH).getRelative(BlockFace.SOUTH).getRelative(BlockFace.WEST).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 3, false);
				waterBlock.getRelative(BlockFace.SOUTH).getRelative(BlockFace.SOUTH).getRelative(BlockFace.WEST).getRelative(BlockFace.WEST).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 3, false);
				waterBlock.getRelative(BlockFace.SOUTH).getRelative(BlockFace.SOUTH).getRelative(BlockFace.EAST).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 3, false);
				waterBlock.getRelative(BlockFace.SOUTH).getRelative(BlockFace.SOUTH).getRelative(BlockFace.EAST).getRelative(BlockFace.EAST).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 3, false);
				waterBlock.getRelative(BlockFace.WEST).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 3, false);
				waterBlock.getRelative(BlockFace.WEST).getRelative(BlockFace.WEST).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 3, false);
				waterBlock.getRelative(BlockFace.NORTH).getRelative(BlockFace.WEST).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 3, false);
				waterBlock.getRelative(BlockFace.NORTH).getRelative(BlockFace.WEST).getRelative(BlockFace.WEST).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 3, false);
				waterBlock.getRelative(BlockFace.SOUTH).getRelative(BlockFace.WEST).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 3, false);
				waterBlock.getRelative(BlockFace.SOUTH).getRelative(BlockFace.WEST).getRelative(BlockFace.WEST).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 3, false);
				waterBlock.getRelative(BlockFace.EAST).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 3, false);
				waterBlock.getRelative(BlockFace.EAST).getRelative(BlockFace.EAST).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 3, false);
				waterBlock.getRelative(BlockFace.NORTH).getRelative(BlockFace.EAST).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 3, false);
				waterBlock.getRelative(BlockFace.NORTH).getRelative(BlockFace.EAST).getRelative(BlockFace.EAST).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 3, false);
				waterBlock.getRelative(BlockFace.SOUTH).getRelative(BlockFace.EAST).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 3, false);
				waterBlock.getRelative(BlockFace.SOUTH).getRelative(BlockFace.EAST).getRelative(BlockFace.EAST).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 3, false);
				
				return true;
			}
			
		} else if (current >= oneAndAHalfPercent*6 && current < oneAndAHalfPercent*7) {
			
			if (SQTechPumps.machineLiquidTypeIdMap.get(machine) != null) {
				waterBlock.setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 2, false);
				waterBlock.getRelative(BlockFace.NORTH).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 2, false);
				waterBlock.getRelative(BlockFace.NORTH).getRelative(BlockFace.NORTH).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 2, false);
				waterBlock.getRelative(BlockFace.NORTH).getRelative(BlockFace.NORTH).getRelative(BlockFace.WEST).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 2, false);
				waterBlock.getRelative(BlockFace.NORTH).getRelative(BlockFace.NORTH).getRelative(BlockFace.WEST).getRelative(BlockFace.WEST).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 2, false);
				waterBlock.getRelative(BlockFace.NORTH).getRelative(BlockFace.NORTH).getRelative(BlockFace.EAST).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 2, false);
				waterBlock.getRelative(BlockFace.NORTH).getRelative(BlockFace.NORTH).getRelative(BlockFace.EAST).getRelative(BlockFace.EAST).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 2, false);
				waterBlock.getRelative(BlockFace.SOUTH).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 2, false);
				waterBlock.getRelative(BlockFace.SOUTH).getRelative(BlockFace.SOUTH).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 2, false);
				waterBlock.getRelative(BlockFace.SOUTH).getRelative(BlockFace.SOUTH).getRelative(BlockFace.WEST).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 2, false);
				waterBlock.getRelative(BlockFace.SOUTH).getRelative(BlockFace.SOUTH).getRelative(BlockFace.WEST).getRelative(BlockFace.WEST).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 2, false);
				waterBlock.getRelative(BlockFace.SOUTH).getRelative(BlockFace.SOUTH).getRelative(BlockFace.EAST).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 2, false);
				waterBlock.getRelative(BlockFace.SOUTH).getRelative(BlockFace.SOUTH).getRelative(BlockFace.EAST).getRelative(BlockFace.EAST).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 2, false);
				waterBlock.getRelative(BlockFace.WEST).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 2, false);
				waterBlock.getRelative(BlockFace.WEST).getRelative(BlockFace.WEST).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 2, false);
				waterBlock.getRelative(BlockFace.NORTH).getRelative(BlockFace.WEST).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 2, false);
				waterBlock.getRelative(BlockFace.NORTH).getRelative(BlockFace.WEST).getRelative(BlockFace.WEST).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 2, false);
				waterBlock.getRelative(BlockFace.SOUTH).getRelative(BlockFace.WEST).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 2, false);
				waterBlock.getRelative(BlockFace.SOUTH).getRelative(BlockFace.WEST).getRelative(BlockFace.WEST).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 2, false);
				waterBlock.getRelative(BlockFace.EAST).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 2, false);
				waterBlock.getRelative(BlockFace.EAST).getRelative(BlockFace.EAST).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 2, false);
				waterBlock.getRelative(BlockFace.NORTH).getRelative(BlockFace.EAST).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 2, false);
				waterBlock.getRelative(BlockFace.NORTH).getRelative(BlockFace.EAST).getRelative(BlockFace.EAST).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 2, false);
				waterBlock.getRelative(BlockFace.SOUTH).getRelative(BlockFace.EAST).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 2, false);
				waterBlock.getRelative(BlockFace.SOUTH).getRelative(BlockFace.EAST).getRelative(BlockFace.EAST).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 2, false);
				
				return true;
			}
			
		} else if (current >= oneAndAHalfPercent*7 && current < oneAndAHalfPercent*8) {
			
			if (SQTechPumps.machineLiquidTypeIdMap.get(machine) != null) {
				waterBlock.setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 1, false);
				waterBlock.getRelative(BlockFace.NORTH).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 1, false);
				waterBlock.getRelative(BlockFace.NORTH).getRelative(BlockFace.NORTH).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 1, false);
				waterBlock.getRelative(BlockFace.NORTH).getRelative(BlockFace.NORTH).getRelative(BlockFace.WEST).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 1, false);
				waterBlock.getRelative(BlockFace.NORTH).getRelative(BlockFace.NORTH).getRelative(BlockFace.WEST).getRelative(BlockFace.WEST).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 1, false);
				waterBlock.getRelative(BlockFace.NORTH).getRelative(BlockFace.NORTH).getRelative(BlockFace.EAST).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 1, false);
				waterBlock.getRelative(BlockFace.NORTH).getRelative(BlockFace.NORTH).getRelative(BlockFace.EAST).getRelative(BlockFace.EAST).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 1, false);
				waterBlock.getRelative(BlockFace.SOUTH).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 1, false);
				waterBlock.getRelative(BlockFace.SOUTH).getRelative(BlockFace.SOUTH).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 1, false);
				waterBlock.getRelative(BlockFace.SOUTH).getRelative(BlockFace.SOUTH).getRelative(BlockFace.WEST).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 1, false);
				waterBlock.getRelative(BlockFace.SOUTH).getRelative(BlockFace.SOUTH).getRelative(BlockFace.WEST).getRelative(BlockFace.WEST).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 1, false);
				waterBlock.getRelative(BlockFace.SOUTH).getRelative(BlockFace.SOUTH).getRelative(BlockFace.EAST).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 1, false);
				waterBlock.getRelative(BlockFace.SOUTH).getRelative(BlockFace.SOUTH).getRelative(BlockFace.EAST).getRelative(BlockFace.EAST).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 1, false);
				waterBlock.getRelative(BlockFace.WEST).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 1, false);
				waterBlock.getRelative(BlockFace.WEST).getRelative(BlockFace.WEST).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 1, false);
				waterBlock.getRelative(BlockFace.NORTH).getRelative(BlockFace.WEST).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 1, false);
				waterBlock.getRelative(BlockFace.NORTH).getRelative(BlockFace.WEST).getRelative(BlockFace.WEST).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 1, false);
				waterBlock.getRelative(BlockFace.SOUTH).getRelative(BlockFace.WEST).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 1, false);
				waterBlock.getRelative(BlockFace.SOUTH).getRelative(BlockFace.WEST).getRelative(BlockFace.WEST).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 1, false);
				waterBlock.getRelative(BlockFace.EAST).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 1, false);
				waterBlock.getRelative(BlockFace.EAST).getRelative(BlockFace.EAST).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 1, false);
				waterBlock.getRelative(BlockFace.NORTH).getRelative(BlockFace.EAST).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 1, false);
				waterBlock.getRelative(BlockFace.NORTH).getRelative(BlockFace.EAST).getRelative(BlockFace.EAST).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 1, false);
				waterBlock.getRelative(BlockFace.SOUTH).getRelative(BlockFace.EAST).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 1, false);
				waterBlock.getRelative(BlockFace.SOUTH).getRelative(BlockFace.EAST).getRelative(BlockFace.EAST).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 1, false);
				
				return true;
			}
			
		} else if (current >= oneAndAHalfPercent*8 && current < oneAndAHalfPercent*9) {
			
			if (SQTechPumps.machineLiquidTypeIdMap.get(machine) != null) {
				waterBlock.setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 0, false);
				waterBlock.getRelative(BlockFace.NORTH).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 0, false);
				waterBlock.getRelative(BlockFace.NORTH).getRelative(BlockFace.NORTH).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 0, false);
				waterBlock.getRelative(BlockFace.NORTH).getRelative(BlockFace.NORTH).getRelative(BlockFace.WEST).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 0, false);
				waterBlock.getRelative(BlockFace.NORTH).getRelative(BlockFace.NORTH).getRelative(BlockFace.WEST).getRelative(BlockFace.WEST).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 0, false);
				waterBlock.getRelative(BlockFace.NORTH).getRelative(BlockFace.NORTH).getRelative(BlockFace.EAST).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 0, false);
				waterBlock.getRelative(BlockFace.NORTH).getRelative(BlockFace.NORTH).getRelative(BlockFace.EAST).getRelative(BlockFace.EAST).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 0, false);
				waterBlock.getRelative(BlockFace.SOUTH).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 0, false);
				waterBlock.getRelative(BlockFace.SOUTH).getRelative(BlockFace.SOUTH).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 0, false);
				waterBlock.getRelative(BlockFace.SOUTH).getRelative(BlockFace.SOUTH).getRelative(BlockFace.WEST).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 0, false);
				waterBlock.getRelative(BlockFace.SOUTH).getRelative(BlockFace.SOUTH).getRelative(BlockFace.WEST).getRelative(BlockFace.WEST).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 0, false);
				waterBlock.getRelative(BlockFace.SOUTH).getRelative(BlockFace.SOUTH).getRelative(BlockFace.EAST).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 0, false);
				waterBlock.getRelative(BlockFace.SOUTH).getRelative(BlockFace.SOUTH).getRelative(BlockFace.EAST).getRelative(BlockFace.EAST).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 0, false);
				waterBlock.getRelative(BlockFace.WEST).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 0, false);
				waterBlock.getRelative(BlockFace.WEST).getRelative(BlockFace.WEST).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 0, false);
				waterBlock.getRelative(BlockFace.NORTH).getRelative(BlockFace.WEST).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 0, false);
				waterBlock.getRelative(BlockFace.NORTH).getRelative(BlockFace.WEST).getRelative(BlockFace.WEST).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 0, false);
				waterBlock.getRelative(BlockFace.SOUTH).getRelative(BlockFace.WEST).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 0, false);
				waterBlock.getRelative(BlockFace.SOUTH).getRelative(BlockFace.WEST).getRelative(BlockFace.WEST).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 0, false);
				waterBlock.getRelative(BlockFace.EAST).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 0, false);
				waterBlock.getRelative(BlockFace.EAST).getRelative(BlockFace.EAST).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 0, false);
				waterBlock.getRelative(BlockFace.NORTH).getRelative(BlockFace.EAST).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 0, false);
				waterBlock.getRelative(BlockFace.NORTH).getRelative(BlockFace.EAST).getRelative(BlockFace.EAST).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 0, false);
				waterBlock.getRelative(BlockFace.SOUTH).getRelative(BlockFace.EAST).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 0, false);
				waterBlock.getRelative(BlockFace.SOUTH).getRelative(BlockFace.EAST).getRelative(BlockFace.EAST).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 0, false);
				
				return true;
			}
			
		}
		
		return false;
		
	}
	
	@SuppressWarnings("deprecation")
	public static boolean waterBlock2(Block waterBlock2, int current, double oneAndAHalfPercent, Machine machine) {
		
		if (current >= oneAndAHalfPercent*9 && current < oneAndAHalfPercent*10) {
			
			if (SQTechPumps.machineLiquidTypeIdMap.get(machine) != null) {
				waterBlock2.setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 7, false);
				waterBlock2.getRelative(BlockFace.NORTH).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 7, false);
				waterBlock2.getRelative(BlockFace.NORTH).getRelative(BlockFace.NORTH).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 7, false);
				waterBlock2.getRelative(BlockFace.NORTH).getRelative(BlockFace.NORTH).getRelative(BlockFace.WEST).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 7, false);
				waterBlock2.getRelative(BlockFace.NORTH).getRelative(BlockFace.NORTH).getRelative(BlockFace.WEST).getRelative(BlockFace.WEST).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 7, false);
				waterBlock2.getRelative(BlockFace.NORTH).getRelative(BlockFace.NORTH).getRelative(BlockFace.EAST).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 7, false);
				waterBlock2.getRelative(BlockFace.NORTH).getRelative(BlockFace.NORTH).getRelative(BlockFace.EAST).getRelative(BlockFace.EAST).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 7, false);
				waterBlock2.getRelative(BlockFace.SOUTH).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 7, false);
				waterBlock2.getRelative(BlockFace.SOUTH).getRelative(BlockFace.SOUTH).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 7, false);
				waterBlock2.getRelative(BlockFace.SOUTH).getRelative(BlockFace.SOUTH).getRelative(BlockFace.WEST).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 7, false);
				waterBlock2.getRelative(BlockFace.SOUTH).getRelative(BlockFace.SOUTH).getRelative(BlockFace.WEST).getRelative(BlockFace.WEST).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 7, false);
				waterBlock2.getRelative(BlockFace.SOUTH).getRelative(BlockFace.SOUTH).getRelative(BlockFace.EAST).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 7, false);
				waterBlock2.getRelative(BlockFace.SOUTH).getRelative(BlockFace.SOUTH).getRelative(BlockFace.EAST).getRelative(BlockFace.EAST).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 7, false);
				waterBlock2.getRelative(BlockFace.WEST).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 7, false);
				waterBlock2.getRelative(BlockFace.WEST).getRelative(BlockFace.WEST).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 7, false);
				waterBlock2.getRelative(BlockFace.NORTH).getRelative(BlockFace.WEST).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 7, false);
				waterBlock2.getRelative(BlockFace.NORTH).getRelative(BlockFace.WEST).getRelative(BlockFace.WEST).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 7, false);
				waterBlock2.getRelative(BlockFace.SOUTH).getRelative(BlockFace.WEST).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 7, false);
				waterBlock2.getRelative(BlockFace.SOUTH).getRelative(BlockFace.WEST).getRelative(BlockFace.WEST).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 7, false);
				waterBlock2.getRelative(BlockFace.EAST).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 7, false);
				waterBlock2.getRelative(BlockFace.EAST).getRelative(BlockFace.EAST).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 7, false);
				waterBlock2.getRelative(BlockFace.NORTH).getRelative(BlockFace.EAST).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 7, false);
				waterBlock2.getRelative(BlockFace.NORTH).getRelative(BlockFace.EAST).getRelative(BlockFace.EAST).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 7, false);
				waterBlock2.getRelative(BlockFace.SOUTH).getRelative(BlockFace.EAST).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 7, false);
				waterBlock2.getRelative(BlockFace.SOUTH).getRelative(BlockFace.EAST).getRelative(BlockFace.EAST).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 7, false);
				
				return true;
			}
			
		} else if (current >= oneAndAHalfPercent*10 && current < oneAndAHalfPercent*11) {
			
			if (SQTechPumps.machineLiquidTypeIdMap.get(machine) != null) {
				waterBlock2.setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 6, false);
				waterBlock2.getRelative(BlockFace.NORTH).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 6, false);
				waterBlock2.getRelative(BlockFace.NORTH).getRelative(BlockFace.NORTH).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 6, false);
				waterBlock2.getRelative(BlockFace.NORTH).getRelative(BlockFace.NORTH).getRelative(BlockFace.WEST).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 6, false);
				waterBlock2.getRelative(BlockFace.NORTH).getRelative(BlockFace.NORTH).getRelative(BlockFace.WEST).getRelative(BlockFace.WEST).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 6, false);
				waterBlock2.getRelative(BlockFace.NORTH).getRelative(BlockFace.NORTH).getRelative(BlockFace.EAST).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 6, false);
				waterBlock2.getRelative(BlockFace.NORTH).getRelative(BlockFace.NORTH).getRelative(BlockFace.EAST).getRelative(BlockFace.EAST).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 6, false);
				waterBlock2.getRelative(BlockFace.SOUTH).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 6, false);
				waterBlock2.getRelative(BlockFace.SOUTH).getRelative(BlockFace.SOUTH).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 6, false);
				waterBlock2.getRelative(BlockFace.SOUTH).getRelative(BlockFace.SOUTH).getRelative(BlockFace.WEST).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 6, false);
				waterBlock2.getRelative(BlockFace.SOUTH).getRelative(BlockFace.SOUTH).getRelative(BlockFace.WEST).getRelative(BlockFace.WEST).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 6, false);
				waterBlock2.getRelative(BlockFace.SOUTH).getRelative(BlockFace.SOUTH).getRelative(BlockFace.EAST).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 6, false);
				waterBlock2.getRelative(BlockFace.SOUTH).getRelative(BlockFace.SOUTH).getRelative(BlockFace.EAST).getRelative(BlockFace.EAST).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 6, false);
				waterBlock2.getRelative(BlockFace.WEST).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 6, false);
				waterBlock2.getRelative(BlockFace.WEST).getRelative(BlockFace.WEST).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 6, false);
				waterBlock2.getRelative(BlockFace.NORTH).getRelative(BlockFace.WEST).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 6, false);
				waterBlock2.getRelative(BlockFace.NORTH).getRelative(BlockFace.WEST).getRelative(BlockFace.WEST).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 6, false);
				waterBlock2.getRelative(BlockFace.SOUTH).getRelative(BlockFace.WEST).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 6, false);
				waterBlock2.getRelative(BlockFace.SOUTH).getRelative(BlockFace.WEST).getRelative(BlockFace.WEST).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 6, false);
				waterBlock2.getRelative(BlockFace.EAST).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 6, false);
				waterBlock2.getRelative(BlockFace.EAST).getRelative(BlockFace.EAST).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 6, false);
				waterBlock2.getRelative(BlockFace.NORTH).getRelative(BlockFace.EAST).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 6, false);
				waterBlock2.getRelative(BlockFace.NORTH).getRelative(BlockFace.EAST).getRelative(BlockFace.EAST).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 6, false);
				waterBlock2.getRelative(BlockFace.SOUTH).getRelative(BlockFace.EAST).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 6, false);
				waterBlock2.getRelative(BlockFace.SOUTH).getRelative(BlockFace.EAST).getRelative(BlockFace.EAST).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 6, false);
				
				return true;
			}
			
		} else if (current >= oneAndAHalfPercent*11 && current < oneAndAHalfPercent*12) {
			
			if (SQTechPumps.machineLiquidTypeIdMap.get(machine) != null) {
				waterBlock2.setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 5, false);
				waterBlock2.getRelative(BlockFace.NORTH).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 5, false);
				waterBlock2.getRelative(BlockFace.NORTH).getRelative(BlockFace.NORTH).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 5, false);
				waterBlock2.getRelative(BlockFace.NORTH).getRelative(BlockFace.NORTH).getRelative(BlockFace.WEST).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 5, false);
				waterBlock2.getRelative(BlockFace.NORTH).getRelative(BlockFace.NORTH).getRelative(BlockFace.WEST).getRelative(BlockFace.WEST).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 5, false);
				waterBlock2.getRelative(BlockFace.NORTH).getRelative(BlockFace.NORTH).getRelative(BlockFace.EAST).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 5, false);
				waterBlock2.getRelative(BlockFace.NORTH).getRelative(BlockFace.NORTH).getRelative(BlockFace.EAST).getRelative(BlockFace.EAST).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 5, false);
				waterBlock2.getRelative(BlockFace.SOUTH).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 5, false);
				waterBlock2.getRelative(BlockFace.SOUTH).getRelative(BlockFace.SOUTH).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 5, false);
				waterBlock2.getRelative(BlockFace.SOUTH).getRelative(BlockFace.SOUTH).getRelative(BlockFace.WEST).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 5, false);
				waterBlock2.getRelative(BlockFace.SOUTH).getRelative(BlockFace.SOUTH).getRelative(BlockFace.WEST).getRelative(BlockFace.WEST).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 5, false);
				waterBlock2.getRelative(BlockFace.SOUTH).getRelative(BlockFace.SOUTH).getRelative(BlockFace.EAST).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 5, false);
				waterBlock2.getRelative(BlockFace.SOUTH).getRelative(BlockFace.SOUTH).getRelative(BlockFace.EAST).getRelative(BlockFace.EAST).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 5, false);
				waterBlock2.getRelative(BlockFace.WEST).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 5, false);
				waterBlock2.getRelative(BlockFace.WEST).getRelative(BlockFace.WEST).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 5, false);
				waterBlock2.getRelative(BlockFace.NORTH).getRelative(BlockFace.WEST).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 5, false);
				waterBlock2.getRelative(BlockFace.NORTH).getRelative(BlockFace.WEST).getRelative(BlockFace.WEST).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 5, false);
				waterBlock2.getRelative(BlockFace.SOUTH).getRelative(BlockFace.WEST).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 5, false);
				waterBlock2.getRelative(BlockFace.SOUTH).getRelative(BlockFace.WEST).getRelative(BlockFace.WEST).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 5, false);
				waterBlock2.getRelative(BlockFace.EAST).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 5, false);
				waterBlock2.getRelative(BlockFace.EAST).getRelative(BlockFace.EAST).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 5, false);
				waterBlock2.getRelative(BlockFace.NORTH).getRelative(BlockFace.EAST).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 5, false);
				waterBlock2.getRelative(BlockFace.NORTH).getRelative(BlockFace.EAST).getRelative(BlockFace.EAST).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 5, false);
				waterBlock2.getRelative(BlockFace.SOUTH).getRelative(BlockFace.EAST).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 5, false);
				waterBlock2.getRelative(BlockFace.SOUTH).getRelative(BlockFace.EAST).getRelative(BlockFace.EAST).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 5, false);
				
				return true;
			}
			
		} else if (current >= oneAndAHalfPercent*12 && current < oneAndAHalfPercent*13) {
			
			if (SQTechPumps.machineLiquidTypeIdMap.get(machine) != null) {
				waterBlock2.setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 4, false);
				waterBlock2.getRelative(BlockFace.NORTH).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 4, false);
				waterBlock2.getRelative(BlockFace.NORTH).getRelative(BlockFace.NORTH).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 4, false);
				waterBlock2.getRelative(BlockFace.NORTH).getRelative(BlockFace.NORTH).getRelative(BlockFace.WEST).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 4, false);
				waterBlock2.getRelative(BlockFace.NORTH).getRelative(BlockFace.NORTH).getRelative(BlockFace.WEST).getRelative(BlockFace.WEST).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 4, false);
				waterBlock2.getRelative(BlockFace.NORTH).getRelative(BlockFace.NORTH).getRelative(BlockFace.EAST).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 4, false);
				waterBlock2.getRelative(BlockFace.NORTH).getRelative(BlockFace.NORTH).getRelative(BlockFace.EAST).getRelative(BlockFace.EAST).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 4, false);
				waterBlock2.getRelative(BlockFace.SOUTH).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 4, false);
				waterBlock2.getRelative(BlockFace.SOUTH).getRelative(BlockFace.SOUTH).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 4, false);
				waterBlock2.getRelative(BlockFace.SOUTH).getRelative(BlockFace.SOUTH).getRelative(BlockFace.WEST).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 4, false);
				waterBlock2.getRelative(BlockFace.SOUTH).getRelative(BlockFace.SOUTH).getRelative(BlockFace.WEST).getRelative(BlockFace.WEST).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 4, false);
				waterBlock2.getRelative(BlockFace.SOUTH).getRelative(BlockFace.SOUTH).getRelative(BlockFace.EAST).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 4, false);
				waterBlock2.getRelative(BlockFace.SOUTH).getRelative(BlockFace.SOUTH).getRelative(BlockFace.EAST).getRelative(BlockFace.EAST).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 4, false);
				waterBlock2.getRelative(BlockFace.WEST).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 4, false);
				waterBlock2.getRelative(BlockFace.WEST).getRelative(BlockFace.WEST).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 4, false);
				waterBlock2.getRelative(BlockFace.NORTH).getRelative(BlockFace.WEST).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 4, false);
				waterBlock2.getRelative(BlockFace.NORTH).getRelative(BlockFace.WEST).getRelative(BlockFace.WEST).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 4, false);
				waterBlock2.getRelative(BlockFace.SOUTH).getRelative(BlockFace.WEST).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 4, false);
				waterBlock2.getRelative(BlockFace.SOUTH).getRelative(BlockFace.WEST).getRelative(BlockFace.WEST).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 4, false);
				waterBlock2.getRelative(BlockFace.EAST).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 4, false);
				waterBlock2.getRelative(BlockFace.EAST).getRelative(BlockFace.EAST).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 4, false);
				waterBlock2.getRelative(BlockFace.NORTH).getRelative(BlockFace.EAST).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 4, false);
				waterBlock2.getRelative(BlockFace.NORTH).getRelative(BlockFace.EAST).getRelative(BlockFace.EAST).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 4, false);
				waterBlock2.getRelative(BlockFace.SOUTH).getRelative(BlockFace.EAST).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 4, false);
				waterBlock2.getRelative(BlockFace.SOUTH).getRelative(BlockFace.EAST).getRelative(BlockFace.EAST).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 4, false);
				
				return true;
			}
			
		} else if (current >= oneAndAHalfPercent*13 && current < oneAndAHalfPercent*14) {
			
			if (SQTechPumps.machineLiquidTypeIdMap.get(machine) != null) {
				waterBlock2.setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 3, false);
				waterBlock2.getRelative(BlockFace.NORTH).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 3, false);
				waterBlock2.getRelative(BlockFace.NORTH).getRelative(BlockFace.NORTH).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 3, false);
				waterBlock2.getRelative(BlockFace.NORTH).getRelative(BlockFace.NORTH).getRelative(BlockFace.WEST).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 3, false);
				waterBlock2.getRelative(BlockFace.NORTH).getRelative(BlockFace.NORTH).getRelative(BlockFace.WEST).getRelative(BlockFace.WEST).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 3, false);
				waterBlock2.getRelative(BlockFace.NORTH).getRelative(BlockFace.NORTH).getRelative(BlockFace.EAST).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 3, false);
				waterBlock2.getRelative(BlockFace.NORTH).getRelative(BlockFace.NORTH).getRelative(BlockFace.EAST).getRelative(BlockFace.EAST).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 3, false);
				waterBlock2.getRelative(BlockFace.SOUTH).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 3, false);
				waterBlock2.getRelative(BlockFace.SOUTH).getRelative(BlockFace.SOUTH).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 3, false);
				waterBlock2.getRelative(BlockFace.SOUTH).getRelative(BlockFace.SOUTH).getRelative(BlockFace.WEST).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 3, false);
				waterBlock2.getRelative(BlockFace.SOUTH).getRelative(BlockFace.SOUTH).getRelative(BlockFace.WEST).getRelative(BlockFace.WEST).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 3, false);
				waterBlock2.getRelative(BlockFace.SOUTH).getRelative(BlockFace.SOUTH).getRelative(BlockFace.EAST).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 3, false);
				waterBlock2.getRelative(BlockFace.SOUTH).getRelative(BlockFace.SOUTH).getRelative(BlockFace.EAST).getRelative(BlockFace.EAST).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 3, false);
				waterBlock2.getRelative(BlockFace.WEST).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 3, false);
				waterBlock2.getRelative(BlockFace.WEST).getRelative(BlockFace.WEST).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 3, false);
				waterBlock2.getRelative(BlockFace.NORTH).getRelative(BlockFace.WEST).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 3, false);
				waterBlock2.getRelative(BlockFace.NORTH).getRelative(BlockFace.WEST).getRelative(BlockFace.WEST).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 3, false);
				waterBlock2.getRelative(BlockFace.SOUTH).getRelative(BlockFace.WEST).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 3, false);
				waterBlock2.getRelative(BlockFace.SOUTH).getRelative(BlockFace.WEST).getRelative(BlockFace.WEST).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 3, false);
				waterBlock2.getRelative(BlockFace.EAST).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 3, false);
				waterBlock2.getRelative(BlockFace.EAST).getRelative(BlockFace.EAST).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 3, false);
				waterBlock2.getRelative(BlockFace.NORTH).getRelative(BlockFace.EAST).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 3, false);
				waterBlock2.getRelative(BlockFace.NORTH).getRelative(BlockFace.EAST).getRelative(BlockFace.EAST).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 3, false);
				waterBlock2.getRelative(BlockFace.SOUTH).getRelative(BlockFace.EAST).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 3, false);
				waterBlock2.getRelative(BlockFace.SOUTH).getRelative(BlockFace.EAST).getRelative(BlockFace.EAST).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 3, false);
				
				return true;
			}
			
		} else if (current >= oneAndAHalfPercent*14 && current < oneAndAHalfPercent*15) {
			
			if (SQTechPumps.machineLiquidTypeIdMap.get(machine) != null) {
				waterBlock2.setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 2, false);
				waterBlock2.getRelative(BlockFace.NORTH).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 2, false);
				waterBlock2.getRelative(BlockFace.NORTH).getRelative(BlockFace.NORTH).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 2, false);
				waterBlock2.getRelative(BlockFace.NORTH).getRelative(BlockFace.NORTH).getRelative(BlockFace.WEST).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 2, false);
				waterBlock2.getRelative(BlockFace.NORTH).getRelative(BlockFace.NORTH).getRelative(BlockFace.WEST).getRelative(BlockFace.WEST).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 2, false);
				waterBlock2.getRelative(BlockFace.NORTH).getRelative(BlockFace.NORTH).getRelative(BlockFace.EAST).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 2, false);
				waterBlock2.getRelative(BlockFace.NORTH).getRelative(BlockFace.NORTH).getRelative(BlockFace.EAST).getRelative(BlockFace.EAST).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 2, false);
				waterBlock2.getRelative(BlockFace.SOUTH).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 2, false);
				waterBlock2.getRelative(BlockFace.SOUTH).getRelative(BlockFace.SOUTH).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 2, false);
				waterBlock2.getRelative(BlockFace.SOUTH).getRelative(BlockFace.SOUTH).getRelative(BlockFace.WEST).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 2, false);
				waterBlock2.getRelative(BlockFace.SOUTH).getRelative(BlockFace.SOUTH).getRelative(BlockFace.WEST).getRelative(BlockFace.WEST).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 2, false);
				waterBlock2.getRelative(BlockFace.SOUTH).getRelative(BlockFace.SOUTH).getRelative(BlockFace.EAST).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 2, false);
				waterBlock2.getRelative(BlockFace.SOUTH).getRelative(BlockFace.SOUTH).getRelative(BlockFace.EAST).getRelative(BlockFace.EAST).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 2, false);
				waterBlock2.getRelative(BlockFace.WEST).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 2, false);
				waterBlock2.getRelative(BlockFace.WEST).getRelative(BlockFace.WEST).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 2, false);
				waterBlock2.getRelative(BlockFace.NORTH).getRelative(BlockFace.WEST).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 2, false);
				waterBlock2.getRelative(BlockFace.NORTH).getRelative(BlockFace.WEST).getRelative(BlockFace.WEST).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 2, false);
				waterBlock2.getRelative(BlockFace.SOUTH).getRelative(BlockFace.WEST).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 2, false);
				waterBlock2.getRelative(BlockFace.SOUTH).getRelative(BlockFace.WEST).getRelative(BlockFace.WEST).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 2, false);
				waterBlock2.getRelative(BlockFace.EAST).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 2, false);
				waterBlock2.getRelative(BlockFace.EAST).getRelative(BlockFace.EAST).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 2, false);
				waterBlock2.getRelative(BlockFace.NORTH).getRelative(BlockFace.EAST).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 2, false);
				waterBlock2.getRelative(BlockFace.NORTH).getRelative(BlockFace.EAST).getRelative(BlockFace.EAST).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 2, false);
				waterBlock2.getRelative(BlockFace.SOUTH).getRelative(BlockFace.EAST).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 2, false);
				waterBlock2.getRelative(BlockFace.SOUTH).getRelative(BlockFace.EAST).getRelative(BlockFace.EAST).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 2, false);
				
				return true;
			}
			
		} else if (current >= oneAndAHalfPercent*15 && current < oneAndAHalfPercent*16) {
			
			if (SQTechPumps.machineLiquidTypeIdMap.get(machine) != null) {
				waterBlock2.setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 1, false);
				waterBlock2.getRelative(BlockFace.NORTH).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 1, false);
				waterBlock2.getRelative(BlockFace.NORTH).getRelative(BlockFace.NORTH).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 1, false);
				waterBlock2.getRelative(BlockFace.NORTH).getRelative(BlockFace.NORTH).getRelative(BlockFace.WEST).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 1, false);
				waterBlock2.getRelative(BlockFace.NORTH).getRelative(BlockFace.NORTH).getRelative(BlockFace.WEST).getRelative(BlockFace.WEST).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 1, false);
				waterBlock2.getRelative(BlockFace.NORTH).getRelative(BlockFace.NORTH).getRelative(BlockFace.EAST).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 1, false);
				waterBlock2.getRelative(BlockFace.NORTH).getRelative(BlockFace.NORTH).getRelative(BlockFace.EAST).getRelative(BlockFace.EAST).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 1, false);
				waterBlock2.getRelative(BlockFace.SOUTH).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 1, false);
				waterBlock2.getRelative(BlockFace.SOUTH).getRelative(BlockFace.SOUTH).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 1, false);
				waterBlock2.getRelative(BlockFace.SOUTH).getRelative(BlockFace.SOUTH).getRelative(BlockFace.WEST).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 1, false);
				waterBlock2.getRelative(BlockFace.SOUTH).getRelative(BlockFace.SOUTH).getRelative(BlockFace.WEST).getRelative(BlockFace.WEST).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 1, false);
				waterBlock2.getRelative(BlockFace.SOUTH).getRelative(BlockFace.SOUTH).getRelative(BlockFace.EAST).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 1, false);
				waterBlock2.getRelative(BlockFace.SOUTH).getRelative(BlockFace.SOUTH).getRelative(BlockFace.EAST).getRelative(BlockFace.EAST).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 1, false);
				waterBlock2.getRelative(BlockFace.WEST).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 1, false);
				waterBlock2.getRelative(BlockFace.WEST).getRelative(BlockFace.WEST).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 1, false);
				waterBlock2.getRelative(BlockFace.NORTH).getRelative(BlockFace.WEST).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 1, false);
				waterBlock2.getRelative(BlockFace.NORTH).getRelative(BlockFace.WEST).getRelative(BlockFace.WEST).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 1, false);
				waterBlock2.getRelative(BlockFace.SOUTH).getRelative(BlockFace.WEST).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 1, false);
				waterBlock2.getRelative(BlockFace.SOUTH).getRelative(BlockFace.WEST).getRelative(BlockFace.WEST).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 1, false);
				waterBlock2.getRelative(BlockFace.EAST).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 1, false);
				waterBlock2.getRelative(BlockFace.EAST).getRelative(BlockFace.EAST).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 1, false);
				waterBlock2.getRelative(BlockFace.NORTH).getRelative(BlockFace.EAST).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 1, false);
				waterBlock2.getRelative(BlockFace.NORTH).getRelative(BlockFace.EAST).getRelative(BlockFace.EAST).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 1, false);
				waterBlock2.getRelative(BlockFace.SOUTH).getRelative(BlockFace.EAST).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 1, false);
				waterBlock2.getRelative(BlockFace.SOUTH).getRelative(BlockFace.EAST).getRelative(BlockFace.EAST).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 1, false);
				
				return true;
			}
			
		} else if (current >= oneAndAHalfPercent*16 && current < oneAndAHalfPercent*17) {
			
			if (SQTechPumps.machineLiquidTypeIdMap.get(machine) != null) {
				waterBlock2.setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 0, false);
				waterBlock2.getRelative(BlockFace.NORTH).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 0, false);
				waterBlock2.getRelative(BlockFace.NORTH).getRelative(BlockFace.NORTH).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 0, false);
				waterBlock2.getRelative(BlockFace.NORTH).getRelative(BlockFace.NORTH).getRelative(BlockFace.WEST).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 0, false);
				waterBlock2.getRelative(BlockFace.NORTH).getRelative(BlockFace.NORTH).getRelative(BlockFace.WEST).getRelative(BlockFace.WEST).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 0, false);
				waterBlock2.getRelative(BlockFace.NORTH).getRelative(BlockFace.NORTH).getRelative(BlockFace.EAST).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 0, false);
				waterBlock2.getRelative(BlockFace.NORTH).getRelative(BlockFace.NORTH).getRelative(BlockFace.EAST).getRelative(BlockFace.EAST).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 0, false);
				waterBlock2.getRelative(BlockFace.SOUTH).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 0, false);
				waterBlock2.getRelative(BlockFace.SOUTH).getRelative(BlockFace.SOUTH).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 0, false);
				waterBlock2.getRelative(BlockFace.SOUTH).getRelative(BlockFace.SOUTH).getRelative(BlockFace.WEST).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 0, false);
				waterBlock2.getRelative(BlockFace.SOUTH).getRelative(BlockFace.SOUTH).getRelative(BlockFace.WEST).getRelative(BlockFace.WEST).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 0, false);
				waterBlock2.getRelative(BlockFace.SOUTH).getRelative(BlockFace.SOUTH).getRelative(BlockFace.EAST).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 0, false);
				waterBlock2.getRelative(BlockFace.SOUTH).getRelative(BlockFace.SOUTH).getRelative(BlockFace.EAST).getRelative(BlockFace.EAST).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 0, false);
				waterBlock2.getRelative(BlockFace.WEST).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 0, false);
				waterBlock2.getRelative(BlockFace.WEST).getRelative(BlockFace.WEST).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 0, false);
				waterBlock2.getRelative(BlockFace.NORTH).getRelative(BlockFace.WEST).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 0, false);
				waterBlock2.getRelative(BlockFace.NORTH).getRelative(BlockFace.WEST).getRelative(BlockFace.WEST).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 0, false);
				waterBlock2.getRelative(BlockFace.SOUTH).getRelative(BlockFace.WEST).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 0, false);
				waterBlock2.getRelative(BlockFace.SOUTH).getRelative(BlockFace.WEST).getRelative(BlockFace.WEST).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 0, false);
				waterBlock2.getRelative(BlockFace.EAST).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 0, false);
				waterBlock2.getRelative(BlockFace.EAST).getRelative(BlockFace.EAST).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 0, false);
				waterBlock2.getRelative(BlockFace.NORTH).getRelative(BlockFace.EAST).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 0, false);
				waterBlock2.getRelative(BlockFace.NORTH).getRelative(BlockFace.EAST).getRelative(BlockFace.EAST).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 0, false);
				waterBlock2.getRelative(BlockFace.SOUTH).getRelative(BlockFace.EAST).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 0, false);
				waterBlock2.getRelative(BlockFace.SOUTH).getRelative(BlockFace.EAST).getRelative(BlockFace.EAST).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 0, false);
				
				return true;
			}
			
		}
		
		return false;
		
	}
	
	@SuppressWarnings("deprecation")
	public static boolean waterBlock3(Block waterBlock3, int current, double oneAndAHalfPercent, Machine machine) {
		
		if (current >= oneAndAHalfPercent*17 && current < oneAndAHalfPercent*18) {
			
			if (SQTechPumps.machineLiquidTypeIdMap.get(machine) != null) {
				waterBlock3.setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 7, false);
				waterBlock3.getRelative(BlockFace.NORTH).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 7, false);
				waterBlock3.getRelative(BlockFace.NORTH).getRelative(BlockFace.NORTH).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 7, false);
				waterBlock3.getRelative(BlockFace.NORTH).getRelative(BlockFace.NORTH).getRelative(BlockFace.WEST).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 7, false);
				waterBlock3.getRelative(BlockFace.NORTH).getRelative(BlockFace.NORTH).getRelative(BlockFace.WEST).getRelative(BlockFace.WEST).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 7, false);
				waterBlock3.getRelative(BlockFace.NORTH).getRelative(BlockFace.NORTH).getRelative(BlockFace.EAST).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 7, false);
				waterBlock3.getRelative(BlockFace.NORTH).getRelative(BlockFace.NORTH).getRelative(BlockFace.EAST).getRelative(BlockFace.EAST).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 7, false);
				waterBlock3.getRelative(BlockFace.SOUTH).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 7, false);
				waterBlock3.getRelative(BlockFace.SOUTH).getRelative(BlockFace.SOUTH).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 7, false);
				waterBlock3.getRelative(BlockFace.SOUTH).getRelative(BlockFace.SOUTH).getRelative(BlockFace.WEST).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 7, false);
				waterBlock3.getRelative(BlockFace.SOUTH).getRelative(BlockFace.SOUTH).getRelative(BlockFace.WEST).getRelative(BlockFace.WEST).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 7, false);
				waterBlock3.getRelative(BlockFace.SOUTH).getRelative(BlockFace.SOUTH).getRelative(BlockFace.EAST).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 7, false);
				waterBlock3.getRelative(BlockFace.SOUTH).getRelative(BlockFace.SOUTH).getRelative(BlockFace.EAST).getRelative(BlockFace.EAST).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 7, false);
				waterBlock3.getRelative(BlockFace.WEST).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 7, false);
				waterBlock3.getRelative(BlockFace.WEST).getRelative(BlockFace.WEST).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 7, false);
				waterBlock3.getRelative(BlockFace.NORTH).getRelative(BlockFace.WEST).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 7, false);
				waterBlock3.getRelative(BlockFace.NORTH).getRelative(BlockFace.WEST).getRelative(BlockFace.WEST).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 7, false);
				waterBlock3.getRelative(BlockFace.SOUTH).getRelative(BlockFace.WEST).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 7, false);
				waterBlock3.getRelative(BlockFace.SOUTH).getRelative(BlockFace.WEST).getRelative(BlockFace.WEST).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 7, false);
				waterBlock3.getRelative(BlockFace.EAST).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 7, false);
				waterBlock3.getRelative(BlockFace.EAST).getRelative(BlockFace.EAST).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 7, false);
				waterBlock3.getRelative(BlockFace.NORTH).getRelative(BlockFace.EAST).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 7, false);
				waterBlock3.getRelative(BlockFace.NORTH).getRelative(BlockFace.EAST).getRelative(BlockFace.EAST).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 7, false);
				waterBlock3.getRelative(BlockFace.SOUTH).getRelative(BlockFace.EAST).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 7, false);
				waterBlock3.getRelative(BlockFace.SOUTH).getRelative(BlockFace.EAST).getRelative(BlockFace.EAST).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 7, false);
				
				return true;
			}
			
		} else if (current >= oneAndAHalfPercent*18 && current < oneAndAHalfPercent*19) {
			
			if (SQTechPumps.machineLiquidTypeIdMap.get(machine) != null) {
				waterBlock3.setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 6, false);
				waterBlock3.getRelative(BlockFace.NORTH).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 6, false);
				waterBlock3.getRelative(BlockFace.NORTH).getRelative(BlockFace.NORTH).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 6, false);
				waterBlock3.getRelative(BlockFace.NORTH).getRelative(BlockFace.NORTH).getRelative(BlockFace.WEST).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 6, false);
				waterBlock3.getRelative(BlockFace.NORTH).getRelative(BlockFace.NORTH).getRelative(BlockFace.WEST).getRelative(BlockFace.WEST).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 6, false);
				waterBlock3.getRelative(BlockFace.NORTH).getRelative(BlockFace.NORTH).getRelative(BlockFace.EAST).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 6, false);
				waterBlock3.getRelative(BlockFace.NORTH).getRelative(BlockFace.NORTH).getRelative(BlockFace.EAST).getRelative(BlockFace.EAST).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 6, false);
				waterBlock3.getRelative(BlockFace.SOUTH).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 6, false);
				waterBlock3.getRelative(BlockFace.SOUTH).getRelative(BlockFace.SOUTH).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 6, false);
				waterBlock3.getRelative(BlockFace.SOUTH).getRelative(BlockFace.SOUTH).getRelative(BlockFace.WEST).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 6, false);
				waterBlock3.getRelative(BlockFace.SOUTH).getRelative(BlockFace.SOUTH).getRelative(BlockFace.WEST).getRelative(BlockFace.WEST).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 6, false);
				waterBlock3.getRelative(BlockFace.SOUTH).getRelative(BlockFace.SOUTH).getRelative(BlockFace.EAST).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 6, false);
				waterBlock3.getRelative(BlockFace.SOUTH).getRelative(BlockFace.SOUTH).getRelative(BlockFace.EAST).getRelative(BlockFace.EAST).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 6, false);
				waterBlock3.getRelative(BlockFace.WEST).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 6, false);
				waterBlock3.getRelative(BlockFace.WEST).getRelative(BlockFace.WEST).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 6, false);
				waterBlock3.getRelative(BlockFace.NORTH).getRelative(BlockFace.WEST).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 6, false);
				waterBlock3.getRelative(BlockFace.NORTH).getRelative(BlockFace.WEST).getRelative(BlockFace.WEST).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 6, false);
				waterBlock3.getRelative(BlockFace.SOUTH).getRelative(BlockFace.WEST).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 6, false);
				waterBlock3.getRelative(BlockFace.SOUTH).getRelative(BlockFace.WEST).getRelative(BlockFace.WEST).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 6, false);
				waterBlock3.getRelative(BlockFace.EAST).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 6, false);
				waterBlock3.getRelative(BlockFace.EAST).getRelative(BlockFace.EAST).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 6, false);
				waterBlock3.getRelative(BlockFace.NORTH).getRelative(BlockFace.EAST).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 6, false);
				waterBlock3.getRelative(BlockFace.NORTH).getRelative(BlockFace.EAST).getRelative(BlockFace.EAST).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 6, false);
				waterBlock3.getRelative(BlockFace.SOUTH).getRelative(BlockFace.EAST).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 6, false);
				waterBlock3.getRelative(BlockFace.SOUTH).getRelative(BlockFace.EAST).getRelative(BlockFace.EAST).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 6, false);
				
				return true;
			}
			
		} else if (current >= oneAndAHalfPercent*19 && current < oneAndAHalfPercent*20) {
			
			if (SQTechPumps.machineLiquidTypeIdMap.get(machine) != null) {
				waterBlock3.setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 5, false);
				waterBlock3.getRelative(BlockFace.NORTH).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 5, false);
				waterBlock3.getRelative(BlockFace.NORTH).getRelative(BlockFace.NORTH).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 5, false);
				waterBlock3.getRelative(BlockFace.NORTH).getRelative(BlockFace.NORTH).getRelative(BlockFace.WEST).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 5, false);
				waterBlock3.getRelative(BlockFace.NORTH).getRelative(BlockFace.NORTH).getRelative(BlockFace.WEST).getRelative(BlockFace.WEST).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 5, false);
				waterBlock3.getRelative(BlockFace.NORTH).getRelative(BlockFace.NORTH).getRelative(BlockFace.EAST).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 5, false);
				waterBlock3.getRelative(BlockFace.NORTH).getRelative(BlockFace.NORTH).getRelative(BlockFace.EAST).getRelative(BlockFace.EAST).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 5, false);
				waterBlock3.getRelative(BlockFace.SOUTH).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 5, false);
				waterBlock3.getRelative(BlockFace.SOUTH).getRelative(BlockFace.SOUTH).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 5, false);
				waterBlock3.getRelative(BlockFace.SOUTH).getRelative(BlockFace.SOUTH).getRelative(BlockFace.WEST).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 5, false);
				waterBlock3.getRelative(BlockFace.SOUTH).getRelative(BlockFace.SOUTH).getRelative(BlockFace.WEST).getRelative(BlockFace.WEST).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 5, false);
				waterBlock3.getRelative(BlockFace.SOUTH).getRelative(BlockFace.SOUTH).getRelative(BlockFace.EAST).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 5, false);
				waterBlock3.getRelative(BlockFace.SOUTH).getRelative(BlockFace.SOUTH).getRelative(BlockFace.EAST).getRelative(BlockFace.EAST).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 5, false);
				waterBlock3.getRelative(BlockFace.WEST).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 5, false);
				waterBlock3.getRelative(BlockFace.WEST).getRelative(BlockFace.WEST).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 5, false);
				waterBlock3.getRelative(BlockFace.NORTH).getRelative(BlockFace.WEST).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 5, false);
				waterBlock3.getRelative(BlockFace.NORTH).getRelative(BlockFace.WEST).getRelative(BlockFace.WEST).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 5, false);
				waterBlock3.getRelative(BlockFace.SOUTH).getRelative(BlockFace.WEST).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 5, false);
				waterBlock3.getRelative(BlockFace.SOUTH).getRelative(BlockFace.WEST).getRelative(BlockFace.WEST).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 5, false);
				waterBlock3.getRelative(BlockFace.EAST).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 5, false);
				waterBlock3.getRelative(BlockFace.EAST).getRelative(BlockFace.EAST).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 5, false);
				waterBlock3.getRelative(BlockFace.NORTH).getRelative(BlockFace.EAST).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 5, false);
				waterBlock3.getRelative(BlockFace.NORTH).getRelative(BlockFace.EAST).getRelative(BlockFace.EAST).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 5, false);
				waterBlock3.getRelative(BlockFace.SOUTH).getRelative(BlockFace.EAST).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 5, false);
				waterBlock3.getRelative(BlockFace.SOUTH).getRelative(BlockFace.EAST).getRelative(BlockFace.EAST).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 5, false);
				
				return true;
			}
			
		} else if (current >= oneAndAHalfPercent*20 && current < oneAndAHalfPercent*21) {
			
			if (SQTechPumps.machineLiquidTypeIdMap.get(machine) != null) {
				waterBlock3.setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 4, false);
				waterBlock3.getRelative(BlockFace.NORTH).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 4, false);
				waterBlock3.getRelative(BlockFace.NORTH).getRelative(BlockFace.NORTH).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 4, false);
				waterBlock3.getRelative(BlockFace.NORTH).getRelative(BlockFace.NORTH).getRelative(BlockFace.WEST).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 4, false);
				waterBlock3.getRelative(BlockFace.NORTH).getRelative(BlockFace.NORTH).getRelative(BlockFace.WEST).getRelative(BlockFace.WEST).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 4, false);
				waterBlock3.getRelative(BlockFace.NORTH).getRelative(BlockFace.NORTH).getRelative(BlockFace.EAST).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 4, false);
				waterBlock3.getRelative(BlockFace.NORTH).getRelative(BlockFace.NORTH).getRelative(BlockFace.EAST).getRelative(BlockFace.EAST).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 4, false);
				waterBlock3.getRelative(BlockFace.SOUTH).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 4, false);
				waterBlock3.getRelative(BlockFace.SOUTH).getRelative(BlockFace.SOUTH).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 4, false);
				waterBlock3.getRelative(BlockFace.SOUTH).getRelative(BlockFace.SOUTH).getRelative(BlockFace.WEST).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 4, false);
				waterBlock3.getRelative(BlockFace.SOUTH).getRelative(BlockFace.SOUTH).getRelative(BlockFace.WEST).getRelative(BlockFace.WEST).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 4, false);
				waterBlock3.getRelative(BlockFace.SOUTH).getRelative(BlockFace.SOUTH).getRelative(BlockFace.EAST).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 4, false);
				waterBlock3.getRelative(BlockFace.SOUTH).getRelative(BlockFace.SOUTH).getRelative(BlockFace.EAST).getRelative(BlockFace.EAST).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 4, false);
				waterBlock3.getRelative(BlockFace.WEST).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 4, false);
				waterBlock3.getRelative(BlockFace.WEST).getRelative(BlockFace.WEST).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 4, false);
				waterBlock3.getRelative(BlockFace.NORTH).getRelative(BlockFace.WEST).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 4, false);
				waterBlock3.getRelative(BlockFace.NORTH).getRelative(BlockFace.WEST).getRelative(BlockFace.WEST).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 4, false);
				waterBlock3.getRelative(BlockFace.SOUTH).getRelative(BlockFace.WEST).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 4, false);
				waterBlock3.getRelative(BlockFace.SOUTH).getRelative(BlockFace.WEST).getRelative(BlockFace.WEST).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 4, false);
				waterBlock3.getRelative(BlockFace.EAST).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 4, false);
				waterBlock3.getRelative(BlockFace.EAST).getRelative(BlockFace.EAST).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 4, false);
				waterBlock3.getRelative(BlockFace.NORTH).getRelative(BlockFace.EAST).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 4, false);
				waterBlock3.getRelative(BlockFace.NORTH).getRelative(BlockFace.EAST).getRelative(BlockFace.EAST).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 4, false);
				waterBlock3.getRelative(BlockFace.SOUTH).getRelative(BlockFace.EAST).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 4, false);
				waterBlock3.getRelative(BlockFace.SOUTH).getRelative(BlockFace.EAST).getRelative(BlockFace.EAST).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 4, false);
				
				return true;
			}
			
		} else if (current >= oneAndAHalfPercent*21 && current < oneAndAHalfPercent*22) {
			
			if (SQTechPumps.machineLiquidTypeIdMap.get(machine) != null) {
				waterBlock3.setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 3, false);
				waterBlock3.getRelative(BlockFace.NORTH).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 3, false);
				waterBlock3.getRelative(BlockFace.NORTH).getRelative(BlockFace.NORTH).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 3, false);
				waterBlock3.getRelative(BlockFace.NORTH).getRelative(BlockFace.NORTH).getRelative(BlockFace.WEST).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 3, false);
				waterBlock3.getRelative(BlockFace.NORTH).getRelative(BlockFace.NORTH).getRelative(BlockFace.WEST).getRelative(BlockFace.WEST).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 3, false);
				waterBlock3.getRelative(BlockFace.NORTH).getRelative(BlockFace.NORTH).getRelative(BlockFace.EAST).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 3, false);
				waterBlock3.getRelative(BlockFace.NORTH).getRelative(BlockFace.NORTH).getRelative(BlockFace.EAST).getRelative(BlockFace.EAST).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 3, false);
				waterBlock3.getRelative(BlockFace.SOUTH).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 3, false);
				waterBlock3.getRelative(BlockFace.SOUTH).getRelative(BlockFace.SOUTH).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 3, false);
				waterBlock3.getRelative(BlockFace.SOUTH).getRelative(BlockFace.SOUTH).getRelative(BlockFace.WEST).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 3, false);
				waterBlock3.getRelative(BlockFace.SOUTH).getRelative(BlockFace.SOUTH).getRelative(BlockFace.WEST).getRelative(BlockFace.WEST).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 3, false);
				waterBlock3.getRelative(BlockFace.SOUTH).getRelative(BlockFace.SOUTH).getRelative(BlockFace.EAST).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 3, false);
				waterBlock3.getRelative(BlockFace.SOUTH).getRelative(BlockFace.SOUTH).getRelative(BlockFace.EAST).getRelative(BlockFace.EAST).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 3, false);
				waterBlock3.getRelative(BlockFace.WEST).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 3, false);
				waterBlock3.getRelative(BlockFace.WEST).getRelative(BlockFace.WEST).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 3, false);
				waterBlock3.getRelative(BlockFace.NORTH).getRelative(BlockFace.WEST).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 3, false);
				waterBlock3.getRelative(BlockFace.NORTH).getRelative(BlockFace.WEST).getRelative(BlockFace.WEST).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 3, false);
				waterBlock3.getRelative(BlockFace.SOUTH).getRelative(BlockFace.WEST).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 3, false);
				waterBlock3.getRelative(BlockFace.SOUTH).getRelative(BlockFace.WEST).getRelative(BlockFace.WEST).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 3, false);
				waterBlock3.getRelative(BlockFace.EAST).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 3, false);
				waterBlock3.getRelative(BlockFace.EAST).getRelative(BlockFace.EAST).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 3, false);
				waterBlock3.getRelative(BlockFace.NORTH).getRelative(BlockFace.EAST).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 3, false);
				waterBlock3.getRelative(BlockFace.NORTH).getRelative(BlockFace.EAST).getRelative(BlockFace.EAST).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 3, false);
				waterBlock3.getRelative(BlockFace.SOUTH).getRelative(BlockFace.EAST).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 3, false);
				waterBlock3.getRelative(BlockFace.SOUTH).getRelative(BlockFace.EAST).getRelative(BlockFace.EAST).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 3, false);
				
				return true;
			}
			
		} else if (current >= oneAndAHalfPercent*22 && current < oneAndAHalfPercent*23) {
			
			if (SQTechPumps.machineLiquidTypeIdMap.get(machine) != null) {
				waterBlock3.setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 2, false);
				waterBlock3.getRelative(BlockFace.NORTH).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 2, false);
				waterBlock3.getRelative(BlockFace.NORTH).getRelative(BlockFace.NORTH).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 2, false);
				waterBlock3.getRelative(BlockFace.NORTH).getRelative(BlockFace.NORTH).getRelative(BlockFace.WEST).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 2, false);
				waterBlock3.getRelative(BlockFace.NORTH).getRelative(BlockFace.NORTH).getRelative(BlockFace.WEST).getRelative(BlockFace.WEST).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 2, false);
				waterBlock3.getRelative(BlockFace.NORTH).getRelative(BlockFace.NORTH).getRelative(BlockFace.EAST).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 2, false);
				waterBlock3.getRelative(BlockFace.NORTH).getRelative(BlockFace.NORTH).getRelative(BlockFace.EAST).getRelative(BlockFace.EAST).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 2, false);
				waterBlock3.getRelative(BlockFace.SOUTH).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 2, false);
				waterBlock3.getRelative(BlockFace.SOUTH).getRelative(BlockFace.SOUTH).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 2, false);
				waterBlock3.getRelative(BlockFace.SOUTH).getRelative(BlockFace.SOUTH).getRelative(BlockFace.WEST).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 2, false);
				waterBlock3.getRelative(BlockFace.SOUTH).getRelative(BlockFace.SOUTH).getRelative(BlockFace.WEST).getRelative(BlockFace.WEST).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 2, false);
				waterBlock3.getRelative(BlockFace.SOUTH).getRelative(BlockFace.SOUTH).getRelative(BlockFace.EAST).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 2, false);
				waterBlock3.getRelative(BlockFace.SOUTH).getRelative(BlockFace.SOUTH).getRelative(BlockFace.EAST).getRelative(BlockFace.EAST).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 2, false);
				waterBlock3.getRelative(BlockFace.WEST).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 2, false);
				waterBlock3.getRelative(BlockFace.WEST).getRelative(BlockFace.WEST).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 2, false);
				waterBlock3.getRelative(BlockFace.NORTH).getRelative(BlockFace.WEST).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 2, false);
				waterBlock3.getRelative(BlockFace.NORTH).getRelative(BlockFace.WEST).getRelative(BlockFace.WEST).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 2, false);
				waterBlock3.getRelative(BlockFace.SOUTH).getRelative(BlockFace.WEST).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 2, false);
				waterBlock3.getRelative(BlockFace.SOUTH).getRelative(BlockFace.WEST).getRelative(BlockFace.WEST).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 2, false);
				waterBlock3.getRelative(BlockFace.EAST).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 2, false);
				waterBlock3.getRelative(BlockFace.EAST).getRelative(BlockFace.EAST).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 2, false);
				waterBlock3.getRelative(BlockFace.NORTH).getRelative(BlockFace.EAST).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 2, false);
				waterBlock3.getRelative(BlockFace.NORTH).getRelative(BlockFace.EAST).getRelative(BlockFace.EAST).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 2, false);
				waterBlock3.getRelative(BlockFace.SOUTH).getRelative(BlockFace.EAST).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 2, false);
				waterBlock3.getRelative(BlockFace.SOUTH).getRelative(BlockFace.EAST).getRelative(BlockFace.EAST).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 2, false);
				
				return true;
			}
			
		} else if (current >= oneAndAHalfPercent*23 && current < oneAndAHalfPercent*24) {
			
			if (SQTechPumps.machineLiquidTypeIdMap.get(machine) != null) {
				waterBlock3.setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 1, false);
				waterBlock3.getRelative(BlockFace.NORTH).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 1, false);
				waterBlock3.getRelative(BlockFace.NORTH).getRelative(BlockFace.NORTH).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 1, false);
				waterBlock3.getRelative(BlockFace.NORTH).getRelative(BlockFace.NORTH).getRelative(BlockFace.WEST).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 1, false);
				waterBlock3.getRelative(BlockFace.NORTH).getRelative(BlockFace.NORTH).getRelative(BlockFace.WEST).getRelative(BlockFace.WEST).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 1, false);
				waterBlock3.getRelative(BlockFace.NORTH).getRelative(BlockFace.NORTH).getRelative(BlockFace.EAST).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 1, false);
				waterBlock3.getRelative(BlockFace.NORTH).getRelative(BlockFace.NORTH).getRelative(BlockFace.EAST).getRelative(BlockFace.EAST).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 1, false);
				waterBlock3.getRelative(BlockFace.SOUTH).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 1, false);
				waterBlock3.getRelative(BlockFace.SOUTH).getRelative(BlockFace.SOUTH).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 1, false);
				waterBlock3.getRelative(BlockFace.SOUTH).getRelative(BlockFace.SOUTH).getRelative(BlockFace.WEST).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 1, false);
				waterBlock3.getRelative(BlockFace.SOUTH).getRelative(BlockFace.SOUTH).getRelative(BlockFace.WEST).getRelative(BlockFace.WEST).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 1, false);
				waterBlock3.getRelative(BlockFace.SOUTH).getRelative(BlockFace.SOUTH).getRelative(BlockFace.EAST).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 1, false);
				waterBlock3.getRelative(BlockFace.SOUTH).getRelative(BlockFace.SOUTH).getRelative(BlockFace.EAST).getRelative(BlockFace.EAST).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 1, false);
				waterBlock3.getRelative(BlockFace.WEST).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 1, false);
				waterBlock3.getRelative(BlockFace.WEST).getRelative(BlockFace.WEST).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 1, false);
				waterBlock3.getRelative(BlockFace.NORTH).getRelative(BlockFace.WEST).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 1, false);
				waterBlock3.getRelative(BlockFace.NORTH).getRelative(BlockFace.WEST).getRelative(BlockFace.WEST).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 1, false);
				waterBlock3.getRelative(BlockFace.SOUTH).getRelative(BlockFace.WEST).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 1, false);
				waterBlock3.getRelative(BlockFace.SOUTH).getRelative(BlockFace.WEST).getRelative(BlockFace.WEST).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 1, false);
				waterBlock3.getRelative(BlockFace.EAST).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 1, false);
				waterBlock3.getRelative(BlockFace.EAST).getRelative(BlockFace.EAST).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 1, false);
				waterBlock3.getRelative(BlockFace.NORTH).getRelative(BlockFace.EAST).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 1, false);
				waterBlock3.getRelative(BlockFace.NORTH).getRelative(BlockFace.EAST).getRelative(BlockFace.EAST).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 1, false);
				waterBlock3.getRelative(BlockFace.SOUTH).getRelative(BlockFace.EAST).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 1, false);
				waterBlock3.getRelative(BlockFace.SOUTH).getRelative(BlockFace.EAST).getRelative(BlockFace.EAST).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 1, false);
				
				return true;
			}
			
		} else if (current >= oneAndAHalfPercent*24 && current < oneAndAHalfPercent*25) {
			
			if (SQTechPumps.machineLiquidTypeIdMap.get(machine) != null) {
				waterBlock3.setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 0, false);
				waterBlock3.getRelative(BlockFace.NORTH).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 0, false);
				waterBlock3.getRelative(BlockFace.NORTH).getRelative(BlockFace.NORTH).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 0, false);
				waterBlock3.getRelative(BlockFace.NORTH).getRelative(BlockFace.NORTH).getRelative(BlockFace.WEST).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 0, false);
				waterBlock3.getRelative(BlockFace.NORTH).getRelative(BlockFace.NORTH).getRelative(BlockFace.WEST).getRelative(BlockFace.WEST).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 0, false);
				waterBlock3.getRelative(BlockFace.NORTH).getRelative(BlockFace.NORTH).getRelative(BlockFace.EAST).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 0, false);
				waterBlock3.getRelative(BlockFace.NORTH).getRelative(BlockFace.NORTH).getRelative(BlockFace.EAST).getRelative(BlockFace.EAST).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 0, false);
				waterBlock3.getRelative(BlockFace.SOUTH).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 0, false);
				waterBlock3.getRelative(BlockFace.SOUTH).getRelative(BlockFace.SOUTH).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 0, false);
				waterBlock3.getRelative(BlockFace.SOUTH).getRelative(BlockFace.SOUTH).getRelative(BlockFace.WEST).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 0, false);
				waterBlock3.getRelative(BlockFace.SOUTH).getRelative(BlockFace.SOUTH).getRelative(BlockFace.WEST).getRelative(BlockFace.WEST).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 0, false);
				waterBlock3.getRelative(BlockFace.SOUTH).getRelative(BlockFace.SOUTH).getRelative(BlockFace.EAST).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 0, false);
				waterBlock3.getRelative(BlockFace.SOUTH).getRelative(BlockFace.SOUTH).getRelative(BlockFace.EAST).getRelative(BlockFace.EAST).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 0, false);
				waterBlock3.getRelative(BlockFace.WEST).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 0, false);
				waterBlock3.getRelative(BlockFace.WEST).getRelative(BlockFace.WEST).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 0, false);
				waterBlock3.getRelative(BlockFace.NORTH).getRelative(BlockFace.WEST).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 0, false);
				waterBlock3.getRelative(BlockFace.NORTH).getRelative(BlockFace.WEST).getRelative(BlockFace.WEST).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 0, false);
				waterBlock3.getRelative(BlockFace.SOUTH).getRelative(BlockFace.WEST).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 0, false);
				waterBlock3.getRelative(BlockFace.SOUTH).getRelative(BlockFace.WEST).getRelative(BlockFace.WEST).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 0, false);
				waterBlock3.getRelative(BlockFace.EAST).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 0, false);
				waterBlock3.getRelative(BlockFace.EAST).getRelative(BlockFace.EAST).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 0, false);
				waterBlock3.getRelative(BlockFace.NORTH).getRelative(BlockFace.EAST).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 0, false);
				waterBlock3.getRelative(BlockFace.NORTH).getRelative(BlockFace.EAST).getRelative(BlockFace.EAST).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 0, false);
				waterBlock3.getRelative(BlockFace.SOUTH).getRelative(BlockFace.EAST).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 0, false);
				waterBlock3.getRelative(BlockFace.SOUTH).getRelative(BlockFace.EAST).getRelative(BlockFace.EAST).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 0, false);
				
				return true;
			}
			
		}
		
		return false;
		
	}
	
}
