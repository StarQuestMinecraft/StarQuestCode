
package us.higashiyama.george.SQRankup.Currencies;

/*
 * This class is a Crate that can hold as many cardboard boxes as needed.
 * This crate can be serialized for easy inventory manipulation.
 * 
 */
import java.io.Serializable;
import java.util.ArrayList;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import us.higashiyama.george.CardboardBox.CardboardBox;

import com.regalphoenixmc.SQRankup.Database;
import com.regalphoenixmc.SQRankup.RankupPlayer;

public class Crate extends Currency implements Serializable {

	private static final long serialVersionUID = 3171408210789986887L;
	public ArrayList<CardboardBox> storage = new ArrayList<CardboardBox>();
	public String name;

	@Override
	public String getAlias() {

		return this.name;
	}

	@Override
	public RankupPlayer canPurchase(Player player, double money, int kills, Perk perk) {

		if (this.buildLocation() != null && !(this.buildLocation().equals(player.getLocation()))) {
			player.sendMessage(ChatColor.RED + "You must be at the trading node specified with this offer.");
			return null;
		}

		for (CardboardBox cb : storage) {
			ItemStack is = cb.unbox();
			if (!(player.getInventory().contains(is))) {
				return null;
			}
		}

		return Database.getEntry(player.getName());
	}

	@Override
	public void purchase(Player player, RankupPlayer rp, double money, int kills) {

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

	public void queuePurchase(Player player) {

		Database.addPerk(player, this, false);

	}

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
