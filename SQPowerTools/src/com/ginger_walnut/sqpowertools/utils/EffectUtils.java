package com.ginger_walnut.sqpowertools.utils;

import org.bukkit.potion.PotionEffectType;

public class EffectUtils {

	public static PotionEffectType getEffectFromId(int id) {
		
		switch (id) {
		
			case 1: return PotionEffectType.SPEED;
			case 2: return PotionEffectType.SLOW;
			case 3: return PotionEffectType.FAST_DIGGING;
			case 4: return PotionEffectType.SLOW_DIGGING;
			case 5: return PotionEffectType.INCREASE_DAMAGE;
			case 6: return PotionEffectType.HEAL;
			case 7: return PotionEffectType.HARM;
			case 8: return PotionEffectType.JUMP;
			case 9: return PotionEffectType.CONFUSION;
			case 10: return PotionEffectType.REGENERATION;
			case 11: return PotionEffectType.DAMAGE_RESISTANCE;
			case 12: return PotionEffectType.FIRE_RESISTANCE;
			case 13: return PotionEffectType.WATER_BREATHING;
			case 14: return PotionEffectType.INVISIBILITY;
			case 15: return PotionEffectType.BLINDNESS;
			case 16: return PotionEffectType.NIGHT_VISION;
			case 17: return PotionEffectType.HUNGER;
			case 18: return PotionEffectType.WEAKNESS;
			case 19: return PotionEffectType.POISON;
			case 20: return PotionEffectType.WITHER;
			case 21: return PotionEffectType.HEALTH_BOOST;
			case 22: return PotionEffectType.ABSORPTION;
			case 23: return PotionEffectType.SATURATION;
			case 24: return PotionEffectType.GLOWING;
			case 25: return PotionEffectType.LEVITATION;
			case 26: return PotionEffectType.LUCK;
			case 27: return PotionEffectType.UNLUCK;
			
		}
		
		return PotionEffectType.SPEED;
		
	}
	
	public static String getEffectName(int id) {
		
		switch(id) {
		
			case 1: return "Speed";
			case 2: return "Slowness";
			case 3: return "Haste";
			case 4: return "Mining Fatigue";
			case 5: return "Strength";
			case 6: return "Instant Health";
			case 7: return "Instant Damage";
			case 8: return "Jump Boost";
			case 9: return "Nausea";
			case 10: return "Regeneration";
			case 11: return "Resistance";
			case 12: return "Fire Resistance";
			case 13: return "Water Breathing";
			case 14: return "Invisibility";
			case 15: return "Blindness";
			case 16: return "Night Vision";
			case 17: return "Hunger";
			case 18: return "Weakness";
			case 19: return "Poison";
			case 20: return "Wither";
			case 21: return "Health Boost";
			case 22: return "Absorbtion";
			case 23: return "Saturation";
			case 24: return "Glowing";
			case 25: return "Levitation";
			case 26: return "Luck";
			case 27: return "Bad Luck";
		
		}
		
		return "Speed";
		
	}
	
	public static String getCaseName(int id) {
		
		switch(id) {
		
			case 1: return "on hit to you";
			case 2: return "on hit to enemy";
			case 3: return "when hit to you";
			case 4: return "when hit to enemy";
			case 5: return "when being held or worn";
		
		}
		
		return "on hit to you";
		
	}
	
	public static boolean isBadEffect(int id) {
		
		switch(id) {
		
			case 2:
			case 4:
			case 7:
			case 9:
			case 15:
			case 17:
			case 18:
			case 19:
			case 20:
			case 24:
			case 27:
				return true;
			default:
				return false;
		
		}
		
	}
	
}
