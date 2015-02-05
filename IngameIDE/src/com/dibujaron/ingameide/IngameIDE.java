package com.dibujaron.ingameide;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.concurrent.Semaphore;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.plugin.java.JavaPlugin;

public class IngameIDE extends JavaPlugin implements Listener{
	
	private static IngameIDE instance;
	
	public void onEnable(){
		instance = this;
		Bukkit.getPluginManager().registerEvents(this, this);
	}
	HashMap<Player, CodeSession> userMap = new HashMap<Player, CodeSession>();
	
	private ArrayList<Runnable> executionQueueCompleted = new ArrayList<Runnable>();
	private ArrayList<String[]> executionQueueForProcessing = new ArrayList<String[]>();
	
	Semaphore lock = new Semaphore(1);
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		String name = cmd.getName().toLowerCase();
		if(name.equals("java")){
			if(sender instanceof Player){
				Player plr = (Player) sender;
				if(args.length == 1){
					String arg = args[0].toLowerCase();
					if(arg.equals("new")){
						newCode(plr);
					} else if(arg.equals("execute")){
						executeCode(plr);
					} else if(arg.equals("exit")){
						exitCode(plr);
					}
					return true;
				}
			}
		}
		return false;
	}
	
	@EventHandler
	public void onPlayerChat(AsyncPlayerChatEvent event){
		CodeSession session = userMap.get(event.getPlayer());
		if(session != null){
			event.setCancelled(true);
			session.addLine(event.getMessage());
		}
	}
	
	@EventHandler
	public void onPlayerQuit(PlayerQuitEvent event){
		userMap.remove(event.getPlayer());
	}
	
	private void newCode(Player sender){
		CodeSession s = userMap.get(sender);
		if(s != null){
			exitCode(sender);
		}
		userMap.put(sender, new CodeSession(sender));
	}
	
	public ClassLoader getLoader(){
		return this.getClassLoader();
	}
	private void executeCode(Player sender){
		CodeSession s = userMap.get(sender);
		if(s != null){
			s.execute();
		} else {
			sender.sendMessage("You have no code to execute.");
		}
	}
	
	private void exitCode(Player sender){
		userMap.remove(sender);
		sender.sendMessage(ChatColor.GREEN + "========================");
	}
	
	public static IngameIDE getInstance(){
		return instance;
	}
}
