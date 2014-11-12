package com.dibujaron.sqtech.pattern;

import org.bukkit.Bukkit;
import org.bukkit.Material;

public class Driver {
	public static void test(){
		BlockPattern boiler = new BlockPattern(5, 3, 3);
		boiler.setValue("p", Material.PISTON_BASE);
		boiler.setValue("sbs", Material.SMOOTH_STAIRS);
		boiler.setValue("sb", Material.SMOOTH_BRICK);
		boiler.setValue("w", Material.WATER);
		boiler.setValue("f", Material.FURNACE);
		boiler.setValue("a", Material.AIR);
		boiler.setValue("c", Material.COBBLE_WALL);
		boiler.setValue("q", Material.QUARTZ_BLOCK);
		
		boiler.addLayer(0, new String[]{
			 "sb,sb,sb",
			 "sb,w,sb",
			 "sb,w,sb",
			 "sb,w,sb",
			 "sb,sb,sb"
			 });
		
		boiler.addLayer(1, new String[]{
			"a,a,a",
			"p,f,p",
			"p,c,p",
			"p,q,p",
			"sb,q,sb",
			});
		
		boiler.addLayer(2, new String[]{
			"a,a,a",
			"sbs,sb,sbs",
			"sbs,sb,sbs",
			"sbs,sb,sbs",
			"sbs,sb,sbs",
			});
		
		boolean success = boiler.detect(Bukkit.getWorlds().get(0).getBlockAt(0,0,0));
	}
}
