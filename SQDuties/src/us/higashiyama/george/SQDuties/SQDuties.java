
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
import org.bukkit.entity.Damageable;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.inventory.CraftItemEvent;
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

	public void onEnable() {

		Database.setUp();
		InvRestoreDB.setUp();
		// Database.alterTables();
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

		if (e.getPlayer().hasPermission("commandspy.track")) {
			if (e.getNewGameMode() == GameMode.CREATIVE) {
				Runnable task = new CommandSpyFile(e.getPlayer().getName(), "General", "Switched to Creative");
				new Thread(task, "CommandSpy").start();
			} else if (e.getNewGameMode() == GameMode.SURVIVAL) {
				Runnable task = new CommandSpyFile(e.getPlayer().getName(), "General", "Switched to Creative");
				new Thread(task, "CommandSpy").start();
			}
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
			/*
						if (cmd.getName().equalsIgnoreCase("dutiesTransfer") && sender.hasPermission("transfer")) {
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
			Player p = (Player) sender;

			if (cmd.getName().equalsIgnoreCase("duty")) {

				if (p.isFlying()) {
					p.sendMessage(ChatColor.RED + "You cannot unduty or duty while flying!");
					return true;
				}

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
		return false;
	}

	public void enableDuty(Player p, String group) {

		permission.playerAddGroup(p, group + "_duty");
		p.getInventory().getHelmet();
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
		((Damageable) p).setHealth(20F);
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
		Database.newKey(p.getName(), InventoryStringDeSerializer.InventoryToString(p.getInventory(), p), InventoryStringDeSerializer.ArmorToString(armorInv),
				locToString(p.getLocation()));
	}

	public void restoreInv(String player) {

		Player p = getServer().getPlayer(player);
		System.out.println("restoring inv");
		Inventory inv = InventoryStringDeSerializer.StringToInventory(Database.getInv(p.getName()));
		Inventory armorInv = InventoryStringDeSerializer.StringToInventory(Database.getArmor(p.getName()));
		double[] exp = InventoryStringDeSerializer.StringToExp(Database.getInv(p.getName()));
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
				BungeePlayerHandler.sendPlayer(p, locString[0], locString[0], Integer.parseInt(locString[1]), Integer.parseInt(locString[2]),
						Integer.parseInt(locString[3]));
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

		return Database.hasKey(p.getName());

	}

	public String locToString(Location loc) {

		String str = new String(loc.getWorld().getName() + ";" + loc.getBlockX() + ";" + loc.getBlockY() + ";" + loc.getBlockZ());
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
					String[] playerGroups = permission.getPlayerGroups(p);
					String group = null;
					for (String s : playerGroups) {
						if (s.contains("Developer")) {
							group = "Developer_Duty";
							break;
						} else if (s.contains("Manager")) {
							group = "Manager_Duty";
							break;
						} else if (s.contains("SrMod")) {
							group = "SrMod_Duty";
							break;
						} else if (s.contains("Mod")) {
							group = "Mod_Duty";
							break;
						} else if (s.contains("TrlMod")) {
							group = "Tr_Duty";
							break;
						}

					}
					permission.playerAddGroup(p, group);
					p.setGameMode(GameMode.CREATIVE);
					p.getInventory().setArmorContents(null);
					p.getInventory().clear();
				}
			}
		}, 20L);
	}

}
