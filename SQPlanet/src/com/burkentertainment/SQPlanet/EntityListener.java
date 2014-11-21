package com.burkentertainment.SQPlanet;
//
//import java.util.Collections;
//import java.util.LinkedList;
//import java.util.List;
//import java.util.Queue;
//
//import org.bukkit.Location;
//import org.bukkit.Material;
//import org.bukkit.entity.Creeper;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Monster;
//import org.bukkit.entity.EntityType;
//import org.bukkit.entity.LivingEntity;
//import org.bukkit.entity.Skeleton;
//import org.bukkit.entity.Skeleton.SkeletonType;
//import org.bukkit.entity.Zombie;
import org.bukkit.World;
import org.bukkit.block.Biome;
import org.bukkit.entity.Entity;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.CreatureSpawnEvent;
import org.bukkit.event.entity.CreatureSpawnEvent.SpawnReason;
import org.bukkit.metadata.FixedMetadataValue;
//import org.bukkit.event.entity.EntityDeathEvent;
//import org.bukkit.inventory.EntityEquipment;
//import org.bukkit.inventory.ItemStack;
//import org.bukkit.potion.PotionEffect;
//import org.bukkit.potion.PotionEffectType;
//
public class EntityListener implements Listener {
	SQPlanetPlugin p;

	EntityListener(SQPlanetPlugin plugin) {
		p = plugin;
	}
//	
//	Queue<Entity> hostileQueue = new LinkedList<Entity>();
//	
	@EventHandler
	public void onEntitySpawn(CreatureSpawnEvent event) {
		if (event.isCancelled())
			return;
		
		// Don't mess with other Plugins or Spawn Eggs
		// REMEMBER: Our own Mod throws CUSTOM spawn events when we cancel the old spawn 
		//           and schedule a new one in this handler
		if ((SpawnReason.CUSTOM == event.getSpawnReason()) ||
			(SpawnReason.DISPENSE_EGG == event.getSpawnReason()))
			return;
		
		if (SpawnReason.BREEDING == event.getSpawnReason()) {
			event.getEntity().setMetadata("domesticated", new FixedMetadataValue(p, "true"));
			// BetterPassives used to assign a CustomName here on passives
			// TODO: Do over-crowding checks
		}
		
		// If a hostile tried to spawn, change it to a locally acceptable hostile
		if (event.getEntity() instanceof Monster) {
			// If this is not one of the Monsters(Hostile) that is allowed here, change it to one that is
		}
		
		if (PlanetSettings.getInstance().isAllowed(event)) {
			PlanetSettings.getInstance().applyPlanetChanges(event.getEntity());
		} else {
			event.setCancelled(true);
		}
		
//		} else if (event.getSpawnReason() != SpawnReason.CUSTOM) {
//			if (!event.isCancelled()) {
//				List<EntityType> types = p.getAcceptableHostileTypes(event.getEntity().getWorld());
//				if (types == null) {
//					event.setCancelled(true);
//					return;
//				}
//				if (types.size() == 0) {
//					event.setCancelled(true);
//					return;
//				}
//				if (types.contains(event.getEntity().getType()) && !event.getEntity().getWorld().getName().equalsIgnoreCase("Ceharram")){
//					hostileQueue.add(event.getEntity());
//					if(hostileQueue.size() > MAX_HOSTILES){
//						hostileQueue.remove().remove();
//					}
//					return;
//				}
//					
//
//				Collections.shuffle(types);
//				EntityType type = types.get(0);
//				Entity e = event.getEntity().getWorld().spawnEntity(event.getEntity().getLocation(), type);
//				String n = e.getWorld().getName().toLowerCase();
//				if (e.getType() == EntityType.SKELETON && n.equals("inaris")) {
//					Skeleton s = (Skeleton) e;
//					s.setSkeletonType(SkeletonType.WITHER);
//				} else if (e.getType() == EntityType.CREEPER && n.equals("acualis")) {
//					Creeper c = (Creeper) e;
//					c.setPowered(true);
//					permaVanish(c);
//				} else if (e.getType() == EntityType.SKELETON && n.equals("krystallos")) {
//					createRobot((Skeleton) e);
//				} else if (e.getType() == EntityType.ZOMBIE && n.equals("emera")) {
//					createSpiritBlock((Zombie) e);
//				}
//				event.setCancelled(true);
//				hostileQueue.add(e);
//				if(hostileQueue.size() > MAX_HOSTILES){
//					hostileQueue.remove().remove();
//				}
//			}
//		}
	}
//
//	private void createRobot(Skeleton s) {
//		EntityEquipment e = s.getEquipment();
//		e.setHelmet(new ItemStack(Material.DISPENSER, 1));
//		e.setChestplate(new ItemStack(Material.IRON_CHESTPLATE, 1));
//		e.setLeggings(new ItemStack(Material.CHAINMAIL_LEGGINGS, 1));
//		e.setLeggings(new ItemStack(Material.IRON_BOOTS, 1));
//		e.setItemInHand(new ItemStack(Material.BOW, 1));
//		permaVanish(s);
//	}
//
//	private void createSpiritBlock(Zombie z) {
//		permaVanish(z);
//		z.getEquipment().setHelmet(new ItemStack(getHighestBlockBeneath(z.getLocation()).getBlock().getType(), 1));
//	}
//
//	private void permaVanish(LivingEntity c) {
//		c.addPotionEffect(new PotionEffect(PotionEffectType.INVISIBILITY, Integer.MAX_VALUE, 1));
//	}
//
//	public static Location getHighestBlockBeneath(Location loc) {
//		if (loc.getWorld().getBlockAt(loc).getType() == Material.AIR) {
//			return getHighestBlockBeneath(new Location(loc.getWorld(), loc.getX(), loc.getY() - 1, loc.getZ()));
//		} else
//			return loc;
//	}
//	@EventHandler(priority = EventPriority.HIGHEST)
//	public void onEntityDeath(EntityDeathEvent event){
//		if(event.getEntityType() == EntityType.SKELETON){
//			Skeleton s = (Skeleton) event.getEntity();
//			if(s.getSkeletonType() == SkeletonType.WITHER){
//				if(Math.random() < 0.3){
//					event.getDrops().add(new ItemStack(372, 1));
//				}
//			}
//		}
//	}
}
