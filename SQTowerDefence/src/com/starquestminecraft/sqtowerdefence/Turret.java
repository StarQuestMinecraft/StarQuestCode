package com.starquestminecraft.sqtowerdefence;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.OfflinePlayer;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.Dropper;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Fireball;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.entity.ThrownPotion;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.potion.Potion;
import org.bukkit.potion.PotionType;
import org.bukkit.scheduler.BukkitTask;
import org.bukkit.util.Vector;

import com.starquestminecraft.sqtechbase.SQTechBase;
import com.starquestminecraft.sqtechbase.objects.GUIBlock;
import com.starquestminecraft.sqtechbase.objects.Machine;
import com.starquestminecraft.sqtechbase.objects.Network;
import com.starquestminecraft.sqtechbase.util.ObjectUtils;
import com.whirlwindgames.dibujaron.sqempire.Empire;
import com.whirlwindgames.dibujaron.sqempire.database.object.EmpirePlayer;

import net.milkbowl.vault.economy.Economy;


public class Turret implements Serializable {
	
	private static final long serialVersionUID = -1752155270951436968L;
	List<Upgrade> upgrades = new ArrayList<Upgrade>();
	List<Upgrade> possibleUpgrades = new ArrayList<Upgrade>();
	List<Upgrade> conflictingUpgrades = new ArrayList<Upgrade>();
	List<PotionType> unlockedPotionTypes = new ArrayList<PotionType>();
	Double guiX = 0.0;
	Double guiY = 0.0;
	Double guiZ = 0.0;
	Integer parentID = 0;
	TurretType type = TurretType.BASE;
	String turretEmpire = "NONE";
	String owner;
	String targetedPlayer;
	String worldName;
	Integer speed = 0;
	Double damage = 0.0;
	Double accuracy = 0.0;
	Double range = 0.0;
	Double cost = 0.0;
	Integer ammo = 0;
	Integer runs = 0;
	String name = "Base Turret";
	Integer blockOneType;
	Integer blockTwoType;
	Integer blockThreeType;
	Integer blockFourType;
	
	boolean hasConflicts = false;
	boolean friendlyFire = false;
	boolean showInGUI = true;
	
	public Turret(TurretType turretType, ArrayList<Upgrade> buyableUpgrades, ArrayList<Upgrade> conflicts, Integer fireRate, Double turretDamage, Double turretAccuracy, Double turretCost, Double turretRange, String turretName) {
		type = turretType;
		speed = fireRate;
		damage = turretDamage;
		accuracy = turretAccuracy;
		cost = turretCost;
		name = turretName;
		range = turretRange;
		unlockedPotionTypes.add(PotionType.POISON);
		if(buyableUpgrades != null) {
			possibleUpgrades = buyableUpgrades;
		}
		if(conflicts != null) {
			conflictingUpgrades = conflicts;
		}
	}
	
	public TurretType getTurretType() {
		return type;
	}
	
	public Integer getFireRate() {
		return speed;
	}
	
	public Double getDamage() {
		return damage;
	}
	
	public Double getAccuracy() {
		return accuracy;
	}
	
	public List<Upgrade> getUpgrades() {
		return upgrades;
	}
	
	public String getName() {
		return name;
	}
	
	public Turret createNewTurret() {
		ArrayList<Upgrade> newPossibleUpgrades = new ArrayList<Upgrade>();
		ArrayList<Upgrade> newConflictingUpgrades = new ArrayList<Upgrade>();
		
		if(!possibleUpgrades.isEmpty()) {
			for(int i=0; i<possibleUpgrades.size(); i++) {
				Upgrade u = possibleUpgrades.get(i).createNewUpgrade();
				newPossibleUpgrades.add(u);
			}
		}
		
		if(!conflictingUpgrades.isEmpty()) {
			for(int i=0; i<conflictingUpgrades.size(); i++) {
				Upgrade u = conflictingUpgrades.get(i).createNewUpgrade();
				newConflictingUpgrades.add(u);
			}
		}
		
		
		
		Turret turret = new Turret(type, newPossibleUpgrades, newConflictingUpgrades, speed, damage, accuracy, cost, range, name);
		return turret;
	}
	
