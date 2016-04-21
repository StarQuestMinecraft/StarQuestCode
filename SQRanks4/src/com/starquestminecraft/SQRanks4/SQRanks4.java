package com.starquestminecraft.SQRanks4;

import com.gmail.nossr50.api.ExperienceAPI;

import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;

import net.milkbowl.vault.economy.Economy;
import net.milkbowl.vault.permission.Permission;

public class SQRanks4 extends JavaPlugin implements Listener{
	public static Permission permission;
	public static Economy eco;

	public void onEnable(){
		setupPermissions();
		setupEconomy();
		Bukkit.getPluginManager().registerEvents(this, this);
		this.saveDefaultConfig();
	}
	
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args){
		if(cmd.getName().equalsIgnoreCase("buyrank") && sender instanceof Player){
			return buyrank(sender, args);
		}
		return false;
	}
	
	public boolean buyrank(CommandSender sender, String[] args){
		Player player = (Player) sender;
		if(args.length != 1){
			return false;
		}
		if(getConfig().getString("ranks."+args[0]+".group") == null){
			player.sendMessage(ChatColor.GOLD + "Rank not found");
			return true;
		}
		
		List <String> prereqs = getConfig().getStringList("ranks."+args[0]+".prereq");
		double price = getConfig().getDouble("ranks."+args[0]+".price");
		String skill = getConfig().getString("ranks."+args[0]+".skill");
		int level = getConfig().getInt("ranks."+args[0]+".level");
		String group = getConfig().getString("ranks."+args[0]+".group");
		
		boolean has_prereqs = true;
		for(String rank : prereqs){
			if(!permission.playerInGroup(player, rank)){
				has_prereqs = false;
			}
		}
		
		if(skill.equals("all")) {
			if(eco.has(player, price) && ExperienceAPI.getPowerLevel(player) >= level && has_prereqs){
				permission.playerAddGroup(player, group);
				eco.withdrawPlayer(player, price);
				player.sendMessage(ChatColor.GREEN + "You have bought the rank: " + args[0]);
			}
			else if(ExperienceAPI.getPowerLevel(player) < level){
				player.sendMessage(ChatColor.GOLD + "This rank requires a total power level of at least " + Integer.toString(level));
			}
			else if(!eco.has(player, price)){
				player.sendMessage(ChatColor.GOLD + "You cannot afford this rank, it costs " + Double.toString(price) + " credits");
			}
			else if(!has_prereqs){
				player.sendMessage(ChatColor.GOLD + "You are missing one or more of the following prerequsite ranks:");
				for(String rank : prereqs){
					player.sendMessage(ChatColor.GOLD + rank);
				}
				
			}
		}
		else if(eco.has(player, price) && ExperienceAPI.getLevel(player, skill) >= level && has_prereqs){
			permission.playerAddGroup(player, group);
			eco.withdrawPlayer(player, price);
			player.sendMessage(ChatColor.GREEN + "You have bought the rank: " + args[0]);
		}
		else if(ExperienceAPI.getLevel(player, skill) < level){
			player.sendMessage(ChatColor.GOLD + "This rank requires a " + skill + " level of at least " + Integer.toString(level));
		}
		else if(!eco.has(player, price)){
			player.sendMessage(ChatColor.GOLD + "You cannot afford this rank, it costs " + Double.toString(price) + " credits");
		}
		else if(!has_prereqs){
			player.sendMessage(ChatColor.GOLD + "You are missing one or more of the following prerequsite ranks:");
			for(String rank : prereqs){
				player.sendMessage(ChatColor.GOLD + rank);
			}
			
		}
		return true;
	}
	
	private boolean setupPermissions() {
		RegisteredServiceProvider<Permission> permissionProvider = getServer().getServicesManager().getRegistration(net.milkbowl.vault.permission.Permission.class);
		if (permissionProvider != null) {
			permission = permissionProvider.getProvider();
		}
		return (permission != null);
	}

	private boolean setupEconomy(){
		RegisteredServiceProvider<Economy> ecoProvider = getServer().getServicesManager().getRegistration(net.milkbowl.vault.economy.Economy.class);
		if (ecoProvider != null) {
			eco = ecoProvider.getProvider();
		}
		return (eco != null);
	}
}
