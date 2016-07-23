package com.starquestminecraft.sqtowerdefence;

import java.io.Serializable;

public enum TurretType implements Serializable{
	BASE, ARROW, CANNON, ANTIAIR, FLAK, CHEMICAL, GAS, SPAWNER, GENERATOR, WALL, TRENCH, LANDMINE;
	
	public TurretType getTurretType(String string) {
		if(string.equalsIgnoreCase("BASE")) {
			return TurretType.BASE;
		}
		else if(string.equalsIgnoreCase("ARROW")) {
			return TurretType.ARROW;
		}
		else if(string.equalsIgnoreCase("CANNON")) {
			return TurretType.CANNON;
		}
		else if(string.equalsIgnoreCase("ANTIAIR")) {
			return TurretType.ANTIAIR;
		}
		else if(string.equalsIgnoreCase("FLAK")) {
			return TurretType.FLAK;
		}
		else if(string.equalsIgnoreCase("CHEMICAL")) {
			return TurretType.CHEMICAL;
		}
		else if(string.equalsIgnoreCase("GAS")) {
			return TurretType.GAS;
		}
		else if(string.equalsIgnoreCase("SPAWNER")) {
			return TurretType.SPAWNER;
		}
		return null;
	}
	
}
