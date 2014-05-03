package com.dibujaron.alliances;

public enum Relation {
	ALLY,
	TRUCE,
	NEUTRAL,
	ENEMY;
	
	public static String relationToString(Relation r){
		switch(r){
		case ALLY:
			return "a";
		case TRUCE:
			return "t";
		case NEUTRAL:
			return "n";
		case ENEMY:
			return "e";
		default:
			return "n";
		}
	}
	public static Relation stringToRelation(String s){
		switch(s){
		case "a":
			return ALLY;
		case "t":
			return TRUCE;
		case "n":
			return NEUTRAL;
		case "e":
			return ENEMY;
		default:
			return NEUTRAL;
		}
	}
}
