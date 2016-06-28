package com.dibujaron.DockingTube;

import java.util.ArrayList;
import java.util.HashMap;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.Dropper;
import org.bukkit.block.Sign;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public class VerticalDockingTube
{
	ArrayList<Block> frame, toggleBlock;

	@SuppressWarnings("deprecation")
	public VerticalDockingTube(Sign sign, Player player)
	{
		Block opening = sign.getBlock().getRelative(BlockFace.DOWN);
		Material openingMaterial  = opening.getType();
		byte openingData = opening.getData();

		frame = new ArrayList<Block>();
		toggleBlock = new ArrayList<Block>();

		if(sign.getLine(1).equals("{" + ChatColor.RED + "RETRACTED" + ChatColor.BLACK + "}"))
			if(opening.getType() == Material.AIR || opening.getType().isSolid() == false)
			{
				player.sendMessage("Not a valid docking tube.");
				return;
			}

		byte signDirection = sign.getBlock().getData();
		org.bukkit.material.Sign signMaterial = (org.bukkit.material.Sign) sign.getData();
		
		Dropper inventory = (Dropper) sign.getBlock().getRelative(signMaterial.getAttachedFace()).getState();
		ArrayList<ItemStack> ladders = new ArrayList<ItemStack>(); // 9 possible dropper slots

		frame = loadFrame(opening);

		if (sign.getLine(1).equals("{" + ChatColor.RED + "RETRACTED" + ChatColor.BLACK + "}"))
		{	
			// makes sure the buttons are placed
			for(Block b : frame)
			{
				Block below = b.getRelative(BlockFace.DOWN);
				if(below.getType() != Material.STONE_BUTTON)
				{
					player.sendMessage("Not a valid docking tube.");
					player.sendMessage("Missing buttons.");
					return;
				}
			}
			// Gets ladders from the dropper
			for(ItemStack stack : inventory.getInventory().getContents())
			{
				if(stack != null && stack.getType() ==  Material.LADDER)
					ladders.add(stack);
			}
			
			if(ladders.isEmpty())
			{
				player.sendMessage("You don't have enough ladders in the dropper!");
				return;
			}

			Material maj = getMajorityMaterialType(frame);
			Material type = Material.GLASS;
			byte data = 0;
			if(maj == Material.WOOL)
			{
				// Colored tube perk
				if(player.hasPermission("dockingtube.color"))
				{
					type = Material.STAINED_GLASS;
					data = getMajorityType(frame);
				}
			}

			// load in empty space between sides, as toggleBlocks
			int distance = 0;
			boolean endFound = false;
			Block mainblock = opening.getRelative(BlockFace.DOWN);

			int amountOfLadders = 0;
			
			for(ItemStack item : ladders)
				amountOfLadders =+ item.getAmount();

			
			while(endFound == false && distance <= 35)
			{
				int buttonsfound = 0;
				for(Block b : loadFrame(mainblock))
				{ 	// Scans blocks to find other end
					if(b.getType() == Material.STONE_BUTTON)
					{
						if (distance == 0)
							toggleBlock.add(b); // adds empty block to the toggle
						else 					// block list
						{
							toggleBlock.add(b);
							buttonsfound++; // counts buttons
						}
					}
					else if (b.getType() == Material.AIR)
						toggleBlock.add(b);
					else 
					{
						player.sendMessage("Docking tube obstructed");
						player.sendMessage(b.getLocation().getBlockX() + ", " + b.getLocation().getBlockY() + ", " + b.getLocation().getBlockZ() + ", " + b.getType());
						endFound = true;
						return;
					}
				}

				mainblock = mainblock.getRelative(BlockFace.DOWN);
				if(buttonsfound >= 4) endFound = true;
				if (distance == 35){
					player.sendMessage("ERROR: far end not found.");
					return;
				}
				distance++;
			}
			
			if((distance + 1) > amountOfLadders)
			{
				player.sendMessage("Not enough ladders in the dropper!");
				return;
			}

			// set the air blocks to glass
			for (Block b: toggleBlock)
			{
				b.setType(type);
				b.setData(data);
			}
			
			inventory.getBlock().getRelative(BlockFace.DOWN, 2).setType(openingMaterial);
			opening.setType(Material.AIR);
			inventory.getBlock().getRelative(BlockFace.DOWN, 2).setData(openingData);
			
			for(int i = 0; i <= distance; i++)
			{
				Block ladder = opening.getRelative(0, -(i), 0);
				ladder.setType(Material.LADDER, false);
				ladder.setData(signDirection, false);
				removeItemsFromInventory(Material.LADDER, inventory.getInventory(), 1);
			}

			sign.setLine(1, "{" + ChatColor.GREEN + "EXTENDED" + ChatColor.BLACK + "}");
			sign.update();
			mainblock.getWorld().playSound(mainblock.getLocation(), Sound.BLOCK_PISTON_EXTEND, 2.0F, 1.0F);
		}
		else if (sign.getLine(1).equals("{" + ChatColor.GREEN + "EXTENDED" + ChatColor.BLACK + "}"))
		{
			boolean endFound = false;
			int distance = 0;
			Block mainblock = opening.getRelative(BlockFace.DOWN);
			ArrayList<Block> farEndBlocks = new ArrayList<Block>();
			
			Block hatch = inventory.getBlock().getRelative(BlockFace.DOWN, 2);
			Material hatchMaterial = hatch.getType();
			byte hatchData = hatch.getData();
			
			int ladderAmount = 0;
			
			while (!endFound && distance <= 36)
			{	
				int blockfound = 0;
				for (Block b : loadFrame(mainblock))
				{
					if (b.getType() == Material.GLASS || b.getType() == Material.STAINED_GLASS 
							|| b.getLocation().equals(hatch.getLocation()))
					{
						toggleBlock.add(b);
					}
					else if(b.getType().isSolid())
					{
						blockfound++;
						farEndBlocks.add(b);
					}
				}
				mainblock = mainblock.getRelative(BlockFace.DOWN);
				if (blockfound >= 4) endFound = true;
				if (distance == 36)
				{
					player.sendMessage("ERROR: far end not found.");
					return;
				}
				distance++;
				
				for(int i = 0; i <= distance; i++)
				{
					if(opening.getRelative(BlockFace.DOWN, i).getType() == Material.LADDER)
					{
						ladderAmount++;
						opening.getRelative(BlockFace.DOWN, i).setType(Material.AIR, false);
					}
				}
				
				// change glass back to air
				for (Block b : toggleBlock){
					b.setType(Material.AIR);
				}
				
				for(Block b : frame)
				{
					b.getRelative(BlockFace.DOWN).setType(Material.STONE_BUTTON);
					b.getRelative(BlockFace.DOWN).setData((byte) 0);
				}
					
				for (Block b: farEndBlocks)
				{
					b.getRelative(BlockFace.UP).setType(Material.STONE_BUTTON);
					b.getRelative(BlockFace.UP).setData((byte) 5);
				}
				
				sign.setLine(1, "{" + ChatColor.RED + "RETRACTED" + ChatColor.BLACK + "}");
				sign.update();
				mainblock.getWorld().playSound(mainblock.getLocation(), Sound.BLOCK_PISTON_CONTRACT, 2.0F, 1.0F);
			}
			HashMap<Integer,ItemStack> itemsNotAdded = addItemsToInventory(Material.LADDER, inventory.getInventory(), ladderAmount);
			
			opening.setType(hatchMaterial);
			opening.setData(hatchData);
			
			for(Integer i : itemsNotAdded.keySet())
			{
				opening.getWorld().dropItem(opening.getLocation().add(0.5, 0.05, 0.5), itemsNotAdded.get(i));
			}
			
			if(!itemsNotAdded.isEmpty())
				player.sendMessage("Your dropper was full so the ladders were placed on the floor.");
		}
	}

	@SuppressWarnings("deprecation")
	private byte getMajorityType(ArrayList<Block> ldfb)
	{
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

	public static Material getMajorityMaterialType(ArrayList<Block> blocks)
	{
		HashMap<String, Integer> materialMap = new HashMap<String, Integer>();
		for(Block b : blocks)
		{
			Integer amount = materialMap.get(b.getType().toString());
			if(amount == null)
				amount = 0;

			amount++;
			materialMap.put(b.getType().toString(), amount);
		}

		Material max = null;
		int maxFound = 0;

		for(String mat : materialMap.keySet())
		{
			if(maxFound < materialMap.get(mat))
			{
				maxFound = materialMap.get(mat);
				max = Material.getMaterial(mat);
			}
		}

		return max;
	}

	ArrayList<Block> loadFrame(Block baseblockOpening)
	{
		ArrayList<Block> frame = new ArrayList<Block>();
		frame.add(baseblockOpening.getRelative(BlockFace.NORTH));
		frame.add(baseblockOpening.getRelative(BlockFace.EAST));
		frame.add(baseblockOpening.getRelative(BlockFace.SOUTH));
		frame.add(baseblockOpening.getRelative(BlockFace.WEST));

		return frame;
	}
	
	/**
	 * 
	 * @param type the Material type of the item
	 * @param inv the Inventory to edit
	 * @param amount the amount to remove
	 * @return the amount of items successfully removed from the Inventory
	 */
	public static int removeItemsFromInventory(Material type, Inventory inv, int amount)
	{
		int amountRemoved = 0;

		for (int i = 0; i < inv.getSize(); i++)
		{
			ItemStack item = inv.getItem(i);
			if ((item != null) && item.getType() == type)
			{
				int amtLeft = amount - amountRemoved;

				item.setAmount(item.getAmount() - amtLeft);
				inv.setItem(i, item);
				amountRemoved = amount;
				break;
			}
		}
		return amountRemoved;
	}
	
	public static HashMap<Integer,ItemStack> addItemsToInventory(Material item, Inventory inv, int amount)
	{
		HashMap<Integer,ItemStack> itemsNotAdded = new HashMap<Integer,ItemStack>();
		
		itemsNotAdded = inv.addItem(new ItemStack(item, amount));
		return itemsNotAdded;
	}
}
