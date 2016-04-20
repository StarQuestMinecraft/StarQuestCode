package com.whirlwindgames.dibujaron.sqempire.util;

import com.whirlwindgames.dibujaron.sqempire.Empire;

public class EmpirePlanetTempUtils {
	
	public static boolean isTargetEnemyRim(String target, Empire me){
		for(int i = 1; i < 4; i++){
			if(i != me.getID()){
				for(String s : getRimPlanetsOfEmpire(Empire.fromID(i))){
					if(s.equalsIgnoreCase(target)){
						return true;
					}
				}
			}
		}
		return false;
	}
	public static String[] getRimPlanetsOfEmpire(Empire e){
		if(e == Empire.ARATOR){
			return new String[]{"Arator","Tyder","Jurion"};
		} else if (e == Empire.REQUIEM){
			return new String[]{"Quillon","Erilon","Mardos"};
		} else if (e == Empire.YAVARI){
			return new String[]{"Yavar","Beskytt","Radawi"};
		}
		return new String[0];
	}
}
