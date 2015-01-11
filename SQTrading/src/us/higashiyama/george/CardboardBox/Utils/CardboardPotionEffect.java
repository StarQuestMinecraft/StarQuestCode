
package us.higashiyama.george.CardboardBox.Utils;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.bukkit.potion.PotionEffect;

public class CardboardPotionEffect implements Serializable {

	private static final long serialVersionUID = 2101802611618393055L;
	private final CardboardPotionType effectType;
	private final int amplifier;
	private final int duration;

	public CardboardPotionEffect(PotionEffect effect) {

		this.effectType = new CardboardPotionType(effect.getType());
		this.amplifier = effect.getAmplifier();
		this.duration = effect.getDuration();
	}

	public PotionEffect unbox() {

		return new PotionEffect(this.effectType.unbox(), this.duration, this.amplifier, false);
	}

	public static List<CardboardPotionEffect> boxPotions(Collection<PotionEffect> effects) {

		List<CardboardPotionEffect> returnPotions = new ArrayList<CardboardPotionEffect>();
		for (PotionEffect e : effects) {
			returnPotions.add(new CardboardPotionEffect(e));
		}
		return returnPotions;
	}

	public static List<PotionEffect> unboxPotions(Collection<CardboardPotionEffect> effects) {

		List<PotionEffect> returnPotions = new ArrayList<PotionEffect>();
		for (CardboardPotionEffect e : effects) {
			returnPotions.add(e.unbox());
		}
		return returnPotions;
	}
}
