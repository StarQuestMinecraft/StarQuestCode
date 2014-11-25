package com.starquestminecraft.sqcp;

import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;

import org.bukkit.plugin.java.JavaPlugin;

public class Pool extends JavaPlugin {

	public static String driver = "com.mysql.jdbc.Driver";
	public static String hostname = "play.starquestminecraft.com";
	public static String port = "3306";
	public static String db_name = "minecraft";
	public static String username = "minecraft";
	public static String password = "R3b!rth!ng";
	public static String dsn = ("jdbc:mysql://" + hostname + ":" + port + "/" + db_name);
	public static int totalChecks = 0;

	private static SharedConnection[] connections;

	public void onEnable() {
		connections = new SharedConnection[3];

		try {
			Driver driver = (Driver) Class.forName(Pool.driver).newInstance();
			DriverManager.registerDriver(driver);
			for (int i = 0; i < connections.length; i++) {
				Connection c = DriverManager.getConnection(dsn, username, password);
				connections[i] = new SharedConnection(c);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/*
	 * Plugins that should use this API
	 * 
	 * - SQShops - SQRankup - SQDuties - SQBungeeMovecraft - Redeem
	 * 
	 * Pre-pool stats: 12 connections * (12 planets + 3 space + 1 limbo + 1
	 * belt) = 204 cntx 12 connections * (10 planets + 3 space + 1 limbo) = 168
	 * cntx
	 * 
	 * Post-pool stats (assuming max pool size of 3): 9 connections * (12
	 * planets + 3 space + 1 limbo + 1 belt) = 153 cntx 9 connections * (10
	 * planets + 3 space + 1 limbo) = 126 cntx
	 * 
	 * Maximum servers with this pool: 22 1 limbo + 1 belt + 4 space + 16
	 * planets
	 */

	public static SharedConnection checkOutConnection() {
		totalChecks++;
		for (SharedConnection s : connections) {
			if (!s.isInUse()) {
				s.acquire();
				return s;
			}
		}
		//if none of them are available, get one of them. With this setup, it will pick a different one each time.
		connections[totalChecks % 3].acquire();
		return connections[totalChecks % 3];
	}

	public static void returnConnection(SharedConnection c) {
		c.release();
	}
}
