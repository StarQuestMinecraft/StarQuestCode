package com.dibujaron.powerboostpurchaser;

import java.util.Calendar;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

import com.massivecraft.factions.Factions;
import com.massivecraft.factions.Rel;
import com.massivecraft.factions.entity.Faction;
import com.massivecraft.factions.entity.FactionColl;
import com.massivecraft.factions.entity.MPlayer;
import com.massivecraft.factions.integration.Econ;

public class UpdateTask extends BukkitRunnable{

	public static void schedule(){
		UpdateTask t = new UpdateTask();
		
		//should be taken at 9:00 PM 
		//first try today
		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.HOUR_OF_DAY, 21);
		cal.set(Calendar.MINUTE, 0);
		cal.set(Calendar.SECOND, 0);
		cal.set(Calendar.MILLISECOND, 0);
		long millis = cal.getTimeInMillis();
		if(millis < System.currentTimeMillis()){
			//this time has already passed, schedule for tomorrow
			cal.add(Calendar.DAY_OF_YEAR, 1);
			millis = cal.getTimeInMillis();
		}
		long millisDiff = millis - System.currentTimeMillis();
		int ticksDiff = (int) (millisDiff * 0.02);
		t.runTaskTimer(PowerboostPurchaser.get(), ticksDiff, 86400 * 20);
	}
	
	@Override
	public void run() {
		Database d = PowerboostPurchaser.get().database;
		PowerboostPurchaser.janeMessage("A new day is here! Powerboost costs and taxes have been collected!");
		for (Faction f : FactionColl.get().getAll()){
			int taxes = d.getTaxesOfFaction(f);
			int total = 0;
			List<MPlayer> l = f.getMPlayers();
			for(int i = 0; i < l.size(); i++){
				MPlayer m = l.get(i);
				if(EcoHandler.getEconomy().withdrawPlayer(Bukkit.getOfflinePlayer(m.getUuid()), taxes).transactionSuccess()){
					total += taxes;
				} else {
					if(!(m.getRole() == Rel.LEADER)){
						f.setInvited(m, false);
						m.resetFactionData();
					}
				}
			}
			Econ.modifyMoney(f, total, "daily tax deposit");
			FactionPowerboost fpb = d.getBoostOfFaction(f);
			
			if(fpb == null) continue;
			double boost = (double) fpb.getBoost();
			if(boost == 0){
				//this means that it was cancelled, we don't have to do anything
				continue;
			}
			if(Econ.hasAtLeast(f, EcoHandler.getCost() * boost, "powerboost charge")){
				Econ.modifyMoney(f, -1 * EcoHandler.getCost() * boost, "powerboost charge");
				if(fpb.getFaction().getPowerBoost() != fpb.getBoost()){
					fpb.getFaction().setPowerBoost(boost);
				}
			} else {
				PowerboostPurchaser.janeMessage(fpb.getFaction().getName() + " could not afford to maintain their powerboost.");
				d.setBoostOfFaction(new FactionPowerboost(fpb.getFaction(), 0));
				fpb.getFaction().setPowerBoost((double) 0);
			}
		}
	}

}
