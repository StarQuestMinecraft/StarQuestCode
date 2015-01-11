
package us.higashiyama.george.Currencies;

/*
 * This class is a Crate that can hold as many cardboard boxes as needed.
 * This crate can be serialized for easy inventory manipulation.
 * 
 */
import java.io.Serializable;
import java.util.ArrayList;

import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import us.higashiyama.george.CardboardBox.CardboardBox;
import us.higashiyama.george.SQTrading.Database;

public class Crate implements Serializable, Currency {

	private static final long serialVersionUID = 3171408210789986887L;
	public ArrayList<CardboardBox> storage = new ArrayList<CardboardBox>();
	public String name;

	public Crate(Iterable<CardboardBox> itr) {

		String name = "";
		for (CardboardBox cb : itr) {
			this.storage.add(cb);

		}

		for (CardboardBox cb : this.storage) {
			System.out.println(cb.toString());

			name += cb.toString() + ";";
			System.out.println(name);
		}

		this.name = name;

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

		String name = "";
		for (ItemStack is : i) {
			CardboardBox cb;
			if (is == null) {
				continue;
			} else {
				cb = new CardboardBox(is);

			}
			storage.add(cb);

			name += cb.toString() + ";";
		}

		this.name = name;

	}

	@Override
	public String getAlias() {

		return this.name;
	}

	@Override
	public boolean hasCurrency(Player player) {

		for (CardboardBox cb : storage) {
			ItemStack is = cb.unbox();
			if (!(player.getInventory().contains(is))) {
				return false;
			}
		}
		return true;
	}

	@Override
	public void removeCurrency(Player p) {

		for (CardboardBox cb : storage) {
			p.getInventory().remove(cb.unbox());
		}

	}

	@Override
	public void giveCurrency(Player player) {

		Database.addPerk(player, this, false);

	}

	public void redeem(Player player) {

		for (CardboardBox cb : storage) {
			ItemStack is = cb.unbox();
			// if inventory is full
			if (player.getInventory().firstEmpty() == -1) {
				player.getWorld().dropItem(player.getLocation(), is);
			} else {
				player.getInventory().addItem(is);
			}

		}

	}

	public void unpack(Inventory i) {

		ArrayList<ItemStack> unpacked = this.unpackBoxes();

		for (int x = 0; x < i.getSize(); x++) {
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
