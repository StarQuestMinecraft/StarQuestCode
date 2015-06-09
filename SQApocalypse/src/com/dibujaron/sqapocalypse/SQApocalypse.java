package com.dibujaron.sqapocalypse;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Sound;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.plugin.java.JavaPlugin;

public class SQApocalypse extends JavaPlugin implements Listener{

	public static int stage;
	public static boolean verbose;
	public static int radius = getRadius();
	public static double day;
	
	private DestroyTask task;
	private PlayerBurnTask task2;
	
	public void onEnable(){
		String name = Bukkit.getServerName();
		if(name.equals("Regalis") || name.equals("Defalos") || name.equals("Digitalia")){
			Bukkit.getPluginManager().disablePlugin(this);
			return;
		}
		Bukkit.getServer().getPluginManager().registerEvents(this,this);
		saveDefaultConfig();
		day = getConfig().getDouble("day");
		verbose = getConfig().getBoolean("verbose");
		stage = getStageFromDay(day);
		//every time the server restarts update it by half a day
		getConfig().set("day", day + 0.5);
		saveConfig();
		if(stage > 0){
			World w = Bukkit.getWorld(Bukkit.getServerName());
			task = new DestroyTask(w, stage);
			task.runTaskTimer(this, 1, 1);
			task2 = new PlayerBurnTask(w, stage);
			task2.runTaskTimer(this, 20, 20);
		}
	}
	
	private int getStageFromDay(double day){
		if(day < 3.0) return 0;
		if(day < 5.0) return 1;
		if(day < 7.0) return 2;
		if(day < 9.0) return 3;
		if(day < 11.0) return 4;
		return 5;
	}
	
	private static int getRadius(){
		String name = Bukkit.getServerName();
		switch(name.toLowerCase()){
		case "boletarian":
		case "boskevine":
		case "quavara":
		case "kelakaria":
		case "iffrizar":
		case "valadro":
			return 3000;
		default:
			return 2000;
		}
	}
	
	@EventHandler
	public void onPlayerJoin(PlayerJoinEvent event){
		final Player p = event.getPlayer();
		Bukkit.getScheduler().scheduleSyncDelayedTask(this, new Runnable(){
			public void run(){
				p.playSound(p.getLocation(), Sound.LEVEL_UP, 1.0F, 1.0F);
				p.sendMessage(ChatColor.RED + "=========================================");
				p.sendMessage(ChatColor.GOLD + "Star" + ChatColor.BLUE + "Quest" + ChatColor.RED + " Solar Apocalypse: Day " + (int) day);
				p.sendMessage(ChatColor.RED + "New Players - come back in a week! SQ is resetting its worlds, this is the end-of-world event!");
				p.sendMessage(ChatColor.RED + "We're glad you're here, but if you stay, you'll probably die a fiery death. :(");
				p.sendMessage(ChatColor.RED + "Check http://starquestminecraft.com for more information.");
				p.sendMessage(ChatColor.RED + "=========================================");
			}
		}, 20 * 10L);
	}

}
