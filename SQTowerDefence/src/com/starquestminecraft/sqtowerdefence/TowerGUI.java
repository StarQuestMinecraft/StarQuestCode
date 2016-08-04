package com.starquestminecraft.sqtowerdefence;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryInteractEvent;
import org.bukkit.event.inventory.InventoryPickupItemEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.material.MaterialData;
import org.bukkit.potion.Potion;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.potion.PotionType;
import org.bukkit.scheduler.BukkitTask;

import com.starquestminecraft.sqtechbase.SQTechBase;
import com.starquestminecraft.sqtechbase.gui.GUI;
import com.starquestminecraft.sqtechbase.objects.GUIBlock;
import com.starquestminecraft.sqtechbase.objects.Machine;
import com.starquestminecraft.sqtechbase.util.InventoryUtils;
import com.starquestminecraft.sqtechbase.util.ObjectUtils;
import com.whirlwindgames.dibujaron.sqempire.database.object.EmpirePlayer;

import net.milkbowl.vault.economy.Economy;

public class TowerGUI extends GUI {
	
	public TowerGUI(Player player, int id) {
		super(player, id);
	}

	GUIBlock guiBlock;
	TowerMachine towerMachine;
	ArrayList<Turret> turretList = com.starquestminecraft.sqtowerdefence.SQTowerDefence.getTurretList();
	
	public void open() {
		
		Machine machine = ObjectUtils.getMachineFromMachineGUI(this);
		
				guiBlock = machine.getGUIBlock();
				
				if(machine instanceof TowerMachine) {
					towerMachine = (TowerMachine) machine;
					
				}
				else {
					towerMachine = new TowerMachine(machine.getEnergy(), machine.getGUIBlock(), machine.getMachineType());
					towerMachine.enabled = machine.enabled;
					towerMachine.exportsEnergy = machine.exportsEnergy;
					towerMachine.importsEnergy = machine.importsEnergy;
					Turret t = new Turret(TurretType.BASE, null, null, 0, 0.0, 0.0, 0.0, 0.0, "Base Tower");
					towerMachine.data.put("turretData", new TurretData(t));
					SQTechBase.machines.remove(machine);
					SQTechBase.machines.add(towerMachine);
				}
				
				
				if(towerMachine.turret == null) {

					TurretData turretData = ((TurretData) (towerMachine.data.get("turretData")));
					towerMachine.turret = turretData.getTurretFromData();
					
				}

		
		if(towerMachine.turret.getName().equals("Base Tower")) {
			
			Inventory gui = Bukkit.createInventory(owner, 27, ChatColor.GRAY + "Tower");
			for(int i=0; i<turretList.size(); i++) {
				Turret turret = turretList.get(i);
				Material material = Material.DISPENSER;
				switch (turret.getTurretType()) {
					case ARROW:
						material = Material.REDSTONE_LAMP_OFF;
						break;
					case CHEMICAL:
						material = Material.FURNACE;
						break;
					case GAS:
						material = Material.FURNACE;
						break;
					case ANTIAIR: case FLAK:
						material = Material.DISPENSER;
						break;
					case CANNON:
						material = Material.TNT;
						break;
					case SPAWNER:
						material = Material.MOB_SPAWNER;
						break;
					default:
						break;
				}
				if(turret.showInGUI) {
					gui.setItem(i, InventoryUtils.createSpecialItem(material, (short) 0, "Build " + turret.name, new String[] {ChatColor.RED + "" + ChatColor.MAGIC + "Contraband", ChatColor.RESET + "" + ChatColor.GOLD + "Cost: " + Double.toString(turret.cost)}));
				}
			}
			owner.openInventory(gui);
			
		}
		else {
			
			openNewInventory(0);
		
		}
		
		if (SQTechBase.currentGui.containsKey(owner)) {
			
			SQTechBase.currentGui.remove(owner);
			SQTechBase.currentGui.put(owner,  this);
			
		} else {
			
			SQTechBase.currentGui.put(owner,  this);
			
		}
		
		
		
		if(!com.starquestminecraft.sqtowerdefence.SQTowerDefence.isChecking) {
			SQTowerDefence plugin = com.starquestminecraft.sqtowerdefence.SQTowerDefence.sqtdInstance;
			Turret t = new Turret(TurretType.BASE, null, null, 0, 0.0, 0.0, 0.0, 0.0, "EntityCheck");
			t.worldName = owner.getWorld().getName();
			BukkitTask task = new SQTDTask(plugin, t).runTaskLater(plugin, 300);
			com.starquestminecraft.sqtowerdefence.SQTowerDefence.isChecking = true;
		}
		
		towerMachine.data.put("turretData", new TurretData(towerMachine.turret));
		
	}
	
