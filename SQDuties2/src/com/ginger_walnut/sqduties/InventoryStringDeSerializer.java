
package com.ginger_walnut.sqduties;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public class InventoryStringDeSerializer {

	public static String InventoryToString(Inventory invInventory, Player p) {

		String serialization = invInventory.getSize() + ";";
		for (int i = 0; i < invInventory.getSize(); i++) {
			ItemStack is = invInventory.getItem(i);
			if (is != null) {
				String serializedItemStack = new String();

				String isType = String.valueOf(is.getType().getId());
				serializedItemStack = serializedItemStack + "t@" + isType;
				if (is.getDurability() != 0) {
					String isDurability = String.valueOf(is.getDurability());
					serializedItemStack = serializedItemStack + ":d@" + isDurability;
				}
				if (is.getAmount() != 1) {
					String isAmount = String.valueOf(is.getAmount());
					serializedItemStack = serializedItemStack + ":a@" + isAmount;
				}
				Map<Enchantment, Integer> isEnch = is.getEnchantments();
				if (isEnch.size() > 0) {
					for (Map.Entry<Enchantment, Integer> ench : isEnch.entrySet()) {
						serializedItemStack = serializedItemStack + ":e@" + ((Enchantment) ench.getKey()).getId() + "@" + ench.getValue();
					}
				}
				serialization = serialization + i + "#" + serializedItemStack + ";";
			}
		}
		return serialization + p.getLevel() + "&" + p.getExp();
	}

	public static String ArmorToString(Inventory invInventory) {

		String serialization = invInventory.getSize() + ";";
		for (int i = 0; i < invInventory.getSize(); i++) {
			ItemStack is = invInventory.getItem(i);
			if (is != null) {
				String serializedItemStack = new String();

				String isType = String.valueOf(is.getType().getId());
				serializedItemStack = serializedItemStack + "t@" + isType;
				if (is.getDurability() != 0) {
					String isDurability = String.valueOf(is.getDurability());
					serializedItemStack = serializedItemStack + ":d@" + isDurability;
				}
				if (is.getAmount() != 1) {
					String isAmount = String.valueOf(is.getAmount());
					serializedItemStack = serializedItemStack + ":a@" + isAmount;
				}
				Map<Enchantment, Integer> isEnch = is.getEnchantments();
				if (isEnch.size() > 0) {
					for (Map.Entry<Enchantment, Integer> ench : isEnch.entrySet()) {
						serializedItemStack = serializedItemStack + ":e@" + ((Enchantment) ench.getKey()).getId() + "@" + ench.getValue();
					}
				}
				serialization = serialization + i + "#" + serializedItemStack + ";";
			}
		}
		return serialization;
	}

	public static double[] StringToExp(String invString) {

		String[] serializedBlocks = invString.split(";");
		double level = Integer.parseInt(serializedBlocks[serializedBlocks.length - 1].split("&")[0]);
		double exp = Double.parseDouble(serializedBlocks[serializedBlocks.length - 1].split("&")[1]);
		double[] returnExp = { level, exp };
		return returnExp;
	}

	public static Inventory StringToInventory(String invString) {

		ArrayList<String> markup = new ArrayList<String>();
		List<String> serialized = Arrays.asList(invString.split(";"));
		for (String s : serialized) {
			if (!s.contains("&")) {
				markup.add(s);
			}
		}

		String[] serializedBlocks = markup.toArray(new String[markup.size()]);
		String invInfo = serializedBlocks[0];
		Inventory deserializedInventory = Bukkit.getServer().createInventory(null, Integer.valueOf(invInfo).intValue());
		for (int i = 1; i < serializedBlocks.length; i++) {
			String[] serializedBlock = serializedBlocks[i].split("#");
			int stackPosition = Integer.valueOf(serializedBlock[0]).intValue();
			if (stackPosition < deserializedInventory.getSize()) {
				ItemStack is = null;
				Boolean createdItemStack = Boolean.valueOf(false);

				String[] serializedItemStack = serializedBlock[1].split(":");
				for (String itemInfo : serializedItemStack) {
					String[] itemAttribute = itemInfo.split("@");
					try {
						if (itemAttribute[0].equals("t")) {
							is = new ItemStack(Material.getMaterial(Integer.valueOf(itemAttribute[1]).intValue()));
							createdItemStack = Boolean.valueOf(true);
						} else if ((itemAttribute[0].equals("d")) && (createdItemStack.booleanValue())) {
							is.setDurability(Short.valueOf(itemAttribute[1]).shortValue());
						} else if ((itemAttribute[0].equals("a")) && (createdItemStack.booleanValue())) {
							is.setAmount(Integer.valueOf(itemAttribute[1]).intValue());
						} else if ((itemAttribute[0].equals("e")) && (createdItemStack.booleanValue())) {
							is.addEnchantment(Enchantment.getById(Integer.valueOf(itemAttribute[1]).intValue()), Integer.valueOf(itemAttribute[2]).intValue());
						}
					} catch (Exception e) {
						SQDuties.getPluginMain().getServer().broadcastMessage(ChatColor.RED + "WARNING: An illegally enchanted item was just vaporized!!!");
						is = new ItemStack(Material.AIR, 0);
					}
				}
				deserializedInventory.setItem(stackPosition, is);
			}
		}
		return deserializedInventory;
	}
}
