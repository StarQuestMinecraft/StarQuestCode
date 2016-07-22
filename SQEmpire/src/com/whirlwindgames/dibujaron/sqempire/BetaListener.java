package com.whirlwindgames.dibujaron.sqempire;

import net.countercraft.movecraft.event.CraftServerJumpEvent;

import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.PotionSplashEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.projectiles.ProjectileSource;

import com.whirlwindgames.dibujaron.sqempire.database.object.EmpirePlayer;
import com.whirlwindgames.dibujaron.sqempire.util.EffectUtils;
import com.whirlwindgames.dibujaron.sqempire.util.EmpirePlanetTempUtils;

public class BetaListener implements Listener{
	
	@EventHandler
	public void onEntityDamageEntity(EntityDamageByEntityEvent event){
		if(event.getEntity() instanceof Player){
			Player damaged = (Player) event.getEntity();
			Player attacker = null;
			if(event.getDamager() instanceof Player){
				attacker = (Player) event.getDamager();
			} else if (event.getDamager() instanceof Projectile){
				ProjectileSource source = ((Projectile) event.getDamager()).getShooter();
				if(source instanceof Player){
					attacker = (Player) source;
				}
				((Projectile) event.getDamager()).setFireTicks(0);
			}
			if(attacker != null){
				EmpirePlayer d = EmpirePlayer.getOnlinePlayer(damaged);
				EmpirePlayer a = EmpirePlayer.getOnlinePlayer(attacker);
				if(d.getEmpire() == a.getEmpire()){
					event.setCancelled(true);
					
					if (event.getDamager() instanceof Projectile){
						((Projectile) event.getDamager()).setFireTicks(0);
					}
					
					attacker.sendMessage(damaged.getName() + " is a member of your empire, you cannot damage them.");
				}
			}
		}
	}
	
	@EventHandler
	public void onPotionSplash(PotionSplashEvent event) {
		
		boolean bad = false;
		
		for (PotionEffect effect : event.getPotion().getEffects()) {
			
			if (EffectUtils.isBadEffect(effect.getType())) {
				
				bad = true;
				
			}
			
		}
		
		if (bad) {
			
			ProjectileSource shooter = event.getPotion().getShooter();
			
			if (shooter instanceof Player) {
				
				EmpirePlayer ep = EmpirePlayer.getOnlinePlayer((Player) shooter);
				
				for (LivingEntity entity : event.getAffectedEntities()) {
					
					if (entity instanceof Player) {
						
						EmpirePlayer player = EmpirePlayer.getOnlinePlayer((Player) entity);
						
						if (ep.getEmpire().equals(player.getEmpire())) {
							
							event.setIntensity(entity, 0);
							
						}
						
					}
					
				}
				
			}
			
		}
		
	}
	
	@EventHandler
	public void onCraftServerJump(CraftServerJumpEvent event){
		EmpirePlayer p = EmpirePlayer.getOnlinePlayer(event.getCraft().pilot);
		Empire e = p.getEmpire();
		if(EmpirePlanetTempUtils.isTargetEnemyRim(event.getTargetServer(), e)){
			event.setCancelled(true);
		}
	}
	
}
