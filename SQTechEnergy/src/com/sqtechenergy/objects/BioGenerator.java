package com.sqtechenergy.objects;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import com.starquestminecraft.sqtechbase.SQTechBase;
import com.starquestminecraft.sqtechbase.gui.GUI;
import com.starquestminecraft.sqtechbase.objects.GUIBlock;
import com.starquestminecraft.sqtechbase.objects.Machine;
import com.starquestminecraft.sqtechbase.objects.MachineType;
import com.starquestminecraft.sqtechbase.util.DirectionUtils;
import com.starquestminecraft.sqtechbase.util.EnergyUtils;
import com.starquestminecraft.sqtechbase.util.InventoryUtils;
import com.starquestminecraft.sqtechenergy.SQTechEnergy;
import com.starquestminecraft.sqtechenergy.gui.BioGeneratorGUI;

import net.md_5.bungee.api.ChatColor;

public class BioGenerator extends MachineType {

	public BioGenerator() {

		super(SQTechEnergy.config.getInt("bio generator.max energy"));
		name = "Bio Generator";

		defaultExport = true;

	}

	@Override
	public boolean detectStructure(GUIBlock guiBlock) {

		Block block = guiBlock.getLocation().getBlock();

		Block glass1 = block.getRelative(BlockFace.UP);

		Block glass2 = glass1.getRelative(BlockFace.UP);

		Block slab = glass2.getRelative(BlockFace.UP);

		List<BlockFace> faces = new ArrayList<BlockFace>();

		faces.add(BlockFace.EAST);
		faces.add(BlockFace.SOUTH);
		faces.add(BlockFace.NORTH);
		faces.add(BlockFace.WEST);

		for (BlockFace f : faces) {

			BlockFace right = DirectionUtils.getRight(f);
			BlockFace left = DirectionUtils.getLeft(f);

			if (block.getType() == Material.LAPIS_BLOCK) {
				if (block.getRelative(right).getType() == Material.STEP) {
					if (block.getRelative(left).getType() == Material.STEP) {
						if (block.getRelative(f).getType() == Material.STAINED_CLAY) {
							if (block.getRelative(f).getRelative(right).getType() == Material.STAINED_CLAY) {
								if (block.getRelative(f).getRelative(left).getType() == Material.STAINED_CLAY) {
									if (block.getRelative(f).getRelative(f).getType() == Material.STAINED_CLAY) {
										if (block.getRelative(f).getRelative(f).getRelative(right).getType() == Material.STEP) {
											if (block.getRelative(f).getRelative(f).getRelative(left).getType() == Material.STEP) {

												if (glass1.getType() == Material.GLASS) {
													if (glass1.getRelative(f).getRelative(right).getType() == Material.GLASS) {
														if (glass1.getRelative(f).getRelative(left).getType() == Material.GLASS) {
															if (glass1.getRelative(f).getRelative(f).getType() == Material.GLASS) {

																if (glass2.getType() == Material.GLASS) {
																	if (glass2.getRelative(f).getRelative(right).getType() == Material.GLASS) {
																		if (glass2.getRelative(f).getRelative(left).getType() == Material.GLASS) {
																			if (glass2.getRelative(f).getRelative(f).getType() == Material.GLASS) {

																				if (slab.getType() == Material.STEP) {
																					if (slab.getRelative(f).getRelative(right).getType() == Material.STEP) {
																						if (slab.getRelative(f).getType() == Material.STEP) {
																							if (slab.getRelative(f).getRelative(left).getType() == Material.STEP) {
																								if (slab.getRelative(f).getRelative(f).getType() == Material.STEP) {
																									return true;
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
				}
			}

		}

		return false;

	}
	
	public static BlockFace getFace(GUIBlock guiBlock) {

		Block block = guiBlock.getLocation().getBlock();

		Block glass1 = block.getRelative(BlockFace.UP);

		Block glass2 = glass1.getRelative(BlockFace.UP);

		Block slab = glass2.getRelative(BlockFace.UP);

		List<BlockFace> faces = new ArrayList<BlockFace>();

		faces.add(BlockFace.EAST);
		faces.add(BlockFace.SOUTH);
		faces.add(BlockFace.NORTH);
		faces.add(BlockFace.WEST);

		for (BlockFace f : faces) {

			BlockFace right = DirectionUtils.getRight(f);
			BlockFace left = DirectionUtils.getLeft(f);

			if (block.getType() == Material.LAPIS_BLOCK) {
				if (block.getRelative(right).getType() == Material.STEP) {
					if (block.getRelative(left).getType() == Material.STEP) {
						if (block.getRelative(f).getType() == Material.STAINED_CLAY) {
							if (block.getRelative(f).getRelative(right).getType() == Material.STAINED_CLAY) {
								if (block.getRelative(f).getRelative(left).getType() == Material.STAINED_CLAY) {
									if (block.getRelative(f).getRelative(f).getType() == Material.STAINED_CLAY) {
										if (block.getRelative(f).getRelative(f).getRelative(right).getType() == Material.STEP) {
											if (block.getRelative(f).getRelative(f).getRelative(left).getType() == Material.STEP) {

												if (glass1.getType() == Material.GLASS) {
													if (glass1.getRelative(f).getRelative(right).getType() == Material.GLASS) {
														if (glass1.getRelative(f).getRelative(left).getType() == Material.GLASS) {
															if (glass1.getRelative(f).getRelative(f).getType() == Material.GLASS) {

																if (glass2.getType() == Material.GLASS) {
																	if (glass2.getRelative(f).getRelative(right).getType() == Material.GLASS) {
																		if (glass2.getRelative(f).getRelative(left).getType() == Material.GLASS) {
																			if (glass2.getRelative(f).getRelative(f).getType() == Material.GLASS) {

																				if (slab.getType() == Material.STEP) {
																					if (slab.getRelative(f).getRelative(right).getType() == Material.STEP) {
																						if (slab.getRelative(f).getType() == Material.STEP) {
																							if (slab.getRelative(f).getRelative(left).getType() == Material.STEP) {
																								if (slab.getRelative(f).getRelative(f).getType() == Material.STEP) {
																									return f;
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
				}
			}

		}

		return null;

	}
	
	@Override
	public GUI getGUI(Player player, int id) {
		
		return new BioGeneratorGUI(player, id);
		
	}
	
	@Override
	public int getSpaceLeft(Machine machine, ItemStack itemStack) {

		int fuels = 0;

		if (machine.data.containsKey("fuel")) {

			if (machine.data.get("fuel") instanceof HashMap<?,?>) {

				HashMap<Fuel, Integer> currentFuels = (HashMap<Fuel, Integer>) machine.data.get("fuel");

				fuels = currentFuels.size();

			}

		}

		if (getMaxEnergy() - machine.getEnergy() > 0 && fuels < 1) {

			boolean isFuel = false;

			for (Fuel fuel : SQTechEnergy.fuels) {

				if (fuel.generator.equals(name)) {

					if (itemStack.getTypeId() == fuel.id && itemStack.getDurability() == fuel.data) {

						isFuel = true;

					}

				}

			}

			if (isFuel) {

				return 1;

			}

		}

		return 0;

	}
	
	@Override
	public void sendItems(Machine machine, ItemStack itemStack) {

		if (!itemStack.getType().equals(Material.AIR)) {

			for (Fuel fuel : SQTechEnergy.fuels) {

				if (fuel.generator.equals(machine.getMachineType().name)) {

					if (itemStack.getTypeId() == fuel.id && itemStack.getDurability() == fuel.data) {

						if (machine.data.containsKey("fuel")) {

							if (machine.data.get("fuel") instanceof HashMap<?,?>) {

								HashMap<Fuel, Integer> currentFuels = (HashMap<Fuel, Integer>) machine.data.get("fuel");

								if (currentFuels.containsKey(fuel)) {

									currentFuels.replace(fuel, currentFuels.get(fuel) + (itemStack.getAmount() * fuel.burnTime));

								} else {

									currentFuels.put(fuel, itemStack.getAmount() * fuel.burnTime);

								}

							}

						} else {

							machine.data.put("fuel", new HashMap<Fuel, Integer>());

							HashMap<Fuel, Integer> currentFuels = (HashMap<Fuel, Integer>) machine.data.get("fuel");
							currentFuels.put(fuel, itemStack.getAmount() * fuel.burnTime);

						}

					}

				}

			}

		}

	}
	
	@Override
	public void updateEnergy(Machine machine) {

		for (Player player : SQTechBase.currentGui.keySet()) {

			if (SQTechBase.currentGui.get(player).id == machine.getGUIBlock().id) {

				if (player.getOpenInventory() != null) {

					if (player.getOpenInventory().getTitle().equals(ChatColor.BLUE + "SQTech - Bio Generator")) {

						player.getOpenInventory().setItem(8, InventoryUtils.createSpecialItem(Material.REDSTONE, (short) 0, "Energy", new String[] {EnergyUtils.formatEnergy(machine.getEnergy()) + "/" + EnergyUtils.formatEnergy(machine.getMachineType().getMaxEnergy()), ChatColor.RED + "" + ChatColor.MAGIC + "Contraband"}));

						if (machine.data.get("fuel") != null) {
							
							if (machine.data.get("fuel") instanceof HashMap<?, ?>) {
								
								HashMap<Fuel, Integer> currentFuels = (HashMap<Fuel, Integer>) machine.data.get("fuel");
								List<Fuel> fuels = new ArrayList<Fuel>();
								fuels.addAll(currentFuels.keySet());
								
								if (fuels.size() < 1) return;
								
								if (fuels.get(0) != null) {
									
									Fuel f = fuels.get(0);
									
									for (String fuel : SQTechEnergy.config.getConfigurationSection("bio generator.fuel").getKeys(false)) {

										String path = "bio generator.fuel." + fuel;
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

	}

}
