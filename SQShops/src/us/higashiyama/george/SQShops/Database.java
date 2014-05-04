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

public class Database {
	public static String driverString = "com.mysql.jdbc.Driver";
	public static String hostname = "localhost";
	public static String port = "3306";
	public static String db_name = "minecraft";
	public static String username = "minecraft";
	public static String password = "R3b!rth!ng";
	public static Connection cntx = null;
	public static String dsn = ("jdbc:mysql://" + hostname + ":" + port + "/" + db_name);

	public static void setUp(){
		
		String Database_table = "CREATE TABLE IF NOT EXISTS Economy_Prices (" 
				+ "`Material` VARCHAR(32),"
				+ "`Price` DOUBLE(16),"
				+ "`totalMoney` DOUBLE(32) DEFAULT 0," 
				+ "`totalQuantity` DOUBLE(32) DEFAULT 0,"
				+ "PRIMARY KEY (`Material`)" 
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
	
	
	public static HashMap<ItemStack, Double> loadData(){
		HashMap<ItemStack, Double> itemIndex = new HashMap<ItemStack, Double>();
		if(!getContext()) System.out.println("something is wrong!");
		PreparedStatement s = null;
		try {
			 s = cntx.prepareStatement("SELECT (`Material`,`Price`) FROM Economy_Prices");
			 ResultSet rs = s.executeQuery();
			while(rs.next()) {
				String unparsedName = rs.getString("Material");
				String[] materialArray = unparsedName.split(":");
				itemIndex.put(new ItemStack(Material.getMaterial(materialArray[0]), 1, Short.parseShort(materialArray[1])), rs.getDouble("Price"));	
			}
			s.close();
			return itemIndex;
		} catch (SQLException e) {
			System.out.print("[CCDB] SQL Error" + e.getMessage());
		} catch (Exception e) {
			System.out.print("[CCDB] SQL Error (Unknown)");
			e.printStackTrace();
		} finally {
			close(s);
		}
		return itemIndex;
	}
	

	public static void updateStats(final ItemStack is, final double quantity){
        Runnable task = new Runnable() {

            public void run() {
            	
                try {
                	double oldTotalMoney = 0;
                	double oldTotalQuantity = 0;
                	double initialPrice = SQShops.itemIndex.get(is);
                	
                	//First we have to get the old prices in the DB
            		if (!getContext())
            			System.out.println("Context didn't work sucessfully");
            		PreparedStatement s = null;
            		try {
            			s = cntx.prepareStatement("SELECT (`totalMoney`,`totalQuantity`) FROM Economy_Prices WHERE `Material` = ?");
                				s.setString(1, is.getType().toString() + ":" + is.getData().getData());

            			ResultSet rs = s.executeQuery();
            				while(rs.next()) {
            					oldTotalMoney = rs.getDouble("totalMoney");
            					oldTotalQuantity = rs.getDouble("totalQuantity");
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
            		//Next we can use the old DB values to update with the new ones
            		if (!getContext())
            			System.out.println("Context didn't work sucessfully");
            		PreparedStatement ps = null;
            		try {
            			ps = cntx.prepareStatement("UPDATE Economy_Prices SET `totalMoney` = ?, `totalQuantity` = ?");
            			ps.setDouble(1, (oldTotalMoney + (quantity * initialPrice)));
            			ps.setDouble(2, (oldTotalQuantity + quantity));
            			ps.execute();
            			ps.close();
            		} catch (SQLException e) {
            			System.out.print("[CCDB] SQL Error" + e.getMessage());
            		} catch (Exception e) {
            			System.out.print("[CCDB] SQL Error (Unknown)");
            			e.printStackTrace();
            		} finally {
            			close(ps);
            		}
                } catch (Exception ex) {
                	System.out.println("Thread Exception: " + ex);
                }
                
                
            }
        };
        new Thread(task, "ServiceThread").start(); 
	}
	
	public static void updateMaterial(final ItemStack is, final double price){
        Runnable task = new Runnable() {

            public void run() {
            	
                try {
                	SQShops.itemIndex.remove(is);
                	SQShops.itemIndex.put(is, price);
            		if (!getContext())
            			System.out.println("Context didn't work sucessfully");
            		PreparedStatement s = null;
            		try {
            			s = cntx.prepareStatement("UPDATE Economy_Prices SET `Price` = ? WHERE `Material` = ?");
            			s.setDouble(1, price);
            			s.setString(2, is.getType().toString() + ":" + is.getData().getData());
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
