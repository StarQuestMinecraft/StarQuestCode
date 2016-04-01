package com.ginger_walnut.sqsmoothcraft.ship;

import java.util.ArrayList;
import java.util.List;

import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.chat.TextComponent;
import net.minecraft.server.v1_9_R1.EntityPlayer;
import net.minecraft.server.v1_9_R1.PacketPlayOutEntityDestroy;
import net.minecraft.server.v1_9_R1.PacketPlayOutPlayerInfo;
import net.minecraft.server.v1_9_R1.PacketPlayOutPlayerInfo.EnumPlayerInfoAction;
import net.minecraft.server.v1_9_R1.PlayerConnection;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.craftbukkit.v1_9_R1.entity.CraftPlayer;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Fireball;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.util.Vector;
import org.inventivetalent.bossbar.BossBar;
import org.inventivetalent.bossbar.BossBarAPI;

import com.ginger_walnut.sqsmoothcraft.SQSmoothCraft;

public class Ship {

	Player captain = null;
	
	public List<ShipBlock> blockList = null;
	public List<ShipBlock> cannonList = null;	
	public List<ShipBlock> missleList = null;
	public List<ShipBlock> reactorList = null;
	
	ShipBlock mainBlock = null;
	
	public ShipBlock thirdPersonBlock = null;
	public EntityPlayer thirdPersonPlayer = null;
	
	float speed = 0.0f;
	float maxSpeed = 0.0f;
	
	float acceleration = 0.0f;
	
	float maxYawRate = 0.0f;
	
//	float lastYaw = 0.0f;
//	
	
	double yawDirection = 0.0f;
	double pitchDirection = 0.0f;
	
	double yawSin = 0.0f;
	double yawCos = 0.0f;
	
//	double lastYawSin = 0.0f;
//	double lastYawCos = 0.0f;
//	
//	float lastPitch = 0.0f;
	
	double pitchSin = 0.0f;
	double pitchCos = 0.0f;
	
//	double lastPitchSin = 0.0f;
//	double lastPitchCos = 0.0f;
	
	double adjustedPitchSin = 0.0f;
	double adjustedPitchCos = 0.0f;
	
//	double lastAdjustedPitchSin = 0.0f;
//	double lastAdjustedPitchCos = 0.0f;
	
	double adjustedYawSin = 0.0f;
	double adjustedYawCos = 0.0f;
	
//	double lastAdjustedYawSin = 0.0f;
//	double lastAdjustedYawCos = 0.0f;
	
	Location lastLocation = null;
	
	Location location = null;	
	
	boolean lockedDirection = true;
	
	float fuel = 0;
	float startingFuel = 0;
	
	BossBar speedBar = null;
	BossBar fuelBar = null;

	boolean alternatingBlockDirection = false;
	
	boolean explosiveMode = false;
	
	public Ship (List<ShipBlock> shipBlocks, ShipBlock firstMainBlock, Player firstCaptain, float firstMaxSpeed, float firstMaxYawRate, float maxAcceleration, float firstFuel) {
		
		captain = firstCaptain;
		blockList = shipBlocks;
		
		mainBlock = firstMainBlock;
		
		maxSpeed = firstMaxSpeed;
		maxYawRate = firstMaxYawRate;
		
		location = captain.getLocation();
		
		location.add(0, -1, 0);
		lastLocation = location;
		
		yawSin = Math.sin(Math.toRadians(captain.getLocation().getYaw()));
		yawCos = Math.cos(Math.toRadians(captain.getLocation().getYaw()));
		
		adjustedPitchSin = Math.sin(Math.toRadians(captain.getLocation().getPitch()));
		adjustedPitchCos = Math.cos(Math.toRadians(captain.getLocation().getPitch()));
		
		acceleration = maxAcceleration;
		
		speedBar = BossBarAPI.addBar(captain, new TextComponent("Speed"), BossBarAPI.Color.BLUE, BossBarAPI.Style.NOTCHED_10, 0.0f);
		speedBar.setVisible(true);
		
		fuelBar = BossBarAPI.addBar(captain, new TextComponent("Fuel"), BossBarAPI.Color.RED, BossBarAPI.Style.NOTCHED_10, 1.0f);
		fuelBar.setVisible(true);
		
		fuel = firstFuel;
		startingFuel = firstFuel;
		
		(new ShipCreator()).run(this, captain);
		
	}
	
