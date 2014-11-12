package com.dibujaron.worldrestore;

import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

public class Core extends JavaPlugin{
	
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if(sender.isOp()){
			if(cmd.getName().equalsIgnoreCase("Worldrestore")){
				if(args.length == 2){
					World from = Bukkit.getWorld(args[0]);
					World to = Bukkit.getWorld(args[1]);
					if(from != null && to != null){
						PluginManager pm = Bukkit.getPluginManager();
						boolean doWG = pm.getPlugin("WorldGuard") != null;
						boolean doTowny = pm.getPlugin("Towny") != null;
						boolean doFactions = pm.getPlugin("Factions") != null;
						WorldRestore r = new WorldRestore(to, from, doWG, doTowny, doFactions);
						r.runTaskTimer(this, 1,1);
						return true;
					}
				}
			} else if(cmd.getName().equalsIgnoreCase("deleteoldwgs")){
				if(sender instanceof Player){
					World w = ((Player) sender).getWorld();
					OldWGRemover.removeOldWGs(w);
					return true;
				}
			}
		}
		return false;
	}
}
