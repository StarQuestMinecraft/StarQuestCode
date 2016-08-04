package com.starquestminecraft.automators;

import java.util.ArrayList;

import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.Recipe;

public class Ingredient {
	
	ItemStack item;
	ArrayList<Recipe> recipes = new ArrayList<Recipe>();
	
	public Ingredient(ItemStack mainItem) {
		item = mainItem;
	}
	
	public void addRecipe(Recipe recipe) {
		for(int i=0; i<recipes.size(); i++) {
			if(recipes.get(i).getResult().equals(recipe.getResult())) {
				return;
			}
		}
		recipes.add(recipe);
	}
	
	public ArrayList<Recipe> getRecipes() {
		return recipes;
	}
	
}
