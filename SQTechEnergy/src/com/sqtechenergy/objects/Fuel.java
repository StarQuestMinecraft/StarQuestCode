package com.sqtechenergy.objects;

import java.io.Serializable;

public class Fuel implements Serializable{

	private static final long serialVersionUID = -3554147416629314630L;
	
	public int id;
	public short data;
	public int energyPerTick;
	public int burnTime;
	
	public String generator;
	
	public Fuel() {
		
	}
	
}
