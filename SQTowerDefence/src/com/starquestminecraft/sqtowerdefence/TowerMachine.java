package com.starquestminecraft.sqtowerdefence;

import com.starquestminecraft.sqtechbase.objects.GUIBlock;
import com.starquestminecraft.sqtechbase.objects.Machine;
import com.starquestminecraft.sqtechbase.objects.MachineType;

public class TowerMachine extends Machine {

	public Turret turret = new Turret(TurretType.BASE, null, null, 0, 0.0, 0.0, 0.0, 0.0, "Base Tower");
	
	public TowerMachine(int energy, GUIBlock guiBlock, MachineType machineType) {
		super(energy, guiBlock, machineType);
	}

}
