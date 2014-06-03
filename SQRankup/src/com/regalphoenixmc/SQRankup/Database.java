
package com.regalphoenixmc.SQRankup;

import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Database {

	public String player;
	public String server;
	public String world;
	public int x, y, z;
	public static String driver = "com.mysql.jdbc.Driver";
	public static String hostname = "localhost";
	public static String port = "3306";
	public static String db_name = "minecraft";
	public static String username = "minecraft";
	public static String password = "R3b!rth!ng";
	public static Connection cntx = null;
	public static String dsn = ("jdbc:mysql://" + hostname + ":" + port + "/" + db_name);

	public static void setUp() {

		String Database_table = "CREATE TABLE IF NOT EXISTS RANKUP (" + "`name` VARCHAR(32) NOT NULL," + "`kills` int(16) DEFAULT 0,"
				+ "`lastkill` VARCHAR(32) DEFAULT NULL," + "`lastkilltime` bigint(18) DEFAULT 0," + "PRIMARY KEY (`name`)" + ")";
		getContext();
		try {
			Driver driver = (Driver) Class.forName(Database.driver).newInstance();
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

	// return 0 if they haven't killed anyone
	public static int getKills(String player) {

		System.out.print("[Rankup] Getting Kills"); // TODO: Debugging ish
		if (!getContext())
			System.out.println("something is wrong!");
		Statement s = null;
		try {
			s = cntx.createStatement();
			ResultSet rs = s.executeQuery("SELECT kills FROM RANKUP WHERE name = " + "\'" + player + "\'");

			while (rs.next()) {
				int kills = rs.getInt("kills");
				return kills;
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
		return 0;
	}

	public static void setNewKills(final String player, final int kills) {

		Runnable task = new Runnable() {

			public void run() {

				if (!getContext())
					System.out.println("something is wrong!");
				PreparedStatement s = null;
				try {
					s = cntx.prepareStatement("INSERT INTO RANKUP (name, kills, lastkill, lastkilltime) values (?,?,?,?)");
					s.setString(1, player);
					s.setInt(2, kills);
					s.setString(3, "placeholder");
					s.setDouble(4, 0);
					s.execute();
					s.close();
				} catch (SQLException e) {
					System.out.print("[SQDatabase] SQL Error" + e.getMessage());
				} catch (Exception e) {
					System.out.print("[SQDatabase] SQL Error (Unknown)");
					e.printStackTrace();
				} finally {
					close(s);
				}
			}
		};

		new Thread(task, "DBThread").start();

	}

	public static void setKills(final String player, final int kills) {

		Runnable task = new Runnable() {

			public void run() {

				if (!getContext())
					System.out.println("something is wrong!");
				PreparedStatement s = null;
				try {
					s = cntx.prepareStatement("UPDATE RANKUP SET kills = ? WHERE NAME = ?");
					s.setInt(1, kills);
					s.setString(2, player);
					s.execute();
					s.close();
				} catch (SQLException e) {
					System.out.print("[SQDatabase] SQL Error" + e.getMessage());
				} catch (Exception e) {
					System.out.print("[SQDatabase] SQL Error (Unknown)");
					e.printStackTrace();
				} finally {
					close(s);
				}
			}
		};

		new Thread(task, "DBThread").start();
	}

	// return null if they haven't killed anyone

	public static String getLastKill(String player) {

		System.out.print("[Rankup] Getting Last Kill"); // TODO: Debugging ish
		if (!getContext())
			System.out.println("something is wrong!");
		Statement s = null;
		try {
			s = cntx.createStatement();
			ResultSet rs = s.executeQuery("SELECT lastkill FROM RANKUP WHERE name = " + "\'" + player + "\'");

			while (rs.next()) {
				String kill = rs.getString("lastkill");
				return kill;
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
		return null;
	}

	public static void setLastKill(final String player, final String killed) {

		Runnable task = new Runnable() {

			public void run() {

				if (!getContext())
					System.out.println("something is wrong!");
				PreparedStatement s = null;
				try {
					s = cntx.prepareStatement("UPDATE RANKUP SET lastkill = ? WHERE NAME = ?");
					s.setString(1, killed);
					s.setString(2, player);
					s.execute();
					s.close();
				} catch (SQLException e) {
					System.out.print("[SQDatabase] SQL Error" + e.getMessage());
				} catch (Exception e) {
					System.out.print("[SQDatabase] SQL Error (Unknown)");
					e.printStackTrace();
				} finally {
					close(s);
				}
			}
		};

		new Thread(task, "DBThread").start();
	}

	// return 0 if they haven't killed anyone
	public static double getLastKillTime(String player) {

		System.out.print("[Rankup] Getting Last Kill Time"); // TODO: Debugging
																// ish
		if (!getContext())
			System.out.println("something is wrong!");
		Statement s = null;
		try {
			s = cntx.createStatement();
			ResultSet rs = s.executeQuery("SELECT lastkilltime FROM RANKUP WHERE name = " + "\'" + player + "\'");

			while (rs.next()) {
				double kills = rs.getDouble("lastkilltime");
				return kills;
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
		return 0;
	}

	public static void setLastKillTime(final String player, final double time) {

		Runnable task = new Runnable() {

			public void run() {

				if (!getContext())
					System.out.println("something is wrong!");
				PreparedStatement s = null;
				try {
					s = cntx.prepareStatement("UPDATE RANKUP SET lastkilltime = ? WHERE NAME = ?");
					s.setDouble(1, time);
					s.setString(2, player);
					s.execute();
					s.close();
				} catch (SQLException e) {
					System.out.print("[SQDatabase] SQL Error" + e.getMessage());
				} catch (Exception e) {
					System.out.print("[SQDatabase] SQL Error (Unknown)");
					e.printStackTrace();
				} finally {
					close(s);
				}
			}
		};
		new Thread(task, "DBThread").start();
	}

	// checks if they are in the DB
	public static boolean hasKey(String player) {

		System.out.print("[Rankup] Getting Last Kill Time"); // TODO: Debugging
																// ish
		if (!getContext())
			System.out.println("something is wrong!");
		Statement s = null;
		try {
			s = cntx.createStatement();
			ResultSet rs = s.executeQuery("SELECT * FROM RANKUP WHERE name = " + "\'" + player + "\'");

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

	// don't bother with these, just helpers for me
	public static void incrementKills(String player, int i) {

		setKills(player, getKills(player) + i);
	}

	public static void setLastKillTimeToCurrent(String player) {

		setLastKillTime(player, System.currentTimeMillis());
	}

	// Connection Setter Method I need
	public static boolean getContext() {

		try {
			if (cntx == null || cntx.isClosed() || !cntx.isValid(1)) {
				if (cntx != null && !cntx.isClosed()) {
					try {
						cntx.close();
					} catch (SQLException e) {
						System.out.print("Exception caught");
					}
					cntx = null;
				}
				if ((Database.username.equalsIgnoreCase("")) && (Database.password.equalsIgnoreCase(""))) {
					cntx = DriverManager.getConnection(Database.dsn);
				} else
					cntx = DriverManager.getConnection(Database.dsn, Database.username, Database.password);

				if (cntx == null || cntx.isClosed())
					return false;
			}

			return true;
		} catch (SQLException e) {
			System.out.print("Error could not Connect to db " + Database.dsn + ": " + e.getMessage());
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