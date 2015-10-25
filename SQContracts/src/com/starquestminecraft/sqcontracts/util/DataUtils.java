package com.starquestminecraft.sqcontracts.util;

import org.bukkit.Material;

public class DataUtils {

	public static String formatItemName(Material type, short data) {
		switch(type){
		case LOG:
			return formatLogOneData(data);
		case WOOD:
			return formatPlankData(data) + type.toString().toLowerCase();
		case SAPLING:
		case WOOD_STEP:
		case LEAVES:
			return formatWoodData(data) + type.toString().toLowerCase();
		case LOG_2:
			return formatLogTwoData(data);
		case WOOL:
		case CARPET:
		case STAINED_GLASS:
		case STAINED_GLASS_PANE:
		case STAINED_CLAY:
			return formatColorData(data) + " " + type.toString().toLowerCase();
		case RAW_FISH:
			return formatFishData(data);
		case COOKED_FISH:
			return formatCookedFishData(data);
		case CLAY_BRICK:
			return "brick";
		case BRICK:
			return "brick block";
		case COAL:
			if(data == 1){
				return "charcoal";
			}
			else{
				return "coal";
			}
		default:
			return formatDefaultBlock(type, data);
		}
	}
	
	private static String formatPlankData(short data) {
		switch(data){
		case 0:
			return "oak plank";
		case 1:
			return "spruce plank";
		case 2:
			return "birch plank";
		case 3:
			return "jungle plank";
		case 4:
			return "acacia plank";
		case 5:
			return "dark oak plank";
		default:
			return "oak plank";
		}
	}
	
	private static String formatWoodData(short data) {
		switch(data){
		case 0:
			return "oak";
		case 1:
			return "spruce";
		case 2:
			return "birch";
		case 3:
			return "jungle";
		case 4:
			return "acacia";
		case 5:
			return "dark oak";
		default:
			return "oak";
		}
	}
	
	private static String formatLogTwoData(short data) {
		switch(data){
		case 0:
			return "acacia log";
		case 1:
			return "dark oak log";
		default:
			return "acacia log";
		}
	}
	
	private static String formatLogOneData(short data) {
		switch(data){
		case 0:
			return "oak log";
		case 1:
			return "spruce log";
		case 2:
			return "birch log";
		case 3:
			return "jungle log";
		case 4:
			return "acacia log";
		case 5:
			return "dark oak log";
		default:
			return "oak log";
		}
	}

	private static String formatColorData(short data) {
		switch(data){
		case 0:
			return "white";
		case 1:
			return "orange";
		case 2:
			return "magenta";
		case 3:
			return "light blue";
		case 4:
			return "yellow";
		case 5:
			return "lime";
		case 6:
			return "pink";
		case 7:
			return "gray";
		case 8:
			return "light gray";
		case 9:
			return "cyan";
		case 10:
			return "purple";
		case 11:
			return "blue";
		case 12:
			return "brown";
		case 13:
			return "green";
		case 14:
			return "red";
		case 15:
			return "black";
		default:
			return "white";
		}
	}

	private static String formatFishData(short data) {
		switch(data){
		case 0:
			return "raw fish";
		case 1:
			return "raw salmon";
		case 2:
			return "clownfish";
		case 3:
			return "pufferfish";
		default:
			return "raw fish";
		}
	}
	
	private static String formatCookedFishData(short data) {
		switch(data){
		case 0:
			return "cooked fish";
		case 1:
			return "cooked salmon";
		default:
			return "cooked fish";
		}
	}
	
	public static String formatDefaultBlock(Material type, short data){
		String typeName = type.toString().toLowerCase();
		if(data == 0) return typeName;
		else return typeName + " with data value " + data;
	}
}
