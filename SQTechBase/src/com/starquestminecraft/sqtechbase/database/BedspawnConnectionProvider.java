package com.starquestminecraft.sqtechbase.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import org.bukkit.Bukkit;

import com.starquestminecraft.sqtechbase.SQTechBase;

import net.countercraft.movecraft.bedspawns.Bedspawn;

public class BedspawnConnectionProvider implements ConnectionProvider{

	@Override
	public Connection getConnection() {
		
		if (Bukkit.getPluginManager().isPluginEnabled("Movecraft")) {
			
			Bedspawn.getContext();
			return Bedspawn.cntx;
			
		} else {
			
			try {
				
				return DriverManager.getConnection("jdbc:mysql://" + SQTechBase.config.getString("database.host") + ":" + SQTechBase.config.getString("database.port") + "/minecraft", SQTechBase.config.getString("database.username"), SQTechBase.config.getString("database.password"));
				
			} catch (SQLException exception) {
				
				exception.printStackTrace();
				
			}
			
		}
		
		return null;

	}

}
