
package us.higashiyama.george.SQDuties;

import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import net.countercraft.movecraft.bedspawns.Bedspawn;

public class Database {

	public String player;
	public String server;
	public String world;
	public int x, y, z;
	public static String driverString = "com.mysql.jdbc.Driver";

	public static void setUp() {

		String Database_table = "CREATE TABLE IF NOT EXISTS Duties (" + "`UUID` VARCHAR(64) NOT NULL," + "`inventory` LONGTEXT," + "`armor` LONGTEXT,"
				+ "`location` LONGTEXT," + "PRIMARY KEY (`name`)" + ")";
		try {
			Driver driver = (Driver) Class.forName(driverString).newInstance();
			DriverManager.registerDriver(driver);
		} catch (Exception e) {
			System.out.println("[SQDatabases] Driver error: " + e);
		}
		Statement s = null;

		try {
			s = getConn().createStatement();
			s.executeUpdate(Database_table);
			System.out.println("[SQDatabase] Table check/creation sucessful");
		} catch (SQLException ee) {
			System.out.println("[SQDatabase] Table Creation Error");
		}

	}

	public static boolean hasKey(String name) { // ought to be UUID

		PreparedStatement s = null;
		try {
			s = getConn().prepareStatement("SELECT * FROM Duties WHERE `UUID` = ?");
			s.setString(1, name.toString());
			ResultSet rs = s.executeQuery();
			while (rs.next()) {
				return true;
			}
			s.close();
			return false;

		} catch (SQLException e) {
			System.out.print("[CCDB] SQL Error" + e.getMessage());
		} catch (Exception e) {
			System.out.print("[CCDB] SQL Error (Unknown)");
			e.printStackTrace();
		}

		return false;

	}

	public static String getInv(String name) { // UUID
		PreparedStatement s = null;
		try {
			s = getConn().prepareStatement("SELECT `inventory` FROM Duties WHERE `UUID` = ?");
			s.setString(1, name.toString());
			ResultSet rs = s.executeQuery();
			while (rs.next()) {
				return rs.getString("inventory");
			}
			s.close();

		} catch (SQLException e) {
			System.out.print("[CCDB] SQL Error" + e.getMessage());
		} catch (Exception e) {
			System.out.print("[CCDB] SQL Error (Unknown)");
			e.printStackTrace();
		} 
		return null;

	}

	public static String getArmor(String name) { // UUID

		PreparedStatement s = null;
		try {
			s = getConn().prepareStatement("SELECT `armor` FROM Duties WHERE `UUID` = ?");
			s.setString(1, name.toString());
			ResultSet rs = s.executeQuery();
			while (rs.next()) {
				return rs.getString("armor");
			}
			s.close();

		} catch (SQLException e) {
			System.out.print("[CCDB] SQL Error" + e.getMessage());
		} catch (Exception e) {
			System.out.print("[CCDB] SQL Error (Unknown)");
			e.printStackTrace();
		}
		return null;

	}

	public static String getLocation(String name) { // UUID

		PreparedStatement s = null;
		try {
			s = getConn().prepareStatement("SELECT `location` FROM Duties WHERE `UUID` = ?");
			s.setString(1, name.toString());
			ResultSet rs = s.executeQuery();
			while (rs.next()) {
				return rs.getString("location");
			}
			s.close();

		} catch (SQLException e) {
			System.out.print("[CCDB] SQL Error" + e.getMessage());
		} catch (Exception e) {
			System.out.print("[CCDB] SQL Error (Unknown)");
			e.printStackTrace();
		}
		return null;

	}

	public static void deleteKey(final String name) {

		Runnable task = new Runnable() {

			public void run() {

				try {
					PreparedStatement s = null;
					try {
						s = getConn().prepareStatement("DELETE FROM Duties WHERE `UUID` = ?");
						s.setString(1, name.toString());
						s.execute();
						s.close();

					} catch (SQLException e) {
						System.out.print("[CCDB] SQL Error" + e.getMessage());
					} catch (Exception e) {
						System.out.print("[CCDB] SQL Error (Unknown)");
						e.printStackTrace();
					}
				} catch (Exception ex) {
					// handle error which cannot be thrown back
				}
			}
		};
		new Thread(task, "ServiceThread").start();

	}

	public static void newKey(final String name, final String inv, final String armor, final String location) {

		Runnable task = new Runnable() {

			public void run() {

				try {
					PreparedStatement s = null;
					try {
						s = getConn().prepareStatement("INSERT INTO Duties VALUES (?,?,?,?)");
						s.setString(1, name.toString());
						s.setString(2, inv);
						s.setString(3, armor);
						s.setString(4, location);
						s.execute();
						s.close();

					} catch (SQLException e) {
						System.out.print("[CCDB] SQL Error" + e.getMessage());
					} catch (Exception e) {
						System.out.print("[CCDB] SQL Error (Unknown)");
						e.printStackTrace();
					}
				} catch (Exception ex) {
					// handle error which cannot be thrown back
				}
			}
		};
		new Thread(task, "ServiceThread").start();

	}
	
	public static Connection getConn(){
		return Bedspawn.cntx;
	}
}
