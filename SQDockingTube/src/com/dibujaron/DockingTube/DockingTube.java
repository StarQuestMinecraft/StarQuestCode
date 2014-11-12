package com.dibujaron.DockingTube;

import java.util.ArrayList;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.Sign;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.plugin.java.JavaPlugin;

public class DockingTube{
	Block baseblock;
	Sign s;
	BlockFace forward, right, left, back;
	//local door frame blocks, far door frame blocks, toggling blocks
	ArrayList<Block> ldfb, tb;
	BlockFace doorside;


	DockingTube(Sign s, Player p){
		forward = DirectionUtils.getGateDirection(s.getBlock());
		right = DirectionUtils.getBlockFaceRight(forward);
		left = DirectionUtils.getBlockFaceLeft(forward);
		back = DirectionUtils.getBlockFaceBack(forward);
		
		ldfb = new ArrayList<Block>();
		tb = new ArrayList<Block>();
		
		baseblock = s.getBlock().getRelative(forward);
		
		if (baseblock.getRelative(left).getTypeId() == 64){
			doorside = left;
		} else if (baseblock.getRelative(right).getTypeId() == 64){
			doorside = right;
		} else {
			p.sendMessage("Not a valid docking tube.");
			p.sendMessage(baseblock.getRelative(left).getType() + "," + baseblock.getRelative(right).getType());
			return;
		}
		
		//load all of the blocks that are the frame for the initial iron door
		ldfb = loadFrame(baseblock);
		
		//opening
		if (s.getLine(1).equals("{" + ChatColor.RED + "RETRACTED" + ChatColor.BLACK + "}") ){
			
			//check to make sure they're buttons
			for(Block b: ldfb){
				Block infront = b.getRelative(forward);
				if (!(infront.getType() == Material.STONE_BUTTON)){
					p.sendMessage("Not a valid docking tube.");
					p.sendMessage(infront.getLocation() + " " + infront.getType());
					return;
				}
			}
			
			Material type = Material.GLASS;
			byte data = 0;
			
			if(p.hasPermission("dockingtube.color")){
				System.out.println("has permission!");
				type = Material.STAINED_GLASS;
				data = getMajorityType(ldfb);
			}
			//load the toggle blocks
			boolean endFound = false;
			int distance = 0;
			Block mainblock = baseblock.getRelative(forward);
			ArrayList<Block> farWoolBlocks = new ArrayList<Block>();
			while (!endFound && distance <= 20){
				int buttonsfound = 0;
				for (Block b: loadFrame(mainblock)){
					if (b.getType() == Material.STONE_BUTTON){
							if (distance == 0){
								tb.add(b);
							} else {
								tb.add(b);
								buttonsfound++;
							}
					} else if (b.getType() == Material.AIR){
						tb.add(b);
					} else {
						p.sendMessage("docking tube obstructed");
						p.sendMessage(b.getLocation() + " " + b.getType());
						endFound = true;
						return;
					}
				}
				mainblock = mainblock.getRelative(forward);
				if (buttonsfound >= 6) endFound = true;
				if (distance == 20){
					p.sendMessage("ERROR: far end not found.");
					return;
				}
				distance++;
			}
			
			//set the toggle blocks
			for (Block b: tb){
				b.setType(type);
				b.setData(data);
			}
			s.setLine(1, "{" + ChatColor.GREEN + "EXTENDED" + ChatColor.BLACK + "}");
			s.update();
			mainblock.getWorld().playSound(mainblock.getLocation(), Sound.PISTON_EXTEND, 2.0F, 1.0F);
		} else if (s.getLine(1).equals("{" + ChatColor.GREEN + "EXTENDED" + ChatColor.BLACK + "}")){
			boolean endFound = false;
			int distance = 0;
			Block mainblock = baseblock.getRelative(forward);
			ArrayList<Block> farWoolBlocks = new ArrayList<Block>();
			while (!endFound && distance <= 21){
				int woolfound = 0;
				for (Block b: loadFrame(mainblock)){
					if (b.getType() == Material.GLASS || b.getType() == Material.STAINED_GLASS){
						tb.add(b);
					} else if(b.getType() == Material.WOOL){
						woolfound++;
						farWoolBlocks.add(b);
					} else {
						p.sendMessage("Docking tube doesn't end in wool- error");
						return;
					}
				}
				mainblock = mainblock.getRelative(forward);
				if (woolfound >= 6) endFound = true;
				if (distance == 21){
					p.sendMessage("ERROR: far end not found.");
					return;
				}
				distance++;
			}
			
			for (Block b: tb){
				b.setType(Material.AIR);
			}
			for (Block b: ldfb){
				b.getRelative(forward).setTypeIdAndData(77, DirectionUtils.getButtonDataAttatchThisBlockFace(forward), true);
			}
			for (Block b: farWoolBlocks){
				b.getRelative(back).setTypeIdAndData(77, DirectionUtils.getButtonDataAttatchThisBlockFace(back), true);
				Block sign = b.getRelative(forward);
				if(sign.getType() == Material.WALL_SIGN){
					s.setLine(1, "{" + ChatColor.RED + "RETRACTED" + ChatColor.BLACK + "}");
					s.update();
				}
			}
			s.setLine(1, "{" + ChatColor.RED + "RETRACTED" + ChatColor.BLACK + "}");
			s.update();
			mainblock.getWorld().playSound(mainblock.getLocation(), Sound.PISTON_RETRACT, 2.0F, 1.0F);
		}
	}
	private byte getMajorityType(ArrayList<Block> ldfb) {
		byte[] retval = new byte[16];
		for(Block b : ldfb){
			byte data = b.getData();
			retval[data]++;
		}
		
		int indexMax = 0;
		byte maxFound = Byte.MIN_VALUE;
		for(int i = 0; i < retval.length; i++){
			byte val = retval[i];
			if(val > maxFound){
				maxFound = val;
				indexMax = i;
			}
		}
		return (byte) indexMax;
	}
	ArrayList<Block> loadFrame(Block baseblock){
		ArrayList<Block> ldfb = new ArrayList<Block>();
		ldfb.add(baseblock);
		ldfb.add(baseblock.getRelative(BlockFace.DOWN));
		ldfb.add(baseblock.getRelative(BlockFace.UP).getRelative(doorside));
		ldfb.add(baseblock.getRelative(doorside).getRelative(doorside));
		ldfb.add(baseblock.getRelative(doorside).getRelative(doorside).getRelative(BlockFace.DOWN));
		ldfb.add(baseblock.getRelative(doorside).getRelative(BlockFace.DOWN).getRelative(BlockFace.DOWN));
		
		return ldfb;
	}
}