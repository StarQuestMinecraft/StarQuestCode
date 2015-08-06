package com.dibujaron.autorestart.tasks;

import org.bukkit.scheduler.BukkitRunnable;

import com.dibujaron.autorestart.AutoRestart;

public class DelayedRestartTask extends BukkitRunnable{
	
	int time;
	
	public DelayedRestartTask(int time){
		super();
		this.time = time;
	}
	@Override
	public void run() {
		switch(time){
		case 0:
			broadcast("StarQuest is restarting!");
			this.cancel();
			AutoRestart.restart();
			return;
		case 1:
			broadcast("StarQuest will be restarting in 1 minute!");
			broadcast("Be sure to unpilot your ships before the restart!");
			time--;
			return;
		default:
			broadcast("StarQuest will be restarting in " + time + " minutes!");
		}
		time--;
	}
	
	private void broadcast(String msg){
		AutoRestart.command("eb janesudo " +  msg);
		System.out.println(msg);
	}
}
