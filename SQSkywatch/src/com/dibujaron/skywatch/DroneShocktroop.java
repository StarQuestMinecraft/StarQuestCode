package com.dibujaron.skywatch;

import java.util.Arrays;

import org.bukkit.Effect;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Fireball;
import org.bukkit.entity.Player;
import org.bukkit.entity.Skeleton;
import org.bukkit.inventory.EntityEquipment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.Vector;

public class DroneShocktroop {
	
	private static final int[] ACCEPTABLE_SPAWN_BLOCKS = new int[]{
		0,
		6,
		30,
		31,
		32,
		37,
		38,
		39,
		40,
		50,
		55,
		59,
		63,
		66,
		68,
		69,
		70,
		71,
		72,
		75,
		76,
		77,
		78,
		83,
		141,
		142,
		143,
	};
	
	public DroneShocktroop(Player target){
		this.target = target;
		Location l = getSafeLocationNearTarget();
		if(l == null) l = target.getLocation();
		mySkeleton = (Skeleton) target.getWorld().spawnEntity(l, EntityType.SKELETON);
		target.playSound(mySkeleton.getLocation(), Sound.PORTAL_TRAVEL, 2.0F, 2.0F);
		mySkeleton.setCustomName("Skywatch Shock Trooper");
		mySkeleton.setCustomNameVisible(true);
		EntityEquipment e = mySkeleton.getEquipment();
		ItemStack hat = new ItemStack(Material.DISPENSER, 1);
		ItemStack chest = new ItemStack(Material.DIAMOND_CHESTPLATE, 1);
		ItemStack leggings = new ItemStack(Material.DIAMOND_LEGGINGS, 1);
		ItemStack boots = new ItemStack(Material.DIAMOND_BOOTS, 1);
		chest.addEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, SQSkywatch.protectionLevel);
		leggings.addEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, SQSkywatch.protectionLevel);
		boots.addEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, SQSkywatch.protectionLevel);
		e.setHelmet(hat);
		e.setChestplate(chest);
		e.setLeggings(leggings);
		e.setBoots(boots);
		

		ItemStack bow = new ItemStack(Material.BOW, 1);
		bow.addEnchantment(Enchantment.ARROW_DAMAGE, SQSkywatch.powerLevel);
		bow.addEnchantment(Enchantment.ARROW_KNOCKBACK, SQSkywatch.knockbackLevel);
		if(SQSkywatch.flameBows){
			bow.addEnchantment(Enchantment.ARROW_FIRE, 1);
		}
		e.setItemInHand(bow);
		mySkeleton.setTarget(target);
		mySkeleton.getWorld().playEffect(mySkeleton.getLocation(), Effect.ENDER_SIGNAL, 0);
	}
	
	public static boolean isPossiblyShocktroop(Entity e) {
		if(e instanceof Skeleton){
			Skeleton s = (Skeleton) e;
			return s.getCustomName() != null && s.getCustomName().equals("Skywatch Shock Trooper");
		}
		return false;
	}

	Skeleton mySkeleton;
	Player target;
	long lastFired;

	public double distToTargetSquared() {
		Vector g = mySkeleton.getLocation().toVector();
		Vector t = target.getLocation().toVector();
		return t.subtract(g).lengthSquared();
	}

	public Location getSafeLocationNearTarget() {
		Location retval = null;
		int tries = 0;
		while(!isSafeSpace(retval) && tries < 500){
			tries++;
			retval = generateRandomLoc(target.getLocation());
		}
		if(isSafeSpace(retval)) return retval;
		return null;
	}

	private boolean isSafeSpace(Location l) {
		if(l == null) return false;
		Block b = l.getBlock();
		System.out.println(b.getType());
		System.out.println(b.getRelative(BlockFace.DOWN).getType());
		System.out.println(b.getRelative(BlockFace.UP).getType());
		return isEmptyBlock(b) && !isEmptyBlock(b.getRelative(BlockFace.DOWN)) && isEmptyBlock(b.getRelative(BlockFace.UP));
	}
	
	private boolean isEmptyBlock(Block b){
		int id = b.getTypeId();
		for(int i : ACCEPTABLE_SPAWN_BLOCKS){
			if(id == i) return true;
		}
		return false;
	}

	public boolean canFireAgain() {
		System.out.println("test fire.");
		return System.currentTimeMillis() - lastFired > (5 * 1000);
	}

	public void fireOnTarget(){
		lastFired = System.currentTimeMillis();
		/*Vector target = toTarget();
		Vector target2 = target.clone().multiply(5);
		System.out.println("addition: " + target2);
		System.out.println("SOUND");
		//myGhast.getWorld().playSound(myGhast.getLocation(), Sound.GHAST_DEATH, 2.0f, (float) (Math.random() * 3.0f));
		Location fireLoc = mySkeleton.getLocation().clone().add(target2);
		System.out.println("fireloc: " + fireLoc);
		Arrow a = (Arrow) mySkeleton.getWorld().spawnEntity(fireLoc, EntityType.ARROW);
		a.setVelocity(target);*/
		
		mySkeleton.launchProjectile(Arrow.class);
		
	}
	
	public Vector toTarget(){
		Vector g = mySkeleton.getLocation().toVector();
		Vector t = target.getLocation().toVector();
		return t.subtract(g).normalize();
	}
	
	private Location generateRandomLoc(Location l) {
		double x = (Math.random() * 6) + (l.getBlockX() - 3);
		double y = l.getBlockY();
		double z = (Math.random() * 6) + (l.getBlockZ() - 3);
		
		Location retval = new Location(l.getWorld(), x, y, z);
		return retval;
	}

	public Skeleton getSkeleton() {
		return mySkeleton;
	}

}
