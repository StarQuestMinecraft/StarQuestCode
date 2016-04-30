package com.ginger_walnut.sqsmoothcraft.ship;

public enum BlockType {
	
	NORMAL, SLAB, DIRECTIONAL;
	
	public static BlockType getBlockType(String type) {
		
		switch (type) {
		
			case "normal": return NORMAL;
			case "slab": return SLAB;
			case "directional": return DIRECTIONAL;
		
		}
		
		return NORMAL;
		
	}
	
}
