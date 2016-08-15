package com.martinjonsson01.sqsmoothcraft.missile;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import com.ginger_walnut.sqsmoothcraft.SQSmoothCraft;
import com.ginger_walnut.sqsmoothcraft.gui.Gui;
import com.ginger_walnut.sqsmoothcraft.gui.MainGui;
import com.ginger_walnut.sqsmoothcraft.utils.ShipUtils;

import net.md_5.bungee.api.ChatColor;

public class MissileGUI extends Gui{
	
	public MissileGUI(Player player) {
		super(player);
	}
	
	@Override
	public void open() {
		
		Inventory inventory = Bukkit.createInventory(owner, 27, ChatColor.AQUA + "Heat Seeking Missiles - Types");
		
		ItemStack exit = new ItemStack(Material.WOOD_DOOR);
		
		List<String> lore = new ArrayList<String>();
		lore.add(ChatColor.DARK_PURPLE + "Click this to return to the ship menu");
		lore.add(ChatColor.RED + "" + ChatColor.MAGIC + "Contraband");
		
		exit = ShipUtils.createSpecialItem(exit, lore, "Return");
		
		inventory.setItem(26, exit);
		
		ItemStack standardMissile = new ItemStack(Material.FIREBALL);
		
		lore = new ArrayList<String>();
		lore.add(ChatColor.GREEN+ "Click to show recipe");
		lore.add(ChatColor.RED + "" + ChatColor.MAGIC + "Contraband");
		
		standardMissile = ShipUtils.createSpecialItem(standardMissile, lore, ChatColor.GOLD + "Heat Seeking Missile");
		
		inventory.setItem(0, standardMissile);
		
		ItemStack empMissile = new ItemStack(Material.FIREBALL);
		
		lore = new ArrayList<String>();
		lore.add(ChatColor.GREEN+ "Click to show recipe");
		lore.add(ChatColor.RED + "" + ChatColor.MAGIC + "Contraband");
		
		empMissile = ShipUtils.createSpecialItem(empMissile, lore, ChatColor.GOLD + "Heat Seeking EMP Missile");
		
		inventory.setItem(1, empMissile);
		
		ItemStack explosiveMissile = new ItemStack(Material.FIREBALL);
		
		lore = new ArrayList<String>();
		lore.add(ChatColor.GREEN+ "Click to show recipe");
		lore.add(ChatColor.RED + "" + ChatColor.MAGIC + "Contraband");
		
		explosiveMissile = ShipUtils.createSpecialItem(explosiveMissile, lore, ChatColor.GOLD + "Heat Seeking Explosive Missile");
		
		inventory.setItem(2, explosiveMissile);
		
		
		owner.openInventory(inventory);
		
		if (SQSmoothCraft.currentGui.containsKey(owner)) {
			
			SQSmoothCraft.currentGui.remove(owner);
			SQSmoothCraft.currentGui.put(owner,  this);
			
		} else {
			
			SQSmoothCraft.currentGui.put(owner,  this);
			
		}
		
		
		
	}
	
	@Override
	public void clicked(String itemName, int slot) {
		
		if(itemName == null) return;
		
		if (itemName.equals(ChatColor.GOLD + "Heat Seeking Missile")) {
			
			openStandardMissileRecipe(owner);
			
		} else if (itemName.equals(ChatColor.GOLD + "Heat Seeking EMP Missile")) {
			
			openEMPMissileRecipe(owner);
			
		} else if (itemName.equals(ChatColor.GOLD + "Heat Seeking Explosive Missile")) {
			
			openExplosiveMissileRecipe(owner);
			
		} else if (itemName.equals("Return")) {
			
			MainGui gui = new MainGui(owner);
			gui.open();
			
		}
		
	}
	
