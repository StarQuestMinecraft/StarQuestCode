package com.ginger_walnut.sqpowertools.utils;

import com.ginger_walnut.sqpowertools.objects.Attribute;

public class AttributeUtils {

	public static String getDisplayName(Attribute attribute) {
		
		switch (attribute.attribute) {
		
			case "generic.attackSpeed": return "Attack Speed";
			case "generic.attackDamage": return "Damage";
			case "generic.movementSpeed": return "Movement Speed";
			case "generic.armor": return "Armor";
			case "generic.knockbackResistance": return "Knockback Resistance";
			case "generic.luck": return "Luck";
			case "generic.maxHealth": return "Health";
			case "generic.armorToughness": return "Armor Toughness";
			default: return attribute.attribute;
		
		}
		
	}
	
	public static float getAdjustment(Attribute attribute) {
		
		switch (attribute.attribute) {
		
			case "generic.attackSpeed": return 4.0f;
			case "generic.attackDamage": return 1.0f;
			default: return 0.0f;
	
		}
		
	}
	
}
