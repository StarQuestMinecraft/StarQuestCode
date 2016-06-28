package com.starquestminecraft.sqtechbase.database.objects;

import java.io.ByteArrayOutputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.HashMap;

import org.bukkit.Bukkit;
import org.bukkit.Location;

import com.starquestminecraft.sqtechbase.GUIBlock;
import com.starquestminecraft.sqtechbase.Machine;
import com.starquestminecraft.sqtechbase.MachineType;
import com.starquestminecraft.sqtechbase.Network;
import com.starquestminecraft.sqtechbase.SQTechBase;

public class SerializableMachine implements Serializable {

	private static final long serialVersionUID = 7348147532012964743L;
	
	String machineType;
	int energy;
	
	int x;
	int y;
	int z;
	String world;
	
	boolean exportsEnergy;
	boolean importsEnergy;
	
	HashMap<String, Object> data;
	
	public SerializableMachine(Machine machine) {
		
		machineType = machine.getMachineType().name;
		energy = machine.getEnergy();
		
		x = machine.getGUIBlock().getLocation().getBlockX();
		y = machine.getGUIBlock().getLocation().getBlockY();
		z = machine.getGUIBlock().getLocation().getBlockZ();
		world = machine.getGUIBlock().getLocation().getWorld().getName();
		
		exportsEnergy = machine.exportsEnergy;
		importsEnergy = machine.importsEnergy;
		
		for (String key: machine.data.keySet()) {
			
			try {
				
				new ObjectOutputStream(new ByteArrayOutputStream()).writeObject(machine.data.get(key));
				
				data.put(key, machine.data.get(key));
				
			} catch (Exception exception) {
				
				System.out.print("SQTechBase: machine with type " + machineType + " was unable to save data with key " + key);
				
			}
			
		}
		
	}
	
	public Machine getMachine() {
		
		if (Bukkit.getWorld(world) != null) {
			
			Location location = new Location(Bukkit.getWorld(world), x, y,z);
			
			GUIBlock guiBlock = null;
			
			for (Network network : SQTechBase.networks) {
				
				for (GUIBlock listGUIBlock : network.getGUIBlocks()) {
					
					if (listGUIBlock.getLocation().equals(location)) {
						
						guiBlock = listGUIBlock;
						
						guiBlock.setExports(listGUIBlock.getExports());
						guiBlock.setImports(listGUIBlock.getImports());
						
					}
					
				}
				
			}
			
			if (guiBlock == null) {
				
				return null;
				
			}
			
			MachineType type = null;
			
			for (MachineType listType : SQTechBase.machineTypes) {
				
				if (listType.getName().equals(machineType)) {
					
					type = listType;
					
				}
				
			}
			
			if (type == null) {
				
				return null;
				
			}
			
			Machine machine = new Machine(energy, guiBlock, type);
			
			machine.exportsEnergy = exportsEnergy;
			machine.importsEnergy = importsEnergy;
			
			machine.data.putAll(data);
			
			return machine;
			
		}
		
		return null;
		
	}

}
