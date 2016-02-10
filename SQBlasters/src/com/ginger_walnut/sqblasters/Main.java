/*
 * Author: Ginger_Walnut
 * Description: A spigot plugin for minecraf that adds blasters (bows that fire instantly on right click)
 * Minecraft Version: 1.8
 * 
 */

package com.ginger_walnut.sqblasters;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin{

	public final Logger logger = Logger.getLogger("Minecraft");
	public static Main plugin;
	
	private static Plugin pluginGrav;
	private static FileConfiguration pluginConfig;
	
	@Override
	public void onDisable() {
		
		pluginGrav = null;
		pluginConfig = null;
		
		PluginDescriptionFile pdfFile = this.getDescription();
		this.logger.info(pdfFile.getName() + " has been disabled!");
		
	}
	
	@Override
	public void onEnable() {

		pluginGrav = this;
		pluginConfig = getConfig();
		
		PluginDescriptionFile pdfFile = this.getDescription();
		this.logger.info(pdfFile.getName() + " has been enabled!");
		
		this.getServer().getPluginManager().registerEvents(new Events(), this);
		
		getConfig().options().copyDefaults(true);
		saveConfig();
		
		ItemStack blaster = new ItemStack(Material.BOW);
		ItemMeta blasterMeta = blaster.getItemMeta();
		List<String> lore = new ArrayList<String>();
		lore.add("§6Type: §fnew blaster");
		blasterMeta.setLore(lore);
		blaster.setItemMeta(blasterMeta);
		
		ShapedRecipe blasterRecpie = new ShapedRecipe(blaster);
		
		blasterRecpie.shape(" g ","iir","ib ");
		
		blasterRecpie.setIngredient('g', Material.GLASS);
		blasterRecpie.setIngredient('i', Material.IRON_INGOT);
		blasterRecpie.setIngredient('b', Material.STONE_BUTTON);
		blasterRecpie.setIngredient('r', Material.EMERALD);
		
		getServer().addRecipe(blasterRecpie);
		
		(new RepeatingTasks()).run();
		
	}
	
	public static Plugin getPluginMain() {
		
		return pluginGrav;
		
	}
	
	public static FileConfiguration getPluginConfig() {
		
		return pluginConfig;
		
	}
	
	public static void setDoneReloading (Player player, int ammoPerPack) {
		
		try {
			
			ItemMeta itemMeta = player.getItemInHand().getItemMeta();
		
			if (itemMeta.getDisplayName().equals(ChatColor.GREEN + "Reloading...")) {
			
				itemMeta.setDisplayName("Blaster " + ChatColor.GOLD +  "(" + ammoPerPack + "/" + ammoPerPack + ")");
			
				player.getItemInHand().setItemMeta(itemMeta);
			
			}
		
		}
		catch (Exception e) {
		}
		
	}
	
	private static void printHelp (Player player) {
		
		player.sendMessage(ChatColor.GOLD + "-----------------------------------------------------");
		player.sendMessage(ChatColor.GOLD + "/blaster help" + ChatColor.BLUE + " - Shows this");
		player.sendMessage(ChatColor.GOLD + "/blaster create" + ChatColor.BLUE + " - Creates a new blaster ");
		player.sendMessage(ChatColor.GOLD + "/blaster guide" + ChatColor.BLUE + " - Displays a guide for SQBlasters");
		player.sendMessage(ChatColor.GOLD + "/blaster recipe" + ChatColor.BLUE + " - Displays the blaster crafting recipe");
		player.sendMessage(ChatColor.GOLD + "/blaster spawn" + ChatColor.BLUE + " - Spawns in a blaster - Moderator only!");
		player.sendMessage(ChatColor.GOLD + "-----------------------------------------------------");
		
	}
	
	private static void printGuide (Player player) {
		
		player.sendMessage(ChatColor.GOLD + "-----------------------------------------------------");
		player.sendMessage(ChatColor.BLUE + "To aquire a blaster, the first step that you need to follow is to craft a blaster with the recipe that you can find with the command /blaster recipe"); 
		player.sendMessage("");
		player.sendMessage(ChatColor.BLUE + "The next step is to type /blaster create and then pick a type of blaster.  Each of the blaster have different stats so pick the one that is best suited for your purpose.");
		player.sendMessage("");
		player.sendMessage(ChatColor.BLUE + "After you select your blaster type, you can fire by right clicking with the blaster in your hand. If the blaster is automatic, you just need to press right click once to toggle on and off automatic shooting. You can left click to zoom in with your blaster.  All of the enchantments except punch work and unbreaking has no use becuase blasters do not loss durability.");
		player.sendMessage(ChatColor.GOLD + "-----------------------------------------------------");
				
	}
	
	public static ItemStack createNewBlaster (String type, Boolean contraband, ItemMeta metaData) {
		
		ItemStack blaster = new ItemStack(Material.BOW);
		
		List<String> lore = new ArrayList<String>();
		ItemMeta meta;
		
		double bowPower;
		
		double bowDamagePrecentIncrease;
		
		if (metaData != null) {
			
			meta = metaData;
			
			bowPower = meta.getEnchantLevel(Enchantment.ARROW_DAMAGE);
			
			bowDamagePrecentIncrease = 25 * bowPower; 
			bowDamagePrecentIncrease = bowDamagePrecentIncrease / 100;
			bowDamagePrecentIncrease = bowDamagePrecentIncrease + 1;
			
			blaster.addEnchantments(metaData.getEnchants());
			
		} else {
			
			meta = (ItemMeta) blaster.getItemMeta();
			
			bowDamagePrecentIncrease = 1;
			
		}
		
		double bowDamage = (getPluginConfig().getDouble(type + ".damage") * bowDamagePrecentIncrease);
		int ammo = 0;
		int ammoPerPack = getPluginConfig().getInt(type + ".ammo per pack");
		int reloadTime = getPluginConfig().getInt(type + ".reload time");
		int fireTime = getPluginConfig().getInt(type + ".fire time");
		
		lore.add(ChatColor.GOLD + "Type: " + ChatColor.WHITE + type);
		lore.add(ChatColor.GOLD + "Ammo Per Pack: " + ChatColor.WHITE + ammoPerPack);
		lore.add(ChatColor.GOLD + "Ammo: " + ChatColor.WHITE + ammo);
		lore.add(ChatColor.GOLD + "Damage: " + ChatColor.WHITE + bowDamage);
		lore.add(ChatColor.GOLD + "Reload Time: " + ChatColor.WHITE + reloadTime);
		lore.add(ChatColor.GOLD + "Fire Time: " + ChatColor.WHITE + fireTime);
		
		if (contraband) {
			
			lore.add(ChatColor.RED + "" + ChatColor.MAGIC + "Contraband");
			
		}
		
		meta.setLore(lore);
		meta.setDisplayName("Blaster " + ChatColor.GOLD +  "(" + ammo + "/" + ammoPerPack + ")");
		blaster.setItemMeta(meta);
		
		return blaster;
		
	}
	
	public static boolean isEmpty (Inventory inventory) {
		
		boolean isEmpty = false;
		
		for (int i = 0; i < inventory.getSize(); i ++) {
			
			if (inventory.getContents()[i] == null) {
				
				isEmpty = true;
				
			}
			
		}
		
		return isEmpty;
		
	}
	
	private static void showRecipie (Player player) {
		
		Inventory inventory = Bukkit.createInventory(player, InventoryType.WORKBENCH, ChatColor.GOLD + "Blaster Recipe");
	
		ItemStack blaster = new ItemStack(Material.BOW);
		List<String> blasterLore = new ArrayList<String>();
		ItemMeta blasterMeta = blaster.getItemMeta();
		
		blasterLore.add(ChatColor.GOLD + "Type: " + ChatColor.WHITE + "new blaster");
		blasterLore.add(ChatColor.RED + "" + ChatColor.MAGIC + "Contraband");
		
		blasterMeta.setLore(blasterLore);		
		blaster.setItemMeta(blasterMeta);
		
		ItemStack glass = new ItemStack(Material.GLASS);
		
		List<String> glassLore = new ArrayList<String>();
		ItemMeta glassMeta = glass.getItemMeta();
		
		glassLore.add(ChatColor.RED + "" + ChatColor.MAGIC + "Contraband");
		
		glassMeta.setLore(glassLore);
		glass.setItemMeta(glassMeta);
		
		ItemStack iron = new ItemStack(Material.IRON_INGOT);
		
		List<String> ironLore = new ArrayList<String>();
		ItemMeta ironMeta = iron.getItemMeta();
		
		ironLore.add(ChatColor.RED + "" + ChatColor.MAGIC + "Contraband");
		
		ironMeta.setLore(ironLore);
		iron.setItemMeta(ironMeta);
		
		ItemStack emerald = new ItemStack(Material.EMERALD);
		
		List<String> emeraldLore = new ArrayList<String>();
		ItemMeta emeraldMeta = emerald.getItemMeta();
		
		emeraldLore.add(ChatColor.RED + "" + ChatColor.MAGIC + "Contraband");
		
		emeraldMeta.setLore(emeraldLore);
		emerald.setItemMeta(emeraldMeta);
		
		ItemStack button = new ItemStack(Material.STONE_BUTTON);
		
		List<String> buttonLore = new ArrayList<String>();
		ItemMeta buttonMeta = button.getItemMeta();
		
		buttonLore.add(ChatColor.RED + "" + ChatColor.MAGIC + "Contraband");
		
		buttonMeta.setLore(buttonLore);
		button.setItemMeta(buttonMeta);
		
		inventory.setItem(2, glass);
		inventory.setItem(4, iron);
		inventory.setItem(5, iron);
		inventory.setItem(6, emerald);
		inventory.setItem(7, iron);
		inventory.setItem(8, button);
		inventory.setItem(0, blaster);
		
		player.closeInventory();
		player.openInventory(inventory);
		
	}
	
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args) {
		
		Player player = (Player) sender;
		
		if (commandLabel.equalsIgnoreCase("blaster")) {
		
			if (args.length == 0) {
				
				printHelp(player);
				
			} else if (args.length == 1) {
				
				if (args[0].equalsIgnoreCase("help")) {
					
					printHelp(player);
					
				} else if (args[0].equalsIgnoreCase("create")) {
					
					ItemStack handItem = player.getItemInHand();
					
					if (handItem.getType().equals(Material.BOW) && handItem.getItemMeta().hasLore()) {
						
						List<String> lore = handItem.getItemMeta().getLore();
						
						String type = lore.get(0).substring(10);
						
						if (type.equals("new blaster")) {
							
							player.setItemInHand(new ItemStack(Material.AIR));
							player.closeInventory();
							
							int blasterTypesAmount = getPluginConfig().getRoot().getKeys(false).size();
							
							Inventory inventory; 
							
							if (blasterTypesAmount <= 9) {
								
								inventory = Bukkit.createInventory(player, 9, ChatColor.GOLD + "Blaster Selection");
								
							} else if (blasterTypesAmount > 9 && blasterTypesAmount <= 18) {
								
								inventory = Bukkit.createInventory(player, 18, ChatColor.GOLD + "Blaster Selection");
								
							} else {
								
								inventory = Bukkit.createInventory(player, 27, ChatColor.GOLD + "Blaster Selection");
								
							}
							
							List<String> blasterTypes = new ArrayList<String>();
							
							blasterTypes.addAll(Main.getPluginConfig().getRoot().getKeys(false));
							
							for (int i = 0; i < blasterTypesAmount; i ++) {
								
								inventory.setItem(i, createNewBlaster(blasterTypes.get(i), true, handItem.getItemMeta()));
							
							}
							
							player.openInventory(inventory);
						
						} else {
							
							sender.sendMessage(ChatColor.RED + "You must be holding a blaster with the type of new blaster to create a new blaster");
							
						}
						
					} else {
						
						sender.sendMessage(ChatColor.RED + "You must be holding a blaster with the type of new blaster to create a new blaster");
						
					}
					
				} else if (args[0].equalsIgnoreCase("guide")) {
					
					printGuide(player);
					
				} else if (args[0].equalsIgnoreCase("recipe")) {
					
					showRecipie(player);
					
				} else if (args[0].equalsIgnoreCase("spawn")) {
					
					if (player.hasPermission("SQBlasters.spawnBlaster")) {
						
						player.closeInventory();
						
						int blasterTypesAmount = getPluginConfig().getRoot().getKeys(false).size();
						
						Inventory inventory; 
						
						if (blasterTypesAmount <= 9) {
							
							inventory = Bukkit.createInventory(player, 9, ChatColor.GOLD + "Blaster Selection");
							
						} else if (blasterTypesAmount > 9 && blasterTypesAmount <= 18) {
							
							inventory = Bukkit.createInventory(player, 18, ChatColor.GOLD + "Blaster Selection");
							
						} else {
							
							inventory = Bukkit.createInventory(player, 27, ChatColor.GOLD + "Blaster Selection");
							
						}
						
						List<String> blasterTypes = new ArrayList<String>();
						
						blasterTypes.addAll(Main.getPluginConfig().getRoot().getKeys(false));
						
						for (int i = 0; i < blasterTypesAmount; i ++) {
							
							inventory.setItem(i, createNewBlaster(blasterTypes.get(i), true, null));
						
						}
						
						player.openInventory(inventory);
						
					} else {
						
						player.sendMessage(ChatColor.RED + "You do not have permission to spawn in a blaster");
						
					}
					
				} else {
				
					
					sender.sendMessage(ChatColor.RED + "Use /blaster help for help on how to use SQBlasters");
					
				}
				
			} else {
				
				sender.sendMessage(ChatColor.RED + "Use /blaster help for help on how to use SQBlasters");
				
			}
			
		}
		
		return false;
		
	}
	
}
	
	
