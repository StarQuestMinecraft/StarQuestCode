package com.martinjonsson01.sqtechpumps.objects;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Player;

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

import net.md_5.bungee.api.ChatColor;

public class MediumTank extends MachineType{

	public MediumTank(int maxEnergy) {

		super(maxEnergy);
		name = "Medium Tank";

	}

	@Override
	public boolean detectStructure(GUIBlock block) {

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
																													if (secondLevel1.getType() == Material.AIR) {
																														if (secondLevel1.getRelative(right).getType() == Material.AIR) {
																															if (secondLevel1.getRelative(right).getRelative(right).getType() == Material.STAINED_GLASS_PANE) {
																																if (secondLevel1.getRelative(left).getType() == Material.AIR) {
																																	if (secondLevel1.getRelative(left).getRelative(left).getType() == Material.STAINED_GLASS_PANE) {
																																		if (secondLevel2.getType() == Material.AIR) {
																																			if (secondLevel2.getRelative(right).getType() == Material.AIR) {
																																				if (secondLevel2.getRelative(right).getRelative(right).getType() == Material.STAINED_GLASS_PANE) {
																																					if (secondLevel2.getRelative(left).getType() == Material.AIR) {
																																						if (secondLevel2.getRelative(left).getRelative(left).getType() == Material.STAINED_GLASS_PANE) {
																																							if (secondLevel3.getType() == Material.AIR) {
																																								if (secondLevel3.getRelative(right).getType() == Material.AIR) {
																																									if (secondLevel3.getRelative(right).getRelative(right).getType() == Material.STAINED_GLASS_PANE) {
																																										if (secondLevel3.getRelative(left).getType() == Material.AIR) {
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
																																																						if (thirdLevel1.getType() == Material.AIR) {
																																																							if (thirdLevel1.getRelative(right).getType() == Material.AIR) {
																																																								if (thirdLevel1.getRelative(right).getRelative(right).getType() == Material.STAINED_GLASS_PANE) {
																																																									if (thirdLevel1.getRelative(left).getType() == Material.AIR) {
																																																										if (thirdLevel1.getRelative(left).getRelative(left).getType() == Material.STAINED_GLASS_PANE) {
																																																											if (thirdLevel2.getType() == Material.AIR) {
																																																												if (thirdLevel2.getRelative(right).getType() == Material.AIR) {
																																																													if (thirdLevel2.getRelative(right).getRelative(right).getType() == Material.STAINED_GLASS_PANE) {
																																																														if (thirdLevel2.getRelative(left).getType() == Material.AIR) {
																																																															if (thirdLevel2.getRelative(left).getRelative(left).getType() == Material.STAINED_GLASS_PANE) {
																																																																if (thirdLevel3.getType() == Material.AIR) {
																																																																	if (thirdLevel3.getRelative(right).getType() == Material.AIR) {
																																																																		if (thirdLevel3.getRelative(right).getRelative(right).getType() == Material.STAINED_GLASS_PANE) {
																																																																			if (thirdLevel3.getRelative(left).getType() == Material.AIR) {
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
																																																																															if (fourthLevel1.getType() == Material.AIR) {
																																																																																if (fourthLevel1.getRelative(right).getType() == Material.AIR) {
																																																																																	if (fourthLevel1.getRelative(right).getRelative(right).getType() == Material.STAINED_GLASS_PANE) {
																																																																																		if (fourthLevel1.getRelative(left).getType() == Material.AIR) {
																																																																																			if (fourthLevel1.getRelative(left).getRelative(left).getType() == Material.STAINED_GLASS_PANE) {
																																																																																				if (fourthLevel2.getType() == Material.AIR) {
																																																																																					if (fourthLevel2.getRelative(right).getType() == Material.AIR) {
																																																																																						if (fourthLevel2.getRelative(right).getRelative(right).getType() == Material.STAINED_GLASS_PANE) {
																																																																																							if (fourthLevel2.getRelative(left).getType() == Material.AIR) {
																																																																																								if (fourthLevel2.getRelative(left).getRelative(left).getType() == Material.STAINED_GLASS_PANE) {
																																																																																									if (fourthLevel3.getType() == Material.AIR) {
																																																																																										if (fourthLevel3.getRelative(right).getType() == Material.AIR) {
																																																																																											if (fourthLevel3.getRelative(right).getRelative(right).getType() == Material.STAINED_GLASS_PANE) {
																																																																																												if (fourthLevel3.getRelative(left).getType() == Material.AIR) {
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
																																																																																																								if (fifthLevel1.getType() == Material.AIR) {
																																																																																																									if (fifthLevel1.getRelative(right).getType() == Material.AIR) {
																																																																																																										if (fifthLevel1.getRelative(right).getRelative(right).getType() == Material.STAINED_GLASS_PANE) {
																																																																																																											if (fifthLevel1.getRelative(left).getType() == Material.AIR) {
																																																																																																												if (fifthLevel1.getRelative(left).getRelative(left).getType() == Material.STAINED_GLASS_PANE) {
																																																																																																													if (fifthLevel2.getType() == Material.AIR) {
																																																																																																														if (fifthLevel2.getRelative(right).getType() == Material.AIR) {
																																																																																																															if (fifthLevel2.getRelative(right).getRelative(right).getType() == Material.STAINED_GLASS_PANE) {
																																																																																																																if (fifthLevel2.getRelative(left).getType() == Material.AIR) {
																																																																																																																	if (fifthLevel2.getRelative(left).getRelative(left).getType() == Material.STAINED_GLASS_PANE) {
																																																																																																																		if (fifthLevel3.getType() == Material.AIR) {
																																																																																																																			if (fifthLevel3.getRelative(right).getType() == Material.AIR) {
																																																																																																																				if (fifthLevel3.getRelative(right).getRelative(right).getType() == Material.STAINED_GLASS_PANE) {
																																																																																																																					if (fifthLevel3.getRelative(left).getType() == Material.AIR) {
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

		return false;

	}

	@Override
	public GUI getGUI(Player player, int id) {

		return new MediumTankGUI(player, id);

	}

	@Override
	public void updateLiquid(Machine machine) {

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

	}


}
