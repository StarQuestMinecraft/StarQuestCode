package com.starquestminecraft.SQDonorTags;

import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.event.Listener;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;

import net.milkbowl.vault.chat.Chat;
import net.milkbowl.vault.permission.Permission;

import com.greatmancode.craftconomy3.Common;
import com.greatmancode.craftconomy3.account.Account;
import com.greatmancode.craftconomy3.Cause;

public class SQDonorTags extends JavaPlugin implements Listener{
	public static Permission perm;
	public static Chat chat;
	public static Common cc3;
	public static World world;

	public void onEnable(){
		setupPermissions();
		setupChat();
		Bukkit.getPluginManager().registerEvents(this, this);
		cc3 = Common.getInstance();
		world = Bukkit.getServer().getWorlds().get(0);
		chat.setGroupSuffix(world, "donor1", "[&6+&f]");
		chat.setGroupSuffix(world, "donor2", "[&6++&f]");
		chat.setGroupSuffix(world, "donor3", "[&6+++&f]");
		chat.setGroupSuffix(world, "donor4", "[&6++++&f]");
		chat.setGroupSuffix(world, "donor5", "[&6+++++&f]");
	}
	
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args){
		if(cmd.getName().equalsIgnoreCase("donation")){ // Usage: /donation UUID amount
			if(args.length != 2){
				return false;
			}
			OfflinePlayer player = Bukkit.getOfflinePlayer(UUID.fromString(args[0]));
			Account account = cc3.getAccountManager().getAccount(player.getName());
			account.deposit(Double.parseDouble(args[1]), world.getName(), "donation", Cause.PLUGIN, "Donation record update");
			// update player groups with new balance
			double bal = account.getBalance(world.getName(), "donation");
			if(bal >= 150){//[+++++]
				perm.playerAddGroup(world.getName(), player, "donor5");
				perm.playerRemoveGroup(world.getName(), player, "donor4");
			}
			else if(bal >= 75){//[++++]
				perm.playerAddGroup(world.getName(), player, "donor4");
				perm.playerRemoveGroup(world.getName(), player, "donor3");
			}
			else if(bal >= 45){//[+++]
				perm.playerAddGroup(world.getName(), player, "donor3");
				perm.playerRemoveGroup(world.getName(), player, "donor2");
			}
			else if(bal >= 15){//[++]
				perm.playerAddGroup(world.getName(), player, "donor2");
				perm.playerRemoveGroup(world.getName(), player, "donor1");
			}
			else if(bal >= 1){//[+]
				perm.playerAddGroup(world.getName(), player, "donor1");
			}
			return true;
		}
		return false;
	}
	
	private boolean setupPermissions() {
		RegisteredServiceProvider<Permission> permissionProvider = getServer().getServicesManager().getRegistration(net.milkbowl.vault.permission.Permission.class);
		if (permissionProvider != null) {
			perm = permissionProvider.getProvider();
		}
		return (perm != null);
	}

	private boolean setupChat() {
        RegisteredServiceProvider<Chat> chatProvider = getServer().getServicesManager().getRegistration(Chat.class);
        chat = chatProvider.getProvider();
        return chat != null;
    }
}
