package com.starquestminecraft.sqcontracts.database;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;

import com.starquestminecraft.sqcontracts.contracts.Contract;
import com.starquestminecraft.sqcontracts.contracts.ShipCaptureContract;
import com.starquestminecraft.sqcontracts.util.WantedUtils;

public class ContractPlayerData implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	UUID player;
	List<Contract> contracts;
	HashMap<String, Integer> balances;
	
	private static Set<String> currencies = new HashSet<String>();
	static{
		currencies.add("philanthropy");
		currencies.add("smuggling");
		currencies.add("trading");
		currencies.add("reputation");
		currencies.add("infamy");
	}
	
	public ContractPlayerData(UUID u, List<Contract> currentContracts, HashMap<String, Integer> tradeAmounts){
		player = u;
		contracts = currentContracts;
		balances = tradeAmounts;
	}
	
	public static ContractPlayerData createDefault(UUID u){
		HashMap<String, Integer> tradeAmounts = new HashMap<String, Integer>();
		List<Contract> currentContracts = new ArrayList<Contract>();
		return new ContractPlayerData(u, currentContracts, tradeAmounts);
	}
	
	public List<Contract> getContracts(){
		return contracts;
	}
	
	public UUID getPlayer(){
		return player;
	}
	
	public int getBalanceInCurrency(String currency){
		Integer i = balances.get(currency);
		if(i == null) return 0;
		return i;
	}
	
	public static Set<String> getCurrencies(){
		return currencies;
	}
	
	public void setBalanceInCurrency(String currency, int amount){
		balances.remove(currency);
		balances.put(currency, amount);
	}
	
	public void addBalanceInCurrency(String currency, int add){
		int amount = balances.get(currency);
		balances.remove(currency);
		balances.put(currency, amount + add);
	}
	
	public int getNumNewContractOffers(){
		return 3;
	}
	
	public boolean isWanted(){
		for(Contract c : contracts){
			if(c.isBlackMarket()) return true;
		}
		if(WantedUtils.delayedWantedPlayers.keySet().contains(player)){
			return true;
		}
		return false;
	}
	
	public boolean isPrivateer(){
		for(Contract c : contracts){
			if(c instanceof ShipCaptureContract && !c.isBlackMarket()) return true;
		}
		return false;
	}
	
	
}
