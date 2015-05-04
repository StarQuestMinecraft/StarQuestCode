
package com.regalphoenixmc.SQRankup;

import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;

public class Database {

	public static String driverString = "com.mysql.jdbc.Driver";
	public static String hostname = "play.starquestminecraft.com";
	public static String port = "3306";
	public static String db_name =  "minecraft";
	public static String username = "minecraft";
	public static String password = "R3b!rth!ng";
	public static Connection cntx = null;
	public static String dsn = "jdbc:mysql://" + hostname + ":" + port + "/" + db_name;

	public static void setUp() {

		String Database_table = "CREATE TABLE IF NOT EXISTS rankup_beta (`killer` VARCHAR(36),`killed` VARCHAR(36),`lastkilltime` bigint(18))";
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

	public static void addKill(final Player player, final OfflinePlayer killed) {

		Runnable task = new Runnable() {

			public void run() {

				if (!Database.getContext()) {
					System.out.println("something is wrong!");
				}
				PreparedStatement s = null;
				try {
					s = Database.cntx.prepareStatement("INSERT INTO rankup_beta (`killer`,`killed`,`lastkilltime`) VALUES (?,?,?)");
					s.setString(1, player.getUniqueId().toString());
					s.setString(2, killed.getUniqueId().toString());
					s.setLong(3, System.currentTimeMillis());
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

	public static boolean isInCooldown(OfflinePlayer killer, OfflinePlayer killed) {

		System.out.print("[Rankup] Getting Last Kill Time");
		if (!getContext()) {
			System.out.println("something is wrong!");
		}
		PreparedStatement s = null;
		try {
			s = cntx.prepareStatement("SELECT * FROM rankup_beta WHERE `killer` = ? AND round(unix_timestamp() * 1000 + MICROSECOND(sysdate(6)) / 1000) > `lastkilltime` + 1200000 AND `killed` = ?");
			s.setString(1, killer.getUniqueId().toString());
			s.setString(2, killed.getUniqueId().toString());
			ResultSet rs = s.executeQuery();
			if (rs.next()) {
				s.close();
				return true;
			} else {
				s.close();
				return false;
			}

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
