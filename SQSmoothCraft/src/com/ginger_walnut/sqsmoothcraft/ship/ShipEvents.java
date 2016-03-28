package com.ginger_walnut.sqsmoothcraft.ship;

import java.util.List;

import net.md_5.bungee.api.ChatColor;
import net.minecraft.server.v1_9_R1.EntityPlayer;
import net.minecraft.server.v1_9_R1.PacketPlayOutAttachEntity;
import net.minecraft.server.v1_9_R1.PacketPlayOutEntityDestroy;
import net.minecraft.server.v1_9_R1.PacketPlayOutNamedEntitySpawn;
import net.minecraft.server.v1_9_R1.PacketPlayOutPlayerInfo;
import net.minecraft.server.v1_9_R1.PacketPlayOutPlayerInfo.EnumPlayerInfoAction;
import net.minecraft.server.v1_9_R1.PlayerConnection;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.World;
import org.bukkit.craftbukkit.v1_9_R1.entity.CraftEntity;
import org.bukkit.craftbukkit.v1_9_R1.entity.CraftPlayer;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Fireball;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerInteractAtEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerPickupItemEvent;
import org.bukkit.event.player.PlayerToggleSneakEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.metadata.MetadataValue;
import org.bukkit.util.Vector;

import com.dibujaron.cardboardbox.Knapsack;
import com.ginger_walnut.sqsmoothcraft.SQSmoothCraft;

public class ShipEvents implements Listener {
	
	@EventHandler void onEntityDamage(EntityDamageEvent event) {
		
		if (event.getEntity() instanceof ArmorStand) {
			
			//System.out.print(event.getCause());
			
		}
		
	}
	
