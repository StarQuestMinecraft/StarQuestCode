
package com.regalphoenixmc.SQRankup;

import net.milkbowl.vault.VaultEco;
import net.milkbowl.vault.economy.Economy;
import net.milkbowl.vault.permission.Permission;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;

public class SQRankup extends JavaPlugin implements Listener {

	public static Permission permission = null;
	public static Economy economy = null;
	public static SQRankup instance;
	public static VaultEco vaultEco;

	public void onEnable() {

		saveDefaultConfig();
		getServer().getPluginManager().registerEvents(this, this);
		setupEconomy();
		setupPermissions();
		instance = this;
		Database.setUp();

	}

	public static SQRankup instance() {

		return instance;
	}

	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {

		if ((cmd.getName().equalsIgnoreCase("rankup")) && ((sender instanceof Player))) {
			Player p = (Player) sender;
			String rank = getRank(p);
			String nextRank = getNextRank(rank);
			if (nextRank == null) {
				p.sendMessage(ChatColor.RED + "You are unable to rank up.");
				return true;
			}
			if (rank.equalsIgnoreCase("SETTLER")) {
				if (args.length == 0) {
					sender.sendMessage(ChatColor.RED + " You need to choose a rank path! do /rankup Pirate or /rankup Colonist");
					return true;
				}
				if (args[0].equalsIgnoreCase("Colonist")) {
					nextRank = "COLONIST";
				} else if (args[0].equalsIgnoreCase("Pirate")) {
					nextRank = "PIRATE";
				} else {
					sender.sendMessage("You must choose either pirate or colonist. Invalid argument.");
					return true;
				}
			}
			if (nextRank.equalsIgnoreCase("SETTLER")) {
				sender.sendMessage(ChatColor.RED + "To rank up to settler, you must apply to the server on our minecraft forums thread");
				sender.sendMessage(ChatColor.GOLD + "http://tinyurl.com/starquestapps");
				return true;
			}
			int moneyRequirement = getMonetaryCost(nextRank);
			int killsRequirement = getKillRequirement(nextRank);
			RankupPlayer entry = Database.getEntry(p.getName());
			int killsFound = entry.getKills();
			double moneyFound = economy.getBalance(p);
			if ((killsFound >= killsRequirement) && (moneyFound >= moneyRequirement)) {
				getServer().broadcastMessage(ChatColor.RED + sender.getName() + " has ranked up to " + nextRank.toString().toLowerCase() + "!");
				permission.playerAddGroup(p, nextRank);
				permission.playerRemoveGroup(p, rank);
				economy.withdrawPlayer(p, moneyRequirement);
				entry = Database.getEntry(p.getName());
				entry.setKills(killsFound - killsRequirement);
				entry.saveData();
			} else {
				sender.sendMessage("Current money: " + moneyFound + " Required Money: " + moneyRequirement + " Current Kills: " + killsFound
						+ " Required Kills: " + killsRequirement);
			}
			return true;
		}
		/*
				if (cmd.getName().equalsIgnoreCase("rankupTransfer") && sender.hasPermission("transfer")) {
					List<String> players = Database.getAllPlayers();
					List<OfflinePlayer> offlinePlayers = new ArrayList<OfflinePlayer>();

					for (String ps : players) {

						offlinePlayers.add(getServer().getOfflinePlayer(ps));
					}

					for (int i = 0; i < offlinePlayers.size(); i++) {
						Database.transferPlayer(offlinePlayers.get(i), players.get(i));
					}

					sender.sendMessage("Done");

				}
		*/
		if ((cmd.getName().equalsIgnoreCase("addapp")) && (sender.hasPermission("SQRankup.addApplication"))) {
			String rank = getRank(getServer().getOfflinePlayer(args[0]));
			String nextRank = getNextRank(rank);
			if (args.length >= 1) {
				getServer().broadcastMessage(ChatColor.RED + args[0] + " has ranked up to settler!");
				getServer().broadcastMessage(
						ChatColor.RED + "Are you a " + ChatColor.GREEN + "Refugee" + ChatColor.RED + "? Rank up to " + ChatColor.DARK_GREEN + "Settler"
								+ ChatColor.RED + " like " + args[0] + " did!");
				getServer().broadcastMessage(
						ChatColor.RED + "Visit " + ChatColor.BLUE + "http://tinyurl.com/starquestapps" + ChatColor.RED + " to apply for Settler rank!");

				permission.playerAddGroup(null, getServer().getOfflinePlayer(args[0]), nextRank);
				permission.playerRemoveGroup(null, getServer().getOfflinePlayer(args[0]), rank);
				if (args.length >= 2) {
					Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "money give " + args[1] + " 10000");
					getServer().broadcastMessage(
							ChatColor.GOLD + args[1] + ChatColor.RED + " brought " + args[0] + " to the server and earned 10000 for doing so!");
				}
				return true;
			}
			sender.sendMessage("Needs an argument.");
			return false;
		}
		return false;
	}

	@EventHandler
	public void onPlayerKill(PlayerDeathEvent event) {

		Entity killer = event.getEntity().getKiller();
		if ((killer instanceof Player)) {
			Player p = (Player) killer;
			RankupPlayer entry;
			if (!Database.hasKey(p.getName())) {
				entry = new RankupPlayer(p.getName(), 0L, "", 0);
				entry.saveNew();
			} else {
				entry = Database.getEntry(p.getName());
				String lastKill = entry.getLastKillName();
				if ((lastKill != null) && (lastKill.equals(event.getEntity().getName())) && (System.currentTimeMillis() - entry.getLastKillTime() < 180000.0D)) {
					((Player) killer).sendMessage("You've already killed this guy in the last half hour. Lay off, dude. This kill wasn't counted.");
					return;
				}
			}
			System.out.println("Kill call: " + rankToKills(((Player) event.getEntity()).getName()));
			entry.setKills(entry.getKills() + rankToKills(((Player) event.getEntity()).getName()));
			entry.setLastKillName(event.getEntity().getName());
			entry.setLastKillTime(System.currentTimeMillis());
			entry.saveData();
			((Player) killer).sendMessage(ChatColor.RED + "This kill was counted in the ranking system as " + rankToKills(event.getEntity().getName())
					+ ". You have " + entry.getKills() + " kills total.");
			return;
		}
	}

	private int rankToKills(String name) {

		int i = 1;
		String[] groups = permission.getPlayerGroups(null, getServer().getOfflinePlayer(name));
		for (String p : groups) {
			switch (p.toLowerCase()) {
				case "refugee":
					i = -3;
					break;
				case "settler":
					i = 1;
					break;
				case "colonist":
				case "pirate":
					i = 2;
					break;
				case "corsair":
				case "citizen":
					i = 3;
					break;
				case "buccaneer":
				case "affluent":
					i = 4;
					break;
				case "warlord":
				case "tycoon":
					i = 4;
					break;
				default:
					break;

			}
		}
		return i;
	}

	// method to get the next rank on the rank structure
	public String getNextRank(String rank) {

		switch (rank.toUpperCase()) {
			case "REFUGEE":
				return "SETTLER";
			case "SETTLER":
				return "AMBIG";
			case "PIRATE":
				return "CORSAIR";
			case "CORSAIR":
				return "BUCCANEER";
			case "BUCCANEER":
				return "WARLORD";
			case "COLONIST":
				return "CITIZEN";
			case "CITIZEN":
				return "AFFLUENT";
			case "AFFLUENT":
				return "TYCOON";
			case "WARLORD":
			case "TYCOON":
				return null;

			default:
				return null;
		}
	}

	// method for getting monetary cost of rankup
	public int getMonetaryCost(String rank) {

		switch (rank.toUpperCase()) {
			case "REFUGEE":
				return getConfig().getInt("refugee.cost");
			case "SETTLER":
				return getConfig().getInt("settler.cost");
			case "PIRATE":
				return getConfig().getInt("pirate.cost");
			case "CORSAIR":
				return getConfig().getInt("corsair.cost");
			case "BUCCANEER":
				return getConfig().getInt("buccaneer.cost");
			case "COLONIST":
				return getConfig().getInt("colonist.cost");
			case "CITIZEN":
				return getConfig().getInt("citizen.cost");
			case "AFFLUENT":
				return getConfig().getInt("affluent.cost");
			case "WARLORD":
				return getConfig().getInt("warlord.cost");
			case "TYCOON":
				return getConfig().getInt("tycoon.cost");
			default:
				return 0;
		}
	}

	public int getKillRequirement(String rank) {

		switch (rank.toUpperCase()) {
			case "REFUGEE":
				return getConfig().getInt("refugee.kills");
			case "SETTLER":
				return getConfig().getInt("settler.kills");
			case "PIRATE":
				return getConfig().getInt("pirate.kills");
			case "CORSAIR":
				return getConfig().getInt("corsair.kills");
			case "BUCCANEER":
				return getConfig().getInt("buccaneer.kills");
			case "COLONIST":
				return getConfig().getInt("colonist.kills");
			case "CITIZEN":
				return getConfig().getInt("citizen.kills");
			case "AFFLUENT":
				return getConfig().getInt("affluent.kills");
			case "WARLORD":
				return getConfig().getInt("warlord.kills");
			case "TYCOON":
				return getConfig().getInt("tycoon.kills");
			default:
				return 0;
		}
	}

	public String getRank(Player player) {

		String[] allGroups = permission.getPlayerGroups(player);
		for (String p : allGroups) {
			for (String configName : getConfig().getKeys(true)) {
				if (p.equalsIgnoreCase(configName)) {
					return configName;
				}
			}

		}
		return null;

	}

	public String getRank(OfflinePlayer player) {

		String[] allGroups = permission.getPlayerGroups(null, player);
		for (String p : allGroups) {
			for (String configName : getConfig().getKeys(true)) {
				if (p.equalsIgnoreCase(configName)) {
					return configName;
				}
			}

		}
		return null;

	}

	private boolean setupEconomy() {

		RegisteredServiceProvider<Economy> economyProvider = getServer().getServicesManager().getRegistration(Economy.class);
		if (economyProvider != null) {
			economy = (Economy) economyProvider.getProvider();
		}
		return economy != null;
	}

	private boolean setupPermissions() {

		RegisteredServiceProvider<Permission> permissionProvider = getServer().getServicesManager().getRegistration(Permission.class);
		if (permissionProvider != null) {
			permission = (Permission) permissionProvider.getProvider();
		}
		return permission != null;
	}

}