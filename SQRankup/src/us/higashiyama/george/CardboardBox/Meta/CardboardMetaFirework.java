
package us.higashiyama.george.CardboardBox.Meta;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.bukkit.FireworkEffect;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.FireworkMeta;
import org.bukkit.inventory.meta.ItemMeta;

import us.higashiyama.george.CardboardBox.Utils.CardboardFireworkEffect;

public class CardboardMetaFirework implements CardboardItemMeta, Serializable {

	private static final long serialVersionUID = -4049866152996023707L;
	private int id;
	private int power;
	private List<CardboardFireworkEffect> effects = new ArrayList<CardboardFireworkEffect>();

	public CardboardMetaFirework(ItemStack firework) {

		this.id = firework.getTypeId();
		FireworkMeta meta = (FireworkMeta) firework.getItemMeta();
		for (FireworkEffect fw : meta.getEffects()) {
			this.effects.add(new CardboardFireworkEffect(fw));
		}

		this.power = meta.getPower();
	}

	public ItemMeta unbox() {

		FireworkMeta meta = (FireworkMeta) new ItemStack(this.id).getItemMeta();
		for (CardboardFireworkEffect fw : effects) {
			meta.addEffect(fw.unbox());
		}

		meta.setPower(this.power);
		return meta;
	}
}
