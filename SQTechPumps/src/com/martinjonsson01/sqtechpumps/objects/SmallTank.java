package com.martinjonsson01.sqtechpumps.objects;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import com.martinjonsson01.sqtechpumps.SQTechPumps;
import com.martinjonsson01.sqtechpumps.gui.PumpGUI;
import com.martinjonsson01.sqtechpumps.gui.SmallTankGUI;
import com.starquestminecraft.sqtechbase.SQTechBase;
import com.starquestminecraft.sqtechbase.gui.GUI;
import com.starquestminecraft.sqtechbase.objects.Fluid;
import com.starquestminecraft.sqtechbase.objects.GUIBlock;
import com.starquestminecraft.sqtechbase.objects.Machine;
import com.starquestminecraft.sqtechbase.objects.MachineType;
import com.starquestminecraft.sqtechbase.util.DirectionUtils;
import com.starquestminecraft.sqtechbase.util.InventoryUtils;
import com.starquestminecraft.sqtechbase.util.ObjectUtils;

import net.md_5.bungee.api.ChatColor;

public class SmallTank extends MachineType{

	public SmallTank(int maxEnergy) {

		super(maxEnergy);
		name = "Small Tank";

	}

	@Override
	public boolean detectStructure(GUIBlock block) {

		Block middleStainedGlass = block.getLocation().getBlock().getRelative(BlockFace.UP);
		
		Machine machine = ObjectUtils.getMachineFromGUIBlock(block);
		
		BlockFace[] faces = new BlockFace[]{
				BlockFace.NORTH,
				BlockFace.EAST,
				BlockFace.WEST,
				BlockFace.SOUTH
		};

		for (BlockFace f : faces) {
			BlockFace left = DirectionUtils.getLeft(f);
			BlockFace right = DirectionUtils.getRight(f);
			BlockFace up = BlockFace.UP;
			BlockFace down = BlockFace.DOWN;

			if (middleStainedGlass.getType() == Material.STAINED_GLASS_PANE) {
				if (middleStainedGlass.getRelative(right).getType() == Material.STAINED_CLAY) {
					if (middleStainedGlass.getRelative(left).getType() == Material.STAINED_CLAY) {
						if (middleStainedGlass.getRelative(up).getType() == Material.STAINED_CLAY) {
							if (middleStainedGlass.getRelative(down).getType() == Material.LAPIS_BLOCK) {
								if (middleStainedGlass.getRelative(f).getType() == Material.AIR || middleStainedGlass.getRelative(f).getType() == Material.STATIONARY_WATER || middleStainedGlass.getRelative(f).getType() == Material.STATIONARY_LAVA) {
									if (middleStainedGlass.getRelative(f).getRelative(left).getType() == Material.STAINED_GLASS_PANE) {
										if (middleStainedGlass.getRelative(f).getRelative(right).getType() == Material.STAINED_GLASS_PANE) {
											if (middleStainedGlass.getRelative(f).getRelative(up).getType() == Material.STAINED_CLAY) {
												if (middleStainedGlass.getRelative(f).getRelative(down).getType() == Material.STAINED_CLAY) {
													if (middleStainedGlass.getRelative(f).getRelative(up).getRelative(left).getType() == Material.STAINED_CLAY) {
														if (middleStainedGlass.getRelative(f).getRelative(up).getRelative(right).getType() == Material.STAINED_CLAY) {
															if (middleStainedGlass.getRelative(f).getRelative(down).getRelative(left).getType() == Material.STAINED_CLAY) {
																if (middleStainedGlass.getRelative(f).getRelative(down).getRelative(right).getType() == Material.STAINED_CLAY) {
																	if (middleStainedGlass.getRelative(f).getRelative(f).getType() == Material.STAINED_GLASS_PANE) {
																		if (middleStainedGlass.getRelative(f).getRelative(f).getRelative(right).getType() == Material.STAINED_CLAY) {
																			if (middleStainedGlass.getRelative(f).getRelative(f).getRelative(left).getType() == Material.STAINED_CLAY) {
																				if (middleStainedGlass.getRelative(f).getRelative(f).getRelative(up).getType() == Material.STAINED_CLAY) {
																					if (middleStainedGlass.getRelative(f).getRelative(f).getRelative(down).getType() == Material.STAINED_CLAY) {
																						SmallTank.updatePhysicalLiquid(machine);
																						SQTechPumps.locationMachineMap.put(block.getLocation(), machine);
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
		
		if (SQTechPumps.tankWaterBlocks.get(machine) != null) {

			Iterator<Block> it = SQTechPumps.tankWaterBlocks.get(machine).iterator();
			while (it.hasNext()) {

				Block b = it.next();

				if (b.getType() == Material.STATIONARY_LAVA || b.getType() == Material.STATIONARY_WATER) {
					b.setType(Material.AIR);
				}

				it.remove();

			}

		}
		
		return false;

	}

	@Override
	public GUI getGUI(Player player, int id) {

		return new SmallTankGUI(player, id);

	}

	@SuppressWarnings("deprecation")
	public static void updatePhysicalLiquid(Machine machine) {
		
		if (machine == null) return;
		
		Block waterBlock = getMiddleBlock(machine.getGUIBlock());
		
		List<Block> waterBlockIntoList = new ArrayList<Block>();
		waterBlockIntoList.add(waterBlock);
		
		SQTechPumps.tankWaterBlocks.put(machine, waterBlockIntoList);

		for (Fluid f : SQTechBase.fluids) {

			if (!(machine.getMaxLiquid(f) > 1)) continue;

			if (f.name == "Water") {
				SQTechPumps.machineLiquidTypeIdMap.put(machine, 9);
			} else if (f.name == "Lava") {
				SQTechPumps.machineLiquidTypeIdMap.put(machine, 11);
			}

			int max = machine.getMaxLiquid(f);

			int current = machine.getLiquid(f);

			double twelvePercent = (max/100) * 12.5;

			if (current >= twelvePercent && current < twelvePercent*2) {

				Bukkit.getScheduler().runTask(SQTechPumps.plugin, new BukkitRunnable() {
					@Override
					public void run() {
						if (SQTechPumps.machineLiquidTypeIdMap.get(machine) != null) {
							waterBlock.setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 7, false);
						}
					}
				});

			} else if (current >= twelvePercent*2 && current < twelvePercent*3) {

				Bukkit.getScheduler().runTask(SQTechPumps.plugin, new BukkitRunnable() {
					@Override
					public void run() {
						if (SQTechPumps.machineLiquidTypeIdMap.get(machine) != null) {
							waterBlock.setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 6, false);
						}
					}
				});

			} else if (current >= twelvePercent*3 && current < twelvePercent*4) {

				Bukkit.getScheduler().runTask(SQTechPumps.plugin, new BukkitRunnable() {
					@Override
					public void run() {
						if (SQTechPumps.machineLiquidTypeIdMap.get(machine) != null) {
							waterBlock.setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 5, false);
						}
					}
				});

			} else if (current >= twelvePercent*4 && current < twelvePercent*5) {

				Bukkit.getScheduler().runTask(SQTechPumps.plugin, new BukkitRunnable() {
					@Override
					public void run() {
						if (SQTechPumps.machineLiquidTypeIdMap.get(machine) != null) {
							waterBlock.setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 4, false);
						}
					}
				});

			} else if (current >= twelvePercent*5 && current < twelvePercent*6) {

				Bukkit.getScheduler().runTask(SQTechPumps.plugin, new BukkitRunnable() {
					@Override
					public void run() {
						if (SQTechPumps.machineLiquidTypeIdMap.get(machine) != null) {
							waterBlock.setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 3, false);
						}
					}
				});

			} else if (current >= twelvePercent*6 && current < twelvePercent*7) {

				Bukkit.getScheduler().runTask(SQTechPumps.plugin, new BukkitRunnable() {
					@Override
					public void run() {
						if (SQTechPumps.machineLiquidTypeIdMap.get(machine) != null) {
							waterBlock.setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 2, false);
						}
					}
				});

			} else if (current >= twelvePercent*7 && current < twelvePercent*8) {

				Bukkit.getScheduler().runTask(SQTechPumps.plugin, new BukkitRunnable() {
					@Override
					public void run() {
						if (SQTechPumps.machineLiquidTypeIdMap.get(machine) != null) {
							waterBlock.setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 1, false);
						}
					}
				});

			} else if (current >= twelvePercent*8) {

				Bukkit.getScheduler().runTask(SQTechPumps.plugin, new BukkitRunnable() {
					@Override
					public void run() {
						if (SQTechPumps.machineLiquidTypeIdMap.get(machine) != null) {
							waterBlock.setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 0, false);
						}
					}
				});

			}

			updateBlock(waterBlock.getRelative(BlockFace.NORTH));

		}

	}
	
	private long lastUpdate = 0;
	
	@SuppressWarnings("deprecation")
	@Override
	public void updateLiquid(Machine machine) {
		
		if (System.currentTimeMillis() - lastUpdate < 900) {
			return;
		}
		
		Bukkit.getServer().getScheduler().runTask(SQTechPumps.plugin, new BukkitRunnable() {

			@Override
			public void run() {

				if (getMiddleBlock(machine.getGUIBlock()) != null) {

					Block waterBlock = getMiddleBlock(machine.getGUIBlock());

					List<Block> waterBlockIntoList = new ArrayList<Block>();
					waterBlockIntoList.add(waterBlock);
					
					SQTechPumps.tankWaterBlocks.put(machine, waterBlockIntoList);

					for (Fluid f : SQTechBase.fluids) {

						if (!(machine.getMaxLiquid(f) > 1)) continue;

						if (f.name == "Water") {
							SQTechPumps.machineLiquidTypeIdMap.put(machine, 9);
						} else if (f.name == "Lava") {
							SQTechPumps.machineLiquidTypeIdMap.put(machine, 11);
						}

						int max = machine.getMaxLiquid(f);

						int current = machine.getLiquid(f);

						double twelvePercent = (max/100) * 12.5;

						if (current >= twelvePercent && current < twelvePercent*2) {

							if (SQTechPumps.machineLiquidTypeIdMap.get(machine) != null) {
								waterBlock.setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 7, false);
							}

						} else if (current >= twelvePercent*2 && current < twelvePercent*3) {

							if (SQTechPumps.machineLiquidTypeIdMap.get(machine) != null) {
								waterBlock.setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 6, false);
							}

						} else if (current >= twelvePercent*3 && current < twelvePercent*4) {

							if (SQTechPumps.machineLiquidTypeIdMap.get(machine) != null) {
								waterBlock.setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 5, false);
							}

						} else if (current >= twelvePercent*4 && current < twelvePercent*5) {

							if (SQTechPumps.machineLiquidTypeIdMap.get(machine) != null) {
								waterBlock.setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 4, false);
							}

						} else if (current >= twelvePercent*5 && current < twelvePercent*6) {

							if (SQTechPumps.machineLiquidTypeIdMap.get(machine) != null) {
								waterBlock.setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 3, false);
							}

						} else if (current >= twelvePercent*6 && current < twelvePercent*7) {

							if (SQTechPumps.machineLiquidTypeIdMap.get(machine) != null) {
								waterBlock.setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 2, false);
							}

						} else if (current >= twelvePercent*7 && current < twelvePercent*8) {

							if (SQTechPumps.machineLiquidTypeIdMap.get(machine) != null) {
								waterBlock.setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 1, false);
							}

						} else if (current >= twelvePercent*8) {

							if (SQTechPumps.machineLiquidTypeIdMap.get(machine) != null) {
								waterBlock.setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 0, false);
							}

						}

						updateBlock(waterBlock.getRelative(BlockFace.NORTH));

					}

				}

			}

		});

		for (Player player : SQTechBase.currentGui.keySet()) {

			if (SQTechBase.currentGui.get(player).id == machine.getGUI(player).id) {

				if (player.getOpenInventory() != null) {

					if (player.getOpenInventory().getTitle().equals(ChatColor.BLUE + "SQTech - Small Tank")) {

						//The liquid info sign
						Fluid fluid = null;
						int amount = 0;
						String name = "none";

						for (Fluid f : SQTechBase.fluids) {
							if (machine.getMaxLiquid(f) > 0) {
								fluid = f;
							}
						}
						if (fluid != null) {
							amount = machine.getLiquid(fluid);
							name = fluid.name;
						}

						String[] infoLore = new String[] {
								ChatColor.BLUE + "This tank can store " + name,
								ChatColor.BLUE + "Amount in millibuckets: " + PumpGUI.format(amount) + "/" + PumpGUI.format(SQTechPumps.config.getInt("small tank max liquid")),
								ChatColor.BLUE + "Amount in buckets: " + PumpGUI.format(amount/1000) + "/" + PumpGUI.format(SQTechPumps.config.getInt("small tank max liquid")/1000),
								ChatColor.RED + "" + ChatColor.MAGIC + "Contraband"
						};
						player.getOpenInventory().setItem(17, InventoryUtils.createSpecialItem(Material.BUCKET, (short) 0, ChatColor.BLUE + "Tank Info", infoLore));


					}

				}

			}

		}
		
		lastUpdate = System.currentTimeMillis();
		
	}

	@SuppressWarnings("deprecation")
	private static void updateBlock(Block b) {

		Bukkit.getScheduler().runTask(SQTechPumps.plugin, new BukkitRunnable() {

			@Override
			public void run() {

				Byte data = b.getData();
				int typeId = b.getTypeId();

				b.setType(Material.AIR);
				b.setTypeIdAndData(typeId, data, false);

				return;

			}

		});

	}

	public static Block getMiddleBlock(GUIBlock block) {

		Block middleStainedGlass = block.getLocation().getBlock().getRelative(BlockFace.UP);

		BlockFace[] faces = new BlockFace[]{
				BlockFace.NORTH,
				BlockFace.EAST,
				BlockFace.WEST,
				BlockFace.SOUTH
		};

		for (BlockFace f : faces) {
			BlockFace left = DirectionUtils.getLeft(f);
			BlockFace right = DirectionUtils.getRight(f);
			BlockFace up = BlockFace.UP;
			BlockFace down = BlockFace.DOWN;

			if (middleStainedGlass.getType() == Material.STAINED_GLASS_PANE) {
				if (middleStainedGlass.getRelative(right).getType() == Material.STAINED_CLAY) {
					if (middleStainedGlass.getRelative(left).getType() == Material.STAINED_CLAY) {
						if (middleStainedGlass.getRelative(up).getType() == Material.STAINED_CLAY) {
							if (middleStainedGlass.getRelative(down).getType() == Material.LAPIS_BLOCK) {
								if (middleStainedGlass.getRelative(f).getType() == Material.AIR || middleStainedGlass.getRelative(f).getType() == Material.STATIONARY_WATER || middleStainedGlass.getRelative(f).getType() == Material.STATIONARY_LAVA) {
									if (middleStainedGlass.getRelative(f).getRelative(left).getType() == Material.STAINED_GLASS_PANE) {
										if (middleStainedGlass.getRelative(f).getRelative(right).getType() == Material.STAINED_GLASS_PANE) {
											if (middleStainedGlass.getRelative(f).getRelative(up).getType() == Material.STAINED_CLAY) {
												if (middleStainedGlass.getRelative(f).getRelative(down).getType() == Material.STAINED_CLAY) {
													if (middleStainedGlass.getRelative(f).getRelative(up).getRelative(left).getType() == Material.STAINED_CLAY) {
														if (middleStainedGlass.getRelative(f).getRelative(up).getRelative(right).getType() == Material.STAINED_CLAY) {
															if (middleStainedGlass.getRelative(f).getRelative(down).getRelative(left).getType() == Material.STAINED_CLAY) {
																if (middleStainedGlass.getRelative(f).getRelative(down).getRelative(right).getType() == Material.STAINED_CLAY) {
																	if (middleStainedGlass.getRelative(f).getRelative(f).getType() == Material.STAINED_GLASS_PANE) {
																		if (middleStainedGlass.getRelative(f).getRelative(f).getRelative(right).getType() == Material.STAINED_CLAY) {
																			if (middleStainedGlass.getRelative(f).getRelative(f).getRelative(left).getType() == Material.STAINED_CLAY) {
																				if (middleStainedGlass.getRelative(f).getRelative(f).getRelative(up).getType() == Material.STAINED_CLAY) {
																					if (middleStainedGlass.getRelative(f).getRelative(f).getRelative(down).getType() == Material.STAINED_CLAY) {
																						return middleStainedGlass.getRelative(f);
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



}
