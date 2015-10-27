package com.starquestminecraft.sqcontracts.contracts;

import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;

import net.countercraft.movecraft.bungee.InventoryUtils;
import net.countercraft.movecraft.craft.Craft;
import net.milkbowl.vault.economy.Economy;

import com.starquestminecraft.sqcontracts.SQContracts;
import com.starquestminecraft.sqcontracts.database.ContractPlayerData;
import com.starquestminecraft.sqcontracts.util.CompletionStatus;
import com.starquestminecraft.sqcontracts.util.InventoryUtil;

public class ShipCaptureContract implements Contract{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private static final String PIRATE_TAG = Contract.BLACK_MARKET_TAG + "[" + ChatColor.RED + "Piracy" + ChatColor.RESET + "] ";
	private static final String PRIVATEER_TAG = "[" + ChatColor.DARK_BLUE + "Privateering" + ChatColor.RESET + "] ";
	String[] craftTypes;
	int num;
	String targetStation;
	int reward;
	boolean blackMarket;
	UUID player;
	long openedMillis;
	
	public ShipCaptureContract(UUID player, int reward, String[] craftTypes, int num, String targetStation, boolean blackMarket){
		this.craftTypes = craftTypes;
		this.num = num;
		this.reward = reward;
		this.targetStation = targetStation;
		this.blackMarket = blackMarket;
		this.player = player;
		this.openedMillis = System.currentTimeMillis();
	}
	
	public CompletionStatus complete(Craft c){
		int left = InventoryUtil.removeDataCoresFromShipInventories(c, num, craftTypes, blackMarket);
		if(left > 0){
			if(left == num){
				num = left;
				return CompletionStatus.INCOMPLETE;
			}
			num = left;
			Bukkit.getOfflinePlayer(player).getPlayer().sendMessage("This contract requires " + Integer.toString(num) + " more data cores.");
			return CompletionStatus.PARTIAL;
		}
		return CompletionStatus.COMPLETE;
	}
	
	public String getTargetStation(){
		return targetStation;
	}

	@Override
	public String getDescription(ChatColor c) {
		
		if(blackMarket){
			return fixColor(c, PIRATE_TAG) + "Capture " + num + " ships of class " + typesToString(craftTypes) +
					" and bring their data cores back to eco station " +
					targetStation + " for proof. Completing this contract" + 
					" earns you " + reward + " credits and one Infamy" + 
					" point. While you have this contract open you will" +
					" be on the Wanted list, and while you are Wanted" + 
					" you will be pursued by Privateers.";
		} else {
			return fixColor(c, PRIVATEER_TAG) + "Capture " + num + " ships of class " + typesToString(craftTypes) +
					" piloted by [Wanted] players and bring their data cores back to eco station " +
					targetStation + " for proof. Completing this contract" + 
					" earns you " + reward + " credits and one Reputation" + 
					" point.";
		}
	}
	
	private String fixColor(ChatColor color, String string){
		return string.replaceAll(ChatColor.RESET.toString(), color.toString());
	}
	
	private String typesToString(String[] types){
		if(types.length == 1){
			return types[0];
		}
		if(types.length == 2){
			return types[0] + " or " + types[1];
		}
		
		String retval = types[0];
		for(int i = 1; i < types.length - 1; i++){
			retval = retval + ", " + types[i];
		}
		retval = retval + ", or " + types[types.length -1];
		return retval;
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
		Economy e = SQContracts.get().getEconomy();
		if(e != null){
			SQContracts.get().getEconomy().depositPlayer(plr, reward);
		}
		String currency;
		if(isBlackMarket()){
			currency = "infamy";
		} else {
			currency = "reputation";
		}
		int balance = d.getBalanceInCurrency(currency);
		d.setBalanceInCurrency(currency, balance + 1);
	}
	
	@Override
	public long getOpenedMillis() {
		return openedMillis;
	}
	
	@Override
	public void penalizeForCancellation(Player p) {
		SQContracts.get().getEconomy().withdrawPlayer(p, reward / 2);
	}
	
	@Override
	public boolean canAffordCancellation(Player p){
		return SQContracts.get().getEconomy().getBalance(p) >= (reward / 2);
	}
}
