
package us.higashiyama.george.SQShops;

import org.bukkit.ChatColor;
import org.bukkit.scheduler.BukkitRunnable;

public class NotifierTask extends BukkitRunnable {

	public void run() {

		if (SQShops.instance.MULTIPLIER != 1) {
			SQShops.instance.getServer().broadcastMessage(
					ChatColor.GOLD + "There is current a x" + SQShops.instance.MULTIPLIER + " multiplier to any sale at Spawn shops!");
		}
	}

}