	public Player getCaptain() {
		
		return captain;
		
	}
	
	public List<ShipBlock> getShipBlocks() {
		
		return blockList;		
		
	}
	
	public void setShipBlocks(List<ShipBlock> shipBlocks) {
		
		blockList = shipBlocks;
		
	}
	
	public List<ShipBlock> getCannons() {
		
		return cannonList;
		
	}
	
	public void setCannons(List<ShipBlock> cannons) {
		
		cannonList = cannons;
		
	}
	
	public ShipBlock getMainBlock() {
		
		return mainBlock;
		
	}
	
	public float getSpeed() {
		
		return speed;
		
	}
	
	public void setSpeed(float newSpeed) {
		
		speed = newSpeed;
		
	}
	
	public float getMaxSpeed() {
		
		return maxSpeed;
		
	}
	
	public void setMaxSpeed(float newMaxSpeed) {
		
		maxSpeed = newMaxSpeed;
		
	}
	
	public float getMaxYawRate() {
		
		return maxYawRate;
		
	}
	
	public void setMaxYawRate(float newMaxYawRate) {
		
		maxYawRate = newMaxYawRate;
		
	}
	
	public Location getLocation() {
		
		return location;
		
	}
	
	public void setLocation(Location newLocation) {
		
/*		if (location.getYaw() != newLocation.getYaw()) {
			
			yawSin = Math.sin(Math.toRadians(newLocation.getYaw()));
			yawCos = Math.cos(Math.toRadians(newLocation.getYaw()));
			
		}*/
		
		location = newLocation;
		
	}
	
	public float getAcceleration() {
		
		return acceleration;
		
	}
	
	public void setAcceleration(float newAcceleration) {
		
		acceleration = newAcceleration;
		
	}
	
	public double getYawSin() {
		
		return yawSin;
		
	}
	
	public double getYawCos() {
		
		return yawCos;
		
	}
	
	public double getAdjustedYawSin() {
		
		return adjustedYawSin;
		
	}
	
	public double getAdjustedYawCos() {
		
		return adjustedYawCos;
		
	}
	
	public double getAdjustedPitchSin() {
		
		return adjustedPitchSin;
		
	}
	
	public double getAdjustedPitchCos() {
		
		return adjustedPitchCos;
		
	}
	
	public void setDirectionYaw(float newYaw) {
//		
//		lastYaw = location.getYaw();
//		
//		lastYawSin = yawSin;
//		lastYawCos = yawCos;
//		
//		lastAdjustedYawSin = adjustedYawSin;
//		lastAdjustedYawCos = adjustedYawCos;
		
		if (newYaw != ((float) yawDirection)) {
			
			double radYaw = Math.toRadians(newYaw);
			
			yawSin = Math.sin(radYaw);
			yawCos = Math.cos(radYaw);
			
			yawDirection = newYaw;
			
		}
		
	}
	
	public void setMovingYaw(float newYaw) {
//		
//		lastYaw = location.getYaw();
//		
//		lastYawSin = yawSin;
//		lastYawCos = yawCos;
//		
//		lastAdjustedYawSin = adjustedYawSin;
//		lastAdjustedYawCos = adjustedYawCos;
		
		if (newYaw != ((float) location.getYaw())) {

			location.setYaw(newYaw);
			
			double radYaw = Math.toRadians(newYaw);
			
			adjustedYawSin = Math.sin(radYaw + 1.570796);
			adjustedYawCos = Math.cos(radYaw + 1.570796);
			
		}
		
	}
	
