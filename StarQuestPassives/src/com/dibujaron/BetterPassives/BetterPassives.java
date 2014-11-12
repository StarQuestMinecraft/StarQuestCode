package com.dibujaron.BetterPassives;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.metadata.MetadataValue;
import org.bukkit.permissions.Permission;
import org.bukkit.permissions.PermissionDefault;
import org.bukkit.plugin.java.JavaPlugin;

public class BetterPassives extends JavaPlugin {

	public List<EntityType> passiveEntityTypes = new ArrayList<EntityType>();
	public List<EntityType> kelakariaPassiveTypes = new ArrayList<EntityType>();
	public List<EntityType> boskevinePassiveTypes = new ArrayList<EntityType>();
	public List<EntityType> iffrizarPassiveTypes = new ArrayList<EntityType>();
	//public List<EntityType> ceharramPassiveTypes = new ArrayList<EntityType>();
	public List<EntityType> boletarianPassiveTypes = new ArrayList<EntityType>();
	//public List<EntityType> quavaraPassiveTypes = new ArrayList<EntityType>();
	public List<EntityType> valadroPassiveTypes = new ArrayList<EntityType>();
	public List<EntityType> krystallosPassiveTypes = new ArrayList<EntityType>();
	public List<EntityType> emeraPassiveTypes = new ArrayList<EntityType>();
	//public List<EntityType> drakosPassiveTypes = new ArrayList<EntityType>();
	public List<EntityType> acualisPassiveTypes = new ArrayList<EntityType>();
	
	public List<EntityType> hostileEntityTypes = new ArrayList<EntityType>();
	public List<EntityType> kelakariaHostileTypes = new ArrayList<EntityType>();
	public List<EntityType> boskevineHostileTypes = new ArrayList<EntityType>();
	public List<EntityType> iffrizarHostileTypes = new ArrayList<EntityType>();
	public List<EntityType> ceharramHostileTypes = new ArrayList<EntityType>();
	//public List<EntityType> boletarianHostileTypes = new ArrayList<EntityType>();
	public List<EntityType> quavaraHostileTypes = new ArrayList<EntityType>();
	public List<EntityType> valadroHostileTypes = new ArrayList<EntityType>();
	public List<EntityType> inarisHostileTypes = new ArrayList<EntityType>();
	public List<EntityType> krystallosHostileTypes = new ArrayList<EntityType>();
	public List<EntityType> emeraHostileTypes = new ArrayList<EntityType>();
	public List<EntityType> drakosHostileTypes = new ArrayList<EntityType>();
	public List<EntityType> acualisHostileTypes = new ArrayList<EntityType>();
	
	
	public List<String> cowNames = new ArrayList<String>();
	public List<String> pigNames = new ArrayList<String>();
	public List<String> wolfNames = new ArrayList<String>();
	public List<String> chickenNames = new ArrayList<String>();
	public List<String> sheepNames = new ArrayList<String>();
	public List<String> ocelotNames = new ArrayList<String>();
	public List<String> mooshroomNames = new ArrayList<String>();

	public void onEnable() {
		getServer().getPluginManager().registerEvents(new PlayerListener(this), this);
		getServer().getPluginManager().registerEvents(new EntityListener(this), this);
		getServer().getPluginManager().registerEvents(new ChunkListener(this), this);

		saveDefaultConfig();

		passiveEntityTypes.add(EntityType.CHICKEN);
		passiveEntityTypes.add(EntityType.COW);
		passiveEntityTypes.add(EntityType.PIG);
		passiveEntityTypes.add(EntityType.SHEEP);
		passiveEntityTypes.add(EntityType.WOLF);
		passiveEntityTypes.add(EntityType.OCELOT);
		passiveEntityTypes.add(EntityType.MUSHROOM_COW);
		
		kelakariaPassiveTypes.add(EntityType.CHICKEN);
		
		valadroPassiveTypes.add(EntityType.COW);
		valadroPassiveTypes.add(EntityType.PIG);
		
		boskevinePassiveTypes.add(EntityType.OCELOT);
		
		iffrizarPassiveTypes.add(EntityType.SHEEP);
		iffrizarPassiveTypes.add(EntityType.WOLF);
		iffrizarPassiveTypes.add(EntityType.COW);
		
		boletarianPassiveTypes.add(EntityType.MUSHROOM_COW);
		
		emeraPassiveTypes.add(EntityType.PIG);
		
		krystallosPassiveTypes.add(EntityType.CHICKEN);
		
		acualisPassiveTypes.add(EntityType.SHEEP);
		
		hostileEntityTypes.add(EntityType.ENDERMAN);
		hostileEntityTypes.add(EntityType.PIG_ZOMBIE);
		hostileEntityTypes.add(EntityType.BLAZE);
		hostileEntityTypes.add(EntityType.CREEPER);
		hostileEntityTypes.add(EntityType.CAVE_SPIDER);
		hostileEntityTypes.add(EntityType.MAGMA_CUBE);
		hostileEntityTypes.add(EntityType.SKELETON);
		hostileEntityTypes.add(EntityType.SLIME);
		hostileEntityTypes.add(EntityType.SPIDER);
		hostileEntityTypes.add(EntityType.ZOMBIE);
		
		kelakariaHostileTypes.add(EntityType.SLIME);
		
		valadroHostileTypes.add(EntityType.CREEPER);
		valadroHostileTypes.add(EntityType.SKELETON);
		
		boskevineHostileTypes.add(EntityType.SPIDER);
		
		iffrizarHostileTypes.add(EntityType.ENDERMAN);
		
		ceharramHostileTypes.add(EntityType.PIG_ZOMBIE);
		ceharramHostileTypes.add(EntityType.BLAZE);
		
		quavaraHostileTypes.add(EntityType.SILVERFISH);
		quavaraHostileTypes.add(EntityType.ZOMBIE);
		
		inarisHostileTypes.add(EntityType.SKELETON);
		
		drakosHostileTypes.add(EntityType.MAGMA_CUBE);
		
		emeraHostileTypes.add(EntityType.ZOMBIE);
		
		acualisHostileTypes.add(EntityType.CREEPER);
		
		krystallosHostileTypes.add(EntityType.SKELETON);
		
		ocelotNames.add("Ocelot");
		mooshroomNames.add("Mooshroom");
		
		cowNames = getConfig().getStringList("cowNames");
		sheepNames = getConfig().getStringList("sheepNames");
		wolfNames = getConfig().getStringList("pigNames");
		chickenNames = getConfig().getStringList("chickenNames");
		pigNames = getConfig().getStringList("pigNames");
				

	}
	
