package com.starquestminecraft.automators;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.Dropper;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.Recipe;
import org.bukkit.inventory.meta.ItemMeta;

import com.starquestminecraft.sqtechbase.SQTechBase;
import com.starquestminecraft.sqtechbase.gui.GUI;
import com.starquestminecraft.sqtechbase.objects.GUIBlock;
import com.starquestminecraft.sqtechbase.objects.Machine;
import com.starquestminecraft.sqtechbase.util.InventoryUtils;
import com.starquestminecraft.sqtechbase.util.ObjectUtils;

import net.milkbowl.vault.economy.Economy;

public class AutomatorGUI extends GUI {
	
	public AutomatorGUI(Player player, int id) {
		super(player, id);
	}

	GUIBlock guiBlock;
	Automator automator;
	
	@Override
	public void open() {

		Machine machine = ObjectUtils.getMachineFromMachineGUI(this);
		
			guiBlock = machine.getGUIBlock();
			if(machine instanceof Automator) {
				automator = (Automator) machine;
			}
			else {
				SQTechBase.machines.remove(machine);
				Automator a = new Automator(machine.getEnergy(), machine.getGUIBlock(), machine.getMachineType());
				a.data.put("level", machine.data.get("level"));
				a.owner = owner;
				SQTechBase.machines.add(a);
				automator = a;
			}
			
		Integer level = ((Integer) automator.data.get("level"));
				
		Inventory gui = Bukkit.createInventory(owner, 27, ChatColor.GRAY + "AutoCrafter");
		
		if(automator.screen == 0) {
			gui.setItem(0, InventoryUtils.createSpecialItem(Material.WORKBENCH, (short) 0, ChatColor.RESET + "AutoCrafter", new String[] {ChatColor.RESET + "" + ChatColor.GRAY + "Automatically crafts items.", ChatColor.RESET + "" + ChatColor.GRAY + "Currently, this autocrafter can",ChatColor.RESET + "" + ChatColor.GRAY + "support up to " + Integer.toString(level) + " crafting tables.", ChatColor.RED + "" + ChatColor.MAGIC + "Contraband"}));
			if(level < 4) {
				gui.setItem(1, InventoryUtils.createSpecialItem(Material.ANVIL, (short) 0, ChatColor.RESET + "Upgrade", new String[] {ChatColor.RESET + "" + ChatColor.GOLD + "Cost: " + Double.toString(automator.upgradeCost), ChatColor.RED + "" + ChatColor.MAGIC + "Contraband"}));
			}
			gui.setItem(2, InventoryUtils.createSpecialItem(Material.IRON_PICKAXE, (short) 0, ChatColor.RESET + "Choose Recipe", new String[] {ChatColor.RED + "" + ChatColor.MAGIC + "Contraband"}));
			
			if(automator.recipe == null) {
				gui.setItem(3, InventoryUtils.createSpecialItem(Material.BARRIER, (short) 0, ChatColor.RESET + "Selected Recipe", new String[] {ChatColor.RESET + "No recipe has been selected!", ChatColor.RED + "" + ChatColor.MAGIC + "Contraband"}));
			}
			else {
				gui.setItem(3, InventoryUtils.createSpecialItem(automator.recipe.getResult().getType(), (short) 0, ChatColor.RESET + "Selected Recipe", new String[] {ChatColor.RESET + automator.recipe.getResult().getType().toString(), ChatColor.RED + "" + ChatColor.MAGIC + "Contraband"}));
			}
			
			gui.setItem(8, InventoryUtils.createSpecialItem(Material.REDSTONE, (short) 0, ChatColor.RESET + "Energy", new String[] {ChatColor.RESET + "" + ChatColor.GRAY + Integer.toString(automator.getEnergy()), ChatColor.RED + "" + ChatColor.MAGIC + "Contraband"}));
			gui.setItem(26, InventoryUtils.createSpecialItem(Material.WOOD_DOOR, (short) 0, ChatColor.RESET + "Back to Main GUI", new String[] {ChatColor.RED + "" + ChatColor.MAGIC + "Contraband"}));
		}
		else if(automator.screen == 1) {
			
		}
		
		owner.openInventory(gui);
		
		if (SQTechBase.currentGui.containsKey(owner)) {
			
			SQTechBase.currentGui.remove(owner);
			SQTechBase.currentGui.put(owner,  this);
			
		} else {
			
			SQTechBase.currentGui.put(owner,  this);
			
		}

		
	}
	
	
	@Override
	public void click(InventoryClickEvent event) {
		
		SQAutomators plugin = com.starquestminecraft.automators.SQAutomators.sqamInstance;
		
		Integer level = ((Integer) automator.data.get("level"));
		
		if(event.getCurrentItem() != null && event.getCurrentItem().getType() != Material.AIR) {
			if(event.getCurrentItem().getItemMeta().getDisplayName().contains("Upgrade")) {
				
				Economy economy = plugin.getEconomy();
				
				if(economy.getBalance(owner) < automator.upgradeCost) {
					return;
				}
				
				level = level + 1;
				automator.data.put("level", level);
				economy.withdrawPlayer(owner, automator.upgradeCost);
				
				Inventory gui = event.getClickedInventory();
				
				gui.setItem(0, InventoryUtils.createSpecialItem(Material.WORKBENCH, (short) 0, ChatColor.RESET + "AutoCrafter", new String[] {ChatColor.RESET + "" + ChatColor.GRAY + "Automatically crafts items.", ChatColor.RESET + "" + ChatColor.GRAY + "Currently, this autocrafter can",ChatColor.RESET + "" + ChatColor.GRAY + "support up to " + Integer.toString(level) + " crafting tables.", ChatColor.RED + "" + ChatColor.MAGIC + "Contraband"}));
				if(level >= 4) {
					gui.setItem(1, new ItemStack(Material.AIR));
				}
			}
		
		if(event.getCurrentItem().getType() != Material.AIR) {
			if(event.getCurrentItem().getItemMeta().getDisplayName().contains("Back to Main GUI")) {
				
				close = false;
				automator.getGUIBlock().getGUI(owner).open();
				close = true;
			
			}
			
			if(event.getCurrentItem().getItemMeta().getDisplayName().contains("Choose Recipe")) {
			
				automator.currentRecipes.clear();
				automator.pageList.clear();
				automator.openRecipes.clear();
				
				Block mainBlock = automator.getGUIBlock().getLocation().getBlock();
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
				
				
				if(north) {
					
					Dropper dropper = (Dropper) mainBlock.getRelative(BlockFace.NORTH).getState();
					inputInventories.add(dropper.getInventory());
					
				}
				
				if(east) {
					
					Dropper dropper = (Dropper) mainBlock.getRelative(BlockFace.EAST).getState();
					inputInventories.add(dropper.getInventory());
					
				}
				
				if(south) {
					
					Dropper dropper = (Dropper) mainBlock.getRelative(BlockFace.SOUTH).getState();
					inputInventories.add(dropper.getInventory());
					
				}
				
				if(west) {
					
					Dropper dropper = (Dropper) mainBlock.getRelative(BlockFace.WEST).getState();
					inputInventories.add(dropper.getInventory());
					
				}
				
				
				HashMap<ItemStack, Ingredient> ingredientList = SQAutomators.ingredients;
				
				/*for(Ingredient i : ingredientList.values()) {
					
					Bukkit.broadcastMessage(i.item.toString());
					if(i.item != null) {
						Bukkit.broadcastMessage("IngredientItem");
						Bukkit.broadcastMessage(i.item.toString());
						Bukkit.broadcastMessage(i.item.getItemMeta().toString());
						Bukkit.broadcastMessage("");
					}
				
				}
				*/
				for(int i=0; i<inputInventories.size(); i++) {
					Inventory inventory = inputInventories.get(i);
					
					for(ItemStack item : inventory.getContents()) {
						
						ItemStack newItem = null;
						
						if(item != null) {
							newItem = item.clone();
							newItem.setAmount(1);
						}
						
						/*Bukkit.broadcastMessage("InvItem");
						if(newItem != null) {
							Bukkit.broadcastMessage(newItem.toString());
							Bukkit.broadcastMessage(newItem.getItemMeta().toString());
							Bukkit.broadcastMessage("");
						}
						*/
						if(ingredientList.containsKey(newItem)) {
							ArrayList<Recipe> recipes = ingredientList.get(newItem).getRecipes();
							for(int x=0; x<recipes.size(); x++) {
								Recipe r = recipes.get(x);
								//Bukkit.broadcastMessage(r.getResult().toString());
								//Bukkit.broadcastMessage("");
								
								if(!automator.currentRecipes.contains(r)) {
									automator.currentRecipes.add(recipes.get(x));
								}
								
							}
							
						}
						
					}
					
				}
				
				Inventory recipeGUI = Bukkit.createInventory(owner, 27, ChatColor.GRAY + "AutoCrafter");
				
				recipeGUI.setItem(18, InventoryUtils.createSpecialItem(Material.STAINED_GLASS_PANE, (short) 14, ChatColor.RESET + "Back", new String[] {ChatColor.RED + "" + ChatColor.MAGIC + "Contraband"}));
				recipeGUI.setItem(26, InventoryUtils.createSpecialItem(Material.STAINED_GLASS_PANE, (short) 5, ChatColor.RESET + "Next Page", new String[] {ChatColor.RED + "" + ChatColor.MAGIC + "Contraband"}));
				
				if(!automator.currentRecipes.isEmpty()) {
					
					ArrayList<ArrayList<Recipe>> pages = new ArrayList<ArrayList<Recipe>>();

					if(automator.currentRecipes.size() <= 18) {
						pages.add(automator.currentRecipes);
					}
					else {
						ArrayList<Recipe> newRecipeList = new ArrayList<Recipe>();
						
						for (int i=0; automator.currentRecipes.size() > 0; i++) {
							
							Recipe r = automator.currentRecipes.get(0);
							
							if(newRecipeList.contains(r)) {
								automator.currentRecipes.remove(r);
							}
							else {	
								if(newRecipeList.size() < 18) {
								
									newRecipeList.add(r);
									automator.currentRecipes.remove(r);
									
								}
								else {
								
									pages.add((ArrayList<Recipe>) newRecipeList.clone());
									newRecipeList.clear();
									newRecipeList.add(r);
								
								}
							}
							
						}
						
						if(!newRecipeList.isEmpty()) {
							
							pages.add(newRecipeList);
							
						}
						
					}
					
					automator.page = 0;
					automator.pageList = pages;
					openUpgradePage(recipeGUI);
					
				}
				else {
					
					recipeGUI.setItem(0, InventoryUtils.createSpecialItem(Material.BARRIER, (short) 14, ChatColor.RESET + "No Recipes", new String[] {ChatColor.RESET + "Add items to the input", ChatColor.RESET + "droppers to access recipes.", ChatColor.RED + "" + ChatColor.MAGIC + "Contraband"}));
					
				}
				
				close = false;
				owner.openInventory(recipeGUI);
				close = true;
				
			}
			
		if(event.getCurrentItem() != null && event.getCurrentItem().getItemMeta() != null  && event.getCurrentItem().getItemMeta().getDisplayName() != null) {
			if(event.getCurrentItem().getItemMeta().getDisplayName().contains("Next Page")) {

				Inventory recipeGUI = Bukkit.createInventory(owner, 27, ChatColor.GRAY + "AutoCrafter");
				
				recipeGUI.setItem(18, InventoryUtils.createSpecialItem(Material.STAINED_GLASS_PANE, (short) 14, ChatColor.RESET + "Back", new String[] {ChatColor.RED + "" + ChatColor.MAGIC + "Contraband"}));
				recipeGUI.setItem(26, InventoryUtils.createSpecialItem(Material.STAINED_GLASS_PANE, (short) 5, ChatColor.RESET + "Next Page", new String[] {ChatColor.RED + "" + ChatColor.MAGIC + "Contraband"}));
				
				automator.page = automator.page + 1;
				
				openUpgradePage(recipeGUI);
				
				close = false;
				owner.openInventory(recipeGUI);
				close = true;
				
			}
			else if(event.getCurrentItem().getItemMeta().getDisplayName().contains("Back")) {
				
				if(automator.page > 0) {
					Inventory recipeGUI = Bukkit.createInventory(owner, 27, ChatColor.GRAY + "AutoCrafter");
					
					recipeGUI.setItem(18, InventoryUtils.createSpecialItem(Material.STAINED_GLASS_PANE, (short) 14, ChatColor.RESET + "Back", new String[] {ChatColor.RED + "" + ChatColor.MAGIC + "Contraband"}));
					recipeGUI.setItem(26, InventoryUtils.createSpecialItem(Material.STAINED_GLASS_PANE, (short) 5, ChatColor.RESET + "Next Page", new String[] {ChatColor.RED + "" + ChatColor.MAGIC + "Contraband"}));
					
					automator.page = automator.page - 1;
					
					openUpgradePage(recipeGUI);
					
					close = false;
					owner.openInventory(recipeGUI);
					close = true;
					
				}
				else {
					open();
				}
			}
			else if(event.getCurrentItem().getItemMeta().getDisplayName().contains("Choose This Recipe")) {
				
				automator.recipe = automator.openRecipes.get(event.getSlot());
				
			}
		}
		}
		}
	}
	
	public void openUpgradePage(Inventory currentGUI) {
		
		if(automator.page < automator.pageList.size()) {
			ArrayList<Recipe> recipeList = automator.pageList.get(automator.page);
			
			for(int i=0; i<recipeList.size(); i++) {
				
				Recipe recipe = recipeList.get(i);
				automator.openRecipes = recipeList;
				
				ItemStack displayItem = recipe.getResult();
				ItemMeta meta = displayItem.getItemMeta();
				
				String itemName = displayItem.getType().toString();
				List<String> lore = new ArrayList<String>();
				if(meta.hasLore()) {
					lore = meta.getLore();
				}
				lore.add(ChatColor.RED + "" + ChatColor.MAGIC + "Contraband");
				
				meta.setDisplayName(ChatColor.RESET + "Choose This Recipe");
				meta.setLore(lore);
				displayItem.setItemMeta(meta);
				
				currentGUI.setItem(i, displayItem);
			
			}
		}
		
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
