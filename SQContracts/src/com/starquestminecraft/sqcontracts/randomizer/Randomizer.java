package com.starquestminecraft.sqcontracts.randomizer;

import java.util.Calendar;
import java.util.Random;
import java.util.UUID;

import com.starquestminecraft.sqcontracts.SQContracts;
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
		int ampm = calendar.get(Calendar.AM_PM);
		
		BASE_SEED = new Random(year + month + dayOfMonth + dayOfWeek + weekOfYear + weekOfMonth - ampm).nextLong();
	}

	public static long getRandomSeed2(ContractPlayerData pData){
		UUID player = pData.getPlayer();
		int modifCurrency = 0;
		for(String s : ContractPlayerData.getCurrencies()){
			modifCurrency += pData.getBalanceInCurrency(s);
		}
		int modifNum = pData.getContracts().size();
		double mostSig = player.getMostSignificantBits();
		double leastSig = player.getLeastSignificantBits();
		
		if(modifCurrency == 0) modifCurrency = -1;
		if(modifNum == 0) modifNum = -2;
		if(leastSig == 0) leastSig = -3;
		if(mostSig == 0) mostSig = -4;
		long seed = (long) (BASE_SEED * leastSig * mostSig * modifCurrency * modifNum);
		return seed;
	}
	
	public static long getRandomSeed(ContractPlayerData pData){
		Random internalRandom = new Random(BASE_SEED);
		UUID player = pData.getPlayer();
		int modifCurrency = internalRandom.nextInt();
		for(String s : ContractPlayerData.getCurrencies()){
			modifCurrency += pData.getBalanceInCurrency(s);
		}
		int mostSig = internalRandom.nextInt() + (int) player.getMostSignificantBits();
		int leastSig = internalRandom.nextInt() + (int) player.getLeastSignificantBits();
		int modifNum = internalRandom.nextInt() + pData.getContracts().size();
		return modifCurrency + mostSig + leastSig + modifNum;
	}
	
	public abstract Contract[] generateContractsForPlayer(UUID player, String type);
}
