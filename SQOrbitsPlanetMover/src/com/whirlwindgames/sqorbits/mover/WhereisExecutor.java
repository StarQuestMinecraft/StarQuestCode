package com.whirlwindgames.sqorbits.mover;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.HashSet;
import java.util.Set;

import net.md_5.bungee.api.ChatColor;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;


public class WhereisExecutor implements CommandExecutor {
	private final SQOrbitsPlanetMover plugin;
	private Set<CommandSender> recentSenders;
	private String chatPrefix = "[SQOrbits]: ";
	
	protected WhereisExecutor(SQOrbitsPlanetMover plugin) {
		super();
		this.plugin = plugin;
		recentSenders = new HashSet<>();
	}

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if(!sender.hasPermission("SQOrbits.whereis")){
			sender.sendMessage(ChatColor.RED+""+chatPrefix+"You don't have permission to use that command.");
		} else {			
			boolean isPlayer;
			if (sender instanceof Player){
				if (recentSenders.contains(sender)){
					sender.sendMessage(ChatColor.RED+""+chatPrefix+"You must wait 5 seconds before issuing that command again.");
					return true;
				} else {
					recentSenders.add(sender);
					Bukkit.getScheduler().runTaskLater(plugin, () -> recentSenders.remove(sender), 100);
				}
				isPlayer = true;
			} else isPlayer= false;
			boolean hasArgs = (args.length==0)?false:true;
			Bukkit.getScheduler().runTaskAsynchronously(plugin, 
					() -> {
						try {
							String sqlString = "SELECT x,y,z,name FROM "+plugin.tableName;
							//if it's player, it doesn't show planets that are in other systems
							//if it's the console, it shows all planets
							//if a planet is specified only that planet it shown
							if (hasArgs || isPlayer) 	sqlString += " WHERE ";
							if (isPlayer) 				sqlString += "system_name=?";
							if (isPlayer && hasArgs) 	sqlString += " AND ";
							if (hasArgs) 				sqlString += " name=?";
							Connection connection = plugin.getConnection();
							PreparedStatement statement = connection.prepareStatement(sqlString);
							if (isPlayer) 				statement.setString(1, ((Player)sender).getWorld().getName());
							if (!isPlayer && hasArgs) 	statement.setString(1, args[0]);
							if (isPlayer && hasArgs) 	statement.setString(2, args[0]);
							
							statement.execute();
							ResultSet results = statement.getResultSet();
							int numResults = 0;
							while(results.next()){
								sender.sendMessage(chatPrefix+"Planet '"+results.getString("name")+"' has xyz coordinates ("
										+results.getInt("x")+", "
										+results.getInt("y")+", "
										+results.getInt("z")+")");
								numResults ++;
							}
							if (numResults==0) sender.sendMessage(chatPrefix+"We couldn't find any "
									+ "planet matching that name, did you spell it right?");
							connection.close();
						} catch (Exception e) {
							sender.sendMessage(ChatColor.RED+""+chatPrefix+"An error occurred finding the planet coordinates you asked for.");
							e.printStackTrace();
						}
					});
		}
		return true;
	}
}
