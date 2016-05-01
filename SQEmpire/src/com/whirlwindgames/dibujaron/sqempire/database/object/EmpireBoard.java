package com.whirlwindgames.dibujaron.sqempire.database.object;

import com.whirlwindgames.dibujaron.sqempire.Empire;
import com.whirlwindgames.dibujaron.sqempire.Planet;
import com.whirlwindgames.dibujaron.sqempire.Settings;
import com.whirlwindgames.dibujaron.sqempire.database.EmpireDB;
import com.whirlwindgames.dibujaron.sqempire.util.AsyncUtil;
import com.whirlwindgames.dibujaron.sqempire.util.RSReader;
import com.whirlwindgames.dibujaron.sqempire.util.SuperPS;

public class EmpireBoard {
	
	static String updateCommand = "INSERT INTO minecraft.empire_board(id,planet,cx,cz,empire)"
			+ "VALUES (?,?,?,?,?) ON DUPLICATE KEY UPDATE empire=?";

	public static Empire getOwnerOfClaim(Planet p, int cx, int cz){
		AsyncUtil.crashIfNotAsync();
		String query = "SELECT * from minecraft.empire_board WHERE id=" + generateKey(p,cx,cz);
		RSReader rs = new RSReader(EmpireDB.requestData(query));
		if(rs.next()){
			return Empire.fromID(rs.getInt("empire"));
		} else {
			return Empire.NONE;
		}
	}
	
	public static void setOwnerOfClaim(Planet p, int cx, int cz, Empire e){
		AsyncUtil.crashIfNotAsync();
		SuperPS ps = new SuperPS(EmpireDB.prepareStatement(updateCommand),6);
		ps.setInt(0, generateKey(p,cx,cz));
		ps.setInt(1, p.getID());
		ps.setInt(2, cx);
		ps.setInt(3, cz);
		ps.setInt(4, e.getID());
		ps.setInt(5, e.getID());
		ps.executeAndClose();
	}
	
	public static Empire calculateOwnerOfPlanet(Planet p){
		AsyncUtil.crashIfNotAsync();
		String query = "SELECT COUNT(*) as Count FROM minecraft.empire_board WHERE planet=" + p.getID() + " AND empire=";
		int largestCount = 0;
		int largestEmpire = 0;
		int totalClaimed = 0;
		for(int e = 1; e < 4; e++){
			RSReader rs = new RSReader(EmpireDB.requestData(query + e));
			int eCount;
			if(rs.next()){
				eCount = rs.getInt("Count");
			} else {
				eCount = 0;
			}
			totalClaimed += eCount;
			if( eCount > largestCount){
				largestCount = e;
				largestEmpire = e;
			}
		}
		if(Settings.DISCOUNT_UNCLAIMED_CHUNKS){
			if(largestCount >= Settings.PLANET_CONTROL_MODIFIER * totalClaimed){
				return Empire.fromID(largestEmpire);
			} else {
				return Empire.NONE;
			}
		} else {
			int claimWidth = Settings.CLAIM_WIDTH;
			int claimRadius = (int) Math.ceil(3000 / claimWidth);
			int area = (int) Math.ceil(claimRadius * claimRadius * Math.PI);
			if(largestCount >= Settings.PLANET_CONTROL_MODIFIER * area){
				return Empire.fromID(largestEmpire);
			} else {
				return Empire.NONE;
			}
		}
		
	}
	public static int getNumClaimsOnPlanet(Empire e, Planet p){
		AsyncUtil.crashIfNotAsync();
		String query = "SELECT COUNT(*) as Count FROM minecraft.empire_board WHERE planet=" + p.getID() + " AND empire=" + e.getID();
		RSReader rs = new RSReader(EmpireDB.requestData(query + e));
		int eCount;
		if(rs.next()){
			eCount = rs.getInt("Count");
		} else {
			eCount = 0;
		}
		return eCount;
	}
	
	private static int generateKey(Planet p, int cx, int cz){
		return (p.getID()*193 + cx)*193 + cz;
	}
	
	public static int getNumPossibleClaimsOnPlanet(){
		int claimWidth = Settings.CLAIM_WIDTH;
		int claimRadius = (int) Math.ceil(3000 / claimWidth);
		int area = (int) Math.ceil(claimRadius * claimRadius * Math.PI);
		return area;
	}
}
