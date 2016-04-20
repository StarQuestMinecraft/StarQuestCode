package com.whirlwindgames.dibujaron.sqempire.database.object;

import com.massivecraft.factions.entity.Faction;
import com.whirlwindgames.dibujaron.sqempire.Empire;
import com.whirlwindgames.dibujaron.sqempire.database.EmpireDB;
import com.whirlwindgames.dibujaron.sqempire.util.AsyncUtil;
import com.whirlwindgames.dibujaron.sqempire.util.RSReader;
import com.whirlwindgames.dibujaron.sqempire.util.SuperPS;

public class EmpireFaction {
	static String updateCommand = "INSERT INTO minecraft.empire_faction(factionID,empire) "
			+ "VALUES (?,?) ON DUPLICATE KEY UPDATE empire=?";

	public Empire getEmpireOfFaction(Faction f){
		AsyncUtil.crashIfNotAsync();
		String id = f.getId();
		String query = "SELECT * from minecraft.empire_faction WHERE factionID=" + id;
		RSReader rs = new RSReader(EmpireDB.requestData(query));
		if(rs.next()){
			return Empire.fromID(rs.getInt("empire"));
		} else {
			return Empire.NONE;
		}
	}
	
	public void setEmpireOfFaction(Faction f, Empire e){
		AsyncUtil.crashIfNotAsync();
		SuperPS ps = new SuperPS(EmpireDB.prepareStatement(updateCommand),3);
		ps.setString(0, f.getId());
		ps.setInt(1, e.getID());
		ps.setString(2, f.getId());
		ps.executeAndClose();
	}
}
