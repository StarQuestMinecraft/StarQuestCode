package com.whirlwindgames.dibujaron.sqempire.util;

import org.bukkit.potion.PotionEffectType;

public class EffectUtils {
	
	public static boolean isBadEffect(PotionEffectType type) {
		
		switch(type.getId()) {
		
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
