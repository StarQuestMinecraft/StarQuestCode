package me.Tongonto;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.block.Sign;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.permissions.Permission;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;

import net.homeip.hall.sqnetevents.SQNetEvents;
import net.homeip.hall.sqnetevents.packet.Data;
import net.homeip.hall.sqnetevents.packet.EventPacket;
import net.homeip.hall.sqnetevents.packet.Packet;
import net.homeip.hall.sqnetevents.packet.ReceivedDataEvent;
import net.milkbowl.vault.economy.Economy;

public class SQShopper extends JavaPlugin {
	
	protected HashMap<Player, Cart> openCartList = new HashMap<Player, Cart>();
	protected HashMap<Player, Integer> modificationNumbers = new HashMap<Player, Integer>();
	protected HashMap<Material, ShopItem> shopItemList = new HashMap<Material, ShopItem>();
	protected ArrayList<Sign> detectedSigns = new ArrayList<Sign>();
	boolean newDay = false;
	boolean sentTimeout = false;
	
	private Economy economy = null;
	
	SQShopper shopperInstance;
	
	public Permission makeShopSignPermission = new Permission("makeShopSign.allowed");
	
	@Override
	public void onEnable() {
		getLogger().info("SQShopper has been enabled!");
		new ShopperListener(this);
		economy = registerEconomy();
		shopperInstance = this;
		PluginManager pm = getServer().getPluginManager();
		pm.addPermission(makeShopSignPermission);
		
		FileConfiguration shopperConfig = this.getConfig();
		
		Calendar calendar = Calendar.getInstance();
		int day = calendar.get(Calendar.DAY_OF_MONTH);
		int lastDay = shopperConfig.getInt("Day");
		if(day != lastDay) {
			shopperConfig.set("Day", day);
			this.saveConfig();
			newDay = true;
		}
		
		for(String item : shopperConfig.getConfigurationSection("items").getKeys(false)) {
			Material material = null;
			if(item.equals("lapis")) {
				material = Material.INK_SACK;
			}
			else if(item.equals("cocoa")) {
				material = Material.BOAT_SPRUCE;
			}
			else {
				material = Material.getMaterial(item);
			}
				List<Integer> buyList = shopperConfig.getIntegerList("items." + item + ".weeklyBought");
				List<Integer> sellList = shopperConfig.getIntegerList("items." + item + ".weeklySold");
			int totalBought = 0;
			int totalSold = 0;
			for(Integer i : buyList) {
				totalBought = totalBought + i;
			}
			
			for(Integer i : sellList) {
				totalSold = totalSold + i;
			}
			Double base = shopperConfig.getDouble("items." + item + ".basevalue");
			ShopItem shopItem = new ShopItem(totalBought, totalSold, base, material);
			if(newDay) {
				shopItem.addBoughtItems(shopperConfig.getInt("items." + item + ".npcBuy"));
				shopItem.addSoldItems(shopperConfig.getInt("items." + item + ".npcSell"));
			}
			shopItemList.put(material, shopItem);
		}
		
	}
	
	@Override
	public void onDisable() {
		FileConfiguration shopperConfig = this.getConfig();
		for(String item : shopperConfig.getConfigurationSection("items").getKeys(false)) {
			Material material = null;
			if(item.equals("lapis")) {
				material = Material.INK_SACK;
			}
			else if(item.equals("cocoa")) {
				material = Material.BOAT_SPRUCE;
			}
			else {
				material = Material.getMaterial(item);
			}
			ShopItem shopItem = shopItemList.get(material);
			List<Integer> buyList = shopperConfig.getIntegerList("items." + item + ".weeklyBought");
			List<Integer> sellList = shopperConfig.getIntegerList("items." + item + ".weeklySold");
			if(newDay) {
				buyList.set(6, buyList.get(5));
				sellList.set(6, sellList.get(5));
				buyList.set(5, buyList.get(4));
				sellList.set(5, sellList.get(4));
				buyList.set(4, buyList.get(3));
				sellList.set(4, sellList.get(3));
				buyList.set(3, buyList.get(2));
				sellList.set(3, sellList.get(2));
				buyList.set(2, buyList.get(1));
				sellList.set(2, sellList.get(1));
				buyList.set(1, buyList.get(0));
				sellList.set(1, sellList.get(0));
				buyList.set(0, shopItem.getDailyBought());
				sellList.set(0, shopItem.getDailySold());
			}
			else {
				buyList.set(0, shopItem.getDailyBought() + buyList.get(0));
				sellList.set(0, shopItem.getDailySold() + sellList.get(0));
			}
			shopperConfig.set("items." + item + ".weeklyBought", buyList);
			shopperConfig.set("items." + item + ".weeklySold", sellList);
		}
		this.saveConfig();
		getLogger().info("SQShopper has been disabled!");
	}
	
