package com.starquestminecraft.sqcontracts.randomizer.function;

import org.bukkit.Material;

public class ItemData {
	
	Material myType;
	double myPrice;
	int minLevel;
	double rarity;
	byte data;
	
	public ItemData(Material type, byte data, double unitPrice, int minLevel, double rarityMod) {
		myType = type;
		this.data = data;
		myPrice = unitPrice;
		this.minLevel = minLevel;
		rarity = rarityMod;
	}

	public Material getType() {
		return myType;
	}

	public byte getData() {
		return data;
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
