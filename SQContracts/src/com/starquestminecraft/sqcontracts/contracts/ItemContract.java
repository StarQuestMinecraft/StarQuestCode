package com.starquestminecraft.sqcontracts.contracts;

import java.util.List;
import java.util.UUID;

import net.countercraft.movecraft.craft.Craft;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;

import com.starquestminecraft.sqcontracts.SQContracts;
import com.starquestminecraft.sqcontracts.database.ContractPlayerData;
import com.starquestminecraft.sqcontracts.util.CompletionStatus;
import com.starquestminecraft.sqcontracts.util.DataUtils;
import com.starquestminecraft.sqcontracts.util.InventoryUtil;

public class ItemContract implements Contract {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	//List of all items needed in contract
	List<ItemHolder> items;
	int reward;
	String targetStation;
	UUID player;
	boolean smuggler;
	long openedMillis;

	private static final String SMUGGLER_TAG = Contract.BLACK_MARKET_TAG + "[" + ChatColor.GOLD + "Smuggling" + ChatColor.RESET + "] ";
	private static final String MERCHANT_TAG = "[" + ChatColor.GREEN + "Merchanting" + ChatColor.RESET + "] ";

	public ItemContract(UUID player, int reward, String targetStation, List<ItemHolder> items, boolean smuggler) {
		this.items = items;
		this.reward = reward;
		this.targetStation = targetStation;
		this.player = player;
		this.smuggler = smuggler;
		this.openedMillis = System.currentTimeMillis();
	}

	@Override
	public CompletionStatus complete(Craft c) {
		//Changed the nature of complete method to avoid bugs and redundancy. Initial retval = INCOMPLETE rather than null. -Evan H.
		CompletionStatus retval = CompletionStatus.INCOMPLETE;
		for (int n = 0; n < items.size(); n++) {
			ItemHolder i = items.get(n);
			int amountLeft = InventoryUtil.removeItemsFromShipInventories(i.getType(), i.getData(), i.getAmount(), c);
			if (amountLeft == 0) {
				//This part of contract (element n) is complete
				items.remove(n);
				n--;
				//Tests the list of parts of contract to determine completeness
				if(items.size() == 0) {
					//All parts of contract are complete
					retval = CompletionStatus.COMPLETE;
				}
				else {
					//Not all parts of contract are complete
					retval = CompletionStatus.PARTIAL;
				}
			}
			//Removed a redundant test ( if(amountLeft == i.getAmount) { ... } is unneeded when init retval = INCOMPLETE
			else if (amountLeft != i.getAmount()) {
				// If this element was partially completed
				i.setAmount(amountLeft);
				retval = CompletionStatus.PARTIAL;
			}
		}
		return retval;
	}

	@Override
	public String getTargetStation() {
		return targetStation;
	}

	@Override
	public String getDescription(ChatColor c) {
		if (smuggler) {
			return fixColor(c, SMUGGLER_TAG) + "Obtain the following items: " + printItems() + " and bring them to eco station " + targetStation + ". Completing this contract" + " earns you " + reward
					+ " credits and one Smuggling" + " point. While you have this contract open you will be on the Wanted list, and while" + " you are Wanted you will be pursued by Privateers.";
		} else {
			return fixColor(c, MERCHANT_TAG) + "Obtain the following items: " + printItems() + " and bring them to eco station " + targetStation + ". Completing this contract " + "earns you " + reward
					+ " credits and one Merchant " + "point.";
		}
	}

	private String fixColor(ChatColor color, String string) {
		return string.replaceAll(ChatColor.RESET.toString(), color.toString());
	}

	private String printItems() {
		String retval = "\n";
		for (ItemHolder i : items) {
			String itemName = DataUtils.formatItemName(i.getType(), i.getData());
				if(itemName == "acacia log_2"){
					itemName = "acacia log";
				}
				if(itemName == "dark oak log_2"){
					itemName = "dark oak log";
				}
				if(itemName == "coal with data value 1"){
					itemName = "charcoal";
				}
			String itemLine = formatAmount(i.getAmount(), i) + " of " + itemName;
			if(items.size() == 1) return itemLine;
			retval = retval + itemLine + "\n";
		}
		return retval;
	}

	private String formatAmount(int amount, ItemHolder item) {
		int fullstax = (int) Math.floor(amount / item.getMaxStackSize());
		if (fullstax == 0)
			return "" + amount;
		int leftovers = amount % item.getMaxStackSize();
		if (item.getMaxStackSize() != 1){
			return amount + " (" + fullstax + " stacks + " + leftovers + ")";
		} else{
			return Integer.toString(amount);
		}
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
		try {
			SQContracts.get().getEconomy().depositPlayer(plr, reward);
		} catch (Exception e) {
			System.out.println("ERROR: no economy found!");
		}
		String currency;
		if (isBlackMarket()) {
			currency = "smuggling";
		} else {
			currency = "trading";
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
