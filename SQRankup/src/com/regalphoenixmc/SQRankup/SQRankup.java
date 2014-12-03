
package com.regalphoenixmc.SQRankup;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import net.milkbowl.vault.VaultEco;
import net.milkbowl.vault.economy.Economy;
import net.milkbowl.vault.permission.Permission;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.OfflinePlayer;
import org.bukkit.block.Block;
import org.bukkit.block.Sign;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerLoginEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;

import us.higashiyama.george.CardboardBox.CardboardBox;
import us.higashiyama.george.SQRankup.Currencies.Crate;
import us.higashiyama.george.SQRankup.Currencies.Credits;
import us.higashiyama.george.SQRankup.Currencies.Currency;
import us.higashiyama.george.SQRankup.Currencies.Perk;

public class SQRankup extends JavaPlugin implements Listener {

	public static Permission permission = null;
	public static Economy economy = null;
	public static SQRankup instance;
	public static VaultEco vaultEco;
	public static int MULTIPLIER = 1;
	public static FileConfiguration config;
	public static ArrayList<Perk> perks = new ArrayList<Perk>();
	public static HashMap<Player, Perk> confirmationMap = new HashMap<Player, Perk>();
	public static HashMap<String, int[]> itemNames = new HashMap<String, int[]>();

	public void onEnable() {

		saveDefaultConfig();
		getServer().getPluginManager().registerEvents(this, this);
		setupEconomy();
		setupPermissions();
		instance = this;
		config = instance.getConfig();
		Database.setUp();
		MULTIPLIER = instance.getConfig().getInt("multiplier");
		new NotifierTask().runTaskTimer(instance, 12000, 12000);
		loadPerks();
		Database.cleanUp();
		loadItemNames();
	}

	@EventHandler
	public void playerLogin(PlayerLoginEvent e) {

		// Checks to make sure all purchased perms are credited
		List<Perk> perks = Database.getRedeemedPlayerPerks(e.getPlayer());
		for (Perk p : perks) {
			List<String> perms = p.getPermissions();
			for (String perm : perms) {
				if (!e.getPlayer().hasPermission(perm)) {
					permission.playerAdd(null, e.getPlayer(), perm);
				}
			}
		}

		List<Currency> unredeemed = Database.getUnredeemedPlayerPerks(e.getPlayer());

		if (unredeemed != null && unredeemed.size() > 0) {
			e.getPlayer().sendMessage(ChatColor.AQUA + "You have the following unredeemed perks: ");
			for (Currency p : unredeemed) {
				System.out.println(ChatColor.GOLD + "     " + p.getAlias());
			}
		}
	}

	private void loadPerks() {

		for (String name : config.getConfigurationSection("perks").getKeys(false)) {
			String alias = config.getString("perks." + name + ".name");
			List<String> permissions = config.getStringList("perks." + name + ".permissions");
			List<String> requiredPerms = config.getStringList("perks." + name + ".requiredperms");
			List<String> commands = config.getStringList("perks." + name + ".commands");
			List<String> addG = config.getStringList("perks." + name + ".addGroups");
			List<String> remG = config.getStringList("perks." + name + ".removeGroups");
			perks.add(new Perk(alias, permissions, requiredPerms, commands, addG, remG));

		}

	}

