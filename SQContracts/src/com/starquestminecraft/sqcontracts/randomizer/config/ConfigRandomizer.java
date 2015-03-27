package com.starquestminecraft.sqcontracts.randomizer.config;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Set;
import java.util.UUID;

import org.bukkit.configuration.file.FileConfiguration;

import com.starquestminecraft.sqcontracts.SQContracts;
import com.starquestminecraft.sqcontracts.contracts.Contract;
import com.starquestminecraft.sqcontracts.database.ContractPlayerData;
import com.starquestminecraft.sqcontracts.randomizer.Randomizer;

public class ConfigRandomizer extends Randomizer{

	private List<PendingContract> availableContracts;
	
	public ConfigRandomizer(){
		availableContracts = loadConfig();
	}
	
	private List<PendingContract> loadConfig() {
		List<PendingContract> retval = new ArrayList<PendingContract>();
		FileConfiguration c = SQContracts.get().getConfig();
		Set<String> keys = c.getConfigurationSection("contracts").getKeys(true);
		for (String s : keys) {
			String type = c.getString("contracts." + s + ".type");
			PendingContract contract;
			switch (type.toLowerCase()) {
			case "item":
				contract = new PendingItemContract(c, s);
				retval.add(contract);
				continue;
			case "capture":
				contract = new PendingShipCaptureContract(c, s);
				retval.add(contract);
				continue;
			case "money":
				contract = new PendingMoneyContract(c, s);
				retval.add(contract);
				continue;
			default:
				continue;
			}
		}
		return retval;
	}

	
	public Contract[] generateContractsForPlayer(UUID player){
		long seed = getRandomSeed(player);
		Random generator = new Random(seed);
		
		
		ContractPlayerData pData = SQContracts.get().getContractDatabase().getDataOfPlayer(player);
		List<PendingContract> contractsAvailableToPlayer = new ArrayList<PendingContract>();
		
		for(PendingContract c : availableContracts){
			if(c.isValidContractForPlayer(pData)){
				contractsAvailableToPlayer.add(c);
			}
		}
		
		Contract[] display = new Contract[pData.getNumNewContractOffers()];
		for(int i = 0; i < display.length; i++){
			int randIndex = generator.nextInt(contractsAvailableToPlayer.size());
			PendingContract pc = contractsAvailableToPlayer.get(randIndex);
			Contract c = pc.giveToPlayer(pData, generator);
			display[i] = c;
		}
		
		return display;
	}
}
