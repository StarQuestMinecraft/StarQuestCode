package com.starquestminecraft.sqcontracts.randomizer.config;

import java.util.Random;
import java.util.UUID;

import org.bukkit.configuration.file.FileConfiguration;

import com.starquestminecraft.sqcontracts.contracts.Contract;
import com.starquestminecraft.sqcontracts.contracts.MoneyContract;
import com.starquestminecraft.sqcontracts.database.ContractPlayerData;
import com.starquestminecraft.sqcontracts.util.StationUtils;

public class PendingMoneyContract extends PendingContract{

	String cause;
	int cost;
	public PendingMoneyContract(FileConfiguration c, String key) {
		super(c, key);
		this.cause = c.getString(key + ".cause");
		this.cost = c.getInt(key + ".cost");
	}

	@Override
	public Contract giveToPlayer(ContractPlayerData d, Random generator, String system) {
		UUID player = d.getPlayer();
		String targetStation = StationUtils.getRandomStation(generator, system);
		cost *= (1 / StationUtils.getModifierForStation(targetStation));
		return new MoneyContract(player, targetStation, cost, cause);
	}

}
