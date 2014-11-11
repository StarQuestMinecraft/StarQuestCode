
package com.regalphoenixmc.SQRankup;

import org.bukkit.entity.Player;

public abstract class Currency {

	// override
	public String getAlias() {

		return "";
	}

	// Also needs override
	public RankupPlayer canPurchase(Player player, double money, int kills, Perk perk) {

		return null;
	}

	// make sure to override for extending classes
	public void purchase(Player player, RankupPlayer rp, double money, int kills) {

	}
}
