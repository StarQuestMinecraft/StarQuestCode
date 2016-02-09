package com.starquestminecraft.sqcontracts;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.starquestminecraft.sqcontracts.contracts.Contract;
import com.starquestminecraft.sqcontracts.database.ContractPlayerData;
import com.starquestminecraft.sqcontracts.util.ContractCompletionRunnable;
import com.starquestminecraft.sqcontracts.util.StationUtils;
import com.starquestminecraft.sqcontracts.util.UUIDUtils;
import com.starquestminecraft.sqcontracts.util.WantedUtils;

public class ContractCommand implements CommandExecutor {

	private static List<String> commandArgs = Arrays.asList(new String[] { "new", "list", "complete", "wanted", "stats", "remove","delete", "available"});

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String name, String[] args) {
		System.out.print(0);
		if (args.length < 1)
			return false;
		if (args[0].equals("admin")) {
			if (sender.hasPermission("contracts.admin")) {
				if (args.length < 3) {
					sender.sendMessage("must specify a player and a subcommand (/contract admin dibujaron list");
					return true;
				}
				String pname = args[1];
				final Player p = Bukkit.getPlayer(pname);
				if (p == null) {
					sender.sendMessage("Player not found.");
					return true;
				}

				final String sub = args[2];
				final CommandSender sndr = sender;
				final String[] cmdargs = genCmdArgs(args);
				Bukkit.getServer().getScheduler().runTaskAsynchronously(SQContracts.get(), new Runnable() {
					public void run() {
						switch (sub) {
						case "new":
							handleNewCommand(p, sndr, cmdargs);
							return;
						case "list":
							displayContractList(p, sndr);
							return;
						case "available":
							handleAvailableCommand(p,sndr, cmdargs);
							return;
						case "complete":
							completeContract(p, cmdargs);
							return;
						case "delete":
							deleteContract(p, sndr, cmdargs);
							return;
						case "setLevel":
							setContractLevel(p, sndr, cmdargs);
							return;
						case "convert":
							ContractConverter.convert();
						}
					}

				});
				return true;
			}
		}
		final Player plr = (Player) sender;
		boolean valid = false;
		if (commandArgs.contains(args[0]) && sender instanceof Player) {
			System.out.print(1);
			//final Player plr = (Player) sender;
			final String[] fnlargs = args;
			valid = true;
			Bukkit.getServer().getScheduler().runTaskAsynchronously(SQContracts.get(), new Runnable() {
				public void run() {
					switch (fnlargs[0]) {
					case "new":
						System.out.print(2);
						handleNewCommand(plr, plr, fnlargs);
						return;
					case "available":
						System.out.print(3);
						handleAvailableCommand(plr, plr, fnlargs);
						return;
					case "list":
						System.out.print(4);
						displayContractList(plr, plr);
						return;
					case "complete":
						System.out.print(5);
						completeContract(plr, fnlargs);
						return;
					case "wanted":
						System.out.print(6);
						displayWantedList(plr, fnlargs);
						return;
					case "stats":
						System.out.print(7);
						displayStatsList(plr, fnlargs);
						return;
					case "remove":
						System.out.print(8);
					case "delete":
						System.out.print(9);
						removeContract(plr, fnlargs);
						return;
					}
				}
			});
			return true;
		} else {
			plr.sendMessage(ChatColor.RED + "Command not recognized. To see available contracts, do: " + ChatColor.BLUE + "/Contract available ${type}");
			plr.sendMessage(ChatColor.RED + "To add a new contract, do: " + ChatColor.BLUE + "/Contract new ${type} ${number}");
		}
		if (!valid) {
			plr.sendMessage(ChatColor.RED + "To see available contracts, do: " + ChatColor.BLUE + "/contract available <type>");
			plr.sendMessage(ChatColor.RED + "To accept a new contract, do: " + ChatColor.BLUE + "/contract new <type> <#>");
			plr.sendMessage(ChatColor.RED + "To list your current contracts, do: " + ChatColor.BLUE + "/contract list");
			plr.sendMessage(ChatColor.RED + "To remove a contract, do: " + ChatColor.BLUE + "/contract remove <#>");
			plr.sendMessage(ChatColor.RED + "Be careful! Removing a contract after 1 hour costs half of it's reward!");
			plr.sendMessage(ChatColor.RED + "To list wanted and privateer players, do: " + ChatColor.BLUE + "/contract wanted");
		}
		return false;
	}

	private String[] genCmdArgs(String[] args) {
		String[] retval = new String[args.length - 2];
		for (int i = 2; i < args.length; i++) {
			retval[i - 2] = args[i];
		}
		return retval;
	}

	private void setContractLevel(Player p, CommandSender sndr, String[] cmdargs) {
		String currency = cmdargs[1];
		try {
			int level = Integer.parseInt(cmdargs[2]);
			ContractPlayerData d = SQContracts.get().getContractDatabase().getDataOfPlayer(p.getUniqueId());
			if (ContractPlayerData.getCurrencies().contains(currency)) {
				d.setBalanceInCurrency(currency, level);
				SQContracts.get().getContractDatabase().updatePlayerData(p.getUniqueId(), d);
				sndr.sendMessage("Done succesfully.");
			} else {
				sndr.sendMessage("Unknown currency.");
			}
		} catch (Exception e) {
			e.printStackTrace();
			sndr.sendMessage("Command failed (did you type the level in right?)");
		}
	}

	private void displayWantedList(Player plr, String[] fnlargs) {
		if (fnlargs.length == 1) {
			List<Player> plrs = plr.getWorld().getPlayers();
			ArrayList<String> wantedMessages = new ArrayList<String>();
			ArrayList<String> privateerMessages = new ArrayList<String>();
			for (Player p : plrs) {
				ContractPlayerData data = SQContracts.get().getContractDatabase().getDataOfPlayer(p.getUniqueId());
				if (data.isWanted()) {
					wantedMessages.add(ChatColor.RED + p.getName());
					if (p != plr)
						p.sendMessage(ChatColor.RED + plr.getName() + " saw your name on the wanted list!");
				}
				if(data.isPrivateer()){
					privateerMessages.add(ChatColor.BLUE + p.getName());
					if (p != plr)
						p.sendMessage(ChatColor.BLUE + plr.getName() + " saw your name on the privateer list!");
				}
			}
			plr.sendMessage(ChatColor.RED + "Wanted players online in your world: ");
			for(String s : wantedMessages) plr.sendMessage(s);
			plr.sendMessage(ChatColor.BLUE + "Privateer players online in your world:");
			for(String s : privateerMessages) plr.sendMessage(s);
		} else {
			UUID u = UUIDUtils.uuidFromUsername(fnlargs[0]);
			if(u == null) plr.sendMessage("This player was not found, did you type their name right?");
			ContractPlayerData data = SQContracts.get().getContractDatabase().getDataOfPlayer(u);
			if(data.isWanted()){
				plr.sendMessage("This player is wanted.");
			} else {
				plr.sendMessage("This player is not wanted.");
			}
			if(data.isPrivateer()){
					plr.sendMessage("This player is a privateer.");
			} else {
				plr.sendMessage("This player is not a privateer.");
			}
		}
	}

	private void displayStatsList(Player plr, String[] fnlargs) {
		Player relevant;
		if (fnlargs.length > 2) {
			String name = fnlargs[1];
			relevant = Bukkit.getPlayer(name);
			if (relevant == null) {
				plr.sendMessage("This player is not online.");
				return;
			}
		} else {
			relevant = plr;
		}

		ContractPlayerData data = SQContracts.get().getContractDatabase().getDataOfPlayer(relevant.getUniqueId());
		plr.sendMessage("Contract stats for " + relevant.getName());
		for (String s : ContractPlayerData.getCurrencies()) {
			plr.sendMessage(ChatColor.RED + s + ": " + data.getBalanceInCurrency(s));
		}
	}
	
	private void removeContract(Player plr, String[] args) {
		System.out.println("removing contract.");
		int i;
		try {
			i = Integer.parseInt(args[1]);
		} catch (Exception e) {
			plr.sendMessage("You must specify a contract number to remove.");
			return;
		}
		ContractPlayerData pdat = SQContracts.get().getContractDatabase().getDataOfPlayer(plr.getUniqueId());
		Contract c = pdat.getContracts().get(i);
		if(System.currentTimeMillis() - c.getOpenedMillis() < 1000 * 60 * 60){
			pdat.getContracts().remove(c);
			SQContracts.get().getContractDatabase().updatePlayerData(plr.getUniqueId(), pdat);
			plr.sendMessage("This contract has been removed for free (< 1 hr).");
		} else {
			if(c.canAffordCancellation(plr)){
				c.penalizeForCancellation(plr);
				pdat.getContracts().remove(c);
				SQContracts.get().getContractDatabase().updatePlayerData(plr.getUniqueId(), pdat);
				plr.sendMessage("You have been charged for your failure to complete this contract.");
				if(c.isBlackMarket()){
					WantedUtils.addDelayedWanted(plr.getUniqueId());
					plr.sendMessage("Your wanted status for this contract will remain for the next hour.");
				}
			} else {
				plr.sendMessage("You cannot afford the penalty for cancelling this contract (1/2 the reward). If you"
						+ " feel you must cancel it, save up some cash and remove it once you can afford it.");
			}
		}
	}

	private void completeContract(Player plr, String[] args) {
		System.out.println("Completing contract.");
		int i;
		try {
			i = Integer.parseInt(args[1]);
		} catch (Exception e) {
			plr.sendMessage("You must specify a contract number to complete.");
			return;
		}
		String station = StationUtils.getStationAtLocation(plr.getLocation());
		if (station == null) {
			plr.sendMessage("You can only complete a contract at its target economic station.");
			return;
		}
		ContractPlayerData pdat = SQContracts.get().getContractDatabase().getDataOfPlayer(plr.getUniqueId());
		Contract c = pdat.getContracts().get(i);
		if (!c.getTargetStation().equals(station)) {
			plr.sendMessage("You can only complete a contract at its target economic station.");
			return;
		}
		plr.sendMessage("Completing contract!");
		ContractCompletionRunnable.addToCompletionQueue(c, pdat);
	}

	private void deleteContract(Player plr, CommandSender sndr, String[] cmdargs) {
		ContractPlayerData pdat = SQContracts.get().getContractDatabase().getDataOfPlayer(plr.getUniqueId());
		int index = Integer.parseInt(cmdargs[1]);
		pdat.getContracts().remove(index);
		SQContracts.get().getContractDatabase().updatePlayerData(plr.getUniqueId(), pdat);
		sndr.sendMessage("Deleted.");
	}

	private void displayContractList(Player plr, CommandSender notify) {
		ContractPlayerData data = SQContracts.get().getContractDatabase().getDataOfPlayer(plr.getUniqueId());
		List<Contract> open = data.getContracts();

		notify.sendMessage(ChatColor.GREEN + "Currently open contracts:");
		notify.sendMessage(ChatColor.GOLD + "============================================");
		for (int i = 0; i < open.size(); i++) {
			Contract c = open.get(i);
			ChatColor color;
			if (i % 2 == 0) {
				color = ChatColor.YELLOW;
			} else {
				color = ChatColor.LIGHT_PURPLE;
			}
			notify.sendMessage(color + "" + i + "): " + c.getDescription(color));
		}
		notify.sendMessage(ChatColor.GOLD + "============================================");
	}

	private void handleNewCommand(Player plr, CommandSender notify, String[] args) {
		if (args.length < 3) {
			notify.sendMessage("To see your list of available contracts, type /contract available <type>.");
			notify.sendMessage("To accept one, type /contract new <type> #, where # is the number of the contract from the available list.");
		} else {
			if(plr.getWorld().getName().toLowerCase().startsWith("trinitos_")){
				plr.sendMessage("You can only accept new contracts on a planet.");
				return;
			}
			try {
				String type = args[1];
				int id = Integer.parseInt(args[2]);
				givePlayerNewContract(plr, notify, type, id);
			} catch (Exception e) {
				notify.sendMessage("To see your list of available contracts, type /contract available <type>.");
				notify.sendMessage("To accept one, type /contract new <type> #, where # is the number of the contract from the available list.");
			}
		}
	}
	
	private void handleAvailableCommand(Player plr, CommandSender notify, String[] args){
		System.out.println("Available handler!");
		if(args.length < 2){
			notify.sendMessage("To see your available contracts, type /contract available <type>");
			notify.sendMessage("Available types are: merchanting, smuggling, privateering, piracy, and philanthropy.");
			return;
		}
		String type = args[1];
		displayNewListForType(plr, notify, type);
	}

	private void givePlayerNewContract(Player plr, CommandSender notify, String type, int id) {
		type = type.toLowerCase();
		String currency = getCurrencyFromType(type);
		if(!ContractPlayerData.getCurrencies().contains(currency)){
			notify.sendMessage("Unknown type, available types are smuggling, merchanting, piracy, privateering, and philanthropy:");
			return;
		}
		Contract[] available = SQContracts.get().getRandomizer().generateContractsForPlayer(plr.getUniqueId(), currency);
		Contract c = available[id];
		ContractPlayerData d = SQContracts.get().getContractDatabase().getDataOfPlayer(plr.getUniqueId());
		if (d.getContracts().size() < 5) {
			d.getContracts().add(c);
			SQContracts.get().getContractDatabase().updatePlayerData(plr.getUniqueId(), d);
			plr.sendMessage("New contract acquired! Do /contract list to see your open contracts.");
			plr.sendMessage("You have up to " + ChatColor.RED + " one hour " + ChatColor.WHITE + "to remove this contract if you don't want to complete it.");
			plr.sendMessage("After that, the contract is binding and you must complete it!");
			plr.sendMessage("Removing a contract after the hour is up will cost you " + ChatColor.RED + " half " + ChatColor.WHITE + "of its rewards!");
			notify.sendMessage("New contract given!");
		} else {
			plr.sendMessage("You can only have 5 contracts open at once!");
		}
	}

	private void displayNewListForType(Player plr, CommandSender notify, String type) {
		System.out.println("Display new list!");
		type = type.toLowerCase();
		String currency = getCurrencyFromType(type);
		if(!ContractPlayerData.getCurrencies().contains(currency)){
			notify.sendMessage("Unknown type, available types are smuggling, merchanting, piracy, privateering, and philanthropy:");
			return;
		}
		Contract[] available = SQContracts.get().getRandomizer().generateContractsForPlayer(plr.getUniqueId(), currency);

		notify.sendMessage(ChatColor.GREEN + "New contracts available for " + plr.getName() + " of type " + type);
		notify.sendMessage(ChatColor.GOLD + "============================================");
		for (int i = 0; i < available.length; i++) {
			Contract c = available[i];
			ChatColor color;
			if (i % 2 == 0) {
				color = ChatColor.YELLOW;
			} else {
				color = ChatColor.LIGHT_PURPLE;
			}
			String desc = c.getDescription(color);
			notify.sendMessage(color + "" + i + "): " + desc);
		}
		notify.sendMessage(ChatColor.GOLD + "============================================");

	}
	
	private String getCurrencyFromType(String type){
		switch(type){
		case "piracy": return "infamy";
		case "privateering": return "reputation";
		case "merchanting": return "trading";
		default: return type;
		}
	}

}
