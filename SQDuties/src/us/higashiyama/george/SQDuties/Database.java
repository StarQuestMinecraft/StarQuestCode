package us.higashiyama.george.SQDuties;

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
	public static String driverString = "com.mysql.jdbc.Driver";
	public static String hostname = "192.99.20.8";
	public static String port = "3306";
	public static String db_name = "minecraft";
	public static String username = "minecraft";
	public static String password = "R3b!rth!ng";
	public static Connection cntx = null;
	public static String dsn = ("jdbc:mysql://" + hostname + ":" + port + "/" + db_name);

	public static void setUp() {
		String Database_table = "CREATE TABLE IF NOT EXISTS Duties ("
				+ "`name` VARCHAR(32) NOT NULL," 
				+ "`inventory` LONGTEXT,"
				+ "`armor` LONGTEXT,"
				+ "`location` LONGTEXT,"
				+ "PRIMARY KEY (`name`)" 
				+ ")";
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

	public static boolean hasKey(String name) {
		if (!getContext())
			System.out.println("Context didn't work sucessfully");
		PreparedStatement s = null;
		try {
			s = cntx.prepareStatement("SELECT * FROM Duties WHERE `name` = ?");
			s.setString(1, name);
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
		} finally {
			close(s);
		}

		return false;

	}

	public static String getInv(String name) {
		if (!getContext())
			System.out.println("Context didn't work sucessfully");
		PreparedStatement s = null;
		try {
			s = cntx.prepareStatement("SELECT `inventory` FROM Duties WHERE `name` = ?");
			s.setString(1, name);
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
		} finally {
			close(s);
		}
		return null;

	}
	
	
	public static String getArmor(String name) {
		if (!getContext())
			System.out.println("Context didn't work sucessfully");
		PreparedStatement s = null;
		try {
			s = cntx.prepareStatement("SELECT `armor` FROM Duties WHERE `name` = ?");
			s.setString(1, name);
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
		} finally {
			close(s);
		}
		return null;

	}
	public static String getLocation(String name) {
		if (!getContext())
			System.out.println("Context didn't work sucessfully");
		PreparedStatement s = null;
		try {
			s = cntx.prepareStatement("SELECT `location` FROM Duties WHERE `name` = ?");
			s.setString(1, name);
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
		} finally {
			close(s);
		}
		return null;

	}

	public static void deleteKey(final String name) {
        Runnable task = new Runnable() {

            public void run() {
                try {
            		if (!getContext())
            			System.out.println("Context didn't work sucessfully");
            		PreparedStatement s = null;
            		try {
            			s = cntx.prepareStatement("DELETE FROM Duties WHERE `name` = ?");
            			s.setString(1, name);
            			s.execute();
            			s.close();

            		} catch (SQLException e) {
            			System.out.print("[CCDB] SQL Error" + e.getMessage());
            		} catch (Exception e) {
            			System.out.print("[CCDB] SQL Error (Unknown)");
            			e.printStackTrace();
            		} finally {
            			close(s);
            		}
                } catch (Exception ex) {
                    //handle error which cannot be thrown back
                }
            }
        };
        new Thread(task, "ServiceThread").start(); 


	}

	public static void newKey(final String name, final String inv, final String armor, final String location) {
        Runnable task = new Runnable() {

            public void run() {
                try {
            		if (!getContext())
            			System.out.println("Context didn't work sucessfully");
            		PreparedStatement s = null;
            		try {
            			s = cntx.prepareStatement("INSERT INTO Duties VALUES (?,?,?,?)");
            			s.setString(1, name);
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
            		} finally {
            			close(s);
            		}
                } catch (Exception ex) {
                    //handle error which cannot be thrown back
                }
            }
        };
        new Thread(task, "ServiceThread").start(); 


	}

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
				if ((username.equalsIgnoreCase(""))
						&& (password.equalsIgnoreCase(""))) {
					cntx = DriverManager.getConnection(dsn);
				} else
					cntx = DriverManager.getConnection(dsn, username, password);

				if (cntx == null || cntx.isClosed())
					return false;
			}

			return true;
		} catch (SQLException e) {
			System.out.print("Error could not Connect to db " + dsn + ": "
					+ e.getMessage());
		}
		return false;
	}

	private static void close(Statement s) {
		if (s == null) {
			return;
		}
		try {
			s.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
