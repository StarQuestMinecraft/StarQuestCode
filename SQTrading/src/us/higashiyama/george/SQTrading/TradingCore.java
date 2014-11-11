
package us.higashiyama.george.SQTrading;

import java.util.ArrayList;
import java.util.HashMap;

import net.milkbowl.vault.economy.Economy;
import net.milkbowl.vault.permission.Permission;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.block.Sign;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;

import us.higashiyama.george.SQTrading.Utils.InventoryUtil;
import us.higashiyama.george.SQTrading.Utils.TransactionType;

public class TradingCore extends JavaPlugin implements Listener {

	public static TradingCore instance;
	public static Permission permission;
	public static Economy economy;
	private static final String SIGN_IDENTIFIER = ChatColor.DARK_AQUA + "Trade Station";
	public static HashMap<String, int[]> itemNames = new HashMap<String, int[]>();

	public void onEnable() {

		instance = this;
		getServer().getPluginManager().registerEvents(this, this);
		setupPermissions();
		setupEconomy();
		Database.setUp();
		loadItemNames();

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

	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {

		if ((sender instanceof Player)) {
			Player p = (Player) sender;

			if (cmd.getName().equalsIgnoreCase("createoffer")) {

				if (args.length < 3) {
					p.sendMessage(ChatColor.RED + "/createoffer <buy/sell> <item> <data> <amount> <price> <station>| Create an offer at a trading station.");
					return false;
				}

				if (args[0].equalsIgnoreCase("buy")) {

					parseBuyOffer(p, args);

				} else if (args[0].equalsIgnoreCase("sell")) { // If sell

					TradingStation ts = Database.getTradingStation(p.getEyeLocation().getBlockX(), p.getEyeLocation().getBlockY(), p.getEyeLocation()
							.getBlockZ(), p.getEyeLocation().getWorld().getName(), Bukkit.getServerName());

					if (ts == null) {
						p.sendMessage(ChatColor.RED + "You are not at a Trading Station");
						return true;
					} else {

						parseSellOffer(p, args, ts);

					}
				} else {
					p.sendMessage(ChatColor.RED + "Offer must be either 'buy' or 'sell'");
					return false;
				}

				if (cmd.getName().equalsIgnoreCase("trade")) {

					if (args.length == 0) {
						p.sendMessage(ChatColor.RED + "/trade <id> | Trade for an ID while at a trading station.");
						return false;
					}

					TradingStation ts = Database.getTradingStation(p.getEyeLocation().getBlockX(), p.getEyeLocation().getBlockY(), p.getEyeLocation()
							.getBlockZ(), p.getEyeLocation().getWorld().getName(), Bukkit.getServerName());
					if (ts == null) {
						p.sendMessage(ChatColor.RED + "You are not at a Trading Station");
						return true;
					} else {
						int id = 0;
						try {

							id = Integer.parseInt(args[0]);
						} catch (NumberFormatException e) {
							p.sendMessage(ChatColor.RED + "Number format IDs accepted only");
							return true;
						}

						TradingOffer to = Database.getSellOffer(ts, id);
						if (to == null) {
							p.sendMessage(ChatColor.RED + "No such offer exists at the current trading station");
							return false;
						}
						trade(p, ts, to);
						return false;
					}
				}

				if (cmd.getName().equalsIgnoreCase("collect")) {

					TradingStation ts = Database.getTradingStation(p.getEyeLocation().getBlockX(), p.getEyeLocation().getBlockY(), p.getEyeLocation()
							.getBlockZ(), p.getEyeLocation().getWorld().getName(), Bukkit.getServerName());
					if (ts == null) {
						p.sendMessage(ChatColor.RED + "You are not at a Trading Station");
						return true;
					} else {

						CompletedTransaction ct = Database.getRandomTransaction(p, ts);
						if (ct == null) {
							p.sendMessage(ChatColor.RED + "Nothing Left To Collect");
							return false;
						}
						giveItems(p, ct);
						return false;
					}
				}

			}

			return false;
		}
		return false;
	}

	private void parseBuyOffer(Player p, String[] args) {

		// format(s)
		// createoffer buy item amount price station
		// createoffer buy hand amount price station

		if (args[1].equalsIgnoreCase("hand")) {

			TradingStation ts = Database.getTradingStationByName(args[4]);
			if (ts == null) {
				p.sendMessage(ChatColor.RED + "No Trading Station with that name.");
				return;
			}
			double price = 0;
			try {
				price = Integer.parseInt(args[3]);
			} catch (NumberFormatException e) {
				p.sendMessage("Please specify a number price.");
				return;
			}

			int amount = 0;
			try {
				amount = Integer.parseInt(args[2]);
			} catch (NumberFormatException e) {
				p.sendMessage("Please specify a number quantity.");
				return;
			}
			ItemStack is = p.getItemInHand();
			Database.addOffer(ts.getName(), TransactionType.SELL, p.getUniqueId(), is.getType().name(), amount, (short) is.getDurability(), price);
		} else {

			TradingStation ts = Database.getTradingStationByName(args[4]);
			if (ts == null) {
				p.sendMessage(ChatColor.RED + "No Trading Station with that name.");
				return;
			}

			String item = args[1];
			int id = 0;

			try {
				id = Integer.parseInt(item);
			} catch (NumberFormatException e) {

			}

			double price = 0;
			try {
				price = Integer.parseInt(args[3]);
			} catch (NumberFormatException e) {
				p.sendMessage("Please specify a number price.");
				return;
			}

			int amount = 0;
			try {
				amount = Integer.parseInt(args[2]);
			} catch (NumberFormatException e) {
				p.sendMessage("Please specify a number quantity.");
				return;
			}

			if (itemExists(item)) {
				p.sendMessage("No such item exists with that name.");
				return;
			}

			Material m = Material.getMaterial(itemNames.get(item.toLowerCase())[1]);
			Database.addOffer(ts.getName(), TransactionType.SELL, p.getUniqueId(), m.name(), amount, (short) itemNames.get(item.toLowerCase())[2], price);
		}

	}

	private void parseSellOffer(Player p, String[] args, TradingStation ts) {

		// format: /createoffer sell amount price
		double price = 0;
		try {
			price = Integer.parseInt(args[2]);
		} catch (NumberFormatException e) {
			p.sendMessage("Please specify a number price.");
			return;
		}

		int amount = 0;
		try {
			amount = Integer.parseInt(args[1]);
		} catch (NumberFormatException e) {
			p.sendMessage("Please specify a number quantity.");
			return;
		}

		// We assume they have the item since it's in their hand
		ItemStack is = p.getItemInHand();

		if (is.getType() == Material.AIR) {
			p.sendMessage(ChatColor.RED + "You really think someone would buy air!?");
			return;
		}

		if (p.getInventory().containsAtLeast(is, amount)) {

			Database.addOffer(ts.getName(), TransactionType.BUY, p.getUniqueId(), is.getType().name(), amount, (short) is.getDurability(), price);

			p.getInventory().remove(is);
			p.sendMessage(ChatColor.GREEN + "You sucessfully made an offer!");

		} else {
			p.sendMessage(ChatColor.RED + "You don't have enough items to make that offer");
			return;
		}

	}

	@EventHandler
	public void onPlayerInteract(PlayerInteractEvent event) {

		if (event.getAction() == Action.RIGHT_CLICK_BLOCK) {
			Material t = event.getClickedBlock().getType();
			if (t == Material.WALL_SIGN || t == Material.SIGN_POST) {
				Sign s = (Sign) event.getClickedBlock().getState();
				signRightClickProcess(s, event.getPlayer(), event);
			}
		} else if (event.getAction() == Action.LEFT_CLICK_BLOCK) {
			Material t = event.getClickedBlock().getType();
			if (t == Material.WALL_SIGN || t == Material.SIGN_POST) {
				Sign s = (Sign) event.getClickedBlock().getState();
				signLeftClickProcess(s, event.getPlayer(), event);
			}
		}
	}

	// called from interact events where it's signs
	private void signRightClickProcess(Sign s, Player p, PlayerInteractEvent e) {

		String line = s.getLine(0);
		if (line.equalsIgnoreCase("[trader]")) {
			e.setCancelled(true);
			// create a new trade station
			s.setLine(0, SIGN_IDENTIFIER);
			String uncheckedID = s.getLine(1);
			// Take pending station name and return it
			if (Database.tradingStationExists(uncheckedID)) {
				p.sendMessage(ChatColor.RED + "A trading station already exists by that name.");
				return;
			}

			String id = ChatColor.GREEN + uncheckedID;
			s.setLine(1, id);
			TradingStation station = new TradingStation(uncheckedID, s.getLocation().getBlockX(), s.getLocation().getBlockY(), s.getLocation().getBlockZ(), s
					.getLocation().getWorld().getName(), Bukkit.getServerName());
			Database.addTradingStation(station);
			s.update(true);
		} else if (line.equals(SIGN_IDENTIFIER)) {

		}
	}

	// called from interact events where it's signs
	private void signLeftClickProcess(Sign s, Player p, PlayerInteractEvent e) {

		String line = s.getLine(0);
		if (line.equals(SIGN_IDENTIFIER)) {

		}
	}

	private static void giveItems(Player p, CompletedTransaction ct) {

		ArrayList<ItemStack> items = new ArrayList<ItemStack>();

		int overflowCheck = 0;

		int airSlots = 0;

		for (ItemStack i : p.getInventory()) {
			if (i == null)
				airSlots++;
		}

		if (ct.getPlayerUUID() == p.getUniqueId() && overflowCheck < airSlots) {
			overflowCheck += Math.ceil(ct.getAmount() / 64);
			items.add(ct.getItemStack());
			Database.clearTransaction(ct);

		} else {
			int totalAmount = ct.getAmount();
			int roomFor = airSlots * Material.getMaterial(ct.getMaterialName()).getMaxStackSize();
			ct.setAmount(totalAmount - roomFor);
			CompletedTransaction semiCT = ct.copy();
			semiCT.setAmount(roomFor);
			items.add(semiCT.getItemStack());
			Database.progressTransaction(ct);

		}

		Inventory pi = p.getInventory();
		pi.addItem(items.toArray(new ItemStack[items.size()]));

	}

	private static void trade(Player p, TradingStation ts, TradingOffer to) {

		if (to.getTradingType() == TransactionType.SELL) {
			int quantity = to.getQuantity();

			Inventory ci = p.getInventory();

			int amountInChest = 0;
			for (ItemStack ciis : ci.getContents()) {
				if (ciis == null)
					continue;
				if (ciis.getType() == to.getItemStack().getType() && ciis.getDurability() == to.getItemStack().getDurability()) {
					amountInChest += ciis.getAmount();
				}
			}

			if (amountInChest >= quantity)
				amountInChest = quantity;
			if (economy.getBalance(instance.getServer().getOfflinePlayer(to.getPlayerUUID())) < amountInChest * to.getPrice()) {
				p.sendMessage(ChatColor.RED + "Selling player could not afford to buy your product.");
				return;
			}

			InventoryUtil.removeInventoryItems(ci, to.getItemStack().getType(), amountInChest);
			int modQuantity = (to.getQuantity() - amountInChest);

			economy.depositPlayer(p, amountInChest * to.getPrice());
			economy.withdrawPlayer(instance.getServer().getOfflinePlayer(to.getPlayerUUID()), amountInChest * to.getPrice());

			if (modQuantity <= 0) {
				CompletedTransaction ct = new CompletedTransaction(to.getPlayerUUID(), to.getMaterial(), to.getData(), to.getQuantity(), ts.getName());

				Database.finishOffer(to); // Deleting offer
				System.out.println("Deleting");
				Database.addCompletedTransaction(ct); // Add a transaction
			} else {
				Database.progressOffer(to, modQuantity); // keep the offer,
															// add
															// a
															// incomplet
															// transaction
			}

			p.sendMessage(ChatColor.GREEN + "You made " + amountInChest * to.getPrice() + " from selling " + amountInChest + " units of "
					+ to.getItemStack().getType().toString());
		}

	}

	private boolean setupPermissions() {

		RegisteredServiceProvider<Permission> permissionProvider = getServer().getServicesManager().getRegistration(Permission.class);
		if (permissionProvider != null) {
			permission = (Permission) permissionProvider.getProvider();
		}
		return permission != null;
	}

	private boolean setupEconomy() {

		RegisteredServiceProvider<Economy> economyProvider = getServer().getServicesManager().getRegistration(Economy.class);
		if (economyProvider != null) {
			economy = (Economy) economyProvider.getProvider();
		}
		return economy != null;
	}

}
