package com.ginger_walnut.sqpowertools.enums;

public enum ProjectileType {

	ARROW (0, "Arrow"), 
	FIREBALL(1, "Fireball"),
	BEAM(2, "Beam"),
	SHOTGUN(3, "Shotgun");
	
	private int id;
	private String name;
	
	ProjectileType(int id, String name) {
		
		this.id = id;
		this.name = name;
		
	}
	
	public int getId() {
		
		return id;
		
	}
	
	public String getName() {
		
		return name;
		
	}
	
	public static ProjectileType getById(int id) {
		
		switch(id) {
		
			case 0: return ARROW;
			case 1: return FIREBALL;
			case 2: return BEAM;
			case 3: return SHOTGUN;
		
		}
		
		return ARROW;
		
	}
	
}
