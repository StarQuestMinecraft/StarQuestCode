package me.dan14941.sqtechdrill;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapelessRecipe;
import org.bukkit.inventory.meta.BookMeta;

public class UpgradeItem
{
	ItemStack upgradeItem;
	private SQTechDrill main;
	
	public UpgradeItem(SQTechDrill plugin)
	{
		this.main = plugin;
		upgradeItem = new ItemStack(Material.WRITTEN_BOOK);
		
		BookMeta bookMeta = (BookMeta) upgradeItem.getItemMeta();
		//bookMeta.setDisplayName(ChatColor.BLUE + "" + ChatColor.ITALIC + "Drill Upgrade");
		bookMeta.setAuthor(ChatColor.AQUA + "Drill");
		bookMeta.setPages("Test pgae 1\n s","asgsgge 2");
		bookMeta.setGeneration(BookMeta.Generation.ORIGINAL);
		bookMeta.setTitle(ChatColor.BLUE + "" + ChatColor.ITALIC + "Drill Upgrade");
		upgradeItem.setItemMeta(bookMeta);
		
		ShapelessRecipe recipe = new ShapelessRecipe(upgradeItem);
		
		//TODO temp recipe
		recipe.addIngredient(Material.DIRT); //Adds the specified ingredient.
		
		main.getServer().addRecipe(recipe);
	}
}
