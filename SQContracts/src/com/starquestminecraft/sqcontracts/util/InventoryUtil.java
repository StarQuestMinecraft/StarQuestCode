package com.starquestminecraft.sqcontracts.util;

import net.countercraft.movecraft.craft.Craft;
import net.countercraft.movecraft.utils.MovecraftLocation;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;

public class InventoryUtil {
	
	//returns the number removed
	public static int removeItemsFromInventory(Material type, short durability, Inventory inv, int amount){
		int amountRemoved = 0;
		for(int i = 0; i < inv.getSize(); i++){
			ItemStack item = inv.getItem(i);
			if(item.getType() == type && item.getDurability() == durability){
				if(item.getAmount() < (amount - amountRemoved) /*the amount left to remove*/){
					amountRemoved += item.getAmount();
					inv.setItem(i, new ItemStack(Material.AIR, 1));
				} else {
					amountRemoved = amount;
					item.setAmount(item.getAmount() - (amount - amountRemoved)  /*the amount left to remove*/);
					break;
				}
			}
		}
		return amountRemoved;
	}
	
	public static int removeItemsFromShipInventories(Material type, short durability, int amount, Craft c){
		int amountLeft = amount;
		for(MovecraftLocation l : c.getBlockList()){
			Block b = c.getW().getBlockAt(l.getX(), l.getY(), l.getZ());
			if(b.getState() instanceof InventoryHolder){
				InventoryHolder h = (InventoryHolder) b.getState();
				int removed = removeItemsFromInventory(type, durability, h.getInventory(), amountLeft);
				amountLeft = amountLeft - removed;
				if(amountLeft <= 0) return 0;
			}
		}
		return amountLeft;
	}
	
	public static int removeDataCoresFromShipInventories(Craft c, int amount, String[] craftTypes){
		int amountLeft = amount;
		for(MovecraftLocation l : c.getBlockList()){
			Block b = c.getW().getBlockAt(l.getX(), l.getY(), l.getZ());
			if(b.getState() instanceof InventoryHolder){
				InventoryHolder h = (InventoryHolder) b.getState();
				for(int n = 0; n < h.getInventory().getSize(); n++){
					ItemStack i = h.getInventory().getItem(n);
					if(ShipDataCore.isShipDataCore(i)){
						ShipDataCore d = new ShipDataCore(i);
						if(contains(craftTypes, d.getType())){
							h.getInventory().setItem(n, new ItemStack(Material.AIR, 1));
							amountLeft--;
							if(amountLeft <= 0) return 0;
						}
					}
				}
			}
		}
		return amountLeft;
	}
	
	private static <T> boolean contains(final T[] array, final T v) {
	    if (v == null) {
	        for (final T e : array)
	            if (e == null)
	                return true;
	    } else {
	        for (final T e : array)
	            if (e == v || v.equals(e))
	                return true;
	    }

	    return false;
	}
}
