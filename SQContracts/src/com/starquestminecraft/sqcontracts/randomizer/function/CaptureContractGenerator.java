package com.starquestminecraft.sqcontracts.randomizer.function;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import com.starquestminecraft.sqcontracts.contracts.ShipCaptureContract;
import com.starquestminecraft.sqcontracts.database.ContractPlayerData;
import com.starquestminecraft.sqcontracts.util.StationUtils;

public class CaptureContractGenerator {
	
	
	public static ShipCaptureContract generate(ContractPlayerData pData, Random generator, boolean blackMarket){
		
		int level;
		if(blackMarket){
			level = pData.getBalanceInCurrency("infamy");
		} else {
			level = pData.getBalanceInCurrency("reputation");
		}
		
	//first get the number of ship classes to offer (1-4)
		int numClasses = (int) Math.round(FunctionRandomizer.randomModifierRange(3, generator, 2));
		System.out.println("numClasses: " + numClasses);
		
	//now get the number of ships to capture (1-15 ish)
		double numShipsBase = 0.14 * (level) + 1;
		System.out.println("numShipsBase: " + numShipsBase);
		double numShipsMod = FunctionRandomizer.randomModifierPercentage(numShipsBase, generator, 50);
		System.out.println("numShipsMod: " + numShipsBase);
		int numShipsFnl = (int) Math.round(numShipsMod);
		System.out.println("rounded: " + numShipsFnl);
		if(numShipsFnl < 1) numShipsFnl = 1;
		
	//for each class type, pick a class type based on their level
		ShipClassData[] data = new ShipClassData[numClasses];
		for(int i = 0; i < numClasses; i++){
			data[i] = FileDataHandler.pickRandomShipClass(pData, generator, blackMarket, data);
		}
		
	//get a price by averaging the kill values for each possible class
		int priceAll = 0;
		for(ShipClassData d : data){
			priceAll += d.getUnitPrice();
		}
		int priceAvg = priceAll / data.length;

		//randomize the price based on a range
		int priceFinal = numShipsFnl * (int) FunctionRandomizer.randomModifierPercentage(priceAvg, generator, 25);
		
	//finalize the craft types
		String[] types = new String[data.length];
		for(int i = 0; i < types.length; i++){
			types[i] = data[i].getType();
		}
		
	//done
		return new ShipCaptureContract(pData.getPlayer(), priceFinal, types, numShipsFnl, StationUtils.getRandomStation(generator), blackMarket);
		
	
	}
	
	
	
	private static HashMap<String, Integer> convert(Map<String, Object> map) {
        HashMap<String, Integer> ret = new HashMap<String, Integer>();
        for (String key : map.keySet()) {
            ret.put(key, (Integer) map.get(key));
        }
        return ret;
    }
}
