package com.dibujaron.powerboostpurchaser;

import net.milkbowl.vault.economy.Economy;
import net.milkbowl.vault.economy.EconomyResponse;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import com.massivecraft.factions.entity.Faction;
import com.massivecraft.factions.entity.FactionColl;
import com.massivecraft.factions.entity.MPlayer;
import com.massivecraft.factions.integration.Econ;

public class PowerboostPurchaser extends JavaPlugin{
	

	Database database;
	
	private static PowerboostPurchaser instance;
	
	public void onEnable(){
		instance = this;
		database = new SQLDatabase();
		EcoHandler.setupEconomy();
		if(Bukkit.getServerName().equals("Trinitos_Alpha")){
			UpdateTask.schedule();
		}
	}
	
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if(!(sender instanceof Player)) return false;
		Player p = (Player) sender;
		if(cmd.getName().equalsIgnoreCase("powerboost")){
			if(args.length < 1){
				sender.sendMessage("Powerboosts cost " + EcoHandler.getCost() + " per power per day.");
				return true;
			}
			String fp = args[0].toLowerCase();
			if(fp.equals("faction")){
				Faction f = getFaction(p);
				if(f == null || f == FactionColl.get().getNone()){
					p.sendMessage("You are not in a faction.");
					return true;
				}
				if(args.length < 2){
					viewFactionBoost(f, p);
					return true;
				}
				if(!checkFactionPermissions(f, p)){
					p.sendMessage("You do not have authority to purchase or cancel boosts for your faction.");
					return true;
				}
				String subcommand = args[1].toLowerCase();
				if(subcommand.equals("purchase")){
					try{
						int power = Integer.parseInt(args[2]);
						purchaseFactionBoost(p, f, power);
						return true;
					} catch (NumberFormatException e){
						p.sendMessage("Invalid amount of power.");
						return true;
					}
				}
				if(subcommand.equals("cancel")){
					cancelFactionBoost(p, f);
					return true;
				}
			} else if(fp.equals("personal")){
				if(args.length < 2){
					viewPersonalBoost(p);
					return true;
				}
				String subcommand = args[1].toLowerCase();
				if(subcommand.equals("purchase")){
					try{
						int power = Integer.parseInt(args[2]);
						purchasePersonalBoost(p, power);
						return true;
					} catch (Exception e){
						p.sendMessage("Invalid amount of power.");
						return true;
					}
				}
				if(subcommand.equals("cancel")){
					cancelPersonalBoost(p);
					return true;
				}
			}
		} else if (cmd.getName().equalsIgnoreCase("taxes")){
			Faction f = getFaction(p);
			if(args.length < 1){
				displayTaxes(f, p);
				return true;
			} else {
				if(args.length == 2 && args[0].equals("set")){
					if(checkFactionPermissions(f, p)){
						try{
							int i = Integer.parseInt(args[1]);
							setTaxes(f, p, i);
							return true;
						} catch (NumberFormatException e){
							p.sendMessage("Invalid tax number.");
							return true;
						}
					} else {
						p.sendMessage("You cannot set your faction's taxes.");
						return true;
					}
				}
			}
		}
		return false;
	}

	private void setTaxes(Faction f, Player p, int i) {
		database.setTaxesOfFaction(f, i);
		p.sendMessage("Your faction's tax rate has been set to " + i + ". Taxes are collected once per day and put in the faction bank.");
	}

	private void displayTaxes(Faction f, Player p) {
		int taxes = database.getTaxesOfFaction(f);
		p.sendMessage("Your faction's tax rate is " + taxes + " per day. This is taken from your account once a day and put in the faction bank. If you cannot pay it you will be kicked.");
	}

	private void cancelPersonalBoost(Player p) {
		database.setBoostOfPlayer(new PersonalPowerboost(p.getUniqueId(), 0));
		p.sendMessage("Your powerboost has been cancelled. It will be removed the next time costs are collected.");
	}

	private void purchasePersonalBoost(Player p, int power) {
		p.sendMessage("Personal boosts are currently disabled.");
		return;
		/*
		PersonalPowerboost b = new PersonalPowerboost(p.getUniqueId(), power);
		Economy eco = EcoHandler.getEconomy();
		EconomyResponse r = eco.withdrawPlayer(p, b.getBoost() * EcoHandler.getCost());
		if(r.transactionSuccess()){
			p.sendMessage("Your powerboost has been set and you have been charged for the first day.");
			database.setBoostOfPlayer(b);
		} else {
			p.sendMessage("You cannot afford this powerboost.");
		}*/
	}

	private void viewPersonalBoost(Player p) {
		Powerboost b = database.getBoostOfPlayer(p);
		if(b == null) p.sendMessage("You have no currently active boost.");
		else p.sendMessage("Your currently active boost is " + b.getBoost() + ".");
		
	}

	private void cancelFactionBoost(Player p, Faction f) {
		database.setBoostOfFaction(new FactionPowerboost(f, 0));
		Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "f powerboost f " + f.getName() + " " + 0);
		p.sendMessage("Your faction powerboost has been cancelled. It will be removed the next time costs are collected.");
	}

	private void purchaseFactionBoost(Player p, Faction f, int power) {
		FactionPowerboost b = new FactionPowerboost(f, power);
		if(Econ.hasAtLeast(f, b.getBoost() * EcoHandler.getCost(), "to purchase powerboost")){
			Econ.modifyMoney(f, -1 * b.getBoost() * EcoHandler.getCost(), "purchasing powerboost");
			p.sendMessage("Your faction powerboost has been set and you have been charged for the first day.");
			Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "f powerboost f " + f.getName() + " " + power);
			database.setBoostOfFaction(b);
		} else {
			p.sendMessage("You cannot afford this powerboost.");
		}
	}

	private boolean checkFactionPermissions(Faction f, Player p) {
		return f.getLeader().getUuid().equals(p.getUniqueId());
	}

	private void viewFactionBoost(Faction f, Player p) {
		Powerboost b = database.getBoostOfFaction(f);
		if(b == null) p.sendMessage("You have no currently active boost in your faction.");
		else p.sendMessage("The currently active boost on faction " + f.getName() + ChatColor.WHITE + " is " + b.getBoost() + ".");
	}

	private Faction getFaction(Player p) {
		return MPlayer.get(p).getFaction();
	}
	
	public static PowerboostPurchaser get(){
		return instance;
	}
	
	public static void janeMessage(String message){
		Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "eb janesudo " + message);
	}
}


