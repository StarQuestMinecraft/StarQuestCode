package com.starquestminecraft.sqtechbase.tasks;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.scheduler.BukkitScheduler;

import com.starquestminecraft.sqtechbase.SQTechBase;
import com.starquestminecraft.sqtechbase.objects.Machine;

public class StructureTask extends Thread {

	public void run() {
		
		BukkitScheduler scheduler = Bukkit.getServer().getScheduler();
		
		scheduler.scheduleSyncRepeatingTask(SQTechBase.getPluginMain(), new Runnable() {
			
			@Override
			public void run() {
				
				List<Machine> removeMachines = new ArrayList<Machine>();
				
				for (Machine machine : SQTechBase.machines) {
					
					if (!machine.detectStructure()) {
						
						if (machine.getGUIBlock().getLocation().getBlock().hasMetadata("guiblock")) {
							
							removeMachines.add(machine);
							
						} else {
							
							machine.enabled = false;
							
						}
						
					} else {
						
						if (machine.getGUIBlock().getLocation().getBlock().hasMetadata("guiblock")) {
							
							machine.enabled = true;
							
						} 
						
					}
					
				}
				
				SQTechBase.machines.removeAll(removeMachines);
				
			}
			
		}, 0, 59);
		
	}
	
}
