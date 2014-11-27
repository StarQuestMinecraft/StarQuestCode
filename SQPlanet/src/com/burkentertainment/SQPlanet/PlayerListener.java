//package com.burkentertainment.SQPlanet;
//
//import java.util.ArrayList;
//
//import org.bukkit.ChatColor;
//import org.bukkit.Effect;
//import org.bukkit.Material;
//import org.bukkit.entity.EntityType;
//import org.bukkit.entity.LivingEntity;
//import org.bukkit.entity.Player;
//import org.bukkit.event.EventHandler;
//import org.bukkit.event.Listener;
//import org.bukkit.event.player.AsyncPlayerChatEvent;
//import org.bukkit.event.player.PlayerInteractEntityEvent;
//
//public class PlayerListener implements Listener{
//	SQPlanetPlugin p;
//	
//	ArrayList<Player> editingPlayers = new ArrayList<Player>();
//	
//	PlayerListener(SQPlanetPlugin plugin){
//		p = plugin;
//	}
//	
//	@EventHandler
//	public void onPlayerInteractEntity(PlayerInteractEntityEvent event){
//		
//		//is the targeted entity a passive?
//		if (!(p.passiveEntityTypes.contains(event.getRightClicked().getType()))){
//			if (!(event.getRightClicked().getType() == EntityType.MUSHROOM_COW)){
//				if (!(event.getRightClicked().getType() == EntityType.OCELOT)){
//					return;
//				}
//			}
//		}
//		
//		//is it the renaming item?
//		if ((event.getPlayer().getItemInHand().getType() == Material.IRON_HOE)){
//			
//			
//			//is it tamed?
//			LivingEntity entity = (LivingEntity) event.getRightClicked();
//			if (entity.getCustomName() == null) return;
//
//			//okay, then enter the name-editing mode.
//			event.getPlayer().sendMessage(ChatColor.GREEN + "**********************************");
//			event.getPlayer().sendMessage(ChatColor.YELLOW + "Type the entity's new name");
//			event.getPlayer().sendMessage(ChatColor.GREEN + "**********************************");
//			editingPlayers.add(event.getPlayer());
//			p.setMetadata(event.getPlayer(), "target", event.getRightClicked());
//		}
//		
//
//		//is it the tame item?
//		if (p.getTameItem(event.getRightClicked()).isSimilar(event.getPlayer().getItemInHand())){
//			
//			//is it already tamed?
//			LivingEntity entity = (LivingEntity) event.getRightClicked();
//			if (entity.getCustomName() != null) return;
//			
//			//Then GO!
//			entity.getWorld().playEffect(entity.getLocation(), Effect.SMOKE, 20);
//			event.getPlayer().sendMessage(ChatColor.YELLOW + "Tamed!");
//			String name = p.getRandomName(entity);
//			entity.setCustomName(name);
//			entity.setCustomNameVisible(true);
//			event.setCancelled(true);
//		}
//	}
//	@EventHandler
//	public void onPlayerChat(AsyncPlayerChatEvent event){
//		
//		//return if player not editing
//		if (!(editingPlayers.contains(event.getPlayer()))) return;
//		
//		//get the mob;
//		LivingEntity entity = (LivingEntity) p.getMetadata(event.getPlayer(), "target");
//		
//		//set its name
//		entity.setCustomName(event.getMessage());
//		entity.setCustomNameVisible(true);
//		
//		//send the message to the player and remove from editing mode.
//		event.getPlayer().sendMessage(ChatColor.YELLOW + "Entity's name changed");
//		editingPlayers.remove(event.getPlayer());
//		event.setCancelled(true);
//	}
//}
