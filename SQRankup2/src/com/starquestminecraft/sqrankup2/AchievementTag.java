package com.starquestminecraft.sqrankup2;

import java.util.HashMap;



import org.bukkit.Achievement;
import org.bukkit.ChatColor;

public class AchievementTag implements BonusTag{
	// a certification given when a player gets an achievement
	
	String tagColor;
	String tag;
	String identifier;
	Achievement a;
	
	public AchievementTag(Achievement a, String tag, String tagColor){
		this.identifier = a.name();
		this.tag = tag;
		this.tagColor = tagColor;
		this.a = a;
	}
	
	public String getTagFormatted(){
		return getTagFormatted(ChatColor.WHITE);
	}
	
	public String getTagFormatted(ChatColor previous){
		return ChatColor.WHITE + "[§" + tagColor + tag + ChatColor.WHITE + "]" + previous;
	}

	@Override
	public String getTag() {
		return tag;
	}

	@Override
	public String getIdentifier() {
		return identifier;
	}
	
	public static void loadAchievements(HashMap<String, BonusTag> map){
		map(map, new AchievementTag(Achievement.MINE_WOOD, "Woodsman", "b"));
		map(map, new AchievementTag(Achievement.BUILD_PICKAXE, "Miner", "b"));
		map(map, new AchievementTag(Achievement.BUILD_FURNACE, "Cook", "b"));
		map(map, new AchievementTag(Achievement.BUILD_HOE, "Farmer", "b"));
		map(map, new AchievementTag(Achievement.BUILD_SWORD, "Swordsman", "c"));
		map(map, new AchievementTag(Achievement.MAKE_BREAD, "Baker", "b"));
		map(map, new AchievementTag(Achievement.COOK_FISH, "Fisherman", "b"));
		map(map, new AchievementTag(Achievement.ON_A_RAIL, "Minecart Engineer", "b"));
		map(map, new AchievementTag(Achievement.ENCHANTMENTS, "Enchanter", "b"));
		map(map, new AchievementTag(Achievement.OVERKILL, "Swordmaster", "9"));
		map(map, new AchievementTag(Achievement.BREW_POTION, "Brewer", "b"));
		map(map, new AchievementTag(Achievement.BOOKCASE, "Librarian", "b"));
	}
	
	private static void map(HashMap<String, BonusTag> map, AchievementTag tag){
		map.put(tag.getIdentifier(), tag);
	}
	
	public Achievement getAchievement(){
		return a;
	}
}
