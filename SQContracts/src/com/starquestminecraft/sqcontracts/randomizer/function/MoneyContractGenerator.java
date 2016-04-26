package com.starquestminecraft.sqcontracts.randomizer.function;

import java.util.List;
import java.util.Random;

import com.starquestminecraft.sqcontracts.SQContracts;
import com.starquestminecraft.sqcontracts.contracts.MoneyContract;
import com.starquestminecraft.sqcontracts.database.ContractPlayerData;
import com.starquestminecraft.sqcontracts.util.StationUtils;

public class MoneyContractGenerator {
	
	static List<String> causes;
	static List<String> locations;
	public static MoneyContract generate(ContractPlayerData pData, Random generator, String system){
		
		//generate money amount based on their level (x+21) ^ 3  + 10000
		
		int balance = pData.getBalanceInCurrency("philanthropy");
		double baseCost = runEquation(balance);
		double cost = FunctionRandomizer.randomModifierPercentage(baseCost, generator, 10);
		
		String cause = getCause(generator);
		String location = getLocation(generator);
		String causemsg = cause + " on " + location;
		
		String station = StationUtils.getRandomStation(generator, system);
		cost *= (1 / StationUtils.getModifierForStation(station));
		return new MoneyContract(pData.getPlayer(), station, (int) cost, causemsg);
		//generate a "cause" based on "afflictions" and planet names, e.g. "starving children" on "krystallos" or "earthquake relief" on "quavara"
		//done 
	}
	
	private static String getCause(Random generator){
		if(causes == null) causes = SQContracts.get().getConfig().getStringList("causes");
		return causes.get((int) generator.nextInt(causes.size()));
	}
	private static String getLocation(Random generator){
		if(locations == null) locations = SQContracts.get().getConfig().getStringList("planets");
		return locations.get((int) generator.nextInt(locations.size()));
	}
	
	private static double runEquation(int x){
		return Math.pow(x+21, 3) + 10000;
	}
}
