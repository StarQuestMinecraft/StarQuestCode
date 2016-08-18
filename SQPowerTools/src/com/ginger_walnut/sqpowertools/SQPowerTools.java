package com.ginger_walnut.sqpowertools;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

import net.md_5.bungee.api.ChatColor;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.java.JavaPlugin;

import com.ginger_walnut.sqpowertools.enums.AmmoType;
import com.ginger_walnut.sqpowertools.enums.ProjectileType;
import com.ginger_walnut.sqpowertools.events.BlasterEvents;
import com.ginger_walnut.sqpowertools.events.ToolUseEvents;
import com.ginger_walnut.sqpowertools.gui.GUI;
import com.ginger_walnut.sqpowertools.gui.ModifierListGUI;
import com.ginger_walnut.sqpowertools.objects.Attribute;
import com.ginger_walnut.sqpowertools.objects.BlasterStats;
import com.ginger_walnut.sqpowertools.objects.Effect;
import com.ginger_walnut.sqpowertools.objects.Modifier;
import com.ginger_walnut.sqpowertools.objects.PowerTool;
import com.ginger_walnut.sqpowertools.objects.PowerToolType;
import com.ginger_walnut.sqpowertools.tasks.BlasterTask;
import com.ginger_walnut.sqpowertools.tasks.CooldownTask;
import com.ginger_walnut.sqpowertools.tasks.HoldingTask;
import com.ginger_walnut.sqpowertools.utils.EffectUtils;

public class SQPowerTools extends JavaPlugin {

	public final Logger logger = Logger.getLogger("Minecraft");
	public static SQPowerTools plugin;
	
	public static List<PowerToolType> powerTools = new ArrayList<PowerToolType>();
	
	public static List<Location> chargerLocations = new ArrayList<Location>();
	
    public static HashMap<Player, GUI> currentGui = new HashMap<Player, GUI>();
	
	public static FileConfiguration config = null;
	
	boolean enabled = false;
	
