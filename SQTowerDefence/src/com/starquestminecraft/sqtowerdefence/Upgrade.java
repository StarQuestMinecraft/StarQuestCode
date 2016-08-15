package com.starquestminecraft.sqtowerdefence;

import java.io.Serializable;

import org.bukkit.Bukkit;

public class Upgrade implements Serializable {
	
	private static final long serialVersionUID = 852392945351678284L;
	UpgradeType type;
	String customName = "";
	Integer level = 0;
	Integer maxLevel = 0;
	Double cost = 0.0;
	Double baseCost = 0.0;
	Double multiplier = 1.0;
	Double boost = 0.0;
	
	public Upgrade(UpgradeType upgradeType, Double startCost, Double costMultiplier, Double upgradeBoost, Integer maximumLevel) {
		type = upgradeType;
		baseCost = startCost;
		cost = startCost;
		boost = upgradeBoost;
		multiplier = costMultiplier;
		maxLevel = maximumLevel;
	}
	
	public Upgrade createNewUpgrade() {
		
		Upgrade upgrade = new Upgrade(type, baseCost, multiplier, boost, maxLevel);
		upgrade.setLevel(level);
		upgrade.cost = cost;
		upgrade.setCustomName(customName);
		return upgrade;
		
	}
	
	public UpgradeType getUpgradeType() {
		return type;
	}
	
	public Integer getLevel() {
		return level;
	}
	
	public Double getBoost() {
		return boost;
	}
	
	public Double getInitialCost() {
		return baseCost;
	}
	
	public Double getCurrentCost() {
		updateCost();
		return cost;
	}
	
	public void setLevel(Integer i) {
		level = i;
	}
	
	public void addLevel() {
		level = level + 1;
	}
	
	public void addLevel(Integer i) {
		level = level + i;
	}
	
	public void updateCost() {
		Double price = baseCost;
		for(int i=1; i<=level; i++) {
			if(multiplier >= 0) {
				price = price * multiplier;
			}
			else {
				Math.pow(price, multiplier * -1);
			}
		}
		cost = price;
	}
	
	public void setCustomName(String string) {
		customName = string;
	}
	
}
