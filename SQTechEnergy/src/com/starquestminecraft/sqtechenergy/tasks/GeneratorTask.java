package com.starquestminecraft.sqtechenergy.tasks;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Color;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.Furnace;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitScheduler;

import com.starquestminecraft.sqtechbase.SQTechBase;
import com.starquestminecraft.sqtechbase.objects.Fluid;
import com.starquestminecraft.sqtechbase.objects.Machine;
import com.starquestminecraft.sqtechbase.util.InventoryUtils;
import com.starquestminecraft.sqtechbase.util.ObjectUtils;
import com.sqtechenergy.objects.BioGenerator;
import com.sqtechenergy.objects.Fuel;
import com.sqtechenergy.objects.SolarPanel;
import com.sqtechenergy.objects.SteamEngine;
import com.sqtechenergy.objects.WaterTurbine;
import com.starquestminecraft.sqtechenergy.SQTechEnergy;

import org.inventivetalent.particle.ParticleEffect;
import net.md_5.bungee.api.ChatColor;

public class GeneratorTask extends Thread {

	public void run() {

		BukkitScheduler scheduler = Bukkit.getServer().getScheduler();

		scheduler.scheduleSyncRepeatingTask(SQTechBase.getPluginMain(), new Runnable() {

			@SuppressWarnings({ "deprecation", "unchecked" })
			@Override
			public void run() {

				//Gets all of the basic generators so it can generate energy with them and loops through them
				for (Machine machine : ObjectUtils.getAllMachinesOfType("Basic Generator")) {

					//Checks to make sure the machine is enabled
					if (machine.enabled) {

						//Makes sure that the machine has the fuel data
						if (machine.data.containsKey("fuel")) {

							//Double check that it is a hashmap
							if (machine.data.get("fuel") instanceof HashMap<?,?>) {

								//pull the currentFuels from the machine
								HashMap<Fuel, Integer> currentFuels = (HashMap<Fuel, Integer>) machine.data.get("fuel");

								//Get the indivudal fuels
								List<Fuel> fuels = new ArrayList<Fuel>();
								fuels.addAll(currentFuels.keySet());

								//Makes sure that there are fuels to be burned
								if (fuels.size() > 0) {

									//Going to get the furnace, need to make sure that its actually there
									if (machine.getGUIBlock().getLocation().getBlock().getRelative(BlockFace.UP).getType().equals(Material.FURNACE) || machine.getGUIBlock().getLocation().getBlock().getRelative(BlockFace.UP).getType().equals(Material.BURNING_FURNACE)) {

										Furnace furnace = (Furnace) machine.getGUIBlock().getLocation().getBlock().getRelative(BlockFace.UP).getState();

										//This is for the effect of it burning, the cook time is to make sure people don't use it as a free furnace
										furnace.setBurnTime((short) 2);
										furnace.setCookTime((short) 0);

									}

									//Get the fuel to be burned
									Fuel fuel = fuels.get(0);

									//Makes sure that theres space left to get energy
									if (machine.getEnergy() < machine.getMachineType().getMaxEnergy()) {

										//get the energy from the fuel
										machine.setEnergy(machine.getEnergy() + fuel.energyPerTick);

										//Remove the fuel from the list
										currentFuels.replace(fuel, currentFuels.get(fuel) - 1);

										if (currentFuels.get(fuel) <= 0) {

											currentFuels.remove(fuel);

										}

									}

									//There arn't any fuels
								} else {

									//Check to make sure that the furnace is actually a furnace
									if (machine.getGUIBlock().getLocation().getBlock().getRelative(BlockFace.UP).getType().equals(Material.FURNACE) || machine.getGUIBlock().getLocation().getBlock().getRelative(BlockFace.UP).getType().equals(Material.BURNING_FURNACE)) {

										//Deactivate the furnace
										Furnace furnace = (Furnace) machine.getGUIBlock().getLocation().getBlock().getRelative(BlockFace.UP).getState();
										furnace.setBurnTime((short) 0);

									}

								}

							}

						}

					}

				}

				//Gets all of the advanced generators so it can generate energy with them and loops through them
				for (Machine machine : ObjectUtils.getAllMachinesOfType("Advanced Generator")) {

					//Checks to make sure the machine is enabled
					if (machine.enabled) {

						//Makes sure that the machine has the fuel data
						if (machine.data.containsKey("fuel")) {

							//Double check that it is a hashmap
							if (machine.data.get("fuel") instanceof HashMap<?,?>) {

								//pull the currentFuels from the machine
								HashMap<Fuel, Integer> currentFuels = (HashMap<Fuel, Integer>) machine.data.get("fuel");

								//Get the indivudal fuels
								List<Fuel> fuels = new ArrayList<Fuel>();
								fuels.addAll(currentFuels.keySet());

								//Makes sure that there are fuels to be burned
								if (fuels.size() > 0) {

									//Going to get the furnace, need to make sure that its actually there
									if (machine.getGUIBlock().getLocation().getBlock().getRelative(BlockFace.UP).getType().equals(Material.FURNACE) || machine.getGUIBlock().getLocation().getBlock().getRelative(BlockFace.UP).getType().equals(Material.BURNING_FURNACE)) {

										Furnace furnace = (Furnace) machine.getGUIBlock().getLocation().getBlock().getRelative(BlockFace.UP).getState();

										//This is for the effect of it burning, the cook time is to make sure people don't use it as a free furnace
										furnace.setBurnTime((short) 2);
										furnace.setCookTime((short) 0);

									}

									//Get the fuel to be burned
									Fuel fuel = fuels.get(0);

									//Makes sure that theres space left to get energy
									if (machine.getEnergy() < machine.getMachineType().getMaxEnergy()) {

										//get the energy from the fuel
										machine.setEnergy(machine.getEnergy() + fuel.energyPerTick);

										//Remove the fuel from the list
										currentFuels.replace(fuel, currentFuels.get(fuel) - 1);

										if (currentFuels.get(fuel) <= 0) {

											currentFuels.remove(fuel);

										}

									}

									//There arn't any fuels
								} else {

									//Check to make sure that the furnace is actually a furnace
									if (machine.getGUIBlock().getLocation().getBlock().getRelative(BlockFace.UP).getType().equals(Material.FURNACE) || machine.getGUIBlock().getLocation().getBlock().getRelative(BlockFace.UP).getType().equals(Material.BURNING_FURNACE)) {

										//Deactivate the furnace
										Furnace furnace = (Furnace) machine.getGUIBlock().getLocation().getBlock().getRelative(BlockFace.UP).getState();
										furnace.setBurnTime((short) 0);

									}

								}

							}

						}

					}

				}

				//Gets all of the bio generators so it can generate energy with them and loops through them
				for (Machine machine : ObjectUtils.getAllMachinesOfType("Bio Generator")) {

					//Checks to make sure the machine is enabled
					if (machine.enabled) {

						//Makes sure that the machine has the fuel data
						if (machine.data.containsKey("fuel")) {

							//Double check that it is a hashmap
							if (machine.data.get("fuel") instanceof HashMap<?,?>) {

								//pull the currentFuels from the machine
								HashMap<Fuel, Integer> currentFuels = (HashMap<Fuel, Integer>) machine.data.get("fuel");

								//Get the indivudal fuels
								List<Fuel> fuels = new ArrayList<Fuel>();
								fuels.addAll(currentFuels.keySet());

								//Makes sure that there are fuels to be burned
								if (fuels.size() > 0) {

									Location location1 = machine.getGUIBlock().getLocation().getBlock().getRelative(BioGenerator.getFace(machine.getGUIBlock())).getRelative(BlockFace.UP).getLocation().add(0.5, 0.5, 0.5);
									Location location2 = machine.getGUIBlock().getLocation().getBlock().getRelative(BioGenerator.getFace(machine.getGUIBlock())).getRelative(BlockFace.UP).getRelative(BlockFace.UP).getLocation().add(0.5, 0.5, 0.5);
									Location location3 = machine.getGUIBlock().getLocation().getBlock().getRelative(BioGenerator.getFace(machine.getGUIBlock())).getRelative(BlockFace.UP).getLocation().add(0.5, 0, 0.5);
									Location location4 = machine.getGUIBlock().getLocation().getBlock().getRelative(BioGenerator.getFace(machine.getGUIBlock())).getRelative(BlockFace.UP).getRelative(BlockFace.UP).getLocation().add(0.5, 0, 0.5);
									Location location5 = machine.getGUIBlock().getLocation().getBlock().getRelative(BioGenerator.getFace(machine.getGUIBlock())).getRelative(BlockFace.UP).getRelative(BlockFace.UP).getLocation().add(0.5, 1, 0.5);

									ParticleEffect.REDSTONE.sendColor(Bukkit.getOnlinePlayers(),location1.getX(), location1.getY(),location1.getZ(), Color.GREEN);
									ParticleEffect.REDSTONE.sendColor(Bukkit.getOnlinePlayers(),location2.getX(), location2.getY(),location2.getZ(), Color.GREEN);
									ParticleEffect.REDSTONE.sendColor(Bukkit.getOnlinePlayers(),location3.getX(), location3.getY(),location3.getZ(), Color.GREEN);
									ParticleEffect.REDSTONE.sendColor(Bukkit.getOnlinePlayers(),location4.getX(), location4.getY(),location4.getZ(), Color.GREEN);
									ParticleEffect.REDSTONE.sendColor(Bukkit.getOnlinePlayers(),location5.getX(), location5.getY(),location5.getZ(), Color.GREEN);

									//Get the fuel to be burned
									Fuel fuel = fuels.get(0);

									//Makes sure that theres space left to get energy
									if (machine.getEnergy() < machine.getMachineType().getMaxEnergy()) {

										//get the energy from the fuel
										machine.setEnergy(machine.getEnergy() + fuel.energyPerTick);

										//Remove the fuel from the list
										currentFuels.replace(fuel, currentFuels.get(fuel) - 1);

										if (currentFuels.get(fuel) <= 0) {

											currentFuels.remove(fuel);

										}

									}

									//There arn't any fuels
								} else {

									//Check to make sure that the furnace is actually a furnace
									if (machine.getGUIBlock().getLocation().getBlock().getRelative(BlockFace.UP).getType().equals(Material.FURNACE) || machine.getGUIBlock().getLocation().getBlock().getRelative(BlockFace.UP).getType().equals(Material.BURNING_FURNACE)) {

										//Deactivate the furnace
										Furnace furnace = (Furnace) machine.getGUIBlock().getLocation().getBlock().getRelative(BlockFace.UP).getState();
										furnace.setBurnTime((short) 0);

									}

								}

							}

						}

					}

				}

				//Gets all of the redstone generators so it can generate energy with them and loops through them
				for (Machine machine : ObjectUtils.getAllMachinesOfType("Redstone Generator")) {

					//Checks to make sure the machine is enabled
					if (machine.enabled) {

						//Makes sure that the machine has the fuel data
						if (machine.data.containsKey("fuel")) {

							//Double check that it is a hashmap
							if (machine.data.get("fuel") instanceof HashMap<?,?>) {

								//pull the currentFuels from the machine
								HashMap<Fuel, Integer> currentFuels = (HashMap<Fuel, Integer>) machine.data.get("fuel");

								//Get the indivudal fuels
								List<Fuel> fuels = new ArrayList<Fuel>();
								fuels.addAll(currentFuels.keySet());

								//Makes sure that there are fuels to be burned
								if (fuels.size() > 0) {

									//Going to get the furnace, need to make sure that its actually there
									if (machine.getGUIBlock().getLocation().getBlock().getRelative(BlockFace.UP).getType().equals(Material.FURNACE) || machine.getGUIBlock().getLocation().getBlock().getRelative(BlockFace.UP).getType().equals(Material.BURNING_FURNACE)) {

										Furnace furnace = (Furnace) machine.getGUIBlock().getLocation().getBlock().getRelative(BlockFace.UP).getState();

										//This is for the effect of it burning, the cook time is to make sure people don't use it as a free furnace
										furnace.setBurnTime((short) 2);
										furnace.setCookTime((short) 0);

									}

									//Get the fuel to be burned
									Fuel fuel = fuels.get(0);

									//Makes sure that theres space left to get energy
									if (machine.getEnergy() < machine.getMachineType().getMaxEnergy()) {

										//get the energy from the fuel
										machine.setEnergy(machine.getEnergy() + fuel.energyPerTick);

										//Remove the fuel from the list
										currentFuels.replace(fuel, currentFuels.get(fuel) - 1);

										if (currentFuels.get(fuel) <= 0) {

											currentFuels.remove(fuel);

										}

									}

									//There arn't any fuels
								} else {

									//Check to make sure that the furnace is actually a furnace
									if (machine.getGUIBlock().getLocation().getBlock().getRelative(BlockFace.UP).getType().equals(Material.FURNACE) || machine.getGUIBlock().getLocation().getBlock().getRelative(BlockFace.UP).getType().equals(Material.BURNING_FURNACE)) {

										//Deactivate the furnace
										Furnace furnace = (Furnace) machine.getGUIBlock().getLocation().getBlock().getRelative(BlockFace.UP).getState();
										furnace.setBurnTime((short) 0);

									}

								}

							}

						}

					}

				} 

				//Gets all of the solar panels so it can generate energy with them and loops through them
				for (Machine machine : ObjectUtils.getAllMachinesOfType("Solar Panel")) {

					//Checks to make sure the machine is enabled
					if (machine.enabled) {

						Block middlePanel = machine.getGUIBlock().getLocation().getBlock().getRelative(BlockFace.UP).getRelative(SolarPanel.getFace(machine.getGUIBlock()));
						Block panel1 = middlePanel.getRelative(BlockFace.NORTH);
						Block panel2 = middlePanel.getRelative(BlockFace.SOUTH);
						Block panel3 = middlePanel.getRelative(BlockFace.EAST);
						Block panel4 = middlePanel.getRelative(BlockFace.WEST);
						Block panel5 = middlePanel.getRelative(BlockFace.NORTH_EAST);
						Block panel6 = middlePanel.getRelative(BlockFace.NORTH_WEST);
						Block panel7 = middlePanel.getRelative(BlockFace.SOUTH_EAST);
						Block panel8 = middlePanel.getRelative(BlockFace.SOUTH_WEST);

						if (middlePanel.getLightFromSky() == 15 &&
								panel1.getLightFromSky() == 15 &&
								panel2.getLightFromSky() == 15 &&
								panel3.getLightFromSky() == 15 &&
								panel4.getLightFromSky() == 15 &&
								panel5.getLightFromSky() == 15 &&
								panel6.getLightFromSky() == 15 &&
								panel7.getLightFromSky() == 15 &&
								panel8.getLightFromSky() == 15) {

							if (middlePanel.getWorld().getTime() <= 12000 || middlePanel.getWorld().getTime() >= 23500) {

								machine.setEnergy((machine.getEnergy() + SQTechEnergy.config.getInt("solar panel.energy per tick")));

							}

						}

					}

				}

				//Gets all of the water turbines so it can generate energy with them and loops through them
				for (Machine machine : ObjectUtils.getAllMachinesOfType("Water Turbine")) {

					//Checks to make sure the machine is enabled
					if (machine.enabled) {

						if (WaterTurbine.isSurroundedByWater(machine)) {

							machine.setEnergy((machine.getEnergy() + SQTechEnergy.config.getInt("water turbine.energy per tick")));

						}

					}

				}

				//Gets all of the steam engines so it can generate energy with them and loops through them
				for (Machine machine : ObjectUtils.getAllMachinesOfType("Steam Engine")) {

					//Checks to make sure the machine is enabled
					if (machine.enabled) {

						//Makes sure that the machine has the fuel data
						if (machine.data.containsKey("fuel")) {

							//Double check that it is a hashmap
							if (machine.data.get("fuel") instanceof HashMap<?,?>) {

								//pull the currentFuels from the machine
								HashMap<Fuel, Integer> currentFuels = (HashMap<Fuel, Integer>) machine.data.get("fuel");

								//Get the indivudal fuels
								List<Fuel> fuels = new ArrayList<Fuel>();
								fuels.addAll(currentFuels.keySet());

								Fluid f = null;
								//Get the water Fluid object
								for (Fluid f2 : SQTechBase.fluids) {
									if (f2.name.equals("Water")) {
										f = f2;
									}
								}

								//Make sure the machine has a liquid maximum set.
								machine.maxLiquid.put(f, SQTechEnergy.config.getInt("steam engine.max liquid"));
								machine.liquidImports.add(f);

								//Makes sure that there are fuels to be burned
								if (fuels.size() > 0 && machine.getLiquid(f) >= SQTechEnergy.config.getInt("steam engine.water usage per tick")) {

									if (SteamEngine.getFace(machine.getGUIBlock()) != null) {
										Location loc1 = machine.getGUIBlock().getLocation().getBlock().getRelative(SteamEngine.getFace(machine.getGUIBlock())).getLocation().add(0.5, 0.5, 0.5);
										Location loc2 = machine.getGUIBlock().getLocation().getBlock().getRelative(SteamEngine.getFace(machine.getGUIBlock())).getRelative(SteamEngine.getFace(machine.getGUIBlock())).getLocation().add(0.5, 0.5, 0.5);

										ParticleEffect.SMOKE_LARGE.send(Bukkit.getOnlinePlayers(), loc1.getX(), loc1.getY(), loc1.getZ(), 0, 0, 0, 1, 1);
										ParticleEffect.SMOKE_LARGE.send(Bukkit.getOnlinePlayers(), loc2.getX(), loc2.getY(), loc2.getZ(), 0, 0, 0, 1, 1);
									}
									
									//Get the fuel to be burned
									Fuel fuel = fuels.get(0);

									//Makes sure that theres space left to get energy
									if (machine.getEnergy() < machine.getMachineType().getMaxEnergy()) {

										//get the energy from the fuel
										machine.setEnergy(machine.getEnergy() + fuel.energyPerTick);

										//Remove the fuel from the list
										currentFuels.replace(fuel, currentFuels.get(fuel) - 1);
										//Remove the water from the machine
										machine.setLiquid(f, machine.getLiquid(f) - SQTechEnergy.config.getInt("steam engine.water usage per tick"));

										if (currentFuels.get(fuel) <= 0) {

											currentFuels.remove(fuel);

										}

									}

									//There arn't any fuels
								} else {

									//Check to make sure that the furnace is actually a furnace
									if (machine.getGUIBlock().getLocation().getBlock().getRelative(BlockFace.UP).getType().equals(Material.FURNACE) || machine.getGUIBlock().getLocation().getBlock().getRelative(BlockFace.UP).getType().equals(Material.BURNING_FURNACE)) {

										//Deactivate the furnace
										Furnace furnace = (Furnace) machine.getGUIBlock().getLocation().getBlock().getRelative(BlockFace.UP).getState();
										furnace.setBurnTime((short) 0);

									}

								}

							}

						}

					}

				}

				//For each gui open
				for (Player player : SQTechBase.currentGui.keySet()) {

					//Makes sure that there is actually a top inventory
					if (player.getOpenInventory().getTopInventory() != null) {

						//Get the top inventory
						Inventory inventory = player.getOpenInventory().getTopInventory();

						//Check to see if its the basic generator screen
						if (inventory.getTitle().equals(ChatColor.BLUE + "SQTech - Basic Generator")) {

							//Check to see if the fuel slot is not null
							if (inventory.getItem(10) != null) {

								//Get the item in the fuel slot
								ItemStack item = inventory.getItem(10);

								//Get the machine
								Machine machine = ObjectUtils.getMachineFromMachineGUI(SQTechBase.currentGui.get(player));

								//Check through all the fuel types
								for (Fuel fuel : SQTechEnergy.fuels) {

									//Make sure its for the Basic Generator
									if (fuel.generator.equals("Basic Generator")) {

										//Makes sure its the same kind of item as the fuel
										if (item.getTypeId() == fuel.id && item.getDurability() == fuel.data) {

											//Sees if the machine already contains the fuel data
											if (machine.data.containsKey("fuel")) {

												//Make sure its a hashmap
												if (machine.data.get("fuel") instanceof HashMap<?,?>) {

													HashMap<Fuel, Integer> currentFuels = (HashMap<Fuel, Integer>) machine.data.get("fuel");

													if (currentFuels.containsKey(fuel)) {

														//Add the fuel if it already contains some of the fuel
														currentFuels.replace(fuel, currentFuels.get(fuel) + (item.getAmount() * fuel.burnTime));

													} else {

														//Add the fuel if it doesn't contain any of the fuel
														currentFuels.put(fuel, item.getAmount() * fuel.burnTime);

													}

													//Remove the item
													inventory.setItem(10, new ItemStack(Material.AIR));

												}

												//The machine doesn't contain the fuel data
											} else {

												//add the fuel data
												machine.data.put("fuel", new HashMap<Fuel, Integer>());

												//pull it
												HashMap<Fuel, Integer> currentFuels = (HashMap<Fuel, Integer>) machine.data.get("fuel");
												//Since we already know that its not going to be located in there, add it to the hashmap
												currentFuels.put(fuel, item.getAmount() * fuel.burnTime);

												//Remove the item
												inventory.setItem(10, new ItemStack(Material.AIR));

											}

										}

									}

								}

							}

						} else //Check to see if its the advanced generator screen
							if (inventory.getTitle().equals(ChatColor.BLUE + "SQTech - Advanced Generator")) {

								//Check to see if the fuel slot is not null
								if (inventory.getItem(10) != null) {

									//Get the item in the fuel slot
									ItemStack item = inventory.getItem(10);

									//Get the machine
									Machine machine = ObjectUtils.getMachineFromMachineGUI(SQTechBase.currentGui.get(player));

									//Check through all the fuel types
									for (Fuel fuel : SQTechEnergy.fuels) {

										//Make sure its for the Advanced Generator
										if (fuel.generator.equals("Advanced Generator")) {

											//Makes sure its the same kind of item as the fuel
											if (item.getTypeId() == fuel.id && item.getDurability() == fuel.data) {

												//Sees if the machine already contains the fuel data
												if (machine.data.containsKey("fuel")) {

													//Make sure its a hashmap
													if (machine.data.get("fuel") instanceof HashMap<?,?>) {

														HashMap<Fuel, Integer> currentFuels = (HashMap<Fuel, Integer>) machine.data.get("fuel");

														if (currentFuels.containsKey(fuel)) {

															//Add the fuel if it already contains some of the fuel
															currentFuels.replace(fuel, currentFuels.get(fuel) + (item.getAmount() * fuel.burnTime));

														} else {

															//Add the fuel if it doesn't contain any of the fuel
															currentFuels.put(fuel, item.getAmount() * fuel.burnTime);

														}

														//Remove the item
														inventory.setItem(10, new ItemStack(Material.AIR));

													}

													//The machine doesn't contain the fuel data
												} else {

													//add the fuel data
													machine.data.put("fuel", new HashMap<Fuel, Integer>());

													//pull it
													HashMap<Fuel, Integer> currentFuels = (HashMap<Fuel, Integer>) machine.data.get("fuel");
													//Since we already know that its not going to be located in there, add it to the hashmap
													currentFuels.put(fuel, item.getAmount() * fuel.burnTime);

													//Remove the item
													inventory.setItem(10, new ItemStack(Material.AIR));

												}

											}

										}

									}

								}

							} else //Check to see if its the bio generator screen
								if (inventory.getTitle().equals(ChatColor.BLUE + "SQTech - Bio Generator")) {

									//Check to see if the fuel slot is not null
									if (inventory.getItem(10) != null) {

										//Get the item in the fuel slot
										ItemStack item = inventory.getItem(10);

										//Get the machine
										Machine machine = ObjectUtils.getMachineFromMachineGUI(SQTechBase.currentGui.get(player));

										//Check through all the fuel types
										for (Fuel fuel : SQTechEnergy.fuels) {

											//Make sure its for the Bio Generator
											if (fuel.generator.equals("Bio Generator")) {

												//Makes sure its the same kind of item as the fuel
												if (item.getTypeId() == fuel.id && item.getDurability() == fuel.data) {

													//Sees if the machine already contains the fuel data
													if (machine.data.containsKey("fuel")) {

														//Make sure its a hashmap
														if (machine.data.get("fuel") instanceof HashMap<?,?>) {

															HashMap<Fuel, Integer> currentFuels = (HashMap<Fuel, Integer>) machine.data.get("fuel");

															if (currentFuels.containsKey(fuel)) {

																//Add the fuel if it already contains some of the fuel
																currentFuels.replace(fuel, currentFuels.get(fuel) + (item.getAmount() * fuel.burnTime));

															} else {

																//Add the fuel if it doesn't contain any of the fuel
																currentFuels.put(fuel, item.getAmount() * fuel.burnTime);

															}

															//Remove the item
															inventory.setItem(10, new ItemStack(Material.AIR));

														}

														//The machine doesn't contain the fuel data
													} else {

														//add the fuel data
														machine.data.put("fuel", new HashMap<Fuel, Integer>());

														//pull it
														HashMap<Fuel, Integer> currentFuels = (HashMap<Fuel, Integer>) machine.data.get("fuel");
														//Since we already know that its not going to be located in there, add it to the hashmap
														currentFuels.put(fuel, item.getAmount() * fuel.burnTime);

														//Remove the item
														inventory.setItem(10, new ItemStack(Material.AIR));

													}

												}

											}

										}

									}

								} else //Check to see if its the redstone generator screen
									if (inventory.getTitle().equals(ChatColor.BLUE + "SQTech - Redstone Generator")) {

										//Check to see if the fuel slot is not null
										if (inventory.getItem(10) != null) {

											//Get the item in the fuel slot
											ItemStack item = inventory.getItem(10);

											//Get the machine
											Machine machine = ObjectUtils.getMachineFromMachineGUI(SQTechBase.currentGui.get(player));

											//Check through all the fuel types
											for (Fuel fuel : SQTechEnergy.fuels) {

												//Make sure its for the Redstone Generator
												if (fuel.generator.equals("Redstone Generator")) {

													//Makes sure its the same kind of item as the fuel
													if (item.getTypeId() == fuel.id && item.getDurability() == fuel.data) {

														//Sees if the machine already contains the fuel data
														if (machine.data.containsKey("fuel")) {

															//Make sure its a hashmap
															if (machine.data.get("fuel") instanceof HashMap<?,?>) {

																HashMap<Fuel, Integer> currentFuels = (HashMap<Fuel, Integer>) machine.data.get("fuel");

																if (currentFuels.containsKey(fuel)) {

																	//Add the fuel if it already contains some of the fuel
																	currentFuels.replace(fuel, currentFuels.get(fuel) + (item.getAmount() * fuel.burnTime));

																} else {

																	//Add the fuel if it doesn't contain any of the fuel
																	currentFuels.put(fuel, item.getAmount() * fuel.burnTime);

																}

																//Remove the item
																inventory.setItem(10, new ItemStack(Material.AIR));

															}

															//The machine doesn't contain the fuel data
														} else {

															//add the fuel data
															machine.data.put("fuel", new HashMap<Fuel, Integer>());

															//pull it
															HashMap<Fuel, Integer> currentFuels = (HashMap<Fuel, Integer>) machine.data.get("fuel");
															//Since we already know that its not going to be located in there, add it to the hashmap
															currentFuels.put(fuel, item.getAmount() * fuel.burnTime);

															//Remove the item
															inventory.setItem(10, new ItemStack(Material.AIR));

														}

													}

												}

											}

										}

									} else //Check to see if its the steam engine screen
										if (inventory.getTitle().equals(ChatColor.BLUE + "SQTech - Steam Engine")) {

											//Check to see if the fuel slot is not null
											if (inventory.getItem(10) != null) {

												//Get the item in the fuel slot
												ItemStack item = inventory.getItem(10);

												//Get the machine
												Machine machine = ObjectUtils.getMachineFromMachineGUI(SQTechBase.currentGui.get(player));

												//Check through all the fuel types
												for (Fuel fuel : SQTechEnergy.fuels) {

													//Make sure its for the Steam Engine
													if (fuel.generator.equals("Steam Engine")) {

														//Makes sure its the same kind of item as the fuel
														if (item.getTypeId() == fuel.id && item.getDurability() == fuel.data) {

															//Sees if the machine already contains the fuel data
															if (machine.data.containsKey("fuel")) {

																//Make sure its a hashmap
																if (machine.data.get("fuel") instanceof HashMap<?,?>) {

																	HashMap<Fuel, Integer> currentFuels = (HashMap<Fuel, Integer>) machine.data.get("fuel");

																	if (currentFuels.containsKey(fuel)) {

																		//Add the fuel if it already contains some of the fuel
																		currentFuels.replace(fuel, currentFuels.get(fuel) + (item.getAmount() * fuel.burnTime));

																	} else {

																		//Add the fuel if it doesn't contain any of the fuel
																		currentFuels.put(fuel, item.getAmount() * fuel.burnTime);

																	}

																	//Remove the item
																	inventory.setItem(10, new ItemStack(Material.AIR));

																}

																//The machine doesn't contain the fuel data
															} else {

																//add the fuel data
																machine.data.put("fuel", new HashMap<Fuel, Integer>());

																//pull it
																HashMap<Fuel, Integer> currentFuels = (HashMap<Fuel, Integer>) machine.data.get("fuel");
																//Since we already know that its not going to be located in there, add it to the hashmap
																currentFuels.put(fuel, item.getAmount() * fuel.burnTime);

																//Remove the item
																inventory.setItem(10, new ItemStack(Material.AIR));

															}

															if (machine.data.get("fuel") != null) {

																if (machine.data.get("fuel") instanceof HashMap<?, ?>) {

																	HashMap<Fuel, Integer> currentFuels = (HashMap<Fuel, Integer>) machine.data.get("fuel");
																	List<Fuel> fuels = new ArrayList<Fuel>();
																	fuels.addAll(currentFuels.keySet());

																	if (fuels.size() < 1) return;

																	if (fuels.get(0) != null) {

																		Fuel f = fuels.get(0);

																		for (String fuel2 : SQTechEnergy.config.getConfigurationSection("steam engine.fuel").getKeys(false)) {

																			String path = "steam engine.fuel." + fuel2;
																			int burnTime = SQTechEnergy.config.getInt(path + ".burn time");

																			player.getOpenInventory().setItem(17, InventoryUtils.createSpecialItem(Material.getMaterial(f.id), (short) 0, "Remaining fuel", new String[] {
																					"Fuel type: " + Material.getMaterial(f.id).name(),
																					"Amount left: " + (currentFuels.get(f)/burnTime),
																					ChatColor.RED + "" + ChatColor.MAGIC + "Contraband"
																			}));

																			return;

																		}

																	}

																}

															}

														}

													}

												}

											}

											//Check to see if the fuel slot is not null
											if (inventory.getItem(13) != null) {

												//Get the item in the fuel slot
												ItemStack item = inventory.getItem(13);

												//Get the machine
												Machine machine = ObjectUtils.getMachineFromMachineGUI(SQTechBase.currentGui.get(player));

												Fluid f = null;
												//Get the water Fluid object
												for (Fluid f2 : SQTechBase.fluids) {
													if (f2.name.equals("Water")) {
														f = f2;
													}
												}

												//Make sure the machine has a liquid maximum set.
												machine.maxLiquid.put(f, SQTechEnergy.config.getInt("steam engine.max liquid"));
												machine.liquidImports.add(f);

												//Make sure the item is a water bucket
												if (item.getType() == Material.WATER_BUCKET) {

													//Check if there is enough room in the machine for another bucket of water
													if ((machine.getMaxLiquid(f)-1000) >= machine.getLiquid(f)) {

														//Add the water
														machine.setLiquid(f, machine.getLiquid(f) + 1000);

														//Empty the bucket
														item.setType(Material.BUCKET);

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
