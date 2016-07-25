package me.ginger_walnut.sqsmoothcraft.objects;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Location;
import org.bukkit.entity.Player;

import me.ginger_walnut.sqsmoothcraft.SQSmoothCraft;

public class Ship {

	public MainBlock mainBlock;
	public List<ShipBlock> shipBlocks = new ArrayList<ShipBlock>();
	
	public float playerInput;
	public float lastPlayerInput;
	
	public Player pilot;
	
	public float speed;
	
	public Location location;
	
	public ShipDirection pointingDirection;
	
	public Ship(Player player) {
		
		mainBlock = new MainBlock(player.getLocation());	

		location = player.getLocation();
		
		pointingDirection = new ShipDirection(player.getLocation().getYaw(), player.getLocation().getPitch());
		
		pilot = player;
		
		mainBlock.stand.getBukkitEntity().setPassenger(player);
		
		shipBlocks.add(new ShipBlock(player.getLocation().clone().add(1, 0, 0), new ShipLocation(1, 0, 0)));
		shipBlocks.add(new ShipBlock(player.getLocation().clone().add(-1, 0, 0), new ShipLocation(-1, 0, 0)));
		shipBlocks.add(new ShipBlock(player.getLocation().clone().add(0, 0, 1), new ShipLocation(0, 0, 1)));
		shipBlocks.add(new ShipBlock(player.getLocation().clone().add(0, 0, -1), new ShipLocation(0, 0, -1)));
		
		SQSmoothCraft.activeShips.add(this);
	}
	
}
