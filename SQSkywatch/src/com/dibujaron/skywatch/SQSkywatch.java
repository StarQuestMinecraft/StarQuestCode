package com.dibujaron.skywatch;


import java.util.HashSet;
import java.util.Iterator;
import java.util.UUID;

import net.countercraft.movecraft.utils.EMPUtils;
import net.countercraft.movecraft.utils.LocationUtils;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Effect;
import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Ghast;
import org.bukkit.entity.LargeFireball;
import org.bukkit.entity.Player;
import org.bukkit.entity.Skeleton;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.entity.EntityShootBowEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.entity.ProjectileLaunchEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.util.Vector;

public class SQSkywatch extends JavaPlugin implements Listener {

	static HashSet<DroneFighter> activeFighters = new HashSet<DroneFighter>();
	static HashSet<DroneShocktroop> activeShocktroops = new HashSet<DroneShocktroop>();
	int numFighters;
	int randomRange;
	int responseTime;
	public static int knockbackLevel;
	public static int powerLevel;
	public static int protectionLevel;
	public static boolean outrightKill;
	public static boolean flameBows;
	public static boolean pvpLock;
	
	public static double wins;
	public static double losses;
	public static String system;
	private static SQSkywatch instance;
	public void onEnable() {
		system = LocationUtils.getSystem();
		if(system.equalsIgnoreCase("Trinitos_Gamma")){
			Bukkit.getPluginManager().disablePlugin(this);
			return;
		}
		instance = this;
		getServer().getScheduler().scheduleSyncRepeatingTask(this, new Runnable() {
			public void run() {
				update();
			}
		}, 100L, 100L);
		saveDefaultConfig();
		
		getServer().getPluginManager().registerEvents(this, this);
		getServer().getPluginManager().registerEvents(new PlayerTriggerHandler(), this);
		numFighters = getConfig().getInt("numFighters");
		randomRange = getConfig().getInt("randomRange");
		responseTime = getConfig().getInt("responseTime");
		knockbackLevel = getConfig().getInt("knockbackLevel");
		powerLevel = getConfig().getInt("powerLevel");
		protectionLevel = getConfig().getInt("protectionLevel");
		outrightKill = getConfig().getBoolean("outrightKill");
		flameBows = getConfig().getBoolean("flame");
		pvpLock = getConfig().getBoolean("pvpLock");
		
		if(system.equalsIgnoreCase("Trinitos_Beta")){
			wins = getConfig().getInt("wins");
			losses = getConfig().getInt("losses");
		}
	}
	
	public void onDisable(){
		for(World w : Bukkit.getWorlds()){
			for(Entity e : w.getEntities()){
				if(e.getType() == EntityType.GHAST){
					e.remove();
				}
				if(e.getType() == EntityType.SKELETON){
					if(DroneShocktroop.isPossiblyShocktroop((Skeleton) e)){
						e.remove();
					}
				}
			}
		}
		
		if(system.equalsIgnoreCase("Trinitos_Beta")){
			getConfig().set("wins", "" + ((int) wins));
			getConfig().set("losses", "" + ((int) losses));
			saveConfig();
		}
	}
	
	@EventHandler
	public void onEntityDeath(EntityDeathEvent event){
		Entity e = event.getEntity();
		if(e instanceof Ghast){
			Ghast g = (Ghast) e;
			DroneFighter f = SQSkywatch.getInstance().getFighter(g);
			SQSkywatch.activeFighters.remove(g);
			Player target = f.target;
			if(!SQSkywatch.outrightKill && SkywatchStatus.statusOf(target.getUniqueId()) == SkywatchStatus.ALL && !stillHasSkywatchAttacking(target, f, null)){
				SkywatchStatus.setStatus(target.getUniqueId(), SkywatchStatus.NONE);
				if(target != null) target.sendMessage("You have defeated all Skywatch troops and are now safe.");
				losses++;
			}
		} else if(DroneShocktroop.isPossiblyShocktroop(e)){
			Skeleton s = (Skeleton) event.getEntity();
			event.getDrops().clear();
			DroneShocktroop stp = SQSkywatch.getInstance().getShocktroop(s);
			SQSkywatch.activeFighters.remove(stp);
			Player target = stp.target;
			if(!SQSkywatch.outrightKill && SkywatchStatus.statusOf(target.getUniqueId()) == SkywatchStatus.ALL && !stillHasSkywatchAttacking(target, null, stp)){
				SkywatchStatus.setStatus(target.getUniqueId(), SkywatchStatus.NONE);
				if(target != null) target.sendMessage("You have defeated all Skywatch troops and are now safe.");
				losses++;
			}
			 //it's skywatch
			//get the skywatch object associated with it
		}
		//is skywatch
		//is last skywatch for player
		//skywatchstatus=none if beta
	}
	
