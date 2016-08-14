package com.ginger_walnut.sqsmoothcraft.listeners;

import java.util.List;
import java.util.ListIterator;

import net.md_5.bungee.api.ChatColor;
import net.minecraft.server.v1_10_R1.EntityPlayer;
import net.minecraft.server.v1_10_R1.PacketPlayOutAttachEntity;
import net.minecraft.server.v1_10_R1.PacketPlayOutNamedEntitySpawn;
import net.minecraft.server.v1_10_R1.PacketPlayOutPlayerInfo;
import net.minecraft.server.v1_10_R1.PacketPlayOutPlayerInfo.EnumPlayerInfoAction;
import net.minecraft.server.v1_10_R1.PlayerConnection;

import org.bukkit.Bukkit;
import org.bukkit.craftbukkit.v1_10_R1.entity.CraftEntity;
import org.bukkit.craftbukkit.v1_10_R1.entity.CraftPlayer;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.entity.Snowball;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerInteractAtEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerPickupItemEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.metadata.MetadataValue;

import com.dibujaron.cardboardbox.Knapsack;
import com.ginger_walnut.sqsmoothcraft.SQSmoothCraft;
import com.ginger_walnut.sqsmoothcraft.events.ShipExitEvent;
import com.ginger_walnut.sqsmoothcraft.objects.Ship;
import com.ginger_walnut.sqsmoothcraft.objects.ShipBlock;
import com.ginger_walnut.sqsmoothcraft.utils.ShipUtils;
import com.martinjonsson01.sqsmoothcraft.missile.MissileGUI;

public class ShipEvents implements Listener {
	
	@EventHandler
	public void onProjectileHit(ProjectileHitEvent event) {
		
		if (event.getEntity() instanceof Arrow) {
			
			Arrow arrow = (Arrow) event.getEntity();
			
			if (arrow.hasMetadata("explosive")) {
				
				arrow.getLocation().getWorld().createExplosion(arrow.getLocation(), (float) SQSmoothCraft.config.getDouble("weapons.cannon.explosion power"));
				
			}
			
		}
		
	}
	
	@EventHandler
	public void onEntityDamageByEntity(EntityDamageByEntityEvent event) {
		
		if (event.getDamager() instanceof Projectile) {
			
			Projectile projectile = (Projectile) event.getDamager();
			
			if (projectile.hasMetadata("no_pickup")) {
				
				if (event.getDamager() instanceof Arrow) {
					
					Arrow arrow = (Arrow) event.getDamager();
					
					if (arrow.hasMetadata("explosive")) {
						
						arrow.getLocation().getWorld().createExplosion(arrow.getLocation(), (float) SQSmoothCraft.config.getDouble("weapons.cannon.explosion power"));
						
					}
					
				}
				
				if (event.getEntity() instanceof ArmorStand) {
					
					ArmorStand stand = (ArmorStand) event.getEntity();
					
					ShipBlock shipBlock = ShipUtils.getShipBlockFromArmorStand(stand);
					
					if (shipBlock != null) {
						
						if (!shipBlock.invincible) {
							
							if(projectile.hasMetadata("type")){
								
								List<MetadataValue> mData = projectile.getMetadata("type");
								
								if(mData.get(0).asString().equals("emp")){
									//Makes the captain of the ship exit the ship
									ShipUtils.getShipBlockFromArmorStand(stand).getShip().exit(true);
								}
								
								
							}
							
							double shipDamage = 0;
							
							List<MetadataValue> shipShotValues = projectile.getMetadata("damage");
							
							for (MetadataValue value : shipShotValues) {
								
								shipDamage = Double.parseDouble(value.value().toString());
								
							}
							
							boolean carryOver = false;
							
							if (projectile.hasMetadata("carry_over")) {
								
								List<MetadataValue> shipShotCarry = projectile.getMetadata("carry_over");
								
								for (MetadataValue value : shipShotCarry) {
									
									carryOver = Boolean.parseBoolean(value.value().toString());
									
								}
								
							}
							
							if(projectile.hasMetadata("type")){
								List<MetadataValue> mData = projectile.getMetadata("type");
								if(mData.get(0).toString().equals("explosive")) {
									shipDamage = shipDamage + 100;
								}
							}
							
							shipBlock.ship.damage(shipBlock, shipDamage, carryOver, projectile.getLocation());
							
							projectile.remove();
							
						} else {
							
							projectile.teleport(projectile.getLocation().add(projectile.getLocation().getDirection().toLocation(projectile.getWorld())));
							
						}
						
					}
					
				} else {
					
					double damage = 0;
					
					boolean anyDamage = false;
					
					List<MetadataValue> values = projectile.getMetadata("damage");
					
					for (MetadataValue value : values) {
						
						if (value.getOwningPlugin() == SQSmoothCraft.getPluginMain()) {
							
							damage = Double.parseDouble(value.value().toString());
							
							anyDamage = true;
							
						}
						
					}
					
					if (anyDamage) {
						
						event.setDamage(damage);
						
					}
					
					projectile.remove();
					
				}
				
			} else {
				if (event.getEntity() instanceof ArmorStand) {
					
					ArmorStand stand = (ArmorStand) event.getEntity();
					
					if (!stand.isVisible()) {
						
						if (projectile instanceof Snowball) {
							
							stand.remove();
							stand = null;
							
						}
						
					}
				
				}
				
			}
			
		}
		
	}
	
