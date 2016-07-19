package com.starquestminecraft.sqtechenergy.tasks;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.BlockFace;
import org.bukkit.block.Furnace;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitScheduler;

import com.starquestminecraft.sqtechbase.SQTechBase;
import com.starquestminecraft.sqtechbase.objects.Machine;
import com.starquestminecraft.sqtechbase.util.EnergyUtils;
import com.starquestminecraft.sqtechbase.util.ObjectUtils;
import com.starquestminecraft.sqtechenergy.SQTechEnergy;

public class ChargerTask extends Thread{

	public void run() {
		
		BukkitScheduler scheduler = Bukkit.getServer().getScheduler();
		
		scheduler.scheduleSyncRepeatingTask(SQTechBase.getPluginMain(), new Runnable() {
			
			@Override
			public void run() {
				
				for (Machine machine : ObjectUtils.getAllMachinesOfType("Charger")) {
						
					Location chargerLocation = machine.getGUIBlock().getLocation().getBlock().getRelative(BlockFace.UP).getLocation();

					if (chargerLocation.getWorld().isChunkLoaded(chargerLocation.getChunk())) {
							
						if (chargerLocation.getBlock().getType().equals(Material.FURNACE) || chargerLocation.getBlock().getType().equals(Material.BURNING_FURNACE)) {
							
							Furnace charger = (Furnace) chargerLocation.getBlock().getState();
							
							if (charger.getInventory().getSmelting() != null) {
										
								if (charger.getInventory().getSmelting().hasItemMeta()) {
											
									if (charger.getInventory().getSmelting().getItemMeta().hasLore()) {
										
										if (charger.getInventory().getSmelting().getItemMeta().getLore().contains(ChatColor.DARK_PURPLE + "Power Tool")) {
												
											if (machine.getEnergy() > 0) {
													
												ItemStack energyItem = charger.getInventory().getSmelting();
												
												charger.setCookTime((short) (((float) EnergyUtils.getEnergy(energyItem) / (float) EnergyUtils.getMaxEnergy(energyItem)) * 200));
												charger.setBurnTime((short) 2);	
												
												int maxEnergy = SQTechEnergy.config.getInt("charger.rate");
												
												if (maxEnergy > machine.getEnergy()) {
													
													maxEnergy = machine.getEnergy();
													
												}
												
												if (EnergyUtils.getEnergy(energyItem) + maxEnergy > EnergyUtils.getMaxEnergy(energyItem)) {
													
													int energyDifference = EnergyUtils.getMaxEnergy(energyItem) - EnergyUtils.getEnergy(energyItem);
													
													energyItem = EnergyUtils.setEnergy(energyItem, EnergyUtils.getMaxEnergy(energyItem));
													
													if (charger.getInventory().getResult() == null) {
														
														charger.getInventory().setResult(energyItem);
														charger.getInventory().setSmelting(new ItemStack(Material.AIR));
														
													} else {
													
														charger.getInventory().setSmelting(energyItem);
														
													}
													
													machine.setEnergy(machine.getEnergy() - energyDifference);

												} else {
													
													charger.getInventory().setSmelting(EnergyUtils.setEnergy(energyItem, EnergyUtils.getEnergy(energyItem) + maxEnergy));
													
													machine.setEnergy(machine.getEnergy() - maxEnergy);
													
												}	
													
											}
											
										}
										
									}	
										
								}							
								
							}
							
						}

					}
					
				}
			
			}		
			
		}, 0, 0);
		
	}
	
}
