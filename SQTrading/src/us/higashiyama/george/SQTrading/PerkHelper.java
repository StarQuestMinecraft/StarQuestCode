
package us.higashiyama.george.SQTrading;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.Sign;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import us.higashiyama.george.CardboardBox.CardboardBox;
import us.higashiyama.george.Currencies.Crate;
import us.higashiyama.george.Currencies.Credits;
import us.higashiyama.george.Currencies.Currency;
import us.higashiyama.george.Currencies.Perk;

public class PerkHelper {

	public static void perkTrade(Player p, String[] args) {

		// format of command
		// trade <wants> <has> <expiry> <player>

		if (args.length < 1 || args.length > 4) {
			return;
		}

		// If the player wants to trade for an existing offer
		if (args.length == 1) {
			int id = 0;
			try {
				id = Integer.parseInt(args[0]);
			} catch (NumberFormatException e) {
				p.sendMessage(ChatColor.AQUA + "Must specify a number ID of the offer");
				return;
			}
			System.out.println("getting currency");
			Currency currency = Database.getOffer(id, p);
			System.out.println(currency);
			if (currency == null) {
				p.sendMessage(ChatColor.AQUA + "No offer with id: " + id + " in the database that you are allowed to redeem at this time");
				return;
			}

			// TODO: Location check

			currency.

			p.sendMessage(ChatColor.AQUA + "Transaction credited.");
			System.out.println("Deleting");
			Database.deleteOffer(id);
			return;
		}

		if (args.length < 3) {
			p.sendMessage(ChatColor.RED + "You left out an argument");
			return;
		}

		Currency want = null;
		Currency has = null;
		int hours = 0;

		try {
			double credits = Double.parseDouble(args[0]);
			want = new Credits(credits);
		} catch (NumberFormatException e) {
			for (Perk perk : SQTrading.perks) {
				if (perk.getAlias().equalsIgnoreCase(args[0])) {
					want = perk;
					break;
				}

			}

			if (args[0].equalsIgnoreCase("inventory")) {
				want = new Crate(p.getInventory());
			}

			if (args[0].equalsIgnoreCase("item")) {
				want = parseItemInput(p);
			}

		}

		if (want == null) {
			p.sendMessage(ChatColor.AQUA + "No perk exists with the name: " + args[0]);
			return;
		}

		try {
			double credits = Double.parseDouble(args[1]);
			has = new Credits(credits);
		} catch (NumberFormatException e) {
			for (Perk perk : SQTrading.perks) {
				if (perk.getAlias().equalsIgnoreCase(args[1])) {
					has = perk;
					break;
				}

			}
			if (args[1].equalsIgnoreCase("inventory")) {
				has = new Crate(p.getInventory());
			}
		}

		if (args[1].equalsIgnoreCase("item")) {
			has = parseItemInput(p);
		}

		if (has == null) {
			p.sendMessage(ChatColor.AQUA + "No perk exists with the name: " + args[0]);
			return;
		}

		try {
			hours = Integer.parseInt(args[2]);
		} catch (NumberFormatException e) {
			p.sendMessage(ChatColor.AQUA + "Expiry time must be a whole integer");
			return;
		}

		Player player = null;
		if (args.length > 3) {
			player = Bukkit.getPlayer(args[4]);
			if (player == null) {
				p.sendMessage(ChatColor.AQUA + "No such player exists");
				return;
			}
		}

		boolean hasPerk = false;
		if (has instanceof Credits) {
			if (SQTrading.economy.getBalance(p) < ((Credits) has).getCredits()) {
				p.sendMessage(ChatColor.AQUA + "Not enough credits to make the offer");
				return;
			} else {
				SQTrading.economy.withdrawPlayer(p, ((Credits) has).getCredits());
				hasPerk = true;
			}

		}

		if (has instanceof Perk) {
			List<Currency> perks = Database.getUnredeemedPlayerPerks(p);
			for (Currency perk : perks) {
				if ((perk instanceof Perk) && perk.getAlias().equalsIgnoreCase(((Perk) has).getAlias())) {
					hasPerk = true;
					Database.removePerk(p, (Perk) perk);
					break;
				}
			}
		}

		if (has instanceof Crate) {
			// We know at this point that the player has the items

			hasPerk = true;
			Crate c = (Crate) has;
			for (CardboardBox cb : c.getBoxes()) {
				p.getInventory().remove(cb.unbox());
			}

		}

		if (!hasPerk) {
			p.sendMessage(ChatColor.AQUA + "You don't own the perk you want to trade");
			return;
		}

		Database.openOffer(p, player, want, has, hours * 3600000);

		p.sendMessage(ChatColor.AQUA + "Offer created!");

	}

