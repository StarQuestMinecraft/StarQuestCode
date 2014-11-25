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

	//private static SharedConnection[] connections;
	private static Connection c;
	
	public void onEnable() {
		//connections = new SharedConnection[3];

		try {
			Driver driver = (Driver) Class.forName(Pool.driver).newInstance();
			DriverManager.registerDriver(driver);
			/*for (int i = 0; i < connections.length; i++) {
				Connection c = DriverManager.getConnection(dsn, username, password);
				connections[i] = new SharedConnection(c);
			}*/
			c = DriverManager.getConnection(dsn, username, password);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static Connection grab(){
		return c;
	}
	/*public static SharedConnection checkOutConnection() {
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
	}*/
}
