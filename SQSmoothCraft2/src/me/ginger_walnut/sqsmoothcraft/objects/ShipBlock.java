package me.ginger_walnut.sqsmoothcraft.objects;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.craftbukkit.v1_10_R1.CraftWorld;
import org.bukkit.craftbukkit.v1_10_R1.entity.CraftPlayer;
import org.bukkit.entity.Player;

import net.minecraft.server.v1_10_R1.EntityArmorStand;
import net.minecraft.server.v1_10_R1.PacketPlayOutSpawnEntityLiving;

public class ShipBlock {

	public EntityArmorStand stand;
	public ShipLocation shipLocation;
	
	public ShipBlock(Location location, ShipLocation shipLocation) {
		
		net.minecraft.server.v1_10_R1.World mcWorld = ((CraftWorld)location.getWorld()).getHandle();
		EntityArmorStand stand = new EntityArmorStand(mcWorld);
		stand.setLocation(location.getX(), location.getY(), location.getZ(), 0.0f, 0.0f);
		
		PacketPlayOutSpawnEntityLiving fakeArmorStandSpawn = new PacketPlayOutSpawnEntityLiving(stand);
		
		for (Player player : Bukkit.getOnlinePlayers()) {
			
			((CraftPlayer)player).getHandle().playerConnection.sendPacket(fakeArmorStandSpawn);
			
		}
		
		this.stand = stand;
		
		this.shipLocation = shipLocation;
		
	}
	
}
