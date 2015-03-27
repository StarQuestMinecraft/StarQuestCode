package com.starquestminecraft.sqcontracts.randomizer.function;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import com.starquestminecraft.sqcontracts.SQContracts;
import com.starquestminecraft.sqcontracts.contracts.ShipCaptureContract;
import com.starquestminecraft.sqcontracts.database.ContractPlayerData;

public class CaptureContractGenerator {
	
	static HashMap<String, Integer> classMinLevels;
	
	public static ShipCaptureContract generate(ContractPlayerData pData, Random generator, boolean blackMarket){
		if(classMinLevels == null) classMinLevels = convert(SQContracts.get().getConfig().getConfigurationSection("craftTypeMinLevels").getValues(false));
		
		//first get the number of ship classes to offer (1-4)
		//for each one, pick a ship class based on the classes they have access to; eliminate any repeats
		//then generate the number of ships as a function of their level (ln(x + 1) * 3) += randomizer
		//then get the reward by getting the average of the offered classes * num += a randomizer
		//done
	}
	
	
	
	private static HashMap<String, Integer> convert(Map<String, Object> map) {
        HashMap<String, Integer> ret = new HashMap<String, Integer>();
        for (String key : map.keySet()) {
            ret.put(key, (Integer) map.get(key));
        }
        return ret;
    }
}
