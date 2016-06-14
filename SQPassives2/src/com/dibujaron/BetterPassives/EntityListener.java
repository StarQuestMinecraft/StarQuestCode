package com.dibujaron.BetterPassives;

import java.util.Arrays;
import java.util.List;
import java.util.Random;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Creeper;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Ghast;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Skeleton;
import org.bukkit.entity.Zombie;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.CreatureSpawnEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.entity.EntitySpawnEvent;
import org.bukkit.inventory.EntityEquipment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class EntityListener implements Listener {
	BetterPassives p;

	EntityListener(BetterPassives plugin) {
		this.p = plugin;
	}

	private static final List<CreatureSpawnEvent.SpawnReason> PASSTHROUGH_REASONS = Arrays
			.asList(new CreatureSpawnEvent.SpawnReason[] { CreatureSpawnEvent.SpawnReason.CUSTOM,
					CreatureSpawnEvent.SpawnReason.SLIME_SPLIT, CreatureSpawnEvent.SpawnReason.BUILD_IRONGOLEM,
					CreatureSpawnEvent.SpawnReason.BUILD_SNOWMAN, CreatureSpawnEvent.SpawnReason.SPAWNER_EGG,
					CreatureSpawnEvent.SpawnReason.SILVERFISH_BLOCK });

	@EventHandler
	public void onEntitySpawn(CreatureSpawnEvent event) {
		if (event.isCancelled()) {
			return;
		}
		List<EntityType> passives = Settings.getPassivesOfPlanet(event.getEntity().getWorld().getName());
		List<EntityType> hostiles = Settings.getHostilesOfPlanet(event.getEntity().getWorld().getName());
		
		if(hostiles == null)
		{
			event.setCancelled(true);
			return;
		}
		
		if (event.getEntity().getType() == EntityType.SQUID) {
			if (passives != null && passives.contains(EntityType.SQUID)) {
				return;
			} else {
				event.setCancelled(true);
				return;
			}
		}

		if ((event.getEntity().getType() == EntityType.WITHER)
				|| (event.getEntity().getType() == EntityType.WITHER_SKULL
						|| event.getEntity().getType() == EntityType.ARMOR_STAND))
		{
			if(event.getEntity().getType() == EntityType.WITHER)
			{
			    event.setCancelled(true);
			}
			return;
		}
		if (event.getSpawnReason() == CreatureSpawnEvent.SpawnReason.BREEDING) {
			checkAndRemoveTooManyEntities(event, passives);
			event.getEntity().setCustomName(this.p.getRandomName(event.getEntity()));
			event.getEntity().setCustomNameVisible(true);
		} else if (event.getSpawnReason() == CreatureSpawnEvent.SpawnReason.EGG) {
			if (passives == null || !passives.contains(EntityType.CHICKEN)) {
				event.setCancelled(true);
			}
		} else if ((!PASSTHROUGH_REASONS.contains(event.getSpawnReason())) && (!event.isCancelled())) {
			if(this.getNumberOfHostiles(event, hostiles) > Settings.getHostilesPerChunk()) // Too many in one chunk!
			{
				event.setCancelled(true);
				return;
			}
			if (FactionUtils.isInClaimedLand(event.getLocation())) {
				event.setCancelled(true);
				return;
			}
			List<EntityType> types = this.p.getAcceptableHostileTypes(event.getLocation().getWorld());
			if (types == null) {
				event.setCancelled(true);
				return;
			}
			if (types.size() == 0) {
				event.setCancelled(true);
				return;
			}
			
			EntityType type = types.get((int) (Math.random() * types.size()));
			//System.out.println(type);
			// Ghast spawning
			if(type == EntityType.GHAST)
			{
				//System.out.println("Entity Loc: " + event.getEntity().getLocation());
				Ghast g = (Ghast) event.getLocation().getWorld().spawn(event.getEntity().getLocation().add(0, 25, 0), Ghast.class);
				g.setAI(true);
				g.setCollidable(true);
				//System.out.println("Ghast loc: " + g.getLocation());
				event.setCancelled(true);
				return;
			}
			
			Entity e = event.getLocation().getWorld().spawnEntity(event.getEntity().getLocation(), type);
			String n = e.getWorld().getName().toLowerCase();
			List<String> specials = Settings.getSpecialPropertiesOfPlanet(n);
			
			if(specials == null)
			{
				event.setCancelled(true);
				return;
			}
			
			if ((e.getType() == EntityType.SKELETON) && (specials.contains("wither skeleton")))
			{
				Skeleton s = (Skeleton) e;
				ItemStack skeletonItems[] = s.getEquipment().getArmorContents();
				s.setSkeletonType(Skeleton.SkeletonType.WITHER);
				s.getEquipment().setArmorContents(skeletonItems);
			}
			else if ((e.getType() == EntityType.CREEPER) && specials.contains("charged creeper")) {
				Creeper c = (Creeper) e;
				c.setPowered(true);
				permaVanish(c);
			} else if ((e.getType() == EntityType.SKELETON) && (n.equals("uru"))) {
				createRobot((Skeleton) e);
			}
			
			//System.out.println("Entity: " + event.getEntity().getWorld().getEntities());
			event.setCancelled(true);
			return;
		}
		if (Settings.getAllHostiles().contains(event.getEntityType())) {
			int chance = BetterPassives.config.getInt("hostile chance");
			Random rand = new Random();
			if (rand.nextInt(101) > chance) {
				event.setCancelled(true);
			}
		}
	}

	private void createRobot(Skeleton s) {
		EntityEquipment e = s.getEquipment();
		e.setHelmet(new ItemStack(Material.DISPENSER, 1));
		e.setChestplate(new ItemStack(Material.IRON_CHESTPLATE, 1));
		e.setLeggings(new ItemStack(Material.CHAINMAIL_LEGGINGS, 1));
		e.setLeggings(new ItemStack(Material.IRON_BOOTS, 1));
		e.setItemInMainHand(new ItemStack(Material.BOW, 1));
		permaVanish(s);
	}

	@SuppressWarnings("unused")
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
			if ((s.getSkeletonType() == Skeleton.SkeletonType.WITHER) && (Math.random() < 0.3))
			{
				event.getDrops().add(new ItemStack(Material.NETHER_STALK, 1));
			}
		}
	}
	

	private void checkAndRemoveTooManyEntities(EntitySpawnEvent event, List<EntityType> passives) {
		Entity[] cents = event.getEntity().getLocation().getChunk().getEntities();
		int count = 0;
		for (Entity e : cents) {
			if (e instanceof LivingEntity) {
				LivingEntity le = (LivingEntity) e;
				if (passives.contains(e.getType())) {
					if ((le.getCustomName() != null) || le.isCustomNameVisible()) {
						if (count > Settings.getTamedPerChunk()) {
							e.remove();
						} else {
							count++;
						}
					}
				}
			}
		}
	}
	
	private int getNumberOfHostiles(CreatureSpawnEvent e, List<EntityType> hostiles)
	{
		Entity[] entities = e.getEntity().getLocation().getChunk().getEntities();
		int count = 0;
		for (Entity en : entities)
			if(en instanceof LivingEntity)
			{
				count++;
			}
		return count;
	}
}

/*
 * Location: C:\Users\Drew\Desktop\SQPassives.jar Qualified Name:
 * com.dibujaron.BetterPassives.EntityListener JD-Core Version: 0.6.2
 */
