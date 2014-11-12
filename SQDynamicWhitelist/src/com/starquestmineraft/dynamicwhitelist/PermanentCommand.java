package com.starquestmineraft.dynamicwhitelist;

import java.util.UUID;

import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.plugin.Command;
import net.md_5.bungee.command.ConsoleCommandSender;

public class PermanentCommand extends Command{
	
	public PermanentCommand(){
		super("permanent");
	}
	
	public void execute(CommandSender sender, String[] args){
		if(!(sender instanceof ConsoleCommandSender)) return;
		String name = args[0];
		boolean permanent = Boolean.parseBoolean(args[1]);
		UUID u = Whitelister.getInstance().getDatabase().uuidFromUsername(name);
		Whitelister.getInstance().getDatabase().setPermanent(u, permanent);
		sender.sendMessage(createMessage("player " + u + " now has permanent status of " + permanent));
	}
	
	private static BaseComponent[] createMessage(String s){
		return new ComponentBuilder(s).create();
	}
}