	public void runTurret() {
		
		SQTowerDefence plugin = com.starquestminecraft.sqtowerdefence.SQTowerDefence.sqtdInstance;
		
		Machine parentMachine = null;
		
		World world = Bukkit.getWorld(worldName);
		
		Location guiBlockLocation = new Location(world, guiX, guiY, guiZ);
		
		if(runs >= 5) {
			runs = 0;
		}
		
		for(Machine machine : SQTechBase.machines) {
			
			if(machine.getGUIBlock().getLocation().equals(guiBlockLocation)) {
				
				parentID = machine.getGUIBlock().id;
				parentMachine = machine;
				
			}
			
		}
		
		if(parentMachine == null) {
			
			for(Machine machine : SQTechBase.machines) {
				
				if(machine.getGUIBlock().id == parentID) {
					
					parentMachine = machine;
					
				}
				
			}
			
		}
		
		if(!parentMachine.check()) {
			
			return;
			
		}
		
		Location actionPoint = parentMachine.getGUIBlock().getLocation().clone();
		actionPoint.add(0.0, 4.0, 0.0);
		Player targetPlayer = null;

		if(targetedPlayer != null) {
			targetPlayer = Bukkit.getPlayer(targetedPlayer);
		}
		
		if(parentMachine.getEnergy() < 10) {
			BukkitTask task = new SQTDTask(plugin, this).runTaskLater(plugin, 40);
			return;
		}
		
		if(!checkTurret()) {
			BukkitTask task = new SQTDTask(plugin, this).runTaskLater(plugin, 40);
			return;
		}
		
		Block blockOne = parentMachine.getGUIBlock().getLocation().getBlock().getRelative(BlockFace.UP);
		Location runLocation = actionPoint.clone();
		runLocation.add(0.5, 0.5, 0.5);
		
		if(runs == 0) {

			if(!Bukkit.getServer().getOnlinePlayers().isEmpty()) {
				
				for(Player p : Bukkit.getServer().getOnlinePlayers()) {
					
				if(EmpirePlayer.getOnlinePlayer(p) != null) {
					
					EmpirePlayer ep = EmpirePlayer.getOnlinePlayer(p);
					
					
					if(!friendlyFire) {

						if(ep.getEmpire() != Empire.fromString(turretEmpire)) {
							if(targetPlayer != null) {
								
									if(p.getLocation().distance(runLocation) < targetPlayer.getLocation().distance(runLocation)) {
										targetedPlayer = p.getDisplayName();
										targetPlayer = p;
									}
									
							}
							else {
								targetedPlayer = p.getDisplayName();
								targetPlayer = p;
							}
						}
					}
					else {

						if(ep.getEmpire() == Empire.fromString(turretEmpire)) {
							if(targetPlayer != null) {
								
									if(p.getLocation().distance(runLocation) < targetPlayer.getLocation().distance(runLocation)) {
										targetedPlayer = p.getDisplayName();
										targetPlayer = p;
									}
									
							}
							else {
								targetedPlayer = p.getDisplayName();
								targetPlayer = p;
							}
						}
						
					}
				
				}
				}
				
			}
			else {
				BukkitTask task = new SQTDTask(plugin, this).runTaskLater(plugin, 60);
				return;
			}
		}
		
		if(targetPlayer == null || !targetPlayer.isOnline()) {
			BukkitTask task = new SQTDTask(plugin, this).runTaskLater(plugin, 60);
			return;
		}
				
		runs = runs + 1;
		
 		Location target = targetPlayer.getEyeLocation();
		
		Double zDist = target.getZ() - runLocation.getZ();
		Double xDist = target.getX() - runLocation.getX();
		Double yDist = target.getY() - runLocation.getY();
		Double hyp = target.distance(runLocation);

		if(hyp > range) {
			BukkitTask task = new SQTDTask(plugin, this).runTaskLater(plugin, 20);
			return;
		}
		
		Double ySin = yDist / hyp;
		Double cos = zDist / hyp;
		Double sin = xDist / hyp;
		
		runLocation.add(sin * 2, ySin * 2, cos * 2);

		
		Vector vector = new Vector(sin, ySin, cos);
		
		Dropper dropper = (Dropper) blockOne.getState();
		Inventory inv = dropper.getInventory();
		
		if(type == TurretType.ARROW) {
			if(inv.contains(Material.ARROW)) {
				ItemStack item = new ItemStack(Material.ARROW, 1);
				Arrow arrow = null;
				inv.removeItem(item);
				Projectile projectile = world.spawnArrow(runLocation, vector, 1.0f, accuracy.floatValue());
				projectile.setMetadata("damage", new FixedMetadataValue(plugin, damage));
				projectile.setGravity(false);
				projectile.setCustomName("SQTDProjectile");
			}
		}
		else if(type == TurretType.CHEMICAL) {
			for(int i=0; i<inv.getSize(); i++) {
				ItemStack invItem = inv.getItem(i);
				if(invItem != null) {
					if(invItem.getType().equals(Material.SPLASH_POTION)) {
						PotionType potionType = com.starquestminecraft.sqtowerdefence.TowerGUI.getPotionType(invItem.getItemMeta());
						if(unlockedPotionTypes.contains(potionType)) {
							
							ThrownPotion thrownPotion = world.spawn(runLocation, ThrownPotion.class);
							//new Potion(potionType).splash().toItemStack(1)
							thrownPotion.setItem(invItem);
							thrownPotion.setVelocity(vector);
							thrownPotion.setGravity(false);
							thrownPotion.setCustomName("SQTDProjectile");
							OfflinePlayer ofp = Bukkit.getOfflinePlayer(owner);
							Player p = ofp.getPlayer();
							thrownPotion.setShooter(p);
							inv.clear(i);
							i = 9001;
							
						}
					}
				}
			}
		}
		else if(type == TurretType.GAS) {
			for(int i=0; i<inv.getSize(); i++) {
				ItemStack invItem = inv.getItem(i);
				if(invItem != null) {
					if(invItem.getType().equals(Material.SPLASH_POTION) || invItem.getType().equals(Material.LINGERING_POTION)) {
						PotionType potionType = com.starquestminecraft.sqtowerdefence.TowerGUI.getPotionType(invItem.getItemMeta());
						if(unlockedPotionTypes.contains(potionType)) {
							
							ThrownPotion thrownPotion = world.spawn(runLocation, ThrownPotion.class);
							//new Potion(potionType).splash().toItemStack(1)
							thrownPotion.setItem(invItem);
							thrownPotion.setVelocity(vector);
							thrownPotion.setGravity(false);
							thrownPotion.setCustomName("SQTDProjectile");
							OfflinePlayer ofp = Bukkit.getOfflinePlayer(owner);
							Player p = ofp.getPlayer();
							thrownPotion.setShooter(p);
							inv.clear(i);
							i = 9001;
							
						}
					}
				}
			}
		}
		else if(type == TurretType.CANNON) {
			if(inv.contains(Material.FIREBALL)) {
				ItemStack item = new ItemStack(Material.FIREBALL, 1);
				inv.removeItem(item);
				Fireball fireBall = world.spawn(runLocation, Fireball.class);
				fireBall.setDirection(vector);
				fireBall.setYield((float) (damage + 0.0f));
				fireBall.setGravity(false);
				fireBall.setCustomName("SQTDProjectile");
			}
		}
		else if(type == TurretType.ANTIAIR) {
			if(inv.contains(Material.FIREBALL)) {
				if(Math.asin(ySin) < 2.61799635 && Math.asin(ySin) > 0.52359927) {
					ItemStack item = new ItemStack(Material.FIREBALL, 1);
					inv.removeItem(item);
					Fireball fireBall = world.spawn(runLocation, Fireball.class);
					fireBall.setDirection(vector);
					fireBall.setYield((float) (damage + 0.0f));
					fireBall.setGravity(false);
					fireBall.setCustomName("SQTDProjectile");
				}
			}
		}
		else if(type == TurretType.FLAK) {
			if(inv.contains(Material.FIREBALL)) {
				if(Math.asin(ySin) < 2.61799635 && Math.asin(ySin) > 0.52359927) {
					if(ammo > 0) {
						ammo = ammo - 1;
						ItemStack item = new ItemStack(Material.FIREBALL, 1);
						inv.removeItem(item);
						Fireball fireBall = world.spawn(runLocation, Fireball.class);
						fireBall.setDirection(vector);
						fireBall.setYield((float) (damage + 0.0f));
						fireBall.setGravity(false);
						fireBall.setCustomName("SQTDProjectile");
						BukkitTask task = new SQTDTask(plugin, this).runTaskLater(plugin, Integer.toUnsignedLong(4));
						if(runs == 10) {
							runs = 0;
						}
						parentMachine.setEnergy(parentMachine.getEnergy() - 10);
						return;
					}
					else {
						ammo = 4;
					}
				}
			}
		}

		parentMachine.setEnergy(parentMachine.getEnergy() - 10);

		BukkitTask task = new SQTDTask(plugin, this).runTaskLater(plugin, Integer.toUnsignedLong(speed));
	}
	
