package com.starquestminecraft.SQHungerTweak;

import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.plugin.java.JavaPlugin;

public class SQHungerTweak extends JavaPlugin implements Listener{
	
	public void onEnable(){
		getServer().getPluginManager().registerEvents(this, this);
	}
	
	@EventHandler (ignoreCancelled = true)
	public void onEntityDamage(EntityDamageByEntityEvent event){
		if(event.getEntityType() == EntityType.PLAYER && event.getDamager().getType() == EntityType.PLAYER){
			Player defender = (Player) event.getEntity();
			defender.setFoodLevel(defender.getFoodLevel() - 1);
		}
	}
}
