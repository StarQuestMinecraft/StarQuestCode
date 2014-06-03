
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

	public static int towns(String name) {

		if (!getContext())
			System.out.println("Context didn't work sucessfully");
		PreparedStatement s = null;
		int towns = 0;
		try {
			String query = "SELECT `name`AS player, COUNT(*) as totalTowns FROM( " + "SELECT`name`, `town`  FROM acualis_residents WHERE `town` != & UNION "
					+ "SELECT`name`, `town`  FROM asteroidbelt_residents WHERE `town` != & UNION "
					+ "SELECT`name`, `town`  FROM boletarian_residents WHERE `town` != & UNION "
					+ "SELECT`name`, `town`  FROM boskevine_residents WHERE `town` != & UNION "
					+ "SELECT`name`, `town`  FROM ceharram_residents WHERE `town` != & UNION "
					+ "SELECT`name`, `town`  FROM defalos_residents WHERE `town` != & UNION "
					+ "SELECT`name`, `town`  FROM digitalia_residents WHERE `town` != & UNION "
					+ "SELECT`name`, `town`  FROM drakos_residents WHERE `town` != & UNION "
					+ "SELECT`name`, `town`  FROM emera_residents WHERE `town` != & UNION "
					+ "SELECT`name`, `town`  FROM iffrizar_residents WHERE `town` != & UNION "
					+ "SELECT`name`, `town`  FROM inaris_residents WHERE `town` != & UNION "
					+ "SELECT`name`, `town`  FROM kelakaria_residents WHERE `town` != & UNION "
					+ "SELECT`name`, `town`  FROM krystallos_residents WHERE `town` != & UNION "
					+ "SELECT`name`, `town`  FROM quavara_residents WHERE `town` != & UNION "
					+ "SELECT`name`, `town`  FROM regalis_residents WHERE `town` != & UNION "
					+ "SELECT`name`, `town`  FROM valadro_residents WHERE `town` != &" + ") AS query WHERE `name` = ?";
			query = query.replace("&", "\"\"");
			System.out.println(query);
			s = cntx.prepareStatement(query);
			s.setString(1, name);
			ResultSet rs = s.executeQuery();
			while (rs.next()) {
				towns = rs.getInt("totalTowns");

			}
			s.close();
			return towns;

		} catch (SQLException e) {
			System.out.print("[CCDB] SQL Error" + e.getMessage());
		} catch (Exception e) {
			System.out.print("[CCDB] SQL Error (Unknown)");
			e.printStackTrace();
		} finally {
			close(s);
		}

		return towns;

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
