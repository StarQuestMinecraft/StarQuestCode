package com.whirlwindgames.dibujaron.sqempire.database.object;

import java.util.List;

import com.whirlwindgames.dibujaron.sqempire.util.AsyncUtil;

public class EmpirePercentage {

	public static int[] getEmpirePopulationDistribution(){
		//gets the percentage of the server population in the given empire.
		AsyncUtil.crashIfNotAsync();
		int[] retvals = new int[3];
	
		List<EmpirePlayer> players = EmpirePlayer.getPlayersOnlineRecently(1209600000L);
		
		for (EmpirePlayer player : players) {
			
			if (player.getEmpire().getID() != 0) {
				
				retvals[player.getEmpire().getID() - 1] ++;
				
			}
	
		}
		
		return retvals;
	}
}
