package com.dibujaron.alliances;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.concurrent.Semaphore;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import com.massivecraft.factions.entity.Faction;
import com.massivecraft.factions.entity.UPlayer;

public class Core extends JavaPlugin {
	
	public static ArrayList<Player> playersAllianceChat = new ArrayList<Player>();
	
	private static Core instance;
	
	public static Semaphore lock = new Semaphore(1);
	
	public void onEnable(){
		instance = this;
	}
	
	public void onDisable(){
		
	}
	
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if(cmd.getName().equalsIgnoreCase("Alliance") || cmd.getName().equalsIgnoreCase("a")){
			if(sender instanceof Player){
				Player p = (Player) sender;
				switch(args[0]){
				case "create": createNew(p, args[1]); return true;
				case "delete": delete(p); return true;
				case "invite": invite(p, args[1]); return true;
				case "join": join(p); return true;
				case "members": members(p); return true;
				case "list": list(p); return true;
				case "bank": EconUtils.balance(p); return true;
				case "withdraw": withdraw(p, args[1]); return true;
				case "deposit": deposit(p, args[1]); return true;
				case "chat":
				case "c:":
					toggleChat(p); return true;
				}
			}
		}
		return false;
	}
	public static void createNew(Player sender, String name){
		String organization = OrganizationUtils.getOrganizationPlayerOwn(sender.getName());
		if(organization == null){
			sender.sendMessage("You are not the owner of an organization on this planet.");
			return;
		}
		if(name.length() > 16){
			sender.sendMessage("Your organization name is too long. It can only be 16 characters.");
		}
		sender.sendMessage("Created a new organization!");
		EconUtils.createNewAccount(name);
		Database.saveNewAlliance(name, organization);
	}
	public static void delete(Player sender){
		String alliance = Database.getAllianceOfPlayer(sender.getName());
		if(alliance == null){
			sender.sendMessage("You are not in an alliance.");
			return;
		}
		if(OrganizationUtils.isOwnerOfAlliance(sender.getName(),  alliance)){
			sender.sendMessage("Deleted your alliance!");
			Database.deleteAlliance(alliance);
		}
	}
	public static void invite(Player sender, String invitee){
		String alliance = Database.getAllianceOfPlayer(sender.getName());
		if(alliance == null){
			sender.sendMessage("You are not in an alliance.");
			return;
		}
		if(OrganizationUtils.isOwnerOfAlliance(sender.getName(),  alliance)){
			sender.sendMessage("Invite sent to " + invitee + "!");
			//TODO add invite timeout
			Database.addAllianceInvite(alliance, invitee);
		}
	}
	public static void join(Player sender){
		String organization = OrganizationUtils.getOrganizationPlayerOwn(sender.getName());
		if(organization == null){
			sender.sendMessage("You cannot join this alliance, you have no organization.");
			return;
		}
		String alliance = Database.getAllianceInvitedTo(organization);
		if(alliance == null){
			sender.sendMessage("You have not been invited to any alliance.");
			return;
		}
		Database.addOrganization(alliance, organization);
	}
	
	public static void members(Player sender){
		String playerAlliance = Database.getAllianceOfPlayer(sender.getName());
		if(playerAlliance == null){
			sender.sendMessage("You have no alliance!");
			return;
		}
		ArrayList<String> members = Database.getOrganizations(playerAlliance);
		sender.sendMessage("Your alliance contains the following organizations:");
		for(String s : members){
			sender.sendMessage(s);
		}
	}
	public static void list(Player sender){
		ArrayList<String> alliances = Database.getAllAlliances();
		sender.sendMessage("The alliances currently existing are as follows:");
		for(String s : alliances){
			sender.sendMessage(s);
		}
	}
	public static void withdraw(Player sender, String amount){
		String alliance = Database.getAllianceOfPlayer(sender.getName());
		if(alliance == null){
			sender.sendMessage("You are not in an alliance.");
			return;
		}
		ArrayList<String> organizations = Database.getOrganizations(alliance);
		String organization = OrganizationUtils.getOrganizationPlayerOwn(sender.getName());
		if(organization != null && organizations.contains(organization)){
			try{
				double d = Double.parseDouble(amount);
				EconUtils.withdraw(sender, alliance, d);
				sender.sendMessage("Withdrew " + d + " from your alliance bank!");
				return;
			} catch (NumberFormatException e){
				sender.sendMessage("Please enter a valid number amount.");
				return;
			}
			
		} else {
			sender.sendMessage("You are not in this alliance or are not the owner of an organization in this alliance.");
		}
	}
	public static void deposit(Player sender, String amount){
		String alliance = Database.getAllianceOfPlayer(sender.getName());
		if(alliance == null){
			sender.sendMessage("You are not in an alliance.");
			return;
		}
		ArrayList<String> organizations = Database.getOrganizations(alliance);
		String organization = OrganizationUtils.getOrganizationOfPlayer(sender.getName());
		if(organization != null && organizations.contains(organization)){
			try{
				double d = Double.parseDouble(amount);
				EconUtils.deposit(sender, alliance, d);
				return;
			} catch (NumberFormatException e){
				sender.sendMessage("Please enter a valid number amount.");
				return;
			}
			
		} else {
			sender.sendMessage("You are not in this alliance");
		}
	}
	
	public static void chat(String sender, String message){
		String playerAlliance = Database.getAllianceOfPlayer(sender);
		if(playerAlliance != null){
			for(Player p : Bukkit.getServer().getOnlinePlayers()){
				if(Database.getAllianceOfPlayer(p.getName()).equals(playerAlliance)){
					p.sendMessage(format(sender,message));
				}
			}
		}
	}

	public static String format(String sender, String message){
		return ChatColor.LIGHT_PURPLE + "[Alliance] " + sender + ": " + message;
	}
	public static void toggleChat(Player p){
		try{
		lock.acquire();
		boolean allianceChatting = playersAllianceChat.contains(p);
		if(!allianceChatting){
			playersAllianceChat.add(p);
			p.sendMessage(ChatColor.LIGHT_PURPLE + "Alliance chat enabled.");
		}
		else{
			playersAllianceChat.remove(p);
			p.sendMessage(ChatColor.LIGHT_PURPLE + "Alliance chat disabled.");
		}
		lock.release();
		} catch (Exception e){
			e.printStackTrace();
		}
	}
	public static Core getInstance(){
		return instance;
	}
}