	public void openStandardMissileRecipe(Player owner){
		
		Inventory inventory = Bukkit.createInventory(owner, InventoryType.WORKBENCH, "Missile Recipe");
		
		ItemStack item0 = new ItemStack(Material.FIREBALL);
		List<String> lore = new ArrayList<String>();
		lore.add(ChatColor.RED + "" + ChatColor.MAGIC + "Contraband");
		item0 = ShipUtils.createSpecialItem(item0, lore, ChatColor.GOLD + "Heat Seeking Missile");
		inventory.setItem(0, item0);
		
		ItemStack item1 = new ItemStack(Material.PAPER);
		lore = new ArrayList<String>();
		lore.add(ChatColor.RED + "" + ChatColor.MAGIC + "Contraband");
		item1 = ShipUtils.createSpecialItem(item1, lore, null);
		inventory.setItem(1, item1);
		
		ItemStack item2 = new ItemStack(Material.FIREBALL);
		lore = new ArrayList<String>();
		lore.add(ChatColor.RED + "" + ChatColor.MAGIC + "Contraband");
		item2 = ShipUtils.createSpecialItem(item2, lore, null);
		inventory.setItem(2, item2);
		
		ItemStack item3 = new ItemStack(Material.PAPER);
		lore = new ArrayList<String>();
		lore.add(ChatColor.RED + "" + ChatColor.MAGIC + "Contraband");
		item3 = ShipUtils.createSpecialItem(item3, lore, null);
		inventory.setItem(3, item3);
		
		ItemStack item4 = new ItemStack(Material.FIREBALL);
		lore = new ArrayList<String>();
		lore.add(ChatColor.RED + "" + ChatColor.MAGIC + "Contraband");
		item4 = ShipUtils.createSpecialItem(item4, lore, null);
		inventory.setItem(4, item4);
		
		ItemStack item5 = new ItemStack(Material.FIREWORK_CHARGE);
		lore = new ArrayList<String>();
		lore.add(ChatColor.RED + "" + ChatColor.MAGIC + "Contraband");
		item5 = ShipUtils.createSpecialItem(item5, lore, null);
		inventory.setItem(5, item5);
		
		ItemStack item6 = new ItemStack(Material.FIREBALL);
		lore = new ArrayList<String>();
		lore.add(ChatColor.RED + "" + ChatColor.MAGIC + "Contraband");
		item6 = ShipUtils.createSpecialItem(item6, lore, null);
		inventory.setItem(6, item6);
		
		ItemStack item7 = new ItemStack(Material.PAPER);
		lore = new ArrayList<String>();
		lore.add(ChatColor.RED + "" + ChatColor.MAGIC + "Contraband");
		item7 = ShipUtils.createSpecialItem(item7, lore, null);
		inventory.setItem(7, item7);
		
		ItemStack item8 = new ItemStack(Material.FIREBALL);
		lore = new ArrayList<String>();
		lore.add(ChatColor.RED + "" + ChatColor.MAGIC + "Contraband");
		item8 = ShipUtils.createSpecialItem(item8, lore, null);
		inventory.setItem(8, item8);
		
		ItemStack item9 = new ItemStack(Material.PAPER);
		lore = new ArrayList<String>();
		lore.add(ChatColor.RED + "" + ChatColor.MAGIC + "Contraband");
		item9 = ShipUtils.createSpecialItem(item9, lore, null);
		inventory.setItem(9, item9);
		
		owner.openInventory(inventory);
		
	}
	
	public void openEMPMissileRecipe(Player owner){
		
		Inventory inventory = Bukkit.createInventory(owner, InventoryType.WORKBENCH, "Missile Recipe");
		
		ItemStack item0 = new ItemStack(Material.FIREBALL);
		List<String> lore = new ArrayList<String>();
		lore.add(ChatColor.RED + "" + ChatColor.MAGIC + "Contraband");
		item0 = ShipUtils.createSpecialItem(item0, lore, ChatColor.GOLD + "Heat Seeking EMP Missile");
		inventory.setItem(0, item0);
		
		ItemStack item1 = new ItemStack(Material.PAPER);
		lore = new ArrayList<String>();
		lore.add(ChatColor.RED + "" + ChatColor.MAGIC + "Contraband");
		item1 = ShipUtils.createSpecialItem(item1, lore, null);
		inventory.setItem(1, item1);
		
		ItemStack item2 = new ItemStack(Material.FIREBALL);
		lore = new ArrayList<String>();
		lore.add(ChatColor.RED + "" + ChatColor.MAGIC + "Contraband");
		item2 = ShipUtils.createSpecialItem(item2, lore, null);
		inventory.setItem(2, item2);
		
		ItemStack item3 = new ItemStack(Material.PAPER);
		lore = new ArrayList<String>();
		lore.add(ChatColor.RED + "" + ChatColor.MAGIC + "Contraband");
		item3 = ShipUtils.createSpecialItem(item3, lore, null);
		inventory.setItem(3, item3);
		
		ItemStack item4 = new ItemStack(Material.FIREBALL);
		lore = new ArrayList<String>();
		lore.add(ChatColor.RED + "" + ChatColor.MAGIC + "Contraband");
		item4 = ShipUtils.createSpecialItem(item4, lore, null);
		inventory.setItem(4, item4);
		
		ItemStack item5 = new ItemStack(Material.REDSTONE_BLOCK);
		lore = new ArrayList<String>();
		lore.add(ChatColor.RED + "" + ChatColor.MAGIC + "Contraband");
		item5 = ShipUtils.createSpecialItem(item5, lore, null);
		inventory.setItem(5, item5);
		
		ItemStack item6 = new ItemStack(Material.FIREBALL);
		lore = new ArrayList<String>();
		lore.add(ChatColor.RED + "" + ChatColor.MAGIC + "Contraband");
		item6 = ShipUtils.createSpecialItem(item6, lore, null);
		inventory.setItem(6, item6);
		
		ItemStack item7 = new ItemStack(Material.PAPER);
		lore = new ArrayList<String>();
		lore.add(ChatColor.RED + "" + ChatColor.MAGIC + "Contraband");
		item7 = ShipUtils.createSpecialItem(item7, lore, null);
		inventory.setItem(7, item7);
		
		ItemStack item8 = new ItemStack(Material.FIREBALL);
		lore = new ArrayList<String>();
		lore.add(ChatColor.RED + "" + ChatColor.MAGIC + "Contraband");
		item8 = ShipUtils.createSpecialItem(item8, lore, null);
		inventory.setItem(8, item8);
		
		ItemStack item9 = new ItemStack(Material.PAPER);
		lore = new ArrayList<String>();
		lore.add(ChatColor.RED + "" + ChatColor.MAGIC + "Contraband");
		item9 = ShipUtils.createSpecialItem(item9, lore, null);
		inventory.setItem(9, item9);
		
		owner.openInventory(inventory);
		
	}
	
