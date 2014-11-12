package com.regaphoenixmc.dibujaron.starquesttrees;

import org.bukkit.plugin.java.JavaPlugin;

public class StarQuestTreesAndMobs extends JavaPlugin{
	@Override
	public void onEnable(){
		getServer().getPluginManager().registerEvents(new TreeListener(this), this);
	}
}
