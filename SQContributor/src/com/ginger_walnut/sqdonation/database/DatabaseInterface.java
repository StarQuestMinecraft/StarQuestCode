package com.ginger_walnut.sqdonation.database;

import java.sql.Connection;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import com.ginger_walnut.sqdonation.SQDonation;

public class DatabaseInterface {
	
	public static void setAmount(String uuid, int amount) {
		
		SQLDatabase database = new SQLDatabase();
		
		Connection con = database.con.getConnection();
		
		try {
			
			database.writeData(con, uuid, amount);
			updateLocalCopy();
			
		} catch (Exception e) {
			
			e.printStackTrace();
			
		}
		
	}
	
	public static void addAmount(String uuid, int amount) {
		
		SQLDatabase database = new SQLDatabase();
		
		Connection con = database.con.getConnection();
		
		try {
			
			updateLocalCopy();
			database.writeData(con, uuid, SQDonation.donaterMap.get(Bukkit.getOfflinePlayer(UUID.fromString(uuid)).getName()) + amount);
			updateLocalCopy();
			
		} catch (Exception e) {
			
			e.printStackTrace();
			
		}
		
		
	}
	
	public static void updateLocalCopy() {
		
		SQLDatabase database = new SQLDatabase();
		
		Connection con = database.con.getConnection();
		
		try {
			
			ResultSet rs = database.readData(con);
			
			SQDonation.topDonaters.clear();
			SQDonation.donaterMap.clear();
			
			while (rs.next()) {
				
				String donater = rs.getString("donater");
				int amount = rs.getInt("amount");
				
				List<Integer> amounts = new ArrayList<Integer>();
				
				amounts.addAll(SQDonation.groupMap.keySet());
				
				for (int i = 0; i < SQDonation.groupMap.size(); i ++) {
					
					System.out.print(Bukkit.getWorlds().get(0).getName());
					System.out.print(Bukkit.getOfflinePlayer(UUID.fromString(donater)));
					System.out.print(SQDonation.permission.getPlayerGroups(Bukkit.getWorlds().get(0).getName(), Bukkit.getOfflinePlayer(UUID.fromString(donater))));
					
					String[] groups = SQDonation.permission.getPlayerGroups(Bukkit.getWorlds().get(0).getName(), Bukkit.getOfflinePlayer(UUID.fromString(donater)));
					
					boolean isInGroup = false;
					
					for (String group : groups) {
						
						if (group.equals(SQDonation.groupMap.get(amounts.get(i)))) {
							
							isInGroup = true;
							
						}
					
					}
					
					if (isInGroup) {
						
						SQDonation.permission.playerRemoveGroup(Bukkit.getWorlds().get(0).getName(), Bukkit.getOfflinePlayer(UUID.fromString(donater)), SQDonation.groupMap.get(amounts.get(i)));
						
					}
					
				}
				
				for (int i = 0; i < SQDonation.groupMap.size(); i ++) {
					
					if (amount >= amounts.get(i)) {
						
						SQDonation.permission.playerAddGroup(Bukkit.getWorlds().get(0).getName(), Bukkit.getOfflinePlayer(UUID.fromString(donater)), SQDonation.groupMap.get(amounts.get(i)));
						
						i = SQDonation.groupMap.size();
						
					}
					
				}
				
				SQDonation.donaterMap.put(Bukkit.getOfflinePlayer(UUID.fromString(donater)).getName(), amount);
				
			}
			
			List<String> donaters = new ArrayList<String>();
			donaters.addAll(SQDonation.donaterMap.keySet());
			
			for (int i = 0; i < donaters.size(); i ++) {
				
				if (i == 0) {
					
					SQDonation.topDonaters.add(donaters.get(i));
					
				} else {

					for (int j = 0; j < SQDonation.topDonaters.size(); j ++) {
						
						if (SQDonation.donaterMap.get(SQDonation.topDonaters.get(j)) < SQDonation.donaterMap.get(donaters.get(i))) {
							
							SQDonation.topDonaters.add(j, donaters.get(i));

							if (SQDonation.topDonaters.size() == 11) {
								
								SQDonation.topDonaters.remove(10);
								
							}
							
							j = 11;
							
						} else if (j >= SQDonation.topDonaters.size() - 1) {
							
							if (SQDonation.topDonaters.size() != 11) {
								
								SQDonation.topDonaters.add(j, donaters.get(i));
								
							}
							
							j = 11;
							
						}
						
					}
					
				}
				
			}
			
		} catch (Exception e) {
			
			e.printStackTrace();
			
		}	
		
	}
	
}
