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
									
									if (importMachine.getMaxLiquid(fluid) != importMachine.getLiquid(fluid)) {
										
										bothMachines.put(importMachine, importMachine.getMaxLiquid(fluid) - importMachine.getLiquid(fluid));
										
									}
									
								}
								
							}
							
						}
						
						HashMap<Machine, Integer> justImportMap = new HashMap<Machine, Integer>();
						
						for (Machine importMachine : importMachines) {
							
							if (!bothMachines.containsKey(importMachine)) {
								
								if (importMachine.getMaxLiquid(fluid) != importMachine.getLiquid(fluid)) {
									
									justImportMap.put(importMachine, importMachine.getMaxLiquid(fluid) - importMachine.getLiquid(fluid));
									
								}
								
							}
							
						}
						
						HashMap<Machine, Integer> liquidLeftMap = new HashMap<Machine, Integer>();
						
						for (Machine exportMachine : exportMachines) {
							
							if (exportMachine.getLiquid(fluid) >= 2500) {
							
								liquidLeftMap.put(exportMachine, 2500);
							
							} else if (exportMachine.getLiquid(fluid) != 0) {
								
								liquidLeftMap.put(exportMachine, exportMachine.getLiquid(fluid));
								
							}
							
						}
						
						int i = 0;
						
						while (justImportMap.size() > 0 && liquidLeftMap.size() > 0 && i < 5000) {
							
							i ++;
							
							List<Machine> exportRemoves = new ArrayList<Machine>();
							List<Machine> importRemoves = new ArrayList<Machine>();
							
							Machine smallestExport = null;
							
							for (Machine exportMachine : liquidLeftMap.keySet()) {
								
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
								
								int liquidLeft = importMachine.getMaxLiquid(fluid) - importMachine.getLiquid(fluid);
								
								if (liquidLeft == 0) {
									
									importRemoves.add(importMachine);
									
								} else {
									
									if (smallestImport == null) {
										
										smallestImport = importMachine;
										
									} else {
										
										if (liquidLeft < smallestImport.getLiquid(fluid)) {
											
											smallestImport = importMachine;
											
										}
										
									}

								}
								
							}
							
							for (Machine remove : importRemoves) {
								
								justImportMap.remove(remove);
								
							}

							for (Machine remove : exportRemoves) {
								
								liquidLeftMap.remove(remove);
								
							}

							if (smallestExport.getLiquid(fluid) / justImportMap.size() > justImportMap.get(smallestImport)) {
								
								int liquid = justImportMap.get(smallestImport);
								
								if (liquid > liquidLeftMap.get(smallestExport) / justImportMap.size()) {
									
									liquid = liquidLeftMap.get(smallestExport) / justImportMap.size();
									
								}

								smallestExport.setLiquid(fluid, smallestExport.getLiquid(fluid) - (liquid * justImportMap.size()));
								
								int liquidLeft = liquidLeftMap.get(smallestExport);
								
								liquidLeft = liquidLeft - (liquid * justImportMap.size());
								
								liquidLeftMap.remove(smallestExport);
								
								if (liquidLeft > 0) {
									
									liquidLeftMap.put(smallestExport, liquidLeft);
									
								}
								
								List<Machine> justImportRemoves = new ArrayList<Machine>();
								
								for (Machine importMachine : justImportMap.keySet()) {
									
									importMachine.setLiquid(fluid, importMachine.getLiquid(fluid) + liquid);
									
									if (importMachine.getLiquid(fluid) == importMachine.getMaxLiquid(fluid)) {
										
										justImportRemoves.add(importMachine);
										
									} else {
										
										justImportMap.replace(importMachine, importMachine.getMaxLiquid(fluid) - importMachine.getLiquid(fluid));
										
									}
									
								}
								
								for (Machine machine : justImportRemoves) {
									
									justImportMap.remove(machine);
									
								}
								
							} else {
								
								int liquid = smallestExport.getLiquid(fluid) / justImportMap.size();
								
								if (liquid > liquidLeftMap.get(smallestExport) / justImportMap.size()) {
									
									liquid = liquidLeftMap.get(smallestExport) / justImportMap.size();
									
								}

								smallestExport.setLiquid(fluid, smallestExport.getLiquid(fluid) - (liquid * justImportMap.size()));
								
								int liquidLeft = liquidLeftMap.get(smallestExport);
								
								liquidLeft = liquidLeft - (liquid * justImportMap.size());
								
								liquidLeftMap.remove(smallestExport);
								
								if (liquidLeft > 0) {
									
									liquidLeftMap.put(smallestExport, liquidLeft);
									
								}
								
								for (Machine importMachine : justImportMap.keySet()) {
									
									importMachine.setLiquid(fluid, importMachine.getLiquid(fluid) + liquid);
									
									if (importMachine.getLiquid(fluid) == importMachine.getMaxLiquid(fluid)) {
										
										justImportMap.remove(importMachine);
										
									} else {
										
										justImportMap.replace(importMachine, importMachine.getMaxLiquid(fluid) - importMachine.getLiquid(fluid));
										
									}
									
								}
								
							}
							
						}
						
						List<Machine> sameMachines = new ArrayList<Machine>();
						
						for (Machine machine : liquidLeftMap.keySet()) {
							
							for (Machine bothMachine : bothMachines.keySet()) {
								
								if (machine == bothMachine) {
									
									sameMachines.add(machine);
									
								}
								
							}
							
						}
						
						for (Machine machine : sameMachines) {
								
							liquidLeftMap.remove(machine);
							
						}
						
						if (justImportMap.size() == 0 && liquidLeftMap.size() > 0) {
							
							i = 0;
							
							while (bothMachines.size() > 0 && liquidLeftMap.size() > 0 && i < 5000) {
								
								i ++;
								
								List<Machine> exportRemoves = new ArrayList<Machine>();
								List<Machine> importRemoves = new ArrayList<Machine>();
								
								Machine smallestExport = null;
								
								for (Machine exportMachine : liquidLeftMap.keySet()) {
									
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
									
									int liquidLeft = importMachine.getMaxLiquid(fluid) - importMachine.getLiquid(fluid);
									
									if (liquidLeft == 0) {
										
										importRemoves.add(importMachine);
										
									} else {
										
										if (smallestImport == null) {
											
											smallestImport = importMachine;
											
										} else {
											
											if (liquidLeft < smallestImport.getLiquid(fluid)) {
												
												smallestImport = importMachine;
												
											}
											
										}

									}
									
								}
								
								for (Machine remove : importRemoves) {
									
									bothMachines.remove(remove);
									
								}

								for (Machine remove : exportRemoves) {
									
									liquidLeftMap.remove(remove);
									
								}
								
								if (smallestExport.getLiquid(fluid) / bothMachines.size() > bothMachines.get(smallestImport)) {
									
									int liquid = bothMachines.get(smallestImport);
									
									if (liquid > liquidLeftMap.get(smallestExport) / bothMachines.size()) {
										
										liquid = liquidLeftMap.get(smallestExport) / bothMachines.size();
										
									}

									smallestExport.setLiquid(fluid, smallestExport.getLiquid(fluid) - (liquid * bothMachines.size()));	
									
									int liquidLeft = liquidLeftMap.get(smallestExport);
									
									liquidLeft = liquidLeft - (liquid * bothMachines.size());
									
									liquidLeftMap.remove(smallestExport);
									
									if (liquidLeft > 0) {
										
										liquidLeftMap.put(smallestExport, liquidLeft);
										
									}
									
									for (Machine importMachine : bothMachines.keySet()) {
											
										importMachine.setLiquid(fluid, importMachine.getLiquid(fluid) + liquid);
											
										if (importMachine.getLiquid(fluid) == importMachine.getMaxLiquid(fluid)) {
												
											bothMachines.remove(importMachine);
												
										} else {
												
											bothMachines.replace(importMachine, importMachine.getMaxLiquid(fluid) - importMachine.getLiquid(fluid));
												
										}
										
									}
									
								} else {
									
									int liquid = smallestExport.getLiquid(fluid) / bothMachines.size();
									
									if (liquid > liquidLeftMap.get(smallestExport) / bothMachines.size()) {
										
										liquid = liquidLeftMap.get(smallestExport) / bothMachines.size();
										
									}

									smallestExport.setLiquid(fluid, smallestExport.getLiquid(fluid) - (liquid * bothMachines.size()));
									
									int liquidLeft = liquidLeftMap.get(smallestExport);
									
									liquidLeft = liquidLeft - (liquid * bothMachines.size());
									
									liquidLeftMap.remove(smallestExport);
									
									if (liquidLeft > 0) {
										
										liquidLeftMap.put(smallestExport, liquidLeft);
										
									}
									
									for (Machine importMachine : bothMachines.keySet()) {
										
										importMachine.setLiquid(fluid, importMachine.getLiquid(fluid) + liquid);
										
										if (importMachine.getLiquid(fluid) == importMachine.getMaxLiquid(fluid)) {
											
											bothMachines.remove(importMachine);
											
										} else {
											
											bothMachines.replace(importMachine, importMachine.getMaxLiquid(fluid) - importMachine.getLiquid(fluid));
											
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
