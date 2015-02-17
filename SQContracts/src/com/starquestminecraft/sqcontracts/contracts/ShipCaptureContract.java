package com.starquestminecraft.sqcontracts.contracts;

import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.OfflinePlayer;

import net.countercraft.movecraft.bungee.InventoryUtils;
import net.countercraft.movecraft.craft.Craft;

import com.starquestminecraft.sqcontracts.SQContracts;
import com.starquestminecraft.sqcontracts.database.ContractPlayerData;
import com.starquestminecraft.sqcontracts.util.CompletionStatus;
import com.starquestminecraft.sqcontracts.util.InventoryUtil;

public class ShipCaptureContract implements Contract{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private static final String PIRATE_TAG = Contract.BLACK_MARKET_TAG + "[" + ChatColor.RED + "Piracy" + ChatColor.WHITE + "] ";
	private static final String PRIVATEER_TAG = "[" + ChatColor.DARK_BLUE + "Privateering" + ChatColor.WHITE + "] ";
	String[] craftTypes;
	int num;
	String targetStation;
	int reward;
	boolean blackMarket;
	UUID player;
	
	public ShipCaptureContract(UUID player, int reward, String craftType, int num, String targetStation, boolean blackMarket){
		this.craftType = craftType;
		this.num = num;
		this.reward = reward;
		this.targetStation = targetStation;
		this.blackMarket = blackMarket;
		this.player = player;
	}
	
	public CompletionStatus complete(Craft c){
		int left = InventoryUtil.removeDataCoresFromShipInventories(c, num, craftType);
		if(left > 0){
			if(left == num){
				num = left;
				return CompletionStatus.INCOMPLETE;
			}
			return CompletionStatus.PARTIAL;
		}
		return CompletionStatus.COMPLETE;
	}
	
	public String getTargetStation(){
		return targetStation;
	}

	@Override
	public String getDescription() {
		if(blackMarket){
			return PIRATE_TAG + "Capture " + num + " ships of class " + craftType +
					" and bring their data cores back to Eco Station " +
					targetStation + " for proof. Completing this contract " + 
					" earns you " + reward + " credits and one Infamy " + 
					" point.";
		} else {
			return PRIVATEER_TAG + "Capture " + num + " ships of class " + craftType +
					" piloted by [Wanted] players and bring their data cores back to Eco Station " +
					targetStation + " for proof. Completing this contract " + 
					" earns you " + reward + " credits and one Reputation " + 
					" point.";
		}
	}

	@Override
	public boolean isBlackMarket() {
		return blackMarket;
	}

	@Override
	public UUID getPlayer() {
		return player;
	}

	@Override
	public void giveRewards(ContractPlayerData d) {
		OfflinePlayer plr = Bukkit.getOfflinePlayer(d.getPlayer());
		SQContracts.get().getEconomy().depositPlayer(plr, reward);
		String currency;
		if(isBlackMarket()){
			currency = "infamy";
		} else {
			currency = "reputation";
		}
		int balance = d.getBalanceInCurrency(currency);
		d.setBalanceInCurrency(currency, balance + 1);
	}
}
