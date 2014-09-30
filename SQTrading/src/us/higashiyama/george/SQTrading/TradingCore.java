
package us.higashiyama.george.SQTrading;

import net.milkbowl.vault.economy.Economy;
import net.milkbowl.vault.permission.Permission;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.BlockFace;
import org.bukkit.block.Chest;
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

	public void onEnable() {

		instance = this;
		getServer().getPluginManager().registerEvents(this, this);
		setupPermissions();
		setupEconomy();
		Database.setUp();
	}

	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {

		if ((sender instanceof Player)) {
			Player p = (Player) sender;
			if (cmd.getName().equalsIgnoreCase("trade")) {

				if (args.length == 0) {
					p.sendMessage(ChatColor.RED + "/trade <id> | Trade for an ID while at a trading station.");
					return false;
				}

				TradingStation ts = Database.getTradingStation(p.getEyeLocation().getBlockX(), p.getEyeLocation().getBlockY(), p.getEyeLocation().getBlockZ(),
						p.getEyeLocation().getWorld().getName(), Bukkit.getServerName());
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

					TradingOffer to = Database.getTradingOffer(ts, id);
					if (to == null) {
						p.sendMessage(ChatColor.RED + "No such offer exists at the current trading station");
						return false;
					}
					trade(p, ts, to);
				}
			}

		}

		return false;

	}

	@EventHandler
	public void onPlayerInteract(PlayerInteractEvent event) {

		if (event.getPlayer().isSneaking()) {
			Database.addOffer("yolo", TransactionType.SELL, event.getPlayer().getUniqueId(), "WOOL", 150, (short) 5, 15);
		}
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

			TradingStation ts = Database.getTradingStation(s.getLocation().getBlockX(), s.getLocation().getBlockY(), s.getLocation().getBlockZ(), s
					.getLocation().getWorld().getName(), Bukkit.getServerName());

		}
	}

	// called from interact events where it's signs
	private void signLeftClickProcess(Sign s, Player p, PlayerInteractEvent e) {

		String line = s.getLine(0);
		if (line.equals(SIGN_IDENTIFIER)) {

		}
	}

	private static void trade(Player p, TradingStation ts, TradingOffer to) {

		Location tsl = ts.getLocation();
		if (tsl.getBlock().getRelative(BlockFace.DOWN).getType() != Material.CHEST) {
			p.sendMessage(ChatColor.RED + "There must be a chest below the sign");
			return;
		}

		int quantity = to.getQuantity();

		Chest c = (Chest) tsl.getBlock().getRelative(BlockFace.DOWN).getState();
		Inventory ci = c.getInventory();

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
			System.out.println("MOD: " + modQuantity);
			System.out.println("QUANT: " + quantity);
			// Database.progressOffer(to, ); // keep the offer, add a incomplete
			// transaction
		}

		p.sendMessage(ChatColor.GREEN + "You made " + amountInChest * to.getPrice() + " from selling " + amountInChest + " units of "
				+ to.getItemStack().getType().toString());

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
