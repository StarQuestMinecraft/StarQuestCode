package com.starquestminecraft.sqcontracts.randomizer.config;

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
	
	int amountMin, amountMax;
	boolean blackMarket;
	List<String> targetClasses;
	
	public PendingShipCaptureContract(FileConfiguration f, String key){
		super(f, key);
		this.blackMarket = f.getBoolean(key + ".blackMarket");
		amountMin = f.getInt(key + ".minAmount");
		amountMax = f.getInt(key + ".maxAmount");
		targetClasses = f.getStringList(key + ".targetClasses");
	}

	@Override
	public Contract giveToPlayer(ContractPlayerData d, Random generator, String system) {
		UUID player = d.getPlayer();
		int reward = getRandomBetween(generator, minReward, maxReward);
		String targetStation = StationUtils.getRandomStation(generator, system);
		reward *= StationUtils.getModifierForStation(targetStation);
		int amount = getRandomBetween(generator, amountMin, amountMax);
		return new ShipCaptureContract(player, reward, targetClasses.toArray(new String[0]), amount, targetStation, blackMarket);
	}
}
