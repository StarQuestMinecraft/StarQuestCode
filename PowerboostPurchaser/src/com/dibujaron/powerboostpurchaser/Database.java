package com.dibujaron.powerboostpurchaser;

import org.bukkit.entity.Player;

import com.massivecraft.factions.entity.Faction;

public interface Database {
	
	public FactionPowerboost getBoostOfFaction(Faction f);
	public PersonalPowerboost getBoostOfPlayer(Player p);
	public void setBoostOfFaction(FactionPowerboost b);
	public void setBoostOfPlayer(PersonalPowerboost b);
	public int getTaxesOfFaction(Faction f);
	public void setTaxesOfFaction(Faction f, int taxes);
}
