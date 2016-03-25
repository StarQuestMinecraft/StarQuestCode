package com.ginger_walnut.sqpowertools;

import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Furnace;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.metadata.MetadataValue;
import org.bukkit.scheduler.BukkitScheduler;

public class ChargerTask extends Thread{

	public void run() {
		
		BukkitScheduler scheduler = Bukkit.getServer().getScheduler();
		
		scheduler.scheduleSyncRepeatingTask(SQPowerTools.getPluginMain(), new Runnable() {
			
			@Override
			public void run() {
				
				for (Location chargerLocation : SQPowerTools.chargerLocations) {
					
					if (Events.isCharger(chargerLocation.getWorld().getBlockAt(chargerLocation))) {
						
						if (chargerLocation.getWorld().isChunkLoaded(chargerLocation.getBlockX() / 16, chargerLocation.getBlockZ() / 16)) {
							
							Furnace charger = (Furnace) chargerLocation.getWorld().getBlockAt(chargerLocation).getState();
							
							if (charger.hasMetadata("fuel_left")) {
								
								if (charger.getInventory().getSmelting() != null) {
									
									if (charger.getInventory().getSmelting().hasItemMeta()) {
										
										if (charger.getInventory().getSmelting().getItemMeta().hasLore()) {
											
											if (charger.getInventory().getSmelting().getItemMeta().getLore().contains(ChatColor.DARK_PURPLE + "Power Tool")) {
												
												ItemStack powerTool = charger.getInventory().getSmelting();
												
												charger.setCookTime((short) (((float) SQPowerTools.getEnergy(powerTool) / (float) SQPowerTools.getMaxEnergy(SQPowerTools.getName(powerTool), powerTool)) * 200));
												
												short timeLeft = 0;
												
												List<MetadataValue> values = charger.getMetadata("fuel_left");
												
												for (MetadataValue value : values) {
													
													if (value.getOwningPlugin() == SQPowerTools.getPluginMain()) {
														
														timeLeft = Short.parseShort(value.value().toString());
														
													}
													
												}
												
												if (timeLeft == 0) {
													
													if (charger.getInventory().getFuel() != null) {
													
														if (charger.getInventory().getFuel().getTypeId() == SQPowerTools.config.getInt("charger.fuel.fuelID")) {
														
															if (SQPowerTools.getEnergy(powerTool) < SQPowerTools.getMaxEnergy(SQPowerTools.getName(powerTool), powerTool)) {
																
																ItemStack fuel = charger.getInventory().getFuel();
																
																fuel.setAmount(fuel.getAmount() - 1);
															
																charger.getInventory().setFuel(fuel);
															
																charger.setMetadata("fuel_left", new FixedMetadataValue(SQPowerTools.getPluginMain(), 200));
																
																charger.setBurnTime((short) 200);
																
															}
															
														}
														
													}
													
												} else {
													
													charger.setBurnTime((short) timeLeft);
													
													charger.setMetadata("fuel_left", new FixedMetadataValue(SQPowerTools.getPluginMain(), timeLeft - 1));
													
													if ((SQPowerTools.getEnergy(powerTool) + (SQPowerTools.config.getInt("charger.fuel.energy") / 200)) > SQPowerTools.getMaxEnergy(SQPowerTools.getName(powerTool), powerTool)) {
														
														powerTool = SQPowerTools.setEnergy(powerTool, SQPowerTools.getMaxEnergy(SQPowerTools.getName(powerTool), powerTool));
														
														charger.getInventory().setResult(powerTool);
														charger.getInventory().setSmelting(new ItemStack(Material.AIR));
														
													} else {
														
														SQPowerTools.setEnergy(powerTool, SQPowerTools.getEnergy(powerTool) + (SQPowerTools.config.getInt("charger.fuel.energy") / 200));
														
													}
													
												}
												
											}
											
										}
										
									}
									
								}
								
							} else {
								
								charger.setMetadata("fuel_left", new FixedMetadataValue(SQPowerTools.getPluginMain(), 0));
								
							}
							
							
						}
						
					} else {
						
						SQPowerTools.chargerLocations.remove(chargerLocation);
						
					}
					
				}
				
			}			
			
		}, 0, 0);
		
	}
	
	public static void setChargerFuel(final Inventory inventory, final ItemStack fuel) {
		
		BukkitScheduler scheduler = Bukkit.getServer().getScheduler();
		
		scheduler.scheduleSyncDelayedTask(SQPowerTools.getPluginMain(), new Runnable() {
		
			@Override
			public void run() {
				
				inventory.setItem(1, fuel);
				
			}
			
		}, 1);
		
	}
	
}
