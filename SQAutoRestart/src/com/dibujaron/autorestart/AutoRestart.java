package com.dibujaron.autorestart;

import java.io.File;
import java.io.IOException;
import java.util.TimerTask;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.plugin.java.JavaPlugin;

import com.dibujaron.autorestart.tasks.DelayedRestartTask;
import com.dibujaron.autorestart.tasks.TimeCheckTask;

public class AutoRestart extends JavaPlugin {

	public void onEnable() {
		if (Bukkit.getServerName().equals("CoreSystem")) {
			System.out.println("Server is Core, scheduling restart!");
			// delay an hour and a half; there's no reason to check before then,
			// and if you check too soon it'll restart loop.
			// check every minute after that. players may get picky about their
			// restart times.
			new TimeCheckTask(this).runTaskTimer(this, 108000, 12000);

		}
	}

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (cmd.getName().equalsIgnoreCase("restartall") && (sender.getName().equals("dibujaron") || sender.hasPermission("SQAutoRestart.restartall"))) {
			if (args.length != 1) {
				sender.sendMessage("put a time argument.");
				return true;
			}
			int time = Integer.parseInt(args[0]);
			sender.sendMessage("Restarting servers!");
			restartDelayed(time);
			return true;
		}
		if(cmd.getName().equalsIgnoreCase("autorestart") && sender instanceof ConsoleCommandSender){
			System.out.println("Server stopping in five seconds...");
			Bukkit.getScheduler().scheduleSyncDelayedTask(this, new Runnable(){
				public void run(){
					Bukkit.getServer().shutdown();
				}
			}, 20 * 5L);
			return true;
		}
		return false;
	}

	private void restartDelayed(int time) {
		new DelayedRestartTask(time).runTaskTimer(this, 0, 1200);
	}

	public static void restart() {
		command("ee autorestart");
		command("eb end");
		executeBatch("autorestart-helper");
	}

	public static void command(String com) {
		Bukkit.dispatchCommand(Bukkit.getConsoleSender(), com);
	}

	private static void executeBatch(String filename) {
		try {
			File dir = new File("C:\\SQ4\\BungeeUtils");
			Runtime.getRuntime().exec("c:\\windows\\system32\\cmd.exe /d /c " + filename + ".bat", null, dir);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
