
package us.higashiyama.george.Currencies;

import org.bukkit.entity.Player;

public interface Currency {

	public String getAlias();

	public boolean hasCurrency(Player player);

	public void removeCurrency(Player p);

	public void giveCurrency(Player player);

}
