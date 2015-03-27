package com.minecraftdimensions.bungeesuitechat.commands.channel;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import com.minecraftdimensions.bungeesuitechat.managers.ChannelManager;

public class ToggleCommand implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command command,
			String label, String[] args) {
		if(args.length==0){
		ChannelManager.togglePlayersChannel(sender);
		}else{
			ChannelManager.togglePlayerToChannel(sender, args[0]);
		}
		return true;
	}

}
