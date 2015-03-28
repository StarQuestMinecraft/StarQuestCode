package com.starquestminecraft.sqcontracts.randomizer.function;

import java.util.Random;

import com.starquestminecraft.sqcontracts.SQContracts;
import com.starquestminecraft.sqcontracts.contracts.ItemContract;
import com.starquestminecraft.sqcontracts.contracts.ShipCaptureContract;
import com.starquestminecraft.sqcontracts.database.ContractPlayerData;

public class ItemContractGenerator {
	public static ItemContract generate(ContractPlayerData pData, Random generator, boolean blackMarket){
		
		//first get the number of item types to offer (ln(x) + 1) += randomizer >= 1
		//pick a random price based on their level, some sort of log scale, += randomizer
		//for each item type, pick an item randomly based on their level
		//randomly determine a price to be met by each item based on the total price
		//given the price for each, figure out about how many items that is (rounding)
		//determine the total price from each price added together
		//create itemstacks from the item id and amount (special case for wood data?)
		//pick a target station
		//done
		
		return null;
	}
}
