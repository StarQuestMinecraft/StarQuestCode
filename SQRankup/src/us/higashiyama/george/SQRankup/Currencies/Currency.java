
package us.higashiyama.george.SQRankup.Currencies;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;

import com.regalphoenixmc.SQRankup.RankupPlayer;
import com.regalphoenixmc.SQRankup.TradingNode;

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

	public Location buildLocation() {

		if (node == null) {
			return null;
		} else {
			return new Location(Bukkit.getWorld(node.getWorld()), node.getX(), node.getY(), node.getZ());
		}
	}
}
