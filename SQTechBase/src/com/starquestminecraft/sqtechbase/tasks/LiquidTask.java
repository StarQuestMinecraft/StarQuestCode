package com.starquestminecraft.sqtechbase.tasks;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.scheduler.BukkitScheduler;

import com.starquestminecraft.sqtechbase.SQTechBase;
import com.starquestminecraft.sqtechbase.objects.Fluid;
import com.starquestminecraft.sqtechbase.objects.GUIBlock;
import com.starquestminecraft.sqtechbase.objects.Machine;
import com.starquestminecraft.sqtechbase.objects.Network;

public class LiquidTask extends Thread {

	@SuppressWarnings("deprecation")
	public void run() {
		
		BukkitScheduler scheduler = Bukkit.getServer().getScheduler();
		
		scheduler.scheduleAsyncRepeatingTask(SQTechBase.getPluginMain(), new Runnable() {
			
			@Override
			public void run() {
				
				for (Fluid fluid : SQTechBase.fluids) {
					
					for (Network network : SQTechBase.networks) {
					
						List<Machine> exportMachines = new ArrayList<Machine>();
						List<Machine> importMachines = new ArrayList<Machine>();
					
						for (GUIBlock guiBlock : network.getGUIBlocks()) {
							
							Machine machine = null;
							
							for (Machine listMachine : SQTechBase.machines) {
								
								if (listMachine.getGUIBlock().equals(guiBlock)) {
									
									machine = listMachine;
									
								}
								
							}
							
							if (machine != null) {
								
								if (machine.enabled) {
									
									if (machine.liquidExports.contains(fluid)) {
										
										exportMachines.add(machine);
										
									}
									
									if (machine.liquidImports.contains(fluid)) {
										
										importMachines.add(machine);
										
									}
									
								}
								
							}
							
						}	
						
						HashMap<Machine, Integer> bothMachines = new HashMap<Machine, Integer>();
						
						for (Machine importMachine : importMachines) {
							
							for (Machine exportMachine : exportMachines) {
								
								if (importMachine == exportMachine) {
									
									if (importMachine.getMachineType().getMaxLiquid(fluid) != importMachine.getLiquid(fluid)) {
										
										bothMachines.put(importMachine, importMachine.getMachineType().getMaxLiquid(fluid) - importMachine.getLiquid(fluid));
										
									}
									
								}
								
							}
							
						}
						
						HashMap<Machine, Integer> justImportMap = new HashMap<Machine, Integer>();
						
						for (Machine importMachine : importMachines) {
							
							if (!bothMachines.containsKey(importMachine)) {
								
								if (importMachine.getMachineType().getMaxLiquid(fluid) != importMachine.getLiquid(fluid)) {
									
									justImportMap.put(importMachine, importMachine.getMachineType().getMaxLiquid(fluid) - importMachine.getLiquid(fluid));
									
								}
								
							}
							
						}
						
						HashMap<Machine, Integer> energyLeftMap = new HashMap<Machine, Integer>();
						
						for (Machine exportMachine : exportMachines) {
							
							if (exportMachine.getLiquid(fluid) >= 2500) {
							
								energyLeftMap.put(exportMachine, 2500);
							
							} else if (exportMachine.getLiquid(fluid) != 0) {
								
								energyLeftMap.put(exportMachine, exportMachine.getLiquid(fluid));
								
							}
							
						}
						
						int i = 0;
						
						while (justImportMap.size() > 0 && energyLeftMap.size() > 0 && i < 5000) {
							
							i ++;
							
							List<Machine> exportRemoves = new ArrayList<Machine>();
							List<Machine> importRemoves = new ArrayList<Machine>();
							
							Machine smallestExport = null;
							
							for (Machine exportMachine : energyLeftMap.keySet()) {
								
								if (exportMachine.getLiquid(fluid) == 0) {
									
									exportRemoves.add(exportMachine);
									
								} else {
									
									if (smallestExport == null) {
										
										smallestExport = exportMachine;
										
									} else {
										
										if (exportMachine.getLiquid(fluid) < smallestExport.getLiquid(fluid)) {
											
											smallestExport = exportMachine;
											
										}
										
									}

								}
								
							}
							
							Machine smallestImport = null;
							
							for (Machine importMachine : justImportMap.keySet()) {
								
								int energyLeft = importMachine.getMachineType().getMaxLiquid(fluid) - importMachine.getLiquid(fluid);
								
								if (energyLeft == 0) {
									
									importRemoves.add(importMachine);
									
								} else {
									
									if (smallestImport == null) {
										
										smallestImport = importMachine;
										
									} else {
										
										if (energyLeft < smallestImport.getLiquid(fluid)) {
											
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

							if (smallestExport.getLiquid(fluid) / justImportMap.size() > justImportMap.get(smallestImport)) {
								
								int energy = justImportMap.get(smallestImport);
								
								if (energy > energyLeftMap.get(smallestExport) / justImportMap.size()) {
									
									energy = energyLeftMap.get(smallestExport) / justImportMap.size();
									
								}

								smallestExport.setLiquid(fluid, smallestExport.getLiquid(fluid) - (energy * justImportMap.size()));
								
								int energyLeft = energyLeftMap.get(smallestExport);
								
								energyLeft = energyLeft - (energy * justImportMap.size());
								
								energyLeftMap.remove(smallestExport);
								
								if (energyLeft > 0) {
									
									energyLeftMap.put(smallestExport, energyLeft);
									
								}
								
								List<Machine> justImportRemoves = new ArrayList<Machine>();
								
								for (Machine importMachine : justImportMap.keySet()) {
									
									importMachine.setLiquid(fluid, importMachine.getLiquid(fluid) + energy);
									
									if (importMachine.getLiquid(fluid) == importMachine.getMachineType().getMaxLiquid(fluid)) {
										
										justImportRemoves.add(importMachine);
										
									} else {
										
										justImportMap.replace(importMachine, importMachine.getMachineType().getMaxLiquid(fluid) - importMachine.getLiquid(fluid));
										
									}
									
								}
								
								for (Machine machine : justImportRemoves) {
									
									justImportMap.remove(machine);
									
								}
								
							} else {
								
								int energy = smallestExport.getLiquid(fluid) / justImportMap.size();
								
								if (energy > energyLeftMap.get(smallestExport) / justImportMap.size()) {
									
									energy = energyLeftMap.get(smallestExport) / justImportMap.size();
									
								}

								smallestExport.setLiquid(fluid, smallestExport.getLiquid(fluid) - (energy * justImportMap.size()));
								
								int energyLeft = energyLeftMap.get(smallestExport);
								
								energyLeft = energyLeft - (energy * justImportMap.size());
								
								energyLeftMap.remove(smallestExport);
								
								if (energyLeft > 0) {
									
									energyLeftMap.put(smallestExport, energyLeft);
									
								}
								
								for (Machine importMachine : justImportMap.keySet()) {
									
									importMachine.setLiquid(fluid, importMachine.getLiquid(fluid) + energy);
									
									if (importMachine.getLiquid(fluid) == importMachine.getMachineType().getMaxLiquid(fluid)) {
										
										justImportMap.remove(importMachine);
										
									} else {
										
										justImportMap.replace(importMachine, importMachine.getMachineType().getMaxLiquid(fluid) - importMachine.getLiquid(fluid));
										
									}
									
								}
								
							}
							
						}
						
						if (justImportMap.size() == 0 && energyLeftMap.size() > 0) {
							
							i = 0;
							
							while (bothMachines.size() > 0 && energyLeftMap.size() > 0 && i < 5000) {
								
								i ++;
								
								List<Machine> exportRemoves = new ArrayList<Machine>();
								List<Machine> importRemoves = new ArrayList<Machine>();
								
								Machine smallestExport = null;
								
								for (Machine exportMachine : energyLeftMap.keySet()) {
									
									if (exportMachine.getLiquid(fluid) == 0) {
										
										exportRemoves.add(exportMachine);
										
									} else {
										
										if (smallestExport == null) {
											
											smallestExport = exportMachine;
											
										} else {
											
											if (exportMachine.getLiquid(fluid) < smallestExport.getLiquid(fluid)) {
												
												smallestExport = exportMachine;
												
											}
											
										}

									}
									
								}
								
								Machine smallestImport = null;
								
								for (Machine importMachine : bothMachines.keySet()) {
									
									int energyLeft = importMachine.getMachineType().getMaxLiquid(fluid) - importMachine.getLiquid(fluid);
									
									if (energyLeft == 0) {
										
										importRemoves.add(importMachine);
										
									} else {
										
										if (smallestImport == null) {
											
											smallestImport = importMachine;
											
										} else {
											
											if (energyLeft < smallestImport.getLiquid(fluid)) {
												
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
								
								if (smallestExport.getLiquid(fluid) / bothMachines.size() > bothMachines.get(smallestImport)) {
									
									int energy = bothMachines.get(smallestImport);
									
									if (energy > energyLeftMap.get(smallestExport) / bothMachines.size()) {
										
										energy = energyLeftMap.get(smallestExport) / bothMachines.size();
										
									}

									smallestExport.setLiquid(fluid, smallestExport.getLiquid(fluid) - (energy * bothMachines.size()));	
									
									int energyLeft = energyLeftMap.get(smallestExport);
									
									energyLeft = energyLeft - (energy * bothMachines.size());
									
									energyLeftMap.remove(smallestExport);
									
									if (energyLeft > 0) {
										
										energyLeftMap.put(smallestExport, energyLeft);
										
									}
									
									for (Machine importMachine : bothMachines.keySet()) {
											
										importMachine.setLiquid(fluid, importMachine.getLiquid(fluid) + energy);
											
										if (importMachine.getLiquid(fluid) == importMachine.getMachineType().getMaxLiquid(fluid)) {
												
											bothMachines.remove(importMachine);
												
										} else {
												
											bothMachines.replace(importMachine, importMachine.getMachineType().getMaxLiquid(fluid) - importMachine.getLiquid(fluid));
												
										}
										
									}
									
								} else {
									
									int energy = smallestExport.getLiquid(fluid) / bothMachines.size();
									
									if (energy > energyLeftMap.get(smallestExport) / bothMachines.size()) {
										
										energy = energyLeftMap.get(smallestExport) / bothMachines.size();
										
									}

									smallestExport.setLiquid(fluid, smallestExport.getLiquid(fluid) - (energy * bothMachines.size()));
									
									int energyLeft = energyLeftMap.get(smallestExport);
									
									energyLeft = energyLeft - (energy * bothMachines.size());
									
									energyLeftMap.remove(smallestExport);
									
									if (energyLeft > 0) {
										
										energyLeftMap.put(smallestExport, energyLeft);
										
									}
									
									for (Machine importMachine : bothMachines.keySet()) {
										
										importMachine.setLiquid(fluid, importMachine.getLiquid(fluid) + energy);
										
										if (importMachine.getLiquid(fluid) == importMachine.getMachineType().getMaxLiquid(fluid)) {
											
											bothMachines.remove(importMachine);
											
										} else {
											
											bothMachines.replace(importMachine, importMachine.getMachineType().getMaxLiquid(fluid) - importMachine.getLiquid(fluid));
											
										}
										
									}
									
								}
								
							}
							
						}
						
					}
					
				}

			}
			
		}, 0, 19);
		
	}
	
}
