package com.dibujaron.skywatch;

import org.bukkit.Effect;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Fireball;
import org.bukkit.entity.Ghast;
import org.bukkit.entity.LargeFireball;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;

public class DroneFighter {
	
	Ghast myGhast;
	Player target;
	long lastFired;
	
	public DroneFighter(Player target){
		this.target = target;
		Location l = getSafeLocationNearTarget();
		if(l == null) return;
		myGhast = (Ghast) target.getWorld().spawnEntity(l, EntityType.GHAST);
		target.playSound(myGhast.getLocation(), Sound.PORTAL_TRAVEL, 2.0F, 2.0F);
		myGhast.getWorld().playEffect(myGhast.getLocation(), Effect.ENDER_SIGNAL, 0);
	}
	
	public void fireOnTarget(){
		lastFired = System.currentTimeMillis();
		Vector target = toTarget();
		Vector target2 = target.clone().multiply(5);
		System.out.println("addition: " + target2);
		System.out.println("SOUND");
		//myGhast.getWorld().playSound(myGhast.getLocation(), Sound.GHAST_DEATH, 2.0f, (float) (Math.random() * 3.0f));
		Location fireLoc = myGhast.getLocation().clone().add(target2);
		System.out.println("fireloc: " + fireLoc);
		Fireball f = (Fireball) myGhast.getWorld().spawnEntity(fireLoc, EntityType.FIREBALL);
		f.setDirection(target);
		f.setIsIncendiary(false);
		f.setYield(2.0F);
	}
	
	public Vector toTarget(){
		Vector g = myGhast.getLocation().toVector();
		Vector t = target.getLocation().toVector();
		return t.subtract(g).normalize();
	}
	
	public Ghast getGhast(){
		return myGhast;
	}
	
	public double distToTargetSquared(){
		Vector g = myGhast.getLocation().toVector();
		Vector t = target.getLocation().toVector();
		return t.subtract(g).lengthSquared();
	}

	public Location getSafeLocationNearTarget() {
		Location retval = null;
		int tries = 0;
		while(!isOpenSpace(retval) && tries < 500){
			tries++;
			retval = generateRandomLoc(target.getLocation());
		}
		if(isOpenSpace(retval)) return retval;
		return null;
	}

	private Location generateRandomLoc(Location l) {
		double x = (Math.random() * 100) + (l.getBlockX() - 50);
		double y = (Math.random() * 40) + (l.getBlockY() - 20);
		double z = (Math.random() * 100) + (l.getBlockZ() - 50);
		
		if(y < 0) y = 1;
		if(y > 255) y = 254;
		
		Location retval = new Location(l.getWorld(), x, y, z);
		return retval;
	}

	private boolean isOpenSpace(Location l) {
		if(l == null) return false;
		return l.getBlock().getType() == Material.AIR;
	}

	public boolean canFireAgain() {
		return System.currentTimeMillis() - lastFired > (5 * 1000);
	}
}
