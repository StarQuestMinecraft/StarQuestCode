package com.martinjonsson01.sqtechpumps.gui;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

import com.martinjonsson01.sqtechpumps.SQTechPumps;
import com.starquestminecraft.sqtechbase.objects.Machine;
import com.starquestminecraft.sqtechbase.util.InventoryUtils;

import net.md_5.bungee.api.ChatColor;

public class MediumTankTypeGUI {
	
	Player owner;

	Machine m;

	public MediumTankTypeGUI(Machine m, Player owner) {
		this.owner = owner;
		this.m = m;
	}

	public void open() {

		Inventory gui = Bukkit.createInventory(owner, 27, ChatColor.BLUE + "SQTech - Choose medium tank type");

		gui.setItem(0, InventoryUtils.createSpecialItem(Material.IRON_FENCE, (short) 0, " ", new String[]{ChatColor.RED + "" + ChatColor.MAGIC + "Contraband"}));
		gui.setItem(1, InventoryUtils.createSpecialItem(Material.IRON_FENCE, (short) 0, " ", new String[]{ChatColor.RED + "" + ChatColor.MAGIC + "Contraband"}));
		gui.setItem(2, InventoryUtils.createSpecialItem(Material.IRON_FENCE, (short) 0, " ", new String[]{ChatColor.RED + "" + ChatColor.MAGIC + "Contraband"}));
		gui.setItem(3, InventoryUtils.createSpecialItem(Material.IRON_FENCE, (short) 0, " ", new String[]{ChatColor.RED + "" + ChatColor.MAGIC + "Contraband"}));
		gui.setItem(4, InventoryUtils.createSpecialItem(Material.IRON_FENCE, (short) 0, " ", new String[]{ChatColor.RED + "" + ChatColor.MAGIC + "Contraband"}));
		gui.setItem(5, InventoryUtils.createSpecialItem(Material.IRON_FENCE, (short) 0, " ", new String[]{ChatColor.RED + "" + ChatColor.MAGIC + "Contraband"}));
		gui.setItem(6, InventoryUtils.createSpecialItem(Material.IRON_FENCE, (short) 0, " ", new String[]{ChatColor.RED + "" + ChatColor.MAGIC + "Contraband"}));
		gui.setItem(7, InventoryUtils.createSpecialItem(Material.IRON_FENCE, (short) 0, " ", new String[]{ChatColor.RED + "" + ChatColor.MAGIC + "Contraband"}));
		gui.setItem(8, InventoryUtils.createSpecialItem(Material.IRON_FENCE, (short) 0, " ", new String[]{ChatColor.RED + "" + ChatColor.MAGIC + "Contraband"}));
		gui.setItem(9, InventoryUtils.createSpecialItem(Material.IRON_FENCE, (short) 0, " ", new String[]{ChatColor.RED + "" + ChatColor.MAGIC + "Contraband"}));
		//Select lava
		gui.setItem(10, InventoryUtils.createSpecialItem(Material.LAVA_BUCKET, (short) 0, ChatColor.BLUE + "Select" + ChatColor.RED + " Lava", new String[]{ChatColor.RED + "" + ChatColor.MAGIC + "Contraband"}));
		gui.setItem(11, InventoryUtils.createSpecialItem(Material.IRON_FENCE, (short) 0, " ", new String[]{ChatColor.RED + "" + ChatColor.MAGIC + "Contraband"}));
		gui.setItem(12, InventoryUtils.createSpecialItem(Material.IRON_FENCE, (short) 0, " ", new String[]{ChatColor.RED + "" + ChatColor.MAGIC + "Contraband"}));
		gui.setItem(13, InventoryUtils.createSpecialItem(Material.IRON_FENCE, (short) 0, " ", new String[]{ChatColor.RED + "" + ChatColor.MAGIC + "Contraband"}));
		gui.setItem(14, InventoryUtils.createSpecialItem(Material.IRON_FENCE, (short) 0, " ", new String[]{ChatColor.RED + "" + ChatColor.MAGIC + "Contraband"}));
		gui.setItem(15, InventoryUtils.createSpecialItem(Material.IRON_FENCE, (short) 0, " ", new String[]{ChatColor.RED + "" + ChatColor.MAGIC + "Contraband"}));
		//Select water
		gui.setItem(16, InventoryUtils.createSpecialItem(Material.WATER_BUCKET, (short) 0, ChatColor.BLUE + "Select" + ChatColor.AQUA + " Water", new String[]{ChatColor.RED + "" + ChatColor.MAGIC + "Contraband"}));
		gui.setItem(17, InventoryUtils.createSpecialItem(Material.IRON_FENCE, (short) 0, " ", new String[]{ChatColor.RED + "" + ChatColor.MAGIC + "Contraband"}));
		gui.setItem(18, InventoryUtils.createSpecialItem(Material.IRON_FENCE, (short) 0, " ", new String[]{ChatColor.RED + "" + ChatColor.MAGIC + "Contraband"}));
		gui.setItem(19, InventoryUtils.createSpecialItem(Material.IRON_FENCE, (short) 0, " ", new String[]{ChatColor.RED + "" + ChatColor.MAGIC + "Contraband"}));
		gui.setItem(20, InventoryUtils.createSpecialItem(Material.IRON_FENCE, (short) 0, " ", new String[]{ChatColor.RED + "" + ChatColor.MAGIC + "Contraband"}));
		gui.setItem(21, InventoryUtils.createSpecialItem(Material.IRON_FENCE, (short) 0, " ", new String[]{ChatColor.RED + "" + ChatColor.MAGIC + "Contraband"}));
		gui.setItem(22, InventoryUtils.createSpecialItem(Material.IRON_FENCE, (short) 0, " ", new String[]{ChatColor.RED + "" + ChatColor.MAGIC + "Contraband"}));
		gui.setItem(23, InventoryUtils.createSpecialItem(Material.IRON_FENCE, (short) 0, " ", new String[]{ChatColor.RED + "" + ChatColor.MAGIC + "Contraband"}));
		gui.setItem(24, InventoryUtils.createSpecialItem(Material.IRON_FENCE, (short) 0, " ", new String[]{ChatColor.RED + "" + ChatColor.MAGIC + "Contraband"}));
		gui.setItem(25, InventoryUtils.createSpecialItem(Material.IRON_FENCE, (short) 0, " ", new String[]{ChatColor.RED + "" + ChatColor.MAGIC + "Contraband"}));
		gui.setItem(26, InventoryUtils.createSpecialItem(Material.IRON_FENCE, (short) 0, " ", new String[]{ChatColor.RED + "" + ChatColor.MAGIC + "Contraband"}));
		
		SQTechPumps.inventoryMap.put(gui, m);
		
		owner.openInventory(gui);

	}
	
}
