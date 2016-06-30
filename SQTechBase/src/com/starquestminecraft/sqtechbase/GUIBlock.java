package com.starquestminecraft.sqtechbase;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Location;
import org.bukkit.inventory.ItemStack;
import org.bukkit.metadata.FixedMetadataValue;

import com.starquestminecraft.sqtechbase.gui.GUI;

public class GUIBlock {

	GUI gui;
	Location location;
	
	List<ItemStack> imports = new ArrayList<ItemStack>();
	List<ItemStack> exports = new ArrayList<ItemStack>();
	
	public int id = -1;
	
	public GUIBlock(GUI gui, Location location) {
		
		this.gui = gui;
		this.location = location;
		
		boolean isMachine = false;
		
		for (Machine machine : SQTechBase.machines) {
			
			if (machine.getGUIBlock().getLocation().equals(location)) {
				
				machine.guiBlock = this;
				isMachine = true;
				
			}
			
		}
		
		if (!isMachine) {
			
			for (MachineType machineType : SQTechBase.machineTypes) {
				
				if (machineType.autodetect) {
					
					if (machineType.detectStructure(this)) {
						
						Machine machine = new Machine(0, this, machineType);
						
						SQTechBase.machines.add(machine);
						
					}
					
				}
									
			}
			
		} else {
			
			for (Machine machine : SQTechBase.machines) {
				
				if (machine.getGUIBlock().getLocation().equals(location)) {
					
					machine.enabled = true;
					
				}
				
			}
			
		}
		
		int highestID = -1;
		
		for (Network network : SQTechBase.networks) {
			
			for (GUIBlock guiBlock : network.GUIBlocks) {
				
				if (guiBlock.id > highestID) {
					
					highestID = guiBlock.id;
					
				}
				
			}
			
		}
		
		id = highestID + 1;
		
		location.getBlock().setMetadata("guiblock", new FixedMetadataValue(SQTechBase.getPluginMain(), id));
		
	}
	
	public GUI getGUI() {
		
		return gui;
		
	}
	
	public Location getLocation() {
		
		return location;
		
	}
	
	void setLocation(Location location) {
		
		this.location = location;
		
	}
	
	public void changeLocation(Location location) {
		
		Location oldLocation = this.location.clone();
		
		this.location = location;
		
		new Network(oldLocation.getBlock().getRelative(1, 0, 0));
		new Network(oldLocation.getBlock().getRelative(-1, 0, 0));
		new Network(oldLocation.getBlock().getRelative(0, 1, 0));
		new Network(oldLocation.getBlock().getRelative(0, -1, 0));
		new Network(oldLocation.getBlock().getRelative(0, 0, 1));
		new Network(oldLocation.getBlock().getRelative(0, 0, -1));
		
		new Network(location.getBlock().getRelative(1, 0, 0));
		new Network(location.getBlock().getRelative(-1, 0, 0));
		new Network(location.getBlock().getRelative(0, 1, 0));
		new Network(location.getBlock().getRelative(0, -1, 0));
		new Network(location.getBlock().getRelative(0, 0, 1));
		new Network(location.getBlock().getRelative(0, 0, -1));
		
	}
	
	public void addImport(ItemStack itemStack) {
		
		imports.add(itemStack);
		
	}
	
	public void addExport(ItemStack itemStack) {
		
		exports.add(itemStack);
		
	}
	
	public List<ItemStack> getImports() {
		
		return imports;
		
	}
	
	public List<ItemStack> getExports() {
		
		return exports;
		
	}
	
	public void setExports(List<ItemStack> exports) {
		
		this.exports = exports;
		
	}
	
	public void setImports(List<ItemStack> imports) {
		
		this.imports = imports;
		
	}
	
	public void removeExport(ItemStack itemStack) {
		
		exports.remove(itemStack);
		
	}
	
	public void removeImport(ItemStack itemStack) {
		
		imports.remove(itemStack);
		
	}
	
}
