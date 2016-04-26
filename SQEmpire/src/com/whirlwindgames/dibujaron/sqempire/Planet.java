package com.whirlwindgames.dibujaron.sqempire;


public enum Planet {
	NONE(0, "None"),
	XIRA(1, "Xira"),
	FELDOST(2, "Feldost"),
	HYLON(3, "Hylon"),
	GRALLION(4, "Grallion"),
	BELT(5, "Asteroid Belt");
	
	
	int id;
	String name;
	
	Planet(int id, String name){
		this.id = id;
		this.name = name;
	}
	
	public int getID(){
		return id;
	}
	
	public String getName(){
		return name;
	}
	
	public static Planet fromID(int id){
		for(Planet p : values()){
			if(p.getID() == id){
				return p;
			}
		}
		return NONE;
	}
	
	public static Planet fromName(String name){
		for(Planet p : values()){
			if(p.getName().equals(name)){
				return p;
			}
		}
		return NONE;
	}
}
