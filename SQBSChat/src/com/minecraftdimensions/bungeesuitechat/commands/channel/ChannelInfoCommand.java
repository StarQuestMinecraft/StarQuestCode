package com.minecraftdimensions.bungeesuitechat.commands.channel;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import com.minecraftdimensions.bungeesuitechat.managers.ChannelManager;
import com.minecraftdimensions.bungeesuitechat.managers.PlayerManager;
import com.minecraftdimensions.bungeesuitechat.objects.BSPlayer;

public class ChannelInfoCommand implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command command,
			String label, String[] args) {
		BSPlayer p = PlayerManager.getPlayer(sender);
		if(args.length==0){
			ChannelManager.getChannelInfo(sender, p.getChannelName());
		}else{
			ChannelManager.getChannelInfo(sender, args[0]);
		}
		return true;
	}

}
