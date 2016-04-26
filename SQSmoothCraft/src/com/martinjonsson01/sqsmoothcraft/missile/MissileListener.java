package com.martinjonsson01.sqsmoothcraft.missile;

import org.bukkit.ChatColor;
import org.bukkit.block.Block;
import org.bukkit.block.Dispenser;
import org.bukkit.block.Sign;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.Inventory;

public class MissileListener implements Listener {
	
	
	
	
	
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
				
				//Checks if the dispenser does not have missile ammo, if it does not, then it returns.
				if (!dispenserInv.containsAtLeast(Missile.standardMissileAmmo(), 1) && !dispenserInv.containsAtLeast(Missile.EMPMissileAmmo(), 1)) return;
					
				if (Missile.missileCoolDownList.contains(e.getPlayer())) {
					e.getPlayer().sendMessage(ChatColor.RED + "You are still on cooldown...");
					return;
				}
				
				// Fires the missile
				if (dispenserInv.containsAtLeast(Missile.standardMissileAmmo(), 1)) {
					if (MissileMovement.activateMissile(ammoDispenserBlock, s, e.getPlayer(), "standard")) {
						dispenserInv.removeItem(Missile.standardMissileAmmo());
						// Removes one ammo from the dispenser
					}
					return;
				}
				if (dispenserInv.containsAtLeast(Missile.EMPMissileAmmo(), 1)) {
					if (MissileMovement.activateMissile(ammoDispenserBlock, s, e.getPlayer(), "emp")) {
						dispenserInv.removeItem(Missile.EMPMissileAmmo());
						// Removes one ammo from the dispenser
					}
					return;
				}
				
			}
		}
		
	}
	
}
