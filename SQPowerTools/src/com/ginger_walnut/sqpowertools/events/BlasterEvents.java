package com.ginger_walnut.sqpowertools.events;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Color;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Fireball;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.entity.SpectralArrow;
import org.bukkit.entity.TippedArrow;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityShootBowEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerPickupItemEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.PotionMeta;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.metadata.MetadataValue;
import org.bukkit.potion.PotionData;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.inventivetalent.particle.ParticleEffect;

import com.ginger_walnut.sqpowertools.SQPowerTools;
import com.ginger_walnut.sqpowertools.enums.AmmoType;
import com.ginger_walnut.sqpowertools.enums.ProjectileType;
import com.ginger_walnut.sqpowertools.objects.BlasterStats;
import com.ginger_walnut.sqpowertools.objects.Effect;
import com.ginger_walnut.sqpowertools.objects.Modifier;
import com.ginger_walnut.sqpowertools.objects.PowerTool;
import com.ginger_walnut.sqpowertools.objects.PowerToolType;
import com.ginger_walnut.sqpowertools.tasks.BlasterTask;
import com.ginger_walnut.sqpowertools.tasks.CooldownTask;
import com.ginger_walnut.sqpowertools.utils.EffectUtils;
import com.whirlwindgames.dibujaron.sqempire.Empire;
import com.whirlwindgames.dibujaron.sqempire.database.object.EmpirePlayer;

public class BlasterEvents implements Listener {

