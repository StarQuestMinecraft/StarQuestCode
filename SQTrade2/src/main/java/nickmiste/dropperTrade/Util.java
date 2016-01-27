package nickmiste.dropperTrade;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.Dropper;
import org.bukkit.block.Sign;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import nickmiste.SQTrade2;

public class Util
{
	public static HashMap<Player, Location> pendingAmount = new HashMap<Player, Location>();
	
	public static void completeTransaction(Player player, int amount)
	{
		if (isValid(pendingAmount.get(player)))
		{
			Sign sign = (Sign) player.getWorld().getBlockAt(pendingAmount.get(player)).getState();
			int price = Integer.parseInt(sign.getLine(2).split("c")[0].substring(2)) * amount;
			
			if (SQTrade2.cc3.getAccountManager().getAccount(player.getName()).hasEnough(price, player.getWorld().getName(), "credit"))
			{
				if (amount <= getAmount(pendingAmount.get(player)))
				{
					SQTrade2.cc3.getAccountManager().getAccount(player.getName()).withdraw(price, player.getWorld().getName(), "credit");
					SQTrade2.cc3.getAccountManager().getAccount(sign.getLine(3).substring(2)).deposit(price, player.getWorld().getName(), "credit");

					Inventory inv = getDropper(pendingAmount.get(player)).getInventory();
					for (int i = 0; i < amount; i++)
					{
						for (int j = 0; j < 9; j++)
						{
							if (inv.getItem(j) != null)
							{
								ItemStack itemToAdd = inv.getItem(j).clone();
								itemToAdd.setAmount(1);
								player.getInventory().addItem(itemToAdd);
								if (inv.getItem(j).getAmount() > 1)
									inv.getItem(j).setAmount(inv.getItem(j).getAmount() - 1);
								else
									inv.setItem(j, null);
								break;
							}
						}
					}
					
					if (getAmount(pendingAmount.get(player)) == 0)
					{
						sign.setLine(1, ChatColor.RED + "OUT");
						sign.setLine(2, ChatColor.RED + "OF");
						sign.setLine(3, ChatColor.RED + "STOCK");
						sign.update();	
					}
					else
					{
						sign.setLine(1, ChatColor.DARK_RED + "" + getAmount(sign.getLocation()) + " " + sign.getLine(1).split(" ")[1]);
						sign.update();
					}
				}
				else player.sendMessage(ChatColor.DARK_RED + "This shop does not have that many items in stock!");
			}
			else player.sendMessage(ChatColor.DARK_RED + "You don't have enough credits for that!");
		}
		else player.sendMessage(ChatColor.DARK_RED + "Improperly built shop!");
		
		pendingAmount.remove(player);
	}
	
	public static boolean isValid(Location sign)
	{
		Block block = sign.getBlock().getRelative(((org.bukkit.material.Sign) sign.getBlock().getState().getData()).getAttachedFace());
		
		if (block.getType().equals(Material.DROPPER))
		{
			Dropper dropper = (Dropper) block.getState();
			ArrayList<ItemStack> inv = new ArrayList<ItemStack>(Arrays.asList(dropper.getInventory().getContents()));
			
			while (inv.remove(null));
			
			if (inv.size() > 0)
			{
				ItemStack testStack = inv.get(0);
				for (ItemStack stack : inv)
				{
					if (!stack.getType().equals(testStack.getType()))
						return false;
					if (stack.hasItemMeta() != testStack.hasItemMeta())
						return false;
					if (stack.hasItemMeta() && testStack.hasItemMeta())
						if (!stack.getItemMeta().equals(testStack.getItemMeta()))
							return false;
					
				}
				return true;
			}
		}
		return false;
	}
	
	public static boolean hasDropper(Location sign)
	{
		Block block = sign.getBlock().getRelative(((org.bukkit.material.Sign) sign.getBlock().getState().getData()).getAttachedFace());
		return block.getType().equals(Material.DROPPER);
	}
	
	public static Dropper getDropper(Location sign)
	{
		Block block = sign.getBlock().getRelative(((org.bukkit.material.Sign) sign.getBlock().getState().getData()).getAttachedFace());
		return (Dropper) block.getState();
	}
	
	public static Sign getSign(Location dropper)
	{
		Location signLoc = null;
		if (dropper.getWorld().getBlockAt(dropper.clone().add(1, 0, 0)).getType().equals(Material.WALL_SIGN))
			signLoc = dropper.clone().add(1, 0, 0);
		else if (dropper.getWorld().getBlockAt(dropper.clone().add(-1, 0, 0)).getType().equals(Material.WALL_SIGN))
			signLoc = dropper.clone().add(-1, 0, 0);
		else if (dropper.getWorld().getBlockAt(dropper.clone().add(0, 0, 1)).getType().equals(Material.WALL_SIGN))
			signLoc = dropper.clone().add(0, 0, 1);
		else if (dropper.getWorld().getBlockAt(dropper.clone().add(0, 0, -1)).getType().equals(Material.WALL_SIGN))
			signLoc = dropper.clone().add(0, 0, -1);
		
		if (signLoc == null)
			return null;
		
		return (Sign) dropper.getWorld().getBlockAt(signLoc).getState();
	}
	
	public static int getAmount(Location sign)
	{
		int amount = 0;
		for (ItemStack stack : getDropper(sign).getInventory().getContents())
			if (stack != null)
				amount += stack.getAmount();
		return amount;
	}
	
	public static Material getType(Location sign)
	{
		Material type = null;
		for (ItemStack stack : getDropper(sign).getInventory().getContents())
		{
			if (stack != null)
			{
				type = stack.getType();
				break;
			}
		}
		return type;
	}
	
	public static void viewContents(Player player, Location sign)
	{
		Inventory inv = Bukkit.createInventory(player, 9, ChatColor.GREEN + "Browsing");

		for (int i = 0; i < 9; i++)
		{
			ItemStack dropperStack = getDropper(sign).getInventory().getItem(i);
			if (dropperStack != null)
			{
				ItemStack stack = dropperStack.clone();
				ItemMeta meta = stack.getItemMeta();
				List<String> lore = meta.hasLore() ? meta.getLore() : new ArrayList<String>();
				lore.add(ChatColor.RED + "" + ChatColor.MAGIC + "Contraband");
				meta.setLore(lore);
				stack.setItemMeta(meta);
				
				inv.setItem(i, stack);
			}
		}

		player.openInventory(inv);
	}
	
	public static boolean isDropperShopSign(Sign sign)
	{
		return sign.getLine(0).equals(ChatColor.DARK_GREEN + "Dropper Shop") ||
				sign.getLine(0).equals(ChatColor.RED + "" + ChatColor.STRIKETHROUGH + "Dropper Shop");
	}
}