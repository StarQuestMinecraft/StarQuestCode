package com.burkentertainment.SQPlanet;

import org.bukkit.plugin.java.JavaPlugin;

public class SQPlanetPlugin extends JavaPlugin {

	public void onLoad() {
		try {
            saveDefaultConfig();
		}
		catch (Exception e){
			e.printStackTrace();
		}
	}
	
	public void onEnable() {
		try {
			PlanetSettings.getInstance().loadSettings(getConfig());
			
			BetterRecipes.addAllRecipes(getConfig());

			this.getCommand("SQPlanet").setExecutor(new SQPlanetCommand(this));
			// Event Listeners
			getServer().getPluginManager().registerEvents(new EntityListener(this), this);
			//getServer().getPluginManager().registerEvents(new PlayerListener(this), this);
			//getServer().getPluginManager().registerEvents(new ChunkListener(this), this);
	
		}
		catch (Exception e){
			e.printStackTrace();
		}

	}
	
	public void onDisable(){
		
		BetterRecipes.removeAllRecipes();
		
//		World w = Bukkit.getWorld(Bukkit.getServerName());
//		List<EntityType> types = getAcceptableHostileTypes(w);
//		ArrayList<Entity> removalQueue = new ArrayList<Entity>();
//		for(Entity e : w.getEntities()){
//			if(types.contains(e.getType())){
//				removalQueue.add(e);
//			}
//		}
//		System.out.println("Removing " + removalQueue.size() + " entities!");
//		for(Entity e : removalQueue){
//			e.remove();
//		}
	}

//	public Block getRealHighestBlockAt(Location location){
//		Location location2 = location.getWorld().getHighestBlockAt(location).getLocation();
//		Location location3 = new Location (location2.getWorld(), location2.getX(), location2.getY() - 1, location2.getZ());
//		return location3.getBlock();
//	}
	
}
