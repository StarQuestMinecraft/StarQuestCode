package com.starquestminecraft.sqtowerdefence;

import java.io.Serializable;

public enum UpgradeType implements Serializable{
	SPEED, DAMAGE, RANGE, ACCURACY, BOT_HEALTH, BOT_DAMAGE, MAX_BOTS, BOT_WEAPON, POTION_EFFECT, TOWER;
	
	public UpgradeType getUpgradeType(String string) {
		if(string.equalsIgnoreCase("SPEED")) {
			return UpgradeType.SPEED;
		}
		else if(string.equalsIgnoreCase("DAMAGE")) {
			return UpgradeType.DAMAGE;
		}
		else if(string.equalsIgnoreCase("RANGE")) {
			return UpgradeType.RANGE;
		}
		else if(string.equalsIgnoreCase("ACCURACY")) {
			return UpgradeType.ACCURACY;
		}
		else if(string.equalsIgnoreCase("BOT_HEALTH")) {
			return UpgradeType.BOT_HEALTH;
		}
		else if(string.equalsIgnoreCase("BOT_DAMAGE")) {
			return UpgradeType.BOT_DAMAGE;
		}
		else if(string.equalsIgnoreCase("MAX_BOTS")) {
			return UpgradeType.MAX_BOTS;
		}
		else if(string.equalsIgnoreCase("BOT_WEAPON")) {
			return UpgradeType.BOT_WEAPON;
		}
		else if(string.equalsIgnoreCase("POTION_EFFECT")) {
			return UpgradeType.POTION_EFFECT;
		}
		
		return UpgradeType.TOWER;
		
	}
}
