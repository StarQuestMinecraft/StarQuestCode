package us.higashiyama.george.SQTurrets.types;

import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.projectiles.ProjectileSource;
import org.bukkit.util.Vector;

import us.higashiyama.george.SQTurrets.Ammo;

public class FireballTurret extends Turret {
	
	public FireballTurret(){
		setName("turret");
	}

	public void shoot(final Player p) {

		// First create a vector
		Vector v = new Vector(getVelocity(), getVelocity(), getVelocity());
		Vector pVec = p.getLocation().getDirection();
		Vector finalVec = new Vector(v.getX() * pVec.getX(), v.getY() * pVec.getY(), v.getZ() * pVec.getZ());

		Location eye = p.getEyeLocation();
		Location oneUp = eye.add(0.0D, 1.0D, 0.0D);
		Location loc = oneUp.toVector().add(p.getLocation().getDirection().multiply(2))
				.toLocation(p.getWorld(), p.getLocation().getYaw(), p.getLocation().getPitch());

		// then launch the projectile
		Projectile proj = (Projectile) p.getWorld().spawnEntity(loc, EntityType.FIREBALL);
		// launchProjectile(this.projectileClass, finalVec);
		// finally, set the shooter
		proj.setVelocity(finalVec);

		/*if (!(proj instanceof Egg) || !(proj instanceof Snowball)) {
			proj.setShooter((ProjectileSource) p);
		}*/
		proj.setShooter(p);

		// play sounds
		p.getWorld().playSound(p.getLocation(), Sound.ENTITY_ARROW_SHOOT, 2.0F, 1.0F);
	}

	/*
	 * Advanced shoot method will have to be overwritten for all new turret
	 * classes that have an extended version of a turret that takes ammo
	 */
	public void shoot(final Player p, Ammo loaded_ammo) {

		// create a vector for the initial projectile
		Vector v = new Vector(getVelocity(), getVelocity(), getVelocity());

		// Now create a vector for the advanced version modifier
		Vector vMod = new Vector(loaded_ammo.getVelocity(), loaded_ammo.getVelocity(), loaded_ammo.getVelocity());
		Vector pVec = p.getLocation().getDirection();
		Vector finalV = pVec.multiply(v.multiply(vMod));

		Location eye = p.getEyeLocation();
		Location oneUp = eye.add(0.0D, 1.0D, 0.0D);
		Location loc = oneUp.toVector().add(p.getLocation().getDirection().multiply(2))
				.toLocation(p.getWorld(), p.getLocation().getYaw(), p.getLocation().getPitch());

		// then launch the projectile
		Projectile proj = (Projectile) p.getWorld().spawnEntity(loc, EntityType.FIREBALL);

		proj.setVelocity(finalV);

		proj.setShooter((ProjectileSource) p);

		// Ammo specific settings
		if (loaded_ammo.isFire()) {
			proj.setFireTicks(Integer.MAX_VALUE);
		}

		// play sounds
		p.getWorld().playSound(p.getLocation(), Sound.ENTITY_ARROW_SHOOT, 0.5F, 1.0F);
	}
}