	@EventHandler
	public void interact(PlayerInteractEvent e) {

		if ((e.getAction() == Action.RIGHT_CLICK_BLOCK) && (e.getClickedBlock().getType() == Material.WALL_SIGN)) {

			Sign s = (Sign) e.getClickedBlock().getState();
			if (s.getLine(0).equals(ChatColor.RED + "Perk Shop")) {
				Player player = e.getPlayer();
				Perk perk = null;
				for (Perk p : perks) {
					if (p.getAlias().equalsIgnoreCase(s.getLine(1))) {
						perk = p;
					}
				}

				if (perk == null) {
					e.getPlayer().sendMessage("Perk Unrecognized");
					return;
				} else {
					// If they are unconfirmed
					if (confirmationMap.get(player) == null) {
						player.sendMessage(ChatColor.RED + "Are you sure you want to purchase " + perk.getAlias() + " for " + s.getLine(2) + " and "
								+ s.getLine(3) + " ?");
						// Opening handshake
						confirmationMap.put(player, perk);
						return;
					} else {
						// If the perk is the same
						if (confirmationMap.get(player).equals(perk)) {

							double money = Double.parseDouble((s.getLine(3)).replace("C", ""));
							int kills = Integer.parseInt((s.getLine(2)).replace(" Kills", ""));
							RankupPlayer rp = perk.canPurchase(player, money, kills, perk);
							if (rp == null) {
								confirmationMap.remove(player);
								return;
							} else {
								perk.purchase(player, rp, money, kills);
							}

							confirmationMap.remove(player);
						} else {
							// if the perk is different
							player.sendMessage(ChatColor.RED + "Transaction Cancelled.");
							confirmationMap.remove(player);
							return;
						}
					}
				}
			} else if (s.getLine(0).equalsIgnoreCase("[perkshop]") && e.getPlayer().hasPermission("rankup.perkshop")) {

				s.setLine(0, ChatColor.RED + "Perk Shop");
				s.update(true);
			}

		}

	}

	public void logout(PlayerQuitEvent e) {

		if (confirmationMap.get(e.getPlayer()) != null) {
			confirmationMap.remove(e.getPlayer());
		}
	}

	public static SQRankup instance() {

		return instance;
	}

