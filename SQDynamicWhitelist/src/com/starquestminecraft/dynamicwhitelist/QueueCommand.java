package com.starquestminecraft.dynamicwhitelist;

import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.plugin.Command;
import net.md_5.bungee.command.ConsoleCommandSender;

public class QueueCommand extends Command{

	public QueueCommand() {
		super("setQueue");
	}

	@Override
	public void execute(CommandSender sender, String[] args) {
		if(sender instanceof ConsoleCommandSender){
			boolean newValue = ! Whitelister.getInstance().getDatabase().isUsingQueue();
			Whitelister.getInstance().setQueue(newValue);
			System.out.println("Queueing: " + newValue);
		}
	}

}
