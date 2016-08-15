package me.ginger_walnut.sqsmoothcraft.entites;

import me.ginger_walnut.sqsmoothcraft.SQSmoothCraft;
import me.ginger_walnut.sqsmoothcraft.objects.Ship;
import net.minecraft.server.v1_10_R1.Entity;
import net.minecraft.server.v1_10_R1.EntityArmorStand;
import net.minecraft.server.v1_10_R1.EntityHuman;
import net.minecraft.server.v1_10_R1.World;

public class MainArmorStand extends EntityArmorStand {

	public MainArmorStand(World world) {
		
		super(world);

	}

	@Override
	public void g (float sideMot, float forMot) {
		
		try {
			
			if (this.passengers.size() > 0) {
				
				if (this.passengers.get(0) != null && this.passengers.get(0) instanceof EntityHuman) {
					
					Entity passenger = this.passengers.get(0);
					
					EntityHuman player = (EntityHuman) passenger;

					for (Ship ship : SQSmoothCraft.activeShips) {
						
						if (ship.pilot.getName().equals(player.getBukkitEntity().getName())) {
							
							ship.lastPlayerInput = ship.playerInput;
							
							ship.playerInput = player.bg;
							
						}
						
					}		
					
					this.aS = 0.02f;
					super.g(sideMot,  forMot);
					
				}
				
			}
			
		} catch (Exception e) {
			
			e.printStackTrace();
			
		}
		
	}
	
}
