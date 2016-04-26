package com.ginger_walnut.sqblasters;

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
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerPickupItemEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.metadata.MetadataValue;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitScheduler;

public class Events implements Listener{
	
	public static void fireBlaster(Player player) {
		
		ItemStack handItem = player.getItemInHand();
		
		if (handItem.getType() == Material.BOW && !player.hasMetadata("reload_timer") && !player.hasMetadata("fire_timer") && handItem.getItemMeta().hasLore()) {
			
			List<String> bowLore = handItem.getItemMeta().getLore();
			int bowFlame = handItem.getEnchantmentLevel(Enchantment.ARROW_FIRE);
			
			if (!bowLore.get(0).substring(10).equals("new blaster")) {
				
				int ammo = Integer.parseInt(bowLore.get(2).substring(10));
				int ammoPerPack = Integer.parseInt(bowLore.get(1).substring(19));
				int fireTime = Integer.parseInt(bowLore.get(5).substring(15));
				
				if (ammo != 0) {
					
					Bukkit.getWorlds().get(0).playSound(player.getLocation(), Sound.ENTITY_ARROW_SHOOT, 1, 1);
					
					Arrow arrow = player.launchProjectile(Arrow.class);
				
					arrow.setMetadata("damage", new FixedMetadataValue(Main.getPluginMain(), bowLore.get(3).substring(12)));
					arrow.setMetadata("no_pickup", new FixedMetadataValue(Main.getPluginMain(), true));
					arrow.setShooter(player);
				
					drainBlasterAmmo(player);
					
					if (bowFlame == 1) {
					
						arrow.setFireTicks(Main.getPluginConfig().getInt(bowLore.get(0).substring(10) + ".flame ticks"));
					
					}
					
					(new Timer()).run(player, ammoPerPack, "fire_timer", fireTime);
					
				} else {
					
					reloadBlaster(player);
					
				}
				
			}
			
		}
		
	}
	
	public static void drainBlasterAmmo (Player player) {
		
		ItemStack handItem = player.getItemInHand();
		
		if (handItem.getType() == Material.BOW && handItem.getItemMeta().hasLore()) {
			
			List<String> bowLore = handItem.getItemMeta().getLore();
			ItemMeta bowMeta = handItem.getItemMeta();
			
			if (!bowLore.get(0).substring(10).equals("new blaster")) {
				
				int ammoPerPack = Integer.parseInt(bowLore.get(1).substring(19));
				int ammo = Integer.parseInt(bowLore.get(2).substring(10));
				
				ammo = ammo - 1;
				
				bowLore.set(2, ChatColor.GOLD + "Ammo: " + ChatColor.WHITE + ammo);
				bowMeta.setLore(bowLore);
				bowMeta.setDisplayName("Blaster " + ChatColor.GOLD +  "(" + ammo + "/" + ammoPerPack + ")");
				handItem.setItemMeta(bowMeta);
				
			}
			
		}
		
	}
	
	public static void reloadBlaster (Player player) {
		
		ItemStack handItem = player.getItemInHand();
		
		int bowInfinity = handItem.getEnchantmentLevel(Enchantment.ARROW_INFINITE);
		
		if (handItem.getType() == Material.BOW && handItem.getItemMeta().hasLore()) {
			
			List<String> bowLore = handItem.getItemMeta().getLore();
			ItemMeta bowMeta = handItem.getItemMeta();
			
			if (!bowLore.get(0).substring(10).equals("new blaster")) {
				
				int ammoPerPack = Integer.parseInt(bowLore.get(1).substring(19));
				int ammo = Integer.parseInt(bowLore.get(2).substring(10));
				int reloadTime = Integer.parseInt(bowLore.get(4).substring(17));
				
				if (player.getInventory().contains(Material.ARROW) || player.getGameMode().equals(GameMode.CREATIVE) || bowInfinity == 1) {
					
					ammo = ammoPerPack;
					
					bowLore.set(2, ChatColor.GOLD + "Ammo: " + ChatColor.WHITE + ammo);
					bowMeta.setLore(bowLore);
					bowMeta.setDisplayName(ChatColor.GREEN + "Reloading...");
					handItem.setItemMeta(bowMeta);
					
					if (!player.getGameMode().equals(GameMode.CREATIVE) && !(bowInfinity == 1)) {
						
						player.getInventory().removeItem(new ItemStack(Material.ARROW,1));
						
					}
					
					//Creating a reload timer for the player
					(new Timer()).run(player, ammoPerPack, "reload_timer", reloadTime);
					
				}
				
			}
			
		}
				
	}
	
