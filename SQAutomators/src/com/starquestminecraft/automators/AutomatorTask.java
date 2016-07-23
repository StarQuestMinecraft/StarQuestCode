package com.starquestminecraft.automators;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.Dropper;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.Recipe;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.inventory.ShapelessRecipe;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;

import com.starquestminecraft.sqtechbase.SQTechBase;
import com.starquestminecraft.sqtechbase.objects.Machine;

public class AutomatorTask extends BukkitRunnable {

	private final SQAutomators plugin;
	Integer productionSpeed = 0;
	Integer runSpeed = 0;
	
	public AutomatorTask(SQAutomators plugin, Integer productionRate, Integer runRate) {
		this.plugin = plugin;
		productionSpeed = productionRate;
		runSpeed = runRate;
	}	
	
	@Override
	public void run() {
		
		BukkitTask task = new AutomatorTask(plugin, productionSpeed, runSpeed).runTaskLater(plugin, runSpeed);
		
		for(Machine machine : SQTechBase.machines) {
			if(machine instanceof Automator) {
				
				Automator automator = (Automator) machine;
				
				if(automator.recipe != null) {
					
					if(automator.getEnergy() < 50) {
						
						return;
						
					}
					
					Integer level = ((Integer) automator.data.get("level"));
					
					Block mainBlock = machine.getGUIBlock().getLocation().getBlock();
					Block middleBlock = mainBlock.getRelative(BlockFace.DOWN);
					Block bottomBlock = middleBlock.getRelative(BlockFace.DOWN);
					
					Integer builtLevel = 0;
					
					boolean north = checkDirection(BlockFace.NORTH, mainBlock, middleBlock, bottomBlock);
					boolean south = checkDirection(BlockFace.SOUTH, mainBlock, middleBlock, bottomBlock);
					boolean east = checkDirection(BlockFace.EAST, mainBlock, middleBlock, bottomBlock);
					boolean west = checkDirection(BlockFace.WEST, mainBlock, middleBlock, bottomBlock);
					
					if(north) {
						builtLevel = builtLevel + 1;
					}
					
					if(south) {
						builtLevel = builtLevel + 1;
						if(builtLevel > level) {
							builtLevel = level;
							south = false;
							east = false;
							west = false;
						}
					}
					
					if(east) {
						builtLevel = builtLevel + 1;
						if(builtLevel > level) {
							builtLevel = level;
							east = false;
							west = false;
						}
					}
					
					if(west) {
						builtLevel = builtLevel + 1;
						if(builtLevel > level) {
							builtLevel = level;
							west = false;
						}
					}
				
					ArrayList<Inventory> inputInventories = new ArrayList<Inventory>();
					ArrayList<Inventory> outputInventories = new ArrayList<Inventory>();
				
					if(north) {
						
						Dropper dropper = (Dropper) mainBlock.getRelative(BlockFace.NORTH).getState();
						inputInventories.add(dropper.getInventory());
						outputInventories.add(((Dropper) mainBlock.getRelative(BlockFace.NORTH).getRelative(BlockFace.DOWN).getRelative(BlockFace.DOWN).getState()).getInventory());
						
					}
					
					if(east) {
						
						Dropper dropper = (Dropper) mainBlock.getRelative(BlockFace.EAST).getState();
						inputInventories.add(dropper.getInventory());
						outputInventories.add(((Dropper) mainBlock.getRelative(BlockFace.EAST).getRelative(BlockFace.DOWN).getRelative(BlockFace.DOWN).getState()).getInventory());
						
					}
					
					if(south) {
						
						Dropper dropper = (Dropper) mainBlock.getRelative(BlockFace.SOUTH).getState();
						inputInventories.add(dropper.getInventory());
						outputInventories.add(((Dropper) mainBlock.getRelative(BlockFace.SOUTH).getRelative(BlockFace.DOWN).getRelative(BlockFace.DOWN).getState()).getInventory());
						
					}
					
					if(west) {
						
						Dropper dropper = (Dropper) mainBlock.getRelative(BlockFace.WEST).getState();
						inputInventories.add(dropper.getInventory());
						outputInventories.add(((Dropper) mainBlock.getRelative(BlockFace.WEST).getRelative(BlockFace.DOWN).getRelative(BlockFace.DOWN).getState()).getInventory());
						
					}
					
					Recipe recipe = automator.recipe;
					
					Inventory totalInventory = Bukkit.createInventory(automator.owner, 54);
					
					for(int i=0; i<inputInventories.size(); i++) {
						
						Inventory inv = inputInventories.get(i);
						
						for(ItemStack item : inv.getContents()) {
							if(item != null) {
								
								totalInventory.addItem(item.clone());
								
							}
						}
						
					}
					
					
					List<ItemStack> ingredientItems = new ArrayList<ItemStack>();
					
					if(recipe instanceof ShapedRecipe) {
						for(ItemStack ingredient : ((ShapedRecipe) recipe).getIngredientMap().values()) {
							
							if(ingredient != null) {
									
								boolean addedAmount = false;
								
								for(int i=0; i<ingredientItems.size(); i++) {
									
									ItemStack currentIngredient = ingredientItems.get(i);
									
									if(ingredient.getType().equals(currentIngredient.getType())) {
										addedAmount = true;
										currentIngredient.setAmount(currentIngredient.getAmount() + ingredient.getAmount());
									}
									
								}
								
								if(!addedAmount) {
									ingredientItems.add(ingredient);
								}
								
							}
						}
					}
					
					if(recipe instanceof ShapelessRecipe) {
						
						ingredientItems = ((ShapelessRecipe) recipe).getIngredientList();
							
					}
					
					
					craftRecipe(automator, inputInventories, outputInventories, totalInventory, ingredientItems, recipe);
					if(level == 2) {
						craftRecipe(automator, inputInventories, outputInventories, totalInventory, ingredientItems, recipe);
					}
					if(level == 3) {
						craftRecipe(automator, inputInventories, outputInventories, totalInventory, ingredientItems, recipe);
						craftRecipe(automator, inputInventories, outputInventories, totalInventory, ingredientItems, recipe);
					}
					if(level == 4) {
						craftRecipe(automator, inputInventories, outputInventories, totalInventory, ingredientItems, recipe);
						craftRecipe(automator, inputInventories, outputInventories, totalInventory, ingredientItems, recipe);
						craftRecipe(automator, inputInventories, outputInventories, totalInventory, ingredientItems, recipe);
					}
										
				}
				
			}
		}
	}
	
