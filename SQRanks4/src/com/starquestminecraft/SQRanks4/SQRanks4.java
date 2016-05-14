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

import net.milkbowl.vault.chat.Chat;
import net.milkbowl.vault.economy.Economy;
import net.milkbowl.vault.permission.Permission;

public class SQRanks4 extends JavaPlugin implements Listener{
	public static Permission permission;
	public static Economy eco;
	public static Chat chat;

	public void onEnable(){
		setupPermissions();
		setupEconomy();
		setupChat();
		Bukkit.getPluginManager().registerEvents(this, this);
		this.saveDefaultConfig();
	}
	
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args){
		if(cmd.getName().equalsIgnoreCase("rankup") && sender instanceof Player){
			return buyrank(sender, args);
		}
		return false;
	}
	
	public boolean buyrank(CommandSender sender, String[] args){
		Player player = (Player) sender;
		String[] ara_ranks = {"Arator0", "Arator1", "Arator2", "Arator3", "Arator4", "Arator5"};
		String[] req_ranks = {"Requiem0", "Requiem1", "Requiem2", "Requiem3", "Requiem4", "Requiem5"};
		String[] yav_ranks = {"Yavari0", "Yavari1", "Yavari2", "Yavari3", "Yavari4", "Yavari5"};

		if(permission.playerInGroup(player, "Arator5") || permission.playerInGroup(player, "Requiem5") || permission.playerInGroup(player, "Yavari5")){
			player.sendMessage(ChatColor.GOLD + "You are already at max rank");
			return false;
		}
		
		String current_rank = "", next_rank = "";
		for(int i = 0; i < 6; i++){
			if(permission.playerInGroup(player, ara_ranks[i])){
				current_rank = ara_ranks[i];
				next_rank = ara_ranks[i+1];
			}
			if(permission.playerInGroup(player, req_ranks[i])){
				current_rank = req_ranks[i];
				next_rank = req_ranks[i+1];
			}
			if(permission.playerInGroup(player, yav_ranks[i])){
				current_rank = yav_ranks[i];
				next_rank = yav_ranks[i+1];
			}
		}
		
		
		List <String> prereqs = getConfig().getStringList("ranks."+next_rank+".prereq");
		double price = getConfig().getDouble("ranks."+next_rank+".price");
		String skill = getConfig().getString("ranks."+next_rank+".skill");
		int level = getConfig().getInt("ranks."+next_rank+".level");
		//String group = getConfig().getString("ranks."+next_rank+".group");
		
		boolean has_prereqs = true;
		for(String rank : prereqs){
			if(!permission.playerInGroup(player, rank)){
				has_prereqs = false;
			}
		}
		
		if(skill.equals("all")) {
			if(eco.has(player, price) && ExperienceAPI.getPowerLevel(player) >= level && has_prereqs){
				permission.playerRemoveGroup(player, current_rank);
				permission.playerAddGroup(player, next_rank);
				eco.withdrawPlayer(player, price);
				player.sendMessage(ChatColor.GREEN + "You have bought the rank: " + chat.getGroupPrefix(player.getWorld(), next_rank));
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
					player.sendMessage(ChatColor.GOLD + chat.getGroupPrefix(player.getWorld(), rank));
				}
				
			}
		}
		else if(eco.has(player, price) && ExperienceAPI.getLevel(player, skill) >= level && has_prereqs){
			permission.playerRemoveGroup(player, current_rank);
			permission.playerAddGroup(player, next_rank);
			eco.withdrawPlayer(player, price);
			player.sendMessage(ChatColor.GREEN + "You have bought the rank: " + chat.getGroupPrefix(player.getWorld(), next_rank));
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
				player.sendMessage(ChatColor.GOLD + chat.getGroupPrefix(player.getWorld(), rank));
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
	
	private boolean setupChat() {
        RegisteredServiceProvider<Chat> chatProvider = getServer().getServicesManager().getRegistration(Chat.class);
        chat = chatProvider.getProvider();
        return chat != null;
    }
}
