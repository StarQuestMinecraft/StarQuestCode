package com.starquestminecraft.sqcontracts.randomizer.function;

import org.bukkit.Material;

public class ShipClassData {
	String className;
	double myPrice;
	int minLevel;
	double rarity;
	
	public ShipClassData(String className, double unitPrice, int minLevel, double rarityMod){
		this.className = className;
		myPrice = unitPrice;
		this.minLevel = minLevel;
		this.rarity = rarityMod;
	}
	
	public String getType() {
		return className;
	}

	public double getUnitPrice() {
		return myPrice;
	}

	public double getRarity() {
		return rarity;
	}

	public int getMinLevel() {
		return minLevel;
	}
}
