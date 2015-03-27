package com.minecraftdimensions.bungeesuitechat.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import com.minecraftdimensions.bungeesuitechat.managers.PlayerManager;

public class MessageCommand implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command command,
			String label, String[] args) {
		if (args.length > 1) {
			String message = "";
			for(String data: args){
				if(!data.equalsIgnoreCase(args[0])){
					message+=data+" ";
				}
			}
			PlayerManager.sendPrivateMessage(sender.getName(),args[0] ,message);
			return true;
		}
		return false;
	}

}
