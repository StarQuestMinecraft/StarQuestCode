package com.starquestminecraft.sqtechbase.database.objects;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import com.starquestminecraft.sqtechbase.objects.GUIBlock;
import com.starquestminecraft.sqtechbase.util.InventoryUtils;

import net.md_5.bungee.api.ChatColor;

public class SerializableGUIBlock implements Serializable{
	
	private static final long serialVersionUID = 8366759059071338459L;
	
	int x;
	int y;
	int z;
	String world;
	
	List<Integer> importIDs = new ArrayList<Integer>();
	List<Short> importDatas = new ArrayList<Short>();
	
	List<Integer> exportIDs = new ArrayList<Integer>();
	List<Short> exportDatas = new ArrayList<Short>();
	
	boolean exportAll;
	
	@SuppressWarnings("deprecation")
	public SerializableGUIBlock (GUIBlock guiBlock) {
		
		x = guiBlock.getLocation().getBlockX();
		y = guiBlock.getLocation().getBlockY();
		z = guiBlock.getLocation().getBlockZ();
		world = guiBlock.getLocation().getWorld().getName();
		
		for (ItemStack importItem : guiBlock.getImports()) {
			
			importIDs.add(importItem.getTypeId());
			importDatas.add(importItem.getDurability());
			
		}
		
		for (ItemStack export : guiBlock.getExports()) {
			
			exportIDs.add(export.getTypeId());
			exportDatas.add(export.getDurability());
			
		}
		
		exportAll = guiBlock.exportAll;
		
	}	
	
	@SuppressWarnings("deprecation")
	public GUIBlock getGUIBlock() {

		if (Bukkit.getWorld(world) != null) {
			
			Location location = new Location(Bukkit.getWorld(world), x, y, z);
			
			if (location.getBlock().getType().equals(Material.LAPIS_BLOCK)) {
				
				GUIBlock guiBlock = new GUIBlock(location, false);
				
				for (int i = 0; i < importIDs.size(); i ++) {
					
					guiBlock.addImport(InventoryUtils.createSpecialItem(Material.getMaterial(importIDs.get(i)), importDatas.get(i), "", new String[] {ChatColor.RED + "" + ChatColor.MAGIC + "Contraband"}));
					
				}
				
				for (int i = 0; i < exportIDs.size(); i ++) {
					
					guiBlock.addExport(InventoryUtils.createSpecialItem(Material.getMaterial(exportIDs.get(i)), exportDatas.get(i), "", new String[] {ChatColor.RED + "" + ChatColor.MAGIC + "Contraband"}));
					
				}
				
				guiBlock.exportAll = exportAll;
				
				return guiBlock;
				
			}

		}
		
		return null;

	}

}
