package com.starquestminecraft.sqcontracts.contracts.pending;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Set;
import java.util.UUID;

import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;

import com.starquestminecraft.sqcontracts.contracts.Contract;
import com.starquestminecraft.sqcontracts.contracts.ShipCaptureContract;
import com.starquestminecraft.sqcontracts.database.ContractPlayerData;
import com.starquestminecraft.sqcontracts.util.StationUtils;

public class PendingShipCaptureContract extends PendingContract{
	
	List<PendingItem> pendingItems = new ArrayList<PendingItem>();
	int amountMin, amountMax;
	boolean blackMarket;
	
	public PendingShipCaptureContract(FileConfiguration f, String key){
		super(f, key);
		this.blackMarket = f.getBoolean(key + ".blackMarket");
		amountMin = f.getInt(key + ".minAmount");
		amountMax = f.getInt(key + ".maxAmount");
		
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
		int amount = getRandomBetween(generator, amountMin, amountMax);
		return new ShipCaptureContract(player, reward, targetClass, amount, targetStation, blackMarket);
	}
}