	@Override
	public void click(InventoryClickEvent event) {
		Inventory gui = event.getClickedInventory();
		if (event.getClickedInventory().getTitle().startsWith(ChatColor.GRAY + "Tower")) {
			
			if(event.getSlot() < turretList.size()) {
				Turret clickedTurret = turretList.get(event.getSlot());
				
				Block mainBlock = guiBlock.getLocation().getBlock();
				
				if(checkBlocks(mainBlock)) {
					
					Material material = Material.DISPENSER;
				switch (clickedTurret.getTurretType()) {
					case ARROW:
						material = Material.REDSTONE_LAMP_OFF;
						break;
					case CHEMICAL:
						material = Material.FURNACE;
						break;
					case GAS:
						material = Material.FURNACE;
						break;
					case ANTIAIR: case FLAK:
						material = Material.DISPENSER;
						break;
					case CANNON:
						material = Material.TNT;
						break;
					case SPAWNER:
						material = Material.MOB_SPAWNER;
						break;
					default:
						break;
				}
					
				Turret t = turretList.get(event.getSlot()).createNewTurret();
				EmpirePlayer ep = EmpirePlayer.getOnlinePlayer(owner);
				t.turretEmpire = ep.getEmpire().toString();
				
				Economy economy = com.starquestminecraft.sqtowerdefence.SQTowerDefence.sqtdInstance.getEconomy();
				Double balance = economy.getBalance(owner);
				if(balance < t.cost) {
					owner.sendMessage("You cannot afford that turret!");
					return;
				}
					
					Block blockTwo = mainBlock.getRelative(BlockFace.UP);
					Block blockThree = blockTwo.getRelative(BlockFace.UP);
					Block blockFour = blockThree.getRelative(BlockFace.UP);
					Block blockFive = blockFour.getRelative(BlockFace.UP);
					
					blockTwo.setType(Material.DROPPER);
					blockThree.setType(Material.DOUBLE_STEP);
					blockFour.setType(Material.DOUBLE_STEP);
					blockFive.setType(material);
					t.worldName = owner.getWorld().getName();
					t.guiX = guiBlock.getLocation().getX();
					t.guiY = guiBlock.getLocation().getY();
					t.guiZ = guiBlock.getLocation().getZ();
					t.setTurretBlock(blockTwo, 1);
					t.setTurretBlock(blockThree, 2);
					t.setTurretBlock(blockFour, 3);
					t.setTurretBlock(blockFive, 4);
					t.owner = owner.getDisplayName();
					economy.withdrawPlayer(owner, t.cost);
					TurretData turretData = new TurretData(t);
					towerMachine.data.put("turretData", turretData);
					towerMachine.turret = t;
					towerMachine.turret.runTurret();
					
					close = false;
					openNewInventory(0);
					close = true;
				}
			}
		}
		else {
			if(com.starquestminecraft.sqtowerdefence.SQTDListener.stringList.contains(event.getClickedInventory().getTitle()) && event.getCurrentItem().getItemMeta() != null) {
				String itemName = event.getCurrentItem().getItemMeta().getDisplayName();
				if(itemName.equals(ChatColor.RESET + "Upgrade Paths")) {
					close = false;
					openNewInventory(1);
					close = true;
				}
				else if (itemName.equals(ChatColor.RESET + "Back")) {
					close = false;
					openNewInventory(0);
					close = true;
				}
				else if(itemName.startsWith(ChatColor.RESET + "Upgrade ") && !(itemName.equals(ChatColor.RESET + "Upgrade Paths"))) {
					Upgrade upgrade = null;
					String upgradeName = itemName.substring(10, itemName.length());
					boolean conflicts = false;
					
					List<Upgrade> possibleUpgrades = towerMachine.turret.possibleUpgrades;
					
					for(int i=0; i<possibleUpgrades.size(); i++) {
						
						Upgrade u = possibleUpgrades.get(i);
						if(u.getUpgradeType().toString().equalsIgnoreCase(upgradeName)) {
							upgrade = u;
						}
						else if (itemName.endsWith(" Turret")) {
							String newUpgradeName = upgradeName.substring(5, upgradeName.length());
							if(u.customName.equals(newUpgradeName)) {
								upgrade = u;
							}
						}
						
					}
					
					if(upgrade == null) {
						
						List<Upgrade> conflictingUpgrades = towerMachine.turret.conflictingUpgrades;
						
						for(int i=0; i<conflictingUpgrades.size(); i++) {
							
							Upgrade u = conflictingUpgrades.get(i);
							if(u.getUpgradeType().toString().equalsIgnoreCase(upgradeName)) {
								upgrade = u;
							}
							else if (itemName.endsWith(" Turret")) {
								String newUpgradeName = upgradeName.substring(5, upgradeName.length());
								if(u.customName.equals(newUpgradeName)) {
									upgrade = u;
								}
							}
							
						}
						
					}
					
					if(towerMachine.turret.buyNewUpgrade(owner, upgrade)) {
						close = false;
						openNewInventory(0);
						close = true;
					}
					
				}
				else if(itemName.contains("Add Support For Potion")) {
					ItemStack item = event.getCurrentItem();
					PotionType pType = getPotionType(item.getItemMeta());
					
					List<Upgrade> possibleUpgrades = towerMachine.turret.possibleUpgrades;
					
					for(int i=0; i<possibleUpgrades.size(); i++) {
						Upgrade u = possibleUpgrades.get(i);
						if(u.getUpgradeType().equals(UpgradeType.POTION_EFFECT)) {
							Integer effectID = u.getBoost().intValue();
							PotionType potionType = PotionType.getByEffect(PotionEffectType.getById(effectID));
							if(potionType.equals(pType)) {
								if(towerMachine.turret.buyNewUpgrade(owner, u)) {
									towerMachine.turret.addPotionType(pType);
								}
							}
						}
					}
					
					List<Upgrade> conflictingUpgrades = towerMachine.turret.conflictingUpgrades;
					
					for(int i=0; i<conflictingUpgrades.size(); i++) {
						Upgrade u = conflictingUpgrades.get(i);
						if(u.getUpgradeType().equals(UpgradeType.POTION_EFFECT)) {
							Integer effectID = u.getBoost().intValue();
							PotionType potionType = PotionType.getByEffect(PotionEffectType.getById(effectID));
							if(potionType.equals(pType)) {
								if(towerMachine.turret.buyNewUpgrade(owner, u)) {
									towerMachine.turret.addPotionType(pType);
								}
							}
						}
					}
					
					close = false;
					openNewInventory(0);
					close = true;
				}
				else if(itemName.contains("Toggle Friendly Fire")) {
					towerMachine.turret.toggleFriendlyFire();
					close = false;
					openNewInventory(0);
					close = true;
				}
				else if(itemName.contains("Repair")) {
					Economy economy = com.starquestminecraft.sqtowerdefence.SQTowerDefence.sqtdInstance.getEconomy();
					Double balance = economy.getBalance(owner);
					if(balance < towerMachine.turret.cost) {
						owner.sendMessage("You cannot afford to repair this turret!");
						return;
					}
					economy.withdrawPlayer(owner, towerMachine.turret.cost);
					
					Block mainBlock = guiBlock.getLocation().getBlock();
					Block blockTwo = mainBlock.getRelative(BlockFace.UP);
					Block blockThree = blockTwo.getRelative(BlockFace.UP);
					Block blockFour = blockThree.getRelative(BlockFace.UP);
					Block blockFive = blockFour.getRelative(BlockFace.UP);
					if(blockTwo.getType().equals(Material.AIR)) {
						blockTwo.setTypeId(towerMachine.turret.blockOneType);
					}
					if(blockThree.getType().equals(Material.AIR)) {
						blockThree.setTypeId(towerMachine.turret.blockTwoType);
					}
					if(blockFour.getType().equals(Material.AIR)) {
						blockFour.setTypeId(towerMachine.turret.blockThreeType);
					}
					if(blockFive.getType().equals(Material.AIR)) {
						blockFive.setTypeId(towerMachine.turret.blockFourType);
					}
					
				}
				else if(itemName.contains("Back to Main GUI")) {
					close = false;
					towerMachine.getGUIBlock().getGUI(owner).open();
					close = true;
				}
				
			}
		}
		
		towerMachine.data.put("turretData", new TurretData(towerMachine.turret));
	}
	