	public void setDirectionPitch(float newPitch) {
//		

		
//		lastPitch = location.getPitch();
//		
//		System.out.print(lastPitch);
//		
//		lastPitchSin = pitchSin;
//		lastPitchCos = pitchCos;
//		
//		lastAdjustedPitchSin = adjustedPitchSin;
//		lastAdjustedPitchCos = adjustedPitchCos;
		
		if (newPitch != ((float) pitchDirection)) {
			
			double radPitch = Math.toRadians(newPitch);
			
			pitchSin = Math.sin(radPitch);
			pitchCos = Math.cos(radPitch);	
			
			pitchDirection = newPitch;
			
		}
		
	}
	
	public void setMovingPitch(float newPitch) {
//		
//		lastPitch = location.getPitch();
//		
//		System.out.print(lastPitch);
//		
//		lastPitchSin = pitchSin;
//		lastPitchCos = pitchCos;
//		
//		lastAdjustedPitchSin = adjustedPitchSin;
//		lastAdjustedPitchCos = adjustedPitchCos;
		
		if (newPitch != ((float) location.getPitch())) {
			
			location.setPitch(newPitch);
			
			double radPitch = Math.toRadians(newPitch);
			
			adjustedPitchSin = Math.sin(radPitch + 1.570796);
			adjustedPitchCos = Math.cos(radPitch + 1.570796);
			
		}
		
	}
	
//	public void revertLocation() {
//		
//		location.setYaw(lastYaw);
//		
//		yawSin = lastYawSin;
//		yawCos = lastYawCos;
//		
//		adjustedYawSin = lastAdjustedYawSin;
//		adjustedYawCos = lastAdjustedYawCos;
//		
//		location.setPitch(lastPitch);
//		
//		pitchSin = lastPitchSin;
//		pitchCos = lastPitchCos;
//		
//		adjustedPitchSin = lastAdjustedPitchSin;
//		adjustedPitchCos = lastAdjustedPitchCos;
//		
//		location = lastLocation;
//		
//	}
	
	public void damage(ShipBlock shipBlock, double damage, boolean carryOver) {
		
		shipBlock.health = shipBlock.health - damage;
		
		if (shipBlock.health <= 0) {
			
			if (shipBlock.stand.getHelmet().getType().equals(Material.COAL_BLOCK)) {
				
				shipBlock.ship.cannonList.remove(shipBlock);
				
			}
			
			if (shipBlock.stand.getHelmet().getType().equals(Material.DROPPER)) {
				
				shipBlock.ship.reactorList.remove(shipBlock);
				
			}
			
			if (shipBlock.stand.getHelmet().getType().equals(Material.DISPENSER)) {
				
				shipBlock.ship.missleList.remove(shipBlock);
				
			}
			
			shipBlock.ship.blockList.remove(shipBlock);
			
			double averageWeight = 0;
			
			for (ShipBlock block : blockList) {
				
				averageWeight = averageWeight + block.weight;
				
			}
			
			averageWeight = averageWeight / blockList.size();
			
			maxSpeed = (float) (1 / averageWeight) + ((float) reactorList.size() / 10.0f);
			
			if (maxSpeed > 1) {
				
				maxSpeed = 1;
				
			}
			
			maxYawRate = maxSpeed * 5;
					
			acceleration = maxSpeed / 20;
			
			shipBlock.stand.remove();
			
		}
		
		if (carryOver) {
			
			if (shipBlock.health <= 0) {
				
				double remainingDamage = shipBlock.health * -1;
				
				List<ShipBlock> surroundingBlocks = new ArrayList<ShipBlock>();
				
				for (ShipBlock block : shipBlock.ship.blockList) {
					
					if (shipBlock.loc.x == block.loc.x + 1 && shipBlock.loc.y == block.loc.y && shipBlock.loc.z == block.loc.z) {
						
						surroundingBlocks.add(block);
						
					} else if (shipBlock.loc.x == block.loc.x - 1 && shipBlock.loc.y == block.loc.y && shipBlock.loc.z == block.loc.z) {
						
						surroundingBlocks.add(block);
						
					} else if (shipBlock.loc.x == block.loc.x && shipBlock.loc.y == block.loc.y + 1 && shipBlock.loc.z == block.loc.z) {
						
						surroundingBlocks.add(block);
						
					} else if (shipBlock.loc.x == block.loc.x && shipBlock.loc.y == block.loc.y - 1 && shipBlock.loc.z == block.loc.z) {
						
						surroundingBlocks.add(block);

					} else if (shipBlock.loc.x == block.loc.x && shipBlock.loc.y == block.loc.y && shipBlock.loc.z == block.loc.z + 1) {
						
						surroundingBlocks.add(block);
						
					} else if (shipBlock.loc.x == block.loc.x && shipBlock.loc.y == block.loc.y && shipBlock.loc.z == block.loc.z - 1) {
						
						surroundingBlocks.add(block);

					}
					
				}
				
				for (ShipBlock block : surroundingBlocks) {

					block.ship.damage(block, remainingDamage / surroundingBlocks.size(), true);
					
				}
				
			}
			
		}
	
	}
	
