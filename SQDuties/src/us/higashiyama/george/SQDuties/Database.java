
package us.higashiyama.george.SQDuties;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.bukkit.entity.Player;

import us.higashiyama.george.CardboardBox.Knapsack;
import us.higashiyama.george.SQDatabase.SQDatabase;

public class Database {

	public static void savePlayer(Player p, Knapsack k) {

		Runnable task = () -> {
			Connection cntx = SQDatabase.getConnection("SQDuties");
			PreparedStatement s = null;
			try {
				s = cntx.prepareStatement("INSERT INTO minecraft.duties (`uuid`, `data`, `location`) VALUES (?,?,?)");
				s.setString(1, p.getUniqueId().toString());
				byte[] knapsackBytes = convertToBytes(k);
				s.setBinaryStream(2, convertToBinary(knapsackBytes), knapsackBytes.length);
				s.setString(3, playerLocationToString(p));
				s.execute();
			} catch (SQLException ee) {
				System.out.println("[SQDatabase] Table Insertion Error");
				System.out.println(ee);
			} finally {
				close(s);
			}
		};

		SQDatabase.executeAsyncQuery(task);

	}

	public static void deletePlayer(Player p) {

		Runnable task = () -> {
			Connection cntx = SQDatabase.getConnection("SQDuties");
			PreparedStatement s = null;
			try {
				s = cntx.prepareStatement("DELETE FROM minecraft.duties WHERE `uuid` = ?");
				s.setString(1, p.getUniqueId().toString());
				s.execute();
			} catch (SQLException ee) {
				System.out.println("[SQDatabase] Table Deletion Error");
			} finally {
				close(s);
			}
		};

		SQDatabase.executeAsyncQuery(task);

	}

	public static void loadData(Player p) {

		Runnable task = () -> {
			Connection cntx = SQDatabase.getConnection("SQDuties");
			PreparedStatement s = null;
			try {
				s = cntx.prepareStatement("SELECT * FROM minecraft.duties WHERE `uuid` = ?");
				s.setString(1, p.getUniqueId().toString());
				ResultSet rs = s.executeQuery();
				while (rs.next()) {
					SQDuties.instance.sendPlayerToLocation(p, rs.getString("location"));
					break;
				}
			} catch (SQLException ee) {
				System.out.println("[SQDatabase] Table Loading Error");
			} finally {
				close(s);
			}
		};

		SQDatabase.executeAsyncQuery(task);

	}

	public static void loadInventory(Player p) {

		Runnable task = () -> {
			Connection cntx = SQDatabase.getConnection("SQDuties");
			PreparedStatement s = null;
			try {
				s = cntx.prepareStatement("SELECT * FROM minecraft.duties WHERE `uuid` = ?");
				s.setString(1, p.getUniqueId().toString());
				ResultSet rs = s.executeQuery();
				while (rs.next()) {
					byte[] unparsedPerk = (byte[]) rs.getObject("data");
					ByteArrayInputStream baip = new ByteArrayInputStream(unparsedPerk);
					ObjectInputStream ois = new ObjectInputStream(baip);
					Knapsack k = (Knapsack) ois.readObject();
					SQDuties.instance.restoreInventory(p, k);
					break;
				}
			} catch (SQLException ee) {
				System.out.println("[SQDatabase] Table Select Error");
				System.out.println(ee);
			} catch (IOException e) {
				e.printStackTrace();
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			} finally {
				close(s);
				deletePlayer(p);
			}
		};

		SQDatabase.executeAsyncQuery(task);

	}

	private static void close(Statement s) {

		try {
			if (s == null || s.isClosed()) {
				return;
			} else {
				s.close();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	private static byte[] convertToBytes(Knapsack k) {

		ByteArrayOutputStream baos1 = new ByteArrayOutputStream();
		ObjectOutputStream oos1;
		try {
			oos1 = new ObjectOutputStream(baos1);
			oos1.writeObject(k);
		} catch (IOException e) {
			e.printStackTrace();
		}
		byte[] wantBytes = baos1.toByteArray();
		return wantBytes;
	}

	private static ByteArrayInputStream convertToBinary(byte[] bytes) {

		return new ByteArrayInputStream(bytes);
	}

	private static String playerLocationToString(Player p) {

		return p.getServer().getServerName() + "," + p.getLocation().getWorld().getName() + "," + p.getLocation().getX() + "," + p.getLocation().getY() + ","
				+ p.getLocation().getZ();
	}

}
