package com.sqtechenergy.objects;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

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
import com.starquestminecraft.sqtechenergy.gui.SolarPanelGUI;

import net.md_5.bungee.api.ChatColor;

public class SolarPanel extends MachineType {

	public SolarPanel() {

		super(SQTechEnergy.config.getInt("solar panel.max energy"));
		name = "Solar Panel";

		defaultExport = true;

	}

	@Override
	public boolean detectStructure(GUIBlock guiBlock) {

		Block block = guiBlock.getLocation().getBlock();

		Block panel = block.getRelative(BlockFace.UP);

		List<BlockFace> faces = new ArrayList<BlockFace>();

		faces.add(BlockFace.EAST);
		faces.add(BlockFace.SOUTH);
		faces.add(BlockFace.NORTH);
		faces.add(BlockFace.WEST);

		for (BlockFace f : faces) {

			BlockFace right = DirectionUtils.getRight(f);
			BlockFace left = DirectionUtils.getLeft(f);

			if (block.getType() == Material.LAPIS_BLOCK) {
				if (block.getRelative(right).getType() == Material.STAINED_CLAY) {
					if (block.getRelative(left).getType() == Material.STAINED_CLAY) {
						if (block.getRelative(f).getType() == Material.STAINED_CLAY) {
							if (block.getRelative(f).getRelative(right).getType() == Material.STAINED_CLAY) {
								if (block.getRelative(f).getRelative(left).getType() == Material.STAINED_CLAY) {
									if (block.getRelative(f).getRelative(f).getType() == Material.STAINED_CLAY) {
										if (block.getRelative(f).getRelative(f).getRelative(right).getType() == Material.STAINED_CLAY) {
											if (block.getRelative(f).getRelative(f).getRelative(left).getType() == Material.STAINED_CLAY) {
												
												if (panel.getType() == Material.DAYLIGHT_DETECTOR) {
													if (panel.getRelative(right).getType() == Material.DAYLIGHT_DETECTOR) {
														if (panel.getRelative(left).getType() == Material.DAYLIGHT_DETECTOR) {
															if (panel.getRelative(f).getType() == Material.DAYLIGHT_DETECTOR) {
																if (panel.getRelative(f).getRelative(right).getType() == Material.DAYLIGHT_DETECTOR) {
																	if (panel.getRelative(f).getRelative(left).getType() == Material.DAYLIGHT_DETECTOR) {
																		if (panel.getRelative(f).getRelative(f).getType() == Material.DAYLIGHT_DETECTOR) {
																			if (panel.getRelative(f).getRelative(f).getRelative(right).getType() == Material.DAYLIGHT_DETECTOR) {
																				if (panel.getRelative(f).getRelative(f).getRelative(left).getType() == Material.DAYLIGHT_DETECTOR) {
																					
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

		return false;

	}
	
	public static BlockFace getFace(GUIBlock guiBlock) {

		Block block = guiBlock.getLocation().getBlock();

		Block panel = block.getRelative(BlockFace.UP);

		List<BlockFace> faces = new ArrayList<BlockFace>();

		faces.add(BlockFace.EAST);
		faces.add(BlockFace.SOUTH);
		faces.add(BlockFace.NORTH);
		faces.add(BlockFace.WEST);

		for (BlockFace f : faces) {

			BlockFace right = DirectionUtils.getRight(f);
			BlockFace left = DirectionUtils.getLeft(f);

			if (block.getType() == Material.LAPIS_BLOCK) {
				if (block.getRelative(right).getType() == Material.STAINED_CLAY) {
					if (block.getRelative(left).getType() == Material.STAINED_CLAY) {
						if (block.getRelative(f).getType() == Material.STAINED_CLAY) {
							if (block.getRelative(f).getRelative(right).getType() == Material.STAINED_CLAY) {
								if (block.getRelative(f).getRelative(left).getType() == Material.STAINED_CLAY) {
									if (block.getRelative(f).getRelative(f).getType() == Material.STAINED_CLAY) {
										if (block.getRelative(f).getRelative(f).getRelative(right).getType() == Material.STAINED_CLAY) {
											if (block.getRelative(f).getRelative(f).getRelative(left).getType() == Material.STAINED_CLAY) {
												
												if (panel.getType() == Material.DAYLIGHT_DETECTOR) {
													if (panel.getRelative(right).getType() == Material.DAYLIGHT_DETECTOR) {
														if (panel.getRelative(left).getType() == Material.DAYLIGHT_DETECTOR) {
															if (panel.getRelative(f).getType() == Material.DAYLIGHT_DETECTOR) {
																if (panel.getRelative(f).getRelative(right).getType() == Material.DAYLIGHT_DETECTOR) {
																	if (panel.getRelative(f).getRelative(left).getType() == Material.DAYLIGHT_DETECTOR) {
																		if (panel.getRelative(f).getRelative(f).getType() == Material.DAYLIGHT_DETECTOR) {
																			if (panel.getRelative(f).getRelative(f).getRelative(right).getType() == Material.DAYLIGHT_DETECTOR) {
																				if (panel.getRelative(f).getRelative(f).getRelative(left).getType() == Material.DAYLIGHT_DETECTOR) {
																					
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

		return null;

	}
	
	@Override
	public GUI getGUI(Player player, int id) {
		
		return new SolarPanelGUI(player, id);
		
	}
	
	@Override
	public void updateEnergy(Machine machine) {

		for (Player player : SQTechBase.currentGui.keySet()) {

			if (SQTechBase.currentGui.get(player).id == machine.getGUIBlock().id) {

				if (player.getOpenInventory() != null) {

					if (player.getOpenInventory().getTitle().equals(ChatColor.BLUE + "SQTech - Solar Panel")) {
						
						String sunlight = ChatColor.RED + "false";
						
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
								
								sunlight = ChatColor.GREEN + "true";
								
								player.getOpenInventory().setItem(4, InventoryUtils.createSpecialItem(Material.STAINED_GLASS_PANE, (short) 5, "Energy", new String[] {EnergyUtils.formatEnergy(machine.getEnergy()) + "/" + EnergyUtils.formatEnergy(machine.getMachineType().getMaxEnergy()), ChatColor.RED + "" + ChatColor.MAGIC + "Contraband"}));
								
							} else {
								player.getOpenInventory().setItem(4, InventoryUtils.createSpecialItem(Material.STAINED_GLASS_PANE, (short) 14, "Energy", new String[] {EnergyUtils.formatEnergy(machine.getEnergy()) + "/" + EnergyUtils.formatEnergy(machine.getMachineType().getMaxEnergy()), ChatColor.RED + "" + ChatColor.MAGIC + "Contraband"}));
							}
							
						}
						
						player.getOpenInventory().setItem(0, InventoryUtils.createSpecialItem(Material.PAPER, (short) 0, "Info", new String[] {
								"The solar panel needs direct",
								"access to sunlight to produce energy.",
								"Sunlight: " + sunlight,
								ChatColor.RED + "" + ChatColor.MAGIC + "Contraband"
						}));

					}

				}

			}

		}

	}

}
