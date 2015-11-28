package com.ginger_walnut.sqsheepshearboost;

import io.netty.util.internal.ThreadLocalRandom;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Sheep;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerShearEntityEvent;
import org.bukkit.inventory.ItemStack;

public class Events implements Listener{	

	@SuppressWarnings("deprecation")
	@EventHandler
	public void onPlayerShearEntity (PlayerShearEntityEvent event) {
		
		int sheepShearMultiplier = Main.getPluginMain().getConfig().getInt("sheep shearing multiplier");
		sheepShearMultiplier = sheepShearMultiplier - 1;
		
		if (sheepShearMultiplier > 0) {
			
			Entity entity = event.getEntity();
			
			if (entity.getType().equals(EntityType.SHEEP)) {
			
				Sheep sheep = (Sheep) entity;
				
				int woolAmount = 0;
				
				for (int i = 0; i < sheepShearMultiplier; i ++) {
					
					woolAmount = woolAmount + ThreadLocalRandom.current().nextInt(1, 4);

				}
				
				ItemStack wool = new ItemStack(Material.WOOL, woolAmount, sheep.getColor().getData());
				
				entity.getWorld().dropItem(entity.getLocation(), wool);
				
			}
			
		}
	
	}
	
	@EventHandler
	public void onPlayerJoin (PlayerJoinEvent event) {
		
		event.getPlayer().sendMessage(ChatColor.GOLD + "SheepShearBoost: The sheep shearing multiplier is currently " + Main.sheepShearMultiplier);
		
	}
	
}
