package com.ginger_walnut.sqpowertools.objects;

import java.util.List;
import java.util.Map;

import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;

public class Modifier {

	public String name;
	public int data;
	public Material material;
	
	public int number;
	public int levels;
	
	public int energy;
	
	public List<Attribute> attributes;
	public List<Effect> effects;
	public Map<Enchantment, Integer> enchants;

	public List<String> cannotCombines;
	
	
	
}