	@EventHandler
	public void onPlayerClick(PlayerInteractEvent event) {
		
		Player player = event.getPlayer();
		Action eAction = event.getAction();
		
		if (eAction == Action.LEFT_CLICK_AIR || eAction == Action.LEFT_CLICK_BLOCK) {
			
			if (SQSmoothCraft.shipMap.containsKey(player.getUniqueId())) {
				
				Ship ship = SQSmoothCraft.shipMap.get(player.getUniqueId());
				
				ship.leftClickControls();
				
			}
			
		} else if (eAction == Action.RIGHT_CLICK_AIR || eAction == Action.RIGHT_CLICK_BLOCK) {
			
			if (SQSmoothCraft.shipMap.containsKey(player.getUniqueId())) {
				
				Ship ship = SQSmoothCraft.shipMap.get(player.getUniqueId());
				
				ship.rightClickControls(player);
				
			}
			
		}
		
	}
	
	@EventHandler
	public void onPlayerInteractAtEntity(PlayerInteractAtEntityEvent event) {
		
		if (event.getRightClicked() instanceof ArmorStand) {
			
			ShipBlock shipBlock = ShipUtils.getShipBlockFromArmorStand((ArmorStand) event.getRightClicked());
			
			if (shipBlock != null) {
				
				if (SQSmoothCraft.stoppedShipMap.contains(shipBlock.getShip())) {
					
					if (shipBlock.getShip().getMainBlock().stand != null) {
						
						event.setCancelled(true);
						
						shipBlock.ship.captain = event.getPlayer();
						
						SQSmoothCraft.shipMap.put(event.getPlayer().getUniqueId(), shipBlock.ship);
						SQSmoothCraft.stoppedShipMap.remove(shipBlock.ship);
						
						shipBlock.ship.speedBar.addPlayer(event.getPlayer());
						shipBlock.ship.speedBar.setVisible(true);
						
						shipBlock.ship.fuelBar.addPlayer(event.getPlayer());
						shipBlock.ship.fuelBar.setVisible(true);
						
						Knapsack knapsack = new Knapsack(event.getPlayer());
						
						SQSmoothCraft.knapsackMap.put(event.getPlayer().getUniqueId(), knapsack);
						
						ShipUtils.setPlayerShipInventory(event.getPlayer());
						
					} else {
						
						event.getPlayer().sendMessage(ChatColor.RED + "The ship's main block is missing, decompiling");
						
						boolean succesful = shipBlock.ship.blockify(true);
						
						if (!succesful) {
							
							for (ShipBlock block : shipBlock.ship.blockList) {
								
								block.getLocation().getWorld().dropItem(block.getLocation(), block.getArmorStand().getHelmet());
								
								block.getArmorStand().remove();
								
							}
							
						}
						
						event.setCancelled(true);
						
					}
					
				} else if (SQSmoothCraft.shipMap.containsValue(shipBlock.getShip())) {
					
					Player player = event.getPlayer();
					
					if (SQSmoothCraft.shipMap.containsKey(player.getUniqueId())) {
						
						Ship ship = SQSmoothCraft.shipMap.get(player.getUniqueId());
						
						if (ship.rightClickControls(player)) {
							
							event.setCancelled(true);
							
						}
						
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
		
		if (SQSmoothCraft.shipMap.containsKey(event.getPlayer().getUniqueId())) {
			
			event.setCancelled(true);
			
		}
		
	}
	
	@EventHandler
	public void onBlockPlace(BlockPlaceEvent event) {
		
		if (SQSmoothCraft.shipMap.containsKey(event.getPlayer().getUniqueId())) {
			
			event.setCancelled(true);
			
		}
		
	}
	
	@EventHandler
	public void onPlayerJoin(PlayerJoinEvent event) {
		
		PlayerConnection connection = ((CraftPlayer) event.getPlayer()).getHandle().playerConnection;
		
		List<EntityPlayer> npcs = ShipUtils.getAllShipNpcs();
		
		for (EntityPlayer npc : npcs) {
			
			connection.sendPacket(new PacketPlayOutPlayerInfo(EnumPlayerInfoAction.ADD_PLAYER, npc));
			connection.sendPacket(new PacketPlayOutNamedEntitySpawn(npc));
			
			connection.sendPacket(new PacketPlayOutAttachEntity(npc, ((CraftEntity) ShipUtils.getShipFromNpc(npc).getMainBlock().getArmorStand()).getHandle()));
			
			//			event.getPlayer().hidePlayer(ShipUtils.getShipFromNpc(npc).getCaptain());
			
		}
		
	}
	
	@EventHandler
	public void onPlayerDropItem(PlayerDropItemEvent event) {
		
		if (SQSmoothCraft.shipMap.containsKey(event.getPlayer().getUniqueId())) {
			
			event.setCancelled(true);
			
			ShipUtils.setPlayerShipInventory(event.getPlayer());
			
		}
		
	}
	
	@EventHandler
	public void onInventoryClick(InventoryClickEvent event) {
		
		if (SQSmoothCraft.shipMap.containsKey(event.getWhoClicked().getUniqueId())) {
			
			event.setCancelled(true);
			
		}
		
		ItemStack item = null;
		
		item = event.getCurrentItem();
		
		if (item == null) {
			
			item = event.getCursor();
			
		}
		
		if (item != null) {
			
			
			if (SQSmoothCraft.guiNames.contains(event.getInventory().getName())) {
				
				if(event.getCurrentItem() == null) return;
				
				if (event.getCurrentItem().hasItemMeta()) {
					
					if (event.getCurrentItem().hasItemMeta()) {
						
						if (event.getCurrentItem().getItemMeta().hasLore()) {
							
							if (event.getCurrentItem().getItemMeta().getLore().contains(ChatColor.RED + "" + ChatColor.MAGIC + "Contraband")) {
								
								event.setCancelled(true);
								
								if (SQSmoothCraft.currentGui.containsKey(event.getWhoClicked())) {
									

									SQSmoothCraft.currentGui.get(event.getWhoClicked()).clicked(event.getCurrentItem().getItemMeta().getDisplayName(), event.getSlot());

									
								}
								
							}
							
						}
						
					}
					
				}
				
			}
			
		}
		
	}
	
	@EventHandler
	public void onInventoryClose(final InventoryCloseEvent e) {
		
		if (e.getInventory().getName() == null)
			return;
			
		if (e.getInventory().getName().equals("Missile Recipe")) {
			
			if (e.getInventory().getItem(0) == null)
				return;
				
			if (e.getInventory().getItem(0).getItemMeta().getDisplayName().equalsIgnoreCase(ChatColor.GOLD + "Heat Seeking Missile") ||
					e.getInventory().getItem(0).getItemMeta().getDisplayName().equalsIgnoreCase(ChatColor.GOLD + "Heat Seeking EMP Missile") ||
					e.getInventory().getItem(0).getItemMeta().getDisplayName().equalsIgnoreCase(ChatColor.GOLD + "Heat Seeking Explosive Missile")) {
					
				Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(SQSmoothCraft.getPluginMain(), new Runnable() {
					
					@Override
					public void run() {
						
						Player p = (Player) e.getPlayer();
						MissileGUI gui = new MissileGUI(p);
						e.getInventory().clear();
						gui.open();
						
					}
					
				}, 1);
				
			}
		}
		
	}
	
	@EventHandler
	public void onPlayerDeath(PlayerDeathEvent e){
		
		if (SQSmoothCraft.shipMap.containsKey(e.getEntity().getUniqueId())) {
			
			Ship ship = SQSmoothCraft.shipMap.get(e.getEntity().getUniqueId());
			ListIterator<ItemStack> litr = e.getDrops().listIterator();
			
			while(litr.hasNext()) {
				
				ItemStack item = litr.next();
				
				if(SQSmoothCraft.controlItems.contains(item)) {
					
					litr.remove();
					
				}
				
			}
			
			ship.exit(true);
			
			for (ItemStack item : e.getEntity().getInventory().getContents()) {
				
				if (item != null) {
					
					e.getEntity().getLocation().getWorld().dropItemNaturally(e.getEntity().getLocation(), item);
					
				}
				
			}
			
		}
		
	}
	
	@EventHandler
	public void onPlayerQuit(PlayerQuitEvent e) {
		
		if (SQSmoothCraft.shipMap.containsKey(e.getPlayer().getUniqueId())){
			
			Ship ship = SQSmoothCraft.shipMap.get(e.getPlayer().getUniqueId());
			ship.exit(true);
			ship.setSpeed(0);
			ship.blockify(true);
			
		}
		
	}
	
	@EventHandler
	public void onShipExit(ShipExitEvent event) {
		
		/*if (event.player.getBukkitEntity() instanceof Player) {
			
			Player player = (Player) event.player.getBukkitEntity();
			
			if (SQSmoothCraft.shipMap.containsKey(player.getUniqueId())) {
				event.setCancelled(true);

			}
			
		}*/
		
	}
	
}
