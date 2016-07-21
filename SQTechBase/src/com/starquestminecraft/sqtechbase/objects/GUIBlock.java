package com.starquestminecraft.sqtechbase.objects;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.metadata.FixedMetadataValue;

import com.starquestminecraft.sqtechbase.SQTechBase;
import com.starquestminecraft.sqtechbase.gui.GUI;
import com.starquestminecraft.sqtechbase.gui.guiblock.GUIBlockGUI;

public class GUIBlock {
	Location location;
	
	List<ItemStack> imports = new ArrayList<ItemStack>();
	List<ItemStack> exports = new ArrayList<ItemStack>();
	
	public int id = -1;
	
	public boolean exportAll = false;
	
	public GUIBlock(Location location, boolean detectMachine) {
		
		this.location = location;
		
		boolean isMachine = false;
		
		SQTechBase.highestId ++;
		
		
		id = SQTechBase.highestId;
		
		for (Machine machine : SQTechBase.machines) {
			
			if (machine.getGUIBlock().getLocation().equals(location)) {
				
				//machine.guiBlock = this;
				isMachine = true;
				
			}
			
		}
		
		if (!isMachine) {
			
			if (detectMachine) {
				
				for (MachineType machineType : SQTechBase.machineTypes) {
					
					if (machineType.autodetect) {
						
						if (machineType.detectStructure(this)) {
							
							Machine machine = new Machine(0, this, machineType);
							
							SQTechBase.machines.add(machine);
							
						}
						
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
		
	}
	
	public GUI getGUI(Player player) {
		
		return new GUIBlockGUI(player, id);
		
	}
	
	public Location getLocation() {
		
		return location;
		
	}
	
	public void setLocation(Location location) {
		
		this.location = location;
		
	}
	
	public void changeLocation(Location location) {
		
		Location oldLocation = this.location.clone();
		
		this.location = location;
		
		new Network(oldLocation.getBlock().getRelative(1, 0, 0), true);
		new Network(oldLocation.getBlock().getRelative(-1, 0, 0), true);
		new Network(oldLocation.getBlock().getRelative(0, 1, 0), true);
		new Network(oldLocation.getBlock().getRelative(0, -1, 0), true);
		new Network(oldLocation.getBlock().getRelative(0, 0, 1), true);
		new Network(oldLocation.getBlock().getRelative(0, 0, -1), true);
		
		new Network(location.getBlock(), true);
		
		Block oldBlock = oldLocation.getBlock();
		
		if (oldBlock.hasMetadata("guiblock")) {
			
			oldBlock.removeMetadata("guiblock", SQTechBase.getPluginMain());
			
		}
		
		location.getBlock().setMetadata("guiblock", new FixedMetadataValue(SQTechBase.getPluginMain(), id));
		
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
