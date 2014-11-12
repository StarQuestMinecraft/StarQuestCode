package net.madrealms.dibujaron.FirstJoinAnnounce;

import org.bukkit.ChatColor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.entity.Player;

public class FirstJoin extends JavaPlugin implements Listener{
	
	String s;
	String s2;
	
	public void onEnable(){
		getServer().getPluginManager().registerEvents(this, this);
		saveDefaultConfig();
		s = getConfig().getString("messageElse");
		s2 = getConfig().getString("messageJoiner");
	}
	
	@EventHandler
	public void onPlayerJoin(PlayerJoinEvent event){
		if (event.getPlayer().hasPlayedBefore()) return;
		String name = event.getPlayer().getName();
		String message = s.replaceAll("%name%", event.getPlayer().getName());
		String message2 = s2.replaceAll("%name%", event.getPlayer().getName());
		for(Player p: getServer().getOnlinePlayers()){
			if (p.getName().equals(name)){
				p.sendMessage(ChatColor.DARK_PURPLE + message2);
			} else {
				p.sendMessage(ChatColor.DARK_PURPLE + message);
			}
		}
		final String name2 = name;
		if (getServer().getPlayer("dibujaron") != null){
			getServer().getScheduler().scheduleSyncDelayedTask(this, new Runnable(){
				public void run() {
					if (getServer().getPlayer("dibujaron") != null){
					getServer().getPlayer("dibujaron").chat("Welcome, " + name2 + "!");
					}
				}
			}, 60L);
		}
	}
}
