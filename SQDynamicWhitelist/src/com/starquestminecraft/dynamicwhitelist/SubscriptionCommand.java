package com.starquestminecraft.dynamicwhitelist;

import java.util.UUID;

import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Command;
import net.md_5.bungee.command.ConsoleCommandSender;

public class SubscriptionCommand extends Command{
	public SubscriptionCommand(){
		super("subscription");
	}
	
	@Override
	public void execute(CommandSender sender, String[] args){
		if(sender instanceof ProxiedPlayer){
			ProxiedPlayer p = (ProxiedPlayer) sender;
			int hours = Whitelister.getInstance().getDatabase().getRemainingTime(p.getUniqueId());
			sender.sendMessage(createMessage("You have " + hoursToReadable(hours) + " remaining on your SQPriority subscription."));
		} else if (sender instanceof ConsoleCommandSender){
			String player = args[0];
			UUID u = Whitelister.getInstance().getDatabase().uuidFromUsername(player);
			int hours = Whitelister.getInstance().getDatabase().getRemainingTime(u);
			sender.sendMessage(createMessage("Player " + player + " has " + hours + " hours remaining on their subscription."));
		}
	}
	
	private static BaseComponent[] createMessage(String s){
		return new ComponentBuilder(s).create();
	}
	
	private String hoursToReadable(int hours){
		int days = (int) hours / 24;
		int hoursRemainder = hours % 24;
		
		return days + " days and " + hoursRemainder + " hours";
	}
}
