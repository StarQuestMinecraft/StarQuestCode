
package us.higashiyama.george.SQLTownyCheck;

import java.util.ArrayList;
import java.util.Arrays;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.plugin.java.JavaPlugin;

public class SQLTownyCheck extends JavaPlugin implements Listener {

	public void onEnable() {

		Database.setUp();
		Bukkit.getServer().getPluginManager().registerEvents(this, this);
	}

	@EventHandler
	public void onPlayerCommand(PlayerCommandPreprocessEvent e) {

		String p = e.getPlayer().getName();
		String command = e.getMessage().replace("/", "");
		ArrayList<String> args = new ArrayList<String>(Arrays.asList(command.split(" ")));

		if (args.size() < 2) {
			return;
		}

		if ((args.get(1).equalsIgnoreCase("add") && (args.get(0).equalsIgnoreCase("town") || args.get(0).equalsIgnoreCase("t")))) {
			if (args.size() > 3) {
				e.getPlayer().sendMessage(ChatColor.AQUA + "Too many arguments");
				e.setCancelled(true);
				return;
			}
			if (Database.hasKey(args.get(2)) && Bukkit.getPlayer(args.get(2)).hasPermission("town.join")) {
				Bukkit.getPlayer(args.get(2)).sendMessage(
						ChatColor.AQUA + "You cannot be invited to a town. You are already in one or do not have permissions.");
				e.getPlayer().sendMessage(ChatColor.AQUA + "That user is already in a town. If this is a mistake please contact a dev");
				e.setCancelled(true);
			}
		}

		if ((args.get(1).equalsIgnoreCase("join") && (args.get(0).equalsIgnoreCase("town") || args.get(0).equalsIgnoreCase("t")))) {
			if (Database.hasKey(p) && Bukkit.getPlayer(p).hasPermission("town.join")) {
				e.getPlayer().sendMessage(ChatColor.AQUA + "You are already in a town or cannot join one.");
				e.setCancelled(true);
			}
		}

		if ((args.get(1).equalsIgnoreCase("new") && (args.get(0).equalsIgnoreCase("town") || args.get(0).equalsIgnoreCase("t")))) {
			if (Database.hasKey(p)) {
				e.getPlayer().sendMessage(ChatColor.AQUA + "You cannot start a town. You are already in one.");
				e.setCancelled(true);
			}
		}

	}
}
