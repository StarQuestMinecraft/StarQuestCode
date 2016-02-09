package com.ginger_walnut.sqboosters.database;

import java.sql.Connection;

public class DatabaseInterface {
	
	public void setMultiplier(String booster, int multiplier) {
		
		SQLDatabase database = new SQLDatabase();
		
		Connection con = database.con.getConnection();
		
		try {
			
			if (database.checkRecordExists(con, booster)) {
				
				database.updateData(con, booster, multiplier);
				
			} else {
				
				database.writeData(con, booster, multiplier);
				
			}
			
		} catch (Exception e) {
			
			e.printStackTrace();
			
		}
		
	}
	
	public int getMultiplier(String booster) {
		
		SQLDatabase database = new SQLDatabase();
		
		Connection con = database.con.getConnection();
		
		try {
			
			if (database.checkRecordExists(con, booster)) {
				
				return database.readData(con, booster);
				
			} else {
				
				database.writeData(con, booster, 1);
				
				return 1;
				
			}
			
		} catch (Exception e) {
			
			e.printStackTrace();
			
			return 1;
			
		}
		
	}
	
}
