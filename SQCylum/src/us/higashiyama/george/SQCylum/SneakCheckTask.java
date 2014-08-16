
package us.higashiyama.george.SQCylum;

import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

public class SneakCheckTask extends BukkitRunnable {

	public SneakCheckTask(Core instance) {

	}

	@Override
	public void run() {

		for (Player p : Core.sneakCache) {
			if (p.isSneaking()) {
				incrementXP(p);
			}
		}
	}

	private void incrementXP(Player player) {

		System.out.println("Exp: " + player.getExp());
		if (player.getExp() >= 0.92) {
			System.out.println("Too much");
			player.setExp(0);
		}
		player.giveExp((int) (player.getExp() + 1));
	}

}
