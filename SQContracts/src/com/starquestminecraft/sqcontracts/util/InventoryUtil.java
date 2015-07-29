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
			if(item != null && item.getType() == type && item.getDurability() == durability){
				int amtLeft = amount-amountRemoved;
				if(item.getAmount() < amtLeft){
					//add to the amount removed and delete the item
					amountRemoved += item.getAmount();
					inv.setItem(i, new ItemStack(Material.AIR, 1));
				} else {
					//everything is removed, subtract the last few from this stack and go
					item.setAmount(item.getAmount() - amtLeft);
					inv.setItem(i, item);
					amountRemoved = amount;
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
	
	public static int removeDataCoresFromShipInventories(Craft c, int amount, String[] craftTypes, boolean blackMarket){
		int amountLeft = amount;
		for(MovecraftLocation l : c.getBlockList()){
			Block b = c.getW().getBlockAt(l.getX(), l.getY(), l.getZ());
			if(b.getState() instanceof InventoryHolder){
				InventoryHolder h = (InventoryHolder) b.getState();
				for(int n = 0; n < h.getInventory().getSize(); n++){
					ItemStack i = h.getInventory().getItem(n);
					if(i != null){
					}
					if(ShipDataCore.isShipDataCore(i, blackMarket)){
						ShipDataCore d = new ShipDataCore(i);
						//if(!d.getPilot().equals(c.pilot.getUniqueId())){
							if(contains(craftTypes, d.getType())){
								h.getInventory().setItem(n, new ItemStack(Material.AIR, 1));
								amountLeft--;
								if(amountLeft <= 0) return 0;
							}
						/*} else {
							c.pilot.sendMessage("You cannot redeem a data core from a ship that you were flying.");
						}*/
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
	
	private static <T> String arrayPrint(T[] arr){
		String retval = "[" + arr[0].toString();
		for(int i = 1; i < arr.length; i++){
			retval = retval + ", " + arr[i];
		}
		retval = retval + "]";
		return retval;
	}
}
