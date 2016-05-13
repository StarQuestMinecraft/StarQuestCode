package net.homeip.hall.sqglobalinfo;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.sql.Connection;

import org.bukkit.entity.Player;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerKickEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import org.bukkit.plugin.java.JavaPlugin;

import net.homeip.hall.sqglobalinfo.database.SQLDatabase;
import net.md_5.bungee.api.ChatColor;

import org.bukkit.Bukkit;
import org.bukkit.Location;

public class SQGlobalInfo extends JavaPlugin implements Listener {
	
	private SQLDatabase database;
	private static SQGlobalInfo instance;
	
	public static void main(String[] args) {}
	
	public void onEnable() {
		instance = this;
		loadSQLDatabase();
		this.getServer().getPluginManager().registerEvents(this, this);
	}
	@EventHandler
	public void onPlayerQuit(PlayerQuitEvent event) {
		Player player = event.getPlayer();
		//Retrieves important information about player
		String name = player.getName();
		String uuid = player.getUniqueId().toString();
		String ip = player.getAddress().getAddress().getHostAddress();
		Date time = new Date();
		boolean online = false;
		String world = player.getWorld().getName();
		String location = getLocation(player);
		//Upserts to database
		Bukkit.getScheduler().runTaskAsynchronously(this, new Runnable() {
			public void run() {
				getSQLDatabase().updatePlayerData(name, uuid, ip, time, online, world, location);
			}
		});
	}
	@EventHandler
	public void onPlayerJoin(PlayerJoinEvent event) {
		Player player = event.getPlayer();
		//Retrieves important information about player
		String name = player.getName();
		String uuid = player.getUniqueId().toString();
		String ip = player.getAddress().getAddress().getHostAddress();
		Date time = new Date();
		boolean online = true;
		String world = player.getWorld().getName();
		String location = getLocation(player);
		//Upserts to database
		Bukkit.getScheduler().runTaskAsynchronously(this, new Runnable() {
			public void run() {
				getSQLDatabase().updatePlayerData(name, uuid, ip, time, online, world, location);
			}
		});
	}
	@EventHandler
	public void onPlayerKick(PlayerKickEvent event) {
		Player player = event.getPlayer();
		//Retrieves important information about player
		String name = player.getName();
		String uuid = player.getUniqueId().toString();
		String ip = player.getAddress().getAddress().getHostAddress();
		Date time = new Date();
		boolean online = true;
		String world = player.getWorld().getName();
		String location = getLocation(player);
		//Upserts to database
		Bukkit.getScheduler().runTaskAsynchronously(this, new Runnable() {
			public void run() {
				getSQLDatabase().updatePlayerData(name, uuid, ip, time, online, world, location);
			}
		});
	}
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		Bukkit.getScheduler().runTaskAsynchronously(this, new Runnable() {
			public void run() {
				if(cmd.getName().equalsIgnoreCase("gseen")) {
					if(args.length > 0) {
						String name = args[0];
						//Prevents SQL injection hack
						if(!(containsIllegalCharacters(name))) {
							ArrayList<String> names = getSQLDatabase().getNames(name, getConnection());
							if((names.size() > 0) && (!(names.get(0).equals(null)))) {
								HashMap<String, Object> data = getSQLDatabase().getData(name, getConnection());
								String uuid = (String) data.get("uuid");
								String ip = (String) data.get("ip");
								Date time = (Date) data.get("time");
								boolean online = (Boolean) data.get("online");
								String world = (String) data.get("world");
								String location = (String) data.get("location");
								name = names.get(0);
								String formerNames = "";
								for(int i = 1; i < names.size(); i++) {
									formerNames += names.get(i);
									if(!(names.size() == i + 1)) {
										formerNames += ", ";
									}
								}
								//sends message if given player is online
								if(online == true) {
									//sends message for staff
									if(sender.hasPermission("sqduties.mod")) {
										sender.sendMessage(ChatColor.GOLD + "Player " + ChatColor.RED + name + ChatColor.GOLD + " has been " + ChatColor.GREEN + "online " + ChatColor.GOLD + "for " + ChatColor.RED + getTimeElapsed(time, new Date()) + "\n" + ChatColor.GOLD + " - UUID: " + ChatColor.RED + uuid + "\n" + ChatColor.GOLD + " - Former Names: " + ChatColor.RED + formerNames + "\n" + ChatColor.GOLD + " - IP Address: " + ChatColor.RED + ip + "\n" + ChatColor.GOLD + " - Location: " + ChatColor.RED + location + ChatColor.GOLD + " on " + ChatColor.RED + world);	
									}
									//sends message for non-staff
									else {
										sender.sendMessage(ChatColor.GOLD + "Player " + ChatColor.RED + name + ChatColor.GOLD + " has been " + ChatColor.GREEN + "online " + ChatColor.GOLD + "for " + ChatColor.RED + getTimeElapsed(time, new Date())  + "\n" + ChatColor.GOLD + " - UUID: " + ChatColor.RED + uuid + "\n" + ChatColor.GOLD + " - Former Names: " + ChatColor.RED + formerNames);	
									}
								}
								//sends message if given player is offline
								else {
									//sends message for staff
									if(sender.hasPermission("sqduties.mod")) {
										sender.sendMessage(ChatColor.GOLD + "Player " + ChatColor.RED + name + ChatColor.GOLD + " has been " + ChatColor.DARK_RED + "offline " + ChatColor.GOLD + "for " + ChatColor.RED + getTimeElapsed(time, new Date())  + "\n" + ChatColor.GOLD + " - UUID: " + ChatColor.RED + uuid + "\n" + ChatColor.GOLD + " - Former Names: " + ChatColor.RED + formerNames + "\n" + ChatColor.GOLD + " - IP Address: " + ChatColor.RED + ip + "\n" + ChatColor.GOLD + " - Location: " + ChatColor.RED + location + ChatColor.GOLD + " on " + ChatColor.RED + world);
									}
									//sends message for non-staff
									else {
										sender.sendMessage(ChatColor.GOLD + "Player " + ChatColor.RED + name + ChatColor.GOLD + " has been " + ChatColor.DARK_RED + "offline " + ChatColor.GOLD + "for " + ChatColor.RED + getTimeElapsed(time, new Date())  + "\n" + ChatColor.GOLD + " - UUID: " + ChatColor.RED + uuid + "\n" + ChatColor.GOLD + " - Former Names: " + ChatColor.RED + formerNames);
									}
								}
							}
							//if no players found
							else {
								sender.sendMessage(ChatColor.DARK_RED + "Player not seen");
							}
						}
						//argument contained illegal characters
						else {
							sender.sendMessage(ChatColor.DARK_RED + "Given name contained illegal characters.");
						}
					}
					//not enough arguments
					else {
						sender.sendMessage("Proper usage: /gseen <name>");
					}
				}
				else if((cmd.getName().equals("showalts")) && (sender.hasPermission("sqduties.mod"))) {
					if(args.length > 1) {
						//Prevents SQL injection hack
						if(!(containsIllegalCharacters(args[1]))) {
							ArrayList<String> names = null;
							String namesAsString = "";
							//if player inputted an ip
							if(args[0].equalsIgnoreCase("ip")) {
								names = getSQLDatabase().getAltsFromIP(args[1], getConnection());
								if(names.size() > 0) {
									//Concatenates results (player names), separated by space and comma, into a usable String
									for(int i = 0; i < names.size(); i++) {
										namesAsString += names.get(i);
										if(names.size() != i + 1) {
											namesAsString += ", ";
										}
									}
									sender.sendMessage(ChatColor.GOLD + "Players with IP of " + ChatColor.RED + args[1] + ChatColor.GOLD + ": \n" + namesAsString);
								}
								//If no players were found
								else {
									sender.sendMessage(ChatColor.DARK_RED + "No players with given  IP found.");
								}
							}
							//if player inputted a name
							else if(args[0].equalsIgnoreCase("name")) {
								names = getSQLDatabase().getAltsFromName(args[1], getConnection());
								if(names.size() > 0) {
									//Concatenates results (player names), separated by space and comma, into a usable String
									for(int i = 0; i < names.size(); i++) {
										namesAsString += names.get(i);
										if(names.size() != i + 1) {
											namesAsString += ", ";
										}
									}
									sender.sendMessage(ChatColor.GOLD + "Players with IP of " + ChatColor.RED + (String) getSQLDatabase().getData(args[1], getConnection()).get("ip") + ChatColor.GOLD + ": \n" + namesAsString);
								}
								//If no players were found
								else {
									sender.sendMessage(ChatColor.DARK_RED + "No players with given name found.");
								}
							}
							//if player inputted neither an ip nor a name
							else {
								sender.sendMessage("Shows possible alts based on IP given a player's name or IP. Proper usage: /showalts <ip | name> <player's ip | player's name>");
							}
						}
						else {
							sender.sendMessage(ChatColor.DARK_RED + "Illegal characters found in command.");
						}
					}
					else {
						sender.sendMessage("Shows possible alts based on IP given a player's name or IP. Proper usage: /showalts <ip | name> <player's ip | player's name>");
					}
				}
			}
		});
		return false;
	}
	
	//Returns the player's location in (x, y, z) String format
	private String getLocation(Player player) {
		Location location = player.getLocation();
		return "(" + ((int) location.getX()) + ", " + ((int) location.getY()) + ", " + ((int) location.getZ()) + ")"; 
		
	}
	
	public static SQGlobalInfo getInstance() {
		return instance;
	}
	
	private SQLDatabase getSQLDatabase() {
		return database;
	}
	private void loadSQLDatabase() {
		database = new SQLDatabase();
	}
	//returns sqlconnect session
	private Connection getConnection() {
		return getSQLDatabase().getConnection();
	}
	//Prevents escaping of query, which in turn prevents sql injection attacks
	private boolean containsIllegalCharacters(String query) {
		if((query.contains("\"")) || (query.contains("\\"))) {
			return true;
		}
		return false;
	}
	//returns time elapsed in weeks, days, hours, minutes, seconds String format from two millisecond timecodes
	private String getTimeElapsed(Date timethen, Date timenow) {
		long millisecondsElapsed = timenow.getTime() - timethen.getTime();
		long seconds = millisecondsElapsed / 1000;
		long minutes = seconds / 60;
		long hours = minutes / 60;
		long days = hours / 24;
		long weeks = days / 7;
		String time = weeks + " weeks " + (days % 7) + " days " + (hours % 24) + " hours " + (minutes % 60) + " minutes " + (seconds % 60) + " seconds";
		return time;
	}
}