	public void setTurretBlock(Block block, Integer blockNumber) {
		switch (blockNumber) {
			case 1:
				blockOneType = block.getTypeId();
				break;
			case 2:
				blockTwoType = block.getTypeId();
				break;
			case 3:
				blockThreeType = block.getTypeId();
				break;
			case 4:
				blockFourType = block.getTypeId();
				break;
		}
	}
	
	public void setWorld(String newWorld) {
		worldName = newWorld;
	}
	
	public void addPotionType(PotionType potionType) {
		unlockedPotionTypes.add(potionType);
	}

	public void changeTurretType(Turret turret) {
		possibleUpgrades = turret.possibleUpgrades;
		conflictingUpgrades = turret.conflictingUpgrades;
		type = turret.type;
		owner = turret.owner;
		speed = turret.speed;
		damage = turret.damage;
		accuracy = turret.accuracy;
		range = turret.range;
		cost = turret.cost;
		name = turret.name;
		hasConflicts = turret.hasConflicts;
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
			default:
				break;
		}
		
		Machine parentMachine = null;
		
		World world = Bukkit.getWorld(worldName);
		
		Location guiBlockLocation = new Location(world, guiX, guiY, guiZ);
		
		for(Network n : SQTechBase.networks) {
			
			for(GUIBlock g : n.getGUIBlocks()) {
				
				if(g.getLocation().equals(guiBlockLocation)) {
					
					for(Machine machine : ObjectUtils.getAllMachinesOfType("Tower")) {
						
						if(machine.getGUIBlock().equals(g)) {
							
							parentMachine = machine;
							
						}
						
					}
					
				}
				
			}
			
		}
		
