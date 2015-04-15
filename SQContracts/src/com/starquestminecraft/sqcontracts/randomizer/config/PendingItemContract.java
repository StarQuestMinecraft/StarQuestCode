package com.starquestminecraft.sqcontracts.randomizer.config;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Set;
import java.util.UUID;

import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.inventory.ItemStack;

import com.starquestminecraft.sqcontracts.contracts.Contract;
import com.starquestminecraft.sqcontracts.contracts.ItemContract;
import com.starquestminecraft.sqcontracts.database.ContractPlayerData;
import com.starquestminecraft.sqcontracts.util.StationUtils;

public class PendingItemContract extends PendingContract{
	List<PendingItem> pendingItems = new ArrayList<PendingItem>();
	
	boolean blackMarket;
	
	public PendingItemContract(FileConfiguration c, String key){
		super(c, key);
		blackMarket = c.getBoolean(key + ".blackMarket");
		Set<String> itemKeys = c.getConfigurationSection(key + ".items").getKeys(true);
		for(String iKey : itemKeys){
			String localKey = key + ".items." + iKey;
			Material type = Material.valueOf(c.getString(localKey + ".type"));
			byte data = (byte) c.getInt(localKey + ".data");
			int amountMin = c.getInt(localKey + ".minAmount");
			int amountMax = c.getInt(localKey + ".maxAmount");
			
			pendingItems.add(new PendingItem(type, data, amountMin, amountMax));
		}
	}
	

	@Override
	public Contract giveToPlayer(ContractPlayerData d, Random generator) {
		UUID player = d.getPlayer();
		int reward = getRandomBetween(generator, minReward, maxReward);
		String targetStation = StationUtils.getRandomStation(generator);
		ArrayList<ItemStack> stax = new ArrayList<ItemStack>(pendingItems.size());
		for(PendingItem i : pendingItems){
			stax.add(i.toItemStack(generator));
		}
		return new ItemContract(player, reward, targetStation, stax, blackMarket);
	}
}


class PendingItem{
	
	Material m;
	byte d;
	int min;
	int max;
	
	public PendingItem(Material type, byte data, int minAmount, int maxAmount){
		m = type;
		d = data;
		min = minAmount;
		max = maxAmount;
	}
	
	public ItemStack toItemStack(Random generator){
		int dist = max - min;
		int amount = generator.nextInt(dist) + min;
		return new ItemStack(m, amount, d);
	}
}