	@SuppressWarnings("deprecation")
	@Override
	public void onEnable() {
		
		plugin = this;
		
		if (!enabled) {
			
			this.getServer().getPluginManager().registerEvents(new Events(), this);
			this.getServer().getPluginManager().registerEvents(new ToolUseEvents(), this);
			this.getServer().getPluginManager().registerEvents(new BlasterEvents(), this);

			(new HoldingTask()).run();
			(new CooldownTask()).run();
			(new BlasterTask()).run();
			
		}
		
		powerTools = new ArrayList<PowerToolType>();
		
		enabled = true;
		
		if (!new File(this.getDataFolder(), "config.yml").exists()) {
			
			saveDefaultConfig();
			saveConfig();
			
		}

		config = getConfig();
		
		List<File> configFiles = new ArrayList<File>();
		
		configFiles.addAll(getAllConfigFiles(this.getDataFolder()));
		
		for (File configFile : configFiles) {
			
			FileConfiguration config = YamlConfiguration.loadConfiguration(configFile);
			
			if (config.contains("power tools")) {
				
				for (String powerTool : config.getConfigurationSection("power tools").getKeys(false)) {
					
					try {
					
						PowerToolType powerToolType = new PowerToolType();
						
						powerToolType.configName = powerTool;
						
						if (config.contains("power tools." + powerTool + ".durability")) {
							
							powerToolType.material = Material.getMaterial(config.getInt("power tools." + powerTool + ".item"));
							powerToolType.durability = (short) config.getInt("power tools." + powerTool + ".durability");
							
						} else {
							
							String[] split = config.getString("power tools." + powerTool + ".item").split(":");
							
							if (split.length == 1) {
								
								powerToolType.material = Material.getMaterial(config.getInt("power tools." + powerTool + ".item"));
								powerToolType.durability = 0;
								
							} else {
								
								powerToolType.material = Material.getMaterial(Integer.parseInt(split[0]));
								powerToolType.durability = Short.parseShort(split[1]);
								
							}
							
						}

						powerToolType.name = config.getString("power tools." + powerTool + ".name");
			
						powerToolType.energy = config.getInt("power tools." + powerTool + ".max energy");
						powerToolType.energyPerUse = config.getInt("power tools." + powerTool + ".energy per use");
						
						HashMap<Enchantment, Integer> enchants = new HashMap<Enchantment, Integer>();
						
						if (config.contains("power tools." + powerTool + ".blaster")) {
							
							BlasterStats blasterStats = new BlasterStats();
							
							blasterStats.cooldown = config.getInt("power tools." + powerTool + ".blaster.cooldown");
							blasterStats.damage = config.getDouble("power tools." + powerTool + ".blaster.damage");
							blasterStats.scope = config.getInt("power tools." + powerTool + ".blaster.scope") - 1;
							
							if (config.contains("power tools." + powerTool + ".blaster.ammo")) {
								
								blasterStats.ammo = config.getInt("power tools." + powerTool + ".blaster.ammo");
								blasterStats.reload = config.getInt("power tools." + powerTool + ".blaster.reload");
								
							}
							
							if (config.contains("power tools." + powerTool + ".blaster.ammoType")) {
								
								blasterStats.ammoType = AmmoType.getById(config.getInt("power tools." + powerTool + ".blaster.ammoType"));

							} else {
								
								blasterStats.ammoType = AmmoType.ENERGY;
								
							}
							
							if (config.contains("power tools." + powerTool + ".blaster.projectileType")) {
								
								blasterStats.projectileType = ProjectileType.getById(config.getInt("power tools." + powerTool + ".blaster.projectileType"));
								
							} else {
								
								blasterStats.projectileType = ProjectileType.ARROW;
								
							}
							
							if (config.contains("power tools." + powerTool + ".blaster.explosionSize")) {
								
								blasterStats.explosionSize = (float) config.getDouble("power tools." + powerTool + ".blaster.explosionSize");
								
							}
							
							if (config.contains("power tools." + powerTool + ".blaster.shotCount")) {
								
								blasterStats.shotCount = config.getInt("power tools." + powerTool + ".blaster.shotCount");
								
							}
							
							powerToolType.blasterStats = blasterStats;
							
						}
						
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
								
								if (config.contains("power tools." + powerTool + ".mods." + mod + ".energy per use")) {

									modifier.energyPerUse = config.getInt("power tools." + powerTool + ".mods." + mod + ".energy per use");
									
								}	
								
								if (config.contains("power tools." + powerTool + ".mods." + mod + ".data")) {
									
									modifier.material = Material.getMaterial(config.getInt("power tools." + powerTool + ".mods." + mod + ".item"));
									modifier.durability = (short) config.getInt("power tools." + powerTool + ".mods." + mod + ".data");
									
								} else {
									
									String[] split = config.getString("power tools." + powerTool + ".mods." + mod + ".item").split(":");
									
									if (split.length == 1) {
										
										modifier.material = Material.getMaterial(config.getInt("power tools." + powerTool + ".mods." + mod + ".item"));
										modifier.durability = 0;
										
									} else {
										
										modifier.material = Material.getMaterial(Integer.parseInt(split[0]));
										modifier.durability = Short.parseShort(split[1]);
										
									}
									
								}

								modifier.number = config.getInt("power tools." + powerTool + ".mods." + mod + ".amount");
								modifier.levels = config.getInt("power tools." + powerTool + ".mods." + mod + ".levels");
								
								if (config.contains("power tools." + powerTool + ".mods." + mod + ".blaster")) {
									
									BlasterStats blasterStats = new BlasterStats();
									
									if (config.contains("power tools." + powerTool + ".mods." + mod + ".blaster.cooldown")) {
										
										blasterStats.cooldown = config.getInt("power tools." + powerTool + ".mods." + mod + ".blaster.cooldown");
										
									}
									
									if (config.contains("power tools." + powerTool + ".mods." + mod + ".blaster.damage")) {
										
										blasterStats.damage = config.getDouble("power tools." + powerTool + ".mods." + mod + ".blaster.damage");
										
									}
									
									if (config.contains("power tools." + powerTool + ".mods." + mod + ".blaster.scope")) {
										
										blasterStats.scope = config.getInt("power tools." + powerTool + ".mods." + mod + ".blaster.scope");
										
									}
									
									if (config.contains("power tools." + powerTool + ".mods." + mod + ".blaster.ammo")) {
										
										blasterStats.ammo = config.getInt("power tools." + powerTool + ".mods." + mod + ".blaster.ammo");
										
									}
									
									if (config.contains("power tools." + powerTool + ".mods." + mod + ".blaster.reload")) {
										
										blasterStats.reload = config.getInt("power tools." + powerTool + ".mods." + mod + ".blaster.reload");
										
									}
									
									if (config.contains("power tools." + powerTool + ".mods." + mod + ".blaster.ammoType")) {
										
										blasterStats.ammoType = AmmoType.getById(config.getInt("power tools." + powerTool + ".mods." + mod + ".blaster.ammoType"));

									}
									
									if (config.contains("power tools." + powerTool + ".mods." + mod + ".blaster.projectileType")) {
										
										blasterStats.projectileType = ProjectileType.getById(config.getInt("power tools." + powerTool + ".mods." + mod + ".blaster.projectileType"));
										
									}
									
									if (config.contains("power tools." + powerTool + ".mods." + mod + ".blaster.explosionSize")) {
										
										blasterStats.explosionSize = (float) config.getDouble("power tools." + powerTool + ".mods." + mod + ".blaster.explosionSize");
										
									} else {
										
										blasterStats.explosionSize = 0;
										
									}
									
									if (config.contains("power tools." + powerTool + ".mods." + mod + ".blaster.shotCount")) {
										
										blasterStats.shotCount = config.getInt("power tools." + powerTool + ".mods." + mod + ".blaster.shotCount");
										
									} else {
										
										blasterStats.shotCount = 0;
										
									}

									modifier.blasterStats = blasterStats;
									
								}
								
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
							
							ShapedRecipe recipe = new ShapedRecipe((new PowerTool(powerToolType)).getItem());
							
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

							if (config.getString("power tools." + powerTool + ".recipe.line1").length() == 2) {
								
								powerToolType.recipe.add(config.getString("power tools." + powerTool + ".recipe.line1") + " ");
								
							} else if (config.getString("power tools." + powerTool + ".recipe.line1").length() == 1) {
								
								powerToolType.recipe.add(config.getString("power tools." + powerTool + ".recipe.line1") + "  ");
								
							} else {
								
								powerToolType.recipe.add(config.getString("power tools." + powerTool + ".recipe.line1"));
								
							}
							
							if (config.contains("power tools." + powerTool + ".recipe.line2")) {
								
								if (config.getString("power tools." + powerTool + ".recipe.line2").length() == 2) {
									
									powerToolType.recipe.add(config.getString("power tools." + powerTool + ".recipe.line2") + " ");
									
								} else if (config.getString("power tools." + powerTool + ".recipe.line2").length() == 1) {
									
									powerToolType.recipe.add(config.getString("power tools." + powerTool + ".recipe.line2") + "  ");
									
								} else {
									
									powerToolType.recipe.add(config.getString("power tools." + powerTool + ".recipe.line2"));
									
								}
								
								if (config.contains("power tools." + powerTool + ".recipe.line3")) {
									
									if (config.getString("power tools." + powerTool + ".recipe.line3").length() == 2) {
										
										powerToolType.recipe.add(config.getString("power tools." + powerTool + ".recipe.line3") + " ");
										
									} else if (config.getString("power tools." + powerTool + ".recipe.line3").length() == 1) {
										
										powerToolType.recipe.add(config.getString("power tools." + powerTool + ".recipe.line3") + "  ");
										
									} else {
										
										powerToolType.recipe.add(config.getString("power tools." + powerTool + ".recipe.line3"));
										
									}
									
								}
								
							}
							
							for (String ingredient : config.getConfigurationSection("power tools." + powerTool + ".recipe.ingredients").getKeys(false)) {
								
								powerToolType.ingredients.add(Material.getMaterial(config.getInt("power tools." + powerTool + ".recipe.ingredients." + ingredient)));
								
							}
							
							powerToolType.ingredientNames.addAll(config.getConfigurationSection("power tools." + powerTool + ".recipe.ingredients").getKeys(false));
							
						} else {
							
							powerToolType.hasRecipe = false;
							
						}
						
						powerTools.add(powerToolType);
					
					} catch (Exception exception) {
						
						exception.printStackTrace();
						
					}

				}
				
			}

		}
		