	private Economy registerEconomy() {
		Economy retval = null;
		RegisteredServiceProvider<Economy> economyProvider = getServer().getServicesManager().getRegistration(net.milkbowl.vault.economy.Economy.class);
		if (economyProvider != null) {
			retval = economyProvider.getProvider();
		}

		return retval;
	}
	
	public Economy getEconomy() {
		return economy;
	}
	
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		
		if(cmd.getName().equalsIgnoreCase("valueall") && sender.isOp()) {
			for(ShopItem shopItem : shopItemList.values()) {
				getLogger().info(shopItem.itemMaterial.toString() + " currently costs " + Double.toString(shopItem.getCurrentPrice()));
			}
			return true;
		}
		
		if(cmd.getName().equalsIgnoreCase("reloadshops") && sender.isOp()) {
			this.reloadConfig();
			HashMap<Material, ShopItem> rememberedShopItemList = new HashMap<Material, ShopItem>();
			
			for(ShopItem shopItem : shopItemList.values()) {
				rememberedShopItemList.put(shopItem.itemMaterial, shopItem);
			}
			
			shopItemList.clear();
			FileConfiguration shopperConfig = this.getConfig();
			for(String item : shopperConfig.getConfigurationSection("items").getKeys(false)) {
				Material material = null;
				if(item.equals("lapis")) {
					material = Material.INK_SACK;
				}
				else if(item.equals("cocoa")) {
					material = Material.BOAT_SPRUCE;
				}
				else {
					material = Material.getMaterial(item);
				}
				List<Integer> buyList = shopperConfig.getIntegerList("items." + item + ".weeklyBought");
				List<Integer> sellList = shopperConfig.getIntegerList("items." + item + ".weeklySold");
				int totalBought = 0;
				int totalSold = 0;
				for(Integer i : buyList) {
					totalBought = totalBought + i;
				}
				
				for(Integer i : sellList) {
					totalSold = totalSold + i;
				}
				Double base = shopperConfig.getDouble("items." + item + ".basevalue");
				ShopItem shopItem = new ShopItem(totalBought, totalSold, base, material);
				if(newDay) {
					shopItem.addBoughtItems(shopperConfig.getInt("items." + item + ".npcBuy"));
					shopItem.addSoldItems(shopperConfig.getInt("items." + item + ".npcSell"));
				}
				shopItem.setDailyBought(rememberedShopItemList.get(material).getDailyBought());
				shopItem.setDailySold(rememberedShopItemList.get(material).getDailySold());
				shopItemList.put(material, shopItem);
			}
			return true;
		}

		if(cmd.getName().equalsIgnoreCase("value") && sender instanceof Player) {
			/*Data data = new Data();
			data.addString("one", "testing");
			EventPacket packet = new EventPacket(new ReceivedDataEvent(data), "s1");
			Bukkit.broadcastMessage(packet.toString());
			SQNetEvents.getInstance().send(packet, "s1");
			*/
			Player player = this.getServer().getPlayer(sender.getName());
			ItemStack itemInHand = player.getInventory().getItemInMainHand();
			boolean isCharcoal = false;
			boolean isDye = false;
			if(itemInHand != null){
				if(itemInHand.getType().equals(Material.COAL) && itemInHand.getDurability() == 1) {
					isCharcoal = true;
				}
				if(itemInHand.getType().equals(Material.INK_SACK) && itemInHand.getDurability() != 4) {
					isDye = true;
				}
				if(itemInHand.getType().equals(Material.INK_SACK) && itemInHand.getDurability() == 3) {
					isDye = false;
				}
				if(!isDye) {
					if(shopItemList.containsKey(itemInHand.getType())) {
						if(itemInHand.getType().equals(Material.INK_SACK) && itemInHand.getDurability() == 4) {
							player.sendMessage("Value of Lapis Lazuli : " + shopItemList.get(itemInHand.getType()).getCurrentPrice());
							return true;
						}
						else if(itemInHand.getType().equals(Material.INK_SACK) && itemInHand.getDurability() == 3) {
							player.sendMessage("Value of Cocoa Beans : " + shopItemList.get(Material.BOAT_SPRUCE).getCurrentPrice());
							return true;
						}
						else {
							player.sendMessage("Value of " + itemInHand.getType().toString() + " : " + shopItemList.get(itemInHand.getType()).getCurrentPrice());
							return true;
						}
					}
				}
				player.sendMessage("That item is not sellable!");
			}
			
			return true;
		}
		
