package com.starquestminecraft.sqcontracts.randomizer.function;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;
import java.util.UUID;

import com.starquestminecraft.sqcontracts.SQContracts;
import com.starquestminecraft.sqcontracts.contracts.Contract;
import com.starquestminecraft.sqcontracts.database.ContractPlayerData;
import com.starquestminecraft.sqcontracts.randomizer.Randomizer;

public class FunctionRandomizer extends Randomizer{

	@Override
	public Contract[] generateContractsForPlayer(UUID player) {
		
		long seed = getRandomSeed(player);
		Random generator = new Random(seed);
		
		ContractPlayerData pData = SQContracts.get().getContractDatabase().getDataOfPlayer(player);
		Contract[] retval = new Contract[pData.getNumNewContractOffers()];
		
		//calculate the "weight" for each type of contract for selection
		//weights are ln(x +1) where x is the balance in that contract
		
		List<Weighable> weights = new ArrayList<Weighable>(pData.getCurrencies().size());
		for(String s : pData.getCurrencies()){
			int balance = pData.getBalanceInCurrency(s);
			double weight = Math.log(balance + 1);
			weights.add(new Weighable(s, weight));
		}
		
		for(int i = 0; i < retval.length; i++){
			retval[i] = generate(generator, pData, weights);
		}
		return null;
	}

	private Contract generate(Random generator, ContractPlayerData pData, List<Weighable> weights) {

		//select one
		Weighable winner = Weighable.weightedRandom(weights, generator);
		
		switch(winner.getName()){
		case "philanthropy":
			return generateMoneyContract(generator, pData);
		case "smuggling":
			return generateItemContract(generator, pData, true);
		case "trading":
			return generateItemContract(generator, pData, false);
		case "infamy":
			return generateShipCaptureContract(generator, pData, true);
		case "reputation":
			return generateShipCaptureContract(generator, pData, false);
		}
		return null;
	}
	
	public static double randomModifier(double base, Random generator, int percentage){
		//the percentage times the base gives the range
		double range = (percentage / 100) * base;
		
		//gives a random number between base+range and base-range
		double modifier = (generator.nextDouble() * (range * 2)) - range;
		
		return base + modifier;
	}

	private Contract generateShipCaptureContract(Random generator, ContractPlayerData pData, boolean blackMarket) {
		
		return null;
	}

	private Contract generateItemContract(Random generator, ContractPlayerData pData, boolean blackMarket) {
		// TODO Auto-generated method stub
		return null;
	}

	private Contract generateMoneyContract(Random generator, ContractPlayerData pData) {
		// TODO Auto-generated method stub
		return null;
	}

}
