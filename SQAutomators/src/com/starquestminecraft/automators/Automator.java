package com.starquestminecraft.automators;

import java.util.ArrayList;

import org.bukkit.entity.Player;
import org.bukkit.inventory.Recipe;

import com.starquestminecraft.sqtechbase.objects.GUIBlock;
import com.starquestminecraft.sqtechbase.objects.Machine;
import com.starquestminecraft.sqtechbase.objects.MachineType;

public class Automator extends Machine {

	Recipe recipe;
	Integer screen = 0;
	Integer page = 0;
	Double upgradeCost = com.starquestminecraft.automators.SQAutomators.sqamInstance.crafterUpgradeCost;
	Player owner;
	ArrayList<ArrayList<Recipe>> pageList = new ArrayList<ArrayList<Recipe>>();
	ArrayList<Recipe> currentRecipes = new ArrayList<Recipe>();
	ArrayList<Recipe> openRecipes = new ArrayList<Recipe>();
	
	public Automator(int energy, GUIBlock guiBlock, MachineType machineType) {
		super(energy, guiBlock, machineType);
		data.put("level", 1);
	}

}