		if(cmd.getName().equalsIgnoreCase("modifysign") && sender instanceof Player) {
			Player player = this.getServer().getPlayer(sender.getName());
			
			int price = Integer.parseInt(args[0]);
			
			if(modificationNumbers.containsKey(player)) {
				modificationNumbers.remove(player);
			}
			modificationNumbers.put(player, price);
			return true;
		}
		
		if(cmd.getName().equalsIgnoreCase("cart") && sender instanceof Player) {
			if(args.length > 0) {
				if(args[0].equalsIgnoreCase("help")) {
					sender.sendMessage(ChatColor.GOLD + "Cart commands:");
					sender.sendMessage(ChatColor.GOLD + "/cart help" + ChatColor.WHITE + ": displays this help page.");
					sender.sendMessage(ChatColor.GOLD + "/cart view"  + ChatColor.WHITE + ": displays your cart's contents");
					sender.sendMessage(ChatColor.GOLD + "/cart clear"  + ChatColor.WHITE + ": empties your cart.");
					sender.sendMessage(ChatColor.GOLD + "/value"  + ChatColor.WHITE + ": shows the price that the item that you're holding can be sold for.");
					sender.sendMessage(ChatColor.DARK_GREEN + "Left-click a sell sign or a shop sign to update it's price.");
					sender.sendMessage(ChatColor.DARK_GREEN + "Right-click a sell sign to sell the contents of it's chest.");
					sender.sendMessage(ChatColor.DARK_GREEN + "Right-click a shop sign to add items to your cart - right-click a checkout sign to buy the items in your cart.");
					return true;
				}
				if(args[0].equalsIgnoreCase("view")) {
					if(openCartList.containsKey(sender)) {
						Cart theirCart = openCartList.get(sender);
						sender.sendMessage("Items in your cart : ");
						for(int i=0; i<theirCart.getCartContents().size(); i++) {
							ItemStack item = theirCart.getCartContents().get(i);
							sender.sendMessage(Integer.toString(item.getAmount()) + " " + item.getType() + " (" + item.getDurability() +")");
						}
						sender.sendMessage("Total Price :" + theirCart.getCost());
					}
					else {
						sender.sendMessage("Your cart is empty!");
					}
					return true;
				}
				if(args[0].equalsIgnoreCase("clear")) {
					if(openCartList.containsKey(sender)) {
						openCartList.remove(sender);
						sender.sendMessage("Cart emptied!");
					}
					else {
						sender.sendMessage("Your cart is already empty.");
					}
					
					return true;
				}
			}
			else {
				sender.sendMessage(ChatColor.GOLD + "Cart commands:");
				sender.sendMessage(ChatColor.GOLD + "/cart help" + ChatColor.WHITE + ": displays this help page.");
				sender.sendMessage(ChatColor.GOLD + "/cart view"  + ChatColor.WHITE + ": displays your cart's contents");
				sender.sendMessage(ChatColor.GOLD + "/cart clear"  + ChatColor.WHITE + ": empties your cart.");
				sender.sendMessage(ChatColor.GOLD + "/value"  + ChatColor.WHITE + ": shows the price that the item that you're holding can be sold for.");
				sender.sendMessage(ChatColor.DARK_GREEN + "Left-click a sell sign or a shop sign to update it's price.");
				sender.sendMessage(ChatColor.DARK_GREEN + "Right-click a sell sign to sell the contents of it's chest.");
				sender.sendMessage(ChatColor.DARK_GREEN + "Right-click a shop sign to add items to your cart - right-click a checkout sign to buy the items in your cart.");
				return true;
			}
		}
		
		return false;
	}
	
}
