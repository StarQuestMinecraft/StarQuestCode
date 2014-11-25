package com.dibujaron.lockettevault;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.Sign;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.Event.Result;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.SignChangeEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.plugin.java.JavaPlugin;

public class LocketteVault extends JavaPlugin implements Listener{
	
	public void onEnable(){
		getServer().getPluginManager().registerEvents(this,this);
	}
	
	@EventHandler(priority = EventPriority.LOWEST)
	public void onSignChange(SignChangeEvent event){
		if(event.getLine(0).equalsIgnoreCase("[private]")){
				boolean door = checkForDoor(event.getBlock());
				if(!door){
					event.setLine(0, "Only doors can");
					event.setLine(1, "be locked.");
				}
		}
	}
	
	@EventHandler(priority = EventPriority.HIGHEST)
	public void onPlayerInteract(PlayerInteractEvent event){
		if(event.getAction() == Action.RIGHT_CLICK_BLOCK){
			Material type = event.getClickedBlock().getType();
			if(type == Material.WOODEN_DOOR || type == Material.IRON_DOOR_BLOCK){
				if(event.getPlayer().hasPermission("lockette.admin.bypass")){
					event.setUseInteractedBlock(Result.ALLOW);
					event.setUseItemInHand(Result.ALLOW);
					event.setCancelled(false);
				}
			}
			if(event.getClickedBlock().getState() instanceof InventoryHolder){
				Block[] edges = getEdges(event.getClickedBlock(), false, false);
				for(Block b : edges){
					if(b.getType() == Material.WALL_SIGN){
						BlockFace face = event.getClickedBlock().getFace(b);
						if(face == getFacingBlockFace(b)){
							Sign s = (Sign) b.getState();
							String line1 = s.getLine(1);
							String line0 = s.getLine(0);
							if(line0.equalsIgnoreCase("[private]")){
								Player p = Bukkit.getPlayer(line1);
								if(p != null){
									p.sendMessage("Don't lock container blocks.");
								}
								b.breakNaturally();
							}
						}
					}
				}
			}
		}
	}
	
	@EventHandler(priority = EventPriority.HIGHEST)
	public void onBlockBreak(BlockBreakEvent event){
		if(event.getBlock().getType() == Material.WALL_SIGN){
			Sign s = (Sign) event.getBlock().getState();
			if(s.getLine(0).equalsIgnoreCase("[Private]")){
				if(event.getPlayer().hasPermission("lockette.admin.bypass")){
					event.setCancelled(false);
				}
			}
		}
	}
	
	private boolean checkForDoor(Block block){
		BlockFace dir = getGateDirection(block);
		if(dir == null) return false;
		Block front = block.getRelative(dir);
		if(front.getType() == Material.CHEST || front.getType() == Material.DROPPER || front.getType() == Material.DISPENSER || front.getType() == Material.FURNACE) return false;
		
		Block frontdown = front.getRelative(BlockFace.DOWN);
		if(frontdown.getType()  == Material.IRON_DOOR_BLOCK || frontdown.getType() == Material.WOODEN_DOOR){
			return true;
		}
		
		if(front.getType() == Material.IRON_DOOR_BLOCK || front.getType() == Material.WOODEN_DOOR){
			return true;
		}
		
		Block frontup = front.getRelative(BlockFace.UP);
		if(frontup.getType()  == Material.IRON_DOOR_BLOCK || frontup.getType() == Material.WOODEN_DOOR){
			return true;
		}
		
		return false;
	}
	
	public static BlockFace getGateDirection(Block sign) {
		if (sign.getType().equals(Material.WALL_SIGN)){
			switch (sign.getData()) {
			case 2:
				return BlockFace.SOUTH;
			case 3:
				return BlockFace.NORTH;
			case 4:
				return BlockFace.EAST;
			case 5:
				return BlockFace.WEST;
			default:
				return null;
			}
		}
		return null;
	}
	
	public static Block[] getEdges(Block b, boolean includeDiagnoals, boolean includeSelf){
		
		int size;
		if(includeDiagnoals){
			size = 18;
		} else {
			size = 6;
		}
		
		if(includeSelf){
			size++;
		}
		Block[] retval = new Block[size];
		
		int index = 0;
		//block itself
		if(includeSelf){
			retval[index++] = b;
		}
		
		//faces
		retval[index++] = b.getRelative(0, 1, 0);
		retval[index++] = b.getRelative(0, -1, 0);
		retval[index++] = b.getRelative(1, 0, 0);
		retval[index++] = b.getRelative(-1, 0, 0);
		retval[index++] = b.getRelative(0, 0, 1);
		retval[index++] = b.getRelative(0, 0, -1);
		
		if(includeDiagnoals){
			//edges on the upper side
			retval[index++] = b.getRelative(1, 1, 0);
			retval[index++] = b.getRelative(-1, 1, 0);
			retval[index++] = b.getRelative(0, 1, 1);
			retval[index++] = b.getRelative(0, 1, -1);
			
			//edges on the lower side
			retval[index++] = b.getRelative(1, -1, 0);
			retval[index++] = b.getRelative(-1, -1, 0);
			retval[index++] = b.getRelative(0, -1, 1);
			retval[index++] = b.getRelative(0, -1, -1);
			
			//edges on the same plane
			retval[index++] = b.getRelative(1, 0, 1);
			retval[index++] = b.getRelative(-1, 0, 1);
			retval[index++] = b.getRelative(1, 0, -1);
			retval[index++] = b.getRelative(-1, 0, -1);
		}
		
		return retval;
	}
	
	public static BlockFace getFacingBlockFace(Block sBlock){
		int data = sBlock.getData();
		switch(data){
			case 2:
				return BlockFace.NORTH;
			case 3:
				return BlockFace.SOUTH;
			case 4:
				return BlockFace.WEST;
			case 5:
				return BlockFace.EAST;
			default:
				return null;
		}
	}
}
