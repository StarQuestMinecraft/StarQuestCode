
package us.higashiyama.george.CardboardBox.Utils;

import java.io.Serializable;

import org.bukkit.enchantments.Enchantment;

/**
 * A serializable Enchantment
 */
public class CardboardEnchantment implements Serializable {

	private static final long serialVersionUID = 8973856768102665381L;

	private final int id;

	public CardboardEnchantment(Enchantment enchantment) {

		this.id = enchantment.getId();
	}

	public Enchantment unbox() {

		return Enchantment.getById(this.id);
	}

	@Override
	public String toString() {

		return Enchantment.getById(this.id).toString();
	}
}