package com.starquestminecraft.sqtechbase;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.starquestminecraft.sqtechbase.gui.GUI;

public class Machine {

	int energy;
	
	GUIBlock guiBlock;
	
	MachineType machineType;
	
	GUI gui;
	
	public boolean exportsEnergy;
	public boolean importsEnergy;
	
	public HashMap<String, Object> data = new HashMap<String, Object>();
	
	public boolean enabled = true;
	
	public Machine(int energy, GUIBlock guiBlock, MachineType machineType) {
		
		this.energy = energy;
		
		this.guiBlock = guiBlock;
		
		this.machineType = machineType;
		
		gui = machineType.getGUI();
		
		List<Machine> removeMachines = new ArrayList<Machine>();
		
		for (Machine machine : SQTechBase.machines) {
			
			if (machine.getGUIBlock() == guiBlock) {
				
				removeMachines.add(machine);
				
			}
			
		}
		
		SQTechBase.machines.removeAll(removeMachines);
		
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
