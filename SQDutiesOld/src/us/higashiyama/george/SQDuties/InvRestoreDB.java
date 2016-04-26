package us.higashiyama.george.SQDuties;

import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import net.countercraft.movecraft.bedspawns.Bedspawn;

public class InvRestoreDB {
	public static String driverString = "com.mysql.jdbc.Driver";
	public static String hostname = "192.99.20.8";
	public static String port = "3306";
	public static String db_name = "minecraft";
	public static String username = "minecraft";
	public static String password = "R3b!rth!ng";
	public static String dsn = ("jdbc:mysql://" + hostname + ":" + port + "/" + db_name);

	public static void setUp() {
		String Database_table = "CREATE TABLE IF NOT EXISTS InventoryRestore ("
				+ "`name` VARCHAR(32) NOT NULL," + "`inventory` LONGTEXT,"
				+ "`armor` LONGTEXT," 
				+ "`time` DATETIME,"
				+ "`cause` LONGTEXT"
				+ ")";
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

	public static String getInv(String name, String datetime) {
		PreparedStatement s = null;
		try {
			s = getConn().prepareStatement("SELECT `inventory` FROM InventoryRestore WHERE `name` = ? && `time` = ?");
			s.setString(1, name);
			s.setString(2, datetime);
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

	public static String getArmor(String name, String datetime) {
		PreparedStatement s = null;
		try {
			s = getConn().prepareStatement("SELECT `armor` FROM InventoryRestore WHERE `name` = ? && `time` = ?");
			s.setString(1, name);
			s.setString(2, datetime);
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
	
	public static void trimRow(final String datetime) {
        Runnable task = new Runnable() {

            public void run() {
                try {
            		PreparedStatement s = null;
            		try {
            			s = getConn().prepareStatement("DELETE FROM InventoryRestore WHERE `time` = ?");
            			s.setString(1, datetime);
            			s.execute();
            			s.close();

            		} catch (SQLException e) {
            			System.out.print("[CCDB] SQL Error" + e.getMessage());
            		} catch (Exception e) {
            			System.out.print("[CCDB] SQL Error (Unknown)");
            			e.printStackTrace();
            		}
                } catch (Exception ex) {
                    //handle error which cannot be thrown back
                }
            }
        };
        new Thread(task, "ServiceThread").start(); 



	}
	
	public static ArrayList<String> getDeaths(String name) {
		System.out.println("GETTING DEATHS");
		ArrayList<String> returnArray = new ArrayList<String>();
		PreparedStatement s = null;
		try {
			System.out.println("TRYING");
			s = getConn().prepareStatement("SELECT * FROM InventoryRestore WHERE `name` = ? ORDER BY InventoryRestore.time DESC");
			s.setString(1, name);
			ResultSet rs = s.executeQuery();
			while (rs.next()) {
				System.out.println("IM THE NEXT");
				String entry = "Name: " + rs.getString("name") + " Time: " + rs.getString("time") + " Cause: " + rs.getString("cause");
				returnArray.add(entry);
			}
			s.close();
			return returnArray;

		} catch (SQLException e) {
			System.out.print("[CCDB] SQL Error" + e.getMessage());
		} catch (Exception e) {
			System.out.print("[CCDB] SQL Error (Unknown)");
			e.printStackTrace();
		}
		System.out.println("RETUNING NULL");
		return null;

	}
	
	public static String getDateIndex(String name, int index) {
		int counter = 1;
		PreparedStatement s = null;
		try {
			s = getConn().prepareStatement("SELECT `time` FROM InventoryRestore WHERE `name` = ? ORDER BY InventoryRestore.time DESC");
			s.setString(1, name);
			ResultSet rs = s.executeQuery();
			while (rs.next()) {
				if (counter == index) {
					System.out.println(counter + ": RETURNING");
					return rs.getString("time");
				} else {
					System.out.println("Incrementing: " + counter);
					counter++;
				}
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

	public static void newKey(final String name, final String inv, final String armor, final String cause) {
        Runnable task = new Runnable() {

            public void run() {
                try {
            		PreparedStatement s = null;
            		try {
            			s = getConn().prepareStatement("INSERT INTO InventoryRestore VALUES (?,?,?,NOW(),?)");
            			s.setString(1, name);
            			s.setString(2, inv);
            			s.setString(3, armor);
            			s.setString(4, cause);
            			s.execute();
            			s.close();

            		} catch (SQLException e) {
            			System.out.print("[CCDB] SQL Error" + e.getMessage());
            		} catch (Exception e) {
            			System.out.print("[CCDB] SQL Error (Unknown)");
            			e.printStackTrace();
            		} finally {
            			trimKey(name);
            		}
                } catch (Exception ex) {
                    //handle error which cannot be thrown back
                }
            }
        };
        new Thread(task, "ServiceThread").start(); 


	}

	public static void trimKey(final String name) {
		  Runnable task = new Runnable() {
	            public void run() {
	                try {
	                	System.out.println("Trimming");
	            		int counter = 1;
	            		PreparedStatement s = null;
	            		try {
	            			s = getConn().prepareStatement("SELECT * FROM InventoryRestore WHERE `name` = ? ORDER BY InventoryRestore.time DESC");
	            			s.setString(1, name);
	            			ResultSet rs = s.executeQuery();
	            			while (rs.next()) {
	            				if (counter <= 5) {
	            					System.out.println(counter);
	            				} else {
	            					System.out.println("deleting row");
	            					trimRow(rs.getString("time"));
	            					
	            				}
	            				counter++;
	            			}
	            			s.close();

	            		} catch (SQLException e) {
	            			System.out.print("[CCDB] SQL Error" + e.getMessage());
	            		} catch (Exception e) {
	            			System.out.print("[CCDB] SQL Error (Unknown)");
	            			e.printStackTrace();
	            		}
	                } catch (Exception ex) {
	                    //handle error which cannot be thrown back
	                }
	            }
	        };
	        new Thread(task, "ServiceThread").start(); 
		
	}
	public static Connection getConn(){
		return Bedspawn.cntx;
	}
}
