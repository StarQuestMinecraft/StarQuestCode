package com.dibujaron.skywatch;

import net.countercraft.movecraft.event.CraftProjectileDetonateEvent;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.event.Cancellable;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

import com.starquestminecraft.sqcontracts.SQContracts;
import com.starquestminecraft.sqcontracts.contracts.Contract;
import com.starquestminecraft.sqcontracts.contracts.ShipCaptureContract;
import com.starquestminecraft.sqcontracts.database.ContractPlayerData;
import com.starquestminecraft.sqcontracts.database.Database;

public class PlayerTriggerHandler implements Listener{
		
	@EventHandler
	public void onPlayerHit(EntityDamageByEntityEvent event){
		System.out.println("Entity hit");
		if(event.getDamager() instanceof Player && event.getEntity() instanceof Player){
			System.out.println("Player hit!");
			//it's pvp
			triggerCheck((Player) event.getDamager(), (Player) event.getEntity(), event, event.getDamage());
		}
		if(event.getDamager() instanceof Projectile){
			Projectile p = (Projectile) event.getDamager();
			if(p.getShooter() instanceof Player){
				triggerCheck((Player) p.getShooter(), (Player) event.getEntity(), event, event.getDamage());
			}
		}
	}
	
	@EventHandler
	public void onCraftShoot(CraftProjectileDetonateEvent event){
		System.out.println("Craft bullet hit");
		Player closest = findClosestPlayer(event.getExplosionBlock());
		if(closest == event.getShooter()) return;
		double dist = distanceSquared(event.getExplosionBlock().getLocation(), closest.getLocation());
		if(dist < 20 * 20){
			System.out.println("Close player, triggering!");
			triggerCheck(event.getShooter(), closest, event, 4D);
		}
	}

	private void triggerCheck(final Player damager, final Player damaged, Cancellable event, final double damage) {
		SkywatchStatus s = SkywatchStatus.statusOf(damager.getUniqueId());
		if(s == SkywatchStatus.WARN_SHORT){
			damager.sendMessage("PvP blocked by Skywatch lock.");
			event.setCancelled(true);
			return;
		}
		Bukkit.getScheduler().runTaskAsynchronously(SQSkywatch.getInstance(), new Runnable(){
			public void run(){
				triggerCheckAsync(damager, damaged, damage);
			}
		});
	}
	
	private void triggerCheckAsync(Player damager, Player damaged, final double damage){
		if(shouldTriggerOnDamager(damager, damaged)){
			SkywatchStatus s = SkywatchStatus.statusOf(damager.getUniqueId());
			if(!skywatchActive(s)){
				if(damage <= 6 && s != SkywatchStatus.WARN_LONG){
					SQSkywatch.getInstance().setWarning(damager);
				} else {
					SQSkywatch.getInstance().triggerSkywatch(damager);
				}
			}
		}
	}
	
	private boolean skywatchActive(SkywatchStatus s){
		switch(s){
			case EN_ROUTE:
			case FIGHTERS:
			case ALL:
				return true;
			default:
				return false;
		}
	}
	
	private boolean shouldTriggerOnDamager(Player damager, Player damaged){
		Database d = SQContracts.get().getContractDatabase();
		ContractPlayerData damagerData = d.getDataOfPlayer(damager.getUniqueId());
		ContractPlayerData damagedData = d.getDataOfPlayer(damaged.getUniqueId());
		boolean isDamagerPrivateer = isPrivateer(damagerData);
		boolean isDamagedPrivateeer = isPrivateer(damagedData);
		boolean isDamagerWanted = damagerData.isWanted();
		boolean isDamagedWanted = damagedData.isWanted();
		
		if(isDamagerPrivateer && isDamagedWanted){
			System.out.println("Should not trigger- damager is privateer and damagee is wanted!");
			return false;
		}
		if(isDamagerWanted && isDamagedPrivateeer){
			System.out.println("Should not trigger- damager is wanted and damagee is privateer!");
			return false;
		}
		return true;
	}
	
	private boolean isPrivateer(ContractPlayerData d) {
		for(Contract c : d.getContracts()){
			if(c instanceof ShipCaptureContract && !c.isBlackMarket()){
				return true;
			}
		}
		return false;
	}

	private Player findClosestPlayer(Block center){
		Player closest = null;
		double closestDistanceSquared = Double.MAX_VALUE;
		for(Player p : center.getWorld().getPlayers()){
			double dist = distanceSquared(center.getLocation(), p.getLocation());
			if(dist < closestDistanceSquared){
				closest = p;
				closestDistanceSquared = dist;
			}
		}
		return closest;
	}
	
	private double distanceSquared(Location a, Location b){
		double dx = a.getX() - b.getX();
		double dy = a.getY() - b.getY();
		double dz = a.getZ() - b.getZ();
		
		return dx * dx + dy * dy + dz * dz;
	}
 
}
