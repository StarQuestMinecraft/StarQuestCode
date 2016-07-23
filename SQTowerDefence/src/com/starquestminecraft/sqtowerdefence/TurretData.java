package com.starquestminecraft.sqtowerdefence;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.bukkit.Material;
import org.bukkit.potion.PotionType;

public class TurretData implements Serializable {
	
	private static final long serialVersionUID = -760950054579858123L;
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
	
	public TurretData(Turret turret) {
		
		upgrades = turret.upgrades;
		possibleUpgrades = turret.possibleUpgrades;
		conflictingUpgrades = turret.conflictingUpgrades;
		unlockedPotionTypes = turret.unlockedPotionTypes;
		guiX = turret.guiX;
		guiY = turret.guiY;
		guiZ = turret.guiZ;
		parentID = turret.parentID;
		type = turret.type;
		turretEmpire = turret.turretEmpire;
		owner = turret.owner;
		targetedPlayer = turret.targetedPlayer;
		worldName = turret.worldName;
		speed = turret.speed;
		damage = turret.damage;
		accuracy = turret.accuracy;
		range = turret.range;
		cost = turret.cost;
		ammo = turret.ammo;
		runs = turret.runs;
		name = turret.name;
		blockOneType = turret.blockOneType;
		blockTwoType = turret.blockTwoType;
		blockThreeType = turret.blockThreeType;
		blockFourType = turret.blockFourType;
		hasConflicts = turret.hasConflicts;
		friendlyFire = turret.friendlyFire;
		showInGUI = turret.showInGUI;
		
	}
	
	public Turret getTurretFromData() {
		
		Turret turret = new Turret(TurretType.BASE, null, null, 0, 0.0, 0.0, 0.0, 0.0, "Base Tower");
		
		turret.upgrades = upgrades;
		turret.possibleUpgrades = possibleUpgrades;
		turret.conflictingUpgrades = conflictingUpgrades;
		turret.unlockedPotionTypes = unlockedPotionTypes;
		turret.guiX = guiX;
		turret.guiY = guiY;
		turret.guiZ = guiZ;
		turret.parentID = parentID;
		turret.type = type;
		turret.turretEmpire = turretEmpire;
		turret.owner = owner;
		turret.targetedPlayer = targetedPlayer;
		turret.worldName = worldName;
		turret.speed = speed;
		turret.damage = damage;
		turret.accuracy = accuracy;
		turret.range = range;
		turret.cost = cost;
		turret.ammo = ammo;
		turret.runs = runs;
		turret.name = name;
		turret.blockOneType = blockOneType;
		turret.blockTwoType = blockTwoType;
		turret.blockThreeType = blockThreeType;
		turret.blockFourType = blockFourType;
		turret.hasConflicts = hasConflicts;
		turret.friendlyFire = friendlyFire;
		turret.showInGUI = showInGUI;
		
		return turret;
		
	}
	
	
}
