package com.starquestminecraft.sqcontracts.randomizer.function;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import com.starquestminecraft.sqcontracts.SQContracts;
import com.starquestminecraft.sqcontracts.contracts.ItemContract;
import com.starquestminecraft.sqcontracts.contracts.ItemHolder;
import com.starquestminecraft.sqcontracts.contracts.ShipCaptureContract;
import com.starquestminecraft.sqcontracts.database.ContractPlayerData;
import com.starquestminecraft.sqcontracts.util.StationUtils;

public class ItemContractGenerator {
	public static ItemContract generate(ContractPlayerData pData, Random generator, boolean blackMarket, String system){
		
	//get their relevant level
		int level;
		if(blackMarket){
			level = pData.getBalanceInCurrency("smuggling");
		} else {
			level = pData.getBalanceInCurrency("trading");
		}
	//now get the number of item types to offer (ln(x) + 1) += randomizer >= 1
		double baseNum = Math.log(level) / 5 + 1;
		double modified = FunctionRandomizer.randomModifierRange(baseNum, generator, 2);
		int numFnl = (int) Math.round(modified);
		if(numFnl < 1) numFnl = 1;
		
	//pick a random price based on their level, some sort of log scale, += randomizer
		
		//with this equation you start at 10,000 and end up with 50000 at level 100
		double priceBase = 85 * level + 1500;

		//randomize the price first by a percentage and then by a range
		//this ensures a good spread at both low and high levels
		//min 9000, max 76000
		double priceTarget = FunctionRandomizer.randomModifierRange(FunctionRandomizer.randomModifierPercentage(priceBase, generator, 50), generator, 1000);
		if(priceTarget < 0) priceTarget = 1;
	//for each item type, pick an item randomly based on their level
		ItemData[] data = new ItemData[numFnl];
		for(int i = 0; i < numFnl; i++){
			data[i] = FileDataHandler.pickRandomItem(pData, generator, blackMarket, data);
		}
		
	//randomly determine a price to be met by each item based on the total price
		int[] prices;
		if(numFnl > 1){ 
			prices = FunctionRandomizer.randSum(numFnl, priceTarget, generator);
		} else{
			int targ = (int) priceTarget;
			prices = new int[1];
			prices[0] = targ;
		}
		
	//given the price for each, figure out about how many items that is (rounding)
		int[] numItemsEach = new int[numFnl];
		for(int i = 0; i < numFnl; i++){
			int priceCoveredByItem = prices[i];
			double itemsdbl = priceCoveredByItem / data[i].getUnitPrice();
			int itemsFnl = (int) Math.round(itemsdbl);
			if(itemsFnl < 1) itemsFnl = 1;
			numItemsEach[i] = itemsFnl;
		}
		
	//determine the total price from each price added together, with a randomizer
		int priceFnl = 0;
		for(int i = 0; i < numFnl; i++){
			priceFnl += numItemsEach[i] * data[i].getUnitPrice() * 1.1;
			priceFnl = (int) FunctionRandomizer.randomModifierRange(FunctionRandomizer.randomModifierPercentage(priceFnl, generator, 10), generator, 1000);
		}
		
	//create itemstacks from the item id and amount (special case for wood data?)
		List<ItemHolder> items = new ArrayList<ItemHolder>(numFnl);
		for(int i = 0; i < numFnl; i++){
			items.add(new ItemHolder(data[i].getType(), numItemsEach[i], data[i].getData()));
		}
		
	//pick a target station
		String targetStation = StationUtils.getRandomStation(generator, system);
		priceFnl *= StationUtils.getModifierForStation(targetStation);
		if(blackMarket){
			priceFnl *= 1.5;
		}
		if(priceFnl < 0) priceFnl *= -1;
	//done
		ItemContract c = new ItemContract(pData.getPlayer(), priceFnl, targetStation, items, blackMarket);
		return c;
		
	}
	
	public static String arrayPrint(int[] arr){
		String retval = "[" + arr[0];
		for(int i = 1; i < arr.length; i++){
			retval = retval + ", " + arr[i];
		}
		retval = retval + "]";
		return retval;
	}
	
}
