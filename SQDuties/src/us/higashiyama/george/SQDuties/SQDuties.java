package us.higashiyama.george.SQDuties;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;

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
import org.bukkit.event.player.PlayerJoinEvent;
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
		Bukkit.getServer().getPluginManager().registerEvents(this, this);
		setupPermissions();
		instance = this;
	    this.getServer().getMessenger().registerOutgoingPluginChannel(this, "BungeeCord");
	   
	}

	private boolean setupPermissions() {
		RegisteredServiceProvider<Permission> permissionProvider = getServer()
				.getServicesManager().getRegistration(Permission.class);
		if (permissionProvider != null) {
			permission = (Permission) permissionProvider.getProvider();
		}
		return permission != null;
	}

	@EventHandler
	public void onPlayerDeath(PlayerDeathEvent e) {
		Player p = (Player) e.getEntity();
		String cause;
		if(p.getLastDamageCause() == null){
			cause = "UNKNOWN(Maybe_Suffocation)";
		} else {
			cause = p.getLastDamageCause().getCause().toString();
		}
		Inventory armorInv = Bukkit.createInventory(p, 9);
		ItemStack[] armor = p.getInventory().getArmorContents();
		for (int i = 0; i < armor.length; i++) {
			armorInv.setItem(i, armor[i]);
		}
		InvRestoreDB.newKey(p.getName(), InventoryStringDeSerializer.InventoryToString(p.getInventory()), InventoryStringDeSerializer.InventoryToString(armorInv), cause);

	}
	

	
	public void restoreDeathInv(String player, int index) {
		Player p = getServer().getPlayer(player);
		System.out.println("restoring inv");
		String timestamp = InvRestoreDB.getDateIndex(player, index);
		Inventory inv = InventoryStringDeSerializer.StringToInventory(InvRestoreDB.getInv(player, timestamp));
		Inventory armorInv = InventoryStringDeSerializer.StringToInventory(InvRestoreDB.getArmor(player, timestamp));
		ItemStack[] armor = new ItemStack[4];
		for (int i = 0; i < armor.length; i++) {
			armor[i] = armorInv.getItem(i);
		}
		p.getInventory().setContents(inv.getContents());
		p.getInventory().setArmorContents(armor);
	}

	public boolean onCommand(CommandSender sender, Command cmd, String label,
			String[] args) {
		if ((sender instanceof Player)) {

			if (cmd.getName().equalsIgnoreCase("invrestore") && sender.hasPermission("SQDuties.restore")) {
				if(args.length < 1 || args.length > 3) {
					sender.sendMessage(ChatColor.AQUA + "/invrestore <player name> <index>");
					sender.sendMessage(ChatColor.AQUA + "/invrestore <player name>");
					return true;
				}
				
				if(args.length == 1){
					sender.sendMessage(ChatColor.AQUA + "Player Lookup");
					sender.sendMessage(ChatColor.AQUA + "-------------------");
					int i = 1;
					for(String cause : InvRestoreDB.getDeaths(args[0])){
						sender.sendMessage(ChatColor.AQUA + "" + i + ": " + cause);
						i++;
					}
				}
				
				if(args.length == 2) {
					sender.sendMessage(ChatColor.AQUA + "Inventory restored");
				restoreDeathInv(args[0], Integer.parseInt(args[1]));
				}
				
			}
			
			
			
			Player p = (Player) sender;
			boolean isMod = false;
			boolean isSrMod = false;
			boolean isTrMod = false;
			if (p.hasPermission("SQDuties.Mod")) {
				isMod = true;
			}
			if (p.hasPermission("SQDuties.Srmod")) {
				isSrMod = true;
			}
			if (p.hasPermission("SQDuties.Trmod")) {
				isTrMod = true;
			}
			if ((isMod) || (isSrMod) || (isTrMod)) {
				if (cmd.getName().equalsIgnoreCase("duty")) {
					if (isSrMod) {
						if (!isInDutymode(p)) {
							enableDuty(p, "SrMod");
							return true;
						}
						disableDuty(p, "SrMod");
						return true;
					}
					if (isTrMod) {
						if (!isInDutymode(p)) {
							enableDuty(p, "TrMod");
							return true;
						}
						disableDuty(p, "TrMod");
						return true;
					}
					if (isMod) {
						if (!isInDutymode(p)) {
							enableDuty(p, "Mod");
							return true;
						}
						disableDuty(p, "Mod");
						return true;
					}
				}
			} else {
				p.sendMessage(ChatColor.RED
						+ "[Doge] Much Permissions, Very No, Such Sadness, Wow");
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
		System.out.println("disableing");
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
		Database.newKey(
				player,
				InventoryStringDeSerializer.InventoryToString(p.getInventory()),
				InventoryStringDeSerializer.InventoryToString(armorInv),
				locToString(p.getLocation()));
	}

	public void restoreInv(String player) {
		Player p = getServer().getPlayer(player);
		System.out.println("restoring inv");
		Inventory inv = InventoryStringDeSerializer.StringToInventory(Database
				.getInv(player));
		Inventory armorInv = InventoryStringDeSerializer
				.StringToInventory(Database.getArmor(player));
		ItemStack[] armor = new ItemStack[4];
		for (int i = 0; i < armor.length; i++) {
			armor[i] = armorInv.getItem(i);
		}
		p.getInventory().setContents(inv.getContents());
		p.getInventory().setArmorContents(armor);
	}

	public void restoreLoc(String player) {
		String[] locString;
		try {
			System.out.println("restoring Loc");
			final Player p = getServer().getPlayer(player);		
			
			if(stringToLocation(Database.getLocation(p.getName())) == null) {
				locString = stringToLocationString(Database.getLocation(p.getName()));
				BungeePlayerHandler.sendPlayer(p, locString[0], locString[0],
						Integer.parseInt(locString[1]), 
						Integer.parseInt(locString[2]), 
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

	public boolean isInDutymode(Player p) {
		return Database.hasKey(p.getName());
	}

	public String locToString(Location loc) {
		String str = new String(loc.getWorld().getName() + ";"
				+ loc.getBlockX() + ";" + loc.getBlockY() + ";"
				+ loc.getBlockZ());
		return str;
	}

	public Location stringToLocation(String str) {
		String[] parts = str.split(";");
		if(getServer().getWorld(parts[0]) == null) {
			return null;
		}
		World world = getServer().getWorld(parts[0]);
		double x = Double.parseDouble(parts[1]);
		double y = Double.parseDouble(parts[2]);
		double z = Double.parseDouble(parts[3]);
		Location newLoc = new Location(world, x, y, z);
		return newLoc;
	}
	
	public String[] stringToLocationString(String str){
		return str.split(";");
		}

	@EventHandler
	public void onPlayerLogin(PlayerJoinEvent event) {
		final Player p = event.getPlayer();
		Bukkit.getScheduler().runTaskLater(this, new Runnable() {
			public void run() {
				if (SQDuties.this.isInDutymode(p)) {
					p.setGameMode(GameMode.CREATIVE);
					p.getInventory().clear();
				}
			}
		}, 5L);
	}
}
