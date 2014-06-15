
package com.regalphoenixmc.SQRankup;

import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Database {

	public static String driverString = "com.mysql.jdbc.Driver";
	public static String hostname = "localhost";
	public static String port = "3306";
	public static String db_name = "minecraft";
	public static String username = "minecraft";
	public static String password = "R3b!rth!ng";
	public static Connection cntx = null;
	public static String dsn = "jdbc:mysql://" + hostname + ":" + port + "/" + db_name;

	public static void setUp() {

		String Database_table = "CREATE TABLE IF NOT EXISTS RANKUP (`name` VARCHAR(32) NOT NULL,`kills` int(16) DEFAULT 0,`lastkill` VARCHAR(32) DEFAULT NULL,`lastkilltime` bigint(18) DEFAULT 0,PRIMARY KEY (`name`))";

		getContext();
		try {
			Driver driver = (Driver) Class.forName(driverString).newInstance();
			DriverManager.registerDriver(driver);
		} catch (Exception e) {
			System.out.println("[SQDatabases] Driver error: " + e);
		}
		Statement s = null;
		try {
			s = cntx.createStatement();
			s.executeUpdate(Database_table);
			System.out.println("[SQDatabase] Table check/creation sucessful");
		} catch (SQLException ee) {
			System.out.println("[SQDatabase] Table Creation Error");
		} finally {
			close(s);
		}
	}

	public static void updateEntry(final String player, final Long lastKillTime, final String killed, final int kills) {

		Runnable task = new Runnable() {

			public void run() {

				if (!Database.getContext()) {
					System.out.println("something is wrong!");
				}
				PreparedStatement s = null;
				try {
					s = Database.cntx.prepareStatement("UPDATE RANKUP SET `lastkill` = ?, `kills` = ?, `lastkilltime` = ? WHERE `name` = ?");
					s.setString(1, killed);
					s.setInt(2, kills);
					s.setLong(3, lastKillTime.longValue());
					s.setString(4, player);
					System.out.println(s);
					s.execute();
					s.close();
				} catch (SQLException e) {
					System.out.print("[SQDatabase] SQL Error" + e.getMessage());
				} catch (Exception e) {
					System.out.print("[SQDatabase] SQL Error (Unknown)");
					e.printStackTrace();
				} finally {
					Database.close(s);
				}
			}
		};
		new Thread(task, "DBThread").start();
	}

	public static void newEntry(final String player, final Long lastKillTime, final String killed, final int kills) {

		Runnable task = new Runnable() {

			public void run() {

				if (!Database.getContext()) {
					System.out.println("something is wrong!");
				}
				PreparedStatement s = null;
				try {
					s = Database.cntx.prepareStatement("INSERT INTO RANKUP (`lastkill`,`kills`,`lastkilltime`,`name`) VALUES(?,?,?,?)");
					s.setString(1, killed);
					s.setInt(2, kills);
					s.setLong(3, lastKillTime.longValue());
					s.setString(4, player);
					System.out.println(s);
					s.execute();
					s.close();
				} catch (SQLException e) {
					System.out.print("[SQDatabase] SQL Error" + e.getMessage());
				} catch (Exception e) {
					System.out.print("[SQDatabase] SQL Error (Unknown)");
					e.printStackTrace();
				} finally {
					Database.close(s);
				}
			}
		};
		new Thread(task, "DBThread").start();
	}

	public static RankupPlayer getEntry(String player) {

		long lastKillTime = 0L;
		int kills = 0;
		String lastKill = "";
		System.out.print("[Rankup] Getting Last Kill Time");
		if (!getContext()) {
			System.out.println("something is wrong!");
		}
		PreparedStatement s = null;
		try {
			s = cntx.prepareStatement("SELECT `lastkilltime`,`kills`,`lastkill` FROM RANKUP WHERE `name` = ?");
			s.setString(1, player);
			System.out.println(s);
			ResultSet rs = s.executeQuery();
			while (rs.next()) {
				lastKillTime = rs.getLong("lastkilltime");
				kills = rs.getInt("kills");
				lastKill = rs.getString("lastkill");
			}
			s.close();
			return new RankupPlayer(player, lastKillTime, lastKill, kills);
		} catch (SQLException e) {
			System.out.print("[SQDatabase] SQL Error" + e.getMessage());
		} catch (Exception e) {
			System.out.print("[SQDatabase] SQL Error (Unknown)");
			e.printStackTrace();
		} finally {
			close(s);
		}
		return null;
	}

	public static boolean hasKey(String player) {

		System.out.print("[Rankup] Getting Last Kill Time");
		if (!getContext()) {
			System.out.println("something is wrong!");
		}
		PreparedStatement s = null;
		try {
			s = cntx.prepareStatement("SELECT * FROM RANKUP WHERE `name` = ?");
			s.setString(1, player);
			ResultSet rs = s.executeQuery();
			if (rs.next()) {
				s.close();
				return true;
			}
			s.close();
		} catch (SQLException e) {
			System.out.print("[SQDatabase] SQL Error" + e.getMessage());
		} catch (Exception e) {
			System.out.print("[SQDatabase] SQL Error (Unknown)");
			e.printStackTrace();
		} finally {
			close(s);
		}
		return false;
	}

	public static boolean getContext() {

		try {
			if ((cntx == null) || (cntx.isClosed()) || (!cntx.isValid(1))) {
				if ((cntx != null) && (!cntx.isClosed())) {
					try {
						cntx.close();
					} catch (SQLException e) {
						System.out.print("Exception caught");
					}
					cntx = null;
				}
				if ((username.equalsIgnoreCase("")) && (password.equalsIgnoreCase(""))) {
					cntx = DriverManager.getConnection(dsn);
				} else {
					cntx = DriverManager.getConnection(dsn, username, password);
				}
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

	private static void close(Statement s) {

		if (s == null) {
			return;
		}
		try {
			s.close();
			cntx.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
