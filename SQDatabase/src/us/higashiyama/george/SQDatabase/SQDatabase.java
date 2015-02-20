
package us.higashiyama.george.SQDatabase;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

public class SQDatabase extends JavaPlugin implements Listener {

	public static SQDataSource ds;

	public void onEnable() {

		ds = new SQDataSource();

	}

	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {

		Runnable r = new Runnable() {

			@Override
			public void run() {

				runQuery();
				System.out.println("Running1: " + System.currentTimeMillis());
			}
		};
		new Thread(r, "DBThread").start();

		runQuery();
		System.out.println("Running2: " + System.currentTimeMillis());
		return false;

	}

	public static synchronized void runQuery() {

		long start = System.currentTimeMillis();
		PreparedStatement s = null;
		Connection c = null;
		try {
			c = ds.getConnection();
			s = c.prepareStatement("SELECT * FROM rankup");
			ResultSet rs = s.executeQuery();

			while (rs.next()) {
				break;
			}
			s.close();
		} catch (SQLException e) {
			System.out.print("[SQDatabase] SQL Error" + e.getMessage());
		} catch (Exception e) {
			System.out.print("[SQDatabase] SQL Error (Unknown)");
			e.printStackTrace();
		} finally {
			close(s, c);
		}

		System.out.println("Final time: " + (System.currentTimeMillis() - start));
	}

	private static void close(Statement s, Connection c) {

		try {
			if (s == null || s.isClosed()) {
				return;
			}
			s.close();
			if (c == null || c.isClosed()) {
				return;
			}
			c.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
