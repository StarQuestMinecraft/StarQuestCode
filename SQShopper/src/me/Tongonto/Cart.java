package me.Tongonto;

import java.util.ArrayList;
import java.util.HashMap;

import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class Cart {
	Player cartOwner = null;
	ArrayList<ItemStack> cartContents = null;
	Double cartCost = null;
	
	public Cart (Player shopper, ArrayList<ItemStack> contents, Double cost) {
		cartOwner = shopper;
		cartContents = contents;
		cartCost = cost;
	}
	
	public Player getShopper(){
		return cartOwner;
	}
	
	public ArrayList<ItemStack> getCartContents(){
		return cartContents;
	}
	
	public Double getCost(){
		return cartCost;
	}
	
	public void addItemToCart(ItemStack item, Double price) {
		
		boolean addedToStack = false;
		for(int i=0; i< cartContents.size(); i++) {
			if(cartContents.get(i).getType() == item.getType() && cartContents.get(i).getDurability() == item.getDurability()) {
				int stackAmount = cartContents.get(i).getAmount();
				stackAmount = stackAmount + item.getAmount();
				cartContents.get(i).setAmount(stackAmount);
				addedToStack = true;
			}
		}
		
		if(addedToStack == false) {
			cartContents.add(item);
		}
		
		cartCost = cartCost + price;
	
	}
	
	public void checkoutItems() {
			HashMap<Integer, ItemStack> leftOverItems = cartOwner.getInventory().addItem(cartContents.toArray(new ItemStack[cartContents.size()]));
			if(leftOverItems != null){
				World theWorld = cartOwner.getWorld();
				for(ItemStack item : leftOverItems.values()){
					if(item != null) {
						if(item.getAmount() > 64) {
							for(int i = item.getAmount(); i>64; i = i - 64) {
								item.setAmount(item.getAmount() - 64);
								ItemStack newItem = item.clone();
								newItem.setAmount(64);
								theWorld.dropItem(cartOwner.getLocation(), newItem);
							}
							if(item.getAmount() <= 64) {
								theWorld.dropItem(cartOwner.getLocation(), item);
							}
						}
						else {
							theWorld.dropItem(cartOwner.getLocation(), item);
						}
					}
				}
			}
			cartOwner.updateInventory();
	}
	
}

