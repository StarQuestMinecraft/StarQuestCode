package com.starquestminecraft.sqcontracts.database;

import java.util.UUID;

public interface Database {
	public ContractPlayerData getDataOfPlayer(UUID u);
	public void updatePlayerData(UUID u, ContractPlayerData d);
	public ContractPlayerData[] getAllPlayerData();
}
