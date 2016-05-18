package com.ginger_walnut.sqboosters.database;

import java.sql.Connection;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import com.ginger_walnut.sqboosters.SQBoosters;

public class DatabaseInterface {
	
	public static void addMultiplier(String booster, int multiplier, String purchaser, int minutes) {
		
		SQLDatabase database = new SQLDatabase();
		
		Connection con = database.con.getConnection();
		
		try {
			
			database.writeData(con, booster, multiplier, purchaser, minutes);
			
		} catch (Exception e) {
			
			e.printStackTrace();
			
		}
		
	}
	
	public static void updateLocalCopy() {
		
		SQLDatabase database = new SQLDatabase();
		
		Connection con = database.con.getConnection();
		
		try {
			
			ResultSet rs = database.readData(con);
			
			SQBoosters.databaseIDs.clear();
			SQBoosters.databaseBoosters.clear();
			SQBoosters.databaseMultipliers.clear();
			SQBoosters.databasePurchasers.clear();
			SQBoosters.databaseExpirationDates.clear();
			SQBoosters.databaseExpirationTimes.clear();
			
			while (rs.next()) {
				
				SQBoosters.databaseIDs.add(rs.getInt("ID"));
				SQBoosters.databaseBoosters.add(rs.getString("booster"));
				SQBoosters.databaseMultipliers.add(rs.getInt("multiplier"));
				SQBoosters.databasePurchasers.add(rs.getString("purchaser"));
				SQBoosters.databaseExpirationDates.add(rs.getDate("expirationdate"));
				SQBoosters.databaseExpirationTimes.add(rs.getTime("expirationdate"));
				
			}
			
		} catch (Exception e) {
			
			e.printStackTrace();
			
		}	
		
	}
	
	public static int getMultiplier(String booster) {
		
		int multiplier = 1;
		
		for (int i = 0; i < SQBoosters.databaseBoosters.size(); i ++) {
			
			if (SQBoosters.databaseBoosters.get(i).equals(booster)) {
				
				multiplier = multiplier + SQBoosters.databaseMultipliers.get(i);
				
			}
			
		}
		
		return multiplier;
		
	}
	
	public static List<Integer> getMultiplierList(String booster) {
		
		List<Integer> multiplierList = new ArrayList<Integer>();
		
		for (int i = 0; i < SQBoosters.databaseBoosters.size(); i ++) {
			
			if (SQBoosters.databaseBoosters.get(i).equals(booster)) {
				
				multiplierList.add(SQBoosters.databaseMultipliers.get(i));
				
			}
			
		}
		
		return multiplierList;
		
	}
	
	public static List<String> getPurchaserList(String booster) {
		
		List<String> purchaserList = new ArrayList<String>();
		
		for (int i = 0; i < SQBoosters.databaseBoosters.size(); i ++) {
			
			if (SQBoosters.databaseBoosters.get(i).equals(booster)) {
				
				purchaserList.add(SQBoosters.databasePurchasers.get(i));
				
			}
			
		}
		
		return purchaserList;
		
	}
	
}
