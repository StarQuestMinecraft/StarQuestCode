package com.dibujaron.powerboostpurchaser;

import java.util.UUID;

public class PersonalPowerboost extends Powerboost{
	
	UUID myPlayer;
	public PersonalPowerboost(UUID player, int boostAmount) {
		super(boostAmount);
		myPlayer = player;
		// TODO Auto-generated constructor stub
	}
	
	public UUID getPlayer(){
		return myPlayer;
	}

}
