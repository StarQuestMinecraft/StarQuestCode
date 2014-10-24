
package us.higashiyama.george.SQTrading;

import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.UUID;

import org.bukkit.entity.Player;

import us.higashiyama.george.SQTrading.Utils.TransactionType;

public class Database {

	public String player;
	public String server;
	public String world;
	public int x, y, z;
	public static String driverString = "com.mysql.jdbc.Driver";
	public static String hostname = "localhost";
	public static String port = "3306";
	public static String db_name = "minecraft";
	public static String username = "root";
	public static String password = "masumoto";
	public static Connection cntx = null;
	public static String dsn = ("jdbc:mysql://" + hostname + ":" + port + "/" + db_name);

	public static void setUp() {

		//@formatting:off
		String TransactionTable = "CREATE TABLE IF NOT EXISTS SQTrading_Transactions"
				+ " ("
				+ "`id` MEDIUMINT(8) UNSIGNED NOT NULL AUTO_INCREMENT,"
				+ " `TradingStation` VARCHAR(32),"
				+ " `Material` VARCHAR(32),"
				+ " `Quantity` INT,"
				+ " `Data` INT,"
				+ " `UUID` VARCHAR(64),"
				+ " PRIMARY KEY (`id`)"
				+ ")";
		
		String OffersTable = "CREATE TABLE IF NOT EXISTS SQTrading_Offers"
				+ " ("
				+ "`id` MEDIUMINT(8) UNSIGNED NOT NULL AUTO_INCREMENT,"
				+ " `TradingStation` VARCHAR(32),"
				+ " `Type` VARCHAR(32),"
				+ " `UUID` VARCHAR(64),"
				+ " `Material` VARCHAR(32),"
				+ " `Quantity` INT,"
				+ " `Data` INT,"
				+ " `Price` DOUBLE,"
				+ " PRIMARY KEY (`id`)"
				+ ")";
		
		String TradingStations = "CREATE TABLE IF NOT EXISTS SQTrading_TradingStations"
				+ " ("
				+ " `Name` VARCHAR(32),"
				+ " `X` INT,"
				+ " `Y` INT,"
				+ " `Z` INT,"
				+ " `World` VARCHAR(32),"
				+ " `Server` VARCHAR(32)"
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
			s.executeUpdate(TransactionTable);
			s.executeUpdate(OffersTable);
			s.executeUpdate(TradingStations);
			System.out.println("[SQDatabase] Table check/creation sucessful");
		} catch (SQLException ee) {
			System.out.println("[SQDatabase] Table Creation Error");
		} finally {
			close(s);
		}

	}

	// @formatting:on
	public static void finishOffer(final TradingOffer to) {

		Runnable task = new Runnable() {

			public void run() {

				try {
					if (!getContext())
						System.out.println("Context didn't work sucessfully");
					PreparedStatement s = null;
					try {
						s = cntx.prepareStatement("DELETE FROM SQTrading_Offers WHERE `id` = ?");
						s.setInt(1, to.getId());
						s.execute();
						s.close();

					} catch (SQLException e) {
						System.out.print("[CCDB] ADD SQL Error " + e.getMessage());
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

	public static TradingStation getTradingStationByName(String name) {

		if (!getContext())
			System.out.println("Context didn't work sucessfully");
		PreparedStatement s = null;
		try {
			TradingStation ts = null;
			s = cntx.prepareStatement("SELECT * FROM SQTrading_TradingStations WHERE `name` = ?");
			s.setString(1, name);
			ResultSet rs = s.executeQuery();
			while (rs.next()) {
				ts = new TradingStation(rs.getString("name"), rs.getInt("x"), rs.getInt("y"), rs.getInt("z"), rs.getString("world"), rs.getString("server"));
			}
			s.close();
			return ts;

		} catch (SQLException e) {
			System.out.print("[CCDB] SQL Error GET" + e.getMessage());
		} catch (Exception e) {
			System.out.print("[CCDB] SQL Error (Unknown)");
			e.printStackTrace();
		} finally {
			close(s);
		}

		return null;

	}

	public static TradingStation getTradingStation(int blockX, int blockY, int blockZ, String name, String name2) {

		if (!getContext())
			System.out.println("Context didn't work sucessfully");
		PreparedStatement s = null;
		try {
			TradingStation ts = null;
			s = cntx.prepareStatement("SELECT * FROM SQTrading_TradingStations WHERE `x` = ? AND `y` = ? AND `z` = ? AND `world` = ? AND `server` = ?");
			s.setInt(1, blockX);
			s.setInt(2, blockY);
			s.setInt(3, blockZ);
			s.setString(4, name.toString());
			s.setString(5, name2.toString());
			ResultSet rs = s.executeQuery();
			while (rs.next()) {
				ts = new TradingStation(rs.getString("name"), rs.getInt("x"), rs.getInt("y"), rs.getInt("z"), rs.getString("world"), rs.getString("server"));
			}
			s.close();
			return ts;

		} catch (SQLException e) {
			System.out.print("[CCDB] SQL Error GET" + e.getMessage());
		} catch (Exception e) {
			System.out.print("[CCDB] SQL Error (Unknown)");
			e.printStackTrace();
		} finally {
			close(s);
		}

		return null;

	}

	public static void addTradingStation(final TradingStation station) {

		Runnable task = new Runnable() {

			public void run() {

				try {
					if (!getContext())
						System.out.println("Context didn't work sucessfully");
					PreparedStatement s = null;
					try {
						s = cntx.prepareStatement("INSERT INTO SQTrading_TradingStations VALUES (?,?,?,?,?,?)");
						s.setString(1, station.getName());
						s.setInt(2, station.getX());
						s.setInt(3, station.getY());
						s.setInt(4, station.getZ());
						s.setString(5, station.getWorld());
						s.setString(6, station.getServer());
						s.execute();
						s.close();

					} catch (SQLException e) {
						System.out.print("[CCDB] ADD SQL Error " + e.getMessage());
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

	public static void addCompletedTransaction(final CompletedTransaction ct) {

		Runnable task = new Runnable() {

			public void run() {

				try {
					if (!getContext())
						System.out.println("Context didn't work sucessfully");
					PreparedStatement s = null;
					try {

						s = cntx.prepareStatement("INSERT INTO SQTrading_transactions (TradingStation, Material, Quantity, Data, UUID) VALUES (?,?,?,?,?)");
						s.setString(1, ct.getTradeStation());
						s.setString(2, ct.getMaterialName());
						s.setInt(3, ct.getAmount());
						s.setInt(4, ct.getData());
						s.setString(5, ct.getPlayerUUID().toString());
						s.execute();
						s.close();

					} catch (SQLException e) {
						System.out.print("[CCDB] SQL Error          " + e.getMessage());
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

	public static void addOffer(final String string, final TransactionType sell, final UUID uniqueId, final String string2, final int i, final short sh,
			final double j) {

		Runnable task = new Runnable() {

			public void run() {

				try {
					if (!getContext())
						System.out.println("Context didn't work sucessfully");
					PreparedStatement s = null;
					try {

						s = cntx.prepareStatement("INSERT INTO SQTrading_Offers (TradingStation, Type, UUID, Material, Quantity, Data, Price) VALUES (?,?,?,?,?,?,?)");
						s.setString(1, string);
						s.setString(2, sell.toString());
						s.setString(3, uniqueId.toString());
						s.setString(4, string2);
						s.setInt(5, i);
						s.setInt(6, (int) sh);
						s.setDouble(7, j);
						s.execute();
						s.close();

					} catch (SQLException e) {
						System.out.print("[CCDB] SQL Error          " + e.getMessage());
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

	public static TradingOffer getSellOffer(TradingStation ts, int id) {

		if (!getContext())
			System.out.println("Context didn't work sucessfully");
		PreparedStatement s = null;
		try {
			TradingOffer offer = null;
			s = cntx.prepareStatement("SELECT * FROM SQTrading_Offers WHERE `TradingStation` = ?");
			s.setString(1, ts.getName());
			ResultSet rs = s.executeQuery();
			while (rs.next()) {
				TransactionType tt;
				if (rs.getString("type").equalsIgnoreCase("BUY")) {
					tt = TransactionType.BUY;

				} else {
					continue;
				}

				offer = new TradingOffer(rs.getString("TradingStation"), tt, UUID.fromString(rs.getString("UUID")), rs.getString("material"),
						rs.getInt("quantity"), (short) rs.getInt("data"), rs.getDouble("price"), id);
			}
			s.close();
			return offer;

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

	public static void clearTransaction(final CompletedTransaction ct) {

		Runnable task = new Runnable() {

			public void run() {

				try {
					if (!getContext())
						System.out.println("Context didn't work sucessfully");
					PreparedStatement s = null;
					try {
						s = cntx.prepareStatement("DELETE FROM SQTrading_Transactions WHERE `id` = ?");
						s.setInt(1, ct.getId());
						s.execute();
						s.close();

					} catch (SQLException e) {
						System.out.print("[CCDB] ADD SQL Error " + e.getMessage());
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

	public static CompletedTransaction getRandomTransaction(Player p, TradingStation ts) {

		if (!getContext())
			System.out.println("Context didn't work sucessfully");
		PreparedStatement s = null;
		try {
			CompletedTransaction ct = null;
			s = cntx.prepareStatement("SELECT * FROM SQTrading_Transactions WHERE `uuid` = ?");
			s.setString(1, p.getUniqueId().toString());
			ResultSet rs = s.executeQuery();
			while (rs.next()) {
				ct = new CompletedTransaction(p.getUniqueId(), rs.getString("material"), (short) rs.getInt("data"), rs.getInt("Quantity"),
						rs.getString("TradingStation"), rs.getInt("id"));
			}
			s.close();
			return ct;

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

	public static boolean tradingStationExists(String uncheckedID) {

		if (!getContext())
			System.out.println("Context didn't work sucessfully");
		PreparedStatement s = null;
		try {
			s = cntx.prepareStatement("SELECT * FROM SQTrading_TradingStations WHERE `Name` = ?");
			s.setString(1, uncheckedID);
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

	public static void progressTransaction(final CompletedTransaction ct) {

		Runnable task = new Runnable() {

			public void run() {

				try {
					if (!getContext())
						System.out.println("Context didn't work sucessfully");
					PreparedStatement s = null;
					try {

						s = cntx.prepareStatement("UPDATE SQTrading_Transactions SET `quantity` = ? WHERE `id` = ?");
						s.setInt(1, ct.getAmount());
						s.setDouble(2, ct.getId());
						s.execute();
						s.close();

					} catch (SQLException e) {
						System.out.print("[CCDB] SQL Error          " + e.getMessage());
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

	public static void progressOffer(final TradingOffer to, final int modQuantity) {

		Runnable task = new Runnable() {

			public void run() {

				try {
					if (!getContext())
						System.out.println("Context didn't work sucessfully");
					PreparedStatement s = null;
					try {

						s = cntx.prepareStatement("UPDATE SQTrading_Offers SET `quantity` = ?, `price` = ? WHERE `id` = ?");
						s.setInt(1, modQuantity);
						s.setDouble(2, to.getPrice());
						s.setInt(3, to.getId());
						s.execute();
						s.close();

					} catch (SQLException e) {
						System.out.print("[CCDB] SQL Error          " + e.getMessage());
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

}
