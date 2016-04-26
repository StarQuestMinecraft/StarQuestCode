package com.starquestminecraft.sqcontracts.randomizer.function;

import java.util.Random;
import java.util.UUID;

import com.starquestminecraft.sqcontracts.SQContracts;
import com.starquestminecraft.sqcontracts.contracts.Contract;
import com.starquestminecraft.sqcontracts.database.ContractPlayerData;
import com.starquestminecraft.sqcontracts.randomizer.Randomizer;

public class FunctionRandomizer extends Randomizer{

	public FunctionRandomizer(){
		super();
		FileDataHandler.loadFromFile();
	}
	@Override
	public Contract[] generateContractsForPlayer(UUID player, String type) {
		
		ContractPlayerData pData = SQContracts.get().getContractDatabase().getDataOfPlayer(player);
		Contract[] retval = new Contract[pData.getNumNewContractOffers()];
		
		long seed = getRandomSeed(pData);
		Random generator = new Random(seed);
		
		//calculate the "weight" for each type of contract for selection
		//weights are ln(x +1) where x is the balance in that contract
		
		/*System.out.println("Creating weighable");
		List<Weighable<String>> weights = new ArrayList<Weighable<String>>(pData.getCurrencies().size());
		for(String s : pData.getCurrencies()){
			int balance = pData.getBalanceInCurrency(s);
			double weight = Math.log(balance + 1);
			System.out.println("Weight for " + s + ": " + weight);
			weights.add(new Weighable<String>(s, weight));
		}
		
		System.out.println("Generating contracts with weights.");
		for(int i = 0; i < retval.length; i++){
			retval[i] = generate(generator, pData, weights);
		}*/
		//String[] currencies = pData.getCurrencies().toArray(new String[0]);
			//String cType = currencies[generator.nextInt(currencies.length)];
		retval[0] = generate(generator, pData, /*cType*/ type, "alpha");
		retval[1] = generate(generator, pData, /*cType*/ type, "beta");
		retval[2] = generate(generator, pData, /*cType*/ type, "gamma");
		return retval;
	}

	private Contract generate(Random generator, ContractPlayerData pData, String type, String system) {

		//select one

		
		switch(type){
		case "philanthropy":
			return generateMoneyContract(generator, pData, system);
		case "smuggling":
			return generateItemContract(generator, pData, true, system);
		case "trading":
			return generateItemContract(generator, pData, false, system);
		case "infamy":
			return generateShipCaptureContract(generator, pData, true, system);
		case "reputation":
			return generateShipCaptureContract(generator, pData, false, system);
		}
		return null;
	}
	
	public static double randomModifierPercentage(double base, Random generator, double percentage){
		//the percentage times the base gives the range
		double range = (percentage / 100) * base;
		return randomModifierRange(base, generator, range);
	}
	
	public static double randomModifierRange(double base, Random generator, double range){
		
		double modifier = (generator.nextDouble() * (range * 2)) - range;
		return base + modifier;
	}

	private Contract generateShipCaptureContract(Random generator, ContractPlayerData pData, boolean blackMarket, String system) {
		return CaptureContractGenerator.generate(pData, generator, blackMarket, system);
	}

	private Contract generateItemContract(Random generator, ContractPlayerData pData, boolean blackMarket, String system) {
		return ItemContractGenerator.generate(pData,generator, blackMarket, system);
	}

	private Contract generateMoneyContract(Random generator, ContractPlayerData pData, String system) {
		return MoneyContractGenerator.generate(pData, generator, system);
	}
	
	public static int[] randSum(int n, double m, Random rand) {
	    double randNums[] = new double[n], sum = 0;

	    for (int i = 0; i < randNums.length; i++) {
	        randNums[i] = rand.nextDouble();
	        sum += randNums[i];
	    }

	    for (int i = 0; i < randNums.length; i++) {
	        randNums[i] = randNums[i] / sum * m;
	    }
	    
	    /*for (int i = 0; i < randNums.length; i++) {
	        randNums[i] *= m;
	    }*/
	    
	    int[] retval = new int[n];
	    for(int i = 0; i < n; i++){
	    	retval[i] = (int) Math.round(randNums[i]);
	    }
	    return retval;
	}

}
