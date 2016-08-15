package com.starquestminecraft.sqtowerdefence;

import org.bukkit.Bukkit;
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

public class Tower extends MachineType {
	
	public String towerName = "Tower";
		
	public Tower(int maxEnergy) {
		super(maxEnergy);
		name = "Tower";
	}
	
	@Override
	public boolean detectStructure(GUIBlock guiBlock) {
		Block block = guiBlock.getLocation().getBlock();
		if(block.getType().equals(Material.LAPIS_BLOCK) && block.getRelative(BlockFace.DOWN).getType().equals(Material.SPONGE)) {
			return true;
		}
		
		return false;
	}
	
	@Override
	public GUI getGUI(Player player, int id) {
		return new TowerGUI(player, id);
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
		               if (com.starquestminecraft.sqtowerdefence.SQTDListener.stringList.contains(player.getOpenInventory().getTitle())) {
		                     player.getOpenInventory().setItem(8, InventoryUtils.createSpecialItem(Material.REDSTONE, (short) 0, ChatColor.RESET + "Current Energy", new String[] {ChatColor.RED + "" + ChatColor.MAGIC + "Contraband", ChatColor.RESET + "" + ChatColor.GRAY + Double.toString(machine.getEnergy())}));
		               }
		          }
		     }
		}
		
	}
	
}
