/*
 * Mods 
 * - TPhere
 * - Clean spawn
 * - Report bugs on behalf of players
 * - Moderate chat
 * - chatreload
 * 
 * Sr
 * - force tp
 * - creative
 * - ship load
 */

package us.higashiyama.george.SQDuties;

import net.countercraft.movecraft.bungee.BungeePlayerHandler;
import net.milkbowl.vault.permission.Permission;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.inventory.CraftItemEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCreativeEvent;
import org.bukkit.event.inventory.InventoryOpenEvent;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerChangedWorldEvent;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerPickupItemEvent;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;

import us.higashiyama.george.CardboardBox.Knapsack;

public class SQDuties extends JavaPlugin implements Listener {

	BungeePlayerHandler utils;
	public static Permission permission = null;
	static SQDuties instance;

	public void onEnable() {

		Bukkit.getServer().getPluginManager().registerEvents(this, this);
		setupPermissions();
		instance = this;
		this.getServer().getMessenger().registerOutgoingPluginChannel(this, "BungeeCord");
	}

	private boolean setupPermissions() {

		RegisteredServiceProvider<Permission> permissionProvider = getServer().getServicesManager().getRegistration(Permission.class);
		if (permissionProvider != null) {
			permission = (Permission) permissionProvider.getProvider();
		}

		return permission != null;
	}

	@EventHandler
	public void dutyChat(AsyncPlayerChatEvent e) {

		if (e.getPlayer().hasPermission("commandspy.track")) {

			Runnable task = new CommandSpyFile(e.getPlayer().getName(), "General", ("Chat: " + e.getMessage()));
			new Thread(task, "CommandSpy").start();

		}
	}

	@EventHandler
	public void dutyWorldChange(PlayerChangedWorldEvent e) {

		if (e.getPlayer().hasPermission("commandspy.track")) {

			Runnable task = new CommandSpyFile(e.getPlayer().getName(), "General", ("Changed worlds from " + e.getFrom() + " to " + getServer().getName()));
			new Thread(task, "CommandSpy").start();
		}
	}

	@EventHandler
	public void dutyDrop(PlayerDropItemEvent e) {

		if (e.getPlayer().hasPermission("commandspy.track")) {

			Runnable task = new CommandSpyFile(e.getPlayer().getName(), "Items", "Dropped item " + e.getItemDrop().getItemStack().getType().toString() + " at "
					+ locationToString(e.getPlayer().getLocation()));
			new Thread(task, "CommandSpy").start();
		}
	}

	@EventHandler
	public void dutyPickup(PlayerPickupItemEvent e) {

		if (e.getPlayer().hasPermission("commandspy.track")) {
			Runnable task = new CommandSpyFile(e.getPlayer().getName(), "Items", "Picked up item " + e.getItem().getItemStack().getType().toString() + " at "
					+ locationToString(e.getPlayer().getLocation()));
			new Thread(task, "CommandSpy").start();
		}
	}

	@EventHandler
	public void invOpen(InventoryOpenEvent e) {

		if (e.getPlayer().hasPermission("commandspy.track")) {
			Runnable task = new CommandSpyFile(((Player) e.getPlayer()).getName(), "Inventory", "Opened " + e.getInventory().getTitle() + "| Location: "
					+ locationToString(e.getPlayer().getLocation()));
			new Thread(task, "CommandSpy").start();
		}
	}

	@EventHandler
	public void clickInventory(InventoryClickEvent e) {

		if (e.getWhoClicked().hasPermission("commandspy.track")) {
			Runnable task = new CommandSpyFile(((Player) e.getWhoClicked()).getName(), "Inventory", e.getInventory().getName() + "|" + e.getAction() + "|"
					+ e.getCurrentItem());
			new Thread(task, "CommandSpy").start();
		}
	}

	@EventHandler
	public void creativeInventory(InventoryCreativeEvent e) {

		if (e.getWhoClicked().hasPermission("commandspy.track")) {
			Runnable task = new CommandSpyFile(((Player) e.getWhoClicked()).getName(), "Inventory", "Own Inventory" + e.getAction() + "|" + e.getCurrentItem());
			new Thread(task, "CommandSpy").start();
		}
	}

	@EventHandler
	public void command(PlayerCommandPreprocessEvent e) {

		if (e.getPlayer().hasPermission("commandspy.track")) {
			Runnable task = new CommandSpyFile(e.getPlayer().getName(), "General", "Command: " + e.getMessage());
			new Thread(task, "CommandSpy").start();
		}
	}

	@EventHandler
	public void blockBreak(BlockBreakEvent e) {

		if (e.getPlayer().hasPermission("commandspy.track")) {
			Runnable task = new CommandSpyFile(((Player) e.getPlayer()).getName(), "Block", "BlockBreak: " + e.getBlock().getType() + " Location: "
					+ locationToString(e.getBlock().getLocation()));
			new Thread(task, "CommandSpy").start();
		} else if (e.getBlock().getType() == Material.DIAMOND_BLOCK || e.getBlock().getType() == Material.GOLD_BLOCK
				|| e.getBlock().getType() == Material.EMERALD_BLOCK) {
			// Adding support for global tracking
			Runnable task = new CommandSpyFile("Global", "Block", "Broken by " + e.getPlayer().getName() + ": " + e.getBlock().getType()
					+ locationToString(e.getBlock().getLocation()));
			new Thread(task, "CommandSpy").start();
		}
	}

