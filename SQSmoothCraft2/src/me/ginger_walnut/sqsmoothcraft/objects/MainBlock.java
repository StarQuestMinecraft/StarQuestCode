package me.ginger_walnut.sqsmoothcraft.objects;

import org.bukkit.Location;
import org.bukkit.craftbukkit.v1_10_R1.CraftWorld;
import org.bukkit.event.entity.CreatureSpawnEvent.SpawnReason;

import me.ginger_walnut.sqsmoothcraft.entites.MainArmorStand;

public class MainBlock {

	public MainArmorStand stand;
	public ShipLocation shipLocation;
	
	public MainBlock(Location location) {
		
		net.minecraft.server.v1_10_R1.World mcWorld = ((CraftWorld)location.getWorld()).getHandle();
		MainArmorStand stand = new MainArmorStand(mcWorld);
		stand.setLocation(location.getX(), location.getY(), location.getZ(), 0.0f, 0.0f);
				
		mcWorld.addEntity(stand, SpawnReason.CUSTOM);
		
		this.stand = stand;
		
	}
	
}
