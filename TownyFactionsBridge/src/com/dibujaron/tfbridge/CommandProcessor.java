package com.dibujaron.tfbridge;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.massivecraft.factions.Factions;

public class CommandProcessor {
	public static boolean onCommandTowny(CommandSender sender, Command cmd, String label, String[] args) {
		String name = args[0];
		switch(name){
		case "map":
			Player p = (Player) sender;
			p.chat("/f map");
			return true;
		case "prices":
			sender.sendMessage(EconomyHandler.getPricesString());
			return true;
		}
		return false;
	}

	public static boolean onCommandTown(CommandSender sender, Command cmd, String label, String[] args) {
		case "new":
	}

	public static boolean onCommandPlot(CommandSender sender, Command cmd, String label, String[] args) {

	}

	public static boolean onCommandResident(CommandSender sender, Command cmd, String label, String[] args) {

	}

}