	@EventHandler
	public void blockPlace(BlockPlaceEvent e) {

		if (e.getPlayer().hasPermission("commandspy.track")) {
			Runnable task = new CommandSpyFile(((Player) e.getPlayer()).getName(), "Block", "BlockPlace: " + e.getBlock().getType() + " Location: "
					+ locationToString(e.getBlock().getLocation()));
			new Thread(task, "CommandSpy").start();
		} else if (e.getBlock().getType() == Material.DIAMOND_BLOCK || e.getBlock().getType() == Material.GOLD_BLOCK
				|| e.getBlock().getType() == Material.EMERALD_BLOCK) {
			// Adding support for global tracking
			Runnable task = new CommandSpyFile("Global", "Block", "Placed by " + e.getPlayer().getName() + ": " + e.getBlock().getType()
					+ locationToString(e.getBlock().getLocation()));
			new Thread(task, "CommandSpy").start();
		}
	}

	@EventHandler
	public void craftEvent(CraftItemEvent e) {

		if (e.getWhoClicked().hasPermission("commandspy.track")) {
			Runnable task = new CommandSpyFile(((Player) e.getWhoClicked()).getName(), "Craft", "Crafted: " + e.getCurrentItem().getType());
			new Thread(task, "CommandSpy").start();
		} else if (e.getCurrentItem().getType() == Material.DIAMOND_BLOCK || e.getCurrentItem().getType() == Material.GOLD_BLOCK
				|| e.getCurrentItem().getType() == Material.EMERALD_BLOCK) {
			// Adding support for global tracking
			Runnable task = new CommandSpyFile("Global", "Craft", "Crafted by " + e.getWhoClicked().getName() + ": " + e.getCurrentItem().getType());
			new Thread(task, "CommandSpy").start();
		}

	}

	public static String locationToString(Location l) {

		String location = ("X: " + Math.round(l.getX()) + " Y: " + Math.round(l.getY()) + " Z: " + Math.round(l.getZ()) + " World: " + l.getWorld().getName()

		);

		return location;
	}

	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {

		if ((sender instanceof Player)) {
			Player p = (Player) sender;
			if (cmd.getName().equalsIgnoreCase("duty")) {
				setDutyGroupVariant(p);
			}
			return true;
		}
		return false;
	}

	public static void setDutyGroupVariant(Player p) {

		String[] pGroups = permission.getPlayerGroups(p);
		String[] allGroups = permission.getGroups();
		// Try to find a match between groups
		for (String g : pGroups) {
			for (String v : allGroups) {
				if (g.equalsIgnoreCase(v)) {
					continue;
				} else if (v.equalsIgnoreCase(g + "_duty")) {
					enableDuty(p, v);
				} else if (g.replace("_duty", "").equalsIgnoreCase(v)) {
					disableDuty(p, v);
				}
			}
		}
		// At this point we know a duty match wasn't found, so let's notify the
		// player
		p.sendMessage(ChatColor.RED + "You cannot enter/exit duty mode");
	}

	public static void enableDuty(Player p, String group) {

		permission.playerAddGroup(p, group + "_duty");
		permission.playerRemoveGroup(p, group);
		Knapsack k = new Knapsack(p);
		Database.savePlayer(p, k);
		GameMode gm = getDefinedGameMode(p);
		p.setGameMode(gm);
		p.getInventory().clear();
		p.getInventory().setArmorContents(null);
		p.sendMessage(ChatColor.GREEN + "Dutymode enabled!");

	}

	// Return adventure mode as a default
	public static GameMode getDefinedGameMode(Player p) {

		String[] pGroups = permission.getPlayerGroups(p);
		GameMode[] gms = { GameMode.ADVENTURE, GameMode.SURVIVAL, GameMode.CREATIVE };
		for (String g : pGroups) {
			if (g.contains("_duty")) {
				for (GameMode gm : gms) {
					if (permission.groupHas("NULL", g, "Duty." + gm.toString())) {
						return gm;
					}
				}
			}
		}

		return GameMode.ADVENTURE;
	}

	public static SQDuties getSQDuties() {

		return instance;
	}

	public static void disableDuty(Player p, String group) {

		permission.playerRemoveGroup(p, group + "_duty");
		permission.playerAddGroup(p, group);
		Database.loadData(p);
		p.setGameMode(GameMode.SURVIVAL);
		p.sendMessage(ChatColor.RED + "Dutymode disabled!");
	}

	public void restoreInventory(Player p, Knapsack k) {

		k.unpack(p);
	}

	public void sendPlayerToLocation(Player p, String str) {

		String[] parts = str.split(",");
		if (getServer().getWorld(parts[1]) == null) {
			return;
		}
		World world = getServer().getWorld(parts[1]);
		int x = Integer.parseInt(parts[2]);
		int y = Integer.parseInt(parts[3]);
		int z = Integer.parseInt(parts[4]);
		// if they are on the same server
		if (parts[0].equalsIgnoreCase(parts[1])) {
			Location newLoc = new Location(world, x, y, z);
			p.teleport(newLoc);
		} else {
			BungeePlayerHandler.sendPlayer(p, parts[0], parts[1], x, y, z);
		}

		Database.loadInventory(p);

	}

	@EventHandler
	public void onPlayerLogin(PlayerJoinEvent event) {

		Player p = event.getPlayer();

		String[] pGroups = permission.getPlayerGroups(p);
		for (String group : pGroups) {
			if (group.contains("_duty")) {
				p.setGameMode(getDefinedGameMode(p));
				p.getInventory().setArmorContents(null);
				p.getInventory().clear();
			}
		}
	}

}
