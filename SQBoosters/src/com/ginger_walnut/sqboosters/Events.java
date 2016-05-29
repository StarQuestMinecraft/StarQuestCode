package com.ginger_walnut.sqboosters;


import java.util.List;

import io.netty.util.internal.ThreadLocalRandom;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Sheep;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerShearEntityEvent;
import org.bukkit.inventory.EntityEquipment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.scheduler.BukkitScheduler;

import com.gmail.nossr50.api.ExperienceAPI;
import com.gmail.nossr50.datatypes.skills.XPGainReason;
import com.gmail.nossr50.events.experience.McMMOPlayerXpGainEvent;

public class Events implements Listener{	

	@EventHandler
	public void onPlayerJoin (PlayerJoinEvent event) {
		
		final Player player = event.getPlayer();
		
		player.setMetadata("lastExp", new FixedMetadataValue(SQBoosters.getPluginMain(), player.getTotalExperience()));
		
		BukkitScheduler scheduler = Bukkit.getServer().getScheduler();

		scheduler.scheduleSyncDelayedTask(SQBoosters.getPluginMain(), new Runnable() {
	
			@Override
			public void run() {
				
		
				for (int i = 0; i < SQBoosters.multipliers.length; i ++) {
					
					if (SQBoosters.multipliers[i] > 1) {
						
						player.sendMessage(ChatColor.GOLD + SQBoosters.permissionName[i] + ChatColor.BLUE + ": The " + SQBoosters.multiplierName[i] + " multiplier is currently " + SQBoosters.multipliers[i]);
						
					}

				}
				
			}
		
		}, 1);
		
	}
	
	@EventHandler
	public void onMcMMOPlayerXpGain(McMMOPlayerXpGainEvent event) {
		
		if (!event.getXpGainReason().equals(XPGainReason.COMMAND)) {
			
			int multiplier = SQBoosters.getMCMMOBooster(event.getSkill());
			
			if (multiplier > 1) {
				
				event.setXpGained(event.getXpGained() * multiplier);
				
			}
			
		}
		
	}
	
	@SuppressWarnings("deprecation")
	@EventHandler
	public void onPlayerShearEntity (PlayerShearEntityEvent event) {
		
		int sheepShearMultiplier = SQBoosters.multipliers[2];
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
	public void onEntityDeath (EntityDeathEvent event) {
		
		int mobDropMultiplier = SQBoosters.multipliers[1];
		mobDropMultiplier = mobDropMultiplier - 1;
		
		Entity entity = event.getEntity();
		
		List<String> allowedEntities = SQBoosters.getPluginMain().getConfig().getStringList("entities");
		
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
	
}
