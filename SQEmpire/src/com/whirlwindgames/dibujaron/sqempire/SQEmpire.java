package com.whirlwindgames.dibujaron.sqempire;

import net.countercraft.movecraft.listener.BlockListener;
import net.countercraft.movecraft.listener.CommandListener;
import net.countercraft.movecraft.listener.EntityListener;
import net.countercraft.movecraft.listener.InteractListener;
import net.countercraft.movecraft.listener.InventoryListener;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;


public class SQEmpire extends JavaPlugin{
	
	private static SQEmpire instance;
	private static Planet thisPlanet;
	
	public void onEnable(){
		instance = this;
		thisPlanet = Planet.fromName(Bukkit.getServerName());
		PluginManager pm = getServer().getPluginManager();
		pm.registerEvents( new BetaListener(), this );
		pm.registerEvents( new PlayerHandler(), this );
	}
	
	public static SQEmpire getInstance(){
		return instance;
	}

	public static void debug(String s){
		instance.getLogger().info(s);
	}
	
	public static Planet thisPlanet(){
		return thisPlanet;
	}
	
	public static void echo(CommandSender s, String... msg){
		msg[0] = ChatColor.WHITE + "[" + ChatColor.GREEN + "SQEmpire" + ChatColor.WHITE + "] " + msg[0];
		s.sendMessage(msg);
	}
}