	private boolean stillHasSkywatchAttacking(Player target, DroneFighter deadFighter, DroneShocktroop deadShocktroop){
		for(DroneFighter ftr : SQSkywatch.activeFighters){
			if(ftr != deadFighter && ftr.target == target){
				if(ftr.getGhast() != null && !ftr.getGhast().isDead()){
					//return, they still have active other skywatch after them.
					return true;
				}
			}
		}
		for(DroneShocktroop stp : SQSkywatch.activeShocktroops){
			if(stp != deadShocktroop && stp.target == target){
				if(stp.getSkeleton() != null && !stp.getSkeleton().isDead()){
					//return, they still have active other skywatch after them.
					return true;
				}
			}
		}
		return false;
	}

	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (cmd.getName().equalsIgnoreCase("drone")) {
			if(sender.hasPermission("SQSkywatch.summon")){
				if (args.length < 1)
					return false;
				String pName = args[0];
				Player p = Bukkit.getPlayer(pName);
				if (p == null)
					return false;
				spawnDrone(p);
				return true;
			}
		}
		if (cmd.getName().equalsIgnoreCase("shocktroop")) {
			if(sender.hasPermission("SQSkywatch.summon")){
				if (args.length < 1)
					return false;
				String pName = args[0];
				Player p = Bukkit.getPlayer(pName);
				if (p == null)
					return false;
				spawnShocktroop(p);
				return true;
			}
		}
		if (cmd.getName().equalsIgnoreCase("triggerSkywatch")) {
			if(sender.hasPermission("SQSkywatch.summon")){
				if (args.length < 1)
					return false;
				String pName = args[0];
				Player p = Bukkit.getPlayer(pName);
				if (p == null)
					return false;
				triggerSkywatch(p);
				return true;
			}
		}
		return false;
	}
	
	public void triggerSkywatch(final Player p) {
		final UUID u = p.getUniqueId();
		SkywatchStatus.setStatus(u, SkywatchStatus.EN_ROUTE);
		alertPlayer(p, "You have broken Skywatch law. Stop your vessel and prepare to be boarded!");
		Bukkit.getScheduler().scheduleSyncDelayedTask(this, new Runnable(){
			public void run(){
				if(SkywatchStatus.statusOf(u) == SkywatchStatus.EN_ROUTE){
					triggerSkywatchFighters(p);
					alertPlayer(p, "Skywatch fighters have arrived at your coordinates. Prepare to surrender your vessel.");
					SkywatchStatus.setStatus(u, SkywatchStatus.FIGHTERS);
				}
			}
		}, responseTime * 20L);
		Bukkit.getScheduler().scheduleSyncDelayedTask(this, new Runnable(){
			public void run(){
				//if he's still not dead within 20 seconds, spawn shocktroops
				if(SkywatchStatus.statusOf(u) == SkywatchStatus.FIGHTERS){
				triggerSkywatchShocktroops(p);
				EMPUtils.detonateEMP(p.getLocation().getBlock().getRelative(BlockFace.DOWN));
				alertPlayer(p, "Since you have continued to resist you will be boarded by elite Shock Troopers. Surrender!");
					SkywatchStatus.setStatus(u, SkywatchStatus.ALL);
				}
			}
		}, responseTime * 20L + 15 * 20L);
		Bukkit.getScheduler().scheduleSyncDelayedTask(this, new Runnable(){
			public void run(){
				if(SkywatchStatus.statusOf(u) == SkywatchStatus.ALL){
					triggerSkywatchShocktroops(p);
					triggerSkywatchFighters(p);
					EMPUtils.detonateEMP(p.getLocation().getBlock().getRelative(BlockFace.DOWN));
					alertPlayer(p, "Your resistance is admirable. More fighters and troops summoned.");
				}
			}
		}, responseTime * 20L + 45 * 20L);
		if(outrightKill){
			Bukkit.getScheduler().scheduleSyncDelayedTask(this, new Runnable(){
				public void run(){
					if(SkywatchStatus.statusOf(u) == SkywatchStatus.ALL){
						p.damage(1000);
						alertPlayer(p, "You have left us no choice but to use biological weapons.");
					}
				}
			}, responseTime * 20L + 90 * 20L);
		}
	}
	
	@EventHandler
	public void onPlayerDeath(PlayerDeathEvent e){
		UUID u = e.getEntity().getUniqueId();
		SkywatchStatus s = SkywatchStatus.statusOf(u);
		if(s != SkywatchStatus.NONE && s != SkywatchStatus.OFFLINE){
			SkywatchStatus.setStatus(u, SkywatchStatus.NONE);
			wins++;
		}
		triggerRemoval(e.getEntity());
	}
	
	@EventHandler
	public void onPlayerQuit(PlayerQuitEvent e){
		UUID u = e.getPlayer().getUniqueId();
		SkywatchStatus s = SkywatchStatus.statusOf(u);
		if(s != SkywatchStatus.NONE && s != SkywatchStatus.OFFLINE){
			SkywatchStatus.setStatus(u, SkywatchStatus.OFFLINE);
			triggerRemoval(e.getPlayer());
		}
	}
	
	@EventHandler
	public void onPlayerJoin(PlayerJoinEvent e){
		UUID u = e.getPlayer().getUniqueId();
		SkywatchStatus s = SkywatchStatus.statusOf(u);
		if(s == SkywatchStatus.OFFLINE){
			triggerSkywatch(e.getPlayer());
		}
	}

	public void triggerSkywatchFighters(final Player p){
		if(numFighters == 0) return;
		int randMod = (int) Math.round(Math.random() * randomRange * 2 - randomRange);
		int numToSummon = numFighters + randMod;
		
		if(system.equalsIgnoreCase("Trinitos_Beta")){
			double ratio = losses / wins;
			double temp = numToSummon * ratio;
			if(!p.getWorld().getName().startsWith("Trinitos")){
				//planet, double shocktroops, half fighters
				temp *= 0.5;
			}
			if(temp < 1) temp = 1;
			numToSummon = (int) Math.round(temp);
		}
		int i = 0;
		while(i < numToSummon){
			spawnDrone(p);
			i++;
		}
	}
	
	public void triggerSkywatchShocktroops(final Player p){
		if(numFighters == 0) return;
		int randMod = (int) Math.round(Math.random() * randomRange * 2 - randomRange);
		int numToSummon = numFighters + randMod;
		if(system.equalsIgnoreCase("Trinitos_Beta")){
			double ratio = losses / wins;
			double temp = numToSummon * ratio;
			if(!p.getWorld().getName().startsWith("Trinitos")){
				//planet, double shocktroops, half fighters
				temp *= 2;
			}
			if(temp < 1) temp = 1;
			numToSummon = (int) Math.round(temp);

		}
		int i = 0;
		while(i < numToSummon){
			spawnShocktroop(p);
			i++;
		}
	}
	
	public void setWarning(final Player p){
		final UUID u = p.getUniqueId();
		if(pvpLock){
			SkywatchStatus.setStatus(u, SkywatchStatus.WARN_SHORT);
			alertPlayer(p, "You have broken Skywatch law! This is your warning. You have been pvp-locked for 3 seconds. Stop attacking or Skywatch troops will be summoned!");
			Bukkit.getScheduler().scheduleSyncDelayedTask(this, new Runnable(){
				public void run(){
					if(SkywatchStatus.statusOf(u) == SkywatchStatus.WARN_SHORT){
						SkywatchStatus.setStatus(u, SkywatchStatus.WARN_LONG);
						alertPlayer(p, "Your PvP lock has been lifted. Continuing to attack will summon Skywatch!");
					}
				}
			}, 20L * 5);
			
			Bukkit.getScheduler().scheduleSyncDelayedTask(this, new Runnable(){
				public void run(){
					if(SkywatchStatus.statusOf(u) == SkywatchStatus.WARN_LONG){
						SkywatchStatus.setStatus(u, SkywatchStatus.NONE);
					}
				}
			}, 20L * 60);
		} else {
			SkywatchStatus.setStatus(u, SkywatchStatus.WARN_LONG);
			
			Bukkit.getScheduler().scheduleSyncDelayedTask(this, new Runnable(){
				public void run(){
					if(SkywatchStatus.statusOf(u) == SkywatchStatus.WARN_LONG){
						SkywatchStatus.setStatus(u, SkywatchStatus.NONE);
					}
				}
			}, 20L * 60);
		}
	}
	
	public static void alertPlayer(Player p, String message){
		p.playSound(p.getLocation(), Sound.LEVEL_UP, 2.0F, 2.0F);
		p.sendMessage(ChatColor.RED + "=========================================");
		p.sendMessage(ChatColor.BLUE + "SKYWATCH WARNING!");
		p.sendMessage(ChatColor.RED + message);
		p.sendMessage(ChatColor.RED + "=========================================");
	}

	
	public static void spawnDrone(Player target) {
		DroneFighter f = new DroneFighter(target);
		activeFighters.add(f);
	}
	
	public static void spawnShocktroop(Player target){
		DroneShocktroop s = new DroneShocktroop(target);
		activeShocktroops.add(s);
	}


	private static void update() {
		Iterator<DroneFighter> i = activeFighters.iterator();
		while (i.hasNext()) {
			DroneFighter f = i.next();
			if(f.myGhast == null || f.myGhast.isDead()){
				i.remove();
				return;
			}
			if (f.target == null || !f.target.isOnline()) {
				f.myGhast.remove();
				i.remove();
				return;
			}
			System.out.println(Math.sqrt(f.distToTargetSquared()) + " blocks away.");
			if (f.distToTargetSquared() > 50 * 50) {
				// if we're too far away go closer.
				Location l = f.getSafeLocationNearTarget();
				if(l != null){
					System.out.println("teleporting.");
					f.myGhast.getWorld().playEffect(f.myGhast.getLocation(), Effect.ENDER_SIGNAL, 0);
					f.myGhast.teleport(l);
					f.myGhast.getWorld().playEffect(f.myGhast.getLocation(), Effect.ENDER_SIGNAL, 0);
				}
			}
			if(f.canFireAgain()){
				System.out.println("Firing!");
				f.fireOnTarget();
			}
		}
		Iterator<DroneShocktroop> i2 = activeShocktroops.iterator();
		while(i2.hasNext()){
			DroneShocktroop t = i2.next();
			System.out.println("Updating shocktroop.");
			if(t.mySkeleton == null || t.mySkeleton.isDead()){
				i2.remove();
				return;
			}
			//t.mySkeleton.setTarget(t.target);
			if(t.mySkeleton.getTarget() != t.target){
				t.mySkeleton.setTarget(t.target);
			}
			//t.mySkeleton.setVelocity(t.toTarget());
			if(t.target == null || !t.target.isOnline()){
				t.mySkeleton.remove();
				i2.remove();
				return;
			}
			System.out.println(Math.sqrt(t.distToTargetSquared()) + " blocks away.");
			if (t.distToTargetSquared() > 50 * 50) {
				Location l = t.getSafeLocationNearTarget();
				if(l != null){
					System.out.println("teleporting.");
					t.mySkeleton.teleport(l);
				}
			}
			/*if(t.canFireAgain()){
				System.out.println("Firing!");
				t.fireOnTarget();
			}*/
		}
	}
	
	@EventHandler
	public void onArrowHit(EntityDamageByEntityEvent event){
		if(event.getDamager() instanceof Arrow){
			Arrow a = (Arrow) event.getDamager();
			if(a.getShooter() instanceof Skeleton && event.getEntity() instanceof Skeleton){
				Skeleton s1 = (Skeleton) a.getShooter();
				Skeleton s2 = (Skeleton) event.getEntity();
				if(DroneShocktroop.isPossiblyShocktroop(s1) && DroneShocktroop.isPossiblyShocktroop(s2)){
					event.setCancelled(true);
				}
			}
		}
	}

	@EventHandler
	public void onGhastShoot(ProjectileLaunchEvent event) {
		if (event.getEntity() instanceof LargeFireball) {
			// ghast fireball
			LargeFireball frb = (LargeFireball) event.getEntity();
			if (frb.getShooter() instanceof Ghast) {
				Ghast g = (Ghast) frb.getShooter();
				DroneFighter f = getFighter(g);
				if (f == null) {
					g.remove();
					System.out.println("f for ghast is null, removing!");
					event.setCancelled(true);
				} else {
					event.setCancelled(true);
					System.out.println("Dud fire!");
				}
			}
		}
	}
	
	@EventHandler
	public void onSkeletonShoot(EntityShootBowEvent event){
		if(event.getEntity() instanceof Skeleton){
			Skeleton s = (Skeleton) event.getEntity();
			if(DroneShocktroop.isPossiblyShocktroop(s)){
				DroneShocktroop troop = getShocktroop(s);
				if(troop == null){
					s.remove();
					System.out.println("troop for named skeleton is null, removing!");
					event.setCancelled(true);
				} else {
					System.out.println("Allowed fire!");
				}
			}
		}
	}
	
	private void triggerRemoval(Player p){
		Iterator<DroneFighter> i = activeFighters.iterator();
		while (i.hasNext()) {
			DroneFighter f = i.next();
			if (f.target == p) {
				f.myGhast.remove();
				i.remove();
			}
		}
		Iterator<DroneShocktroop> i2 = activeShocktroops.iterator();
		while (i2.hasNext()) {
			DroneShocktroop f = i2.next();
			if (f.target == p) {
				f.mySkeleton.remove();
				i2.remove();
			}
		}
	}

	DroneFighter getFighter(Ghast g) {
		for (DroneFighter f : activeFighters) {
			if (f.getGhast() == g) {
				return f;
			}
		}
		return null;
	}
	
	DroneShocktroop getShocktroop(Skeleton skel){
		for (DroneShocktroop s : activeShocktroops) {
			if (s.mySkeleton == skel) {
				return s;
			}
		}
		return null;
	}

	public static SQSkywatch getInstance() {
		return instance;
	}
}
