
package com.regalphoenixmc.SQRankup;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

import net.milkbowl.vault.VaultEco;
import net.milkbowl.vault.permission.Permission;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.event.player.PlayerLoginEvent;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;

import com.greatmancode.craftconomy3.Cause;
import com.greatmancode.craftconomy3.Common;
import com.greatmancode.craftconomy3.tools.interfaces.Loader;
import com.regalphoenixmc.SQRankup.CC3Wrapper.CC3Currency;

public class SQRankup extends JavaPlugin implements Listener {

	public static Permission permission = null;
	public static SQRankup instance;
	public static VaultEco vaultEco;
	public static int MULTIPLIER = 1;
	public static FileConfiguration config;
	public static HashMap<String, Integer> infamyCostMap = new HashMap<String, Integer>();
	public static HashMap<String, Integer> creditMap = new HashMap<String, Integer>();
	public static HashMap<String, Integer> infamyGainMap = new HashMap<String, Integer>();
	public static HashMap<String, List<String>> rankTree = new HashMap<String, List<String>>();
	public static HashMap<String, int[]> itemNames = new HashMap<String, int[]>();
	public static Common craftconomy;

	public void onEnable() {

		saveDefaultConfig();
		getServer().getPluginManager().registerEvents(this, this);
		setupPermissions();
		instance = this;
		config = instance.getConfig();
		Database.setUp();
		loadRanks();
		MULTIPLIER = config.getInt("multiplier");
		new NotifierTask().runTaskTimer(instance, 12000, 12000);
		Plugin plugin = Bukkit.getPluginManager().getPlugin("Craftconomy3");
		if (plugin != null) {
			craftconomy = (Common) ((Loader) plugin).getCommon();
		}
	}

	public void loadRanks() {

		Set<String> names = config.getConfigurationSection("ranks").getKeys(false);
		for (String name : names) {
			infamyCostMap.put(name.toLowerCase(), config.getInt("ranks." + name + ".infamyCost"));
			creditMap.put(name.toLowerCase(), config.getInt("ranks." + name + ".credits"));
			infamyGainMap.put(name.toLowerCase(), config.getInt("ranks." + name + ".infamyGain"));
			rankTree.put(name.toLowerCase(), config.getStringList("ranks." + name + ".next"));
		}
	}

	@EventHandler
	public void onPlayerCommand(PlayerCommandPreprocessEvent e) {

		String command = e.getMessage().replace("/", "").toLowerCase();
		if (command.contains("money pay") && (command.contains("infamy") || command.contains("vote"))) {
			e.getPlayer().sendMessage(ChatColor.RED + "You cannot send that currency to other players.");
			e.setCancelled(true);
			return;
		}

	}

	@EventHandler
	public void playerLogin(PlayerLoginEvent e) {

		notifyPlayerOfMultiplier(e.getPlayer());
	}

