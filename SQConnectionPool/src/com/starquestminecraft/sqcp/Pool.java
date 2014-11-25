package com.starquestminecraft.sqcp;

import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.SQLException;

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
	
	public void onEnable() {
		//connections = new SharedConnection[3];

		try {
			Driver driver = (Driver) Class.forName(Pool.driver).newInstance();
			DriverManager.registerDriver(driver);
			/*for (int i = 0; i < connections.length; i++) {
				Connection c = DriverManager.getConnection(dsn, username, password);
				connections[i] = new SharedConnection(c);
			}*/
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static Connection grab(){
		Connection c = null;
		try {
			c = DriverManager.getConnection(dsn, username, password);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return c;
	}

	public static void returnConnection(Connection c) {
		try {
			c.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
