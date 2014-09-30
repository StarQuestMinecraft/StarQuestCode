
package us.higashiyama.george.SQTrading.Utils;

import org.bukkit.Material;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public class InventoryUtil {

	public static void removeInventoryItems(Inventory inv, ItemStack item) {

		removeInventoryItems(inv, item.getType(), item.getAmount());
	}

	public static void removeInventoryItems(Inventory inv, Material type, int amount) {

		ItemStack[] items = inv.getContents();
		for (int i = 0; i < items.length; i++) {
			ItemStack is = items[i];
			if (is != null && is.getType() == type) {
				int newamount = is.getAmount() - amount;
				if (newamount > 0) {
					is.setAmount(newamount);
					break;
				} else {
					items[i] = new ItemStack(Material.AIR);
					amount = -newamount;
					if (amount == 0)
						break;
				}
			}
		}
		inv.setContents(items);
	}

	public static int getTotalItems(Inventory inv, Material type) {

		int amount = 0;
		for (ItemStack is : inv.getContents()) {
			if (is != null) {
				if (is.getType() == type) {
					amount += is.getAmount();
				}
			}
		}
		return amount;
	}

}