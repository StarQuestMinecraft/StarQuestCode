package com.starquestminecraft.sqtechbase.tasks;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.Chest;
import org.bukkit.block.Dropper;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitScheduler;

import com.starquestminecraft.sqtechbase.GUIBlock;
import com.starquestminecraft.sqtechbase.Machine;
import com.starquestminecraft.sqtechbase.MachineType;
import com.starquestminecraft.sqtechbase.Network;
import com.starquestminecraft.sqtechbase.SQTechBase;
import com.starquestminecraft.sqtechbase.util.BlockUtils;

public class EnergyTask extends Thread {

	public void run() {
		
		BukkitScheduler scheduler = Bukkit.getServer().getScheduler();
		
		scheduler.scheduleSyncRepeatingTask(SQTechBase.getPluginMain(), new Runnable() {
			
			@Override
			public void run() {
				
				List<Machine> exportMachines = new ArrayList<Machine>();
				List<Machine> importMachines = new ArrayList<Machine>();
				
				for (Network network : SQTechBase.networks) {
					
					for (GUIBlock guiBlock : network.getGUIBlocks()) {
						
						Machine machine = null;
						
						for (Machine listMachine : SQTechBase.machines) {
							
							if (listMachine.getGUIBlock().equals(guiBlock)) {
								
								machine = listMachine;
								
							}
							
						}
						
						if (machine != null) {
							
							if (machine.exportsEnergy) {
								
								exportMachines.add(machine);
								
							}
							
							if (machine.importsEnergy) {
								
								importMachines.add(machine);
								
							}
							
						}
						
					}
					
				}
				
				HashMap<Machine, Integer> bothMachines = new HashMap<Machine, Integer>();
				
				for (Machine importMachine : importMachines) {
					
					for (Machine exportMachine : exportMachines) {
						
						if (importMachine.equals(exportMachine)) {
							
							if (importMachine.getMachineType().getMaxEnergy() != importMachine.getEnergy()) {
								
								bothMachines.put(importMachine, importMachine.getMachineType().getMaxEnergy() - importMachine.getEnergy());
								
							}
							
						}
						
					}
					
				}
				
				HashMap<Machine, Integer> justImportMap = new HashMap<Machine, Integer>();
				
				for (Machine importMachine : importMachines) {
					
					if (!bothMachines.containsKey(importMachine)) {
						
						if (importMachine.getMachineType().getMaxEnergy() != importMachine.getEnergy()) {
							
							justImportMap.put(importMachine, importMachine.getMachineType().getMaxEnergy() - importMachine.getEnergy());
							
						}
						
					}
					
				}
				
				HashMap<Machine, Integer> energyLeftMap = new HashMap<Machine, Integer>();
				
				for (Machine exportMachine : exportMachines) {
					
					if (exportMachine.getEnergy() >= 2500) {
					
						energyLeftMap.put(exportMachine, 2500);
					
					} else if (exportMachine.getEnergy() != 0) {
						
						energyLeftMap.put(exportMachine, exportMachine.getEnergy());
						
					}
					
				}
				
				while (justImportMap.size() > 0 && energyLeftMap.size() > 0) {
					
					List<Machine> exportRemoves = new ArrayList<Machine>();
					List<Machine> importRemoves = new ArrayList<Machine>();
					
					Machine smallestExport = null;
					
					for (Machine exportMachine : energyLeftMap.keySet()) {
						
						if (exportMachine.getEnergy() == 0) {
							
							exportRemoves.add(exportMachine);
							
						} else {
							
							if (smallestExport == null) {
								
								smallestExport = exportMachine;
								
							} else {
								
								if (exportMachine.getEnergy() < smallestExport.getEnergy()) {
									
									smallestExport = exportMachine;
									
								}
								
							}

						}
						
					}
					
					Machine smallestImport = null;
					
					for (Machine importMachine : justImportMap.keySet()) {
						
						int energyLeft = importMachine.getMachineType().getMaxEnergy() - importMachine.getEnergy();
						
						if (energyLeft == 0) {
							
							importRemoves.add(importMachine);
							
						} else {
							
							if (smallestImport == null) {
								
								smallestImport = importMachine;
								
							} else {
								
								if (energyLeft < smallestImport.getEnergy()) {
									
									smallestImport = importMachine;
									
								}
								
							}

						}
						
					}
					
					for (Machine remove : importRemoves) {
						
						justImportMap.remove(remove);
						
					}

					for (Machine remove : exportRemoves) {
						
						energyLeftMap.remove(remove);
						
					}

					if (smallestExport.getEnergy() / justImportMap.size() > justImportMap.get(smallestImport)) {
						
						int energy = justImportMap.get(smallestImport);
						
						if (energy > energyLeftMap.get(smallestExport) / justImportMap.size()) {
							
							energy = energyLeftMap.get(smallestExport) / justImportMap.size();
							
						}

						smallestExport.setEnergy(smallestExport.getEnergy() - (energy * justImportMap.size()));
						
						int energyLeft = energyLeftMap.get(smallestExport);
						
						energyLeft = energyLeft - (energy * justImportMap.size());
						
						energyLeftMap.remove(smallestExport);
						
						if (energyLeft > 0) {
							
							energyLeftMap.put(smallestExport, energyLeft);
							
						}
						
						for (Machine importMachine : justImportMap.keySet()) {
							
							importMachine.setEnergy(importMachine.getEnergy() + energy);
							
							if (importMachine.getEnergy() == importMachine.getMachineType().getMaxEnergy()) {
								
								justImportMap.remove(importMachine);
								
							} else {
								
								justImportMap.replace(importMachine, importMachine.getMachineType().getMaxEnergy() - importMachine.getEnergy());
								
							}
							
						}
						
					} else {
						
						int energy = smallestExport.getEnergy() / justImportMap.size();
						
						if (energy > energyLeftMap.get(smallestExport) / justImportMap.size()) {
							
							energy = energyLeftMap.get(smallestExport) / justImportMap.size();
							
						}

						smallestExport.setEnergy(smallestExport.getEnergy() - (energy * justImportMap.size()));
						
						int energyLeft = energyLeftMap.get(smallestExport);
						
						energyLeft = energyLeft - (energy * justImportMap.size());
						
						energyLeftMap.remove(smallestExport);
						
						if (energyLeft > 0) {
							
							energyLeftMap.put(smallestExport, energyLeft);
							
						}
						
						for (Machine importMachine : justImportMap.keySet()) {
							
							importMachine.setEnergy(importMachine.getEnergy() + energy);
							
							if (importMachine.getEnergy() == importMachine.getMachineType().getMaxEnergy()) {
								
								justImportMap.remove(importMachine);
								
							} else {
								
								justImportMap.replace(importMachine, importMachine.getMachineType().getMaxEnergy() - importMachine.getEnergy());
								
							}
							
						}
						
					}
					
				}
				
				if (justImportMap.size() == 0 && energyLeftMap.size() > 0) {
					
					while (bothMachines.size() > 0 && energyLeftMap.size() > 0) {
						
						List<Machine> exportRemoves = new ArrayList<Machine>();
						List<Machine> importRemoves = new ArrayList<Machine>();
						
						Machine smallestExport = null;
						
						for (Machine exportMachine : energyLeftMap.keySet()) {
							
							if (exportMachine.getEnergy() == 0) {
								
								exportRemoves.add(exportMachine);
								
							} else {
								
								if (smallestExport == null) {
									
									smallestExport = exportMachine;
									
								} else {
									
									if (exportMachine.getEnergy() < smallestExport.getEnergy()) {
										
										smallestExport = exportMachine;
										
									}
									
								}

							}
							
						}
						
						Machine smallestImport = null;
						
						for (Machine importMachine : bothMachines.keySet()) {
							
							int energyLeft = importMachine.getMachineType().getMaxEnergy() - importMachine.getEnergy();
							
							if (energyLeft == 0) {
								
								importRemoves.add(importMachine);
								
							} else {
								
								if (smallestImport == null) {
									
									smallestImport = importMachine;
									
								} else {
									
									if (energyLeft < smallestImport.getEnergy()) {
										
										smallestImport = importMachine;
										
									}
									
								}

							}
							
						}
						
						for (Machine remove : importRemoves) {
							
							bothMachines.remove(remove);
							
						}

						for (Machine remove : exportRemoves) {
							
							energyLeftMap.remove(remove);
							
						}
						
						if (smallestExport.getEnergy() / bothMachines.size() > bothMachines.get(smallestImport)) {
							
							int energy = bothMachines.get(smallestImport);
							
							if (energy > energyLeftMap.get(smallestExport) / bothMachines.size()) {
								
								energy = energyLeftMap.get(smallestExport) / bothMachines.size();
								
							}

							smallestExport.setEnergy(smallestExport.getEnergy() - (energy * bothMachines.size()));
							
							int energyLeft = energyLeftMap.get(smallestExport);
							
							energyLeft = energyLeft - (energy * bothMachines.size());
							
							energyLeftMap.remove(smallestExport);
							
							if (energyLeft > 0) {
								
								energyLeftMap.put(smallestExport, energyLeft);
								
							}
							
							for (Machine importMachine : bothMachines.keySet()) {
								
								if (!importMachine.equals(smallestExport)) {
									
									importMachine.setEnergy(importMachine.getEnergy() + energy);
									
									if (importMachine.getEnergy() == importMachine.getMachineType().getMaxEnergy()) {
										
										bothMachines.remove(importMachine);
										
									} else {
										
										bothMachines.replace(importMachine, importMachine.getMachineType().getMaxEnergy() - importMachine.getEnergy());
										
									}
									
								}
								
							}
							
						} else {
							
							int energy = smallestExport.getEnergy() / bothMachines.size();
							
							if (energy > energyLeftMap.get(smallestExport) / bothMachines.size()) {
								
								energy = energyLeftMap.get(smallestExport) / bothMachines.size();
								
							}

							smallestExport.setEnergy(smallestExport.getEnergy() - (energy * bothMachines.size()));
							
							int energyLeft = energyLeftMap.get(smallestExport);
							
							energyLeft = energyLeft - (energy * bothMachines.size());
							
							energyLeftMap.remove(smallestExport);
							
							if (energyLeft > 0) {
								
								energyLeftMap.put(smallestExport, energyLeft);
								
							}
							
							for (Machine importMachine : bothMachines.keySet()) {
								
								importMachine.setEnergy(importMachine.getEnergy() + energy);
								
								if (importMachine.getEnergy() == importMachine.getMachineType().getMaxEnergy()) {
									
									bothMachines.remove(importMachine);
									
								} else {
									
									bothMachines.replace(importMachine, importMachine.getMachineType().getMaxEnergy() - importMachine.getEnergy());
									
								}
								
							}
							
						}
						
					}
					
				}

			}
			
		}, 0, 19);
		
	}
	
}