	@EventHandler
	public static void onPlayerInteract(PlayerInteractEvent event) {
		
		if (event.getAction().equals(Action.RIGHT_CLICK_AIR) || event.getAction().equals(Action.RIGHT_CLICK_BLOCK)) {
				
			Player player = event.getPlayer();
			
			ItemStack handItem = null;
			boolean main = true;
			
			if (player.getInventory().getItemInMainHand() != null) {
				
				ItemStack item = player.getInventory().getItemInMainHand();			
				
				if (item.hasItemMeta()) {
					
					if (item.getItemMeta().hasLore()) {
						
						List<String> lore = item.getItemMeta().getLore();
							
						if (lore.contains(ChatColor.DARK_PURPLE + "Power Tool")) {
						
							handItem = item;
							
						}
						
					}
					
				}
				
			}
			
			if (handItem == null) {
				
				if (player.getInventory().getItemInOffHand() != null) {
					
					ItemStack item = player.getInventory().getItemInOffHand();			
					
					if (item.hasItemMeta()) {
						
						if (item.getItemMeta().hasLore()) {
							
							List<String> lore = item.getItemMeta().getLore();
								
							if (lore.contains(ChatColor.DARK_PURPLE + "Power Tool")) {
							
								handItem = item;
								main = false;
								
							}
							
						}
						
					}
					
				}
				
			}
			
			if (handItem != null) {
				
				PowerToolType type = SQPowerTools.getType(handItem);
				
				if (type.blasterStats != null) {
					
					handItem = SQPowerTools.fixPowerTool(handItem);
					
					event.setCancelled(true);
					
					boolean onCooldown = false;
					
					if (player.hasMetadata("blaster_cooldown" + "_" + type.name.toLowerCase())) {
						
						if (player.getMetadata("blaster_cooldown" + "_" + type.name.toLowerCase()).get(0).asInt() > 0) {
							
							onCooldown = true;
							
						}
						
					}

					if (!onCooldown) {

						int energy = SQPowerTools.getEnergy(handItem);
						int energyPerUse = (new PowerTool(handItem)).getEnergyPerUse();
							
						if (energy == 0) {
								
							event.setCancelled(true);
								
							player.sendMessage(ChatColor.RED + "Your Power Tool is out of energy");
								
						} else {
								
							if (energy <= energyPerUse) {
									
								PowerTool powerTool = new PowerTool(handItem);
								powerTool.setEnergy(0);
									
								handItem = powerTool.getItem();
									
								player.sendMessage(ChatColor.RED + "Your Power Tool has run out of energy");
									
							} else {

								PowerTool powerTool = new PowerTool(handItem);
								BlasterStats blasterStats = powerTool.getTotalBlasterStats();
								
								if (blasterStats.ammo == 0) {
									
									if (blasterStats.projectileType.equals(ProjectileType.ARROW)) {
										
										powerTool.setEnergy(energy - energyPerUse);	
										handItem = powerTool.getItem();
										
										player.setMetadata("blaster_cooldown" + "_" + type.name.toLowerCase(), new FixedMetadataValue(SQPowerTools.getPluginMain(), blasterStats.cooldown));
										
										player.getWorld().playSound(player.getLocation(), Sound.ENTITY_ARROW_SHOOT, 1, 1);
										
										Arrow arrow = player.launchProjectile(Arrow.class);
										
										arrow.setMetadata("damage", new FixedMetadataValue(SQPowerTools.getPluginMain(), blasterStats.damage));
										arrow.setMetadata("no_pickup", new FixedMetadataValue(SQPowerTools.getPluginMain(), true));
										arrow.setShooter(player);
										
										BlasterTask.arrows.put(arrow, 200);
										BlasterTask.velocities.put(arrow, arrow.getVelocity());
										
									} else if (blasterStats.projectileType.equals(ProjectileType.FIREBALL)) {
										
										powerTool.setEnergy(energy - energyPerUse);	
										handItem = powerTool.getItem();
										
										player.setMetadata("blaster_cooldown" + "_" + type.name.toLowerCase(), new FixedMetadataValue(SQPowerTools.getPluginMain(), blasterStats.cooldown));
										
										Fireball fireball = player.launchProjectile(Fireball.class);
										
										player.getWorld().playSound(player.getLocation(), Sound.ENTITY_BLAZE_SHOOT, 1, 1);
										
										fireball.setMetadata("damage", new FixedMetadataValue(SQPowerTools.getPluginMain(), blasterStats.damage));
										fireball.setMetadata("no_pickup", new FixedMetadataValue(SQPowerTools.getPluginMain(), true));
										fireball.setShooter(player);
										
										fireball.setYield(blasterStats.explosionSize);
										
									} else if (blasterStats.projectileType.equals(ProjectileType.BEAM)) {
										
										double pitch = player.getLocation().getPitch();
										
										double yaw = player.getLocation().getYaw();
										
										double x = Math.sin(Math.toRadians(yaw)) * -.25 * Math.cos(Math.toRadians(pitch));
										double y = Math.sin(Math.toRadians(pitch)) * -.25;
										double z = Math.cos(Math.toRadians(yaw)) * .25 * Math.cos(Math.toRadians(pitch));
										
										Location startLocation = player.getLocation().add(player.getLocation().getDirection());
										startLocation.add(0, 1.5, 0);
										
										double currentX = startLocation.getX();
										double currentY = startLocation.getY();
										double currentZ = startLocation.getZ();
		
										player.getWorld().playSound(player.getLocation(), Sound.ENTITY_ARROW_SHOOT, 2.0F, 1.0F);
										
										for (int i = 0; i < 400; i ++) {
											
											final Location location = new Location(player.getWorld(), currentX, currentY, currentZ);
											
											List<Entity> entites = new ArrayList<Entity>();
											entites.addAll(location.getWorld().getNearbyEntities(location, .25, 1, .25));
											
											for (Entity entity : location.getWorld().getNearbyEntities(location, .25, 1, .25)) {
													
												if (entity instanceof LivingEntity) {
														
													if (entity != player) {
														
														i = 400;
														
														((LivingEntity) entity).damage(blasterStats.damage, player);
														
														List<PotionEffect> selfEffects = new ArrayList<PotionEffect>();
														List<PotionEffect> otherEffects = new ArrayList<PotionEffect>();
														
														HashMap<Modifier, Integer> modifiers = SQPowerTools.getModifiers(handItem);
														
														for (Modifier modifier : type.modifiers) {
															
															if (modifiers.containsKey(modifier)) {
																
																for (Effect effect : modifier.effects) {
																	
																	if (effect != null) {
																		
																		if (effect.effectCase == 1) {
																			
																			selfEffects.add(new PotionEffect(EffectUtils.getEffectFromId(effect.effect), effect.duration * 20, effect.level * modifiers.get(modifier)));
																			
																		} else if (effect.effectCase == 2) {
																			
																			otherEffects.add(new PotionEffect(EffectUtils.getEffectFromId(effect.effect), effect.duration * 20, effect.level * modifiers.get(modifier)));
																			
																		}
																		
																	}
																	
																}
																
															}
															
														}
														
														for (Effect effect : type.effects) {
															
															if (effect != null) {
																
																if (effect.effectCase == 1) {
																	
																	selfEffects.add(new PotionEffect(EffectUtils.getEffectFromId(effect.effect), effect.duration * 20, effect.level));
																	
																} else if (effect.effectCase == 2) {
																	
																	otherEffects.add(new PotionEffect(EffectUtils.getEffectFromId(effect.effect), effect.duration * 20, effect.level));
																	
																}
																
															}
															
														}
														
														for (PotionEffect effect : selfEffects) {
															
															if (player.hasPotionEffect(effect.getType())) {
																
																player.removePotionEffect(effect.getType());
																
															}
															
														}
														
														player.addPotionEffects(selfEffects);
														
														LivingEntity enemy = (LivingEntity) entity;
														
														for (PotionEffect effect : otherEffects) {
															
															if (!EffectUtils.isBadEffect(effect.getType().getId())) {
																
																if (enemy.hasPotionEffect(effect.getType())) {
																	
																	enemy.removePotionEffect(effect.getType());
																	
																}
																
																enemy.addPotionEffect(effect);
																
															}
															
														}
														
														location.getWorld().playSound(location, Sound.ENTITY_ARROW_HIT, 2.0F, 1.0F);
														
													}
	
												}
													
											}
												
											if (location.getBlock().getType().equals(Material.AIR)) {
												
												EmpirePlayer ep = EmpirePlayer.getOnlinePlayer(player);
												
												if (ep.getEmpire().equals(Empire.ARATOR)) {
													
													ParticleEffect.REDSTONE.sendColor(Bukkit.getOnlinePlayers(),location.getX(), location.getY(),location.getZ(), Color.BLUE);
													
												} else if (ep.getEmpire().equals(Empire.REQUIEM)) {
													
													ParticleEffect.REDSTONE.sendColor(Bukkit.getOnlinePlayers(),location.getX(), location.getY(),location.getZ(), Color.RED);
													
												} else if (ep.getEmpire().equals(Empire.YAVARI)) {
													
													ParticleEffect.REDSTONE.sendColor(Bukkit.getOnlinePlayers(),location.getX(), location.getY(),location.getZ(), Color.PURPLE);
													
												} else {
													
													ParticleEffect.REDSTONE.sendColor(Bukkit.getOnlinePlayers(),location.getX(), location.getY(),location.getZ(), Color.WHITE);
													
												}
						
												currentX = currentX + x;
												currentY = currentY + y;
												currentZ = currentZ + z;
												
											} else {
												
												i = 400;
		
											}
											
										}
										
									} else if (blasterStats.projectileType.equals(ProjectileType.SHOTGUN)) {
										
										player.getWorld().playSound(player.getLocation(), Sound.ENTITY_ARROW_SHOOT, 1, 1);
										
										Random random = new Random();
										
										HashMap<Entity, Integer> damaged = new HashMap<Entity, Integer>();
										
										for (int i = 0; i < blasterStats.shotCount; i ++) {
											
											double pitch = player.getLocation().getPitch() + (random.nextDouble() * 10.0) - 5.0;
											double yaw = player.getLocation().getYaw() + (random.nextDouble() * 10.0) - 5.0;
											
											double x = Math.sin(Math.toRadians(yaw)) * Math.cos(Math.toRadians(pitch)) * -1;
											double y = Math.sin(Math.toRadians(pitch)) * -1;
											double z = Math.cos(Math.toRadians(yaw)) * Math.cos(Math.toRadians(pitch));
											
											Location startLocation = player.getLocation().add(player.getLocation().getDirection());
											startLocation.add(0, 1.5, 0);
											
											double currentX = startLocation.getX();
											double currentY = startLocation.getY();
											double currentZ = startLocation.getZ();
											
											for (int j = 0; j < 100; j ++) {
												
												final Location location = new Location(player.getWorld(), currentX, currentY, currentZ);
												
												List<Entity> entites = new ArrayList<Entity>();
												entites.addAll(location.getWorld().getNearbyEntities(location, .25, 1, .25));
												
												for (Entity entity : location.getWorld().getNearbyEntities(location, .25, 1, .25)) {
														
													if (entity instanceof LivingEntity) {
															
														if (entity != player) {
															
															j = 100;
															
															if (damaged.containsKey(entity)) {
																
																damaged.replace(entity, damaged.get(entity) + 1);
																
															} else {
																
																damaged.put(entity, 1);
																
															}
															
														}
		
													}
														
												}
													
												if (location.getBlock().getType().equals(Material.AIR)) {
													
													EmpirePlayer ep = EmpirePlayer.getOnlinePlayer(player);
													
													if (ep.getEmpire().equals(Empire.ARATOR)) {
														
														ParticleEffect.REDSTONE.sendColor(Bukkit.getOnlinePlayers(),location.getX(), location.getY(),location.getZ(), Color.BLUE);
														
													} else if (ep.getEmpire().equals(Empire.REQUIEM)) {
														
														ParticleEffect.REDSTONE.sendColor(Bukkit.getOnlinePlayers(),location.getX(), location.getY(),location.getZ(), Color.RED);
														
													} else if (ep.getEmpire().equals(Empire.YAVARI)) {
														
														ParticleEffect.REDSTONE.sendColor(Bukkit.getOnlinePlayers(),location.getX(), location.getY(),location.getZ(), Color.PURPLE);
														
													} else {
														
														ParticleEffect.REDSTONE.sendColor(Bukkit.getOnlinePlayers(),location.getX(), location.getY(),location.getZ(), Color.WHITE);
														
													}
							
													currentX = currentX + x;
													currentY = currentY + y;
													currentZ = currentZ + z;
													
												} else {
													
													j = 100;
			
												}
												
											}
											
										}
										
										for (Entity entity : damaged.keySet()) {
											
											((LivingEntity) entity).damage(blasterStats.damage * damaged.get(entity), player);
											
											List<PotionEffect> selfEffects = new ArrayList<PotionEffect>();
											List<PotionEffect> otherEffects = new ArrayList<PotionEffect>();
											
											HashMap<Modifier, Integer> modifiers = SQPowerTools.getModifiers(handItem);
											
											for (Modifier modifier : type.modifiers) {
												
												if (modifiers.containsKey(modifier)) {
													
													for (Effect effect : modifier.effects) {
														
														if (effect != null) {
															
															if (effect.effectCase == 1) {
																
																selfEffects.add(new PotionEffect(EffectUtils.getEffectFromId(effect.effect), effect.duration * 20, effect.level * modifiers.get(modifier)));
																
															} else if (effect.effectCase == 2) {
																
																otherEffects.add(new PotionEffect(EffectUtils.getEffectFromId(effect.effect), effect.duration * 20 * damaged.get(entity), effect.level * modifiers.get(modifier)));
																
															}
															
														}
														
													}
													
												}
												
											}
											
											for (Effect effect : type.effects) {
												
												if (effect != null) {
													
													if (effect.effectCase == 1) {
														
														selfEffects.add(new PotionEffect(EffectUtils.getEffectFromId(effect.effect), effect.duration * 20, effect.level));
														
													} else if (effect.effectCase == 2) {
														
														otherEffects.add(new PotionEffect(EffectUtils.getEffectFromId(effect.effect), effect.duration * 20 * damaged.get(entity), effect.level));
														
													}
													
												}
												
											}
											
											for (PotionEffect effect : selfEffects) {
												
												if (player.hasPotionEffect(effect.getType())) {
													
													player.removePotionEffect(effect.getType());
													
												}
												
											}
											
											player.addPotionEffects(selfEffects);
											
											LivingEntity enemy = (LivingEntity) entity;
											
											for (PotionEffect effect : otherEffects) {
												
												if (!EffectUtils.isBadEffect(effect.getType().getId())) {
													
													if (enemy.hasPotionEffect(effect.getType())) {
														
														enemy.removePotionEffect(effect.getType());
														
													}
													
													enemy.addPotionEffect(effect);
													
												}
												
											}
											
											entity.getWorld().playSound(entity.getLocation(), Sound.ENTITY_ARROW_HIT, 2.0F, 1.0F);
											
										}
										
									}

								} else {
									
									if (blasterStats.ammo == 0) {
										
										powerTool.setEnergy(energy - energyPerUse);	
										handItem = powerTool.getItem();

										player.setMetadata("blaster_cooldown" + "_" + type.name.toLowerCase(), new FixedMetadataValue(SQPowerTools.getPluginMain(), blasterStats.cooldown));
										
										player.getWorld().playSound(player.getLocation(), Sound.ENTITY_ARROW_SHOOT, 1, 1);
										
										Arrow arrow = player.launchProjectile(Arrow.class);
										
										arrow.setMetadata("damage", new FixedMetadataValue(SQPowerTools.getPluginMain(), blasterStats.damage));
										arrow.setMetadata("no_pickup", new FixedMetadataValue(SQPowerTools.getPluginMain(), true));
										arrow.setShooter(player);
										
										BlasterTask.arrows.put(arrow, 200);
										BlasterTask.velocities.put(arrow, arrow.getVelocity());
										
									} else {
										
										if (powerTool.getAmmo() == 0) {
											
											if (player.hasMetadata("blaster_effect_" + type.name.toLowerCase())) {
												
												player.removeMetadata("blaster_effect_" + type.name.toLowerCase(), SQPowerTools.getPluginMain());
												
											}

											if (blasterStats.ammoType == AmmoType.ARROWS) {
												
												if (!player.getGameMode().equals(GameMode.CREATIVE)) {
												
													boolean contains = false;
													
													int last = 0;
													
													for (int i = 0; i < player.getInventory().getContents().length; i ++) {
														
														ItemStack itemStack = player.getInventory().getContents()[i];
														
														if (itemStack != null) {
															
															if (itemStack.getType().equals(Material.ARROW)) {
																
																contains = true;
																last = i;
																
															}
															
														}
														
													}
													
													if (contains) {
														
														ItemStack itemStack = player.getInventory().getItem(last);
														itemStack.setAmount(itemStack.getAmount() - 1);
														
														player.getInventory().setItem(last, itemStack);
														
													} else {
														
														player.sendMessage(ChatColor.RED + "You are out of arrows!");
														
														return;
														
													}
													
												}
												
											} else if (blasterStats.ammoType == AmmoType.POTION_ARROWS) {
												
												boolean contains = false;
												
												int last = 0;
												PotionData data = null;
												boolean spectral = false;
												
												for (int i = 0; i < player.getInventory().getContents().length; i ++) {
													
													ItemStack itemStack = player.getInventory().getContents()[i];
													
													if (itemStack != null) {
														
														if (itemStack.getType().equals(Material.TIPPED_ARROW)) {
															
															contains = true;
															last = i;
															data = ((PotionMeta)itemStack.getItemMeta()).getBasePotionData();
															spectral = false;
															
														} else if (itemStack.getType().equals(Material.SPECTRAL_ARROW)) {
															
															contains = true;
															last = i;
															spectral = true;
															
														}
														
													}
													
												}
												
												if (contains) {
													
													if (player.getGameMode() != GameMode.CREATIVE) {
														
														ItemStack itemStack = player.getInventory().getItem(last);
														itemStack.setAmount(itemStack.getAmount() - 1);
														
														player.getInventory().setItem(last, itemStack);
														
													}

													if (spectral) {
														
														player.setMetadata("blaster_effect_" + type.name.toLowerCase(), new FixedMetadataValue(SQPowerTools.getPluginMain(), 1));
														
													} else {
														
														player.setMetadata("blaster_effect_" + type.name.toLowerCase(), new FixedMetadataValue(SQPowerTools.getPluginMain(), data));
														
													}
													
												} else {
													
													player.sendMessage(ChatColor.RED + "You are out of arrows!");
													
													return;
													
												}
												
											} else if (blasterStats.ammoType == AmmoType.ALL_ARROWS) {
												
												boolean contains = false;
												
												int last = 0;
												PotionData data = null;
												boolean spectral = false;
												
												for (int i = 0; i < player.getInventory().getContents().length; i ++) {
													
													ItemStack itemStack = player.getInventory().getContents()[i];
													
													if (itemStack != null) {
														
														if (itemStack.getType().equals(Material.TIPPED_ARROW)) {
															
															contains = true;
															last = i;
															data = ((PotionMeta)itemStack.getItemMeta()).getBasePotionData();
															spectral = false;
															
														} else if (itemStack.getType().equals(Material.SPECTRAL_ARROW)) {
															
															contains = true;
															last = i;
															spectral = true;
															
														} else if (itemStack.getType().equals(Material.ARROW)) {
															
															contains = true;
															last = i;
															data = null;
															spectral = false;
															
														}
														
													}
													
												}
												
												if (contains) {
													
													if (player.getGameMode() != GameMode.CREATIVE) {
														
														ItemStack itemStack = player.getInventory().getItem(last);
														itemStack.setAmount(itemStack.getAmount() - 1);
														
														player.getInventory().setItem(last, itemStack);
														
													}
													
													if (spectral) {
														
														player.setMetadata("blaster_effect_" + type.name.toLowerCase(), new FixedMetadataValue(SQPowerTools.getPluginMain(), 1));
														
													} else if (data != null) {
														
														player.setMetadata("blaster_effect_" + type.name.toLowerCase(), new FixedMetadataValue(SQPowerTools.getPluginMain(), data));
														
													}
													
												} else {
													
													player.sendMessage(ChatColor.RED + "You are out of arrows!");
													
													return;
													
												}
												
											} else if (blasterStats.ammoType.equals(AmmoType.FIRE_CHARGES)) {
												
												if (!player.getGameMode().equals(GameMode.CREATIVE)) {
													
													boolean contains = false;
													
													int last = 0;
													
													for (int i = 0; i < player.getInventory().getContents().length; i ++) {
														
														ItemStack itemStack = player.getInventory().getContents()[i];
														
														if (itemStack != null) {
															
															if (itemStack.getType().equals(Material.FIREBALL)) {
																
																contains = true;
																last = i;
																
															}
															
														}
														
													}
													
													if (contains) {
														
														ItemStack itemStack = player.getInventory().getItem(last);
														itemStack.setAmount(itemStack.getAmount() - 1);
														
														player.getInventory().setItem(last, itemStack);
														
													} else {
														
														player.sendMessage(ChatColor.RED + "You are out of fire charges!");
														
														return;
														
													}
													
												}
												
											}
											
											powerTool.setEnergy(energy - energyPerUse);	
											powerTool.setDisplayName(type.name + " - " + blasterStats.ammo + "/" + blasterStats.ammo);
											handItem = powerTool.getItem();
											
											player.setMetadata("blaster_cooldown" + "_" + type.name.toLowerCase(), new FixedMetadataValue(SQPowerTools.getPluginMain(), blasterStats.reload));

											player.sendMessage(ChatColor.GREEN + "Reloading...");
											CooldownTask.reloading.add(player.getName());
											
										} else {
											
											int ammo = powerTool.getAmmo() - 1;
											
											powerTool.setDisplayName(type.name + " - " + ammo + "/" + blasterStats.ammo);
											handItem = powerTool.getItem();
											
											player.setMetadata("blaster_cooldown" + "_" + type.name.toLowerCase(), new FixedMetadataValue(SQPowerTools.getPluginMain(), blasterStats.cooldown));
											
											if (blasterStats.projectileType.equals(ProjectileType.ARROW)) {
												
												player.getWorld().playSound(player.getLocation(), Sound.ENTITY_ARROW_SHOOT, 1, 1);
												
												if (player.hasMetadata("blaster_effect_" + type.name.toLowerCase())) {
													
													if (player.getMetadata("blaster_effect_" + type.name.toLowerCase()).get(0).asInt() == 1) {
														
														SpectralArrow arrow = player.launchProjectile(SpectralArrow.class);
														
														arrow.setMetadata("damage", new FixedMetadataValue(SQPowerTools.getPluginMain(), blasterStats.damage));
														arrow.setMetadata("no_pickup", new FixedMetadataValue(SQPowerTools.getPluginMain(), true));
														arrow.setShooter(player);
														
														BlasterTask.arrows.put(arrow, 200);
														BlasterTask.velocities.put(arrow, arrow.getVelocity());
														
													} else {
														
														TippedArrow arrow = player.launchProjectile(TippedArrow.class);
														
														arrow.setMetadata("damage", new FixedMetadataValue(SQPowerTools.getPluginMain(), blasterStats.damage));
														arrow.setMetadata("no_pickup", new FixedMetadataValue(SQPowerTools.getPluginMain(), true));
														arrow.setShooter(player);
														
														try {
															
															arrow.setBasePotionData((PotionData) player.getMetadata("blaster_effect_" + type.name.toLowerCase()).get(0).value());
															
														} catch (Exception e) {
															
														}

														BlasterTask.arrows.put(arrow, 200);
														BlasterTask.velocities.put(arrow, arrow.getVelocity());
														
													}
													
												} else {
													
													Arrow arrow = player.launchProjectile(Arrow.class);
													
													arrow.setMetadata("damage", new FixedMetadataValue(SQPowerTools.getPluginMain(), blasterStats.damage));
													arrow.setMetadata("no_pickup", new FixedMetadataValue(SQPowerTools.getPluginMain(), true));
													arrow.setShooter(player);
													
													BlasterTask.arrows.put(arrow, 200);
													BlasterTask.velocities.put(arrow, arrow.getVelocity());
													
												}
												
											} else if (blasterStats.projectileType.equals(ProjectileType.FIREBALL)) {
												
												Fireball fireball = player.launchProjectile(Fireball.class);
												
												player.getWorld().playSound(player.getLocation(), Sound.ENTITY_BLAZE_SHOOT, 1, 1);
												
												fireball.setMetadata("damage", new FixedMetadataValue(SQPowerTools.getPluginMain(), blasterStats.damage));
												fireball.setMetadata("no_pickup", new FixedMetadataValue(SQPowerTools.getPluginMain(), true));
												fireball.setShooter(player);
												
												fireball.setYield(blasterStats.explosionSize);
												
											} else if (blasterStats.projectileType.equals(ProjectileType.BEAM)) {
												
												double pitch = player.getLocation().getPitch();
												
												double yaw = player.getLocation().getYaw();
												
												double x = Math.sin(Math.toRadians(yaw)) * -.25 * Math.cos(Math.toRadians(pitch));
												double y = Math.sin(Math.toRadians(pitch)) * -.25;
												double z = Math.cos(Math.toRadians(yaw)) * .25 * Math.cos(Math.toRadians(pitch));
												
												Location startLocation = player.getLocation().add(player.getLocation().getDirection());
												startLocation.add(0, 1.5, 0);
												
												double currentX = startLocation.getX();
												double currentY = startLocation.getY();
												double currentZ = startLocation.getZ();
				
												player.getWorld().playSound(player.getLocation(), Sound.ENTITY_ARROW_SHOOT, 2.0F, 1.0F);
												
												for (int i = 0; i < 400; i ++) {
													
													final Location location = new Location(player.getWorld(), currentX, currentY, currentZ);
													
													List<Entity> entites = new ArrayList<Entity>();
													entites.addAll(location.getWorld().getNearbyEntities(location, .25, 1, .25));
													
													for (Entity entity : location.getWorld().getNearbyEntities(location, .25, 1, .25)) {
															
														if (entity instanceof LivingEntity) {
																
															if (entity != player) {
																
																i = 400;
																
																((LivingEntity) entity).damage(blasterStats.damage, player);
																
																List<PotionEffect> selfEffects = new ArrayList<PotionEffect>();
																List<PotionEffect> otherEffects = new ArrayList<PotionEffect>();
																
																HashMap<Modifier, Integer> modifiers = SQPowerTools.getModifiers(handItem);
																
																for (Modifier modifier : type.modifiers) {
																	
																	if (modifiers.containsKey(modifier)) {
																		
																		for (Effect effect : modifier.effects) {
																			
																			if (effect != null) {
																				
																				if (effect.effectCase == 1) {
																					
																					selfEffects.add(new PotionEffect(EffectUtils.getEffectFromId(effect.effect), effect.duration * 20, effect.level * modifiers.get(modifier)));
																					
																				} else if (effect.effectCase == 2) {
																					
																					otherEffects.add(new PotionEffect(EffectUtils.getEffectFromId(effect.effect), effect.duration * 20, effect.level * modifiers.get(modifier)));
																					
																				}
																				
																			}
																			
																		}
																		
																	}
																	
																}
																
																for (Effect effect : type.effects) {
																	
																	if (effect != null) {
																		
																		if (effect.effectCase == 1) {
																			
																			selfEffects.add(new PotionEffect(EffectUtils.getEffectFromId(effect.effect), effect.duration * 20, effect.level));
																			
																		} else if (effect.effectCase == 2) {
																			
																			otherEffects.add(new PotionEffect(EffectUtils.getEffectFromId(effect.effect), effect.duration * 20, effect.level));
																			
																		}
																		
																	}
																	
																}
																
																for (PotionEffect effect : selfEffects) {
																	
																	if (player.hasPotionEffect(effect.getType())) {
																		
																		player.removePotionEffect(effect.getType());
																		
																	}
																	
																}
																
																player.addPotionEffects(selfEffects);
																
																LivingEntity enemy = (LivingEntity) entity;
																
																for (PotionEffect effect : otherEffects) {
																	
																	if (!EffectUtils.isBadEffect(effect.getType().getId())) {
																		
																		if (enemy.hasPotionEffect(effect.getType())) {
																			
																			enemy.removePotionEffect(effect.getType());
																			
																		}
																		
																		enemy.addPotionEffect(effect);
																		
																	}
																	
																}
																
																location.getWorld().playSound(location, Sound.ENTITY_ARROW_HIT, 2.0F, 1.0F);
																
															}
			
														}
															
													}
														
													if (location.getBlock().getType().equals(Material.AIR)) {
														
														EmpirePlayer ep = EmpirePlayer.getOnlinePlayer(player);
														
														if (ep.getEmpire().equals(Empire.ARATOR)) {
															
															ParticleEffect.REDSTONE.sendColor(Bukkit.getOnlinePlayers(),location.getX(), location.getY(),location.getZ(), Color.BLUE);
															
														} else if (ep.getEmpire().equals(Empire.REQUIEM)) {
															
															ParticleEffect.REDSTONE.sendColor(Bukkit.getOnlinePlayers(),location.getX(), location.getY(),location.getZ(), Color.RED);
															
														} else if (ep.getEmpire().equals(Empire.YAVARI)) {
															
															ParticleEffect.REDSTONE.sendColor(Bukkit.getOnlinePlayers(),location.getX(), location.getY(),location.getZ(), Color.PURPLE);
															
														} else {
															
															ParticleEffect.REDSTONE.sendColor(Bukkit.getOnlinePlayers(),location.getX(), location.getY(),location.getZ(), Color.WHITE);
															
														}
								
														currentX = currentX + x;
														currentY = currentY + y;
														currentZ = currentZ + z;
														
													} else {
														
														i = 400;
				
													}
													
												}
												
											} else if (blasterStats.projectileType.equals(ProjectileType.SHOTGUN)) {
												
												player.getWorld().playSound(player.getLocation(), Sound.ENTITY_ARROW_SHOOT, 1, 1);
												
												Random random = new Random();
												
												HashMap<Entity, Integer> damaged = new HashMap<Entity, Integer>();
												
												for (int i = 0; i < blasterStats.shotCount; i ++) {
													
													double pitch = player.getLocation().getPitch() + (random.nextDouble() * 10.0) - 5.0;
													double yaw = player.getLocation().getYaw() + (random.nextDouble() * 10.0) - 5.0;
													
													double x = Math.sin(Math.toRadians(yaw)) * Math.cos(Math.toRadians(pitch)) * -1;
													double y = Math.sin(Math.toRadians(pitch)) * -1;
													double z = Math.cos(Math.toRadians(yaw)) * Math.cos(Math.toRadians(pitch));
													
													Location startLocation = player.getLocation().add(player.getLocation().getDirection());
													startLocation.add(0, 1.5, 0);
													
													double currentX = startLocation.getX();
													double currentY = startLocation.getY();
													double currentZ = startLocation.getZ();
													
													for (int j = 0; j < 100; j ++) {
														
														final Location location = new Location(player.getWorld(), currentX, currentY, currentZ);
														
														List<Entity> entites = new ArrayList<Entity>();
														entites.addAll(location.getWorld().getNearbyEntities(location, .25, 1, .25));
														
														for (Entity entity : location.getWorld().getNearbyEntities(location, .25, 1, .25)) {
																
															if (entity instanceof LivingEntity) {
																	
																if (entity != player) {
																	
																	j = 100;
																	
																	if (damaged.containsKey(entity)) {
																		
																		damaged.replace(entity, damaged.get(entity) + 1);
																		
																	} else {
																		
																		damaged.put(entity, 1);
																		
																	}
																	
																}
				
															}
																
														}
															
														if (location.getBlock().getType().equals(Material.AIR)) {
															
															EmpirePlayer ep = EmpirePlayer.getOnlinePlayer(player);
															
															if (ep.getEmpire().equals(Empire.ARATOR)) {
																
																ParticleEffect.REDSTONE.sendColor(Bukkit.getOnlinePlayers(),location.getX(), location.getY(),location.getZ(), Color.BLUE);
																
															} else if (ep.getEmpire().equals(Empire.REQUIEM)) {
																
																ParticleEffect.REDSTONE.sendColor(Bukkit.getOnlinePlayers(),location.getX(), location.getY(),location.getZ(), Color.RED);
																
															} else if (ep.getEmpire().equals(Empire.YAVARI)) {
																
																ParticleEffect.REDSTONE.sendColor(Bukkit.getOnlinePlayers(),location.getX(), location.getY(),location.getZ(), Color.PURPLE);
																
															} else {
																
																ParticleEffect.REDSTONE.sendColor(Bukkit.getOnlinePlayers(),location.getX(), location.getY(),location.getZ(), Color.WHITE);
																
															}
									
															currentX = currentX + x;
															currentY = currentY + y;
															currentZ = currentZ + z;
															
														} else {
															
															j = 100;
					
														}
														
													}
													
												}
												
												for (Entity entity : damaged.keySet()) {
													
													((LivingEntity) entity).damage(blasterStats.damage * damaged.get(entity), player);
													
													List<PotionEffect> selfEffects = new ArrayList<PotionEffect>();
													List<PotionEffect> otherEffects = new ArrayList<PotionEffect>();
													
													HashMap<Modifier, Integer> modifiers = SQPowerTools.getModifiers(handItem);
													
													for (Modifier modifier : type.modifiers) {
														
														if (modifiers.containsKey(modifier)) {
															
															for (Effect effect : modifier.effects) {
																
																if (effect != null) {
																	
																	if (effect.effectCase == 1) {
																		
																		selfEffects.add(new PotionEffect(EffectUtils.getEffectFromId(effect.effect), effect.duration * 20, effect.level * modifiers.get(modifier)));
																		
																	} else if (effect.effectCase == 2) {
																		
																		otherEffects.add(new PotionEffect(EffectUtils.getEffectFromId(effect.effect), effect.duration * 20 * damaged.get(entity), effect.level * modifiers.get(modifier)));
																		
																	}
																	
																}
																
															}
															
														}
														
													}
													
													for (Effect effect : type.effects) {
														
														if (effect != null) {
															
															if (effect.effectCase == 1) {
																
																selfEffects.add(new PotionEffect(EffectUtils.getEffectFromId(effect.effect), effect.duration * 20, effect.level));
																
															} else if (effect.effectCase == 2) {
																
																otherEffects.add(new PotionEffect(EffectUtils.getEffectFromId(effect.effect), effect.duration * 20 * damaged.get(entity), effect.level));
																
															}
															
														}
														
													}
													
													for (PotionEffect effect : selfEffects) {
														
														if (player.hasPotionEffect(effect.getType())) {
															
															player.removePotionEffect(effect.getType());
															
														}
														
													}
													
													player.addPotionEffects(selfEffects);
													
													LivingEntity enemy = (LivingEntity) entity;
													
													for (PotionEffect effect : otherEffects) {
														
														if (!EffectUtils.isBadEffect(effect.getType().getId())) {
															
															if (enemy.hasPotionEffect(effect.getType())) {
																
																enemy.removePotionEffect(effect.getType());
																
															}
															
															enemy.addPotionEffect(effect);
															
														}
														
													}
													
													entity.getWorld().playSound(entity.getLocation(), Sound.ENTITY_ARROW_HIT, 2.0F, 1.0F);
													
												}
												
											}
											
										}
										
									}
									
								}

							}
								
						}
						
					}
					
				}
				
			}	
			
			if (handItem != null) {
				
				if (main) {
					
					player.getInventory().setItemInMainHand(handItem);
					
				} else {
					
					player.getInventory().setItemInOffHand(handItem);
					
				}
				
			}
			
		} else if (event.getAction().equals(Action.LEFT_CLICK_AIR) || event.getAction().equals(Action.LEFT_CLICK_BLOCK)) {
			
			boolean failed = true;
			
			Player player = event.getPlayer();
			
			if (player.getInventory().getItemInMainHand() != null) {
				
				ItemStack handItem = player.getInventory().getItemInMainHand();			
				
				if (handItem.hasItemMeta()) {
					
					if (handItem.getItemMeta().hasLore()) {
						
						List<String> lore = handItem.getItemMeta().getLore();
							
						if (lore.contains(ChatColor.DARK_PURPLE + "Power Tool")) {
								
							handItem = SQPowerTools.fixPowerTool(handItem);
							player.getInventory().setItemInMainHand(handItem);
							
							PowerToolType type = SQPowerTools.getType(handItem);
							
							if (type.blasterStats != null) {
								
								failed = false;
								
								if (player.hasMetadata("scoped")) {
									
									player.removeMetadata("scoped", SQPowerTools.getPluginMain());
									player.removePotionEffect(PotionEffectType.SLOW);
									
								} else {
										
									PowerTool powerTool = new PowerTool(handItem);
									
									player.setMetadata("scoped", new FixedMetadataValue(SQPowerTools.getPluginMain(), type.name));
									player.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, 1000000000, powerTool.getTotalBlasterStats().scope));
									
								}
								
							}
							
						}
						
					}
					
				}

			}
			
