
package us.higashiyama.george.CardboardBox.Meta;

import java.io.Serializable;

import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.MapMeta;

public class CardboardMetaMap implements CardboardItemMeta, Serializable {

	private static final long serialVersionUID = -5808013506987424914L;
	private int id;
	private boolean scaling;

	@SuppressWarnings("deprecation")
	public CardboardMetaMap(ItemStack map) {

		this.id = map.getTypeId();
		MapMeta meta = (MapMeta) map.getItemMeta();
		if (meta.isScaling()) {
			this.scaling = true;
		} else {
			this.scaling = false;
		}
	}

	@SuppressWarnings("deprecation")
	public ItemMeta unbox() {

		MapMeta meta = (MapMeta) new ItemStack(this.id).getItemMeta();
		if (this.scaling) {
			meta.setScaling(true);
		}

		return meta;
	}
}
