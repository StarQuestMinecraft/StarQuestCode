package com.dibujaron.DockingTube;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.Sign;
import org.bukkit.entity.Player;

public class DockingTube{
	Block baseblock;
	Sign s;
	BlockFace forward, right, left, back;
	//local door frame blocks, far door frame blocks, toggling blocks
	ArrayList<Block> ldfb, tb;
	BlockFace doorside;


	DockingTube(Sign s, Player p)
	{
		org.bukkit.material.Sign signMaterial = (org.bukkit.material.Sign) s.getBlock().getState().getData();
		
		if(signMaterial.isWallSign() && s.getBlock().getRelative(signMaterial.getAttachedFace()).getType() == Material.DROPPER)
		{
			new VerticalDockingTube(s, p);
			return;
		}
		
		forward = DirectionUtils.getGateDirection(s.getBlock());
		right = DirectionUtils.getBlockFaceRight(forward);
		left = DirectionUtils.getBlockFaceLeft(forward);
		back = DirectionUtils.getBlockFaceBack(forward);
		
		ldfb = new ArrayList<Block>();
		tb = new ArrayList<Block>();
		
		baseblock = s.getBlock().getRelative(forward);
		
		List<Material> doors = new ArrayList<Material>();
		doors.add(Material.WOODEN_DOOR);
		doors.add(Material.BIRCH_DOOR);
		doors.add(Material.SPRUCE_DOOR);
		doors.add(Material.JUNGLE_DOOR);
		doors.add(Material.ACACIA_DOOR);
		doors.add(Material.DARK_OAK_DOOR);
		doors.add(Material.IRON_DOOR);
		
		if (doors.contains(baseblock.getRelative(left).getType())){
			doorside = left;
		} else if (doors.contains(baseblock.getRelative(right).getType())){
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
			
			Material maj = VerticalDockingTube.getMajorityMaterialType(ldfb);
			Material type = Material.GLASS;
			byte data = 0;
			if(maj == Material.WOOL)
			{
				// Colored tube perk
				if(p.hasPermission("dockingtube.color"))
				{
					type = Material.STAINED_GLASS;
					data = getMajorityType(ldfb);
				}
			}
			
			//load the toggle blocks
			boolean endFound = false;
			int distance = 0;
			Block mainblock = baseblock.getRelative(forward);
			ArrayList<Block> farWoolBlocks = new ArrayList<Block>();
			while (!endFound && distance <= 20){
				int buttonsfound = 0;
				for (Block b: loadFrame(mainblock))
				{
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
						p.sendMessage("Docking tube obstructed");
						p.sendMessage(b.getLocation().getBlockX() + ", " + b.getLocation().getBlockY() + ", " + b.getLocation().getBlockZ() + ", " + b.getType());
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
			mainblock.getWorld().playSound(mainblock.getLocation(), Sound.BLOCK_PISTON_EXTEND, 2.0F, 1.0F);
		} else if (s.getLine(1).equals("{" + ChatColor.GREEN + "EXTENDED" + ChatColor.BLACK + "}")){
			boolean endFound = false;
			int distance = 0;
			Block mainblock = baseblock.getRelative(forward);
			ArrayList<Block> farEndBlocks = new ArrayList<Block>();
			while (!endFound && distance <= 21){
				int blockfound = 0;
				for (Block b: loadFrame(mainblock)){
					if (b.getType() == Material.GLASS || b.getType() == Material.STAINED_GLASS){
						tb.add(b);
					} else if(b.getType().isSolid()){
						blockfound++;
						farEndBlocks.add(b);
					}
				}
				mainblock = mainblock.getRelative(forward);
				if (blockfound >= 6) endFound = true;
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
			for (Block b: farEndBlocks){
				b.getRelative(back).setTypeIdAndData(77, DirectionUtils.getButtonDataAttatchThisBlockFace(back), true);
				Block sign = b.getRelative(forward);
				if(sign.getType() == Material.WALL_SIGN){
					s.setLine(1, "{" + ChatColor.RED + "RETRACTED" + ChatColor.BLACK + "}");
					s.update();
				}
			}
			s.setLine(1, "{" + ChatColor.RED + "RETRACTED" + ChatColor.BLACK + "}");
			s.update();
			mainblock.getWorld().playSound(mainblock.getLocation(), Sound.BLOCK_PISTON_CONTRACT, 2.0F, 1.0F);
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