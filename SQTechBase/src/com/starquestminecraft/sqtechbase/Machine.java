package com.starquestminecraft.sqtechbase;

import java.util.HashMap;

import com.starquestminecraft.sqtechbase.gui.GUI;

public class Machine {

	int energy;
	
	GUIBlock guiBlock;
	
	MachineType machineType;
	
	GUI gui;
	
	public boolean exportsEnergy;
	public boolean importsEnergy;
	
	public HashMap<String, Object> data;
	
	public boolean enabled;
	
	public Machine(int energy, GUIBlock guiBlock, MachineType machineType) {
		
		this.energy = energy;
		
		this.guiBlock = guiBlock;
		
		this.machineType = machineType;
		
		gui = machineType.getGUI();
		
	}
	
	public GUIBlock getGUIBlock() {
		
		return guiBlock;
		
	}
	
	public GUI getGUI() {
		
		return gui;
		
	}
	
	public MachineType getMachineType() {
		
		return machineType;
		
	}
	
	public boolean detectStructure() {
		
		return machineType.detectStructure(guiBlock);
		
	}
	
	public int getEnergy() {
		
		return energy;
		
	}
	
	public void setEnergy(int energy) {
		
		this.energy = energy;
		
	}
	
	
	public void changeMachineType(MachineType newMachineType) {
		
		machineType = newMachineType;
		
		gui = machineType.getGUI();
		
	}
}
