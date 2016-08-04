package com.starquestminecraft.automators;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.Recipe;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.inventory.ShapelessRecipe;
import org.bukkit.material.MaterialData;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitTask;

import com.starquestminecraft.sqtechbase.SQTechBase;

import net.milkbowl.vault.economy.Economy;
import net.minecraft.server.v1_10_R1.CraftingManager;
import net.minecraft.server.v1_10_R1.IRecipe;
import net.minecraft.server.v1_10_R1.ShapelessRecipes;

public class SQAutomators extends JavaPlugin {
	
	public static SQAutomators sqamInstance;
	public static Double crafterUpgradeCost = 0.0;
	public static ArrayList<Recipe> recipes = new ArrayList<Recipe>();
	public static HashMap<ItemStack, Ingredient> ingredients = new HashMap<ItemStack, Ingredient>();
	
	private Economy economy = null;
	
	@Override
	public void onEnable() {
		getLogger().info("SQAutomators has been enabled!");
		
		new AutomatorListener(this);
		
		sqamInstance = this;
		
		economy = registerEconomy();
		
		FileConfiguration automatorConfig = this.getConfig();
		crafterUpgradeCost = automatorConfig.getDouble("crafterUpgradeCost");
		Integer runSpeed = automatorConfig.getInt("runspeed");
		Integer productionSpeed = automatorConfig.getInt("productionSpeed");
		BukkitTask task = new AutomatorTask(this, productionSpeed, runSpeed).runTaskLater(this, runSpeed);
		
		AutoCrafter machine = new AutoCrafter(1000);
		SQTechBase.addMachineType(machine);
		
		Iterator<Recipe> recipeList = this.getServer().recipeIterator();
		while (recipeList.hasNext()) {
			Recipe r = recipeList.next();
			recipes.add(r);
			
			if(r instanceof ShapedRecipe) {
				for(ItemStack i : ((ShapedRecipe) r).getIngredientMap().values()) {
					
					if(i != null) {		
						if(ingredients.containsKey(i)) {
							ingredients.get(i).addRecipe(r);
						}
						else {
							Ingredient ingredient = new Ingredient(i);
							ingredient.addRecipe(r);
							ingredients.put(i, ingredient);
						}
					}
										
				}
			}
			
			if(r instanceof ShapelessRecipe) {
				for(ItemStack i : ((ShapelessRecipe) r).getIngredientList()) {
					
					if(i != null) {		
						if(ingredients.containsKey(i)) {
							ingredients.get(i).addRecipe(r);
						}
						else {
							ingredients.put(i, new Ingredient(i));
						}
					}
					
				}
			}
			
		}
		
		
		//This is where custom recipes are added
		List<Recipe> secondRecipeList = new ArrayList<Recipe>();
		
		ShapedRecipe recipe = null;
		recipe = new ShapedRecipe(new ItemStack(Material.REDSTONE, 9));
		recipe.shape("...",".*.","...");
		recipe.setIngredient('*', Material.REDSTONE_BLOCK);
		recipe.setIngredient('.', Material.AIR);
		secondRecipeList.add(recipe);
		
		recipe = new ShapedRecipe(new ItemStack(Material.IRON_INGOT, 9));
		recipe.shape("...",".*.","...");
		recipe.setIngredient('*', Material.IRON_BLOCK);
		recipe.setIngredient('.', Material.AIR);
		secondRecipeList.add(recipe);
		
		recipe = new ShapedRecipe(new ItemStack(Material.GOLD_INGOT, 9));
		recipe.shape("...",".*.","...");
		recipe.setIngredient('*', Material.GOLD_BLOCK);
		recipe.setIngredient('.', Material.AIR);
		secondRecipeList.add(recipe);
		
		recipe = new ShapedRecipe(new ItemStack(Material.DIAMOND, 9));
		recipe.shape("...",".*.","...");
		recipe.setIngredient('*', Material.DIAMOND_BLOCK);
		recipe.setIngredient('.', Material.AIR);
		secondRecipeList.add(recipe);
		
		recipe = new ShapedRecipe(new ItemStack(Material.EMERALD, 9));
		recipe.shape("...",".*.","...");
		recipe.setIngredient('*', Material.EMERALD_BLOCK);
		recipe.setIngredient('.', Material.AIR);
		secondRecipeList.add(recipe);
		
		ItemStack lapis = new ItemStack(Material.INK_SACK, 9);
		lapis.setDurability((short) 4);
		recipe = new ShapedRecipe(lapis);
		recipe.shape("...",".*.","...");
		recipe.setIngredient('*', Material.LAPIS_BLOCK);
		recipe.setIngredient('.', Material.AIR);
		secondRecipeList.add(recipe);
		
		recipe = new ShapedRecipe(new ItemStack(Material.WOOD_BUTTON, 1));
		recipe.shape("...",".*.","...");
		recipe.setIngredient('*', Material.WOOD);
		recipe.setIngredient('.', Material.AIR);
		secondRecipeList.add(recipe);
		
		//Iterates through custom recipes, adds Ingredients
		for(Recipe r : secondRecipeList) {
			recipes.add(r);
			for(ItemStack i : ((ShapedRecipe) r).getIngredientMap().values()) {
				
				if(i != null) {		
					if(ingredients.containsKey(i)) {
						ingredients.get(i).addRecipe(r);
					}
					else {
						Ingredient ingredient = new Ingredient(i);
						ingredient.addRecipe(r);
						ingredients.put(i, ingredient);
					}
				}
									
			}
			
		}
		
	}
	
	@Override
	public void onDisable() {
		getLogger().info("SQAutomators has been disabled!");
	}
	
	private Economy registerEconomy() {
		Economy retval = null;
		RegisteredServiceProvider<Economy> economyProvider = getServer().getServicesManager().getRegistration(net.milkbowl.vault.economy.Economy.class);
		if (economyProvider != null) {
			retval = economyProvider.getProvider();
		}

		return retval;
	}
	
	public Economy getEconomy() {
		return economy;
	}
	
}
