
package us.higashiyama.george.SQDatabase;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Set;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

public class SQDatabase extends JavaPlugin implements Listener {

	// Maps plugin names to their data source
	private static HashMap<String, SQDataSource> pluginPoolMap = new HashMap<String, SQDataSource>();

	public void onEnable() {

		ConfigAccessor ca = new ConfigAccessor(this, "DBSettings", "C:/StarQuest/GlobalConfigs/");
		FileConfiguration fc = ca.getConfig();

		// Load our connection strings
		for (String name : fc.getConfigurationSection("connstrings").getKeys(false))
			outer: {
				String user = fc.getString("connstrings." + name + ".username");
				String pass = fc.getString("connstrings." + name + ".password");
				String conn = fc.getString("connstrings." + name + ".conn");

				// Create a separate set to avoid concurrent mod issues
				Set<String> registeredPlugins = pluginPoolMap.keySet();

				// Iterate through already registered plugins to see if we can
				// map to the same pool
				for (String pluginName : registeredPlugins) {
					if (pluginPoolMap.get(pluginName).getConnectionString().equalsIgnoreCase(conn)) {
						pluginPoolMap.put(name.toLowerCase(), pluginPoolMap.get(pluginName));
						break outer;
					}
				}

				// If this isn't broken, then we assume that the plugin doesn't
				// match with a pool

				// If it's not registered, then we add it
				pluginPoolMap.put(name.toLowerCase(), new SQDataSource(conn, user, pass));

			}

		/*
		 * Now let's print out all the pools we have
		 * loaded just for the user's sake
		*/

		System.out.println("Following plugins have registered pools:");
		for (String name : pluginPoolMap.keySet()) {
			System.out.println("    " + name + ": " + pluginPoolMap.get(name));
		}

	}

	public static Connection getConnection(String plugin) {

		SQDataSource ds = pluginPoolMap.get(plugin.toLowerCase());
		if (ds != null) {
			try {
				System.out.println(plugin + " requested a connection");
				System.out.println(ds.getPool().getMaximumPoolSize());
				return ds.getPool().getConnection();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return null;
	}

	// This runnable should specify what to do with the result
	public static void executeAsyncQuery(Runnable task) {

		new Thread(task, "DatabaseAsyncExecution").run();
	}
}
