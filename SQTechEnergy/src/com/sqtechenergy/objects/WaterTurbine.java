package com.sqtechenergy.objects;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Player;

import com.starquestminecraft.sqtechbase.SQTechBase;
import com.starquestminecraft.sqtechbase.gui.GUI;
import com.starquestminecraft.sqtechbase.objects.GUIBlock;
import com.starquestminecraft.sqtechbase.objects.Machine;
import com.starquestminecraft.sqtechbase.objects.MachineType;
import com.starquestminecraft.sqtechbase.util.DirectionUtils;
import com.starquestminecraft.sqtechbase.util.EnergyUtils;
import com.starquestminecraft.sqtechbase.util.InventoryUtils;
import com.starquestminecraft.sqtechenergy.SQTechEnergy;
import com.starquestminecraft.sqtechenergy.gui.WaterTurbineGUI;

import net.md_5.bungee.api.ChatColor;

public class WaterTurbine extends MachineType {

	public WaterTurbine() {

		super(SQTechEnergy.config.getInt("water turbine.max energy"));
		name = "Water Turbine";

		defaultExport = true;

	}

	@Override
	public boolean detectStructure(GUIBlock guiBlock) {

		Block block = guiBlock.getLocation().getBlock();

		Block aboveSlab = block.getRelative(BlockFace.UP);

		Block belowSlab = block.getRelative(BlockFace.DOWN);

		List<BlockFace> faces = new ArrayList<BlockFace>();

		faces.add(BlockFace.EAST);
		faces.add(BlockFace.SOUTH);
		faces.add(BlockFace.NORTH);
		faces.add(BlockFace.WEST);

		for (BlockFace f : faces) {

			BlockFace right = DirectionUtils.getRight(f);
			BlockFace left = DirectionUtils.getLeft(f);

			Block extend1 = block.getRelative(f);
			Block extend2 = extend1.getRelative(f);
			Block extend3 = extend2.getRelative(f);
			Block extend4 = extend3.getRelative(f);

			if (block.getType() == Material.LAPIS_BLOCK) {
				if (block.getRelative(right).getType() == Material.STAINED_CLAY) {
					if (block.getRelative(left).getType() == Material.STAINED_CLAY) {
						if (belowSlab.getType() == Material.STEP) {
							if (belowSlab.getRelative(right).getType() == Material.STEP) {
								if (belowSlab.getRelative(left).getType() == Material.STEP) {
									if (aboveSlab.getType() == Material.STEP) {
										if (aboveSlab.getRelative(right).getType() == Material.STEP) {
											if (aboveSlab.getRelative(left).getType() == Material.STEP) {

												if (extend1.getType() == Material.IRON_FENCE) {

													if (extend2.getType() == Material.STAINED_CLAY) {
														if (extend2.getRelative(BlockFace.UP).getType() == Material.IRON_FENCE) {
															if (extend2.getRelative(BlockFace.DOWN).getType() == Material.IRON_FENCE) {
																if (extend2.getRelative(right).getType() == Material.IRON_FENCE) {
																	if (extend2.getRelative(left).getType() == Material.IRON_FENCE) {

																		if (extend3.getType() == Material.IRON_FENCE) {

																			if (extend4.getType() == Material.STAINED_CLAY) {
																				if (extend4.getRelative(BlockFace.UP).getType() == Material.IRON_FENCE) {
																					if (extend4.getRelative(BlockFace.DOWN).getType() == Material.IRON_FENCE) {
																						if (extend4.getRelative(right).getType() == Material.IRON_FENCE) {
																							if (extend4.getRelative(left).getType() == Material.IRON_FENCE) {

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

		return false;

	}

	public static BlockFace getFace(GUIBlock guiBlock) {

		Block block = guiBlock.getLocation().getBlock();

		Block aboveSlab = block.getRelative(BlockFace.UP);

		Block belowSlab = block.getRelative(BlockFace.DOWN);

		List<BlockFace> faces = new ArrayList<BlockFace>();

		faces.add(BlockFace.EAST);
		faces.add(BlockFace.SOUTH);
		faces.add(BlockFace.NORTH);
		faces.add(BlockFace.WEST);

		for (BlockFace f : faces) {

			BlockFace right = DirectionUtils.getRight(f);
			BlockFace left = DirectionUtils.getLeft(f);

			Block extend1 = block.getRelative(f);
			Block extend2 = extend1.getRelative(f);
			Block extend3 = extend2.getRelative(f);
			Block extend4 = extend3.getRelative(f);

			if (block.getType() == Material.LAPIS_BLOCK) {
				if (block.getRelative(right).getType() == Material.STAINED_CLAY) {
					if (block.getRelative(left).getType() == Material.STAINED_CLAY) {
						if (belowSlab.getType() == Material.STEP) {
							if (belowSlab.getRelative(right).getType() == Material.STEP) {
								if (belowSlab.getRelative(left).getType() == Material.STEP) {
									if (aboveSlab.getType() == Material.STEP) {
										if (aboveSlab.getRelative(right).getType() == Material.STEP) {
											if (aboveSlab.getRelative(left).getType() == Material.STEP) {

												if (extend1.getType() == Material.IRON_FENCE) {

													if (extend2.getType() == Material.STAINED_CLAY) {
														if (extend2.getRelative(BlockFace.UP).getType() == Material.IRON_FENCE) {
															if (extend2.getRelative(BlockFace.DOWN).getType() == Material.IRON_FENCE) {
																if (extend2.getRelative(right).getType() == Material.IRON_FENCE) {
																	if (extend2.getRelative(left).getType() == Material.IRON_FENCE) {

																		if (extend3.getType() == Material.IRON_FENCE) {

																			if (extend4.getType() == Material.STAINED_CLAY) {
																				if (extend4.getRelative(BlockFace.UP).getType() == Material.IRON_FENCE) {
																					if (extend4.getRelative(BlockFace.DOWN).getType() == Material.IRON_FENCE) {
																						if (extend4.getRelative(right).getType() == Material.IRON_FENCE) {
																							if (extend4.getRelative(left).getType() == Material.IRON_FENCE) {

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

		return null;

	}

	@Override
	public GUI getGUI(Player player, int id) {

		return new WaterTurbineGUI(player, id);

	}

	@Override
	public void updateEnergy(Machine machine) {

		for (Player player : SQTechBase.currentGui.keySet()) {

			if (SQTechBase.currentGui.get(player).id == machine.getGUIBlock().id) {

				if (player.getOpenInventory() != null) {

					if (player.getOpenInventory().getTitle().equals(ChatColor.BLUE + "SQTech - Water Turbine")) {

						String water = ChatColor.RED + "false";

						if (WaterTurbine.isSurroundedByWater(machine)) {
							water = ChatColor.GREEN + "true";
							player.getOpenInventory().setItem(4, InventoryUtils.createSpecialItem(Material.STAINED_GLASS_PANE, (short) 5, "Energy", new String[] {EnergyUtils.formatEnergy(machine.getEnergy()) + "/" + EnergyUtils.formatEnergy(machine.getMachineType().getMaxEnergy()), ChatColor.RED + "" + ChatColor.MAGIC + "Contraband"}));
						} else {
							player.getOpenInventory().setItem(4, InventoryUtils.createSpecialItem(Material.STAINED_GLASS_PANE, (short) 14, "Energy", new String[] {EnergyUtils.formatEnergy(machine.getEnergy()) + "/" + EnergyUtils.formatEnergy(machine.getMachineType().getMaxEnergy()), ChatColor.RED + "" + ChatColor.MAGIC + "Contraband"}));
						}

						player.getOpenInventory().setItem(0, InventoryUtils.createSpecialItem(Material.PAPER, (short) 0, "Info", new String[] {
								"The water turbine needs",
								"to be surrounded by water",
								"to function. ",
								"Water Status: " + water,
								ChatColor.RED + "" + ChatColor.MAGIC + "Contraband"
						}));

					}

				}

			}

		}

	}

	public static boolean isSurroundedByWater(Machine machine) {

		Boolean water = false;

		if (WaterTurbine.getFace(machine.getGUIBlock()) == null) return water;

		BlockFace f = WaterTurbine.getFace(machine.getGUIBlock());
		BlockFace right = DirectionUtils.getRight(WaterTurbine.getFace(machine.getGUIBlock()));
		BlockFace left = DirectionUtils.getLeft(WaterTurbine.getFace(machine.getGUIBlock()));
		BlockFace back = SQTechEnergy.getOpposite(f);

		Block wheel1 = machine.getGUIBlock().getLocation().getBlock().getRelative(f).getRelative(f);
		Block downBar1 = wheel1.getRelative(BlockFace.DOWN);
		Block rightBar1 = wheel1.getRelative(right);
		Block leftBar1 = wheel1.getRelative(left);

		Block wheel2 = wheel1.getRelative(f).getRelative(f);
		Block downBar2 = wheel2.getRelative(BlockFace.DOWN);
		Block rightBar2 = wheel2.getRelative(right);
		Block leftBar2 = wheel2.getRelative(left);

		if (downBar1.getRelative(BlockFace.NORTH).getType() == Material.WATER ||
				downBar1.getRelative(BlockFace.NORTH).getType() == Material.STATIONARY_WATER) {
			if (downBar1.getRelative(BlockFace.EAST).getType() == Material.WATER ||
					downBar1.getRelative(BlockFace.EAST).getType() == Material.STATIONARY_WATER) {
				if (downBar1.getRelative(BlockFace.WEST).getType() == Material.WATER ||
						downBar1.getRelative(BlockFace.WEST).getType() == Material.STATIONARY_WATER) {
					if (downBar1.getRelative(BlockFace.SOUTH).getType() == Material.WATER ||
							downBar1.getRelative(BlockFace.SOUTH).getType() == Material.STATIONARY_WATER) {

						if (rightBar1.getRelative(right).getType() == Material.WATER ||
								rightBar1.getRelative(right).getType() == Material.STATIONARY_WATER) {
							if (rightBar1.getRelative(f).getType() == Material.WATER ||
									rightBar1.getRelative(f).getType() == Material.STATIONARY_WATER) {
								if (rightBar1.getRelative(back).getType() == Material.WATER ||
										rightBar1.getRelative(back).getType() == Material.STATIONARY_WATER) {
									if (rightBar1.getRelative(BlockFace.DOWN).getType() == Material.WATER ||
											rightBar1.getRelative(BlockFace.DOWN).getType() == Material.STATIONARY_WATER) {

										if (leftBar1.getRelative(left).getType() == Material.WATER ||
												leftBar1.getRelative(left).getType() == Material.STATIONARY_WATER) {
											if (leftBar1.getRelative(f).getType() == Material.WATER ||
													leftBar1.getRelative(f).getType() == Material.STATIONARY_WATER) {
												if (leftBar1.getRelative(back).getType() == Material.WATER ||
														leftBar1.getRelative(back).getType() == Material.STATIONARY_WATER) {
													if (leftBar1.getRelative(BlockFace.DOWN).getType() == Material.WATER ||
															leftBar1.getRelative(BlockFace.DOWN).getType() == Material.STATIONARY_WATER) {

														if (downBar2.getRelative(BlockFace.NORTH).getType() == Material.WATER ||
																downBar2.getRelative(BlockFace.NORTH).getType() == Material.STATIONARY_WATER) {
															if (downBar2.getRelative(BlockFace.EAST).getType() == Material.WATER ||
																	downBar2.getRelative(BlockFace.EAST).getType() == Material.STATIONARY_WATER) {
																if (downBar2.getRelative(BlockFace.WEST).getType() == Material.WATER ||
																		downBar2.getRelative(BlockFace.WEST).getType() == Material.STATIONARY_WATER) {
																	if (downBar2.getRelative(BlockFace.SOUTH).getType() == Material.WATER ||
																			downBar2.getRelative(BlockFace.SOUTH).getType() == Material.STATIONARY_WATER) {

																		if (rightBar2.getRelative(right).getType() == Material.WATER ||
																				rightBar2.getRelative(right).getType() == Material.STATIONARY_WATER) {
																			if (rightBar2.getRelative(f).getType() == Material.WATER ||
																					rightBar2.getRelative(f).getType() == Material.STATIONARY_WATER) {
																				if (rightBar2.getRelative(back).getType() == Material.WATER ||
																						rightBar2.getRelative(back).getType() == Material.STATIONARY_WATER) {
																					if (rightBar2.getRelative(BlockFace.DOWN).getType() == Material.WATER ||
																							rightBar2.getRelative(BlockFace.DOWN).getType() == Material.STATIONARY_WATER) {

																						if (leftBar2.getRelative(left).getType() == Material.WATER ||
																								leftBar2.getRelative(left).getType() == Material.STATIONARY_WATER) {
																							if (leftBar2.getRelative(f).getType() == Material.WATER ||
																									leftBar2.getRelative(f).getType() == Material.STATIONARY_WATER) {
																								if (leftBar2.getRelative(back).getType() == Material.WATER ||
																										leftBar2.getRelative(back).getType() == Material.STATIONARY_WATER) {
																									if (leftBar2.getRelative(BlockFace.DOWN).getType() == Material.WATER ||
																											leftBar2.getRelative(BlockFace.DOWN).getType() == Material.STATIONARY_WATER) {

																										water = true;

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
		}

		return water;

	}

}
