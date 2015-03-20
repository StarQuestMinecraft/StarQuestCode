package com.dibujaron.autorestart;

import java.io.File;
import java.io.IOException;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.plugin.java.JavaPlugin;

public class AutoRestart extends JavaPlugin{
	
	public void onEnable(){
		getServer().getScheduler().scheduleSyncDelayedTask(this, new Runnable(){
			public void run(){
				command("sync console all chatreload");
			}
		}, 11000L);
		//delay an hour and a half; there's no reason to check before then, and if you check too soon it'll restart loop.
		//check every minute after that. players may get picky about their restart times.
		new TimeCheckTask(this).runTaskTimer(this, 108000, 12000);
	}
	
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if(cmd.getName().equalsIgnoreCase("restartall") && (sender.getName().equals("dibujaron") || sender instanceof ConsoleCommandSender)){
			if(args.length != 1){
				sender.sendMessage("put a time argument.");
				return true;
			}
			int time = Integer.parseInt(args[0]);
			sender.sendMessage("Restarting servers!");
			restartDelayed(time);
			return true;
		}
		return false;
	}
	
	private void restartDelayed(int time){
		new DelayedRestartTask(time).runTaskTimer(this, 0, 1200);
	}
	
	public static void restart(){
		command("sync console all stop");
		command("sync console bungee end");
		executeBatch("autorestart-helper");
	}
	
	public static void command(String com){
		Bukkit.dispatchCommand(Bukkit.getConsoleSender(), com);
	}
	
	private static void executeBatch(String filename ){
		try {
			File dir = new File("C:\\StarQuest\\BungeeUtils");
			Runtime.getRuntime().exec("c:\\windows\\system32\\cmd.exe /d /c " + filename + ".bat", null, dir);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
