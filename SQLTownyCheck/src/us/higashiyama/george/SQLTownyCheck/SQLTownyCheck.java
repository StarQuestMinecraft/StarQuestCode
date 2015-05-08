
package us.higashiyama.george.SQLTownyCheck;

import java.util.ArrayList;
import java.util.Arrays;

import net.milkbowl.vault.permission.Permission;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;

public class SQLTownyCheck extends JavaPlugin implements Listener {

	static Permission permission;

	public void onEnable() {

		Database.setUp();
		Bukkit.getServer().getPluginManager().registerEvents(this, this);
		setupPermissions();
	}

	private boolean setupPermissions() {

		RegisteredServiceProvider<Permission> permissionProvider = getServer().getServicesManager().getRegistration(Permission.class);
		if (permissionProvider != null) {
			permission = (Permission) permissionProvider.getProvider();
		}
		return permission != null;
	}

	@EventHandler
	public void onPlayerCommand(PlayerCommandPreprocessEvent e) {

		Player p = e.getPlayer();
		String command = e.getMessage().replace("/", "");
		ArrayList<String> args = new ArrayList<String>(Arrays.asList(command.split(" ")));

		if (args.size() < 3) {
			return;
		}

		if ((args.get(1).equalsIgnoreCase("add") && (args.get(0).equalsIgnoreCase("town") || args.get(0).equalsIgnoreCase("t")))) {
			if (args.size() > 3) {
				e.getPlayer().sendMessage(ChatColor.AQUA + "Too many arguments");
				e.setCancelled(true);
				return;
			}

			Player potential = Bukkit.getPlayer(args.get(2));
			if (!canJoinTown(potential)) {
				potential.sendMessage(ChatColor.AQUA + "You cannot be invited to a town. You are already in one or do not have permissions.");
				e.getPlayer().sendMessage(ChatColor.AQUA + "That user is already in a town. If this is a mistake please contact a dev");
				e.setCancelled(true);
				return;
			}
		}

		if ((args.get(1).equalsIgnoreCase("join") && (args.get(0).equalsIgnoreCase("town") || args.get(0).equalsIgnoreCase("t")))) {
			if (!isColonist(p)) {
				e.getPlayer().sendMessage("You are not a member of the colonist track, you cannot join a town.");
				return;
			}
			if (!canJoinTown(p)) {
				e.getPlayer().sendMessage(ChatColor.AQUA + "You are already in a town or cannot join one.");
				e.setCancelled(true);
				return;
			}
		}

		if ((args.get(1).equalsIgnoreCase("new") && (args.get(0).equalsIgnoreCase("town") || args.get(0).equalsIgnoreCase("t")))) {
			if (!isColonist(p)) {
				e.getPlayer().sendMessage("You are not a member of the colonist track, you cannot start a town.");
				return;
			}
			if (!canStartTown(p)) {
				e.getPlayer().sendMessage(ChatColor.AQUA + "You cannot start a town. You are already in one.");
				e.setCancelled(true);
				return;
			}
		}
		if ((args.get(1).equalsIgnoreCase("set") && args.get(2).equalsIgnoreCase("name") && (args.get(0).equalsIgnoreCase("town") || args.get(0).equalsIgnoreCase("t")))) {
			e.getPlayer().sendMessage("You cannot rename towns.");
			e.setCancelled(true);
			return;
		}

	}

	public static boolean canJoinTown(Player p) {

		boolean spaceWorld = isSpaceWorld(p.getWorld().getName());
		if (spaceWorld) {
			return true;
		}
		int currentTowns = Database.towns(p.getName());
		if ((currentTowns == 0) && p.hasPermission("town.join")) {
			return true;
		} else if ((currentTowns > 0 && p.hasPermission("town.multiple." + (currentTowns + 1)))) {
			return true;
		} else {
			return false;
		}
	}

	private static boolean isSpaceWorld(String name) {

		return name.equalsIgnoreCase("Regalis") || name.equalsIgnoreCase("Digitalia") || name.equalsIgnoreCase("Defalos");
	}

	private static boolean isColonist(Player p) {

		String[] allGroups = permission.getPlayerGroups(p);
		for (String s : allGroups) {
			if (s.equals("settler") || s.equals("colonist") || s.equals("citizen") || s.equals("affluent") || s.equals("tycoon"))
				return true;
		}
		return false;
	}

	public static boolean canStartTown(Player p) {

		int currentTowns = Database.towns(p.getName());
		if ((currentTowns == 0) && p.hasPermission("town.join")) {
			return true;
		} else if ((currentTowns > 0 && p.hasPermission("town.startmultiple." + (currentTowns + 1)))) {
			return true;
		} else {
			return false;
		}
	}
}