		Location actionPoint = parentMachine.getGUIBlock().getLocation();
		actionPoint.add(0.0, 4.0, 0.0);
		
		actionPoint.getBlock().setType(material);
	}

	public void setTurretType(TurretType turretType) {
		type = turretType;
	}
	
	public void setFireRate(Integer fireRate) {
		speed = fireRate;
	}
	
	public void setDamage(Double turretDamage) {
		damage = turretDamage;
	}
	
	public void setAccuracy(Double turretAccuracy) {
		accuracy = turretAccuracy;
	}
	
	public void setRange(Double turretRange) {
		range = turretRange;
	}
	
	public void addUpgrade(Upgrade upgrade) {
		upgrades.add(upgrade);
	}
	
	public void removeUpgrade(Upgrade upgrade) {
		if(upgrades.contains(upgrade)) {
			upgrades.remove(upgrade);
		}
	}
	
	public void removeUpgrade(Integer i) {
		if(i < upgrades.size()) {
			upgrades.remove(i);
		}
	}
	
	public void setUpgrades(ArrayList<Upgrade> upgradeList) {
		upgrades = upgradeList;
	}
	
	public boolean buyNewUpgrade(Player player, Upgrade upgrade) {
		
		if(upgrade.getUpgradeType().equals(UpgradeType.TOWER)) {
			
			String towerName = upgrade.customName;
			ArrayList<Turret> turretList = com.starquestminecraft.sqtowerdefence.SQTowerDefence.getTurretList();
			
			for(int i=0; i<turretList.size(); i++) {
				
				Turret t = turretList.get(i);
				
				
				if(t.getName().equals(towerName)) {
					
					List<Upgrade> upgradeList = this.getUpgrades();
					
					for(int x=0; x<upgradeList.size(); x++) {
						Upgrade u = upgradeList.get(x);
						if(t.conflictingUpgrades.contains(u)) {
							this.removeUpgrade(u);
						}
						if(!t.possibleUpgrades.contains(u)) {
							this.removeUpgrade(u);
						}
					}
					
					this.changeTurretType(t);
					
					return true;
				}
			
			}
		}
		
			if(possibleUpgrades.contains(upgrade)) {
			Economy economy = com.starquestminecraft.sqtowerdefence.SQTowerDefence.sqtdInstance.getEconomy();
			Double balance = economy.getBalance(player);
			Double cost = upgrade.getCurrentCost();
			if(balance > cost) {
				switch (upgrade.getUpgradeType()) {
					case DAMAGE:
						setDamage(damage + upgrade.getBoost());
						break;
					case SPEED:
						setFireRate(speed - upgrade.getBoost().intValue());
						break;
					case ACCURACY:
						setAccuracy(accuracy - upgrade.getBoost());
						break;
					case RANGE:
						setRange(range + upgrade.getBoost());
						break;
					default:
						break;
				}
				
				upgrade.addLevel();
				economy.withdrawPlayer(player, cost);
				
				if(upgrade.level == upgrade.maxLevel) {
					possibleUpgrades.remove(upgrade);
				}
				
				if(!upgrades.isEmpty())	{
					for(int i=0; i<upgrades.size(); i++) {
						Upgrade u = upgrades.get(i);
						if(u.getUpgradeType() == upgrade.getUpgradeType()) {
							upgrades.remove(u);
						}
					}
				}
				
				upgrades.add(upgrade);
				return true;
			
			}
			else {
				player.sendMessage("You cannot afford this upgrade!");
				return false;
			}
			
			}
			else if (conflictingUpgrades.contains(upgrade)) {
				Economy economy = com.starquestminecraft.sqtowerdefence.SQTowerDefence.sqtdInstance.getEconomy();
				Double balance = economy.getBalance(player);
				Double cost = upgrade.getCurrentCost();
				if(balance > cost) {
					switch (upgrade.getUpgradeType()) {
					case DAMAGE:
						setDamage(damage + upgrade.getBoost());
						break;
					case SPEED:
						setFireRate(speed - upgrade.getBoost().intValue());
						break;
					case ACCURACY:
						setAccuracy(accuracy - upgrade.getBoost());
						break;
					case RANGE:
						setRange(range + upgrade.getBoost());
						break;
					default:
						break;
					}
					upgrade.addLevel();
					economy.withdrawPlayer(player, cost);
					
					hasConflicts = true;
					possibleUpgrades.add(upgrade);
					
					if(!upgrades.isEmpty())	{
						for(int i=0; i<upgrades.size(); i++) {
							Upgrade u = upgrades.get(i);
							if(u.getUpgradeType() == upgrade.getUpgradeType()) {
								upgrades.remove(u);
							}
						}
					}
					
					upgrades.add(upgrade);
					return true;
				
				}
				else {
					player.sendMessage("You cannot afford this upgrade!");
					return false;
				}
			
			}
			else {
				player.sendMessage("This turret cannot have that upgrade!");
				return false;
			}
	}
	
	public boolean checkTurret() {
		
		Machine parentMachine = null;
		
		World world = Bukkit.getWorld(worldName);
		
		Location guiBlockLocation = new Location(world, guiX, guiY, guiZ);
					
			for(Machine machine : SQTechBase.machines) {
						
				if(machine.getGUIBlock().getLocation().equals(guiBlockLocation)) {
					
					parentMachine = machine;
					
				}
				
			}

		
		Block blockOne = parentMachine.getGUIBlock().getLocation().getBlock().getRelative(BlockFace.UP);
		Block blockTwo = blockOne.getRelative(BlockFace.UP);
		Block blockThree = blockTwo.getRelative(BlockFace.UP);
		Block blockFour = blockThree.getRelative(BlockFace.UP);

		if(blockOne.getTypeId() == blockOneType) {
			if(blockTwo.getTypeId() == blockTwoType) {
				if(blockThree.getTypeId() == blockThreeType) {
					if(blockFour.getTypeId() == blockFourType) {
						return true;
					}
				}
			}
		}
		
		return false;
	}
	
	public void toggleFriendlyFire() {
				
		if(friendlyFire) {
			
			friendlyFire = false;
			
		}
		else {

			friendlyFire = true;
			
		}
		
	}
	
}
