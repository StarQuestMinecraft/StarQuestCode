package com.ginger_walnut.sqblasters;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.ProjectileLaunchEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerPickupItemEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.metadata.MetadataValue;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitScheduler;

public class Events implements Listener{
	
	@EventHandler
	public void onPlayerClick(PlayerInteractEvent event) {
		
			Action eAction = event.getAction();	
			Player player = event.getPlayer();
			ItemStack handItem = player.getItemInHand();
			
			//Checking to see if the player right clicked
			if (eAction == Action.RIGHT_CLICK_AIR || eAction == Action.RIGHT_CLICK_BLOCK) {
				
				if (handItem.getType() == Material.BOW) {
					
					ItemStack bow = handItem;
					player.getInventory().setItemInHand(bow);
					
				}
				
				//Checking if the item in hand is a bow/blaster and they can shoot
				if (handItem.getType() == Material.BOW && !event.getPlayer().hasMetadata("reload_timer") && !event.getPlayer().hasMetadata("fire_timer")) {
					
					double bowPower = handItem.getEnchantmentLevel(Enchantment.ARROW_DAMAGE);
					int bowInfinity = handItem.getEnchantmentLevel(Enchantment.ARROW_INFINITE);
					int bowFlame = handItem.getEnchantmentLevel(Enchantment.ARROW_FIRE);
					
					double bowDamagePrecentIncrease = 25 * bowPower; 
					bowDamagePrecentIncrease = bowDamagePrecentIncrease / 100;
					bowDamagePrecentIncrease = bowDamagePrecentIncrease + 1;
					
					double bowDamage = (Main.getPluginConfig().getDouble("damage") * bowDamagePrecentIncrease);
					
					int ammo = 0;
					int ammoPerPack = 0;
					int reloadTime = 0;
					int fireTime = 0;
					
					//Checking to make sure that the blaster lore is updated to the config
					if (handItem.getItemMeta().hasLore()) {
					
						List<String> lore2 = handItem.getItemMeta().getLore();
						ItemMeta meta2 = (ItemMeta) handItem.getItemMeta();
					
						if (!(lore2.get(0).equals("§6Ammo Per Pack: §f" + Main.getPluginConfig().getString("ammo per pack")) && 
							lore2.get(2).equals("§6Damage: §f" + Main.getPluginConfig().getString("damage"))  && 
							lore2.get(3).equals("§6Reload Time: §f" + Main.getPluginConfig().getString("reload time")) && 
							lore2.get(4).equals("§6Fire Time: §f" + Main.getPluginConfig().getString("fire time")))) {
							
							lore2.clear();
							
							lore2.add(ChatColor.GOLD + "Ammo Per Pack: " + ChatColor.WHITE + Main.getPluginConfig().getString("ammo per pack"));
							lore2.add(ChatColor.GOLD + "Ammo: " + ChatColor.WHITE + "0");
							lore2.add(ChatColor.GOLD + "Damage: " + ChatColor.WHITE + bowDamage);
							lore2.add(ChatColor.GOLD + "Reload Time: " + ChatColor.WHITE + Main.getPluginConfig().getString("reload time"));
							lore2.add(ChatColor.GOLD + "Fire Time: " + ChatColor.WHITE + Main.getPluginConfig().getString("fire time"));
							meta2.setLore(lore2);
							meta2.setDisplayName("Blaster " + ChatColor.GOLD +  "(" + ammo + "/" + ammoPerPack + ")");
							handItem.setItemMeta(meta2);
						
						}
						
					}
					
					if (!handItem.getItemMeta().hasLore()) {
						
						List<String> lore = new ArrayList<String>();
						ItemMeta meta = (ItemMeta) handItem.getItemMeta();
						
						lore.add(ChatColor.GOLD + "Ammo Per Pack: " + ChatColor.WHITE + Main.getPluginConfig().getString("ammo per pack"));
						lore.add(ChatColor.GOLD + "Ammo: " + ChatColor.WHITE + "0");
						lore.add(ChatColor.GOLD + "Damage: " + ChatColor.WHITE + bowDamage);
						lore.add(ChatColor.GOLD + "Reload Time: " + ChatColor.WHITE + Main.getPluginConfig().getString("reload time"));
						lore.add(ChatColor.GOLD + "Fire Time: " + ChatColor.WHITE + Main.getPluginConfig().getString("fire time"));
						meta.setLore(lore);
						meta.setDisplayName("Blaster " + ChatColor.GOLD +  "(" + ammo + "/" + ammoPerPack + ")");
						handItem.setItemMeta(meta);
						
					}
					
					List<String> bowLore = handItem.getItemMeta().getLore();
					ItemMeta bowMeta = (ItemMeta) handItem.getItemMeta();
					
					//Checking to see if the blaster has correct lore
					if (handItem.getItemMeta().hasLore() && (bowLore.get(0).substring(2, 15).equals("Ammo Per Pack") && bowLore.get(1).substring(2, 6).equals("Ammo") && bowLore.get(2).substring(2, 8).equals("Damage")  && bowLore.get(3).substring(2, 13).equals("Reload Time") && bowLore.get(4).substring(2, 11).equals("Fire Time"))) {
						
						ammo = Integer.parseInt(bowLore.get(1).substring(10));
						ammoPerPack = Integer.parseInt(bowLore.get(0).substring(19));
						reloadTime = (int) (Double.parseDouble(bowLore.get(3).substring(17)));
						fireTime = (int) (Double.parseDouble(bowLore.get(4).substring(15)));
						
					} else {
						
						List<String> lore = new ArrayList<String>();
						ItemMeta meta = (ItemMeta) handItem.getItemMeta();
						
						lore.add(ChatColor.GOLD + "Ammo Per Pack: " + Main.getPluginConfig().getString("ammo per pack"));
						lore.add(ChatColor.GOLD + "Ammo: " + ChatColor.WHITE + "0");
						lore.add(ChatColor.GOLD + "Damage: " + ChatColor.WHITE + bowDamage);
						lore.add(ChatColor.GOLD + "Reload Time: " + ChatColor.WHITE + Main.getPluginConfig().getString("reload time"));
						lore.add(ChatColor.GOLD + "Fire Time: " + ChatColor.WHITE + Main.getPluginConfig().getString("fire time"));
						meta.setLore(lore);
						meta.setDisplayName("Blaster " + ChatColor.GOLD +  "(" + ammo + "/" + ammoPerPack + ")");
						handItem.setItemMeta(meta);
						
						ammo = 0;
						ammoPerPack = 7;
						reloadTime = 500;
						fireTime = 250;
						
					}
					
					bowLore.set(2, ChatColor.GOLD + "Damage: " + ChatColor.WHITE + bowDamage);
					bowMeta.setLore(bowLore);
					handItem.setItemMeta(bowMeta);
					
					if (ammo == 0) {
						
						if (player.getInventory().contains(Material.ARROW) || player.getGameMode().equals(GameMode.CREATIVE) || bowInfinity == 1) {
							
							ammo = ammoPerPack;
							
							bowLore.set(1, ChatColor.GOLD + "Ammo: " + ChatColor.WHITE + ammo);
							bowMeta.setLore(bowLore);
							bowMeta.setDisplayName(ChatColor.GREEN + "Reloading...");
							handItem.setItemMeta(bowMeta);
							
							if (!player.getGameMode().equals(GameMode.CREATIVE) && !(bowInfinity == 1)) {
								
								player.getInventory().removeItem(new ItemStack(Material.ARROW,1));
								
							}
							
							//Creating a reload timer for the player
							(new Timer()).run(player, ammoPerPack, "reload_timer", reloadTime);
							
						}
						
					} else {
						
						Bukkit.getWorlds().get(0).playSound(player.getLocation(), Sound.SHOOT_ARROW, 1, 1);
						
						Arrow arrow = player.launchProjectile(Arrow.class);

						arrow.setMetadata("damage", new FixedMetadataValue(Main.getPluginMain(), bowLore.get(2).substring(12)));
						arrow.setMetadata("no_pickup", new FixedMetadataValue(Main.getPluginMain(), true));
						
						if (bowFlame == 1) {
							
							arrow.setFireTicks(200);
							
						}
						
						ammo = ammo - 1;
						
						bowLore.set(1, ChatColor.GOLD + "Ammo: " + ChatColor.WHITE + ammo);
						bowMeta.setLore(bowLore);
						bowMeta.setDisplayName("Blaster " + ChatColor.GOLD +  "(" + ammo + "/" + ammoPerPack + ")");
						handItem.setItemMeta(bowMeta);
						
						//Setting fire timer
						(new Timer()).run(player, ammoPerPack, "fire_timer", fireTime);
						
						if (ammo == 0) {
							
							if (player.getInventory().contains(Material.ARROW) || player.getGameMode().equals(GameMode.CREATIVE) || bowInfinity == 1) {
								
								ammo = ammoPerPack;
								
								bowLore.set(1, ChatColor.GOLD + "Ammo: " + ChatColor.WHITE + ammo);
								bowMeta.setLore(bowLore);
								bowMeta.setDisplayName(ChatColor.GREEN + "Reloading...");
								handItem.setItemMeta(bowMeta);
								
								if (!player.getGameMode().equals(GameMode.CREATIVE) && !(bowInfinity == 1)) {
									
									player.getInventory().removeItem(new ItemStack(Material.ARROW,1));
									
								}
								
								(new Timer()).run(player, ammoPerPack, "reload_timer", reloadTime);
							
							}
							
						}
						
					}
					
				}
							
			} else if((eAction == Action.LEFT_CLICK_AIR && player.getItemInHand().getType() == Material.BOW) || (eAction == Action.LEFT_CLICK_BLOCK && player.getItemInHand().getType() == Material.BOW)) {		
				
				
				if (player.hasPotionEffect(PotionEffectType.SLOW)) {
					
					player.removePotionEffect(PotionEffectType.SLOW);
					
				} else {
						
					player.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, 1000000000, Main.getPluginConfig().getInt("scope")));
					
				}
				
			}
				
		}
	
	@SuppressWarnings({ "deprecation" })
	@EventHandler
	public void onProjectileLuanch(ProjectileLaunchEvent event) {
		
		if(event.getEntity() instanceof Arrow) {
			
			final Arrow arrow = (Arrow) event.getEntity();
			
			//Making the arrow fly straight
			(new NoGravity()).run(arrow);
			
			BukkitScheduler scheduler = Bukkit.getServer().getScheduler();
			
			Player player = (Player) arrow.getShooter();
			
			final int bowInfinty = player.getItemInHand().getEnchantmentLevel(Enchantment.ARROW_INFINITE);
			
			//Scheduling a delayed task because the arrow needs to be shot first
			scheduler.scheduleSyncDelayedTask(Main.getPluginMain(), new Runnable() {
				
				@Override
				public void run() {
					
					//Checking if the shooter is not null (Dispenser or other source)
					if(!(arrow.getShooter() == null)) {
						
						//Checking to see if the arrow was fired by the player but not by correct blaster means 
						if (!arrow.hasMetadata("no_pickup") && arrow.getShooter().getType().equals(EntityType.PLAYER)) {
						
							Player player = (Player) arrow.getShooter();
							
							arrow.remove();
							
							if (!(bowInfinty == 1 || player.getGameMode().equals(GameMode.CREATIVE))) {
								
								player.getInventory().addItem(new ItemStack(Material.ARROW));

								
							}
							
						}
						
					}
				
				}
				
			}, 1);
		
		}
		
	}

	@EventHandler
	public void onPlayerPickupItem(PlayerPickupItemEvent event) {
		
		if(event.getItem().hasMetadata("no_pickup")) {
			
			event.setCancelled(true);

    	}

	}
	
	@EventHandler
	public void onEntityDamageByEntity(EntityDamageByEntityEvent event) {		
		
		if (event.getDamager().getType().equals(EntityType.ARROW)) {
			
			Arrow arrow = (Arrow) event.getDamager();

			if (arrow.hasMetadata("no_pickup")) {
				
				double damage = 0;
				
				List<MetadataValue> values = arrow.getMetadata("damage");
				
				for (MetadataValue value : values) {
					
					if (value.getOwningPlugin() == Main.getPluginMain()) {
						
						damage = Double.parseDouble(value.value().toString());
						
					}
					
				}
				
				event.setDamage(damage);
				
				arrow.remove();
				
			}
			
		}
		
	}

}
