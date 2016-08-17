package com.sqtechenergy.objects;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Player;

import com.starquestminecraft.sqtechbase.SQTechBase;
import com.starquestminecraft.sqtechbase.gui.GUI;
import com.starquestminecraft.sqtechbase.objects.GUIBlock;
import com.starquestminecraft.sqtechbase.objects.Machine;
import com.starquestminecraft.sqtechbase.objects.MachineType;
import com.starquestminecraft.sqtechbase.util.DirectionUtils;
import com.starquestminecraft.sqtechbase.util.EnergyUtils;
import com.starquestminecraft.sqtechbase.util.InventoryUtils;
import com.starquestminecraft.sqtechenergy.gui.ChargerGUI;

import net.md_5.bungee.api.ChatColor;

public class Charger extends MachineType {
	
	public Charger() {
		
		super(10000);		
		name = "Charger";
		
		defaultImport = true;
		
	}
	
	@Override
	public boolean detectStructure(GUIBlock guiBlock) {
		
		Block block = guiBlock.getLocation().getBlock();

		List<BlockFace> faces = new ArrayList<BlockFace>();
		
		faces.add(BlockFace.EAST);
		faces.add(BlockFace.SOUTH);
		faces.add(BlockFace.NORTH);
		faces.add(BlockFace.WEST);
		
		for (BlockFace face : faces) {
			
			if ((block.getRelative(BlockFace.UP).getType().equals(Material.FURNACE) ||
				block.getRelative(BlockFace.UP).getType().equals(Material.BURNING_FURNACE)) &&
				block.getRelative(face).getType().equals(Material.REDSTONE_BLOCK) &&
				block.getRelative(face).getRelative(BlockFace.UP).getType().equals(Material.REDSTONE_BLOCK) &&
				block.getRelative(face).getRelative(DirectionUtils.getLeft(face)).getType().equals(Material.STAINED_GLASS_PANE) &&
				block.getRelative(face).getRelative(DirectionUtils.getRight(face)).getType().equals(Material.STAINED_GLASS_PANE) &&
				block.getRelative(face).getRelative(DirectionUtils.getLeft(face)).getRelative(BlockFace.UP).getType().equals(Material.STAINED_GLASS_PANE) &&
				block.getRelative(face).getRelative(DirectionUtils.getRight(face)).getRelative(BlockFace.UP).getType().equals(Material.STAINED_GLASS_PANE) &&
				block.getRelative(DirectionUtils.getLeft(face)).getType().equals(Material.STAINED_GLASS_PANE) &&
				block.getRelative(DirectionUtils.getRight(face)).getType().equals(Material.STAINED_GLASS_PANE) &&
				block.getRelative(DirectionUtils.getLeft(face)).getRelative(BlockFace.UP).getType().equals(Material.STAINED_GLASS_PANE) &&
				block.getRelative(DirectionUtils.getRight(face)).getRelative(BlockFace.UP).getType().equals(Material.STAINED_GLASS_PANE)) {
				
				return true;
				
			}
			
		}
		
		return false;
		
	}
	
	@Override
	public GUI getGUI(Player player, int id) {
		
		return new ChargerGUI(player, id);
		
	}
	
	@Override
	public void updateEnergy(Machine machine) {
		
		for (Player player : SQTechBase.currentGui.keySet()) {
			
			if (SQTechBase.currentGui.get(player).id == machine.getGUIBlock().id) {
				
				if (player.getOpenInventory() != null) {
					
					if (player.getOpenInventory().getTitle().equals(ChatColor.BLUE + "SQTech - Charger")) {
						
						player.getOpenInventory().setItem(8, InventoryUtils.createSpecialItem(Material.REDSTONE, (short) 0, "Energy", new String[] {EnergyUtils.formatEnergy(machine.getEnergy()) + "/" + EnergyUtils.formatEnergy(machine.getMachineType().getMaxEnergy()), ChatColor.RED + "" + ChatColor.MAGIC + "Contraband"}));
						
					}
					
				}
				
			}
			
		}
		
	}
	
}