	public void openNewInventory(Integer inv) {	
		
		Turret t = towerMachine.turret;
		List<Upgrade> upgradeList = new ArrayList<Upgrade>();
		
		if(inv == 0) {
			upgradeList = t.possibleUpgrades;
		}
		else if(inv == 1) {
			upgradeList = t.conflictingUpgrades;
		}
					
			Inventory newGUI = Bukkit.createInventory(owner, 27, ChatColor.GRAY + towerMachine.turret.getName());				
			
			for(int i=0; i<upgradeList.size(); i++) {
				Upgrade u = upgradeList.get(i);
				Material material = Material.IRON_SWORD;
				switch (u.getUpgradeType()) {
					case ACCURACY:
						material = Material.BOW;
						break;
					case SPEED:
						material = Material.REDSTONE_TORCH_ON;
						break;
					case DAMAGE:
						material = Material.FIREBALL;
						break;
					case RANGE:
						material = Material.ARROW;
						break;
					case POTION_EFFECT:
						material = Material.POTION;
						break;
					case BOT_HEALTH:
						material = Material.IRON_CHESTPLATE;
						break;
					case BOT_DAMAGE:
						material = Material.IRON_SWORD;
						break;
					case MAX_BOTS:
						material = Material.SKULL;
						break;
					case BOT_WEAPON:
						material = Material.BOW;
						break;
					case TOWER:
						material = Material.DISPENSER;
						break;
					default:
						break;
				}
				
				String upgradeMessage = "Upgrade " + u.getUpgradeType().toString().toLowerCase();
				String loreOne = ChatColor.RESET + "" + ChatColor.DARK_GREEN + "By: " + Double.toString(u.getBoost());
				String loreTwo = ChatColor.RESET + "" + ChatColor.DARK_GREEN + "Current Level: " + u.getLevel();
				String loreThree = ChatColor.RESET + "" + ChatColor.DARK_GREEN + "Cost: " + Double.toString(u.getCurrentCost());
				
				ItemStack item = null;
				
				if(u.getUpgradeType().equals(UpgradeType.POTION_EFFECT)) {
					Integer effectID = u.getBoost().intValue();
					
					PotionEffectType potionEffect = PotionEffectType.getById(effectID);
					Potion potion = new Potion(PotionType.getByEffect(potionEffect));
					
					item = potion.toItemStack(1);
					
					upgradeMessage = "Add Support For Potion";
					loreOne = ChatColor.RESET + "" + ChatColor.DARK_GREEN + "Cost: " + Double.toString(u.getCurrentCost());
					loreTwo = "";
					loreThree = "";
					String[] loreList;
					
				}
				
				if(u.getUpgradeType().equals(UpgradeType.TOWER)) {
					upgradeMessage = "Upgrade to a " + u.customName;
					loreOne = "";
					loreTwo = "";
					loreThree = "";
				}
				
				if(u.getUpgradeType().equals(UpgradeType.POTION_EFFECT)) {
					newGUI.setItem(i + 9, createSpecialItem(item, ChatColor.RESET + upgradeMessage, new String[] {ChatColor.RED + "" + ChatColor.MAGIC + "Contraband", loreOne, loreTwo, loreThree}));
				}
				else {
					newGUI.setItem(i + 9, InventoryUtils.createSpecialItem(material, (short) 0, ChatColor.RESET + upgradeMessage, new String[] {ChatColor.RED + "" + ChatColor.MAGIC + "Contraband", loreOne, loreTwo, loreThree}));
				}
				
				
			}
			close = false;
			owner.openInventory(newGUI);
			close = true;
			if(inv == 0) {
				if(!t.hasConflicts) {	
					newGUI.setItem(18, InventoryUtils.createSpecialItem(Material.BEACON, (short) 0, ChatColor.RESET + "Upgrade Paths", new String[] {ChatColor.RED + "" + ChatColor.MAGIC + "Contraband", ChatColor.RESET + "" + ChatColor.GOLD + "Opens a menu of upgrades", ChatColor.RESET + "" + ChatColor.GOLD + "that conflict with one another.", ChatColor.RESET + "" + ChatColor.GOLD + "A turret cannot have", ChatColor.RESET + "" + ChatColor.GOLD +  "2 upgrades that conflict." }));
				}
					String lore = "";
					String lore2 = "";
					String lore3 = "";
					String lore4 = "";
					switch (t.getTurretType()) {
						case ARROW:
							lore = ChatColor.RESET + "Fires arrows.";
							lore2 = ChatColor.RESET + "" + ChatColor.GRAY + "Requires ammunition.";
							break;
						case CHEMICAL:
							lore = ChatColor.RESET + "Fires splash potions.";
							lore2 = ChatColor.RESET + "" + ChatColor.GRAY + "Requires ammunition.";
							break;
						case GAS:
							lore = ChatColor.RESET + "Fires lingering potions.";
							lore2 = ChatColor.RESET + "" + ChatColor.GRAY + "Requires ammunition.";
							break;
						case ANTIAIR:
							lore = ChatColor.RESET + "Fires fire charges.";
							lore2 = ChatColor.RESET + "" + ChatColor.GRAY + "Requires ammunition.";
							lore3 = ChatColor.RESET + "" + ChatColor.GRAY + "Can only fire in an";
							lore4 = ChatColor.RESET + "" + ChatColor.GRAY + "upwards arc of 120 degrees.";
							break;
						case FLAK:
							lore = ChatColor.RESET + "Fires bursts of fire charges.";
							lore2 = ChatColor.RESET + "" + ChatColor.GRAY + "Requires ammunition.";
							lore3 = ChatColor.RESET + "" + ChatColor.GRAY + "Can only fire in an";
							lore4 = ChatColor.RESET + "" + ChatColor.GRAY + "upwards arc of 120 degrees.";
							break;
						case CANNON:
							lore = ChatColor.RESET + "Fires fire charges.";
							lore2 = ChatColor.RESET + "" + ChatColor.GRAY + "Requires ammunition.";
							break;
						default:
							break;
					}
					
					newGUI.setItem(0, InventoryUtils.createSpecialItem(Material.SPONGE, (short) 0, ChatColor.RESET + t.getName(), new String[] { lore, lore2, lore3, lore4, ChatColor.RESET + "" + ChatColor.GRAY + "Damage: " + Double.toString(t.getDamage()), ChatColor.RESET + "" + ChatColor.GRAY + "Firerate: " + Double.toString(t.getFireRate()), ChatColor.RESET + "" + ChatColor.GRAY + "Accuracy: " + Double.toString(t.getAccuracy()), ChatColor.RESET + "" + ChatColor.GRAY + "Range: " + Double.toString(t.range) + " Blocks", ChatColor.RED + "" + ChatColor.MAGIC + "Contraband"}));
					
					
					ItemStack item = new ItemStack(Material.COMMAND_CHAIN, 1);
					
					List <Upgrade> upgrades = t.getUpgrades();
					ItemMeta meta = item.getItemMeta();
					List<String> loreList = new ArrayList<String>();
					
					
					meta.setDisplayName(ChatColor.RESET +  "Current Upgrades");
					for(int i=0; i<upgrades.size(); i++) {
						Upgrade upgrade = upgrades.get(i);
						loreList.add(ChatColor.RESET + "" + ChatColor.GRAY + upgrade.getUpgradeType().toString() + ", Level " + Integer.toString(upgrade.getLevel()) + " (+" + Double.toString(upgrade.getBoost() * upgrade.getLevel()) + ")");
					}
					loreList.add(ChatColor.RED + "" + ChatColor.MAGIC + "Contraband");
					meta.setLore(loreList);
					item.setItemMeta(meta);
					newGUI.setItem(1, item);
					newGUI.setItem(2, InventoryUtils.createSpecialItem(Material.BRICK, (short) 0, ChatColor.RESET + "Repair", new String[] {ChatColor.RED + "" + ChatColor.MAGIC + "Contraband", ChatColor.RESET + "" + ChatColor.GOLD + "Completely repairs the turret", ChatColor.RESET + "" + ChatColor.GOLD + "for the turret's full cost."}));
					newGUI.setItem(8, InventoryUtils.createSpecialItem(Material.REDSTONE, (short) 0, ChatColor.RESET + "Current Energy", new String[] {ChatColor.RED + "" + ChatColor.MAGIC + "Contraband", ChatColor.RESET + "" + ChatColor.GRAY + Double.toString(towerMachine.getEnergy())}));
					newGUI.setItem(26, InventoryUtils.createSpecialItem(Material.WOOD_DOOR, (short) 0, ChatColor.RESET + "Back to Main GUI", new String[] {ChatColor.RED + "" + ChatColor.MAGIC + "Contraband"}));
					
					if(t.getTurretType().equals(TurretType.CHEMICAL) || t.getTurretType().equals(TurretType.GAS)) {
						ItemStack potions = new ItemStack(Material.BREWING_STAND_ITEM, 1);
						
						List <PotionType> potionTypes = t.unlockedPotionTypes;
						ItemMeta potionsMeta = potions.getItemMeta();
						List<String> potionsLoreList = new ArrayList<String>();
						
						
						potionsMeta.setDisplayName(ChatColor.RESET +  "Usable Potion Types");
						for(int i=0; i<potionTypes.size(); i++) {
							PotionType potionType = potionTypes.get(i);
							potionsLoreList.add(ChatColor.RESET + "" + ChatColor.GOLD + potionType.toString());
						}
						potionsLoreList.add(ChatColor.RED + "" + ChatColor.MAGIC + "Contraband");
						potionsMeta.setLore(potionsLoreList);
						potions.setItemMeta(potionsMeta);
						newGUI.setItem(3, potions);
						newGUI.setItem(4, InventoryUtils.createSpecialItem(Material.FURNACE, (short) 0, ChatColor.RESET + "Toggle Friendly Fire", new String[] {ChatColor.RED + "" + ChatColor.MAGIC + "Contraband", ChatColor.RESET + Boolean.toString(t.friendlyFire), ChatColor.RESET + "" + ChatColor.GOLD + "When this is true, this", ChatColor.RESET + "" + ChatColor.GOLD + "turret will fire at fellow", ChatColor.RESET + "" + ChatColor.GOLD + "members of your empire."}));
					}
					
			}
			else if(inv == 1) {
				newGUI.setItem(18, InventoryUtils.createSpecialItem(Material.BEACON, (short) 0, ChatColor.RESET + "Back", new String[] {ChatColor.RED + "" + ChatColor.MAGIC + "Contraband", ChatColor.RESET + "" + ChatColor.GOLD + "Go back to the main menu."}));
			}
		
	}
	
