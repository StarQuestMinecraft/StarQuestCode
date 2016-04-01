package com.martinjonsson01.sqsmoothcraft.missile;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.block.Dispenser;
import org.bukkit.block.Sign;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Fireball;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.util.Vector;

import com.ginger_walnut.sqsmoothcraft.SQSmoothCraft;

public class MissileListener implements Listener {
	
	Location currentLoc = null;
	Location targetLoc = null;
	
	@EventHandler
	public void onPlayerInteract(PlayerInteractEvent e) {
		
		if (e.getAction() != Action.RIGHT_CLICK_BLOCK)
			return;
		
		if (e.getClickedBlock().getState() instanceof Sign) {
			
			Sign s = (Sign) e.getClickedBlock().getState();
			if (s.getLine(0).equalsIgnoreCase("[hsmissile]")) {
				if (MissileDetection.detectLauncher(s.getBlock())) {
					s.setLine(0, "");
					s.setLine(1, ChatColor.LIGHT_PURPLE + "[" + ChatColor.GOLD + "Missile" + ChatColor.LIGHT_PURPLE + "]");
					s.setLine(2, ChatColor.LIGHT_PURPLE + "[" + ChatColor.RED + "Heat Seeking" + ChatColor.LIGHT_PURPLE + "]");
					s.setLine(3, "");
					s.update();
					return;
				}
			}
			
			if (s.getLine(1).equals(ChatColor.LIGHT_PURPLE + "[" + ChatColor.GOLD + "Missile" + ChatColor.LIGHT_PURPLE + "]")
					&& s.getLine(2).equals(ChatColor.LIGHT_PURPLE + "[" + ChatColor.RED + "Heat Seeking" + ChatColor.LIGHT_PURPLE + "]")) {
				
				Block ammoDispenserBlock = MissileDetection.getAmmoDispenser(s.getBlock());
				Dispenser ammoDispenser = (Dispenser) ammoDispenserBlock.getState();
				Inventory dispenserInv = ammoDispenser.getInventory();
				
				//Checks if the dispenser of the missile launcher has ammo, if so, then it launches the missile fireball thingy
				if (dispenserInv.containsAtLeast(Missile.missileAmmo(), 1)) {
					
					dispenserInv.removeItem(Missile.missileAmmo()); //Removes one missile ammo from dispenser
					
					Fireball fireball = (Fireball) ammoDispenserBlock.getLocation().getWorld().spawnEntity(MissileDetection.inFrontOfDispenser(s.getBlock()).getLocation(), EntityType.FIREBALL);
					
					fireball.setBounce(false);
					fireball.setShooter(e.getPlayer());
					fireball.getLocation().getYaw();
					fireball.getLocation().getPitch();
					Player p = e.getPlayer();
					
					Bukkit.getScheduler().scheduleSyncRepeatingTask(SQSmoothCraft.getPluginMain(), new Runnable(){
						@Override
						public void run() {
							currentLoc = fireball.getLocation();
							targetLoc = p.getLocation();
							Vector vector = targetLoc.toVector().normalize().subtract(currentLoc.toVector().normalize());
							vector = vector.multiply(.5f);
							fireball.setDirection(vector);
							//fireball.setVelocity(vector);
							
							
						}
					}, 21*2, 5); //21*2 because the this one needs to fire off after the one below does VVV
					
					Bukkit.getScheduler().scheduleSyncDelayedTask(SQSmoothCraft.getPluginMain(), new Runnable(){
						
						@Override
						public void run() {
							
							fireball.setVelocity(new Vector(0, 0, 0));
							fireball.setDirection(new Vector(0, 0, 0));
							
						}
						
					}, 20*2);
					
					Bukkit.getScheduler().scheduleSyncDelayedTask(SQSmoothCraft.getPluginMain(), new Runnable(){
						
						@Override
						public void run() {
							
							if(!fireball.isDead()){
								e.getPlayer().sendMessage(ChatColor.RED + "The fuel of your heat seeking missile ran out.");
							}
							fireball.remove();
						}
						
					}, 20*10);
				}
				
			}
		}
		
	}
	
}
