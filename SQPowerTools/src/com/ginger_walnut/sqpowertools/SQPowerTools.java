package com.ginger_walnut.sqpowertools;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

import net.md_5.bungee.api.ChatColor;
import net.minecraft.server.v1_9_R1.NBTTagCompound;
import net.minecraft.server.v1_9_R1.NBTTagFloat;
import net.minecraft.server.v1_9_R1.NBTTagInt;
import net.minecraft.server.v1_9_R1.NBTTagList;
import net.minecraft.server.v1_9_R1.NBTTagString;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.craftbukkit.v1_9_R1.inventory.CraftItemStack;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.java.JavaPlugin;

public class SQPowerTools extends JavaPlugin{

	public final Logger logger = Logger.getLogger("Minecraft");
	public static SQPowerTools plugin;
	
	public static List<String> powerToolNames = new ArrayList<String>();
	public static List<Material> powerToolMaterials = new ArrayList<Material>();
	
	public static List<String> powerToolConfigNames = new ArrayList<String>();
	
	public static List<Boolean> powerToolHasRecipes = new ArrayList<Boolean>();
	
	public static List<List<String>> powerToolAttributes = new ArrayList<List<String>>();
	public static List<List<Float>> powerToolAmounts = new ArrayList<List<Float>>();
	public static List<List<String>> powerToolSlots = new ArrayList<List<String>>();
	public static List<List<Integer>> powerToolOperations = new ArrayList<List<Integer>>();
	
	public static List<Short> powerToolDurabilities = new ArrayList<Short>();
		
	public static List<Integer> powerToolEnergies = new ArrayList<Integer>();
	public static List<Integer> powerToolEnergyPerUse = new ArrayList<Integer>();
	
	public static List<Map<Enchantment, Integer>> powerToolEnchants = new ArrayList<Map<Enchantment, Integer>>();
	
	public static List<List<String>> powerToolLores = new ArrayList<List<String>>();
	
	public static List<List<String>> powerToolModNames = new ArrayList<List<String>>();
	public static List<List<Integer>> powerToolModDatas = new ArrayList<List<Integer>>();
	public static List<List<Material>> powerToolModMaterials = new ArrayList<List<Material>>();
	public static List<List<Integer>> powerToolModItemNumbers = new ArrayList<List<Integer>>();
	public static List<List<Integer>> powerToolModLevels = new ArrayList<List<Integer>>();
	
	public static List<List<List<String>>> powerToolModAttributes = new ArrayList<List<List<String>>>();
	public static List<List<List<Float>>> powerToolModAmounts = new ArrayList<List<List<Float>>>();
	public static List<List<List<String>>> powerToolModSlots = new ArrayList<List<List<String>>>();
	public static List<List<List<Integer>>> powerToolModOperations = new ArrayList<List<List<Integer>>>();
	
	public static List<List<Map<Enchantment, Integer>>> powerToolModEnchants = new ArrayList<List<Map<Enchantment, Integer>>>();
	
	public static List<List<Integer>> powerToolModEnergies = new ArrayList<List<Integer>>();
	
	public static List<List<List<String>>> powerToolModCannotCombines = new ArrayList<List<List<String>>>();
	
	public static List<Integer> powerToolMaxLevels = new ArrayList<Integer>();
	
	public static List<Location> chargerLocations = new ArrayList<Location>();
	
	public static FileConfiguration config = null;
	
	boolean enabled = false;
	
	@Override
	public void onDisable() {
		
		PluginDescriptionFile pdfFile = this.getDescription();
		this.logger.info(pdfFile.getName() + " has been disabled!");
		
		saveDefaultConfig();
		
	}
	
