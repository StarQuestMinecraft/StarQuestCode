package com.dibujaron.powerboostpurchaser;

import com.massivecraft.factions.entity.Faction;

public class FactionPowerboost extends Powerboost{

	Faction myFac;
	
	public FactionPowerboost(Faction f, int boostAmount) {
		super(boostAmount);
		myFac = f;
	}
	
	public Faction getFaction(){
		return myFac;
	}
}
