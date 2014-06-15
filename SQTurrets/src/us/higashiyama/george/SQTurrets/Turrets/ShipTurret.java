
package us.higashiyama.george.SQTurrets.Turrets;

import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.entity.Fireball;
import org.bukkit.entity.Player;

import us.higashiyama.george.SQTurrets.Turret;

public class ShipTurret extends Turret {

	@Override
	public void shoot(Player player) {

		Location eye = player.getEyeLocation();
		Location oneUp = eye.add(0.0D, 1.0D, 0.0D);
		Location loc = oneUp.toVector().add(player.getLocation().getDirection().multiply(2))
				.toLocation(player.getWorld(), player.getLocation().getYaw(), player.getLocation().getPitch());
		Fireball fireball = (Fireball) player.getWorld().spawn(loc, Fireball.class);
		// fireball.setShooter(player);
		oneUp.getWorld().playSound(oneUp, Sound.SHOOT_ARROW, 2.0F, 1.0F);

	}

	@Override
	public void shootAdvanced(Player player) {

		Location eye = player.getEyeLocation();
		Location oneUp = eye.add(0.0D, 1.0D, 0.0D);
		Location loc = oneUp.toVector().add(player.getLocation().getDirection().multiply(2))
				.toLocation(player.getWorld(), player.getLocation().getYaw(), player.getLocation().getPitch());
		Fireball fireball = (Fireball) player.getWorld().spawn(loc, Fireball.class);
		// fireball.setShooter(player);
		oneUp.getWorld().playSound(oneUp, Sound.SHOOT_ARROW, 2.0F, 1.0F);
		player.sendMessage("ADVANCED");

	}

}
