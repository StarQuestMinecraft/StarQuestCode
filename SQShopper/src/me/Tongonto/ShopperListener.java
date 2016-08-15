package me.Tongonto;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.Chest;
import org.bukkit.block.Sign;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitTask;

import net.homeip.hall.sqnetevents.packet.Data;
import net.homeip.hall.sqnetevents.packet.ReceivedDataEvent;
import net.milkbowl.vault.economy.Economy;


public class ShopperListener implements Listener {
	
	private final SQShopper plugin;
	
	public ShopperListener(SQShopper plugin) {
		this.plugin = plugin;
		plugin.getServer().getPluginManager().registerEvents(this, plugin);
	}
	
	public void updateShopSign(Sign sign) {
		if(sign.getLine(0).equals(ChatColor.BOLD + "Shop")) {
			if(plugin.shopItemList.containsKey(Material.getMaterial(sign.getLine(1))) || sign.getLine(1).equals("BlueDye") || sign.getLine(1).equals("Prismarine") || sign.getLine(1).equals("BrownDye")) {
				int amount = Integer.parseInt(sign.getLine(2));
				Double cost = null;
				FileConfiguration shopperConfig = plugin.getConfig();
				if((sign.getLine(1).equals("BlueDye") || sign.getLine(1).equals("Prismarine") || sign.getLine(1).equals("BrownDye"))) {
					if(sign.getLine(1).equals("Prismarine")) {
						cost = amount * plugin.shopItemList.get(Material.PRISMARINE_SHARD).getCurrentPrice() * shopperConfig.getDouble("items." + Material.PRISMARINE_SHARD.toString() + ".buypriceboost");
					}
					else if(sign.getLine(1).equals("BlueDye")) {
						cost = amount * plugin.shopItemList.get(Material.INK_SACK).getCurrentPrice() * shopperConfig.getDouble("items.lapis.buypriceboost");
					}
					else {
						cost = amount * plugin.shopItemList.get(Material.BOAT_SPRUCE).getCurrentPrice() * shopperConfig.getDouble("items.cocoa.buypriceboost");
					}
				}
				else {
					cost = amount * plugin.shopItemList.get(Material.getMaterial(sign.getLine(1))).getCurrentPrice() * shopperConfig.getDouble("items." + sign.getLine(1) + ".buypriceboost");
				}
				cost = (double) Math.round(cost * 100) / 100;
				sign.setLine(3, Double.toString(cost));
				sign.update();
				if(!(plugin.sentTimeout)) {
					BukkitTask task = new ShopperTask(this.plugin).runTaskLater(this.plugin, 200);
					plugin.sentTimeout = true;
				}
				
				ArrayList<Block> blockList = new ArrayList<Block>();
				blockList.add(sign.getBlock().getRelative(BlockFace.NORTH));
				blockList.add(sign.getBlock().getRelative(BlockFace.SOUTH));
				blockList.add(sign.getBlock().getRelative(BlockFace.EAST));
				blockList.add(sign.getBlock().getRelative(BlockFace.WEST));
				blockList.add(sign.getBlock().getRelative(BlockFace.UP));
				blockList.add(sign.getBlock().getRelative(BlockFace.DOWN));
				for(int i=0; i<6; i++) {
					Block block = blockList.get(i);
					if(block.getType() == Material.WALL_SIGN || block.getType() == Material.SIGN_POST) {
						Sign newSign = (Sign) block.getState();
						if(!(plugin.detectedSigns.contains(newSign))) {
							plugin.detectedSigns.add(newSign);
							updateShopSign(newSign);
						}
					}
					
				}
			}
		}
	}
	
	@EventHandler
	public void onMessageRecieved(ReceivedDataEvent e) {
		/*Bukkit.broadcastMessage("Woooohooooo!");
		Data data = e.getData();
		Bukkit.broadcastMessage(data.getString("one"));
		*/
	}
	
