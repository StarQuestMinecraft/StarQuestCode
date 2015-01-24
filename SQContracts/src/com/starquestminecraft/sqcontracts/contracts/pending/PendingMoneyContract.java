package com.starquestminecraft.sqcontracts.contracts.pending;

import java.util.Random;

import org.bukkit.configuration.file.FileConfiguration;

import com.starquestminecraft.sqcontracts.contracts.Contract;
import com.starquestminecraft.sqcontracts.database.ContractPlayerData;

public class PendingMoneyContract extends PendingContract{

	public PendingMoneyContract(FileConfiguration c, String key) {
		super(c, key);
		// TODO Auto-generated constructor stub
	}

	@Override
	public Contract giveToPlayer(ContractPlayerData d, Random generator) {
		// TODO Auto-generated method stub
		return null;
	}

}
