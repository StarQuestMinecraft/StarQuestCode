package com.starquestminecraft.sqcontracts;

import java.io.File;
import java.util.UUID;

import net.countercraft.movecraft.database.StarshipData;
import net.countercraft.movecraft.event.CraftSignBreakEvent;
import net.md_5.bungee.api.ChatColor;
import net.milkbowl.vault.economy.Economy;

import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;

import com.starquestminecraft.sqcontracts.database.Database;
import com.starquestminecraft.sqcontracts.database.SQLDatabase;
import com.starquestminecraft.sqcontracts.randomizer.Randomizer;
import com.starquestminecraft.sqcontracts.randomizer.config.ConfigRandomizer;
import com.starquestminecraft.sqcontracts.randomizer.function.FunctionRandomizer;
import com.starquestminecraft.sqcontracts.util.ContractCompletionRunnable;
import com.starquestminecraft.sqcontracts.util.ShipDataCore;
import com.starquestminecraft.sqcontracts.util.StationUtils;

public class SQContracts extends JavaPlugin implements Listener{

	Database contractDatabase;

	private static SQContracts instance;

	private Economy economy = null;

	private Randomizer randomizer;
	
	public void onEnable() {
		instance = this;
		saveDefaultConfig();
		if ( !new File( getDataFolder() + "shipclassdata.txt" ).exists() ) {
			this.saveResource( "shipclassdata.txt", false );
		}
		if ( !new File( getDataFolder() + "itemdata.txt" ).exists() ) {
			this.saveResource( "itemdata.txt", false );
		}
		Randomizer.captureBaseSeed();
		StationUtils.setUp(getConfig());
		economy = registerEconomy();
		getCommand("contract").setExecutor(new ContractCommand());
		ContractCompletionRunnable r = new ContractCompletionRunnable();
		r.runTaskTimer(this, 20, 20);
		randomizer = new FunctionRandomizer();
		contractDatabase = new SQLDatabase();
		getServer().getPluginManager().registerEvents(this, this);
	}

	public static SQContracts get() {
		return instance;
	}
	
	public Randomizer getRandomizer(){
		return randomizer;
	}

	public Database getContractDatabase() {
		return contractDatabase;
	}

	private Economy registerEconomy() {
		Economy retval = null;
		RegisteredServiceProvider<Economy> economyProvider = getServer().getServicesManager().getRegistration(net.milkbowl.vault.economy.Economy.class);
		if (economyProvider != null) {
			retval = economyProvider.getProvider();
		}

		return retval;
	}
	
	public Economy getEconomy(){
		return economy;
	}
	
	@EventHandler(priority = EventPriority.MONITOR)
	public void onSignBreak( final CraftSignBreakEvent e ) {
		if(!e.isBrokenByOwner() && e.isWithinKillCooldown()){
			StarshipData d = e.getData();
			ShipDataCore.createShipDataCore(e.getPlayer(), d);
			UUID captain = d.getCaptain();
			Bukkit.broadcastMessage(ChatColor.RED + e.getPlayer().getName() + " destroyed " + Bukkit.getOfflinePlayer(captain).getName() + "'s " + d.getType() + " Controls!");
			System.out.print("Debug: 4");
		}
		System.out.print("Debug: 3");
	}
}
