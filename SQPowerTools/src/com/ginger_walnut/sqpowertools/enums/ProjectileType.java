package com.ginger_walnut.sqpowertools.enums;

public enum ProjectileType {

	ARROW (0), 
	FIREBALL(1);
	
	private int id;
	
	ProjectileType(int id) {
		
		this.id = id;
		
	}
	
	public int getId() {
		
		return id;
		
	}
	
	public static ProjectileType getById(int id) {
		
		switch(id) {
		
			case 0: return ARROW;
			case 1: return FIREBALL;
		
		}
		
		return ARROW;
		
	}
	
}
