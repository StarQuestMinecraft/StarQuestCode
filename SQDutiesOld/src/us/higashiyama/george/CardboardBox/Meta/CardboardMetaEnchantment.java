
package us.higashiyama.george.CardboardBox.Meta;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFactory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.EnchantmentStorageMeta;
import org.bukkit.inventory.meta.ItemMeta;

import us.higashiyama.george.CardboardBox.Utils.CardboardEnchantment;

/*
 * This class deals with items that hold enchantment data as storage. This applies
 * to enchanting books, but not items with enchantments.
 */

public class CardboardMetaEnchantment implements CardboardItemMeta, Serializable {

	private static final long serialVersionUID = 5343923327369616762L;
	private int id;
	private Map<CardboardEnchantment, Integer> enchantments = new HashMap<CardboardEnchantment, Integer>();

	@SuppressWarnings("deprecation")
	public CardboardMetaEnchantment(ItemStack item) {

		this.id = item.getTypeId();
		EnchantmentStorageMeta meta = (EnchantmentStorageMeta) item.getItemMeta();
		for (Enchantment e : meta.getStoredEnchants().keySet()) {
			this.enchantments.put(new CardboardEnchantment(e), meta.getStoredEnchants().get(e));
		}

	}

	@SuppressWarnings("deprecation")
	public ItemMeta unbox() {

		ItemFactory factory = Bukkit.getServer().getItemFactory();
		EnchantmentStorageMeta meta = (EnchantmentStorageMeta) factory.getItemMeta(Material.getMaterial(this.id));

		for (CardboardEnchantment e : this.enchantments.keySet()) {
			meta.addStoredEnchant(e.unbox(), this.enchantments.get(e), true);
		}
		return meta;
	}
}
