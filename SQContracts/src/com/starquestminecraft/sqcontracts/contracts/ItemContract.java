package com.starquestminecraft.sqcontracts.contracts;

import java.util.List;
import java.util.UUID;

import net.countercraft.movecraft.craft.Craft;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.OfflinePlayer;
import org.bukkit.inventory.ItemStack;

import com.starquestminecraft.sqcontracts.SQContracts;
import com.starquestminecraft.sqcontracts.database.ContractPlayerData;
import com.starquestminecraft.sqcontracts.util.CompletionStatus;
import com.starquestminecraft.sqcontracts.util.InventoryUtil;

public class ItemContract implements Contract{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	List<ItemStack> items;
	int reward;
	String targetStation;
	UUID player;
	boolean smuggler;
	
	private static final String SMUGGLER_TAG = Contract.BLACK_MARKET_TAG + "[" + ChatColor.YELLOW + "Smuggling" + ChatColor.WHITE + "] ";
	private static final String MERCHANT_TAG = "[" + ChatColor.GREEN + "Merchanting" + ChatColor.WHITE + "] ";
	
	public ItemContract(UUID player, int reward, String targetStation, List<ItemStack> items, boolean smuggler){
		this.items = items;
		this.reward = reward;
		this.targetStation = targetStation;
		this.player = player;
		this.smuggler = smuggler;
	}

	@Override
	public CompletionStatus complete(Craft c) {
		//CHECK THIS, IT'S DODGY
		/*CompletionStatus retval = CompletionStatus.INCOMPLETE;
		CompletionStatus[] statuses = new CompletionStatus[items.length];
		for(int n = 0; n < items.length; n++){
			ItemStack i = items[n];
			int amountLeft = InventoryUtil.removeItemsFromShipInventories(i.getType(), i.getData().getData(), i.getAmount(), c);
			if(amountLeft == i.getAmount()){
				statuses[n] = CompletionStatus.INCOMPLETE;
			} else if (amountLeft > 0){
				statuses[n] = CompletionStatus.PARTIAL;
			} else if (amountLeft == 0){
				statuses[n] = CompletionStatus.COMPLETE;
			}
		}
		for(CompletionStatus s : statuses){
			if(s == CompletionStatus.COMPLETE){
				retval = CompletionStatus.COMPLETE;
			} else if(s == CompletionStatus.PARTIAL){
				retval = CompletionStatus.PARTIAL;
				return retval;
			}
		}
		return retval;*/
		
		CompletionStatus retval = null;
		for(int n = 0; n < items.size(); n++){
			ItemStack i = items.get(n);
			int amountLeft = InventoryUtil.removeItemsFromShipInventories(i.getType(), i.getData().getData(), i.getAmount(), c);
			if(amountLeft == 0){
				//this one is complete
				items.remove(n);
				n--;
				if(retval == CompletionStatus.INCOMPLETE){
					//one incomplete and one complete is a partial
					retval = CompletionStatus.PARTIAL;
				} else if(retval == null){
					retval = CompletionStatus.COMPLETE;
				}
			} else if(amountLeft == i.getAmount()){
				if(retval == null){
					retval = CompletionStatus.INCOMPLETE;
				}
			} else {
				//partial
				i.setAmount(amountLeft);
				if(retval != CompletionStatus.PARTIAL){
					retval = CompletionStatus.PARTIAL;
				}
			}
		}
		return retval;
	}

	@Override
	public String getTargetStation() {
		return targetStation;
	}

	@Override
	public String getDescription() {
		if(smuggler){
			return SMUGGLER_TAG + "Obtain the following items: " + printItems() +
					" and bring them to " + targetStation + ". Completing this contract " + 
					" earns you " + reward + " credits and one Smuggling " + 
					" point. While you have this contract open you will be on the Wanted list, and while"
					+ "you are Wanted you will be pursued by Privateers.";
		} else {
			return MERCHANT_TAG + "Obtain the following items: " + printItems() +
					" and bring them to " + targetStation + ". Completing this contract " + 
					" earns you " + reward + " credits and one Merchant " + 
					" point.";
		}
	}

	private String printItems() {
		String retval = "\n";
		for(ItemStack i : items){
			String itemLine = i.getAmount() + " of " + i.getType().toString().toLowerCase() + " with data value " + i.getDurability();
			retval = retval + itemLine + "\n";
		}
		return retval;
	}

	@Override
	public boolean isBlackMarket() {
		return smuggler;
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
			currency = "smuggling";
		} else {
			currency = "trading";
		}
		int balance = d.getBalanceInCurrency(currency);
		d.setBalanceInCurrency(currency, balance + 1);
	}
}
