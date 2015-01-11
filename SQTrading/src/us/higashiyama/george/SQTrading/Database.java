
package us.higashiyama.george.SQTrading;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import us.higashiyama.george.Currencies.Crate;
import us.higashiyama.george.Currencies.Credits;
import us.higashiyama.george.Currencies.Currency;
import us.higashiyama.george.Currencies.Infamy;
import us.higashiyama.george.Currencies.Perk;

public class Database {

	public static String driverString = "com.mysql.jdbc.Driver";
	public static String hostname = "192.99.20.8";
	public static String port = "3306";
	public static String db_name = "minecraft";
	public static String username = "minecraft";
	public static String password = "R3b!rth!ng";
	public static Connection cntx = null;
	public static String dsn = "jdbc:mysql://" + hostname + ":" + port + "/" + db_name;

	public static void setUp() {

		String Database_table = "CREATE TABLE IF NOT EXISTS RANKUP (`name` VARCHAR(32) NOT NULL,`kills` int(16) DEFAULT 0,`lastkill` VARCHAR(32) DEFAULT NULL,`lastkilltime` bigint(18) DEFAULT 0, `x` INTEGER, `y` INTEGER, `z` INTEGER, `world` VARCHAR(32), PRIMARY KEY (`name`))";
		String Database_perks = "CREATE TABLE IF NOT EXISTS perks (`uuid` VARCHAR(64), `perkname` VARCHAR(32), `perk` BLOB, `redeemed` BOOLEAN)";
		String Database_escrow = "CREATE TABLE IF NOT EXISTS escrow (`id` INT NOT NULL AUTO_INCREMENT PRIMARY KEY, `uuid` VARCHAR(64), `wants` BLOB, `wantsname` LONGTEXT, `has` BLOB, `hasname` LONGTEXT, `from` VARCHAR(64), `time` LONG, `x` INTEGER, `y` INTEGER, `z` INTEGER, `world` VARCHAR(32))";
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
			s.executeUpdate(Database_perks);
			s.executeUpdate(Database_escrow);
			System.out.println("[SQDatabase] Table check/creation sucessful");
		} catch (SQLException ee) {
			System.out.println("[SQDatabase] Table Creation Error");
		} finally {
			close(s);
		}
	}

	public static void showPrivateOffers(Player p) {

		if (!Database.getContext()) {
			System.out.println("something is wrong!");
		}
		PreparedStatement s = null;
		try {

			s = Database.cntx.prepareStatement("SELECT * FROM escrow WHERE `from` LIKE ?");
			s.setString(1, p.getName());
			ResultSet rs = s.executeQuery();

			while (rs.next()) {
				p.sendMessage(ChatColor.AQUA + "   Offer ID: " + rs.getInt("id") + " | " + Bukkit.getOfflinePlayer(UUID.fromString(rs.getString("uuid")))
						+ " wants " + rs.getString("wantsname") + " for " + rs.getString("hasname"));
			}
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

	public static Currency removeOffer(int id, Player p) {

		if (!Database.getContext()) {
			System.out.println("something is wrong!");
		}
		PreparedStatement s = null;
		try {

			s = Database.cntx.prepareStatement("SELECT * FROM escrow WHERE `id` = ? AND `uuid` = ?");
			s.setInt(1, id);
			s.setString(2, p.getUniqueId().toString());
			ResultSet rs = s.executeQuery();

			while (rs.next()) {
				String name = rs.getString("hasname");
				byte[] unparsedPerk = (byte[]) rs.getObject("has");
				ByteArrayInputStream baip = new ByteArrayInputStream(unparsedPerk);
				ObjectInputStream ois = new ObjectInputStream(baip);
				Currency c = null;
				if (name.split(" ").length == 2 && name.split(" ")[1].equals("credits")) {
					c = (Credits) ois.readObject();
				} else if (SQTrading.isPerk(name)) {
					c = (Perk) ois.readObject();
				} else {
					c = (Crate) ois.readObject();
				}
				Database.deleteOffer(id);

				return c;

			}
			s.close();
		} catch (SQLException e) {
			System.out.print("[SQDatabase] SQL Error" + e.getMessage());
		} catch (Exception e) {
			System.out.print("[SQDatabase] SQL Error (Unknown)");
			e.printStackTrace();
		} finally {
			Database.close(s);
		}

		return null;

	}

	public static void cleanUp() {

		Runnable task = new Runnable() {

			public void run() {

				if (!Database.getContext()) {
					System.out.println("something is wrong!");
				}
				PreparedStatement s = null;
				try {

					s = Database.cntx.prepareStatement("DELETE FROM escrow WHERE round(unix_timestamp() * 1000 + MICROSECOND(sysdate(6)) / 1000) > `time`");
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

	public static void deleteOffer(final int id) {

		Runnable task = new Runnable() {

			public void run() {

				if (!Database.getContext()) {
					System.out.println("something is wrong!");
				}
				PreparedStatement s = null;
				try {

					s = Database.cntx.prepareStatement("DELETE FROM escrow WHERE `id` = ?");
					s.setInt(1, id);
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

	public static Currency getOffer(int id, Player p) {

		if (!Database.getContext()) {
			System.out.println("something is wrong!");
		}
		PreparedStatement s = null;
		try {

			s = Database.cntx
					.prepareStatement("SELECT * FROM escrow WHERE `id` = ? AND (`from` LIKE ? || `from` = \"\") AND round(unix_timestamp() * 1000 + MICROSECOND(sysdate(6)) / 1000) < `time`");
			s.setInt(1, id);
			s.setString(2, p.getName());
			ResultSet rs = s.executeQuery();

			while (rs.next()) {
				String name = rs.getString("hasname");
				byte[] unparsedPerk = (byte[]) rs.getObject("has");
				ByteArrayInputStream baip = new ByteArrayInputStream(unparsedPerk);
				ObjectInputStream ois = new ObjectInputStream(baip);
				Currency c = null;

				if (name.split(" ").length == 2 && name.split(" ")[1].equals("credits")) {
					System.out.println("credits");
					c = (Credits) ois.readObject();
				} else if (SQTrading.isPerk(name)) {
					System.out.println("perk");
					c = (Perk) ois.readObject();
				} else if (name.split(" ").length == 2 && name.split(" ")[1].equals("infamy")) {
					c = (Infamy) ois.readObject();
				} else {
					System.out.println("crate");
					c = (Crate) ois.readObject();
				}

				return c;
			}
			s.close();

		} catch (SQLException e) {
			System.out.print("[SQDatabase] SQL Error" + e.getMessage());
		} catch (Exception e) {
			System.out.print("[SQDatabase] SQL Error (Unknown)");
			e.printStackTrace();
		} finally {
			Database.close(s);
		}
		return null;
	}

	public static void openOffer(final Player opener, final Player from, final Currency wants, final Currency has, final long ms) {

		Runnable task = new Runnable() {

			public void run() {

				if (!Database.getContext()) {
					System.out.println("something is wrong!");
				}
				PreparedStatement s = null;
				try {

					ByteArrayOutputStream baos1 = new ByteArrayOutputStream();
					ObjectOutputStream oos1 = new ObjectOutputStream(baos1);
					oos1.writeObject(wants);
					byte[] wantBytes = baos1.toByteArray();
					ByteArrayInputStream bais1 = new ByteArrayInputStream(wantBytes);

					ByteArrayOutputStream baos2 = new ByteArrayOutputStream();
					ObjectOutputStream oos = new ObjectOutputStream(baos2);
					oos.writeObject(has);
					byte[] hasBytes = baos2.toByteArray();
					ByteArrayInputStream bais2 = new ByteArrayInputStream(hasBytes);

					s = Database.cntx
							.prepareStatement("INSERT INTO escrow (`uuid`, `wantsname`, `hasname`, `wants`, `has`, `from`, `time`) VALUES (?,?,?,?,?,?,?)");
					s.setString(1, opener.getUniqueId().toString());
					s.setString(2, wants.getAlias());
					s.setString(3, has.getAlias());
					s.setBinaryStream(4, bais1, wantBytes.length);
					s.setBinaryStream(5, bais2, hasBytes.length);
					if (from == null) {
						s.setString(6, "");
					} else {
						s.setString(6, from.getUniqueId().toString());
					}
					s.setLong(7, System.currentTimeMillis() + ms);
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

	public static List<Perk> getUnredeemedPlayerPerks(Player player) {

		List<Perk> perks = new ArrayList<Perk>();
		if (!Database.getContext()) {
			System.out.println("something is wrong!");
		}
		PreparedStatement s = null;
		try {
			s = Database.cntx.prepareStatement("SELECT * FROM perks WHERE `uuid` = ? AND `redeemed` = FALSE");
			s.setString(1, player.getUniqueId().toString());
			ResultSet rs = s.executeQuery();

			while (rs.next()) {
				byte[] unparsedPerk = (byte[]) rs.getObject("perk");
				ByteArrayInputStream baip = new ByteArrayInputStream(unparsedPerk);
				ObjectInputStream ois = new ObjectInputStream(baip);
				Perk p = (Perk) ois.readObject();
				perks.add(p);
			}
			s.close();
			return perks;
		} catch (SQLException e) {
			System.out.print("[SQDatabase] SQL Error" + e.getMessage());
		} catch (Exception e) {
			System.out.print("[SQDatabase] SQL Error (Unknown)");
			e.printStackTrace();
		} finally {
			Database.close(s);
		}
		return null;

	}

	public static List<Perk> getRedeemedPlayerPerks(Player player) {

		List<Perk> perks = new ArrayList<Perk>();
		if (!Database.getContext()) {
			System.out.println("something is wrong!");
		}
		PreparedStatement s = null;
		try {
			s = Database.cntx.prepareStatement("SELECT * FROM perks WHERE `uuid` = ? AND `redeemed` = TRUE");
			s.setString(1, player.getUniqueId().toString());
			ResultSet rs = s.executeQuery();

			while (rs.next()) {
				byte[] unparsedPerk = (byte[]) rs.getObject("perk");
				ByteArrayInputStream baip = new ByteArrayInputStream(unparsedPerk);
				ObjectInputStream ois = new ObjectInputStream(baip);
				Perk p = (Perk) ois.readObject();
				perks.add(p);
			}
			s.close();
			return perks;
		} catch (SQLException e) {
			System.out.print("[SQDatabase] SQL Error" + e.getMessage());
		} catch (Exception e) {
			System.out.print("[SQDatabase] SQL Error (Unknown)");
			e.printStackTrace();
		} finally {
			Database.close(s);
		}
		return null;

	}

	public static void addPerk(final Player player, final Currency perk, final boolean status) {

		Runnable task = new Runnable() {

			public void run() {

				if (!Database.getContext()) {
					System.out.println("something is wrong!");
				}
				PreparedStatement s = null;
				try {

					ByteArrayOutputStream baos = new ByteArrayOutputStream();
					ObjectOutputStream oos = new ObjectOutputStream(baos);
					oos.writeObject(perk);
					byte[] perkBytes = baos.toByteArray();
					ByteArrayInputStream bais = new ByteArrayInputStream(perkBytes);

					s = Database.cntx.prepareStatement("INSERT INTO perks (`uuid`, `perkname`, `perk`, `redeemed`) VALUES (?,?,?,?)");
					s.setString(1, player.getUniqueId().toString());
					s.setString(2, perk.getAlias());
					s.setBinaryStream(3, bais, perkBytes.length);
					s.setBoolean(4, status);
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

	public static void removePerk(Player player, Perk perk) {

		if (!Database.getContext()) {
			System.out.println("something is wrong!");
		}
		PreparedStatement s = null;
		try {

			s = Database.cntx.prepareStatement("DELETE FROM perks WHERE `uuid` = ? AND `perkname` = ? AND `redeemed` = 0 LIMIT 1");
			s.setString(1, player.getUniqueId().toString());
			s.setString(2, perk.getAlias());
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
