package com.starquestminecraft.sqrankup2.database;

import java.util.ArrayList;
import java.util.UUID;

public interface Database {
	public ArrayList<String> getCertsOfPlayer(UUID u);
	public void updateCertsOfPlayer(UUID u, ArrayList<String> newCerts);
}
