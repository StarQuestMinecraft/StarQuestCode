package com.starquestminecraft.sqcontracts;

import com.starquestminecraft.sqcontracts.database.ContractPlayerData;

public class ContractConverter {
	public static void convert(){
		ContractPlayerData[] all = SQContracts.get().getContractDatabase().getAllPlayerData();
		if(all == null){
			System.out.println("No data found.");
			return;
		}
		int i = 0;
		for(ContractPlayerData d : all){
			i++;
			if(i % 100 == 0){
				System.out.println("Updated 100 entries, total " + i);
			}
			d.getContracts().clear();
			SQContracts.get().getContractDatabase().updatePlayerData(d.getPlayer(), d);
		}
		System.out.println("Updated " + i + " values.");
	}
}
