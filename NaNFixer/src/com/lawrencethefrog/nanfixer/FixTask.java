package com.lawrencethefrog.nanfixer;

import org.bukkit.command.CommandSender;
import org.bukkit.scheduler.BukkitRunnable;

public class FixTask extends BukkitRunnable {
	private NaNFixer plugin;
	private String playerUUID;
	private CommandSender sender;
	
	protected FixTask(NaNFixer plugin, String playerUUID, CommandSender sender) {
		super();
		this.plugin = plugin;
		this.playerUUID = playerUUID;
		this.sender = sender;
	}

	@Override
	public void run() {
		plugin.fixPlayer(playerUUID, sender);
	}
}
