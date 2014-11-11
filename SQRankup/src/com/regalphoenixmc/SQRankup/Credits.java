
package com.regalphoenixmc.SQRankup;

import java.io.Serializable;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

public class Credits extends Currency implements Serializable {

	private double credits;

	private static final long serialVersionUID = 3122520575811516789L;

	public Credits(double c) {

		this.setCredits(c);
	}

	@Override
	public String getAlias() {

		return credits + " credits";
	}

	@Override
	public RankupPlayer canPurchase(Player player, double money, int kills, Perk perk) {

		RankupPlayer rp = Database.getEntry(player.getName());
		if (rp == null) {
			rp = new RankupPlayer(player.getName(), 0, "", 0);
		}

		return rp;
	}

	@Override
	public void purchase(Player player, RankupPlayer rp, double money, int kills) {

		SQRankup.economy.depositPlayer(player, this.credits);
		player.sendMessage(ChatColor.AQUA + "You recieved " + this.credits + " credits!");
	}

	public double getCredits() {

		return credits;
	}

	public void setCredits(double credits) {

		this.credits = credits;
	}

}
