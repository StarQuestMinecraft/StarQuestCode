package com.starquestminecraft.sqrankup2;

import org.bukkit.ChatColor;

public interface BonusTag {
	public String getTag();
	public String getIdentifier();
	public String getTagFormatted();
	public String getTagFormatted(ChatColor color);
}
