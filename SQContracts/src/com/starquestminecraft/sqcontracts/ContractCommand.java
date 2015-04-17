package com.starquestminecraft.sqcontracts;

import java.util.Arrays;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.starquestminecraft.sqcontracts.contracts.Contract;
import com.starquestminecraft.sqcontracts.database.ContractPlayerData;
import com.starquestminecraft.sqcontracts.randomizer.config.ConfigRandomizer;
import com.starquestminecraft.sqcontracts.util.ContractCompletionRunnable;
import com.starquestminecraft.sqcontracts.util.StationUtils;

public class ContractCommand implements CommandExecutor {

	private static List<String> commandArgs = Arrays.asList(new String[] { "new", "list", "complete", "wanted","stats" });

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String name, String[] args) {
		if(args.length < 1) return false;
		if(args[0].equals("admin")){
			if(sender.hasPermission("contracts.admin")){
				if(args.length < 3){
					sender.sendMessage("must specify a player and a subcommand (/contract admin dibujaron list");
					return true;
				}
				String pname = args[1];
				final Player p = Bukkit.getPlayer(pname);
				if(p == null){
					sender.sendMessage("Player not found.");
					return true;
				}
				
				final String sub = args[2];
				final CommandSender sndr = sender;
				final String[] cmdargs = genCmdArgs(args);
				Bukkit.getServer().getScheduler().runTaskAsynchronously(SQContracts.get(), new Runnable(){
					public void run(){
						switch (sub) {
						case "new":
							handleNewCommand(p, sndr, cmdargs);
							return;
						case "list":
							displayContractList(p, sndr);
							return;
						case "complete":
							completeContract(p, cmdargs);
							return;
						case "delete":
							deleteContract(p, sndr, cmdargs);
							return;
						}
					}
				});
				return true;
			}
		}
		if (commandArgs.contains(args[0]) && sender instanceof Player) {
			final Player plr = (Player) sender;
			final String[] fnlargs = args;
			Bukkit.getServer().getScheduler().runTaskAsynchronously(SQContracts.get(), new Runnable(){
				public void run(){
					switch (fnlargs[0]) {
					case "new":
						handleNewCommand(plr, plr, fnlargs);
						return;
					case "list":
						displayContractList(plr, plr);
						return;
					case "complete":
						completeContract(plr, fnlargs);
						return;
					case "wanted":
						displayWantedList(plr);
						return;
					case "stats":
						displayStatsList(plr, fnlargs);
						return;
					}
				}
			});
			return true;
		}
		return false;
	}
	
	private String[] genCmdArgs(String[] args){
		String[] retval = new String[args.length - 2];
		for(int i = 2; i < args.length; i++){
			retval[i - 2] = args[i];
		}
		return retval;
	}

	private void displayWantedList(Player plr) {
		List<Player> plrs = plr.getWorld().getPlayers();
		
		plr.sendMessage(ChatColor.RED + "Wanted players online in your world: ");
		for(Player p : plrs){
			ContractPlayerData data = SQContracts.get().getContractDatabase().getDataOfPlayer(p.getUniqueId());
			if(data.isWanted()){
				plr.sendMessage(ChatColor.RED + p.getName());
				if(p != plr) p.sendMessage(ChatColor.RED + plr.getName() + " saw your name on the wanted list!");
			}
		}
	}
	
	private void displayStatsList(Player plr, String[] fnlargs) {
		Player relevant;
		if(fnlargs.length > 2){
			String name = fnlargs[1];
			relevant = Bukkit.getPlayer(name);
			if(relevant == null){
				plr.sendMessage("This player is not online.");
				return;
			}
		} else {
			relevant = plr;
		}
		
		ContractPlayerData data = SQContracts.get().getContractDatabase().getDataOfPlayer(relevant.getUniqueId());
		plr.sendMessage("Contract stats for " + relevant.getName());
		for(String s : data.getCurrencies()){
			plr.sendMessage(ChatColor.RED + s + ": " + data.getBalanceInCurrency(s));
		}
	}

	private void completeContract(Player plr, String[] args) {
		System.out.println("Completing contract.");
		int i;
		try{
			i = Integer.parseInt(args[1]);
		} catch(Exception e){
			plr.sendMessage("You must specify a contract number to complete.");
			return;
		}
		String station = StationUtils.getStationAtLocation(plr.getLocation());
		if(station == null){
			plr.sendMessage("You can only complete a contract at its target economic station.");
			return;
		}
		ContractPlayerData pdat = SQContracts.get().getContractDatabase().getDataOfPlayer(plr.getUniqueId());
		Contract c = pdat.getContracts().get(i);
		if(!c.getTargetStation().equals(station)){
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
		if (args.length < 2 || args[1] == null || args[1] == "") {
			displayNewList(plr, notify);
		} else {
			try {
				int id = Integer.parseInt(args[1]);
				givePlayerNewContract(plr, notify, id);
			} catch (Exception e) {
				displayNewList(plr, notify);
			}
		}
	}

	private void givePlayerNewContract(Player plr, CommandSender notify, int id) {
		Contract[] available = SQContracts.get().getRandomizer().generateContractsForPlayer(plr.getUniqueId());
		Contract c = available[id];
		ContractPlayerData d = SQContracts.get().getContractDatabase().getDataOfPlayer(plr.getUniqueId());
		d.getContracts().add(c);
		SQContracts.get().getContractDatabase().updatePlayerData(plr.getUniqueId(), d);
		plr.sendMessage("New contract acquired! Do /contract list to see your open contracts.");
		notify.sendMessage("New contract given!");
	}

	private void displayNewList(Player plr, CommandSender notify) {
		Contract[] available = SQContracts.get().getRandomizer().generateContractsForPlayer(plr.getUniqueId());

		notify.sendMessage(ChatColor.GREEN + "New contracts available for " + plr.getName());
		notify.sendMessage(ChatColor.GOLD + "============================================");
		for (int i = 0; i < available.length; i++) {
			Contract c = available[i];
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

}
