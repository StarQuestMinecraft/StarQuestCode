package com.starquestminecraft.sqtechbase.util;

import java.util.ArrayList;
import java.util.List;

import com.starquestminecraft.sqtechbase.SQTechBase;
import com.starquestminecraft.sqtechbase.gui.GUI;
import com.starquestminecraft.sqtechbase.objects.GUIBlock;
import com.starquestminecraft.sqtechbase.objects.Machine;
import com.starquestminecraft.sqtechbase.objects.Network;

public class ObjectUtils {

	public static GUIBlock getGUIBlockFromGUI(GUI gui) {
		
		for (Network network : SQTechBase.networks) {
			
			for (GUIBlock guiBlock : network.getGUIBlocks()) {
				
				if (guiBlock.id == gui.id) {
					
					return guiBlock;
					
				}
				
			}
			
		}
		
		return null;
		
	}
	
	public static Machine getMachineFromGUIBlock(GUIBlock guiBlock) {
		
		for (Machine machine : SQTechBase.machines) {
			
			if (machine.getGUIBlock().equals(guiBlock)) {
				
				return machine;
				
			}
			
		}
		
		return null;
		
	}
	
	public static Machine getMachineFromMachineGUI(GUI gui) {
		
		for (Machine machine : SQTechBase.machines) {
			
			if (machine.getGUIBlock().id == gui.id) {
				
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
