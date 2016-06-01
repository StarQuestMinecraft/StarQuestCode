package com.dibujaron.autorestart.tasks;

import java.util.Calendar;
import java.util.GregorianCalendar;

import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

import com.whirlwindgames.dibujaron.sqempire.SQEmpire;

public class TimeCheckTask extends BukkitRunnable{
	
	JavaPlugin p;
	
	public TimeCheckTask(JavaPlugin p){
		this.p = p;
	}
	public void run(){
		int hour = new GregorianCalendar().get(Calendar.HOUR_OF_DAY);
		//should restart at 9 AM and 9 PM server time (12 AM and 12 PM EST)
		//9 PM is hour 21, java calendar is military time
		if(hour == 9 || hour == 21){
			//time for a restart
			SQEmpire.automaticRestart = true;			
			new DelayedRestartTask(3).runTaskTimer(p, 1200, 1200);
			this.cancel();
		}
	}
}
