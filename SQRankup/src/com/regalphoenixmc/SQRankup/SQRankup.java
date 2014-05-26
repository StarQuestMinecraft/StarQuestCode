
package com.regalphoenixmc.SQRankup;

import java.util.Arrays;
import java.util.List;

import net.milkbowl.vault.economy.Economy;
import net.milkbowl.vault.permission.Permission;

import org.apache.commons.lang.WordUtils;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerLoginEvent;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;

import ru.tehkode.permissions.PermissionGroup;
import ru.tehkode.permissions.PermissionManager;
import ru.tehkode.permissions.PermissionUser;
import ru.tehkode.permissions.bukkit.PermissionsEx;

public class SQRankup extends JavaPlugin implements Listener {

	public static Permission permission = null;
	public static Economy economy = null;
	public static int index = 0;
	PermissionManager pex;
	public static List<PermissionGroup> pexGroups;

	public void onEnable() {

		saveDefaultConfig();
		getServer().getPluginManager().registerEvents(this, this);
		setupEconomy();
		Database.setUp();
		pex = PermissionsEx.getPermissionManager();
		pexGroups = Arrays.asList(pex.getGroups());
	}

	// command
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {

		if (cmd.getName().equalsIgnoreCase("rankup") && sender instanceof Player) {
			Player p = (Player) sender;
			p.sendMessage(ChatColor.GOLD + "Getting rankup data for you! Please wait.");
			String rank = getRank(p.getName());
			String nextRank = getNextRank(rank);
			PermissionUser user = pex.getUser(p);

			if (rank.equalsIgnoreCase("SETTLER")) {
				if (args.length == 0) {
					sender.sendMessage(ChatColor.RED + " You need to choose a rank path! do /rankup Pirate or /rankup Colonist");
					return true;
				} else if (args[0].equalsIgnoreCase("Colonist")) {
					nextRank = "COLONIST";
				} else if (args[0].equalsIgnoreCase("Pirate")) {
					nextRank = "PIRATE";
					// TODO: townKick(p);
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

			int killsFound = Database.getKills(sender.getName());
			double moneyFound = economy.getBalance(sender.getName());

			if (killsFound >= killsRequirement && moneyFound >= moneyRequirement) {
				getServer().broadcastMessage(ChatColor.RED + sender.getName() + " has ranked up to " + nextRank.toString().toLowerCase() + "!");
				user.addGroup(WordUtils.capitalize(nextRank));
				user.removeGroup(WordUtils.capitalize(rank));
				economy.withdrawPlayer(sender.getName(), moneyRequirement);
				Database.setKills(sender.getName(), killsFound - killsRequirement);
			} else {
				sender.sendMessage("Current money: " + moneyFound + " Required Money: " + moneyRequirement + " Current Kills: " + killsFound
						+ " Required Kills: " + killsRequirement);
			}
			return true;
		}
		if (cmd.getName().equalsIgnoreCase("addapp") && sender.hasPermission("SQRankup.addApplication")) {
			String rank = getRank(args[0]);
			String nextRank = getNextRank(rank);
			PermissionUser user = pex.getUser(args[0]);
			if (args.length >= 1) {
				if (getServer().getOfflinePlayer(args[0]) != null) {
					getServer().broadcastMessage(ChatColor.RED + args[0] + " has ranked up to settler!");
					getServer().broadcastMessage(
							ChatColor.RED + "Are you a " + ChatColor.GREEN + "Refugee" + ChatColor.RED + "? Rank up to " + ChatColor.DARK_GREEN + "Settler"
									+ ChatColor.RED + " like " + args[0] + " did!");
					getServer().broadcastMessage(
							ChatColor.RED + "Visit " + ChatColor.BLUE + "http://tinyurl.com/starquestapps" + ChatColor.RED + " to apply for Settler rank!");

					user.addGroup(nextRank);
					user.removeGroup(rank);
					if (args.length >= 2 && getServer().getOfflinePlayer(args[1]) != null) {
						economy.depositPlayer(args[1], 10000);
						getServer().broadcastMessage(
								ChatColor.GOLD + args[1] + ChatColor.RED + " brought " + args[0] + " to the server and earned 10000 for doing so!");
					}
					return true;
				}
				sender.sendMessage("This player has never played before. Did you get their name right?");
				return false;
			}
			sender.sendMessage("Needs an argument.");
			return false;
		}
		return false;

	}

	// listener for kills
	@EventHandler
	public void onPlayerKill(PlayerDeathEvent event) {

		Player killer = event.getEntity().getKiller();
		if (killer instanceof Player) {
			String name = killer.getName();
			String lastKill = Database.getLastKill(name);
			if (lastKill != null && lastKill.equals(event.getEntity().getName())) {
				if (System.currentTimeMillis() - Database.getLastKillTime(name) < 180000) {
					killer.sendMessage("You've already killed this guy in the last half hour. Lay off, dude. This kill wasn't counted.");
					return;
				}
			}
			if (!Database.hasKey(name)) {
				Database.setNewKills(name, 0);
			}
			Database.incrementKills(name);
			Database.setLastKill(name, event.getEntity().getName());
			Database.setLastKillTimeToCurrent(name);
			killer.sendMessage(ChatColor.RED + "This kill was counted in the ranking system.");
			return;

		}

	}

	// method to get the next rank on the rank structure
	public String getNextRank(String rank) {

		switch (rank) {
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

		switch (rank) {
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

		switch (rank) {
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

	public String getRank(String player) {

		for (PermissionGroup group : pexGroups) {
			for (PermissionUser user : group.getUsers()) {
				if (user.getName().equalsIgnoreCase(player)) {
					if (getNextRank(group.getName().toUpperCase()) != null) {
						return group.getName().toUpperCase();
					}
				}
			}
		}

		return null;
	}

	public PermissionGroup getGroup(String player) {

		for (PermissionGroup group : pexGroups) {
			for (PermissionUser user : group.getUsers()) {
				if (user.getName().equalsIgnoreCase(player)) {
					if (getNextRank(group.getName().toUpperCase()) != null) {
						return group;
					}
				}
			}
		}

		return null;
	}

	@EventHandler
	public void playerLogin(PlayerLoginEvent e) {

		pex.getUser(e.getPlayer().getName());
	}

	// Handling economy provider onEnable (Vault)
	private boolean setupEconomy() {

		RegisteredServiceProvider<Economy> economyProvider = getServer().getServicesManager().getRegistration(Economy.class);
		if (economyProvider != null) {
			economy = (Economy) economyProvider.getProvider();
		}
		return economy != null;
	}

}
