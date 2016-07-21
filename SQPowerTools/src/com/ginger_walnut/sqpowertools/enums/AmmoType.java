package com.ginger_walnut.sqpowertools.enums;

public enum AmmoType {

	ENERGY (0, "Energy"),
	ARROWS (1, "Arrows"), 
	POTION_ARROWS (2, "Potion Arrows"), 
	ALL_ARROWS (3, "All Arrows"),
	FIRE_CHARGES (4, "Fire Charges");
	
	private int id;
	private String name;
	
	AmmoType(int id, String name) {
		
		this.id = id;
		this.name = name;
		
	}
	
	public int getId() {
		
		return id;
		
	}
	
	public String getName() {
		
		return name;
		
	}
	
	public static AmmoType getById(int id) {
		
		switch(id) {
		
			case 0: return ENERGY;
			case 1: return ARROWS;
			case 2: return POTION_ARROWS;
			case 3: return ALL_ARROWS;
			case 4: return FIRE_CHARGES;
		
		}
		
		return ENERGY;
		
	}
	
}
