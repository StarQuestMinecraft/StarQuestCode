package com.starquestminecraft.sqtechbase;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
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
				
				if (machineType.detectStructure(this)) {
					
					Machine machine = new Machine(0, this, machineType);
					
					SQTechBase.machines.add(machine);
					
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
	
	public void setLocation(Location location) {
		
		this.location = location;
		
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
