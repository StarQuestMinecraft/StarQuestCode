package com.burkentertainment.SQPlanet;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class SQPlanetCommand implements CommandExecutor {
	private final SQPlanetPlugin plugin;
	
	public SQPlanetCommand(SQPlanetPlugin p) {
		this.plugin = p;
	}

	// Command handler for main "SQPlanet" command
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (args[0].equalsIgnoreCase("reload")) {
			if ((null != sender) && sender.hasPermission("sqplanet.admin")) {
				this.plugin.onDisable();
				this.plugin.onEnable();
			}
		}
		return false;
	}

}
