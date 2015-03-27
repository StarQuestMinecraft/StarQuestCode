package com.starquestminecraft.sqcontracts;

import java.util.List;

import net.milkbowl.vault.economy.Economy;

import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;

import com.starquestminecraft.sqcontracts.database.Database;
import com.starquestminecraft.sqcontracts.randomizer.Randomizer;
import com.starquestminecraft.sqcontracts.randomizer.config.ConfigRandomizer;
import com.starquestminecraft.sqcontracts.util.ContractCompletionRunnable;
import com.starquestminecraft.sqcontracts.util.StationUtils;

public class SQContracts extends JavaPlugin {

	Database contractDatabase;

	private static SQContracts instance;

	private Economy economy = null;

	private Randomizer randomizer;
	
	public void onEnable() {
		instance = this;
		ConfigRandomizer.captureBaseSeed();
		StationUtils.setUp(getConfig());
		economy = registerEconomy();
		getCommand("contract").setExecutor(new ContractCommand());
		ContractCompletionRunnable r = new ContractCompletionRunnable();
		r.runTaskTimer(this, 20, 20);
		randomizer = new ConfigRandomizer();
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
}
