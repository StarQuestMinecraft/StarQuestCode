package com.starquestminecraft.sqcontracts.util;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import net.countercraft.movecraft.database.StarshipData;

public class ShipDataCore {
	
	private static final String LONG_OBFUSCATED = "dibujarondibujarondibujarondibuj";
	
	public static void createShipDataCore(StarshipData d, ItemStack paper){
		String type = d.getType();
		UUID pilot = d.getCaptain();
		String name = Bukkit.getOfflinePlayer(pilot).getName();
		int bnum = d.getBlockList().length;
		
		ItemMeta meta = paper.getItemMeta();
		meta.setDisplayName(ChatColor.RESET + "" + ChatColor.AQUA + "Starship Data Core");
		ArrayList<String> lore = new ArrayList<String>();
		lore.add(ChatColor.MAGIC + pilot.toString());
		lore.add(type);
		lore.add(bnum + " blocks");
		lore.add("Captain: " + name);
		lore.add(ChatColor.MAGIC + LONG_OBFUSCATED);
		
		paper.setItemMeta(meta);
	}
	
	public static boolean isShipDataCore(ItemStack i){
		if(i.getType() != Material.PAPER) return false;
		ItemMeta m = i.getItemMeta();
		return m.getDisplayName().equals(ChatColor.RESET + "" + ChatColor.AQUA + "Starship Data Core");
	}

	private String type;
	private UUID pilot;
	private String pilotName;
	private int blocksLength;
	
	public ShipDataCore(ItemStack paper){
		ItemMeta meta = paper.getItemMeta();
		List<String> lore = meta.getLore();
		String uid = lore.get(0);
		pilot = UUID.fromString(uid.substring(2, uid.length()));
		type = lore.get(1);
		blocksLength = Integer.parseInt(lore.get(2));
		pilotName = lore.get(3);
	}

	public String getType() {
		return type;
	}

	public UUID getPilot() {
		return pilot;
	}

	public String getPilotName() {
		return pilotName;
	}

	public int getBlocksLength() {
		return blocksLength;
	}
}
