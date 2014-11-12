package com.dibujaron.feudalism.kingdoms;

import java.util.ArrayList;

import org.bukkit.ChatColor;

public class Kingdom {
	ArrayList<Territory> myTerritories = new ArrayList<Territory>();
	ArrayList<Kingdom> allyWishes;
	ArrayList<Kingdom> enemyWishes;
	
	public Relation getRelation(Kingdom other){
		if(allyWishes.contains(other) && other.allyWishes.contains(this)){
			return Relation.ALLY;
		} else if(enemyWishes.contains(other)||other.enemyWishes.contains(this)){
			return Relation.ENEMY;
		} else {
			return Relation.NEUTRAL;
		}
	}
	
	public ChatColor getRelationColor(Kingdom other){
		Relation r = getRelation(other);
		switch(r){
		case ENEMY:
			return ChatColor.RED;
		case ALLY:
			return ChatColor.GREEN;
		default:
			return ChatColor.WHITE;
		}
	}
}
