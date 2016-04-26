package com.starquestminecraft.sqcontracts.randomizer.config;

import java.util.HashMap;
import java.util.Random;
import java.util.Set;

import org.bukkit.configuration.file.FileConfiguration;

import com.starquestminecraft.sqcontracts.contracts.Contract;
import com.starquestminecraft.sqcontracts.database.ContractPlayerData;

public abstract class PendingContract {
	
	HashMap<String, Integer> minBalances = new HashMap<String, Integer>();
	int minReward, maxReward;
	
	public PendingContract(FileConfiguration c, String key){
		System.out.println("Low level key: " + key + ".minLevels");
		Set<String> minBalances = c.getConfigurationSection(key + ".minLevels").getKeys(false);
		for(String currency : minBalances){
			String localKey = key + ".minLevels." + currency;
			int amount = c.getInt(localKey);
			this.minBalances.put(currency, amount);
		}
		minReward = c.getInt(key + ".minReward");
		maxReward = c.getInt(key + ".maxReward");
	}
	
	public abstract Contract giveToPlayer(ContractPlayerData d, Random generator, String system);
	
	public boolean isValidContractForPlayer(ContractPlayerData d) {
		for(String s : minBalances.keySet()){
			System.out.println("Testing for balance: " + s);
			int requiredLevel = minBalances.get(s);
			System.out.println("Required level: " + requiredLevel);
			int playerLevel = d.getBalanceInCurrency(s);
			System.out.println("Found level: " + playerLevel);
			if(playerLevel < requiredLevel) return false;
		}
		return true;
	}
	
	public int getRandomBetween(Random r, int min, int max){
		int diff = max - min;
		int rand = r.nextInt(diff);
		return rand + min;
	}
}
