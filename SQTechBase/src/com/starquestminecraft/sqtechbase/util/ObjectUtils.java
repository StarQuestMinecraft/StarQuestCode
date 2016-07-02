package com.starquestminecraft.sqtechbase.util;

import java.util.ArrayList;
import java.util.List;

import com.starquestminecraft.sqtechbase.GUIBlock;
import com.starquestminecraft.sqtechbase.Machine;
import com.starquestminecraft.sqtechbase.MachineType;
import com.starquestminecraft.sqtechbase.Network;
import com.starquestminecraft.sqtechbase.SQTechBase;
import com.starquestminecraft.sqtechbase.gui.GUI;
import com.starquestminecraft.sqtechbase.gui.GUIBlockGUI;

public class ObjectUtils {

	public static GUIBlock getGUIBlockFromGUI(GUIBlockGUI gui) {
		
		for (Network network : SQTechBase.networks) {
			
			for (GUIBlock guiBlock : network.getGUIBlocks()) {
				
				if (guiBlock.getGUI() == gui) {
					
					return guiBlock;
					
				}
				
			}
			
		}
		
		return null;
		
	}
	
	public static Machine getMachineFromMachineGUI(GUI gui) {
		
		for (Machine machine : SQTechBase.machines) {
			
			if (machine.getGUI() == gui) {
				
				return machine;
				
			}
			
		}
		
		return null;
		
	}
	
	public static List<Machine> getAllMachinesOfType(String name) {
		
		List<Machine> machines = new ArrayList<Machine>();
		
		for (Machine machine : SQTechBase.machines) {
			
			if (machine.getMachineType().name.equals(name)) {
				
				machines.add(machine);
				
			}
			
		}
		
		return machines;
		
	}
	
}