	public static SQRankup instance() {

		return instance;
	}

	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {

		if (cmd.getName().equalsIgnoreCase("rankuprefresh") && sender.hasPermission("SQRankup.refresh")) {
			config = YamlConfiguration.loadConfiguration(new File(getDataFolder(), "config.yml"));
			refresh();
			sender.sendMessage("Rankup Multiplier Refreshed");
			return true;
		}

		if (cmd.getName().equalsIgnoreCase("rankupmultiplier") && sender.hasPermission("SQRankup.multiplier")) {
			MULTIPLIER = Integer.parseInt(args[0]);
			instance.getConfig().set("multiplier", MULTIPLIER);
			saveConfig();
			String suffix = "";
			if (args.length > 1) {
				suffix += " by " + args[1];
			}
			sender.sendMessage("Rankup Multiplier set to " + MULTIPLIER + suffix);
			return true;
		}

		if ((cmd.getName().equalsIgnoreCase("rankup")) && ((sender instanceof Player))) {
			Player p = (Player) sender;
			String rank = getRank(p);
			String nextRank = null;
			List<String> nextRanks = rankTree.get(rank.toLowerCase());

			if (nextRanks == null || nextRanks.size() == 0) {
				p.sendMessage(ChatColor.RED + "No next ranks found. You are unable to rank up.");
				return true;
			}

			if (args.length == 0) {
				sender.sendMessage(ChatColor.RED + " You need to choose a rank path! Your avaliable choices are:");
				for (String r : nextRanks) {
					sender.sendMessage(ChatColor.GOLD + "    " + r);
				}
				return true;
			} else if (args.length >= 1) {
				if (nextRanks.contains(args[0].toLowerCase())) {
					nextRank = args[0].toLowerCase();
				} else {
					p.sendMessage(ChatColor.RED + "No such rank by the name " + ChatColor.AQUA + args[0] + ChatColor.RED + " that you can rank up to.");
					return true;
				}
			}

			if (rank.equalsIgnoreCase("refugee")) {
				sender.sendMessage(ChatColor.RED + "To rank up to settler, you must apply to the server on our minecraft forums thread");
				sender.sendMessage(ChatColor.GOLD + "http://tinyurl.com/starquestapps");
				return true;
			}

			int moneyRequirement = getMonetaryCost(nextRank);
			int killsRequirement = getKillRequirement(nextRank);
			double killsFound = CC3Wrapper.getBalance(p.getName(), CC3Currency.INFAMY);
			double moneyFound = CC3Wrapper.getBalance(p.getName(), CC3Currency.CREDITS);
			if ((killsFound >= killsRequirement) && (moneyFound >= moneyRequirement)) {
				getServer().broadcastMessage(ChatColor.RED + sender.getName() + " has ranked up to " + nextRank.toString().toLowerCase() + "!");
				permission.playerAddGroup(null, p, nextRank.toLowerCase());
				permission.playerRemoveGroup(null, p, rank);
				CC3Wrapper.withdraw(moneyRequirement, p.getName(), CC3Currency.CREDITS, Cause.PLUGIN, "Rankup withdrawl");
				CC3Wrapper.withdraw(killsRequirement, p.getName(), CC3Currency.INFAMY, Cause.PLUGIN, "Rankup withdrawl");
			} else {

				sender.sendMessage(ChatColor.AQUA + "Rankup Stats:");
				sender.sendMessage(ChatColor.GOLD + "    " + moneyFound + "/" + moneyRequirement);
				sender.sendMessage(ChatColor.GOLD + "    " + killsFound + "/" + killsRequirement);
			}
			return true;
		}

		if ((cmd.getName().equalsIgnoreCase("addapp")) && (sender.hasPermission("SQRankup.addApplication"))) {
			if (args.length >= 1) {
				getServer().broadcastMessage(ChatColor.RED + args[0] + " has ranked up to settler!");
				getServer().broadcastMessage(
						ChatColor.RED + "Are you a " + ChatColor.GREEN + "Refugee" + ChatColor.RED + "? Rank up to " + ChatColor.DARK_GREEN + "Settler"
								+ ChatColor.RED + " like " + args[0] + " did!");
				getServer().broadcastMessage(
						ChatColor.RED + "Visit " + ChatColor.BLUE + "http://tinyurl.com/starquestapps" + ChatColor.RED + " to apply for Settler rank!");

				permission.playerAddGroup(null, getServer().getOfflinePlayer(args[0]), "SETTLER");
				permission.playerRemoveGroup(null, getServer().getOfflinePlayer(args[0]), "REFUGEE");
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

	public static void refresh() {

		MULTIPLIER = instance.getConfig().getInt("multiplier");
	}

	@EventHandler
	public void onPlayerKill(final PlayerDeathEvent event) {

		// Let's run this async
		Runnable task = new Runnable() {

			public void run() {

				// if it's a suicide
				if (event.getEntity().getKiller() == event.getEntity())
					return;

				if (event.getEntity().getKiller() instanceof Player) {
					Player killer = (Player) event.getEntity().getKiller();
					Player killed = event.getEntity();

					if (!Database.isInCooldown(killer, killed)) {
						int infamy = rankToKills(killed);
						CC3Wrapper.deposit(infamy, killer.getName(), CC3Currency.INFAMY, Cause.PLUGIN, "Rankup Kill");
						killer.sendMessage(ChatColor.RED + "You were awarded " + infamy + " infamy for that kill. You now have "
								+ CC3Wrapper.getBalance(killer.getName(), CC3Currency.INFAMY) + " infamy");
						Database.addKill(killer, killed);
					} else {
						killer.sendMessage(ChatColor.RED + "You already killed that player in the last 20 minutes! Lay off for a bit...");
					}
				}
			}
		};
		new Thread(task, "RankupThread").start();
	}

	private static int rankToKills(Player killed) {

		int i = 0;
		String[] groups = permission.getPlayerGroups(null, killed);
		for (String group : groups) {
			if (infamyGainMap.containsKey(group.toLowerCase())) {
				i = infamyGainMap.get(group.toLowerCase());
			}
		}
		int cost = i < 0 ? i : i * MULTIPLIER;
		return cost;
	}

	// method for getting monetary cost of rankup
	public int getMonetaryCost(String rank) {

		Integer i = creditMap.get(rank.toLowerCase());
		if (i == null) {
			return 1000000000;
		} else {
			return i;
		}
	}

	public int getKillRequirement(String rank) {

		Integer i = infamyCostMap.get(rank.toLowerCase());
		if (i == null) {
			return 1000000000;
		} else {
			return i;
		}
	}

	public String getRank(OfflinePlayer player) {

		String[] allGroups = permission.getPlayerGroups(null, player);
		for (String group : allGroups) {
			List<String> nextRanks = rankTree.get(group.toLowerCase());
			if (nextRanks != null)
				return group;

		}
		return null;

	}

	private boolean setupPermissions() {

		RegisteredServiceProvider<Permission> permissionProvider = getServer().getServicesManager().getRegistration(Permission.class);
		if (permissionProvider != null) {
			permission = (Permission) permissionProvider.getProvider();
		}
		return permission != null;
	}

	private void notifyPlayerOfMultiplier(Player player) {

		if (MULTIPLIER > 1) {
			player.sendMessage(ChatColor.GOLD + "There is a x" + MULTIPLIER + " multiplier on all kill values");
		}
	}

}