
package us.higashiyama.george.SQDuties;

import java.util.ArrayList;

import net.countercraft.movecraft.bungee.BungeePlayerHandler;
import net.milkbowl.vault.permission.Permission;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCreativeEvent;
import org.bukkit.event.inventory.InventoryOpenEvent;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerChangedWorldEvent;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerGameModeChangeEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerPickupItemEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;

public class SQDuties extends JavaPlugin implements Listener {

	BungeePlayerHandler utils;
	public static Permission permission = null;
	static SQDuties instance;
	public static ArrayList<Player> creativeCheck = new ArrayList<Player>();

	public void onEnable() {

		Database.setUp();
		InvRestoreDB.setUp();
		Bukkit.getServer().getPluginManager().registerEvents(this, this);
		setupPermissions();
		instance = this;
		this.getServer().getMessenger().registerOutgoingPluginChannel(this, "BungeeCord");

	}

	private boolean setupPermissions() {

		RegisteredServiceProvider<Permission> permissionProvider = getServer().getServicesManager().getRegistration(
				Permission.class);
		if (permissionProvider != null) {
			permission = (Permission) permissionProvider.getProvider();
		}
		return permission != null;
	}

	@EventHandler
	public void dutyChat(AsyncPlayerChatEvent e) {

		if (creativeCheck.contains(e.getPlayer())) {
			CommandSpy.writeString("Chat: " + e.getMessage(), e.getPlayer());
		}
	}

	@EventHandler
	public void dutyWorldChange(PlayerChangedWorldEvent e) {

		if (creativeCheck.contains(e.getPlayer())) {
			CommandSpy
					.writeString("Changed worlds from " + e.getFrom() + " to " + getServer().getName(), e.getPlayer());
		}
	}

	@EventHandler
	public void dutyDrop(PlayerDropItemEvent e) {

		if (creativeCheck.contains(e.getPlayer())) {
			CommandSpy.writeString("Dropped item " + e.getItemDrop().getType().toString() + " at "
					+ locationToString(e.getPlayer().getLocation()), e.getPlayer());
		}
	}

	@EventHandler
	public void dutyPickup(PlayerPickupItemEvent e) {

		if (creativeCheck.contains(e.getPlayer())) {
			CommandSpy.writeString("Picked up item " + e.getItem().getType().toString() + " at "
					+ locationToString(e.getPlayer().getLocation()), e.getPlayer());
		}
	}

	@EventHandler
	public void invOpen(InventoryOpenEvent e) {

		if (creativeCheck.contains(e.getPlayer())) {
			CommandSpy.writeString("Opened " + e.getInventory().getTitle(), (Player) e.getPlayer());
		}
	}

	@EventHandler
	public void clickInventory(InventoryClickEvent e) {

		if (creativeCheck.contains(e.getWhoClicked())) {
			CommandSpy.writeString(e.getInventory().getName() + "|" + e.getAction() + "|" + e.getCurrentItem(),
					(Player) e.getWhoClicked());
		}
	}

	@EventHandler
	public void creativeInventory(InventoryCreativeEvent e) {

		if (creativeCheck.contains(e.getWhoClicked())) {
			CommandSpy.writeString("Own Inventory" + e.getAction() + "|" + e.getCurrentItem(),
					(Player) e.getWhoClicked());
		}
	}

	@EventHandler
	public void command(PlayerCommandPreprocessEvent e) {

		if (creativeCheck.contains(e.getPlayer())) {
			CommandSpy.writeString("Command: " + e.getMessage(), e.getPlayer());
		}
	}

	public static String locationToString(Location l) {

		String location = ("X: " + Math.round(l.getX()) + " Y: " + Math.round(l.getY()) + " Z: " + Math.round(l.getZ())
				+ " World: " + l.getWorld().getName()

		);

		return location;
	}

	@EventHandler
	public void onPlayerDeath(PlayerDeathEvent e) {

		Player p = (Player) e.getEntity();
		String cause;
		if (p.getLastDamageCause() == null) {
			cause = "UNKNOWN(Maybe_Suffocation)";
		} else {
			cause = p.getLastDamageCause().getCause().toString();
		}
		Inventory armorInv = Bukkit.createInventory(p, 9);
		ItemStack[] armor = p.getInventory().getArmorContents();
		for (int i = 0; i < armor.length; i++) {
			armorInv.setItem(i, armor[i]);
		}
		InvRestoreDB.newKey(p.getName(), InventoryStringDeSerializer.InventoryToString(p.getInventory(), p),
				InventoryStringDeSerializer.ArmorToString(armorInv), cause);

	}

