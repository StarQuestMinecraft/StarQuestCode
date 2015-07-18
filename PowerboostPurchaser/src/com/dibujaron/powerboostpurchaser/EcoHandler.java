package com.dibujaron.powerboostpurchaser;

import net.milkbowl.vault.economy.Economy;

import org.bukkit.Bukkit;
import org.bukkit.plugin.RegisteredServiceProvider;


public class EcoHandler {
	private static Economy eco;
	
	public static int getCost(){
		return 100;
	}
	
	public static Economy getEconomy(){
		return eco;
	}
	
	public static void setupEconomy(){
		eco = getEco();
	}
	
	private static Economy getEco(){
	    RegisteredServiceProvider<Economy> economyProvider = Bukkit.getServer().getServicesManager().getRegistration(net.milkbowl.vault.economy.Economy.class);
	    if (economyProvider != null) {
	        return economyProvider.getProvider();
	    }

	    return null;
	}
}
