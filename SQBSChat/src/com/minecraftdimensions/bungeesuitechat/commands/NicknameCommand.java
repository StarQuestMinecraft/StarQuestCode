package com.minecraftdimensions.bungeesuitechat.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import com.minecraftdimensions.bungeesuitechat.managers.PlayerManager;

public class NicknameCommand implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command command,
			String label, String[] args) {
		if (args.length == 1) {
			PlayerManager.nicknamePlayer(sender.getName(), sender.getName(),args[0], true);
			return true;
		}
		if (args.length == 2) {
			if (!sender.hasPermission("bungeesuite.chat.command.nickname.other")) {
				sender.sendMessage(command.getPermissionMessage());
				return true;
			}else{
				PlayerManager.nicknamePlayer(sender.getName(), args[0], args[1], true);
			return true;
			}
		}
		return false;
	}

}
