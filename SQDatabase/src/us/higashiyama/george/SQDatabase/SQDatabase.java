
package us.higashiyama.george.SQDatabase;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Set;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

public class SQDatabase extends JavaPlugin implements Listener {

	// Maps plugin names to their sql connection string
	// private static HashMap<String, SQLConnectionData> connStringMap = new
	// HashMap<String, SQLConnectionData>();
	private static HashMap<String, SQDataSource> pluginPoolMap = new HashMap<String, SQDataSource>();

	public void onEnable() {

		ConfigAccessor ca = new ConfigAccessor(this, "DBSettings", "C:/StarQuest/GlobalConfigs");
		FileConfiguration fc = ca.getConfig();

		// Load our connection strings
		for (String name : fc.getConfigurationSection("connstrings").getKeys(false)) {
			String user = fc.getString("connstrings." + name + ".username");
			String pass = fc.getString("connstrings." + name + ".password");
			String conn = fc.getString("connstrings." + name + ".conn");

			if (pluginPoolMap.containsKey(name.toLowerCase())) {
				// Create a separate set to avoid concurrent mod issues
				Set<String> registeredPlugins = pluginPoolMap.keySet();

				// Iterate through already registered plugins to see if we can
				// map to the same pool
				for (String pluginName : registeredPlugins) {
					if (pluginPoolMap.get(pluginName).getConnectionString().equalsIgnoreCase(conn)) {
						pluginPoolMap.put(name.toLowerCase(), pluginPoolMap.get(pluginName));
						break;
					}
				}

			} else {
				// If it's not registered, then we add it
				pluginPoolMap.put(name.toLowerCase(), new SQDataSource(conn, user, pass));
			}
		}

	}

	public static Connection getConnection(String plugin) {

		SQDataSource ds = pluginPoolMap.get(plugin.toLowerCase());
		if (ds != null) {
			try {
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
