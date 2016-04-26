
package us.higashiyama.george.CardboardBox.Utils;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.bukkit.Color;
import org.bukkit.FireworkEffect;

public class CardboardFireworkEffect implements Serializable {

	private static final long serialVersionUID = -6362289252237171646L;

	private List<CardboardColor> colors = new ArrayList<CardboardColor>();
	private List<CardboardColor> fade = new ArrayList<CardboardColor>();
	private boolean flicker;
	private boolean trail;

	@SuppressWarnings("deprecation")
	public CardboardFireworkEffect(FireworkEffect effect) { // TODO: PASS NULL

		// EFFECT

		if (effect != null) {
			for (Color c : effect.getColors()) {
				this.colors.add(new CardboardColor(c));
			}
			for (Color c : effect.getFadeColors()) {
				this.fade.add(new CardboardColor(c));
			}
			this.flicker = effect.hasFlicker();
			this.trail = effect.hasTrail();
		}
	}

	@SuppressWarnings("deprecation")
	public FireworkEffect unbox() {

		FireworkEffect effect = FireworkEffect.builder().withColor(CardboardColor.unboxAsList(this.colors)).flicker(this.flicker).trail(this.trail)
				.withFade(CardboardColor.unboxAsList(this.fade)).build();
		return effect;
	}
}