	@EventHandler
	public void onGamemodeChange(PlayerGameModeChangeEvent e) {

		if (e.getNewGameMode() == GameMode.CREATIVE) {
			creativeCheck.add(e.getPlayer());
			CommandSpy.writeString(e.getPlayer().getName() + " went into creative", e.getPlayer());
			new CommandSpy(e.getPlayer());
		} else if (e.getNewGameMode() == GameMode.SURVIVAL) {
			CommandSpy.writeString(e.getPlayer().getName() + " went into survival", e.getPlayer());
			if (creativeCheck.contains(e.getPlayer()))
				creativeCheck.remove(e.getPlayer());
		}

	}

	public void restoreDeathInv(String player, int index) {

		Player p = getServer().getPlayer(player);
		System.out.println("restoring inv");
		String timestamp = InvRestoreDB.getDateIndex(player, index);
		Inventory inv = InventoryStringDeSerializer.StringToInventory(InvRestoreDB.getInv(player, timestamp));
		Inventory armorInv = InventoryStringDeSerializer.StringToInventory(InvRestoreDB.getArmor(player, timestamp));
		double[] exp = InventoryStringDeSerializer.StringToExp(InvRestoreDB.getInv(player, timestamp));
		p.setLevel((int) exp[0]);
		p.setExp((float) exp[1]);

		ItemStack[] armor = new ItemStack[4];
		for (int i = 0; i < armor.length; i++) {
			armor[i] = armorInv.getItem(i);
		}
		p.getInventory().setContents(inv.getContents());
		p.getInventory().setArmorContents(armor);

	}

	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {

		if ((sender instanceof Player)) {

			if (cmd.getName().equalsIgnoreCase("invrestore") && sender.hasPermission("SQDuties.restore")) {
				if (args.length < 1 || args.length > 3) {
					sender.sendMessage(ChatColor.AQUA + "/invrestore <player name> <index>");
					sender.sendMessage(ChatColor.AQUA + "/invrestore <player name>");
					return true;
				}

				if (args.length == 1) {
					sender.sendMessage(ChatColor.AQUA + "Player Lookup");
					sender.sendMessage(ChatColor.AQUA + "-------------------");
					int i = 1;
					for (String cause : InvRestoreDB.getDeaths(args[0])) {
						sender.sendMessage(ChatColor.AQUA + "" + i + ": " + cause);
						i++;
					}
					return true;
				}

				if (args.length == 2) {
					sender.sendMessage(ChatColor.AQUA + "Inventory restored");
					restoreDeathInv(args[0], Integer.parseInt(args[1]));
					return true;
				}

			}

			Player p = (Player) sender;

			if (p.hasPermission("SQDuties.Developer")) {
				if (!isInDutymode(p, p.getWorld().getName())) {
					enableDuty(p, "Developer");
					return true;
				}
				disableDuty(p, "Developer");
				return true;
			}
			if (p.hasPermission("SQDuties.Manager")) {
				if (!isInDutymode(p, p.getWorld().getName())) {
					enableDuty(p, "Manager");
					return true;
				}
				disableDuty(p, "Manager");
				return true;
			}
			if (p.hasPermission("SQDuties.Srmod")) {
				if (!isInDutymode(p, p.getWorld().getName())) {
					enableDuty(p, "SrMod");
					return true;
				}
				disableDuty(p, "SrMod");
				return true;
			}
			if (p.hasPermission("SQDuties.Mod")) {
				if (!isInDutymode(p, p.getWorld().getName())) {
					enableDuty(p, "Mod");
					return true;
				}
				disableDuty(p, "Mod");
				return true;
			}
			if (p.hasPermission("SQDuties.Trmod")) {
				if (!isInDutymode(p, p.getWorld().getName())) {
					enableDuty(p, "TrMod");
					return true;
				}
				disableDuty(p, "TrMod");
				return true;
			}

		}
		return true;
	}

	public void enableDuty(Player p, String group) {

		permission.playerAddGroup(p, group + "_duty");
		p.setGameMode(GameMode.CREATIVE);
		saveData(p.getName());
		p.getInventory().clear();
		p.getInventory().setArmorContents(null);
		p.sendMessage(ChatColor.GREEN + "Dutymode enabled!");
	}

