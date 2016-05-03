package com.martinjonsson01.sqsmoothcraft.missile;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.inventory.meta.ItemMeta;

import com.ginger_walnut.sqsmoothcraft.SQSmoothCraft;
import com.whirlwindgames.dibujaron.sqempire.Empire;
import com.whirlwindgames.dibujaron.sqempire.database.object.EmpirePlayer;

public class Missile {
	
	public static List<Player> missileCoolDownList = new ArrayList<Player>();
	
	public static ItemStack standardMissileAmmo() {
		
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
		
		ShapedRecipe missileAmmoRecipe = new ShapedRecipe(standardMissileAmmo());
		missileAmmoRecipe.shape("*%*", "%F%", "*%*");
		missileAmmoRecipe.setIngredient('*', Material.PAPER);
		missileAmmoRecipe.setIngredient('%', Material.FIREBALL);
		missileAmmoRecipe.setIngredient('F', Material.FIREWORK_CHARGE);
		Bukkit.getServer().addRecipe(missileAmmoRecipe);
		
	}
	
	public static ItemStack EMPMissileAmmo() {
		
		ItemStack missileAmmo = new ItemStack(Material.FIREBALL, 1);
		ItemMeta im = missileAmmo.getItemMeta();
		im.setDisplayName(ChatColor.GOLD + "Heat Seeking EMP Missile");
		List<String> lore = new ArrayList<String>();
		lore.add(ChatColor.LIGHT_PURPLE + "A heat seeking");
		lore.add(ChatColor.BLUE + "EMP");
		lore.add(ChatColor.LIGHT_PURPLE + "missile that can");
		lore.add(ChatColor.LIGHT_PURPLE + "only target");
		lore.add(ChatColor.LIGHT_PURPLE + "SnubFighters");
		im.setLore(lore);
		missileAmmo.setItemMeta(im);
		
		return missileAmmo;
		
	}
	
	public static void setupEMPMissileAmmoRecipe() {
		
		ShapedRecipe EMPMissileAmmoRecipe = new ShapedRecipe(EMPMissileAmmo());
		EMPMissileAmmoRecipe.shape("*%*", "%F%", "*%*");
		EMPMissileAmmoRecipe.setIngredient('*', Material.PAPER);
		EMPMissileAmmoRecipe.setIngredient('%', Material.FIREBALL);
		EMPMissileAmmoRecipe.setIngredient('F', Material.REDSTONE_BLOCK);
		Bukkit.getServer().addRecipe(EMPMissileAmmoRecipe);
		
	}
	
	public static ItemStack explosiveMissileAmmo() {
		
		ItemStack missileAmmo = new ItemStack(Material.FIREBALL, 1);
		ItemMeta im = missileAmmo.getItemMeta();
		im.setDisplayName(ChatColor.GOLD + "Heat Seeking Explosive Missile");
		List<String> lore = new ArrayList<String>();
		lore.add(ChatColor.LIGHT_PURPLE + "A heat seeking");
		lore.add(ChatColor.BLUE + "explosive");
		lore.add(ChatColor.LIGHT_PURPLE + "missile that can");
		lore.add(ChatColor.LIGHT_PURPLE + "only target");
		lore.add(ChatColor.LIGHT_PURPLE + "SnubFighters");
		im.setLore(lore);
		missileAmmo.setItemMeta(im);
		
		return missileAmmo;
		
	}
	
	public static void setupExplosiveMissileAmmoRecipe() {
		
		ShapedRecipe explosiveMissileAmmoRecipe = new ShapedRecipe(explosiveMissileAmmo());
		explosiveMissileAmmoRecipe.shape("*%*", "%F%", "*%*");
		explosiveMissileAmmoRecipe.setIngredient('*', Material.PAPER);
		explosiveMissileAmmoRecipe.setIngredient('%', Material.FIREBALL);
		explosiveMissileAmmoRecipe.setIngredient('F', Material.TNT);
		Bukkit.getServer().addRecipe(explosiveMissileAmmoRecipe);
		
	}
	
	public static Player getNearestPlayer(Player shooter) {
		
		for (Player p : Bukkit.getServer().getOnlinePlayers()) {
			
			Player target = p;
			int detectionRange = SQSmoothCraft.config.getInt("weapons.heatseeking missile.detection range");
			
			if (target.getLocation().distance(shooter.getLocation()) <= detectionRange) {
				
				if(SQSmoothCraft.useEmpires) {
					
					EmpirePlayer empShooter = EmpirePlayer.getOnlinePlayer(shooter);
					Empire shooterEmpire = empShooter.getEmpire();
					
					EmpirePlayer empTarget = EmpirePlayer.getOnlinePlayer(target);
					Empire targetEmpire = empTarget.getEmpire();
					
					if(shooterEmpire.equals(targetEmpire)) {
						
						return null;
						
					}
					
				}
				
				if(SQSmoothCraft.playerFriendList.get(shooter.getUniqueId()).contains(target.getUniqueId())) return null;
				
				if (SQSmoothCraft.shipMap.containsKey(target.getUniqueId())) {
					
					if (target != shooter) {
						return target;
					}
				}
				
			}
			
		}
		return null;
		
	}
	
	
}
