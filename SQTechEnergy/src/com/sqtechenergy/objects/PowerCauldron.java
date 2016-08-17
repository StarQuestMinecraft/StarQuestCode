package com.sqtechenergy.objects;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Player;

import com.starquestminecraft.sqtechbase.SQTechBase;
import com.starquestminecraft.sqtechbase.gui.GUI;
import com.starquestminecraft.sqtechbase.objects.GUIBlock;
import com.starquestminecraft.sqtechbase.objects.Machine;
import com.starquestminecraft.sqtechbase.objects.MachineType;
import com.starquestminecraft.sqtechbase.util.EnergyUtils;
import com.starquestminecraft.sqtechbase.util.InventoryUtils;
import com.starquestminecraft.sqtechenergy.gui.PowerCauldronGUI;

import net.md_5.bungee.api.ChatColor;

public class PowerCauldron extends MachineType {

	public PowerCauldron() {
		
		super(50000);		
		name = "Cauldron Of Power";
		
		defaultExport = true;
		defaultImport = true;
		
	}
	
	@Override
	public boolean detectStructure(GUIBlock guiBlock) {
		
		Block block = guiBlock.getLocation().getBlock();
			
		if (block.getRelative(BlockFace.UP).getType().equals(Material.REDSTONE_BLOCK) &&
			block.getRelative(BlockFace.UP, 2).getType().equals(Material.REDSTONE_BLOCK) &&
			block.getRelative(BlockFace.NORTH).getRelative(BlockFace.UP, 1).getType().equals(Material.STAINED_GLASS_PANE) &&
			block.getRelative(BlockFace.NORTH).getRelative(BlockFace.UP, 2).getType().equals(Material.STAINED_GLASS_PANE) &&
			block.getRelative(BlockFace.EAST).getRelative(BlockFace.UP, 1).getType().equals(Material.STAINED_GLASS_PANE) &&
			block.getRelative(BlockFace.EAST).getRelative(BlockFace.UP, 2).getType().equals(Material.STAINED_GLASS_PANE) &&
			block.getRelative(BlockFace.WEST).getRelative(BlockFace.UP, 1).getType().equals(Material.STAINED_GLASS_PANE) &&
			block.getRelative(BlockFace.WEST).getRelative(BlockFace.UP, 2).getType().equals(Material.STAINED_GLASS_PANE) &&
			block.getRelative(BlockFace.SOUTH).getRelative(BlockFace.UP, 1).getType().equals(Material.STAINED_GLASS_PANE) &&
			block.getRelative(BlockFace.SOUTH).getRelative(BlockFace.UP, 2).getType().equals(Material.STAINED_GLASS_PANE) &&
			block.getRelative(BlockFace.SOUTH).getRelative(BlockFace.EAST).getRelative(BlockFace.UP).getType().equals(Material.STAINED_GLASS_PANE) &&
			block.getRelative(BlockFace.SOUTH).getRelative(BlockFace.EAST).getRelative(BlockFace.UP, 1).getType().equals(Material.STAINED_GLASS_PANE) &&
			block.getRelative(BlockFace.SOUTH).getRelative(BlockFace.EAST).getRelative(BlockFace.UP, 2).getType().equals(Material.STAINED_GLASS_PANE) &&
			block.getRelative(BlockFace.SOUTH).getRelative(BlockFace.WEST).getRelative(BlockFace.UP).getType().equals(Material.STAINED_GLASS_PANE) &&
			block.getRelative(BlockFace.SOUTH).getRelative(BlockFace.WEST).getRelative(BlockFace.UP, 1).getType().equals(Material.STAINED_GLASS_PANE) &&
			block.getRelative(BlockFace.SOUTH).getRelative(BlockFace.WEST).getRelative(BlockFace.UP, 2).getType().equals(Material.STAINED_GLASS_PANE) &&
			block.getRelative(BlockFace.NORTH).getRelative(BlockFace.EAST).getRelative(BlockFace.UP).getType().equals(Material.STAINED_GLASS_PANE) &&
			block.getRelative(BlockFace.NORTH).getRelative(BlockFace.EAST).getRelative(BlockFace.UP, 1).getType().equals(Material.STAINED_GLASS_PANE) &&
			block.getRelative(BlockFace.NORTH).getRelative(BlockFace.EAST).getRelative(BlockFace.UP, 2).getType().equals(Material.STAINED_GLASS_PANE) &&
			block.getRelative(BlockFace.NORTH).getRelative(BlockFace.WEST).getRelative(BlockFace.UP).getType().equals(Material.STAINED_GLASS_PANE) &&
			block.getRelative(BlockFace.NORTH).getRelative(BlockFace.WEST).getRelative(BlockFace.UP, 1).getType().equals(Material.STAINED_GLASS_PANE) &&
			block.getRelative(BlockFace.NORTH).getRelative(BlockFace.WEST).getRelative(BlockFace.UP, 2).getType().equals(Material.STAINED_GLASS_PANE)) {
				
			return true;
			
		}
		
		return false;
		
	}
	
	@Override
	public GUI getGUI(Player player, int id) {
		
		return new PowerCauldronGUI(player, id);
		
	}
	
	@Override
	public void updateEnergy(Machine machine) {
		
		for (Player player : SQTechBase.currentGui.keySet()) {
			
			if (SQTechBase.currentGui.get(player).id == machine.getGUIBlock().id) {
				
				if (player.getOpenInventory() != null) {
					
					if (player.getOpenInventory().getTitle().equals(ChatColor.BLUE + "SQTech - Power Cauldron")) {
						
						player.getOpenInventory().setItem(8, InventoryUtils.createSpecialItem(Material.REDSTONE, (short) 0, "Energy", new String[] {EnergyUtils.formatEnergy(machine.getEnergy()) + "/" + EnergyUtils.formatEnergy(machine.getMachineType().getMaxEnergy()), ChatColor.RED + "" + ChatColor.MAGIC + "Contraband"}));
						
					}
					
				}
				
			}
			
		}
		
	}
	
}
