package com.ginger_walnut.sqsmoothcraft.utils;

import java.util.ArrayList;
import java.util.List;

import net.md_5.bungee.api.ChatColor;
import net.minecraft.server.v1_10_R1.EntityPlayer;

import org.bukkit.Material;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import com.ginger_walnut.sqsmoothcraft.SQSmoothCraft;
import com.ginger_walnut.sqsmoothcraft.objects.Ship;
import com.ginger_walnut.sqsmoothcraft.objects.ShipBlock;

public class ShipUtils {
	
	public static ShipBlock getShipBlockFromArmorStand(ArmorStand stand) {
		
		List<ShipBlock> shipBlocks = new ArrayList<ShipBlock>();
		
		for (Object object : SQSmoothCraft.shipMap.values().toArray()) {
			
			Ship ship = (Ship) object;
			
			shipBlocks.addAll(ship.getShipBlocks());
			
		}
		
		for (Ship ship : SQSmoothCraft.stoppedShipMap) {
			
			shipBlocks.addAll(ship.getShipBlocks());
			
		}
		
		for (ShipBlock shipBlock : shipBlocks) {
			
			if (shipBlock.getArmorStand().equals(stand)) {
				
				return shipBlock;
				
			}
			
		}
		
		return null;
		
	}
	
	public static Ship getShipFromNpc(EntityPlayer npc) {
		
		List<Ship> ships = new ArrayList<Ship>();		
		
		for (Ship ship : ships) {
			
			if (ship.thirdPersonPlayer.equals(npc)) {
				
				return ship;
				
			}
			
		}
		
		return null;
		
	}
	
	public static List<EntityPlayer> getAllShipNpcs() {
		
		List<EntityPlayer> npcs = new ArrayList<EntityPlayer>();
		
		for (Ship ship : SQSmoothCraft.shipMap.values()) {
			
			if (ship.thirdPersonPlayer != null) {
				
				npcs.add(ship.thirdPersonPlayer);
				
			}
			
		}
		
		return npcs;
		
	}
	
	public static void setPlayerShipInventory(Player player) {
		
		player.getInventory().clear();
		player.getInventory().setArmorContents(null);
		
		ItemStack mcd = new ItemStack(Material.WATCH);
		ItemMeta mcdMeta = mcd.getItemMeta();
		
		mcdMeta.setDisplayName("Main Control Device");
		
		List<String> mcdLore = new ArrayList<String>();
		mcdLore.add(ChatColor.DARK_PURPLE + "Point in the direction you want to turn");
		mcdLore.add(ChatColor.DARK_PURPLE + "while holding this to turn. You can left");
		mcdLore.add(ChatColor.DARK_PURPLE + "click while holding this to fire the");
		mcdLore.add(ChatColor.DARK_PURPLE + "ship's main cannons and right click to");
		mcdLore.add(ChatColor.DARK_PURPLE + "fire the ship's missiles.");
		
		mcdMeta.setLore(mcdLore);
		
		mcd.setItemMeta(mcdMeta);
		
		player.getInventory().setItem(1, mcd);
		SQSmoothCraft.controlItems.add(mcd);

		ItemStack explosive = new ItemStack(Material.SULPHUR);
		ItemMeta explosiveMeta = explosive.getItemMeta();
		
		explosiveMeta.setDisplayName("Cannon Explosive Mode");
		
		List<String> explosiveLore = new ArrayList<String>();
		explosiveLore.add(ChatColor.DARK_PURPLE + "Right click this to toggle explosive mode");
		explosiveLore.add(ChatColor.DARK_PURPLE + "for the main cannons");
		
		explosiveMeta.setLore(explosiveLore);
		
		explosive.setItemMeta(explosiveMeta);
		
		player.getInventory().setItem(2, explosive);
		SQSmoothCraft.controlItems.add(explosive);
		
		ItemStack directionLock = new ItemStack(Material.COMPASS);
		ItemMeta directionLockMeta = directionLock.getItemMeta();
		
		directionLockMeta.setDisplayName("Direction Locker");
		
		List<String> directionLockLore = new ArrayList<String>();
		directionLockLore.add(ChatColor.DARK_PURPLE + "Right click this to lock the direction that");
		directionLockLore.add(ChatColor.DARK_PURPLE + "the ship is facing");
		
		directionLockMeta.setLore(directionLockLore);
		
		directionLock.setItemMeta(directionLockMeta);
		
		player.getInventory().setItem(7, directionLock);
		SQSmoothCraft.controlItems.add(directionLock);
		
		ItemStack menu = new ItemStack(Material.REDSTONE);
		ItemMeta menuMeta = menu.getItemMeta();
		
		menuMeta.setDisplayName("Menu");
		
		List<String> menuLore = new ArrayList<String>();
		menuLore.add(ChatColor.DARK_PURPLE + "Right click with this in hand to open");
		menuLore.add(ChatColor.DARK_PURPLE + "the ship menu.");
		
		menuMeta.setLore(menuLore);
		
		menu.setItemMeta(menuMeta);
		
		player.getInventory().setItem(8, menu);
		SQSmoothCraft.controlItems.add(menu);
		
		ItemStack emFieldInfo = new ItemStack(Material.STAINED_GLASS);
		
		ArrayList<String> lore = new ArrayList<String>();
		lore.add(ChatColor.DARK_PURPLE + "Click this to show the current strength of the EM field");
		lore.add(ChatColor.RED + "" + ChatColor.MAGIC + "Contraband");
		
		emFieldInfo = ShipUtils.createSpecialItem(emFieldInfo, lore, "EM Field Info");
		
		player.getInventory().setItem(6, emFieldInfo);
		SQSmoothCraft.controlItems.add(emFieldInfo);
		
		player.getInventory().setHeldItemSlot(0);
		
	}
	
	public static ItemStack createSpecialItem(ItemStack itemStack, List<String> lore, String name) {
		
		ItemMeta itemMeta = itemStack.getItemMeta();
		
		itemMeta.setDisplayName(name);
		
		itemMeta.setLore(lore);
		
		itemStack.setItemMeta(itemMeta);
		
		return itemStack;
		
	}
	
	public static float sin(float radians) {
		
		//System.out.print(square(2, 2));
		
		//return (radians - (square(radians, 3) / 6) + (square(radians, 5) / 120) - (square(radians, 7) / 5040) + (square(radians, 9) / 362880) - (square(radians, 11) / 39916800));
		
		return (float) Math.sin(radians);
		
	}
	
	public static float cos(float radians) {
		
		//return (1 - (square(radians, 2) / 2) + (square(radians, 4) / 24) - (square(radians, 6) / 24) + (square(radians, 8) / 40320) - (square(radians, 6) / 24));
		
		return (float) Math.cos(radians);
		
	}
	
	@SuppressWarnings("unused")
	private static float square (float number, int power) {
		
		
		float newNumber = 1;
		
		for (int i = 0; i < power; i++) {
			
			newNumber = newNumber * number;
			
		}
		
		return newNumber;
		
	}
	
}
