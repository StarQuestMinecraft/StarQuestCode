
package com.regalphoenixmc.SQRankup;

import org.bukkit.ChatColor;
import org.bukkit.scheduler.BukkitRunnable;

public class NotifierTask extends BukkitRunnable {

	@Override
	public void run() {

		if (SQRankup.instance().MULTIPLIER != 1) {
			SQRankup.instance().getServer()
					.broadcastMessage(ChatColor.GOLD + "There is current a x" + SQRankup.instance().MULTIPLIER + " multiplier to any kill!");
		}
	}

}
