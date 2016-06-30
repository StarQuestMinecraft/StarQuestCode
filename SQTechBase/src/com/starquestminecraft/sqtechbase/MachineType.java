package com.starquestminecraft.sqtechbase;

import com.starquestminecraft.sqtechbase.gui.GUI;

public class MachineType {

	int maxEnergy = 0;
	public String name = "Machine Type";
	
	public boolean autodetect = true;
	
	public MachineType(int maxEnergy) {
		
		this.maxEnergy = maxEnergy;
		
	}
	
	public boolean detectStructure(GUIBlock guiBlock) {
		
		return false;
		
	}
	
	public GUI getGUI() {
		
		return new GUI();
		
	}
	
	public int getMaxEnergy() {
		
		return maxEnergy;
		
	}
	
	public String getName() {
		
		return name;
		
	}
	
}