	public void openExplosiveMissileRecipe(Player owner){
		
		Inventory inventory = Bukkit.createInventory(owner, InventoryType.WORKBENCH, "Missile Recipe");
		
		ItemStack item0 = new ItemStack(Material.FIREBALL);
		List<String> lore = new ArrayList<String>();
		lore.add(ChatColor.RED + "" + ChatColor.MAGIC + "Contraband");
		item0 = ShipUtils.createSpecialItem(item0, lore, ChatColor.GOLD + "Heat Seeking Explosive Missile");
		inventory.setItem(0, item0);
		
		ItemStack item1 = new ItemStack(Material.PAPER);
		lore = new ArrayList<String>();
		lore.add(ChatColor.RED + "" + ChatColor.MAGIC + "Contraband");
		item1 = ShipUtils.createSpecialItem(item1, lore, null);
		inventory.setItem(1, item1);
		
		ItemStack item2 = new ItemStack(Material.FIREBALL);
		lore = new ArrayList<String>();
		lore.add(ChatColor.RED + "" + ChatColor.MAGIC + "Contraband");
		item2 = ShipUtils.createSpecialItem(item2, lore, null);
		inventory.setItem(2, item2);
		
		ItemStack item3 = new ItemStack(Material.PAPER);
		lore = new ArrayList<String>();
		lore.add(ChatColor.RED + "" + ChatColor.MAGIC + "Contraband");
		item3 = ShipUtils.createSpecialItem(item3, lore, null);
		inventory.setItem(3, item3);
		
		ItemStack item4 = new ItemStack(Material.FIREBALL);
		lore = new ArrayList<String>();
		lore.add(ChatColor.RED + "" + ChatColor.MAGIC + "Contraband");
		item4 = ShipUtils.createSpecialItem(item4, lore, null);
		inventory.setItem(4, item4);
		
		ItemStack item5 = new ItemStack(Material.TNT);
		lore = new ArrayList<String>();
		lore.add(ChatColor.RED + "" + ChatColor.MAGIC + "Contraband");
		item5 = ShipUtils.createSpecialItem(item5, lore, null);
		inventory.setItem(5, item5);
		
		ItemStack item6 = new ItemStack(Material.FIREBALL);
		lore = new ArrayList<String>();
		lore.add(ChatColor.RED + "" + ChatColor.MAGIC + "Contraband");
		item6 = ShipUtils.createSpecialItem(item6, lore, null);
		inventory.setItem(6, item6);
		
		ItemStack item7 = new ItemStack(Material.PAPER);
		lore = new ArrayList<String>();
		lore.add(ChatColor.RED + "" + ChatColor.MAGIC + "Contraband");
		item7 = ShipUtils.createSpecialItem(item7, lore, null);
		inventory.setItem(7, item7);
		
		ItemStack item8 = new ItemStack(Material.FIREBALL);
		lore = new ArrayList<String>();
		lore.add(ChatColor.RED + "" + ChatColor.MAGIC + "Contraband");
		item8 = ShipUtils.createSpecialItem(item8, lore, null);
		inventory.setItem(8, item8);
		
		ItemStack item9 = new ItemStack(Material.PAPER);
		lore = new ArrayList<String>();
		lore.add(ChatColor.RED + "" + ChatColor.MAGIC + "Contraband");
		item9 = ShipUtils.createSpecialItem(item9, lore, null);
		inventory.setItem(9, item9);
		
		owner.openInventory(inventory);
		
	}
	
	
	
	
}
