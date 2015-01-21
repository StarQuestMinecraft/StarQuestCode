package com.starquestminecraft.dynamicwhitelist;

import java.util.HashSet;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

import com.starquestminecraft.dynamicwhitelist.flatfiledb.DatabaseFlatfile;

import net.md_5.bungee.api.ServerPing;
import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.connection.Connection;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.event.ChatEvent;
import net.md_5.bungee.api.event.LoginEvent;
import net.md_5.bungee.api.event.PlayerDisconnectEvent;
import net.md_5.bungee.api.event.ProxyPingEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.api.plugin.Plugin;
import net.md_5.bungee.event.EventHandler;
import net.md_5.bungee.event.EventPriority;

public class Whitelister extends Plugin implements Listener{
	
	private HashSet<UUID> premiumPlayers = new HashSet<UUID>();
	
	private static Whitelister instance;
	
	private static final int MAX_STANDARD_PLAYERS = 20;
	private Database d;
	
	public void onEnable(){
		instance = this;
		d = new DatabaseFlatfile();
		getProxy().getPluginManager().registerListener(this, this);
		getProxy().getPluginManager().registerCommand(this, new AddCommand());
		getProxy().getPluginManager().registerCommand(this, new SubscriptionCommand());
		getProxy().getPluginManager().registerCommand(this, new PermanentCommand());
	}
	
	@EventHandler
	public void onServerListPing(ProxyPingEvent event){
		ServerPing ping = event.getResponse();
		ping.setDescription("§1=====§6Star§9Quest§1=====" + " \n§7Premium Players: " + premiumPlayers.size() + " §7Standard Players: " + getStandardPlayerCount());
	}
	
	@EventHandler(priority = EventPriority.HIGHEST)
	public void onLogin(LoginEvent event){
		if(!event.isCancelled()){
			UUID u = event.getConnection().getUniqueId();
			System.out.println("LoginEvent Called");
			if(!d.hasPlayedBefore(u)){
				d.registerNewPlayer(u);
				d.addPremiumTime(u, 168);
				System.out.println("New player joined!");
				sendDelayedMessage(u, "Welcome to StarQuest! As a new player, you recieve 48 hours of Priority Access!" );
				sendDelayedMessage(u, "visit http://starquestminecraft.buycraft.net to subscribe to Priority Access!");
				System.out.println("Is Premium!");
				premiumPlayers.add(u);
				System.out.println(premiumPlayers.size());
				return;
			}
			boolean isPremium = d.isPremium(u);
			if(isPremium){
				System.out.println("Is Premium!");
				premiumPlayers.add(u);
				System.out.println(premiumPlayers.size());
				sendDelayedMessage(u, "Welcome! Thank you for using SQ Priority!");
			} else {
				System.out.println("Is not Premium!!");
			if(getStandardPlayerCount() >= MAX_STANDARD_PLAYERS){
					event.setCancelReason("Sorry, StarQuest is full right now! To join a full server purchase Priority Access at http://starquestminecraft.buycraft.net");
					event.setCancelled(true);
				} else {
					System.out.println(getStandardPlayerCount());
					sendDelayedMessage(event.getConnection().getUniqueId(), "Welcome to StarQuest!");
				}
			}
		}
	}
	
	@EventHandler
	public void onQuit(PlayerDisconnectEvent event){
		UUID u = event.getPlayer().getUniqueId();
		boolean isPremium = premiumPlayers.contains(u);
		if(isPremium){
			premiumPlayers.remove(u);
			System.out.println("Premium Quit!");
			System.out.println(premiumPlayers.size());
		} else {
			System.out.println("Standard Quit!");
			System.out.println(getStandardPlayerCount());
		}
	}
	
	public static Whitelister getInstance(){
		return instance;
	}
	
	public Database getDatabase(){
		return d;
	}
	
	private void sendDelayedMessage(final UUID u, final String message){
		getProxy().getScheduler().schedule(this, new Runnable(){
			public void run(){
				ProxiedPlayer p = getProxy().getPlayer(u);
				if( p != null){
					p.sendMessage(createMessage(message));
				}
			}
		}, 1000, TimeUnit.MILLISECONDS);
	}
	
	private static BaseComponent[] createMessage(String s){
		return new ComponentBuilder(s).create();
	}
	
	private int getStandardPlayerCount(){
		return getProxy().getOnlineCount() - premiumPlayers.size();
	}
}
