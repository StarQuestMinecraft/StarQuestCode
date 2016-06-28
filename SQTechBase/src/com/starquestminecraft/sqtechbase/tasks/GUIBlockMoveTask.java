package com.starquestminecraft.sqtechbase.tasks;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.scheduler.BukkitScheduler;

import com.starquestminecraft.sqtechbase.GUIBlock;
import com.starquestminecraft.sqtechbase.Machine;
import com.starquestminecraft.sqtechbase.Network;
import com.starquestminecraft.sqtechbase.SQTechBase;

public class GUIBlockMoveTask {

	@SuppressWarnings("deprecation")
	public void run() {
		
		BukkitScheduler scheduler = Bukkit.getServer().getScheduler();
		
		scheduler.scheduleAsyncRepeatingTask(SQTechBase.getPluginMain(), new Runnable() {
			
			@Override
			public void run() {
				
				List<Location> oldLocations = new ArrayList<Location>();
				oldLocations.addAll(SQTechBase.oldLocationQueue);
				
				List<Network> networks = new ArrayList<Network>();
				List<GUIBlock> guiBlocks = new ArrayList<GUIBlock>();
				
				for (Network network : SQTechBase.networks) {
					
					for (GUIBlock guiBlock : network.getGUIBlocks()) {
						
						for (int i = 0; i < SQTechBase.oldLocationQueue.size(); i ++) {
							
							if (guiBlock.getLocation().equals(oldLocations.get(i))) {
								
								guiBlocks.add(guiBlock);
								networks.add(network);
								
							}
							
						}
						
					}
					
				}
				
				List<Location> newLocations = new ArrayList<Location>();
				newLocations.addAll(SQTechBase.newLocationQueue);
				
				HashMap<Network, List<Block>> oldBlocks = new HashMap<Network, List<Block>>();
				HashMap<Network, List<Block>> newBlocks = new HashMap<Network, List<Block>>();
				
				for (int i = 0; i < guiBlocks.size(); i ++) {

					Network network = networks.get(i);
					GUIBlock guiBlock = guiBlocks.get(i);
					
					guiBlock.setLocation(newLocations.get(i));
								
					for (int j = 0; j < network.detectedGUIBlocks.size(); j ++) {
									
						if (network.detectedGUIBlocks.get(j).getLocation().equals(oldLocations.get(i))) {
							
							if (oldBlocks.containsKey(network)) {
								
								List<Block> blocks = new ArrayList<Block>();
								
								blocks.addAll(oldBlocks.get(network));
								oldBlocks.remove(network);
								
								blocks.add(network.detectedGUIBlocks.get(j));
								oldBlocks.put(network, blocks);
								
							} else {
								
								List<Block> blocks = new ArrayList<Block>();
								
								blocks.add(network.detectedGUIBlocks.get(j));
								oldBlocks.put(network, blocks);
								
							}
							
							if (newBlocks.containsKey(network)) {
								
								List<Block> blocks = new ArrayList<Block>();
								
								blocks.addAll(newBlocks.get(network));
								newBlocks.remove(network);
								
								blocks.add(newLocations.get(i).getBlock());
								newBlocks.put(network, blocks);
								
							} else {
								
								List<Block> blocks = new ArrayList<Block>();
								
								blocks.add(newLocations.get(i).getBlock());
								newBlocks.put(network, blocks);
								
							}
							
							network.detectedGUIBlocks.add(j, newLocations.get(i).getBlock());
										
							network.detectedWireBlocks.clear();
										
						}
			
					}
					
				}
				
				for (Network network : oldBlocks.keySet()) {
					
					network.detectedGUIBlocks.removeAll(oldBlocks.get(network));
					network.detectedGUIBlocks.addAll(newBlocks.get(network));
					
				}
				
				if (newLocations.size() > 0) {
					
					for (Location newLocation : newLocations) {
						
						new Network(newLocation.getBlock());
						
					}
				
					SQTechBase.newLocationQueue.removeAll(newLocations);
					SQTechBase.oldLocationQueue.removeAll(oldLocations);
					
				}
				
			}
			
		}, 0, 0);
		
	}
	
}
