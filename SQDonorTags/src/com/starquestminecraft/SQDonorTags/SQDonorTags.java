package com.starquestminecraft.SQDonorTags;

import com.greatmancode.craftconomy3.Cause;
import com.greatmancode.craftconomy3.Common;
import com.greatmancode.craftconomy3.account.Account;
import com.greatmancode.craftconomy3.account.AccountManager;

import java.util.HashMap;
import java.util.List;
import java.util.UUID;
import net.milkbowl.vault.chat.Chat;
import net.milkbowl.vault.permission.Permission;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.Server;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.event.Listener;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.ServicesManager;
import org.bukkit.plugin.java.JavaPlugin;

public class SQDonorTags extends JavaPlugin implements Listener {
	public static Permission perm;
	public static Chat chat;
	public static Common cc3;
	public static World world;
	public static HashMap<String, Long> times = new HashMap<String, Long>();

	public void onEnable() {
		setupPermissions();
		setupChat();
		Bukkit.getPluginManager().registerEvents(this, this);
		cc3 = Common.getInstance();
		world = (World) Bukkit.getServer().getWorlds().get(0);
		/*
		 * chat.setGroupSuffix(world, "donor1", "[&6+&f]");
		 * chat.setGroupSuffix(world, "donor2", "[&6++&f]");
		 * chat.setGroupSuffix(world, "donor3", "[&6+++&f]");
		 * chat.setGroupSuffix(world, "donor4", "[&6++++&f]");
		 * chat.setGroupSuffix(world, "donor5", "[&6+++++&f]");
		 */
	}

	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (cmd.getName().equalsIgnoreCase("donation") && sender instanceof ConsoleCommandSender) {
			if (args.length != 2) {
				return false;
			}
			OfflinePlayer player = Bukkit.getOfflinePlayer(UUID.fromString(args[0]));
			if(player == null || player.getName() == null){
				System.out.println("Player not found, cannot give donation funds!");
				return true;
			}
			if(times.containsKey(args[0]) && System.currentTimeMillis() - times.get(args[0]) < 60000){
				return true;
			}
			times.put(args[0], System.currentTimeMillis());
			Account account = cc3.getAccountManager().getAccount(player.getName());
			account.deposit(Double.parseDouble(args[1]), world.getName(), "donation", Cause.PLUGIN,
					"Donation record update");

			double bal = account.getBalance(world.getName(), "donation");
			if (bal >= 150.0D) {
				addGroup(player, "donor5");
			} else if (bal >= 75.0D) {
				addGroup(player, "donor4");
			} else if (bal >= 40.0D) {
				addGroup(player, "donor3");
			} else if (bal >= 15.0D) {
				addGroup(player, "donor2");
			} else if (bal >= 1.0D) {
				addGroup(player, "donor1");
			}
			return true;
		}
		return false;
	}
	
	private void addGroup(OfflinePlayer p, String group){
		Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "pp user " + p.getName() + " addgroup " + group);
	}

	private boolean setupPermissions() {
		RegisteredServiceProvider<Permission> permissionProvider = getServer().getServicesManager()
				.getRegistration(Permission.class);
		if (permissionProvider != null) {
			perm = (Permission) permissionProvider.getProvider();
		}
		return perm != null;
	}

	private boolean setupChat() {
		RegisteredServiceProvider<Chat> chatProvider = getServer().getServicesManager().getRegistration(Chat.class);
		chat = (Chat) chatProvider.getProvider();
		return chat != null;
	}
}
