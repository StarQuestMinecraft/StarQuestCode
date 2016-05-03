package com.whirlwindgames.dibujaron.sqempire.database.object;

import com.whirlwindgames.dibujaron.sqempire.database.EmpireDB;
import com.whirlwindgames.dibujaron.sqempire.util.AsyncUtil;
import com.whirlwindgames.dibujaron.sqempire.util.RSReader;

public class EmpirePercentage {

	public static int[] getEmpirePopulationDistribution(){
		//gets the percentage of the server population in the given empire.
		AsyncUtil.crashIfNotAsync();
		int[] retvals = new int[3];
		for(int i = 0; i < retvals.length; i++){
			String query = "SELECT count(*) AS count from minecraft.empire_player WHERE empire=" + (i+1);
			RSReader rs = new RSReader(EmpireDB.requestData(query));
	
			if(rs.next()){
				retvals[i] = rs.getInt("count");
			}
		}
		return retvals;
	}
}
