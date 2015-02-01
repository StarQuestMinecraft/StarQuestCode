package com.starquestminecraft.dynamicwhitelist;

import java.util.HashSet;
import java.util.Iterator;
import java.util.UUID;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;

import com.starquestminecraft.dynamicwhitelist.flatfiledb.DatabaseFlatfile;

import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.ServerPing;
import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.config.ServerInfo;
import net.md_5.bungee.api.connection.Connection;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.event.ChatEvent;
import net.md_5.bungee.api.event.LoginEvent;
import net.md_5.bungee.api.event.PlayerDisconnectEvent;
import net.md_5.bungee.api.event.PostLoginEvent;
import net.md_5.bungee.api.event.ProxyPingEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.api.plugin.Plugin;
import net.md_5.bungee.event.EventHandler;
import net.md_5.bungee.event.EventPriority;

public class Whitelister extends Plugin implements Listener {

	private HashSet<UUID> premiumPlayers = new HashSet<UUID>();

	private static Whitelister instance;

	private static final int MAX_STANDARD_PLAYERS = 20;

	private static final ServerInfo WAIT_SERVER = ProxyServer.getInstance().getServerInfo("waitroom");

	private static final LinkedBlockingQueue<QueuedPlayer> playerQueue = new LinkedBlockingQueue<QueuedPlayer>();

	private Database d;

	private boolean useQueue = false;

	public void onEnable() {
		instance = this;
		d = new DatabaseFlatfile();
		getProxy().getPluginManager().registerListener(this, this);
		getProxy().getPluginManager().registerCommand(this, new AddCommand());
		getProxy().getPluginManager().registerCommand(this, new SubscriptionCommand());
		getProxy().getPluginManager().registerCommand(this, new PermanentCommand());
		getProxy().getPluginManager().registerCommand(this, new QueueCommand());
		useQueue = d.isUsingQueue();
	}

	@EventHandler
	public void onServerListPing(ProxyPingEvent event) {
		ServerPing ping = event.getResponse();
		ping.setDescription("§1=====§6Star§9Quest§1=====" + " \n§7Premium Players: " + premiumPlayers.size() + " §7Standard Players: " + getStandardPlayerCount());
	}

	@EventHandler(priority = EventPriority.HIGHEST)
	public void onLogin(LoginEvent event) {
		if (useQueue)
			return;
		if (!event.isCancelled()) {
			UUID u = event.getConnection().getUniqueId();
			if (!d.hasPlayedBefore(u)) {
				d.registerNewPlayer(u);
				d.addPremiumTime(u, 168);
				System.out.println("New player joined!");
				sendDelayedMessage(u, "Welcome to StarQuest! As a new player, you recieve 48 hours of Priority Access!");
				sendDelayedMessage(u, "visit http://starquestminecraft.buycraft.net to subscribe to Priority Access!");
				System.out.println("Is Premium!");
				premiumPlayers.add(u);
				System.out.println(premiumPlayers.size());
				return;
			}
			boolean isPremium = d.isPremium(u);
			if (isPremium) {
				premiumPlayers.add(u);
				System.out.println(premiumPlayers.size());
				sendDelayedMessage(u, "Welcome! Thank you for using SQ Priority!");
			} else {
				if (getStandardPlayerCount() >= MAX_STANDARD_PLAYERS) {
					event.setCancelReason("Sorry, StarQuest is full right now! To join a full server purchase Priority Access at http://starquestminecraft.buycraft.net");
					event.setCancelled(true);
				} else {
					System.out.println(getStandardPlayerCount());
					sendDelayedMessage(u, "Welcome to StarQuest!");
				}
			}
		}
	}

