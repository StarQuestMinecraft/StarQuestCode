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

import com.ginger_walnut.sqpowertools.objects.Attribute;
import com.ginger_walnut.sqpowertools.objects.Effect;
import com.ginger_walnut.sqpowertools.objects.Modifier;
import com.ginger_walnut.sqpowertools.objects.PowerTool;
import com.ginger_walnut.sqpowertools.objects.PowerToolType;
import com.ginger_walnut.sqpowertools.tasks.ChargerTask;
import com.ginger_walnut.sqpowertools.tasks.HoldingTask;
import com.ginger_walnut.sqpowertools.utils.EffectUtils;

public class SQPowerTools extends JavaPlugin {

	public final Logger logger = Logger.getLogger("Minecraft");
	public static SQPowerTools plugin;
	
	public static List<PowerToolType> powerTools = new ArrayList<PowerToolType>();
	
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
		
		powerTools = new ArrayList<PowerToolType>();
		
		enabled = true;
		
		if (!new File(this.getDataFolder(), "config.yml").exists()) {
			
			saveDefaultConfig();
			saveConfig();
			
		}

		(new ChargerTask()).run();
		(new HoldingTask()).run();
		
		config = getConfig();
		
		for (String powerTool : config.getConfigurationSection("power tools").getKeys(false)) {
			
			PowerToolType powerToolType = new PowerToolType();
			
			powerToolType.configName = powerTool;
			
			powerToolType.material = Material.getMaterial(config.getInt("power tools." + powerTool + ".item"));
			powerToolType.durability = (short) config.getInt("power tools." + powerTool + ".durability");
			powerToolType.name = config.getString("power tools." + powerTool + ".name");

			powerToolType.energy = config.getInt("power tools." + powerTool + ".max energy");
			powerToolType.energyPerUse = config.getInt("power tools." + powerTool + ".energy per use");
			
			HashMap<Enchantment, Integer> enchants = new HashMap<Enchantment, Integer>();
			
			if (config.contains("power tools." + powerTool + ".enchants")) {

				for (String enchantment : config.getConfigurationSection("power tools." + powerTool + ".enchants").getKeys(false)) {
					
					enchants.put(Enchantment.getById(config.getInt("power tools." + powerTool + ".enchants." + enchantment + ".enchant")), config.getInt("power tools." + powerTool + ".enchants." + enchantment + ".level"));
					
				}
				
			}
			
			powerToolType.enchants = enchants;
			
			List<Attribute> attributes = new ArrayList<Attribute>();
			
			if (config.contains("power tools." + powerTool + ".attributes")) {

				for (String configAttribute : config.getConfigurationSection("power tools." + powerTool + ".attributes").getKeys(false)) {
					
					Attribute attribute = new Attribute();
					
					attribute.attribute = config.getString("power tools." + powerTool + ".attributes." + configAttribute + ".attribute");
					attribute.amount = (float) config.getDouble("power tools." + powerTool + ".attributes." + configAttribute + ".amount");
					attribute.slot = config.getString("power tools." + powerTool + ".attributes." + configAttribute + ".slot");
					attribute.operation = config.getInt("power tools." + powerTool + ".attributes." + configAttribute + ".operation");

					if (config.contains("power tools." + powerTool + ".attributes." + configAttribute + ".uuid")) {
						
						attribute.uuid = config.getInt("power tools." + powerTool + ".attributes." + configAttribute + ".uuid");
						
					} else {
						
						attribute.uuid = 0;
						
					}
					
					attributes.add(attribute);
					
				}
				
			}
			
			powerToolType.attributes = attributes;
			
			List<Effect> effects = new ArrayList<Effect>();
			
			if (config.contains("power tools." + powerTool + ".potion")) {
				
				for (String configEffect : config.getConfigurationSection("power tools." + powerTool + ".potion").getKeys(false)) {
					
					Effect effect = new Effect();
					
					effect.effect = config.getInt("power tools." + powerTool + ".potion." + configEffect + ".effect");
					effect.level = config.getInt("power tools." + powerTool + ".potion." + configEffect + ".level");
					effect.duration = config.getInt("power tools." + powerTool + ".potion." + configEffect + ".duration");
					effect.effectCase = config.getInt("power tools." + powerTool + ".potion." + configEffect + ".case");
					
					effects.add(effect);
					
				}
				
			}
			
			powerToolType.effects = effects;
			
			if (config.contains("power tools." + powerTool + ".lore")) {
				
				powerToolType.extraLore = config.getStringList("power tools." + powerTool + ".lore");
				
			} else {
				
				powerToolType.extraLore = new ArrayList<String>();
				
			}
			
			List<Modifier> modifiers = new ArrayList<Modifier>();
			
			if (config.contains("power tools." + powerTool + ".mods")) {
				
				for (String mod : config.getConfigurationSection("power tools." + powerTool + ".mods").getKeys(false)) {
					
					Modifier modifier = new Modifier();
					
					modifier.name = config.getString("power tools." + powerTool + ".mods." + mod + ".name");
					modifier.data = config.getInt("power tools." + powerTool + ".mods." + mod + ".data");
					modifier.material = Material.getMaterial(config.getInt("power tools." + powerTool + ".mods." + mod + ".item"));
					modifier.number = config.getInt("power tools." + powerTool + ".mods." + mod + ".amount");
					modifier.levels = config.getInt("power tools." + powerTool + ".mods." + mod + ".levels");
					
					List<Attribute> modAttributes = new ArrayList<Attribute>();
					
					if (config.contains("power tools." + powerTool + ".mods." + mod + ".effects.attributes")) {
						
						for (String configAttribute : config.getConfigurationSection("power tools." + powerTool + ".mods." + mod + ".effects.attributes").getKeys(false)) {
							
							Attribute attribute = new Attribute();
							
							attribute.attribute = config.getString("power tools." + powerTool + ".mods." + mod + ".effects.attributes." + configAttribute + ".attribute");
							attribute.amount = (float) config.getDouble("power tools." + powerTool + ".mods." + mod + ".effects.attributes." + configAttribute + ".amount");
							attribute.operation = config.getInt("power tools." + powerTool + ".mods." + mod + ".effects.attributes." + configAttribute + ".operation");
							attribute.slot = config.getString("power tools." + powerTool + ".mods." + mod + ".effects.attributes." + configAttribute + ".slot");
							
							if (config.contains("power tools." + powerTool + ".mods." + mod + ".effects.attributes." + configAttribute + ".uuid")) {
								
								attribute.uuid = config.getInt("power tools." + powerTool + ".mods." + mod + ".effects.attributes." + configAttribute + ".uuid");
								
							} else {
								
								attribute.uuid = 0;
								
							}

							modAttributes.add(attribute);
							
						}
						
					}
					
					modifier.attributes = modAttributes;
					
					List<Effect> modEffects = new ArrayList<Effect>();
					
					if (config.contains("power tools." + powerTool + ".mods." + mod + ".effects.potion")) {
						
						Effect effect = new Effect();

						for (String configEffect : config.getConfigurationSection("power tools." + powerTool + ".mods." + mod + ".effects.potion").getKeys(false)) {
							
							effect.effect = config.getInt("power tools." + powerTool + ".mods." + mod + ".effects.potion." + configEffect + ".effect");
							effect.level = config.getInt("power tools." + powerTool + ".mods." + mod + ".effects.potion." + configEffect + ".level");
							effect.duration = config.getInt("power tools." + powerTool + ".mods." + mod + ".effects.potion." + configEffect + ".duration");
							effect.effectCase = config.getInt("power tools." + powerTool + ".mods." + mod + ".effects.potion." + configEffect + ".case");
							
							modEffects.add(effect);
							
						}
						
					}
					
					modifier.effects = modEffects;
					
					Map<Enchantment, Integer> modEnchants = new HashMap<Enchantment, Integer>();

					if (config.contains("power tools." + powerTool + ".mods." + mod + ".effects.enchants")) {
						
						for (String enchant : config.getConfigurationSection("power tools." + powerTool + ".mods." + mod + ".effects.enchants").getKeys(false)) {
							
							modEnchants.put(Enchantment.getById(config.getInt("power tools." + powerTool + ".mods." + mod + ".effects.enchants." + enchant + ".enchant")), config.getInt("power tools." + powerTool + ".mods." + mod + ".effects.enchants." + enchant + ".level"));
							
						}
						
					}
					
					modifier.enchants = modEnchants;
					
					int modEnergy = 0;
					
					if (config.contains("power tools." + powerTool + ".mods." + mod + ".effects.energy")) {
						
						modEnergy = config.getInt("power tools." + powerTool + ".mods." + mod + ".effects.energy");
						
					}
					
					modifier.energy = modEnergy;
					
					List<String> modCannotCombines = new ArrayList<String>();
					
					if (config.contains("power tools." + powerTool + ".mods." + mod + ".cannot combine with")) {
						
						modCannotCombines = config.getStringList("power tools." + powerTool + ".mods." + mod + ".cannot combine with");
						
					}

					modifier.cannotCombines = modCannotCombines;

					modifiers.add(modifier);
					
				}
				
			}
			
			powerToolType.modifiers = modifiers;
			
			powerToolType.maxMods = config.getInt("power tools." + powerTool + ".max modifiers");
			
			if (config.contains("power tools." + powerTool + ".recipe")) {
				
				ShapedRecipe recipe = new ShapedRecipe((new PowerTool(powerToolType)).item);
				
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
					
					recipe.setIngredient(ingredients.get(i).toCharArray()[0], Material.getMaterial(config.getInt("power tools." + powerTool + ".recipe.ingredients." + ingredients.get(i))));
					
				}
				
				getServer().addRecipe(recipe);
				
				powerToolType.hasRecipe = true;
				
			} else {
				
				powerToolType.hasRecipe = false;
				
			}
			
