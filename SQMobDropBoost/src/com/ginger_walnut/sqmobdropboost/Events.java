package com.ginger_walnut.sqmobdropboost;

import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.inventory.EntityEquipment;
import org.bukkit.inventory.ItemStack;

public class Events implements Listener{	

	@EventHandler
	public void onEntityDeath (EntityDeathEvent event) {
		
		int mobDropMultiplier = Main.getPluginMain().getConfig().getInt("mob drop multiplier");
		mobDropMultiplier = mobDropMultiplier - 1;
		
		Entity entity = event.getEntity();
		
		List<String> allowedEntities = Main.getPluginMain().getConfig().getStringList("entities");
		
		if (allowedEntities.contains(entity.getName())) {
			
			List<ItemStack> drops = event.getDrops();
			EntityEquipment equipment = ((LivingEntity) entity).getEquipment();	
			
			if (drops.contains(equipment.getHelmet())) {
				
				drops.remove(equipment.getHelmet());
				
			}
			
			if (drops.contains(equipment.getChestplate())) {
				
				drops.remove(equipment.getChestplate());
				
			}
			
			if (drops.contains(equipment.getLeggings())) {
				
				drops.remove(equipment.getLeggings());
				
			}
			
			if (drops.contains(equipment.getBoots())) {
				
				drops.remove(equipment.getBoots());
				
			}
			
			if (drops.contains(equipment.getItemInHand())) {
				
				for (int i = 0; i < drops.size(); i ++) {
					
					if (drops.get(i).equals(equipment.getItemInHand())) {
						
						if (drops.get(i).getAmount() > 1) {
							
							ItemStack itemStack = drops.get(i);
							
							itemStack.setAmount(drops.get(i).getAmount() - 1);
							drops.set(i, itemStack);
							
						} else {
							
							drops.remove(equipment.getItemInHand());
							
						}
						
					}
					
				}
				
			}
			
			for (int i = 0; i < mobDropMultiplier; i ++) {
					
				for (int j = 0; j < event.getDrops().size(); j ++) {
					
					entity.getWorld().dropItem(entity.getLocation(), event.getDrops().get(j));
										
				}

			}
			
		}
		
	}
	
	@EventHandler
	public void onPlayerJoin (PlayerJoinEvent event) {
		
		event.getPlayer().sendMessage(ChatColor.GOLD + "MobDropBoost: The mob drop multiplier is currently " + Main.mobDropMultiplier);
		
	}
	
}
