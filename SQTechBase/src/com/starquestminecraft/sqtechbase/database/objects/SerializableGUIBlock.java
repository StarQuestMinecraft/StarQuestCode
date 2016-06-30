package com.starquestminecraft.sqtechbase.database.objects;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import com.starquestminecraft.sqtechbase.GUIBlock;
import com.starquestminecraft.sqtechbase.SQTechBase;
import com.starquestminecraft.sqtechbase.gui.GUIBlockGUI;

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
		
	}	
	
	public GUIBlock getGUIBlock() {

		if (Bukkit.getWorld(world) != null) {
			
			GUIBlock guiBlock = new GUIBlock(new GUIBlockGUI(), new Location(Bukkit.getWorld(world), x, y, z));
			
			for (int i = 0; i < importIDs.size(); i ++) {
				
				guiBlock.addImport(new ItemStack(Material.getMaterial(importIDs.get(i)), 1, importDatas.get(i)));
				
			}
			
			for (int i = 0; i < exportIDs.size(); i ++) {
				
				guiBlock.addExport(new ItemStack(Material.getMaterial(exportIDs.get(i)), 1, exportDatas.get(i)));
				
			}
			
			return guiBlock;
			
		}
		
		return null;

	}

}
