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
import com.starquestminecraft.sqcontracts.util.ContractCompletionRunnable;
import com.starquestminecraft.sqcontracts.util.ContractRandomizer;
import com.starquestminecraft.sqcontracts.util.StationUtils;

public class ContractCommand implements CommandExecutor {

	private static List<String> commandArgs = Arrays.asList(new String[] { "new", "list", "complete", "wanted" });

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String name, String[] args) {
		if (commandArgs.contains(args[0]) && sender instanceof Player) {
			final Player plr = (Player) sender;
			final String[] fnlargs = args;
			Bukkit.getServer().getScheduler().runTaskAsynchronously(SQContracts.get(), new Runnable(){
				public void run(){
					switch (fnlargs[0]) {
					case "new":
						handleNewCommand(plr, fnlargs);
					case "list":
						displayContractList(plr);
					case "complete":
						completeContract(plr, fnlargs);
					case "wanted":
						displayWantedList(plr);
					}
				}
			});
			return true;
		}
		return false;
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

	private void completeContract(Player plr, String[] args) {
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
		
		ContractCompletionRunnable.addToCompletionQueue(c, pdat);
	}

	private void displayContractList(Player plr) {
		ContractPlayerData data = SQContracts.get().getContractDatabase().getDataOfPlayer(plr.getUniqueId());
		List<Contract> open = data.getContracts();

		plr.sendMessage(ChatColor.GREEN + "Your currently open contracts:");
		plr.sendMessage(ChatColor.GOLD + "============================================");
		for (int i = 0; i < open.size(); i++) {
			Contract c = open.get(i);
			ChatColor color;
			if (i % 2 == 0) {
				color = ChatColor.YELLOW;
			} else {
				color = ChatColor.LIGHT_PURPLE;
			}
			plr.sendMessage(color + "" + i + "): " + c.getDescription());
		}
		plr.sendMessage(ChatColor.GOLD + "============================================");
	}

	private void handleNewCommand(Player plr, String[] args) {
		if (args[1] == null || args[1] == "") {
			displayNewList(plr);
		} else {
			try {
				int id = Integer.parseInt(args[1]);
				givePlayerNewContract(plr, id);
			} catch (Exception e) {
				displayNewList(plr);
			}
		}
	}

	private void givePlayerNewContract(Player plr, int id) {
		Contract[] available = ContractRandomizer.generateContractsForPlayer(plr.getUniqueId());
		Contract c = available[id];
		ContractPlayerData d = SQContracts.get().getContractDatabase().getDataOfPlayer(plr.getUniqueId());
		d.getContracts().add(c);
		SQContracts.get().getContractDatabase().updatePlayerData(plr.getUniqueId(), d);
		plr.sendMessage("New contract acquired! Do /contract list to see your open contracts.");
	}

	private void displayNewList(Player plr) {
		Contract[] available = ContractRandomizer.generateContractsForPlayer(plr.getUniqueId());

		plr.sendMessage(ChatColor.GREEN + "New contracts available for " + plr.getName());
		plr.sendMessage(ChatColor.GOLD + "============================================");
		for (int i = 0; i < available.length; i++) {
			Contract c = available[i];
			ChatColor color;
			if (i % 2 == 0) {
				color = ChatColor.YELLOW;
			} else {
				color = ChatColor.LIGHT_PURPLE;
			}
			plr.sendMessage(color + "" + i + "): " + c.getDescription());
		}
		plr.sendMessage(ChatColor.GOLD + "============================================");

	}

}