	public boolean checkBlocks(Block mainBlock) {
		
		ArrayList<Block> blockList = new ArrayList<Block>();
		Block blockTwo = mainBlock.getRelative(BlockFace.UP);
		Block blockThree = blockTwo.getRelative(BlockFace.UP);
		Block blockFour = blockThree.getRelative(BlockFace.UP);
		Block blockFive = blockFour.getRelative(BlockFace.UP);
		
		blockList.add(blockTwo);
		blockList.add(blockThree);
		blockList.add(blockFour);
		blockList.add(blockFive);
		
		for(Block block : blockList) {
				if(!block.getType().equals(Material.AIR)) {
					owner.sendMessage("There is no room to build that turret!");
					return false;
				}
			}
		return true;
	}
	
	public static ItemStack createSpecialItem(ItemStack item, String name, String[] lore) {
		
		ItemStack newItem = item;
		ItemMeta itemMeta = item.getItemMeta();
		
		itemMeta.setDisplayName(name);
		itemMeta.setLore(Arrays.asList(lore));
		
		newItem.setItemMeta(itemMeta);
		
		return newItem;
		
	}
	
	public static PotionType getPotionType(ItemMeta meta) {
		
		String string = meta.toString();
		
		if(string.contains("water")) {
			return PotionType.WATER;
		}
		else if(string.contains("mundane")) {
			return PotionType.MUNDANE;
		}
		else if(string.contains("thick")) {
			return PotionType.THICK;
		}
		else if(string.contains("awkward")) {
			return PotionType.AWKWARD;
		}
		else if(string.contains("night_vision")) {
			return PotionType.NIGHT_VISION;
		}
		else if(string.contains("invisibility")) {
			return PotionType.INVISIBILITY;
		}
		else if(string.contains("leaping")) {
			return PotionType.JUMP;
		}
		else if(string.contains("fire_resistance")) {
			return PotionType.FIRE_RESISTANCE;
		}
		else if(string.contains("swiftness")) {
			return PotionType.SPEED;
		}
		else if(string.contains("slowness")) {
			return PotionType.SLOWNESS;
		}
		else if(string.contains("water_breathing")) {
			return PotionType.WATER_BREATHING;
		}
		else if(string.contains("healing")) {
			return PotionType.INSTANT_HEAL;
		}
		else if(string.contains("harming")) {
			return PotionType.INSTANT_DAMAGE;
		}
		else if(string.contains("poison")) {
			return PotionType.POISON;
		}
		else if(string.contains("regeneration")) {
			return PotionType.REGEN;
		}
		else if(string.contains("strength")) {
			return PotionType.STRENGTH;
		}
		else if(string.contains("weakness")) {
			return PotionType.WEAKNESS;
		}
		else if(string.contains("luck")) {
			return PotionType.LUCK;
		}
		else if(string.contains("empty")) {
			return PotionType.UNCRAFTABLE;
		}
		
		return PotionType.AWKWARD;
	}
	
}