	@SuppressWarnings("deprecation")
	public boolean blockify() {
		
		float yaw = location.getYaw();
		
		if (yaw < 0) {
			
			yaw = yaw * -1;
				
			yaw = 360 - yaw;
				
		}
		
		List<Block> blocks = new ArrayList<Block>();
		List<Material> materials = new ArrayList<Material>();
		List<Short> durabilitys = new ArrayList<Short>();
		
		if (yaw >= 315 || yaw < 45) {
			
			for (ShipBlock block : blockList) {
				
				Location blockLocation = new Location(location.getWorld(), mainBlock.getLocation().getBlockX() + block.loc.x, mainBlock.getLocation().getBlockY() + block.loc.y, mainBlock.getLocation().getBlockZ() + block.loc.z);
				
				blocks.add(location.getWorld().getBlockAt(blockLocation));		
				materials.add(block.stand.getHelmet().getType());	
				durabilitys.add(block.stand.getHelmet().getDurability());
				
			}
			
		} else if (yaw >= 225 && yaw < 315) {
			
			for (ShipBlock block : blockList) {
				
				Location blockLocation = new Location(location.getWorld(), mainBlock.getLocation().getBlockX() + block.loc.z, mainBlock.getLocation().getBlockY() + block.loc.y, mainBlock.getLocation().getBlockZ() + block.loc.x);
				
				blocks.add(location.getWorld().getBlockAt(blockLocation));		
				materials.add(block.stand.getHelmet().getType());	
				durabilitys.add(block.stand.getHelmet().getDurability());
				
			}
			
		} else if (yaw >= 135 && yaw < 225) {
			
			for (ShipBlock block : blockList) {
				
				Location blockLocation = new Location(location.getWorld(), mainBlock.getLocation().getBlockX() - block.loc.x, mainBlock.getLocation().getBlockY() + block.loc.y, mainBlock.getLocation().getBlockZ() - block.loc.z);
				
				blocks.add(location.getWorld().getBlockAt(blockLocation));		
				materials.add(block.stand.getHelmet().getType());	
				durabilitys.add(block.stand.getHelmet().getDurability());
				
			}
			
		} else if (yaw >= 45 && yaw < 135) {
			
			for (ShipBlock block : blockList) {
				
				Location blockLocation = new Location(location.getWorld(), mainBlock.getLocation().getBlockX() - block.loc.z, mainBlock.getLocation().getBlockY() + block.loc.y, mainBlock.getLocation().getBlockZ() - block.loc.x);
				
				blocks.add(location.getWorld().getBlockAt(blockLocation));		
				materials.add(block.stand.getHelmet().getType());	
				durabilitys.add(block.stand.getHelmet().getDurability());
				
			}
			
		}
		
		boolean canDecompile = true;
		
		for (Block block : blocks) {
			
			if (!block.getType().equals(Material.AIR)) {
				
				canDecompile = false;
				
			}
			
		}
		
		if (canDecompile) {
			
			for (int i = 0; i < blocks.size(); i ++) {
				
				blocks.get(i).setType(materials.get(i));
				blocks.get(i).setData((byte) (int) durabilitys.get(i));
				
			}
			
			for (ShipBlock shipBlock : blockList) {
				
				shipBlock.stand.remove();
				
			}

			if (SQSmoothCraft.shipMap.containsKey(captain.getUniqueId())) {
				
				SQSmoothCraft.shipMap.remove(captain.getUniqueId());
				
				captain.teleport(location.getWorld().getBlockAt(new Location (location.getWorld(), mainBlock.getLocation().getBlockX() + .5, mainBlock.getLocation().getBlockY(), mainBlock.getLocation().getBlockZ() + .5)).getRelative(0, 1, 0).getLocation());
				
			} else {
				
				SQSmoothCraft.stoppedShipMap.remove(this);
				
			}
			
			return true;			
			
		} else {
			
			captain.sendMessage(ChatColor.RED + "Ship cannot undetect becuase of objects in the way");
			
			return false;
			
		}
		
	}
	
