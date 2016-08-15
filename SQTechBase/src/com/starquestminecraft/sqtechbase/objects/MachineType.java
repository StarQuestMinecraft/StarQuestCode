package com.starquestminecraft.sqtechbase.objects;

import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import com.starquestminecraft.sqtechbase.gui.GUI;

public class MachineType {

	int maxEnergy = 0;
	public String name = "Machine Type";
	
	public boolean autodetect = true;
	
	public boolean defaultExport = false;
	public boolean defaultImport = false;
	
	public MachineType(int maxEnergy) {
		
		this.maxEnergy = maxEnergy;
		
	}
	
	public void initialize(Machine machine) {
		
	}
	
	public boolean detectStructure(GUIBlock guiBlock) {
		
		return false;
		
	}
	
	public GUI getGUI(Player player, int id) {
		
		return new GUI(player, id);
		
	}
	
	public int getMaxEnergy() {
		
		return maxEnergy;
		
	}
	
	public String getName() {
		
		return name;
		
	}
	
	public int getSpaceLeft(Machine machine, ItemStack itemStack) {
		
		return 0;
		
	}
	
	public void sendItems(Machine machine, ItemStack itemStack) {

	}
	
	public void updateEnergy(Machine machine) {
		
	}
	
	public void updateLiquid(Machine machine) {
		
	}
	
}
