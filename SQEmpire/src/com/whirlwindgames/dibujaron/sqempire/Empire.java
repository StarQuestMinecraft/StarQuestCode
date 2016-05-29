package com.whirlwindgames.dibujaron.sqempire;

import org.bukkit.ChatColor;
import org.bukkit.DyeColor;
import org.bukkit.block.Banner;
import org.bukkit.block.Block;
import org.bukkit.block.banner.Pattern;
import org.bukkit.block.banner.PatternType;

public enum Empire {
	NONE(0, ChatColor.DARK_GRAY, ChatColor.GRAY, "None"),
	ARATOR(1, ChatColor.DARK_BLUE, ChatColor.BLUE, "Arator"),
	REQUIEM(2, ChatColor.DARK_RED, ChatColor.RED, "Requiem"),
	YAVARI(3, ChatColor.DARK_PURPLE, ChatColor.LIGHT_PURPLE, "Yavari");
	
	int id;
	ChatColor dark;
	ChatColor light;
	String name;
	
	Empire(int id, ChatColor dark, ChatColor light, String name){
		this.dark = dark;
		this.light = light;
		this.id = id;
		this.name = name;
	}
	
	public ChatColor getDarkColor(){
		return dark;
	}
	
	public ChatColor getLightColor(){
		return light;
	}
	
	public String getName(){
		return name;
	}
	
	public int getID(){
		return id;
	}
	
	public Banner getBanner(Block block) {
		
		if (id == 1) {
			
			Banner banner = (Banner) block.getState();
			banner.addPattern(new Pattern(DyeColor.BLUE, PatternType.GRADIENT_UP));
			banner.addPattern(new Pattern(DyeColor.WHITE, PatternType.CROSS));
			banner.addPattern(new Pattern(DyeColor.BLUE, PatternType.CURLY_BORDER));
			banner.addPattern(new Pattern(DyeColor.BLUE, PatternType.FLOWER));
			banner.addPattern(new Pattern(DyeColor.BLUE, PatternType.RHOMBUS_MIDDLE));
			banner.addPattern(new Pattern(DyeColor.WHITE, PatternType.CIRCLE_MIDDLE));
			
			return banner;
			
		} else  if (id == 2) {
			
			Banner banner = (Banner) block.getState();
			banner.addPattern(new Pattern(DyeColor.RED, PatternType.GRADIENT_UP));
			banner.addPattern(new Pattern(DyeColor.RED, PatternType.GRADIENT));
			banner.addPattern(new Pattern(DyeColor.BLACK, PatternType.CROSS));
			banner.addPattern(new Pattern(DyeColor.BLACK, PatternType.STRIPE_CENTER));
			banner.addPattern(new Pattern(DyeColor.GRAY, PatternType.RHOMBUS_MIDDLE));
			banner.addPattern(new Pattern(DyeColor.BLACK, PatternType.FLOWER));
			
			return banner;
			
		} else if (id == 3) {
			
			Banner banner = (Banner) block.getState();
			banner.addPattern(new Pattern(DyeColor.SILVER, PatternType.TRIANGLE_TOP));
			banner.addPattern(new Pattern(DyeColor.SILVER, PatternType.TRIANGLE_BOTTOM));
			banner.addPattern(new Pattern(DyeColor.BLACK, PatternType.GRADIENT_UP));
			banner.addPattern(new Pattern(DyeColor.BLACK, PatternType.GRADIENT));
			banner.addPattern(new Pattern(DyeColor.BLACK, PatternType.RHOMBUS_MIDDLE));
			banner.addPattern(new Pattern(DyeColor.PURPLE, PatternType.CIRCLE_MIDDLE));
			
			return banner;
			
		} else {
			
			Banner banner = (Banner) block.getState();
			
			return banner;
			
		}
		
	}
	
	public static Empire fromID(int id){
		for(Empire e : values()){
			if(e.getID() == id){
				return e;
			}
		}
		return NONE;
	}
	public static Empire fromString(String s){
		for(Empire e : values()){
			if(e.getName().equalsIgnoreCase(s)){
				return e;
			}
		}
		return NONE;
	}

	public static Empire getThirdEmpire(Empire a, Empire b) {
		//congrats you're an idiot
		if(a == b){
			System.out.println("Congrats, you played yourself. You tried to get the third empire, passing in the same empire for both args.");
			return Empire.NONE;
		}
		if(a == REQUIEM && b == ARATOR) return YAVARI;
		if(a == REQUIEM && b == YAVARI) return ARATOR;
		return REQUIEM;
	}
}
