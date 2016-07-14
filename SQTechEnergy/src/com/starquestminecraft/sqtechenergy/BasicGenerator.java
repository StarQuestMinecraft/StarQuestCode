package com.starquestminecraft.sqtechenergy;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

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
import com.starquestminecraft.sqtechbase.util.DirectionUtils;
import com.starquestminecraft.sqtechbase.util.InventoryUtils;
import com.starquestminecraft.sqtechenergy.gui.BasicGeneratorGUI;

import net.md_5.bungee.api.ChatColor;

public class BasicGenerator extends MachineType {
	
	public BasicGenerator() {
		
		super(10000);		
		name = "Basic Generator";
		
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
			
			if (block.getRelative(face).getType().equals(Material.STAINED_CLAY) && 
				block.getRelative(face).getRelative(BlockFace.UP).getType().equals(Material.STAINED_CLAY) &&
				(block.getRelative(BlockFace.UP).getType().equals(Material.FURNACE) ||
				block.getRelative(BlockFace.UP).getType().equals(Material.BURNING_FURNACE)) &&
				block.getRelative(DirectionUtils.getRight(face)).getType().equals(Material.STAINED_CLAY) &&
				block.getRelative(DirectionUtils.getRight(face)).getRelative(BlockFace.UP).getType().equals(Material.STAINED_CLAY) &&
				block.getRelative(DirectionUtils.getLeft(face)).getType().equals(Material.STAINED_CLAY) &&
				block.getRelative(DirectionUtils.getLeft(face)).getRelative(BlockFace.UP).getType().equals(Material.STAINED_CLAY)) {
				
				return true;
				
			}
			
		}
		
		return false;
		
	}
	
	@Override
	public GUI getGUI(Player player, int id) {
		
		return new BasicGeneratorGUI(player, id);
		
	}
	
	@Override
	public int getSpaceLeft(Machine machine, ItemStack itemStack) {
		
		return 3456;
		
	}
	
	@Override
	public void sendItems(Machine machine, ItemStack itemStack) {

		if (!itemStack.getType().equals(Material.AIR)) {

			for (Fuel fuel : SQTechEnergy.fuels) {
				
				if (fuel.generator.equals(machine.getMachineType().name)) {
					
					if (itemStack.getTypeId() == fuel.id) {
						
						if (machine.data.containsKey("fuel")) {
							
							if (machine.data.get("fuel") instanceof HashMap<?,?>) {
								
								HashMap<Fuel, Integer> currentFuels = (HashMap<Fuel, Integer>) machine.data.get("fuel");
								
								if (currentFuels.containsKey(fuel)) {
									
									currentFuels.replace(fuel, currentFuels.get(fuel) + (itemStack.getAmount() * fuel.burnTime));
									
								} else {
									
									currentFuels.put(fuel, itemStack.getAmount() * fuel.burnTime);
									
								}
								
							}
							
						} else {
							
							machine.data.put("fuel", new HashMap<Fuel, Integer>());
							
							HashMap<Fuel, Integer> currentFuels = (HashMap<Fuel, Integer>) machine.data.get("fuel");
							currentFuels.put(fuel, itemStack.getAmount() * fuel.burnTime);
							
						}
						
					}
					
				}
				
			}
			
		}
		
	}
	
	@Override
	public void updateEnergy(Machine machine) {
		
		for (Player player : SQTechBase.currentGui.keySet()) {
			
			if (SQTechBase.currentGui.get(player).id == machine.getGUIBlock().id) {
				
				if (player.getOpenInventory() != null) {
					
					if (player.getOpenInventory().getTitle().equals(ChatColor.BLUE + "SQTech - Basic Generator")) {
						
						player.getOpenInventory().setItem(8, InventoryUtils.createSpecialItem(Material.REDSTONE, (short) 0, "Energy: " + machine.getEnergy(), new String[] {ChatColor.RED + "" + ChatColor.MAGIC + "Contraband"}));
						
					}
					
				}
				
			}
			
		}
		
	}
	
}
