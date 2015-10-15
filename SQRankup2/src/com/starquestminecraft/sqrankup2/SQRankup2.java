package com.starquestminecraft.sqrankup2;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

import net.milkbowl.vault.chat.Chat;
import net.milkbowl.vault.economy.Economy;
import net.milkbowl.vault.permission.Permission;

import org.bukkit.Achievement;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerAchievementAwardedEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;

import com.starquestminecraft.sqcontracts.SQContracts;
import com.starquestminecraft.sqcontracts.database.ContractPlayerData;
import com.starquestminecraft.sqrankup2.database.Database;
import com.starquestminecraft.sqrankup2.database.SQLDatabase;

public class SQRankup2 extends JavaPlugin implements Listener{

	HashMap<String, Certification> certPool = new HashMap<String, Certification>();
	public HashMap<String, Certification> defaultCerts = new HashMap<String, Certification>();
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
				if(c.isDefault()){
					defaultCerts.put(c.getIdentifier(), c);
				}
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
	
	@EventHandler
	public void onPlayerJoin(PlayerJoinEvent event){
		if(Bukkit.getServerName().equals("Trinitos_Alpha")){
			if(!event.getPlayer().hasPlayedBefore()){
				System.out.println("New player @alpha!");
				System.out.println("Stripping groups!");
				for(String s : permission.getPlayerGroups(event.getPlayer())){
					if(s.equalsIgnoreCase("mod") || s.equalsIgnoreCase("developer") || s.equalsIgnoreCase("srmod") || s.equalsIgnoreCase("trlmod") || s.equalsIgnoreCase("trmod") || s.equalsIgnoreCase("jrdev") || s.equalsIgnoreCase("Manager")){
						continue;
					}
					permission.playerRemoveGroup(event.getPlayer(), s);
				}
				permission.playerAddGroup(event.getPlayer(), "Refugee");
			}
		}
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
		if(cmd.getName().equalsIgnoreCase("certification")){
			if (args.length < 1) {
				displayHelp(sender);
				return true;
			}
			if(args[0].equalsIgnoreCase("reload") && (sender instanceof ConsoleCommandSender)){
				reload();
			}
			final String keyArg = args[0];
			if (!(sender instanceof Player))
				return false;
			final Player p = (Player) sender;
			if(!p.hasPermission("rankup.use")){
				p.sendMessage("You must be a [P] (player) to use Rankup! Apply for [P] at http://tinyurl.com/starquestapps");
				return true;
			}
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
					case "available":
						handleAvailable(p, args);
						return;
					default:
						displayHelp(p);
					}
				}
			});
			return true;
		} else if ((cmd.getName().equalsIgnoreCase("addapp")) && (sender.hasPermission("SQRankup.addApplication"))) {
			if (args.length >= 1) {
				getServer().broadcastMessage(ChatColor.RED + args[0] + " has ranked up to Player!");
				getServer().broadcastMessage(
						ChatColor.RED + "Are you a [" + ChatColor.DARK_GRAY + "G" + ChatColor.RED + "] (Guest)? Rank up to [" + ChatColor.GRAY + "P"
								+ ChatColor.RED + "] (Player) like " + args[0] + " did!");
				getServer().broadcastMessage(
						ChatColor.RED + "Visit " + ChatColor.BLUE + "http://tinyurl.com/starquestapps" + ChatColor.RED + " to apply for Player rank!");

				OfflinePlayer p = getServer().getOfflinePlayer(args[0]);
				permission.playerAddGroup(null, p, "SETTLER");
				permission.playerRemoveGroup(null, p, "REFUGEE");
				if (args.length >= 2) {
					Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "money give " + args[1] + " 30000");
					OfflinePlayer p2 = getServer().getOfflinePlayer(args[1]);
					if(p2 != null){
						ContractPlayerData d = SQContracts.get().getContractDatabase().getDataOfPlayer(p2.getUniqueId());
						d.setBalanceInCurrency("philanthropy", d.getBalanceInCurrency("philanthropy") + 1);
						SQContracts.get().getContractDatabase().updatePlayerData(p2.getUniqueId(), d);
					}
					getServer().broadcastMessage(
							ChatColor.GOLD + args[1] + ChatColor.RED + " brought " + args[0] + " to the server and earned 30000c + one philanthropy point for doing so!");
				}
				return true;
			}
			sender.sendMessage("Needs an argument.");
			return false;
		} else if (cmd.getName().equalsIgnoreCase("resetrank") && sender.hasPermission("SQRankup.resetrank")){
			if(args.length < 1){
				sender.sendMessage("You need to provide a player!");
				return true;
			}
			Player p = Bukkit.getPlayer(args[0]);
			if(p == null){
				sender.sendMessage("This player is not online, attempting to set group, but make sure you typed their name right!");
			} else {
				sender.sendMessage("Setting group.");
			}
			Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "perms player " + args[0] + " setgroup Settler");
			Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "sync console all perms refresh");
			return true;
		}
		return false;
	}

	private void reload() {
		int count = 0;
		certPool.clear();
		defaultCerts.clear();
		for (File f : this.getDataFolder().listFiles()) {
			if (f.getName().endsWith(".cert")) {
				Certification c = new Certification(f.getPath());
				certPool.put(c.getIdentifier(), c);
				if(c.isDefault()){
					defaultCerts.put(c.getIdentifier(), c);
				}
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

	private void handleUnlock(Player p, String[] args) {
		ArrayList<String> playerCerts = database.getCertsOfPlayer(p.getUniqueId());
		ArrayList<Certification> available = getAvailableUnlocks(p, playerCerts);
		if (args.length == 1) {
			// display the list of available unlocks for the sender
			p.sendMessage("do /certs available to see your available unlocks, then do /certs unlock # to unlock that cert.");
		} else {
			if (args.length >= 2) {
				try {
					int i = Integer.parseInt(args[1]);
					UnlockType type;
					if(args.length >= 3){
						if(args[2].equalsIgnoreCase("outlaw")){
							type = UnlockType.OUTLAW;
						} else if(args[2].equalsIgnoreCase("lawful")){
							type = UnlockType.LAWFUL;
						} else {
							type = UnlockType.NEUTRAL;
						}
					} else {
						type = UnlockType.NEUTRAL;
					}
					if (i > -1 && i < available.size()) {
						Certification c = available.get(i);
						if(!c.canAffordCosts(p)){
							p.sendMessage("you do not have the credits to purchase this cert.");
							return;
						}
						ContractPlayerData d = SQContracts.get().getContractDatabase().getDataOfPlayer(p.getUniqueId());
						if(c.hasLevels(d)){
							String certType = getTypeOfCertToGive(c, p, d, type);
							if(certType == null){
								p.sendMessage("You specified " + args[2] + " for your unlock but you do not have the levels.");
								return;
							}
							c.takeCost(p);
							c.giveToPlayer(p);
							playerCerts.add(certType + "-" + c.getIdentifier());
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
	
	public static int getNumUpgrades(List<String> certs, String shipclass){
		String key = shipclass.toLowerCase() + "_";
		int retval = 0;
		for(String s : certs){
			String certName = stripType(s).toLowerCase();
			if(certName.startsWith(key)){
				retval++;
			}
		}
		return retval;
	}

	private String getTypeOfCertToGive(Certification c, Player p,
			ContractPlayerData d,  UnlockType type) {
		//we know they satisfy the requirements overall
		//first, if they specified a type, let's make sure they have the stuff for it
		if(type == UnlockType.LAWFUL){
			if(d.getBalanceInCurrency("reputation") < c.getRequirementInContractLevels("reputationOrInfamy")){
				//they're stupid
				return null;
			}
			if(d.getBalanceInCurrency("trading") < c.getRequirementInContractLevels("tradingOrSmuggling")){
				//they're stupid
				return null;
			}
			return "lawful";
		}
		if(type == UnlockType.OUTLAW){
			if(d.getBalanceInCurrency("infamy") < c.getRequirementInContractLevels("reputationOrInfamy")){
				//they're stupid
				return null;
			}
			if(d.getBalanceInCurrency("smuggling") < c.getRequirementInContractLevels("tradingOrSmuggling")){
				//they're stupid
				return null;
			}
			return "outlaw";
		}
		//type is neutral, determine which is higher and give them that
		int totalLawful = d.getBalanceInCurrency("trading") + d.getBalanceInCurrency("reputation");
		int totalOutlaw = d.getBalanceInCurrency("smuggling") + d.getBalanceInCurrency("infamy");
		if(totalOutlaw > totalLawful) return "outlaw";
		return "lawful";
	}
	
	private void handleAvailable(Player p, String[] args){
		ArrayList<String> unlocks = database.getCertsOfPlayer(p.getUniqueId());
		ArrayList<Certification> available = getAvailableUnlocks(p, unlocks);
		if(args.length > 1){
			try{
				int i = Integer.parseInt(args[1]);
				displayAvailable(p, available, i);
			} catch (Exception e){
				p.sendMessage("Invalid format, do it like this: /certs available <page number>");
			}
		} else {
			displayAvailable(p, available, 1);
		}
		
	}

	private void displayAvailable(Player p, ArrayList<Certification> unlocks, int page) {
		
		int startPoint = 0 + ((page -1) * 10); //0, 10, 20, 30
		p.sendMessage(ChatColor.GREEN + "Available certifications to unlock (page " + page + "):");
		p.sendMessage(ChatColor.GOLD + "============================================");
		int endPoint = startPoint + 10 > unlocks.size() ? unlocks.size() : startPoint + 10;
		for (int i = startPoint; i < endPoint; i++) {
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
		p.sendMessage(ChatColor.GREEN + "do /certs available " + (page + 1) + " to see the next page");
	}

	private ArrayList<Certification> getAvailableUnlocks(Player p, ArrayList<String> existingCerts) {
		ArrayList<String> existingStripped = new ArrayList<String>();
		for(String s : existingCerts){
			existingStripped.add(stripType(s));
		}
		ArrayList<Certification> retval = new ArrayList<Certification>();
		for (Certification c : certPool.values()) {
			if (!existingStripped.contains(stripType(c.getIdentifier()))) {
				if (c.satisfiesLawfulPreReqs(existingCerts) || c.satisfiesOutlawPreReqs(existingCerts)) {
					if(c.satisfiesUpgradeCountRequirements(existingCerts)){
						retval.add(c);
					}
				}
			}
		}
		Collections.sort(retval);
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
			Certification c = certPool.get(stripType(cert));
			if(c != null){
				c.removeFromPlayer(p);
				playerCerts.remove(cert);
				database.updateCertsOfPlayer(p.getUniqueId(), playerCerts);
				p.sendMessage("Cert removed.");
			} else {
				if(playerCerts.contains(cert)){
					playerCerts.remove(cert);
					database.updateCertsOfPlayer(p.getUniqueId(), playerCerts);
					p.sendMessage("This cert no longer exists, so you were not penalized for its removal. Cert Removed.");
				} else {
					p.sendMessage("Error: cert not found.");
				}
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
				p.sendMessage(num + "): " + stripType(s));
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
			Certification c = certPool.get(stripType(s));
			if(c != null){
				UnlockType type = getLawfulType(s);
				if(type == UnlockType.OUTLAW){
					String tag = c.getOutlawTagFormatted();
					if(tag != null){
						retval.add(c.getOutlawTagFormatted());
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
		Achievement a = event.getAchievement();
		for(String s : bonusTags.keySet()){
			BonusTag t = bonusTags.get(s);
			if(t instanceof AchievementTag){
				AchievementTag tag = (AchievementTag) t;
				Achievement a2 = tag.getAchievement();
				if(a2 == a){
					String id = tag.getIdentifier();
					ArrayList<String> certs = database.getCertsOfPlayer(event.getPlayer().getUniqueId());
					if(!certs.contains(id)){
						certs.add(id);
					}
					database.updateCertsOfPlayer(event.getPlayer().getUniqueId(), certs);
					event.getPlayer().sendMessage("You have unlocked the tag " + tag.getTagFormatted() + ", do /certs tag to set it.");
					return;
				}
			}
		}
	}
	
	public static String stripType(String s){
		if(s.startsWith("outlaw-") || s.startsWith("lawful-")){
			return s.substring(7, s.length());
		} return s;
	}
	
	public UnlockType getLawfulType(String s){
		if(s.startsWith("outlaw-")) return UnlockType.OUTLAW;
		if(s.startsWith("lawful-")) return UnlockType.LAWFUL;
		return UnlockType.NEUTRAL;
	}
	
	public static int getBalanceInCombat(ContractPlayerData d){
		int a = d.getBalanceInCurrency("infamy");
		int b = d.getBalanceInCurrency("reputation");
		if(a > b) return a;
		return b;
	}
	
	public static int getBalanceInTrade(ContractPlayerData d){
		int a = d.getBalanceInCurrency("trading");
		int b = d.getBalanceInCurrency("smuggling");
		if(a > b) return a;
		return b;
	}
	
}
