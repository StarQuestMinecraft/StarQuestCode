package com.dibujaron.alliances;

import net.milkbowl.vault.economy.Economy;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.RegisteredServiceProvider;

public class EconUtils {
	public static Economy economy;
	
	public static void setUp(){
		setupEconomy();
	}
	
	
	
	private static boolean setupEconomy(){
        RegisteredServiceProvider<Economy> economyProvider = Bukkit.getServer().getServicesManager().getRegistration(net.milkbowl.vault.economy.Economy.class);
        if (economyProvider != null) {
            economy = economyProvider.getProvider();
        }

        return (economy != null);
    }
	
	public static void createNewAccount(String holder){
		boolean b = economy.createPlayerAccount(holder);
	}
	
	public static void withdraw(Player sender, String holder, double amount){
		if(economy.has(holder, amount)){
			economy.withdrawPlayer(holder, amount);
			economy.depositPlayer(sender.getName(), amount);
		} else {
			sender.sendMessage("There is not this much money in the account.");
		}
	}
	
	public static void deposit(Player sender, String holder, double amount){
		if(economy.has(sender.getName(), amount)){
			economy.withdrawPlayer(sender.getName(), amount);
			economy.depositPlayer(holder, amount);
		} else {
			sender.sendMessage("There is not this much money in the account.");
		}
	}
	public static void balance(Player sender){
		String alliance = Database.getAllianceOfPlayer(sender.getName());
		double bal = economy.getBalance(alliance);
		sender.sendMessage("Your alliance's balance is " + bal + ".");
	}
}