	@SuppressWarnings("deprecation")
	@Override
	public void onEnable() {
		
		plugin = this;
		
		PluginDescriptionFile pdfFile = this.getDescription();
		this.logger.info(pdfFile.getName() + " has been enabled!");
		
		if (!enabled) {
			
			this.getServer().getPluginManager().registerEvents(new Events(), this);
			
		}
		
		enabled = true;
		
		if (!new File(this.getDataFolder(), "config.yml").exists()) {
			
			saveDefaultConfig();
			saveConfig();
			
		}

		config = getConfig();
		
		powerToolNames = new ArrayList<String>();
		powerToolMaterials = new ArrayList<Material>();
		
		powerToolConfigNames = new ArrayList<String>();
		
		powerToolHasRecipes = new ArrayList<Boolean>();
		
		powerToolAttributes = new ArrayList<List<String>>();
		powerToolAmounts = new ArrayList<List<Float>>();
		powerToolSlots = new ArrayList<List<String>>();
		powerToolOperations = new ArrayList<List<Integer>>();
		
		powerToolDurabilities = new ArrayList<Short>();
			
		powerToolEnergies = new ArrayList<Integer>();
		powerToolEnergyPerUse = new ArrayList<Integer>();
		
		powerToolEnchants = new ArrayList<Map<Enchantment, Integer>>();
		
		powerToolLores = new ArrayList<List<String>>();
		
		powerToolModNames = new ArrayList<List<String>>();
		powerToolModDatas = new ArrayList<List<Integer>>();
		powerToolModMaterials = new ArrayList<List<Material>>();
		powerToolModItemNumbers = new ArrayList<List<Integer>>();
		powerToolModLevels = new ArrayList<List<Integer>>();
		
		powerToolModAttributes = new ArrayList<List<List<String>>>();
		powerToolModAmounts = new ArrayList<List<List<Float>>>();
		powerToolModSlots = new ArrayList<List<List<String>>>();
		powerToolModOperations = new ArrayList<List<List<Integer>>>();
		
		powerToolModEnchants = new ArrayList<List<Map<Enchantment, Integer>>>();
		
		powerToolModEnergies = new ArrayList<List<Integer>>();
		
		powerToolModCannotCombines = new ArrayList<List<List<String>>>();
		
		powerToolMaxLevels = new ArrayList<Integer>();
		
		List<String> powerTools = new ArrayList<String>();
		
		powerTools.addAll(config.getConfigurationSection("power tools").getKeys(false));
		
		for (String powerTool : powerTools) {
			
			powerToolConfigNames.add(powerTool);
			
			powerToolMaterials.add(Material.getMaterial(config.getInt("power tools." + powerTool + ".item")));
			powerToolDurabilities.add((short) config.getInt("power tools." + powerTool + ".durability"));
			powerToolNames.add(config.getString("power tools." + powerTool + ".name"));
			
			powerToolEnergies.add(config.getInt("power tools." + powerTool + ".max energy"));
			powerToolEnergyPerUse.add(config.getInt("power tools." + powerTool + ".energy per use"));
			
			if (config.contains("power tools." + powerTool + ".enchants")) {
				
				HashMap<Enchantment, Integer> enchants = new HashMap<Enchantment, Integer>();
				
				System.out.print(config.getConfigurationSection("power tools." + powerTool + ".enchants").getKeys(false));
				
				for (String enchantment : config.getConfigurationSection("power tools." + powerTool + ".enchants").getKeys(false)) {
					
					enchants.put(Enchantment.getById(config.getInt("power tools." + powerTool + ".enchants." + enchantment + ".enchant")), config.getInt("power tools." + powerTool + ".enchants." + enchantment + ".level"));
					
				}
				
				powerToolEnchants.add(enchants);
				
			} else {
				
				powerToolEnchants.add(new HashMap<Enchantment, Integer>());
				
			}
			
			if (config.contains("power tools." + powerTool + ".attributes")) {
				
				List<String> attributes = new ArrayList<String>();
				List<Float> amounts = new ArrayList<Float>();
				List<String> slots = new ArrayList<String>();
				List<Integer> operations = new ArrayList<Integer>();
				
				for (String attribute : config.getConfigurationSection("power tools." + powerTool + ".attributes").getKeys(false)) {
					
					attributes.add(config.getString("power tools." + powerTool + ".attributes." + attribute + ".attribute"));
					amounts.add((float) config.getDouble("power tools." + powerTool + ".attributes." + attribute + ".amount"));
					slots.add(config.getString("power tools." + powerTool + ".attributes." + attribute + ".slot"));
					operations.add(config.getInt("power tools." + powerTool + ".attributes." + attribute + ".operation"));
					
				}
				
				powerToolAttributes.add(attributes);
				powerToolAmounts.add(amounts);
				powerToolSlots.add(slots);
				powerToolOperations.add(operations);
				
			} else {
				
				powerToolAttributes.add(new ArrayList<String>());
				powerToolAmounts.add(new ArrayList<Float>());
				powerToolSlots.add(new ArrayList<String>());
				powerToolOperations.add(new ArrayList<Integer>());
				
			}
			
			if (config.contains("power tools." + powerTool + ".lore")) {
				
				powerToolLores.add(config.getStringList("power tools." + powerTool + ".lore"));
				
			} else {
				
				powerToolLores.add(new ArrayList<String>());
				
			}
			
			if (config.contains("power tools." + powerTool + ".mods")) {
				
				List<String> names = new ArrayList<String>();
				List<Integer> datas = new ArrayList<Integer>();
				List<Material> materials = new ArrayList<Material>();
				List<Integer> itemNumbers = new ArrayList<Integer>();
				List<Integer> levels = new ArrayList<Integer>();
				
				List<List<String>> modAttributes = new ArrayList<List<String>>();
				List<List<Float>> modAmounts = new ArrayList<List<Float>>();
				List<List<Integer>> modOperations = new ArrayList<List<Integer>>();
				List<List<String>> modSlots = new ArrayList<List<String>>();
				
				List<Map<Enchantment, Integer>> modEnchants = new ArrayList<Map<Enchantment, Integer>>();
				
				List<Integer> modEnergies = new ArrayList<Integer>();
				
				List<List<String>> modCannotCombines = new ArrayList<List<String>>();
				
				for (String mod : config.getConfigurationSection("power tools." + powerTool + ".mods").getKeys(false)) {
					
					names.add(config.getString("power tools." + powerTool + ".mods." + mod + ".name"));
					datas.add(config.getInt("power tools." + powerTool + ".mods." + mod + ".data"));
					materials.add(Material.getMaterial(config.getInt("power tools." + powerTool + ".mods." + mod + ".item")));
					itemNumbers.add(config.getInt("power tools." + powerTool + ".mods." + mod + ".amount"));
					levels.add(config.getInt("power tools." + powerTool + ".mods." + mod + ".levels"));
					
					if (config.contains("power tools." + powerTool + ".mods." + mod + ".effects.attributes")) {
						
						List<String> attributes = new ArrayList<String>();
						List<Float> amounts = new ArrayList<Float>();
						List<Integer> operations = new ArrayList<Integer>();
						List<String> slots = new ArrayList<String>();
						
						for (String attribute : config.getConfigurationSection("power tools." + powerTool + ".mods." + mod + ".effects.attributes").getKeys(false)) {
							
							attributes.add(config.getString("power tools." + powerTool + ".mods." + mod + ".effects.attributes." + attribute + ".attribute"));
							amounts.add((float) config.getDouble("power tools." + powerTool + ".mods." + mod + ".effects.attributes." + attribute + ".amount"));
							operations.add(config.getInt("power tools." + powerTool + ".mods." + mod + ".effects.attributes." + attribute + ".operation"));
							slots.add(config.getString("power tools." + powerTool + ".mods." + mod + ".effects.attributes." + attribute + ".slot"));
							
						}
						
						modAttributes.add(attributes);
						modAmounts.add(amounts);
						modOperations.add(operations);
						modSlots.add(slots);
						
					} else {
						
						modAttributes.add(new ArrayList<String>());
						modAmounts.add(new ArrayList<Float>());
						modOperations.add(new ArrayList<Integer>());
						modSlots.add(new ArrayList<String>());
						
					}
					
					if (config.contains("power tools." + powerTool + ".mods." + mod + ".effects.enchants")) {
						
						Map<Enchantment, Integer> enchants = new HashMap<Enchantment, Integer>();
						
						for (String enchant : config.getConfigurationSection("power tools." + powerTool + ".mods." + mod + ".effects.enchants").getKeys(false)) {
							
							enchants.put(Enchantment.getById(config.getInt("power tools." + powerTool + ".mods." + mod + ".effects.enchants." + enchant + ".enchant")), config.getInt("power tools." + powerTool + ".mods." + mod + ".effects.enchants." + enchant + ".level"));
							
						}
						
						modEnchants.add(enchants);
						
					} else {
						
						modEnchants.add(new HashMap<Enchantment, Integer>());
						
					}
					
					if (config.contains("power tools." + powerTool + ".mods." + mod + ".effects.energy")) {
						
						modEnergies.add(config.getInt("power tools." + powerTool + ".mods." + mod + ".effects.energy"));
						
					} else {
						
						modEnergies.add(0);
						
					}
					
					if (config.contains("power tools." + powerTool + ".mods." + mod + ".cannot combine with")) {
						
						modCannotCombines.add(config.getStringList("power tools." + powerTool + ".mods." + mod + ".cannot combine with"));
						
					} else {
						
						modCannotCombines.add(new ArrayList<String>());
						
					}

				}
				
				powerToolModNames.add(names);
				powerToolModDatas.add(datas);
				powerToolModMaterials.add(materials);
				powerToolModItemNumbers.add(itemNumbers);
				powerToolModLevels.add(levels);
				
				powerToolModAttributes.add(modAttributes);
				powerToolModAmounts.add(modAmounts);
				powerToolModOperations.add(modOperations);
				powerToolModSlots.add(modSlots);
				
				powerToolModEnchants.add(modEnchants);
				
				powerToolModEnergies.add(modEnergies);
				
				powerToolModCannotCombines.add(modCannotCombines);
				
			} else {
				
				powerToolModNames.add(new ArrayList<String>());
				powerToolModMaterials.add(new ArrayList<Material>());
				powerToolModItemNumbers.add(new ArrayList<Integer>());
				powerToolModLevels.add(new ArrayList<Integer>());
				
				powerToolModAttributes.add(new ArrayList<List<String>>());
				powerToolModAmounts.add(new ArrayList<List<Float>>());
				powerToolModOperations.add(new ArrayList<List<Integer>>());
				powerToolModSlots.add(new ArrayList<List<String>>());	
				
				powerToolModEnchants.add(new ArrayList<Map<Enchantment, Integer>>());
				
				powerToolModEnergies.add(new ArrayList<Integer>());
				
				powerToolModCannotCombines.add(new ArrayList<List<String>>());
				
			}
			
			powerToolMaxLevels.add(config.getInt("power tools." + powerTool + ".max modifiers"));
			
			if (config.contains("power tools." + powerTool + ".recipe")) {
				
				ShapedRecipe recipe = new ShapedRecipe(getPowerTool(config.getString("power tools." + powerTool + ".name")));
				
				if (config.contains("power tools." + powerTool + ".recipe.line2")) {
					
					if (config.contains("power tools." + powerTool + ".recipe.line3")) {
						
						recipe.shape(config.getString("power tools." + powerTool + ".recipe.line1"), config.getString("power tools." + powerTool + ".recipe.line2"), config.getString("power tools." + powerTool + ".recipe.line3"));
						
					} else {
						
						recipe.shape(config.getString("power tools." + powerTool + ".recipe.line1"), config.getString("power tools." + powerTool + ".recipe.line2"));
						
					}
					
				} else {
					
					recipe.shape(config.getString("power tools." + powerTool + ".recipe.line1"));
					
				}
				
				List<String> ingredients = new ArrayList<String>();
				
				ingredients.addAll(config.getConfigurationSection("power tools." + powerTool + ".recipe.ingredients").getKeys(false));
				
				for (int i = 0; i < ingredients.size(); i ++) {
					
					System.out.print(powerTool);
					System.out.print(ingredients.get(i));
					System.out.print(config.getInt("power tools." + powerTool + ".recipe.ingredients." + ingredients.get(i)));
					
					recipe.setIngredient(ingredients.get(i).toCharArray()[0], Material.getMaterial(config.getInt("power tools." + powerTool + ".recipe.ingredients." + ingredients.get(i))));
					
				}
				
				getServer().addRecipe(recipe);
				
				powerToolHasRecipes.add(true);
				
			} else {
				
				powerToolHasRecipes.add(false);
				
			}

		}
		
		(new ChargerTask()).run();
		
	}
	
