package com.whirlwindgames.dibujaron.sqempire;

public class BattleConnection {

	Territory territory1;
	Territory territory2;
	
	double x1 = 0.0;
	double z1 = 0.0;
	double x2 = 0.0;
	double z2 = 0.0;
	
	public BattleConnection () {
		
	}
	
	public String getName() {
		
		return territory1.name.replace('_', ' ') + "-" + territory2.name.replace('_', ' ');
		
	}
	
}
