package me.Tongonto;

import org.bukkit.scheduler.BukkitRunnable;

public class ShopperTask extends BukkitRunnable {
	
	private final SQShopper plugin;
	
	public ShopperTask(SQShopper plugin) {
		this.plugin = plugin;
	}
	
	@Override
	public void run() {
		plugin.sentTimeout = false;
		plugin.detectedSigns.clear();
	}

}
