package com.starquestminecraft.sqtechbase.database.objects;

import java.io.ByteArrayOutputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Location;

import com.starquestminecraft.sqtechbase.SQTechBase;
import com.starquestminecraft.sqtechbase.objects.Fluid;
import com.starquestminecraft.sqtechbase.objects.GUIBlock;
import com.starquestminecraft.sqtechbase.objects.Machine;
import com.starquestminecraft.sqtechbase.objects.MachineType;
import com.starquestminecraft.sqtechbase.objects.Network;

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
	
	HashMap<String, Object> data = new HashMap<String, Object>();
	
	boolean enabled;
	
	List<Integer> liquidExports = new ArrayList<Integer>();
	List<Integer> liquidImports = new ArrayList<Integer>();
	
	public HashMap<Integer, Integer> liquid = new HashMap<Integer, Integer>();
	
	public HashMap<Integer, Integer> maxLiquid = new HashMap<Integer, Integer>();
	
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
				exception.printStackTrace();
				
			}
			
		}
		
		enabled = machine.enabled;
		
		for (Fluid fluid : machine.liquidExports) {
			
			System.out.print(fluid.id);
			
			liquidExports.add(fluid.id);
			
		}
		
		for (Fluid fluid : machine.liquidImports) {
			
			liquidImports.add(fluid.id);
			
		}
		
		for (Fluid fluid : machine.liquid.keySet()) {
			
			liquid.put(fluid.id, machine.liquid.get(fluid));
			
		}
		
		for (Fluid fluid : machine.maxLiquid.keySet()) {
			
			maxLiquid.put(fluid.id, machine.maxLiquid.get(fluid));
			
		}
		
	}
	
	public Machine getMachine() {
		
		if (Bukkit.getWorld(world) != null) {
			
			Location location = new Location(Bukkit.getWorld(world), x, y,z);
			
			MachineType type = null;
			
			for (MachineType listType : SQTechBase.machineTypes) {
				
				if (listType.getName().equals(machineType)) {
					
					type = listType;
					
				}
				
			}
			
			if (type == null) {
				
				return null;
				
			}
			
			for (Network network : SQTechBase.networks) {
				
				for (GUIBlock guiBlock : network.getGUIBlocks()) {
					
					if (guiBlock.getLocation().equals(location)) {
						
						if (type.detectStructure(guiBlock)) {
							
							Machine machine = new Machine(energy, guiBlock, type);
							
							machine.exportsEnergy = exportsEnergy;
							machine.importsEnergy = importsEnergy;
							
							machine.data.putAll(data);
							
							machine.enabled = enabled;
							
							if (liquidExports != null) {
								
								for (Integer id : liquidExports) {
									
									for (Fluid fluid : SQTechBase.fluids) {
										
										if (fluid.id == id) {
											
											machine.liquidExports.add(fluid);
											
										}
										
									}
									
								}
								
								for (Integer id : liquidImports) {
									
									for (Fluid fluid : SQTechBase.fluids) {
										
										if (fluid.id == id) {
											
											machine.liquidImports.add(fluid);
											
										}
										
									}
									
								}
								
							}
							
							if (liquid != null) {
								
								for (Integer id : liquid.keySet()) {
									
									for (Fluid fluid : SQTechBase.fluids) {
										
										if (fluid.id == id) {
											
											machine.liquid.put(fluid, liquid.get(id));
											
										}
										
									}
									
								}
								
								for (Integer id : maxLiquid.keySet()) {
									
									for (Fluid fluid : SQTechBase.fluids) {
										
										if (fluid.id == id) {
											
											machine.maxLiquid.put(fluid, maxLiquid.get(id));
											
										}
										
									}
									
								}
								
							}

							return machine;
							
						}
						
					}
					
				}
				
			}

		}
		
		return null;
		
	}

}