	public void onDisable(){
		World w = Bukkit.getWorld(Bukkit.getServerName());
		List<EntityType> types = getAcceptableHostileTypes(w);
		ArrayList<Entity> removalQueue = new ArrayList<Entity>();
		for(Entity e : w.getEntities()){
			if(types.contains(e.getType())){
				removalQueue.add(e);
			}
		}
		System.out.println("Removing " + removalQueue.size() + " entities!");
		for(Entity e : removalQueue){
			e.remove();
		}
	}

	public void setMetadata(Player player, String key, Object value) {
		player.setMetadata(key, new FixedMetadataValue(this, value));
	}

	public Object getMetadata(Player player, String key) {
		List<MetadataValue> values = player.getMetadata(key);
		for (MetadataValue value : values) {
			if (value.getOwningPlugin().getDescription().getName().equals(this.getDescription().getName())) {
				return value.value();
			}
		}
		return null;
	}
	public ItemStack getTameItem(Entity entity){
		EntityType type = entity.getType();
		
		if (type == EntityType.PIG) return new ItemStack(Material.CARROT_ITEM);
		if (type == EntityType.COW || type == EntityType.SHEEP || type == EntityType.MUSHROOM_COW) return new ItemStack(Material.WHEAT);
		if (type == EntityType.WOLF) return new ItemStack(Material.BONE);
		if (type == EntityType.CHICKEN) return new ItemStack(Material.SEEDS);
		if (type == EntityType.OCELOT) return new ItemStack(Material.RAW_FISH);
		return null;
	}
	public String getRandomName(Entity entity){
		EntityType type = entity.getType();
		
		if (type == EntityType.PIG) return pigNames.get((int) (pigNames.size() * Math.random()));
		if (type == EntityType.COW || type == EntityType.MUSHROOM_COW) return cowNames.get((int) (cowNames.size() * Math.random()));
		if (type == EntityType.SHEEP) return sheepNames.get((int) (sheepNames.size() * Math.random()));
		if (type == EntityType.WOLF) return wolfNames.get((int) (wolfNames.size() * Math.random()));
		if (type == EntityType.CHICKEN) return chickenNames.get((int) (chickenNames.size() * Math.random()));
		if (type == EntityType.OCELOT) return wolfNames.get((int) (wolfNames.size() * Math.random()));
		return null;
		
	}
	public Block getRealHighestBlockAt(Location location){
		Location location2 = location.getWorld().getHighestBlockAt(location).getLocation();
		Location location3 = new Location (location2.getWorld(), location2.getX(), location2.getY() - 1, location2.getZ());
		return location3.getBlock();
	}
	public List<EntityType> getAcceptableTypes(World world){
		String worldname = world.getName();
		switch(worldname.toLowerCase()){
		case "quavara":
		case "ceharram":
		case "space":
		case "inaris":
		case "asteroidbelt":
		case "drakos":
			return null;
		case "iffrizar":
			return iffrizarPassiveTypes;
		case "boletarian":
			return boletarianPassiveTypes;
		case "boskevine":
			return boskevinePassiveTypes;
		case "kelakaria":
			return kelakariaPassiveTypes;
		case "valadro":
			return valadroPassiveTypes;
		case "krystallos":
			return krystallosPassiveTypes;
		case "emera":
			return emeraPassiveTypes;
		case "acualis":
			return acualisPassiveTypes;
		default:
			return passiveEntityTypes;
		}
	}
	public List<EntityType> getAcceptableHostileTypes(World world){
		String worldname = world.getName();
		switch(worldname.toLowerCase()){
		case "quavara":
			return quavaraHostileTypes;
		case "ceharram":
			return ceharramHostileTypes;
		case "iffrizar":
			return iffrizarHostileTypes;
		case "boletarian":
		case "space":
		case "asteroidbelt":
			return null;
		case "boskevine":
			return boskevineHostileTypes;
		case "kelakaria":
			return kelakariaHostileTypes;
		case "valadro":
			return valadroHostileTypes;
		case "inaris":
		    return inarisHostileTypes;
		case "acualis":
			return acualisHostileTypes;
		case "emera":
			return emeraHostileTypes;
		case "krystallos":
			return krystallosHostileTypes;
		case "drakos":
			return drakosHostileTypes;
		default:
			return hostileEntityTypes;
		}
	}
}
