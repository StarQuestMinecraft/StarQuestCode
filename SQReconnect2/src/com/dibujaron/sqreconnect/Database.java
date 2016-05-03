package com.dibujaron.sqreconnect;

import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;


import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.connection.ProxiedPlayer;

import com.starquestminecraft.greeter.sqldb.CachingMySQLDB;

public class Database {

	/*public static String driverString = "com.mysql.jdbc.Driver";
	public static String hostname = "starquest.c1odbljhmyum.us-east-1.rds.amazonaws.com";
	public static String port = "3306";
	public static String db_name = "minecraft";
	public static String username = "sqmaster";
	public static String password = "R3b!rth!ng";
	public static Connection cntx = null;
	public static String dsn = ("jdbc:mysql://" + hostname + ":" + port + "/" + db_name);*/
	
	private static final String update_both = "INSERT INTO reconnect_data(uuid, lastServer, lastSQServer) VALUES (?, ?, ?) ON DUPLICATE KEY UPDATE lastServer=?,lastSQServer=?";
	private static final String update_other_only = "INSERT INTO reconnect_data(uuid, lastServer) VALUES (?, ?) ON DUPLICATE KEY UPDATE lastServer=?";
	private static final String get_last = "SELECT lastServer FROM reconnect_data WHERE uuid = ?";
	private static final String get_SQ = "SELECT lastSQServer FROM reconnect_data WHERE uuid = ?";

	//c
	public static void setUp() {

		String Database_table = "CREATE TABLE IF NOT EXISTS reconnect_data (`uuid` VARCHAR(36),`lastServer` VARCHAR(32),`lastSQServer` VARCHAR(32), PRIMARY KEY(`uuid`))";
		getContext();
		/*try {
			Driver driver = (Driver) Class.forName(driverString).newInstance();
			DriverManager.registerDriver(driver);
		} catch (Exception e) {
			System.out.println("[SQReconnect] Driver error: " + e);
		}*/
		Statement s = null;
		try {
			getContext();
			s = getConnection().createStatement();
			s.executeUpdate(Database_table);
			System.out.println("[SQReconnect] Table check/creation sucessful");
		} catch (SQLException ee) {
			System.out.println("[SQReconnect] Table Creation Error");
		}
	}

	public static void updateServer(final ProxiedPlayer player, final String server, final boolean isSQ) {

		Runnable task = new Runnable() {

			public void run() {

				if (!Database.getContext()) {
					System.out.println("something is wrong!");
				}
				PreparedStatement s = null;
				try {
					String uidstr = player.getUniqueId().toString();
					if(isSQ){
						s = getConnection().prepareStatement(update_both);
						System.out.println("Update type: sq");
						s.setString(1, uidstr);
						s.setString(2, server);
						s.setString(3, server);
						s.setString(4, server);
						s.setString(5, server);
					} else {
						s = getConnection().prepareStatement(update_other_only);
						System.out.println("Update type: other server");
						s.setString(1, uidstr);
						s.setString(2, server);
						s.setString(3, server);
					}
					s.execute();
					s.close();
				} catch (SQLException e) {
					System.out.print("[SQReconnect] SQL Error" + e.getMessage());
				} catch (Exception e) {
					System.out.print("[SQReconnect] SQL Error (Unknown)");
					e.printStackTrace();
				}
			}
		};
		ProxyServer.getInstance().getScheduler().runAsync(SQReconnect.getInstance(), task);
	}

	public static String getServer(ProxiedPlayer plr, boolean SQOnly) {

		if (!getContext()) {
			System.out.println("something is wrong!");
		}
		PreparedStatement s = null;
		try {
			String statement;
			if(SQOnly){
				statement = get_SQ;
			} else {
				statement = get_last;
			}
			s = getConnection().prepareStatement(statement);
			s.setString(1, plr.getUniqueId().toString());
			ResultSet rs = s.executeQuery();
			if (rs.next()) {
				String server;
				if(SQOnly){
					try{
					server = rs.getString("lastSQServer");
					} catch (Exception e){
						server = "Trinitos_Alpha";
					}
					if(server == null){
						server = "Trinitos_Alpha";
					}
				} else {
					server = rs.getString("lastServer");
				}
				s.close();
				return server;
			} else {
				s.close();
				return null;
			}

		} catch (SQLException e) {
			System.out.print("[SQDatabase] SQL Error" + e.getMessage());
		} catch (Exception e) {
			System.out.print("[SQDatabase] SQL Error (Unknown)");
			e.printStackTrace();
		}
		return null;
	}

	public static boolean getContext() {
		return CachingMySQLDB.getContext();
	}
	
	public static Connection getConnection(){
		return CachingMySQLDB.cntx;
	}

}
