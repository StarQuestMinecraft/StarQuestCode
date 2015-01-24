package com.starquestminecraft.sqcontracts.util;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Random;
import java.util.UUID;

import com.starquestminecraft.sqcontracts.SQContracts;
import com.starquestminecraft.sqcontracts.contracts.Contract;
import com.starquestminecraft.sqcontracts.contracts.pending.PendingContract;
import com.starquestminecraft.sqcontracts.database.ContractPlayerData;

public class ContractRandomizer {

	private static long BASE_SEED;

	public static void captureBaseSeed() {
		Calendar calendar = Calendar.getInstance();
		int year = calendar.get(Calendar.YEAR);
		int month = calendar.get(Calendar.MONTH);
		int dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH);
		int dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK);
		int weekOfYear = calendar.get(Calendar.WEEK_OF_YEAR);
		int weekOfMonth = calendar.get(Calendar.WEEK_OF_MONTH);
		
		BASE_SEED = new Random(year + month + dayOfMonth + dayOfWeek + weekOfYear + weekOfMonth).nextLong();
		System.out.println("Base Seed: " + BASE_SEED);
	}

	public static long getRandomSeed(UUID player){
		return BASE_SEED + (player.getLeastSignificantBits() - player.getMostSignificantBits());
	}
	
	public static Contract[] generateContractsForPlayer(UUID player){
		long seed = getRandomSeed(player);
		Random generator = new Random(seed);
		
		List<PendingContract> availableContracts = SQContracts.get().getAvailableContracts();
		
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