	public void exit() {
		
		Ship ship = SQSmoothCraft.shipMap.get(captain.getUniqueId());
			
		if (thirdPersonPlayer != null) {
				
			for (Player onlinePlayer : SQSmoothCraft.getPluginMain().getServer().getOnlinePlayers()) {
				
				PlayerConnection connection = ((CraftPlayer) onlinePlayer).getHandle().playerConnection;
				
				connection.sendPacket(new PacketPlayOutPlayerInfo(EnumPlayerInfoAction.REMOVE_PLAYER, thirdPersonPlayer));
				connection.sendPacket(new PacketPlayOutEntityDestroy(thirdPersonPlayer.getId()));
				
//			onlinePlayer.showPlayer(event.getPlayer());
				
			}
				
		}
			
		thirdPersonPlayer = null;
			
		if (SQSmoothCraft.shipMap.containsKey(captain.getUniqueId())) {

			SQSmoothCraft.stoppedShipMap.add(ship);
				
			SQSmoothCraft.shipMap.remove(captain.getUniqueId());
			
		}
		
		captain.getInventory().clear();
		captain.getInventory().setArmorContents(null);
		
		speedBar.removePlayer(captain);
		speedBar.setVisible(false);
		
		fuelBar.removePlayer(captain);
		fuelBar.setVisible(false);
		
		SQSmoothCraft.knapsackMap.get(captain.getUniqueId()).unpack(captain);
		
		mainBlock.stand.eject();
		
	}
	
	public void fireMissles() {
		
		if (captain.hasMetadata("missile_cooldown")) {
			
			captain.sendMessage(ChatColor.RED + "The missle launchers are on cooldown");
			
		} else {
			
			new CooldownHandler(captain, "missile_cooldown", SQSmoothCraft.config.getInt("weapons.missile launcher.cooldown")).runTaskTimer(SQSmoothCraft.getPluginMain(), 0, 1);
			
			World world = captain.getWorld();
			
			Location captainLocation = captain.getLocation();
			
			if (fuel > 0.0f) {
			
				for (int i = 0; i < missleList.size(); i ++) {
					
					Location blockLocation = missleList.get(i).getLocation();
					Location location = blockLocation.toVector().add(captainLocation.getDirection().multiply(4)).toLocation(world, captainLocation.getYaw(), captainLocation.getPitch());
					
					Fireball fireball = (Fireball) world.spawnEntity(location, EntityType.FIREBALL);
					
					fireball.setYield((float) SQSmoothCraft.config.getDouble("weapons.missile launcher.explosion power"));
					
					Vector newVelocity = captain.getLocation().getDirection();
					
					newVelocity.multiply(4);
					
					fireball.setVelocity(newVelocity);
					
					fireball.setMetadata("damage", new FixedMetadataValue(SQSmoothCraft.getPluginMain(), SQSmoothCraft.config.getInt("weapons.missile launcher.damage")));
					fireball.setMetadata("no_pickup", new FixedMetadataValue(SQSmoothCraft.getPluginMain(), true));
					fireball.setMetadata("carry_over", new FixedMetadataValue(SQSmoothCraft.getPluginMain(), SQSmoothCraft.config.getBoolean("weapons.missile launcher.carry over")));
					
					new ProjectileSmoother(fireball, newVelocity).runTaskTimer(SQSmoothCraft.getPluginMain(), 0, 1);
					
					world.playSound(blockLocation, Sound.ENTITY_GHAST_SHOOT, 2, 1);
					
				}
				
			}
			
		}
		
	}
	