	public static Currency parseItemInput(Player player) {

		Block b = player.getTargetBlock(null, 100);
		if (b.getType() == (Material.SIGN) || b.getType() == (Material.WALL_SIGN)) {
			// safecast
			Sign s = (Sign) player.getTargetBlock(null, 100).getState();

			int quantity;
			try {
				quantity = Integer.parseInt(s.getLine(1));
			} catch (NumberFormatException ex) {
				player.sendMessage(ChatColor.AQUA + "Must specify a whole number quantity on line 2 of the sign");
				return null;
			}
			String name = s.getLine(0);

			ArrayList<ItemStack> stackList = new ArrayList<ItemStack>();
			for (String testName : itemNames.keySet()) {
				if (testName.equalsIgnoreCase(name)) {

					int stacks = getStackData(quantity)[0];
					int remaining = getStackData(quantity)[1];

					while (stacks > 0) {
						ItemStack is = new ItemStack(Material.getMaterial(itemNames.get(testName)[0]), stacks * 64, (byte) itemNames.get(testName)[1]);
						stackList.add(is);
						stacks--;
					}
					ItemStack is = new ItemStack(Material.getMaterial(itemNames.get(testName)[0]), remaining, (byte) itemNames.get(testName)[1]);

					stackList.add(is);
				}
			}

			ArrayList<CardboardBox> cbList = new ArrayList<CardboardBox>();
			for (ItemStack tis : stackList) {
				cbList.add(new CardboardBox(tis));
			}

			Crate c = new Crate(cbList);
			return c;

		}
		return null;
	}

	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {

		if (cmd.getName().equalsIgnoreCase("perktradeoffers")) {
			if (!(sender instanceof Player))
				return true;
			Player p = (Player) sender;
			p.sendMessage(ChatColor.GOLD + "Private Offers: ");
			Database.showPrivateOffers(p);
			return true;

		}

		if (cmd.getName().equalsIgnoreCase("perktraderemove")) {
			if (!(sender instanceof Player))
				return true;
			Player p = (Player) sender;
			int id = 0;
			try {
				id = Integer.parseInt(args[0]);
			} catch (NumberFormatException e) {
				sender.sendMessage(ChatColor.AQUA + "Must specify a number ID of the offer");
				return true;
			}

			Currency currency = Database.removeOffer(id, p);

			if (currency == null) {
				sender.sendMessage(ChatColor.AQUA + "No offer with id: " + id + " in the database that you are allowed to delete");
				return true;
			}
			if (currency instanceof Credits) {
				currency.purchase((Player) sender, Database.getEntry(((Player) sender).getName()), 0, 0);
			}

			if (currency instanceof Perk) {
				((Perk) currency).queuePurchase((Player) sender);
			}

			if (currency instanceof Crate) {
				currency.purchase((Player) sender, Database.getEntry(((Player) sender).getName()), 0, 0);
			}
			sender.sendMessage(ChatColor.AQUA + "Perk removed and recredited.");
		}
		if (cmd.getName().equalsIgnoreCase("perktrade")) {
			if (!(sender instanceof Player))
				return true;
			// format of command
			// perktrade <wants> <has> <expiry> <player>

			if (args.length < 1 || args.length > 4) {
				return false;
			}

			// for making the transaction
			if (args.length == 1) {
				int id = 0;
				try {
					id = Integer.parseInt(args[0]);
				} catch (NumberFormatException e) {
					sender.sendMessage(ChatColor.AQUA + "Must specify a number ID of the offer");
					return true;
				}
				System.out.println("getting currency");
				Currency currency = Database.getOffer(id, (Player) sender);
				System.out.println(currency);
				if (currency == null) {
					sender.sendMessage(ChatColor.AQUA + "No offer with id: " + id + " in the database that you are allowed to redeem at this time");
					return true;
				}

				// If the player is at the node
				if (currency.buildLocation() != null) {
					if (!currency.buildLocation().equals(((Player) sender).getLocation())) {
						sender.sendMessage(ChatColor.RED + "You are not at the designated trading node for this offer");
						return true;
					}
				}

				if (currency instanceof Credits) {
					currency.purchase((Player) sender, Database.getEntry(((Player) sender).getName()), 0, 0);
				}

				if (currency instanceof Perk) {
					System.out.println("perk");
					((Perk) currency).queuePurchase((Player) sender);
				}

				if (currency instanceof Crate) {
					System.out.println("Crate");
					((Crate) currency).queuePurchase((Player) sender);
				}
				sender.sendMessage(ChatColor.AQUA + "Transaction credited.");
				System.out.println("Deleting");
				Database.deleteOffer(id);
				return true;
			}

			if (args.length < 3) {
				sender.sendMessage(ChatColor.RED + "You left out an argument");
				return true;
			}

			Currency want = null;
			Currency has = null;
			int hours = 0;

			try {
				double credits = Double.parseDouble(args[0]);
				want = new Credits(credits);
			} catch (NumberFormatException e) {
				for (Perk p : perks) {
					if (p.getAlias().equalsIgnoreCase(args[0])) {
						want = p;
						break;
					}

				}

				if (args[0].equalsIgnoreCase("inventory")) {
					want = new Crate(((Player) sender).getInventory());
				}

				if (args[0].equalsIgnoreCase("item")) {
					want = parseItemInput((Player) sender);
				}

			}

			if (want == null) {
				sender.sendMessage(ChatColor.AQUA + "No perk exists with the name: " + args[0]);
				return true;
			}

			try {
				double credits = Double.parseDouble(args[1]);
				has = new Credits(credits);
			} catch (NumberFormatException e) {
				for (Perk p : perks) {
					if (p.getAlias().equalsIgnoreCase(args[1])) {
						has = p;
						break;
					}

				}
				if (args[1].equalsIgnoreCase("inventory")) {
					has = new Crate(((Player) sender).getInventory());
				}
			}

			if (args[1].equalsIgnoreCase("item")) {
				has = parseItemInput((Player) sender);
			}

			if (has == null) {
				sender.sendMessage(ChatColor.AQUA + "No perk exists with the name: " + args[0]);
				return true;
			}

			try {
				hours = Integer.parseInt(args[2]);
			} catch (NumberFormatException e) {
				sender.sendMessage(ChatColor.AQUA + "Expiry time must be a whole integer");
				return true;
			}

			Player player = null;
			if (args.length > 3) {
				player = Bukkit.getPlayer(args[4]);
				if (player == null) {
					sender.sendMessage(ChatColor.AQUA + "No such player exists");
					return true;
				}
			}

			boolean hasPerk = false;
			if (has instanceof Credits) {
				if (economy.getBalance((Player) sender) < ((Credits) has).getCredits()) {
					sender.sendMessage(ChatColor.AQUA + "Not enough credits to make the offer");
					return true;
				} else {
					economy.withdrawPlayer((Player) sender, ((Credits) has).getCredits());
					hasPerk = true;
				}

			}

			if (has instanceof Perk) {
				List<Currency> perks = Database.getUnredeemedPlayerPerks((Player) sender);
				for (Currency p : perks) {
					if ((p instanceof Perk) && p.getAlias().equalsIgnoreCase(((Perk) has).getAlias())) {
						hasPerk = true;
						Database.removePerk((Player) sender, (Perk) p);
						break;
					}
				}
			}

			if (has instanceof Crate) {
				// We know at this point that the player has the items

				hasPerk = true;
				Crate c = (Crate) has;
				for (CardboardBox cb : c.getBoxes()) {
					((Player) sender).getInventory().remove(cb.unbox());
				}

			}

			if (!hasPerk) {
				sender.sendMessage(ChatColor.AQUA + "You don't own the perk you want to trade");
				return true;
			}

			Database.openOffer((Player) sender, player, want, has, hours * 3600000);

			sender.sendMessage(ChatColor.AQUA + "Offer created!");

			return true;
		}

		if (cmd.getName().equalsIgnoreCase("perkredeem")) {
			if (!(sender instanceof Player))
				return true;

			Player p = (Player) sender;
			if (args.length == 1) {
				List<Currency> unredeemed = Database.getUnredeemedPlayerPerks(p);
				for (Currency perk : unredeemed) {
					if (perk.getAlias().equalsIgnoreCase(args[0])) {
						perk.purchase(p, Database.getEntry(p.getName()), 0, 0);
						break;
					}
				}
			} else if (args.length == 0) {
				List<Currency> unredeemed = Database.getUnredeemedPlayerPerks(p);
				if (unredeemed.size() > 0) {
					p.sendMessage(ChatColor.AQUA + "You have the following unredeemed perks: ");
					for (Currency perk : unredeemed) {
						p.sendMessage(ChatColor.GOLD + "     " + perk.getAlias());
					}
				} else {
					p.sendMessage(ChatColor.AQUA + "You have no perks to redeem");
				}
			}
			return true;

		}

		if (cmd.getName().equalsIgnoreCase("perkadd") && sender.hasPermission("SQRankup.perkadd")) {
			if (args.length == 2) {
				String perk = args[0];
				for (Perk p : perks) {
					if (p.getAlias().equalsIgnoreCase(perk)) {

						Player player = Bukkit.getPlayer(args[1]);
						RankupPlayer rp = p.canPurchase(player, 0, 0, p);
						if (rp != null) {

							// Give the player the perk, but leave it unredeemed
							p.queuePurchase(player);
						}

					}
				}
				return true;
			} else {
				return false;
			}
		}

		if (cmd.getName().equalsIgnoreCase("rankuprefresh") && sender.hasPermission("SQRankup.refresh")) {
			config = YamlConfiguration.loadConfiguration(new File(getDataFolder(), "config.yml"));
			refresh();
			perks.clear();
			loadPerks();
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

		if ((cmd.getName().equalsIgnoreCase("modifykills")) && (sender.hasPermission("SQRankup.setKills"))) {

			if (args.length < 2) {
				sender.sendMessage("/modifykills <player> <new amount>");
				sender.sendMessage("/modifykills <player> +<amount to add>");
				sender.sendMessage("/modifykills <player> -<amount to subtract>");
				return true;
			}
			String amount;

			if (args[1].contains("+")) {
				amount = args[1].replace("+", "");
			} else if (args[1].contains("-")) {
				amount = args[1].replace("-", "");
			} else {
				amount = args[1];
			}

			int parsedAmount = Integer.parseInt(amount);

			Database.addKills(args[0], parsedAmount);

			sender.sendMessage(args[0] + " now has " + Database.getEntry(args[0]).getKills() + " kills.");
		}

		if ((cmd.getName().equalsIgnoreCase("viewkills")) && (sender.hasPermission("SQRankup.viewKills"))) {

			if (args.length < 1) {
				sender.sendMessage("/viewkills <player>");
				return true;
			}

			sender.sendMessage(args[0] + "  has " + Database.getEntry(args[0]).getKills() + " kills.");
		}

		return false;
	}

	@EventHandler
	public void login(PlayerLoginEvent e) {

		if (MULTIPLIER != 1) {
			e.getPlayer().sendMessage(ChatColor.GOLD + "Hey " + e.getPlayer().getName() + "! There's a x" + MULTIPLIER + " multiplier on all kills!!");
		}
	}

	public static void refresh() {

		MULTIPLIER = instance.getConfig().getInt("multiplier");
	}

	@EventHandler
	public void onPlayerKill(PlayerDeathEvent event) {

		if (event.getEntity().getKiller() == event.getEntity())
			return;
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
				if ((lastKill != null) && (lastKill.equals(event.getEntity().getName())) && (System.currentTimeMillis() - entry.getLastKillTime() < 1800000)) {
					((Player) killer).sendMessage("You've already killed this guy in the last half hour. Lay off, dude. This kill wasn't counted.");
					return;
				}
			}
			entry.setAsyncKills(rankToKills(((Player) event.getEntity()).getName()));
			entry.setLastKillName(event.getEntity().getName());
			entry.setLastKillTime(System.currentTimeMillis());
			entry.saveData();
			if (MULTIPLIER == 1) {
				((Player) killer).sendMessage(ChatColor.RED + "This kill was counted in the ranking system as "
						+ rankToKills(((Player) event.getEntity()).getName()) + ". You have " + entry.getKills() + " kills total.");
			} else {
				((Player) killer).sendMessage(ChatColor.RED
						+ "This kill was counted in the ranking system as "
						+ rankToKills(((Player) event.getEntity()).getName() + "because of an active kill booster! You have " + entry.getKills()
								+ " kills total."));
			}

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

		return i * MULTIPLIER;
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

	public static void dispatchCommands(List<String> commands, Player p) {

		for (String c : commands) {
			String command = c.replace("{name}", p.getName());
			Bukkit.dispatchCommand(Bukkit.getConsoleSender(), command);
		}
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

	public static boolean isPerk(String name) {

		for (Perk p : perks) {
			if (p.getAlias().equalsIgnoreCase(name)) {
				return true;
			}
		}
		return false;
	}

	private void loadItemNames() {

		for (String s : instance.getConfig().getStringList("values")) {
			String[] unparsed = s.split(",");
			int[] parsed = { Integer.parseInt(unparsed[1]), Integer.parseInt(unparsed[2]) };
			itemNames.put(unparsed[0], parsed);
		}

	}

	private boolean itemExists(String s) {

		if (itemNames.keySet().contains(s.toLowerCase()))
			return true;
		return false;
	}

	// returns array = {full stacks, number in second stack}
	public static int[] getStackData(int i) {

		// downcasts acts as Math.floor
		int fullstacks = (int) (i % 64);
		int remaining = i - (fullstacks * 64);

		int[] retArray = { fullstacks, remaining };
		return retArray;

	}

}