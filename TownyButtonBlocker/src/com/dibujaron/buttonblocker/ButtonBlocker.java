package com.dibujaron.buttonblocker;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.entity.EntityInteractEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.plugin.java.JavaPlugin;

public class ButtonBlocker extends JavaPlugin implements Listener{
	
	public void onEnable(){
		Bukkit.getPluginManager().registerEvents(this,this);
	}
	
	@EventHandler
	public void onEntityInteract(EntityInteractEvent event){
		if(event.getBlock().getType() == Material.WOOD_BUTTON){
			if(event.getEntity() instanceof Arrow){
				Arrow a = (Arrow) event.getEntity();
				if(a.getShooter() instanceof Player){
					Player p = (Player) a.getShooter();
					if(!canBuild(p, event.getBlock())){
						event.setCancelled(true);
					}
				}
			}
			
		}
	}
	
	public void onPlayerInteract(PlayerInteractEvent event){
		if(event.getAction() == Action.RIGHT_CLICK_BLOCK){
			if(event.getClickedBlock().getType() == Material.WOOD_BUTTON){
				if(!canBuild(event.getPlayer(), event.getClickedBlock())){
					event.setCancelled(true);
				}
			}
		}
	}
	
	private boolean canBuild(Player p, Block b){
		BlockBreakEvent fake = new BlockBreakEvent(b, p);
		Bukkit.getServer().getPluginManager().callEvent(fake);
		return !fake.isCancelled();
	}
}
