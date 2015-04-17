package com.starquestminecraft.sqcontracts.database;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Set;
import java.util.UUID;

import com.starquestminecraft.sqcontracts.contracts.Contract;

public class ContractPlayerData implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	UUID player;
	List<Contract> contracts;
	HashMap<String, Integer> balances;
	
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
		System.out.println("Getting balance in currency: " + currency);
		Integer i = balances.get(currency);
		if(i == null) return 0;
		return i;
	}
	
	public Set<String> getCurrencies(){
		return balances.keySet();
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
		return 5;
	}
	
	public boolean isWanted(){
		for(Contract c : contracts){
			if(c.isBlackMarket()) return true;
		}
		return false;
	}
}
