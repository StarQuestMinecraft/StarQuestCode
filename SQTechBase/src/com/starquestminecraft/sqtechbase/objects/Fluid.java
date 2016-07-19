package com.starquestminecraft.sqtechbase.objects;

import org.bukkit.Material;

public class Fluid {

	public int id = 0;
	public Material material;
	public short durability;
	public String name;
	
	public Fluid(int id, Material material, int durability, String name) {
		
		this.id = id;
		this.material = material;
		this.durability = (short) durability;
		this.name = name;
		
	}
	
}
