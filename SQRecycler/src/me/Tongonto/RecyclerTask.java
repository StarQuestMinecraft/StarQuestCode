package me.Tongonto;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.Dropper;
import org.bukkit.block.Furnace;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Entity;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;
import org.bukkit.util.Vector;

import net.minecraft.server.v1_8_R3.EntityItem;

public class RecyclerTask extends BukkitRunnable{
	
	ItemStack air = new ItemStack(Material.AIR);
	
	int count = 0;
	int newAmount = 0;
	
	private final SQRecycler recyclerPlugin;
	
	public RecyclerTask(SQRecycler plugin){
		this.recyclerPlugin = plugin;
	}
	
	public void runRecyclerMachine(RecyclerMachine recyclerMachine){
		FileConfiguration recyclerConfig = recyclerPlugin.recyclerConfig;
		
		Dropper inputDropper = recyclerMachine.getInputDropper();
		Furnace recyclerFurnace = recyclerMachine.getRecyclerFurnace();
		Dropper outputDropper = recyclerMachine.getOutputDropper();
		
		ItemStack recipeBlueprint = recyclerFurnace.getInventory().getSmelting();
		if(recipeBlueprint != null){
		if(recyclerConfig.contains(recipeBlueprint.getType().toString())){
			String ingredientString = recyclerConfig.get(recipeBlueprint.getType().toString() + ".ingredient").toString();
			String outputString = recyclerConfig.get(recipeBlueprint.getType().toString() + ".output").toString();
			String costString = recyclerConfig.get(recipeBlueprint.getType().toString() + ".cost").toString();
			String productionString = recyclerConfig.get(recipeBlueprint.getType().toString() + ".production").toString();
			
			ItemStack ingredientItem = new ItemStack(Material.getMaterial(ingredientString));
			ItemStack producedItem = new ItemStack(Material.getMaterial(outputString));
			int ingredientCost = Integer.parseInt(costString);
			int numberProduced = Integer.parseInt(productionString);
			producedItem.setAmount(numberProduced);
			
			producedItem.setDurability(recipeBlueprint.getDurability());
			
			boolean dontGo = false;
			
			ItemStack theFurnaceStack = recyclerFurnace.getInventory().getResult();
			if(theFurnaceStack != null){
				newAmount = theFurnaceStack.getAmount();
				if(newAmount == ingredientCost && theFurnaceStack.getType() == ingredientItem.getType()){
					recyclerFurnace.getInventory().setResult(air);
					outputDropper.getInventory().addItem(producedItem);
					dontGo = true;
				}
				else if(newAmount > ingredientCost && theFurnaceStack.getType() == ingredientItem.getType()){
					newAmount = newAmount - ingredientCost;
					theFurnaceStack.setAmount(newAmount);
					outputDropper.getInventory().addItem(producedItem);
					dontGo = true;
				}
			}
			if(dontGo == false){
			if(inputDropper.getInventory().contains(ingredientItem.getType())){
				inputDropper.getInventory().removeItem(ingredientItem);
				
				ItemStack furnaceStack = recyclerFurnace.getInventory().getResult();
				if(furnaceStack != null){
					if(furnaceStack.getType() == ingredientItem.getType()){
						newAmount = furnaceStack.getAmount();
						newAmount = furnaceStack.getAmount() + 1;
						furnaceStack.setAmount(newAmount);
						recyclerFurnace.getInventory().setResult(furnaceStack);
					}
				}
				else{
					recyclerFurnace.getInventory().setResult(ingredientItem);
				}
			}
			}
		}
		else if(recipeBlueprint.getType() == Material.WOOD){
			ItemStack plank = new ItemStack(Material.WOOD);
			plank.setDurability(recipeBlueprint.getDurability());
			ItemStack slab = new ItemStack(Material.WOOD_STEP);
			ItemStack fence = new ItemStack(Material.FENCE);
			ItemStack sprucefence = new ItemStack(Material.SPRUCE_FENCE);
			ItemStack birchfence = new ItemStack(Material.BIRCH_FENCE);
			ItemStack junglefence = new ItemStack(Material.JUNGLE_FENCE);
			ItemStack acaciafence = new ItemStack(Material.ACACIA_FENCE);
			ItemStack darkoakfence = new ItemStack(Material.DARK_OAK_FENCE);
			ItemStack gate = new ItemStack(Material.FENCE_GATE);
			ItemStack sprucegate = new ItemStack(Material.SPRUCE_FENCE_GATE);
			ItemStack birchgate = new ItemStack(Material.BIRCH_FENCE_GATE);
			ItemStack junglegate = new ItemStack(Material.JUNGLE_FENCE_GATE);
			ItemStack acaciagate = new ItemStack(Material.ACACIA_FENCE_GATE);
			ItemStack darkoakgate = new ItemStack(Material.DARK_OAK_FENCE_GATE);
			ItemStack door = new ItemStack(Material.WOOD_DOOR);
			ItemStack sprucedoor = new ItemStack(Material.SPRUCE_DOOR_ITEM);
			ItemStack birchdoor = new ItemStack(Material.BIRCH_DOOR_ITEM);
			ItemStack jungledoor = new ItemStack(Material.JUNGLE_DOOR_ITEM);
			ItemStack acaciadoor = new ItemStack(Material.ACACIA_DOOR_ITEM);
			ItemStack darkoakdoor = new ItemStack(Material.DARK_OAK_DOOR_ITEM);
			ItemStack stair = new ItemStack(Material.WOOD_STAIRS);
			ItemStack sprucestair = new ItemStack(Material.SPRUCE_WOOD_STAIRS);
			ItemStack birchstair = new ItemStack(Material.BIRCH_WOOD_STAIRS);
			ItemStack junglestair = new ItemStack(Material.JUNGLE_WOOD_STAIRS);
			ItemStack acaciastair = new ItemStack(Material.ACACIA_STAIRS);
			ItemStack darkoakstair = new ItemStack(Material.DARK_OAK_STAIRS);
			ItemStack sign = new ItemStack(Material.SIGN);
			ItemStack trapdoor = new ItemStack(Material.TRAP_DOOR);
			ItemStack chest = new ItemStack(Material.CHEST);
			slab.setDurability(recipeBlueprint.getDurability());
			boolean dontGo = false;
			ItemStack ingredientItem = null;
			ItemStack producedItem = plank;
			int ingredientCost = 0;
			int numberProduced = 0;
			
			Inventory dropperInventory = inputDropper.getInventory();
			Inventory furnaceInventory = recyclerFurnace.getInventory();
			
			if(plank.getDurability() == 0){
				if(dropperInventory.containsAtLeast(fence, 1) || furnaceInventory.containsAtLeast(fence, 1)){
					ingredientItem = fence;
					ingredientCost = recyclerConfig.getInt("PLANKVARS.fencecost");
					numberProduced = recyclerConfig.getInt("PLANKVARS.fenceprod");
				}
				else if(dropperInventory.containsAtLeast(gate, 1) || furnaceInventory.containsAtLeast(gate, 1)){
					ingredientItem = gate;
					ingredientCost = recyclerConfig.getInt("PLANKVARS.gatecost");
					numberProduced = recyclerConfig.getInt("PLANKVARS.gateprod");
				}
				else if(dropperInventory.containsAtLeast(door, 1) || furnaceInventory.containsAtLeast(door, 1)){
					ingredientItem = door;
					ingredientCost = recyclerConfig.getInt("PLANKVARS.doorcost");
					numberProduced = recyclerConfig.getInt("PLANKVARS.doorprod");
				}
				else if(dropperInventory.containsAtLeast(stair, 1) || furnaceInventory.containsAtLeast(stair, 1)){
					ingredientItem = stair;
					ingredientCost = recyclerConfig.getInt("PLANKVARS.staircost");
					numberProduced = recyclerConfig.getInt("PLANKVARS.stairprod");
				}
			}
			else if(plank.getDurability() == 1){
				if(dropperInventory.containsAtLeast(sprucefence, 1) || furnaceInventory.containsAtLeast(sprucefence, 1)){
					ingredientItem = sprucefence;
					ingredientCost = recyclerConfig.getInt("PLANKVARS.fencecost");
					numberProduced = recyclerConfig.getInt("PLANKVARS.fenceprod");
				}
				else if(dropperInventory.containsAtLeast(sprucegate, 1) || furnaceInventory.containsAtLeast(sprucegate, 1)){
					ingredientItem = sprucegate;
					ingredientCost = recyclerConfig.getInt("PLANKVARS.gatecost");
					numberProduced = recyclerConfig.getInt("PLANKVARS.gateprod");
				}
				else if(dropperInventory.containsAtLeast(sprucedoor, 1) || furnaceInventory.containsAtLeast(sprucedoor, 1)){
					ingredientItem = sprucedoor;
					ingredientCost = recyclerConfig.getInt("PLANKVARS.doorcost");
					numberProduced = recyclerConfig.getInt("PLANKVARS.doorprod");
				}
				else if(dropperInventory.containsAtLeast(sprucestair, 1) || furnaceInventory.containsAtLeast(sprucestair, 1)){
					ingredientItem = sprucestair;
					ingredientCost = recyclerConfig.getInt("PLANKVARS.staircost");
					numberProduced = recyclerConfig.getInt("PLANKVARS.stairprod");
				}
			}
			else if(plank.getDurability() == 2){
				if(dropperInventory.containsAtLeast(birchfence, 1) || furnaceInventory.containsAtLeast(birchfence, 1)){
					ingredientItem = birchfence;
					ingredientCost = recyclerConfig.getInt("PLANKVARS.fencecost");
					numberProduced = recyclerConfig.getInt("PLANKVARS.fenceprod");
				}
				else if(dropperInventory.containsAtLeast(birchgate, 1) || furnaceInventory.containsAtLeast(birchgate, 1)){
					ingredientItem = birchgate;
					ingredientCost = recyclerConfig.getInt("PLANKVARS.gatecost");
					numberProduced = recyclerConfig.getInt("PLANKVARS.gateprod");
				}
				else if(dropperInventory.containsAtLeast(birchdoor, 1) || furnaceInventory.containsAtLeast(birchdoor, 1)){
					ingredientItem = birchdoor;
					ingredientCost = recyclerConfig.getInt("PLANKVARS.doorcost");
					numberProduced = recyclerConfig.getInt("PLANKVARS.doorprod");
				}
				else if(dropperInventory.containsAtLeast(birchstair, 1) || furnaceInventory.containsAtLeast(birchstair, 1)){
					ingredientItem = birchstair;
					ingredientCost = recyclerConfig.getInt("PLANKVARS.staircost");
					numberProduced = recyclerConfig.getInt("PLANKVARS.stairprod");
				}
			}
			else if(plank.getDurability() == 3){
				if(dropperInventory.containsAtLeast(junglefence, 1) || furnaceInventory.containsAtLeast(junglefence, 1)){
					ingredientItem = junglefence;
					ingredientCost = recyclerConfig.getInt("PLANKVARS.fencecost");
					numberProduced = recyclerConfig.getInt("PLANKVARS.fenceprod");
				}
				else if(dropperInventory.containsAtLeast(junglegate, 1) || furnaceInventory.containsAtLeast(junglegate, 1)){
					ingredientItem = junglegate;
					ingredientCost = recyclerConfig.getInt("PLANKVARS.gatecost");
					numberProduced = recyclerConfig.getInt("PLANKVARS.gateprod");
				}
				else if(dropperInventory.containsAtLeast(jungledoor, 1) || furnaceInventory.containsAtLeast(jungledoor, 1)){
					ingredientItem = jungledoor;
					ingredientCost = recyclerConfig.getInt("PLANKVARS.doorcost");
					numberProduced = recyclerConfig.getInt("PLANKVARS.doorprod");
				}
				else if(dropperInventory.containsAtLeast(junglestair, 1) || furnaceInventory.containsAtLeast(junglestair, 1)){
					ingredientItem = junglestair;
					ingredientCost = recyclerConfig.getInt("PLANKVARS.staircost");
					numberProduced = recyclerConfig.getInt("PLANKVARS.stairprod");
				}
			}
			else if(plank.getDurability() == 4){
				if(dropperInventory.containsAtLeast(acaciafence, 1) || furnaceInventory.containsAtLeast(acaciafence, 1)){
					ingredientItem = acaciafence;
					ingredientCost = recyclerConfig.getInt("PLANKVARS.fencecost");
					numberProduced = recyclerConfig.getInt("PLANKVARS.fenceprod");
				}
				else if(dropperInventory.containsAtLeast(acaciagate, 1) || furnaceInventory.containsAtLeast(acaciagate, 1)){
					ingredientItem = acaciagate;
					ingredientCost = recyclerConfig.getInt("PLANKVARS.gatecost");
					numberProduced = recyclerConfig.getInt("PLANKVARS.gateprod");
				}
				else if(dropperInventory.containsAtLeast(acaciadoor, 1) || furnaceInventory.containsAtLeast(acaciadoor, 1)){
					ingredientItem = acaciadoor;
					ingredientCost = recyclerConfig.getInt("PLANKVARS.doorcost");
					numberProduced = recyclerConfig.getInt("PLANKVARS.doorprod");
				}
				else if(dropperInventory.containsAtLeast(acaciastair, 1) || furnaceInventory.containsAtLeast(acaciastair, 1)){
					ingredientItem = acaciastair;
					ingredientCost = recyclerConfig.getInt("PLANKVARS.staircost");
					numberProduced = recyclerConfig.getInt("PLANKVARS.stairprod");
				}
			}
			else if(plank.getDurability() == 5){
				if(dropperInventory.containsAtLeast(darkoakfence, 1) || furnaceInventory.containsAtLeast(darkoakfence, 1)){
					ingredientItem = darkoakfence;
					ingredientCost = recyclerConfig.getInt("PLANKVARS.fencecost");
					numberProduced = recyclerConfig.getInt("PLANKVARS.fenceprod");
				}
				else if(dropperInventory.containsAtLeast(darkoakgate, 1) || furnaceInventory.containsAtLeast(darkoakgate, 1)){
					ingredientItem = darkoakgate;
					ingredientCost = recyclerConfig.getInt("PLANKVARS.gatecost");
					numberProduced = recyclerConfig.getInt("PLANKVARS.gateprod");
				}
				else if(dropperInventory.containsAtLeast(darkoakdoor, 1) || furnaceInventory.containsAtLeast(darkoakdoor, 1)){
					ingredientItem = darkoakdoor;
					ingredientCost = recyclerConfig.getInt("PLANKVARS.doorcost");
					numberProduced = recyclerConfig.getInt("PLANKVARS.doorprod");
				}
				else if(dropperInventory.containsAtLeast(darkoakstair, 1) || furnaceInventory.containsAtLeast(darkoakstair, 1)){
					ingredientItem = darkoakstair;
					ingredientCost = recyclerConfig.getInt("PLANKVARS.staircost");
					numberProduced = recyclerConfig.getInt("PLANKVARS.stairprod");
				}
			}
			
			if(dropperInventory.containsAtLeast(slab, 1) || furnaceInventory.containsAtLeast(slab, 1)){
				ingredientItem = slab;
				ingredientCost = recyclerConfig.getInt("PLANKVARS.slabcost");
				numberProduced = recyclerConfig.getInt("PLANKVARS.slabprod");
			}
			else if(dropperInventory.containsAtLeast(trapdoor, 1) || furnaceInventory.containsAtLeast(trapdoor, 1)){
				ingredientItem = trapdoor;
				ingredientCost = recyclerConfig.getInt("PLANKVARS.trapdoorcost");
				numberProduced = recyclerConfig.getInt("PLANKVARS.trapdoorprod");
			}
			else if(dropperInventory.containsAtLeast(chest, 1) || furnaceInventory.containsAtLeast(chest, 1)){
				ingredientItem = chest;
				ingredientCost = recyclerConfig.getInt("PLANKVARS.chestcost");
				numberProduced = recyclerConfig.getInt("PLANKVARS.chestprod");
			}
			else if(dropperInventory.containsAtLeast(sign, 1) || furnaceInventory.containsAtLeast(sign, 1)){
				ingredientItem = sign;
				ingredientCost = recyclerConfig.getInt("PLANKVARS.signcost");
				numberProduced = recyclerConfig.getInt("PLANKVARS.signprod");
			}
			
			producedItem.setAmount(numberProduced);
			
			ItemStack theFurnaceStack = recyclerFurnace.getInventory().getResult();
			if(theFurnaceStack != null){
				newAmount = theFurnaceStack.getAmount();
				if(newAmount == ingredientCost && theFurnaceStack.getType() == ingredientItem.getType()){
					recyclerFurnace.getInventory().setResult(air);
					outputDropper.getInventory().addItem(producedItem);
					dontGo = true;
				}
				else if(newAmount > ingredientCost && theFurnaceStack.getType() == ingredientItem.getType()){
					newAmount = newAmount - ingredientCost;
					theFurnaceStack.setAmount(newAmount);
					outputDropper.getInventory().addItem(producedItem);
					dontGo = true;
				}
				
				
			}

			if(dontGo == false){
			if(inputDropper.getInventory().containsAtLeast(ingredientItem, 1)){
				inputDropper.getInventory().removeItem(ingredientItem);
				
				ItemStack furnaceStack = recyclerFurnace.getInventory().getResult();
				if(furnaceStack != null){
					if(furnaceStack.getType() == ingredientItem.getType() && furnaceStack.getDurability() == ingredientItem.getDurability()){
						newAmount = furnaceStack.getAmount() + 1;
						furnaceStack.setAmount(newAmount);
						recyclerFurnace.getInventory().setResult(furnaceStack);
					}
				}
				else{
					recyclerFurnace.getInventory().setResult(ingredientItem);
				}
			}
			}
			
		}
		else if(recipeBlueprint.getType() == Material.INK_SACK || recipeBlueprint.getDurability() == 15){
			ItemStack ingredientItem = new ItemStack(Material.BONE);
			ItemStack producedItem = new ItemStack(Material.INK_SACK);
			int ingredientCost = recyclerConfig.getInt("BONEVARS.bonecost");
			int bonesProduced = recyclerConfig.getInt("BONEVARS.boneproduction");
			Bukkit.broadcastMessage(":)" + Integer.toString(bonesProduced));
			producedItem.setDurability((short) 15);
			producedItem.setAmount(bonesProduced);
			boolean dontGo = false;
			
				ItemStack theFurnaceStack = recyclerFurnace.getInventory().getResult();
				if(theFurnaceStack != null){
					Bukkit.broadcastMessage(Integer.toString(producedItem.getAmount()));
					newAmount = theFurnaceStack.getAmount();
					if(newAmount == ingredientCost && theFurnaceStack.getType() == ingredientItem.getType()){
						recyclerFurnace.getInventory().setResult(air);
						outputDropper.getInventory().addItem(producedItem);
						dontGo = true;
					}
					else if(newAmount > ingredientCost && theFurnaceStack.getType() == ingredientItem.getType()){
						newAmount = newAmount - ingredientCost;
						theFurnaceStack.setAmount(newAmount);
						outputDropper.getInventory().addItem(producedItem);
						dontGo = true;
					}
					
					
				}

				if(dontGo == false){
				if(inputDropper.getInventory().containsAtLeast(ingredientItem, 1)){
					inputDropper.getInventory().removeItem(ingredientItem);
					
					ItemStack furnaceStack = recyclerFurnace.getInventory().getResult();
					if(furnaceStack != null){
						if(furnaceStack.getType() == ingredientItem.getType() && furnaceStack.getDurability() == ingredientItem.getDurability()){
							newAmount = furnaceStack.getAmount() + 1;
							furnaceStack.setAmount(newAmount);
							recyclerFurnace.getInventory().setResult(furnaceStack);
						}
					}
					else{
						recyclerFurnace.getInventory().setResult(ingredientItem);
					}
				}
				}
		}
		}
		
	}
	
