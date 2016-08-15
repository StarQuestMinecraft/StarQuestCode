package com.ginger_walnut.sqsmoothcraft.tasks;

import java.util.ArrayList;
import java.util.List;

import net.md_5.bungee.api.ChatColor;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitScheduler;

import us.higashiyama.george.SQSpace.SQSpace;

import com.dibujaron.cardboardbox.Knapsack;
import com.ginger_walnut.sqsmoothcraft.SQSmoothCraft;
import com.ginger_walnut.sqsmoothcraft.objects.Ship;
import com.ginger_walnut.sqsmoothcraft.objects.ShipBlock;
import com.ginger_walnut.sqsmoothcraft.utils.ShipUtils;

public class ShipCreator extends Thread{
	
	public void run(final Ship ship, final Player pilot) {
		
		BukkitScheduler scheduler = Bukkit.getServer().getScheduler();
		
		scheduler.scheduleSyncDelayedTask(SQSmoothCraft.getPluginMain(), new Runnable () {
			
			@Override
			public void run() {
				
				ship.getCaptain().setSneaking(false);
				ship.getMainBlock().getShipLocation().setMainBlock(ship.getMainBlock());
				
				ship.getMainBlock().getArmorStand().setPassenger(pilot);
				
				List<ShipBlock> cannonList = new ArrayList<ShipBlock>();
				
				for (int i = 0; i < ship.getShipBlocks().size(); i ++) {
					
					if (ship.getShipBlocks().get(i).getArmorStand().getHelmet().getType().equals(Material.getMaterial(SQSmoothCraft.config.getString("weapons.cannon.material")))) {
						
						cannonList.add(ship.getShipBlocks().get(i));
						
					}
					
				}
				
				List<ShipBlock> missleList = new ArrayList<ShipBlock>();
				
				for (int i = 0; i < ship.getShipBlocks().size(); i ++) {
					
					if (ship.getShipBlocks().get(i).getArmorStand().getHelmet().getType().equals(Material.getMaterial(SQSmoothCraft.config.getString("weapons.missile launcher.material")))) {
						
						missleList.add(ship.getShipBlocks().get(i));
						
					}
					
				}
				
				List<ShipBlock> reactorList = new ArrayList<ShipBlock>();
				
				for (int i = 0; i < ship.getShipBlocks().size(); i ++) {
					
					if (ship.getShipBlocks().get(i).getArmorStand().getHelmet().getType().equals(Material.getMaterial(SQSmoothCraft.config.getString("utilites.reactor.material")))) {
						
						reactorList.add(ship.getShipBlocks().get(i));
						
					}
					
				}
				
				List<ShipBlock> emFieldGenList = new ArrayList<ShipBlock>();
				
				for (int i = 0; i < ship.getShipBlocks().size(); i ++) {
					
					if (ship.getShipBlocks().get(i).getArmorStand().getHelmet().getType().equals(Material.getMaterial(SQSmoothCraft.config.getString("utilites.emFieldGenerator.material")))) {
						
						emFieldGenList.add(ship.getShipBlocks().get(i));
						
					}
					
				}
				
				for (ShipBlock shipBlock : ship.getShipBlocks()) {
					
					shipBlock.ship = ship;
					
				}
				
				ship.getMainBlock().setShip(ship);	
				ship.setCannons(cannonList);
				ship.missleList = missleList;
				ship.reactorList = reactorList;
				ship.emFieldGenList = emFieldGenList;
				
				float maxSpeed = 0f;
				
				double averageWeight = 0;
				
				for (ShipBlock block : ship.blockList) {
					
					averageWeight = averageWeight + block.weight;
					
				}
				
				averageWeight = averageWeight / ship.blockList.size();
				
				maxSpeed = (float) (1 / averageWeight) + ((float) reactorList.size() / 10.0f);
				
				if (maxSpeed > 1) {
					
					maxSpeed = 1;
					
				}
				
				if(ship.emFieldGenList.size() >= 1) {
					
					ship.shieldHealth = SQSmoothCraft.config.getDouble("utilites.emFieldGenerator.fieldPower");
					
					
				}
				
				ship.maxSpeed = maxSpeed;
				
				ship.maxYawRate = maxSpeed * 5;
				
				ship.acceleration = maxSpeed / 20;
				
				SQSpace.noSuffacatePlayers.add(pilot);
				
				Knapsack knapsack = new Knapsack(pilot);
				
				SQSmoothCraft.knapsackMap.put(pilot.getUniqueId(), knapsack);
				
				ShipUtils.setPlayerShipInventory(pilot);
				
				SQSmoothCraft.shipMap.put(pilot.getUniqueId(), ship);
				
				pilot.sendMessage(ChatColor.GREEN + "Your ship has been registered!");
				pilot.sendMessage(ChatColor.RED + "Make sure to disable your rotational lock when you are ready to turn your craft");
				pilot.sendMessage("EM Field Generators: " + ship.emFieldGenList.size());
				
			}
			
		}, 3);
		
	}
	
}