	@EventHandler
	public void onPlayerClick(PlayerInteractEvent event) {
		
			Action eAction = event.getAction();	
			Player player = event.getPlayer();
			ItemStack handItem = player.getItemInHand();
			
			//Checking to see if the player right clicked
			if (eAction == Action.RIGHT_CLICK_AIR || eAction == Action.RIGHT_CLICK_BLOCK) {
				
				//Checking if the item in hand is a bow/blaster and they can shoot
				if (handItem.getType() == Material.BOW) {
					
					double bowPower = handItem.getEnchantmentLevel(Enchantment.ARROW_DAMAGE);
//					int bowInfinity = handItem.getEnchantmentLevel(Enchantment.ARROW_INFINITE);
					
					double bowDamagePrecentIncrease = 25 * bowPower; 
					bowDamagePrecentIncrease = bowDamagePrecentIncrease / 100;
					bowDamagePrecentIncrease = bowDamagePrecentIncrease + 1;
					
					double bowDamage;
					int ammo = 0;
					int ammoPerPack;
					int reloadTime;
					int fireTime;
					
					//Checking to make sure that the blaster lore is updated to the config
					if (handItem.getItemMeta().hasLore()) {
					
						List<String> lore2 = handItem.getItemMeta().getLore();
						ItemMeta meta2 = (ItemMeta) handItem.getItemMeta();
						
						String type = lore2.get(0).substring(10);
						
						if (!type.equals("new blaster")) {
							
							bowDamage = (Main.getPluginConfig().getDouble(type + ".damage") * bowDamagePrecentIncrease);
							ammo = 0;
							ammoPerPack = Main.getPluginConfig().getInt(type + ".ammo per pack");
							reloadTime = Main.getPluginConfig().getInt(type + ".reload time");
							fireTime = Main.getPluginConfig().getInt(type + ".fire time");
							
							if (!(Main.getPluginConfig().getRoot().getKeys(false).contains(type) &&
								lore2.get(1).equals("§6Ammo Per Pack: §f" + ammoPerPack) && 
								lore2.get(3).equals("§6Damage: §f" + bowDamage)  && 
								lore2.get(4).equals("§6Reload Time: §f" + reloadTime) && 
								lore2.get(5).equals("§6Fire Time: §f" + fireTime))) {
								
								lore2.clear();
								
								if (!Main.getPluginConfig().getRoot().getKeys(false).contains(type)) {
									
									type = "blaster";
									
								}
								
								lore2.add(ChatColor.GOLD + "Type: " + ChatColor.WHITE + type);
								lore2.add(ChatColor.GOLD + "Ammo Per Pack: " + ChatColor.WHITE + ammoPerPack);
								lore2.add(ChatColor.GOLD + "Ammo: " + ChatColor.WHITE + ammo);
								lore2.add(ChatColor.GOLD + "Damage: " + ChatColor.WHITE + bowDamage);
								lore2.add(ChatColor.GOLD + "Reload Time: " + ChatColor.WHITE + reloadTime);
								lore2.add(ChatColor.GOLD + "Fire Time: " + ChatColor.WHITE + fireTime);
								meta2.setLore(lore2);
								meta2.setDisplayName("Blaster " + ChatColor.GOLD +  "(" + ammo + "/" + ammoPerPack + ")");
								handItem.setItemMeta(meta2);
							
							
						}
							
					}
							
					List<String> bowLore = handItem.getItemMeta().getLore();
					ItemMeta bowMeta = (ItemMeta) handItem.getItemMeta();
					
					bowDamage = (Main.getPluginConfig().getDouble(type + ".damage") * bowDamagePrecentIncrease);
					ammo = 0;
					ammoPerPack = Main.getPluginConfig().getInt(type + ".ammo per pack");
					reloadTime = Main.getPluginConfig().getInt(type + ".reload time");
					fireTime = Main.getPluginConfig().getInt(type + ".fire time");
					List<String> special = Main.getPluginConfig().getStringList(type + ".special");
					
					if (!bowLore.get(0).substring(10).equals("new blaster")) {
						
						//Checking to see if the blaster has correct lore
						if (handItem.getItemMeta().hasLore() && (bowLore.get(1).substring(2, 15).equals("Ammo Per Pack") && bowLore.get(2).substring(2, 6).equals("Ammo") && bowLore.get(3).substring(2, 8).equals("Damage")  && bowLore.get(4).substring(2, 13).equals("Reload Time") && bowLore.get(5).substring(2, 11).equals("Fire Time"))) {
							
						} else {
							
							bowLore.clear();
							
							bowLore.add(ChatColor.GOLD + "Ammo Per Pack: " + ammoPerPack);
							bowLore.add(ChatColor.GOLD + "Ammo: " + ChatColor.WHITE + ammo);
							bowLore.add(ChatColor.GOLD + "Damage: " + ChatColor.WHITE + bowDamage);
							bowLore.add(ChatColor.GOLD + "Reload Time: " + ChatColor.WHITE + reloadTime);
							bowLore.add(ChatColor.GOLD + "Fire Time: " + ChatColor.WHITE + fireTime);
							bowMeta.setLore(bowLore);
							bowMeta.setDisplayName("Blaster " + ChatColor.GOLD +  "(" + ammo + "/" + ammoPerPack + ")");
							handItem.setItemMeta(bowMeta);
							
						}

						if (special.contains("automatic")) {
								
							if (player.hasMetadata("automatic")) {
									
								player.removeMetadata("automatic", Main.getPluginMain());
									
							} else {
									
								player.setMetadata("automatic", new FixedMetadataValue(Main.getPluginMain(), true));
									
							}
								
						} else {
								
							fireBlaster(player);
							
						}
						
					}
										
				}
							
			}
				
		} else if((eAction == Action.LEFT_CLICK_AIR && player.getItemInHand().getType() == Material.BOW && player.getItemInHand().getItemMeta().hasLore()) || (eAction == Action.LEFT_CLICK_BLOCK && player.getItemInHand().getType() == Material.BOW && player.getItemInHand().getItemMeta().hasLore())) {		
			
			if (!player.getItemInHand().getItemMeta().getLore().get(0).substring(10).equals("new blaster")) {
				
				if (player.hasMetadata("scoped")) {
					
					player.removeMetadata("scoped", Main.getPluginMain());
					player.removePotionEffect(PotionEffectType.SLOW);
					
				} else {
						
					player.setMetadata("scoped", new FixedMetadataValue(Main.getPluginMain(), Main.getPluginConfig().getInt(player.getItemInHand().getItemMeta().getLore().get(0).substring(10) + ".scope")));
					player.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, 1000000000, Main.getPluginConfig().getInt(player.getItemInHand().getItemMeta().getLore().get(0).substring(10) + ".scope")));
					
				}
				
			}
			
		}
	
	}
			
	@EventHandler
	public void onProjectileLuanch(ProjectileLaunchEvent event) {
		
		if(event.getEntity() instanceof Arrow) {
			
			final Arrow arrow = (Arrow) event.getEntity();
			
			if (arrow.getShooter() instanceof Player) {
				
				final Player player = (Player) arrow.getShooter();
				
				final ItemStack handItem = player.getItemInHand();
				
				if (handItem.getItemMeta().hasLore()) {
					
					List<String> bowLore = handItem.getItemMeta().getLore();
					
					if (bowLore.get(0).substring(2, 7).equals("Type:")) {
						
						BukkitScheduler scheduler = Bukkit.getServer().getScheduler();
						
						scheduler.scheduleSyncDelayedTask(Main.getPluginMain(), new Runnable() {
							
							@Override
							public void run() {
							
								if (arrow.hasMetadata("no_pickup")) {
									
									(new NoGravity()).run(arrow);
									
								} else {
									
									int bowInfinty = handItem.getEnchantmentLevel(Enchantment.ARROW_INFINITE);
									
									arrow.remove();
									
									player.getItemInHand().setDurability((short) 0);
									
									if (!(bowInfinty == 1 || player.getGameMode().equals(GameMode.CREATIVE))) {
										
										player.getInventory().addItem(new ItemStack(Material.ARROW));

										
									}
									
								}
								
							}
							
						}, 1);
									
					}
					
				}
				
			}
		
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
				
				boolean anyDamage = false;
				
				List<MetadataValue> values = arrow.getMetadata("damage");
				
				for (MetadataValue value : values) {
					
					if (value.getOwningPlugin() == Main.getPluginMain()) {
						
						damage = Double.parseDouble(value.value().toString());
						
						anyDamage = true;
						
					}
					
				}
				
				if (anyDamage) {
					
					event.setDamage(damage);
					
				}
				
				arrow.remove();
				
			}
			
		}
		
	}
	
	@EventHandler
	public void onInventoryClick(InventoryClickEvent event) {
		
		Player player = (Player) event.getWhoClicked();
		Inventory inventory = event.getClickedInventory();
		ItemStack item = event.getCurrentItem();
		
		if (inventory != null) {
			
			if (inventory.getName().equals(ChatColor.GOLD + "Blaster Selection")) {
				
				String  type = item.getItemMeta().getLore().get(0).substring(10);
					
				ItemStack blaster = Main.createNewBlaster(type, false, item.getItemMeta());
				
				if (Main.isEmpty(player.getInventory())) {
					
					player.getInventory().addItem(blaster);
					
				} else {
					
					player.getWorld().dropItem(player.getLocation(), blaster);
					
				}
				
				inventory.setContents(new ItemStack[1]);
				
				player.closeInventory();
				
			} else if (inventory.getName().equals(ChatColor.GOLD + "Blaster Recipie")) {
				
				event.setCancelled(true);
				
			}
			
		}
				
	}
	
	@EventHandler
	public void onInventoryClose(InventoryCloseEvent event) {
		
		final Player player = (Player) event.getPlayer();		
		final Inventory inventory = event.getInventory();
		
		if (inventory != null) {
			
			if (inventory.getName().equals(ChatColor.GOLD + "Blaster Selection") && inventory.getItem(0) != null) {
				
				BukkitScheduler scheduler = Bukkit.getServer().getScheduler();
				
				scheduler.scheduleSyncDelayedTask(Main.getPluginMain(), new Runnable() {
					
					@Override
					public void run() {
					
						if (player.isOnline()) {
							
							player.openInventory(inventory);
							
						}
						
					}
					
				}, 0);
				
			}
			
		}
		
	}
	
	@EventHandler
	public void onPlayerJoin (PlayerJoinEvent event) {
		
		Player player = event.getPlayer();

		player.removeMetadata("reload_timer", Main.getPluginMain());
		player.removeMetadata("fire_timer", Main.getPluginMain());
		
	}

}