	public boolean recyclerIsIntact(RecyclerMachine recyclerOne){
		
		Block topBlock = recyclerOne.getInputDropper().getLocation().getBlock();
		Block middleBlock = recyclerOne.getRecyclerFurnace().getLocation().getBlock();
		Block bottomBlock = recyclerOne.getOutputDropper().getLocation().getBlock();
		Block signBlock = recyclerOne.getSign().getLocation().getBlock();
		
		if(topBlock.getType() != Material.DROPPER || middleBlock.getType() != Material.FURNACE || bottomBlock.getType() != Material.DROPPER || signBlock.getType() != Material.WALL_SIGN){
			return false;
		}
		
		return true;
	}
	
	@Override
	public void run(){
		
		BukkitTask recycleTask = new RecyclerTask(this.recyclerPlugin).runTaskLater(recyclerPlugin, 20);
		
		HashMap  <Location, RecyclerMachine> activeRecyclerList = recyclerPlugin.activeRecyclerList;
		ArrayList <Location> recyclerLocationList = recyclerPlugin.recyclerLocationList;
		int locationListSize = recyclerLocationList.size();
		
		if(activeRecyclerList != null){
		if(activeRecyclerList.size() > 0){
			for(int i=0; i<locationListSize; i++){
				RecyclerMachine recyclerUno = activeRecyclerList.get(recyclerLocationList.get(i));
				if(recyclerIsIntact(recyclerUno)){
					runRecyclerMachine(recyclerUno);
				}
				else{
					recyclerPlugin.activeRecyclerList.remove(recyclerUno.getRecyclerSignLocation());
					recyclerPlugin.recyclerLocationList.remove(i);
				}
			}
		}
		
		}
	}
	
}
