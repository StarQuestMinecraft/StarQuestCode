package com.starquestminecraft.sqcontracts.contracts;

import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.OfflinePlayer;

import net.countercraft.movecraft.craft.Craft;
import net.milkbowl.vault.economy.Economy;
import net.milkbowl.vault.economy.EconomyResponse;

import com.starquestminecraft.sqcontracts.SQContracts;
import com.starquestminecraft.sqcontracts.database.ContractPlayerData;
import com.starquestminecraft.sqcontracts.util.CompletionStatus;

public class MoneyContract implements Contract {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private UUID player;
	private String completionTarget;
	private int cost;
	private String cause;
	
	private static final String TAG = "[" + ChatColor.DARK_AQUA + "Philanthropy" + ChatColor.WHITE + "] ";

	public MoneyContract(UUID player, String targetStation, int cost, String cause) {
		this.player = player;
		this.completionTarget = targetStation;
		this.cost = cost;
		this.cause = cause;
	}

	@Override
	public CompletionStatus complete(Craft c) {
		Economy e = SQContracts.get().getEconomy();
		OfflinePlayer plr = Bukkit.getOfflinePlayer(player);
		double bal = e.getBalance(plr);
		if(bal >= cost){
			e.withdrawPlayer(plr, cost);
			return CompletionStatus.COMPLETE;
		} else if(bal <= 0){
			return CompletionStatus.INCOMPLETE;
		} else {
			e.withdrawPlayer(plr, bal);
			return CompletionStatus.PARTIAL;
		}
	}

	@Override
	public String getTargetStation() {
		return completionTarget;
	}

	@Override
	public String getDescription() {
		return TAG + "Collect " + cost + " credits and bring them to " + getTargetStation() + " to help " + cause + "."
				+ "Doing this will earn you one philanthropy point.";
	}

	@Override
	public boolean isBlackMarket() {
		return false;
	}

	@Override
	public UUID getPlayer() {
		return player;
	}

	@Override
	public void giveRewards(ContractPlayerData d) {
		String currency = "philanthropy";
		int balance = d.getBalanceInCurrency(currency);
		d.setBalanceInCurrency(currency, balance + 1);
	}
}
