package com.starquestminecraft.sqcontracts.contracts.pending;

import java.util.Random;
import java.util.UUID;

import org.bukkit.configuration.file.FileConfiguration;

import com.starquestminecraft.sqcontracts.contracts.Contract;
import com.starquestminecraft.sqcontracts.contracts.ShipCaptureContract;
import com.starquestminecraft.sqcontracts.database.ContractPlayerData;
import com.starquestminecraft.sqcontracts.util.StationUtils;

public class PendingShipCaptureContract extends PendingContract{
	
	String targetClass;
	int amountMin, amountMax;
	boolean blackMarket;
	
	public PendingShipCaptureContract(FileConfiguration f, String key){
		super(f, key);
		this.blackMarket = f.getBoolean(key + ".blackMarket");
		targetClass = f.getString(key + ".targetClass");
		amountMin = f.getInt(key + ".minAmount");
		amountMax = f.getInt(key + ".maxAmount");
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
