package com.starquestminecraft.sqtowerdefence;

import java.util.ArrayList;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Entity;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;

import com.starquestminecraft.sqtechbase.SQTechBase;

import net.milkbowl.vault.economy.Economy;

public class SQTowerDefence extends JavaPlugin {
	
	public static ArrayList<Turret> turretTypes = new ArrayList<Turret>();
	public static SQTowerDefence sqtdInstance;
	public static ArrayList<Entity> projectileList = new ArrayList<Entity>();
	public static boolean isChecking = false;
	
	private Economy economy = null;
	
	@Override
	public void onEnable() {
		getLogger().info("SQTowerDefence has been enabled!");
		
		sqtdInstance = this;
		
		economy = registerEconomy();
		
		FileConfiguration sqtdConfig = this.getConfig();

		for(String tower : sqtdConfig.getConfigurationSection("turrets").getKeys(false)) {
			
			TurretType turretType = TurretType.BASE;
			
			
			ArrayList<Upgrade> buyableUpgrades = new ArrayList<Upgrade>();
			if(sqtdConfig.getConfigurationSection("turrets." + tower + ".upgrades") != null) {
				for(String upgrade : sqtdConfig.getConfigurationSection("turrets." + tower + ".upgrades").getKeys(false)) {
					
					UpgradeType upgradeType = UpgradeType.TOWER;
				
					Upgrade newUpgrade = new Upgrade(upgradeType.getUpgradeType(upgrade), sqtdConfig.getDouble("turrets." + tower + ".upgrades." + upgrade + ".cost"), 1.0, 0.0, 1);
					if(newUpgrade.getUpgradeType().equals(UpgradeType.TOWER)) {
						newUpgrade.setCustomName(upgrade.replaceAll("_", " "));
					}
					else {
						Integer maxLevel = sqtdConfig.getInt("turrets." + tower + ".upgrades." + upgrade + ".maxlevel");
						newUpgrade = new Upgrade(upgradeType.getUpgradeType(upgrade), sqtdConfig.getDouble("turrets." + tower + ".upgrades." + upgrade + ".cost"), sqtdConfig.getDouble("turrets." + tower + ".upgrades." + upgrade + ".multiplier"), sqtdConfig.getDouble("turrets." + tower + ".upgrades." + upgrade + ".bonus"), maxLevel);
					}
					
					buyableUpgrades.add(newUpgrade);
				
				}
			}
			
			ArrayList<Upgrade> conflictingUpgrades = new ArrayList<Upgrade>();
			if(sqtdConfig.getConfigurationSection("turrets." + tower + ".conflicting_upgrades") != null) {	
				for(String upgrade : sqtdConfig.getConfigurationSection("turrets." + tower + ".conflicting_upgrades").getKeys(false)) {
					
					UpgradeType upgradeType = UpgradeType.TOWER;
					
					Upgrade newUpgrade = new Upgrade(upgradeType.getUpgradeType(upgrade), sqtdConfig.getDouble("turrets." + tower + ".conflicting_upgrades." + upgrade + ".cost"), 1.0, 0.0, 1);
					if(newUpgrade.getUpgradeType().equals(UpgradeType.TOWER)) {
						newUpgrade.setCustomName(upgrade.replaceAll("_", " "));
					}
					else {
						Integer maxLevel = sqtdConfig.getInt("turrets." + tower + ".conflicting_upgrades." + upgrade + ".maxlevel");
						newUpgrade = new Upgrade(upgradeType.getUpgradeType(upgrade), sqtdConfig.getDouble("turrets." + tower + ".conflicting_upgrades." + upgrade + ".cost"), sqtdConfig.getDouble("turrets." + tower + ".conflicting_upgrades." + upgrade + ".multiplier"), sqtdConfig.getDouble("turrets." + tower + ".conflicting_upgrades." + upgrade + ".bonus"), maxLevel);
					}
				
					conflictingUpgrades.add(newUpgrade);
			
				}		
			}
			
			Turret turret = new Turret(turretType.getTurretType(sqtdConfig.getString("turrets." + tower + ".type")), buyableUpgrades, conflictingUpgrades, sqtdConfig.getInt("turrets." + tower + ".speed"), sqtdConfig.getDouble("turrets." + tower + ".damage"), sqtdConfig.getDouble("turrets." + tower + ".accuracy"), sqtdConfig.getDouble("turrets." + tower + ".cost"), sqtdConfig.getDouble("turrets." + tower + ".range"),  tower.replaceAll("_", " "));
			
			if(sqtdConfig.getInt("turrets." + tower + ".show") == 0) {
				turret.showInGUI = false;
			}
			else {
				turret.showInGUI = true;
			}
			
			turretTypes.add(turret);
			
		}
		
		Tower tower = new Tower(1000);
		SQTechBase.addMachineType(tower);
		
		new SQTDListener(this);
		
		
		
	}
	
	@Override
	public void onDisable() {
		getLogger().info("SQTowerDefence has been disabled!");
	}
	
	private Economy registerEconomy() {
		Economy retval = null;
		RegisteredServiceProvider<Economy> economyProvider = getServer().getServicesManager().getRegistration(net.milkbowl.vault.economy.Economy.class);
		if (economyProvider != null) {
			retval = economyProvider.getProvider();
		}

		return retval;
	}
	
	public Economy getEconomy() {
		return economy;
	}
	
	public static ArrayList<Turret> getTurretList() {
		return turretTypes;
	}
	
}
