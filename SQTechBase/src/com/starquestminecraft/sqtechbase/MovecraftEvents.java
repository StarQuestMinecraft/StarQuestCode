package com.starquestminecraft.sqtechbase;

import net.countercraft.movecraft.event.CraftReleaseEvent;
import net.countercraft.movecraft.utils.MovecraftLocation;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Location;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class MovecraftEvents implements Listener {

	@EventHandler
	public void onCraftRelease(CraftReleaseEvent event) {
		
		List<Network> transferedNetworks = new ArrayList<Network>();
		
		for (MovecraftLocation movecraftLocation : event.getCraft().getBlockList()) {
			
			Location location = new Location(event.getCraft().getW(), movecraftLocation.getX(), movecraftLocation.getY(), movecraftLocation.getZ());
			
			if (location.getBlock().hasMetadata("guiblock")) {
				
				int id = location.getBlock().getMetadata("guiblock").get(0).asInt();
				
				for (Network network : SQTechBase.networks) {
					
					if (!transferedNetworks.contains(network)) {
						
						for (GUIBlock guiBlock : network.getGUIBlocks()) {
							
							if (guiBlock.id == id) {
								
								transferedNetworks.add(network);
								
								Location transfer = location.subtract(guiBlock.getLocation());
								
								for (GUIBlock networkGUIBlock : network.getGUIBlocks()) {
									
									networkGUIBlock.setLocation(networkGUIBlock.getLocation().add(transfer));
									
									Location guiBlockLocation = networkGUIBlock.getLocation();
									guiBlockLocation.setWorld(event.getCraft().getW());
									networkGUIBlock.setLocation(guiBlockLocation);
									
									if (networkGUIBlock != guiBlock) {
										
										new Network(networkGUIBlock.getLocation().getBlock());
										
									}
									
								}
								
								new Network(guiBlock.getLocation().getBlock());
								
							}
							
						}
						
					}
					
				}
				
			}
			
		}
		
	}
	
}