	@EventHandler
	public void onPostLogin(PostLoginEvent event) {
		if (!useQueue)
			return;
		UUID u = event.getPlayer().getUniqueId();
		if (!d.hasPlayedBefore(u)) {
			d.registerNewPlayer(u);
			d.addPremiumTime(u, 168);
			System.out.println("New player joined!");
			sendDelayedMessage(u, "Welcome to StarQuest! As a new player, you recieve one week of Priority Access!");
			sendDelayedMessage(u, "visit http://starquestminecraft.buycraft.net to subscribe to Priority Access!");
			System.out.println("Is Premium!");
			premiumPlayers.add(u);
			System.out.println(premiumPlayers.size());
			return;
		}
		boolean isPremium = d.isPremium(u);
		if (isPremium) {
			premiumPlayers.add(u);
			System.out.println(premiumPlayers.size());
			sendDelayedMessage(u, "Welcome! Thank you for using SQ Priority!");
		} else {
			if (getStandardPlayerCount() >= MAX_STANDARD_PLAYERS) {
				addToQueue(event.getPlayer());
				event.getPlayer().connect(WAIT_SERVER);
				sendDelayedMessage(u, "StarQuest is full for standard players right now. You will be transferred to the server as soon as a slot is available.");
			} else {
				System.out.println(getStandardPlayerCount());
				sendDelayedMessage(u, "Welcome to StarQuest!");
			}
		}
	}

	private static void addToQueue(ProxiedPlayer plr) {
		try {
			QueuedPlayer q = new QueuedPlayer(plr.getName(), plr.getUniqueId(), plr.getReconnectServer() );
			playerQueue.put(q);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	@EventHandler
	public void onQuit(PlayerDisconnectEvent event) {
		UUID u = event.getPlayer().getUniqueId();
		boolean isPremium = premiumPlayers.contains(u);
		if (isPremium) {
			premiumPlayers.remove(u);
			System.out.println("Premium Quit!");
			System.out.println(premiumPlayers.size());
		} else if (isQueued(u)) {
			System.out.println("Queued Quit!");
			QueuedPlayer q = unQueue(u);
			if(q != null){
				event.getPlayer().setReconnectServer(q.getReconnect());
			}
			System.out.println(playerQueue.size());
		} else {
			System.out.println("Standard Quit!");
			int stdCount = getStandardPlayerCount();
			System.out.println(stdCount);
			if (stdCount < 20) {
				QueuedPlayer queuedPlr = playerQueue.poll();
				ProxiedPlayer plr = ProxyServer.getInstance().getPlayer(queuedPlr.getUniqueId());
				if (plr != null) {
					plr.connect(queuedPlr.getReconnect());
					plr.sendMessage(createMessage("A slot opened up on the server, welcome to StarQuest!"));
				}
			}
		}
	}

	private static boolean isQueued(UUID u) {
		for(QueuedPlayer q : playerQueue){
			if(q.getUniqueId().equals(u)){
				return true;
			}
		}
		return false;
	}
	
	private static QueuedPlayer unQueue(UUID u){
		Iterator<QueuedPlayer> i = playerQueue.iterator();
		while(i.hasNext()){
			QueuedPlayer q = i.next();
			if(q.getUniqueId().equals(u)){
				i.remove();
				return q;
			}
		}
		return null;
	}

	public static Whitelister getInstance() {
		return instance;
	}

	public Database getDatabase() {
		return d;
	}

	private void sendDelayedMessage(final UUID u, final String message) {
		getProxy().getScheduler().schedule(this, new Runnable() {
			public void run() {
				ProxiedPlayer p = getProxy().getPlayer(u);
				if (p != null) {
					p.sendMessage(createMessage(message));
				}
			}
		}, 1000, TimeUnit.MILLISECONDS);
	}

	private static BaseComponent[] createMessage(String s) {
		return new ComponentBuilder(s).create();
	}

	private int getStandardPlayerCount() {
		return getProxy().getOnlineCount() - premiumPlayers.size();
	}
	
	public void setQueue(boolean value){
		useQueue = value;
		getDatabase().setUsingQueue(value);
		if(value == false){
			playerQueue.clear();
			for(ProxiedPlayer plr : ProxyServer.getInstance().getPlayers()){
				if(plr.getServer().getInfo().equals(WAIT_SERVER)){
					plr.disconnect(createMessage("Queue shut down!"));
				}
			}
		}
	}
}
