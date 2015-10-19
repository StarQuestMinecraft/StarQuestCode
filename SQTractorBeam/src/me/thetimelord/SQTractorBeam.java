package me.thetimelord;

import org.bukkit.plugin.java.JavaPlugin;

public class SQTractorBeam extends JavaPlugin {
	
	@Override
	public void onEnable() {
		new PlayerListener(this);
		
	}
	@Override
	public void onDisable() {
		
		
	}
}


