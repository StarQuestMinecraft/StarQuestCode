package us.higashiyama.george.SQSpace;

import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

public class SuffocationTask extends BukkitRunnable {
	Player p;
	int OxygenLevel = 0;
	SQSpace plugin;
	boolean canceled = false;

	SuffocationTask(SQSpace plugin, Player p) {
		this.p = p;
		long delay = 20L;
		this.runTaskTimer(plugin, delay, delay);
		this.plugin = plugin;
	}

	public void cancel(Player p) {
		this.cancel();
		this.canceled = true;

		// Clear the player from the list of suffocating players
		SQSpace.Players.remove(p);
		// Check if we need to trigger a different speed suffocation task, otherwise tell them they are not suffocating
		if (!this.plugin.checkIfSuffocating(p))
		{
			p.sendMessage(ChatColor.AQUA + "[Space] " + ChatColor.GREEN
					+ "You are no longer suffocating!");
		}
	}

	@Override
	public void run() {
		if (!this.plugin.isInSpace(this.p)) {
			this.cancel(this.p);
		}
		if (this.plugin.hasSpaceArmor(this.p)) {
			this.cancel(this.p);
		}
		if (this.p.isDead()) {
			this.cancel(this.p);
		}
		if (this.plugin.noSuffacatePlayers.contains(this.p)) {
			this.cancel(this.p);
		}
		if (!this.canceled) {
			if (p.getGameMode().equals(GameMode.SURVIVAL) || p.getGameMode().equals(GameMode.ADVENTURE)) {
				double health = this.p.getHealth();
				this.p.damage(1.0D);
				this.p.setHealth(health-1.0D);
			}
		}
	}
}
