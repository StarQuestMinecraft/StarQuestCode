package com.minecraftdimensions.bungeesuitechat.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import com.minecraftdimensions.bungeesuitechat.managers.PlayerManager;

public class NicknameOffCommand implements CommandExecutor {
	
	@Override
	public boolean onCommand(CommandSender sender, Command command,
			String label, String[] args) {
		if (args.length == 0) {
			PlayerManager.nicknamePlayer(sender.getName(), sender.getName(),"", false);
			return true;
		}
		if (args.length == 1) {
			if (!sender.hasPermission("bungeesuite.chat.command.nickname.other")) {
				sender.sendMessage(command.getPermissionMessage());
				return true;
			}else{
				PlayerManager.nicknamePlayer(sender.getName(), args[0], "", false);
			return true;
			}
		}
		return false;
	}

}
