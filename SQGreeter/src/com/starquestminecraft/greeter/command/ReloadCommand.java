package com.starquestminecraft.greeter.command;

import com.starquestminecraft.greeter.Greeter;

import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.plugin.Command;
import net.md_5.bungee.command.ConsoleCommandSender;

public class ReloadCommand extends Command {
	public ReloadCommand() {
		super("priorityreload");
	}

	public void execute(CommandSender sender, String[] args) {
		if (!(sender instanceof ConsoleCommandSender)) {
			sender.sendMessage(createMessage("This command can only be run from console!"));
			return;
		}
		Greeter.getInstance().loadSettings();
		sender.sendMessage(createMessage("Settings reloaded."));
	}

	private static BaseComponent[] createMessage(String s) {
		return new ComponentBuilder(s).create();
	}
}

/*
 * Location: C:\Users\Drew\Desktop\SQDynamicWhitelist.jar Qualified Name:
 * com.starquestminecraft.dynamicwhitelist.command.ReloadCommand JD-Core
 * Version: 0.6.2
 */