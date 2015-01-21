
package us.higashiyama.george.RankupUtils;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.logging.Level;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

public class ConfigAccessor {

	private final String fileName;
	private final JavaPlugin plugin;
	private File configFile;
	private FileConfiguration fileConfiguration;

	public ConfigAccessor(JavaPlugin plugin, String fileName, String dir) {

		if (plugin == null)
			throw new IllegalArgumentException("plugin cannot be null");
		if (!plugin.isInitialized())
			throw new IllegalArgumentException("plugin must be initialized");
		this.plugin = plugin;
		this.fileName = fileName;
		File dataFolder = new File(dir);
		if (dataFolder == null)
			throw new IllegalStateException();
		this.configFile = new File(dir, fileName + ".yml");
		System.out.println(this.configFile.toString());
	}

	public void reloadConfig() {

		fileConfiguration = YamlConfiguration.loadConfiguration(configFile);

		// Look for defaults in the jar
		InputStream defConfigStream = plugin.getResource(fileName);
		if (defConfigStream != null) {
			YamlConfiguration defConfig = YamlConfiguration.loadConfiguration(defConfigStream);
			fileConfiguration.setDefaults(defConfig);
		}
	}

	public FileConfiguration getConfig() {

		if (fileConfiguration == null) {
			this.reloadConfig();
		}
		return fileConfiguration;
	}

	public void saveConfig() {

		if (fileConfiguration == null || configFile == null) {
			return;
		} else {
			try {
				getConfig().save(configFile);
			} catch (IOException ex) {
				plugin.getLogger().log(Level.SEVERE, "Could not save config to " + configFile, ex);
			}
		}
	}

	public void saveDefaultConfig() {

		if (!configFile.exists()) {
			this.plugin.saveResource(fileName, false);
		}
	}

}
