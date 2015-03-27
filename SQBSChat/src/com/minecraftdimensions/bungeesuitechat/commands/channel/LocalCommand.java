package com.minecraftdimensions.bungeesuitechat.commands.channel;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import com.minecraftdimensions.bungeesuitechat.managers.ChannelManager;
import com.minecraftdimensions.bungeesuitechat.managers.PlayerManager;
import com.minecraftdimensions.bungeesuitechat.objects.BSPlayer;
import com.minecraftdimensions.bungeesuitechat.objects.ServerData;

public class LocalCommand implements CommandExecutor {


	@Override
	public boolean onCommand(CommandSender sender, Command command,
			String label, String[] args) {
		if(args.length>0){
			String message = "";
			for(String data: args){
					message+=data+" ";
			}
			if(message.charAt(0)=='/'){
				message=" "+message;
			}
			BSPlayer p = PlayerManager.getPlayer(sender);
			String channel = p.getChannelName();
			p.setChannel(ServerData.getServerName()+" Local");
			p.getPlayer().chat(message);
			p.setChannel(channel);
		}else{
			ChannelManager.togglePlayerToChannel(sender, ServerData.getServerName()+" Local");
		}
		return true;
	}

}