	public static SQDuties getSQDuties() {

		return instance;
	}

	public void disableDuty(Player p, String group) {

		permission.playerRemoveGroup(p, group + "_duty");
		p.setGameMode(GameMode.SURVIVAL);
		p.getInventory().clear();
		p.getInventory().setArmorContents(null);
		restoreInv(p.getName());
		restoreLoc(p.getName());
		Database.deleteKey(p.getName());
		p.sendMessage(ChatColor.RED + "Dutymode disabled!");
	}

	public void saveData(String player) {

		Player p = getServer().getPlayer(player);
		Inventory armorInv = Bukkit.createInventory(p, 9);
		ItemStack[] armor = p.getInventory().getArmorContents();
		for (int i = 0; i < armor.length; i++) {
			armorInv.setItem(i, armor[i]);
		}
		Database.newKey(player, InventoryStringDeSerializer.InventoryToString(p.getInventory(), p),
				InventoryStringDeSerializer.ArmorToString(armorInv), locToString(p.getLocation()));
	}

	public void restoreInv(String player) {

		Player p = getServer().getPlayer(player);
		System.out.println("restoring inv");
		Inventory inv = InventoryStringDeSerializer.StringToInventory(Database.getInv(player));
		Inventory armorInv = InventoryStringDeSerializer.StringToInventory(Database.getArmor(player));
		double[] exp = InventoryStringDeSerializer.StringToExp(Database.getInv(player));
		ItemStack[] armor = new ItemStack[4];
		for (int i = 0; i < armor.length; i++) {
			armor[i] = armorInv.getItem(i);
		}
		p.getInventory().setContents(inv.getContents());
		p.getInventory().setArmorContents(armor);
		p.setLevel((int) exp[0]);
		p.setExp((float) exp[1]);
	}

	public void restoreLoc(String player) {

		String[] locString;
		try {
			System.out.println("restoring Loc");
			final Player p = getServer().getPlayer(player);

			if (stringToLocation(Database.getLocation(p.getName())) == null) {
				locString = stringToLocationString(Database.getLocation(p.getName()));
				BungeePlayerHandler.sendPlayer(p, locString[0], locString[0], Integer.parseInt(locString[1]),
						Integer.parseInt(locString[2]), Integer.parseInt(locString[3]));
			} else {

				final Location newLoc = stringToLocation(Database.getLocation(p.getName()));
				p.teleport(newLoc);
			}

		} catch (Exception localException) {
			System.out.println(localException);
			System.out.println("localException Caught");
		}
	}

	public boolean isInDutymode(Player p, String world) {

		if (permission.playerInGroup(world, p.getName(), "TrMod_Duty")) {
			return true;
		}
		if (permission.playerInGroup(world, p.getName(), "Mod_Duty")) {
			return true;
		}
		if (permission.playerInGroup(world, p.getName(), "SrMod_Duty")) {
			return true;
		}
		if (permission.playerInGroup(world, p.getName(), "Manager_Duty")) {
			return true;
		}
		if (permission.playerInGroup(world, p.getName(), "Developer_Duty")) {
			return true;
		}

		return false;

	}

	public String locToString(Location loc) {

		String str = new String(loc.getWorld().getName() + ";" + loc.getBlockX() + ";" + loc.getBlockY() + ";"
				+ loc.getBlockZ());
		return str;
	}

	public Location stringToLocation(String str) {

		String[] parts = str.split(";");
		if (getServer().getWorld(parts[0]) == null) {
			return null;
		}
		World world = getServer().getWorld(parts[0]);
		double x = Double.parseDouble(parts[1]);
		double y = Double.parseDouble(parts[2]);
		double z = Double.parseDouble(parts[3]);
		Location newLoc = new Location(world, x, y, z);
		return newLoc;
	}

	public String[] stringToLocationString(String str) {

		return str.split(";");
	}

	@EventHandler
	public void onPlayerLogin(PlayerJoinEvent event) {

		final Player p = event.getPlayer();
		Bukkit.getScheduler().runTaskLater(this, new Runnable() {

			public void run() {

				if (SQDuties.this.isInDutymode(p, p.getWorld().getName())) {
					p.setGameMode(GameMode.CREATIVE);
					p.getInventory().clear();
				}
			}
		}, 20L);
	}

}
