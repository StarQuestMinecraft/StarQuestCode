package com.starquestminecraft.greeter.command;

import com.starquestminecraft.greeter.MaintenanceMode;
import com.starquestminecraft.greeter.Greeter;

import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.plugin.Command;
import net.md_5.bungee.command.ConsoleCommandSender;

public class MaintenanceCommand extends Command {
	public MaintenanceCommand() {
		super("maintenance");
	}

	public void execute(CommandSender sender, String[] args) {
		if (!(sender instanceof ConsoleCommandSender)) {
			sender.sendMessage(createMessage("This command can only be run from console!"));
			return;
		}

		if (args.length >= 1) {
			if(args[0].equals("toggle")){
				String message = "";
				for(int i = 1; i < args.length; i++){
					message = message + " " + args[i];
				}
				MaintenanceMode.toggleEnabled(message);
				if(MaintenanceMode.isEnabled()){
					sender.sendMessage(createMessage("Maintenance mode enabled."));
				} else {
					sender.sendMessage(createMessage("Maintenance mode disabled."));
				}
				return;
			} else {
				MaintenanceMode.addPlayer(args[0]);
				sender.sendMessage(createMessage("Player " + args[0] + " added to maintenance list."));
			}
		} else {
			sender.sendMessage(createMessage("maintenance <toggle/player>"));
			return;
		}
	}

	private static BaseComponent[] createMessage(String s) {
		return new ComponentBuilder(s).create();
	}
}

/*
 * Location: C:\Users\Drew\Desktop\SQDynamicWhitelist.jar Qualified Name:
 * com.starquestminecraft.dynamicwhitelist.command.AddCommand JD-Core Version:
 * 0.6.2
 */