	@EventHandler
	public void onEntityDamageByEntity(EntityDamageByEntityEvent event) {
		
		if (event.getDamager() instanceof Projectile) {
			
			Projectile projectile = (Projectile) event.getDamager();

			if (projectile.hasMetadata("no_pickup")) {
				
				if (event.getEntity() instanceof ArmorStand) {
					
					ArmorStand stand = (ArmorStand) event.getEntity();
					
					ShipBlock shipBlock = ShipUtils.getShipBlockFromArmorStand(stand);
					
					if (shipBlock != null) {
						
						if (!shipBlock.invincible) {
							
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
							
							shipBlock.ship.damage(shipBlock, shipDamage, carryOver);
							
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
				
			}
			
		}

	}
	
	@EventHandler
	public void onPlayerClick(PlayerInteractEvent event) {
		
		Player player = event.getPlayer();
		Action eAction = event.getAction();
		
		if (eAction == Action.LEFT_CLICK_AIR || eAction == Action.LEFT_CLICK_BLOCK) {
			
			if (player.getItemInHand().getType().equals(Material.WATCH)) {
				
				if (SQSmoothCraft.shipMap.containsKey(player.getUniqueId())) {
				
					if (SQSmoothCraft.shipMap.get(player.getUniqueId()).getMainBlock().getShip().getCaptain() != null) {
						
						String name = player.getItemInHand().getItemMeta().getDisplayName();
						
						if (name.equals("Main Control Device") || name.equals("Accelerator") || name.equals("Decelerator")) {
							
							World world = player.getWorld();
							
							Ship ship = SQSmoothCraft.shipMap.get(player.getUniqueId());
							
							if (ship.fuel > 0.0f) {
							
								for (int i = 0; i < ship.getCannons().size(); i ++) {
									
									Location blockLocation = ship.getCannons().get(i).getLocation();
									Location location = blockLocation.toVector().add(player.getLocation().getDirection().multiply(4)).toLocation(player.getWorld(), player.getLocation().getYaw(), player.getLocation().getPitch());
									
									Arrow arrow = (Arrow) world.spawnEntity(location, EntityType.ARROW);
									
									Vector newVelocity = player.getLocation().getDirection();
									
									newVelocity.multiply(4);
									
									arrow.setVelocity(newVelocity);
									
									arrow.setMetadata("damage", new FixedMetadataValue(SQSmoothCraft.getPluginMain(), 10));
									arrow.setMetadata("no_pickup", new FixedMetadataValue(SQSmoothCraft.getPluginMain(), true));
									arrow.setMetadata("carry_over", new FixedMetadataValue(SQSmoothCraft.getPluginMain(), false));
									
									new ProjectileSmoother(arrow, newVelocity).runTaskTimer(SQSmoothCraft.getPluginMain(), 0, 1);
									
									player.getWorld().playSound(player.getLocation(), Sound.ENTITY_ARROW_SHOOT, 2, 1);
									
								}
								
							}
							
						} else if (name.equals("Missile Controler")) {
							
							if (player.hasMetadata("missle_cooldown")) {
								
								player.sendMessage(ChatColor.RED + "The missle launchers are on cooldown");
								
							} else {
								
								new CooldownHandler(player, "missle_cooldown", 100).runTaskTimer(SQSmoothCraft.getPluginMain(), 0, 1);
								
								World world = player.getWorld();
								
								Ship ship = SQSmoothCraft.shipMap.get(player.getUniqueId());
								
								if (ship.fuel > 0.0f) {
								
									for (int i = 0; i < ship.missleList.size(); i ++) {
										
										Location blockLocation = ship.missleList.get(i).getLocation();
										Location location = blockLocation.toVector().add(player.getLocation().getDirection().multiply(4)).toLocation(player.getWorld(), player.getLocation().getYaw(), player.getLocation().getPitch());
										
										Fireball fireball = (Fireball) world.spawnEntity(location, EntityType.FIREBALL);
										
										fireball.setYield(1f);
										
										Vector newVelocity = player.getLocation().getDirection();
										
										newVelocity.multiply(4);
										
										fireball.setVelocity(newVelocity);
										
										fireball.setMetadata("damage", new FixedMetadataValue(SQSmoothCraft.getPluginMain(), 200));
										fireball.setMetadata("no_pickup", new FixedMetadataValue(SQSmoothCraft.getPluginMain(), true));
										fireball.setMetadata("carry_over", new FixedMetadataValue(SQSmoothCraft.getPluginMain(), true));
										
										new ProjectileSmoother(fireball, newVelocity).runTaskTimer(SQSmoothCraft.getPluginMain(), 0, 1);
										
										player.getWorld().playSound(player.getLocation(), Sound.ENTITY_GHAST_SHOOT, 2, 1);
										
									}
									
								}
								
							}
							
						}
						
					}

				}
				
			}
			
		} else if (eAction == Action.RIGHT_CLICK_AIR || eAction == Action.RIGHT_CLICK_BLOCK) {
			
			if (player.getItemInHand().getType().equals(Material.WATCH)) {
				
				if (SQSmoothCraft.shipMap.containsKey(player.getUniqueId())) {
				
					Ship ship = SQSmoothCraft.shipMap.get(player.getUniqueId());
					
					ItemStack itemInHand = event.getPlayer().getItemInHand();
				
					if (itemInHand.getItemMeta().getDisplayName().equals("Accelerator")) {
					
						if (ship.fuel > 0.0f) {
						
							if (ship.getSpeed() == ship.getMaxSpeed()) {
							
							} else if (ship.getSpeed() > ship.getMaxSpeed()){
									
								ship.setSpeed(ship.getMaxSpeed());
								
							} else {
									
								ship.setSpeed(ship.getSpeed() + ship.getAcceleration());
									
							}
							
						}
					
					} else if (itemInHand.getItemMeta().getDisplayName().equals("Decelerator")) {
						
						if (ship.fuel > 0.0f) {
						
							if (ship.getSpeed() == 0.0f) {
						
							} else if (ship.getSpeed() < 0.0f) {
						
								ship.setSpeed(0.0f);
						
							} else {
						
								ship.setSpeed(ship.getSpeed() - ship.getAcceleration());
						
							}
							
							if (ship.getSpeed() < 0.0f) {
								
								ship.setSpeed(0.0f);
								
							}
							
						}
						
					} else if (itemInHand.getItemMeta().getDisplayName().equals("Direction Locker")) {
						
						if (ship.lockedDirection) {
							
							ship.lockedDirection = false;
							
						} else {
							
							ship.lockedDirection = true;
							
						}

					}
					
				}
				
			} else if (player.getItemInHand().getType().equals(Material.WOOD_DOOR)) {
				
				if (SQSmoothCraft.shipMap.containsKey(player.getUniqueId())) {
					
					Ship ship = SQSmoothCraft.shipMap.get(player.getUniqueId());
					
					ship.exit();
					
				}
				
			}
			
		}

	}
	
	@EventHandler
	public void onPlayerInteractAtEntity(PlayerInteractAtEntityEvent event) {
		
		if (event.getRightClicked() instanceof ArmorStand) {
			
			ShipBlock shipBlock = ShipUtils.getShipBlockFromArmorStand((ArmorStand) event.getRightClicked());
			
			if (shipBlock != null) {
				
				if (SQSmoothCraft.stoppedShipMap.contains(shipBlock.getShip())) {
					
					if (!shipBlock.getShip().getMainBlock().stand.isDead()) {
						
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
						
						boolean succesful = shipBlock.ship.blockify();
						
						if (!succesful) {
						
							for (ShipBlock block : shipBlock.ship.blockList) {
							
							block.getLocation().getWorld().dropItem(block.getLocation(), block.getArmorStand().getHelmet());
							
							block.getArmorStand().remove();
							
							}
							
						}
						
						event.setCancelled(true);
						
					}
					
				} else if (event.getPlayer().getItemInHand().getType().equals(Material.WATCH)) {
					
					if (SQSmoothCraft.shipMap.containsKey(event.getPlayer().getUniqueId())) {
					
						Ship ship = SQSmoothCraft.shipMap.get(event.getPlayer().getUniqueId());
						
						ItemStack itemInHand = event.getPlayer().getItemInHand();
					
						if (itemInHand.getItemMeta().getDisplayName().equals("Accelerator")) {
						
							if (ship.fuel > 0.0f) {
								
								if (ship.getSpeed() == ship.getMaxSpeed()) {
									
								} else if (ship.getSpeed() > ship.getMaxSpeed()){
											
									ship.setSpeed(ship.getMaxSpeed());
										
								} else {
											
									ship.setSpeed(ship.getSpeed() + ship.getAcceleration());
											
								}
								
							}

						} else if (itemInHand.getItemMeta().getDisplayName().equals("Decelerator")) {
							
							if (ship.fuel > 0.0f) {
							
								if (ship.getSpeed() == 0.0f) {
							
								} else if (ship.getSpeed() < 0.0f) {
							
									ship.setSpeed(0.0f);
							
								} else {
							
									ship.setSpeed(ship.getSpeed() - ship.getAcceleration());
							
								}
								
								if (ship.getSpeed() < 0.0f) {
									
									ship.setSpeed(0.0f);
									
								}
								
							}
							
						} 
						
					}
					
				} else if (event.getPlayer().getItemInHand().getType().equals(Material.WOOD_DOOR)) {
					
					Player player = event.getPlayer();
					
					if (SQSmoothCraft.shipMap.containsKey(player.getUniqueId())) {
						
						Ship ship = SQSmoothCraft.shipMap.get(player.getUniqueId());
						
						ship.exit();
						
					}
					
				} else if (event.getPlayer().getItemInHand().getType().equals(Material.COMPASS)) {
					
					if (SQSmoothCraft.shipMap.containsKey(event.getPlayer().getUniqueId())) {
						
						Ship ship = SQSmoothCraft.shipMap.get(event.getPlayer().getUniqueId());
						
						ItemStack itemInHand = event.getPlayer().getItemInHand();
						
						if (itemInHand.getItemMeta().getDisplayName().equals("Direction Locker")) {
							
							if (ship.lockedDirection) {
								
								ship.lockedDirection = false;
								
								event.getPlayer().sendMessage(ChatColor.GREEN + "The direction has been unlocked");
								
							} else {
								
								ship.lockedDirection = true;
								
								event.getPlayer().sendMessage(ChatColor.RED + "The direction has been locked");
								
							}

						}
						
					}
					
				}else {
				
					
					event.setCancelled(true);
					
				}
				
			}
			
		}
		
	}
	
	@EventHandler
	public void onPlayerToggleSneak(PlayerToggleSneakEvent event) {

		if (SQSmoothCraft.shipMap.containsKey(event.getPlayer().getUniqueId())) {
			
			if (SQSmoothCraft.shipMap.get(event.getPlayer().getUniqueId()).thirdPersonPlayer != null) {
			
				for (Player onlinePlayer : SQSmoothCraft.getPluginMain().getServer().getOnlinePlayers()) {
				
					PlayerConnection connection = ((CraftPlayer) onlinePlayer).getHandle().playerConnection;
				
					connection.sendPacket(new PacketPlayOutPlayerInfo(EnumPlayerInfoAction.REMOVE_PLAYER, (SQSmoothCraft.shipMap.get(event.getPlayer().getUniqueId()).thirdPersonPlayer)));
					connection.sendPacket(new PacketPlayOutEntityDestroy((SQSmoothCraft.shipMap.get(event.getPlayer().getUniqueId()).thirdPersonPlayer.getId())));
				
//				onlinePlayer.showPlayer(event.getPlayer());
				
				}
				
			}
			
			SQSmoothCraft.shipMap.get(event.getPlayer().getUniqueId()).thirdPersonPlayer = null;
			
			SQSmoothCraft.stoppedShipMap.add(SQSmoothCraft.shipMap.get(event.getPlayer().getUniqueId()));
			
			SQSmoothCraft.shipMap.remove(event.getPlayer().getUniqueId());
		
			event.getPlayer().getInventory().clear();
			event.getPlayer().getInventory().setArmorContents(null);
			
			SQSmoothCraft.knapsackMap.get(event.getPlayer().getUniqueId()).unpack(event.getPlayer());
			
		}
		
	}
	
	@EventHandler
	public void onPlayerPickupItem(PlayerPickupItemEvent event) {
		
		if(event.getItem().hasMetadata("no_pickup")) {
			
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
		
	}

}
