package com.starquestminecraft.sqrankup2;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;

import net.milkbowl.vault.chat.Chat;
import net.milkbowl.vault.economy.Economy;
import net.milkbowl.vault.permission.Permission;

import org.bukkit.Achievement;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerAchievementAwardedEvent;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;

import com.starquestminecraft.sqrankup2.database.Database;
import com.starquestminecraft.sqrankup2.database.SQLDatabase;

public class SQRankup2 extends JavaPlugin implements Listener{

	HashMap<String, Certification> certPool = new HashMap<String, Certification>();
	HashMap<String, BonusTag> bonusTags = new HashMap<String, BonusTag>();

	private static SQRankup2 instance;
	private Database database;
	public static Permission permission;
	public static Chat chat;
	public static Economy eco;

	public void onEnable() {
		instance = this;
		database = new SQLDatabase();
		int count = 0;
		for (File f : this.getDataFolder().listFiles()) {
			if (f.getName().endsWith(".cert")) {
				Certification c = new Certification(f.getPath());
				certPool.put(c.getIdentifier(), c);
				count++;
			}
		}
		setupPermissions();
		setupChat();
		setupEconomy();
		Bukkit.getPluginManager().registerEvents(this, this);
		AchievementTag.loadAchievements(bonusTags);

		System.out.println("Certs loaded from file: " + count);
	}

	private boolean setupPermissions() {
		RegisteredServiceProvider<Permission> permissionProvider = getServer().getServicesManager().getRegistration(net.milkbowl.vault.permission.Permission.class);
		if (permissionProvider != null) {
			permission = permissionProvider.getProvider();
		}
		return (permission != null);
	}

	private boolean setupChat() {
		RegisteredServiceProvider<Chat> chatProvider = getServer().getServicesManager().getRegistration(net.milkbowl.vault.chat.Chat.class);
		if (chatProvider != null) {
			chat = chatProvider.getProvider();
		}

		return (chat != null);
	}
	
	private boolean setupEconomy(){
		RegisteredServiceProvider<Economy> ecoProvider = getServer().getServicesManager().getRegistration(net.milkbowl.vault.economy.Economy.class);
		if (ecoProvider != null) {
			eco = ecoProvider.getProvider();
		}

		return (eco != null);
	}

	public static SQRankup2 get() {
		return instance;
	}

	public Database getCertsDatabase() {
		return database;
	}

	public boolean onCommand(CommandSender sender, Command cmd, String label, final String[] args) {
		if (args.length < 1) {
			displayHelp(sender);
			return true;
		}
		final String keyArg = args[0];
		if (!(sender instanceof Player))
			return false;
		final Player p = (Player) sender;
		Bukkit.getServer().getScheduler().runTaskAsynchronously(this, new Runnable() {
			public void run() {
				switch (keyArg) {
				case "unlock":
					handleUnlock(p, args);
					return;
				case "remove":
					removeCert(p, args);
					return;
				case "list":
					listCerts(p, args);
					return;
				case "tag":
					handleTags(p, args);
					return;
				default:
					displayHelp(p);
				}
			}
		});
		return true;
	}

	private void handleUnlock(Player p, String[] args) {
		ArrayList<String> playerCerts = database.getCertsOfPlayer(p.getUniqueId());
		ArrayList<Certification> available = getAvailableUnlocks(p, playerCerts);
		if (args.length == 1) {
			// display the list of available unlocks for the sender
			displayUnlocks(p, available);
		} else {
			if (args.length == 2) {
				try {
					int i = Integer.parseInt(args[1]);
					System.out.println(i);
					System.out.println(available.size());
					if (i > -1 && i < available.size()) {
						Certification c = available.get(i);
						if(!c.canAffordCosts(p)){
							p.sendMessage("you do not have the credits to purchase this cert.");
							return;
						}
						if(c.hasLevels(p)){
							c.takeCost(p);
							c.giveToPlayer(p);
							playerCerts.add(c.getIdentifier());
							database.updateCertsOfPlayer(p.getUniqueId(), playerCerts);
							p.sendMessage("You have succesfully unlocked cert " + c.getIdentifier() + "!");
						} else if(c.hasAltLevels(p)){
							c.takeCost(p);
							c.giveToPlayer(p);
							playerCerts.add("alt-" + c.getIdentifier());
							database.updateCertsOfPlayer(p.getUniqueId(), playerCerts);
							p.sendMessage("You have succesfully unlocked cert " + c.getIdentifier() + "!");
						} else {
							p.sendMessage("You do not have the required contract levels for this cert.");
						}
						
					} else {
						p.sendMessage("This is not a valid number");
						return;
					}
				} catch (Exception e) {
					e.printStackTrace();
					p.sendMessage("This is not a valid number.");
					return;
				}
			}
		}
	}

	private void displayUnlocks(Player p, ArrayList<Certification> unlocks) {

		p.sendMessage(ChatColor.GREEN + "Available certifications to unlock:");
		p.sendMessage(ChatColor.GOLD + "============================================");
		for (int i = 0; i < unlocks.size(); i++) {
			Certification c = unlocks.get(i);
			ChatColor color;
			if (i % 2 == 0) {
				color = ChatColor.YELLOW;
			} else {
				color = ChatColor.LIGHT_PURPLE;
			}
			p.sendMessage(color + "" + i + "): " + c.getFullDescription(color));
		}
		p.sendMessage(ChatColor.GOLD + "============================================");
	}

	private ArrayList<Certification> getAvailableUnlocks(Player p, ArrayList<String> existingCerts) {
		ArrayList<Certification> retval = new ArrayList<Certification>();
		for (Certification c : certPool.values()) {
			System.out.println("testing cert: " + c.getIdentifier());
			if (!existingCerts.contains(c.getIdentifier()) && !existingCerts.contains("alt-" + c.getIdentifier())) {
				if (c.satisfiesPreReqs(existingCerts) || c.satisfiesAltPreReqs(existingCerts)) {
					System.out.println("Cert passed!");
					retval.add(c);
				}
			}
		}
		return retval;
	}