			powerTools.add(powerToolType);

		}
		
	}
	
	public static Plugin getPluginMain() {
		
		return plugin;
		
	}

	public static void sendHelp(CommandSender sender) {
		
		sender.sendMessage(ChatColor.GOLD + "-----------------------------------------------------");
		sender.sendMessage(ChatColor.GOLD + "/sqpowertools help" + ChatColor.BLUE + " - Shows this");
		sender.sendMessage(ChatColor.GOLD + "/sqpowertools guide" + ChatColor.BLUE + " - Displays a guide for SQPowerTools");
			
		if (!(sender instanceof ConsoleCommandSender)) {
			
			sender.sendMessage(ChatColor.GOLD + "/sqpowertools recipes" + ChatColor.BLUE + " - Displays power tool recipes");
			sender.sendMessage(ChatColor.GOLD + "/sqpowertools mods" + ChatColor.BLUE + " - Displays power tool mods");
			sender.sendMessage(ChatColor.GOLD + "/sqpowertools fuel" + ChatColor.BLUE + " - Displays the charger fuel");
				
		}
			
		if (sender instanceof ConsoleCommandSender || sender.hasPermission("SQPowerTools.reload")) {
			
			sender.sendMessage(ChatColor.GOLD + "/sqpowertools reload" + ChatColor.BLUE + " - Reloads the power tool config");
				
		}
			
		if (sender.hasPermission("SQPowerTools.spawn")) {
				
			sender.sendMessage(ChatColor.GOLD + "/sqpowertools spawn" + ChatColor.BLUE + " - Spawns a power tool");
			sender.sendMessage(ChatColor.GOLD + "/sqpowertools addmods" + ChatColor.BLUE + " - Adds mods to a power tool");
				
		}
				
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
								
						for (int i = 0; i < powerTools.size(); i ++) {
							
							if (powerTools.get(i).hasRecipe) {
								
								ItemStack powerTool = (new PowerTool(powerTools.get(i))).item;
								
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
								
						for (int i = 0; i < powerTools.size(); i ++) {
								
							ItemStack powerTool = (new PowerTool(powerTools.get(i))).item;
								
							ItemMeta itemMeta = powerTool.getItemMeta();
								
							List<String> lore = itemMeta.getLore();
								
							lore.add(ChatColor.RED + "" + ChatColor.MAGIC + "Contraband");
								
							itemMeta.setLore(lore);
								
							powerTool.setItemMeta(itemMeta);
								
							inventory.addItem(powerTool);
							
							player.openInventory(inventory);

						}
						
					} else {
						
						sender.sendMessage(ChatColor.RED + "You must be a player to use this command");
						
					}	
					
				} else if (args[0].equals("spawn")) {
					
					if (sender instanceof Player) {
						
						Player player = (Player) sender;
						
						if (player.hasPermission("SQPowerTools.spawn")) {
						
							Inventory inventory = Bukkit.createInventory(player, 54, "Power Tools");
									
							for (int i = 0; i < powerTools.size(); i ++) {
									
								ItemStack powerTool = (new PowerTool(powerTools.get(i))).item;
									
								ItemMeta itemMeta = powerTool.getItemMeta();
									
								List<String> lore = itemMeta.getLore();
									
								lore.add(ChatColor.RED + "" + ChatColor.MAGIC + "Contraband");
									
								itemMeta.setLore(lore);
									
								powerTool.setItemMeta(itemMeta);
									
								inventory.addItem(powerTool);
								
								player.openInventory(inventory);
	
							}
							
						}
						
					}
					
				} else if (args[0].equals("addmods") || args[0].equals("addmod")) {
					
					if (sender instanceof Player) {
						
						Player player = (Player) sender;
						
						if (player.hasPermission("SQPowerTools.spawn")) {
						
							boolean isPowerTool = false;
							
							if (player.getInventory().getItemInMainHand().hasItemMeta()) {
								
								if (player.getInventory().getItemInMainHand().getItemMeta().hasLore()) {
									
									if (player.getInventory().getItemInMainHand().getItemMeta().getLore().contains(ChatColor.DARK_PURPLE + "Power Tool")) {
										
										isPowerTool = true;
										
										Inventory modifierInventory = Bukkit.createInventory(player, 54, "Power Tool Addmods");
										
										modifierInventory.setItem(53, player.getInventory().getItemInMainHand());
										
										PowerToolType toolType = SQPowerTools.getType(player.getInventory().getItemInMainHand());
										
										ItemStack maxMods = new ItemStack(Material.PAPER);
										
										List<String> maxModsLore = new ArrayList<String>();
										
										maxModsLore.add("Max Total Mod Levels: " + Integer.toString(toolType.maxMods));
										maxModsLore.add(ChatColor.RED + "" + ChatColor.MAGIC + "Contraband");
	
										ItemMeta itemMeta = maxMods.getItemMeta();
										
										itemMeta.setLore(maxModsLore);
										
										itemMeta.setDisplayName("Info");
										
										maxMods.setItemMeta(itemMeta);
										
										modifierInventory.setItem(45, maxMods);
										
										for (int i = 0; i < toolType.modifiers.size(); i ++) {
											
											ItemStack mod = new ItemStack((toolType.modifiers.get(i).material));
											
											mod.addUnsafeEnchantments(toolType.modifiers.get(i).enchants);
											
											mod.setDurability(Short.parseShort(Integer.toString(toolType.modifiers.get(i).data))); 
											
											ItemMeta modItemMeta = mod.getItemMeta();
											
											List<String> modLore = new ArrayList<String>();
											
											modLore.add(toolType.modifiers.get(i).name);
											modLore.add("Amount: " + toolType.modifiers.get(i).number);
											modLore.add("Levels: " + toolType.modifiers.get(i).levels);
											
											if (toolType.modifiers.get(i).energy > 0) {
								
												modLore.add(ChatColor.RED + "+ " + toolType.modifiers.get(i).energy + " Energy");
												
											} else if (toolType.modifiers.get(i).energy < 0) {
												
												modLore.add(ChatColor.RED + "- " + toolType.modifiers.get(i).energy + " Energy");
												
											}
											
											for (int j = 0; j < toolType.modifiers.get(i).attributes.size(); j ++) {
												
												String attributeName = "";
												
												float adjustment = toolType.modifiers.get(i).attributes.get(j).amount;
												
												if (toolType.modifiers.get(i).attributes.get(j).attribute.equals("generic.attackSpeed")) {
													
													attributeName = "Attack Speed";
	
												} else if (toolType.modifiers.get(i).attributes.get(j).attribute.equals("generic.attackDamage")) {
													
													attributeName = "Damage";
													
												} else if (toolType.modifiers.get(i).attributes.get(j).attribute.equals("generic.movementSpeed")) {
													
													attributeName = "Movement Speed";
													
												} else if (toolType.modifiers.get(i).attributes.get(j).attribute.equals("generic.armor")) {
													
													attributeName = "Armor";
													
												} else if (toolType.modifiers.get(i).attributes.get(j).attribute.equals("generic.knockbackResistance")) {
													
													attributeName = "Knockback Resistance";
													
												} else if (toolType.modifiers.get(i).attributes.get(j).attribute.equals("generic.luck")) {
													
													attributeName = "Luck";
													
												} else if (toolType.modifiers.get(i).attributes.get(j).attribute.equals("generic.maxHealth")) {
													
													attributeName = "Health";
													
												} else if (toolType.modifiers.get(i).attributes.get(j).attribute.equals("generic.armorToughness")) {
													
													attributeName = "Armor Toughness";
													
												} else {
												
													
													attributeName = toolType.modifiers.get(i).attributes.get(j).attribute;
												}
												
												if (toolType.modifiers.get(i).attributes.get(j).operation == 0) {
													
													if (adjustment > 0) {
														
														modLore .add(ChatColor.GOLD + "+ " + adjustment + " " + attributeName);
														
													} else if (adjustment < 0) {
														
														modLore .add(ChatColor.GOLD + "- " + Math.abs(adjustment) + " " + attributeName);
														
													}
													
												} else {
													
													if (adjustment > 0) {
														
														modLore .add(ChatColor.GOLD + "+ " + (adjustment * 100) + "% " + attributeName);
														
													} else if (adjustment < 0) {
														
														modLore .add(ChatColor.GOLD + "- " + Math.abs(adjustment * 100) + "% " + attributeName);
														
													}
													
												}
												
											}
											
											if (toolType.modifiers.get(i).effects.size() > 0) {
											
												modLore.add(ChatColor.DARK_PURPLE + "Potion Effects:");
												
												for (int j = 0; j < toolType.modifiers.get(i).effects.size(); j ++) {
													
													modLore.add(ChatColor.DARK_PURPLE + "  " + EffectUtils.getEffectName(toolType.modifiers.get(i).effects.get(j).effect) + ":");
													modLore.add(ChatColor.DARK_PURPLE + "    Level " + (toolType.modifiers.get(i).effects.get(j).level + 1));
													modLore.add(ChatColor.DARK_PURPLE + "    For " + toolType.modifiers.get(i).effects.get(j).duration + " seconds");
													modLore.add(ChatColor.DARK_PURPLE + "    Applies " + EffectUtils.getCaseName(toolType.modifiers.get(i).effects.get(j).effectCase));
													
												}
											
											}
											
											if (toolType.modifiers.get(i).cannotCombines.size() > 0) {
												
												modLore.add(ChatColor.BLUE + "Cannot Combine With:");
												
												for (int j = 0; j < toolType.modifiers.get(i).cannotCombines.size(); j ++) {
													
													modLore.add(ChatColor.BLUE + " - " + toolType.modifiers.get(i).cannotCombines.get(j));
													
												}
												
											}
											
											modLore.add(ChatColor.RED + "" + ChatColor.MAGIC + "Contraband");
											
											modItemMeta.setLore(modLore);
											
											mod.setItemMeta(modItemMeta);
											
											modifierInventory.addItem(mod);
											
										}
										
										player.openInventory(modifierInventory);
										
									}
									
								}
								
							}
							
							if (!isPowerTool) {
								
								sender.sendMessage(ChatColor.RED + "You must be holding a power tool");
								
							}
							
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
		
		PowerToolType type = getType(powerTool);
		
		int currentEnergy = 0;
		
		for (String line : lore) {
			
			if (line.startsWith(ChatColor.RED + "Energy:")) {
					
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
					
			}
			
		}
		
		if (type != null) {	
			
			HashMap<String, Integer> modifiers = getModifiers(powerTool);
			
			powerTool = getPowerTool(type, modifiers);
			powerTool = setEnergy(powerTool, currentEnergy);
			powerTool = addModifiers(powerTool, modifiers);
			
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
	
	public static int getMaxEnergy(PowerToolType type, ItemStack powerTool) {
		
		HashMap<String, Integer> modifierMap = getModifiers(powerTool);
		
		List<String> modNames = new ArrayList<String>();
		
		modNames.addAll(modifierMap.keySet());
		
		List<Integer> modLevels = new ArrayList<Integer>();
		
		for (int i = 0; i < modNames.size(); i ++) {
			
			modLevels.add(modifierMap.get(modNames.get(i)));
		}
		
		int energy = type.energy;
		
		for (int i = 0; i < type.modifiers.size(); i ++) {
			
			for (int j = 0; j < modNames.size(); j ++) {
				
				if (modNames.get(j).equals(type.modifiers.get(i).name)) {
				
					energy = energy + (type.modifiers.get(i).energy * modLevels.get(j));
						
				}
				
			}
			
		}
		
		return energy;
		
	}
	
	
	public static ItemStack setEnergy(ItemStack powerTool, int energy) {
		
		List<String> lore = powerTool.getItemMeta().getLore();
		
		for (int i = 0; i < lore.size(); i ++) {
			
			if (lore.get(i).startsWith(ChatColor.RED + "Energy:")) {
				
				lore.set(i, ChatColor.RED + "Energy: " + formatEnergy(energy) + "/" + formatEnergy(getMaxEnergy(getType(powerTool), powerTool)));
				
			}
			
		}
		
		ItemMeta itemMeta = powerTool.getItemMeta();
		
		itemMeta.setLore(lore);
		
		powerTool.setItemMeta(itemMeta);
		
		return powerTool;
		
	}
	
	public static PowerToolType getType(ItemStack powerTool) {
		
		List<String> lore = powerTool.getItemMeta().getLore();
		
		for (String line : lore) {
			
			for (PowerToolType type : powerTools) {
			
				if (type.name.equals(line.substring(2))) {
							
					return type;
							
				}
				
			}
			
		}
		
		return powerTools.get(0);
		
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
	
	/**public static ItemStack addLore(ItemStack powerTool, PowerToolType type, Map<String, Integer> modifierMap, boolean contraband) {
		
		ItemMeta itemMeta = powerTool.getItemMeta();
		
		itemMeta.setLore(new ArrayList<String>());
		
		List<String> lore = new ArrayList<String>();
		
		for (String line : type.extraLore) {
			
			lore.add(ChatColor.GRAY + line);
			
		}
		
		List<String> modNames = new ArrayList<String>();
		List<Integer> modLevels = new ArrayList<Integer>();
		
		modNames.addAll(modifierMap.keySet());
		
		for (int j = 0; j < modifierMap.size(); j ++) {
			
			modLevels.add(modifierMap.get(modNames.get(j)));
			
		}
		
		lore.add(ChatColor.DARK_PURPLE + "Power Tool");
		lore.add(ChatColor.DARK_PURPLE + type.name);

		int energy = type.energy;
		
		for (int i = 0; i < type.modifiers.size(); i ++) {
			
			for (int j = 0; j < modNames.size(); j ++) {
				
				if (modNames.get(j).equals(type.modifiers.get(i).name)) {
				
					energy = energy + (type.modifiers.get(i).energy * modLevels.get(j));
						
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
		
		for (int j = 0; j < type.attributes.size(); j ++) {
			
			String attributeName = "";
			
			float adjustment = 0;
			
			if (type.attributes.get(j).attribute.equals("generic.attackSpeed")) {
			
				attributeName = "Attack Speed";
				
				adjustment = 4;
				
				for (int k = 0; k < type.attributes.size(); k ++) {
					
					for (int l = 0; l < modNames.size(); l ++) {
							
						if (modNames.get(l).equals(type.modifiers.get(k).name)) {
						
							for (int m = 0; m < type.modifiers.get(k).attributes.size(); m ++) {
							
								if (type.modifiers.get(k).attributes.get(m).attribute.equals("generic.attackSpeed")) {
								
									adjustment = adjustment + (type.modifiers.get(k).attributes.get(m).amount * modLevels.get(l));
									
								}
								
							}
							
						}
						
					}
					
				}
				
			} else if (type.attributes.get(j).attribute.equals("generic.attackDamage")) {
				
				attributeName = "Damage";
				
				if (powerTool.containsEnchantment(Enchantment.DAMAGE_ALL)) {
					
					adjustment = (float) ((powerTool.getEnchantmentLevel(Enchantment.DAMAGE_ALL) * .5) + 1.5);
					
				} else {
					
					adjustment = 1;
					
				}
				
				for (int k = 0; k < type.modifiers.size(); k ++) {
					
					for (int l = 0; l < modNames.size(); l ++) {
							
						if (modNames.get(l).equals(type.modifiers.get(k).name)) {
						
							for (int m = 0; m < type.modifiers.get(k).attributes.size(); m ++) {
							
								if (type.modifiers.get(k).attributes.get(m).attribute.equals("generic.attackDamage")) {
								
									adjustment = adjustment + (type.modifiers.get(k).attributes.get(m).amount * modLevels.get(l));
									
								}
								
							}
							
						}
						
					}
					
				}
				
			} else if (type.attributes.get(j).attribute.equals("generic.movementSpeed")) {
			
				attributeName = "Movement Speed";
				
				for (int k = 0; k < type.attributes.size(); k ++) {
					
					for (int l = 0; l < modNames.size(); l ++) {
							
						if (modNames.get(l).equals(type.modifiers.get(k).name)) {
						
							for (int m = 0; m < type.modifiers.get(k).attributes.size(); m ++) {
							
								if (type.modifiers.get(k).attributes.get(m).attribute.equals("generic.movementSpeed")) {
								
									adjustment = adjustment + (type.modifiers.get(k).attributes.get(m).amount * modLevels.get(l));
									
								}
								
							}
							
						}
						
					}
					
				}
				
			} else if (type.attributes.get(j).attribute.equals("generic.armor")) {
			
				attributeName = "Armor";
				
				for (int k = 0; k < type.attributes.size(); k ++) {
					
					for (int l = 0; l < modNames.size(); l ++) {
							
						if (modNames.get(l).equals(type.modifiers.get(k).name)) {
						
							for (int m = 0; m < type.modifiers.get(k).attributes.size(); m ++) {
							
								if (type.modifiers.get(k).attributes.get(m).attribute.equals("generic.armor")) {
								
									adjustment = adjustment + (type.modifiers.get(k).attributes.get(m).amount * modLevels.get(l));
									
								}
								
							}
							
						}
						
					}
					
				}
				
			} else if (type.attributes.get(j).attribute.equals("generic.knockbackResistance")) {
					
					attributeName = "Knockback Resistance";
					
					for (int k = 0; k < type.attributes.size(); k ++) {
						
						for (int l = 0; l < modNames.size(); l ++) {
								
							if (modNames.get(l).equals(type.modifiers.get(k).name)) {
							
								for (int m = 0; m < type.modifiers.get(k).attributes.size(); m ++) {
								
									if (type.modifiers.get(k).attributes.get(m).attribute.equals("generic.knockbackResistance")) {
									
										adjustment = adjustment + (type.modifiers.get(k).attributes.get(m).amount * modLevels.get(l));
										
									}
									
								}
								
							}
							
						}
						
					}
					
			} else if (type.attributes.get(j).attribute.equals("generic.luck")) {
				
				attributeName = "Luck";
				
				for (int k = 0; k < type.attributes.size(); k ++) {
					
					for (int l = 0; l < modNames.size(); l ++) {
							
						if (modNames.get(l).equals(type.modifiers.get(k).name)) {
						
							for (int m = 0; m < type.modifiers.get(k).attributes.size(); m ++) {
							
								if (type.modifiers.get(k).attributes.get(m).attribute.equals("generic.luck")) {
								
									adjustment = adjustment + (type.modifiers.get(k).attributes.get(m).amount * modLevels.get(l));
									
								}
								
							}
							
						}
						
					}
					
				}
				
			} else if (type.attributes.get(j).attribute.equals("generic.maxHealth")) {
				
				attributeName = "Health";
				
				for (int k = 0; k < type.attributes.size(); k ++) {
					
					for (int l = 0; l < modNames.size(); l ++) {
							
						if (modNames.get(l).equals(type.modifiers.get(k).name)) {
						
							for (int m = 0; m < type.modifiers.get(k).attributes.size(); m ++) {
							
								if (type.modifiers.get(k).attributes.get(m).attribute.equals("generic.maxHealth")) {
								
									adjustment = adjustment + (type.modifiers.get(k).attributes.get(m).amount * modLevels.get(l));
									
								}
								
							}
							
						}
						
					}
					
				}
				
			} else if (type.attributes.get(j).attribute.equals("generic.armorToughness")) {
				
				attributeName = "Armor Toughness";
				
				for (int k = 0; k < type.attributes.size(); k ++) {
					
					for (int l = 0; l < modNames.size(); l ++) {
							
						if (modNames.get(l).equals(type.modifiers.get(k).name)) {
						
							for (int m = 0; m < type.modifiers.get(k).attributes.size(); m ++) {
							
								if (type.modifiers.get(k).attributes.get(m).attribute.equals("generic.armorToughness")) {
								
									adjustment = adjustment + (type.modifiers.get(k).attributes.get(m).amount * modLevels.get(l));
									
								}
								
							}
							
						}
						
					}
					
				}
				
			} else {
				
				attributeName = type.attributes.get(j).attribute;
			}
			
			if (type.attributes.get(j).operation == 0) {
				
				lore.add(ChatColor.GOLD + attributeName + ": " + String.format("%.1f", type.attributes.get(j).amount + adjustment));
				
			} else {
				
				if (type.attributes.get(j).amount + adjustment >= 0) {
					
					lore.add(ChatColor.GOLD + attributeName + ": +" + String.format("%.1f", ((type.attributes.get(j).amount + adjustment) * 100)) + "%");
					
				} else {
					
					lore.add(ChatColor.GOLD + attributeName + ": " + String.format("%.1f", ((type.attributes.get(j).amount + adjustment) * 100)) + "%");
					
				}

			}
			
		}
		
		if (contraband) {
			
			lore.add(ChatColor.RED + "" + ChatColor.MAGIC + "Contraband");
			
		}
		
		itemMeta.setLore(lore);
		
		powerTool.setItemMeta(itemMeta);
		
		return powerTool;
		
	}**/
	
	public static ItemStack addModifiers(ItemStack powerTool, HashMap<String, Integer> modifiers) {
		
		List<String> modifierNames = new ArrayList<String>();
				
		modifierNames.addAll(modifiers.keySet());
		
		List<Integer> modifierLevels = new ArrayList<Integer>();
		
		for (int i = 0; i < modifierNames.size(); i ++) {
			
			modifierLevels.add(modifiers.get(modifierNames.get(i)));
			
		}
		
		List<Attribute> attributes = new ArrayList<Attribute>();
		
		PowerToolType type = getType(powerTool);
		
		for (int j = 0; j < type.modifiers.size(); j ++) {
					
			for (int k = 0; k < modifierNames.size(); k ++) {
						
				if (type.modifiers.get(j).name.equals(modifierNames.get(k))) {
							
					List<Enchantment> enchants = new ArrayList<Enchantment>();
							
					enchants.addAll(type.modifiers.get(j).enchants.keySet());
							
					for (int l = 0; l < enchants.size(); l ++) {
							
						if (powerTool.containsEnchantment(enchants.get(l))) {
									
							int currentLevel = powerTool.getEnchantmentLevel(enchants.get(l));
									
							powerTool.removeEnchantment(enchants.get(l));
									
							powerTool.addUnsafeEnchantment(enchants.get(l), currentLevel + (type.modifiers.get(j).enchants.get(enchants.get(l)) * modifierLevels.get(k)));
									
						} else {
									
							powerTool.addUnsafeEnchantment(enchants.get(l), type.modifiers.get(j).enchants.get(enchants.get(l)) * modifierLevels.get(k));
									
						}
								
					}
							
					for (int l = 0; l < type.modifiers.get(j).attributes.size(); l ++) {
								
						for (int m = 0; m < modifierLevels.get(k); m ++) {
							
							attributes.add(type.modifiers.get(j).attributes.get(l));
							
						}
								
					}
							
				}
						
			}

		}
				
		for (int j = 0; j < type.attributes.size(); j ++) {
					
			attributes.add(type.attributes.get(j));
				
		}

		
		powerTool = addAttributes(powerTool, attributes);
			
		return powerTool;
			
	}
	
}
