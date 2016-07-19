package com.starquestminecraft.sqtechbase.listeners;

import net.countercraft.movecraft.event.CraftReleaseEvent;
import net.countercraft.movecraft.utils.MovecraftLocation;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import com.starquestminecraft.sqtechbase.SQTechBase;
import com.starquestminecraft.sqtechbase.objects.GUIBlock;
import com.starquestminecraft.sqtechbase.objects.Network;

public class MovecraftEvents implements Listener {

	@EventHandler
	public void onCraftRelease(CraftReleaseEvent event) {
		
		List<Network> networks = new ArrayList<Network>();
		networks.addAll(SQTechBase.networks);
		
		List<Block> newNetworks = new ArrayList<Block>();
		
		for (MovecraftLocation movecraftLocation : event.getCraft().getBlockList()) {
			
			Location location = new Location(event.getCraft().getW(), movecraftLocation.getX(), movecraftLocation.getY(), movecraftLocation.getZ());
			
			if (location.getBlock().hasMetadata("guiblock")) {
				
				int id = location.getBlock().getMetadata("guiblock").get(0).asInt();

				System.out.print(id);
				
				for (Network network : networks) {

					for (GUIBlock guiBlock : network.getGUIBlocks()) {
							
						System.out.print(guiBlock.id);
						
						if (guiBlock.id == id) {

							//if (SQTechBase.networks.contains(network)) {
								
								//SQTechBase.networks.remove(network);
								
							//}
							
							//Network newNetwork = new Network(location.getBlock());
							
							//for (int i = 0; i < newNetwork.getGUIBlocks().size(); i ++) {
								
								//if (newNetwork.getGUIBlocks().get(i).getLocation().equals(location)) {
									
									guiBlock.setLocation(location);
									//newNetworks.add(location.getBlock());
									//List<GUIBlock> GUIBlocks = newNetwork.getGUIBlocks();
									//GUIBlocks.set(i, guiBlock);
									//newNetwork.setGUIBlocks(GUIBlocks);

								//}
								
							//}
							
						}
						
					}
					
				}
				
			}
			
		}
		
		List<Block> detectedBlocks = new ArrayList<Block>();
		
		for (Block block : newNetworks) {
			
			if (!detectedBlocks.contains(block)) {
				
				Network network = new Network(block, true);
				
				for (GUIBlock guiBlock : network.getGUIBlocks()) {
					
					detectedBlocks.add(guiBlock.getLocation().getBlock());
					
				}
				
			}

		}
		
	}
	
}
