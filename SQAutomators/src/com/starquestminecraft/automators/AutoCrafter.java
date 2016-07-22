package com.starquestminecraft.automators;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import com.starquestminecraft.sqtechbase.SQTechBase;
import com.starquestminecraft.sqtechbase.gui.GUI;
import com.starquestminecraft.sqtechbase.objects.GUIBlock;
import com.starquestminecraft.sqtechbase.objects.Machine;
import com.starquestminecraft.sqtechbase.objects.MachineType;
import com.starquestminecraft.sqtechbase.util.InventoryUtils;

public class AutoCrafter extends MachineType {

	public AutoCrafter(int maxEnergy) {
		super(maxEnergy);
		name = "AutoCrafter";
	}
	
	@Override
	public boolean detectStructure(GUIBlock guiBlock) {
		
		Block mainBlock = guiBlock.getLocation().getBlock();
		Block middleBlock = mainBlock.getRelative(BlockFace.DOWN);
		Block bottomBlock = middleBlock.getRelative(BlockFace.DOWN);
		
		if(mainBlock.getType().equals(Material.LAPIS_BLOCK)) {
			
			if(middleBlock.getType().equals(Material.SPONGE)) {
				
				if(bottomBlock.getType().equals(Material.LAPIS_BLOCK)) {
					
					if(checkDirection(BlockFace.NORTH, mainBlock, middleBlock, bottomBlock)) {
						return true;
					}
					
					if(checkDirection(BlockFace.EAST, mainBlock, middleBlock, bottomBlock)) {
						return true;
					}
					
					if(checkDirection(BlockFace.WEST, mainBlock, middleBlock, bottomBlock)) {
						return true;
					}
					
					if(checkDirection(BlockFace.SOUTH, mainBlock, middleBlock, bottomBlock)) {
						return true;
					}
					
				}
				
			}
			
		}
		
		return false;
	}
	
	@Override
	public GUI getGUI(Player player, int id) {
		return new AutomatorGUI(player, id);
	}
	
	public int getSpaceLeft(Machine machine, ItemStack itemStack) {
		return 0;
	}
	
	public void sendItems(Machine machine, ItemStack itemStack) {
		
	}
	
	public void updateEnergy(Machine machine) {
		
		for (Player player : SQTechBase.currentGui.keySet()) {
		     if (SQTechBase.currentGui.get(player).id == machine.getGUIBlock().id) {
		          if (player.getOpenInventory() != null) {
		               if (player.getOpenInventory().getTitle().equals(ChatColor.GRAY + "AutoCrafter")) {
		                     player.getOpenInventory().setItem(8, InventoryUtils.createSpecialItem(Material.REDSTONE, (short) 0, ChatColor.RESET + "Current Energy", new String[] {ChatColor.RED + "" + ChatColor.MAGIC + "Contraband", ChatColor.RESET + "" + ChatColor.GRAY + Double.toString(machine.getEnergy())}));
		               }
		          }
		     }
		}
		
	}
	
	public boolean checkDirection(BlockFace blockFace, Block mainBlock, Block middleBlock, Block bottomBlock) {
		
		if(mainBlock.getRelative(blockFace).getType().equals(Material.DROPPER)) {
			if(middleBlock.getRelative(blockFace).getType().equals(Material.WORKBENCH)) {
				if(bottomBlock.getRelative(blockFace).getType().equals(Material.DROPPER)) {
					return true;
				}
			}
		}
		
		return false;
		
	}
	
}
