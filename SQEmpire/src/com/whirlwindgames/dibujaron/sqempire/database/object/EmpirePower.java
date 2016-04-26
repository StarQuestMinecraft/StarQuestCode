package com.whirlwindgames.dibujaron.sqempire.database.object;

import com.whirlwindgames.dibujaron.sqempire.Empire;
import com.whirlwindgames.dibujaron.sqempire.Planet;
import com.whirlwindgames.dibujaron.sqempire.Settings;
import com.whirlwindgames.dibujaron.sqempire.database.EmpireDB;
import com.whirlwindgames.dibujaron.sqempire.util.AsyncUtil;
import com.whirlwindgames.dibujaron.sqempire.util.RSReader;
import com.whirlwindgames.dibujaron.sqempire.util.SuperPS;

public class EmpirePower {

	public static int getPowerOfEmpireOnPlanet(Planet p, Empire e){
		AsyncUtil.crashIfNotAsync();
		String query = "SELECT sum(power_" + p.getID() + ") as planetSum FROM minecraft.empire_player WHERE empire=" + e.getID();
		RSReader rs = new RSReader(EmpireDB.requestData(query));
		if(rs.next()){
			return rs.getInt("planetSum");
		} else {
			return 0;
		}
	}
	
	public static boolean getCanClaimChunk(Planet p, Empire attacking, Empire defending){
		AsyncUtil.crashIfNotAsync();
		int numClaimsAttacker = EmpireBoard.getNumClaimsOnPlanet(attacking, p);
		int numClaimsDefender = EmpireBoard.getNumClaimsOnPlanet(defending, p);
		int powerAttacker = getPowerOfEmpireOnPlanet(p, attacking);
		int powerDefender = getPowerOfEmpireOnPlanet(p, defending);
		int power3rd = getPowerOfEmpireOnPlanet(p, Empire.getThirdEmpire(attacking, defending));
		int totClaimSpaces = EmpireBoard.getNumPossibleClaimsOnPlanet();
		
		int totPowerPool = powerAttacker + powerDefender + power3rd;
		int cMaxAttacking = powerAttacker / totPowerPool * totClaimSpaces;
		int cMaxDefending = powerDefender / totPowerPool * totClaimSpaces;
		
		return cMaxAttacking > numClaimsAttacker && cMaxDefending < numClaimsDefender;
	}
	

	
	private int generateKey(Planet p, int cx, int cz){
		return (p.getID()*191 + cx)*191 + cz;
	}
}
