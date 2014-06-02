
package us.higashiyama.george.SQDuties;

import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.sql.DataSource;

import org.apache.commons.dbcp2.ConnectionFactory;
import org.apache.commons.dbcp2.DriverManagerConnectionFactory;
import org.apache.commons.dbcp2.PoolableConnection;
import org.apache.commons.dbcp2.PoolableConnectionFactory;
import org.apache.commons.dbcp2.PoolingDataSource;
import org.apache.commons.pool2.ObjectPool;
import org.apache.commons.pool2.impl.GenericObjectPool;

public class Database {

	public static DataSource dataSource;
	private static String dbName = "minecraft"

	public static String driverString = "com.mysql.jdbc.Driver";
	public static String hostname = "192.99.20.8";
	public static String port = "3306";
	public static String db_name = "minecraft";
	public static String username = "minecraft";
	public static String password = "R3b!rth!ng";
	public static Connection cntx = null;
	public static String dsn = ("jdbc:mysql://" + hostname + ":" + port + "/" + db_name);

	public static void setUp() {

		String Database_table = "CREATE TABLE IF NOT EXISTS Duties (" + "`name` VARCHAR(32) NOT NULL," + "`inventory` LONGTEXT," + "`armor` LONGTEXT,"
				+ "`location` LONGTEXT," + "PRIMARY KEY (`name`)" + ")";

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
					// handle error which cannot be thrown back
				}
			}
		};
		new Thread(task, "ServiceThread").start();

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

	private static void loadSources() {

		try {

			// Registering the driver
			Class.forName("com.mysql.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		// Getting the data source (Poolable in this case)
		dataSource = setupDataSource("jdbc:mysql://192.99.20.8:3306/" + dbName);
	}

	public static DataSource setupDataSource(String connectURI) {

		ConnectionFactory connectionFactory = new DriverManagerConnectionFactory(connectURI, "minecraft", "R3b!rth!ng");
		PoolableConnectionFactory poolableConnectionFactory = new PoolableConnectionFactory(connectionFactory, null);
		ObjectPool<PoolableConnection> connectionPool = new GenericObjectPool<>(poolableConnectionFactory);
		PoolingDataSource<PoolableConnection> dataSource = new PoolingDataSource<>(connectionPool);
		return dataSource;
	}

}
