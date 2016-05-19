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

public class Database {

	public static String driverString = "com.mysql.jdbc.Driver";
	public static String hostname = "starquest.c1odbljhmyum.us-east-1.rds.amazonaws.com";
	public static String port = "3306";
	public static String db_name = "minecraft";
	public static String username = "sqmaster";
	public static String password = "90jrd3v";
	public static Connection cntx = null;
	public static String dsn = ("jdbc:mysql://" + hostname + ":" + port + "/" + db_name);
	
	private static final String update_main = "INSERT INTO reconnect_data(uuid, mainServer, lastServer) VALUES (?, ?, ?) ON DUPLICATE KEY UPDATE mainServer=?,lastServer=?";
	private static final String update_alt = "INSERT INTO reconnect_data(uuid, altServer, lastServer) VALUES (?, ?, ?) ON DUPLICATE KEY UPDATE altServer=?,lastServer=?";
	private static final String get_main = "SELECT mainServer FROM reconnect_data WHERE uuid = ?";
	private static final String get_alt = "SELECT altServer FROM reconnect_data WHERE uuid = ?";
	private static final String get_last = "SELECT lastServer FROM reconnect_data WHERE uuid = ?";

	//c
	public static void setUp() {

		String Database_table = "CREATE TABLE IF NOT EXISTS reconnect_data (`uuid` VARCHAR(36),`mainServer` VARCHAR(32),`altServer` VARCHAR(32), `lastServer` VARCHAR(32), PRIMARY KEY(`uuid`))";
		getContext();
		try {
			Driver driver = (Driver) Class.forName(driverString).newInstance();
			DriverManager.registerDriver(driver);
		} catch (Exception e) {
			System.out.println("[SQReconnect] Driver error: " + e);
		}
		Statement s = null;
		try {
			s = cntx.createStatement();
			s.executeUpdate(Database_table);
			System.out.println("[SQReconnect] Table check/creation sucessful");
		} catch (SQLException ee) {
			System.out.println("[SQReconnect] Table Creation Error");
		}
	}

	public static void updateServer(final ProxiedPlayer player, final String server, final ServerType type) {

		Runnable task = new Runnable() {

			public void run() {

				if (!Database.getContext()) {
					System.out.println("something is wrong!");
				}
				PreparedStatement s = null;
				try {
					if(type == ServerType.ALT){
						s = cntx.prepareStatement(update_alt);
					} else {
						s = cntx.prepareStatement(update_main);
					}
					String uidstr = player.getUniqueId().toString();
					s.setString(1, uidstr);
					s.setString(2, server);
					s.setString(3, server);
					s.setString(4, server);
					s.setString(5, server);
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

	public static String getServer(ProxiedPlayer plr, ServerType type) {

		if (!getContext()) {
			System.out.println("something is wrong!");
		}
		PreparedStatement s = null;
		try {
			String statement;
			if(type == ServerType.MAIN){
				statement = get_main;
			} else if(type == ServerType.ALT){
				statement = get_alt;
			} else {
				statement = get_last;
			}
			s = cntx.prepareStatement(statement);
			s.setString(1, plr.getUniqueId().toString());
			ResultSet rs = s.executeQuery();
			if (rs.next()) {
				String server;
				if(type == ServerType.MAIN){
					server = rs.getString("mainServer");
				} else if(type == ServerType.ALT){
					server = rs.getString("altServer");
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

		try {
			if ((cntx == null) || (cntx.isClosed()) || (!cntx.isValid(1))) {
				cntx = DriverManager.getConnection(dsn, username, password);
				if ((cntx == null) || (cntx.isClosed())) {
					return false;
				}
			}
			return true;
		} catch (SQLException e) {
			System.out.print("Error could not Connect to db " + dsn + ": " + e.getMessage());
		}
		return false;
	}

}