	private void removeCert(Player p, String[] args) {
		/*String id = args[1];
		ArrayList<String> playerCerts = database.getCertsOfPlayer(p.getUniqueId());
		if (playerCerts.contains(id)) {
			playerCerts.remove(id);
			removeCertFromDb(p, id, playerCerts);
		} else if (playerCerts.contains(stripAlt(id))){
			playerCerts.remove(stripAlt(id));
			removeCertFromDb(p, id, playerCerts);
		} else {
			p.sendMessage("You do not have a cert with this identifier.");
		}*/
		try{
			int i = Integer.parseInt(args[1]);
			ArrayList<String> playerCerts = database.getCertsOfPlayer(p.getUniqueId());
			if(i < 0 || i >= playerCerts.size()){
				p.sendMessage("This is not a valid number. Please provide the number of the cert you wish to remove from /cert list.");
				return;
			}
			String cert = playerCerts.get(i);
			Certification c = certPool.get(stripAlt(cert));
			System.out.println("Removing cert!");
			if(c != null){
				System.out.println("Removing cert!");
				c.removeFromPlayer(p);
				playerCerts.remove(cert);
				database.updateCertsOfPlayer(p.getUniqueId(), playerCerts);
				p.sendMessage("Cert removed.");
			} else {
				p.sendMessage("Error: cert not found.");
			}
		} catch (NumberFormatException e){
			p.sendMessage("This is not a valid number. Please provide the number of the cert you wish to remove from /cert list.");
		}
	}
	
	private void listCerts(Player p, String[] args) {
		p.sendMessage("Your current certifications are:");
		ArrayList<String> playerCerts = database.getCertsOfPlayer(p.getUniqueId());
		int num = 0;
		for (String s : playerCerts) {
			if(isAlt(s)){
				p.sendMessage(num + "): " + stripAlt(s));
			} else {
				p.sendMessage(num + "): " + s);
			}
			num++;
		}
	}

	private void handleTags(Player p, String[] args) {
		if (args.length > 1) {
			setTag(p, args[1]);
		} else {
			displayTagList(p);
		}
	}

	private void setTag(Player p, String string) {

		ArrayList<String> tags = getTagList(p);
		try{
			int id = Integer.parseInt(string);
			String tag = tags.get(id);
			command("permissions player " + p.getName() + " prefix " + tag);
			p.sendMessage("Your tag has been updated.");
			return;
		} catch(NumberFormatException e){
			p.sendMessage("This is not a valid tag ID. Type the number of the tag from /cert tag");
		}
	}

	private void displayTagList(Player p) {
		p.sendMessage("Your available tags:");
		int num =  0;
		for (String s : getTagList(p)) {
			p.sendMessage(num + "): " + s);
			num++;
		}
	}
	
	private ArrayList<String> getTagList(Player p){
		ArrayList<String> playerCerts = database.getCertsOfPlayer(p.getUniqueId());
		ArrayList<String> retval = new ArrayList<String>();
		for (String s : playerCerts) {
			Certification c = certPool.get(stripAlt(s));
			if(c != null){
				if(isAlt(s)){
					String tag = c.getAltTagFormatted();
					if(tag != null){
						retval.add(c.getAltTagFormatted());
					}
				} else {
					String tag = c.getTagFormatted();
					if(tag != null){
						retval.add( c.getTagFormatted());
					}
				}
			} else {
				BonusTag t = bonusTags.get(s);
				if(t != null){
					retval.add(t.getTagFormatted());
				}
			}
		}
		return retval;
	}

	private void displayHelp(CommandSender sender) {
		sender.sendMessage("/certs list: list your current certifications.");
		sender.sendMessage("/certs unlock: displays your available unlocks.");
		sender.sendMessage("/certs unlock {id}: unlock the cert with {id} from your /certs unlock list.");
		sender.sendMessage("/certs remove {id}: remove the cert with {id} from your unlocked certs.");
		sender.sendMessage("/certs tag: displays your available tags.");
		sender.sendMessage("/certs tag {id}: sets your current tag to the tag with {id} from the /certs tag list.");
	}

	private void command(String command) {
		Bukkit.dispatchCommand(Bukkit.getConsoleSender(), command);
	}
	
	@EventHandler
	public void onPlayerAchievement(PlayerAchievementAwardedEvent event){
		System.out.println("Achievement!");
		Achievement a = event.getAchievement();
		for(String s : bonusTags.keySet()){
			System.out.println("Testing bonus tag " + s);
			BonusTag t = bonusTags.get(s);
			if(t instanceof AchievementTag){
				AchievementTag tag = (AchievementTag) t;
				Achievement a2 = tag.getAchievement();
				if(a2 == a){
					System.out.println("Achievement found!");
					String id = tag.getIdentifier();
					ArrayList<String> certs = database.getCertsOfPlayer(event.getPlayer().getUniqueId());
					if(!certs.contains(id)){
						System.out.println("Adding cert for bonus tag.");
						certs.add(id);
					}
					database.updateCertsOfPlayer(event.getPlayer().getUniqueId(), certs);
					event.getPlayer().sendMessage("You have unlocked the tag " + tag.getTagFormatted() + ", do /certs tag to set it.");
					return;
				}
			}
		}
	}
	
	public boolean isAlt(String s){
		return s.startsWith("alt-");
	}
	
	public String stripAlt(String s){
		if(s.startsWith("alt-")){
			return s.substring(4, s.length());
		}
		return s;
	}
}
