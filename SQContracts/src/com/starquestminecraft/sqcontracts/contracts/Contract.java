package com.starquestminecraft.sqcontracts.contracts;

import java.io.Serializable;
import java.util.UUID;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import com.starquestminecraft.sqcontracts.database.ContractPlayerData;
import com.starquestminecraft.sqcontracts.util.CompletionStatus;

import net.countercraft.movecraft.craft.Craft;

public interface Contract extends Serializable{
	
	public static final String BLACK_MARKET_TAG = "[" + ChatColor.DARK_RED + "Black Market" + ChatColor.RESET + "]";
	public static final String REGULAR_MARKET_TAG = "[" + ChatColor.GRAY + "Main Market" + ChatColor.RESET + "]";
	
	//-1 for complete failure, 0 for partial, 1 for success
	public CompletionStatus complete(Craft c);
	public String getDescription(ChatColor color);
	public String getTargetStation();
	public boolean isBlackMarket();
	public UUID getPlayer();
	public void giveRewards(ContractPlayerData d);
	public long getOpenedMillis();
	public void penalizeForCancellation(Player p);
	public boolean canAffordCancellation(Player p);
	
}
