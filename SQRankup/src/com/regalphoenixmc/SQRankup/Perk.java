
package com.regalphoenixmc.SQRankup;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

public class Perk extends Currency implements Serializable {

	private static final long serialVersionUID = 1326173868767072823L;
	private String alias;
	private List<String> permissions;
	private List<String> requiredPerms;
	private List<String> commands;
	private List<String> addGroups;
	private List<String> removeGroups;

	public Perk(String alias, List<String> permissions, List<String> ranks, List<String> commands, List<String> addGroups, List<String> removeGroups) {

		this.alias = alias;
		this.permissions = permissions;
		this.requiredPerms = ranks;
		this.commands = commands;
		this.addGroups = addGroups;
		this.removeGroups = removeGroups;
	}

	// RankupPlayer return is so we can conserve SQL calls. null = false
	@Override
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

	@Override
	public void purchase(Player player, RankupPlayer rp, double money, int kills) {

		if (addGroups != null) {
			for (String group : addGroups) {
				SQRankup.permission.playerAddGroup(player, group);
			}
		}

		if (removeGroups != null) {
			for (String group : addGroups) {
				SQRankup.permission.playerRemoveGroup(player, group);
			}
		}

		SQRankup.economy.withdrawPlayer(player, money);

		rp.setAsyncKills(kills * -1);
		rp.saveData();
		for (String perm : this.getPermissions()) {
			SQRankup.permission.playerAdd(null, player, perm);
		}

		SQRankup.dispatchCommands(this.getCommands(), player);

		this.requiredPerms.clear();
		this.commands.clear();

		if (addGroups != null) {
			this.addGroups.clear();
		}
		if (removeGroups != null) {
			this.removeGroups.clear();
		}

		Database.removePerk(player, this);
		Database.addPerk(player, this, true);

		player.sendMessage(ChatColor.GREEN + "Purchase Completed!");
	}

	public void queuePurchase(Player player) {

		Database.addPerk(player, this, false);

	}

	@Override
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

	public List<String> getRequiredPerms() {

		return requiredPerms;
	}

	public void setRequiredPerms(List<String> requiredPerms) {

		this.requiredPerms = requiredPerms;
	}

	public List<String> getAddGroups() {

		return addGroups;
	}

	public void setAddGroups(List<String> addGroups) {

		this.addGroups = addGroups;
	}

	public List<String> getRemoveGroups() {

		return removeGroups;
	}

	public void setRemoveGroups(List<String> removeGroups) {

		this.removeGroups = removeGroups;
	}

}
