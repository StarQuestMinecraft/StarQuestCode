package com.starquestminecraft.sqcontracts.randomizer;

import java.util.Calendar;
import java.util.Random;
import java.util.UUID;

import com.starquestminecraft.sqcontracts.contracts.Contract;
import com.starquestminecraft.sqcontracts.database.ContractPlayerData;

public abstract class Randomizer {
	
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

	public static long getRandomSeed(ContractPlayerData pData){
		UUID player = pData.getPlayer();
		int modif = pData.getContracts().size() + 1;
		return BASE_SEED + (player.getLeastSignificantBits() - player.getMostSignificantBits()) / modif;
	}
	
	public abstract Contract[] generateContractsForPlayer(UUID player);
}
