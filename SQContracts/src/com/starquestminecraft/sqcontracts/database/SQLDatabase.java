package com.starquestminecraft.sqcontracts.database;

import java.sql.Connection;
import java.util.UUID;



import net.countercraft.movecraft.bedspawns.Bedspawn;

public class SQLDatabase implements Database{

	@Override
	public ContractPlayerData getDataOfPlayer(UUID u) {
		return null;
	}

	@Override
	public void updatePlayerData(UUID u, ContractPlayerData d) {
		// TODO Auto-generated method stub
		
	}
	
	public static boolean getContext() {
		return Bedspawn.getContext();
	}
	
	private static Connection getConnection(){
		return Bedspawn.cntx;
	}

}
