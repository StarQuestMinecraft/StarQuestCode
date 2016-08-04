package com.ginger_walnut.sqduties;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import net.milkbowl.vault.permission.Permission;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitScheduler;

import com.dibujaron.cardboardbox.Knapsack;
import com.ginger_walnut.sqduties.database.InvRestoreDB;
import com.ginger_walnut.sqduties.database.SQLDatabase;

public class SQDuties extends JavaPlugin implements Listener{

	public final Logger logger = Logger.getLogger("Minecraft");
	public static SQDuties plugin;

	private static Plugin pluginMain;
	
	List<String> baseGroups = new ArrayList<String>();
	List<String> dutyGroups = new ArrayList<String>();
	
	public static Permission permission = null;
	
	@Override
	public void onDisable() {

		pluginMain = null;
		
		PluginDescriptionFile pdfFile = this.getDescription();
		this.logger.info(pdfFile.getName() + " has been disabled!");
		
	}
	
	@Override
	public void onEnable() {
		
		pluginMain = this;
		
		PluginDescriptionFile pdfFile = this.getDescription();
		this.logger.info(pdfFile.getName() + " has been enabled!");
		
		setupPermissions();
		
		this.getServer().getPluginManager().registerEvents(new Events(), this);
		Bukkit.getServer().getPluginManager().registerEvents(this, this);
		
		getConfig().options().copyDefaults(true);
		saveConfig();
		
		baseGroups = getConfig().getStringList("base groups");
		dutyGroups = getConfig().getStringList("duty groups");
		
		
	}
	
	private boolean setupPermissions() {

		RegisteredServiceProvider<Permission> permissionProvider = getServer().getServicesManager().getRegistration(Permission.class);
		
		if (permissionProvider != null) {
			
			permission = (Permission) permissionProvider.getProvider();
			
		}

		return permission != null;
		
	}
	
	public static Plugin getPluginMain() {
		
		return pluginMain;
		
	}
	
