package com.whirlwindgames.dibujaron.sqempire;

import net.countercraft.movecraft.event.CraftServerJumpEvent;

import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.projectiles.ProjectileSource;

import com.whirlwindgames.dibujaron.sqempire.database.object.EmpirePlayer;
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
			}
			if(attacker != null){
				EmpirePlayer d = EmpirePlayer.getOnlinePlayer(damaged);
				EmpirePlayer a = EmpirePlayer.getOnlinePlayer(attacker);
				if(d.getEmpire() == a.getEmpire()){
					event.setCancelled(true);
					attacker.sendMessage(damaged.getName() + " is a member of your empire, you cannot damage them.");
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
