package com.minecraftdimensions.bungeesuitechat.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import com.minecraftdimensions.bungeesuitechat.managers.PlayerManager;


public class TempMuteCommand implements CommandExecutor {
	
	@Override
	public boolean onCommand(CommandSender sender, Command command,
			String label, String[] args) {
		if(args.length>1){
			int time;
			try{
			time = Integer.parseInt(args[1]);
			}catch(Exception e){
				return false;
			}
			PlayerManager.tempMutePlayer(sender.getName(),args[0],time);
			return true;
		}
		return false;
	}

}
