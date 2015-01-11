
package us.higashiyama.george.CardboardBox.Utils;

import java.io.Serializable;

import org.bukkit.potion.PotionEffectType;

public class CardboardPotionType implements Serializable {

	private static final long serialVersionUID = -8248751036123556417L;
	private int id;

	public CardboardPotionType(PotionEffectType effect) {

		this.id = effect.getId();
	}

	public PotionEffectType unbox() {

		return (PotionEffectType.getById(this.id));
	}
}
