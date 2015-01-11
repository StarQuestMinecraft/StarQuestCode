package com.starquestminecraft.dynamicwhitelist;

import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.plugin.Command;
import net.md_5.bungee.command.ConsoleCommandSender;

public class AddCommand extends Command{
	public AddCommand(){
		super("timeadd");
	}
	
	@Override
	public void execute(CommandSender sender, String[] args){
		if(!(sender instanceof ConsoleCommandSender)){
			sender.sendMessage(createMessage("This command can only be run from console!"));
			return;
		}
		
		if(args.length >= 2){
			String name = args[0];
			String time = args[1];
			int hours = 0;
			try{
				hours = Integer.parseInt(time);
			}catch(Exception e){
				sender.sendMessage(createMessage("Invalid time argument!"));
				return;
			}
			
			Whitelister.getInstance().getDatabase().addPremiumTime(name, hours);
			sender.sendMessage(createMessage(hours + " hours added to " + name));
		} else {
			sender.sendMessage(createMessage("Not enough arguments!"));
			return;
		}
	}
	
	private static BaseComponent[] createMessage(String s){
		return new ComponentBuilder(s).create();
	}
}