			if (failed) {
				
				if (player.getInventory().getItemInOffHand() != null) {
					
					ItemStack handItem = player.getInventory().getItemInOffHand();			
					
					if (handItem.hasItemMeta()) {
						
						if (handItem.getItemMeta().hasLore()) {
							
							List<String> lore = handItem.getItemMeta().getLore();
								
							if (lore.contains(ChatColor.DARK_PURPLE + "Power Tool")) {
									
								handItem = SQPowerTools.fixPowerTool(handItem);
								player.getInventory().setItemInOffHand(handItem);
								
								PowerToolType type = SQPowerTools.getType(handItem);
								
								if (type.blasterStats != null) {
									
									if (player.hasMetadata("scoped")) {
										
										player.removeMetadata("scoped", SQPowerTools.getPluginMain());
										player.removePotionEffect(PotionEffectType.SLOW);
										
									} else {
											
										PowerTool powerTool = new PowerTool(handItem);
										
										player.setMetadata("scoped", new FixedMetadataValue(SQPowerTools.getPluginMain(), type.name));
										player.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, 1000000000, powerTool.getTotalBlasterStats().scope));
										
									}
									
								}
								
							}
							
						}
						
					}

				}
				
			}
			
		}
		
	}
	
	@EventHandler
	public void onPlayerPickupItem(PlayerPickupItemEvent event) {
		
		if (event.getItem().hasMetadata("no_pickup")) {
			
			event.setCancelled(true);
			
		}
		
	}
	
	@EventHandler
	public void onEntityDamageByEntity(EntityDamageByEntityEvent event) {		
		
		if (event.getDamager() instanceof Projectile) {
			
			Projectile projectile = (Projectile) event.getDamager();

			if (projectile.hasMetadata("damage")) {
				
				double damage = 0;
				
				boolean anyDamage = false;
				
				List<MetadataValue> values = projectile.getMetadata("damage");
				
				for (MetadataValue value : values) {
					
					if (value.getOwningPlugin() == SQPowerTools.getPluginMain()) {
						
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
	
	@EventHandler
	public void onEntityShootBow(EntityShootBowEvent event) {
		
		if (event.getBow().hasItemMeta()) {
			
			if (event.getBow().getItemMeta().hasLore()) {
				
				if (event.getBow().getItemMeta().getLore().contains(ChatColor.DARK_PURPLE + "Power Tool")) {
					
					if (SQPowerTools.getType(event.getBow()).blasterStats != null) {
						
						event.setCancelled(true);
						
					}
					
				}
				
			}
			
		}
		
	}
	
}
