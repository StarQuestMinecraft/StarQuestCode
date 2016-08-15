package com.ginger_walnut.sqpowertools.objects;

import java.util.List;
import java.util.Map;

import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;

public class Modifier {

	public String name;
	public int durability;
	public Material material;
	
	public int number;
	public int levels;
	
	public int energy = 0;
	public int energyPerUse = 0;
	
	public List<Attribute> attributes;
	public List<Effect> effects;
	public Map<Enchantment, Integer> enchants;

	public List<String> cannotCombines;
	
	public BlasterStats blasterStats;
	
	public Modifier() {
		
	}	
	
}
