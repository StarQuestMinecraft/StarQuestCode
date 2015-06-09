package com.dibujaron.powerboostpurchaser;

public abstract class Powerboost {
	//contains data about a powerboost
	private int boostAmount;
	
	public Powerboost(int boostAmount){
		this.boostAmount = boostAmount;
	}
	
	public int getBoost(){
		return boostAmount;
	}
}
