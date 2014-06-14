
package us.higashiyama.george.SQTurrets.Turrets;

import java.util.ArrayList;

import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;

import us.higashiyama.george.SQTurrets.Turret;

public class APTurret extends Turret {

	public static ArrayList<Arrow> arrowList = new ArrayList<Arrow>();

	@Override
	public void shoot(Player player) {

		Location eye = player.getEyeLocation();
		double pitch = (eye.getPitch() + 90.0F) * 3.141592653589793D / 180.0D;
		double yaw = (eye.getYaw() + 90.0F) * 3.141592653589793D / 180.0D;
		double x = Math.sin(pitch) * Math.cos(yaw);
		double y = Math.sin(pitch) * Math.sin(yaw);
		double z = Math.cos(pitch);
		Vector v = new Vector(x, z, y);
		eye.add(v);
		Arrow a = player.getWorld().spawnArrow(eye, v, 3.0F, 0.0F);
		// a.setShooter(player);
		arrowList.add(a);
		eye.getWorld().playSound(eye, Sound.SHOOT_ARROW, 2.0F, 1.0F);
	}
}
