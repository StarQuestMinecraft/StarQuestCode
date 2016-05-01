package com.whirlwindgames.dibujaron.sqempire.util;

import java.util.Calendar;

import org.bukkit.Bukkit;

import com.whirlwindgames.dibujaron.sqempire.SQEmpire;

public class AsyncUtil {

	public static boolean isMainThread(){
		return Thread.currentThread().getId() == 1;
	}
	
	public static void crashIfNotAsync(){
		if(isMainThread()){
			throw new EmpireAsyncException();
		}
	}
	
	public static void runAsync(Runnable r){
		Bukkit.getScheduler().runTaskAsynchronously(SQEmpire.getInstance(), r);
	}

	public static void runSync(Runnable runnable) {
		Bukkit.getScheduler().runTask(SQEmpire.getInstance(), runnable);
	}
	
	public static void runSyncLater(Runnable runnable, long delay) {
		Bukkit.getScheduler().scheduleSyncDelayedTask(SQEmpire.getInstance(), runnable, delay);
	}
	public static void scheduleAsync(long delay, long interval, Runnable r){
		Bukkit.getScheduler().runTaskTimerAsynchronously(SQEmpire.getInstance(), r, delay, interval);
	}
	public static void scheduleAsync(long delay, Runnable r){
		Bukkit.getScheduler().runTaskLaterAsynchronously(SQEmpire.getInstance(), r, delay);
	}
	
	
	public static void scheduleOnHourTask(Runnable r){
		//set up a calendar that represents the time at the end of this hour.
		Calendar c = Calendar.getInstance();
		c.add(Calendar.HOUR_OF_DAY, 1);
		c.set(Calendar.MINUTE, 0);
		c.set(Calendar.SECOND, 0);
		c.set(Calendar.MILLISECOND, 0);
		long ticksDiff = (c.getTimeInMillis() - System.currentTimeMillis()) / 50; //a tick is 50ms
		long ticksPerHour = 72000;
		scheduleAsync(ticksDiff, ticksPerHour, r);
		
	}

}
