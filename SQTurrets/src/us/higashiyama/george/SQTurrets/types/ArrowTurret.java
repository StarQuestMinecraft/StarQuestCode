package us.higashiyama.george.SQTurrets.types;

import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.entity.TippedArrow;
import org.bukkit.inventory.meta.PotionMeta;
import org.bukkit.projectiles.ProjectileSource;
import org.bukkit.util.Vector;

import us.higashiyama.george.SQTurrets.Ammo;

public class ArrowTurret extends Turret {
	
	public ArrowTurret(){
		setName("apturret");
	}

	@Override
	public void shoot(Player p) {
		// TODO Auto-generated method stub
		p.sendMessage("AP turrets require arrows as ammo");
	}

	@Override
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
		Arrow proj;
		if(loaded_ammo.getItem().hasItemMeta()){
			proj = (TippedArrow) p.getWorld().spawnEntity(loc, EntityType.TIPPED_ARROW);
			PotionMeta meta = (PotionMeta) loaded_ammo.getItem().getItemMeta();
			((TippedArrow) proj).setBasePotionData(meta.getBasePotionData());
		} else {
			proj = (Arrow) p.getWorld().spawnEntity(loc, EntityType.ARROW);
		}
		proj.setCritical(true);
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
