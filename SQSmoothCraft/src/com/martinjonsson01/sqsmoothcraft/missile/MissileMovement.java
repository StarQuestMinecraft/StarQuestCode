package com.martinjonsson01.sqsmoothcraft.missile;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.block.Sign;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.entity.ShulkerBullet;
import org.bukkit.metadata.FixedMetadataValue;

import com.ginger_walnut.sqsmoothcraft.SQSmoothCraft;

public class MissileMovement {
	
	static Location currentLoc = null;
	static Location targetLoc = null;
	
	@SuppressWarnings("deprecation")
	public static boolean activateMissile(Block ammoDispenserBlock, Sign s, final Player shooter, String type) {
		
		int detectionRange = SQSmoothCraft.config.getInt("weapons.heatseeking missile.detection range");
		int cooldown = SQSmoothCraft.config.getInt("weapons.heatseeking missile.cooldown");
		int fuelTime = SQSmoothCraft.config.getInt("weapons.heatseeking missile.fuelTime");
		
		final ShulkerBullet shulkerBullet = (ShulkerBullet) ammoDispenserBlock.getLocation().getWorld().spawnEntity(MissileDetection.inFrontOfDispenser(s.getBlock()).getLocation(), EntityType.SHULKER_BULLET);
		
		shulkerBullet.getLocation().setDirection(MissileDetection.getDirectionVector(s.getBlock()));
		
		shulkerBullet.setVelocity(MissileDetection.getDirectionVector(s.getBlock()).normalize().multiply(2));
		
		shulkerBullet.setMetadata("damage", new FixedMetadataValue(SQSmoothCraft.getPluginMain(), SQSmoothCraft.config.getInt("weapons.heatseeking missile.damage")));
		shulkerBullet.setMetadata("no_pickup", new FixedMetadataValue(SQSmoothCraft.getPluginMain(), true));
		shulkerBullet.setMetadata("carry_over", new FixedMetadataValue(SQSmoothCraft.getPluginMain(), SQSmoothCraft.config.getBoolean("weapons.heatseeking missile.carry over")));
		shulkerBullet.setMetadata("type", new FixedMetadataValue(SQSmoothCraft.getPluginMain(), type));
		
		shulkerBullet.setBounce(false);
		shulkerBullet.setShooter(shooter);
		
		Player target = Missile.getNearestPlayer(shooter);
		shulkerBullet.setTarget(target);
		
		if (shulkerBullet.getTarget() == null) {
			shulkerBullet.remove();
			shooter.sendMessage(ChatColor.RED + "Could not find any targets in a " + detectionRange + " block range.");
			return false;
		}
		
		final int updateshulkerBulletScheduler = Bukkit.getScheduler().scheduleSyncRepeatingTask(SQSmoothCraft.getPluginMain(), new Runnable() {
			@Override
			public void run() {
				
				shulkerBullet.setVelocity(shulkerBullet.getVelocity().multiply(2));
				// To make it go faster
			}
		}, 2, 10);
		
		Bukkit.getScheduler().scheduleSyncDelayedTask(SQSmoothCraft.getPluginMain(), new Runnable() {
			
			@Override
			public void run() {
				
				if (!shulkerBullet.isDead()) {
					shooter.sendMessage(ChatColor.RED + "The fuel of your heat seeking missile ran out.");
				}
				shulkerBullet.remove();
				Bukkit.getScheduler().cancelTask(updateshulkerBulletScheduler);
			}
			
		}, fuelTime * 20);
		
		Missile.missileCoolDownList.add(shooter);
		final Player cdPlayer = shooter;
		
		Bukkit.getScheduler().scheduleAsyncDelayedTask(SQSmoothCraft.getPluginMain(), new Runnable() {
			
			@Override
			public void run() {
				
				Missile.missileCoolDownList.remove(cdPlayer);
				
			}
			
		}, cooldown * 20);
		return true;
		
	}
	
}