	public static Plugin getPluginMain() {
		
		return plugin;
		
	}

	public static void sendHelp(CommandSender sender) {
		
		sender.sendMessage(ChatColor.GOLD + "-----------------------------------------------------");
		sender.sendMessage(ChatColor.GOLD + "/sqpowertools help" + ChatColor.BLUE + " - Shows this");
		sender.sendMessage(ChatColor.GOLD + "/sqpowertools guide" + ChatColor.BLUE + " - Displays a guide for SQPowerTools");
		sender.sendMessage(ChatColor.GOLD + "/sqpowertools recipes" + ChatColor.BLUE + " - Displays power tool recipes");
		sender.sendMessage(ChatColor.GOLD + "/sqpowertools mods" + ChatColor.BLUE + " - Displays power tool mods");
		sender.sendMessage(ChatColor.GOLD + "/sqpowertools fuel" + ChatColor.BLUE + " - Displays the charger fuel");
		sender.sendMessage(ChatColor.GOLD + "-----------------------------------------------------");
		
	}
	
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args) {
		
		if (commandLabel.equals("sqpowertools") || commandLabel.equals("sqpt") || commandLabel.equals("sqpowertool")) {
			
			if (args.length == 0) {
				
				sendHelp(sender);
				
			} else if (args.length == 1) {
				
				if (args[0].equals("help")) {
					
					sendHelp(sender);
					
				} else if (args[0].equals("guide")) {
					
					sender.sendMessage(ChatColor.GOLD + "-----------------------------------------------------");
					sender.sendMessage(ChatColor.BLUE + "SQPowerTools is a plugin that allows you to create power tools that are unbreakable.  These tools function similar to normal tools, but they must occasionally be recharged.  These tools have custom stats and textures and they can be enchanted using modifiers.");
					sender.sendMessage("");
					sender.sendMessage(ChatColor.BLUE + "Power tools can be crafted on a crafting table. Use /sqpowertools recipes to see a list of tools. Click on one of these tools to see its recipe.");
					sender.sendMessage("");
					sender.sendMessage(ChatColor.BLUE + "When your power tools run low on energy, you can recharge them with a charger. To create a charger, simply place a sign with [charger] on a furnace and right click it. Use /sqpowertools fuel to see what fuel is required. Place the fuel in the fuel slot and the power tool in the top slot of the furnace and right click the charger sign to activate the charger. The power tool will move to the right furnace slot when it is charged.");
					sender.sendMessage("");
					sender.sendMessage(ChatColor.BLUE + "Type /sqpowertools mods to see possible modifications and restrictions.  To modify a power tool, you must first create a modifier by right clicking on a [modifier] sign attached to an iron block. Next, right click on the modifier, place the power tool in the left slot, and place the appropriate modifier material in the middle slot. You may need to click the refresh button to complete this process.  Once the updated power tool appears, you may click it to add it to you inventory.");
					sender.sendMessage(ChatColor.GOLD + "-----------------------------------------------------");
					
				} else if (args[0].equals("recipes") || args[0].equals("recipe")) {
					
					if (sender instanceof Player) {
						
						Player player = (Player) sender;
						
						Inventory inventory = Bukkit.createInventory(player, 54, "Power Tool Recipes");
								
						for (int i = 0; i < powerToolNames.size(); i ++) {
							
							if (powerToolHasRecipes.get(i)) {
								
								ItemStack powerTool = getPowerTool(powerToolNames.get(i));
								
								ItemMeta itemMeta = powerTool.getItemMeta();
								
								List<String> lore = itemMeta.getLore();
								
								lore.add(ChatColor.RED + "" + ChatColor.MAGIC + "Contraband");
								
								itemMeta.setLore(lore);
								
								powerTool.setItemMeta(itemMeta);
								
								inventory.addItem(powerTool);
								
							}
							
							player.openInventory(inventory);

						}
						
					} else {
						
						sender.sendMessage(ChatColor.RED + "You must be a player to use this command");
						
					}					
					
				} else if (args[0].equals("fuel")) {
					
					if (sender instanceof Player) {
						
						Player player = (Player) sender;
						
						Inventory inventory = Bukkit.createInventory(player, 54, "Charger Fuel");
							
						ItemStack fuel = new ItemStack(Material.getMaterial(config.getInt("charger.fuel.fuelID")));
								
						ItemMeta itemMeta = fuel.getItemMeta();
						
						List<String> lore = new ArrayList<String>();
								
						lore.add(ChatColor.RED + "" + ChatColor.MAGIC + "Contraband");
								
						itemMeta.setLore(lore);
								
						fuel.setItemMeta(itemMeta);
								
						inventory.addItem(fuel);
								
						player.openInventory(inventory);
					
					} else {
						
						sender.sendMessage(ChatColor.RED + "You must be a player to use this command");
						
					}	
					
				} else if (args[0].equals("mods") || args[0].equals("mod")) {
					
					if (sender instanceof Player) {
						
						Player player = (Player) sender;
						
						Inventory inventory = Bukkit.createInventory(player, 54, "Power Tool Mods");
								
						for (int i = 0; i < powerToolNames.size(); i ++) {
							
							if (powerToolHasRecipes.get(i)) {
								
								ItemStack powerTool = getPowerTool(powerToolNames.get(i));
								
								ItemMeta itemMeta = powerTool.getItemMeta();
								
								List<String> lore = itemMeta.getLore();
								
								lore.add(ChatColor.RED + "" + ChatColor.MAGIC + "Contraband");
								
								itemMeta.setLore(lore);
								
								powerTool.setItemMeta(itemMeta);
								
								inventory.addItem(powerTool);
								
							}
							
							player.openInventory(inventory);

						}
						
					} else {
						
						sender.sendMessage(ChatColor.RED + "You must be a player to use this command");
						
					}	
					
				} else if (args[0].equals("reload") && (sender instanceof ConsoleCommandSender || sender.hasPermission("SQPowerTools.reload"))) {
					
					reloadConfig();
					
					onDisable();
					onEnable();
					
					sender.sendMessage(ChatColor.GREEN + "SQPowerTools has been reloaded");
					
				} else {
					
					sender.sendMessage(ChatColor.RED + "Type /sqpowertools help to see a list of available commands");
					
				}
				
			} else {
				
				sender.sendMessage(ChatColor.RED + "Type /sqpowertools help to see a list of available commands");
				
			}
			
		}
		
		return false;
		
	}
	
	public static ItemStack addAttributes(ItemStack itemStack, List<String> attributeName, List<Float> amount, List<Integer> operation, List<String> slot){
		  
		net.minecraft.server.v1_9_R1.ItemStack nmsStack = CraftItemStack.asNMSCopy(itemStack);
	         
		HashMap<String, Integer> attributeUUIDs = new HashMap<String, Integer>();
		
	    NBTTagCompound compound = nmsStack.getTag();
	         
	    if (compound == null) {
	        	 
	    	compound = new NBTTagCompound();
	        nmsStack.setTag(compound);
	        compound = nmsStack.getTag();
	             
	    }
	         
	    NBTTagList modifiers = new NBTTagList();

	    for (int i = 0; i < attributeName.size(); i ++) {
	    	
		    NBTTagCompound attribute = new NBTTagCompound();
	    	
		    int uuid = 1;
		    
		    if (attributeUUIDs.containsKey(attributeName.get(i))) {
		    	
		    	int currentUUID = (int) attributeUUIDs.get(attributeName.get(i));
		    	
		    	attributeUUIDs.remove(attributeName.get(i));
		    	
		    	attributeUUIDs.put(attributeName.get(i), currentUUID + 1);
		    	
		    	uuid = currentUUID + 1;
		    	
		    } else {
		    	
		    	attributeUUIDs.put(attributeName.get(i), 1);
		    	
		    }
		    
	    	attribute.set("Slot", new NBTTagString(slot.get(i)));
		    attribute.set("AttributeName", new NBTTagString(attributeName.get(i)));
		    attribute.set("Name", new NBTTagString(attributeName.get(i)));
		    attribute.set("Amount", new NBTTagFloat(amount.get(i)));
		    attribute.set("Operation", new NBTTagInt(operation.get(i)));
		    attribute.set("UUIDLeast", new NBTTagInt(uuid));
		    attribute.set("UUIDMost", new NBTTagInt(uuid));
	    	
		    modifiers.add(attribute);
		    
	    }

	    compound.set("HideFlags", new NBTTagInt(6));
	    
	    compound.set("Unbreakable", new NBTTagInt(1));
	    
	    compound.set("AttributeModifiers", modifiers);
	    nmsStack.setTag(compound);
	    
	    itemStack = CraftItemStack.asBukkitCopy(nmsStack);
	    
	    return itemStack;
	       
	}
	
	public static ItemStack getPowerTool(String name) {
		
		for (int i = 0; i < powerToolNames.size(); i ++) {
			
			if (powerToolNames.get(i).equals(name)) {
				
				ItemStack powerTool = new ItemStack(powerToolMaterials.get(i));
				
				ItemMeta itemMeta = powerTool.getItemMeta();
				
				itemMeta.setDisplayName(name);
				
				powerTool.setItemMeta(itemMeta);
				
				powerTool = addLore(powerTool, i, name, new ArrayList<String>(), new ArrayList<Integer>(), false);
				
				powerTool.addUnsafeEnchantments(powerToolEnchants.get(i));
				
				powerTool = addAttributes(powerTool, powerToolAttributes.get(i), powerToolAmounts.get(i), powerToolOperations.get(i), powerToolSlots.get(i));
				
				powerTool.setDurability(powerToolDurabilities.get(i)); 
				
				return powerTool;
				
			}
			
		}
		
		return null;
		
	}
	
	public static String formatEnergy(float energy) {
		
		String formattedEnergy = "";
		
		if (energy >= 0 && energy < 1000) {
			
			formattedEnergy = Float.toString(energy);
			
		} else if (energy >= 1000 && energy < 1000000) {
			
			formattedEnergy = Float.toString(energy / 1000) + "k";
			
		} else if (energy >= 1000000) {
			
			formattedEnergy = Float.toString(energy / 1000000) + "m";
			
		} else {
			
			formattedEnergy = "0";
			
		}
		
		return formattedEnergy;
		
	}
	
	public static float unformatEnergy(String formattedEnergy) {
		
		if (formattedEnergy.endsWith("k")) {
			
			return Float.parseFloat(formattedEnergy.substring(0, formattedEnergy.toCharArray().length - 1)) * 1000;
			
		} else if (formattedEnergy.endsWith("m")) {
			
			return Float.parseFloat(formattedEnergy.substring(0, formattedEnergy.toCharArray().length - 1)) * 1000000;
			
		} else {
			
			return Float.parseFloat(formattedEnergy);
			
		}
		
	}
	
	public static ItemStack fixPowerTool(ItemStack powerTool) {
		
		List<String> lore = powerTool.getItemMeta().getLore();
		
		int pos = 0;
		
		String name = "";
		
		int maxEnergy = 0;
		int currentEnergy = 0;
		
		Map<Enchantment,Integer> enchants = powerTool.getEnchantments();
		
		for (String line : lore) {
			
			if (powerToolNames.contains(line.substring(2))) {
				
				for (int i = 0; i < powerToolNames.size(); i ++) {
					
					if (powerToolNames.get(i).equals(line.substring(2))) {
						
						pos = i;
						name = line.substring(2);
						
					}
					
				}
				
			} else if (line.startsWith(ChatColor.RED + "Energy:")) {
				
				char[] lineAsCharArray = line.toCharArray();
				
				boolean current = true;
				
				String energy = "";
				
				for (int i = 0; i < lineAsCharArray.length; i ++) {
					
					if (i > 9) {
						
						if (lineAsCharArray[i] != '/') {
							
							energy = energy + lineAsCharArray[i];
													
						} else {
							
							if (current) {

								current = false;
								
								currentEnergy = (int) unformatEnergy(energy);
								
								energy = "";
								
							}
							
						}
						
					}
					
				}
				
				maxEnergy = (int) unformatEnergy(energy);
				
			}
			
		}
		
		if (powerToolNames.contains(name)) {
			
			if (currentEnergy > 0) {
				
				net.minecraft.server.v1_9_R1.ItemStack nmsStack = CraftItemStack.asNMSCopy(powerTool);
				
			    NBTTagCompound compound = nmsStack.getTag();
			      
			    NBTTagList modifiers = (NBTTagList) compound.get("AttributeModifiers");

			    boolean needsToChange = false;
			    
			    for (int i = 0; i < modifiers.size(); i ++) {
			    	
			    	int attributePos = 0;
			    	
			    	if (powerToolAttributes.get(pos).contains(modifiers.get(i).get("Name").toString().substring(1, modifiers.get(i).get("Name").toString().toCharArray().length - 1))) {
			    		
			    		for (int j = 0; j < powerToolAttributes.get(pos).size(); j ++) {
			    			
			    			if (powerToolAttributes.get(pos).get(j).equals(modifiers.get(i).get("Name").toString().substring(1, modifiers.get(i).get("Name").toString().toCharArray().length - 1))) {
			    				
			    				attributePos = j;
			    				
			    			}
			    			
			    		}

			    		if (!powerToolAmounts.get(pos).get(attributePos).equals(Float.parseFloat(modifiers.get(i).get("Amount").toString().substring(0, modifiers.get(i).get("Amount").toString().toCharArray().length - 1)))) {
			    			
			    			needsToChange = true;
			    			
			    		}

			    		
			    	} else {
			    		
			    		needsToChange = true;
			    		
			    	}
				    
			    }

			    if (needsToChange) {
			    	
			    	HashMap<String, Integer> modifierMap = getModifiers(powerTool);
					
					List<String> modifierNames = new ArrayList<String>();
							
					modifierNames.addAll(modifierMap.keySet());
					
					List<Integer> modifierLevels = new ArrayList<Integer>();
					
					for (int i = 0; i < modifierNames.size(); i ++) {
						
						modifierLevels.add(modifierMap.get(modifierNames.get(i)));
						
					}
			    	
			    	List<String> attributes = new ArrayList<String>();
					List<Float> amounts = new ArrayList<Float>();
					List<String> slots = new ArrayList<String>();
					List<Integer> operations = new ArrayList<Integer>();
					
					for (int i = 0; i < powerToolNames.size(); i ++) {
						
						if (powerToolNames.get(i).equals(name)) {
							
							for (int j = 0; j < powerToolModNames.get(i).size(); j ++) {
								
								for (int k = 0; k < modifierNames.size(); k ++) {
									
									if (powerToolModNames.get(i).get(j).equals(modifierNames.get(k))) {
										
										for (int l = 0; l < powerToolModAttributes.get(i).get(j).size(); l ++) {
											
											attributes.add(powerToolModAttributes.get(i).get(j).get(l));
											amounts.add(powerToolModAmounts.get(i).get(j).get(l) * modifierLevels.get(k));
											slots.add(powerToolModSlots.get(i).get(j).get(l));
											operations.add(powerToolModOperations.get(i).get(j).get(l));
											
										}
										
									}
									
								}

							}
							
							for (int j = 0; j < powerToolAttributes.get(i).size(); j ++) {
								
								attributes.add(powerToolAttributes.get(i).get(j));
								amounts.add(powerToolAmounts.get(i).get(j));
								slots.add(powerToolSlots.get(i).get(j));
								operations.add(powerToolOperations.get(i).get(j));
								
							}
							
						}
						
					}
					
					powerTool = addAttributes(powerTool, attributes, amounts, operations, slots);
			    	
					int energy = getEnergy(powerTool);
					
			    	powerTool = addLore(powerTool, pos, name, modifierNames, modifierLevels, false);
			    	
			    	setEnergy(powerTool, energy);
			    	
			    }

		    	HashMap<String, Integer> modifierMap = getModifiers(powerTool);
			    
				List<String> modifierNames = new ArrayList<String>();
				
				modifierNames.addAll(modifierMap.keySet());
				
				List<Integer> modifierLevels = new ArrayList<Integer>();
				
				for (int i = 0; i < modifierNames.size(); i ++) {
					
					modifierLevels.add(modifierMap.get(modifierNames.get(i)));
					
				}

			    Map<Enchantment, Integer> realEnchants = new HashMap<Enchantment, Integer>();
			    
				for (int i = 0; i < powerToolNames.size(); i ++) {
					
					if (powerToolNames.get(i).equals(name)) {
						
						for (int j = 0; j < powerToolModNames.get(i).size(); j ++) {
							
							for (int k = 0; k < modifierNames.size(); k ++) {
								
								if (powerToolModNames.get(i).get(j).equals(modifierNames.get(k))) {
									
									List<Enchantment> justEnchants = new ArrayList<Enchantment>();
									
									justEnchants.addAll(powerToolModEnchants.get(i).get(j).keySet());
									
									List<Integer> justLevels = new ArrayList<Integer>();
									
									for (int l = 0; l < justEnchants.size(); l ++) {
										
										justLevels.add(powerToolModEnchants.get(i).get(j).get(justEnchants.get(l)));
										
									}
									
									for (int l = 0; l < justEnchants.size(); l ++) {
										
										realEnchants.put(justEnchants.get(l), (justLevels.get(l) * modifierLevels.get(k)));
										
									}
									
								}
								
							}

						}
						
					}
					
				}
			    
				realEnchants.putAll(powerToolEnchants.get(pos));
				
			    if (!enchants.equals(realEnchants)) {
			    	
			    	for (Enchantment enchant : enchants.keySet()) {
			    		
			    		powerTool.removeEnchantment(enchant);
			    		
			    	}
			    	
			    	powerTool.addUnsafeEnchantments(realEnchants);
			    	
			    }
			    
			    if (maxEnergy != powerToolEnergies.get(pos)) {
			    	
			    	int energy = getEnergy(powerTool);
			    	
			    	powerTool = addLore(powerTool, pos, name, modifierNames, modifierLevels, false);
			    	
			    	powerTool = setEnergy(powerTool, energy);
			    	
			    }
			    
			    boolean complete = false;
			    
			    List<String> specialLore = new ArrayList<String>();
			    
			    for (String line : lore) {
			    	
			    	if (!complete) {
			    		
				    	if (line.equals(ChatColor.DARK_PURPLE + "Power Tool")) {
				    		
				    		complete = true;
				    		
				    	} else {
				    		
				    		specialLore.add(line.substring(2));
				    		
				    	}
			    		
			    	}
			    	
			    }
			    
			    if (!specialLore.equals(powerToolLores.get(pos))) {
			    	
			    	HashMap<String,Integer> currentModifierMap = SQPowerTools.getModifiers(powerTool);
			    	
			    	List<Integer> currentModifierLevels = new ArrayList<Integer>();
					
					List<String> currentModifierNames = new ArrayList<String>();
					
					currentModifierNames.addAll(currentModifierMap.keySet());
					
					for (int j = 0; j < currentModifierMap.size(); j ++) {
						
						currentModifierLevels.add(currentModifierMap.get(currentModifierNames.get(j)));
						
					}
			    	
					int energy = getEnergy(powerTool);
					
			    	powerTool = addLore(powerTool, pos, name, currentModifierNames, currentModifierLevels, false);
			    	
			    	setEnergy(powerTool, energy);
			    	
			    }
				
			}	
			
		} else {
			
			powerTool = new ItemStack(powerTool.getType());
			
		}

	    return powerTool;
		
	}
	
	public static int getEnergy(ItemStack powerTool) {
		
		List<String> lore = powerTool.getItemMeta().getLore();
		
		for (String line : lore) {
			
			if (line.startsWith(ChatColor.RED + "Energy:")) {
				
				char[] lineAsCharArray = line.toCharArray();
				
				String energy = "";
				
				for (int i = 0; i < lineAsCharArray.length; i ++) {

					if (i > 9) {
						
						if (lineAsCharArray[i] != '/') {
							
							energy = energy + lineAsCharArray[i];
							
						} else {
							
							return (int) unformatEnergy(energy);
							
						}
						
					}
					
				}
				
			}
			
		}
		
		return 0;
		
	}
	
	public static int getMaxEnergy(String name, ItemStack powerTool) {
		
		int pos = 0;
		
		for (int i = 0; i < powerToolNames.size(); i ++) {
			
			if (powerToolNames.get(i).equals(name)) {
				
				pos = i;
				
			}
			
		}
		
		HashMap<String, Integer> modifierMap = getModifiers(powerTool);
		
		List<String> modNames = new ArrayList<String>();
		
		modNames.addAll(modifierMap.keySet());
		
		List<Integer> modLevels = new ArrayList<Integer>();
		
		for (int i = 0; i < modNames.size(); i ++) {
			
			modLevels.add(modifierMap.get(modNames.get(i)));
		}
		
		int energy = powerToolEnergies.get(pos);
		
		for (int i = 0; i < powerToolModNames.get(pos).size(); i ++) {
			
			for (int j = 0; j < modNames.size(); j ++) {
				
				if (modNames.get(j).equals(powerToolModNames.get(pos).get(i))) {
				
					energy = energy + (powerToolModEnergies.get(pos).get(i) * modLevels.get(j));
						
				}
				
			}
			
		}
		
		return energy;
		
	}
	
	
	public static ItemStack setEnergy(ItemStack powerTool, int energy) {
		
		List<String> lore = powerTool.getItemMeta().getLore();
		
		for (int i = 0; i < lore.size(); i ++) {
			
			if (lore.get(i).startsWith(ChatColor.RED + "Energy:")) {
				
				lore.set(i, ChatColor.RED + "Energy: " + formatEnergy(energy) + "/" + formatEnergy(getMaxEnergy(getName(powerTool), powerTool)));
				
			}
			
		}
		
		ItemMeta itemMeta = powerTool.getItemMeta();
		
		itemMeta.setLore(lore);
		
		powerTool.setItemMeta(itemMeta);
		
		return powerTool;
		
	}
	
	public static int getEnergyPerUse(String name) {
		
		for (int i = 0; i < powerToolNames.size(); i ++) {
			
			if (powerToolNames.get(i).equals(name)) {
				
				return powerToolEnergyPerUse.get(i);
				
			}
			
		}
		
		return 0;
		
	}
	
	public static String getName(ItemStack powerTool) {
		
		List<String> lore = powerTool.getItemMeta().getLore();
		
		for (String line : lore) {
			
			if (powerToolNames.contains(line.substring(2))) {
				
				for (int i = 0; i < powerToolNames.size(); i ++) {
					
					if (powerToolNames.get(i).equals(line.substring(2))) {
						
						return line.substring(2);
						
					}
					
				}
				
			}
			
		}
		
		return "";
		
	}
	
	public static HashMap<String, Integer> getModifiers(ItemStack powerTool) {
		
		HashMap<String, Integer> modifiers = new HashMap<String, Integer>();
		
		boolean reading = false;
		
		for (String line : powerTool.getItemMeta().getLore()) {
			
			if (line.equals(ChatColor.GOLD + "Mods")) {
				
				reading = true;
				
			} else if (reading) {
				
				if (line.equals(ChatColor.BLUE + "----------")) {
					
					reading = false;
					
				} else {
					
					List<String> words = new ArrayList<String>();
					
					String currentWord = "";
					
					int level = 0;
					
					boolean readModifier = false;
					
					for (int i = 0; i < line.length(); i ++) {
						
						if (i > 1) {
							
							if (!readModifier) {
								
								if (line.toCharArray()[i] != ' ') {
									
									currentWord = currentWord + line.toCharArray()[i] ;
									
								} else {
									
									if (currentWord.equals("Level")) {
										
										readModifier = true;
										
									} else {
										
										words.add(currentWord);
										
										currentWord = "";
										
									}
	
								}
								
							} else {
								
								level = Integer.parseInt(line.substring(i));
								
							}
							
						}
						
					}
					
					String modifierName = "";
					
					for (int i = 0; i < words.size(); i ++) {
						
						if (i > 0) {
							
							modifierName = modifierName + " " + words.get(i);
							
						} else {
							
							modifierName = modifierName + words.get(i);
							
						}
						
					}
					
					modifiers.put(modifierName, level);
					
				}
				
			}
			
		}
		
		return modifiers;
		
	}
	
	public static ItemStack addLore(ItemStack powerTool, int pos, String name, List<String> modNames, List<Integer> modLevels, boolean contraband) {
		
		ItemMeta itemMeta = powerTool.getItemMeta();
		
		itemMeta.setLore(new ArrayList<String>());
		
		List<String> lore = new ArrayList<String>();
		
		for (String line : powerToolLores.get(pos)) {
			
			lore.add(ChatColor.GRAY + line);
			
		}

		lore.add(ChatColor.DARK_PURPLE + "Power Tool");
		lore.add(ChatColor.DARK_PURPLE + name);

		int energy = powerToolEnergies.get(pos);
		
		for (int i = 0; i < powerToolModNames.get(pos).size(); i ++) {
			
			for (int j = 0; j < modNames.size(); j ++) {
				
				if (modNames.get(j).equals(powerToolModNames.get(pos).get(i))) {
				
					energy = energy + (powerToolModEnergies.get(pos).get(i) * modLevels.get(j));
						
				}
				
			}
			
		}
		
		lore.add(ChatColor.RED + "Energy: " + formatEnergy(energy) + "/" + formatEnergy(energy));
		
		if (modNames.size() > 0) {
			
			lore.add(ChatColor.BLUE + "----------");
			lore.add(ChatColor.GOLD + "Mods");
			
			for (int i = 0; i < modNames.size(); i ++) {
				
				lore.add(ChatColor.GOLD + modNames.get(i) + " Level " + modLevels.get(i));
				
			}
			
		}
		
		lore.add(ChatColor.BLUE + "----------");
		
		for (int j = 0; j < powerToolAttributes.get(pos).size(); j ++) {
			
			String attributeName = "";
			
			float adjustment = 0;
			
			if (powerToolAttributes.get(pos).get(j).equals("generic.attackSpeed")) {
			
				attributeName = "Attack Speed";
				
				adjustment = 4;
				
				for (int k = 0; k < powerToolModNames.get(pos).size(); k ++) {
					
					for (int l = 0; l < modNames.size(); l ++) {
							
						if (modNames.get(l).equals(powerToolModNames.get(pos).get(k))) {
						
							for (int m = 0; m < powerToolModAttributes.get(pos).get(k).size(); m ++) {
							
								if (powerToolModAttributes.get(pos).get(k).get(m).equals("generic.attackSpeed")) {
								
									adjustment = adjustment + (powerToolModAmounts.get(pos).get(k).get(m) * modLevels.get(l));
									
								}
								
							}
							
						}
						
					}
					
				}
				
			} else if (powerToolAttributes.get(pos).get(j).equals("generic.attackDamage")) {
				
				attributeName = "Damage";
				
				if (powerTool.containsEnchantment(Enchantment.DAMAGE_ALL)) {
					
					adjustment = (float) ((powerTool.getEnchantmentLevel(Enchantment.DAMAGE_ALL) * .5) + 1.5);
					
				} else {
					
					adjustment = 1;
					
				}
				
				for (int k = 0; k < powerToolModNames.get(pos).size(); k ++) {
					
					for (int l = 0; l < modNames.size(); l ++) {
							
						if (modNames.get(l).equals(powerToolModNames.get(pos).get(k))) {
						
							for (int m = 0; m < powerToolModAttributes.get(pos).get(k).size(); m ++) {
							
								if (powerToolModAttributes.get(pos).get(k).get(m).equals("generic.attackDamage")) {
								
									adjustment = adjustment + (powerToolModAmounts.get(pos).get(k).get(m) * modLevels.get(l));
									
								}
								
							}
							
						}
						
					}
					
				}
				
			} else if (powerToolAttributes.get(pos).get(j).equals("generic.movementSpeed")) {
				
				attributeName = "Movement Speed";
				
				adjustment = .1f;
				
				for (int k = 0; k < powerToolModNames.get(pos).size(); k ++) {
					
					for (int l = 0; l < modNames.size(); l ++) {
							
						if (modNames.get(l).equals(powerToolModNames.get(pos).get(k))) {
						
							for (int m = 0; m < powerToolModAttributes.get(pos).get(k).size(); m ++) {
							
								if (powerToolModAttributes.get(pos).get(k).get(m).equals("generic.movementSpeed")) {
								
									adjustment = adjustment + (powerToolModAmounts.get(pos).get(k).get(m) * modLevels.get(l));
									
								}
								
							}
							
						}
						
					}
					
				}
				
			} else if (powerToolAttributes.get(pos).get(j).equals("generic.armor")) {
				
				attributeName = "Armor";
				
				for (int k = 0; k < powerToolModNames.get(pos).size(); k ++) {
					
					for (int l = 0; l < modNames.size(); l ++) {
							
						if (modNames.get(l).equals(powerToolModNames.get(pos).get(k))) {
						
							for (int m = 0; m < powerToolModAttributes.get(pos).get(k).size(); m ++) {
							
								if (powerToolModAttributes.get(pos).get(k).get(m).equals("generic.armor")) {
								
									adjustment = adjustment + (powerToolModAmounts.get(pos).get(k).get(m) * modLevels.get(l));
									
								}
								
							}
							
						}
						
					}
					
				}
				
			} else if (powerToolAttributes.get(pos).get(j).equals("generic.knockbackResistance")) {
				
				attributeName = "Knockback Resistance";
				
				for (int k = 0; k < powerToolModNames.get(pos).size(); k ++) {
					
					for (int l = 0; l < modNames.size(); l ++) {
							
						if (modNames.get(l).equals(powerToolModNames.get(pos).get(k))) {
						
							for (int m = 0; m < powerToolModAttributes.get(pos).get(k).size(); m ++) {
							
								if (powerToolModAttributes.get(pos).get(k).get(m).equals("generic.knockbackResistance")) {
								
									adjustment = adjustment + (powerToolModAmounts.get(pos).get(k).get(m) * modLevels.get(l));
									
								}
								
							}
							
						}
						
					}
					
				}
				
			} else if (powerToolAttributes.get(pos).get(j).equals("generic.luck")) {
				
				attributeName = "Luck";
				
				for (int k = 0; k < powerToolModNames.get(pos).size(); k ++) {
					
					for (int l = 0; l < modNames.size(); l ++) {
							
						if (modNames.get(l).equals(powerToolModNames.get(pos).get(k))) {
						
							for (int m = 0; m < powerToolModAttributes.get(pos).get(k).size(); m ++) {
							
								if (powerToolModAttributes.get(pos).get(k).get(m).equals("generic.luck")) {
								
									adjustment = adjustment + (powerToolModAmounts.get(pos).get(k).get(m) * modLevels.get(l));
									
								}
								
							}
							
						}
						
					}
					
				}
				
			} else if (powerToolAttributes.get(pos).get(j).equals("generic.maxHealth")) {
				
				attributeName = "Health";
				
				for (int k = 0; k < powerToolModNames.get(pos).size(); k ++) {
					
					for (int l = 0; l < modNames.size(); l ++) {
							
						if (modNames.get(l).equals(powerToolModNames.get(pos).get(k))) {
						
							for (int m = 0; m < powerToolModAttributes.get(pos).get(k).size(); m ++) {
							
								if (powerToolModAttributes.get(pos).get(k).get(m).equals("generic.maxHealth")) {
								
									adjustment = adjustment + (powerToolModAmounts.get(pos).get(k).get(m) * modLevels.get(l));
									
								}
								
							}
							
						}
						
					}
					
				}
				
			} else {
				
				attributeName = powerToolAttributes.get(pos).get(j);
			}
			
			if (powerToolOperations.get(pos).get(j) == 0) {
				
				lore.add(ChatColor.GOLD + attributeName + ": " + String.format("%.1f", powerToolAmounts.get(pos).get(j) + adjustment));
				
			} else {
				
				lore.add(ChatColor.GOLD + attributeName + ": +" + String.format("%.1f", (powerToolAmounts.get(pos).get(j) * 100)) + "%");
				
			}
			
		}
		
		if (contraband) {
			
			lore.add(ChatColor.RED + "" + ChatColor.MAGIC + "Contraband");
			
		}
		
		itemMeta.setLore(lore);
		
		powerTool.setItemMeta(itemMeta);
		
		return powerTool;
		
	}
	
	public static ItemStack addModifiers(ItemStack powerTool, HashMap<String, Integer> modifiers) {
		
		String name = getName(powerTool);
		
		List<String> modifierNames = new ArrayList<String>();
				
		modifierNames.addAll(modifiers.keySet());
		
		List<Integer> modifierLevels = new ArrayList<Integer>();
		
		for (int i = 0; i < modifierNames.size(); i ++) {
			
			modifierLevels.add(modifiers.get(modifierNames.get(i)));
			
		}
		
		List<String> attributes = new ArrayList<String>();
		List<Float> amounts = new ArrayList<Float>();
		List<String> slots = new ArrayList<String>();
		List<Integer> operations = new ArrayList<Integer>();
		
		for (int i = 0; i < powerToolNames.size(); i ++) {
			
			if (powerToolNames.get(i).equals(name)) {
				
				for (int j = 0; j < powerToolModNames.get(i).size(); j ++) {
					
					for (int k = 0; k < modifierNames.size(); k ++) {
						
						if (powerToolModNames.get(i).get(j).equals(modifierNames.get(k))) {
							
							List<Enchantment> enchants = new ArrayList<Enchantment>();
							
							enchants.addAll(powerToolModEnchants.get(i).get(j).keySet());
							
							for (int l = 0; l < enchants.size(); l ++) {
								
								if (powerTool.containsEnchantment(enchants.get(l))) {
									
									int currentLevel = powerTool.getEnchantmentLevel(enchants.get(l));
									
									powerTool.removeEnchantment(enchants.get(l));
									
									powerTool.addUnsafeEnchantment(enchants.get(l), currentLevel + (powerToolModEnchants.get(i).get(j).get(enchants.get(l)) * modifierLevels.get(k)));
									
								} else {
									
									powerTool.addUnsafeEnchantment(enchants.get(l), powerToolModEnchants.get(i).get(j).get(enchants.get(l)) * modifierLevels.get(k));
									
								}
								
							}
							
							for (int l = 0; l < powerToolModAttributes.get(i).get(j).size(); l ++) {
								
								attributes.add(powerToolModAttributes.get(i).get(j).get(l));
								amounts.add(powerToolModAmounts.get(i).get(j).get(l) * modifierLevels.get(k));
								slots.add(powerToolModSlots.get(i).get(j).get(l));
								operations.add(powerToolModOperations.get(i).get(j).get(l));
								
							}
							
						}
						
					}

				}
				
				for (int j = 0; j < powerToolAttributes.get(i).size(); j ++) {
					
					attributes.add(powerToolAttributes.get(i).get(j));
					amounts.add(powerToolAmounts.get(i).get(j));
					slots.add(powerToolSlots.get(i).get(j));
					operations.add(powerToolOperations.get(i).get(j));
					
				}
				
			}
			
		}
		
		powerTool = addAttributes(powerTool, attributes, amounts, operations, slots);
		
		return powerTool;
		
	}
	
}
