
package com.regalphoenixmc.SQRankup;

import org.bukkit.entity.Player;

public abstract class Currency {

	private TradingNode node = null;

	public String getAlias() {

		return "";
	}

	public RankupPlayer canPurchase(Player player, double money, int kills, Perk perk) {

		return null;
	}

	public void purchase(Player player, RankupPlayer rp, double money, int kills) {

	}

	public TradingNode getNode() {

		return node;
	}

	public void setNode(TradingNode node) {

		this.node = node;
	}
}
