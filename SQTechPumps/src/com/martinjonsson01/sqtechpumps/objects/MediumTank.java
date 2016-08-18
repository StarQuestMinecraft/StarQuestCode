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
import com.martinjonsson01.sqtechpumps.gui.MediumTankGUI;
import com.martinjonsson01.sqtechpumps.gui.PumpGUI;
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

public class MediumTank extends MachineType{

	public MediumTank(int maxEnergy) {

		super(maxEnergy);
		name = "Medium Tank";

	}

	@Override
	public boolean detectStructure(GUIBlock block) {
		
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

			Block firstLevel = block.getLocation().getBlock();
			Block firstLevel1 = firstLevel.getRelative(f);
			Block firstLevel2 = firstLevel.getRelative(f).getRelative(f);
			Block firstLevel3 = firstLevel.getRelative(f).getRelative(f).getRelative(f);
			Block firstLevel4 = firstLevel.getRelative(f).getRelative(f).getRelative(f).getRelative(f);

			Block secondLevel = firstLevel.getRelative(up);
			Block secondLevel1 = secondLevel.getRelative(f);
			Block secondLevel2 = secondLevel.getRelative(f).getRelative(f);
			Block secondLevel3 = secondLevel.getRelative(f).getRelative(f).getRelative(f);
			Block secondLevel4 = secondLevel.getRelative(f).getRelative(f).getRelative(f).getRelative(f);

			Block thirdLevel = secondLevel.getRelative(up);
			Block thirdLevel1 = thirdLevel.getRelative(f);
			Block thirdLevel2 = thirdLevel.getRelative(f).getRelative(f);
			Block thirdLevel3 = thirdLevel.getRelative(f).getRelative(f).getRelative(f);
			Block thirdLevel4 = thirdLevel.getRelative(f).getRelative(f).getRelative(f).getRelative(f);

			Block fourthLevel = thirdLevel.getRelative(up);
			Block fourthLevel1 = fourthLevel.getRelative(f);
			Block fourthLevel2 = fourthLevel.getRelative(f).getRelative(f);
			Block fourthLevel3 = fourthLevel.getRelative(f).getRelative(f).getRelative(f);
			Block fourthLevel4 = fourthLevel.getRelative(f).getRelative(f).getRelative(f).getRelative(f);

			Block fifthLevel = fourthLevel.getRelative(up);
			Block fifthLevel1 = fifthLevel.getRelative(f);
			Block fifthLevel2 = fifthLevel.getRelative(f).getRelative(f);
			Block fifthLevel3 = fifthLevel.getRelative(f).getRelative(f).getRelative(f);
			Block fifthLevel4 = fifthLevel.getRelative(f).getRelative(f).getRelative(f).getRelative(f);

			Block sixthLevel = fifthLevel.getRelative(up);
			Block sixthLevel1 = sixthLevel.getRelative(f);
			Block sixthLevel2 = sixthLevel.getRelative(f).getRelative(f);
			Block sixthLevel3 = sixthLevel.getRelative(f).getRelative(f).getRelative(f);
			Block sixthLevel4 = sixthLevel.getRelative(f).getRelative(f).getRelative(f).getRelative(f);

			if (firstLevel.getType() == Material.LAPIS_BLOCK) {
				if (firstLevel.getRelative(right).getType() == Material.STAINED_CLAY) {
					if (firstLevel.getRelative(left).getType() == Material.STAINED_CLAY) {
						if (firstLevel1.getType() == Material.STAINED_CLAY) {
							if (firstLevel1.getRelative(left).getType() == Material.STAINED_CLAY) {
								if (firstLevel1.getRelative(left).getRelative(left).getType() == Material.STAINED_CLAY) {
									if (firstLevel1.getRelative(right).getType() == Material.STAINED_CLAY) {
										if (firstLevel1.getRelative(right).getRelative(right).getType() == Material.STAINED_CLAY) {
											if (firstLevel2.getType() == Material.STAINED_CLAY) {
												if (firstLevel2.getRelative(left).getType() == Material.STAINED_CLAY) {
													if (firstLevel2.getRelative(left).getRelative(left).getType() == Material.STAINED_CLAY) {
														if (firstLevel2.getRelative(right).getType() == Material.STAINED_CLAY) {
															if (firstLevel2.getRelative(right).getRelative(right).getType() == Material.STAINED_CLAY) {
																if (firstLevel3.getType() == Material.STAINED_CLAY) {
																	if (firstLevel3.getRelative(left).getType() == Material.STAINED_CLAY) {
																		if (firstLevel3.getRelative(left).getRelative(left).getType() == Material.STAINED_CLAY) {
																			if (firstLevel3.getRelative(right).getType() == Material.STAINED_CLAY) {
																				if (firstLevel3.getRelative(right).getRelative(right).getType() == Material.STAINED_CLAY) {
																					if (firstLevel4.getType() == Material.STAINED_CLAY) {
																						if (firstLevel4.getRelative(right).getType() == Material.STAINED_CLAY) {
																							if (firstLevel4.getRelative(left).getType() == Material.STAINED_CLAY) {

																								if (secondLevel.getType() == Material.STAINED_GLASS_PANE) {
																									if (secondLevel.getRelative(right).getType() == Material.STAINED_GLASS_PANE) {
																										if (secondLevel.getRelative(right).getRelative(right).getType() == Material.STAINED_CLAY) {
																											if (secondLevel.getRelative(left).getType() == Material.STAINED_GLASS_PANE) {
																												if (secondLevel.getRelative(left).getRelative(left).getType() == Material.STAINED_CLAY) {
																													if (secondLevel1.getType() == Material.AIR || secondLevel1.getType() == Material.STATIONARY_LAVA || secondLevel1.getType() == Material.STATIONARY_WATER) {
																														if (secondLevel1.getRelative(right).getType() == Material.AIR || secondLevel1.getRelative(right).getType() == Material.STATIONARY_LAVA || secondLevel1.getRelative(right).getType() == Material.STATIONARY_WATER) {
																															if (secondLevel1.getRelative(right).getRelative(right).getType() == Material.STAINED_GLASS_PANE) {
																																if (secondLevel1.getRelative(left).getType() == Material.AIR || secondLevel1.getRelative(left).getType() == Material.STATIONARY_LAVA || secondLevel1.getRelative(left).getType() == Material.STATIONARY_WATER) {
																																	if (secondLevel1.getRelative(left).getRelative(left).getType() == Material.STAINED_GLASS_PANE) {
																																		if (secondLevel2.getType() == Material.AIR || secondLevel2.getType() == Material.STATIONARY_LAVA || secondLevel2.getType() == Material.STATIONARY_WATER) {
																																			if (secondLevel2.getRelative(right).getType() == Material.AIR || secondLevel2.getRelative(right).getType() == Material.STATIONARY_LAVA || secondLevel2.getRelative(right).getType() == Material.STATIONARY_WATER) {
																																				if (secondLevel2.getRelative(right).getRelative(right).getType() == Material.STAINED_GLASS_PANE) {
																																					if (secondLevel2.getRelative(left).getType() == Material.AIR || secondLevel2.getRelative(left).getType() == Material.STATIONARY_LAVA || secondLevel2.getRelative(left).getType() == Material.STATIONARY_WATER) {
																																						if (secondLevel2.getRelative(left).getRelative(left).getType() == Material.STAINED_GLASS_PANE) {
																																							if (secondLevel3.getType() == Material.AIR || secondLevel3.getType() == Material.STATIONARY_LAVA || secondLevel3.getType() == Material.STATIONARY_WATER) {
																																								if (secondLevel3.getRelative(right).getType() == Material.AIR || secondLevel3.getRelative(right).getType() == Material.STATIONARY_LAVA || secondLevel3.getRelative(right).getType() == Material.STATIONARY_WATER) {
																																									if (secondLevel3.getRelative(right).getRelative(right).getType() == Material.STAINED_GLASS_PANE) {
																																										if (secondLevel3.getRelative(left).getType() == Material.AIR || secondLevel3.getRelative(left).getType() == Material.STATIONARY_LAVA || secondLevel3.getRelative(left).getType() == Material.STATIONARY_WATER) {
																																											if (secondLevel3.getRelative(left).getRelative(left).getType() == Material.STAINED_GLASS_PANE) {
																																												if (secondLevel4.getType() == Material.STAINED_GLASS_PANE) {
																																													if (secondLevel4.getRelative(right).getType() == Material.STAINED_GLASS_PANE) {
																																														if (secondLevel4.getRelative(right).getRelative(right).getType() == Material.STAINED_CLAY) {
																																															if (secondLevel4.getRelative(left).getType() == Material.STAINED_GLASS_PANE) {
																																																if (secondLevel4.getRelative(left).getRelative(left).getType() == Material.STAINED_CLAY) {

																																																	if (thirdLevel.getType() == Material.STAINED_GLASS_PANE) {
																																																		if (thirdLevel.getRelative(right).getType() == Material.STAINED_GLASS_PANE) {
																																																			if (thirdLevel.getRelative(right).getRelative(right).getType() == Material.STAINED_CLAY) {
																																																				if (thirdLevel.getRelative(left).getType() == Material.STAINED_GLASS_PANE) {
																																																					if (thirdLevel.getRelative(left).getRelative(left).getType() == Material.STAINED_CLAY) {
																																																						if (thirdLevel1.getType() == Material.AIR || thirdLevel1.getType() == Material.STATIONARY_LAVA || thirdLevel1.getType() == Material.STATIONARY_WATER) {
																																																							if (thirdLevel1.getRelative(right).getType() == Material.AIR || thirdLevel1.getRelative(right).getType() == Material.STATIONARY_LAVA || thirdLevel1.getRelative(right).getType() == Material.STATIONARY_WATER) {
																																																								if (thirdLevel1.getRelative(right).getRelative(right).getType() == Material.STAINED_GLASS_PANE) {
																																																									if (thirdLevel1.getRelative(left).getType() == Material.AIR || thirdLevel1.getRelative(left).getType() == Material.STATIONARY_LAVA || thirdLevel1.getRelative(left).getType() == Material.STATIONARY_WATER) {
																																																										if (thirdLevel1.getRelative(left).getRelative(left).getType() == Material.STAINED_GLASS_PANE) {
																																																											if (thirdLevel2.getType() == Material.AIR || thirdLevel2.getType() == Material.STATIONARY_LAVA || thirdLevel2.getType() == Material.STATIONARY_WATER) {
																																																												if (thirdLevel2.getRelative(right).getType() == Material.AIR || thirdLevel2.getRelative(right).getType() == Material.STATIONARY_LAVA || thirdLevel2.getRelative(right).getType() == Material.STATIONARY_WATER) {
																																																													if (thirdLevel2.getRelative(right).getRelative(right).getType() == Material.STAINED_GLASS_PANE) {
																																																														if (thirdLevel2.getRelative(left).getType() == Material.AIR || thirdLevel2.getRelative(left).getType() == Material.STATIONARY_LAVA || thirdLevel2.getRelative(left).getType() == Material.STATIONARY_WATER) {
																																																															if (thirdLevel2.getRelative(left).getRelative(left).getType() == Material.STAINED_GLASS_PANE) {
																																																																if (thirdLevel3.getType() == Material.AIR || thirdLevel3.getType() == Material.STATIONARY_LAVA || thirdLevel3.getType() == Material.STATIONARY_WATER) {
																																																																	if (thirdLevel3.getRelative(right).getType() == Material.AIR || thirdLevel3.getRelative(right).getType() == Material.STATIONARY_LAVA || thirdLevel3.getRelative(right).getType() == Material.STATIONARY_WATER) {
																																																																		if (thirdLevel3.getRelative(right).getRelative(right).getType() == Material.STAINED_GLASS_PANE) {
																																																																			if (thirdLevel3.getRelative(left).getType() == Material.AIR || thirdLevel3.getRelative(left).getType() == Material.STATIONARY_LAVA || thirdLevel3.getRelative(left).getType() == Material.STATIONARY_WATER) {
																																																																				if (thirdLevel3.getRelative(left).getRelative(left).getType() == Material.STAINED_GLASS_PANE) {
																																																																					if (thirdLevel4.getType() == Material.STAINED_GLASS_PANE) {
																																																																						if (thirdLevel4.getRelative(right).getType() == Material.STAINED_GLASS_PANE) {
																																																																							if (thirdLevel4.getRelative(right).getRelative(right).getType() == Material.STAINED_CLAY) {
																																																																								if (thirdLevel4.getRelative(left).getType() == Material.STAINED_GLASS_PANE) {
																																																																									if (thirdLevel4.getRelative(left).getRelative(left).getType() == Material.STAINED_CLAY) {

																																																																										if (fourthLevel.getType() == Material.STAINED_GLASS_PANE) {
																																																																											if (fourthLevel.getRelative(right).getType() == Material.STAINED_GLASS_PANE) {
																																																																												if (fourthLevel.getRelative(right).getRelative(right).getType() == Material.STAINED_CLAY) {
																																																																													if (fourthLevel.getRelative(left).getType() == Material.STAINED_GLASS_PANE) {
																																																																														if (fourthLevel.getRelative(left).getRelative(left).getType() == Material.STAINED_CLAY) {
																																																																															if (fourthLevel1.getType() == Material.AIR || fourthLevel1.getType() == Material.STATIONARY_LAVA || fourthLevel1.getType() == Material.STATIONARY_WATER) {
																																																																																if (fourthLevel1.getRelative(right).getType() == Material.AIR || fourthLevel1.getRelative(right).getType() == Material.STATIONARY_LAVA || fourthLevel1.getRelative(right).getType() == Material.STATIONARY_WATER) {
																																																																																	if (fourthLevel1.getRelative(right).getRelative(right).getType() == Material.STAINED_GLASS_PANE) {
																																																																																		if (fourthLevel1.getRelative(left).getType() == Material.AIR || fourthLevel1.getRelative(left).getType() == Material.STATIONARY_LAVA || fourthLevel1.getRelative(left).getType() == Material.STATIONARY_WATER) {
																																																																																			if (fourthLevel1.getRelative(left).getRelative(left).getType() == Material.STAINED_GLASS_PANE) {
																																																																																				if (fourthLevel2.getType() == Material.AIR || fourthLevel2.getType() == Material.STATIONARY_LAVA || fourthLevel2.getType() == Material.STATIONARY_WATER) {
																																																																																					if (fourthLevel2.getRelative(right).getType() == Material.AIR || fourthLevel2.getRelative(right).getType() == Material.STATIONARY_LAVA || fourthLevel2.getRelative(right).getType() == Material.STATIONARY_WATER) {
																																																																																						if (fourthLevel2.getRelative(right).getRelative(right).getType() == Material.STAINED_GLASS_PANE) {
																																																																																							if (fourthLevel2.getRelative(left).getType() == Material.AIR || fourthLevel2.getRelative(left).getType() == Material.STATIONARY_LAVA || fourthLevel2.getRelative(left).getType() == Material.STATIONARY_WATER) {
																																																																																								if (fourthLevel2.getRelative(left).getRelative(left).getType() == Material.STAINED_GLASS_PANE) {
																																																																																									if (fourthLevel3.getType() == Material.AIR || fourthLevel3.getType() == Material.STATIONARY_LAVA || fourthLevel3.getType() == Material.STATIONARY_WATER) {
																																																																																										if (fourthLevel3.getRelative(right).getType() == Material.AIR || fourthLevel3.getRelative(right).getType() == Material.STATIONARY_LAVA || fourthLevel3.getRelative(right).getType() == Material.STATIONARY_WATER) {
																																																																																											if (fourthLevel3.getRelative(right).getRelative(right).getType() == Material.STAINED_GLASS_PANE) {
																																																																																												if (fourthLevel3.getRelative(left).getType() == Material.AIR || fourthLevel3.getRelative(left).getType() == Material.STATIONARY_LAVA || fourthLevel3.getRelative(left).getType() == Material.STATIONARY_WATER) {
																																																																																													if (fourthLevel3.getRelative(left).getRelative(left).getType() == Material.STAINED_GLASS_PANE) {
																																																																																														if (fourthLevel4.getType() == Material.STAINED_GLASS_PANE) {
																																																																																															if (fourthLevel4.getRelative(right).getType() == Material.STAINED_GLASS_PANE) {
																																																																																																if (fourthLevel4.getRelative(right).getRelative(right).getType() == Material.STAINED_CLAY) {
																																																																																																	if (fourthLevel4.getRelative(left).getType() == Material.STAINED_GLASS_PANE) {
																																																																																																		if (fourthLevel4.getRelative(left).getRelative(left).getType() == Material.STAINED_CLAY) {

																																																																																																			if (fifthLevel.getType() == Material.STAINED_GLASS_PANE) {
																																																																																																				if (fifthLevel.getRelative(right).getType() == Material.STAINED_GLASS_PANE) {
																																																																																																					if (fifthLevel.getRelative(right).getRelative(right).getType() == Material.STAINED_CLAY) {
																																																																																																						if (fifthLevel.getRelative(left).getType() == Material.STAINED_GLASS_PANE) {
																																																																																																							if (fifthLevel.getRelative(left).getRelative(left).getType() == Material.STAINED_CLAY) {
																																																																																																								if (fifthLevel1.getType() == Material.AIR || fifthLevel1.getType() == Material.STATIONARY_LAVA || fifthLevel1.getType() == Material.STATIONARY_WATER) {
																																																																																																									if (fifthLevel1.getRelative(right).getType() == Material.AIR || fifthLevel1.getRelative(right).getType() == Material.STATIONARY_LAVA || fifthLevel1.getRelative(right).getType() == Material.STATIONARY_WATER) {
																																																																																																										if (fifthLevel1.getRelative(right).getRelative(right).getType() == Material.STAINED_GLASS_PANE) {
																																																																																																											if (fifthLevel1.getRelative(left).getType() == Material.AIR || fifthLevel1.getRelative(left).getType() == Material.STATIONARY_LAVA || fifthLevel1.getRelative(left).getType() == Material.STATIONARY_WATER) {
																																																																																																												if (fifthLevel1.getRelative(left).getRelative(left).getType() == Material.STAINED_GLASS_PANE) {
																																																																																																													if (fifthLevel2.getType() == Material.AIR || fifthLevel2.getType() == Material.STATIONARY_LAVA || fifthLevel2.getType() == Material.STATIONARY_WATER) {
																																																																																																														if (fifthLevel2.getRelative(right).getType() == Material.AIR || fifthLevel2.getRelative(right).getType() == Material.STATIONARY_LAVA || fifthLevel2.getRelative(right).getType() == Material.STATIONARY_WATER) {
																																																																																																															if (fifthLevel2.getRelative(right).getRelative(right).getType() == Material.STAINED_GLASS_PANE) {
																																																																																																																if (fifthLevel2.getRelative(left).getType() == Material.AIR || fifthLevel2.getRelative(left).getType() == Material.STATIONARY_LAVA || fifthLevel2.getRelative(left).getType() == Material.STATIONARY_WATER) {
																																																																																																																	if (fifthLevel2.getRelative(left).getRelative(left).getType() == Material.STAINED_GLASS_PANE) {
																																																																																																																		if (fifthLevel3.getType() == Material.AIR || fifthLevel3.getType() == Material.STATIONARY_LAVA || fifthLevel3.getType() == Material.STATIONARY_WATER) {
																																																																																																																			if (fifthLevel3.getRelative(right).getType() == Material.AIR || fifthLevel3.getRelative(right).getType() == Material.STATIONARY_LAVA || fifthLevel3.getRelative(right).getType() == Material.STATIONARY_WATER) {
																																																																																																																				if (fifthLevel3.getRelative(right).getRelative(right).getType() == Material.STAINED_GLASS_PANE) {
																																																																																																																					if (fifthLevel3.getRelative(left).getType() == Material.AIR || fifthLevel3.getRelative(left).getType() == Material.STATIONARY_LAVA || fifthLevel3.getRelative(left).getType() == Material.STATIONARY_WATER) {
																																																																																																																						if (fifthLevel3.getRelative(left).getRelative(left).getType() == Material.STAINED_GLASS_PANE) {
																																																																																																																							if (fifthLevel4.getType() == Material.STAINED_GLASS_PANE) {
																																																																																																																								if (fifthLevel4.getRelative(right).getType() == Material.STAINED_GLASS_PANE) {
																																																																																																																									if (fifthLevel4.getRelative(right).getRelative(right).getType() == Material.STAINED_CLAY) {
																																																																																																																										if (fifthLevel4.getRelative(left).getType() == Material.STAINED_GLASS_PANE) {
																																																																																																																											if (fifthLevel4.getRelative(left).getRelative(left).getType() == Material.STAINED_CLAY) {

																																																																																																																												if (sixthLevel.getType() == Material.STAINED_CLAY) {
																																																																																																																													if (sixthLevel.getRelative(right).getType() == Material.STAINED_CLAY) {
																																																																																																																														if (sixthLevel.getRelative(left).getType() == Material.STAINED_CLAY) {
																																																																																																																															if (sixthLevel1.getType() == Material.STAINED_CLAY) {
																																																																																																																																if (sixthLevel1.getRelative(left).getType() == Material.STAINED_CLAY) {
																																																																																																																																	if (sixthLevel1.getRelative(left).getRelative(left).getType() == Material.STAINED_CLAY) {
																																																																																																																																		if (sixthLevel1.getRelative(right).getType() == Material.STAINED_CLAY) {
																																																																																																																																			if (sixthLevel1.getRelative(right).getRelative(right).getType() == Material.STAINED_CLAY) {
																																																																																																																																				if (sixthLevel2.getType() == Material.STAINED_CLAY) {
																																																																																																																																					if (sixthLevel2.getRelative(left).getType() == Material.STAINED_CLAY) {
																																																																																																																																						if (sixthLevel2.getRelative(left).getRelative(left).getType() == Material.STAINED_CLAY) {
																																																																																																																																							if (sixthLevel2.getRelative(right).getType() == Material.STAINED_CLAY) {
																																																																																																																																								if (sixthLevel2.getRelative(right).getRelative(right).getType() == Material.STAINED_CLAY) {
																																																																																																																																									if (sixthLevel3.getType() == Material.STAINED_CLAY) {
																																																																																																																																										if (sixthLevel3.getRelative(left).getType() == Material.STAINED_CLAY) {
																																																																																																																																											if (sixthLevel3.getRelative(left).getRelative(left).getType() == Material.STAINED_CLAY) {
																																																																																																																																												if (sixthLevel3.getRelative(right).getType() == Material.STAINED_CLAY) {
																																																																																																																																													if (sixthLevel3.getRelative(right).getRelative(right).getType() == Material.STAINED_CLAY) {
																																																																																																																																														if (sixthLevel4.getType() == Material.STAINED_CLAY) {
																																																																																																																																															if (sixthLevel4.getRelative(right).getType() == Material.STAINED_CLAY) {
																																																																																																																																																if (sixthLevel4.getRelative(left).getType() == Material.STAINED_CLAY) {
																																																																																																																																																	
																																																																																																																																																	MediumTank.updatePhysicalLiquid(machine);
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

		return new MediumTankGUI(player, id);

	}

	@SuppressWarnings("deprecation")
	public static void updatePhysicalLiquid(Machine machine) {
		
		if (machine == null) return;
		
		Block waterBlock = getMiddleBlock(machine.getGUIBlock());
		Block waterBlock2 = waterBlock.getRelative(BlockFace.UP);
		Block waterBlock3 = waterBlock2.getRelative(BlockFace.UP);
		Block waterBlock4 = waterBlock3.getRelative(BlockFace.UP);
		
		List<Block> waterBlocks = new ArrayList<Block>();
		if (SQTechPumps.tankWaterBlocks.get(machine) != null) {
			waterBlocks = SQTechPumps.tankWaterBlocks.get(machine);
		}
		
		if (!waterBlocks.contains(waterBlock))  {
			waterBlocks.add(waterBlock);
		}
		if (!waterBlocks.contains(waterBlock.getRelative(BlockFace.NORTH))) {
			waterBlocks.add(waterBlock.getRelative(BlockFace.NORTH));
		}
		if (!waterBlocks.contains(waterBlock.getRelative(BlockFace.EAST))) {
			waterBlocks.add(waterBlock.getRelative(BlockFace.EAST));
		}
		if (!waterBlocks.contains(waterBlock.getRelative(BlockFace.WEST))) {
			waterBlocks.add(waterBlock.getRelative(BlockFace.WEST));
		}
		if (!waterBlocks.contains(waterBlock.getRelative(BlockFace.SOUTH))) {
			waterBlocks.add(waterBlock.getRelative(BlockFace.SOUTH));
		}
		if (!waterBlocks.contains(waterBlock.getRelative(BlockFace.NORTH_EAST))) {
			waterBlocks.add(waterBlock.getRelative(BlockFace.NORTH_EAST));
		}
		if (!waterBlocks.contains(waterBlock.getRelative(BlockFace.NORTH_WEST))) {
			waterBlocks.add(waterBlock.getRelative(BlockFace.NORTH_WEST));
		}
		if (!waterBlocks.contains(waterBlock.getRelative(BlockFace.SOUTH_EAST))) {
			waterBlocks.add(waterBlock.getRelative(BlockFace.SOUTH_EAST));
		}
		if (!waterBlocks.contains(waterBlock.getRelative(BlockFace.SOUTH_WEST))) {
			waterBlocks.add(waterBlock.getRelative(BlockFace.SOUTH_WEST));
		}
			
		//INBETWEEN
		
		if (!waterBlocks.contains(waterBlock2))  {
			waterBlocks.add(waterBlock2);
		}
		if (!waterBlocks.contains(waterBlock2.getRelative(BlockFace.NORTH))) {
			waterBlocks.add(waterBlock2.getRelative(BlockFace.NORTH));
		}
		if (!waterBlocks.contains(waterBlock2.getRelative(BlockFace.EAST))) {
			waterBlocks.add(waterBlock2.getRelative(BlockFace.EAST));
		}
		if (!waterBlocks.contains(waterBlock2.getRelative(BlockFace.WEST))) {
			waterBlocks.add(waterBlock2.getRelative(BlockFace.WEST));
		}
		if (!waterBlocks.contains(waterBlock2.getRelative(BlockFace.SOUTH))) {
			waterBlocks.add(waterBlock2.getRelative(BlockFace.SOUTH));
		}
		if (!waterBlocks.contains(waterBlock2.getRelative(BlockFace.NORTH_EAST))) {
			waterBlocks.add(waterBlock2.getRelative(BlockFace.NORTH_EAST));
		}
		if (!waterBlocks.contains(waterBlock2.getRelative(BlockFace.NORTH_WEST))) {
			waterBlocks.add(waterBlock2.getRelative(BlockFace.NORTH_WEST));
		}
		if (!waterBlocks.contains(waterBlock2.getRelative(BlockFace.SOUTH_EAST))) {
			waterBlocks.add(waterBlock2.getRelative(BlockFace.SOUTH_EAST));
		}
		if (!waterBlocks.contains(waterBlock2.getRelative(BlockFace.SOUTH_WEST))) {
			waterBlocks.add(waterBlock2.getRelative(BlockFace.SOUTH_WEST));
		}
			
		//INBETWEEN
		
		if (!waterBlocks.contains(waterBlock3))  {
			waterBlocks.add(waterBlock3);
		}
		if (!waterBlocks.contains(waterBlock3.getRelative(BlockFace.NORTH))) {
			waterBlocks.add(waterBlock3.getRelative(BlockFace.NORTH));
		}
		if (!waterBlocks.contains(waterBlock3.getRelative(BlockFace.EAST))) {
			waterBlocks.add(waterBlock3.getRelative(BlockFace.EAST));
		}
		if (!waterBlocks.contains(waterBlock3.getRelative(BlockFace.WEST))) {
			waterBlocks.add(waterBlock3.getRelative(BlockFace.WEST));
		}
		if (!waterBlocks.contains(waterBlock3.getRelative(BlockFace.SOUTH))) {
			waterBlocks.add(waterBlock3.getRelative(BlockFace.SOUTH));
		}
		if (!waterBlocks.contains(waterBlock3.getRelative(BlockFace.NORTH_EAST))) {
			waterBlocks.add(waterBlock3.getRelative(BlockFace.NORTH_EAST));
		}
		if (!waterBlocks.contains(waterBlock3.getRelative(BlockFace.NORTH_WEST))) {
			waterBlocks.add(waterBlock3.getRelative(BlockFace.NORTH_WEST));
		}
		if (!waterBlocks.contains(waterBlock3.getRelative(BlockFace.SOUTH_EAST))) {
			waterBlocks.add(waterBlock3.getRelative(BlockFace.SOUTH_EAST));
		}
		if (!waterBlocks.contains(waterBlock3.getRelative(BlockFace.SOUTH_WEST))) {
			waterBlocks.add(waterBlock3.getRelative(BlockFace.SOUTH_WEST));
		}
			
		//INBETWEEN
		
		if (!waterBlocks.contains(waterBlock4))  {
			waterBlocks.add(waterBlock4);
		}
		if (!waterBlocks.contains(waterBlock4.getRelative(BlockFace.NORTH))) {
			waterBlocks.add(waterBlock4.getRelative(BlockFace.NORTH));
		}
		if (!waterBlocks.contains(waterBlock4.getRelative(BlockFace.EAST))) {
			waterBlocks.add(waterBlock4.getRelative(BlockFace.EAST));
		}
		if (!waterBlocks.contains(waterBlock4.getRelative(BlockFace.WEST))) {
			waterBlocks.add(waterBlock4.getRelative(BlockFace.WEST));
		}
		if (!waterBlocks.contains(waterBlock4.getRelative(BlockFace.SOUTH))) {
			waterBlocks.add(waterBlock4.getRelative(BlockFace.SOUTH));
		}
		if (!waterBlocks.contains(waterBlock4.getRelative(BlockFace.NORTH_EAST))) {
			waterBlocks.add(waterBlock4.getRelative(BlockFace.NORTH_EAST));
		}
		if (!waterBlocks.contains(waterBlock4.getRelative(BlockFace.NORTH_WEST))) {
			waterBlocks.add(waterBlock4.getRelative(BlockFace.NORTH_WEST));
		}
		if (!waterBlocks.contains(waterBlock4.getRelative(BlockFace.SOUTH_EAST))) {
			waterBlocks.add(waterBlock4.getRelative(BlockFace.SOUTH_EAST));
		}
		if (!waterBlocks.contains(waterBlock4.getRelative(BlockFace.SOUTH_WEST))) {
			waterBlocks.add(waterBlock4.getRelative(BlockFace.SOUTH_WEST));
		}
			
		SQTechPumps.tankWaterBlocks.put(machine, waterBlocks);

		for (Fluid f : SQTechBase.fluids) {

			if (!(machine.getMaxLiquid(f) > 1)) continue;

			if (f.name == "Water") {
				SQTechPumps.machineLiquidTypeIdMap.put(machine, 9);
			} else if (f.name == "Lava") {
				SQTechPumps.machineLiquidTypeIdMap.put(machine, 11);
			}

			int max = machine.getMaxLiquid(f);

			int current = machine.getLiquid(f);

			double threePercent = (max/100) * 3.125;

			if (current >= threePercent && current < threePercent*2) {

				Bukkit.getScheduler().runTask(SQTechPumps.plugin, new BukkitRunnable() {
					@Override
					public void run() {
						if (SQTechPumps.machineLiquidTypeIdMap.get(machine) != null) {
							waterBlock.setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 7, false);
							waterBlock.getRelative(BlockFace.NORTH).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 7, false);
							waterBlock.getRelative(BlockFace.EAST).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 7, false);
							waterBlock.getRelative(BlockFace.WEST).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 7, false);
							waterBlock.getRelative(BlockFace.SOUTH).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 7, false);
							waterBlock.getRelative(BlockFace.NORTH_EAST).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 7, false);
							waterBlock.getRelative(BlockFace.NORTH_WEST).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 7, false);
							waterBlock.getRelative(BlockFace.SOUTH_EAST).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 7, false);
							waterBlock.getRelative(BlockFace.SOUTH_WEST).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 7, false);
						}
					}
				});

			} else if (current >= threePercent*2 && current < threePercent*3) {

				Bukkit.getScheduler().runTask(SQTechPumps.plugin, new BukkitRunnable() {
					@Override
					public void run() {
						if (SQTechPumps.machineLiquidTypeIdMap.get(machine) != null) {
							waterBlock.setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 6, false);
							waterBlock.getRelative(BlockFace.NORTH).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 6, false);
							waterBlock.getRelative(BlockFace.EAST).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 6, false);
							waterBlock.getRelative(BlockFace.WEST).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 6, false);
							waterBlock.getRelative(BlockFace.SOUTH).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 6, false);
							waterBlock.getRelative(BlockFace.NORTH_EAST).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 6, false);
							waterBlock.getRelative(BlockFace.NORTH_WEST).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 6, false);
							waterBlock.getRelative(BlockFace.SOUTH_EAST).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 6, false);
							waterBlock.getRelative(BlockFace.SOUTH_WEST).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 6, false);
						}
					}
				});

			} else if (current >= threePercent*3 && current < threePercent*4) {

				Bukkit.getScheduler().runTask(SQTechPumps.plugin, new BukkitRunnable() {
					@Override
					public void run() {
						if (SQTechPumps.machineLiquidTypeIdMap.get(machine) != null) {
							waterBlock.setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 5, false);
							waterBlock.getRelative(BlockFace.NORTH).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 5, false);
							waterBlock.getRelative(BlockFace.EAST).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 5, false);
							waterBlock.getRelative(BlockFace.WEST).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 5, false);
							waterBlock.getRelative(BlockFace.SOUTH).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 5, false);
							waterBlock.getRelative(BlockFace.NORTH_EAST).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 5, false);
							waterBlock.getRelative(BlockFace.NORTH_WEST).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 5, false);
							waterBlock.getRelative(BlockFace.SOUTH_EAST).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 5, false);
							waterBlock.getRelative(BlockFace.SOUTH_WEST).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 5, false);
						}
					}
				});

			} else if (current >= threePercent*4 && current < threePercent*5) {

				Bukkit.getScheduler().runTask(SQTechPumps.plugin, new BukkitRunnable() {
					@Override
					public void run() {
						if (SQTechPumps.machineLiquidTypeIdMap.get(machine) != null) {
							waterBlock.setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 4, false);
							waterBlock.getRelative(BlockFace.NORTH).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 4, false);
							waterBlock.getRelative(BlockFace.EAST).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 4, false);
							waterBlock.getRelative(BlockFace.WEST).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 4, false);
							waterBlock.getRelative(BlockFace.SOUTH).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 4, false);
							waterBlock.getRelative(BlockFace.NORTH_EAST).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 4, false);
							waterBlock.getRelative(BlockFace.NORTH_WEST).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 4, false);
							waterBlock.getRelative(BlockFace.SOUTH_EAST).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 4, false);
							waterBlock.getRelative(BlockFace.SOUTH_WEST).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 4, false);
						}
					}
				});

			} else if (current >= threePercent*5 && current < threePercent*6) {

				Bukkit.getScheduler().runTask(SQTechPumps.plugin, new BukkitRunnable() {
					@Override
					public void run() {
						if (SQTechPumps.machineLiquidTypeIdMap.get(machine) != null) {
							waterBlock.setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 3, false);
							waterBlock.getRelative(BlockFace.NORTH).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 3, false);
							waterBlock.getRelative(BlockFace.EAST).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 3, false);
							waterBlock.getRelative(BlockFace.WEST).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 3, false);
							waterBlock.getRelative(BlockFace.SOUTH).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 3, false);
							waterBlock.getRelative(BlockFace.NORTH_EAST).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 3, false);
							waterBlock.getRelative(BlockFace.NORTH_WEST).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 3, false);
							waterBlock.getRelative(BlockFace.SOUTH_EAST).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 3, false);
							waterBlock.getRelative(BlockFace.SOUTH_WEST).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 3, false);
						}
					}
				});

			} else if (current >= threePercent*6 && current < threePercent*7) {

				Bukkit.getScheduler().runTask(SQTechPumps.plugin, new BukkitRunnable() {
					@Override
					public void run() {
						if (SQTechPumps.machineLiquidTypeIdMap.get(machine) != null) {
							waterBlock.setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 2, false);
							waterBlock.getRelative(BlockFace.NORTH).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 2, false);
							waterBlock.getRelative(BlockFace.EAST).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 2, false);
							waterBlock.getRelative(BlockFace.WEST).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 2, false);
							waterBlock.getRelative(BlockFace.SOUTH).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 2, false);
							waterBlock.getRelative(BlockFace.NORTH_EAST).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 2, false);
							waterBlock.getRelative(BlockFace.NORTH_WEST).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 2, false);
							waterBlock.getRelative(BlockFace.SOUTH_EAST).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 2, false);
							waterBlock.getRelative(BlockFace.SOUTH_WEST).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 2, false);
						}
					}
				});

			} else if (current >= threePercent*7 && current < threePercent*8) {

				Bukkit.getScheduler().runTask(SQTechPumps.plugin, new BukkitRunnable() {
					@Override
					public void run() {
						if (SQTechPumps.machineLiquidTypeIdMap.get(machine) != null) {
							waterBlock.setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 1, false);
							waterBlock.getRelative(BlockFace.NORTH).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 1, false);
							waterBlock.getRelative(BlockFace.EAST).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 1, false);
							waterBlock.getRelative(BlockFace.WEST).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 1, false);
							waterBlock.getRelative(BlockFace.SOUTH).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 1, false);
							waterBlock.getRelative(BlockFace.NORTH_EAST).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 1, false);
							waterBlock.getRelative(BlockFace.NORTH_WEST).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 1, false);
							waterBlock.getRelative(BlockFace.SOUTH_EAST).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 1, false);
							waterBlock.getRelative(BlockFace.SOUTH_WEST).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 1, false);
						}
					}
				});

			} else if (current >= threePercent*8 && current < threePercent*9) {

				Bukkit.getScheduler().runTask(SQTechPumps.plugin, new BukkitRunnable() {
					@Override
					public void run() {
						if (SQTechPumps.machineLiquidTypeIdMap.get(machine) != null) {
							waterBlock.setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 0, false);
							waterBlock.getRelative(BlockFace.NORTH).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 0, false);
							waterBlock.getRelative(BlockFace.EAST).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 0, false);
							waterBlock.getRelative(BlockFace.WEST).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 0, false);
							waterBlock.getRelative(BlockFace.SOUTH).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 0, false);
							waterBlock.getRelative(BlockFace.NORTH_EAST).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 0, false);
							waterBlock.getRelative(BlockFace.NORTH_WEST).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 0, false);
							waterBlock.getRelative(BlockFace.SOUTH_EAST).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 0, false);
							waterBlock.getRelative(BlockFace.SOUTH_WEST).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 0, false);
						}
					}
				});

			} else if (current >= threePercent*9 && current < threePercent*10) {

				Bukkit.getScheduler().runTask(SQTechPumps.plugin, new BukkitRunnable() {
					@Override
					public void run() {
						if (SQTechPumps.machineLiquidTypeIdMap.get(machine) != null) {
							waterBlock.setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 0, false);
							waterBlock.getRelative(BlockFace.NORTH).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 0, false);
							waterBlock.getRelative(BlockFace.EAST).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 0, false);
							waterBlock.getRelative(BlockFace.WEST).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 0, false);
							waterBlock.getRelative(BlockFace.SOUTH).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 0, false);
							waterBlock.getRelative(BlockFace.NORTH_EAST).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 0, false);
							waterBlock.getRelative(BlockFace.NORTH_WEST).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 0, false);
							waterBlock.getRelative(BlockFace.SOUTH_EAST).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 0, false);
							waterBlock.getRelative(BlockFace.SOUTH_WEST).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 0, false);
							
							waterBlock2.setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 7, false);
							waterBlock2.getRelative(BlockFace.NORTH).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 7, false);
							waterBlock2.getRelative(BlockFace.EAST).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 7, false);
							waterBlock2.getRelative(BlockFace.WEST).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 7, false);
							waterBlock2.getRelative(BlockFace.SOUTH).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 7, false);
							waterBlock2.getRelative(BlockFace.NORTH_EAST).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 7, false);
							waterBlock2.getRelative(BlockFace.NORTH_WEST).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 7, false);
							waterBlock2.getRelative(BlockFace.SOUTH_EAST).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 7, false);
							waterBlock2.getRelative(BlockFace.SOUTH_WEST).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 7, false);
						}
					}
				});

			} else if (current >= threePercent*10 && current < threePercent*11) {

				Bukkit.getScheduler().runTask(SQTechPumps.plugin, new BukkitRunnable() {
					@Override
					public void run() {
						if (SQTechPumps.machineLiquidTypeIdMap.get(machine) != null) {
							waterBlock.setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 0, false);
							waterBlock.getRelative(BlockFace.NORTH).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 0, false);
							waterBlock.getRelative(BlockFace.EAST).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 0, false);
							waterBlock.getRelative(BlockFace.WEST).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 0, false);
							waterBlock.getRelative(BlockFace.SOUTH).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 0, false);
							waterBlock.getRelative(BlockFace.NORTH_EAST).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 0, false);
							waterBlock.getRelative(BlockFace.NORTH_WEST).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 0, false);
							waterBlock.getRelative(BlockFace.SOUTH_EAST).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 0, false);
							waterBlock.getRelative(BlockFace.SOUTH_WEST).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 0, false);
							
							waterBlock2.setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 6, false);
							waterBlock2.getRelative(BlockFace.NORTH).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 6, false);
							waterBlock2.getRelative(BlockFace.EAST).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 6, false);
							waterBlock2.getRelative(BlockFace.WEST).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 6, false);
							waterBlock2.getRelative(BlockFace.SOUTH).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 6, false);
							waterBlock2.getRelative(BlockFace.NORTH_EAST).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 6, false);
							waterBlock2.getRelative(BlockFace.NORTH_WEST).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 6, false);
							waterBlock2.getRelative(BlockFace.SOUTH_EAST).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 6, false);
							waterBlock2.getRelative(BlockFace.SOUTH_WEST).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 6, false);
						}
					}
				});

			} else if (current >= threePercent*11 && current < threePercent*12) {

				Bukkit.getScheduler().runTask(SQTechPumps.plugin, new BukkitRunnable() {
					@Override
					public void run() {
						if (SQTechPumps.machineLiquidTypeIdMap.get(machine) != null) {
							waterBlock.setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 0, false);
							waterBlock.getRelative(BlockFace.NORTH).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 0, false);
							waterBlock.getRelative(BlockFace.EAST).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 0, false);
							waterBlock.getRelative(BlockFace.WEST).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 0, false);
							waterBlock.getRelative(BlockFace.SOUTH).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 0, false);
							waterBlock.getRelative(BlockFace.NORTH_EAST).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 0, false);
							waterBlock.getRelative(BlockFace.NORTH_WEST).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 0, false);
							waterBlock.getRelative(BlockFace.SOUTH_EAST).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 0, false);
							waterBlock.getRelative(BlockFace.SOUTH_WEST).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 0, false);
							
							waterBlock2.setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 5, false);
							waterBlock2.getRelative(BlockFace.NORTH).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 5, false);
							waterBlock2.getRelative(BlockFace.EAST).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 5, false);
							waterBlock2.getRelative(BlockFace.WEST).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 5, false);
							waterBlock2.getRelative(BlockFace.SOUTH).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 5, false);
							waterBlock2.getRelative(BlockFace.NORTH_EAST).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 5, false);
							waterBlock2.getRelative(BlockFace.NORTH_WEST).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 5, false);
							waterBlock2.getRelative(BlockFace.SOUTH_EAST).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 5, false);
							waterBlock2.getRelative(BlockFace.SOUTH_WEST).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 5, false);
						}
					}
				});

			} else if (current >= threePercent*12 && current < threePercent*13) {

				Bukkit.getScheduler().runTask(SQTechPumps.plugin, new BukkitRunnable() {
					@Override
					public void run() {
						if (SQTechPumps.machineLiquidTypeIdMap.get(machine) != null) {
							waterBlock.setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 0, false);
							waterBlock.getRelative(BlockFace.NORTH).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 0, false);
							waterBlock.getRelative(BlockFace.EAST).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 0, false);
							waterBlock.getRelative(BlockFace.WEST).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 0, false);
							waterBlock.getRelative(BlockFace.SOUTH).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 0, false);
							waterBlock.getRelative(BlockFace.NORTH_EAST).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 0, false);
							waterBlock.getRelative(BlockFace.NORTH_WEST).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 0, false);
							waterBlock.getRelative(BlockFace.SOUTH_EAST).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 0, false);
							waterBlock.getRelative(BlockFace.SOUTH_WEST).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 0, false);
							
							waterBlock2.setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 4, false);
							waterBlock2.getRelative(BlockFace.NORTH).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 4, false);
							waterBlock2.getRelative(BlockFace.EAST).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 4, false);
							waterBlock2.getRelative(BlockFace.WEST).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 4, false);
							waterBlock2.getRelative(BlockFace.SOUTH).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 4, false);
							waterBlock2.getRelative(BlockFace.NORTH_EAST).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 4, false);
							waterBlock2.getRelative(BlockFace.NORTH_WEST).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 4, false);
							waterBlock2.getRelative(BlockFace.SOUTH_EAST).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 4, false);
							waterBlock2.getRelative(BlockFace.SOUTH_WEST).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 4, false);
						}
					}
				});

			} else if (current >= threePercent*13 && current < threePercent*14) {

				Bukkit.getScheduler().runTask(SQTechPumps.plugin, new BukkitRunnable() {
					@Override
					public void run() {
						if (SQTechPumps.machineLiquidTypeIdMap.get(machine) != null) {
							waterBlock.setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 0, false);
							waterBlock.getRelative(BlockFace.NORTH).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 0, false);
							waterBlock.getRelative(BlockFace.EAST).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 0, false);
							waterBlock.getRelative(BlockFace.WEST).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 0, false);
							waterBlock.getRelative(BlockFace.SOUTH).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 0, false);
							waterBlock.getRelative(BlockFace.NORTH_EAST).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 0, false);
							waterBlock.getRelative(BlockFace.NORTH_WEST).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 0, false);
							waterBlock.getRelative(BlockFace.SOUTH_EAST).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 0, false);
							waterBlock.getRelative(BlockFace.SOUTH_WEST).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 0, false);
							
							waterBlock2.setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 3, false);
							waterBlock2.getRelative(BlockFace.NORTH).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 3, false);
							waterBlock2.getRelative(BlockFace.EAST).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 3, false);
							waterBlock2.getRelative(BlockFace.WEST).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 3, false);
							waterBlock2.getRelative(BlockFace.SOUTH).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 3, false);
							waterBlock2.getRelative(BlockFace.NORTH_EAST).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 3, false);
							waterBlock2.getRelative(BlockFace.NORTH_WEST).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 3, false);
							waterBlock2.getRelative(BlockFace.SOUTH_EAST).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 3, false);
							waterBlock2.getRelative(BlockFace.SOUTH_WEST).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 3, false);
						}
					}
				});

			} else if (current >= threePercent*14 && current < threePercent*15) {

				Bukkit.getScheduler().runTask(SQTechPumps.plugin, new BukkitRunnable() {
					@Override
					public void run() {
						if (SQTechPumps.machineLiquidTypeIdMap.get(machine) != null) {
							waterBlock.setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 0, false);
							waterBlock.getRelative(BlockFace.NORTH).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 0, false);
							waterBlock.getRelative(BlockFace.EAST).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 0, false);
							waterBlock.getRelative(BlockFace.WEST).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 0, false);
							waterBlock.getRelative(BlockFace.SOUTH).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 0, false);
							waterBlock.getRelative(BlockFace.NORTH_EAST).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 0, false);
							waterBlock.getRelative(BlockFace.NORTH_WEST).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 0, false);
							waterBlock.getRelative(BlockFace.SOUTH_EAST).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 0, false);
							waterBlock.getRelative(BlockFace.SOUTH_WEST).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 0, false);
							
							waterBlock2.setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 2, false);
							waterBlock2.getRelative(BlockFace.NORTH).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 2, false);
							waterBlock2.getRelative(BlockFace.EAST).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 2, false);
							waterBlock2.getRelative(BlockFace.WEST).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 2, false);
							waterBlock2.getRelative(BlockFace.SOUTH).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 2, false);
							waterBlock2.getRelative(BlockFace.NORTH_EAST).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 2, false);
							waterBlock2.getRelative(BlockFace.NORTH_WEST).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 2, false);
							waterBlock2.getRelative(BlockFace.SOUTH_EAST).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 2, false);
							waterBlock2.getRelative(BlockFace.SOUTH_WEST).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 2, false);
						}
					}
				});

			} else if (current >= threePercent*15 && current < threePercent*16) {

				Bukkit.getScheduler().runTask(SQTechPumps.plugin, new BukkitRunnable() {
					@Override
					public void run() {
						if (SQTechPumps.machineLiquidTypeIdMap.get(machine) != null) {
							waterBlock.setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 0, false);
							waterBlock.getRelative(BlockFace.NORTH).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 0, false);
							waterBlock.getRelative(BlockFace.EAST).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 0, false);
							waterBlock.getRelative(BlockFace.WEST).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 0, false);
							waterBlock.getRelative(BlockFace.SOUTH).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 0, false);
							waterBlock.getRelative(BlockFace.NORTH_EAST).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 0, false);
							waterBlock.getRelative(BlockFace.NORTH_WEST).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 0, false);
							waterBlock.getRelative(BlockFace.SOUTH_EAST).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 0, false);
							waterBlock.getRelative(BlockFace.SOUTH_WEST).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 0, false);
							
							waterBlock2.setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 1, false);
							waterBlock2.getRelative(BlockFace.NORTH).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 1, false);
							waterBlock2.getRelative(BlockFace.EAST).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 1, false);
							waterBlock2.getRelative(BlockFace.WEST).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 1, false);
							waterBlock2.getRelative(BlockFace.SOUTH).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 1, false);
							waterBlock2.getRelative(BlockFace.NORTH_EAST).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 1, false);
							waterBlock2.getRelative(BlockFace.NORTH_WEST).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 1, false);
							waterBlock2.getRelative(BlockFace.SOUTH_EAST).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 1, false);
							waterBlock2.getRelative(BlockFace.SOUTH_WEST).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 1, false);
						}
					}
				});

			} else if (current >= threePercent*16 && current < threePercent*17) {

				Bukkit.getScheduler().runTask(SQTechPumps.plugin, new BukkitRunnable() {
					@Override
					public void run() {
						if (SQTechPumps.machineLiquidTypeIdMap.get(machine) != null) {
							waterBlock.setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 0, false);
							waterBlock.getRelative(BlockFace.NORTH).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 0, false);
							waterBlock.getRelative(BlockFace.EAST).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 0, false);
							waterBlock.getRelative(BlockFace.WEST).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 0, false);
							waterBlock.getRelative(BlockFace.SOUTH).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 0, false);
							waterBlock.getRelative(BlockFace.NORTH_EAST).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 0, false);
							waterBlock.getRelative(BlockFace.NORTH_WEST).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 0, false);
							waterBlock.getRelative(BlockFace.SOUTH_EAST).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 0, false);
							waterBlock.getRelative(BlockFace.SOUTH_WEST).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 0, false);
							
							waterBlock2.setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 0, false);
							waterBlock2.getRelative(BlockFace.NORTH).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 0, false);
							waterBlock2.getRelative(BlockFace.EAST).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 0, false);
							waterBlock2.getRelative(BlockFace.WEST).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 0, false);
							waterBlock2.getRelative(BlockFace.SOUTH).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 0, false);
							waterBlock2.getRelative(BlockFace.NORTH_EAST).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 0, false);
							waterBlock2.getRelative(BlockFace.NORTH_WEST).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 0, false);
							waterBlock2.getRelative(BlockFace.SOUTH_EAST).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 0, false);
							waterBlock2.getRelative(BlockFace.SOUTH_WEST).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 0, false);
						}
					}
				});

			} else if (current >= threePercent*17 && current < threePercent*18) {

				Bukkit.getScheduler().runTask(SQTechPumps.plugin, new BukkitRunnable() {
					@Override
					public void run() {
						if (SQTechPumps.machineLiquidTypeIdMap.get(machine) != null) {
							waterBlock.setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 0, false);
							waterBlock.getRelative(BlockFace.NORTH).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 0, false);
							waterBlock.getRelative(BlockFace.EAST).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 0, false);
							waterBlock.getRelative(BlockFace.WEST).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 0, false);
							waterBlock.getRelative(BlockFace.SOUTH).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 0, false);
							waterBlock.getRelative(BlockFace.NORTH_EAST).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 0, false);
							waterBlock.getRelative(BlockFace.NORTH_WEST).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 0, false);
							waterBlock.getRelative(BlockFace.SOUTH_EAST).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 0, false);
							waterBlock.getRelative(BlockFace.SOUTH_WEST).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 0, false);
							
							waterBlock2.setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 0, false);
							waterBlock2.getRelative(BlockFace.NORTH).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 0, false);
							waterBlock2.getRelative(BlockFace.EAST).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 0, false);
							waterBlock2.getRelative(BlockFace.WEST).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 0, false);
							waterBlock2.getRelative(BlockFace.SOUTH).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 0, false);
							waterBlock2.getRelative(BlockFace.NORTH_EAST).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 0, false);
							waterBlock2.getRelative(BlockFace.NORTH_WEST).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 0, false);
							waterBlock2.getRelative(BlockFace.SOUTH_EAST).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 0, false);
							waterBlock2.getRelative(BlockFace.SOUTH_WEST).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 0, false);
							
							waterBlock3.setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 7, false);
							waterBlock3.getRelative(BlockFace.NORTH).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 7, false);
							waterBlock3.getRelative(BlockFace.EAST).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 7, false);
							waterBlock3.getRelative(BlockFace.WEST).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 7, false);
							waterBlock3.getRelative(BlockFace.SOUTH).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 7, false);
							waterBlock3.getRelative(BlockFace.NORTH_EAST).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 7, false);
							waterBlock3.getRelative(BlockFace.NORTH_WEST).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 7, false);
							waterBlock3.getRelative(BlockFace.SOUTH_EAST).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 7, false);
							waterBlock3.getRelative(BlockFace.SOUTH_WEST).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 7, false);
						}
					}
				});

			} else if (current >= threePercent*18 && current < threePercent*19) {

				Bukkit.getScheduler().runTask(SQTechPumps.plugin, new BukkitRunnable() {
					@Override
					public void run() {
						if (SQTechPumps.machineLiquidTypeIdMap.get(machine) != null) {
							waterBlock.setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 0, false);
							waterBlock.getRelative(BlockFace.NORTH).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 0, false);
							waterBlock.getRelative(BlockFace.EAST).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 0, false);
							waterBlock.getRelative(BlockFace.WEST).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 0, false);
							waterBlock.getRelative(BlockFace.SOUTH).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 0, false);
							waterBlock.getRelative(BlockFace.NORTH_EAST).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 0, false);
							waterBlock.getRelative(BlockFace.NORTH_WEST).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 0, false);
							waterBlock.getRelative(BlockFace.SOUTH_EAST).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 0, false);
							waterBlock.getRelative(BlockFace.SOUTH_WEST).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 0, false);
							
							waterBlock2.setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 0, false);
							waterBlock2.getRelative(BlockFace.NORTH).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 0, false);
							waterBlock2.getRelative(BlockFace.EAST).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 0, false);
							waterBlock2.getRelative(BlockFace.WEST).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 0, false);
							waterBlock2.getRelative(BlockFace.SOUTH).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 0, false);
							waterBlock2.getRelative(BlockFace.NORTH_EAST).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 0, false);
							waterBlock2.getRelative(BlockFace.NORTH_WEST).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 0, false);
							waterBlock2.getRelative(BlockFace.SOUTH_EAST).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 0, false);
							waterBlock2.getRelative(BlockFace.SOUTH_WEST).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 0, false);
							
							waterBlock3.setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 6, false);
							waterBlock3.getRelative(BlockFace.NORTH).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 6, false);
							waterBlock3.getRelative(BlockFace.EAST).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 6, false);
							waterBlock3.getRelative(BlockFace.WEST).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 6, false);
							waterBlock3.getRelative(BlockFace.SOUTH).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 6, false);
							waterBlock3.getRelative(BlockFace.NORTH_EAST).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 6, false);
							waterBlock3.getRelative(BlockFace.NORTH_WEST).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 6, false);
							waterBlock3.getRelative(BlockFace.SOUTH_EAST).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 6, false);
							waterBlock3.getRelative(BlockFace.SOUTH_WEST).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 6, false);
						}
					}
				});

			} else if (current >= threePercent*19 && current < threePercent*20) {

				Bukkit.getScheduler().runTask(SQTechPumps.plugin, new BukkitRunnable() {
					@Override
					public void run() {
						if (SQTechPumps.machineLiquidTypeIdMap.get(machine) != null) {
							waterBlock.setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 0, false);
							waterBlock.getRelative(BlockFace.NORTH).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 0, false);
							waterBlock.getRelative(BlockFace.EAST).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 0, false);
							waterBlock.getRelative(BlockFace.WEST).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 0, false);
							waterBlock.getRelative(BlockFace.SOUTH).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 0, false);
							waterBlock.getRelative(BlockFace.NORTH_EAST).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 0, false);
							waterBlock.getRelative(BlockFace.NORTH_WEST).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 0, false);
							waterBlock.getRelative(BlockFace.SOUTH_EAST).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 0, false);
							waterBlock.getRelative(BlockFace.SOUTH_WEST).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 0, false);
							
							waterBlock2.setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 0, false);
							waterBlock2.getRelative(BlockFace.NORTH).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 0, false);
							waterBlock2.getRelative(BlockFace.EAST).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 0, false);
							waterBlock2.getRelative(BlockFace.WEST).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 0, false);
							waterBlock2.getRelative(BlockFace.SOUTH).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 0, false);
							waterBlock2.getRelative(BlockFace.NORTH_EAST).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 0, false);
							waterBlock2.getRelative(BlockFace.NORTH_WEST).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 0, false);
							waterBlock2.getRelative(BlockFace.SOUTH_EAST).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 0, false);
							waterBlock2.getRelative(BlockFace.SOUTH_WEST).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 0, false);
							
							waterBlock3.setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 5, false);
							waterBlock3.getRelative(BlockFace.NORTH).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 5, false);
							waterBlock3.getRelative(BlockFace.EAST).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 5, false);
							waterBlock3.getRelative(BlockFace.WEST).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 5, false);
							waterBlock3.getRelative(BlockFace.SOUTH).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 5, false);
							waterBlock3.getRelative(BlockFace.NORTH_EAST).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 5, false);
							waterBlock3.getRelative(BlockFace.NORTH_WEST).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 5, false);
							waterBlock3.getRelative(BlockFace.SOUTH_EAST).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 5, false);
							waterBlock3.getRelative(BlockFace.SOUTH_WEST).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 5, false);
						}
					}
				});

			} else if (current >= threePercent*20 && current < threePercent*21) {

				Bukkit.getScheduler().runTask(SQTechPumps.plugin, new BukkitRunnable() {
					@Override
					public void run() {
						if (SQTechPumps.machineLiquidTypeIdMap.get(machine) != null) {
							waterBlock.setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 0, false);
							waterBlock.getRelative(BlockFace.NORTH).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 0, false);
							waterBlock.getRelative(BlockFace.EAST).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 0, false);
							waterBlock.getRelative(BlockFace.WEST).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 0, false);
							waterBlock.getRelative(BlockFace.SOUTH).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 0, false);
							waterBlock.getRelative(BlockFace.NORTH_EAST).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 0, false);
							waterBlock.getRelative(BlockFace.NORTH_WEST).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 0, false);
							waterBlock.getRelative(BlockFace.SOUTH_EAST).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 0, false);
							waterBlock.getRelative(BlockFace.SOUTH_WEST).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 0, false);
							
							waterBlock2.setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 0, false);
							waterBlock2.getRelative(BlockFace.NORTH).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 0, false);
							waterBlock2.getRelative(BlockFace.EAST).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 0, false);
							waterBlock2.getRelative(BlockFace.WEST).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 0, false);
							waterBlock2.getRelative(BlockFace.SOUTH).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 0, false);
							waterBlock2.getRelative(BlockFace.NORTH_EAST).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 0, false);
							waterBlock2.getRelative(BlockFace.NORTH_WEST).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 0, false);
							waterBlock2.getRelative(BlockFace.SOUTH_EAST).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 0, false);
							waterBlock2.getRelative(BlockFace.SOUTH_WEST).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 0, false);
							
							waterBlock3.setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 4, false);
							waterBlock3.getRelative(BlockFace.NORTH).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 4, false);
							waterBlock3.getRelative(BlockFace.EAST).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 4, false);
							waterBlock3.getRelative(BlockFace.WEST).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 4, false);
							waterBlock3.getRelative(BlockFace.SOUTH).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 4, false);
							waterBlock3.getRelative(BlockFace.NORTH_EAST).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 4, false);
							waterBlock3.getRelative(BlockFace.NORTH_WEST).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 4, false);
							waterBlock3.getRelative(BlockFace.SOUTH_EAST).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 4, false);
							waterBlock3.getRelative(BlockFace.SOUTH_WEST).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 4, false);
						}
					}
				});

			} else if (current >= threePercent*21 && current < threePercent*22) {

				Bukkit.getScheduler().runTask(SQTechPumps.plugin, new BukkitRunnable() {
					@Override
					public void run() {
						if (SQTechPumps.machineLiquidTypeIdMap.get(machine) != null) {
							waterBlock.setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 0, false);
							waterBlock.getRelative(BlockFace.NORTH).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 0, false);
							waterBlock.getRelative(BlockFace.EAST).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 0, false);
							waterBlock.getRelative(BlockFace.WEST).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 0, false);
							waterBlock.getRelative(BlockFace.SOUTH).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 0, false);
							waterBlock.getRelative(BlockFace.NORTH_EAST).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 0, false);
							waterBlock.getRelative(BlockFace.NORTH_WEST).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 0, false);
							waterBlock.getRelative(BlockFace.SOUTH_EAST).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 0, false);
							waterBlock.getRelative(BlockFace.SOUTH_WEST).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 0, false);
							
							waterBlock2.setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 0, false);
							waterBlock2.getRelative(BlockFace.NORTH).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 0, false);
							waterBlock2.getRelative(BlockFace.EAST).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 0, false);
							waterBlock2.getRelative(BlockFace.WEST).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 0, false);
							waterBlock2.getRelative(BlockFace.SOUTH).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 0, false);
							waterBlock2.getRelative(BlockFace.NORTH_EAST).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 0, false);
							waterBlock2.getRelative(BlockFace.NORTH_WEST).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 0, false);
							waterBlock2.getRelative(BlockFace.SOUTH_EAST).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 0, false);
							waterBlock2.getRelative(BlockFace.SOUTH_WEST).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 0, false);
							
							waterBlock3.setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 3, false);
							waterBlock3.getRelative(BlockFace.NORTH).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 3, false);
							waterBlock3.getRelative(BlockFace.EAST).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 3, false);
							waterBlock3.getRelative(BlockFace.WEST).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 3, false);
							waterBlock3.getRelative(BlockFace.SOUTH).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 3, false);
							waterBlock3.getRelative(BlockFace.NORTH_EAST).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 3, false);
							waterBlock3.getRelative(BlockFace.NORTH_WEST).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 3, false);
							waterBlock3.getRelative(BlockFace.SOUTH_EAST).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 3, false);
							waterBlock3.getRelative(BlockFace.SOUTH_WEST).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 3, false);
						}
					}
				});

			} else if (current >= threePercent*22 && current < threePercent*23) {

				Bukkit.getScheduler().runTask(SQTechPumps.plugin, new BukkitRunnable() {
					@Override
					public void run() {
						if (SQTechPumps.machineLiquidTypeIdMap.get(machine) != null) {
							waterBlock.setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 0, false);
							waterBlock.getRelative(BlockFace.NORTH).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 0, false);
							waterBlock.getRelative(BlockFace.EAST).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 0, false);
							waterBlock.getRelative(BlockFace.WEST).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 0, false);
							waterBlock.getRelative(BlockFace.SOUTH).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 0, false);
							waterBlock.getRelative(BlockFace.NORTH_EAST).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 0, false);
							waterBlock.getRelative(BlockFace.NORTH_WEST).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 0, false);
							waterBlock.getRelative(BlockFace.SOUTH_EAST).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 0, false);
							waterBlock.getRelative(BlockFace.SOUTH_WEST).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 0, false);
							
							waterBlock2.setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 0, false);
							waterBlock2.getRelative(BlockFace.NORTH).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 0, false);
							waterBlock2.getRelative(BlockFace.EAST).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 0, false);
							waterBlock2.getRelative(BlockFace.WEST).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 0, false);
							waterBlock2.getRelative(BlockFace.SOUTH).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 0, false);
							waterBlock2.getRelative(BlockFace.NORTH_EAST).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 0, false);
							waterBlock2.getRelative(BlockFace.NORTH_WEST).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 0, false);
							waterBlock2.getRelative(BlockFace.SOUTH_EAST).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 0, false);
							waterBlock2.getRelative(BlockFace.SOUTH_WEST).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 0, false);
							
							waterBlock3.setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 2, false);
							waterBlock3.getRelative(BlockFace.NORTH).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 2, false);
							waterBlock3.getRelative(BlockFace.EAST).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 2, false);
							waterBlock3.getRelative(BlockFace.WEST).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 2, false);
							waterBlock3.getRelative(BlockFace.SOUTH).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 2, false);
							waterBlock3.getRelative(BlockFace.NORTH_EAST).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 2, false);
							waterBlock3.getRelative(BlockFace.NORTH_WEST).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 2, false);
							waterBlock3.getRelative(BlockFace.SOUTH_EAST).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 2, false);
							waterBlock3.getRelative(BlockFace.SOUTH_WEST).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 2, false);
						}
					}
				});

			} else if (current >= threePercent*23 && current < threePercent*24) {

				Bukkit.getScheduler().runTask(SQTechPumps.plugin, new BukkitRunnable() {
					@Override
					public void run() {
						if (SQTechPumps.machineLiquidTypeIdMap.get(machine) != null) {
							waterBlock.setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 0, false);
							waterBlock.getRelative(BlockFace.NORTH).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 0, false);
							waterBlock.getRelative(BlockFace.EAST).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 0, false);
							waterBlock.getRelative(BlockFace.WEST).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 0, false);
							waterBlock.getRelative(BlockFace.SOUTH).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 0, false);
							waterBlock.getRelative(BlockFace.NORTH_EAST).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 0, false);
							waterBlock.getRelative(BlockFace.NORTH_WEST).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 0, false);
							waterBlock.getRelative(BlockFace.SOUTH_EAST).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 0, false);
							waterBlock.getRelative(BlockFace.SOUTH_WEST).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 0, false);
							
							waterBlock2.setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 0, false);
							waterBlock2.getRelative(BlockFace.NORTH).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 0, false);
							waterBlock2.getRelative(BlockFace.EAST).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 0, false);
							waterBlock2.getRelative(BlockFace.WEST).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 0, false);
							waterBlock2.getRelative(BlockFace.SOUTH).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 0, false);
							waterBlock2.getRelative(BlockFace.NORTH_EAST).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 0, false);
							waterBlock2.getRelative(BlockFace.NORTH_WEST).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 0, false);
							waterBlock2.getRelative(BlockFace.SOUTH_EAST).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 0, false);
							waterBlock2.getRelative(BlockFace.SOUTH_WEST).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 0, false);
							
							waterBlock3.setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 1, false);
							waterBlock3.getRelative(BlockFace.NORTH).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 1, false);
							waterBlock3.getRelative(BlockFace.EAST).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 1, false);
							waterBlock3.getRelative(BlockFace.WEST).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 1, false);
							waterBlock3.getRelative(BlockFace.SOUTH).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 1, false);
							waterBlock3.getRelative(BlockFace.NORTH_EAST).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 1, false);
							waterBlock3.getRelative(BlockFace.NORTH_WEST).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 1, false);
							waterBlock3.getRelative(BlockFace.SOUTH_EAST).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 1, false);
							waterBlock3.getRelative(BlockFace.SOUTH_WEST).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 1, false);
						}
					}
				});

			} else if (current >= threePercent*24 && current < threePercent*25) {

				Bukkit.getScheduler().runTask(SQTechPumps.plugin, new BukkitRunnable() {
					@Override
					public void run() {
						if (SQTechPumps.machineLiquidTypeIdMap.get(machine) != null) {
							waterBlock.setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 0, false);
							waterBlock.getRelative(BlockFace.NORTH).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 0, false);
							waterBlock.getRelative(BlockFace.EAST).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 0, false);
							waterBlock.getRelative(BlockFace.WEST).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 0, false);
							waterBlock.getRelative(BlockFace.SOUTH).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 0, false);
							waterBlock.getRelative(BlockFace.NORTH_EAST).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 0, false);
							waterBlock.getRelative(BlockFace.NORTH_WEST).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 0, false);
							waterBlock.getRelative(BlockFace.SOUTH_EAST).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 0, false);
							waterBlock.getRelative(BlockFace.SOUTH_WEST).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 0, false);
							
							waterBlock2.setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 0, false);
							waterBlock2.getRelative(BlockFace.NORTH).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 0, false);
							waterBlock2.getRelative(BlockFace.EAST).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 0, false);
							waterBlock2.getRelative(BlockFace.WEST).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 0, false);
							waterBlock2.getRelative(BlockFace.SOUTH).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 0, false);
							waterBlock2.getRelative(BlockFace.NORTH_EAST).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 0, false);
							waterBlock2.getRelative(BlockFace.NORTH_WEST).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 0, false);
							waterBlock2.getRelative(BlockFace.SOUTH_EAST).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 0, false);
							waterBlock2.getRelative(BlockFace.SOUTH_WEST).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 0, false);
							
							waterBlock3.setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 0, false);
							waterBlock3.getRelative(BlockFace.NORTH).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 0, false);
							waterBlock3.getRelative(BlockFace.EAST).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 0, false);
							waterBlock3.getRelative(BlockFace.WEST).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 0, false);
							waterBlock3.getRelative(BlockFace.SOUTH).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 0, false);
							waterBlock3.getRelative(BlockFace.NORTH_EAST).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 0, false);
							waterBlock3.getRelative(BlockFace.NORTH_WEST).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 0, false);
							waterBlock3.getRelative(BlockFace.SOUTH_EAST).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 0, false);
							waterBlock3.getRelative(BlockFace.SOUTH_WEST).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 0, false);
						}
					}
				});

			} else if (current >= threePercent*25 && current < threePercent*26) {

				Bukkit.getScheduler().runTask(SQTechPumps.plugin, new BukkitRunnable() {
					@Override
					public void run() {
						if (SQTechPumps.machineLiquidTypeIdMap.get(machine) != null) {
							waterBlock.setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 0, false);
							waterBlock.getRelative(BlockFace.NORTH).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 0, false);
							waterBlock.getRelative(BlockFace.EAST).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 0, false);
							waterBlock.getRelative(BlockFace.WEST).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 0, false);
							waterBlock.getRelative(BlockFace.SOUTH).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 0, false);
							waterBlock.getRelative(BlockFace.NORTH_EAST).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 0, false);
							waterBlock.getRelative(BlockFace.NORTH_WEST).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 0, false);
							waterBlock.getRelative(BlockFace.SOUTH_EAST).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 0, false);
							waterBlock.getRelative(BlockFace.SOUTH_WEST).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 0, false);
							
							waterBlock2.setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 0, false);
							waterBlock2.getRelative(BlockFace.NORTH).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 0, false);
							waterBlock2.getRelative(BlockFace.EAST).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 0, false);
							waterBlock2.getRelative(BlockFace.WEST).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 0, false);
							waterBlock2.getRelative(BlockFace.SOUTH).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 0, false);
							waterBlock2.getRelative(BlockFace.NORTH_EAST).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 0, false);
							waterBlock2.getRelative(BlockFace.NORTH_WEST).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 0, false);
							waterBlock2.getRelative(BlockFace.SOUTH_EAST).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 0, false);
							waterBlock2.getRelative(BlockFace.SOUTH_WEST).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 0, false);
							
							waterBlock3.setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 0, false);
							waterBlock3.getRelative(BlockFace.NORTH).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 0, false);
							waterBlock3.getRelative(BlockFace.EAST).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 0, false);
							waterBlock3.getRelative(BlockFace.WEST).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 0, false);
							waterBlock3.getRelative(BlockFace.SOUTH).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 0, false);
							waterBlock3.getRelative(BlockFace.NORTH_EAST).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 0, false);
							waterBlock3.getRelative(BlockFace.NORTH_WEST).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 0, false);
							waterBlock3.getRelative(BlockFace.SOUTH_EAST).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 0, false);
							waterBlock3.getRelative(BlockFace.SOUTH_WEST).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 0, false);
							
							waterBlock4.setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 7, false);
							waterBlock4.getRelative(BlockFace.NORTH).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 7, false);
							waterBlock4.getRelative(BlockFace.EAST).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 7, false);
							waterBlock4.getRelative(BlockFace.WEST).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 7, false);
							waterBlock4.getRelative(BlockFace.SOUTH).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 7, false);
							waterBlock4.getRelative(BlockFace.NORTH_EAST).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 7, false);
							waterBlock4.getRelative(BlockFace.NORTH_WEST).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 7, false);
							waterBlock4.getRelative(BlockFace.SOUTH_EAST).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 7, false);
							waterBlock4.getRelative(BlockFace.SOUTH_WEST).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 7, false);
						}
					}
				});

			} else if (current >= threePercent*26 && current < threePercent*27) {

				Bukkit.getScheduler().runTask(SQTechPumps.plugin, new BukkitRunnable() {
					@Override
					public void run() {
						if (SQTechPumps.machineLiquidTypeIdMap.get(machine) != null) {
							waterBlock.setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 0, false);
							waterBlock.getRelative(BlockFace.NORTH).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 0, false);
							waterBlock.getRelative(BlockFace.EAST).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 0, false);
							waterBlock.getRelative(BlockFace.WEST).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 0, false);
							waterBlock.getRelative(BlockFace.SOUTH).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 0, false);
							waterBlock.getRelative(BlockFace.NORTH_EAST).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 0, false);
							waterBlock.getRelative(BlockFace.NORTH_WEST).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 0, false);
							waterBlock.getRelative(BlockFace.SOUTH_EAST).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 0, false);
							waterBlock.getRelative(BlockFace.SOUTH_WEST).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 0, false);
							
							waterBlock2.setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 0, false);
							waterBlock2.getRelative(BlockFace.NORTH).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 0, false);
							waterBlock2.getRelative(BlockFace.EAST).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 0, false);
							waterBlock2.getRelative(BlockFace.WEST).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 0, false);
							waterBlock2.getRelative(BlockFace.SOUTH).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 0, false);
							waterBlock2.getRelative(BlockFace.NORTH_EAST).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 0, false);
							waterBlock2.getRelative(BlockFace.NORTH_WEST).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 0, false);
							waterBlock2.getRelative(BlockFace.SOUTH_EAST).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 0, false);
							waterBlock2.getRelative(BlockFace.SOUTH_WEST).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 0, false);
							
							waterBlock3.setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 0, false);
							waterBlock3.getRelative(BlockFace.NORTH).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 0, false);
							waterBlock3.getRelative(BlockFace.EAST).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 0, false);
							waterBlock3.getRelative(BlockFace.WEST).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 0, false);
							waterBlock3.getRelative(BlockFace.SOUTH).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 0, false);
							waterBlock3.getRelative(BlockFace.NORTH_EAST).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 0, false);
							waterBlock3.getRelative(BlockFace.NORTH_WEST).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 0, false);
							waterBlock3.getRelative(BlockFace.SOUTH_EAST).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 0, false);
							waterBlock3.getRelative(BlockFace.SOUTH_WEST).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 0, false);
							
							waterBlock4.setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 6, false);
							waterBlock4.getRelative(BlockFace.NORTH).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 6, false);
							waterBlock4.getRelative(BlockFace.EAST).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 6, false);
							waterBlock4.getRelative(BlockFace.WEST).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 6, false);
							waterBlock4.getRelative(BlockFace.SOUTH).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 6, false);
							waterBlock4.getRelative(BlockFace.NORTH_EAST).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 6, false);
							waterBlock4.getRelative(BlockFace.NORTH_WEST).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 6, false);
							waterBlock4.getRelative(BlockFace.SOUTH_EAST).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 6, false);
							waterBlock4.getRelative(BlockFace.SOUTH_WEST).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 6, false);
						}
					}
				});

			} else if (current >= threePercent*27 && current < threePercent*28) {

				Bukkit.getScheduler().runTask(SQTechPumps.plugin, new BukkitRunnable() {
					@Override
					public void run() {
						if (SQTechPumps.machineLiquidTypeIdMap.get(machine) != null) {
							waterBlock.setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 0, false);
							waterBlock.getRelative(BlockFace.NORTH).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 0, false);
							waterBlock.getRelative(BlockFace.EAST).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 0, false);
							waterBlock.getRelative(BlockFace.WEST).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 0, false);
							waterBlock.getRelative(BlockFace.SOUTH).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 0, false);
							waterBlock.getRelative(BlockFace.NORTH_EAST).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 0, false);
							waterBlock.getRelative(BlockFace.NORTH_WEST).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 0, false);
							waterBlock.getRelative(BlockFace.SOUTH_EAST).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 0, false);
							waterBlock.getRelative(BlockFace.SOUTH_WEST).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 0, false);
							
							waterBlock2.setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 0, false);
							waterBlock2.getRelative(BlockFace.NORTH).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 0, false);
							waterBlock2.getRelative(BlockFace.EAST).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 0, false);
							waterBlock2.getRelative(BlockFace.WEST).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 0, false);
							waterBlock2.getRelative(BlockFace.SOUTH).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 0, false);
							waterBlock2.getRelative(BlockFace.NORTH_EAST).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 0, false);
							waterBlock2.getRelative(BlockFace.NORTH_WEST).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 0, false);
							waterBlock2.getRelative(BlockFace.SOUTH_EAST).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 0, false);
							waterBlock2.getRelative(BlockFace.SOUTH_WEST).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 0, false);
							
							waterBlock3.setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 0, false);
							waterBlock3.getRelative(BlockFace.NORTH).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 0, false);
							waterBlock3.getRelative(BlockFace.EAST).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 0, false);
							waterBlock3.getRelative(BlockFace.WEST).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 0, false);
							waterBlock3.getRelative(BlockFace.SOUTH).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 0, false);
							waterBlock3.getRelative(BlockFace.NORTH_EAST).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 0, false);
							waterBlock3.getRelative(BlockFace.NORTH_WEST).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 0, false);
							waterBlock3.getRelative(BlockFace.SOUTH_EAST).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 0, false);
							waterBlock3.getRelative(BlockFace.SOUTH_WEST).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 0, false);
							
							waterBlock4.setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 5, false);
							waterBlock4.getRelative(BlockFace.NORTH).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 5, false);
							waterBlock4.getRelative(BlockFace.EAST).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 5, false);
							waterBlock4.getRelative(BlockFace.WEST).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 5, false);
							waterBlock4.getRelative(BlockFace.SOUTH).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 5, false);
							waterBlock4.getRelative(BlockFace.NORTH_EAST).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 5, false);
							waterBlock4.getRelative(BlockFace.NORTH_WEST).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 5, false);
							waterBlock4.getRelative(BlockFace.SOUTH_EAST).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 5, false);
							waterBlock4.getRelative(BlockFace.SOUTH_WEST).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 5, false);
						}
					}
				});

			} else if (current >= threePercent*28 && current < threePercent*29) {

				Bukkit.getScheduler().runTask(SQTechPumps.plugin, new BukkitRunnable() {
					@Override
					public void run() {
						if (SQTechPumps.machineLiquidTypeIdMap.get(machine) != null) {
							waterBlock.setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 0, false);
							waterBlock.getRelative(BlockFace.NORTH).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 0, false);
							waterBlock.getRelative(BlockFace.EAST).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 0, false);
							waterBlock.getRelative(BlockFace.WEST).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 0, false);
							waterBlock.getRelative(BlockFace.SOUTH).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 0, false);
							waterBlock.getRelative(BlockFace.NORTH_EAST).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 0, false);
							waterBlock.getRelative(BlockFace.NORTH_WEST).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 0, false);
							waterBlock.getRelative(BlockFace.SOUTH_EAST).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 0, false);
							waterBlock.getRelative(BlockFace.SOUTH_WEST).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 0, false);
							
							waterBlock2.setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 0, false);
							waterBlock2.getRelative(BlockFace.NORTH).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 0, false);
							waterBlock2.getRelative(BlockFace.EAST).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 0, false);
							waterBlock2.getRelative(BlockFace.WEST).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 0, false);
							waterBlock2.getRelative(BlockFace.SOUTH).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 0, false);
							waterBlock2.getRelative(BlockFace.NORTH_EAST).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 0, false);
							waterBlock2.getRelative(BlockFace.NORTH_WEST).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 0, false);
							waterBlock2.getRelative(BlockFace.SOUTH_EAST).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 0, false);
							waterBlock2.getRelative(BlockFace.SOUTH_WEST).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 0, false);
							
							waterBlock3.setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 0, false);
							waterBlock3.getRelative(BlockFace.NORTH).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 0, false);
							waterBlock3.getRelative(BlockFace.EAST).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 0, false);
							waterBlock3.getRelative(BlockFace.WEST).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 0, false);
							waterBlock3.getRelative(BlockFace.SOUTH).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 0, false);
							waterBlock3.getRelative(BlockFace.NORTH_EAST).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 0, false);
							waterBlock3.getRelative(BlockFace.NORTH_WEST).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 0, false);
							waterBlock3.getRelative(BlockFace.SOUTH_EAST).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 0, false);
							waterBlock3.getRelative(BlockFace.SOUTH_WEST).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 0, false);
							
							waterBlock4.setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 4, false);
							waterBlock4.getRelative(BlockFace.NORTH).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 4, false);
							waterBlock4.getRelative(BlockFace.EAST).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 4, false);
							waterBlock4.getRelative(BlockFace.WEST).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 4, false);
							waterBlock4.getRelative(BlockFace.SOUTH).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 4, false);
							waterBlock4.getRelative(BlockFace.NORTH_EAST).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 4, false);
							waterBlock4.getRelative(BlockFace.NORTH_WEST).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 4, false);
							waterBlock4.getRelative(BlockFace.SOUTH_EAST).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 4, false);
							waterBlock4.getRelative(BlockFace.SOUTH_WEST).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 4, false);
						}
					}
				});

			} else if (current >= threePercent*29 && current < threePercent*30) {

				Bukkit.getScheduler().runTask(SQTechPumps.plugin, new BukkitRunnable() {
					@Override
					public void run() {
						if (SQTechPumps.machineLiquidTypeIdMap.get(machine) != null) {
							waterBlock.setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 0, false);
							waterBlock.getRelative(BlockFace.NORTH).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 0, false);
							waterBlock.getRelative(BlockFace.EAST).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 0, false);
							waterBlock.getRelative(BlockFace.WEST).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 0, false);
							waterBlock.getRelative(BlockFace.SOUTH).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 0, false);
							waterBlock.getRelative(BlockFace.NORTH_EAST).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 0, false);
							waterBlock.getRelative(BlockFace.NORTH_WEST).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 0, false);
							waterBlock.getRelative(BlockFace.SOUTH_EAST).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 0, false);
							waterBlock.getRelative(BlockFace.SOUTH_WEST).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 0, false);
							
							waterBlock2.setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 0, false);
							waterBlock2.getRelative(BlockFace.NORTH).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 0, false);
							waterBlock2.getRelative(BlockFace.EAST).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 0, false);
							waterBlock2.getRelative(BlockFace.WEST).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 0, false);
							waterBlock2.getRelative(BlockFace.SOUTH).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 0, false);
							waterBlock2.getRelative(BlockFace.NORTH_EAST).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 0, false);
							waterBlock2.getRelative(BlockFace.NORTH_WEST).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 0, false);
							waterBlock2.getRelative(BlockFace.SOUTH_EAST).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 0, false);
							waterBlock2.getRelative(BlockFace.SOUTH_WEST).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 0, false);
							
							waterBlock3.setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 0, false);
							waterBlock3.getRelative(BlockFace.NORTH).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 0, false);
							waterBlock3.getRelative(BlockFace.EAST).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 0, false);
							waterBlock3.getRelative(BlockFace.WEST).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 0, false);
							waterBlock3.getRelative(BlockFace.SOUTH).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 0, false);
							waterBlock3.getRelative(BlockFace.NORTH_EAST).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 0, false);
							waterBlock3.getRelative(BlockFace.NORTH_WEST).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 0, false);
							waterBlock3.getRelative(BlockFace.SOUTH_EAST).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 0, false);
							waterBlock3.getRelative(BlockFace.SOUTH_WEST).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 0, false);
							
							waterBlock4.setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 3, false);
							waterBlock4.getRelative(BlockFace.NORTH).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 3, false);
							waterBlock4.getRelative(BlockFace.EAST).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 3, false);
							waterBlock4.getRelative(BlockFace.WEST).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 3, false);
							waterBlock4.getRelative(BlockFace.SOUTH).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 3, false);
							waterBlock4.getRelative(BlockFace.NORTH_EAST).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 3, false);
							waterBlock4.getRelative(BlockFace.NORTH_WEST).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 3, false);
							waterBlock4.getRelative(BlockFace.SOUTH_EAST).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 3, false);
							waterBlock4.getRelative(BlockFace.SOUTH_WEST).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 3, false);
						}
					}
				});

			} else if (current >= threePercent*30 && current < threePercent*31) {

				Bukkit.getScheduler().runTask(SQTechPumps.plugin, new BukkitRunnable() {
					@Override
					public void run() {
						if (SQTechPumps.machineLiquidTypeIdMap.get(machine) != null) {
							waterBlock.setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 0, false);
							waterBlock.getRelative(BlockFace.NORTH).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 0, false);
							waterBlock.getRelative(BlockFace.EAST).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 0, false);
							waterBlock.getRelative(BlockFace.WEST).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 0, false);
							waterBlock.getRelative(BlockFace.SOUTH).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 0, false);
							waterBlock.getRelative(BlockFace.NORTH_EAST).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 0, false);
							waterBlock.getRelative(BlockFace.NORTH_WEST).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 0, false);
							waterBlock.getRelative(BlockFace.SOUTH_EAST).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 0, false);
							waterBlock.getRelative(BlockFace.SOUTH_WEST).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 0, false);
							
							waterBlock2.setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 0, false);
							waterBlock2.getRelative(BlockFace.NORTH).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 0, false);
							waterBlock2.getRelative(BlockFace.EAST).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 0, false);
							waterBlock2.getRelative(BlockFace.WEST).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 0, false);
							waterBlock2.getRelative(BlockFace.SOUTH).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 0, false);
							waterBlock2.getRelative(BlockFace.NORTH_EAST).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 0, false);
							waterBlock2.getRelative(BlockFace.NORTH_WEST).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 0, false);
							waterBlock2.getRelative(BlockFace.SOUTH_EAST).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 0, false);
							waterBlock2.getRelative(BlockFace.SOUTH_WEST).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 0, false);
							
							waterBlock3.setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 0, false);
							waterBlock3.getRelative(BlockFace.NORTH).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 0, false);
							waterBlock3.getRelative(BlockFace.EAST).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 0, false);
							waterBlock3.getRelative(BlockFace.WEST).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 0, false);
							waterBlock3.getRelative(BlockFace.SOUTH).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 0, false);
							waterBlock3.getRelative(BlockFace.NORTH_EAST).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 0, false);
							waterBlock3.getRelative(BlockFace.NORTH_WEST).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 0, false);
							waterBlock3.getRelative(BlockFace.SOUTH_EAST).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 0, false);
							waterBlock3.getRelative(BlockFace.SOUTH_WEST).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 0, false);
							
							waterBlock4.setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 2, false);
							waterBlock4.getRelative(BlockFace.NORTH).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 2, false);
							waterBlock4.getRelative(BlockFace.EAST).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 2, false);
							waterBlock4.getRelative(BlockFace.WEST).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 2, false);
							waterBlock4.getRelative(BlockFace.SOUTH).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 2, false);
							waterBlock4.getRelative(BlockFace.NORTH_EAST).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 2, false);
							waterBlock4.getRelative(BlockFace.NORTH_WEST).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 2, false);
							waterBlock4.getRelative(BlockFace.SOUTH_EAST).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 2, false);
							waterBlock4.getRelative(BlockFace.SOUTH_WEST).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 2, false);
						}
					}
				});

			} else if (current >= threePercent*31 && current < threePercent*32) {

				Bukkit.getScheduler().runTask(SQTechPumps.plugin, new BukkitRunnable() {
					@Override
					public void run() {
						if (SQTechPumps.machineLiquidTypeIdMap.get(machine) != null) {
							waterBlock.setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 0, false);
							waterBlock.getRelative(BlockFace.NORTH).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 0, false);
							waterBlock.getRelative(BlockFace.EAST).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 0, false);
							waterBlock.getRelative(BlockFace.WEST).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 0, false);
							waterBlock.getRelative(BlockFace.SOUTH).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 0, false);
							waterBlock.getRelative(BlockFace.NORTH_EAST).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 0, false);
							waterBlock.getRelative(BlockFace.NORTH_WEST).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 0, false);
							waterBlock.getRelative(BlockFace.SOUTH_EAST).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 0, false);
							waterBlock.getRelative(BlockFace.SOUTH_WEST).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 0, false);
							
							waterBlock2.setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 0, false);
							waterBlock2.getRelative(BlockFace.NORTH).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 0, false);
							waterBlock2.getRelative(BlockFace.EAST).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 0, false);
							waterBlock2.getRelative(BlockFace.WEST).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 0, false);
							waterBlock2.getRelative(BlockFace.SOUTH).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 0, false);
							waterBlock2.getRelative(BlockFace.NORTH_EAST).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 0, false);
							waterBlock2.getRelative(BlockFace.NORTH_WEST).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 0, false);
							waterBlock2.getRelative(BlockFace.SOUTH_EAST).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 0, false);
							waterBlock2.getRelative(BlockFace.SOUTH_WEST).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 0, false);
							
							waterBlock3.setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 0, false);
							waterBlock3.getRelative(BlockFace.NORTH).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 0, false);
							waterBlock3.getRelative(BlockFace.EAST).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 0, false);
							waterBlock3.getRelative(BlockFace.WEST).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 0, false);
							waterBlock3.getRelative(BlockFace.SOUTH).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 0, false);
							waterBlock3.getRelative(BlockFace.NORTH_EAST).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 0, false);
							waterBlock3.getRelative(BlockFace.NORTH_WEST).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 0, false);
							waterBlock3.getRelative(BlockFace.SOUTH_EAST).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 0, false);
							waterBlock3.getRelative(BlockFace.SOUTH_WEST).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 0, false);
							
							waterBlock4.setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 1, false);
							waterBlock4.getRelative(BlockFace.NORTH).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 1, false);
							waterBlock4.getRelative(BlockFace.EAST).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 1, false);
							waterBlock4.getRelative(BlockFace.WEST).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 1, false);
							waterBlock4.getRelative(BlockFace.SOUTH).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 1, false);
							waterBlock4.getRelative(BlockFace.NORTH_EAST).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 1, false);
							waterBlock4.getRelative(BlockFace.NORTH_WEST).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 1, false);
							waterBlock4.getRelative(BlockFace.SOUTH_EAST).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 1, false);
							waterBlock4.getRelative(BlockFace.SOUTH_WEST).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 1, false);
						}
					}
				});

			} else if (current >= threePercent*32) {

				Bukkit.getScheduler().runTask(SQTechPumps.plugin, new BukkitRunnable() {
					@Override
					public void run() {
						if (SQTechPumps.machineLiquidTypeIdMap.get(machine) != null) {
							waterBlock.setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 0, false);
							waterBlock.getRelative(BlockFace.NORTH).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 0, false);
							waterBlock.getRelative(BlockFace.EAST).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 0, false);
							waterBlock.getRelative(BlockFace.WEST).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 0, false);
							waterBlock.getRelative(BlockFace.SOUTH).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 0, false);
							waterBlock.getRelative(BlockFace.NORTH_EAST).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 0, false);
							waterBlock.getRelative(BlockFace.NORTH_WEST).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 0, false);
							waterBlock.getRelative(BlockFace.SOUTH_EAST).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 0, false);
							waterBlock.getRelative(BlockFace.SOUTH_WEST).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 0, false);
							
							waterBlock2.setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 0, false);
							waterBlock2.getRelative(BlockFace.NORTH).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 0, false);
							waterBlock2.getRelative(BlockFace.EAST).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 0, false);
							waterBlock2.getRelative(BlockFace.WEST).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 0, false);
							waterBlock2.getRelative(BlockFace.SOUTH).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 0, false);
							waterBlock2.getRelative(BlockFace.NORTH_EAST).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 0, false);
							waterBlock2.getRelative(BlockFace.NORTH_WEST).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 0, false);
							waterBlock2.getRelative(BlockFace.SOUTH_EAST).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 0, false);
							waterBlock2.getRelative(BlockFace.SOUTH_WEST).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 0, false);
							
							waterBlock3.setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 0, false);
							waterBlock3.getRelative(BlockFace.NORTH).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 0, false);
							waterBlock3.getRelative(BlockFace.EAST).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 0, false);
							waterBlock3.getRelative(BlockFace.WEST).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 0, false);
							waterBlock3.getRelative(BlockFace.SOUTH).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 0, false);
							waterBlock3.getRelative(BlockFace.NORTH_EAST).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 0, false);
							waterBlock3.getRelative(BlockFace.NORTH_WEST).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 0, false);
							waterBlock3.getRelative(BlockFace.SOUTH_EAST).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 0, false);
							waterBlock3.getRelative(BlockFace.SOUTH_WEST).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 0, false);
							
							waterBlock4.setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 0, false);
							waterBlock4.getRelative(BlockFace.NORTH).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 0, false);
							waterBlock4.getRelative(BlockFace.EAST).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 0, false);
							waterBlock4.getRelative(BlockFace.WEST).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 0, false);
							waterBlock4.getRelative(BlockFace.SOUTH).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 0, false);
							waterBlock4.getRelative(BlockFace.NORTH_EAST).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 0, false);
							waterBlock4.getRelative(BlockFace.NORTH_WEST).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 0, false);
							waterBlock4.getRelative(BlockFace.SOUTH_EAST).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 0, false);
							waterBlock4.getRelative(BlockFace.SOUTH_WEST).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 0, false);
						}
					}
				});

			}

			updateBlock(waterBlock.getRelative(BlockFace.NORTH).getRelative(BlockFace.NORTH));

		}

	}
	
	private long lastUpdate = 0;
	
	@SuppressWarnings("deprecation")
	@Override
	public void updateLiquid(Machine machine) {
		//Bukkit.getServer().broadcastMessage("" + System.currentTimeMillis() + " - " + lastUpdate + " == " + (System.currentTimeMillis() - lastUpdate) + " < 900");
		if (System.currentTimeMillis() - lastUpdate < 900) {
			return;
		}
		
		Bukkit.getServer().getScheduler().runTask(SQTechPumps.plugin, new BukkitRunnable() {
			
			@Override
			public void run() {

				if (machine != null) {

					if (machine.getGUIBlock() != null) {

						if (getMiddleBlock(machine.getGUIBlock()) != null && getMiddleBlock(machine.getGUIBlock()).getRelative(BlockFace.UP) != null &&
								getMiddleBlock(machine.getGUIBlock()).getRelative(BlockFace.UP).getRelative(BlockFace.UP) != null &&
								getMiddleBlock(machine.getGUIBlock()).getRelative(BlockFace.UP).getRelative(BlockFace.UP).getRelative(BlockFace.UP) != null) {
							
							Block waterBlock = getMiddleBlock(machine.getGUIBlock());
							Block waterBlock2 = getMiddleBlock(machine.getGUIBlock()).getRelative(BlockFace.UP);
							Block waterBlock3 = getMiddleBlock(machine.getGUIBlock()).getRelative(BlockFace.UP).getRelative(BlockFace.UP);
							Block waterBlock4 = getMiddleBlock(machine.getGUIBlock()).getRelative(BlockFace.UP).getRelative(BlockFace.UP).getRelative(BlockFace.UP);

							List<Block> waterBlocks = new ArrayList<Block>();
							if (SQTechPumps.tankWaterBlocks.get(machine) != null) {
								waterBlocks = SQTechPumps.tankWaterBlocks.get(machine);
							}
							
							if (!waterBlocks.contains(waterBlock))  {
								waterBlocks.add(waterBlock);
							}
							if (!waterBlocks.contains(waterBlock.getRelative(BlockFace.NORTH))) {
								waterBlocks.add(waterBlock.getRelative(BlockFace.NORTH));
							}
							if (!waterBlocks.contains(waterBlock.getRelative(BlockFace.EAST))) {
								waterBlocks.add(waterBlock.getRelative(BlockFace.EAST));
							}
							if (!waterBlocks.contains(waterBlock.getRelative(BlockFace.WEST))) {
								waterBlocks.add(waterBlock.getRelative(BlockFace.WEST));
							}
							if (!waterBlocks.contains(waterBlock.getRelative(BlockFace.SOUTH))) {
								waterBlocks.add(waterBlock.getRelative(BlockFace.SOUTH));
							}
							if (!waterBlocks.contains(waterBlock.getRelative(BlockFace.NORTH_EAST))) {
								waterBlocks.add(waterBlock.getRelative(BlockFace.NORTH_EAST));
							}
							if (!waterBlocks.contains(waterBlock.getRelative(BlockFace.NORTH_WEST))) {
								waterBlocks.add(waterBlock.getRelative(BlockFace.NORTH_WEST));
							}
							if (!waterBlocks.contains(waterBlock.getRelative(BlockFace.SOUTH_EAST))) {
								waterBlocks.add(waterBlock.getRelative(BlockFace.SOUTH_EAST));
							}
							if (!waterBlocks.contains(waterBlock.getRelative(BlockFace.SOUTH_WEST))) {
								waterBlocks.add(waterBlock.getRelative(BlockFace.SOUTH_WEST));
							}
								
							//INBETWEEN
							
							if (!waterBlocks.contains(waterBlock2))  {
								waterBlocks.add(waterBlock2);
							}
							if (!waterBlocks.contains(waterBlock2.getRelative(BlockFace.NORTH))) {
								waterBlocks.add(waterBlock2.getRelative(BlockFace.NORTH));
							}
							if (!waterBlocks.contains(waterBlock2.getRelative(BlockFace.EAST))) {
								waterBlocks.add(waterBlock2.getRelative(BlockFace.EAST));
							}
							if (!waterBlocks.contains(waterBlock2.getRelative(BlockFace.WEST))) {
								waterBlocks.add(waterBlock2.getRelative(BlockFace.WEST));
							}
							if (!waterBlocks.contains(waterBlock2.getRelative(BlockFace.SOUTH))) {
								waterBlocks.add(waterBlock2.getRelative(BlockFace.SOUTH));
							}
							if (!waterBlocks.contains(waterBlock2.getRelative(BlockFace.NORTH_EAST))) {
								waterBlocks.add(waterBlock2.getRelative(BlockFace.NORTH_EAST));
							}
							if (!waterBlocks.contains(waterBlock2.getRelative(BlockFace.NORTH_WEST))) {
								waterBlocks.add(waterBlock2.getRelative(BlockFace.NORTH_WEST));
							}
							if (!waterBlocks.contains(waterBlock2.getRelative(BlockFace.SOUTH_EAST))) {
								waterBlocks.add(waterBlock2.getRelative(BlockFace.SOUTH_EAST));
							}
							if (!waterBlocks.contains(waterBlock2.getRelative(BlockFace.SOUTH_WEST))) {
								waterBlocks.add(waterBlock2.getRelative(BlockFace.SOUTH_WEST));
							}
								
							//INBETWEEN
							
							if (!waterBlocks.contains(waterBlock3))  {
								waterBlocks.add(waterBlock3);
							}
							if (!waterBlocks.contains(waterBlock3.getRelative(BlockFace.NORTH))) {
								waterBlocks.add(waterBlock3.getRelative(BlockFace.NORTH));
							}
							if (!waterBlocks.contains(waterBlock3.getRelative(BlockFace.EAST))) {
								waterBlocks.add(waterBlock3.getRelative(BlockFace.EAST));
							}
							if (!waterBlocks.contains(waterBlock3.getRelative(BlockFace.WEST))) {
								waterBlocks.add(waterBlock3.getRelative(BlockFace.WEST));
							}
							if (!waterBlocks.contains(waterBlock3.getRelative(BlockFace.SOUTH))) {
								waterBlocks.add(waterBlock3.getRelative(BlockFace.SOUTH));
							}
							if (!waterBlocks.contains(waterBlock3.getRelative(BlockFace.NORTH_EAST))) {
								waterBlocks.add(waterBlock3.getRelative(BlockFace.NORTH_EAST));
							}
							if (!waterBlocks.contains(waterBlock3.getRelative(BlockFace.NORTH_WEST))) {
								waterBlocks.add(waterBlock3.getRelative(BlockFace.NORTH_WEST));
							}
							if (!waterBlocks.contains(waterBlock3.getRelative(BlockFace.SOUTH_EAST))) {
								waterBlocks.add(waterBlock3.getRelative(BlockFace.SOUTH_EAST));
							}
							if (!waterBlocks.contains(waterBlock3.getRelative(BlockFace.SOUTH_WEST))) {
								waterBlocks.add(waterBlock3.getRelative(BlockFace.SOUTH_WEST));
							}
								
							//INBETWEEN
							
							if (!waterBlocks.contains(waterBlock4))  {
								waterBlocks.add(waterBlock4);
							}
							if (!waterBlocks.contains(waterBlock4.getRelative(BlockFace.NORTH))) {
								waterBlocks.add(waterBlock4.getRelative(BlockFace.NORTH));
							}
							if (!waterBlocks.contains(waterBlock4.getRelative(BlockFace.EAST))) {
								waterBlocks.add(waterBlock4.getRelative(BlockFace.EAST));
							}
							if (!waterBlocks.contains(waterBlock4.getRelative(BlockFace.WEST))) {
								waterBlocks.add(waterBlock4.getRelative(BlockFace.WEST));
							}
							if (!waterBlocks.contains(waterBlock4.getRelative(BlockFace.SOUTH))) {
								waterBlocks.add(waterBlock4.getRelative(BlockFace.SOUTH));
							}
							if (!waterBlocks.contains(waterBlock4.getRelative(BlockFace.NORTH_EAST))) {
								waterBlocks.add(waterBlock4.getRelative(BlockFace.NORTH_EAST));
							}
							if (!waterBlocks.contains(waterBlock4.getRelative(BlockFace.NORTH_WEST))) {
								waterBlocks.add(waterBlock4.getRelative(BlockFace.NORTH_WEST));
							}
							if (!waterBlocks.contains(waterBlock4.getRelative(BlockFace.SOUTH_EAST))) {
								waterBlocks.add(waterBlock4.getRelative(BlockFace.SOUTH_EAST));
							}
							if (!waterBlocks.contains(waterBlock4.getRelative(BlockFace.SOUTH_WEST))) {
								waterBlocks.add(waterBlock4.getRelative(BlockFace.SOUTH_WEST));
							}
								
							SQTechPumps.tankWaterBlocks.put(machine, waterBlocks);

							for (Fluid f : SQTechBase.fluids) {

								if (!(machine.getMaxLiquid(f) > 1)) continue;

								if (f.name == "Water") {
									SQTechPumps.machineLiquidTypeIdMap.put(machine, 9);
								} else if (f.name == "Lava") {
									SQTechPumps.machineLiquidTypeIdMap.put(machine, 11);
								}

								int max = machine.getMaxLiquid(f);

								int current = machine.getLiquid(f);

								double threePercent = (max/100) * 3.125;

								if (current >= threePercent && current < threePercent*2) {

									Bukkit.getScheduler().runTask(SQTechPumps.plugin, new BukkitRunnable() {
										@Override
										public void run() {
											if (SQTechPumps.machineLiquidTypeIdMap.get(machine) != null) {
												waterBlock.setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 7, false);
												waterBlock.getRelative(BlockFace.NORTH).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 7, false);
												waterBlock.getRelative(BlockFace.EAST).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 7, false);
												waterBlock.getRelative(BlockFace.WEST).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 7, false);
												waterBlock.getRelative(BlockFace.SOUTH).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 7, false);
												waterBlock.getRelative(BlockFace.NORTH_EAST).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 7, false);
												waterBlock.getRelative(BlockFace.NORTH_WEST).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 7, false);
												waterBlock.getRelative(BlockFace.SOUTH_EAST).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 7, false);
												waterBlock.getRelative(BlockFace.SOUTH_WEST).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 7, false);
											}
										}
									});

								} else if (current >= threePercent*2 && current < threePercent*3) {

									Bukkit.getScheduler().runTask(SQTechPumps.plugin, new BukkitRunnable() {
										@Override
										public void run() {
											if (SQTechPumps.machineLiquidTypeIdMap.get(machine) != null) {
												waterBlock.setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 6, false);
												waterBlock.getRelative(BlockFace.NORTH).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 6, false);
												waterBlock.getRelative(BlockFace.EAST).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 6, false);
												waterBlock.getRelative(BlockFace.WEST).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 6, false);
												waterBlock.getRelative(BlockFace.SOUTH).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 6, false);
												waterBlock.getRelative(BlockFace.NORTH_EAST).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 6, false);
												waterBlock.getRelative(BlockFace.NORTH_WEST).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 6, false);
												waterBlock.getRelative(BlockFace.SOUTH_EAST).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 6, false);
												waterBlock.getRelative(BlockFace.SOUTH_WEST).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 6, false);
											}
										}
									});

								} else if (current >= threePercent*3 && current < threePercent*4) {

									Bukkit.getScheduler().runTask(SQTechPumps.plugin, new BukkitRunnable() {
										@Override
										public void run() {
											if (SQTechPumps.machineLiquidTypeIdMap.get(machine) != null) {
												waterBlock.setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 5, false);
												waterBlock.getRelative(BlockFace.NORTH).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 5, false);
												waterBlock.getRelative(BlockFace.EAST).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 5, false);
												waterBlock.getRelative(BlockFace.WEST).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 5, false);
												waterBlock.getRelative(BlockFace.SOUTH).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 5, false);
												waterBlock.getRelative(BlockFace.NORTH_EAST).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 5, false);
												waterBlock.getRelative(BlockFace.NORTH_WEST).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 5, false);
												waterBlock.getRelative(BlockFace.SOUTH_EAST).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 5, false);
												waterBlock.getRelative(BlockFace.SOUTH_WEST).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 5, false);
											}
										}
									});

								} else if (current >= threePercent*4 && current < threePercent*5) {

									Bukkit.getScheduler().runTask(SQTechPumps.plugin, new BukkitRunnable() {
										@Override
										public void run() {
											if (SQTechPumps.machineLiquidTypeIdMap.get(machine) != null) {
												waterBlock.setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 4, false);
												waterBlock.getRelative(BlockFace.NORTH).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 4, false);
												waterBlock.getRelative(BlockFace.EAST).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 4, false);
												waterBlock.getRelative(BlockFace.WEST).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 4, false);
												waterBlock.getRelative(BlockFace.SOUTH).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 4, false);
												waterBlock.getRelative(BlockFace.NORTH_EAST).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 4, false);
												waterBlock.getRelative(BlockFace.NORTH_WEST).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 4, false);
												waterBlock.getRelative(BlockFace.SOUTH_EAST).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 4, false);
												waterBlock.getRelative(BlockFace.SOUTH_WEST).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 4, false);
											}
										}
									});

								} else if (current >= threePercent*5 && current < threePercent*6) {

									Bukkit.getScheduler().runTask(SQTechPumps.plugin, new BukkitRunnable() {
										@Override
										public void run() {
											if (SQTechPumps.machineLiquidTypeIdMap.get(machine) != null) {
												waterBlock.setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 3, false);
												waterBlock.getRelative(BlockFace.NORTH).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 3, false);
												waterBlock.getRelative(BlockFace.EAST).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 3, false);
												waterBlock.getRelative(BlockFace.WEST).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 3, false);
												waterBlock.getRelative(BlockFace.SOUTH).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 3, false);
												waterBlock.getRelative(BlockFace.NORTH_EAST).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 3, false);
												waterBlock.getRelative(BlockFace.NORTH_WEST).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 3, false);
												waterBlock.getRelative(BlockFace.SOUTH_EAST).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 3, false);
												waterBlock.getRelative(BlockFace.SOUTH_WEST).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 3, false);
											}
										}
									});

								} else if (current >= threePercent*6 && current < threePercent*7) {

									Bukkit.getScheduler().runTask(SQTechPumps.plugin, new BukkitRunnable() {
										@Override
										public void run() {
											if (SQTechPumps.machineLiquidTypeIdMap.get(machine) != null) {
												waterBlock.setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 2, false);
												waterBlock.getRelative(BlockFace.NORTH).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 2, false);
												waterBlock.getRelative(BlockFace.EAST).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 2, false);
												waterBlock.getRelative(BlockFace.WEST).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 2, false);
												waterBlock.getRelative(BlockFace.SOUTH).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 2, false);
												waterBlock.getRelative(BlockFace.NORTH_EAST).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 2, false);
												waterBlock.getRelative(BlockFace.NORTH_WEST).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 2, false);
												waterBlock.getRelative(BlockFace.SOUTH_EAST).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 2, false);
												waterBlock.getRelative(BlockFace.SOUTH_WEST).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 2, false);
											}
										}
									});

								} else if (current >= threePercent*7 && current < threePercent*8) {

									Bukkit.getScheduler().runTask(SQTechPumps.plugin, new BukkitRunnable() {
										@Override
										public void run() {
											if (SQTechPumps.machineLiquidTypeIdMap.get(machine) != null) {
												waterBlock.setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 1, false);
												waterBlock.getRelative(BlockFace.NORTH).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 1, false);
												waterBlock.getRelative(BlockFace.EAST).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 1, false);
												waterBlock.getRelative(BlockFace.WEST).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 1, false);
												waterBlock.getRelative(BlockFace.SOUTH).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 1, false);
												waterBlock.getRelative(BlockFace.NORTH_EAST).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 1, false);
												waterBlock.getRelative(BlockFace.NORTH_WEST).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 1, false);
												waterBlock.getRelative(BlockFace.SOUTH_EAST).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 1, false);
												waterBlock.getRelative(BlockFace.SOUTH_WEST).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 1, false);
											}
										}
									});

								} else if (current >= threePercent*8 && current < threePercent*9) {

									Bukkit.getScheduler().runTask(SQTechPumps.plugin, new BukkitRunnable() {
										@Override
										public void run() {
											if (SQTechPumps.machineLiquidTypeIdMap.get(machine) != null) {
												waterBlock.setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 0, false);
												waterBlock.getRelative(BlockFace.NORTH).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 0, false);
												waterBlock.getRelative(BlockFace.EAST).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 0, false);
												waterBlock.getRelative(BlockFace.WEST).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 0, false);
												waterBlock.getRelative(BlockFace.SOUTH).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 0, false);
												waterBlock.getRelative(BlockFace.NORTH_EAST).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 0, false);
												waterBlock.getRelative(BlockFace.NORTH_WEST).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 0, false);
												waterBlock.getRelative(BlockFace.SOUTH_EAST).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 0, false);
												waterBlock.getRelative(BlockFace.SOUTH_WEST).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 0, false);
											}
										}
									});

								} else if (current >= threePercent*9 && current < threePercent*10) {

									Bukkit.getScheduler().runTask(SQTechPumps.plugin, new BukkitRunnable() {
										@Override
										public void run() {
											if (SQTechPumps.machineLiquidTypeIdMap.get(machine) != null) {
												waterBlock.setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 0, false);
												waterBlock.getRelative(BlockFace.NORTH).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 0, false);
												waterBlock.getRelative(BlockFace.EAST).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 0, false);
												waterBlock.getRelative(BlockFace.WEST).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 0, false);
												waterBlock.getRelative(BlockFace.SOUTH).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 0, false);
												waterBlock.getRelative(BlockFace.NORTH_EAST).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 0, false);
												waterBlock.getRelative(BlockFace.NORTH_WEST).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 0, false);
												waterBlock.getRelative(BlockFace.SOUTH_EAST).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 0, false);
												waterBlock.getRelative(BlockFace.SOUTH_WEST).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 0, false);
												
												waterBlock2.setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 7, false);
												waterBlock2.getRelative(BlockFace.NORTH).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 7, false);
												waterBlock2.getRelative(BlockFace.EAST).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 7, false);
												waterBlock2.getRelative(BlockFace.WEST).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 7, false);
												waterBlock2.getRelative(BlockFace.SOUTH).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 7, false);
												waterBlock2.getRelative(BlockFace.NORTH_EAST).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 7, false);
												waterBlock2.getRelative(BlockFace.NORTH_WEST).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 7, false);
												waterBlock2.getRelative(BlockFace.SOUTH_EAST).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 7, false);
												waterBlock2.getRelative(BlockFace.SOUTH_WEST).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 7, false);
											}
										}
									});

								} else if (current >= threePercent*10 && current < threePercent*11) {

									Bukkit.getScheduler().runTask(SQTechPumps.plugin, new BukkitRunnable() {
										@Override
										public void run() {
											if (SQTechPumps.machineLiquidTypeIdMap.get(machine) != null) {
												waterBlock.setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 0, false);
												waterBlock.getRelative(BlockFace.NORTH).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 0, false);
												waterBlock.getRelative(BlockFace.EAST).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 0, false);
												waterBlock.getRelative(BlockFace.WEST).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 0, false);
												waterBlock.getRelative(BlockFace.SOUTH).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 0, false);
												waterBlock.getRelative(BlockFace.NORTH_EAST).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 0, false);
												waterBlock.getRelative(BlockFace.NORTH_WEST).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 0, false);
												waterBlock.getRelative(BlockFace.SOUTH_EAST).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 0, false);
												waterBlock.getRelative(BlockFace.SOUTH_WEST).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 0, false);
												
												waterBlock2.setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 6, false);
												waterBlock2.getRelative(BlockFace.NORTH).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 6, false);
												waterBlock2.getRelative(BlockFace.EAST).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 6, false);
												waterBlock2.getRelative(BlockFace.WEST).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 6, false);
												waterBlock2.getRelative(BlockFace.SOUTH).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 6, false);
												waterBlock2.getRelative(BlockFace.NORTH_EAST).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 6, false);
												waterBlock2.getRelative(BlockFace.NORTH_WEST).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 6, false);
												waterBlock2.getRelative(BlockFace.SOUTH_EAST).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 6, false);
												waterBlock2.getRelative(BlockFace.SOUTH_WEST).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 6, false);
											}
										}
									});

								} else if (current >= threePercent*11 && current < threePercent*12) {

									Bukkit.getScheduler().runTask(SQTechPumps.plugin, new BukkitRunnable() {
										@Override
										public void run() {
											if (SQTechPumps.machineLiquidTypeIdMap.get(machine) != null) {
												waterBlock.setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 0, false);
												waterBlock.getRelative(BlockFace.NORTH).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 0, false);
												waterBlock.getRelative(BlockFace.EAST).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 0, false);
												waterBlock.getRelative(BlockFace.WEST).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 0, false);
												waterBlock.getRelative(BlockFace.SOUTH).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 0, false);
												waterBlock.getRelative(BlockFace.NORTH_EAST).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 0, false);
												waterBlock.getRelative(BlockFace.NORTH_WEST).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 0, false);
												waterBlock.getRelative(BlockFace.SOUTH_EAST).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 0, false);
												waterBlock.getRelative(BlockFace.SOUTH_WEST).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 0, false);
												
												waterBlock2.setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 5, false);
												waterBlock2.getRelative(BlockFace.NORTH).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 5, false);
												waterBlock2.getRelative(BlockFace.EAST).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 5, false);
												waterBlock2.getRelative(BlockFace.WEST).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 5, false);
												waterBlock2.getRelative(BlockFace.SOUTH).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 5, false);
												waterBlock2.getRelative(BlockFace.NORTH_EAST).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 5, false);
												waterBlock2.getRelative(BlockFace.NORTH_WEST).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 5, false);
												waterBlock2.getRelative(BlockFace.SOUTH_EAST).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 5, false);
												waterBlock2.getRelative(BlockFace.SOUTH_WEST).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 5, false);
											}
										}
									});

								} else if (current >= threePercent*12 && current < threePercent*13) {

									Bukkit.getScheduler().runTask(SQTechPumps.plugin, new BukkitRunnable() {
										@Override
										public void run() {
											if (SQTechPumps.machineLiquidTypeIdMap.get(machine) != null) {
												waterBlock.setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 0, false);
												waterBlock.getRelative(BlockFace.NORTH).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 0, false);
												waterBlock.getRelative(BlockFace.EAST).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 0, false);
												waterBlock.getRelative(BlockFace.WEST).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 0, false);
												waterBlock.getRelative(BlockFace.SOUTH).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 0, false);
												waterBlock.getRelative(BlockFace.NORTH_EAST).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 0, false);
												waterBlock.getRelative(BlockFace.NORTH_WEST).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 0, false);
												waterBlock.getRelative(BlockFace.SOUTH_EAST).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 0, false);
												waterBlock.getRelative(BlockFace.SOUTH_WEST).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 0, false);
												
												waterBlock2.setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 4, false);
												waterBlock2.getRelative(BlockFace.NORTH).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 4, false);
												waterBlock2.getRelative(BlockFace.EAST).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 4, false);
												waterBlock2.getRelative(BlockFace.WEST).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 4, false);
												waterBlock2.getRelative(BlockFace.SOUTH).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 4, false);
												waterBlock2.getRelative(BlockFace.NORTH_EAST).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 4, false);
												waterBlock2.getRelative(BlockFace.NORTH_WEST).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 4, false);
												waterBlock2.getRelative(BlockFace.SOUTH_EAST).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 4, false);
												waterBlock2.getRelative(BlockFace.SOUTH_WEST).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 4, false);
											}
										}
									});

								} else if (current >= threePercent*13 && current < threePercent*14) {

									Bukkit.getScheduler().runTask(SQTechPumps.plugin, new BukkitRunnable() {
										@Override
										public void run() {
											if (SQTechPumps.machineLiquidTypeIdMap.get(machine) != null) {
												waterBlock.setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 0, false);
												waterBlock.getRelative(BlockFace.NORTH).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 0, false);
												waterBlock.getRelative(BlockFace.EAST).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 0, false);
												waterBlock.getRelative(BlockFace.WEST).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 0, false);
												waterBlock.getRelative(BlockFace.SOUTH).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 0, false);
												waterBlock.getRelative(BlockFace.NORTH_EAST).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 0, false);
												waterBlock.getRelative(BlockFace.NORTH_WEST).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 0, false);
												waterBlock.getRelative(BlockFace.SOUTH_EAST).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 0, false);
												waterBlock.getRelative(BlockFace.SOUTH_WEST).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 0, false);
												
												waterBlock2.setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 3, false);
												waterBlock2.getRelative(BlockFace.NORTH).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 3, false);
												waterBlock2.getRelative(BlockFace.EAST).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 3, false);
												waterBlock2.getRelative(BlockFace.WEST).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 3, false);
												waterBlock2.getRelative(BlockFace.SOUTH).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 3, false);
												waterBlock2.getRelative(BlockFace.NORTH_EAST).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 3, false);
												waterBlock2.getRelative(BlockFace.NORTH_WEST).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 3, false);
												waterBlock2.getRelative(BlockFace.SOUTH_EAST).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 3, false);
												waterBlock2.getRelative(BlockFace.SOUTH_WEST).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 3, false);
											}
										}
									});

								} else if (current >= threePercent*14 && current < threePercent*15) {

									Bukkit.getScheduler().runTask(SQTechPumps.plugin, new BukkitRunnable() {
										@Override
										public void run() {
											if (SQTechPumps.machineLiquidTypeIdMap.get(machine) != null) {
												waterBlock.setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 0, false);
												waterBlock.getRelative(BlockFace.NORTH).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 0, false);
												waterBlock.getRelative(BlockFace.EAST).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 0, false);
												waterBlock.getRelative(BlockFace.WEST).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 0, false);
												waterBlock.getRelative(BlockFace.SOUTH).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 0, false);
												waterBlock.getRelative(BlockFace.NORTH_EAST).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 0, false);
												waterBlock.getRelative(BlockFace.NORTH_WEST).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 0, false);
												waterBlock.getRelative(BlockFace.SOUTH_EAST).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 0, false);
												waterBlock.getRelative(BlockFace.SOUTH_WEST).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 0, false);
												
												waterBlock2.setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 2, false);
												waterBlock2.getRelative(BlockFace.NORTH).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 2, false);
												waterBlock2.getRelative(BlockFace.EAST).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 2, false);
												waterBlock2.getRelative(BlockFace.WEST).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 2, false);
												waterBlock2.getRelative(BlockFace.SOUTH).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 2, false);
												waterBlock2.getRelative(BlockFace.NORTH_EAST).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 2, false);
												waterBlock2.getRelative(BlockFace.NORTH_WEST).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 2, false);
												waterBlock2.getRelative(BlockFace.SOUTH_EAST).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 2, false);
												waterBlock2.getRelative(BlockFace.SOUTH_WEST).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 2, false);
											}
										}
									});

								} else if (current >= threePercent*15 && current < threePercent*16) {

									Bukkit.getScheduler().runTask(SQTechPumps.plugin, new BukkitRunnable() {
										@Override
										public void run() {
											if (SQTechPumps.machineLiquidTypeIdMap.get(machine) != null) {
												waterBlock.setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 0, false);
												waterBlock.getRelative(BlockFace.NORTH).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 0, false);
												waterBlock.getRelative(BlockFace.EAST).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 0, false);
												waterBlock.getRelative(BlockFace.WEST).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 0, false);
												waterBlock.getRelative(BlockFace.SOUTH).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 0, false);
												waterBlock.getRelative(BlockFace.NORTH_EAST).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 0, false);
												waterBlock.getRelative(BlockFace.NORTH_WEST).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 0, false);
												waterBlock.getRelative(BlockFace.SOUTH_EAST).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 0, false);
												waterBlock.getRelative(BlockFace.SOUTH_WEST).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 0, false);
												
												waterBlock2.setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 1, false);
												waterBlock2.getRelative(BlockFace.NORTH).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 1, false);
												waterBlock2.getRelative(BlockFace.EAST).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 1, false);
												waterBlock2.getRelative(BlockFace.WEST).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 1, false);
												waterBlock2.getRelative(BlockFace.SOUTH).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 1, false);
												waterBlock2.getRelative(BlockFace.NORTH_EAST).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 1, false);
												waterBlock2.getRelative(BlockFace.NORTH_WEST).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 1, false);
												waterBlock2.getRelative(BlockFace.SOUTH_EAST).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 1, false);
												waterBlock2.getRelative(BlockFace.SOUTH_WEST).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 1, false);
											}
										}
									});

								} else if (current >= threePercent*16 && current < threePercent*17) {

									Bukkit.getScheduler().runTask(SQTechPumps.plugin, new BukkitRunnable() {
										@Override
										public void run() {
											if (SQTechPumps.machineLiquidTypeIdMap.get(machine) != null) {
												waterBlock.setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 0, false);
												waterBlock.getRelative(BlockFace.NORTH).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 0, false);
												waterBlock.getRelative(BlockFace.EAST).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 0, false);
												waterBlock.getRelative(BlockFace.WEST).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 0, false);
												waterBlock.getRelative(BlockFace.SOUTH).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 0, false);
												waterBlock.getRelative(BlockFace.NORTH_EAST).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 0, false);
												waterBlock.getRelative(BlockFace.NORTH_WEST).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 0, false);
												waterBlock.getRelative(BlockFace.SOUTH_EAST).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 0, false);
												waterBlock.getRelative(BlockFace.SOUTH_WEST).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 0, false);
												
												waterBlock2.setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 0, false);
												waterBlock2.getRelative(BlockFace.NORTH).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 0, false);
												waterBlock2.getRelative(BlockFace.EAST).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 0, false);
												waterBlock2.getRelative(BlockFace.WEST).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 0, false);
												waterBlock2.getRelative(BlockFace.SOUTH).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 0, false);
												waterBlock2.getRelative(BlockFace.NORTH_EAST).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 0, false);
												waterBlock2.getRelative(BlockFace.NORTH_WEST).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 0, false);
												waterBlock2.getRelative(BlockFace.SOUTH_EAST).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 0, false);
												waterBlock2.getRelative(BlockFace.SOUTH_WEST).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 0, false);
											}
										}
									});

								} else if (current >= threePercent*17 && current < threePercent*18) {

									Bukkit.getScheduler().runTask(SQTechPumps.plugin, new BukkitRunnable() {
										@Override
										public void run() {
											if (SQTechPumps.machineLiquidTypeIdMap.get(machine) != null) {
												waterBlock.setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 0, false);
												waterBlock.getRelative(BlockFace.NORTH).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 0, false);
												waterBlock.getRelative(BlockFace.EAST).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 0, false);
												waterBlock.getRelative(BlockFace.WEST).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 0, false);
												waterBlock.getRelative(BlockFace.SOUTH).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 0, false);
												waterBlock.getRelative(BlockFace.NORTH_EAST).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 0, false);
												waterBlock.getRelative(BlockFace.NORTH_WEST).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 0, false);
												waterBlock.getRelative(BlockFace.SOUTH_EAST).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 0, false);
												waterBlock.getRelative(BlockFace.SOUTH_WEST).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 0, false);
												
												waterBlock2.setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 0, false);
												waterBlock2.getRelative(BlockFace.NORTH).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 0, false);
												waterBlock2.getRelative(BlockFace.EAST).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 0, false);
												waterBlock2.getRelative(BlockFace.WEST).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 0, false);
												waterBlock2.getRelative(BlockFace.SOUTH).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 0, false);
												waterBlock2.getRelative(BlockFace.NORTH_EAST).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 0, false);
												waterBlock2.getRelative(BlockFace.NORTH_WEST).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 0, false);
												waterBlock2.getRelative(BlockFace.SOUTH_EAST).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 0, false);
												waterBlock2.getRelative(BlockFace.SOUTH_WEST).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 0, false);
												
												waterBlock3.setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 7, false);
												waterBlock3.getRelative(BlockFace.NORTH).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 7, false);
												waterBlock3.getRelative(BlockFace.EAST).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 7, false);
												waterBlock3.getRelative(BlockFace.WEST).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 7, false);
												waterBlock3.getRelative(BlockFace.SOUTH).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 7, false);
												waterBlock3.getRelative(BlockFace.NORTH_EAST).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 7, false);
												waterBlock3.getRelative(BlockFace.NORTH_WEST).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 7, false);
												waterBlock3.getRelative(BlockFace.SOUTH_EAST).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 7, false);
												waterBlock3.getRelative(BlockFace.SOUTH_WEST).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 7, false);
											}
										}
									});

								} else if (current >= threePercent*18 && current < threePercent*19) {

									Bukkit.getScheduler().runTask(SQTechPumps.plugin, new BukkitRunnable() {
										@Override
										public void run() {
											if (SQTechPumps.machineLiquidTypeIdMap.get(machine) != null) {
												waterBlock.setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 0, false);
												waterBlock.getRelative(BlockFace.NORTH).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 0, false);
												waterBlock.getRelative(BlockFace.EAST).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 0, false);
												waterBlock.getRelative(BlockFace.WEST).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 0, false);
												waterBlock.getRelative(BlockFace.SOUTH).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 0, false);
												waterBlock.getRelative(BlockFace.NORTH_EAST).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 0, false);
												waterBlock.getRelative(BlockFace.NORTH_WEST).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 0, false);
												waterBlock.getRelative(BlockFace.SOUTH_EAST).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 0, false);
												waterBlock.getRelative(BlockFace.SOUTH_WEST).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 0, false);
												
												waterBlock2.setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 0, false);
												waterBlock2.getRelative(BlockFace.NORTH).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 0, false);
												waterBlock2.getRelative(BlockFace.EAST).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 0, false);
												waterBlock2.getRelative(BlockFace.WEST).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 0, false);
												waterBlock2.getRelative(BlockFace.SOUTH).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 0, false);
												waterBlock2.getRelative(BlockFace.NORTH_EAST).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 0, false);
												waterBlock2.getRelative(BlockFace.NORTH_WEST).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 0, false);
												waterBlock2.getRelative(BlockFace.SOUTH_EAST).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 0, false);
												waterBlock2.getRelative(BlockFace.SOUTH_WEST).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 0, false);
												
												waterBlock3.setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 6, false);
												waterBlock3.getRelative(BlockFace.NORTH).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 6, false);
												waterBlock3.getRelative(BlockFace.EAST).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 6, false);
												waterBlock3.getRelative(BlockFace.WEST).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 6, false);
												waterBlock3.getRelative(BlockFace.SOUTH).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 6, false);
												waterBlock3.getRelative(BlockFace.NORTH_EAST).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 6, false);
												waterBlock3.getRelative(BlockFace.NORTH_WEST).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 6, false);
												waterBlock3.getRelative(BlockFace.SOUTH_EAST).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 6, false);
												waterBlock3.getRelative(BlockFace.SOUTH_WEST).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 6, false);
											}
										}
									});

								} else if (current >= threePercent*19 && current < threePercent*20) {

									Bukkit.getScheduler().runTask(SQTechPumps.plugin, new BukkitRunnable() {
										@Override
										public void run() {
											if (SQTechPumps.machineLiquidTypeIdMap.get(machine) != null) {
												waterBlock.setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 0, false);
												waterBlock.getRelative(BlockFace.NORTH).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 0, false);
												waterBlock.getRelative(BlockFace.EAST).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 0, false);
												waterBlock.getRelative(BlockFace.WEST).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 0, false);
												waterBlock.getRelative(BlockFace.SOUTH).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 0, false);
												waterBlock.getRelative(BlockFace.NORTH_EAST).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 0, false);
												waterBlock.getRelative(BlockFace.NORTH_WEST).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 0, false);
												waterBlock.getRelative(BlockFace.SOUTH_EAST).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 0, false);
												waterBlock.getRelative(BlockFace.SOUTH_WEST).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 0, false);
												
												waterBlock2.setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 0, false);
												waterBlock2.getRelative(BlockFace.NORTH).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 0, false);
												waterBlock2.getRelative(BlockFace.EAST).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 0, false);
												waterBlock2.getRelative(BlockFace.WEST).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 0, false);
												waterBlock2.getRelative(BlockFace.SOUTH).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 0, false);
												waterBlock2.getRelative(BlockFace.NORTH_EAST).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 0, false);
												waterBlock2.getRelative(BlockFace.NORTH_WEST).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 0, false);
												waterBlock2.getRelative(BlockFace.SOUTH_EAST).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 0, false);
												waterBlock2.getRelative(BlockFace.SOUTH_WEST).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 0, false);
												
												waterBlock3.setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 5, false);
												waterBlock3.getRelative(BlockFace.NORTH).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 5, false);
												waterBlock3.getRelative(BlockFace.EAST).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 5, false);
												waterBlock3.getRelative(BlockFace.WEST).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 5, false);
												waterBlock3.getRelative(BlockFace.SOUTH).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 5, false);
												waterBlock3.getRelative(BlockFace.NORTH_EAST).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 5, false);
												waterBlock3.getRelative(BlockFace.NORTH_WEST).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 5, false);
												waterBlock3.getRelative(BlockFace.SOUTH_EAST).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 5, false);
												waterBlock3.getRelative(BlockFace.SOUTH_WEST).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 5, false);
											}
										}
									});

								} else if (current >= threePercent*20 && current < threePercent*21) {

									Bukkit.getScheduler().runTask(SQTechPumps.plugin, new BukkitRunnable() {
										@Override
										public void run() {
											if (SQTechPumps.machineLiquidTypeIdMap.get(machine) != null) {
												waterBlock.setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 0, false);
												waterBlock.getRelative(BlockFace.NORTH).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 0, false);
												waterBlock.getRelative(BlockFace.EAST).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 0, false);
												waterBlock.getRelative(BlockFace.WEST).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 0, false);
												waterBlock.getRelative(BlockFace.SOUTH).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 0, false);
												waterBlock.getRelative(BlockFace.NORTH_EAST).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 0, false);
												waterBlock.getRelative(BlockFace.NORTH_WEST).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 0, false);
												waterBlock.getRelative(BlockFace.SOUTH_EAST).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 0, false);
												waterBlock.getRelative(BlockFace.SOUTH_WEST).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 0, false);
												
												waterBlock2.setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 0, false);
												waterBlock2.getRelative(BlockFace.NORTH).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 0, false);
												waterBlock2.getRelative(BlockFace.EAST).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 0, false);
												waterBlock2.getRelative(BlockFace.WEST).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 0, false);
												waterBlock2.getRelative(BlockFace.SOUTH).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 0, false);
												waterBlock2.getRelative(BlockFace.NORTH_EAST).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 0, false);
												waterBlock2.getRelative(BlockFace.NORTH_WEST).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 0, false);
												waterBlock2.getRelative(BlockFace.SOUTH_EAST).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 0, false);
												waterBlock2.getRelative(BlockFace.SOUTH_WEST).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 0, false);
												
												waterBlock3.setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 4, false);
												waterBlock3.getRelative(BlockFace.NORTH).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 4, false);
												waterBlock3.getRelative(BlockFace.EAST).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 4, false);
												waterBlock3.getRelative(BlockFace.WEST).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 4, false);
												waterBlock3.getRelative(BlockFace.SOUTH).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 4, false);
												waterBlock3.getRelative(BlockFace.NORTH_EAST).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 4, false);
												waterBlock3.getRelative(BlockFace.NORTH_WEST).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 4, false);
												waterBlock3.getRelative(BlockFace.SOUTH_EAST).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 4, false);
												waterBlock3.getRelative(BlockFace.SOUTH_WEST).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 4, false);
											}
										}
									});

								} else if (current >= threePercent*21 && current < threePercent*22) {

									Bukkit.getScheduler().runTask(SQTechPumps.plugin, new BukkitRunnable() {
										@Override
										public void run() {
											if (SQTechPumps.machineLiquidTypeIdMap.get(machine) != null) {
												waterBlock.setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 0, false);
												waterBlock.getRelative(BlockFace.NORTH).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 0, false);
												waterBlock.getRelative(BlockFace.EAST).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 0, false);
												waterBlock.getRelative(BlockFace.WEST).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 0, false);
												waterBlock.getRelative(BlockFace.SOUTH).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 0, false);
												waterBlock.getRelative(BlockFace.NORTH_EAST).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 0, false);
												waterBlock.getRelative(BlockFace.NORTH_WEST).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 0, false);
												waterBlock.getRelative(BlockFace.SOUTH_EAST).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 0, false);
												waterBlock.getRelative(BlockFace.SOUTH_WEST).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 0, false);
												
												waterBlock2.setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 0, false);
												waterBlock2.getRelative(BlockFace.NORTH).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 0, false);
												waterBlock2.getRelative(BlockFace.EAST).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 0, false);
												waterBlock2.getRelative(BlockFace.WEST).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 0, false);
												waterBlock2.getRelative(BlockFace.SOUTH).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 0, false);
												waterBlock2.getRelative(BlockFace.NORTH_EAST).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 0, false);
												waterBlock2.getRelative(BlockFace.NORTH_WEST).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 0, false);
												waterBlock2.getRelative(BlockFace.SOUTH_EAST).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 0, false);
												waterBlock2.getRelative(BlockFace.SOUTH_WEST).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 0, false);
												
												waterBlock3.setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 3, false);
												waterBlock3.getRelative(BlockFace.NORTH).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 3, false);
												waterBlock3.getRelative(BlockFace.EAST).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 3, false);
												waterBlock3.getRelative(BlockFace.WEST).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 3, false);
												waterBlock3.getRelative(BlockFace.SOUTH).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 3, false);
												waterBlock3.getRelative(BlockFace.NORTH_EAST).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 3, false);
												waterBlock3.getRelative(BlockFace.NORTH_WEST).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 3, false);
												waterBlock3.getRelative(BlockFace.SOUTH_EAST).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 3, false);
												waterBlock3.getRelative(BlockFace.SOUTH_WEST).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 3, false);
											}
										}
									});

								} else if (current >= threePercent*22 && current < threePercent*23) {

									Bukkit.getScheduler().runTask(SQTechPumps.plugin, new BukkitRunnable() {
										@Override
										public void run() {
											if (SQTechPumps.machineLiquidTypeIdMap.get(machine) != null) {
												waterBlock.setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 0, false);
												waterBlock.getRelative(BlockFace.NORTH).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 0, false);
												waterBlock.getRelative(BlockFace.EAST).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 0, false);
												waterBlock.getRelative(BlockFace.WEST).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 0, false);
												waterBlock.getRelative(BlockFace.SOUTH).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 0, false);
												waterBlock.getRelative(BlockFace.NORTH_EAST).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 0, false);
												waterBlock.getRelative(BlockFace.NORTH_WEST).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 0, false);
												waterBlock.getRelative(BlockFace.SOUTH_EAST).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 0, false);
												waterBlock.getRelative(BlockFace.SOUTH_WEST).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 0, false);
												
												waterBlock2.setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 0, false);
												waterBlock2.getRelative(BlockFace.NORTH).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 0, false);
												waterBlock2.getRelative(BlockFace.EAST).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 0, false);
												waterBlock2.getRelative(BlockFace.WEST).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 0, false);
												waterBlock2.getRelative(BlockFace.SOUTH).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 0, false);
												waterBlock2.getRelative(BlockFace.NORTH_EAST).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 0, false);
												waterBlock2.getRelative(BlockFace.NORTH_WEST).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 0, false);
												waterBlock2.getRelative(BlockFace.SOUTH_EAST).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 0, false);
												waterBlock2.getRelative(BlockFace.SOUTH_WEST).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 0, false);
												
												waterBlock3.setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 2, false);
												waterBlock3.getRelative(BlockFace.NORTH).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 2, false);
												waterBlock3.getRelative(BlockFace.EAST).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 2, false);
												waterBlock3.getRelative(BlockFace.WEST).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 2, false);
												waterBlock3.getRelative(BlockFace.SOUTH).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 2, false);
												waterBlock3.getRelative(BlockFace.NORTH_EAST).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 2, false);
												waterBlock3.getRelative(BlockFace.NORTH_WEST).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 2, false);
												waterBlock3.getRelative(BlockFace.SOUTH_EAST).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 2, false);
												waterBlock3.getRelative(BlockFace.SOUTH_WEST).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 2, false);
											}
										}
									});

								} else if (current >= threePercent*23 && current < threePercent*24) {

									Bukkit.getScheduler().runTask(SQTechPumps.plugin, new BukkitRunnable() {
										@Override
										public void run() {
											if (SQTechPumps.machineLiquidTypeIdMap.get(machine) != null) {
												waterBlock.setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 0, false);
												waterBlock.getRelative(BlockFace.NORTH).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 0, false);
												waterBlock.getRelative(BlockFace.EAST).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 0, false);
												waterBlock.getRelative(BlockFace.WEST).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 0, false);
												waterBlock.getRelative(BlockFace.SOUTH).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 0, false);
												waterBlock.getRelative(BlockFace.NORTH_EAST).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 0, false);
												waterBlock.getRelative(BlockFace.NORTH_WEST).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 0, false);
												waterBlock.getRelative(BlockFace.SOUTH_EAST).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 0, false);
												waterBlock.getRelative(BlockFace.SOUTH_WEST).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 0, false);
												
												waterBlock2.setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 0, false);
												waterBlock2.getRelative(BlockFace.NORTH).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 0, false);
												waterBlock2.getRelative(BlockFace.EAST).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 0, false);
												waterBlock2.getRelative(BlockFace.WEST).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 0, false);
												waterBlock2.getRelative(BlockFace.SOUTH).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 0, false);
												waterBlock2.getRelative(BlockFace.NORTH_EAST).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 0, false);
												waterBlock2.getRelative(BlockFace.NORTH_WEST).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 0, false);
												waterBlock2.getRelative(BlockFace.SOUTH_EAST).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 0, false);
												waterBlock2.getRelative(BlockFace.SOUTH_WEST).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 0, false);
												
												waterBlock3.setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 1, false);
												waterBlock3.getRelative(BlockFace.NORTH).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 1, false);
												waterBlock3.getRelative(BlockFace.EAST).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 1, false);
												waterBlock3.getRelative(BlockFace.WEST).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 1, false);
												waterBlock3.getRelative(BlockFace.SOUTH).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 1, false);
												waterBlock3.getRelative(BlockFace.NORTH_EAST).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 1, false);
												waterBlock3.getRelative(BlockFace.NORTH_WEST).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 1, false);
												waterBlock3.getRelative(BlockFace.SOUTH_EAST).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 1, false);
												waterBlock3.getRelative(BlockFace.SOUTH_WEST).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 1, false);
											}
										}
									});

								} else if (current >= threePercent*24 && current < threePercent*25) {

									Bukkit.getScheduler().runTask(SQTechPumps.plugin, new BukkitRunnable() {
										@Override
										public void run() {
											if (SQTechPumps.machineLiquidTypeIdMap.get(machine) != null) {
												waterBlock.setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 0, false);
												waterBlock.getRelative(BlockFace.NORTH).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 0, false);
												waterBlock.getRelative(BlockFace.EAST).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 0, false);
												waterBlock.getRelative(BlockFace.WEST).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 0, false);
												waterBlock.getRelative(BlockFace.SOUTH).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 0, false);
												waterBlock.getRelative(BlockFace.NORTH_EAST).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 0, false);
												waterBlock.getRelative(BlockFace.NORTH_WEST).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 0, false);
												waterBlock.getRelative(BlockFace.SOUTH_EAST).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 0, false);
												waterBlock.getRelative(BlockFace.SOUTH_WEST).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 0, false);
												
												waterBlock2.setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 0, false);
												waterBlock2.getRelative(BlockFace.NORTH).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 0, false);
												waterBlock2.getRelative(BlockFace.EAST).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 0, false);
												waterBlock2.getRelative(BlockFace.WEST).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 0, false);
												waterBlock2.getRelative(BlockFace.SOUTH).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 0, false);
												waterBlock2.getRelative(BlockFace.NORTH_EAST).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 0, false);
												waterBlock2.getRelative(BlockFace.NORTH_WEST).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 0, false);
												waterBlock2.getRelative(BlockFace.SOUTH_EAST).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 0, false);
												waterBlock2.getRelative(BlockFace.SOUTH_WEST).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 0, false);
												
												waterBlock3.setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 0, false);
												waterBlock3.getRelative(BlockFace.NORTH).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 0, false);
												waterBlock3.getRelative(BlockFace.EAST).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 0, false);
												waterBlock3.getRelative(BlockFace.WEST).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 0, false);
												waterBlock3.getRelative(BlockFace.SOUTH).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 0, false);
												waterBlock3.getRelative(BlockFace.NORTH_EAST).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 0, false);
												waterBlock3.getRelative(BlockFace.NORTH_WEST).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 0, false);
												waterBlock3.getRelative(BlockFace.SOUTH_EAST).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 0, false);
												waterBlock3.getRelative(BlockFace.SOUTH_WEST).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 0, false);
											}
										}
									});

								} else if (current >= threePercent*25 && current < threePercent*26) {

									Bukkit.getScheduler().runTask(SQTechPumps.plugin, new BukkitRunnable() {
										@Override
										public void run() {
											if (SQTechPumps.machineLiquidTypeIdMap.get(machine) != null) {
												waterBlock.setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 0, false);
												waterBlock.getRelative(BlockFace.NORTH).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 0, false);
												waterBlock.getRelative(BlockFace.EAST).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 0, false);
												waterBlock.getRelative(BlockFace.WEST).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 0, false);
												waterBlock.getRelative(BlockFace.SOUTH).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 0, false);
												waterBlock.getRelative(BlockFace.NORTH_EAST).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 0, false);
												waterBlock.getRelative(BlockFace.NORTH_WEST).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 0, false);
												waterBlock.getRelative(BlockFace.SOUTH_EAST).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 0, false);
												waterBlock.getRelative(BlockFace.SOUTH_WEST).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 0, false);
												
												waterBlock2.setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 0, false);
												waterBlock2.getRelative(BlockFace.NORTH).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 0, false);
												waterBlock2.getRelative(BlockFace.EAST).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 0, false);
												waterBlock2.getRelative(BlockFace.WEST).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 0, false);
												waterBlock2.getRelative(BlockFace.SOUTH).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 0, false);
												waterBlock2.getRelative(BlockFace.NORTH_EAST).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 0, false);
												waterBlock2.getRelative(BlockFace.NORTH_WEST).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 0, false);
												waterBlock2.getRelative(BlockFace.SOUTH_EAST).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 0, false);
												waterBlock2.getRelative(BlockFace.SOUTH_WEST).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 0, false);
												
												waterBlock3.setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 0, false);
												waterBlock3.getRelative(BlockFace.NORTH).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 0, false);
												waterBlock3.getRelative(BlockFace.EAST).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 0, false);
												waterBlock3.getRelative(BlockFace.WEST).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 0, false);
												waterBlock3.getRelative(BlockFace.SOUTH).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 0, false);
												waterBlock3.getRelative(BlockFace.NORTH_EAST).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 0, false);
												waterBlock3.getRelative(BlockFace.NORTH_WEST).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 0, false);
												waterBlock3.getRelative(BlockFace.SOUTH_EAST).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 0, false);
												waterBlock3.getRelative(BlockFace.SOUTH_WEST).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 0, false);
												
												waterBlock4.setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 7, false);
												waterBlock4.getRelative(BlockFace.NORTH).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 7, false);
												waterBlock4.getRelative(BlockFace.EAST).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 7, false);
												waterBlock4.getRelative(BlockFace.WEST).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 7, false);
												waterBlock4.getRelative(BlockFace.SOUTH).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 7, false);
												waterBlock4.getRelative(BlockFace.NORTH_EAST).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 7, false);
												waterBlock4.getRelative(BlockFace.NORTH_WEST).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 7, false);
												waterBlock4.getRelative(BlockFace.SOUTH_EAST).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 7, false);
												waterBlock4.getRelative(BlockFace.SOUTH_WEST).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 7, false);
											}
										}
									});

								} else if (current >= threePercent*26 && current < threePercent*27) {

									Bukkit.getScheduler().runTask(SQTechPumps.plugin, new BukkitRunnable() {
										@Override
										public void run() {
											if (SQTechPumps.machineLiquidTypeIdMap.get(machine) != null) {
												waterBlock.setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 0, false);
												waterBlock.getRelative(BlockFace.NORTH).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 0, false);
												waterBlock.getRelative(BlockFace.EAST).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 0, false);
												waterBlock.getRelative(BlockFace.WEST).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 0, false);
												waterBlock.getRelative(BlockFace.SOUTH).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 0, false);
												waterBlock.getRelative(BlockFace.NORTH_EAST).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 0, false);
												waterBlock.getRelative(BlockFace.NORTH_WEST).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 0, false);
												waterBlock.getRelative(BlockFace.SOUTH_EAST).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 0, false);
												waterBlock.getRelative(BlockFace.SOUTH_WEST).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 0, false);
												
												waterBlock2.setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 0, false);
												waterBlock2.getRelative(BlockFace.NORTH).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 0, false);
												waterBlock2.getRelative(BlockFace.EAST).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 0, false);
												waterBlock2.getRelative(BlockFace.WEST).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 0, false);
												waterBlock2.getRelative(BlockFace.SOUTH).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 0, false);
												waterBlock2.getRelative(BlockFace.NORTH_EAST).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 0, false);
												waterBlock2.getRelative(BlockFace.NORTH_WEST).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 0, false);
												waterBlock2.getRelative(BlockFace.SOUTH_EAST).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 0, false);
												waterBlock2.getRelative(BlockFace.SOUTH_WEST).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 0, false);
												
												waterBlock3.setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 0, false);
												waterBlock3.getRelative(BlockFace.NORTH).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 0, false);
												waterBlock3.getRelative(BlockFace.EAST).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 0, false);
												waterBlock3.getRelative(BlockFace.WEST).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 0, false);
												waterBlock3.getRelative(BlockFace.SOUTH).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 0, false);
												waterBlock3.getRelative(BlockFace.NORTH_EAST).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 0, false);
												waterBlock3.getRelative(BlockFace.NORTH_WEST).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 0, false);
												waterBlock3.getRelative(BlockFace.SOUTH_EAST).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 0, false);
												waterBlock3.getRelative(BlockFace.SOUTH_WEST).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 0, false);
												
												waterBlock4.setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 6, false);
												waterBlock4.getRelative(BlockFace.NORTH).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 6, false);
												waterBlock4.getRelative(BlockFace.EAST).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 6, false);
												waterBlock4.getRelative(BlockFace.WEST).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 6, false);
												waterBlock4.getRelative(BlockFace.SOUTH).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 6, false);
												waterBlock4.getRelative(BlockFace.NORTH_EAST).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 6, false);
												waterBlock4.getRelative(BlockFace.NORTH_WEST).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 6, false);
												waterBlock4.getRelative(BlockFace.SOUTH_EAST).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 6, false);
												waterBlock4.getRelative(BlockFace.SOUTH_WEST).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 6, false);
											}
										}
									});

								} else if (current >= threePercent*27 && current < threePercent*28) {

									Bukkit.getScheduler().runTask(SQTechPumps.plugin, new BukkitRunnable() {
										@Override
										public void run() {
											if (SQTechPumps.machineLiquidTypeIdMap.get(machine) != null) {
												waterBlock.setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 0, false);
												waterBlock.getRelative(BlockFace.NORTH).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 0, false);
												waterBlock.getRelative(BlockFace.EAST).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 0, false);
												waterBlock.getRelative(BlockFace.WEST).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 0, false);
												waterBlock.getRelative(BlockFace.SOUTH).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 0, false);
												waterBlock.getRelative(BlockFace.NORTH_EAST).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 0, false);
												waterBlock.getRelative(BlockFace.NORTH_WEST).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 0, false);
												waterBlock.getRelative(BlockFace.SOUTH_EAST).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 0, false);
												waterBlock.getRelative(BlockFace.SOUTH_WEST).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 0, false);
												
												waterBlock2.setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 0, false);
												waterBlock2.getRelative(BlockFace.NORTH).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 0, false);
												waterBlock2.getRelative(BlockFace.EAST).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 0, false);
												waterBlock2.getRelative(BlockFace.WEST).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 0, false);
												waterBlock2.getRelative(BlockFace.SOUTH).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 0, false);
												waterBlock2.getRelative(BlockFace.NORTH_EAST).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 0, false);
												waterBlock2.getRelative(BlockFace.NORTH_WEST).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 0, false);
												waterBlock2.getRelative(BlockFace.SOUTH_EAST).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 0, false);
												waterBlock2.getRelative(BlockFace.SOUTH_WEST).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 0, false);
												
												waterBlock3.setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 0, false);
												waterBlock3.getRelative(BlockFace.NORTH).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 0, false);
												waterBlock3.getRelative(BlockFace.EAST).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 0, false);
												waterBlock3.getRelative(BlockFace.WEST).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 0, false);
												waterBlock3.getRelative(BlockFace.SOUTH).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 0, false);
												waterBlock3.getRelative(BlockFace.NORTH_EAST).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 0, false);
												waterBlock3.getRelative(BlockFace.NORTH_WEST).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 0, false);
												waterBlock3.getRelative(BlockFace.SOUTH_EAST).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 0, false);
												waterBlock3.getRelative(BlockFace.SOUTH_WEST).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 0, false);
												
												waterBlock4.setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 5, false);
												waterBlock4.getRelative(BlockFace.NORTH).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 5, false);
												waterBlock4.getRelative(BlockFace.EAST).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 5, false);
												waterBlock4.getRelative(BlockFace.WEST).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 5, false);
												waterBlock4.getRelative(BlockFace.SOUTH).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 5, false);
												waterBlock4.getRelative(BlockFace.NORTH_EAST).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 5, false);
												waterBlock4.getRelative(BlockFace.NORTH_WEST).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 5, false);
												waterBlock4.getRelative(BlockFace.SOUTH_EAST).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 5, false);
												waterBlock4.getRelative(BlockFace.SOUTH_WEST).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 5, false);
											}
										}
									});

								} else if (current >= threePercent*28 && current < threePercent*29) {

									Bukkit.getScheduler().runTask(SQTechPumps.plugin, new BukkitRunnable() {
										@Override
										public void run() {
											if (SQTechPumps.machineLiquidTypeIdMap.get(machine) != null) {
												waterBlock.setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 0, false);
												waterBlock.getRelative(BlockFace.NORTH).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 0, false);
												waterBlock.getRelative(BlockFace.EAST).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 0, false);
												waterBlock.getRelative(BlockFace.WEST).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 0, false);
												waterBlock.getRelative(BlockFace.SOUTH).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 0, false);
												waterBlock.getRelative(BlockFace.NORTH_EAST).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 0, false);
												waterBlock.getRelative(BlockFace.NORTH_WEST).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 0, false);
												waterBlock.getRelative(BlockFace.SOUTH_EAST).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 0, false);
												waterBlock.getRelative(BlockFace.SOUTH_WEST).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 0, false);
												
												waterBlock2.setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 0, false);
												waterBlock2.getRelative(BlockFace.NORTH).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 0, false);
												waterBlock2.getRelative(BlockFace.EAST).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 0, false);
												waterBlock2.getRelative(BlockFace.WEST).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 0, false);
												waterBlock2.getRelative(BlockFace.SOUTH).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 0, false);
												waterBlock2.getRelative(BlockFace.NORTH_EAST).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 0, false);
												waterBlock2.getRelative(BlockFace.NORTH_WEST).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 0, false);
												waterBlock2.getRelative(BlockFace.SOUTH_EAST).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 0, false);
												waterBlock2.getRelative(BlockFace.SOUTH_WEST).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 0, false);
												
												waterBlock3.setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 0, false);
												waterBlock3.getRelative(BlockFace.NORTH).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 0, false);
												waterBlock3.getRelative(BlockFace.EAST).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 0, false);
												waterBlock3.getRelative(BlockFace.WEST).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 0, false);
												waterBlock3.getRelative(BlockFace.SOUTH).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 0, false);
												waterBlock3.getRelative(BlockFace.NORTH_EAST).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 0, false);
												waterBlock3.getRelative(BlockFace.NORTH_WEST).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 0, false);
												waterBlock3.getRelative(BlockFace.SOUTH_EAST).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 0, false);
												waterBlock3.getRelative(BlockFace.SOUTH_WEST).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 0, false);
												
												waterBlock4.setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 4, false);
												waterBlock4.getRelative(BlockFace.NORTH).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 4, false);
												waterBlock4.getRelative(BlockFace.EAST).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 4, false);
												waterBlock4.getRelative(BlockFace.WEST).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 4, false);
												waterBlock4.getRelative(BlockFace.SOUTH).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 4, false);
												waterBlock4.getRelative(BlockFace.NORTH_EAST).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 4, false);
												waterBlock4.getRelative(BlockFace.NORTH_WEST).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 4, false);
												waterBlock4.getRelative(BlockFace.SOUTH_EAST).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 4, false);
												waterBlock4.getRelative(BlockFace.SOUTH_WEST).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 4, false);
											}
										}
									});

								} else if (current >= threePercent*29 && current < threePercent*30) {

									Bukkit.getScheduler().runTask(SQTechPumps.plugin, new BukkitRunnable() {
										@Override
										public void run() {
											if (SQTechPumps.machineLiquidTypeIdMap.get(machine) != null) {
												waterBlock.setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 0, false);
												waterBlock.getRelative(BlockFace.NORTH).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 0, false);
												waterBlock.getRelative(BlockFace.EAST).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 0, false);
												waterBlock.getRelative(BlockFace.WEST).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 0, false);
												waterBlock.getRelative(BlockFace.SOUTH).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 0, false);
												waterBlock.getRelative(BlockFace.NORTH_EAST).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 0, false);
												waterBlock.getRelative(BlockFace.NORTH_WEST).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 0, false);
												waterBlock.getRelative(BlockFace.SOUTH_EAST).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 0, false);
												waterBlock.getRelative(BlockFace.SOUTH_WEST).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 0, false);
												
												waterBlock2.setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 0, false);
												waterBlock2.getRelative(BlockFace.NORTH).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 0, false);
												waterBlock2.getRelative(BlockFace.EAST).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 0, false);
												waterBlock2.getRelative(BlockFace.WEST).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 0, false);
												waterBlock2.getRelative(BlockFace.SOUTH).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 0, false);
												waterBlock2.getRelative(BlockFace.NORTH_EAST).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 0, false);
												waterBlock2.getRelative(BlockFace.NORTH_WEST).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 0, false);
												waterBlock2.getRelative(BlockFace.SOUTH_EAST).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 0, false);
												waterBlock2.getRelative(BlockFace.SOUTH_WEST).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 0, false);
												
												waterBlock3.setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 0, false);
												waterBlock3.getRelative(BlockFace.NORTH).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 0, false);
												waterBlock3.getRelative(BlockFace.EAST).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 0, false);
												waterBlock3.getRelative(BlockFace.WEST).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 0, false);
												waterBlock3.getRelative(BlockFace.SOUTH).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 0, false);
												waterBlock3.getRelative(BlockFace.NORTH_EAST).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 0, false);
												waterBlock3.getRelative(BlockFace.NORTH_WEST).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 0, false);
												waterBlock3.getRelative(BlockFace.SOUTH_EAST).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 0, false);
												waterBlock3.getRelative(BlockFace.SOUTH_WEST).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 0, false);
												
												waterBlock4.setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 3, false);
												waterBlock4.getRelative(BlockFace.NORTH).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 3, false);
												waterBlock4.getRelative(BlockFace.EAST).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 3, false);
												waterBlock4.getRelative(BlockFace.WEST).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 3, false);
												waterBlock4.getRelative(BlockFace.SOUTH).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 3, false);
												waterBlock4.getRelative(BlockFace.NORTH_EAST).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 3, false);
												waterBlock4.getRelative(BlockFace.NORTH_WEST).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 3, false);
												waterBlock4.getRelative(BlockFace.SOUTH_EAST).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 3, false);
												waterBlock4.getRelative(BlockFace.SOUTH_WEST).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 3, false);
											}
										}
									});

								} else if (current >= threePercent*30 && current < threePercent*31) {

									Bukkit.getScheduler().runTask(SQTechPumps.plugin, new BukkitRunnable() {
										@Override
										public void run() {
											if (SQTechPumps.machineLiquidTypeIdMap.get(machine) != null) {
												waterBlock.setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 0, false);
												waterBlock.getRelative(BlockFace.NORTH).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 0, false);
												waterBlock.getRelative(BlockFace.EAST).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 0, false);
												waterBlock.getRelative(BlockFace.WEST).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 0, false);
												waterBlock.getRelative(BlockFace.SOUTH).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 0, false);
												waterBlock.getRelative(BlockFace.NORTH_EAST).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 0, false);
												waterBlock.getRelative(BlockFace.NORTH_WEST).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 0, false);
												waterBlock.getRelative(BlockFace.SOUTH_EAST).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 0, false);
												waterBlock.getRelative(BlockFace.SOUTH_WEST).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 0, false);
												
												waterBlock2.setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 0, false);
												waterBlock2.getRelative(BlockFace.NORTH).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 0, false);
												waterBlock2.getRelative(BlockFace.EAST).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 0, false);
												waterBlock2.getRelative(BlockFace.WEST).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 0, false);
												waterBlock2.getRelative(BlockFace.SOUTH).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 0, false);
												waterBlock2.getRelative(BlockFace.NORTH_EAST).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 0, false);
												waterBlock2.getRelative(BlockFace.NORTH_WEST).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 0, false);
												waterBlock2.getRelative(BlockFace.SOUTH_EAST).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 0, false);
												waterBlock2.getRelative(BlockFace.SOUTH_WEST).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 0, false);
												
												waterBlock3.setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 0, false);
												waterBlock3.getRelative(BlockFace.NORTH).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 0, false);
												waterBlock3.getRelative(BlockFace.EAST).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 0, false);
												waterBlock3.getRelative(BlockFace.WEST).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 0, false);
												waterBlock3.getRelative(BlockFace.SOUTH).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 0, false);
												waterBlock3.getRelative(BlockFace.NORTH_EAST).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 0, false);
												waterBlock3.getRelative(BlockFace.NORTH_WEST).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 0, false);
												waterBlock3.getRelative(BlockFace.SOUTH_EAST).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 0, false);
												waterBlock3.getRelative(BlockFace.SOUTH_WEST).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 0, false);
												
												waterBlock4.setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 2, false);
												waterBlock4.getRelative(BlockFace.NORTH).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 2, false);
												waterBlock4.getRelative(BlockFace.EAST).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 2, false);
												waterBlock4.getRelative(BlockFace.WEST).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 2, false);
												waterBlock4.getRelative(BlockFace.SOUTH).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 2, false);
												waterBlock4.getRelative(BlockFace.NORTH_EAST).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 2, false);
												waterBlock4.getRelative(BlockFace.NORTH_WEST).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 2, false);
												waterBlock4.getRelative(BlockFace.SOUTH_EAST).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 2, false);
												waterBlock4.getRelative(BlockFace.SOUTH_WEST).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 2, false);
											}
										}
									});

								} else if (current >= threePercent*31 && current < threePercent*32) {

									Bukkit.getScheduler().runTask(SQTechPumps.plugin, new BukkitRunnable() {
										@Override
										public void run() {
											if (SQTechPumps.machineLiquidTypeIdMap.get(machine) != null) {
												waterBlock.setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 0, false);
												waterBlock.getRelative(BlockFace.NORTH).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 0, false);
												waterBlock.getRelative(BlockFace.EAST).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 0, false);
												waterBlock.getRelative(BlockFace.WEST).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 0, false);
												waterBlock.getRelative(BlockFace.SOUTH).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 0, false);
												waterBlock.getRelative(BlockFace.NORTH_EAST).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 0, false);
												waterBlock.getRelative(BlockFace.NORTH_WEST).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 0, false);
												waterBlock.getRelative(BlockFace.SOUTH_EAST).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 0, false);
												waterBlock.getRelative(BlockFace.SOUTH_WEST).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 0, false);
												
												waterBlock2.setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 0, false);
												waterBlock2.getRelative(BlockFace.NORTH).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 0, false);
												waterBlock2.getRelative(BlockFace.EAST).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 0, false);
												waterBlock2.getRelative(BlockFace.WEST).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 0, false);
												waterBlock2.getRelative(BlockFace.SOUTH).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 0, false);
												waterBlock2.getRelative(BlockFace.NORTH_EAST).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 0, false);
												waterBlock2.getRelative(BlockFace.NORTH_WEST).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 0, false);
												waterBlock2.getRelative(BlockFace.SOUTH_EAST).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 0, false);
												waterBlock2.getRelative(BlockFace.SOUTH_WEST).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 0, false);
												
												waterBlock3.setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 0, false);
												waterBlock3.getRelative(BlockFace.NORTH).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 0, false);
												waterBlock3.getRelative(BlockFace.EAST).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 0, false);
												waterBlock3.getRelative(BlockFace.WEST).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 0, false);
												waterBlock3.getRelative(BlockFace.SOUTH).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 0, false);
												waterBlock3.getRelative(BlockFace.NORTH_EAST).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 0, false);
												waterBlock3.getRelative(BlockFace.NORTH_WEST).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 0, false);
												waterBlock3.getRelative(BlockFace.SOUTH_EAST).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 0, false);
												waterBlock3.getRelative(BlockFace.SOUTH_WEST).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 0, false);
												
												waterBlock4.setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 1, false);
												waterBlock4.getRelative(BlockFace.NORTH).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 1, false);
												waterBlock4.getRelative(BlockFace.EAST).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 1, false);
												waterBlock4.getRelative(BlockFace.WEST).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 1, false);
												waterBlock4.getRelative(BlockFace.SOUTH).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 1, false);
												waterBlock4.getRelative(BlockFace.NORTH_EAST).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 1, false);
												waterBlock4.getRelative(BlockFace.NORTH_WEST).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 1, false);
												waterBlock4.getRelative(BlockFace.SOUTH_EAST).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 1, false);
												waterBlock4.getRelative(BlockFace.SOUTH_WEST).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 1, false);
											}
										}
									});

								} else if (current >= threePercent*32) {

									Bukkit.getScheduler().runTask(SQTechPumps.plugin, new BukkitRunnable() {
										@Override
										public void run() {
											if (SQTechPumps.machineLiquidTypeIdMap.get(machine) != null) {
												waterBlock.setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 0, false);
												waterBlock.getRelative(BlockFace.NORTH).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 0, false);
												waterBlock.getRelative(BlockFace.EAST).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 0, false);
												waterBlock.getRelative(BlockFace.WEST).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 0, false);
												waterBlock.getRelative(BlockFace.SOUTH).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 0, false);
												waterBlock.getRelative(BlockFace.NORTH_EAST).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 0, false);
												waterBlock.getRelative(BlockFace.NORTH_WEST).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 0, false);
												waterBlock.getRelative(BlockFace.SOUTH_EAST).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 0, false);
												waterBlock.getRelative(BlockFace.SOUTH_WEST).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 0, false);
												
												waterBlock2.setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 0, false);
												waterBlock2.getRelative(BlockFace.NORTH).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 0, false);
												waterBlock2.getRelative(BlockFace.EAST).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 0, false);
												waterBlock2.getRelative(BlockFace.WEST).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 0, false);
												waterBlock2.getRelative(BlockFace.SOUTH).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 0, false);
												waterBlock2.getRelative(BlockFace.NORTH_EAST).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 0, false);
												waterBlock2.getRelative(BlockFace.NORTH_WEST).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 0, false);
												waterBlock2.getRelative(BlockFace.SOUTH_EAST).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 0, false);
												waterBlock2.getRelative(BlockFace.SOUTH_WEST).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 0, false);
												
												waterBlock3.setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 0, false);
												waterBlock3.getRelative(BlockFace.NORTH).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 0, false);
												waterBlock3.getRelative(BlockFace.EAST).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 0, false);
												waterBlock3.getRelative(BlockFace.WEST).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 0, false);
												waterBlock3.getRelative(BlockFace.SOUTH).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 0, false);
												waterBlock3.getRelative(BlockFace.NORTH_EAST).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 0, false);
												waterBlock3.getRelative(BlockFace.NORTH_WEST).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 0, false);
												waterBlock3.getRelative(BlockFace.SOUTH_EAST).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 0, false);
												waterBlock3.getRelative(BlockFace.SOUTH_WEST).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 0, false);
												
												waterBlock4.setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 0, false);
												waterBlock4.getRelative(BlockFace.NORTH).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 0, false);
												waterBlock4.getRelative(BlockFace.EAST).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 0, false);
												waterBlock4.getRelative(BlockFace.WEST).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 0, false);
												waterBlock4.getRelative(BlockFace.SOUTH).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 0, false);
												waterBlock4.getRelative(BlockFace.NORTH_EAST).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 0, false);
												waterBlock4.getRelative(BlockFace.NORTH_WEST).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 0, false);
												waterBlock4.getRelative(BlockFace.SOUTH_EAST).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 0, false);
												waterBlock4.getRelative(BlockFace.SOUTH_WEST).setTypeIdAndData(SQTechPumps.machineLiquidTypeIdMap.get(machine), (byte) 0, false);
											}
										}
									});

								}

								updateBlock(waterBlock.getRelative(BlockFace.NORTH).getRelative(BlockFace.NORTH));

							}

						}

					}

				}

			}

		});

		for (Player player : SQTechBase.currentGui.keySet()) {

			if (SQTechBase.currentGui.get(player).id == machine.getGUI(player).id) {

				if (player.getOpenInventory() != null) {

					if (player.getOpenInventory().getTitle().equals(ChatColor.BLUE + "SQTech - Medium Tank")) {

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
								ChatColor.BLUE + "Amount in millibuckets: " + PumpGUI.format(amount) + "/" + PumpGUI.format(SQTechPumps.config.getInt("medium tank max liquid")),
								ChatColor.BLUE + "Amount in buckets: " + PumpGUI.format(amount/1000) + "/" + PumpGUI.format(SQTechPumps.config.getInt("medium tank max liquid")/1000),
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

			Block firstLevel = block.getLocation().getBlock();
			Block firstLevel1 = firstLevel.getRelative(f);
			Block firstLevel2 = firstLevel.getRelative(f).getRelative(f);
			Block firstLevel3 = firstLevel.getRelative(f).getRelative(f).getRelative(f);
			Block firstLevel4 = firstLevel.getRelative(f).getRelative(f).getRelative(f).getRelative(f);

			Block secondLevel = firstLevel.getRelative(up);
			Block secondLevel1 = secondLevel.getRelative(f);
			Block secondLevel2 = secondLevel.getRelative(f).getRelative(f);
			Block secondLevel3 = secondLevel.getRelative(f).getRelative(f).getRelative(f);
			Block secondLevel4 = secondLevel.getRelative(f).getRelative(f).getRelative(f).getRelative(f);

			Block thirdLevel = secondLevel.getRelative(up);
			Block thirdLevel1 = thirdLevel.getRelative(f);
			Block thirdLevel2 = thirdLevel.getRelative(f).getRelative(f);
			Block thirdLevel3 = thirdLevel.getRelative(f).getRelative(f).getRelative(f);
			Block thirdLevel4 = thirdLevel.getRelative(f).getRelative(f).getRelative(f).getRelative(f);

			Block fourthLevel = thirdLevel.getRelative(up);
			Block fourthLevel1 = fourthLevel.getRelative(f);
			Block fourthLevel2 = fourthLevel.getRelative(f).getRelative(f);
			Block fourthLevel3 = fourthLevel.getRelative(f).getRelative(f).getRelative(f);
			Block fourthLevel4 = fourthLevel.getRelative(f).getRelative(f).getRelative(f).getRelative(f);

			Block fifthLevel = fourthLevel.getRelative(up);
			Block fifthLevel1 = fifthLevel.getRelative(f);
			Block fifthLevel2 = fifthLevel.getRelative(f).getRelative(f);
			Block fifthLevel3 = fifthLevel.getRelative(f).getRelative(f).getRelative(f);
			Block fifthLevel4 = fifthLevel.getRelative(f).getRelative(f).getRelative(f).getRelative(f);

			Block sixthLevel = fifthLevel.getRelative(up);
			Block sixthLevel1 = sixthLevel.getRelative(f);
			Block sixthLevel2 = sixthLevel.getRelative(f).getRelative(f);
			Block sixthLevel3 = sixthLevel.getRelative(f).getRelative(f).getRelative(f);
			Block sixthLevel4 = sixthLevel.getRelative(f).getRelative(f).getRelative(f).getRelative(f);

			if (firstLevel.getType() == Material.LAPIS_BLOCK) {
				if (firstLevel.getRelative(right).getType() == Material.STAINED_CLAY) {
					if (firstLevel.getRelative(left).getType() == Material.STAINED_CLAY) {
						if (firstLevel1.getType() == Material.STAINED_CLAY) {
							if (firstLevel1.getRelative(left).getType() == Material.STAINED_CLAY) {
								if (firstLevel1.getRelative(left).getRelative(left).getType() == Material.STAINED_CLAY) {
									if (firstLevel1.getRelative(right).getType() == Material.STAINED_CLAY) {
										if (firstLevel1.getRelative(right).getRelative(right).getType() == Material.STAINED_CLAY) {
											if (firstLevel2.getType() == Material.STAINED_CLAY) {
												if (firstLevel2.getRelative(left).getType() == Material.STAINED_CLAY) {
													if (firstLevel2.getRelative(left).getRelative(left).getType() == Material.STAINED_CLAY) {
														if (firstLevel2.getRelative(right).getType() == Material.STAINED_CLAY) {
															if (firstLevel2.getRelative(right).getRelative(right).getType() == Material.STAINED_CLAY) {
																if (firstLevel3.getType() == Material.STAINED_CLAY) {
																	if (firstLevel3.getRelative(left).getType() == Material.STAINED_CLAY) {
																		if (firstLevel3.getRelative(left).getRelative(left).getType() == Material.STAINED_CLAY) {
																			if (firstLevel3.getRelative(right).getType() == Material.STAINED_CLAY) {
																				if (firstLevel3.getRelative(right).getRelative(right).getType() == Material.STAINED_CLAY) {
																					if (firstLevel4.getType() == Material.STAINED_CLAY) {
																						if (firstLevel4.getRelative(right).getType() == Material.STAINED_CLAY) {
																							if (firstLevel4.getRelative(left).getType() == Material.STAINED_CLAY) {

																								if (secondLevel.getType() == Material.STAINED_GLASS_PANE) {
																									if (secondLevel.getRelative(right).getType() == Material.STAINED_GLASS_PANE) {
																										if (secondLevel.getRelative(right).getRelative(right).getType() == Material.STAINED_CLAY) {
																											if (secondLevel.getRelative(left).getType() == Material.STAINED_GLASS_PANE) {
																												if (secondLevel.getRelative(left).getRelative(left).getType() == Material.STAINED_CLAY) {
																													if (secondLevel1.getType() == Material.AIR || secondLevel1.getType() == Material.STATIONARY_LAVA || secondLevel1.getType() == Material.STATIONARY_WATER) {
																														if (secondLevel1.getRelative(right).getType() == Material.AIR || secondLevel1.getRelative(right).getType() == Material.STATIONARY_LAVA || secondLevel1.getRelative(right).getType() == Material.STATIONARY_WATER) {
																															if (secondLevel1.getRelative(right).getRelative(right).getType() == Material.STAINED_GLASS_PANE) {
																																if (secondLevel1.getRelative(left).getType() == Material.AIR || secondLevel1.getRelative(left).getType() == Material.STATIONARY_LAVA || secondLevel1.getRelative(left).getType() == Material.STATIONARY_WATER) {
																																	if (secondLevel1.getRelative(left).getRelative(left).getType() == Material.STAINED_GLASS_PANE) {
																																		if (secondLevel2.getType() == Material.AIR || secondLevel2.getType() == Material.STATIONARY_LAVA || secondLevel2.getType() == Material.STATIONARY_WATER) {
																																			if (secondLevel2.getRelative(right).getType() == Material.AIR || secondLevel2.getRelative(right).getType() == Material.STATIONARY_LAVA || secondLevel2.getRelative(right).getType() == Material.STATIONARY_WATER) {
																																				if (secondLevel2.getRelative(right).getRelative(right).getType() == Material.STAINED_GLASS_PANE) {
																																					if (secondLevel2.getRelative(left).getType() == Material.AIR || secondLevel2.getRelative(left).getType() == Material.STATIONARY_LAVA || secondLevel2.getRelative(left).getType() == Material.STATIONARY_WATER) {
																																						if (secondLevel2.getRelative(left).getRelative(left).getType() == Material.STAINED_GLASS_PANE) {
																																							if (secondLevel3.getType() == Material.AIR || secondLevel3.getType() == Material.STATIONARY_LAVA || secondLevel3.getType() == Material.STATIONARY_WATER) {
																																								if (secondLevel3.getRelative(right).getType() == Material.AIR || secondLevel3.getRelative(right).getType() == Material.STATIONARY_LAVA || secondLevel3.getRelative(right).getType() == Material.STATIONARY_WATER) {
																																									if (secondLevel3.getRelative(right).getRelative(right).getType() == Material.STAINED_GLASS_PANE) {
																																										if (secondLevel3.getRelative(left).getType() == Material.AIR || secondLevel3.getRelative(left).getType() == Material.STATIONARY_LAVA || secondLevel3.getRelative(left).getType() == Material.STATIONARY_WATER) {
																																											if (secondLevel3.getRelative(left).getRelative(left).getType() == Material.STAINED_GLASS_PANE) {
																																												if (secondLevel4.getType() == Material.STAINED_GLASS_PANE) {
																																													if (secondLevel4.getRelative(right).getType() == Material.STAINED_GLASS_PANE) {
																																														if (secondLevel4.getRelative(right).getRelative(right).getType() == Material.STAINED_CLAY) {
																																															if (secondLevel4.getRelative(left).getType() == Material.STAINED_GLASS_PANE) {
																																																if (secondLevel4.getRelative(left).getRelative(left).getType() == Material.STAINED_CLAY) {

																																																	if (thirdLevel.getType() == Material.STAINED_GLASS_PANE) {
																																																		if (thirdLevel.getRelative(right).getType() == Material.STAINED_GLASS_PANE) {
																																																			if (thirdLevel.getRelative(right).getRelative(right).getType() == Material.STAINED_CLAY) {
																																																				if (thirdLevel.getRelative(left).getType() == Material.STAINED_GLASS_PANE) {
																																																					if (thirdLevel.getRelative(left).getRelative(left).getType() == Material.STAINED_CLAY) {
																																																						if (thirdLevel1.getType() == Material.AIR || thirdLevel1.getType() == Material.STATIONARY_LAVA || thirdLevel1.getType() == Material.STATIONARY_WATER) {
																																																							if (thirdLevel1.getRelative(right).getType() == Material.AIR || thirdLevel1.getRelative(right).getType() == Material.STATIONARY_LAVA || thirdLevel1.getRelative(right).getType() == Material.STATIONARY_WATER) {
																																																								if (thirdLevel1.getRelative(right).getRelative(right).getType() == Material.STAINED_GLASS_PANE) {
																																																									if (thirdLevel1.getRelative(left).getType() == Material.AIR || thirdLevel1.getRelative(left).getType() == Material.STATIONARY_LAVA || thirdLevel1.getRelative(left).getType() == Material.STATIONARY_WATER) {
																																																										if (thirdLevel1.getRelative(left).getRelative(left).getType() == Material.STAINED_GLASS_PANE) {
																																																											if (thirdLevel2.getType() == Material.AIR || thirdLevel2.getType() == Material.STATIONARY_LAVA || thirdLevel2.getType() == Material.STATIONARY_WATER) {
																																																												if (thirdLevel2.getRelative(right).getType() == Material.AIR || thirdLevel2.getRelative(right).getType() == Material.STATIONARY_LAVA || thirdLevel2.getRelative(right).getType() == Material.STATIONARY_WATER) {
																																																													if (thirdLevel2.getRelative(right).getRelative(right).getType() == Material.STAINED_GLASS_PANE) {
																																																														if (thirdLevel2.getRelative(left).getType() == Material.AIR || thirdLevel2.getRelative(left).getType() == Material.STATIONARY_LAVA || thirdLevel2.getRelative(left).getType() == Material.STATIONARY_WATER) {
																																																															if (thirdLevel2.getRelative(left).getRelative(left).getType() == Material.STAINED_GLASS_PANE) {
																																																																if (thirdLevel3.getType() == Material.AIR || thirdLevel3.getType() == Material.STATIONARY_LAVA || thirdLevel3.getType() == Material.STATIONARY_WATER) {
																																																																	if (thirdLevel3.getRelative(right).getType() == Material.AIR || thirdLevel3.getRelative(right).getType() == Material.STATIONARY_LAVA || thirdLevel3.getRelative(right).getType() == Material.STATIONARY_WATER) {
																																																																		if (thirdLevel3.getRelative(right).getRelative(right).getType() == Material.STAINED_GLASS_PANE) {
																																																																			if (thirdLevel3.getRelative(left).getType() == Material.AIR || thirdLevel3.getRelative(left).getType() == Material.STATIONARY_LAVA || thirdLevel3.getRelative(left).getType() == Material.STATIONARY_WATER) {
																																																																				if (thirdLevel3.getRelative(left).getRelative(left).getType() == Material.STAINED_GLASS_PANE) {
																																																																					if (thirdLevel4.getType() == Material.STAINED_GLASS_PANE) {
																																																																						if (thirdLevel4.getRelative(right).getType() == Material.STAINED_GLASS_PANE) {
																																																																							if (thirdLevel4.getRelative(right).getRelative(right).getType() == Material.STAINED_CLAY) {
																																																																								if (thirdLevel4.getRelative(left).getType() == Material.STAINED_GLASS_PANE) {
																																																																									if (thirdLevel4.getRelative(left).getRelative(left).getType() == Material.STAINED_CLAY) {

																																																																										if (fourthLevel.getType() == Material.STAINED_GLASS_PANE) {
																																																																											if (fourthLevel.getRelative(right).getType() == Material.STAINED_GLASS_PANE) {
																																																																												if (fourthLevel.getRelative(right).getRelative(right).getType() == Material.STAINED_CLAY) {
																																																																													if (fourthLevel.getRelative(left).getType() == Material.STAINED_GLASS_PANE) {
																																																																														if (fourthLevel.getRelative(left).getRelative(left).getType() == Material.STAINED_CLAY) {
																																																																															if (fourthLevel1.getType() == Material.AIR || fourthLevel1.getType() == Material.STATIONARY_LAVA || fourthLevel1.getType() == Material.STATIONARY_WATER) {
																																																																																if (fourthLevel1.getRelative(right).getType() == Material.AIR || fourthLevel1.getRelative(right).getType() == Material.STATIONARY_LAVA || fourthLevel1.getRelative(right).getType() == Material.STATIONARY_WATER) {
																																																																																	if (fourthLevel1.getRelative(right).getRelative(right).getType() == Material.STAINED_GLASS_PANE) {
																																																																																		if (fourthLevel1.getRelative(left).getType() == Material.AIR || fourthLevel1.getRelative(left).getType() == Material.STATIONARY_LAVA || fourthLevel1.getRelative(left).getType() == Material.STATIONARY_WATER) {
																																																																																			if (fourthLevel1.getRelative(left).getRelative(left).getType() == Material.STAINED_GLASS_PANE) {
																																																																																				if (fourthLevel2.getType() == Material.AIR || fourthLevel2.getType() == Material.STATIONARY_LAVA || fourthLevel2.getType() == Material.STATIONARY_WATER) {
																																																																																					if (fourthLevel2.getRelative(right).getType() == Material.AIR || fourthLevel2.getRelative(right).getType() == Material.STATIONARY_LAVA || fourthLevel2.getRelative(right).getType() == Material.STATIONARY_WATER) {
																																																																																						if (fourthLevel2.getRelative(right).getRelative(right).getType() == Material.STAINED_GLASS_PANE) {
																																																																																							if (fourthLevel2.getRelative(left).getType() == Material.AIR || fourthLevel2.getRelative(left).getType() == Material.STATIONARY_LAVA || fourthLevel2.getRelative(left).getType() == Material.STATIONARY_WATER) {
																																																																																								if (fourthLevel2.getRelative(left).getRelative(left).getType() == Material.STAINED_GLASS_PANE) {
																																																																																									if (fourthLevel3.getType() == Material.AIR || fourthLevel3.getType() == Material.STATIONARY_LAVA || fourthLevel3.getType() == Material.STATIONARY_WATER) {
																																																																																										if (fourthLevel3.getRelative(right).getType() == Material.AIR || fourthLevel3.getRelative(right).getType() == Material.STATIONARY_LAVA || fourthLevel3.getRelative(right).getType() == Material.STATIONARY_WATER) {
																																																																																											if (fourthLevel3.getRelative(right).getRelative(right).getType() == Material.STAINED_GLASS_PANE) {
																																																																																												if (fourthLevel3.getRelative(left).getType() == Material.AIR || fourthLevel3.getRelative(left).getType() == Material.STATIONARY_LAVA || fourthLevel3.getRelative(left).getType() == Material.STATIONARY_WATER) {
																																																																																													if (fourthLevel3.getRelative(left).getRelative(left).getType() == Material.STAINED_GLASS_PANE) {
																																																																																														if (fourthLevel4.getType() == Material.STAINED_GLASS_PANE) {
																																																																																															if (fourthLevel4.getRelative(right).getType() == Material.STAINED_GLASS_PANE) {
																																																																																																if (fourthLevel4.getRelative(right).getRelative(right).getType() == Material.STAINED_CLAY) {
																																																																																																	if (fourthLevel4.getRelative(left).getType() == Material.STAINED_GLASS_PANE) {
																																																																																																		if (fourthLevel4.getRelative(left).getRelative(left).getType() == Material.STAINED_CLAY) {

																																																																																																			if (fifthLevel.getType() == Material.STAINED_GLASS_PANE) {
																																																																																																				if (fifthLevel.getRelative(right).getType() == Material.STAINED_GLASS_PANE) {
																																																																																																					if (fifthLevel.getRelative(right).getRelative(right).getType() == Material.STAINED_CLAY) {
																																																																																																						if (fifthLevel.getRelative(left).getType() == Material.STAINED_GLASS_PANE) {
																																																																																																							if (fifthLevel.getRelative(left).getRelative(left).getType() == Material.STAINED_CLAY) {
																																																																																																								if (fifthLevel1.getType() == Material.AIR || fifthLevel1.getType() == Material.STATIONARY_LAVA || fifthLevel1.getType() == Material.STATIONARY_WATER) {
																																																																																																									if (fifthLevel1.getRelative(right).getType() == Material.AIR || fifthLevel1.getRelative(right).getType() == Material.STATIONARY_LAVA || fifthLevel1.getRelative(right).getType() == Material.STATIONARY_WATER) {
																																																																																																										if (fifthLevel1.getRelative(right).getRelative(right).getType() == Material.STAINED_GLASS_PANE) {
																																																																																																											if (fifthLevel1.getRelative(left).getType() == Material.AIR || fifthLevel1.getRelative(left).getType() == Material.STATIONARY_LAVA || fifthLevel1.getRelative(left).getType() == Material.STATIONARY_WATER) {
																																																																																																												if (fifthLevel1.getRelative(left).getRelative(left).getType() == Material.STAINED_GLASS_PANE) {
																																																																																																													if (fifthLevel2.getType() == Material.AIR || fifthLevel2.getType() == Material.STATIONARY_LAVA || fifthLevel2.getType() == Material.STATIONARY_WATER) {
																																																																																																														if (fifthLevel2.getRelative(right).getType() == Material.AIR || fifthLevel2.getRelative(right).getType() == Material.STATIONARY_LAVA || fifthLevel2.getRelative(right).getType() == Material.STATIONARY_WATER) {
																																																																																																															if (fifthLevel2.getRelative(right).getRelative(right).getType() == Material.STAINED_GLASS_PANE) {
																																																																																																																if (fifthLevel2.getRelative(left).getType() == Material.AIR || fifthLevel2.getRelative(left).getType() == Material.STATIONARY_LAVA || fifthLevel2.getRelative(left).getType() == Material.STATIONARY_WATER) {
																																																																																																																	if (fifthLevel2.getRelative(left).getRelative(left).getType() == Material.STAINED_GLASS_PANE) {
																																																																																																																		if (fifthLevel3.getType() == Material.AIR || fifthLevel3.getType() == Material.STATIONARY_LAVA || fifthLevel3.getType() == Material.STATIONARY_WATER) {
																																																																																																																			if (fifthLevel3.getRelative(right).getType() == Material.AIR || fifthLevel3.getRelative(right).getType() == Material.STATIONARY_LAVA || fifthLevel3.getRelative(right).getType() == Material.STATIONARY_WATER) {
																																																																																																																				if (fifthLevel3.getRelative(right).getRelative(right).getType() == Material.STAINED_GLASS_PANE) {
																																																																																																																					if (fifthLevel3.getRelative(left).getType() == Material.AIR || fifthLevel3.getRelative(left).getType() == Material.STATIONARY_LAVA || fifthLevel3.getRelative(left).getType() == Material.STATIONARY_WATER) {
																																																																																																																						if (fifthLevel3.getRelative(left).getRelative(left).getType() == Material.STAINED_GLASS_PANE) {
																																																																																																																							if (fifthLevel4.getType() == Material.STAINED_GLASS_PANE) {
																																																																																																																								if (fifthLevel4.getRelative(right).getType() == Material.STAINED_GLASS_PANE) {
																																																																																																																									if (fifthLevel4.getRelative(right).getRelative(right).getType() == Material.STAINED_CLAY) {
																																																																																																																										if (fifthLevel4.getRelative(left).getType() == Material.STAINED_GLASS_PANE) {
																																																																																																																											if (fifthLevel4.getRelative(left).getRelative(left).getType() == Material.STAINED_CLAY) {

																																																																																																																												if (sixthLevel.getType() == Material.STAINED_CLAY) {
																																																																																																																													if (sixthLevel.getRelative(right).getType() == Material.STAINED_CLAY) {
																																																																																																																														if (sixthLevel.getRelative(left).getType() == Material.STAINED_CLAY) {
																																																																																																																															if (sixthLevel1.getType() == Material.STAINED_CLAY) {
																																																																																																																																if (sixthLevel1.getRelative(left).getType() == Material.STAINED_CLAY) {
																																																																																																																																	if (sixthLevel1.getRelative(left).getRelative(left).getType() == Material.STAINED_CLAY) {
																																																																																																																																		if (sixthLevel1.getRelative(right).getType() == Material.STAINED_CLAY) {
																																																																																																																																			if (sixthLevel1.getRelative(right).getRelative(right).getType() == Material.STAINED_CLAY) {
																																																																																																																																				if (sixthLevel2.getType() == Material.STAINED_CLAY) {
																																																																																																																																					if (sixthLevel2.getRelative(left).getType() == Material.STAINED_CLAY) {
																																																																																																																																						if (sixthLevel2.getRelative(left).getRelative(left).getType() == Material.STAINED_CLAY) {
																																																																																																																																							if (sixthLevel2.getRelative(right).getType() == Material.STAINED_CLAY) {
																																																																																																																																								if (sixthLevel2.getRelative(right).getRelative(right).getType() == Material.STAINED_CLAY) {
																																																																																																																																									if (sixthLevel3.getType() == Material.STAINED_CLAY) {
																																																																																																																																										if (sixthLevel3.getRelative(left).getType() == Material.STAINED_CLAY) {
																																																																																																																																											if (sixthLevel3.getRelative(left).getRelative(left).getType() == Material.STAINED_CLAY) {
																																																																																																																																												if (sixthLevel3.getRelative(right).getType() == Material.STAINED_CLAY) {
																																																																																																																																													if (sixthLevel3.getRelative(right).getRelative(right).getType() == Material.STAINED_CLAY) {
																																																																																																																																														if (sixthLevel4.getType() == Material.STAINED_CLAY) {
																																																																																																																																															if (sixthLevel4.getRelative(right).getType() == Material.STAINED_CLAY) {
																																																																																																																																																if (sixthLevel4.getRelative(left).getType() == Material.STAINED_CLAY) {

																																																																																																																																																	return firstLevel2.getRelative(BlockFace.UP);

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


}