	public void fireCannons() {
		
		if (!explosiveMode) {
			
			if (captain.hasMetadata("cannon_cooldown")) {
				
				captain.sendMessage(ChatColor.RED + "The cannons are on cooldown");
				
			} else {
				
				new CooldownHandler(captain, "cannon_cooldown", SQSmoothCraft.config.getInt("weapons.cannon.cooldown")).runTaskTimer(SQSmoothCraft.getPluginMain(), 0, 1);
				
				World world = captain.getWorld();
				
				Location captainLocation = captain.getLocation();
				
				if (fuel > 0.0f) {
				
					for (int i = 0; i < cannonList.size(); i ++) {
						
						Location blockLocation = cannonList.get(i).getLocation();
						Location location = blockLocation.toVector().add(captainLocation.getDirection().multiply(4)).toLocation(world, captainLocation.getYaw(), captainLocation.getPitch());
						
						Arrow arrow = (Arrow) world.spawnEntity(location, EntityType.ARROW);
						
						Vector newVelocity = captainLocation.getDirection();
						
						newVelocity.multiply(4);
						
						arrow.setVelocity(newVelocity);
						
						arrow.setMetadata("damage", new FixedMetadataValue(SQSmoothCraft.getPluginMain(), SQSmoothCraft.config.getInt("weapons.cannon.damage")));
						arrow.setMetadata("no_pickup", new FixedMetadataValue(SQSmoothCraft.getPluginMain(), true));
						arrow.setMetadata("carry_over", new FixedMetadataValue(SQSmoothCraft.getPluginMain(), SQSmoothCraft.config.getBoolean("weapons.cannon.carry over")));
						
						new ProjectileSmoother(arrow, newVelocity).runTaskTimer(SQSmoothCraft.getPluginMain(), 0, 1);
						
						world.playSound(blockLocation, Sound.ENTITY_ARROW_SHOOT, 2, 1);
						
					}
					
				}
				
			}
			
		} else {
			
			if (captain.hasMetadata("cannon_explosive_cooldown")) {
				
				captain.sendMessage(ChatColor.RED + "The cannons are on cooldown");
				
			} else {
				
				new CooldownHandler(captain, "cannon_explosive_cooldown", SQSmoothCraft.config.getInt("weapons.cannon.explosive mode cooldown")).runTaskTimer(SQSmoothCraft.getPluginMain(), 0, 1);
				
				World world = captain.getWorld();
				
				Location captainLocation = captain.getLocation();
				
				if (fuel > 0.0f) {
				
					for (int i = 0; i < cannonList.size(); i ++) {
						
						Location blockLocation = cannonList.get(i).getLocation();
						Location location = blockLocation.toVector().add(captainLocation.getDirection().multiply(4)).toLocation(world, captainLocation.getYaw(), captainLocation.getPitch());
						
						Arrow arrow = (Arrow) world.spawnEntity(location, EntityType.ARROW);
						
						Vector newVelocity = captainLocation.getDirection();
						
						newVelocity.multiply(4);
						
						arrow.setVelocity(newVelocity);
						
						arrow.setMetadata("damage", new FixedMetadataValue(SQSmoothCraft.getPluginMain(), SQSmoothCraft.config.getInt("weapons.cannon.damage")));
						arrow.setMetadata("no_pickup", new FixedMetadataValue(SQSmoothCraft.getPluginMain(), true));
						arrow.setMetadata("carry_over", new FixedMetadataValue(SQSmoothCraft.getPluginMain(), SQSmoothCraft.config.getBoolean("weapons.cannon.carry over")));
						
						arrow.setMetadata("explosive", new FixedMetadataValue(SQSmoothCraft.getPluginMain(), true));
						
						new ProjectileSmoother(arrow, newVelocity).runTaskTimer(SQSmoothCraft.getPluginMain(), 0, 1);
						
						world.playSound(blockLocation, Sound.ENTITY_ARROW_SHOOT, 2, 1);
						
					}
					
				}
				
			}
			
		}
		
	}	
	
