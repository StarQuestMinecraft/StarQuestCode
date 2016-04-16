package com.martinjonsson01.sqsmoothcraft.missile;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.inventory.meta.ItemMeta;

public class Missile {
	
	public static ItemStack missileAmmo() {
		
		ItemStack missileAmmo = new ItemStack(Material.FIREBALL, 1);
		ItemMeta im = missileAmmo.getItemMeta();
		im.setDisplayName(ChatColor.GOLD + "Heat Seeking Missile");
		List<String> lore = new ArrayList<String>();
		lore.add(ChatColor.LIGHT_PURPLE + "A heat seeking");
		lore.add(ChatColor.LIGHT_PURPLE + "missile that can");
		lore.add(ChatColor.LIGHT_PURPLE + "only target");
		lore.add(ChatColor.LIGHT_PURPLE + "SnubFighters");
		im.setLore(lore);
		missileAmmo.setItemMeta(im);
		
		return missileAmmo;
		
	}
	
	public static void setupMissileAmmoRecipe() {
		
		ShapedRecipe missileAmmoRecipe = new ShapedRecipe(missileAmmo());
		missileAmmoRecipe.shape("*%*", "%F%", "*%*");
		missileAmmoRecipe.setIngredient('*', Material.PAPER);
		missileAmmoRecipe.setIngredient('%', Material.FIREBALL);
		missileAmmoRecipe.setIngredient('F', Material.FIREWORK_CHARGE);
		Bukkit.getServer().addRecipe(missileAmmoRecipe);
		
	}
	
}
