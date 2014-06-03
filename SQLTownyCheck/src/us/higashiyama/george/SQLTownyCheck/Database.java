package us.higashiyama.george.SQLTownyCheck;

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
		public static String db_name = "towny";
		public static String username = "minecraft";
		public static String password = "R3b!rth!ng";
		public static Connection cntx = null;
		public static String dsn = ("jdbc:mysql://" + hostname + ":" + port + "/" + db_name);

		public static void setUp() {
			try {
				Driver driver = (Driver) Class.forName(driverString).newInstance();
				System.out.println(driver);
				DriverManager.registerDriver(driver);
			} catch (Exception e) {
				System.out.println("[CCDBs] Driver error: " + e);
			}
		}
		


		
		public static boolean hasKey(String name) {
			if(!getContext()) System.out.println("Context didn't work sucessfully");
			PreparedStatement s = null;
			try {
				String query = "SELECT `name` FROM acualis_residents WHERE `town` != & UNION SELECT `name` FROM asteroidbelt_residents WHERE `town` != & UNION SELECT `name` FROM boletarian_residents WHERE `town` != & UNION SELECT `name` FROM boskevine_residents WHERE `town` != & UNION SELECT `name` FROM ceharram_residents WHERE `town` != & UNION SELECT `name` FROM defalos_residents WHERE `town` != & UNION SELECT `name` FROM digitalia_residents WHERE `town` != & UNION SELECT `name` FROM drakos_residents WHERE `town` != & UNION SELECT `name` FROM emera_residents WHERE `town` != & UNION SELECT `name` FROM iffrizar_residents WHERE `town` != & UNION SELECT `name` FROM inaris_residents WHERE `town` != & UNION SELECT `name` FROM kelakaria_residents WHERE `town` != & UNION SELECT `name` FROM krystallos_residents WHERE `town` != & UNION SELECT `name` FROM quavara_residents WHERE `town` != & UNION SELECT `name` FROM regalis_residents WHERE `town` != & UNION SELECT `name` FROM valadro_residents WHERE `town` != &";
				query = query.replace("&", "\"\"");
				System.out.println(query);
				s = cntx.prepareStatement(query);
				ResultSet rs = s.executeQuery();
				while(rs.next()) {
					if(rs.getString("name").equalsIgnoreCase(name)) {
						return true;
					}
					
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