	public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args) {
	
		if (commandLabel.equals("duty")) {
			
			if (sender instanceof Player) {
				
				Player player = (Player) sender;
				
				if (player.isFlying()) {
					
					player.sendMessage(ChatColor.RED + "You cannot unduty or duty while flying!");
					
					return false;
					
				}
				
				Object[] playerInDutyResults = playerInDuty(player);
				
				if (!(boolean) playerInDutyResults[0]) {
					
					Object[] playerCanDutyResults = playerCanDuty(player);
					
					if ((boolean) playerCanDutyResults[0]) {
						
						System.out.print(player.getName() + " has entered duty mode");
										
						Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "pp user " + player.getName() + " addgroup " + (String) playerCanDutyResults[2]);
						//Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "pp user " + player.getName() + " removegroup " + (String) playerCanDutyResults[1]);
						
						//permission.playerAddGroup(player, (String) playerCanDutyResults[2]);
						
						Knapsack knapsack = new Knapsack(player);
						
						SQLDatabase database = new SQLDatabase();						
						database.addPlayer(SQLDatabase.con.getConnection(), player, knapsack);
						
						GameMode gameMode = getDutyGameMode(player);
						player.setGameMode(gameMode);
						
						player.getInventory().clear();
						player.getInventory().setArmorContents(null);
						
						player.sendMessage(ChatColor.GREEN + "Dutymode enabled!");
						
					} else {
						
						player.sendMessage(ChatColor.RED + "You cannot enter/exit duty mode");
						
					}
				
				} else {
					
					System.out.print(player.getName() + " has left duty mode");
					
					Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "pp user " + player.getName() + " removegroup " + (String) playerInDutyResults[2]);
					//Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "pp user " + player.getName() + " addgroup " + (String) playerInDutyResults[1]);
					
					//permission.playerRemoveGroup(player, (String) playerInDutyResults[2]);

					player.setGameMode(GameMode.SURVIVAL);
					
					SQLDatabase database = new SQLDatabase();					
					database.loadPlayer(SQLDatabase.con.getConnection(), player);

					player.sendMessage(ChatColor.RED + "Dutymode disabled!");
					
				}
				
			}
			
		} else if (commandLabel.equals("invrestore") && (sender.hasPermission("SQDuties.restore") || sender instanceof ConsoleCommandSender)) {
			
			if (args.length < 1 || args.length > 3) { 
				
				sender.sendMessage(ChatColor.AQUA + "/invrestore <player name> <index>"); 
				sender.sendMessage(ChatColor.AQUA + "/invrestore <player name>"); 

				
			} else if (args.length == 1) { 
				
				sender.sendMessage(ChatColor.AQUA + "Player Lookup"); 
				sender.sendMessage(ChatColor.AQUA + "-------------------"); 

				InvRestoreDB db = new InvRestoreDB();
				
				ArrayList<String> deaths = db.getDeaths(args[0]);
				
				for (int i = 0; i < deaths.size(); i ++) {

					sender.sendMessage(ChatColor.AQUA + "" + (i + 1) + ": " + deaths.get(i)); 
					
				}
				
			} else if (args.length == 2) { 
				
				Player player = getServer().getPlayer(args[0]);
				
				System.out.print("restoring inventory");
				
				String timestamp  = InvRestoreDB.getDateIndex(args[0], Integer.parseInt(args[1]));
				
				InvRestoreDB db = new InvRestoreDB();
				
				Knapsack knapsack = db.getKnapsack(args[0], timestamp);
				
				double health = player.getHealth();
				
				knapsack.unpack(player);
				
				player.setHealth(health);
				
				sender.sendMessage(ChatColor.AQUA + "Inventory restored"); 
				
			} 

		}
		
		return false;

	}
	
	public Object[] playerCanDuty(Player player) {
		
		String[] playerGroups = permission.getPlayerGroups(player);

		for (int i = 0; i < playerGroups.length; i ++) {
			
			for (int j = 0; j < baseGroups.size(); j ++) {

				if (playerGroups[i].equalsIgnoreCase(baseGroups.get(j))) {
					
					return new Object[] {true, baseGroups.get(j), dutyGroups.get(j)};	
					
				}
				
			}

		}
		
		return new Object[] {false};
			
	}
	
	
	public Object[] playerInDuty(Player player) {
		
		String[] playerGroups = permission.getPlayerGroups(player);

		for (int i = 0; i < playerGroups.length; i ++) {
			
			for (int j = 0; j < dutyGroups.size(); j ++) {

				if (playerGroups[i].equalsIgnoreCase(dutyGroups.get(j))) {
					
					return new Object[] {true, baseGroups.get(j), dutyGroups.get(j)};	
					
				}
				
			}

		}
		
		return new Object[] {false};
		
	}
	
	public GameMode getDutyGameMode(Player player) {

		String[] playerGroups = permission.getPlayerGroups(player);
		GameMode[] gameModes = { GameMode.ADVENTURE, GameMode.SURVIVAL, GameMode.CREATIVE, GameMode.SPECTATOR };
		
		for (int i = 0; i < playerGroups.length; i ++) {
			
			if (dutyGroups.contains(playerGroups.length)) {
				
				for (int j = 0; j < gameModes.length; j ++) {
					
					if (permission.groupHas("NULL", playerGroups[i], "Duty." + gameModes[j].toString())) {
						
						return gameModes[j];
						
					}
				}
			}
		}

		return GameMode.CREATIVE;
	}
	
	@EventHandler
	public void onPlayerLogin(PlayerJoinEvent event) {
		
		Player player = event.getPlayer();
		
		if ((boolean) playerCanDuty(player)[0]) {
			
			Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "pp reload");
			
			if ((boolean) playerInDuty(player)[0]) {
				
				player.setGameMode(GameMode.CREATIVE);
				
			}
			
		}
		
	}
	
	@SuppressWarnings("deprecation")
	@EventHandler
	public void onPlayerDeath(PlayerDeathEvent event) {
		
		Player player = (Player) event.getEntity();
		
		final InvRestoreDB db = new InvRestoreDB();
		
		final String name = player.getName();
		final Knapsack knapsack = new Knapsack(player);
		
		BukkitScheduler scheduler = Bukkit.getServer().getScheduler();

		scheduler.scheduleAsyncDelayedTask(this, new Runnable() {
	
			@Override
			public void run() {
		
			db.newKey(name, knapsack);
			
			}
			
		}, 1);
		
	}
	
}
	
	
