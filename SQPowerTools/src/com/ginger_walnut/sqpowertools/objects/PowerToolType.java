package com.ginger_walnut.sqpowertools.objects;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;

public class PowerToolType {

	public String name;
	public String configName;
	
	public Material material;
	
	public boolean hasRecipe;

	public short durability;
	
	public int energy;
	public int energyPerUse;
	
	public int maxMods;
	
	public List<Attribute> attributes = new ArrayList<Attribute>();
	public List<Effect> effects = new ArrayList<Effect>();
	public List<Modifier> modifiers = new ArrayList<Modifier>();
	
	public Map<Enchantment, Integer> enchants = new HashMap<Enchantment, Integer>();
	
	public PowerToolType(String name, String configName, Material material, boolean hasRecipe, short durability, int energy, int energyPerUse, int maxMods, List<Attribute> attributes, List<Effect> effects, List<Modifier> modifiers, Map<Enchantment, Integer> enchants) {
		
		this.name = name;
		this.configName = configName;
		
		this.material = material;
		
		this.hasRecipe = hasRecipe;
		
		this.durability = durability;
		
		this.energy = energy;
		this.energyPerUse = energyPerUse;
		
		this.maxMods = maxMods;
		
		this.attributes = attributes;
		this.effects = effects;
		this.modifiers = modifiers;
		
		this.enchants = enchants;
		
	}
	
}
