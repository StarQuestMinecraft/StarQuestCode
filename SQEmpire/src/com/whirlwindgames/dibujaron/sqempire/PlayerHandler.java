package com.whirlwindgames.dibujaron.sqempire;

import java.util.Calendar;
import java.util.LinkedList;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import com.whirlwindgames.dibujaron.sqempire.database.object.EmpirePlayer;
import com.whirlwindgames.dibujaron.sqempire.util.AsyncUtil;

public class PlayerHandler implements Listener{

	public PlayerHandler(){
		/*AsyncUtil.scheduleOnHourTask(new Runnable(){
			public void run(){
				deductPowerOffline();
			}
		});*/
	}
	
	@EventHandler
	public void onPlayerJoin(PlayerJoinEvent event){
		EmpirePlayer.loadPlayerData(event.getPlayer());
	}
	
	@EventHandler
	public void onPlayerQuit(PlayerQuitEvent event){
		EmpirePlayer.unloadPlayerData(event.getPlayer());
	}
	
	private void deductPowerOffline(){
		AsyncUtil.crashIfNotAsync();
		return;
		/*LinkedList<EmpirePlayer> playersToDeduct = EmpirePlayer.getPlayersOfflineMoreThan(SQEmpire.thisPlanet(), Settings.SAFE_POWERLOSS_TIME);
		for(EmpirePlayer p : playersToDeduct){
			int currentPower = p.getPowerOnPlanet(SQEmpire.thisPlanet());
			int newPower = currentPower - Settings.POWER_LOSS_PER_DAY;
			if(newPower < 0) newPower = 0;
			p.updatePowerOnPlanet(SQEmpire.thisPlanet(), newPower);
		}*/
	}
}
