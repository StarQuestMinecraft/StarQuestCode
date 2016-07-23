package com.martinjonsson01.sqtechpumps.objects;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Player;

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

import net.md_5.bungee.api.ChatColor;

public class SmallTank extends MachineType{

	public SmallTank(int maxEnergy) {

		super(maxEnergy);
		name = "Small Tank";

	}

	@Override
	public boolean detectStructure(GUIBlock block) {

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
								if (middleStainedGlass.getRelative(f).getType() == Material.AIR) {
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

		return false;

	}

	@Override
	public GUI getGUI(Player player, int id) {

		return new SmallTankGUI(player, id);

	}

	@Override
	public void updateLiquid(Machine machine) {
		
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

	}





}
