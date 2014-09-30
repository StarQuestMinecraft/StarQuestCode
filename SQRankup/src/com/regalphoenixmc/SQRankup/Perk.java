
package com.regalphoenixmc.SQRankup;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

public class Perk {

	private String alias;
	private List<String> permissions;
	private List<String> requiredPerms;

	public List<String> getRequiredPerms() {

		return requiredPerms;
	}

	public void setRequiredPerms(List<String> requiredPerms) {

		this.requiredPerms = requiredPerms;
	}

	private List<String> commands;

	public Perk(String alias, List<String> permissions, List<String> ranks, List<String> commands) {

		this.alias = alias;
		this.permissions = permissions;
		this.requiredPerms = ranks;
		this.commands = commands;
	}

	@Override
	public String toString() {

		return "Perk [alias=" + alias + ", permissions=" + permissions + ", requiredPerms=" + requiredPerms + ", commands=" + commands + "]";
	}

	// RankupPlayer return is so we can conserve SQL calls. null = false
	public RankupPlayer canPurchase(Player player, double money, int kills, Perk perk) {

		for (String perm : perk.getRequiredPerms()) {
			if (!(SQRankup.permission.has(player, perm))) {
				player.sendMessage(ChatColor.RED + "You are missing one or more required prerequisite(s).");
				return null;
			}
		}
		RankupPlayer rp = Database.getEntry(player.getName());
		if (rp == null) {
			rp = new RankupPlayer(player.getName(), 0, "", 0);
		}
		if (!(rp.getKills() >= kills)) {
			int differential = kills - rp.getKills();
			player.sendMessage(ChatColor.RED + "Not enough kills. You need " + differential + " more kills.");
			return null;
		}

		double bal = SQRankup.economy.getBalance(player);
		if (!(bal >= money)) {
			double differential = money - bal;
			player.sendMessage(ChatColor.RED + "Not enough credits. You need " + differential + " more credits.");
			return null;
		}

		return rp;
	}

	public void purchase(Player player, RankupPlayer rp, double money, int kills, Perk perk) {

		SQRankup.economy.withdrawPlayer(player, money);

		rp.setAsyncKills(kills * -1);
		rp.saveData();
		for (String perm : perk.getPermissions()) {
			SQRankup.permission.playerAdd(player, perm);
		}

		SQRankup.dispatchCommands(perk.getCommands());

		player.sendMessage(ChatColor.GREEN + "Purchase Completed!");
	}

	public String getAlias() {

		return alias;
	}

	public void setAlias(String alias) {

		this.alias = alias;
	}

	public List<String> getPermissions() {

		return permissions;
	}

	public void setPermissions(ArrayList<String> permissions) {

		this.permissions = permissions;
	}

	public List<String> getCommands() {

		return commands;
	}

	public void setCommands(List<String> commands) {

		this.commands = commands;
	}

}
