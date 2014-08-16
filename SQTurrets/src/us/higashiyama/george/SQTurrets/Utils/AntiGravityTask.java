
package us.higashiyama.george.SQTurrets.Utils;

import org.bukkit.scheduler.BukkitRunnable;

import us.higashiyama.george.SQTurrets.SQTurrets;

public class AntiGravityTask extends BukkitRunnable {

	public AntiGravityTask() {

		runTaskTimer(SQTurrets.getInstance(), 1L, 1L);
	}

	/*
	 * public void run() {
	 * 
	 * for (int i = 0; i < SQTurrets.arrowList.size(); i++) { if ((((Arrow)
	 * SQTurrets.arrowList.get(i)).isOnGround()) || (((Arrow)
	 * SQTurrets.arrowList.get(i)).getTicksLived() > 100)) {
	 * SQTurrets.arrowList.remove(i); i--; } } for (int i = 0; i <
	 * SQTurrets.arrowList.size(); i++) { Arrow a = (Arrow)
	 * SQTurrets.arrowList.get(i); if
	 * ((a.getLocation().getWorld().getName().equals("space")) ||
	 * (a.getLocation().getWorld().getName().equals("AsteroidBelt"))) { Vector
	 * old = a.getVelocity(); Vector grav = new Vector(0.0F, 0.05F, 0.0F); old =
	 * old.add(grav); old = old.multiply(1.25F); a.setVelocity(old); } } }
	 */

	public void run() {

		// TODO Auto-generated method stub

	}

}
