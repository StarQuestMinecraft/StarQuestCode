
package us.higashiyama.george.CardboardBox.Utils;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.bukkit.Color;

public class CardboardColor implements Serializable {

	private static final long serialVersionUID = 842541538023716705L;
	private final int color;

	@SuppressWarnings("deprecation")
	public CardboardColor(Color color) {

		this.color = color.asRGB();
	}

	@SuppressWarnings("deprecation")
	public Color unbox() {

		return Color.fromRGB(this.color);
	}

	public static List<Color> unboxAsList(List<CardboardColor> colors) {

		List<Color> colorList = new ArrayList<Color>();
		for (CardboardColor c : colors) {
			colorList.add(c.unbox());
		}

		return colorList;
	}
}
