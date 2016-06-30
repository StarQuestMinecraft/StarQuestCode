package com.starquestminecraft.sqtechbase;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.scheduler.BukkitScheduler;

import com.starquestminecraft.sqtechbase.gui.GUIBlockGUI;

public class Network {

	List<Block> wires = new ArrayList<Block>();
	List<GUIBlock> GUIBlocks = new ArrayList<GUIBlock>();
	
	List<Block> attemptedBlocks = new ArrayList<Block>();
	List<Block> attemptableBlocksFrom = new ArrayList<Block>();
	public List<Block> detectedGUIBlocks = new ArrayList<Block>();
	public List<Block> detectedWireBlocks = new ArrayList<Block>();
	
	public Network(Block startingBlock) {
		
		if (startingBlock.getType().equals(Material.LAPIS_BLOCK) || startingBlock.getType().equals(Material.STAINED_GLASS) || startingBlock.getType().equals(Material.GLASS)) {
			
			attemptedBlocks.clear();
			attemptableBlocksFrom.clear();
			detectedGUIBlocks.clear();
			detectedWireBlocks.clear();
			
			attemptedBlocks.add(startingBlock);
			
			if (startingBlock.getType().equals(Material.LAPIS_BLOCK)) {
				
				detectedGUIBlocks.add(startingBlock);
				
			} else if (startingBlock.getType().equals(Material.STAINED_GLASS) || startingBlock.getType().equals(Material.GLASS)) {
				
				detectedWireBlocks.add(startingBlock);
				
			}
			
			detectSideBlocks(startingBlock);
			
			boolean detectable = true;
			
			if (attemptableBlocksFrom.size() > 0) {
				
				int i = 0;
				
				do {

					i ++;
					
					detectSideBlocks(attemptableBlocksFrom.get(0));
					attemptableBlocksFrom.remove(0);
					
					if (attemptableBlocksFrom.size() <= 0) {
						
						detectable = false;
						
					}
					
					if (i > 2500) {
						
						detectable = false;
						
					}
					
				} while (detectable);
				
			}
			
			List<Network> mergeNetworks = new ArrayList<Network>();
			
			for (Block block : detectedWireBlocks) {
				
				wires.add(block);
				
				List<Network> networks = new ArrayList<Network>();
				networks.addAll(SQTechBase.networks);
				
				for (Network network : networks) {
					
					for (Block wire : network.detectedWireBlocks) {
						
						if (wire.getLocation().equals(block.getLocation())) {
							
							mergeNetworks.add(network);
							
						}
						
					}
					
				}
				
			}
			
			for (Block block : detectedGUIBlocks) {
				
				GUIBlocks.add(new GUIBlock(new GUIBlockGUI(), block.getLocation()));
				
				List<Network> networks = new ArrayList<Network>();
				networks.addAll(SQTechBase.networks);
				
				for (Network network : networks) {
					
					for (GUIBlock guiBlock : network.GUIBlocks) {
						
						if (guiBlock.getLocation().equals(block.getLocation())) {
							
							mergeNetworks.add(network);
							
						}
						
					}
					
				}
				
			}
			
			for (Network network : mergeNetworks) {
				
				merge(network);
				
			}
			
			SQTechBase.networks.add(this);
			
		}

	}
	
	@SuppressWarnings("deprecation")
	private void detectSideBlocks(Block block) {
		
		List<Block> sideBlocks = new ArrayList<Block>();
		
		sideBlocks.add(block.getRelative(1, 0, 0));
		sideBlocks.add(block.getRelative(-1, 0, 0));
		sideBlocks.add(block.getRelative(0, 1, 0));
		sideBlocks.add(block.getRelative(0, -1, 0));
		sideBlocks.add(block.getRelative(0, 0, 1));
		sideBlocks.add(block.getRelative(0, 0, -1));
		
		for (Block sideBlock : sideBlocks) {
			
			if (!attemptedBlocks.contains(sideBlock)) {
		
				attemptedBlocks.add(sideBlock);
				
				if (sideBlock.getType().equals(Material.LAPIS_BLOCK)) {
					
					detectedGUIBlocks.add(sideBlock);	
					attemptableBlocksFrom.add(sideBlock);

				} else if (sideBlock.getType().equals(Material.STAINED_GLASS) && (block.getType().equals(Material.LAPIS_BLOCK) || block.getType().equals(Material.GLASS) || block.getData() == sideBlock.getData())) {
					
					detectedWireBlocks.add(sideBlock);
					attemptableBlocksFrom.add(sideBlock);
					
				} else if (sideBlock.getType().equals(Material.GLASS)) {
					
					detectedWireBlocks.add(sideBlock);
					attemptableBlocksFrom.add(sideBlock);
					
				}
				
			}
			
		}
		
	}
	
	public void merge(final Network network) {
			
		BukkitScheduler scheduler = Bukkit.getScheduler();
		scheduler.scheduleSyncDelayedTask(SQTechBase.getPluginMain(), new Runnable() {
			
			public void run() {
			
				SQTechBase.networks.remove(network);
			
			}
			
		}, 1);
		


		for (GUIBlock guiBlock : network.GUIBlocks) {
			
			for (GUIBlock selfGUIBlock : GUIBlocks) {
				
				if (guiBlock.getLocation().equals(selfGUIBlock.getLocation())) {
					
					selfGUIBlock.exports = guiBlock.exports;
					selfGUIBlock.imports = guiBlock.imports;
					
					for (Machine machine : SQTechBase.machines) {
						
						if (machine.guiBlock.equals(guiBlock)) {
														
							machine.guiBlock = selfGUIBlock;
							
						}
						
					}
					
				}
				
			}
			
		}
		
	}
	
	public List<GUIBlock> getGUIBlocks() {
		
		return GUIBlocks;
		
	}
	
	public List<Block> getWires() {
		
		return wires;
		
	}
	
}
