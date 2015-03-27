package com.minecraftdimensions.bungeesuitechat.commands;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.minecraftdimensions.bungeesuitechat.managers.PlayerManager;

public class MeCommand implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command command,
			String label, String[] args) {
		if(PlayerManager.getPlayer(sender).isMuted()){
			return true;
		}
		if(args.length>0){
			String message =ChatColor.GRAY+" *"+sender.getName()+" ";
			for(String arg: args){
				message+= arg+" ";
			}
			Player p =(Player) sender;
			for(Player pl:p.getWorld().getPlayers()){
				pl.sendMessage(message);
			}
			return true;
		}
		return false;
	}

}
