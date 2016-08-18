package com.whirlwindgames.dibujaron.sqempire;

import org.bukkit.Bukkit;
import org.bukkit.scheduler.BukkitScheduler;

import com.whirlwindgames.dibujaron.sqempire.database.EmpireDB;
import com.whirlwindgames.dibujaron.sqempire.util.RSReader;

public class DatabaseTask extends Thread {
	
	@SuppressWarnings("deprecation")
	public void run() {
		
		BukkitScheduler scheduler = Bukkit.getServer().getScheduler();
		
		scheduler.scheduleAsyncRepeatingTask(SQEmpire.getInstance(), new Runnable() {
			
			@Override
			public void run() {
				
				String query = "SELECT * from minecraft.empire_territories";
				RSReader rs = new RSReader(EmpireDB.requestData(query));

				while (rs.next()) {
					
					double empire0 = rs.getInt("empire0");
					double empire1 = rs.getInt("empire1");
					double empire2 = rs.getInt("empire2");
					double empire3 = rs.getInt("empire3");
					
					double total = empire0 + empire1 + empire2 + empire3;
					
					if (total > 0) {
						
						if (empire0 / total >= .6) {
							
							SQEmpire.dominantEmpires.put(rs.getString("planet"), Empire.NONE);
							
						} else if (empire1 / total >= .6) {
							
							SQEmpire.dominantEmpires.put(rs.getString("planet"), Empire.ARATOR);
							
						} else if (empire2 / total >= .6) {
							
							SQEmpire.dominantEmpires.put(rs.getString("planet"), Empire.REQUIEM);
							
						} else if (empire3 / total >= .6) {
							
							SQEmpire.dominantEmpires.put(rs.getString("planet"), Empire.YAVARI);
							
						}
						
					}

				}
				
			}
			
		}, 60, 6000);
		
	}
	
}
