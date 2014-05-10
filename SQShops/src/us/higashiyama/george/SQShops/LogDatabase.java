package us.higashiyama.george.SQShops;

import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

public class LogDatabase {
	public static String driverString = "com.mysql.jdbc.Driver";
	public static String hostname = "192.99.20.8";
	public static String port = "3306";
	public static String db_name = "minecraft";
	public static String username = "minecraft";
	public static String password = "R3b!rth!ng";
	public static Connection cntx = null;
	public static String dsn = ("jdbc:mysql://" + hostname + ":" + port + "/" + db_name);

	public static void setUp(){
		
		String Database_table = "CREATE TABLE IF NOT EXISTS Economy_Log (" 
				+ "`name` VARCHAR(32),"
				+ "`material` VARCHAR(32),"
				+ "`quantity` DOUBLE,"
				+ "`profit` DOUBLE,"
				+ "`time` DATETIME" 
				+ ")";

		getContext();
		try {
			Driver driver = (Driver) Class.forName(driverString).newInstance();
			DriverManager.registerDriver(driver);
		} catch (Exception e) {
			System.out.println("[CCDBs] Driver error: " + e);
		}
		Statement s = null;
		try {
			s = cntx.createStatement();
			s.executeUpdate(Database_table);
			System.out.println("[CCDB] Table check/creation sucessful");
		} catch (SQLException ee) {
			System.out.println("[CCDB] Table Creation Error");
		} finally {
			close(s);
		}
	}
	
	



	public static void addPurchase(final ItemStack stack, final double quantity, final double profit, final String player){
        Runnable task = new Runnable() {

            public void run() {
            	
                try {                	
            		if (!getContext())
            			System.out.println("Context didn't work sucessfully");
            		PreparedStatement s = null;
            		try {
            			s = cntx.prepareStatement("INSERT INTO Economy_Log VALUES (?,?,?,?,NOW())");
                			s.setString(1, player);
                			s.setString(2, stack.getType().toString());
                			s.setDouble(3, quantity);
                			s.setDouble(4, profit);
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
                	System.out.println("Thread Exception: " + ex);
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
				if ((username.equalsIgnoreCase("")) && (password.equalsIgnoreCase(""))) {
					cntx = DriverManager.getConnection(dsn);
				} else
					cntx = DriverManager.getConnection(dsn, username, password);

				if (cntx == null || cntx.isClosed())
					return false;
			}

			return true;
		} catch (SQLException e) {
			System.out.print("Error could not Connect to db " + dsn + ": " + e.getMessage());
		}
		return false;
	}
	
	
	  private static void close(Statement s)
	  {
	    if (s == null) {
	      return;
	    }
	    try
	    {
	      s.close();
	    }
	    catch (Exception e)
	    {
	      e.printStackTrace();
	    }
	  }
}
