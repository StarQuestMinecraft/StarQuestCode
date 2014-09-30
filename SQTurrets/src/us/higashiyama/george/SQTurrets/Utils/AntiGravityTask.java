
package us.higashiyama.george.SQTurrets.Utils;

import org.bukkit.entity.Projectile;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

import us.higashiyama.george.SQTurrets.SQTurrets;

public class AntiGravityTask extends BukkitRunnable {

	public AntiGravityTask() {

		runTaskTimer(SQTurrets.getInstance(), 1L, 1L);
	}

	public void run() {

		for (int i = 0; i < SQTurrets.liveProjectiles.size(); i++) {
			if ((SQTurrets.liveProjectiles.get(i)).getTicksLived() > 100 || ((SQTurrets.liveProjectiles.get(i)).isOnGround())) {
				SQTurrets.liveProjectiles.remove(i);
				i--;
			}
		}
		for (int i = 0; i < SQTurrets.liveProjectiles.size(); i++) {
			Projectile a = SQTurrets.liveProjectiles.get(i);
			if ((a.getLocation().getWorld().getName().equals("Regalis")) || (a.getLocation().getWorld().getName().equals("AsteroidBelt"))
					|| (a.getLocation().getWorld().getName().equals("Defalos")) || (a.getLocation().getWorld().getName().equals("Digitalia"))) {
				Vector old = a.getVelocity();
				Vector grav = new Vector(0.0F, 0.05F, 0.0F);
				old = old.add(grav);
				old = old.multiply(1.25F);
				a.setVelocity(old);
			}
		}
	}

}