	public void accelerate(float multiplier) {
		
		if (fuel > 0.0f) {
			
			if (speed == maxSpeed) {
			
			} else if (speed > maxSpeed){
					
				speed = maxSpeed;
				
			} else {
					
				speed = speed + (acceleration * multiplier);
					
			}
			
		}
		
	}
	
	public void decelerate(float multiplier) {
		
		if (fuel > 0.0f) {
			
			if (speed == maxSpeed) {
			
			} else if (speed < 0){
					
				speed = 0;
				
			} else {
					
				speed = speed - (acceleration * multiplier);
					
			}
			
			if (speed < 0) {
				
				speed = 0;
				
			}
			
		}
		
	}
	
	public void toggleLock() {

		ItemStack itemInHand = captain.getInventory().getItemInMainHand();
			
		if (itemInHand.getItemMeta().getDisplayName().equals("Direction Locker")) {
				
			if (lockedDirection) {
					
				lockedDirection = false;
					
				captain.sendMessage(ChatColor.GREEN + "The direction has been unlocked");
					
			} else {
					
				lockedDirection = true;
					
				captain.sendMessage(ChatColor.RED + "The direction has been locked");
					
			}

		}
			
	}
	
	public void toggleExplosive() {

		ItemStack itemInHand = captain.getInventory().getItemInMainHand();
			
		if (itemInHand.getItemMeta().getDisplayName().equals("Cannon Explosive Mode")) {
				
			if (explosiveMode) {
					
				explosiveMode = false;
					
				captain.sendMessage(ChatColor.RED + "Cannon explosive mode has been disabled");
					
			} else {
					
				explosiveMode = true;
					
				captain.sendMessage(ChatColor.GREEN + "Cannon explosive mode has been enabled");
					
			}

		}
			
	}
	
	public boolean rightClickControls() {
		
		ItemStack itemInHand = captain.getInventory().getItemInMainHand();
		
		if (itemInHand.getType().equals(Material.WATCH)) {
			
			if (itemInHand.getItemMeta().getDisplayName().equals("Accelerator")) {
				
				accelerate(1);
				
			} else if (itemInHand.getItemMeta().getDisplayName().equals("Decelerator")) {
					
				decelerate(1);
					
			}
			
		} else if (itemInHand.getType().equals(Material.COMPASS)) {
			
			toggleLock();
			
		} else if (itemInHand.getType().equals(Material.WOOD_DOOR)) {

			exit();
				
		} else if (itemInHand.getType().equals(Material.SULPHUR)) {
			
			toggleExplosive();
			
		} else {
		
			
			return true;
			
		}
		
		return false;
		
	}
	
	public void leftClickControls() {
		
		ItemStack itemInHand = captain.getInventory().getItemInMainHand();
		
		if (itemInHand.getType().equals(Material.WATCH)) {

			String name = itemInHand.getItemMeta().getDisplayName();
					
			if (name.equals("Main Control Device") || name.equals("Accelerator") || name.equals("Decelerator")) {
						
				fireCannons();
						
			} else if (name.equals("Missile Controler")) {
						
				fireMissles();
						
			}
			
		}
		
	}
	
}
