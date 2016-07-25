package com.ginger_walnut.sqsmoothcraft.enums;

public enum BlockType {
	
	NORMAL, SLAB, DIRECTIONAL;
	
	public static BlockType getBlockType(String type) {
		
		if (type != null) {
			
			switch (type) {
			
				case "normal": return NORMAL;
				case "slab": return SLAB;
				case "directional": return DIRECTIONAL;
		
			}
			
		}

		return NORMAL;
		
	}
	
}