	@EventHandler
	public void onBlockInteraction(PlayerInteractEvent e) {
		Player player = e.getPlayer();
		Block blockClicked = e.getClickedBlock();
		if(blockClicked != null) {
			if(e.getAction().equals(Action.RIGHT_CLICK_BLOCK)) {
			if(blockClicked.getType() == Material.WALL_SIGN || blockClicked.getType() == Material.SIGN_POST) {
				Sign clickedSign = (Sign) blockClicked.getState();
				
				if(clickedSign.getLine(0).equals(ChatColor.BOLD + "Shop")) {
					updateShopSign(clickedSign);
					ItemStack forSaleItem = null;
					ArrayList<String> miscList = new ArrayList<String>();
					
					miscList.add("RedDye");
					miscList.add("OrangeDye");
					miscList.add("YellowDye");
					miscList.add("LimeDye");
					miscList.add("GreenDye");
					miscList.add("LightBlueDye");
					miscList.add("CyanDye");
					miscList.add("BlueDye");
					miscList.add("PurpleDye");
					miscList.add("MagentaDye");
					miscList.add("PinkDye");
					miscList.add("WhiteDye");
					miscList.add("LightGrayDye");
					miscList.add("GrayDye");
					miscList.add("BlackDye");
					miscList.add("BrownDye");
					miscList.add("Prismarine");
					miscList.add("NetherBrick");
					if(miscList.contains(clickedSign.getLine(1))) {
						String string = clickedSign.getLine(1);
						if(string.equals("RedDye")) {
							forSaleItem = new ItemStack(Material.INK_SACK);
							forSaleItem.setDurability((short) 1);
						}
						if(string.equals("OrangeDye")) {
							forSaleItem = new ItemStack(Material.INK_SACK);
							forSaleItem.setDurability((short) 14);
						}
						if(string.equals("YellowDye")) {
							forSaleItem = new ItemStack(Material.INK_SACK);
							forSaleItem.setDurability((short) 11);
						}
						if(string.equals("LimeDye")) {
							forSaleItem = new ItemStack(Material.INK_SACK);
							forSaleItem.setDurability((short) 10);
						}
						if(string.equals("GreenDye")) {
							forSaleItem = new ItemStack(Material.INK_SACK);
							forSaleItem.setDurability((short) 2);
						}
						if(string.equals("LightBlueDye")) {
							forSaleItem = new ItemStack(Material.INK_SACK);
							forSaleItem.setDurability((short) 12);
						}
						if(string.equals("CyanDye")) {
							forSaleItem = new ItemStack(Material.INK_SACK);
							forSaleItem.setDurability((short) 6);
						}
						if(string.equals("BlueDye")) {
							forSaleItem = new ItemStack(Material.INK_SACK);
							forSaleItem.setDurability((short) 4);
						}
						if(string.equals("PurpleDye")) {
							forSaleItem = new ItemStack(Material.INK_SACK);
							forSaleItem.setDurability((short) 5);
						}
						if(string.equals("MagentaDye")) {
							forSaleItem = new ItemStack(Material.INK_SACK);
							forSaleItem.setDurability((short) 13);
						}
						if(string.equals("PinkDye")) {
							forSaleItem = new ItemStack(Material.INK_SACK);
							forSaleItem.setDurability((short) 9);
						}
						if(string.equals("WhiteDye")) {
							forSaleItem = new ItemStack(Material.INK_SACK);
							forSaleItem.setDurability((short) 15);
						}
						if(string.equals("LightGrayDye")) {
							forSaleItem = new ItemStack(Material.INK_SACK);
							forSaleItem.setDurability((short) 7);
						}
						if(string.equals("GrayDye")) {
							forSaleItem = new ItemStack(Material.INK_SACK);
							forSaleItem.setDurability((short) 8);
						}
						if(string.equals("BlackDye")) {
							forSaleItem = new ItemStack(Material.INK_SACK);
							forSaleItem.setDurability((short) 0);
						}
						if(string.equals("BrownDye")) {
							forSaleItem = new ItemStack(Material.INK_SACK);
							forSaleItem.setDurability((short) 3);
						}
						if(string.equals("Prismarine")) {
							forSaleItem = new ItemStack(Material.PRISMARINE_SHARD);
						}
						if(string.equals("NetherBrick")) {
							forSaleItem = new ItemStack(Material.NETHER_BRICK_ITEM);
						}
						
					}
					else {
						forSaleItem = new ItemStack(Material.getMaterial(clickedSign.getLine(1)));
					}
					forSaleItem.setAmount(Integer.parseInt(clickedSign.getLine(2)));
					double cost = Double.parseDouble(clickedSign.getLine(3));
					
					if(plugin.openCartList.containsKey(player)) {
						Cart shoppingCart = plugin.openCartList.get(player);
						shoppingCart.addItemToCart(forSaleItem, cost);
						player.sendMessage("Added " + Integer.toString(forSaleItem.getAmount()) + " " + forSaleItem.getType() + " to cart.");
						player.sendMessage("Cost : " + Double.toString(cost));
					}
					else {
						ArrayList<ItemStack> contents = new ArrayList<ItemStack>();
						contents.add(forSaleItem);
						Cart shoppingCart = new Cart(player, contents, cost);
						plugin.openCartList.put(player, shoppingCart);
						player.sendMessage("Added " + Integer.toString(forSaleItem.getAmount()) + " " + forSaleItem.getType() + " to cart.");
						player.sendMessage("Cost : " + Double.toString(cost));
					}
				}
				
				if(clickedSign.getLine(0).equals(ChatColor.BOLD + "Checkout")) {
					if(plugin.openCartList.containsKey(player)){
						Cart theirCart = plugin.openCartList.get(player);
						Economy economy = plugin.shopperInstance.getEconomy();
						double cost = theirCart.getCost();
						double endPrice = economy.getBalance(player) - cost;
						if(endPrice >= 0){
							economy.withdrawPlayer(player, cost);
							plugin.getLogger().info("" + player.getName() + " bought");
							for(int i=0; i<theirCart.cartContents.size(); i++) {
								ItemStack itemBought = theirCart.getCartContents().get(i);
								if(plugin.shopItemList.containsKey(itemBought.getType())) {
									plugin.shopItemList.get(itemBought.getType()).addBoughtItems(itemBought.getAmount());
								}
								plugin.getLogger().info("" + Integer.toString(itemBought.getAmount()) + " " + itemBought.getType());
							}
							plugin.getLogger().info("for " + Double.toString(cost));
							plugin.openCartList.get(player).checkoutItems();
							plugin.openCartList.remove(player);
							player.sendMessage("Transaction Complete!");
						}
						else {
							player.sendMessage("You cannot afford that purchase!");
						}
					}
				}
				
				if(clickedSign.getLine(0).equals(ChatColor.BOLD + "Sell Shop")) {
					if(blockClicked.getRelative(BlockFace.DOWN).getType() == Material.CHEST) {
						Economy economy = plugin.shopperInstance.getEconomy();
						Block blockBelow = blockClicked.getRelative(BlockFace.DOWN);
						Chest sellChest = (Chest) blockBelow.getState();
						ItemStack[] chestList = sellChest.getInventory().getContents();
						
						Double totalProfit = 0.0;
						boolean itemsLeft = false;
						boolean totalProfitWasModified = false;
						ArrayList<ItemStack> logItemList = new ArrayList<ItemStack>();
						for(int i=0; i<chestList.length; i++) {
							ItemStack item = chestList[i];
							boolean isCharcoal = false;
							boolean isDye = false;
							if(item != null){
								if(item.getType().equals(Material.COAL) && item.getDurability() == 1) {
									isCharcoal = true;
								}
								if(item.getType().equals(Material.INK_SACK) && item.getDurability() != 4) {
									isDye = true;
								}
								if(item.getType().equals(Material.INK_SACK) && item.getDurability() == 3) {
									isDye = false;
								}
								
								if(plugin.shopItemList.containsKey(item.getType()) && !isCharcoal && !isDye) {
									boolean isCocoa = false;
									if(item.getDurability() == 3) {
										item.setType(Material.BOAT_SPRUCE);
										isCocoa = true;
									}
									Double profit = 0.0;
									profit = item.getAmount() * plugin.shopItemList.get(item.getType()).getCurrentPrice() * com.ginger_walnut.sqboosters.SQBoosters.getSellBooster();
									plugin.shopItemList.get(item.getType()).addSoldItems(item.getAmount());
									totalProfit = totalProfit + profit;
									totalProfitWasModified = true;
									boolean addedToStack = false;
									for(int l=0; l< logItemList.size(); l++) {
										if(logItemList.get(l).getType() == item.getType()) {
											int stackAmount = logItemList.get(l).getAmount();
											stackAmount = stackAmount + item.getAmount();
											logItemList.get(l).setAmount(stackAmount);
											addedToStack = true;
										}
									}
									if(isCocoa) {
										item.setType(Material.INK_SACK);
										item.setDurability((short) 3);
									}
									if(addedToStack == false) {
										logItemList.add(item);
									}
									sellChest.getInventory().remove(item);
								}
								else if (item.getType() != Material.AIR) {
									itemsLeft = true;
								}
							}
						}
						if(totalProfitWasModified == true) {
							plugin.getLogger().info(player.getName() + " sold");
							for(int i=0; i<logItemList.size(); i++) {
								ItemStack item = logItemList.get(i);
								plugin.getLogger().info(Integer.toString(item.getAmount()) + " " + item.getType().toString());
							}
							DecimalFormat df = new DecimalFormat("#.##");
							df.format(totalProfit);
							plugin.getLogger().info("for " + Double.toString(totalProfit));
							economy.depositPlayer(player, totalProfit);
							player.sendMessage("Transaction Complete! " + Double.toString(totalProfit) + " made!");
						}
						if(itemsLeft == true) {
							player.sendMessage("Some of your items were not sold!");
						}
						clickedSign.setLine(1, Double.toString(0.0));
						clickedSign.update();
					}
				}
				
				if(player.hasPermission("makeShopSign.allowed")) {
					if(clickedSign.getLine(0).equalsIgnoreCase("[spawnshop]")) {
						clickedSign.setLine(0, ChatColor.BOLD + "Shop");
						clickedSign.update();
						plugin.getLogger().info(player.getName() + "created a Spawn Shop sign. Material: " + clickedSign.getLine(1) + ", Amount: " + clickedSign.getLine(2) + ", Cost: " + clickedSign.getLine(3));
						plugin.getLogger().info("Location(XYZ): " + clickedSign.getLocation().getBlockX() + "," + clickedSign.getLocation().getBlockY() + "," + clickedSign.getLocation().getBlockZ());
					}
				
					if(clickedSign.getLine(0).equalsIgnoreCase("[checkout]")) {
						clickedSign.setLine(0, ChatColor.BOLD + "Checkout");
						clickedSign.update();
						plugin.getLogger().info(player.getName() + "created a Checkout sign.");
						plugin.getLogger().info("Location(XYZ): " + clickedSign.getLocation().getBlockX() + "," + clickedSign.getLocation().getBlockY() + "," + clickedSign.getLocation().getBlockZ());
					}
					
					if(clickedSign.getLine(0).equalsIgnoreCase("[sellshop]")) {
						clickedSign.setLine(0, ChatColor.BOLD + "Sell Shop");
						clickedSign.update();
						plugin.getLogger().info(player.getName() + " created a Sell Shop sign.");
						plugin.getLogger().info("Location(XYZ): " + clickedSign.getLocation().getBlockX() + "," + clickedSign.getLocation().getBlockY() + "," + clickedSign.getLocation().getBlockZ());
					}
				}
			}
		}
			if(e.getAction().equals(Action.LEFT_CLICK_BLOCK)) {
				if(blockClicked.getType().equals(Material.SIGN_POST) || blockClicked.getType().equals(Material.WALL_SIGN)) {
				Sign clickedSign = (Sign) blockClicked.getState();
				if(clickedSign.getLine(0).equals(ChatColor.BOLD + "Sell Shop")) {
					if(blockClicked.getRelative(BlockFace.DOWN).getType() == Material.CHEST) {
						Block blockBelow = blockClicked.getRelative(BlockFace.DOWN);
						Chest sellChest = (Chest) blockBelow.getState();
						ItemStack[] chestList = sellChest.getInventory().getContents();
						
						Double totalProfit = 0.0;
						boolean itemsLeft = false;
						boolean totalProfitWasModified = false;
						ArrayList<ItemStack> logItemList = new ArrayList<ItemStack>();
						for(int i=0; i<chestList.length; i++) {
							ItemStack item = chestList[i];
							boolean isCharcoal = false;
							boolean isLapis = false;
							if(item != null){
								if(item.getType().equals(Material.COAL) && item.getDurability() == 1) {
									isCharcoal = true;
								}
								if(item.getType().equals(Material.INK_SACK) && item.getDurability() == 4) {
									isLapis = true;
								}
								
								if(isLapis || plugin.shopItemList.containsKey(item.getType()) && !isCharcoal) {
									Double profit = 0.0;
									profit = item.getAmount() * plugin.shopItemList.get(item.getType()).getCurrentPrice() * com.ginger_walnut.sqboosters.SQBoosters.getSellBooster();
									totalProfit = totalProfit + profit;
									totalProfitWasModified = true;
								}
								
							}
						}
						
							clickedSign.setLine(1, Double.toString(totalProfit));
							clickedSign.update();
						

					}
				}
				
				if(clickedSign.getLine(0).equals(ChatColor.BOLD + "Shop")) {
					updateShopSign(clickedSign);
				}
				
				if(player.hasPermission("makeShopSign.allowed")) {
					if(blockClicked.getType() == Material.WALL_SIGN || blockClicked.getType() == Material.SIGN_POST) {
						clickedSign = (Sign) blockClicked.getState();
						if(clickedSign.getLine(0).equals(ChatColor.BOLD + "Shop")) {
							if(plugin.modificationNumbers.containsKey(player)) {
								int price = plugin.modificationNumbers.get(player);
								int amount = Integer.parseInt(clickedSign.getLine(2));
								int newprice = price * amount;
								clickedSign.setLine(3, Integer.toString(newprice));
								plugin.getLogger().info(player.getName() + " modified a Sell Shop sign.");
								plugin.getLogger().info("New price per item set: " + Integer.toString(newprice));
								clickedSign.update();
							}
						}
					}
				}
			}
		}
			
	}
	}
}
