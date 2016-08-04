package com.starquestminecraft.sqtechbase.objects;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.bukkit.entity.Player;

import com.starquestminecraft.sqtechbase.SQTechBase;
import com.starquestminecraft.sqtechbase.gui.GUI;

public class Machine {

	int energy;
	
	public GUIBlock guiBlock;
	
	MachineType machineType;
	
	public boolean exportsEnergy;
	public boolean importsEnergy;
	
	public HashMap<String, Object> data = new HashMap<String, Object>();
	
	public boolean enabled = true;
	
	public List<Fluid> liquidExports = new ArrayList<Fluid>();
	public List<Fluid> liquidImports = new ArrayList<Fluid>();
	
	public HashMap<Fluid, Integer> liquid = new HashMap<Fluid, Integer>();
	
	public HashMap<Fluid, Integer> maxLiquid = new HashMap<Fluid, Integer>();
	
	public Machine(int energy, GUIBlock guiBlock, MachineType machineType) {
		
		this.energy = energy;
		
		this.guiBlock = guiBlock;
		
		this.machineType = machineType;
		
		List<Machine> removeMachines = new ArrayList<Machine>();
		
		for (Machine machine : SQTechBase.machines) {
			
			if (machine.getGUIBlock().getLocation().equals(guiBlock.getLocation()) && machine != this) {
				
				removeMachines.add(machine);
				
			}
			
		}
		
		if (machineType.defaultExport) {
			
			exportsEnergy = true;
			
		}
		
		if (machineType.defaultImport) {
			
			importsEnergy = true;
			
		}
		
		SQTechBase.machines.removeAll(removeMachines);
		
		for (Fluid fluid : maxLiquid.keySet()) {
			
			liquid.put(fluid, 0);
			
		}
		
		machineType.initialize(this);
 		
	}
	
	public GUIBlock getGUIBlock() {
		
		return guiBlock;
		
	}
	
	public GUI getGUI(Player player) {
		
		return machineType.getGUI(player, guiBlock.id);
		
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
		
		machineType.updateEnergy(this);
		
	}
	
	
	public void changeMachineType(MachineType newMachineType) {
		
		machineType = newMachineType;
		
	}
	
	public boolean check() {
		
		for (Network network : SQTechBase.networks) {
			
			for (GUIBlock guiBlock : network.getGUIBlocks()) {
				
				if (guiBlock == this.guiBlock) {
					
					return true;
					
				}
				
			}
			
		}

		SQTechBase.machines.remove(this);
		
		return false;
		
	}

	public int getLiquid(Fluid fluid) {
		
		if (liquid.containsKey(fluid)) {
			
			return liquid.get(fluid);
			
		}
		
		return 0;
		
	}
	
	public void setLiquid(Fluid fluid, int amount) {
		
		if (liquid.containsKey(fluid)) {
			
			liquid.replace(fluid, amount);
			
			machineType.updateLiquid(this);
			
		} else {
			
			liquid.put(fluid, amount);
			
			machineType.updateLiquid(this);
			
		}
		
	}
	
	public int getMaxLiquid(Fluid fluid) {
		
		if (maxLiquid.containsKey(fluid)) {
			
			return maxLiquid.get(fluid);
			
		}
		
		return 0;
		
	}
	
}
