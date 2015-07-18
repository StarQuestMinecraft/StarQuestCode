package com.dibujaron.BetterPassives;

import java.util.Arrays;
import java.util.List;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Creeper;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Skeleton;
import org.bukkit.entity.Zombie;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.CreatureSpawnEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.inventory.EntityEquipment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class EntityListener implements Listener {
	BetterPassives p;

	EntityListener(BetterPassives plugin) {
		this.p = plugin;
	}

	private static final List<CreatureSpawnEvent.SpawnReason> PASSTHROUGH_REASONS = Arrays.asList(new CreatureSpawnEvent.SpawnReason[] {
			CreatureSpawnEvent.SpawnReason.CUSTOM, CreatureSpawnEvent.SpawnReason.SLIME_SPLIT, CreatureSpawnEvent.SpawnReason.BUILD_IRONGOLEM, 
			CreatureSpawnEvent.SpawnReason.BUILD_SNOWMAN, CreatureSpawnEvent.SpawnReason.SPAWNER_EGG });

	@EventHandler
	public void onEntitySpawn(CreatureSpawnEvent event) {
		if (event.isCancelled()) {
			System.out.println("[SQPassives] Event pre-cancelled.");
			return;
		}
		if (event.getEntity().getType() == EntityType.SQUID) {
			List<EntityType> passives = Settings.getPassivesOfPlanet(event.getEntity().getWorld().getName());
			if(passives != null && passives.contains(EntityType.SQUID)){
				return;
			} else {
				event.setCancelled(true);
				return;
			}
		}

		if ((event.getEntity().getType() == EntityType.WITHER) || (event.getEntity().getType() == EntityType.WITHER_SKULL || event.getEntity().getType() == EntityType.ARMOR_STAND)) {
			System.out.println("[SQPassives] wither/witherskull/armorstand allowed.");
			return;
		}

		if (event.getSpawnReason() == CreatureSpawnEvent.SpawnReason.BREEDING) {
			System.out.println("[SQPassives] breeding!");
			event.getEntity().setCustomName(this.p.getRandomName(event.getEntity()));
			event.getEntity().setCustomNameVisible(true);
		} else if (event.getSpawnReason() == CreatureSpawnEvent.SpawnReason.EGG) {
			String n = event.getEntity().getWorld().getName().toLowerCase();
			if (!(n.equals("nalavor") || n.equals("sampetra"))){
				System.out.println("[SQPassives] egg cancelled, not allowed here!");
				event.setCancelled(true);
			}
		} else if ((!PASSTHROUGH_REASONS.contains(event.getSpawnReason())) && (!event.isCancelled())) {
			List<EntityType> types = this.p.getAcceptableHostileTypes(event.getLocation().getWorld());
			if (types == null) {
				System.out.println("[SQPassives] types null for planet.");
				event.setCancelled(true);
				return;
			}
			if (types.size() == 0) {
				System.out.println("[SQPassives] no hostiles for planet");
				event.setCancelled(true);
				return;
			}

			EntityType type = types.get((int) (Math.random() * types.size()));
			Entity e = event.getLocation().getWorld().spawnEntity(event.getEntity().getLocation(), type);
			String n = e.getWorld().getName().toLowerCase();
			if ((e.getType() == EntityType.SKELETON) && (n.equals("avaquo"))) {
				Skeleton s = (Skeleton) e;
				s.setSkeletonType(Skeleton.SkeletonType.WITHER);
			} else if ((e.getType() == EntityType.CREEPER) && (n.equals("tallimar"))) {
				Creeper c = (Creeper) e;
				c.setPowered(true);
				permaVanish(c);
			} else if ((e.getType() == EntityType.SKELETON) && (n.equals("uru"))) {
				createRobot((Skeleton) e);
			}
			event.setCancelled(true);
		}
	}

	private void createRobot(Skeleton s) {
		EntityEquipment e = s.getEquipment();
		e.setHelmet(new ItemStack(Material.DISPENSER, 1));
		e.setChestplate(new ItemStack(Material.IRON_CHESTPLATE, 1));
		e.setLeggings(new ItemStack(Material.CHAINMAIL_LEGGINGS, 1));
		e.setLeggings(new ItemStack(Material.IRON_BOOTS, 1));
		e.setItemInHand(new ItemStack(Material.BOW, 1));
		permaVanish(s);
	}

	private void createSpiritBlock(Zombie z) {
		permaVanish(z);
		z.getEquipment().setHelmet(new ItemStack(getHighestBlockBeneath(z.getLocation()).getBlock().getType(), 1));
	}

	private void permaVanish(LivingEntity c) {
		c.addPotionEffect(new PotionEffect(PotionEffectType.INVISIBILITY, 2147483647, 1));
	}

	public static Location getHighestBlockBeneath(Location loc) {
		if (loc.getWorld().getBlockAt(loc).getType() == Material.AIR) {
			return getHighestBlockBeneath(new Location(loc.getWorld(), loc.getX(), loc.getY() - 1.0D, loc.getZ()));
		}
		return loc;
	}

	@EventHandler(priority = EventPriority.HIGHEST)
	public void onEntityDeath(EntityDeathEvent event) {
		if (event.getEntityType() == EntityType.SKELETON) {
			Skeleton s = (Skeleton) event.getEntity();
			if ((s.getSkeletonType() == Skeleton.SkeletonType.WITHER) && (Math.random() < 0.3D))
				event.getDrops().add(new ItemStack(372, 1));
		}
	}
}

/*
 * Location: C:\Users\Drew\Desktop\SQPassives.jar Qualified Name:
 * com.dibujaron.BetterPassives.EntityListener JD-Core Version: 0.6.2
 */