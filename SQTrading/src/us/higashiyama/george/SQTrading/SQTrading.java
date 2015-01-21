
package us.higashiyama.george.SQTrading;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import net.milkbowl.vault.VaultEco;
import net.milkbowl.vault.permission.Permission;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.block.Sign;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerLoginEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;

import us.higashiyama.george.Currencies.Perk;

import com.greatmancode.craftconomy3.Common;
import com.greatmancode.craftconomy3.tools.interfaces.Loader;

public class SQTrading extends JavaPlugin implements Listener {

	public static Permission permission = null;
	public static SQTrading instance;
	public static VaultEco vaultEco;
	public static FileConfiguration config;
	public static ArrayList<Perk> perks = new ArrayList<Perk>();
	public static HashMap<Player, Perk> confirmationMap = new HashMap<Player, Perk>();
	public static HashMap<String, int[]> itemNames = new HashMap<String, int[]>();
	public static Common craftconomy;

	public void onEnable() {

		saveDefaultConfig();
		getServer().getPluginManager().registerEvents(this, this);
		setupPermissions();
		instance = this;
		config = instance.getConfig();
		Database.setUp();
		loadPerks();
		Database.cleanUp();
		loadItemNames();
		Plugin plugin = Bukkit.getPluginManager().getPlugin("Craftconomy3");
		if (plugin != null) {
			craftconomy = (Common) ((Loader) plugin).getCommon();
		}
	}

	@EventHandler
	public void playerLogin(PlayerLoginEvent e) {

		notifyPlayerOfPerks(e.getPlayer());
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
							perk.hasCurrency(player);
							if (perk.hasCurrency(player)) {
								confirmationMap.remove(player);
								return;
							} else {
								perk.giveCurrency(player);
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

	public static SQTrading instance() {

		return instance;
	}

	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {

		if (cmd.getName().equalsIgnoreCase("tradingoffers")) {
			if (!(sender instanceof Player))
				return true;
			Player p = (Player) sender;
			PerkHelper.showPrivateOffers(p);
			return true;

		}

		if (cmd.getName().equalsIgnoreCase("removeoffer")) {
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

			PerkHelper.removeOfferAndRecredit(p, id);
			return true;

		}
		if (cmd.getName().equalsIgnoreCase("trade")) {
			if (!(sender instanceof Player))
				return true;
			PerkHelper.perkTrade((Player) sender, args);
			return true;
		}

		if (cmd.getName().equalsIgnoreCase("perkredeem")) {
			if (!(sender instanceof Player))
				return true;
			Player p = (Player) sender;
			if (args.length == 1) {
				PerkHelper.searchAndRedeemPerk(p, args[0]);
			} else if (args.length == 0) {
				PerkHelper.showUnredeemedPerks(p);
			}
			return true;

		}

		if (cmd.getName().equalsIgnoreCase("perkadd") && sender.hasPermission("SQRankup.perkadd")) {
			if (args.length == 2) {
				// Player, Perk
				PerkHelper.addPerkToPlayer(args[1], args[0]);
			} else {
				return false;
			}
		}

		return false;
	}

	public static void dispatchCommands(List<String> commands, Player p) {

		for (String c : commands) {
			String command = c.replace("{name}", p.getName());
			Bukkit.dispatchCommand(Bukkit.getConsoleSender(), command);
		}
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

	private void notifyPlayerOfPerks(Player player) {

		// Checks to make sure all purchased perms are credited
		List<Perk> perks = Database.getRedeemedPlayerPerks(player);
		for (Perk p : perks) {
			List<String> perms = p.getPermissions();
			for (String perm : perms) {
				if (!player.hasPermission(perm)) {
					permission.playerAdd(null, player, perm);
				}
			}
		}

		// Notify the player of purchased perks
		List<Perk> unredeemed = Database.getUnredeemedPlayerPerks(player);

		if (unredeemed != null && unredeemed.size() > 0) {
			player.sendMessage(ChatColor.AQUA + "You have the following unredeemed perks: ");
			for (Perk p : unredeemed) {
				System.out.println(ChatColor.GOLD + "     " + p.getAlias());
			}
		}

	}

}