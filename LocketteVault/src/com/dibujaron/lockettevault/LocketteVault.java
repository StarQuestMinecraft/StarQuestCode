package com.dibujaron.lockettevault;

import java.awt.List;
import java.util.ArrayList;

import net.md_5.bungee.api.ChatColor;

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
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.block.SignChangeEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.plugin.java.JavaPlugin;

public class LocketteVault extends JavaPlugin implements Listener{
	
	ArrayList<Material> doors = new ArrayList<Material>();
	

	
	public void onEnable(){
		getServer().getPluginManager().registerEvents(this,this);
		doors.add(Material.WOODEN_DOOR);
		doors.add(Material.IRON_DOOR_BLOCK);
		doors.add(Material.ACACIA_DOOR);
		doors.add(Material.BIRCH_DOOR);
		doors.add(Material.SPRUCE_DOOR);
		doors.add(Material.DARK_OAK_DOOR);
		doors.add(Material.JUNGLE_DOOR);		
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
		if(event.getBlock().getType().equals(Material.WALL_SIGN) || event.getBlock().getType().equals(Material.SIGN_POST) || event.getBlock().getType().equals(Material.SIGN)){
			if(event.getLine(0).toLowerCase().contains("private") && (doors.contains(event.getBlock().getRelative(-1, 1, 0).getType()) || doors.contains(event.getBlock().getRelative(1, 1, 0).getType()) || doors.contains(event.getBlock().getRelative(0, 1, -1).getType()) || doors.contains(event.getBlock().getRelative(0, 1, 1).getType()))) {
				event.setLine(0, "[?]");
				event.setCancelled(true);
				event.getPlayer().sendMessage(ChatColor.RED +"[Lockette] Conflict with an existing protected door.");
			}
		}
	}
	
	@EventHandler(priority = EventPriority.HIGHEST)
	public void onPlayerInteract(PlayerInteractEvent event){
		if(event.getAction() == Action.RIGHT_CLICK_BLOCK){
			Material type = event.getClickedBlock().getType();
			if(doors.contains(type)){
				if(event.getPlayer().hasPermission("lockette.admin.bypass")){
					event.setUseInteractedBlock(Result.ALLOW);
					event.setUseItemInHand(Result.ALLOW);
					event.setCancelled(false);
				} else {
					if(checkForBrokenSign(event.getClickedBlock())){
						event.setCancelled(true);
						event.getPlayer().sendMessage("There was a broken lock sign on this door. Try again.");
					}
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
	
	private boolean checkForBrokenSign(Block clickedBlock) {
		Block down = clickedBlock.getRelative(BlockFace.DOWN);
		Block up = clickedBlock.getRelative(BlockFace.UP);
		Material upType = up.getType();
		Material downType = down.getType();
		if(doors.contains(upType)){
			//sign is mounted on the low door
			if(checkForBrokenSignAroundBlock(up) || checkForBrokenSignAroundBlock(clickedBlock)) return true;
		}
		else if(doors.contains(downType)){
			//sign is mounted on high door
			if(checkForBrokenSignAroundBlock(down) || checkForBrokenSignAroundBlock(clickedBlock)) return true;
		}
		return false;	
	}
	
	private boolean checkForBrokenSignAroundBlock(Block doorBlock){
		for(Block b : getEdges(doorBlock, true, false)){
			if(b.getType() == Material.WALL_SIGN || b.getType() == Material.SIGN_POST){
				Sign s = (Sign) b.getState();
				if(s.getLine(0).toLowerCase().contains("private") && !s.getLine(0).toLowerCase().contains("[private]")){
					s.setLine(0, "[Private]");
					s.update();
					return true;
				}
			}
		}
		return false;
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
	
	@EventHandler(priority = EventPriority.HIGHEST)
	public void onBlockPlace(BlockPlaceEvent event){

	}
	
	private boolean checkForDoor(Block block){
		BlockFace dir = getGateDirection(block);
		if(dir == null) return false;
		Block front = block.getRelative(dir);
		if(front.getType() == Material.CHEST || front.getType() == Material.DROPPER || front.getType() == Material.DISPENSER || front.getType() == Material.FURNACE) return false;
		
		Block frontdown = front.getRelative(BlockFace.DOWN);
		if(doors.contains(frontdown.getType())){
			return true;
		}
		
		if(doors.contains(front.getType())){
			return true;
		}
		
		Block frontup = front.getRelative(BlockFace.UP);
		if(doors.contains(frontup.getType())){
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
