package com.starquestminecraft.sqtechbase.database.objects;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import com.dibujaron.cardboardbox.CardboardBox;
import com.starquestminecraft.sqtechbase.objects.GUIBlock;
import com.starquestminecraft.sqtechbase.util.InventoryUtils;

import net.md_5.bungee.api.ChatColor;

public class SerializableGUIBlock implements Serializable{
	
	private static final long serialVersionUID = 8366759059071338459L;
	
	int x;
	int y;
	int z;
	String world;
	
	List<CardboardBox> imports = new ArrayList<CardboardBox>();
	List<CardboardBox> exports = new ArrayList<CardboardBox>();
	
	boolean exportAll;
	
	@SuppressWarnings("deprecation")
	public SerializableGUIBlock (GUIBlock guiBlock) {
		
		x = guiBlock.getLocation().getBlockX();
		y = guiBlock.getLocation().getBlockY();
		z = guiBlock.getLocation().getBlockZ();
		world = guiBlock.getLocation().getWorld().getName();
		
		for (ItemStack importItem : guiBlock.getImports()) {
			
			imports.add(new CardboardBox(importItem));
			
		}
		
		for (ItemStack export : guiBlock.getExports()) {
			
			exports.add(new CardboardBox(export));
			
		}
		
		exportAll = guiBlock.exportAll;
		
	}	
	
	@SuppressWarnings("deprecation")
	public GUIBlock getGUIBlock() {

		if (Bukkit.getWorld(world) != null) {
			
			Location location = new Location(Bukkit.getWorld(world), x, y, z);
			
			if (location.getBlock().getType().equals(Material.LAPIS_BLOCK)) {
				
				GUIBlock guiBlock = new GUIBlock(location, false);
				
				for (CardboardBox box : imports) {
					
					guiBlock.addImport(box.unbox());

				}
				
				for (CardboardBox box : exports) {
					
					guiBlock.addExport(box.unbox());
					
				}
				
				guiBlock.exportAll = exportAll;
				
				return guiBlock;
				
			}

		}
		
		return null;

	}

}
