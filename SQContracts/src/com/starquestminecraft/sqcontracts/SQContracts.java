package com.starquestminecraft.sqcontracts;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import net.milkbowl.vault.economy.Economy;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;

import com.starquestminecraft.sqcontracts.contracts.pending.PendingContract;
import com.starquestminecraft.sqcontracts.contracts.pending.PendingItemContract;
import com.starquestminecraft.sqcontracts.contracts.pending.PendingMoneyContract;
import com.starquestminecraft.sqcontracts.contracts.pending.PendingShipCaptureContract;
import com.starquestminecraft.sqcontracts.database.Database;
import com.starquestminecraft.sqcontracts.util.ContractCompletionRunnable;
import com.starquestminecraft.sqcontracts.util.ContractRandomizer;
import com.starquestminecraft.sqcontracts.util.StationUtils;

public class SQContracts extends JavaPlugin {

	List<PendingContract> availableContracts;
	Database contractDatabase;

	private static SQContracts instance;

	private Economy economy = null;

	public void onEnable() {
		instance = this;
		ContractRandomizer.captureBaseSeed();
		StationUtils.setUp(getConfig());
		availableContracts = loadConfig();
		economy = registerEconomy();
		getCommand("contract").setExecutor(new ContractCommand());
		ContractCompletionRunnable r = new ContractCompletionRunnable();
		r.runTaskTimer(this, 20, 20);
	}

	public static SQContracts get() {
		return instance;
	}

	private List<PendingContract> loadConfig() {
		List<PendingContract> retval = new ArrayList<PendingContract>();
		FileConfiguration c = getConfig();
		Set<String> keys = c.getConfigurationSection("contracts").getKeys(true);
		for (String s : keys) {
			String type = c.getString("contracts." + s + ".type");
			PendingContract contract;
			switch (type.toLowerCase()) {
			case "item":
				contract = new PendingItemContract(c, s);
				retval.add(contract);
				continue;
			case "capture":
				contract = new PendingShipCaptureContract(c, s);
				retval.add(contract);
				continue;
			case "money":
				contract = new PendingMoneyContract(c, s);
				retval.add(contract);
				continue;
			default:
				continue;
			}
		}
		return retval;
	}

	public List<PendingContract> getAvailableContracts() {
		return availableContracts;
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
