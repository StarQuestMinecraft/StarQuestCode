
package us.higashiyama.george.Currencies;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import us.higashiyama.george.SQTrading.Database;
import us.higashiyama.george.SQTrading.SQTrading;

public class Perk implements Serializable, Currency {

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

	@Override
	public boolean hasCurrency(Player player) {

		List<Perk> perks = Database.getUnredeemedPlayerPerks(player);
		for (Perk p : perks) {
			if (perkEquals(p, this)) {
				return true;
			}
		}
		return false;

	}

	@Override
	public void removeCurrency(Player p) {

		Database.removePerk(p, this);

	}

	@Override
	public void giveCurrency(Player player) {

		Database.addPerk(player, this, false);

	}

	public void redeem(Player player) {

		if (addGroups != null) {
			for (String group : addGroups) {
				SQTrading.permission.playerAddGroup(player, group);
			}
		}

		if (removeGroups != null) {
			for (String group : addGroups) {
				SQTrading.permission.playerRemoveGroup(player, group);
			}
		}

		for (String perm : this.getPermissions()) {
			SQTrading.permission.playerAdd(null, player, perm);
		}

		SQTrading.dispatchCommands(this.getCommands(), player);

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

	private static boolean perkEquals(Perk p1, Perk p2) {

		if (!p1.alias.equals(p2.alias)) {
			return false;
		}

		if (!p1.permissions.equals(p2.permissions)) {
			return false;
		}

		if (!p1.requiredPerms.equals(p2.requiredPerms)) {
			return false;
		}

		if (!p1.commands.equals(p2.commands)) {
			return false;
		}

		if (!p1.addGroups.equals(p2.addGroups)) {
			return false;
		}

		if (!p1.removeGroups.equals(p2.removeGroups)) {
			return false;
		}

		return true;
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
