package com.starquestminecraft.sqtowerdefence;

import com.starquestminecraft.sqtechbase.objects.GUIBlock;
import com.starquestminecraft.sqtechbase.objects.Machine;
import com.starquestminecraft.sqtechbase.objects.MachineType;

public class TowerMachine extends Machine {
	
	Turret turret;
	
	public TowerMachine(int energy, GUIBlock guiBlock, MachineType machineType) {
		super(energy, guiBlock, machineType);
		if(!data.containsKey("turret")) {
			data.put("turret", new Turret(TurretType.BASE, null, null, 0, 0.0, 0.0, 0.0, 0.0, "Base Tower"));
		}
	}

}
