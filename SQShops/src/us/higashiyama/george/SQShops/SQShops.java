package us.higashiyama.george.SQShops;

import java.util.HashMap;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

public class SQShops extends JavaPlugin implements Listener {

	public static HashMap<Material, Double> itemIndex = new HashMap<Material, Double>();
	
	
	
	public void onEnable() {
		Database.setUp();
		Bukkit.getServer().getPluginManager().registerEvents(this, this);
		itemIndex = Database.loadData();
	}
	
	
	
}
