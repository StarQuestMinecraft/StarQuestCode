package com.burkentertainment.SQPlanet;

import java.util.Iterator;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.inventory.FurnaceRecipe;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.Recipe;
import org.bukkit.inventory.ShapelessRecipe;

public class BetterRecipes {

	static void addAllRecipes (FileConfiguration config) {
		
		if (config.getBoolean("allowFleshSmelt")) {
			Bukkit.addRecipe(new FurnaceRecipe(new ItemStack(Material.LEATHER),Material.ROTTEN_FLESH));
		}
		
		if (config.getBoolean("allowJerky")){
			ShapelessRecipe jerky = new ShapelessRecipe(new ItemStack(Material.RAW_BEEF));
			jerky.addIngredient(2, Material.ROTTEN_FLESH);
			jerky.addIngredient(Material.SUGAR);
			Bukkit.addRecipe(jerky);		
		}
	}
	
	static void removeAllRecipes() {
		Recipe recipe;
		
		// Check iterator for any of our recipes, if so, remove them
		Iterator<Recipe> iter = Bukkit.recipeIterator();
		while (iter.hasNext()) {
			recipe = iter.next();
			
			// Check if this Recipe is one of the ones we are responsible for
			// First, Furnace Recipes
			try {
				if (recipe instanceof FurnaceRecipe) {
					if ((Material.LEATHER == recipe.getResult().getType()) &&
						(Material.ROTTEN_FLESH == ((FurnaceRecipe) recipe).getInput().getType())){
							iter.remove();
					}
				}
				
				// Check for our Shapeless Recipes
				if (recipe instanceof ShapelessRecipe) {
					if (Material.RAW_BEEF == recipe.getResult().getType()) {
						List<ItemStack> ingredients = ((ShapelessRecipe) recipe).getIngredientList();
						if (3 == ingredients.size() &&
							ingredients.contains(new ItemStack(Material.ROTTEN_FLESH)) &&
							ingredients.contains(new ItemStack(Material.SUGAR))) {
							iter.remove();
						}
					}
				}
			}
			catch (NullPointerException e) {
				System.out.print("[ERROR]: Failed to process Recipe iterator");
				e.printStackTrace();
				// Don't rethrow, attempt recovery
			}
		}
	}
}
