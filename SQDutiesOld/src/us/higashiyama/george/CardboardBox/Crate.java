
package us.higashiyama.george.CardboardBox;

/*
 * This class is a Crate that can hold as many cardboard boxes as needed.
 * This crate can be serialized for easy inventory manipulation.
 * 
 */
import java.io.Serializable;
import java.util.ArrayList;

import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public class Crate implements Serializable {

	private static final long serialVersionUID = 3171408210789986887L;
	public ArrayList<CardboardBox> storage = new ArrayList<CardboardBox>();

	public Crate(Iterable<CardboardBox> itr) {

		for (CardboardBox cb : itr) {
			this.storage.add(cb);
		}
	}

	public Crate(ItemStack[] i) {

		for (ItemStack is : i) {
			if (is == null) {
				storage.add(new CardboardBox(new ItemStack(0)));
			} else {
				storage.add(new CardboardBox(is));
			}
		}
	}

	public Crate(Inventory i) {

		for (ItemStack is : i) {
			if (is == null) {
				storage.add(new CardboardBox(new ItemStack(0)));
			} else {

				storage.add(new CardboardBox(is));
			}
		}
	}

	public void unpack(Inventory i) {

		ArrayList<ItemStack> unpacked = this.unpackBoxes();

		for (int x = 0; x < i.getSize() - 1; x++) {
			i.setItem(x, unpacked.get(x));
		}

	}

	public ItemStack[] unpackItemArray() {

		ItemStack[] unpacked = new ItemStack[this.storage.size()];

		for (int x = 0; x < unpacked.length; x++) {
			unpacked[x] = this.storage.get(x).unbox();
		}

		return unpacked;

	}

	public ArrayList<CardboardBox> getBoxes() {

		return this.storage;
	}

	public ArrayList<ItemStack> unpackBoxes() {

		ArrayList<ItemStack> unpacked = new ArrayList<ItemStack>();
		for (CardboardBox cb : this.storage) {
			unpacked.add(cb.unbox());
		}
		return unpacked;

	}
}