	public static Currency parseItemInput(Player player) {

		Block b = player.getTargetBlock(null, 100);
		if (b.getType() == (Material.SIGN) || b.getType() == (Material.WALL_SIGN)) {
			// safecast
			Sign s = (Sign) player.getTargetBlock(null, 100).getState();

			int quantity;
			try {
				quantity = Integer.parseInt(s.getLine(1));
			} catch (NumberFormatException ex) {
				player.sendMessage(ChatColor.AQUA + "Must specify a whole number quantity on line 2 of the sign");
				return null;
			}
			String name = s.getLine(0);

			ArrayList<ItemStack> stackList = new ArrayList<ItemStack>();
			for (String testName : SQTrading.itemNames.keySet()) {
				if (testName.equalsIgnoreCase(name)) {

					int stacks = getStackData(quantity)[0];
					int remaining = getStackData(quantity)[1];

					while (stacks > 0) {
						ItemStack is = new ItemStack(Material.getMaterial(SQTrading.itemNames.get(testName)[0]), stacks * 64,
								(byte) SQTrading.itemNames.get(testName)[1]);
						stackList.add(is);
						stacks--;
					}
					ItemStack is = new ItemStack(Material.getMaterial(SQTrading.itemNames.get(testName)[0]), remaining,
							(byte) SQTrading.itemNames.get(testName)[1]);

					stackList.add(is);
				}
			}

			ArrayList<CardboardBox> cbList = new ArrayList<CardboardBox>();
			for (ItemStack tis : stackList) {
				cbList.add(new CardboardBox(tis));
			}

			Crate c = new Crate(cbList);
			return c;

		}
		return null;
	}

	// returns array = {full stacks, number in second stack}
	private static int[] getStackData(int i) {

		// downcasts acts as Math.floor
		int fullstacks = (int) (i % 64);
		int remaining = i - (fullstacks * 64);

		int[] retArray = { fullstacks, remaining };
		return retArray;

	}

	public static void removeOfferAndRecredit(Player p, int id) {

		Currency currency = Database.removeOffer(id, p);

		if (currency == null) {
			p.sendMessage(ChatColor.AQUA + "No offer with id: " + id + " in the database that you are allowed to delete");
			return;
		}
		if (currency instanceof Credits) {
			currency.purchase(p, Database.getEntry(p.getName()), 0, 0);
		}

		if (currency instanceof Perk) {
			((Perk) currency).queuePurchase(p);
		}

		if (currency instanceof Crate) {
			currency.purchase(p, Database.getEntry(p.getName()), 0, 0);
		}
		p.sendMessage(ChatColor.AQUA + "Perk removed and recredited.");
	}

	public static void addPerkToPlayer(String playerAlias, String perk) {

		for (Perk p : SQTrading.perks) {
			if (p.getAlias().equalsIgnoreCase(perk)) {

				Player player = Bukkit.getPlayer(playerAlias);
				RankupPlayer rp = p.canPurchase(player, 0, 0, p);
				if (rp != null) {
					// Give the player the perk, but leave it unredeemed
					p.queuePurchase(player);
				}

			}
		}

	}

	public static void searchAndRedeemPerk(Player p, String alias) {

		List<Currency> unredeemed = Database.getUnredeemedPlayerPerks(p);
		for (Currency perk : unredeemed) {
			if (perk.getAlias().equalsIgnoreCase(alias)) {
				// Leave the other params because of per-currency overriding
				perk.purchase(p, Database.getEntry(p.getName()), 0, 0);
				break;
			}
		}
	}

	public static void showPrivateOffers(Player p) {

		p.sendMessage(ChatColor.GOLD + "Private Offers: ");
		Database.showPrivateOffers(p);
	}

	public static void showUnredeemedPerks(Player p) {

		List<Currency> unredeemed = Database.getUnredeemedPlayerPerks(p);
		if (unredeemed.size() > 0) {
			p.sendMessage(ChatColor.AQUA + "You have the following unredeemed perks: ");
			for (Currency perk : unredeemed) {
				p.sendMessage(ChatColor.GOLD + "     " + perk.getAlias());
			}
		} else {
			p.sendMessage(ChatColor.AQUA + "You have no perks to redeem");
		}

	}
}
