
package us.higashiyama.george.CardboardBox.Meta;

import java.io.Serializable;

import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.FireworkEffectMeta;
import org.bukkit.inventory.meta.ItemMeta;

import us.higashiyama.george.CardboardBox.Utils.CardboardFireworkEffect;

public class CardboardMetaFireworkEffect implements CardboardItemMeta, Serializable {

	private static final long serialVersionUID = -738623056624942344L;
	private int id;
	private CardboardFireworkEffect effect;

	@SuppressWarnings("deprecation")
	public CardboardMetaFireworkEffect(ItemStack firework) {

		this.id = firework.getTypeId();
		FireworkEffectMeta meta = (FireworkEffectMeta) firework.getItemMeta();
		this.effect = new CardboardFireworkEffect(meta.getEffect());
	}

	@SuppressWarnings("deprecation")
	public ItemMeta unbox() {

		FireworkEffectMeta meta = (FireworkEffectMeta) new ItemStack(this.id).getItemMeta();
		meta.setEffect(this.effect.unbox());
		return meta;
	}
}