		PluginDescriptionFile pdfFile = this.getDescription();
		this.logger.info(pdfFile.getName() + " has been enabled!");

	}
	
	@Override
	public void onDisable() {
		
		saveDefaultConfig();
		
		PluginDescriptionFile pdfFile = this.getDescription();
		this.logger.info(pdfFile.getName() + " has been disabled!");
		
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
								
								ItemStack powerTool = (new PowerTool(powerTools.get(i))).getItem();
								
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
						
						/*Inventory inventory = Bukkit.createInventory(player, 54, "Power Tool Mods");
								
						for (int i = 0; i < powerTools.size(); i ++) {
								
							ItemStack powerTool = (new PowerTool(powerTools.get(i))).getItem();
								
							ItemMeta itemMeta = powerTool.getItemMeta();
								
							List<String> lore = itemMeta.getLore();
								
							lore.add(ChatColor.RED + "" + ChatColor.MAGIC + "Contraband");
								
							itemMeta.setLore(lore);
								
							powerTool.setItemMeta(itemMeta);
								
							inventory.addItem(powerTool);
							
							player.openInventory(inventory);

						}*/
						
						ModifierListGUI gui = new ModifierListGUI();
						gui.open(player);
						
					} else {
						
						sender.sendMessage(ChatColor.RED + "You must be a player to use this command");
						
					}	
					
				} else if (args[0].equals("spawn")) {
					
					if (sender instanceof Player) {
						
						Player player = (Player) sender;
						
						if (player.hasPermission("SQPowerTools.spawn")) {
						
							Inventory inventory = Bukkit.createInventory(player, 54, "Power Tools");
									
							for (int i = 0; i < powerTools.size(); i ++) {
									
								ItemStack powerTool = (new PowerTool(powerTools.get(i))).getItem();
									
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
											
											mod.setDurability(Short.parseShort(Integer.toString(toolType.modifiers.get(i).durability))); 
											
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
			
			HashMap<Modifier, Integer> modifiers = getModifiers(powerTool);
			
			PowerTool powerToolObject = new PowerTool(type, modifiers);
			powerToolObject.setEnergy(currentEnergy);

			powerToolObject.setDisplayName(powerTool.getItemMeta().getDisplayName());
			powerTool = powerToolObject.getItem();
		
			
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
		
		HashMap<Modifier, Integer> modifierMap = getModifiers(powerTool);
		
		List<String> modNames = new ArrayList<String>();
		
		List<Integer> modLevels = new ArrayList<Integer>();

		for (Modifier modifier : modifierMap.keySet()) {
			
			modNames.add(modifier.name);
			modLevels.add(modifierMap.get(modifier));
			
		}

		int energy = type.energy;
		
		for (int i = 0; i < type.modifiers.size(); i ++) {
			
			for (int j = 0; j < modNames.size(); j ++) {
				
				if (modNames.get(j).equals(type.modifiers.get(i).name)) {
				
					if (type.modifiers.get(i).energy != 0) {
						
						energy = energy + (type.modifiers.get(i).energy * modLevels.get(j));
						
					}
						
				}
				
			}
			
		}
		
		return energy;
		
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
	
	public static HashMap<Modifier, Integer> getModifiers(ItemStack powerTool) {
		
		HashMap<Modifier, Integer> modifiers = new HashMap<Modifier, Integer>();
		
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
								
								i = line.length();
								
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
					
					Modifier modifier = null;
					
					for (Modifier typeModifier : getType(powerTool).modifiers) {
						
						if (typeModifier.name.equals(modifierName)) {
							
							modifier = typeModifier;
							
						}
						
					}
					
					if (modifier != null) {
						
						modifiers.put(modifier, level);
						
					}

				}
				
			}
			
		}
		
		return modifiers;
		
	}
	
	public List<File> getAllConfigFiles(File directory) {
		
		List<File> configFiles = new ArrayList<File>();
		
		for (File file : directory.listFiles()) {
			
			if (file.getName().endsWith(".yml")) {
				
				configFiles.add(file);
				
			}
			
			if (file.isDirectory()) {
				
				configFiles.addAll(getAllConfigFiles(file));
				
			}
			
		}
		
		return configFiles;
		
	}
	
}