	public void craftRecipe(Automator automator, List<Inventory> inventories, List<Inventory> outputInventories, Inventory totalInventory, List<ItemStack> ingredientItems, Recipe recipe) {
		
		List<ItemStack> inventoryContents = new ArrayList<ItemStack>();
		
		for(ItemStack item : totalInventory) {
			if(item != null) {
				boolean addedAmount = false;	
				
				if(!inventoryContents.isEmpty()) {
					int invContentsSize = inventoryContents.size();
					for(int x=0; x<invContentsSize; x++) {
						ItemStack i = inventoryContents.get(x);
						if(i.getType() == item.getType()) {
						
							addedAmount = true;
							i.setAmount(i.getAmount() + item.getAmount());
						
						}
					}
				}
				else {
					inventoryContents.add(item);
				}
				
				if(!addedAmount) {
					
					inventoryContents.add(item);
				
				}
				
			}
		}
		
		ArrayList<ItemStack> itemsLeftOver = new ArrayList<ItemStack>();
		
		for(int i=0; i<ingredientItems.size(); i++) {
			
			itemsLeftOver.add(ingredientItems.get(i));
			
		}
		
		if(inventoryContents.isEmpty()) {
			return;
		}
		
			
		for(int x=0; x<itemsLeftOver.size(); x++) {
			ItemStack ingrItem = itemsLeftOver.get(x);
				
				if(!totalInventory.contains(ingrItem.getType())) {
					
					return;
					
				}
				
				for(int i=0; i<inventoryContents.size(); i++) {
					ItemStack item = inventoryContents.get(i);
					
					if(item.getType().equals(ingrItem.getType())) {
						
						if(item.getAmount() < ingrItem.getAmount()) {
							
							return;
							
						}
					
					}
				
				
				}
			
		}
		
		boolean addedItem = false;
		
		ItemStack producedItem = recipe.getResult();
		Integer inventory = 0;
		
		for(Inventory inv : outputInventories) {
			
			inventory = inventory + 1;

		if(inventory < 5) {
			if(inv.firstEmpty() == -1) {
				if(inv.first(producedItem.getType()) != -1) {
					
					HashMap<Integer, ItemStack> leftOverItems = inv.addItem(producedItem);
					
					if(!leftOverItems.isEmpty()) {
						ItemStack unAddedItem = leftOverItems.get(0);
						Integer itemsAdded = producedItem.getAmount() - unAddedItem.getAmount();
						producedItem.setAmount(producedItem.getAmount() - itemsAdded);
						
						if(inventory >= 4) {
														
							if(unAddedItem.getAmount() != recipe.getResult().getAmount()) {
								
								Integer amountToRemove = recipe.getResult().getAmount() - unAddedItem.getAmount();
								
								unAddedItem.setAmount(amountToRemove);
								
								inv.removeItem(unAddedItem);
								
								return;
								
							}
							
						}
						
					}
					
					
					
				}
				else {
					return;
				}
			}
			else {
				inventory = 5;
				inv.addItem(producedItem);
			}
		}
			
		}
				
		for(Inventory inv : inventories) {

			for(int i=0; i<itemsLeftOver.size(); i++) {
				ItemStack ingredient = itemsLeftOver.get(i);
				
				if(inv.contains(ingredient.getType())) {

					for(int x=0; x<inv.getContents().length; x++) {

						ItemStack item = inv.getItem(x);
						

						
						if(item != null) {

							if(item.getType().equals(ingredient.getType())) {

								if(item.getAmount() >= ingredient.getAmount()) {

									
									inv.removeItem(ingredient);
									x = 100;

								}
								else {

									ingredient.setAmount(ingredient.getAmount() - item.getAmount());
									inv.removeItem(item);

								}
						}
					}
					
					}
				}
			}
		}
		
		automator.setEnergy(automator.getEnergy() - 50);
		
	}
	
	public boolean checkDirection(BlockFace blockFace, Block mainBlock, Block middleBlock, Block bottomBlock) {
		
		if(mainBlock.getRelative(blockFace).getType().equals(Material.DROPPER)) {
			if(middleBlock.getRelative(blockFace).getType().equals(Material.WORKBENCH)) {
				if(bottomBlock.getRelative(blockFace).getType().equals(Material.DROPPER)) {
					return true;
				}
			}
		}
		
		return false;
		
	}
	
}