package com.martinjonsson01.sqsmoothcraft.missile;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.block.Dispenser;
import org.bukkit.block.Sign;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.entity.ShulkerBullet;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.metadata.FixedMetadataValue;

import com.ginger_walnut.sqsmoothcraft.SQSmoothCraft;

public class MissileListener implements Listener {
	
	Location currentLoc = null;
	Location targetLoc = null;
	
	public static List<Player> missileCoolDownList = new ArrayList<Player>();
	
	@SuppressWarnings("deprecation")
	@EventHandler
	public void onPlayerInteract(PlayerInteractEvent e) {
		
		if (e.getAction() != Action.RIGHT_CLICK_BLOCK)
			return;
			
		if (e.getClickedBlock().getState() instanceof Sign) {
			
			Sign s = (Sign) e.getClickedBlock().getState();
			if (s.getLine(0).equalsIgnoreCase("[hsmissile]")) {
				if (MissileDetection.detectLauncher(s.getBlock())) {
					s.setLine(0, "");
					s.setLine(1, ChatColor.LIGHT_PURPLE + "[" + ChatColor.GOLD + "Missile" + ChatColor.LIGHT_PURPLE + "]");
					s.setLine(2, ChatColor.LIGHT_PURPLE + "[" + ChatColor.RED + "Heat Seeking" + ChatColor.LIGHT_PURPLE + "]");
					s.setLine(3, "");
					s.update();
					return;
				}
			}
			
			if (s.getLine(1).equals(ChatColor.LIGHT_PURPLE + "[" + ChatColor.GOLD + "Missile" + ChatColor.LIGHT_PURPLE + "]")
					&& s.getLine(2).equals(ChatColor.LIGHT_PURPLE + "[" + ChatColor.RED + "Heat Seeking" + ChatColor.LIGHT_PURPLE + "]")) {
					
				Block ammoDispenserBlock = MissileDetection.getAmmoDispenser(s.getBlock());
				Dispenser ammoDispenser = (Dispenser) ammoDispenserBlock.getState();
				Inventory dispenserInv = ammoDispenser.getInventory();
				
				// Checks if the dispenser of the missile launcher has ammo, if
				// so, then it launches the missile
				if (dispenserInv.containsAtLeast(Missile.missileAmmo(), 1)) {
					
					if (missileCoolDownList.contains(e.getPlayer())) {
						e.getPlayer().sendMessage(ChatColor.RED + "You are still on cooldown...");
						return;
					}
					
					ShulkerBullet shulkerBullet = (ShulkerBullet) ammoDispenserBlock.getLocation().getWorld().spawnEntity(MissileDetection.inFrontOfDispenser(s.getBlock()).getLocation(), EntityType.SHULKER_BULLET);
					
					shulkerBullet.getLocation().setDirection(MissileDetection.getDirectionVector(s.getBlock()));
					
					shulkerBullet.setVelocity(MissileDetection.getDirectionVector(s.getBlock()).normalize().multiply(2));
					
					// shulkerBullet.setMetadata("Heat seeking missile", new
					// FixedMetadataValue(SQSmoothCraft.getPluginMain(), "type
					// 1"));
					
					shulkerBullet.setMetadata("damage", new FixedMetadataValue(SQSmoothCraft.getPluginMain(), SQSmoothCraft.config.getInt("weapons.heatseeking missile.damage")));
					shulkerBullet.setMetadata("no_pickup", new FixedMetadataValue(SQSmoothCraft.getPluginMain(), true));
					shulkerBullet.setMetadata("carry_over", new FixedMetadataValue(SQSmoothCraft.getPluginMain(), SQSmoothCraft.config.getBoolean("weapons.heatseeking missile.carry over")));
					
					shulkerBullet.setBounce(false);
					shulkerBullet.setShooter(e.getPlayer());
					int detectionRange = SQSmoothCraft.config.getInt("weapons.heatseeking missile.detection range");
					int cooldown = SQSmoothCraft.config.getInt("weapons.heatseeking missile.cooldown");
					int fuelTime = SQSmoothCraft.config.getInt("weapons.heatseeking missile.fuelTime");
					
					for (Player p : Bukkit.getServer().getOnlinePlayers()) {
						
						if (p.getLocation().distance(e.getPlayer().getLocation()) <= detectionRange) {
							
							// if(p is not in the same empire as e.getPlayer() )
							
							if (SQSmoothCraft.shipMap.containsKey(p.getUniqueId())) {
								
								if (p != e.getPlayer()) {
									shulkerBullet.setTarget(p);
									break;
								}
							}
							
						}
						
					}
					if (shulkerBullet.getTarget() == null) {
						shulkerBullet.remove();
						e.getPlayer().sendMessage(ChatColor.RED + "Could not find any targets in a " + detectionRange + " block range.");
						return;
					}
					
					dispenserInv.removeItem(Missile.missileAmmo());
					// Removes
					// one
					// missile
					// ammo from
					// dispenser
					
					
					int updateshulkerBulletScheduler = Bukkit.getScheduler().scheduleSyncRepeatingTask(SQSmoothCraft.getPluginMain(), new Runnable() {
						@Override
						public void run() {
							
							currentLoc = shulkerBullet.getLocation();
							targetLoc = e.getPlayer().getLocation();
						
							shulkerBullet.setVelocity(shulkerBullet.getVelocity().multiply(2));
						}
					}, 2, 10);
					
					Bukkit.getScheduler().scheduleSyncDelayedTask(SQSmoothCraft.getPluginMain(), new Runnable() {
						
						@Override
						public void run() {
							
							if (!shulkerBullet.isDead()) {
								e.getPlayer().sendMessage(ChatColor.RED + "The fuel of your heat seeking missile ran out.");
							}
							shulkerBullet.remove();
							Bukkit.getScheduler().cancelTask(updateshulkerBulletScheduler);
						}
						
					}, fuelTime * 20);
					
					missileCoolDownList.add(e.getPlayer());
					Player cdPlayer = e.getPlayer();
					
					Bukkit.getScheduler().scheduleAsyncDelayedTask(SQSmoothCraft.getPluginMain(), new Runnable() {
						
						@Override
						public void run() {
							
							missileCoolDownList.remove(cdPlayer);
							
						}
						
					}, cooldown * 20);
				}
				
			}
		}
		
	}
	
}
