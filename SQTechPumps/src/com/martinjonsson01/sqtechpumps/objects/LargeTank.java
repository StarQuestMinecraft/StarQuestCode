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

import com.martinjonsson01.sqtechpumps.LargeTankAssets;
import com.martinjonsson01.sqtechpumps.SQTechPumps;
import com.martinjonsson01.sqtechpumps.gui.LargeTankGUI;
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

public class LargeTank extends MachineType{

	public LargeTank(int maxEnergy) {

		super(maxEnergy);
		name = "Large Tank";

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
			Block firstLevel5 = firstLevel.getRelative(f).getRelative(f).getRelative(f).getRelative(f).getRelative(f);
			Block firstLevel6 = firstLevel.getRelative(f).getRelative(f).getRelative(f).getRelative(f).getRelative(f).getRelative(f);

			Block secondLevel = firstLevel.getRelative(up);
			Block secondLevel1 = secondLevel.getRelative(f);
			Block secondLevel2 = secondLevel.getRelative(f).getRelative(f);
			Block secondLevel3 = secondLevel.getRelative(f).getRelative(f).getRelative(f);
			Block secondLevel4 = secondLevel.getRelative(f).getRelative(f).getRelative(f).getRelative(f);
			Block secondLevel5 = secondLevel.getRelative(f).getRelative(f).getRelative(f).getRelative(f).getRelative(f);
			Block secondLevel6 = secondLevel.getRelative(f).getRelative(f).getRelative(f).getRelative(f).getRelative(f).getRelative(f);

			Block thirdLevel = secondLevel.getRelative(up);
			Block thirdLevel1 = thirdLevel.getRelative(f);
			Block thirdLevel2 = thirdLevel.getRelative(f).getRelative(f);
			Block thirdLevel3 = thirdLevel.getRelative(f).getRelative(f).getRelative(f);
			Block thirdLevel4 = thirdLevel.getRelative(f).getRelative(f).getRelative(f).getRelative(f);
			Block thirdLevel5 = thirdLevel.getRelative(f).getRelative(f).getRelative(f).getRelative(f).getRelative(f);
			Block thirdLevel6 = thirdLevel.getRelative(f).getRelative(f).getRelative(f).getRelative(f).getRelative(f).getRelative(f);

			Block fourthLevel = thirdLevel.getRelative(up);
			Block fourthLevel1 = fourthLevel.getRelative(f);
			Block fourthLevel2 = fourthLevel.getRelative(f).getRelative(f);
			Block fourthLevel3 = fourthLevel.getRelative(f).getRelative(f).getRelative(f);
			Block fourthLevel4 = fourthLevel.getRelative(f).getRelative(f).getRelative(f).getRelative(f);
			Block fourthLevel5 = fourthLevel.getRelative(f).getRelative(f).getRelative(f).getRelative(f).getRelative(f);
			Block fourthLevel6 = fourthLevel.getRelative(f).getRelative(f).getRelative(f).getRelative(f).getRelative(f).getRelative(f);

			Block fifthLevel = fourthLevel.getRelative(up);
			Block fifthLevel1 = fifthLevel.getRelative(f);
			Block fifthLevel2 = fifthLevel.getRelative(f).getRelative(f);
			Block fifthLevel3 = fifthLevel.getRelative(f).getRelative(f).getRelative(f);
			Block fifthLevel4 = fifthLevel.getRelative(f).getRelative(f).getRelative(f).getRelative(f);
			Block fifthLevel5 = fifthLevel.getRelative(f).getRelative(f).getRelative(f).getRelative(f).getRelative(f);
			Block fifthLevel6 = fifthLevel.getRelative(f).getRelative(f).getRelative(f).getRelative(f).getRelative(f).getRelative(f);

			Block sixthLevel = fifthLevel.getRelative(up);
			Block sixthLevel1 = sixthLevel.getRelative(f);
			Block sixthLevel2 = sixthLevel.getRelative(f).getRelative(f);
			Block sixthLevel3 = sixthLevel.getRelative(f).getRelative(f).getRelative(f);
			Block sixthLevel4 = sixthLevel.getRelative(f).getRelative(f).getRelative(f).getRelative(f);
			Block sixthLevel5 = sixthLevel.getRelative(f).getRelative(f).getRelative(f).getRelative(f).getRelative(f);
			Block sixthLevel6 = sixthLevel.getRelative(f).getRelative(f).getRelative(f).getRelative(f).getRelative(f).getRelative(f);

			Block seventhLevel = sixthLevel.getRelative(up);
			Block seventhLevel1 = seventhLevel.getRelative(f);
			Block seventhLevel2 = seventhLevel.getRelative(f).getRelative(f);
			Block seventhLevel3 = seventhLevel.getRelative(f).getRelative(f).getRelative(f);
			Block seventhLevel4 = seventhLevel.getRelative(f).getRelative(f).getRelative(f).getRelative(f);
			Block seventhLevel5 = seventhLevel.getRelative(f).getRelative(f).getRelative(f).getRelative(f).getRelative(f);
			Block seventhLevel6 = seventhLevel.getRelative(f).getRelative(f).getRelative(f).getRelative(f).getRelative(f).getRelative(f);

			Block eightLevel = seventhLevel.getRelative(up);
			Block eightLevel1 = eightLevel.getRelative(f);
			Block eightLevel2 = eightLevel.getRelative(f).getRelative(f);
			Block eightLevel3 = eightLevel.getRelative(f).getRelative(f).getRelative(f);
			Block eightLevel4 = eightLevel.getRelative(f).getRelative(f).getRelative(f).getRelative(f);
			Block eightLevel5 = eightLevel.getRelative(f).getRelative(f).getRelative(f).getRelative(f).getRelative(f);
			Block eightLevel6 = eightLevel.getRelative(f).getRelative(f).getRelative(f).getRelative(f).getRelative(f).getRelative(f);

			Block ninthLevel = eightLevel.getRelative(up);
			Block ninthLevel1 = ninthLevel.getRelative(f);
			Block ninthLevel2 = ninthLevel.getRelative(f).getRelative(f);
			Block ninthLevel3 = ninthLevel.getRelative(f).getRelative(f).getRelative(f);
			Block ninthLevel4 = ninthLevel.getRelative(f).getRelative(f).getRelative(f).getRelative(f);
			Block ninthLevel5 = ninthLevel.getRelative(f).getRelative(f).getRelative(f).getRelative(f).getRelative(f);
			Block ninthLevel6 = ninthLevel.getRelative(f).getRelative(f).getRelative(f).getRelative(f).getRelative(f).getRelative(f);

			if (firstLevel.getType() == Material.LAPIS_BLOCK) {
				if (firstLevel.getRelative(right).getType() == Material.STAINED_CLAY) {
					if (firstLevel.getRelative(left).getType() == Material.STAINED_CLAY) {
						if (firstLevel1.getType() == Material.STAINED_CLAY) {
							if (firstLevel1.getRelative(right).getType() == Material.STAINED_CLAY) {
								if (firstLevel1.getRelative(right).getRelative(right).getType() == Material.STAINED_CLAY) {
									if (firstLevel1.getRelative(left).getType() == Material.STAINED_CLAY) {
										if (firstLevel1.getRelative(left).getRelative(left).getType() == Material.STAINED_CLAY) {
											if (firstLevel2.getType() == Material.STAINED_CLAY) {
												if (firstLevel2.getRelative(right).getType() == Material.STAINED_CLAY) {
													if (firstLevel2.getRelative(right).getRelative(right).getType() == Material.STAINED_CLAY) {
														if (firstLevel2.getRelative(right).getRelative(right).getRelative(right).getType() == Material.STAINED_CLAY) {
															if (firstLevel2.getRelative(left).getType() == Material.STAINED_CLAY) {
																if (firstLevel2.getRelative(left).getRelative(left).getType() == Material.STAINED_CLAY) {
																	if (firstLevel2.getRelative(left).getRelative(left).getRelative(left).getType() == Material.STAINED_CLAY) {
																		if (firstLevel3.getType() == Material.STAINED_CLAY) {
																			if (firstLevel3.getRelative(right).getType() == Material.STAINED_CLAY) {
																				if (firstLevel3.getRelative(right).getRelative(right).getType() == Material.STAINED_CLAY) {
																					if (firstLevel3.getRelative(right).getRelative(right).getRelative(right).getType() == Material.STAINED_CLAY) {
																						if (firstLevel3.getRelative(left).getType() == Material.STAINED_CLAY) {
																							if (firstLevel3.getRelative(left).getRelative(left).getType() == Material.STAINED_CLAY) {
																								if (firstLevel3.getRelative(left).getRelative(left).getRelative(left).getType() == Material.STAINED_CLAY) {
																									if (firstLevel4.getType() == Material.STAINED_CLAY) {
																										if (firstLevel4.getRelative(right).getType() == Material.STAINED_CLAY) {
																											if (firstLevel4.getRelative(right).getRelative(right).getType() == Material.STAINED_CLAY) {
																												if (firstLevel4.getRelative(right).getRelative(right).getRelative(right).getType() == Material.STAINED_CLAY) {
																													if (firstLevel4.getRelative(left).getType() == Material.STAINED_CLAY) {
																														if (firstLevel4.getRelative(left).getRelative(left).getType() == Material.STAINED_CLAY) {
																															if (firstLevel4.getRelative(left).getRelative(left).getRelative(left).getType() == Material.STAINED_CLAY) {
																																if (firstLevel5.getType() == Material.STAINED_CLAY) {
																																	if (firstLevel5.getRelative(right).getType() == Material.STAINED_CLAY) {
																																		if (firstLevel5.getRelative(right).getRelative(right).getType() == Material.STAINED_CLAY) {
																																			if (firstLevel5.getRelative(left).getType() == Material.STAINED_CLAY) {
																																				if (firstLevel5.getRelative(left).getRelative(left).getType() == Material.STAINED_CLAY) {
																																					if (firstLevel6.getType() == Material.STAINED_CLAY) {
																																						if (firstLevel6.getRelative(right).getType() == Material.STAINED_CLAY) {
																																							if (firstLevel6.getRelative(left).getType() == Material.STAINED_CLAY) {

																																								if (secondLevel.getType() == Material.STAINED_GLASS_PANE) {
																																									if (secondLevel.getRelative(right).getType() == Material.STAINED_GLASS_PANE) {
																																										if (secondLevel.getRelative(right).getRelative(right).getType() == Material.STAINED_CLAY) {
																																											if (secondLevel.getRelative(left).getType() == Material.STAINED_GLASS_PANE) {
																																												if (secondLevel.getRelative(left).getRelative(left).getType() == Material.STAINED_CLAY) {
																																													if (secondLevel1.getType() == Material.AIR || secondLevel1.getType() == Material.STATIONARY_LAVA || secondLevel1.getType() == Material.STATIONARY_WATER) {
																																														if (secondLevel1.getRelative(right).getType() == Material.AIR || secondLevel1.getRelative(right).getType() == Material.STATIONARY_LAVA || secondLevel1.getRelative(right).getType() == Material.STATIONARY_WATER) {
																																															if (secondLevel1.getRelative(right).getRelative(right).getType() == Material.AIR || secondLevel1.getRelative(right).getRelative(right).getType() == Material.STATIONARY_LAVA || secondLevel1.getRelative(right).getRelative(right).getType() == Material.STATIONARY_WATER) {
																																																if (secondLevel1.getRelative(right).getRelative(right).getRelative(right).getType() == Material.STAINED_CLAY) {
																																																	if (secondLevel1.getRelative(left).getType() == Material.AIR || secondLevel1.getRelative(left).getType() == Material.STATIONARY_LAVA || secondLevel1.getRelative(left).getType() == Material.STATIONARY_WATER) {
																																																		if (secondLevel1.getRelative(left).getRelative(left).getType() == Material.AIR || secondLevel1.getRelative(left).getRelative(left).getType() == Material.STATIONARY_LAVA || secondLevel1.getRelative(left).getRelative(left).getType() == Material.STATIONARY_WATER) {
																																																			if (secondLevel1.getRelative(left).getRelative(left).getRelative(left).getType() == Material.STAINED_CLAY) {
																																																				if (secondLevel2.getType() == Material.AIR || secondLevel2.getType() == Material.STATIONARY_LAVA || secondLevel2.getType() == Material.STATIONARY_WATER) {
																																																					if (secondLevel2.getRelative(right).getType() == Material.AIR || secondLevel2.getRelative(right).getType() == Material.STATIONARY_LAVA || secondLevel2.getRelative(right).getType() == Material.STATIONARY_WATER) {
																																																						if (secondLevel2.getRelative(right).getRelative(right).getType() == Material.AIR || secondLevel2.getRelative(right).getRelative(right).getType() == Material.STATIONARY_LAVA || secondLevel2.getRelative(right).getRelative(right).getType() == Material.STATIONARY_WATER) {
																																																							if (secondLevel2.getRelative(right).getRelative(right).getRelative(right).getType() == Material.STAINED_GLASS_PANE) {
																																																								if (secondLevel2.getRelative(left).getType() == Material.AIR || secondLevel2.getRelative(left).getType() == Material.STATIONARY_LAVA || secondLevel2.getRelative(left).getType() == Material.STATIONARY_WATER) {
																																																									if (secondLevel2.getRelative(left).getRelative(left).getType() == Material.AIR || secondLevel2.getRelative(left).getRelative(left).getType() == Material.STATIONARY_LAVA || secondLevel2.getRelative(left).getRelative(left).getType() == Material.STATIONARY_WATER) {
																																																										if (secondLevel2.getRelative(left).getRelative(left).getRelative(left).getType() == Material.STAINED_GLASS_PANE) {
																																																											if (secondLevel3.getType() == Material.AIR || secondLevel3.getType() == Material.STATIONARY_LAVA || secondLevel3.getType() == Material.STATIONARY_WATER) {
																																																												if (secondLevel3.getRelative(right).getType() == Material.AIR || secondLevel3.getRelative(right).getType() == Material.STATIONARY_LAVA || secondLevel3.getRelative(right).getType() == Material.STATIONARY_WATER) {
																																																													if (secondLevel3.getRelative(right).getRelative(right).getType() == Material.AIR || secondLevel3.getRelative(right).getRelative(right).getType() == Material.STATIONARY_LAVA || secondLevel3.getRelative(right).getRelative(right).getType() == Material.STATIONARY_WATER) {
																																																														if (secondLevel3.getRelative(right).getRelative(right).getRelative(right).getType() == Material.STAINED_GLASS_PANE) {
																																																															if (secondLevel3.getRelative(left).getType() == Material.AIR || secondLevel3.getRelative(left).getType() == Material.STATIONARY_LAVA || secondLevel3.getRelative(left).getType() == Material.STATIONARY_WATER) {
																																																																if (secondLevel3.getRelative(left).getRelative(left).getType() == Material.AIR || secondLevel3.getRelative(left).getRelative(left).getType() == Material.STATIONARY_LAVA || secondLevel3.getRelative(left).getRelative(left).getType() == Material.STATIONARY_WATER) {
																																																																	if (secondLevel3.getRelative(left).getRelative(left).getRelative(left).getType() == Material.STAINED_GLASS_PANE) {
																																																																		if (secondLevel4.getType() == Material.AIR || secondLevel4.getType() == Material.STATIONARY_LAVA || secondLevel4.getType() == Material.STATIONARY_WATER) {
																																																																			if (secondLevel4.getRelative(right).getType() == Material.AIR || secondLevel4.getRelative(right).getType() == Material.STATIONARY_LAVA || secondLevel4.getRelative(right).getType() == Material.STATIONARY_WATER) {
																																																																				if (secondLevel4.getRelative(right).getRelative(right).getType() == Material.AIR || secondLevel4.getRelative(right).getRelative(right).getType() == Material.STATIONARY_LAVA || secondLevel4.getRelative(right).getRelative(right).getType() == Material.STATIONARY_WATER) {
																																																																					if (secondLevel4.getRelative(right).getRelative(right).getRelative(right).getType() == Material.STAINED_GLASS_PANE) {
																																																																						if (secondLevel4.getRelative(left).getType() == Material.AIR || secondLevel4.getRelative(left).getType() == Material.STATIONARY_LAVA || secondLevel4.getRelative(left).getType() == Material.STATIONARY_WATER) {
																																																																							if (secondLevel4.getRelative(left).getRelative(left).getType() == Material.AIR || secondLevel4.getRelative(left).getRelative(left).getType() == Material.STATIONARY_LAVA || secondLevel4.getRelative(left).getRelative(left).getType() == Material.STATIONARY_WATER) {
																																																																								if (secondLevel4.getRelative(left).getRelative(left).getRelative(left).getType() == Material.STAINED_GLASS_PANE) {
																																																																									if (secondLevel5.getType() == Material.AIR || secondLevel5.getType() == Material.STATIONARY_LAVA || secondLevel5.getType() == Material.STATIONARY_WATER) {
																																																																										if (secondLevel5.getRelative(right).getType() == Material.AIR || secondLevel5.getRelative(right).getType() == Material.STATIONARY_LAVA || secondLevel5.getRelative(right).getType() == Material.STATIONARY_WATER) {
																																																																											if (secondLevel5.getRelative(right).getRelative(right).getType() == Material.AIR || secondLevel5.getRelative(right).getRelative(right).getType() == Material.STATIONARY_LAVA || secondLevel5.getRelative(right).getRelative(right).getType() == Material.STATIONARY_WATER) {
																																																																												if (secondLevel5.getRelative(right).getRelative(right).getRelative(right).getType() == Material.STAINED_CLAY) {
																																																																													if (secondLevel5.getRelative(left).getType() == Material.AIR || secondLevel5.getRelative(left).getType() == Material.STATIONARY_LAVA || secondLevel5.getRelative(left).getType() == Material.STATIONARY_WATER) {
																																																																														if (secondLevel5.getRelative(left).getRelative(left).getType() == Material.AIR || secondLevel5.getRelative(left).getRelative(left).getType() == Material.STATIONARY_LAVA || secondLevel5.getRelative(left).getRelative(left).getType() == Material.STATIONARY_WATER) {
																																																																															if (secondLevel5.getRelative(left).getRelative(left).getRelative(left).getType() == Material.STAINED_CLAY) {
																																																																																if (secondLevel6.getType() == Material.STAINED_GLASS_PANE) {
																																																																																	if (secondLevel6.getRelative(left).getType() == Material.STAINED_GLASS_PANE) {
																																																																																		if (secondLevel6.getRelative(left).getRelative(left).getType() == Material.STAINED_CLAY) {
																																																																																			if (secondLevel6.getRelative(right).getType() == Material.STAINED_GLASS_PANE) {
																																																																																				if (secondLevel6.getRelative(right).getRelative(right).getType() == Material.STAINED_CLAY) {

																																																																																					if (thirdLevel.getType() == Material.STAINED_GLASS_PANE) {
																																																																																						if (thirdLevel.getRelative(right).getType() == Material.STAINED_GLASS_PANE) {
																																																																																							if (thirdLevel.getRelative(right).getRelative(right).getType() == Material.STAINED_CLAY) {
																																																																																								if (thirdLevel.getRelative(left).getType() == Material.STAINED_GLASS_PANE) {
																																																																																									if (thirdLevel.getRelative(left).getRelative(left).getType() == Material.STAINED_CLAY) {
																																																																																										if (thirdLevel1.getType() == Material.AIR || thirdLevel1.getType() == Material.STATIONARY_LAVA || thirdLevel1.getType() == Material.STATIONARY_WATER) {
																																																																																											if (thirdLevel1.getRelative(right).getType() == Material.AIR || thirdLevel1.getRelative(right).getType() == Material.STATIONARY_LAVA || thirdLevel1.getRelative(right).getType() == Material.STATIONARY_WATER) {
																																																																																												if (thirdLevel1.getRelative(right).getRelative(right).getType() == Material.AIR || thirdLevel1.getRelative(right).getRelative(right).getType() == Material.STATIONARY_LAVA || thirdLevel1.getRelative(right).getRelative(right).getType() == Material.STATIONARY_WATER) {
																																																																																													if (thirdLevel1.getRelative(right).getRelative(right).getRelative(right).getType() == Material.STAINED_CLAY) {
																																																																																														if (thirdLevel1.getRelative(left).getType() == Material.AIR || thirdLevel1.getRelative(left).getType() == Material.STATIONARY_LAVA || thirdLevel1.getRelative(left).getType() == Material.STATIONARY_WATER) {
																																																																																															if (thirdLevel1.getRelative(left).getRelative(left).getType() == Material.AIR || thirdLevel1.getRelative(left).getRelative(left).getType() == Material.STATIONARY_LAVA || thirdLevel1.getRelative(left).getRelative(left).getType() == Material.STATIONARY_WATER) {
																																																																																																if (thirdLevel1.getRelative(left).getRelative(left).getRelative(left).getType() == Material.STAINED_CLAY) {
																																																																																																	if (thirdLevel2.getType() == Material.AIR || thirdLevel2.getType() == Material.STATIONARY_LAVA || thirdLevel2.getType() == Material.STATIONARY_WATER) {
																																																																																																		if (thirdLevel2.getRelative(right).getType() == Material.AIR || thirdLevel2.getRelative(right).getType() == Material.STATIONARY_LAVA || thirdLevel2.getRelative(right).getType() == Material.STATIONARY_WATER) {
																																																																																																			if (thirdLevel2.getRelative(right).getRelative(right).getType() == Material.AIR || thirdLevel2.getRelative(right).getRelative(right).getType() == Material.STATIONARY_LAVA || thirdLevel2.getRelative(right).getRelative(right).getType() == Material.STATIONARY_WATER) {
																																																																																																				if (thirdLevel2.getRelative(right).getRelative(right).getRelative(right).getType() == Material.STAINED_GLASS_PANE) {
																																																																																																					if (thirdLevel2.getRelative(left).getType() == Material.AIR || thirdLevel2.getRelative(left).getType() == Material.STATIONARY_LAVA || thirdLevel2.getRelative(left).getType() == Material.STATIONARY_WATER) {
																																																																																																						if (thirdLevel2.getRelative(left).getRelative(left).getType() == Material.AIR || thirdLevel2.getRelative(left).getRelative(left).getType() == Material.STATIONARY_LAVA || thirdLevel2.getRelative(left).getRelative(left).getType() == Material.STATIONARY_WATER) {
																																																																																																							if (thirdLevel2.getRelative(left).getRelative(left).getRelative(left).getType() == Material.STAINED_GLASS_PANE) {
																																																																																																								if (thirdLevel3.getType() == Material.AIR || thirdLevel3.getType() == Material.STATIONARY_LAVA || thirdLevel3.getType() == Material.STATIONARY_WATER) {
																																																																																																									if (thirdLevel3.getRelative(right).getType() == Material.AIR || thirdLevel3.getRelative(right).getType() == Material.STATIONARY_LAVA || thirdLevel3.getRelative(right).getType() == Material.STATIONARY_WATER) {
																																																																																																										if (thirdLevel3.getRelative(right).getRelative(right).getType() == Material.AIR || thirdLevel3.getRelative(right).getRelative(right).getType() == Material.STATIONARY_LAVA || thirdLevel3.getRelative(right).getRelative(right).getType() == Material.STATIONARY_WATER) {
																																																																																																											if (thirdLevel3.getRelative(right).getRelative(right).getRelative(right).getType() == Material.STAINED_GLASS_PANE) {
																																																																																																												if (thirdLevel3.getRelative(left).getType() == Material.AIR || thirdLevel3.getRelative(left).getType() == Material.STATIONARY_LAVA || thirdLevel3.getRelative(left).getType() == Material.STATIONARY_WATER) {
																																																																																																													if (thirdLevel3.getRelative(left).getRelative(left).getType() == Material.AIR || thirdLevel3.getRelative(left).getRelative(left).getType() == Material.STATIONARY_LAVA || thirdLevel3.getRelative(left).getRelative(left).getType() == Material.STATIONARY_WATER) {
																																																																																																														if (thirdLevel3.getRelative(left).getRelative(left).getRelative(left).getType() == Material.STAINED_GLASS_PANE) {
																																																																																																															if (thirdLevel4.getType() == Material.AIR || thirdLevel4.getType() == Material.STATIONARY_LAVA || thirdLevel4.getType() == Material.STATIONARY_WATER) {
																																																																																																																if (thirdLevel4.getRelative(right).getType() == Material.AIR || thirdLevel4.getRelative(right).getType() == Material.STATIONARY_LAVA || thirdLevel4.getRelative(right).getType() == Material.STATIONARY_WATER) {
																																																																																																																	if (thirdLevel4.getRelative(right).getRelative(right).getType() == Material.AIR || thirdLevel4.getRelative(right).getRelative(right).getType() == Material.STATIONARY_LAVA || thirdLevel4.getRelative(right).getRelative(right).getType() == Material.STATIONARY_WATER) {
																																																																																																																		if (thirdLevel4.getRelative(right).getRelative(right).getRelative(right).getType() == Material.STAINED_GLASS_PANE) {
																																																																																																																			if (thirdLevel4.getRelative(left).getType() == Material.AIR || thirdLevel4.getRelative(left).getType() == Material.STATIONARY_LAVA || thirdLevel4.getRelative(left).getType() == Material.STATIONARY_WATER) {
																																																																																																																				if (thirdLevel4.getRelative(left).getRelative(left).getType() == Material.AIR || thirdLevel4.getRelative(left).getRelative(left).getType() == Material.STATIONARY_LAVA || thirdLevel4.getRelative(left).getRelative(left).getType() == Material.STATIONARY_WATER) {
																																																																																																																					if (thirdLevel4.getRelative(left).getRelative(left).getRelative(left).getType() == Material.STAINED_GLASS_PANE) {
																																																																																																																						if (thirdLevel5.getType() == Material.AIR || thirdLevel5.getType() == Material.STATIONARY_LAVA || thirdLevel5.getType() == Material.STATIONARY_WATER) {
																																																																																																																							if (thirdLevel5.getRelative(right).getType() == Material.AIR || thirdLevel5.getRelative(right).getType() == Material.STATIONARY_LAVA || thirdLevel5.getRelative(right).getType() == Material.STATIONARY_WATER) {
																																																																																																																								if (thirdLevel5.getRelative(right).getRelative(right).getType() == Material.AIR || thirdLevel5.getRelative(right).getRelative(right).getType() == Material.STATIONARY_LAVA || thirdLevel5.getRelative(right).getRelative(right).getType() == Material.STATIONARY_WATER) {
																																																																																																																									if (thirdLevel5.getRelative(right).getRelative(right).getRelative(right).getType() == Material.STAINED_CLAY) {
																																																																																																																										if (thirdLevel5.getRelative(left).getType() == Material.AIR || thirdLevel5.getRelative(left).getType() == Material.STATIONARY_LAVA || thirdLevel5.getRelative(left).getType() == Material.STATIONARY_WATER) {
																																																																																																																											if (thirdLevel5.getRelative(left).getRelative(left).getType() == Material.AIR || thirdLevel5.getRelative(left).getRelative(left).getType() == Material.STATIONARY_LAVA || thirdLevel5.getRelative(left).getRelative(left).getType() == Material.STATIONARY_WATER) {
																																																																																																																												if (thirdLevel5.getRelative(left).getRelative(left).getRelative(left).getType() == Material.STAINED_CLAY) {
																																																																																																																													if (thirdLevel6.getType() == Material.STAINED_GLASS_PANE) {
																																																																																																																														if (thirdLevel6.getRelative(left).getType() == Material.STAINED_GLASS_PANE) {
																																																																																																																															if (thirdLevel6.getRelative(left).getRelative(left).getType() == Material.STAINED_CLAY) {
																																																																																																																																if (thirdLevel6.getRelative(right).getType() == Material.STAINED_GLASS_PANE) {
																																																																																																																																	if (thirdLevel6.getRelative(right).getRelative(right).getType() == Material.STAINED_CLAY) {

																																																																																																																																		if (fourthLevel.getType() == Material.STAINED_GLASS_PANE) {
																																																																																																																																			if (fourthLevel.getRelative(right).getType() == Material.STAINED_GLASS_PANE) {
																																																																																																																																				if (fourthLevel.getRelative(right).getRelative(right).getType() == Material.STAINED_CLAY) {
																																																																																																																																					if (fourthLevel.getRelative(left).getType() == Material.STAINED_GLASS_PANE) {
																																																																																																																																						if (fourthLevel.getRelative(left).getRelative(left).getType() == Material.STAINED_CLAY) {
																																																																																																																																							if (fourthLevel1.getType() == Material.AIR || fourthLevel1.getType() == Material.STATIONARY_LAVA || fourthLevel1.getType() == Material.STATIONARY_WATER) {
																																																																																																																																								if (fourthLevel1.getRelative(right).getType() == Material.AIR || fourthLevel1.getRelative(right).getType() == Material.STATIONARY_LAVA || fourthLevel1.getRelative(right).getType() == Material.STATIONARY_WATER) {
																																																																																																																																									if (fourthLevel1.getRelative(right).getRelative(right).getType() == Material.AIR || fourthLevel1.getRelative(right).getRelative(right).getType() == Material.STATIONARY_LAVA || fourthLevel1.getRelative(right).getRelative(right).getType() == Material.STATIONARY_WATER) {
																																																																																																																																										if (fourthLevel1.getRelative(right).getRelative(right).getRelative(right).getType() == Material.STAINED_CLAY) {
																																																																																																																																											if (fourthLevel1.getRelative(left).getType() == Material.AIR || fourthLevel1.getRelative(left).getType() == Material.STATIONARY_LAVA || fourthLevel1.getRelative(left).getType() == Material.STATIONARY_WATER) {
																																																																																																																																												if (fourthLevel1.getRelative(left).getRelative(left).getType() == Material.AIR || fourthLevel1.getRelative(left).getRelative(left).getType() == Material.STATIONARY_LAVA || fourthLevel1.getRelative(left).getRelative(left).getType() == Material.STATIONARY_WATER) {
																																																																																																																																													if (fourthLevel1.getRelative(left).getRelative(left).getRelative(left).getType() == Material.STAINED_CLAY) {
																																																																																																																																														if (fourthLevel2.getType() == Material.AIR || fourthLevel2.getType() == Material.STATIONARY_LAVA || fourthLevel2.getType() == Material.STATIONARY_WATER) {
																																																																																																																																															if (fourthLevel2.getRelative(right).getType() == Material.AIR || fourthLevel2.getRelative(right).getType() == Material.STATIONARY_LAVA || fourthLevel2.getRelative(right).getType() == Material.STATIONARY_WATER) {
																																																																																																																																																if (fourthLevel2.getRelative(right).getRelative(right).getType() == Material.AIR || fourthLevel2.getRelative(right).getRelative(right).getType() == Material.STATIONARY_LAVA || fourthLevel2.getRelative(right).getRelative(right).getType() == Material.STATIONARY_WATER) {
																																																																																																																																																	if (fourthLevel2.getRelative(right).getRelative(right).getRelative(right).getType() == Material.STAINED_GLASS_PANE) {
																																																																																																																																																		if (fourthLevel2.getRelative(left).getType() == Material.AIR || fourthLevel2.getRelative(left).getType() == Material.STATIONARY_LAVA || fourthLevel2.getRelative(left).getType() == Material.STATIONARY_WATER) {
																																																																																																																																																			if (fourthLevel2.getRelative(left).getRelative(left).getType() == Material.AIR || fourthLevel2.getRelative(left).getRelative(left).getType() == Material.STATIONARY_LAVA || fourthLevel2.getRelative(left).getRelative(left).getType() == Material.STATIONARY_WATER) {
																																																																																																																																																				if (fourthLevel2.getRelative(left).getRelative(left).getRelative(left).getType() == Material.STAINED_GLASS_PANE) {
																																																																																																																																																					if (fourthLevel3.getType() == Material.AIR || fourthLevel3.getType() == Material.STATIONARY_LAVA || fourthLevel3.getType() == Material.STATIONARY_WATER) {
																																																																																																																																																						if (fourthLevel3.getRelative(right).getType() == Material.AIR || fourthLevel3.getRelative(right).getType() == Material.STATIONARY_LAVA || fourthLevel3.getRelative(right).getType() == Material.STATIONARY_WATER) {
																																																																																																																																																							if (fourthLevel3.getRelative(right).getRelative(right).getType() == Material.AIR || fourthLevel3.getRelative(right).getRelative(right).getType() == Material.STATIONARY_LAVA || fourthLevel3.getRelative(right).getRelative(right).getType() == Material.STATIONARY_WATER) {
																																																																																																																																																								if (fourthLevel3.getRelative(right).getRelative(right).getRelative(right).getType() == Material.STAINED_GLASS_PANE) {
																																																																																																																																																									if (fourthLevel3.getRelative(left).getType() == Material.AIR || fourthLevel3.getRelative(left).getType() == Material.STATIONARY_LAVA || fourthLevel3.getRelative(left).getType() == Material.STATIONARY_WATER) {
																																																																																																																																																										if (fourthLevel3.getRelative(left).getRelative(left).getType() == Material.AIR || fourthLevel3.getRelative(left).getRelative(left).getType() == Material.STATIONARY_LAVA || fourthLevel3.getRelative(left).getRelative(left).getType() == Material.STATIONARY_WATER) {
																																																																																																																																																											if (fourthLevel3.getRelative(left).getRelative(left).getRelative(left).getType() == Material.STAINED_GLASS_PANE) {
																																																																																																																																																												if (fourthLevel4.getType() == Material.AIR || fourthLevel4.getType() == Material.STATIONARY_LAVA || fourthLevel4.getType() == Material.STATIONARY_WATER) {
																																																																																																																																																													if (fourthLevel4.getRelative(right).getType() == Material.AIR || fourthLevel4.getRelative(right).getType() == Material.STATIONARY_LAVA || fourthLevel4.getRelative(right).getType() == Material.STATIONARY_WATER) {
																																																																																																																																																														if (fourthLevel4.getRelative(right).getRelative(right).getType() == Material.AIR || fourthLevel4.getRelative(right).getRelative(right).getType() == Material.STATIONARY_LAVA || fourthLevel4.getRelative(right).getRelative(right).getType() == Material.STATIONARY_WATER) {
																																																																																																																																																															if (fourthLevel4.getRelative(right).getRelative(right).getRelative(right).getType() == Material.STAINED_GLASS_PANE) {
																																																																																																																																																																if (fourthLevel4.getRelative(left).getType() == Material.AIR || fourthLevel4.getRelative(left).getType() == Material.STATIONARY_LAVA || fourthLevel4.getRelative(left).getType() == Material.STATIONARY_WATER) {
																																																																																																																																																																	if (fourthLevel4.getRelative(left).getRelative(left).getType() == Material.AIR || fourthLevel4.getRelative(left).getRelative(left).getType() == Material.STATIONARY_LAVA || fourthLevel4.getRelative(left).getRelative(left).getType() == Material.STATIONARY_WATER) {
																																																																																																																																																																		if (fourthLevel4.getRelative(left).getRelative(left).getRelative(left).getType() == Material.STAINED_GLASS_PANE) {
																																																																																																																																																																			if (fourthLevel5.getType() == Material.AIR || fourthLevel5.getType() == Material.STATIONARY_LAVA || fourthLevel5.getType() == Material.STATIONARY_WATER) {
																																																																																																																																																																				if (fourthLevel5.getRelative(right).getType() == Material.AIR || fourthLevel5.getRelative(right).getType() == Material.STATIONARY_LAVA || fourthLevel5.getRelative(right).getType() == Material.STATIONARY_WATER) {
																																																																																																																																																																					if (fourthLevel5.getRelative(right).getRelative(right).getType() == Material.AIR || fourthLevel5.getRelative(right).getRelative(right).getType() == Material.STATIONARY_LAVA || fourthLevel5.getRelative(right).getRelative(right).getType() == Material.STATIONARY_WATER) {
																																																																																																																																																																						if (fourthLevel5.getRelative(right).getRelative(right).getRelative(right).getType() == Material.STAINED_CLAY) {
																																																																																																																																																																							if (fourthLevel5.getRelative(left).getType() == Material.AIR || fourthLevel5.getRelative(left).getType() == Material.STATIONARY_LAVA || fourthLevel5.getRelative(left).getType() == Material.STATIONARY_WATER) {
																																																																																																																																																																								if (fourthLevel5.getRelative(left).getRelative(left).getType() == Material.AIR || fourthLevel5.getRelative(left).getRelative(left).getType() == Material.STATIONARY_LAVA || fourthLevel5.getRelative(left).getRelative(left).getType() == Material.STATIONARY_WATER) {
																																																																																																																																																																									if (fourthLevel5.getRelative(left).getRelative(left).getRelative(left).getType() == Material.STAINED_CLAY) {
																																																																																																																																																																										if (fourthLevel6.getType() == Material.STAINED_GLASS_PANE) {
																																																																																																																																																																											if (fourthLevel6.getRelative(left).getType() == Material.STAINED_GLASS_PANE) {
																																																																																																																																																																												if (fourthLevel6.getRelative(left).getRelative(left).getType() == Material.STAINED_CLAY) {
																																																																																																																																																																													if (fourthLevel6.getRelative(right).getType() == Material.STAINED_GLASS_PANE) {
																																																																																																																																																																														if (fourthLevel6.getRelative(right).getRelative(right).getType() == Material.STAINED_CLAY) {

																																																																																																																																																																															if (fifthLevel.getType() == Material.STAINED_GLASS_PANE) {
																																																																																																																																																																																if (fifthLevel.getRelative(right).getType() == Material.STAINED_GLASS_PANE) {
																																																																																																																																																																																	if (fifthLevel.getRelative(right).getRelative(right).getType() == Material.STAINED_CLAY) {
																																																																																																																																																																																		if (fifthLevel.getRelative(left).getType() == Material.STAINED_GLASS_PANE) {
																																																																																																																																																																																			if (fifthLevel.getRelative(left).getRelative(left).getType() == Material.STAINED_CLAY) {
																																																																																																																																																																																				if (fifthLevel1.getType() == Material.AIR || fifthLevel1.getType() == Material.STATIONARY_LAVA || fifthLevel1.getType() == Material.STATIONARY_WATER) {
																																																																																																																																																																																					if (fifthLevel1.getRelative(right).getType() == Material.AIR || fifthLevel1.getRelative(right).getType() == Material.STATIONARY_LAVA || fifthLevel1.getRelative(right).getType() == Material.STATIONARY_WATER) {
																																																																																																																																																																																						if (fifthLevel1.getRelative(right).getRelative(right).getType() == Material.AIR || fifthLevel1.getRelative(right).getRelative(right).getType() == Material.STATIONARY_LAVA || fifthLevel1.getRelative(right).getRelative(right).getType() == Material.STATIONARY_WATER) {
																																																																																																																																																																																							if (fifthLevel1.getRelative(right).getRelative(right).getRelative(right).getType() == Material.STAINED_CLAY) {
																																																																																																																																																																																								if (fifthLevel1.getRelative(left).getType() == Material.AIR || fifthLevel1.getRelative(left).getType() == Material.STATIONARY_LAVA || fifthLevel1.getRelative(left).getType() == Material.STATIONARY_WATER) {
																																																																																																																																																																																									if (fifthLevel1.getRelative(left).getRelative(left).getType() == Material.AIR || fifthLevel1.getRelative(left).getRelative(left).getType() == Material.STATIONARY_LAVA || fifthLevel1.getRelative(left).getRelative(left).getType() == Material.STATIONARY_WATER) {
																																																																																																																																																																																										if (fifthLevel1.getRelative(left).getRelative(left).getRelative(left).getType() == Material.STAINED_CLAY) {
																																																																																																																																																																																											if (fifthLevel2.getType() == Material.AIR || fifthLevel2.getType() == Material.STATIONARY_LAVA || fifthLevel2.getType() == Material.STATIONARY_WATER) {
																																																																																																																																																																																												if (fifthLevel2.getRelative(right).getType() == Material.AIR || fifthLevel2.getRelative(right).getType() == Material.STATIONARY_LAVA || fifthLevel2.getRelative(right).getType() == Material.STATIONARY_WATER) {
																																																																																																																																																																																													if (fifthLevel2.getRelative(right).getRelative(right).getType() == Material.AIR || fifthLevel2.getRelative(right).getRelative(right).getType() == Material.STATIONARY_LAVA || fifthLevel2.getRelative(right).getRelative(right).getType() == Material.STATIONARY_WATER) {
																																																																																																																																																																																														if (fifthLevel2.getRelative(right).getRelative(right).getRelative(right).getType() == Material.STAINED_GLASS_PANE) {
																																																																																																																																																																																															if (fifthLevel2.getRelative(left).getType() == Material.AIR || fifthLevel2.getRelative(left).getType() == Material.STATIONARY_LAVA || fifthLevel2.getRelative(left).getType() == Material.STATIONARY_WATER) {
																																																																																																																																																																																																if (fifthLevel2.getRelative(left).getRelative(left).getType() == Material.AIR || fifthLevel2.getRelative(left).getRelative(left).getType() == Material.STATIONARY_LAVA || fifthLevel2.getRelative(left).getRelative(left).getType() == Material.STATIONARY_WATER) {
																																																																																																																																																																																																	if (fifthLevel2.getRelative(left).getRelative(left).getRelative(left).getType() == Material.STAINED_GLASS_PANE) {
																																																																																																																																																																																																		if (fifthLevel3.getType() == Material.AIR || fifthLevel3.getType() == Material.STATIONARY_LAVA || fifthLevel3.getType() == Material.STATIONARY_WATER) {
																																																																																																																																																																																																			if (fifthLevel3.getRelative(right).getType() == Material.AIR || fifthLevel3.getRelative(right).getType() == Material.STATIONARY_LAVA || fifthLevel3.getRelative(right).getType() == Material.STATIONARY_WATER) {
																																																																																																																																																																																																				if (fifthLevel3.getRelative(right).getRelative(right).getType() == Material.AIR || fifthLevel3.getRelative(right).getRelative(right).getType() == Material.STATIONARY_LAVA || fifthLevel3.getRelative(right).getRelative(right).getType() == Material.STATIONARY_WATER) {
																																																																																																																																																																																																					if (fifthLevel3.getRelative(right).getRelative(right).getRelative(right).getType() == Material.STAINED_GLASS_PANE) {
																																																																																																																																																																																																						if (fifthLevel3.getRelative(left).getType() == Material.AIR || fifthLevel3.getRelative(left).getType() == Material.STATIONARY_LAVA || fifthLevel3.getRelative(left).getType() == Material.STATIONARY_WATER) {
																																																																																																																																																																																																							if (fifthLevel3.getRelative(left).getRelative(left).getType() == Material.AIR || fifthLevel3.getRelative(left).getRelative(left).getType() == Material.STATIONARY_LAVA || fifthLevel3.getRelative(left).getRelative(left).getType() == Material.STATIONARY_WATER) {
																																																																																																																																																																																																								if (fifthLevel3.getRelative(left).getRelative(left).getRelative(left).getType() == Material.STAINED_GLASS_PANE) {
																																																																																																																																																																																																									if (fifthLevel4.getType() == Material.AIR || fifthLevel4.getType() == Material.STATIONARY_LAVA || fifthLevel4.getType() == Material.STATIONARY_WATER) {
																																																																																																																																																																																																										if (fifthLevel4.getRelative(right).getType() == Material.AIR || fifthLevel4.getRelative(right).getType() == Material.STATIONARY_LAVA || fifthLevel4.getRelative(right).getType() == Material.STATIONARY_WATER) {
																																																																																																																																																																																																											if (fifthLevel4.getRelative(right).getRelative(right).getType() == Material.AIR || fifthLevel4.getRelative(right).getRelative(right).getType() == Material.STATIONARY_LAVA || fifthLevel4.getRelative(right).getRelative(right).getType() == Material.STATIONARY_WATER) {
																																																																																																																																																																																																												if (fifthLevel4.getRelative(right).getRelative(right).getRelative(right).getType() == Material.STAINED_GLASS_PANE) {
																																																																																																																																																																																																													if (fifthLevel4.getRelative(left).getType() == Material.AIR || fifthLevel4.getRelative(left).getType() == Material.STATIONARY_LAVA || fifthLevel4.getRelative(left).getType() == Material.STATIONARY_WATER) {
																																																																																																																																																																																																														if (fifthLevel4.getRelative(left).getRelative(left).getType() == Material.AIR || fifthLevel4.getRelative(left).getRelative(left).getType() == Material.STATIONARY_LAVA || fifthLevel4.getRelative(left).getRelative(left).getType() == Material.STATIONARY_WATER) {
																																																																																																																																																																																																															if (fifthLevel4.getRelative(left).getRelative(left).getRelative(left).getType() == Material.STAINED_GLASS_PANE) {
																																																																																																																																																																																																																if (fifthLevel5.getType() == Material.AIR || fifthLevel5.getType() == Material.STATIONARY_LAVA || fifthLevel5.getType() == Material.STATIONARY_WATER) {
																																																																																																																																																																																																																	if (fifthLevel5.getRelative(right).getType() == Material.AIR || fifthLevel5.getRelative(right).getType() == Material.STATIONARY_LAVA || fifthLevel5.getRelative(right).getType() == Material.STATIONARY_WATER) {
																																																																																																																																																																																																																		if (fifthLevel5.getRelative(right).getRelative(right).getType() == Material.AIR || fifthLevel5.getRelative(right).getRelative(right).getType() == Material.STATIONARY_LAVA || fifthLevel5.getRelative(right).getRelative(right).getType() == Material.STATIONARY_WATER) {
																																																																																																																																																																																																																			if (fifthLevel5.getRelative(right).getRelative(right).getRelative(right).getType() == Material.STAINED_CLAY) {
																																																																																																																																																																																																																				if (fifthLevel5.getRelative(left).getType() == Material.AIR || fifthLevel5.getRelative(left).getType() == Material.STATIONARY_LAVA || fifthLevel5.getRelative(left).getType() == Material.STATIONARY_WATER) {
																																																																																																																																																																																																																					if (fifthLevel5.getRelative(left).getRelative(left).getType() == Material.AIR || fifthLevel5.getRelative(left).getRelative(left).getType() == Material.STATIONARY_LAVA || fifthLevel5.getRelative(left).getRelative(left).getType() == Material.STATIONARY_WATER) {
																																																																																																																																																																																																																						if (fifthLevel5.getRelative(left).getRelative(left).getRelative(left).getType() == Material.STAINED_CLAY) {
																																																																																																																																																																																																																							if (fifthLevel6.getType() == Material.STAINED_GLASS_PANE) {
																																																																																																																																																																																																																								if (fifthLevel6.getRelative(left).getType() == Material.STAINED_GLASS_PANE) {
																																																																																																																																																																																																																									if (fifthLevel6.getRelative(left).getRelative(left).getType() == Material.STAINED_CLAY) {
																																																																																																																																																																																																																										if (fifthLevel6.getRelative(right).getType() == Material.STAINED_GLASS_PANE) {
																																																																																																																																																																																																																											if (fifthLevel6.getRelative(right).getRelative(right).getType() == Material.STAINED_CLAY) {

																																																																																																																																																																																																																												if (sixthLevel.getType() == Material.STAINED_GLASS_PANE) {
																																																																																																																																																																																																																													if (sixthLevel.getRelative(right).getType() == Material.STAINED_GLASS_PANE) {
																																																																																																																																																																																																																														if (sixthLevel.getRelative(right).getRelative(right).getType() == Material.STAINED_CLAY) {
																																																																																																																																																																																																																															if (sixthLevel.getRelative(left).getType() == Material.STAINED_GLASS_PANE) {
																																																																																																																																																																																																																																if (sixthLevel.getRelative(left).getRelative(left).getType() == Material.STAINED_CLAY) {
																																																																																																																																																																																																																																	if (sixthLevel1.getType() == Material.AIR || sixthLevel1.getType() == Material.STATIONARY_LAVA || sixthLevel1.getType() == Material.STATIONARY_WATER) {
																																																																																																																																																																																																																																		if (sixthLevel1.getRelative(right).getType() == Material.AIR || sixthLevel1.getRelative(right).getType() == Material.STATIONARY_LAVA || sixthLevel1.getRelative(right).getType() == Material.STATIONARY_WATER) {
																																																																																																																																																																																																																																			if (sixthLevel1.getRelative(right).getRelative(right).getType() == Material.AIR || sixthLevel1.getRelative(right).getRelative(right).getType() == Material.STATIONARY_LAVA || sixthLevel1.getRelative(right).getRelative(right).getType() == Material.STATIONARY_WATER) {
																																																																																																																																																																																																																																				if (sixthLevel1.getRelative(right).getRelative(right).getRelative(right).getType() == Material.STAINED_CLAY) {
																																																																																																																																																																																																																																					if (sixthLevel1.getRelative(left).getType() == Material.AIR || sixthLevel1.getRelative(left).getType() == Material.STATIONARY_LAVA || sixthLevel1.getRelative(left).getType() == Material.STATIONARY_WATER) {
																																																																																																																																																																																																																																						if (sixthLevel1.getRelative(left).getRelative(left).getType() == Material.AIR || sixthLevel1.getRelative(left).getRelative(left).getType() == Material.STATIONARY_LAVA || sixthLevel1.getRelative(left).getRelative(left).getType() == Material.STATIONARY_WATER) {
																																																																																																																																																																																																																																							if (sixthLevel1.getRelative(left).getRelative(left).getRelative(left).getType() == Material.STAINED_CLAY) {
																																																																																																																																																																																																																																								if (sixthLevel2.getType() == Material.AIR || sixthLevel2.getType() == Material.STATIONARY_LAVA || sixthLevel2.getType() == Material.STATIONARY_WATER) {
																																																																																																																																																																																																																																									if (sixthLevel2.getRelative(right).getType() == Material.AIR || sixthLevel2.getRelative(right).getType() == Material.STATIONARY_LAVA || sixthLevel2.getRelative(right).getType() == Material.STATIONARY_WATER) {
																																																																																																																																																																																																																																										if (sixthLevel2.getRelative(right).getRelative(right).getType() == Material.AIR || sixthLevel2.getRelative(right).getRelative(right).getType() == Material.STATIONARY_LAVA || sixthLevel2.getRelative(right).getRelative(right).getType() == Material.STATIONARY_WATER) {
																																																																																																																																																																																																																																											if (sixthLevel2.getRelative(right).getRelative(right).getRelative(right).getType() == Material.STAINED_GLASS_PANE) {
																																																																																																																																																																																																																																												if (sixthLevel2.getRelative(left).getType() == Material.AIR || sixthLevel2.getRelative(left).getType() == Material.STATIONARY_LAVA || sixthLevel2.getRelative(left).getType() == Material.STATIONARY_WATER) {
																																																																																																																																																																																																																																													if (sixthLevel2.getRelative(left).getRelative(left).getType() == Material.AIR || sixthLevel2.getRelative(left).getRelative(left).getType() == Material.STATIONARY_LAVA || sixthLevel2.getRelative(left).getRelative(left).getType() == Material.STATIONARY_WATER) {
																																																																																																																																																																																																																																														if (sixthLevel2.getRelative(left).getRelative(left).getRelative(left).getType() == Material.STAINED_GLASS_PANE) {
																																																																																																																																																																																																																																															if (sixthLevel3.getType() == Material.AIR || sixthLevel3.getType() == Material.STATIONARY_LAVA || sixthLevel3.getType() == Material.STATIONARY_WATER) {
																																																																																																																																																																																																																																																if (sixthLevel3.getRelative(right).getType() == Material.AIR || sixthLevel3.getRelative(right).getType() == Material.STATIONARY_LAVA || sixthLevel3.getRelative(right).getType() == Material.STATIONARY_WATER) {
																																																																																																																																																																																																																																																	if (sixthLevel3.getRelative(right).getRelative(right).getType() == Material.AIR || sixthLevel3.getRelative(right).getRelative(right).getType() == Material.STATIONARY_LAVA || sixthLevel3.getRelative(right).getRelative(right).getType() == Material.STATIONARY_WATER) {
																																																																																																																																																																																																																																																		if (sixthLevel3.getRelative(right).getRelative(right).getRelative(right).getType() == Material.STAINED_GLASS_PANE) {
																																																																																																																																																																																																																																																			if (sixthLevel3.getRelative(left).getType() == Material.AIR || sixthLevel3.getRelative(left).getType() == Material.STATIONARY_LAVA || sixthLevel3.getRelative(left).getType() == Material.STATIONARY_WATER) {
																																																																																																																																																																																																																																																				if (sixthLevel3.getRelative(left).getRelative(left).getType() == Material.AIR || sixthLevel3.getRelative(left).getRelative(left).getType() == Material.STATIONARY_LAVA || sixthLevel3.getRelative(left).getRelative(left).getType() == Material.STATIONARY_WATER) {
																																																																																																																																																																																																																																																					if (sixthLevel3.getRelative(left).getRelative(left).getRelative(left).getType() == Material.STAINED_GLASS_PANE) {
																																																																																																																																																																																																																																																						if (sixthLevel4.getType() == Material.AIR || sixthLevel4.getType() == Material.STATIONARY_LAVA || sixthLevel4.getType() == Material.STATIONARY_WATER) {
																																																																																																																																																																																																																																																							if (sixthLevel4.getRelative(right).getType() == Material.AIR || sixthLevel4.getRelative(right).getType() == Material.STATIONARY_LAVA || sixthLevel4.getRelative(right).getType() == Material.STATIONARY_WATER) {
																																																																																																																																																																																																																																																								if (sixthLevel4.getRelative(right).getRelative(right).getType() == Material.AIR || sixthLevel4.getRelative(right).getRelative(right).getType() == Material.STATIONARY_LAVA || sixthLevel4.getRelative(right).getRelative(right).getType() == Material.STATIONARY_WATER) {
																																																																																																																																																																																																																																																									if (sixthLevel4.getRelative(right).getRelative(right).getRelative(right).getType() == Material.STAINED_GLASS_PANE) {
																																																																																																																																																																																																																																																										if (sixthLevel4.getRelative(left).getType() == Material.AIR || sixthLevel4.getRelative(left).getType() == Material.STATIONARY_LAVA || sixthLevel4.getRelative(left).getType() == Material.STATIONARY_WATER) {
																																																																																																																																																																																																																																																											if (sixthLevel4.getRelative(left).getRelative(left).getType() == Material.AIR || sixthLevel4.getRelative(left).getRelative(left).getType() == Material.STATIONARY_LAVA || sixthLevel4.getRelative(left).getRelative(left).getType() == Material.STATIONARY_WATER) {
																																																																																																																																																																																																																																																												if (sixthLevel4.getRelative(left).getRelative(left).getRelative(left).getType() == Material.STAINED_GLASS_PANE) {
																																																																																																																																																																																																																																																													if (sixthLevel5.getType() == Material.AIR || sixthLevel5.getType() == Material.STATIONARY_LAVA || sixthLevel5.getType() == Material.STATIONARY_WATER) {
																																																																																																																																																																																																																																																														if (sixthLevel5.getRelative(right).getType() == Material.AIR || sixthLevel5.getRelative(right).getType() == Material.STATIONARY_LAVA || sixthLevel5.getRelative(right).getType() == Material.STATIONARY_WATER) {
																																																																																																																																																																																																																																																															if (sixthLevel5.getRelative(right).getRelative(right).getType() == Material.AIR || sixthLevel5.getRelative(right).getRelative(right).getType() == Material.STATIONARY_LAVA || sixthLevel5.getRelative(right).getRelative(right).getType() == Material.STATIONARY_WATER) {
																																																																																																																																																																																																																																																																if (sixthLevel5.getRelative(right).getRelative(right).getRelative(right).getType() == Material.STAINED_CLAY) {
																																																																																																																																																																																																																																																																	if (sixthLevel5.getRelative(left).getType() == Material.AIR || sixthLevel5.getRelative(left).getType() == Material.STATIONARY_LAVA || sixthLevel5.getRelative(left).getType() == Material.STATIONARY_WATER) {
																																																																																																																																																																																																																																																																		if (sixthLevel5.getRelative(left).getRelative(left).getType() == Material.AIR || sixthLevel5.getRelative(left).getRelative(left).getType() == Material.STATIONARY_LAVA || sixthLevel5.getRelative(left).getRelative(left).getType() == Material.STATIONARY_WATER) {
																																																																																																																																																																																																																																																																			if (sixthLevel5.getRelative(left).getRelative(left).getRelative(left).getType() == Material.STAINED_CLAY) {
																																																																																																																																																																																																																																																																				if (sixthLevel6.getType() == Material.STAINED_GLASS_PANE) {
																																																																																																																																																																																																																																																																					if (sixthLevel6.getRelative(left).getType() == Material.STAINED_GLASS_PANE) {
																																																																																																																																																																																																																																																																						if (sixthLevel6.getRelative(left).getRelative(left).getType() == Material.STAINED_CLAY) {
																																																																																																																																																																																																																																																																							if (sixthLevel6.getRelative(right).getType() == Material.STAINED_GLASS_PANE) {
																																																																																																																																																																																																																																																																								if (sixthLevel6.getRelative(right).getRelative(right).getType() == Material.STAINED_CLAY) {

																																																																																																																																																																																																																																																																									if (seventhLevel.getType() == Material.STAINED_GLASS_PANE) {
																																																																																																																																																																																																																																																																										if (seventhLevel.getRelative(right).getType() == Material.STAINED_GLASS_PANE) {
																																																																																																																																																																																																																																																																											if (seventhLevel.getRelative(right).getRelative(right).getType() == Material.STAINED_CLAY) {
																																																																																																																																																																																																																																																																												if (seventhLevel.getRelative(left).getType() == Material.STAINED_GLASS_PANE) {
																																																																																																																																																																																																																																																																													if (seventhLevel.getRelative(left).getRelative(left).getType() == Material.STAINED_CLAY) {
																																																																																																																																																																																																																																																																														if (seventhLevel1.getType() == Material.AIR || seventhLevel1.getType() == Material.STATIONARY_LAVA || seventhLevel1.getType() == Material.STATIONARY_WATER) {
																																																																																																																																																																																																																																																																															if (seventhLevel1.getRelative(right).getType() == Material.AIR || seventhLevel1.getRelative(right).getType() == Material.STATIONARY_LAVA || seventhLevel1.getRelative(right).getType() == Material.STATIONARY_WATER) {
																																																																																																																																																																																																																																																																																if (seventhLevel1.getRelative(right).getRelative(right).getType() == Material.AIR || seventhLevel1.getRelative(right).getRelative(right).getType() == Material.STATIONARY_LAVA || seventhLevel1.getRelative(right).getRelative(right).getType() == Material.STATIONARY_WATER) {
																																																																																																																																																																																																																																																																																	if (seventhLevel1.getRelative(right).getRelative(right).getRelative(right).getType() == Material.STAINED_CLAY) {
																																																																																																																																																																																																																																																																																		if (seventhLevel1.getRelative(left).getType() == Material.AIR || seventhLevel1.getRelative(left).getType() == Material.STATIONARY_LAVA || seventhLevel1.getRelative(left).getType() == Material.STATIONARY_WATER) {
																																																																																																																																																																																																																																																																																			if (seventhLevel1.getRelative(left).getRelative(left).getType() == Material.AIR || seventhLevel1.getRelative(left).getRelative(left).getType() == Material.STATIONARY_LAVA || seventhLevel1.getRelative(left).getRelative(left).getType() == Material.STATIONARY_WATER) {
																																																																																																																																																																																																																																																																																				if (seventhLevel1.getRelative(left).getRelative(left).getRelative(left).getType() == Material.STAINED_CLAY) {
																																																																																																																																																																																																																																																																																					if (seventhLevel2.getType() == Material.AIR || seventhLevel2.getType() == Material.STATIONARY_LAVA || seventhLevel2.getType() == Material.STATIONARY_WATER) {
																																																																																																																																																																																																																																																																																						if (seventhLevel2.getRelative(right).getType() == Material.AIR || seventhLevel2.getRelative(right).getType() == Material.STATIONARY_LAVA || seventhLevel2.getRelative(right).getType() == Material.STATIONARY_WATER) {
																																																																																																																																																																																																																																																																																							if (seventhLevel2.getRelative(right).getRelative(right).getType() == Material.AIR || seventhLevel2.getRelative(right).getRelative(right).getType() == Material.STATIONARY_LAVA || seventhLevel2.getRelative(right).getRelative(right).getType() == Material.STATIONARY_WATER) {
																																																																																																																																																																																																																																																																																								if (seventhLevel2.getRelative(right).getRelative(right).getRelative(right).getType() == Material.STAINED_GLASS_PANE) {
																																																																																																																																																																																																																																																																																									if (seventhLevel2.getRelative(left).getType() == Material.AIR || seventhLevel2.getRelative(left).getType() == Material.STATIONARY_LAVA || seventhLevel2.getRelative(left).getType() == Material.STATIONARY_WATER) {
																																																																																																																																																																																																																																																																																										if (seventhLevel2.getRelative(left).getRelative(left).getType() == Material.AIR || seventhLevel2.getRelative(left).getRelative(left).getType() == Material.STATIONARY_LAVA || seventhLevel2.getRelative(left).getRelative(left).getType() == Material.STATIONARY_WATER) {
																																																																																																																																																																																																																																																																																											if (seventhLevel2.getRelative(left).getRelative(left).getRelative(left).getType() == Material.STAINED_GLASS_PANE) {
																																																																																																																																																																																																																																																																																												if (seventhLevel3.getType() == Material.AIR || seventhLevel3.getType() == Material.STATIONARY_LAVA || seventhLevel3.getType() == Material.STATIONARY_WATER) {
																																																																																																																																																																																																																																																																																													if (seventhLevel3.getRelative(right).getType() == Material.AIR || seventhLevel3.getRelative(right).getType() == Material.STATIONARY_LAVA || seventhLevel3.getRelative(right).getType() == Material.STATIONARY_WATER) {
																																																																																																																																																																																																																																																																																														if (seventhLevel3.getRelative(right).getRelative(right).getType() == Material.AIR || seventhLevel3.getRelative(right).getRelative(right).getType() == Material.STATIONARY_LAVA || seventhLevel3.getRelative(right).getRelative(right).getType() == Material.STATIONARY_WATER) {
																																																																																																																																																																																																																																																																																															if (seventhLevel3.getRelative(right).getRelative(right).getRelative(right).getType() == Material.STAINED_GLASS_PANE) {
																																																																																																																																																																																																																																																																																																if (seventhLevel3.getRelative(left).getType() == Material.AIR || seventhLevel3.getRelative(left).getType() == Material.STATIONARY_LAVA || seventhLevel3.getRelative(left).getType() == Material.STATIONARY_WATER) {
																																																																																																																																																																																																																																																																																																	if (seventhLevel3.getRelative(left).getRelative(left).getType() == Material.AIR || seventhLevel3.getRelative(left).getRelative(left).getType() == Material.STATIONARY_LAVA || seventhLevel3.getRelative(left).getRelative(left).getType() == Material.STATIONARY_WATER) {
																																																																																																																																																																																																																																																																																																		if (seventhLevel3.getRelative(left).getRelative(left).getRelative(left).getType() == Material.STAINED_GLASS_PANE) {
																																																																																																																																																																																																																																																																																																			if (seventhLevel4.getType() == Material.AIR || seventhLevel4.getType() == Material.STATIONARY_LAVA || seventhLevel4.getType() == Material.STATIONARY_WATER) {
																																																																																																																																																																																																																																																																																																				if (seventhLevel4.getRelative(right).getType() == Material.AIR || seventhLevel4.getRelative(right).getType() == Material.STATIONARY_LAVA || seventhLevel4.getRelative(right).getType() == Material.STATIONARY_WATER) {
																																																																																																																																																																																																																																																																																																					if (seventhLevel4.getRelative(right).getRelative(right).getType() == Material.AIR || seventhLevel4.getRelative(right).getRelative(right).getType() == Material.STATIONARY_LAVA || seventhLevel4.getRelative(right).getRelative(right).getType() == Material.STATIONARY_WATER) {
																																																																																																																																																																																																																																																																																																						if (seventhLevel4.getRelative(right).getRelative(right).getRelative(right).getType() == Material.STAINED_GLASS_PANE) {
																																																																																																																																																																																																																																																																																																							if (seventhLevel4.getRelative(left).getType() == Material.AIR || seventhLevel4.getRelative(left).getType() == Material.STATIONARY_LAVA || seventhLevel4.getRelative(left).getType() == Material.STATIONARY_WATER) {
																																																																																																																																																																																																																																																																																																								if (seventhLevel4.getRelative(left).getRelative(left).getType() == Material.AIR || seventhLevel4.getRelative(left).getRelative(left).getType() == Material.STATIONARY_LAVA || seventhLevel4.getRelative(left).getRelative(left).getType() == Material.STATIONARY_WATER) {
																																																																																																																																																																																																																																																																																																									if (seventhLevel4.getRelative(left).getRelative(left).getRelative(left).getType() == Material.STAINED_GLASS_PANE) {
																																																																																																																																																																																																																																																																																																										if (seventhLevel5.getType() == Material.AIR || seventhLevel5.getType() == Material.STATIONARY_LAVA || seventhLevel5.getType() == Material.STATIONARY_WATER) {
																																																																																																																																																																																																																																																																																																											if (seventhLevel5.getRelative(right).getType() == Material.AIR || seventhLevel5.getRelative(right).getType() == Material.STATIONARY_LAVA || seventhLevel5.getRelative(right).getType() == Material.STATIONARY_WATER) {
																																																																																																																																																																																																																																																																																																												if (seventhLevel5.getRelative(right).getRelative(right).getType() == Material.AIR || seventhLevel5.getRelative(right).getRelative(right).getType() == Material.STATIONARY_LAVA || seventhLevel5.getRelative(right).getRelative(right).getType() == Material.STATIONARY_WATER) {
																																																																																																																																																																																																																																																																																																													if (seventhLevel5.getRelative(right).getRelative(right).getRelative(right).getType() == Material.STAINED_CLAY) {
																																																																																																																																																																																																																																																																																																														if (seventhLevel5.getRelative(left).getType() == Material.AIR || seventhLevel5.getRelative(left).getType() == Material.STATIONARY_LAVA || seventhLevel5.getRelative(left).getType() == Material.STATIONARY_WATER) {
																																																																																																																																																																																																																																																																																																															if (seventhLevel5.getRelative(left).getRelative(left).getType() == Material.AIR || seventhLevel5.getRelative(left).getRelative(left).getType() == Material.STATIONARY_LAVA || seventhLevel5.getRelative(left).getRelative(left).getType() == Material.STATIONARY_WATER) {
																																																																																																																																																																																																																																																																																																																if (seventhLevel5.getRelative(left).getRelative(left).getRelative(left).getType() == Material.STAINED_CLAY) {
																																																																																																																																																																																																																																																																																																																	if (seventhLevel6.getType() == Material.STAINED_GLASS_PANE) {
																																																																																																																																																																																																																																																																																																																		if (seventhLevel6.getRelative(left).getType() == Material.STAINED_GLASS_PANE) {
																																																																																																																																																																																																																																																																																																																			if (seventhLevel6.getRelative(left).getRelative(left).getType() == Material.STAINED_CLAY) {
																																																																																																																																																																																																																																																																																																																				if (seventhLevel6.getRelative(right).getType() == Material.STAINED_GLASS_PANE) {
																																																																																																																																																																																																																																																																																																																					if (seventhLevel6.getRelative(right).getRelative(right).getType() == Material.STAINED_CLAY) {

																																																																																																																																																																																																																																																																																																																						if (eightLevel.getType() == Material.STAINED_GLASS_PANE) {
																																																																																																																																																																																																																																																																																																																							if (eightLevel.getRelative(right).getType() == Material.STAINED_GLASS_PANE) {
																																																																																																																																																																																																																																																																																																																								if (eightLevel.getRelative(right).getRelative(right).getType() == Material.STAINED_CLAY) {
																																																																																																																																																																																																																																																																																																																									if (eightLevel.getRelative(left).getType() == Material.STAINED_GLASS_PANE) {
																																																																																																																																																																																																																																																																																																																										if (eightLevel.getRelative(left).getRelative(left).getType() == Material.STAINED_CLAY) {
																																																																																																																																																																																																																																																																																																																											if (eightLevel1.getType() == Material.AIR || eightLevel1.getType() == Material.STATIONARY_LAVA || eightLevel1.getType() == Material.STATIONARY_WATER) {
																																																																																																																																																																																																																																																																																																																												if (eightLevel1.getRelative(right).getType() == Material.AIR || eightLevel1.getRelative(right).getType() == Material.STATIONARY_LAVA || eightLevel1.getRelative(right).getType() == Material.STATIONARY_WATER) {
																																																																																																																																																																																																																																																																																																																													if (eightLevel1.getRelative(right).getRelative(right).getType() == Material.AIR || eightLevel1.getRelative(right).getRelative(right).getType() == Material.STATIONARY_LAVA || eightLevel1.getRelative(right).getRelative(right).getType() == Material.STATIONARY_WATER) {
																																																																																																																																																																																																																																																																																																																														if (eightLevel1.getRelative(right).getRelative(right).getRelative(right).getType() == Material.STAINED_CLAY) {
																																																																																																																																																																																																																																																																																																																															if (eightLevel1.getRelative(left).getType() == Material.AIR || eightLevel1.getRelative(left).getType() == Material.STATIONARY_LAVA || eightLevel1.getRelative(left).getType() == Material.STATIONARY_WATER) {
																																																																																																																																																																																																																																																																																																																																if (eightLevel1.getRelative(left).getRelative(left).getType() == Material.AIR || eightLevel1.getRelative(left).getRelative(left).getType() == Material.STATIONARY_LAVA || eightLevel1.getRelative(left).getRelative(left).getType() == Material.STATIONARY_WATER) {
																																																																																																																																																																																																																																																																																																																																	if (eightLevel1.getRelative(left).getRelative(left).getRelative(left).getType() == Material.STAINED_CLAY) {
																																																																																																																																																																																																																																																																																																																																		if (eightLevel2.getType() == Material.AIR || eightLevel2.getType() == Material.STATIONARY_LAVA || eightLevel2.getType() == Material.STATIONARY_WATER) {
																																																																																																																																																																																																																																																																																																																																			if (eightLevel2.getRelative(right).getType() == Material.AIR || eightLevel2.getRelative(right).getType() == Material.STATIONARY_LAVA || eightLevel2.getRelative(right).getType() == Material.STATIONARY_WATER) {
																																																																																																																																																																																																																																																																																																																																				if (eightLevel2.getRelative(right).getRelative(right).getType() == Material.AIR || eightLevel2.getRelative(right).getRelative(right).getType() == Material.STATIONARY_LAVA || eightLevel2.getRelative(right).getRelative(right).getType() == Material.STATIONARY_WATER) {
																																																																																																																																																																																																																																																																																																																																					if (eightLevel2.getRelative(right).getRelative(right).getRelative(right).getType() == Material.STAINED_GLASS_PANE) {
																																																																																																																																																																																																																																																																																																																																						if (eightLevel2.getRelative(left).getType() == Material.AIR || eightLevel2.getRelative(left).getType() == Material.STATIONARY_LAVA || eightLevel2.getRelative(left).getType() == Material.STATIONARY_WATER) {
																																																																																																																																																																																																																																																																																																																																							if (eightLevel2.getRelative(left).getRelative(left).getType() == Material.AIR || eightLevel2.getRelative(left).getRelative(left).getType() == Material.STATIONARY_LAVA || eightLevel2.getRelative(left).getRelative(left).getType() == Material.STATIONARY_WATER) {
																																																																																																																																																																																																																																																																																																																																								if (eightLevel2.getRelative(left).getRelative(left).getRelative(left).getType() == Material.STAINED_GLASS_PANE) {
																																																																																																																																																																																																																																																																																																																																									if (eightLevel3.getType() == Material.AIR || eightLevel3.getType() == Material.STATIONARY_LAVA || eightLevel3.getType() == Material.STATIONARY_WATER) {
																																																																																																																																																																																																																																																																																																																																										if (eightLevel3.getRelative(right).getType() == Material.AIR || eightLevel3.getRelative(right).getType() == Material.STATIONARY_LAVA || eightLevel3.getRelative(right).getType() == Material.STATIONARY_WATER) {
																																																																																																																																																																																																																																																																																																																																											if (eightLevel3.getRelative(right).getRelative(right).getType() == Material.AIR || eightLevel3.getRelative(right).getRelative(right).getType() == Material.STATIONARY_LAVA || eightLevel3.getRelative(right).getRelative(right).getType() == Material.STATIONARY_WATER) {
																																																																																																																																																																																																																																																																																																																																												if (eightLevel3.getRelative(right).getRelative(right).getRelative(right).getType() == Material.STAINED_GLASS_PANE) {
																																																																																																																																																																																																																																																																																																																																													if (eightLevel3.getRelative(left).getType() == Material.AIR || eightLevel3.getRelative(left).getType() == Material.STATIONARY_LAVA || eightLevel3.getRelative(left).getType() == Material.STATIONARY_WATER) {
																																																																																																																																																																																																																																																																																																																																														if (eightLevel3.getRelative(left).getRelative(left).getType() == Material.AIR || eightLevel3.getRelative(left).getRelative(left).getType() == Material.STATIONARY_LAVA || eightLevel3.getRelative(left).getRelative(left).getType() == Material.STATIONARY_WATER) {
																																																																																																																																																																																																																																																																																																																																															if (eightLevel3.getRelative(left).getRelative(left).getRelative(left).getType() == Material.STAINED_GLASS_PANE) {
																																																																																																																																																																																																																																																																																																																																																if (eightLevel4.getType() == Material.AIR || eightLevel4.getType() == Material.STATIONARY_LAVA || eightLevel4.getType() == Material.STATIONARY_WATER) {
																																																																																																																																																																																																																																																																																																																																																	if (eightLevel4.getRelative(right).getType() == Material.AIR || eightLevel4.getRelative(right).getType() == Material.STATIONARY_LAVA || eightLevel4.getRelative(right).getType() == Material.STATIONARY_WATER) {
																																																																																																																																																																																																																																																																																																																																																		if (eightLevel4.getRelative(right).getRelative(right).getType() == Material.AIR || eightLevel4.getRelative(right).getRelative(right).getType() == Material.STATIONARY_LAVA || eightLevel4.getRelative(right).getRelative(right).getType() == Material.STATIONARY_WATER) {
																																																																																																																																																																																																																																																																																																																																																			if (eightLevel4.getRelative(right).getRelative(right).getRelative(right).getType() == Material.STAINED_GLASS_PANE) {
																																																																																																																																																																																																																																																																																																																																																				if (eightLevel4.getRelative(left).getType() == Material.AIR || eightLevel4.getRelative(left).getType() == Material.STATIONARY_LAVA || eightLevel4.getRelative(left).getType() == Material.STATIONARY_WATER) {
																																																																																																																																																																																																																																																																																																																																																					if (eightLevel4.getRelative(left).getRelative(left).getType() == Material.AIR || eightLevel4.getRelative(left).getRelative(left).getType() == Material.STATIONARY_LAVA || eightLevel4.getRelative(left).getRelative(left).getType() == Material.STATIONARY_WATER) {
																																																																																																																																																																																																																																																																																																																																																						if (eightLevel4.getRelative(left).getRelative(left).getRelative(left).getType() == Material.STAINED_GLASS_PANE) {
																																																																																																																																																																																																																																																																																																																																																							if (eightLevel5.getType() == Material.AIR || eightLevel5.getType() == Material.STATIONARY_LAVA || eightLevel5.getType() == Material.STATIONARY_WATER) {
																																																																																																																																																																																																																																																																																																																																																								if (eightLevel5.getRelative(right).getType() == Material.AIR || eightLevel5.getRelative(right).getType() == Material.STATIONARY_LAVA || eightLevel5.getRelative(right).getType() == Material.STATIONARY_WATER) {
																																																																																																																																																																																																																																																																																																																																																									if (eightLevel5.getRelative(right).getRelative(right).getType() == Material.AIR || eightLevel5.getRelative(right).getRelative(right).getType() == Material.STATIONARY_LAVA || eightLevel5.getRelative(right).getRelative(right).getType() == Material.STATIONARY_WATER) {
																																																																																																																																																																																																																																																																																																																																																										if (eightLevel5.getRelative(right).getRelative(right).getRelative(right).getType() == Material.STAINED_CLAY) {
																																																																																																																																																																																																																																																																																																																																																											if (eightLevel5.getRelative(left).getType() == Material.AIR || eightLevel5.getRelative(left).getType() == Material.STATIONARY_LAVA || eightLevel5.getRelative(left).getType() == Material.STATIONARY_WATER) {
																																																																																																																																																																																																																																																																																																																																																												if (eightLevel5.getRelative(left).getRelative(left).getType() == Material.AIR || eightLevel5.getRelative(left).getRelative(left).getType() == Material.STATIONARY_LAVA || eightLevel5.getRelative(left).getRelative(left).getType() == Material.STATIONARY_WATER) {
																																																																																																																																																																																																																																																																																																																																																													if (eightLevel5.getRelative(left).getRelative(left).getRelative(left).getType() == Material.STAINED_CLAY) {
																																																																																																																																																																																																																																																																																																																																																														if (eightLevel6.getType() == Material.STAINED_GLASS_PANE) {
																																																																																																																																																																																																																																																																																																																																																															if (eightLevel6.getRelative(left).getType() == Material.STAINED_GLASS_PANE) {
																																																																																																																																																																																																																																																																																																																																																																if (eightLevel6.getRelative(left).getRelative(left).getType() == Material.STAINED_CLAY) {
																																																																																																																																																																																																																																																																																																																																																																	if (eightLevel6.getRelative(right).getType() == Material.STAINED_GLASS_PANE) {
																																																																																																																																																																																																																																																																																																																																																																		if (eightLevel6.getRelative(right).getRelative(right).getType() == Material.STAINED_CLAY) {

																																																																																																																																																																																																																																																																																																																																																																			if (ninthLevel.getType() == Material.STAINED_CLAY) {
																																																																																																																																																																																																																																																																																																																																																																				if (ninthLevel.getRelative(right).getType() == Material.STAINED_CLAY) {
																																																																																																																																																																																																																																																																																																																																																																					if (ninthLevel.getRelative(left).getType() == Material.STAINED_CLAY) {
																																																																																																																																																																																																																																																																																																																																																																						if (ninthLevel1.getType() == Material.STAINED_CLAY) {
																																																																																																																																																																																																																																																																																																																																																																							if (ninthLevel1.getRelative(right).getType() == Material.STAINED_CLAY) {
																																																																																																																																																																																																																																																																																																																																																																								if (ninthLevel1.getRelative(right).getRelative(right).getType() == Material.STAINED_CLAY) {
																																																																																																																																																																																																																																																																																																																																																																									if (ninthLevel1.getRelative(left).getType() == Material.STAINED_CLAY) {
																																																																																																																																																																																																																																																																																																																																																																										if (ninthLevel1.getRelative(left).getRelative(left).getType() == Material.STAINED_CLAY) {
																																																																																																																																																																																																																																																																																																																																																																											if (ninthLevel2.getType() == Material.STAINED_CLAY) {
																																																																																																																																																																																																																																																																																																																																																																												if (ninthLevel2.getRelative(right).getType() == Material.STAINED_CLAY) {
																																																																																																																																																																																																																																																																																																																																																																													if (ninthLevel2.getRelative(right).getRelative(right).getType() == Material.STAINED_CLAY) {
																																																																																																																																																																																																																																																																																																																																																																														if (ninthLevel2.getRelative(right).getRelative(right).getRelative(right).getType() == Material.STAINED_CLAY) {
																																																																																																																																																																																																																																																																																																																																																																															if (ninthLevel2.getRelative(left).getType() == Material.STAINED_CLAY) {
																																																																																																																																																																																																																																																																																																																																																																																if (ninthLevel2.getRelative(left).getRelative(left).getType() == Material.STAINED_CLAY) {
																																																																																																																																																																																																																																																																																																																																																																																	if (ninthLevel2.getRelative(left).getRelative(left).getRelative(left).getType() == Material.STAINED_CLAY) {
																																																																																																																																																																																																																																																																																																																																																																																		if (ninthLevel3.getType() == Material.STAINED_CLAY) {
																																																																																																																																																																																																																																																																																																																																																																																			if (ninthLevel3.getRelative(right).getType() == Material.STAINED_CLAY) {
																																																																																																																																																																																																																																																																																																																																																																																				if (ninthLevel3.getRelative(right).getRelative(right).getType() == Material.STAINED_CLAY) {
																																																																																																																																																																																																																																																																																																																																																																																					if (ninthLevel3.getRelative(right).getRelative(right).getRelative(right).getType() == Material.STAINED_CLAY) {
																																																																																																																																																																																																																																																																																																																																																																																						if (ninthLevel3.getRelative(left).getType() == Material.STAINED_CLAY) {
																																																																																																																																																																																																																																																																																																																																																																																							if (ninthLevel3.getRelative(left).getRelative(left).getType() == Material.STAINED_CLAY) {
																																																																																																																																																																																																																																																																																																																																																																																								if (ninthLevel3.getRelative(left).getRelative(left).getRelative(left).getType() == Material.STAINED_CLAY) {
																																																																																																																																																																																																																																																																																																																																																																																									if (ninthLevel4.getType() == Material.STAINED_CLAY) {
																																																																																																																																																																																																																																																																																																																																																																																										if (ninthLevel4.getRelative(right).getType() == Material.STAINED_CLAY) {
																																																																																																																																																																																																																																																																																																																																																																																											if (ninthLevel4.getRelative(right).getRelative(right).getType() == Material.STAINED_CLAY) {
																																																																																																																																																																																																																																																																																																																																																																																												if (ninthLevel4.getRelative(right).getRelative(right).getRelative(right).getType() == Material.STAINED_CLAY) {
																																																																																																																																																																																																																																																																																																																																																																																													if (ninthLevel4.getRelative(left).getType() == Material.STAINED_CLAY) {
																																																																																																																																																																																																																																																																																																																																																																																														if (ninthLevel4.getRelative(left).getRelative(left).getType() == Material.STAINED_CLAY) {
																																																																																																																																																																																																																																																																																																																																																																																															if (ninthLevel4.getRelative(left).getRelative(left).getRelative(left).getType() == Material.STAINED_CLAY) {
																																																																																																																																																																																																																																																																																																																																																																																																if (ninthLevel5.getType() == Material.STAINED_CLAY) {
																																																																																																																																																																																																																																																																																																																																																																																																	if (ninthLevel5.getRelative(right).getType() == Material.STAINED_CLAY) {
																																																																																																																																																																																																																																																																																																																																																																																																		if (ninthLevel5.getRelative(right).getRelative(right).getType() == Material.STAINED_CLAY) {
																																																																																																																																																																																																																																																																																																																																																																																																			if (ninthLevel5.getRelative(left).getType() == Material.STAINED_CLAY) {
																																																																																																																																																																																																																																																																																																																																																																																																				if (ninthLevel5.getRelative(left).getRelative(left).getType() == Material.STAINED_CLAY) {
																																																																																																																																																																																																																																																																																																																																																																																																					if (ninthLevel6.getType() == Material.STAINED_CLAY) {
																																																																																																																																																																																																																																																																																																																																																																																																						if (ninthLevel6.getRelative(right).getType() == Material.STAINED_CLAY) {
																																																																																																																																																																																																																																																																																																																																																																																																							if (ninthLevel6.getRelative(left).getType() == Material.STAINED_CLAY) {

																																																																																																																																																																																																																																																																																																																																																																																																								LargeTank.updatePhysicalLiquid(machine);
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
																																																																																																																																																																																																																																																								}
																																																																																																																																																																																																																																																							}
																																																																																																																																																																																																																																																						}
																																																																																																																																																																																																																																																					}
																																																																																																																																																																																																																																																				}
																																																																																																																																																																																																																																																			}
																																																																																																																																																																																																																																																		}
																																																																																																																																																																																																																																																	}
																																																																																																																																																																																																																																																}
																																																																																																																																																																																																																																															}
																																																																																																																																																																																																																																														}
																																																																																																																																																																																																																																													}
																																																																																																																																																																																																																																												}
																																																																																																																																																																																																																																											}
																																																																																																																																																																																																																																										}
																																																																																																																																																																																																																																									}
																																																																																																																																																																																																																																								}
																																																																																																																																																																																																																																							}
																																																																																																																																																																																																																																						}
																																																																																																																																																																																																																																					}
																																																																																																																																																																																																																																				}
																																																																																																																																																																																																																																			}
																																																																																																																																																																																																																																		}
																																																																																																																																																																																																																																	}
																																																																																																																																																																																																																																}
																																																																																																																																																																																																																															}
																																																																																																																																																																																																																														}
																																																																																																																																																																																																																													}
																																																																																																																																																																																																																												}
																																																																																																																																																																																																																											}
																																																																																																																																																																																																																										}
																																																																																																																																																																																																																									}
																																																																																																																																																																																																																								}
																																																																																																																																																																																																																							}
																																																																																																																																																																																																																						}
																																																																																																																																																																																																																					}
																																																																																																																																																																																																																				}
																																																																																																																																																																																																																			}
																																																																																																																																																																																																																		}
																																																																																																																																																																																																																	}
																																																																																																																																																																																																																}

																																																																																																																																																																																																															}
																																																																																																																																																																																																														}
																																																																																																																																																																																																													}
																																																																																																																																																																																																												}
																																																																																																																																																																																																											}
																																																																																																																																																																																																										}
																																																																																																																																																																																																									}
																																																																																																																																																																																																								}
																																																																																																																																																																																																							}
																																																																																																																																																																																																						}
																																																																																																																																																																																																					}
																																																																																																																																																																																																				}
																																																																																																																																																																																																			}
																																																																																																																																																																																																		}
																																																																																																																																																																																																	}
																																																																																																																																																																																																}
																																																																																																																																																																																															}
																																																																																																																																																																																														}
																																																																																																																																																																																													}
																																																																																																																																																																																												}
																																																																																																																																																																																											}
																																																																																																																																																																																										}
																																																																																																																																																																																									}
																																																																																																																																																																																								}
																																																																																																																																																																																							}
																																																																																																																																																																																						}
																																																																																																																																																																																					}
																																																																																																																																																																																				}
																																																																																																																																																																																			}
																																																																																																																																																																																		}
																																																																																																																																																																																	}
																																																																																																																																																																																}
																																																																																																																																																																															}
																																																																																																																																																																														}
																																																																																																																																																																													}
																																																																																																																																																																												}
																																																																																																																																																																											}
																																																																																																																																																																										}
																																																																																																																																																																									}
																																																																																																																																																																								}
																																																																																																																																																																							}
																																																																																																																																																																						}
																																																																																																																																																																					}
																																																																																																																																																																				}
																																																																																																																																																																			}

																																																																																																																																																																		}
																																																																																																																																																																	}
																																																																																																																																																																}
																																																																																																																																																															}
																																																																																																																																																														}
																																																																																																																																																													}
																																																																																																																																																												}
																																																																																																																																																											}
																																																																																																																																																										}
																																																																																																																																																									}
																																																																																																																																																								}
																																																																																																																																																							}
																																																																																																																																																						}
																																																																																																																																																					}
																																																																																																																																																				}
																																																																																																																																																			}
																																																																																																																																																		}
																																																																																																																																																	}
																																																																																																																																																}
																																																																																																																																															}
																																																																																																																																														}
																																																																																																																																													}
																																																																																																																																												}
																																																																																																																																											}
																																																																																																																																										}
																																																																																																																																									}
																																																																																																																																								}
																																																																																																																																							}
																																																																																																																																						}
																																																																																																																																					}
																																																																																																																																				}
																																																																																																																																			}
																																																																																																																																		}
																																																																																																																																	}
																																																																																																																																}
																																																																																																																															}
																																																																																																																														}
																																																																																																																													}
																																																																																																																												}
																																																																																																																											}
																																																																																																																										}
																																																																																																																									}
																																																																																																																								}
																																																																																																																							}
																																																																																																																						}

																																																																																																																					}
																																																																																																																				}
																																																																																																																			}
																																																																																																																		}
																																																																																																																	}
																																																																																																																}
																																																																																																															}
																																																																																																														}
																																																																																																													}
																																																																																																												}
																																																																																																											}
																																																																																																										}
																																																																																																									}
																																																																																																								}
																																																																																																							}
																																																																																																						}
																																																																																																					}
																																																																																																				}
																																																																																																			}
																																																																																																		}
																																																																																																	}
																																																																																																}
																																																																																															}
																																																																																														}
																																																																																													}
																																																																																												}
																																																																																											}
																																																																																										}
																																																																																									}
																																																																																								}
																																																																																							}
																																																																																						}
																																																																																					}
																																																																																				}
																																																																																			}
																																																																																		}
																																																																																	}
																																																																																}
																																																																															}
																																																																														}
																																																																													}
																																																																												}
																																																																											}
																																																																										}
																																																																									}

																																																																								}
																																																																							}
																																																																						}
																																																																					}
																																																																				}
																																																																			}
																																																																		}
																																																																	}
																																																																}
																																																															}
																																																														}
																																																													}
																																																												}
																																																											}
																																																										}
																																																									}
																																																								}
																																																							}
																																																						}
																																																					}
																																																				}
																																																			}
																																																		}
																																																	}
																																																}
																																															}
																																														}
																																													}
																																												}
																																											}
																																										}
																																									}
																																								}
																																							}
																																						}
																																					}
																																				}
																																			}
																																		}
																																	}
																																}
																															}
																														}
																													}
																												}

																											}
																										}
																									}
																								}
																							}
																						}
																					}
																				}
																			}
																		}
																	}
																}
															}
														}
													}
												}
											}
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

		return new LargeTankGUI(player, id);

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

				if (machine != null) {

					if (machine.getGUIBlock() != null) {

						if (getMiddleBlock(machine.getGUIBlock()) != null &&
								getMiddleBlock(machine.getGUIBlock()).getLocation().add(0, 1, 0).getBlock() != null &&
								getMiddleBlock(machine.getGUIBlock()).getLocation().add(0, 2, 0).getBlock() != null &&
								getMiddleBlock(machine.getGUIBlock()).getLocation().add(0, 3, 0).getBlock() != null &&
								getMiddleBlock(machine.getGUIBlock()).getLocation().add(0, 4, 0).getBlock() != null &&
								getMiddleBlock(machine.getGUIBlock()).getLocation().add(0, 5, 0).getBlock() != null &&
								getMiddleBlock(machine.getGUIBlock()).getLocation().add(0, 6, 0).getBlock() != null &&
								getMiddleBlock(machine.getGUIBlock()).getLocation().add(0, 7, 0).getBlock() != null) {

							final Block waterBlock = getMiddleBlock(machine.getGUIBlock());
							Block waterBlock2 = getMiddleBlock(machine.getGUIBlock()).getLocation().add(0, 1, 0).getBlock();
							Block waterBlock3 = getMiddleBlock(machine.getGUIBlock()).getLocation().add(0, 2, 0).getBlock();
							Block waterBlock4 = getMiddleBlock(machine.getGUIBlock()).getLocation().add(0, 3, 0).getBlock();
							Block waterBlock5 = getMiddleBlock(machine.getGUIBlock()).getLocation().add(0, 4, 0).getBlock();
							Block waterBlock6 = getMiddleBlock(machine.getGUIBlock()).getLocation().add(0, 5, 0).getBlock();
							Block waterBlock7 = getMiddleBlock(machine.getGUIBlock()).getLocation().add(0, 6, 0).getBlock();

							List<Block> waterBlocks = new ArrayList<Block>();
							if (SQTechPumps.tankWaterBlocks.get(machine) != null) {
								waterBlocks = SQTechPumps.tankWaterBlocks.get(machine);
							}

							if (!waterBlocks.contains(waterBlock)) {
								waterBlocks.add(waterBlock);
							}
							if (!waterBlocks.contains(waterBlock.getRelative(BlockFace.NORTH))) {
								waterBlocks.add(waterBlock.getRelative(BlockFace.NORTH));
							}
							if (!waterBlocks.contains(waterBlock.getRelative(BlockFace.NORTH).getRelative(BlockFace.NORTH))) {
								waterBlocks.add(waterBlock.getRelative(BlockFace.NORTH).getRelative(BlockFace.NORTH));
							}
							if (!waterBlocks.contains(waterBlock.getRelative(BlockFace.NORTH).getRelative(BlockFace.NORTH).getRelative(BlockFace.WEST))) {
								waterBlocks.add(waterBlock.getRelative(BlockFace.NORTH).getRelative(BlockFace.NORTH).getRelative(BlockFace.WEST));
							}
							if (!waterBlocks.contains(waterBlock.getRelative(BlockFace.NORTH).getRelative(BlockFace.NORTH).getRelative(BlockFace.WEST).getRelative(BlockFace.WEST))) {
								waterBlocks.add(waterBlock.getRelative(BlockFace.NORTH).getRelative(BlockFace.NORTH).getRelative(BlockFace.WEST).getRelative(BlockFace.WEST));
							}
							if (!waterBlocks.contains(waterBlock.getRelative(BlockFace.NORTH).getRelative(BlockFace.NORTH).getRelative(BlockFace.EAST))) {
								waterBlocks.add(waterBlock.getRelative(BlockFace.NORTH).getRelative(BlockFace.NORTH).getRelative(BlockFace.EAST));
							}
							if (!waterBlocks.contains(waterBlock.getRelative(BlockFace.NORTH).getRelative(BlockFace.NORTH).getRelative(BlockFace.EAST).getRelative(BlockFace.EAST))) {
								waterBlocks.add(waterBlock.getRelative(BlockFace.NORTH).getRelative(BlockFace.NORTH).getRelative(BlockFace.EAST).getRelative(BlockFace.EAST));
							}
							if (!waterBlocks.contains(waterBlock.getRelative(BlockFace.SOUTH))) {
								waterBlocks.add(waterBlock.getRelative(BlockFace.SOUTH));
							}
							if (!waterBlocks.contains(waterBlock.getRelative(BlockFace.SOUTH).getRelative(BlockFace.SOUTH))) {
								waterBlocks.add(waterBlock.getRelative(BlockFace.SOUTH).getRelative(BlockFace.SOUTH));
							}
							if (!waterBlocks.contains(waterBlock.getRelative(BlockFace.SOUTH).getRelative(BlockFace.SOUTH).getRelative(BlockFace.WEST))) {
								waterBlocks.add(waterBlock.getRelative(BlockFace.SOUTH).getRelative(BlockFace.SOUTH).getRelative(BlockFace.WEST));
							}
							if (!waterBlocks.contains(waterBlock.getRelative(BlockFace.SOUTH).getRelative(BlockFace.SOUTH).getRelative(BlockFace.WEST).getRelative(BlockFace.WEST))) {
								waterBlocks.add(waterBlock.getRelative(BlockFace.SOUTH).getRelative(BlockFace.SOUTH).getRelative(BlockFace.WEST).getRelative(BlockFace.WEST));
							}
							if (!waterBlocks.contains(waterBlock.getRelative(BlockFace.SOUTH).getRelative(BlockFace.SOUTH).getRelative(BlockFace.EAST))) {
								waterBlocks.add(waterBlock.getRelative(BlockFace.SOUTH).getRelative(BlockFace.SOUTH).getRelative(BlockFace.EAST));
							}
							if (!waterBlocks.contains(waterBlock.getRelative(BlockFace.SOUTH).getRelative(BlockFace.SOUTH).getRelative(BlockFace.EAST).getRelative(BlockFace.EAST))) {
								waterBlocks.add(waterBlock.getRelative(BlockFace.SOUTH).getRelative(BlockFace.SOUTH).getRelative(BlockFace.EAST).getRelative(BlockFace.EAST));
							}
							if (!waterBlocks.contains(waterBlock.getRelative(BlockFace.WEST))) {
								waterBlocks.add(waterBlock.getRelative(BlockFace.WEST));
							}
							if (!waterBlocks.contains(waterBlock.getRelative(BlockFace.WEST).getRelative(BlockFace.WEST))) {
								waterBlocks.add(waterBlock.getRelative(BlockFace.WEST).getRelative(BlockFace.WEST));
							}
							if (!waterBlocks.contains(waterBlock.getRelative(BlockFace.NORTH).getRelative(BlockFace.WEST))) {
								waterBlocks.add(waterBlock.getRelative(BlockFace.NORTH).getRelative(BlockFace.WEST));
							}
							if (!waterBlocks.contains(waterBlock.getRelative(BlockFace.NORTH).getRelative(BlockFace.WEST).getRelative(BlockFace.WEST))) {
								waterBlocks.add(waterBlock.getRelative(BlockFace.NORTH).getRelative(BlockFace.WEST).getRelative(BlockFace.WEST));
							}
							if (!waterBlocks.contains(waterBlock.getRelative(BlockFace.SOUTH).getRelative(BlockFace.WEST))) {
								waterBlocks.add(waterBlock.getRelative(BlockFace.SOUTH).getRelative(BlockFace.WEST));
							}
							if (!waterBlocks.contains(waterBlock.getRelative(BlockFace.SOUTH).getRelative(BlockFace.WEST).getRelative(BlockFace.WEST))) {
								waterBlocks.add(waterBlock.getRelative(BlockFace.SOUTH).getRelative(BlockFace.WEST).getRelative(BlockFace.WEST));
							}
							if (!waterBlocks.contains(waterBlock.getRelative(BlockFace.EAST))) {
								waterBlocks.add(waterBlock.getRelative(BlockFace.EAST));
							}
							if (!waterBlocks.contains(waterBlock.getRelative(BlockFace.EAST).getRelative(BlockFace.EAST))) {
								waterBlocks.add(waterBlock.getRelative(BlockFace.EAST).getRelative(BlockFace.EAST));
							}
							if (!waterBlocks.contains(waterBlock.getRelative(BlockFace.NORTH).getRelative(BlockFace.EAST))) {
								waterBlocks.add(waterBlock.getRelative(BlockFace.NORTH).getRelative(BlockFace.EAST));
							}
							if (!waterBlocks.contains(waterBlock.getRelative(BlockFace.NORTH).getRelative(BlockFace.EAST).getRelative(BlockFace.EAST))) {
								waterBlocks.add(waterBlock.getRelative(BlockFace.NORTH).getRelative(BlockFace.EAST).getRelative(BlockFace.EAST));
							}
							if (!waterBlocks.contains(waterBlock.getRelative(BlockFace.SOUTH).getRelative(BlockFace.EAST))) {
								waterBlocks.add(waterBlock.getRelative(BlockFace.SOUTH).getRelative(BlockFace.EAST));
							}
							if (!waterBlocks.contains(waterBlock.getRelative(BlockFace.SOUTH).getRelative(BlockFace.EAST).getRelative(BlockFace.EAST))) {
								waterBlocks.add(waterBlock.getRelative(BlockFace.SOUTH).getRelative(BlockFace.EAST).getRelative(BlockFace.EAST));
							}

							//INBETWEEN

							if (!waterBlocks.contains(waterBlock2)) {
								waterBlocks.add(waterBlock2);
							}
							if (!waterBlocks.contains(waterBlock2.getRelative(BlockFace.NORTH))) {
								waterBlocks.add(waterBlock2.getRelative(BlockFace.NORTH));
							}
							if (!waterBlocks.contains(waterBlock2.getRelative(BlockFace.NORTH).getRelative(BlockFace.NORTH))) {
								waterBlocks.add(waterBlock2.getRelative(BlockFace.NORTH).getRelative(BlockFace.NORTH));
							}
							if (!waterBlocks.contains(waterBlock2.getRelative(BlockFace.NORTH).getRelative(BlockFace.NORTH).getRelative(BlockFace.WEST))) {
								waterBlocks.add(waterBlock2.getRelative(BlockFace.NORTH).getRelative(BlockFace.NORTH).getRelative(BlockFace.WEST));
							}
							if (!waterBlocks.contains(waterBlock2.getRelative(BlockFace.NORTH).getRelative(BlockFace.NORTH).getRelative(BlockFace.WEST).getRelative(BlockFace.WEST))) {
								waterBlocks.add(waterBlock2.getRelative(BlockFace.NORTH).getRelative(BlockFace.NORTH).getRelative(BlockFace.WEST).getRelative(BlockFace.WEST));
							}
							if (!waterBlocks.contains(waterBlock2.getRelative(BlockFace.NORTH).getRelative(BlockFace.NORTH).getRelative(BlockFace.EAST))) {
								waterBlocks.add(waterBlock2.getRelative(BlockFace.NORTH).getRelative(BlockFace.NORTH).getRelative(BlockFace.EAST));
							}
							if (!waterBlocks.contains(waterBlock2.getRelative(BlockFace.NORTH).getRelative(BlockFace.NORTH).getRelative(BlockFace.EAST).getRelative(BlockFace.EAST))) {
								waterBlocks.add(waterBlock2.getRelative(BlockFace.NORTH).getRelative(BlockFace.NORTH).getRelative(BlockFace.EAST).getRelative(BlockFace.EAST));
							}
							if (!waterBlocks.contains(waterBlock2.getRelative(BlockFace.SOUTH))) {
								waterBlocks.add(waterBlock2.getRelative(BlockFace.SOUTH));
							}
							if (!waterBlocks.contains(waterBlock2.getRelative(BlockFace.SOUTH).getRelative(BlockFace.SOUTH))) {
								waterBlocks.add(waterBlock2.getRelative(BlockFace.SOUTH).getRelative(BlockFace.SOUTH));
							}
							if (!waterBlocks.contains(waterBlock2.getRelative(BlockFace.SOUTH).getRelative(BlockFace.SOUTH).getRelative(BlockFace.WEST))) {
								waterBlocks.add(waterBlock2.getRelative(BlockFace.SOUTH).getRelative(BlockFace.SOUTH).getRelative(BlockFace.WEST));
							}
							if (!waterBlocks.contains(waterBlock2.getRelative(BlockFace.SOUTH).getRelative(BlockFace.SOUTH).getRelative(BlockFace.WEST).getRelative(BlockFace.WEST))) {
								waterBlocks.add(waterBlock2.getRelative(BlockFace.SOUTH).getRelative(BlockFace.SOUTH).getRelative(BlockFace.WEST).getRelative(BlockFace.WEST));
							}
							if (!waterBlocks.contains(waterBlock2.getRelative(BlockFace.SOUTH).getRelative(BlockFace.SOUTH).getRelative(BlockFace.EAST))) {
								waterBlocks.add(waterBlock2.getRelative(BlockFace.SOUTH).getRelative(BlockFace.SOUTH).getRelative(BlockFace.EAST));
							}
							if (!waterBlocks.contains(waterBlock2.getRelative(BlockFace.SOUTH).getRelative(BlockFace.SOUTH).getRelative(BlockFace.EAST).getRelative(BlockFace.EAST))) {
								waterBlocks.add(waterBlock2.getRelative(BlockFace.SOUTH).getRelative(BlockFace.SOUTH).getRelative(BlockFace.EAST).getRelative(BlockFace.EAST));
							}
							if (!waterBlocks.contains(waterBlock2.getRelative(BlockFace.WEST))) {
								waterBlocks.add(waterBlock2.getRelative(BlockFace.WEST));
							}
							if (!waterBlocks.contains(waterBlock2.getRelative(BlockFace.WEST).getRelative(BlockFace.WEST))) {
								waterBlocks.add(waterBlock2.getRelative(BlockFace.WEST).getRelative(BlockFace.WEST));
							}
							if (!waterBlocks.contains(waterBlock2.getRelative(BlockFace.NORTH).getRelative(BlockFace.WEST))) {
								waterBlocks.add(waterBlock2.getRelative(BlockFace.NORTH).getRelative(BlockFace.WEST));
							}
							if (!waterBlocks.contains(waterBlock2.getRelative(BlockFace.NORTH).getRelative(BlockFace.WEST).getRelative(BlockFace.WEST))) {
								waterBlocks.add(waterBlock2.getRelative(BlockFace.NORTH).getRelative(BlockFace.WEST).getRelative(BlockFace.WEST));
							}
							if (!waterBlocks.contains(waterBlock2.getRelative(BlockFace.SOUTH).getRelative(BlockFace.WEST))) {
								waterBlocks.add(waterBlock2.getRelative(BlockFace.SOUTH).getRelative(BlockFace.WEST));
							}
							if (!waterBlocks.contains(waterBlock2.getRelative(BlockFace.SOUTH).getRelative(BlockFace.WEST).getRelative(BlockFace.WEST))) {
								waterBlocks.add(waterBlock2.getRelative(BlockFace.SOUTH).getRelative(BlockFace.WEST).getRelative(BlockFace.WEST));
							}
							if (!waterBlocks.contains(waterBlock2.getRelative(BlockFace.EAST))) {
								waterBlocks.add(waterBlock2.getRelative(BlockFace.EAST));
							}
							if (!waterBlocks.contains(waterBlock2.getRelative(BlockFace.EAST).getRelative(BlockFace.EAST))) {
								waterBlocks.add(waterBlock2.getRelative(BlockFace.EAST).getRelative(BlockFace.EAST));
							}
							if (!waterBlocks.contains(waterBlock2.getRelative(BlockFace.NORTH).getRelative(BlockFace.EAST))) {
								waterBlocks.add(waterBlock2.getRelative(BlockFace.NORTH).getRelative(BlockFace.EAST));
							}
							if (!waterBlocks.contains(waterBlock2.getRelative(BlockFace.NORTH).getRelative(BlockFace.EAST).getRelative(BlockFace.EAST))) {
								waterBlocks.add(waterBlock2.getRelative(BlockFace.NORTH).getRelative(BlockFace.EAST).getRelative(BlockFace.EAST));
							}
							if (!waterBlocks.contains(waterBlock2.getRelative(BlockFace.SOUTH).getRelative(BlockFace.EAST))) {
								waterBlocks.add(waterBlock2.getRelative(BlockFace.SOUTH).getRelative(BlockFace.EAST));
							}
							if (!waterBlocks.contains(waterBlock2.getRelative(BlockFace.SOUTH).getRelative(BlockFace.EAST).getRelative(BlockFace.EAST))) {
								waterBlocks.add(waterBlock2.getRelative(BlockFace.SOUTH).getRelative(BlockFace.EAST).getRelative(BlockFace.EAST));
							}

							//INBETWEEN

							if (!waterBlocks.contains(waterBlock3)) {
								waterBlocks.add(waterBlock3);
							}
							if (!waterBlocks.contains(waterBlock3.getRelative(BlockFace.NORTH))) {
								waterBlocks.add(waterBlock3.getRelative(BlockFace.NORTH));
							}
							if (!waterBlocks.contains(waterBlock3.getRelative(BlockFace.NORTH).getRelative(BlockFace.NORTH))) {
								waterBlocks.add(waterBlock3.getRelative(BlockFace.NORTH).getRelative(BlockFace.NORTH));
							}
							if (!waterBlocks.contains(waterBlock3.getRelative(BlockFace.NORTH).getRelative(BlockFace.NORTH).getRelative(BlockFace.WEST))) {
								waterBlocks.add(waterBlock3.getRelative(BlockFace.NORTH).getRelative(BlockFace.NORTH).getRelative(BlockFace.WEST));
							}
							if (!waterBlocks.contains(waterBlock3.getRelative(BlockFace.NORTH).getRelative(BlockFace.NORTH).getRelative(BlockFace.WEST).getRelative(BlockFace.WEST))) {
								waterBlocks.add(waterBlock3.getRelative(BlockFace.NORTH).getRelative(BlockFace.NORTH).getRelative(BlockFace.WEST).getRelative(BlockFace.WEST));
							}
							if (!waterBlocks.contains(waterBlock3.getRelative(BlockFace.NORTH).getRelative(BlockFace.NORTH).getRelative(BlockFace.EAST))) {
								waterBlocks.add(waterBlock3.getRelative(BlockFace.NORTH).getRelative(BlockFace.NORTH).getRelative(BlockFace.EAST));
							}
							if (!waterBlocks.contains(waterBlock3.getRelative(BlockFace.NORTH).getRelative(BlockFace.NORTH).getRelative(BlockFace.EAST).getRelative(BlockFace.EAST))) {
								waterBlocks.add(waterBlock3.getRelative(BlockFace.NORTH).getRelative(BlockFace.NORTH).getRelative(BlockFace.EAST).getRelative(BlockFace.EAST));
							}
							if (!waterBlocks.contains(waterBlock3.getRelative(BlockFace.SOUTH))) {
								waterBlocks.add(waterBlock3.getRelative(BlockFace.SOUTH));
							}
							if (!waterBlocks.contains(waterBlock3.getRelative(BlockFace.SOUTH).getRelative(BlockFace.SOUTH))) {
								waterBlocks.add(waterBlock3.getRelative(BlockFace.SOUTH).getRelative(BlockFace.SOUTH));
							}
							if (!waterBlocks.contains(waterBlock3.getRelative(BlockFace.SOUTH).getRelative(BlockFace.SOUTH).getRelative(BlockFace.WEST))) {
								waterBlocks.add(waterBlock3.getRelative(BlockFace.SOUTH).getRelative(BlockFace.SOUTH).getRelative(BlockFace.WEST));
							}
							if (!waterBlocks.contains(waterBlock3.getRelative(BlockFace.SOUTH).getRelative(BlockFace.SOUTH).getRelative(BlockFace.WEST).getRelative(BlockFace.WEST))) {
								waterBlocks.add(waterBlock3.getRelative(BlockFace.SOUTH).getRelative(BlockFace.SOUTH).getRelative(BlockFace.WEST).getRelative(BlockFace.WEST));
							}
							if (!waterBlocks.contains(waterBlock3.getRelative(BlockFace.SOUTH).getRelative(BlockFace.SOUTH).getRelative(BlockFace.EAST))) {
								waterBlocks.add(waterBlock3.getRelative(BlockFace.SOUTH).getRelative(BlockFace.SOUTH).getRelative(BlockFace.EAST));
							}
							if (!waterBlocks.contains(waterBlock3.getRelative(BlockFace.SOUTH).getRelative(BlockFace.SOUTH).getRelative(BlockFace.EAST).getRelative(BlockFace.EAST))) {
								waterBlocks.add(waterBlock3.getRelative(BlockFace.SOUTH).getRelative(BlockFace.SOUTH).getRelative(BlockFace.EAST).getRelative(BlockFace.EAST));
							}
							if (!waterBlocks.contains(waterBlock3.getRelative(BlockFace.WEST))) {
								waterBlocks.add(waterBlock3.getRelative(BlockFace.WEST));
							}
							if (!waterBlocks.contains(waterBlock3.getRelative(BlockFace.WEST).getRelative(BlockFace.WEST))) {
								waterBlocks.add(waterBlock3.getRelative(BlockFace.WEST).getRelative(BlockFace.WEST));
							}
							if (!waterBlocks.contains(waterBlock3.getRelative(BlockFace.NORTH).getRelative(BlockFace.WEST))) {
								waterBlocks.add(waterBlock3.getRelative(BlockFace.NORTH).getRelative(BlockFace.WEST));
							}
							if (!waterBlocks.contains(waterBlock3.getRelative(BlockFace.NORTH).getRelative(BlockFace.WEST).getRelative(BlockFace.WEST))) {
								waterBlocks.add(waterBlock3.getRelative(BlockFace.NORTH).getRelative(BlockFace.WEST).getRelative(BlockFace.WEST));
							}
							if (!waterBlocks.contains(waterBlock3.getRelative(BlockFace.SOUTH).getRelative(BlockFace.WEST))) {
								waterBlocks.add(waterBlock3.getRelative(BlockFace.SOUTH).getRelative(BlockFace.WEST));
							}
							if (!waterBlocks.contains(waterBlock3.getRelative(BlockFace.SOUTH).getRelative(BlockFace.WEST).getRelative(BlockFace.WEST))) {
								waterBlocks.add(waterBlock3.getRelative(BlockFace.SOUTH).getRelative(BlockFace.WEST).getRelative(BlockFace.WEST));
							}
							if (!waterBlocks.contains(waterBlock3.getRelative(BlockFace.EAST))) {
								waterBlocks.add(waterBlock3.getRelative(BlockFace.EAST));
							}
							if (!waterBlocks.contains(waterBlock3.getRelative(BlockFace.EAST).getRelative(BlockFace.EAST))) {
								waterBlocks.add(waterBlock3.getRelative(BlockFace.EAST).getRelative(BlockFace.EAST));
							}
							if (!waterBlocks.contains(waterBlock3.getRelative(BlockFace.NORTH).getRelative(BlockFace.EAST))) {
								waterBlocks.add(waterBlock3.getRelative(BlockFace.NORTH).getRelative(BlockFace.EAST));
							}
							if (!waterBlocks.contains(waterBlock3.getRelative(BlockFace.NORTH).getRelative(BlockFace.EAST).getRelative(BlockFace.EAST))) {
								waterBlocks.add(waterBlock3.getRelative(BlockFace.NORTH).getRelative(BlockFace.EAST).getRelative(BlockFace.EAST));
							}
							if (!waterBlocks.contains(waterBlock3.getRelative(BlockFace.SOUTH).getRelative(BlockFace.EAST))) {
								waterBlocks.add(waterBlock3.getRelative(BlockFace.SOUTH).getRelative(BlockFace.EAST));
							}
							if (!waterBlocks.contains(waterBlock3.getRelative(BlockFace.SOUTH).getRelative(BlockFace.EAST).getRelative(BlockFace.EAST))) {
								waterBlocks.add(waterBlock3.getRelative(BlockFace.SOUTH).getRelative(BlockFace.EAST).getRelative(BlockFace.EAST));
							}

							//INBETWEEN

							if (!waterBlocks.contains(waterBlock4)) {
								waterBlocks.add(waterBlock4);
							}
							if (!waterBlocks.contains(waterBlock4.getRelative(BlockFace.NORTH))) {
								waterBlocks.add(waterBlock4.getRelative(BlockFace.NORTH));
							}
							if (!waterBlocks.contains(waterBlock4.getRelative(BlockFace.NORTH).getRelative(BlockFace.NORTH))) {
								waterBlocks.add(waterBlock4.getRelative(BlockFace.NORTH).getRelative(BlockFace.NORTH));
							}
							if (!waterBlocks.contains(waterBlock4.getRelative(BlockFace.NORTH).getRelative(BlockFace.NORTH).getRelative(BlockFace.WEST))) {
								waterBlocks.add(waterBlock4.getRelative(BlockFace.NORTH).getRelative(BlockFace.NORTH).getRelative(BlockFace.WEST));
							}
							if (!waterBlocks.contains(waterBlock4.getRelative(BlockFace.NORTH).getRelative(BlockFace.NORTH).getRelative(BlockFace.WEST).getRelative(BlockFace.WEST))) {
								waterBlocks.add(waterBlock4.getRelative(BlockFace.NORTH).getRelative(BlockFace.NORTH).getRelative(BlockFace.WEST).getRelative(BlockFace.WEST));
							}
							if (!waterBlocks.contains(waterBlock4.getRelative(BlockFace.NORTH).getRelative(BlockFace.NORTH).getRelative(BlockFace.EAST))) {
								waterBlocks.add(waterBlock4.getRelative(BlockFace.NORTH).getRelative(BlockFace.NORTH).getRelative(BlockFace.EAST));
							}
							if (!waterBlocks.contains(waterBlock4.getRelative(BlockFace.NORTH).getRelative(BlockFace.NORTH).getRelative(BlockFace.EAST).getRelative(BlockFace.EAST))) {
								waterBlocks.add(waterBlock4.getRelative(BlockFace.NORTH).getRelative(BlockFace.NORTH).getRelative(BlockFace.EAST).getRelative(BlockFace.EAST));
							}
							if (!waterBlocks.contains(waterBlock4.getRelative(BlockFace.SOUTH))) {
								waterBlocks.add(waterBlock4.getRelative(BlockFace.SOUTH));
							}
							if (!waterBlocks.contains(waterBlock4.getRelative(BlockFace.SOUTH).getRelative(BlockFace.SOUTH))) {
								waterBlocks.add(waterBlock4.getRelative(BlockFace.SOUTH).getRelative(BlockFace.SOUTH));
							}
							if (!waterBlocks.contains(waterBlock4.getRelative(BlockFace.SOUTH).getRelative(BlockFace.SOUTH).getRelative(BlockFace.WEST))) {
								waterBlocks.add(waterBlock4.getRelative(BlockFace.SOUTH).getRelative(BlockFace.SOUTH).getRelative(BlockFace.WEST));
							}
							if (!waterBlocks.contains(waterBlock4.getRelative(BlockFace.SOUTH).getRelative(BlockFace.SOUTH).getRelative(BlockFace.WEST).getRelative(BlockFace.WEST))) {
								waterBlocks.add(waterBlock4.getRelative(BlockFace.SOUTH).getRelative(BlockFace.SOUTH).getRelative(BlockFace.WEST).getRelative(BlockFace.WEST));
							}
							if (!waterBlocks.contains(waterBlock4.getRelative(BlockFace.SOUTH).getRelative(BlockFace.SOUTH).getRelative(BlockFace.EAST))) {
								waterBlocks.add(waterBlock4.getRelative(BlockFace.SOUTH).getRelative(BlockFace.SOUTH).getRelative(BlockFace.EAST));
							}
							if (!waterBlocks.contains(waterBlock4.getRelative(BlockFace.SOUTH).getRelative(BlockFace.SOUTH).getRelative(BlockFace.EAST).getRelative(BlockFace.EAST))) {
								waterBlocks.add(waterBlock4.getRelative(BlockFace.SOUTH).getRelative(BlockFace.SOUTH).getRelative(BlockFace.EAST).getRelative(BlockFace.EAST));
							}
							if (!waterBlocks.contains(waterBlock4.getRelative(BlockFace.WEST))) {
								waterBlocks.add(waterBlock4.getRelative(BlockFace.WEST));
							}
							if (!waterBlocks.contains(waterBlock4.getRelative(BlockFace.WEST).getRelative(BlockFace.WEST))) {
								waterBlocks.add(waterBlock4.getRelative(BlockFace.WEST).getRelative(BlockFace.WEST));
							}
							if (!waterBlocks.contains(waterBlock4.getRelative(BlockFace.NORTH).getRelative(BlockFace.WEST))) {
								waterBlocks.add(waterBlock4.getRelative(BlockFace.NORTH).getRelative(BlockFace.WEST));
							}
							if (!waterBlocks.contains(waterBlock4.getRelative(BlockFace.NORTH).getRelative(BlockFace.WEST).getRelative(BlockFace.WEST))) {
								waterBlocks.add(waterBlock4.getRelative(BlockFace.NORTH).getRelative(BlockFace.WEST).getRelative(BlockFace.WEST));
							}
							if (!waterBlocks.contains(waterBlock4.getRelative(BlockFace.SOUTH).getRelative(BlockFace.WEST))) {
								waterBlocks.add(waterBlock4.getRelative(BlockFace.SOUTH).getRelative(BlockFace.WEST));
							}
							if (!waterBlocks.contains(waterBlock4.getRelative(BlockFace.SOUTH).getRelative(BlockFace.WEST).getRelative(BlockFace.WEST))) {
								waterBlocks.add(waterBlock4.getRelative(BlockFace.SOUTH).getRelative(BlockFace.WEST).getRelative(BlockFace.WEST));
							}
							if (!waterBlocks.contains(waterBlock4.getRelative(BlockFace.EAST))) {
								waterBlocks.add(waterBlock4.getRelative(BlockFace.EAST));
							}
							if (!waterBlocks.contains(waterBlock4.getRelative(BlockFace.EAST).getRelative(BlockFace.EAST))) {
								waterBlocks.add(waterBlock4.getRelative(BlockFace.EAST).getRelative(BlockFace.EAST));
							}
							if (!waterBlocks.contains(waterBlock4.getRelative(BlockFace.NORTH).getRelative(BlockFace.EAST))) {
								waterBlocks.add(waterBlock4.getRelative(BlockFace.NORTH).getRelative(BlockFace.EAST));
							}
							if (!waterBlocks.contains(waterBlock4.getRelative(BlockFace.NORTH).getRelative(BlockFace.EAST).getRelative(BlockFace.EAST))) {
								waterBlocks.add(waterBlock4.getRelative(BlockFace.NORTH).getRelative(BlockFace.EAST).getRelative(BlockFace.EAST));
							}
							if (!waterBlocks.contains(waterBlock4.getRelative(BlockFace.SOUTH).getRelative(BlockFace.EAST))) {
								waterBlocks.add(waterBlock4.getRelative(BlockFace.SOUTH).getRelative(BlockFace.EAST));
							}
							if (!waterBlocks.contains(waterBlock4.getRelative(BlockFace.SOUTH).getRelative(BlockFace.EAST).getRelative(BlockFace.EAST))) {
								waterBlocks.add(waterBlock4.getRelative(BlockFace.SOUTH).getRelative(BlockFace.EAST).getRelative(BlockFace.EAST));
							}

							//INBETWEEN

							if (!waterBlocks.contains(waterBlock5)) {
								waterBlocks.add(waterBlock5);
							}
							if (!waterBlocks.contains(waterBlock5.getRelative(BlockFace.NORTH))) {
								waterBlocks.add(waterBlock5.getRelative(BlockFace.NORTH));
							}
							if (!waterBlocks.contains(waterBlock5.getRelative(BlockFace.NORTH).getRelative(BlockFace.NORTH))) {
								waterBlocks.add(waterBlock5.getRelative(BlockFace.NORTH).getRelative(BlockFace.NORTH));
							}
							if (!waterBlocks.contains(waterBlock5.getRelative(BlockFace.NORTH).getRelative(BlockFace.NORTH).getRelative(BlockFace.WEST))) {
								waterBlocks.add(waterBlock5.getRelative(BlockFace.NORTH).getRelative(BlockFace.NORTH).getRelative(BlockFace.WEST));
							}
							if (!waterBlocks.contains(waterBlock5.getRelative(BlockFace.NORTH).getRelative(BlockFace.NORTH).getRelative(BlockFace.WEST).getRelative(BlockFace.WEST))) {
								waterBlocks.add(waterBlock5.getRelative(BlockFace.NORTH).getRelative(BlockFace.NORTH).getRelative(BlockFace.WEST).getRelative(BlockFace.WEST));
							}
							if (!waterBlocks.contains(waterBlock5.getRelative(BlockFace.NORTH).getRelative(BlockFace.NORTH).getRelative(BlockFace.EAST))) {
								waterBlocks.add(waterBlock5.getRelative(BlockFace.NORTH).getRelative(BlockFace.NORTH).getRelative(BlockFace.EAST));
							}
							if (!waterBlocks.contains(waterBlock5.getRelative(BlockFace.NORTH).getRelative(BlockFace.NORTH).getRelative(BlockFace.EAST).getRelative(BlockFace.EAST))) {
								waterBlocks.add(waterBlock5.getRelative(BlockFace.NORTH).getRelative(BlockFace.NORTH).getRelative(BlockFace.EAST).getRelative(BlockFace.EAST));
							}
							if (!waterBlocks.contains(waterBlock5.getRelative(BlockFace.SOUTH))) {
								waterBlocks.add(waterBlock5.getRelative(BlockFace.SOUTH));
							}
							if (!waterBlocks.contains(waterBlock5.getRelative(BlockFace.SOUTH).getRelative(BlockFace.SOUTH))) {
								waterBlocks.add(waterBlock5.getRelative(BlockFace.SOUTH).getRelative(BlockFace.SOUTH));
							}
							if (!waterBlocks.contains(waterBlock5.getRelative(BlockFace.SOUTH).getRelative(BlockFace.SOUTH).getRelative(BlockFace.WEST))) {
								waterBlocks.add(waterBlock5.getRelative(BlockFace.SOUTH).getRelative(BlockFace.SOUTH).getRelative(BlockFace.WEST));
							}
							if (!waterBlocks.contains(waterBlock5.getRelative(BlockFace.SOUTH).getRelative(BlockFace.SOUTH).getRelative(BlockFace.WEST).getRelative(BlockFace.WEST))) {
								waterBlocks.add(waterBlock5.getRelative(BlockFace.SOUTH).getRelative(BlockFace.SOUTH).getRelative(BlockFace.WEST).getRelative(BlockFace.WEST));
							}
							if (!waterBlocks.contains(waterBlock5.getRelative(BlockFace.SOUTH).getRelative(BlockFace.SOUTH).getRelative(BlockFace.EAST))) {
								waterBlocks.add(waterBlock5.getRelative(BlockFace.SOUTH).getRelative(BlockFace.SOUTH).getRelative(BlockFace.EAST));
							}
							if (!waterBlocks.contains(waterBlock5.getRelative(BlockFace.SOUTH).getRelative(BlockFace.SOUTH).getRelative(BlockFace.EAST).getRelative(BlockFace.EAST))) {
								waterBlocks.add(waterBlock5.getRelative(BlockFace.SOUTH).getRelative(BlockFace.SOUTH).getRelative(BlockFace.EAST).getRelative(BlockFace.EAST));
							}
							if (!waterBlocks.contains(waterBlock5.getRelative(BlockFace.WEST))) {
								waterBlocks.add(waterBlock5.getRelative(BlockFace.WEST));
							}
							if (!waterBlocks.contains(waterBlock5.getRelative(BlockFace.WEST).getRelative(BlockFace.WEST))) {
								waterBlocks.add(waterBlock5.getRelative(BlockFace.WEST).getRelative(BlockFace.WEST));
							}
							if (!waterBlocks.contains(waterBlock5.getRelative(BlockFace.NORTH).getRelative(BlockFace.WEST))) {
								waterBlocks.add(waterBlock5.getRelative(BlockFace.NORTH).getRelative(BlockFace.WEST));
							}
							if (!waterBlocks.contains(waterBlock5.getRelative(BlockFace.NORTH).getRelative(BlockFace.WEST).getRelative(BlockFace.WEST))) {
								waterBlocks.add(waterBlock5.getRelative(BlockFace.NORTH).getRelative(BlockFace.WEST).getRelative(BlockFace.WEST));
							}
							if (!waterBlocks.contains(waterBlock5.getRelative(BlockFace.SOUTH).getRelative(BlockFace.WEST))) {
								waterBlocks.add(waterBlock5.getRelative(BlockFace.SOUTH).getRelative(BlockFace.WEST));
							}
							if (!waterBlocks.contains(waterBlock5.getRelative(BlockFace.SOUTH).getRelative(BlockFace.WEST).getRelative(BlockFace.WEST))) {
								waterBlocks.add(waterBlock5.getRelative(BlockFace.SOUTH).getRelative(BlockFace.WEST).getRelative(BlockFace.WEST));
							}
							if (!waterBlocks.contains(waterBlock5.getRelative(BlockFace.EAST))) {
								waterBlocks.add(waterBlock5.getRelative(BlockFace.EAST));
							}
							if (!waterBlocks.contains(waterBlock5.getRelative(BlockFace.EAST).getRelative(BlockFace.EAST))) {
								waterBlocks.add(waterBlock5.getRelative(BlockFace.EAST).getRelative(BlockFace.EAST));
							}
							if (!waterBlocks.contains(waterBlock5.getRelative(BlockFace.NORTH).getRelative(BlockFace.EAST))) {
								waterBlocks.add(waterBlock5.getRelative(BlockFace.NORTH).getRelative(BlockFace.EAST));
							}
							if (!waterBlocks.contains(waterBlock5.getRelative(BlockFace.NORTH).getRelative(BlockFace.EAST).getRelative(BlockFace.EAST))) {
								waterBlocks.add(waterBlock5.getRelative(BlockFace.NORTH).getRelative(BlockFace.EAST).getRelative(BlockFace.EAST));
							}
							if (!waterBlocks.contains(waterBlock5.getRelative(BlockFace.SOUTH).getRelative(BlockFace.EAST))) {
								waterBlocks.add(waterBlock5.getRelative(BlockFace.SOUTH).getRelative(BlockFace.EAST));
							}
							if (!waterBlocks.contains(waterBlock5.getRelative(BlockFace.SOUTH).getRelative(BlockFace.EAST).getRelative(BlockFace.EAST))) {
								waterBlocks.add(waterBlock5.getRelative(BlockFace.SOUTH).getRelative(BlockFace.EAST).getRelative(BlockFace.EAST));
							}

							//INBETWEEN

							if (!waterBlocks.contains(waterBlock6)) {
								waterBlocks.add(waterBlock6);
							}
							if (!waterBlocks.contains(waterBlock6.getRelative(BlockFace.NORTH))) {
								waterBlocks.add(waterBlock6.getRelative(BlockFace.NORTH));
							}
							if (!waterBlocks.contains(waterBlock6.getRelative(BlockFace.NORTH).getRelative(BlockFace.NORTH))) {
								waterBlocks.add(waterBlock6.getRelative(BlockFace.NORTH).getRelative(BlockFace.NORTH));
							}
							if (!waterBlocks.contains(waterBlock6.getRelative(BlockFace.NORTH).getRelative(BlockFace.NORTH).getRelative(BlockFace.WEST))) {
								waterBlocks.add(waterBlock6.getRelative(BlockFace.NORTH).getRelative(BlockFace.NORTH).getRelative(BlockFace.WEST));
							}
							if (!waterBlocks.contains(waterBlock6.getRelative(BlockFace.NORTH).getRelative(BlockFace.NORTH).getRelative(BlockFace.WEST).getRelative(BlockFace.WEST))) {
								waterBlocks.add(waterBlock6.getRelative(BlockFace.NORTH).getRelative(BlockFace.NORTH).getRelative(BlockFace.WEST).getRelative(BlockFace.WEST));
							}
							if (!waterBlocks.contains(waterBlock6.getRelative(BlockFace.NORTH).getRelative(BlockFace.NORTH).getRelative(BlockFace.EAST))) {
								waterBlocks.add(waterBlock6.getRelative(BlockFace.NORTH).getRelative(BlockFace.NORTH).getRelative(BlockFace.EAST));
							}
							if (!waterBlocks.contains(waterBlock6.getRelative(BlockFace.NORTH).getRelative(BlockFace.NORTH).getRelative(BlockFace.EAST).getRelative(BlockFace.EAST))) {
								waterBlocks.add(waterBlock6.getRelative(BlockFace.NORTH).getRelative(BlockFace.NORTH).getRelative(BlockFace.EAST).getRelative(BlockFace.EAST));
							}
							if (!waterBlocks.contains(waterBlock6.getRelative(BlockFace.SOUTH))) {
								waterBlocks.add(waterBlock6.getRelative(BlockFace.SOUTH));
							}
							if (!waterBlocks.contains(waterBlock6.getRelative(BlockFace.SOUTH).getRelative(BlockFace.SOUTH))) {
								waterBlocks.add(waterBlock6.getRelative(BlockFace.SOUTH).getRelative(BlockFace.SOUTH));
							}
							if (!waterBlocks.contains(waterBlock6.getRelative(BlockFace.SOUTH).getRelative(BlockFace.SOUTH).getRelative(BlockFace.WEST))) {
								waterBlocks.add(waterBlock6.getRelative(BlockFace.SOUTH).getRelative(BlockFace.SOUTH).getRelative(BlockFace.WEST));
							}
							if (!waterBlocks.contains(waterBlock6.getRelative(BlockFace.SOUTH).getRelative(BlockFace.SOUTH).getRelative(BlockFace.WEST).getRelative(BlockFace.WEST))) {
								waterBlocks.add(waterBlock6.getRelative(BlockFace.SOUTH).getRelative(BlockFace.SOUTH).getRelative(BlockFace.WEST).getRelative(BlockFace.WEST));
							}
							if (!waterBlocks.contains(waterBlock6.getRelative(BlockFace.SOUTH).getRelative(BlockFace.SOUTH).getRelative(BlockFace.EAST))) {
								waterBlocks.add(waterBlock6.getRelative(BlockFace.SOUTH).getRelative(BlockFace.SOUTH).getRelative(BlockFace.EAST));
							}
							if (!waterBlocks.contains(waterBlock6.getRelative(BlockFace.SOUTH).getRelative(BlockFace.SOUTH).getRelative(BlockFace.EAST).getRelative(BlockFace.EAST))) {
								waterBlocks.add(waterBlock6.getRelative(BlockFace.SOUTH).getRelative(BlockFace.SOUTH).getRelative(BlockFace.EAST).getRelative(BlockFace.EAST));
							}
							if (!waterBlocks.contains(waterBlock6.getRelative(BlockFace.WEST))) {
								waterBlocks.add(waterBlock6.getRelative(BlockFace.WEST));
							}
							if (!waterBlocks.contains(waterBlock6.getRelative(BlockFace.WEST).getRelative(BlockFace.WEST))) {
								waterBlocks.add(waterBlock6.getRelative(BlockFace.WEST).getRelative(BlockFace.WEST));
							}
							if (!waterBlocks.contains(waterBlock6.getRelative(BlockFace.NORTH).getRelative(BlockFace.WEST))) {
								waterBlocks.add(waterBlock6.getRelative(BlockFace.NORTH).getRelative(BlockFace.WEST));
							}
							if (!waterBlocks.contains(waterBlock6.getRelative(BlockFace.NORTH).getRelative(BlockFace.WEST).getRelative(BlockFace.WEST))) {
								waterBlocks.add(waterBlock6.getRelative(BlockFace.NORTH).getRelative(BlockFace.WEST).getRelative(BlockFace.WEST));
							}
							if (!waterBlocks.contains(waterBlock6.getRelative(BlockFace.SOUTH).getRelative(BlockFace.WEST))) {
								waterBlocks.add(waterBlock6.getRelative(BlockFace.SOUTH).getRelative(BlockFace.WEST));
							}
							if (!waterBlocks.contains(waterBlock6.getRelative(BlockFace.SOUTH).getRelative(BlockFace.WEST).getRelative(BlockFace.WEST))) {
								waterBlocks.add(waterBlock6.getRelative(BlockFace.SOUTH).getRelative(BlockFace.WEST).getRelative(BlockFace.WEST));
							}
							if (!waterBlocks.contains(waterBlock6.getRelative(BlockFace.EAST))) {
								waterBlocks.add(waterBlock6.getRelative(BlockFace.EAST));
							}
							if (!waterBlocks.contains(waterBlock6.getRelative(BlockFace.EAST).getRelative(BlockFace.EAST))) {
								waterBlocks.add(waterBlock6.getRelative(BlockFace.EAST).getRelative(BlockFace.EAST));
							}
							if (!waterBlocks.contains(waterBlock6.getRelative(BlockFace.NORTH).getRelative(BlockFace.EAST))) {
								waterBlocks.add(waterBlock6.getRelative(BlockFace.NORTH).getRelative(BlockFace.EAST));
							}
							if (!waterBlocks.contains(waterBlock6.getRelative(BlockFace.NORTH).getRelative(BlockFace.EAST).getRelative(BlockFace.EAST))) {
								waterBlocks.add(waterBlock6.getRelative(BlockFace.NORTH).getRelative(BlockFace.EAST).getRelative(BlockFace.EAST));
							}
							if (!waterBlocks.contains(waterBlock6.getRelative(BlockFace.SOUTH).getRelative(BlockFace.EAST))) {
								waterBlocks.add(waterBlock6.getRelative(BlockFace.SOUTH).getRelative(BlockFace.EAST));
							}
							if (!waterBlocks.contains(waterBlock6.getRelative(BlockFace.SOUTH).getRelative(BlockFace.EAST).getRelative(BlockFace.EAST))) {
								waterBlocks.add(waterBlock6.getRelative(BlockFace.SOUTH).getRelative(BlockFace.EAST).getRelative(BlockFace.EAST));
							}

							//INBETWEEN

							if (!waterBlocks.contains(waterBlock7)) {
								waterBlocks.add(waterBlock7);
							}
							if (!waterBlocks.contains(waterBlock7.getRelative(BlockFace.NORTH))) {
								waterBlocks.add(waterBlock7.getRelative(BlockFace.NORTH));
							}
							if (!waterBlocks.contains(waterBlock7.getRelative(BlockFace.NORTH).getRelative(BlockFace.NORTH))) {
								waterBlocks.add(waterBlock7.getRelative(BlockFace.NORTH).getRelative(BlockFace.NORTH));
							}
							if (!waterBlocks.contains(waterBlock7.getRelative(BlockFace.NORTH).getRelative(BlockFace.NORTH).getRelative(BlockFace.WEST))) {
								waterBlocks.add(waterBlock7.getRelative(BlockFace.NORTH).getRelative(BlockFace.NORTH).getRelative(BlockFace.WEST));
							}
							if (!waterBlocks.contains(waterBlock7.getRelative(BlockFace.NORTH).getRelative(BlockFace.NORTH).getRelative(BlockFace.WEST).getRelative(BlockFace.WEST))) {
								waterBlocks.add(waterBlock7.getRelative(BlockFace.NORTH).getRelative(BlockFace.NORTH).getRelative(BlockFace.WEST).getRelative(BlockFace.WEST));
							}
							if (!waterBlocks.contains(waterBlock7.getRelative(BlockFace.NORTH).getRelative(BlockFace.NORTH).getRelative(BlockFace.EAST))) {
								waterBlocks.add(waterBlock7.getRelative(BlockFace.NORTH).getRelative(BlockFace.NORTH).getRelative(BlockFace.EAST));
							}
							if (!waterBlocks.contains(waterBlock7.getRelative(BlockFace.NORTH).getRelative(BlockFace.NORTH).getRelative(BlockFace.EAST).getRelative(BlockFace.EAST))) {
								waterBlocks.add(waterBlock7.getRelative(BlockFace.NORTH).getRelative(BlockFace.NORTH).getRelative(BlockFace.EAST).getRelative(BlockFace.EAST));
							}
							if (!waterBlocks.contains(waterBlock7.getRelative(BlockFace.SOUTH))) {
								waterBlocks.add(waterBlock7.getRelative(BlockFace.SOUTH));
							}
							if (!waterBlocks.contains(waterBlock7.getRelative(BlockFace.SOUTH).getRelative(BlockFace.SOUTH))) {
								waterBlocks.add(waterBlock7.getRelative(BlockFace.SOUTH).getRelative(BlockFace.SOUTH));
							}
							if (!waterBlocks.contains(waterBlock7.getRelative(BlockFace.SOUTH).getRelative(BlockFace.SOUTH).getRelative(BlockFace.WEST))) {
								waterBlocks.add(waterBlock7.getRelative(BlockFace.SOUTH).getRelative(BlockFace.SOUTH).getRelative(BlockFace.WEST));
							}
							if (!waterBlocks.contains(waterBlock7.getRelative(BlockFace.SOUTH).getRelative(BlockFace.SOUTH).getRelative(BlockFace.WEST).getRelative(BlockFace.WEST))) {
								waterBlocks.add(waterBlock7.getRelative(BlockFace.SOUTH).getRelative(BlockFace.SOUTH).getRelative(BlockFace.WEST).getRelative(BlockFace.WEST));
							}
							if (!waterBlocks.contains(waterBlock7.getRelative(BlockFace.SOUTH).getRelative(BlockFace.SOUTH).getRelative(BlockFace.EAST))) {
								waterBlocks.add(waterBlock7.getRelative(BlockFace.SOUTH).getRelative(BlockFace.SOUTH).getRelative(BlockFace.EAST));
							}
							if (!waterBlocks.contains(waterBlock7.getRelative(BlockFace.SOUTH).getRelative(BlockFace.SOUTH).getRelative(BlockFace.EAST).getRelative(BlockFace.EAST))) {
								waterBlocks.add(waterBlock7.getRelative(BlockFace.SOUTH).getRelative(BlockFace.SOUTH).getRelative(BlockFace.EAST).getRelative(BlockFace.EAST));
							}
							if (!waterBlocks.contains(waterBlock7.getRelative(BlockFace.WEST))) {
								waterBlocks.add(waterBlock7.getRelative(BlockFace.WEST));
							}
							if (!waterBlocks.contains(waterBlock7.getRelative(BlockFace.WEST).getRelative(BlockFace.WEST))) {
								waterBlocks.add(waterBlock7.getRelative(BlockFace.WEST).getRelative(BlockFace.WEST));
							}
							if (!waterBlocks.contains(waterBlock7.getRelative(BlockFace.NORTH).getRelative(BlockFace.WEST))) {
								waterBlocks.add(waterBlock7.getRelative(BlockFace.NORTH).getRelative(BlockFace.WEST));
							}
							if (!waterBlocks.contains(waterBlock7.getRelative(BlockFace.NORTH).getRelative(BlockFace.WEST).getRelative(BlockFace.WEST))) {
								waterBlocks.add(waterBlock7.getRelative(BlockFace.NORTH).getRelative(BlockFace.WEST).getRelative(BlockFace.WEST));
							}
							if (!waterBlocks.contains(waterBlock7.getRelative(BlockFace.SOUTH).getRelative(BlockFace.WEST))) {
								waterBlocks.add(waterBlock7.getRelative(BlockFace.SOUTH).getRelative(BlockFace.WEST));
							}
							if (!waterBlocks.contains(waterBlock7.getRelative(BlockFace.SOUTH).getRelative(BlockFace.WEST).getRelative(BlockFace.WEST))) {
								waterBlocks.add(waterBlock7.getRelative(BlockFace.SOUTH).getRelative(BlockFace.WEST).getRelative(BlockFace.WEST));
							}
							if (!waterBlocks.contains(waterBlock7.getRelative(BlockFace.EAST))) {
								waterBlocks.add(waterBlock7.getRelative(BlockFace.EAST));
							}
							if (!waterBlocks.contains(waterBlock7.getRelative(BlockFace.EAST).getRelative(BlockFace.EAST))) {
								waterBlocks.add(waterBlock7.getRelative(BlockFace.EAST).getRelative(BlockFace.EAST));
							}
							if (!waterBlocks.contains(waterBlock7.getRelative(BlockFace.NORTH).getRelative(BlockFace.EAST))) {
								waterBlocks.add(waterBlock7.getRelative(BlockFace.NORTH).getRelative(BlockFace.EAST));
							}
							if (!waterBlocks.contains(waterBlock7.getRelative(BlockFace.NORTH).getRelative(BlockFace.EAST).getRelative(BlockFace.EAST))) {
								waterBlocks.add(waterBlock7.getRelative(BlockFace.NORTH).getRelative(BlockFace.EAST).getRelative(BlockFace.EAST));
							}
							if (!waterBlocks.contains(waterBlock7.getRelative(BlockFace.SOUTH).getRelative(BlockFace.EAST))) {
								waterBlocks.add(waterBlock7.getRelative(BlockFace.SOUTH).getRelative(BlockFace.EAST));
							}
							if (!waterBlocks.contains(waterBlock7.getRelative(BlockFace.SOUTH).getRelative(BlockFace.EAST).getRelative(BlockFace.EAST))) {
								waterBlocks.add(waterBlock7.getRelative(BlockFace.SOUTH).getRelative(BlockFace.EAST).getRelative(BlockFace.EAST));
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

								double oneAndAHalfPercent = (max/100) * 1.785;
								
								if (LargeTankAssets.waterBlock(waterBlock, current, oneAndAHalfPercent, machine)) {
									
									/*It executes the code inside itself, so no need to put anything here. 
									 * This had to be done like that because of the max byte limit for a java function.
									 * All of them were not done this way because once these 3 were removed, it no longer
									 * exceeded the limit.*/
									break;

								} else if (LargeTankAssets.waterBlock2(waterBlock2, waterBlock, current, oneAndAHalfPercent, machine)) {
									
									/*It executes the code inside itself, so no need to put anything here. 
									 * This had to be done like that because of the max byte limit for a java function.
									 * All of them were not done this way because once these 3 were removed, it no longer
									 * exceeded the limit.*/
									break;

								} else if (LargeTankAssets.waterBlock3(waterBlock3, waterBlock2, waterBlock, current, oneAndAHalfPercent, machine)) {
									
									/*It executes the code inside itself, so no need to put anything here. 
									 * This had to be done like that because of the max byte limit for a java function.
									 * All of them were not done this way because once these 3 were removed, it no longer
									 * exceeded the limit.*/
									break;

								} else if (LargeTankAssets.waterBlock4(waterBlock4, waterBlock3, waterBlock2, waterBlock, current, oneAndAHalfPercent, machine)) {
									
									/*It executes the code inside itself, so no need to put anything here. 
									 * This had to be done like that because of the max byte limit for a java function.
									 * All of them were not done this way because once these 3 were removed, it no longer
									 * exceeded the limit.*/
									break;

								} else if (LargeTankAssets.waterBlock5(waterBlock5, waterBlock4, waterBlock3, waterBlock2, waterBlock, current, oneAndAHalfPercent, machine)) {
									
									/*It executes the code inside itself, so no need to put anything here. 
									 * This had to be done like that because of the max byte limit for a java function.
									 * All of them were not done this way because once these 3 were removed, it no longer
									 * exceeded the limit.*/
									break;
									
								} else if (LargeTankAssets.waterBlock6(waterBlock6, waterBlock5, waterBlock4, waterBlock3, waterBlock2, waterBlock, current, oneAndAHalfPercent, machine)) {
									
									/*It executes the code inside itself, so no need to put anything here. 
									 * This had to be done like that because of the max byte limit for a java function.
									 * All of them were not done this way because once these 3 were removed, it no longer
									 * exceeded the limit.*/
									break;
									
								} else if (LargeTankAssets.waterBlock7(waterBlock7, waterBlock6, waterBlock5, waterBlock4, waterBlock3, waterBlock2, waterBlock, current, oneAndAHalfPercent, machine)) {
									
									/*It executes the code inside itself, so no need to put anything here. 
									 * This had to be done like that because of the max byte limit for a java function.
									 * All of them were not done this way because once these 3 were removed, it no longer
									 * exceeded the limit.*/
									break;
									
								}

							}

							updateBlock(waterBlock.getRelative(BlockFace.NORTH).getRelative(BlockFace.NORTH).getRelative(BlockFace.NORTH));

						}

					}

				}

			}

		});

		for (Player player : SQTechBase.currentGui.keySet()) {

			if (SQTechBase.currentGui.get(player).id == machine.getGUI(player).id) {

				if (player.getOpenInventory() != null) {

					if (player.getOpenInventory().getTitle().equals(ChatColor.BLUE + "SQTech - Large Tank")) {

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
								ChatColor.BLUE + "Amount in millibuckets: " + PumpGUI.format(amount) + "/" + PumpGUI.format(SQTechPumps.config.getInt("large tank max liquid")),
								ChatColor.BLUE + "Amount in buckets: " + PumpGUI.format(amount/1000) + "/" + PumpGUI.format(SQTechPumps.config.getInt("large tank max liquid")/1000),
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

	@SuppressWarnings("deprecation")
	public static void updatePhysicalLiquid(Machine machine) {

		if (machine == null) return;

		Bukkit.getServer().getScheduler().runTask(SQTechPumps.plugin, new BukkitRunnable() {

			@Override
			public void run() {

				if (machine != null) {

					if (machine.getGUIBlock() != null) {

						if (getMiddleBlock(machine.getGUIBlock()) != null &&
								getMiddleBlock(machine.getGUIBlock()).getLocation().add(0, 1, 0).getBlock() != null &&
								getMiddleBlock(machine.getGUIBlock()).getLocation().add(0, 2, 0).getBlock() != null &&
								getMiddleBlock(machine.getGUIBlock()).getLocation().add(0, 3, 0).getBlock() != null &&
								getMiddleBlock(machine.getGUIBlock()).getLocation().add(0, 4, 0).getBlock() != null &&
								getMiddleBlock(machine.getGUIBlock()).getLocation().add(0, 5, 0).getBlock() != null &&
								getMiddleBlock(machine.getGUIBlock()).getLocation().add(0, 6, 0).getBlock() != null &&
								getMiddleBlock(machine.getGUIBlock()).getLocation().add(0, 7, 0).getBlock() != null) {

							Block waterBlock = getMiddleBlock(machine.getGUIBlock());
							Block waterBlock2 = getMiddleBlock(machine.getGUIBlock()).getLocation().add(0, 1, 0).getBlock();
							Block waterBlock3 = getMiddleBlock(machine.getGUIBlock()).getLocation().add(0, 2, 0).getBlock();
							Block waterBlock4 = getMiddleBlock(machine.getGUIBlock()).getLocation().add(0, 3, 0).getBlock();
							Block waterBlock5 = getMiddleBlock(machine.getGUIBlock()).getLocation().add(0, 4, 0).getBlock();
							Block waterBlock6 = getMiddleBlock(machine.getGUIBlock()).getLocation().add(0, 5, 0).getBlock();
							Block waterBlock7 = getMiddleBlock(machine.getGUIBlock()).getLocation().add(0, 6, 0).getBlock();

							List<Block> waterBlocks = new ArrayList<Block>();
							if (SQTechPumps.tankWaterBlocks.get(machine) != null) {
								waterBlocks = SQTechPumps.tankWaterBlocks.get(machine);
							}

							if (!waterBlocks.contains(waterBlock)) {
								waterBlocks.add(waterBlock);
							}
							if (!waterBlocks.contains(waterBlock.getRelative(BlockFace.NORTH))) {
								waterBlocks.add(waterBlock.getRelative(BlockFace.NORTH));
							}
							if (!waterBlocks.contains(waterBlock.getRelative(BlockFace.NORTH).getRelative(BlockFace.NORTH))) {
								waterBlocks.add(waterBlock.getRelative(BlockFace.NORTH).getRelative(BlockFace.NORTH));
							}
							if (!waterBlocks.contains(waterBlock.getRelative(BlockFace.NORTH).getRelative(BlockFace.NORTH).getRelative(BlockFace.WEST))) {
								waterBlocks.add(waterBlock.getRelative(BlockFace.NORTH).getRelative(BlockFace.NORTH).getRelative(BlockFace.WEST));
							}
							if (!waterBlocks.contains(waterBlock.getRelative(BlockFace.NORTH).getRelative(BlockFace.NORTH).getRelative(BlockFace.WEST).getRelative(BlockFace.WEST))) {
								waterBlocks.add(waterBlock.getRelative(BlockFace.NORTH).getRelative(BlockFace.NORTH).getRelative(BlockFace.WEST).getRelative(BlockFace.WEST));
							}
							if (!waterBlocks.contains(waterBlock.getRelative(BlockFace.NORTH).getRelative(BlockFace.NORTH).getRelative(BlockFace.EAST))) {
								waterBlocks.add(waterBlock.getRelative(BlockFace.NORTH).getRelative(BlockFace.NORTH).getRelative(BlockFace.EAST));
							}
							if (!waterBlocks.contains(waterBlock.getRelative(BlockFace.NORTH).getRelative(BlockFace.NORTH).getRelative(BlockFace.EAST).getRelative(BlockFace.EAST))) {
								waterBlocks.add(waterBlock.getRelative(BlockFace.NORTH).getRelative(BlockFace.NORTH).getRelative(BlockFace.EAST).getRelative(BlockFace.EAST));
							}
							if (!waterBlocks.contains(waterBlock.getRelative(BlockFace.SOUTH))) {
								waterBlocks.add(waterBlock.getRelative(BlockFace.SOUTH));
							}
							if (!waterBlocks.contains(waterBlock.getRelative(BlockFace.SOUTH).getRelative(BlockFace.SOUTH))) {
								waterBlocks.add(waterBlock.getRelative(BlockFace.SOUTH).getRelative(BlockFace.SOUTH));
							}
							if (!waterBlocks.contains(waterBlock.getRelative(BlockFace.SOUTH).getRelative(BlockFace.SOUTH).getRelative(BlockFace.WEST))) {
								waterBlocks.add(waterBlock.getRelative(BlockFace.SOUTH).getRelative(BlockFace.SOUTH).getRelative(BlockFace.WEST));
							}
							if (!waterBlocks.contains(waterBlock.getRelative(BlockFace.SOUTH).getRelative(BlockFace.SOUTH).getRelative(BlockFace.WEST).getRelative(BlockFace.WEST))) {
								waterBlocks.add(waterBlock.getRelative(BlockFace.SOUTH).getRelative(BlockFace.SOUTH).getRelative(BlockFace.WEST).getRelative(BlockFace.WEST));
							}
							if (!waterBlocks.contains(waterBlock.getRelative(BlockFace.SOUTH).getRelative(BlockFace.SOUTH).getRelative(BlockFace.EAST))) {
								waterBlocks.add(waterBlock.getRelative(BlockFace.SOUTH).getRelative(BlockFace.SOUTH).getRelative(BlockFace.EAST));
							}
							if (!waterBlocks.contains(waterBlock.getRelative(BlockFace.SOUTH).getRelative(BlockFace.SOUTH).getRelative(BlockFace.EAST).getRelative(BlockFace.EAST))) {
								waterBlocks.add(waterBlock.getRelative(BlockFace.SOUTH).getRelative(BlockFace.SOUTH).getRelative(BlockFace.EAST).getRelative(BlockFace.EAST));
							}
							if (!waterBlocks.contains(waterBlock.getRelative(BlockFace.WEST))) {
								waterBlocks.add(waterBlock.getRelative(BlockFace.WEST));
							}
							if (!waterBlocks.contains(waterBlock.getRelative(BlockFace.WEST).getRelative(BlockFace.WEST))) {
								waterBlocks.add(waterBlock.getRelative(BlockFace.WEST).getRelative(BlockFace.WEST));
							}
							if (!waterBlocks.contains(waterBlock.getRelative(BlockFace.NORTH).getRelative(BlockFace.WEST))) {
								waterBlocks.add(waterBlock.getRelative(BlockFace.NORTH).getRelative(BlockFace.WEST));
							}
							if (!waterBlocks.contains(waterBlock.getRelative(BlockFace.NORTH).getRelative(BlockFace.WEST).getRelative(BlockFace.WEST))) {
								waterBlocks.add(waterBlock.getRelative(BlockFace.NORTH).getRelative(BlockFace.WEST).getRelative(BlockFace.WEST));
							}
							if (!waterBlocks.contains(waterBlock.getRelative(BlockFace.SOUTH).getRelative(BlockFace.WEST))) {
								waterBlocks.add(waterBlock.getRelative(BlockFace.SOUTH).getRelative(BlockFace.WEST));
							}
							if (!waterBlocks.contains(waterBlock.getRelative(BlockFace.SOUTH).getRelative(BlockFace.WEST).getRelative(BlockFace.WEST))) {
								waterBlocks.add(waterBlock.getRelative(BlockFace.SOUTH).getRelative(BlockFace.WEST).getRelative(BlockFace.WEST));
							}
							if (!waterBlocks.contains(waterBlock.getRelative(BlockFace.EAST))) {
								waterBlocks.add(waterBlock.getRelative(BlockFace.EAST));
							}
							if (!waterBlocks.contains(waterBlock.getRelative(BlockFace.EAST).getRelative(BlockFace.EAST))) {
								waterBlocks.add(waterBlock.getRelative(BlockFace.EAST).getRelative(BlockFace.EAST));
							}
							if (!waterBlocks.contains(waterBlock.getRelative(BlockFace.NORTH).getRelative(BlockFace.EAST))) {
								waterBlocks.add(waterBlock.getRelative(BlockFace.NORTH).getRelative(BlockFace.EAST));
							}
							if (!waterBlocks.contains(waterBlock.getRelative(BlockFace.NORTH).getRelative(BlockFace.EAST).getRelative(BlockFace.EAST))) {
								waterBlocks.add(waterBlock.getRelative(BlockFace.NORTH).getRelative(BlockFace.EAST).getRelative(BlockFace.EAST));
							}
							if (!waterBlocks.contains(waterBlock.getRelative(BlockFace.SOUTH).getRelative(BlockFace.EAST))) {
								waterBlocks.add(waterBlock.getRelative(BlockFace.SOUTH).getRelative(BlockFace.EAST));
							}
							if (!waterBlocks.contains(waterBlock.getRelative(BlockFace.SOUTH).getRelative(BlockFace.EAST).getRelative(BlockFace.EAST))) {
								waterBlocks.add(waterBlock.getRelative(BlockFace.SOUTH).getRelative(BlockFace.EAST).getRelative(BlockFace.EAST));
							}

							//INBETWEEN

							if (!waterBlocks.contains(waterBlock2)) {
								waterBlocks.add(waterBlock2);
							}
							if (!waterBlocks.contains(waterBlock2.getRelative(BlockFace.NORTH))) {
								waterBlocks.add(waterBlock2.getRelative(BlockFace.NORTH));
							}
							if (!waterBlocks.contains(waterBlock2.getRelative(BlockFace.NORTH).getRelative(BlockFace.NORTH))) {
								waterBlocks.add(waterBlock2.getRelative(BlockFace.NORTH).getRelative(BlockFace.NORTH));
							}
							if (!waterBlocks.contains(waterBlock2.getRelative(BlockFace.NORTH).getRelative(BlockFace.NORTH).getRelative(BlockFace.WEST))) {
								waterBlocks.add(waterBlock2.getRelative(BlockFace.NORTH).getRelative(BlockFace.NORTH).getRelative(BlockFace.WEST));
							}
							if (!waterBlocks.contains(waterBlock2.getRelative(BlockFace.NORTH).getRelative(BlockFace.NORTH).getRelative(BlockFace.WEST).getRelative(BlockFace.WEST))) {
								waterBlocks.add(waterBlock2.getRelative(BlockFace.NORTH).getRelative(BlockFace.NORTH).getRelative(BlockFace.WEST).getRelative(BlockFace.WEST));
							}
							if (!waterBlocks.contains(waterBlock2.getRelative(BlockFace.NORTH).getRelative(BlockFace.NORTH).getRelative(BlockFace.EAST))) {
								waterBlocks.add(waterBlock2.getRelative(BlockFace.NORTH).getRelative(BlockFace.NORTH).getRelative(BlockFace.EAST));
							}
							if (!waterBlocks.contains(waterBlock2.getRelative(BlockFace.NORTH).getRelative(BlockFace.NORTH).getRelative(BlockFace.EAST).getRelative(BlockFace.EAST))) {
								waterBlocks.add(waterBlock2.getRelative(BlockFace.NORTH).getRelative(BlockFace.NORTH).getRelative(BlockFace.EAST).getRelative(BlockFace.EAST));
							}
							if (!waterBlocks.contains(waterBlock2.getRelative(BlockFace.SOUTH))) {
								waterBlocks.add(waterBlock2.getRelative(BlockFace.SOUTH));
							}
							if (!waterBlocks.contains(waterBlock2.getRelative(BlockFace.SOUTH).getRelative(BlockFace.SOUTH))) {
								waterBlocks.add(waterBlock2.getRelative(BlockFace.SOUTH).getRelative(BlockFace.SOUTH));
							}
							if (!waterBlocks.contains(waterBlock2.getRelative(BlockFace.SOUTH).getRelative(BlockFace.SOUTH).getRelative(BlockFace.WEST))) {
								waterBlocks.add(waterBlock2.getRelative(BlockFace.SOUTH).getRelative(BlockFace.SOUTH).getRelative(BlockFace.WEST));
							}
							if (!waterBlocks.contains(waterBlock2.getRelative(BlockFace.SOUTH).getRelative(BlockFace.SOUTH).getRelative(BlockFace.WEST).getRelative(BlockFace.WEST))) {
								waterBlocks.add(waterBlock2.getRelative(BlockFace.SOUTH).getRelative(BlockFace.SOUTH).getRelative(BlockFace.WEST).getRelative(BlockFace.WEST));
							}
							if (!waterBlocks.contains(waterBlock2.getRelative(BlockFace.SOUTH).getRelative(BlockFace.SOUTH).getRelative(BlockFace.EAST))) {
								waterBlocks.add(waterBlock2.getRelative(BlockFace.SOUTH).getRelative(BlockFace.SOUTH).getRelative(BlockFace.EAST));
							}
							if (!waterBlocks.contains(waterBlock2.getRelative(BlockFace.SOUTH).getRelative(BlockFace.SOUTH).getRelative(BlockFace.EAST).getRelative(BlockFace.EAST))) {
								waterBlocks.add(waterBlock2.getRelative(BlockFace.SOUTH).getRelative(BlockFace.SOUTH).getRelative(BlockFace.EAST).getRelative(BlockFace.EAST));
							}
							if (!waterBlocks.contains(waterBlock2.getRelative(BlockFace.WEST))) {
								waterBlocks.add(waterBlock2.getRelative(BlockFace.WEST));
							}
							if (!waterBlocks.contains(waterBlock2.getRelative(BlockFace.WEST).getRelative(BlockFace.WEST))) {
								waterBlocks.add(waterBlock2.getRelative(BlockFace.WEST).getRelative(BlockFace.WEST));
							}
							if (!waterBlocks.contains(waterBlock2.getRelative(BlockFace.NORTH).getRelative(BlockFace.WEST))) {
								waterBlocks.add(waterBlock2.getRelative(BlockFace.NORTH).getRelative(BlockFace.WEST));
							}
							if (!waterBlocks.contains(waterBlock2.getRelative(BlockFace.NORTH).getRelative(BlockFace.WEST).getRelative(BlockFace.WEST))) {
								waterBlocks.add(waterBlock2.getRelative(BlockFace.NORTH).getRelative(BlockFace.WEST).getRelative(BlockFace.WEST));
							}
							if (!waterBlocks.contains(waterBlock2.getRelative(BlockFace.SOUTH).getRelative(BlockFace.WEST))) {
								waterBlocks.add(waterBlock2.getRelative(BlockFace.SOUTH).getRelative(BlockFace.WEST));
							}
							if (!waterBlocks.contains(waterBlock2.getRelative(BlockFace.SOUTH).getRelative(BlockFace.WEST).getRelative(BlockFace.WEST))) {
								waterBlocks.add(waterBlock2.getRelative(BlockFace.SOUTH).getRelative(BlockFace.WEST).getRelative(BlockFace.WEST));
							}
							if (!waterBlocks.contains(waterBlock2.getRelative(BlockFace.EAST))) {
								waterBlocks.add(waterBlock2.getRelative(BlockFace.EAST));
							}
							if (!waterBlocks.contains(waterBlock2.getRelative(BlockFace.EAST).getRelative(BlockFace.EAST))) {
								waterBlocks.add(waterBlock2.getRelative(BlockFace.EAST).getRelative(BlockFace.EAST));
							}
							if (!waterBlocks.contains(waterBlock2.getRelative(BlockFace.NORTH).getRelative(BlockFace.EAST))) {
								waterBlocks.add(waterBlock2.getRelative(BlockFace.NORTH).getRelative(BlockFace.EAST));
							}
							if (!waterBlocks.contains(waterBlock2.getRelative(BlockFace.NORTH).getRelative(BlockFace.EAST).getRelative(BlockFace.EAST))) {
								waterBlocks.add(waterBlock2.getRelative(BlockFace.NORTH).getRelative(BlockFace.EAST).getRelative(BlockFace.EAST));
							}
							if (!waterBlocks.contains(waterBlock2.getRelative(BlockFace.SOUTH).getRelative(BlockFace.EAST))) {
								waterBlocks.add(waterBlock2.getRelative(BlockFace.SOUTH).getRelative(BlockFace.EAST));
							}
							if (!waterBlocks.contains(waterBlock2.getRelative(BlockFace.SOUTH).getRelative(BlockFace.EAST).getRelative(BlockFace.EAST))) {
								waterBlocks.add(waterBlock2.getRelative(BlockFace.SOUTH).getRelative(BlockFace.EAST).getRelative(BlockFace.EAST));
							}

							//INBETWEEN

							if (!waterBlocks.contains(waterBlock3)) {
								waterBlocks.add(waterBlock3);
							}
							if (!waterBlocks.contains(waterBlock3.getRelative(BlockFace.NORTH))) {
								waterBlocks.add(waterBlock3.getRelative(BlockFace.NORTH));
							}
							if (!waterBlocks.contains(waterBlock3.getRelative(BlockFace.NORTH).getRelative(BlockFace.NORTH))) {
								waterBlocks.add(waterBlock3.getRelative(BlockFace.NORTH).getRelative(BlockFace.NORTH));
							}
							if (!waterBlocks.contains(waterBlock3.getRelative(BlockFace.NORTH).getRelative(BlockFace.NORTH).getRelative(BlockFace.WEST))) {
								waterBlocks.add(waterBlock3.getRelative(BlockFace.NORTH).getRelative(BlockFace.NORTH).getRelative(BlockFace.WEST));
							}
							if (!waterBlocks.contains(waterBlock3.getRelative(BlockFace.NORTH).getRelative(BlockFace.NORTH).getRelative(BlockFace.WEST).getRelative(BlockFace.WEST))) {
								waterBlocks.add(waterBlock3.getRelative(BlockFace.NORTH).getRelative(BlockFace.NORTH).getRelative(BlockFace.WEST).getRelative(BlockFace.WEST));
							}
							if (!waterBlocks.contains(waterBlock3.getRelative(BlockFace.NORTH).getRelative(BlockFace.NORTH).getRelative(BlockFace.EAST))) {
								waterBlocks.add(waterBlock3.getRelative(BlockFace.NORTH).getRelative(BlockFace.NORTH).getRelative(BlockFace.EAST));
							}
							if (!waterBlocks.contains(waterBlock3.getRelative(BlockFace.NORTH).getRelative(BlockFace.NORTH).getRelative(BlockFace.EAST).getRelative(BlockFace.EAST))) {
								waterBlocks.add(waterBlock3.getRelative(BlockFace.NORTH).getRelative(BlockFace.NORTH).getRelative(BlockFace.EAST).getRelative(BlockFace.EAST));
							}
							if (!waterBlocks.contains(waterBlock3.getRelative(BlockFace.SOUTH))) {
								waterBlocks.add(waterBlock3.getRelative(BlockFace.SOUTH));
							}
							if (!waterBlocks.contains(waterBlock3.getRelative(BlockFace.SOUTH).getRelative(BlockFace.SOUTH))) {
								waterBlocks.add(waterBlock3.getRelative(BlockFace.SOUTH).getRelative(BlockFace.SOUTH));
							}
							if (!waterBlocks.contains(waterBlock3.getRelative(BlockFace.SOUTH).getRelative(BlockFace.SOUTH).getRelative(BlockFace.WEST))) {
								waterBlocks.add(waterBlock3.getRelative(BlockFace.SOUTH).getRelative(BlockFace.SOUTH).getRelative(BlockFace.WEST));
							}
							if (!waterBlocks.contains(waterBlock3.getRelative(BlockFace.SOUTH).getRelative(BlockFace.SOUTH).getRelative(BlockFace.WEST).getRelative(BlockFace.WEST))) {
								waterBlocks.add(waterBlock3.getRelative(BlockFace.SOUTH).getRelative(BlockFace.SOUTH).getRelative(BlockFace.WEST).getRelative(BlockFace.WEST));
							}
							if (!waterBlocks.contains(waterBlock3.getRelative(BlockFace.SOUTH).getRelative(BlockFace.SOUTH).getRelative(BlockFace.EAST))) {
								waterBlocks.add(waterBlock3.getRelative(BlockFace.SOUTH).getRelative(BlockFace.SOUTH).getRelative(BlockFace.EAST));
							}
							if (!waterBlocks.contains(waterBlock3.getRelative(BlockFace.SOUTH).getRelative(BlockFace.SOUTH).getRelative(BlockFace.EAST).getRelative(BlockFace.EAST))) {
								waterBlocks.add(waterBlock3.getRelative(BlockFace.SOUTH).getRelative(BlockFace.SOUTH).getRelative(BlockFace.EAST).getRelative(BlockFace.EAST));
							}
							if (!waterBlocks.contains(waterBlock3.getRelative(BlockFace.WEST))) {
								waterBlocks.add(waterBlock3.getRelative(BlockFace.WEST));
							}
							if (!waterBlocks.contains(waterBlock3.getRelative(BlockFace.WEST).getRelative(BlockFace.WEST))) {
								waterBlocks.add(waterBlock3.getRelative(BlockFace.WEST).getRelative(BlockFace.WEST));
							}
							if (!waterBlocks.contains(waterBlock3.getRelative(BlockFace.NORTH).getRelative(BlockFace.WEST))) {
								waterBlocks.add(waterBlock3.getRelative(BlockFace.NORTH).getRelative(BlockFace.WEST));
							}
							if (!waterBlocks.contains(waterBlock3.getRelative(BlockFace.NORTH).getRelative(BlockFace.WEST).getRelative(BlockFace.WEST))) {
								waterBlocks.add(waterBlock3.getRelative(BlockFace.NORTH).getRelative(BlockFace.WEST).getRelative(BlockFace.WEST));
							}
							if (!waterBlocks.contains(waterBlock3.getRelative(BlockFace.SOUTH).getRelative(BlockFace.WEST))) {
								waterBlocks.add(waterBlock3.getRelative(BlockFace.SOUTH).getRelative(BlockFace.WEST));
							}
							if (!waterBlocks.contains(waterBlock3.getRelative(BlockFace.SOUTH).getRelative(BlockFace.WEST).getRelative(BlockFace.WEST))) {
								waterBlocks.add(waterBlock3.getRelative(BlockFace.SOUTH).getRelative(BlockFace.WEST).getRelative(BlockFace.WEST));
							}
							if (!waterBlocks.contains(waterBlock3.getRelative(BlockFace.EAST))) {
								waterBlocks.add(waterBlock3.getRelative(BlockFace.EAST));
							}
							if (!waterBlocks.contains(waterBlock3.getRelative(BlockFace.EAST).getRelative(BlockFace.EAST))) {
								waterBlocks.add(waterBlock3.getRelative(BlockFace.EAST).getRelative(BlockFace.EAST));
							}
							if (!waterBlocks.contains(waterBlock3.getRelative(BlockFace.NORTH).getRelative(BlockFace.EAST))) {
								waterBlocks.add(waterBlock3.getRelative(BlockFace.NORTH).getRelative(BlockFace.EAST));
							}
							if (!waterBlocks.contains(waterBlock3.getRelative(BlockFace.NORTH).getRelative(BlockFace.EAST).getRelative(BlockFace.EAST))) {
								waterBlocks.add(waterBlock3.getRelative(BlockFace.NORTH).getRelative(BlockFace.EAST).getRelative(BlockFace.EAST));
							}
							if (!waterBlocks.contains(waterBlock3.getRelative(BlockFace.SOUTH).getRelative(BlockFace.EAST))) {
								waterBlocks.add(waterBlock3.getRelative(BlockFace.SOUTH).getRelative(BlockFace.EAST));
							}
							if (!waterBlocks.contains(waterBlock3.getRelative(BlockFace.SOUTH).getRelative(BlockFace.EAST).getRelative(BlockFace.EAST))) {
								waterBlocks.add(waterBlock3.getRelative(BlockFace.SOUTH).getRelative(BlockFace.EAST).getRelative(BlockFace.EAST));
							}

							//INBETWEEN

							if (!waterBlocks.contains(waterBlock4)) {
								waterBlocks.add(waterBlock4);
							}
							if (!waterBlocks.contains(waterBlock4.getRelative(BlockFace.NORTH))) {
								waterBlocks.add(waterBlock4.getRelative(BlockFace.NORTH));
							}
							if (!waterBlocks.contains(waterBlock4.getRelative(BlockFace.NORTH).getRelative(BlockFace.NORTH))) {
								waterBlocks.add(waterBlock4.getRelative(BlockFace.NORTH).getRelative(BlockFace.NORTH));
							}
							if (!waterBlocks.contains(waterBlock4.getRelative(BlockFace.NORTH).getRelative(BlockFace.NORTH).getRelative(BlockFace.WEST))) {
								waterBlocks.add(waterBlock4.getRelative(BlockFace.NORTH).getRelative(BlockFace.NORTH).getRelative(BlockFace.WEST));
							}
							if (!waterBlocks.contains(waterBlock4.getRelative(BlockFace.NORTH).getRelative(BlockFace.NORTH).getRelative(BlockFace.WEST).getRelative(BlockFace.WEST))) {
								waterBlocks.add(waterBlock4.getRelative(BlockFace.NORTH).getRelative(BlockFace.NORTH).getRelative(BlockFace.WEST).getRelative(BlockFace.WEST));
							}
							if (!waterBlocks.contains(waterBlock4.getRelative(BlockFace.NORTH).getRelative(BlockFace.NORTH).getRelative(BlockFace.EAST))) {
								waterBlocks.add(waterBlock4.getRelative(BlockFace.NORTH).getRelative(BlockFace.NORTH).getRelative(BlockFace.EAST));
							}
							if (!waterBlocks.contains(waterBlock4.getRelative(BlockFace.NORTH).getRelative(BlockFace.NORTH).getRelative(BlockFace.EAST).getRelative(BlockFace.EAST))) {
								waterBlocks.add(waterBlock4.getRelative(BlockFace.NORTH).getRelative(BlockFace.NORTH).getRelative(BlockFace.EAST).getRelative(BlockFace.EAST));
							}
							if (!waterBlocks.contains(waterBlock4.getRelative(BlockFace.SOUTH))) {
								waterBlocks.add(waterBlock4.getRelative(BlockFace.SOUTH));
							}
							if (!waterBlocks.contains(waterBlock4.getRelative(BlockFace.SOUTH).getRelative(BlockFace.SOUTH))) {
								waterBlocks.add(waterBlock4.getRelative(BlockFace.SOUTH).getRelative(BlockFace.SOUTH));
							}
							if (!waterBlocks.contains(waterBlock4.getRelative(BlockFace.SOUTH).getRelative(BlockFace.SOUTH).getRelative(BlockFace.WEST))) {
								waterBlocks.add(waterBlock4.getRelative(BlockFace.SOUTH).getRelative(BlockFace.SOUTH).getRelative(BlockFace.WEST));
							}
							if (!waterBlocks.contains(waterBlock4.getRelative(BlockFace.SOUTH).getRelative(BlockFace.SOUTH).getRelative(BlockFace.WEST).getRelative(BlockFace.WEST))) {
								waterBlocks.add(waterBlock4.getRelative(BlockFace.SOUTH).getRelative(BlockFace.SOUTH).getRelative(BlockFace.WEST).getRelative(BlockFace.WEST));
							}
							if (!waterBlocks.contains(waterBlock4.getRelative(BlockFace.SOUTH).getRelative(BlockFace.SOUTH).getRelative(BlockFace.EAST))) {
								waterBlocks.add(waterBlock4.getRelative(BlockFace.SOUTH).getRelative(BlockFace.SOUTH).getRelative(BlockFace.EAST));
							}
							if (!waterBlocks.contains(waterBlock4.getRelative(BlockFace.SOUTH).getRelative(BlockFace.SOUTH).getRelative(BlockFace.EAST).getRelative(BlockFace.EAST))) {
								waterBlocks.add(waterBlock4.getRelative(BlockFace.SOUTH).getRelative(BlockFace.SOUTH).getRelative(BlockFace.EAST).getRelative(BlockFace.EAST));
							}
							if (!waterBlocks.contains(waterBlock4.getRelative(BlockFace.WEST))) {
								waterBlocks.add(waterBlock4.getRelative(BlockFace.WEST));
							}
							if (!waterBlocks.contains(waterBlock4.getRelative(BlockFace.WEST).getRelative(BlockFace.WEST))) {
								waterBlocks.add(waterBlock4.getRelative(BlockFace.WEST).getRelative(BlockFace.WEST));
							}
							if (!waterBlocks.contains(waterBlock4.getRelative(BlockFace.NORTH).getRelative(BlockFace.WEST))) {
								waterBlocks.add(waterBlock4.getRelative(BlockFace.NORTH).getRelative(BlockFace.WEST));
							}
							if (!waterBlocks.contains(waterBlock4.getRelative(BlockFace.NORTH).getRelative(BlockFace.WEST).getRelative(BlockFace.WEST))) {
								waterBlocks.add(waterBlock4.getRelative(BlockFace.NORTH).getRelative(BlockFace.WEST).getRelative(BlockFace.WEST));
							}
							if (!waterBlocks.contains(waterBlock4.getRelative(BlockFace.SOUTH).getRelative(BlockFace.WEST))) {
								waterBlocks.add(waterBlock4.getRelative(BlockFace.SOUTH).getRelative(BlockFace.WEST));
							}
							if (!waterBlocks.contains(waterBlock4.getRelative(BlockFace.SOUTH).getRelative(BlockFace.WEST).getRelative(BlockFace.WEST))) {
								waterBlocks.add(waterBlock4.getRelative(BlockFace.SOUTH).getRelative(BlockFace.WEST).getRelative(BlockFace.WEST));
							}
							if (!waterBlocks.contains(waterBlock4.getRelative(BlockFace.EAST))) {
								waterBlocks.add(waterBlock4.getRelative(BlockFace.EAST));
							}
							if (!waterBlocks.contains(waterBlock4.getRelative(BlockFace.EAST).getRelative(BlockFace.EAST))) {
								waterBlocks.add(waterBlock4.getRelative(BlockFace.EAST).getRelative(BlockFace.EAST));
							}
							if (!waterBlocks.contains(waterBlock4.getRelative(BlockFace.NORTH).getRelative(BlockFace.EAST))) {
								waterBlocks.add(waterBlock4.getRelative(BlockFace.NORTH).getRelative(BlockFace.EAST));
							}
							if (!waterBlocks.contains(waterBlock4.getRelative(BlockFace.NORTH).getRelative(BlockFace.EAST).getRelative(BlockFace.EAST))) {
								waterBlocks.add(waterBlock4.getRelative(BlockFace.NORTH).getRelative(BlockFace.EAST).getRelative(BlockFace.EAST));
							}
							if (!waterBlocks.contains(waterBlock4.getRelative(BlockFace.SOUTH).getRelative(BlockFace.EAST))) {
								waterBlocks.add(waterBlock4.getRelative(BlockFace.SOUTH).getRelative(BlockFace.EAST));
							}
							if (!waterBlocks.contains(waterBlock4.getRelative(BlockFace.SOUTH).getRelative(BlockFace.EAST).getRelative(BlockFace.EAST))) {
								waterBlocks.add(waterBlock4.getRelative(BlockFace.SOUTH).getRelative(BlockFace.EAST).getRelative(BlockFace.EAST));
							}

							//INBETWEEN

							if (!waterBlocks.contains(waterBlock5)) {
								waterBlocks.add(waterBlock5);
							}
							if (!waterBlocks.contains(waterBlock5.getRelative(BlockFace.NORTH))) {
								waterBlocks.add(waterBlock5.getRelative(BlockFace.NORTH));
							}
							if (!waterBlocks.contains(waterBlock5.getRelative(BlockFace.NORTH).getRelative(BlockFace.NORTH))) {
								waterBlocks.add(waterBlock5.getRelative(BlockFace.NORTH).getRelative(BlockFace.NORTH));
							}
							if (!waterBlocks.contains(waterBlock5.getRelative(BlockFace.NORTH).getRelative(BlockFace.NORTH).getRelative(BlockFace.WEST))) {
								waterBlocks.add(waterBlock5.getRelative(BlockFace.NORTH).getRelative(BlockFace.NORTH).getRelative(BlockFace.WEST));
							}
							if (!waterBlocks.contains(waterBlock5.getRelative(BlockFace.NORTH).getRelative(BlockFace.NORTH).getRelative(BlockFace.WEST).getRelative(BlockFace.WEST))) {
								waterBlocks.add(waterBlock5.getRelative(BlockFace.NORTH).getRelative(BlockFace.NORTH).getRelative(BlockFace.WEST).getRelative(BlockFace.WEST));
							}
							if (!waterBlocks.contains(waterBlock5.getRelative(BlockFace.NORTH).getRelative(BlockFace.NORTH).getRelative(BlockFace.EAST))) {
								waterBlocks.add(waterBlock5.getRelative(BlockFace.NORTH).getRelative(BlockFace.NORTH).getRelative(BlockFace.EAST));
							}
							if (!waterBlocks.contains(waterBlock5.getRelative(BlockFace.NORTH).getRelative(BlockFace.NORTH).getRelative(BlockFace.EAST).getRelative(BlockFace.EAST))) {
								waterBlocks.add(waterBlock5.getRelative(BlockFace.NORTH).getRelative(BlockFace.NORTH).getRelative(BlockFace.EAST).getRelative(BlockFace.EAST));
							}
							if (!waterBlocks.contains(waterBlock5.getRelative(BlockFace.SOUTH))) {
								waterBlocks.add(waterBlock5.getRelative(BlockFace.SOUTH));
							}
							if (!waterBlocks.contains(waterBlock5.getRelative(BlockFace.SOUTH).getRelative(BlockFace.SOUTH))) {
								waterBlocks.add(waterBlock5.getRelative(BlockFace.SOUTH).getRelative(BlockFace.SOUTH));
							}
							if (!waterBlocks.contains(waterBlock5.getRelative(BlockFace.SOUTH).getRelative(BlockFace.SOUTH).getRelative(BlockFace.WEST))) {
								waterBlocks.add(waterBlock5.getRelative(BlockFace.SOUTH).getRelative(BlockFace.SOUTH).getRelative(BlockFace.WEST));
							}
							if (!waterBlocks.contains(waterBlock5.getRelative(BlockFace.SOUTH).getRelative(BlockFace.SOUTH).getRelative(BlockFace.WEST).getRelative(BlockFace.WEST))) {
								waterBlocks.add(waterBlock5.getRelative(BlockFace.SOUTH).getRelative(BlockFace.SOUTH).getRelative(BlockFace.WEST).getRelative(BlockFace.WEST));
							}
							if (!waterBlocks.contains(waterBlock5.getRelative(BlockFace.SOUTH).getRelative(BlockFace.SOUTH).getRelative(BlockFace.EAST))) {
								waterBlocks.add(waterBlock5.getRelative(BlockFace.SOUTH).getRelative(BlockFace.SOUTH).getRelative(BlockFace.EAST));
							}
							if (!waterBlocks.contains(waterBlock5.getRelative(BlockFace.SOUTH).getRelative(BlockFace.SOUTH).getRelative(BlockFace.EAST).getRelative(BlockFace.EAST))) {
								waterBlocks.add(waterBlock5.getRelative(BlockFace.SOUTH).getRelative(BlockFace.SOUTH).getRelative(BlockFace.EAST).getRelative(BlockFace.EAST));
							}
							if (!waterBlocks.contains(waterBlock5.getRelative(BlockFace.WEST))) {
								waterBlocks.add(waterBlock5.getRelative(BlockFace.WEST));
							}
							if (!waterBlocks.contains(waterBlock5.getRelative(BlockFace.WEST).getRelative(BlockFace.WEST))) {
								waterBlocks.add(waterBlock5.getRelative(BlockFace.WEST).getRelative(BlockFace.WEST));
							}
							if (!waterBlocks.contains(waterBlock5.getRelative(BlockFace.NORTH).getRelative(BlockFace.WEST))) {
								waterBlocks.add(waterBlock5.getRelative(BlockFace.NORTH).getRelative(BlockFace.WEST));
							}
							if (!waterBlocks.contains(waterBlock5.getRelative(BlockFace.NORTH).getRelative(BlockFace.WEST).getRelative(BlockFace.WEST))) {
								waterBlocks.add(waterBlock5.getRelative(BlockFace.NORTH).getRelative(BlockFace.WEST).getRelative(BlockFace.WEST));
							}
							if (!waterBlocks.contains(waterBlock5.getRelative(BlockFace.SOUTH).getRelative(BlockFace.WEST))) {
								waterBlocks.add(waterBlock5.getRelative(BlockFace.SOUTH).getRelative(BlockFace.WEST));
							}
							if (!waterBlocks.contains(waterBlock5.getRelative(BlockFace.SOUTH).getRelative(BlockFace.WEST).getRelative(BlockFace.WEST))) {
								waterBlocks.add(waterBlock5.getRelative(BlockFace.SOUTH).getRelative(BlockFace.WEST).getRelative(BlockFace.WEST));
							}
							if (!waterBlocks.contains(waterBlock5.getRelative(BlockFace.EAST))) {
								waterBlocks.add(waterBlock5.getRelative(BlockFace.EAST));
							}
							if (!waterBlocks.contains(waterBlock5.getRelative(BlockFace.EAST).getRelative(BlockFace.EAST))) {
								waterBlocks.add(waterBlock5.getRelative(BlockFace.EAST).getRelative(BlockFace.EAST));
							}
							if (!waterBlocks.contains(waterBlock5.getRelative(BlockFace.NORTH).getRelative(BlockFace.EAST))) {
								waterBlocks.add(waterBlock5.getRelative(BlockFace.NORTH).getRelative(BlockFace.EAST));
							}
							if (!waterBlocks.contains(waterBlock5.getRelative(BlockFace.NORTH).getRelative(BlockFace.EAST).getRelative(BlockFace.EAST))) {
								waterBlocks.add(waterBlock5.getRelative(BlockFace.NORTH).getRelative(BlockFace.EAST).getRelative(BlockFace.EAST));
							}
							if (!waterBlocks.contains(waterBlock5.getRelative(BlockFace.SOUTH).getRelative(BlockFace.EAST))) {
								waterBlocks.add(waterBlock5.getRelative(BlockFace.SOUTH).getRelative(BlockFace.EAST));
							}
							if (!waterBlocks.contains(waterBlock5.getRelative(BlockFace.SOUTH).getRelative(BlockFace.EAST).getRelative(BlockFace.EAST))) {
								waterBlocks.add(waterBlock5.getRelative(BlockFace.SOUTH).getRelative(BlockFace.EAST).getRelative(BlockFace.EAST));
							}

							//INBETWEEN

							if (!waterBlocks.contains(waterBlock6)) {
								waterBlocks.add(waterBlock6);
							}
							if (!waterBlocks.contains(waterBlock6.getRelative(BlockFace.NORTH))) {
								waterBlocks.add(waterBlock6.getRelative(BlockFace.NORTH));
							}
							if (!waterBlocks.contains(waterBlock6.getRelative(BlockFace.NORTH).getRelative(BlockFace.NORTH))) {
								waterBlocks.add(waterBlock6.getRelative(BlockFace.NORTH).getRelative(BlockFace.NORTH));
							}
							if (!waterBlocks.contains(waterBlock6.getRelative(BlockFace.NORTH).getRelative(BlockFace.NORTH).getRelative(BlockFace.WEST))) {
								waterBlocks.add(waterBlock6.getRelative(BlockFace.NORTH).getRelative(BlockFace.NORTH).getRelative(BlockFace.WEST));
							}
							if (!waterBlocks.contains(waterBlock6.getRelative(BlockFace.NORTH).getRelative(BlockFace.NORTH).getRelative(BlockFace.WEST).getRelative(BlockFace.WEST))) {
								waterBlocks.add(waterBlock6.getRelative(BlockFace.NORTH).getRelative(BlockFace.NORTH).getRelative(BlockFace.WEST).getRelative(BlockFace.WEST));
							}
							if (!waterBlocks.contains(waterBlock6.getRelative(BlockFace.NORTH).getRelative(BlockFace.NORTH).getRelative(BlockFace.EAST))) {
								waterBlocks.add(waterBlock6.getRelative(BlockFace.NORTH).getRelative(BlockFace.NORTH).getRelative(BlockFace.EAST));
							}
							if (!waterBlocks.contains(waterBlock6.getRelative(BlockFace.NORTH).getRelative(BlockFace.NORTH).getRelative(BlockFace.EAST).getRelative(BlockFace.EAST))) {
								waterBlocks.add(waterBlock6.getRelative(BlockFace.NORTH).getRelative(BlockFace.NORTH).getRelative(BlockFace.EAST).getRelative(BlockFace.EAST));
							}
							if (!waterBlocks.contains(waterBlock6.getRelative(BlockFace.SOUTH))) {
								waterBlocks.add(waterBlock6.getRelative(BlockFace.SOUTH));
							}
							if (!waterBlocks.contains(waterBlock6.getRelative(BlockFace.SOUTH).getRelative(BlockFace.SOUTH))) {
								waterBlocks.add(waterBlock6.getRelative(BlockFace.SOUTH).getRelative(BlockFace.SOUTH));
							}
							if (!waterBlocks.contains(waterBlock6.getRelative(BlockFace.SOUTH).getRelative(BlockFace.SOUTH).getRelative(BlockFace.WEST))) {
								waterBlocks.add(waterBlock6.getRelative(BlockFace.SOUTH).getRelative(BlockFace.SOUTH).getRelative(BlockFace.WEST));
							}
							if (!waterBlocks.contains(waterBlock6.getRelative(BlockFace.SOUTH).getRelative(BlockFace.SOUTH).getRelative(BlockFace.WEST).getRelative(BlockFace.WEST))) {
								waterBlocks.add(waterBlock6.getRelative(BlockFace.SOUTH).getRelative(BlockFace.SOUTH).getRelative(BlockFace.WEST).getRelative(BlockFace.WEST));
							}
							if (!waterBlocks.contains(waterBlock6.getRelative(BlockFace.SOUTH).getRelative(BlockFace.SOUTH).getRelative(BlockFace.EAST))) {
								waterBlocks.add(waterBlock6.getRelative(BlockFace.SOUTH).getRelative(BlockFace.SOUTH).getRelative(BlockFace.EAST));
							}
							if (!waterBlocks.contains(waterBlock6.getRelative(BlockFace.SOUTH).getRelative(BlockFace.SOUTH).getRelative(BlockFace.EAST).getRelative(BlockFace.EAST))) {
								waterBlocks.add(waterBlock6.getRelative(BlockFace.SOUTH).getRelative(BlockFace.SOUTH).getRelative(BlockFace.EAST).getRelative(BlockFace.EAST));
							}
							if (!waterBlocks.contains(waterBlock6.getRelative(BlockFace.WEST))) {
								waterBlocks.add(waterBlock6.getRelative(BlockFace.WEST));
							}
							if (!waterBlocks.contains(waterBlock6.getRelative(BlockFace.WEST).getRelative(BlockFace.WEST))) {
								waterBlocks.add(waterBlock6.getRelative(BlockFace.WEST).getRelative(BlockFace.WEST));
							}
							if (!waterBlocks.contains(waterBlock6.getRelative(BlockFace.NORTH).getRelative(BlockFace.WEST))) {
								waterBlocks.add(waterBlock6.getRelative(BlockFace.NORTH).getRelative(BlockFace.WEST));
							}
							if (!waterBlocks.contains(waterBlock6.getRelative(BlockFace.NORTH).getRelative(BlockFace.WEST).getRelative(BlockFace.WEST))) {
								waterBlocks.add(waterBlock6.getRelative(BlockFace.NORTH).getRelative(BlockFace.WEST).getRelative(BlockFace.WEST));
							}
							if (!waterBlocks.contains(waterBlock6.getRelative(BlockFace.SOUTH).getRelative(BlockFace.WEST))) {
								waterBlocks.add(waterBlock6.getRelative(BlockFace.SOUTH).getRelative(BlockFace.WEST));
							}
							if (!waterBlocks.contains(waterBlock6.getRelative(BlockFace.SOUTH).getRelative(BlockFace.WEST).getRelative(BlockFace.WEST))) {
								waterBlocks.add(waterBlock6.getRelative(BlockFace.SOUTH).getRelative(BlockFace.WEST).getRelative(BlockFace.WEST));
							}
							if (!waterBlocks.contains(waterBlock6.getRelative(BlockFace.EAST))) {
								waterBlocks.add(waterBlock6.getRelative(BlockFace.EAST));
							}
							if (!waterBlocks.contains(waterBlock6.getRelative(BlockFace.EAST).getRelative(BlockFace.EAST))) {
								waterBlocks.add(waterBlock6.getRelative(BlockFace.EAST).getRelative(BlockFace.EAST));
							}
							if (!waterBlocks.contains(waterBlock6.getRelative(BlockFace.NORTH).getRelative(BlockFace.EAST))) {
								waterBlocks.add(waterBlock6.getRelative(BlockFace.NORTH).getRelative(BlockFace.EAST));
							}
							if (!waterBlocks.contains(waterBlock6.getRelative(BlockFace.NORTH).getRelative(BlockFace.EAST).getRelative(BlockFace.EAST))) {
								waterBlocks.add(waterBlock6.getRelative(BlockFace.NORTH).getRelative(BlockFace.EAST).getRelative(BlockFace.EAST));
							}
							if (!waterBlocks.contains(waterBlock6.getRelative(BlockFace.SOUTH).getRelative(BlockFace.EAST))) {
								waterBlocks.add(waterBlock6.getRelative(BlockFace.SOUTH).getRelative(BlockFace.EAST));
							}
							if (!waterBlocks.contains(waterBlock6.getRelative(BlockFace.SOUTH).getRelative(BlockFace.EAST).getRelative(BlockFace.EAST))) {
								waterBlocks.add(waterBlock6.getRelative(BlockFace.SOUTH).getRelative(BlockFace.EAST).getRelative(BlockFace.EAST));
							}

							//INBETWEEN

							if (!waterBlocks.contains(waterBlock7)) {
								waterBlocks.add(waterBlock7);
							}
							if (!waterBlocks.contains(waterBlock7.getRelative(BlockFace.NORTH))) {
								waterBlocks.add(waterBlock7.getRelative(BlockFace.NORTH));
							}
							if (!waterBlocks.contains(waterBlock7.getRelative(BlockFace.NORTH).getRelative(BlockFace.NORTH))) {
								waterBlocks.add(waterBlock7.getRelative(BlockFace.NORTH).getRelative(BlockFace.NORTH));
							}
							if (!waterBlocks.contains(waterBlock7.getRelative(BlockFace.NORTH).getRelative(BlockFace.NORTH).getRelative(BlockFace.WEST))) {
								waterBlocks.add(waterBlock7.getRelative(BlockFace.NORTH).getRelative(BlockFace.NORTH).getRelative(BlockFace.WEST));
							}
							if (!waterBlocks.contains(waterBlock7.getRelative(BlockFace.NORTH).getRelative(BlockFace.NORTH).getRelative(BlockFace.WEST).getRelative(BlockFace.WEST))) {
								waterBlocks.add(waterBlock7.getRelative(BlockFace.NORTH).getRelative(BlockFace.NORTH).getRelative(BlockFace.WEST).getRelative(BlockFace.WEST));
							}
							if (!waterBlocks.contains(waterBlock7.getRelative(BlockFace.NORTH).getRelative(BlockFace.NORTH).getRelative(BlockFace.EAST))) {
								waterBlocks.add(waterBlock7.getRelative(BlockFace.NORTH).getRelative(BlockFace.NORTH).getRelative(BlockFace.EAST));
							}
							if (!waterBlocks.contains(waterBlock7.getRelative(BlockFace.NORTH).getRelative(BlockFace.NORTH).getRelative(BlockFace.EAST).getRelative(BlockFace.EAST))) {
								waterBlocks.add(waterBlock7.getRelative(BlockFace.NORTH).getRelative(BlockFace.NORTH).getRelative(BlockFace.EAST).getRelative(BlockFace.EAST));
							}
							if (!waterBlocks.contains(waterBlock7.getRelative(BlockFace.SOUTH))) {
								waterBlocks.add(waterBlock7.getRelative(BlockFace.SOUTH));
							}
							if (!waterBlocks.contains(waterBlock7.getRelative(BlockFace.SOUTH).getRelative(BlockFace.SOUTH))) {
								waterBlocks.add(waterBlock7.getRelative(BlockFace.SOUTH).getRelative(BlockFace.SOUTH));
							}
							if (!waterBlocks.contains(waterBlock7.getRelative(BlockFace.SOUTH).getRelative(BlockFace.SOUTH).getRelative(BlockFace.WEST))) {
								waterBlocks.add(waterBlock7.getRelative(BlockFace.SOUTH).getRelative(BlockFace.SOUTH).getRelative(BlockFace.WEST));
							}
							if (!waterBlocks.contains(waterBlock7.getRelative(BlockFace.SOUTH).getRelative(BlockFace.SOUTH).getRelative(BlockFace.WEST).getRelative(BlockFace.WEST))) {
								waterBlocks.add(waterBlock7.getRelative(BlockFace.SOUTH).getRelative(BlockFace.SOUTH).getRelative(BlockFace.WEST).getRelative(BlockFace.WEST));
							}
							if (!waterBlocks.contains(waterBlock7.getRelative(BlockFace.SOUTH).getRelative(BlockFace.SOUTH).getRelative(BlockFace.EAST))) {
								waterBlocks.add(waterBlock7.getRelative(BlockFace.SOUTH).getRelative(BlockFace.SOUTH).getRelative(BlockFace.EAST));
							}
							if (!waterBlocks.contains(waterBlock7.getRelative(BlockFace.SOUTH).getRelative(BlockFace.SOUTH).getRelative(BlockFace.EAST).getRelative(BlockFace.EAST))) {
								waterBlocks.add(waterBlock7.getRelative(BlockFace.SOUTH).getRelative(BlockFace.SOUTH).getRelative(BlockFace.EAST).getRelative(BlockFace.EAST));
							}
							if (!waterBlocks.contains(waterBlock7.getRelative(BlockFace.WEST))) {
								waterBlocks.add(waterBlock7.getRelative(BlockFace.WEST));
							}
							if (!waterBlocks.contains(waterBlock7.getRelative(BlockFace.WEST).getRelative(BlockFace.WEST))) {
								waterBlocks.add(waterBlock7.getRelative(BlockFace.WEST).getRelative(BlockFace.WEST));
							}
							if (!waterBlocks.contains(waterBlock7.getRelative(BlockFace.NORTH).getRelative(BlockFace.WEST))) {
								waterBlocks.add(waterBlock7.getRelative(BlockFace.NORTH).getRelative(BlockFace.WEST));
							}
							if (!waterBlocks.contains(waterBlock7.getRelative(BlockFace.NORTH).getRelative(BlockFace.WEST).getRelative(BlockFace.WEST))) {
								waterBlocks.add(waterBlock7.getRelative(BlockFace.NORTH).getRelative(BlockFace.WEST).getRelative(BlockFace.WEST));
							}
							if (!waterBlocks.contains(waterBlock7.getRelative(BlockFace.SOUTH).getRelative(BlockFace.WEST))) {
								waterBlocks.add(waterBlock7.getRelative(BlockFace.SOUTH).getRelative(BlockFace.WEST));
							}
							if (!waterBlocks.contains(waterBlock7.getRelative(BlockFace.SOUTH).getRelative(BlockFace.WEST).getRelative(BlockFace.WEST))) {
								waterBlocks.add(waterBlock7.getRelative(BlockFace.SOUTH).getRelative(BlockFace.WEST).getRelative(BlockFace.WEST));
							}
							if (!waterBlocks.contains(waterBlock7.getRelative(BlockFace.EAST))) {
								waterBlocks.add(waterBlock7.getRelative(BlockFace.EAST));
							}
							if (!waterBlocks.contains(waterBlock7.getRelative(BlockFace.EAST).getRelative(BlockFace.EAST))) {
								waterBlocks.add(waterBlock7.getRelative(BlockFace.EAST).getRelative(BlockFace.EAST));
							}
							if (!waterBlocks.contains(waterBlock7.getRelative(BlockFace.NORTH).getRelative(BlockFace.EAST))) {
								waterBlocks.add(waterBlock7.getRelative(BlockFace.NORTH).getRelative(BlockFace.EAST));
							}
							if (!waterBlocks.contains(waterBlock7.getRelative(BlockFace.NORTH).getRelative(BlockFace.EAST).getRelative(BlockFace.EAST))) {
								waterBlocks.add(waterBlock7.getRelative(BlockFace.NORTH).getRelative(BlockFace.EAST).getRelative(BlockFace.EAST));
							}
							if (!waterBlocks.contains(waterBlock7.getRelative(BlockFace.SOUTH).getRelative(BlockFace.EAST))) {
								waterBlocks.add(waterBlock7.getRelative(BlockFace.SOUTH).getRelative(BlockFace.EAST));
							}
							if (!waterBlocks.contains(waterBlock7.getRelative(BlockFace.SOUTH).getRelative(BlockFace.EAST).getRelative(BlockFace.EAST))) {
								waterBlocks.add(waterBlock7.getRelative(BlockFace.SOUTH).getRelative(BlockFace.EAST).getRelative(BlockFace.EAST));
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

								double oneAndAHalfPercent = (max/100) * 1.785;

								if (LargeTankAssets.waterBlock(waterBlock, current, oneAndAHalfPercent, machine)) {

									/*It executes the code inside itself, so no need to put anything here. 
									 * This had to be done like that because of the max byte limit for a java function.
									 * All of them were not done this way because once these 3 were removed, it no longer
									 * exceeded the limit.*/

								} else if (LargeTankAssets.waterBlock2(waterBlock2, waterBlock, current, oneAndAHalfPercent, machine)) {

									/*It executes the code inside itself, so no need to put anything here. 
									 * This had to be done like that because of the max byte limit for a java function.
									 * All of them were not done this way because once these 3 were removed, it no longer
									 * exceeded the limit.*/

								} else if (LargeTankAssets.waterBlock3(waterBlock3, waterBlock2, waterBlock, current, oneAndAHalfPercent, machine)) {

									/*It executes the code inside itself, so no need to put anything here. 
									 * This had to be done like that because of the max byte limit for a java function.
									 * All of them were not done this way because once these 3 were removed, it no longer
									 * exceeded the limit.*/

								} else if (LargeTankAssets.waterBlock4(waterBlock4, waterBlock3, waterBlock2, waterBlock7, current, oneAndAHalfPercent, machine)) {

									/*It executes the code inside itself, so no need to put anything here. 
									 * This had to be done like that because of the max byte limit for a java function.
									 * All of them were not done this way because once these 3 were removed, it no longer
									 * exceeded the limit.*/

								} else if (LargeTankAssets.waterBlock5(waterBlock5, waterBlock4, waterBlock3, waterBlock2, waterBlock, current, oneAndAHalfPercent, machine)) {
									
									/*It executes the code inside itself, so no need to put anything here. 
									 * This had to be done like that because of the max byte limit for a java function.
									 * All of them were not done this way because once these 3 were removed, it no longer
									 * exceeded the limit.*/
									
								} else if (LargeTankAssets.waterBlock6(waterBlock6, waterBlock5, waterBlock4, waterBlock3, waterBlock2, waterBlock, current, oneAndAHalfPercent, machine)) {
									
									
									
								} else if (LargeTankAssets.waterBlock7(waterBlock7, waterBlock6, waterBlock5, waterBlock4, waterBlock3, waterBlock2, waterBlock, current, oneAndAHalfPercent, machine)) {
									
									/*It executes the code inside itself, so no need to put anything here. 
									 * This had to be done like that because of the max byte limit for a java function.
									 * All of them were not done this way because once these 3 were removed, it no longer
									 * exceeded the limit.*/
									
								}

								updateBlock(waterBlock.getRelative(BlockFace.NORTH).getRelative(BlockFace.NORTH).getRelative(BlockFace.NORTH));

							}

						}

					}

				}

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
			Block firstLevel5 = firstLevel.getRelative(f).getRelative(f).getRelative(f).getRelative(f).getRelative(f);
			Block firstLevel6 = firstLevel.getRelative(f).getRelative(f).getRelative(f).getRelative(f).getRelative(f).getRelative(f);

			Block secondLevel = firstLevel.getRelative(up);
			Block secondLevel1 = secondLevel.getRelative(f);
			Block secondLevel2 = secondLevel.getRelative(f).getRelative(f);
			Block secondLevel3 = secondLevel.getRelative(f).getRelative(f).getRelative(f);
			Block secondLevel4 = secondLevel.getRelative(f).getRelative(f).getRelative(f).getRelative(f);
			Block secondLevel5 = secondLevel.getRelative(f).getRelative(f).getRelative(f).getRelative(f).getRelative(f);
			Block secondLevel6 = secondLevel.getRelative(f).getRelative(f).getRelative(f).getRelative(f).getRelative(f).getRelative(f);

			Block thirdLevel = secondLevel.getRelative(up);
			Block thirdLevel1 = thirdLevel.getRelative(f);
			Block thirdLevel2 = thirdLevel.getRelative(f).getRelative(f);
			Block thirdLevel3 = thirdLevel.getRelative(f).getRelative(f).getRelative(f);
			Block thirdLevel4 = thirdLevel.getRelative(f).getRelative(f).getRelative(f).getRelative(f);
			Block thirdLevel5 = thirdLevel.getRelative(f).getRelative(f).getRelative(f).getRelative(f).getRelative(f);
			Block thirdLevel6 = thirdLevel.getRelative(f).getRelative(f).getRelative(f).getRelative(f).getRelative(f).getRelative(f);

			Block fourthLevel = thirdLevel.getRelative(up);
			Block fourthLevel1 = fourthLevel.getRelative(f);
			Block fourthLevel2 = fourthLevel.getRelative(f).getRelative(f);
			Block fourthLevel3 = fourthLevel.getRelative(f).getRelative(f).getRelative(f);
			Block fourthLevel4 = fourthLevel.getRelative(f).getRelative(f).getRelative(f).getRelative(f);
			Block fourthLevel5 = fourthLevel.getRelative(f).getRelative(f).getRelative(f).getRelative(f).getRelative(f);
			Block fourthLevel6 = fourthLevel.getRelative(f).getRelative(f).getRelative(f).getRelative(f).getRelative(f).getRelative(f);

			Block fifthLevel = fourthLevel.getRelative(up);
			Block fifthLevel1 = fifthLevel.getRelative(f);
			Block fifthLevel2 = fifthLevel.getRelative(f).getRelative(f);
			Block fifthLevel3 = fifthLevel.getRelative(f).getRelative(f).getRelative(f);
			Block fifthLevel4 = fifthLevel.getRelative(f).getRelative(f).getRelative(f).getRelative(f);
			Block fifthLevel5 = fifthLevel.getRelative(f).getRelative(f).getRelative(f).getRelative(f).getRelative(f);
			Block fifthLevel6 = fifthLevel.getRelative(f).getRelative(f).getRelative(f).getRelative(f).getRelative(f).getRelative(f);

			Block sixthLevel = fifthLevel.getRelative(up);
			Block sixthLevel1 = sixthLevel.getRelative(f);
			Block sixthLevel2 = sixthLevel.getRelative(f).getRelative(f);
			Block sixthLevel3 = sixthLevel.getRelative(f).getRelative(f).getRelative(f);
			Block sixthLevel4 = sixthLevel.getRelative(f).getRelative(f).getRelative(f).getRelative(f);
			Block sixthLevel5 = sixthLevel.getRelative(f).getRelative(f).getRelative(f).getRelative(f).getRelative(f);
			Block sixthLevel6 = sixthLevel.getRelative(f).getRelative(f).getRelative(f).getRelative(f).getRelative(f).getRelative(f);

			Block seventhLevel = sixthLevel.getRelative(up);
			Block seventhLevel1 = seventhLevel.getRelative(f);
			Block seventhLevel2 = seventhLevel.getRelative(f).getRelative(f);
			Block seventhLevel3 = seventhLevel.getRelative(f).getRelative(f).getRelative(f);
			Block seventhLevel4 = seventhLevel.getRelative(f).getRelative(f).getRelative(f).getRelative(f);
			Block seventhLevel5 = seventhLevel.getRelative(f).getRelative(f).getRelative(f).getRelative(f).getRelative(f);
			Block seventhLevel6 = seventhLevel.getRelative(f).getRelative(f).getRelative(f).getRelative(f).getRelative(f).getRelative(f);

			Block eightLevel = seventhLevel.getRelative(up);
			Block eightLevel1 = eightLevel.getRelative(f);
			Block eightLevel2 = eightLevel.getRelative(f).getRelative(f);
			Block eightLevel3 = eightLevel.getRelative(f).getRelative(f).getRelative(f);
			Block eightLevel4 = eightLevel.getRelative(f).getRelative(f).getRelative(f).getRelative(f);
			Block eightLevel5 = eightLevel.getRelative(f).getRelative(f).getRelative(f).getRelative(f).getRelative(f);
			Block eightLevel6 = eightLevel.getRelative(f).getRelative(f).getRelative(f).getRelative(f).getRelative(f).getRelative(f);

			Block ninthLevel = eightLevel.getRelative(up);
			Block ninthLevel1 = ninthLevel.getRelative(f);
			Block ninthLevel2 = ninthLevel.getRelative(f).getRelative(f);
			Block ninthLevel3 = ninthLevel.getRelative(f).getRelative(f).getRelative(f);
			Block ninthLevel4 = ninthLevel.getRelative(f).getRelative(f).getRelative(f).getRelative(f);
			Block ninthLevel5 = ninthLevel.getRelative(f).getRelative(f).getRelative(f).getRelative(f).getRelative(f);
			Block ninthLevel6 = ninthLevel.getRelative(f).getRelative(f).getRelative(f).getRelative(f).getRelative(f).getRelative(f);

			if (firstLevel.getType() == Material.LAPIS_BLOCK) {
				if (firstLevel.getRelative(right).getType() == Material.STAINED_CLAY) {
					if (firstLevel.getRelative(left).getType() == Material.STAINED_CLAY) {
						if (firstLevel1.getType() == Material.STAINED_CLAY) {
							if (firstLevel1.getRelative(right).getType() == Material.STAINED_CLAY) {
								if (firstLevel1.getRelative(right).getRelative(right).getType() == Material.STAINED_CLAY) {
									if (firstLevel1.getRelative(left).getType() == Material.STAINED_CLAY) {
										if (firstLevel1.getRelative(left).getRelative(left).getType() == Material.STAINED_CLAY) {
											if (firstLevel2.getType() == Material.STAINED_CLAY) {
												if (firstLevel2.getRelative(right).getType() == Material.STAINED_CLAY) {
													if (firstLevel2.getRelative(right).getRelative(right).getType() == Material.STAINED_CLAY) {
														if (firstLevel2.getRelative(right).getRelative(right).getRelative(right).getType() == Material.STAINED_CLAY) {
															if (firstLevel2.getRelative(left).getType() == Material.STAINED_CLAY) {
																if (firstLevel2.getRelative(left).getRelative(left).getType() == Material.STAINED_CLAY) {
																	if (firstLevel2.getRelative(left).getRelative(left).getRelative(left).getType() == Material.STAINED_CLAY) {
																		if (firstLevel3.getType() == Material.STAINED_CLAY) {
																			if (firstLevel3.getRelative(right).getType() == Material.STAINED_CLAY) {
																				if (firstLevel3.getRelative(right).getRelative(right).getType() == Material.STAINED_CLAY) {
																					if (firstLevel3.getRelative(right).getRelative(right).getRelative(right).getType() == Material.STAINED_CLAY) {
																						if (firstLevel3.getRelative(left).getType() == Material.STAINED_CLAY) {
																							if (firstLevel3.getRelative(left).getRelative(left).getType() == Material.STAINED_CLAY) {
																								if (firstLevel3.getRelative(left).getRelative(left).getRelative(left).getType() == Material.STAINED_CLAY) {
																									if (firstLevel4.getType() == Material.STAINED_CLAY) {
																										if (firstLevel4.getRelative(right).getType() == Material.STAINED_CLAY) {
																											if (firstLevel4.getRelative(right).getRelative(right).getType() == Material.STAINED_CLAY) {
																												if (firstLevel4.getRelative(right).getRelative(right).getRelative(right).getType() == Material.STAINED_CLAY) {
																													if (firstLevel4.getRelative(left).getType() == Material.STAINED_CLAY) {
																														if (firstLevel4.getRelative(left).getRelative(left).getType() == Material.STAINED_CLAY) {
																															if (firstLevel4.getRelative(left).getRelative(left).getRelative(left).getType() == Material.STAINED_CLAY) {
																																if (firstLevel5.getType() == Material.STAINED_CLAY) {
																																	if (firstLevel5.getRelative(right).getType() == Material.STAINED_CLAY) {
																																		if (firstLevel5.getRelative(right).getRelative(right).getType() == Material.STAINED_CLAY) {
																																			if (firstLevel5.getRelative(left).getType() == Material.STAINED_CLAY) {
																																				if (firstLevel5.getRelative(left).getRelative(left).getType() == Material.STAINED_CLAY) {
																																					if (firstLevel6.getType() == Material.STAINED_CLAY) {
																																						if (firstLevel6.getRelative(right).getType() == Material.STAINED_CLAY) {
																																							if (firstLevel6.getRelative(left).getType() == Material.STAINED_CLAY) {

																																								if (secondLevel.getType() == Material.STAINED_GLASS_PANE) {
																																									if (secondLevel.getRelative(right).getType() == Material.STAINED_GLASS_PANE) {
																																										if (secondLevel.getRelative(right).getRelative(right).getType() == Material.STAINED_CLAY) {
																																											if (secondLevel.getRelative(left).getType() == Material.STAINED_GLASS_PANE) {
																																												if (secondLevel.getRelative(left).getRelative(left).getType() == Material.STAINED_CLAY) {
																																													if (secondLevel1.getType() == Material.AIR || secondLevel1.getType() == Material.STATIONARY_LAVA || secondLevel1.getType() == Material.STATIONARY_WATER) {
																																														if (secondLevel1.getRelative(right).getType() == Material.AIR || secondLevel1.getRelative(right).getType() == Material.STATIONARY_LAVA || secondLevel1.getRelative(right).getType() == Material.STATIONARY_WATER) {
																																															if (secondLevel1.getRelative(right).getRelative(right).getType() == Material.AIR || secondLevel1.getRelative(right).getRelative(right).getType() == Material.STATIONARY_LAVA || secondLevel1.getRelative(right).getRelative(right).getType() == Material.STATIONARY_WATER) {
																																																if (secondLevel1.getRelative(right).getRelative(right).getRelative(right).getType() == Material.STAINED_CLAY) {
																																																	if (secondLevel1.getRelative(left).getType() == Material.AIR || secondLevel1.getRelative(left).getType() == Material.STATIONARY_LAVA || secondLevel1.getRelative(left).getType() == Material.STATIONARY_WATER) {
																																																		if (secondLevel1.getRelative(left).getRelative(left).getType() == Material.AIR || secondLevel1.getRelative(left).getRelative(left).getType() == Material.STATIONARY_LAVA || secondLevel1.getRelative(left).getRelative(left).getType() == Material.STATIONARY_WATER) {
																																																			if (secondLevel1.getRelative(left).getRelative(left).getRelative(left).getType() == Material.STAINED_CLAY) {
																																																				if (secondLevel2.getType() == Material.AIR || secondLevel2.getType() == Material.STATIONARY_LAVA || secondLevel2.getType() == Material.STATIONARY_WATER) {
																																																					if (secondLevel2.getRelative(right).getType() == Material.AIR || secondLevel2.getRelative(right).getType() == Material.STATIONARY_LAVA || secondLevel2.getRelative(right).getType() == Material.STATIONARY_WATER) {
																																																						if (secondLevel2.getRelative(right).getRelative(right).getType() == Material.AIR || secondLevel2.getRelative(right).getRelative(right).getType() == Material.STATIONARY_LAVA || secondLevel2.getRelative(right).getRelative(right).getType() == Material.STATIONARY_WATER) {
																																																							if (secondLevel2.getRelative(right).getRelative(right).getRelative(right).getType() == Material.STAINED_GLASS_PANE) {
																																																								if (secondLevel2.getRelative(left).getType() == Material.AIR || secondLevel2.getRelative(left).getType() == Material.STATIONARY_LAVA || secondLevel2.getRelative(left).getType() == Material.STATIONARY_WATER) {
																																																									if (secondLevel2.getRelative(left).getRelative(left).getType() == Material.AIR || secondLevel2.getRelative(left).getRelative(left).getType() == Material.STATIONARY_LAVA || secondLevel2.getRelative(left).getRelative(left).getType() == Material.STATIONARY_WATER) {
																																																										if (secondLevel2.getRelative(left).getRelative(left).getRelative(left).getType() == Material.STAINED_GLASS_PANE) {
																																																											if (secondLevel3.getType() == Material.AIR || secondLevel3.getType() == Material.STATIONARY_LAVA || secondLevel3.getType() == Material.STATIONARY_WATER) {
																																																												if (secondLevel3.getRelative(right).getType() == Material.AIR || secondLevel3.getRelative(right).getType() == Material.STATIONARY_LAVA || secondLevel3.getRelative(right).getType() == Material.STATIONARY_WATER) {
																																																													if (secondLevel3.getRelative(right).getRelative(right).getType() == Material.AIR || secondLevel3.getRelative(right).getRelative(right).getType() == Material.STATIONARY_LAVA || secondLevel3.getRelative(right).getRelative(right).getType() == Material.STATIONARY_WATER) {
																																																														if (secondLevel3.getRelative(right).getRelative(right).getRelative(right).getType() == Material.STAINED_GLASS_PANE) {
																																																															if (secondLevel3.getRelative(left).getType() == Material.AIR || secondLevel3.getRelative(left).getType() == Material.STATIONARY_LAVA || secondLevel3.getRelative(left).getType() == Material.STATIONARY_WATER) {
																																																																if (secondLevel3.getRelative(left).getRelative(left).getType() == Material.AIR || secondLevel3.getRelative(left).getRelative(left).getType() == Material.STATIONARY_LAVA || secondLevel3.getRelative(left).getRelative(left).getType() == Material.STATIONARY_WATER) {
																																																																	if (secondLevel3.getRelative(left).getRelative(left).getRelative(left).getType() == Material.STAINED_GLASS_PANE) {
																																																																		if (secondLevel4.getType() == Material.AIR || secondLevel4.getType() == Material.STATIONARY_LAVA || secondLevel4.getType() == Material.STATIONARY_WATER) {
																																																																			if (secondLevel4.getRelative(right).getType() == Material.AIR || secondLevel4.getRelative(right).getType() == Material.STATIONARY_LAVA || secondLevel4.getRelative(right).getType() == Material.STATIONARY_WATER) {
																																																																				if (secondLevel4.getRelative(right).getRelative(right).getType() == Material.AIR || secondLevel4.getRelative(right).getRelative(right).getType() == Material.STATIONARY_LAVA || secondLevel4.getRelative(right).getRelative(right).getType() == Material.STATIONARY_WATER) {
																																																																					if (secondLevel4.getRelative(right).getRelative(right).getRelative(right).getType() == Material.STAINED_GLASS_PANE) {
																																																																						if (secondLevel4.getRelative(left).getType() == Material.AIR || secondLevel4.getRelative(left).getType() == Material.STATIONARY_LAVA || secondLevel4.getRelative(left).getType() == Material.STATIONARY_WATER) {
																																																																							if (secondLevel4.getRelative(left).getRelative(left).getType() == Material.AIR || secondLevel4.getRelative(left).getRelative(left).getType() == Material.STATIONARY_LAVA || secondLevel4.getRelative(left).getRelative(left).getType() == Material.STATIONARY_WATER) {
																																																																								if (secondLevel4.getRelative(left).getRelative(left).getRelative(left).getType() == Material.STAINED_GLASS_PANE) {
																																																																									if (secondLevel5.getType() == Material.AIR || secondLevel5.getType() == Material.STATIONARY_LAVA || secondLevel5.getType() == Material.STATIONARY_WATER) {
																																																																										if (secondLevel5.getRelative(right).getType() == Material.AIR || secondLevel5.getRelative(right).getType() == Material.STATIONARY_LAVA || secondLevel5.getRelative(right).getType() == Material.STATIONARY_WATER) {
																																																																											if (secondLevel5.getRelative(right).getRelative(right).getType() == Material.AIR || secondLevel5.getRelative(right).getRelative(right).getType() == Material.STATIONARY_LAVA || secondLevel5.getRelative(right).getRelative(right).getType() == Material.STATIONARY_WATER) {
																																																																												if (secondLevel5.getRelative(right).getRelative(right).getRelative(right).getType() == Material.STAINED_CLAY) {
																																																																													if (secondLevel5.getRelative(left).getType() == Material.AIR || secondLevel5.getRelative(left).getType() == Material.STATIONARY_LAVA || secondLevel5.getRelative(left).getType() == Material.STATIONARY_WATER) {
																																																																														if (secondLevel5.getRelative(left).getRelative(left).getType() == Material.AIR || secondLevel5.getRelative(left).getRelative(left).getType() == Material.STATIONARY_LAVA || secondLevel5.getRelative(left).getRelative(left).getType() == Material.STATIONARY_WATER) {
																																																																															if (secondLevel5.getRelative(left).getRelative(left).getRelative(left).getType() == Material.STAINED_CLAY) {
																																																																																if (secondLevel6.getType() == Material.STAINED_GLASS_PANE) {
																																																																																	if (secondLevel6.getRelative(left).getType() == Material.STAINED_GLASS_PANE) {
																																																																																		if (secondLevel6.getRelative(left).getRelative(left).getType() == Material.STAINED_CLAY) {
																																																																																			if (secondLevel6.getRelative(right).getType() == Material.STAINED_GLASS_PANE) {
																																																																																				if (secondLevel6.getRelative(right).getRelative(right).getType() == Material.STAINED_CLAY) {

																																																																																					if (thirdLevel.getType() == Material.STAINED_GLASS_PANE) {
																																																																																						if (thirdLevel.getRelative(right).getType() == Material.STAINED_GLASS_PANE) {
																																																																																							if (thirdLevel.getRelative(right).getRelative(right).getType() == Material.STAINED_CLAY) {
																																																																																								if (thirdLevel.getRelative(left).getType() == Material.STAINED_GLASS_PANE) {
																																																																																									if (thirdLevel.getRelative(left).getRelative(left).getType() == Material.STAINED_CLAY) {
																																																																																										if (thirdLevel1.getType() == Material.AIR || thirdLevel1.getType() == Material.STATIONARY_LAVA || thirdLevel1.getType() == Material.STATIONARY_WATER) {
																																																																																											if (thirdLevel1.getRelative(right).getType() == Material.AIR || thirdLevel1.getRelative(right).getType() == Material.STATIONARY_LAVA || thirdLevel1.getRelative(right).getType() == Material.STATIONARY_WATER) {
																																																																																												if (thirdLevel1.getRelative(right).getRelative(right).getType() == Material.AIR || thirdLevel1.getRelative(right).getRelative(right).getType() == Material.STATIONARY_LAVA || thirdLevel1.getRelative(right).getRelative(right).getType() == Material.STATIONARY_WATER) {
																																																																																													if (thirdLevel1.getRelative(right).getRelative(right).getRelative(right).getType() == Material.STAINED_CLAY) {
																																																																																														if (thirdLevel1.getRelative(left).getType() == Material.AIR || thirdLevel1.getRelative(left).getType() == Material.STATIONARY_LAVA || thirdLevel1.getRelative(left).getType() == Material.STATIONARY_WATER) {
																																																																																															if (thirdLevel1.getRelative(left).getRelative(left).getType() == Material.AIR || thirdLevel1.getRelative(left).getRelative(left).getType() == Material.STATIONARY_LAVA || thirdLevel1.getRelative(left).getRelative(left).getType() == Material.STATIONARY_WATER) {
																																																																																																if (thirdLevel1.getRelative(left).getRelative(left).getRelative(left).getType() == Material.STAINED_CLAY) {
																																																																																																	if (thirdLevel2.getType() == Material.AIR || thirdLevel2.getType() == Material.STATIONARY_LAVA || thirdLevel2.getType() == Material.STATIONARY_WATER) {
																																																																																																		if (thirdLevel2.getRelative(right).getType() == Material.AIR || thirdLevel2.getRelative(right).getType() == Material.STATIONARY_LAVA || thirdLevel2.getRelative(right).getType() == Material.STATIONARY_WATER) {
																																																																																																			if (thirdLevel2.getRelative(right).getRelative(right).getType() == Material.AIR || thirdLevel2.getRelative(right).getRelative(right).getType() == Material.STATIONARY_LAVA || thirdLevel2.getRelative(right).getRelative(right).getType() == Material.STATIONARY_WATER) {
																																																																																																				if (thirdLevel2.getRelative(right).getRelative(right).getRelative(right).getType() == Material.STAINED_GLASS_PANE) {
																																																																																																					if (thirdLevel2.getRelative(left).getType() == Material.AIR || thirdLevel2.getRelative(left).getType() == Material.STATIONARY_LAVA || thirdLevel2.getRelative(left).getType() == Material.STATIONARY_WATER) {
																																																																																																						if (thirdLevel2.getRelative(left).getRelative(left).getType() == Material.AIR || thirdLevel2.getRelative(left).getRelative(left).getType() == Material.STATIONARY_LAVA || thirdLevel2.getRelative(left).getRelative(left).getType() == Material.STATIONARY_WATER) {
																																																																																																							if (thirdLevel2.getRelative(left).getRelative(left).getRelative(left).getType() == Material.STAINED_GLASS_PANE) {
																																																																																																								if (thirdLevel3.getType() == Material.AIR || thirdLevel3.getType() == Material.STATIONARY_LAVA || thirdLevel3.getType() == Material.STATIONARY_WATER) {
																																																																																																									if (thirdLevel3.getRelative(right).getType() == Material.AIR || thirdLevel3.getRelative(right).getType() == Material.STATIONARY_LAVA || thirdLevel3.getRelative(right).getType() == Material.STATIONARY_WATER) {
																																																																																																										if (thirdLevel3.getRelative(right).getRelative(right).getType() == Material.AIR || thirdLevel3.getRelative(right).getRelative(right).getType() == Material.STATIONARY_LAVA || thirdLevel3.getRelative(right).getRelative(right).getType() == Material.STATIONARY_WATER) {
																																																																																																											if (thirdLevel3.getRelative(right).getRelative(right).getRelative(right).getType() == Material.STAINED_GLASS_PANE) {
																																																																																																												if (thirdLevel3.getRelative(left).getType() == Material.AIR || thirdLevel3.getRelative(left).getType() == Material.STATIONARY_LAVA || thirdLevel3.getRelative(left).getType() == Material.STATIONARY_WATER) {
																																																																																																													if (thirdLevel3.getRelative(left).getRelative(left).getType() == Material.AIR || thirdLevel3.getRelative(left).getRelative(left).getType() == Material.STATIONARY_LAVA || thirdLevel3.getRelative(left).getRelative(left).getType() == Material.STATIONARY_WATER) {
																																																																																																														if (thirdLevel3.getRelative(left).getRelative(left).getRelative(left).getType() == Material.STAINED_GLASS_PANE) {
																																																																																																															if (thirdLevel4.getType() == Material.AIR || thirdLevel4.getType() == Material.STATIONARY_LAVA || thirdLevel4.getType() == Material.STATIONARY_WATER) {
																																																																																																																if (thirdLevel4.getRelative(right).getType() == Material.AIR || thirdLevel4.getRelative(right).getType() == Material.STATIONARY_LAVA || thirdLevel4.getRelative(right).getType() == Material.STATIONARY_WATER) {
																																																																																																																	if (thirdLevel4.getRelative(right).getRelative(right).getType() == Material.AIR || thirdLevel4.getRelative(right).getRelative(right).getType() == Material.STATIONARY_LAVA || thirdLevel4.getRelative(right).getRelative(right).getType() == Material.STATIONARY_WATER) {
																																																																																																																		if (thirdLevel4.getRelative(right).getRelative(right).getRelative(right).getType() == Material.STAINED_GLASS_PANE) {
																																																																																																																			if (thirdLevel4.getRelative(left).getType() == Material.AIR || thirdLevel4.getRelative(left).getType() == Material.STATIONARY_LAVA || thirdLevel4.getRelative(left).getType() == Material.STATIONARY_WATER) {
																																																																																																																				if (thirdLevel4.getRelative(left).getRelative(left).getType() == Material.AIR || thirdLevel4.getRelative(left).getRelative(left).getType() == Material.STATIONARY_LAVA || thirdLevel4.getRelative(left).getRelative(left).getType() == Material.STATIONARY_WATER) {
																																																																																																																					if (thirdLevel4.getRelative(left).getRelative(left).getRelative(left).getType() == Material.STAINED_GLASS_PANE) {
																																																																																																																						if (thirdLevel5.getType() == Material.AIR || thirdLevel5.getType() == Material.STATIONARY_LAVA || thirdLevel5.getType() == Material.STATIONARY_WATER) {
																																																																																																																							if (thirdLevel5.getRelative(right).getType() == Material.AIR || thirdLevel5.getRelative(right).getType() == Material.STATIONARY_LAVA || thirdLevel5.getRelative(right).getType() == Material.STATIONARY_WATER) {
																																																																																																																								if (thirdLevel5.getRelative(right).getRelative(right).getType() == Material.AIR || thirdLevel5.getRelative(right).getRelative(right).getType() == Material.STATIONARY_LAVA || thirdLevel5.getRelative(right).getRelative(right).getType() == Material.STATIONARY_WATER) {
																																																																																																																									if (thirdLevel5.getRelative(right).getRelative(right).getRelative(right).getType() == Material.STAINED_CLAY) {
																																																																																																																										if (thirdLevel5.getRelative(left).getType() == Material.AIR || thirdLevel5.getRelative(left).getType() == Material.STATIONARY_LAVA || thirdLevel5.getRelative(left).getType() == Material.STATIONARY_WATER) {
																																																																																																																											if (thirdLevel5.getRelative(left).getRelative(left).getType() == Material.AIR || thirdLevel5.getRelative(left).getRelative(left).getType() == Material.STATIONARY_LAVA || thirdLevel5.getRelative(left).getRelative(left).getType() == Material.STATIONARY_WATER) {
																																																																																																																												if (thirdLevel5.getRelative(left).getRelative(left).getRelative(left).getType() == Material.STAINED_CLAY) {
																																																																																																																													if (thirdLevel6.getType() == Material.STAINED_GLASS_PANE) {
																																																																																																																														if (thirdLevel6.getRelative(left).getType() == Material.STAINED_GLASS_PANE) {
																																																																																																																															if (thirdLevel6.getRelative(left).getRelative(left).getType() == Material.STAINED_CLAY) {
																																																																																																																																if (thirdLevel6.getRelative(right).getType() == Material.STAINED_GLASS_PANE) {
																																																																																																																																	if (thirdLevel6.getRelative(right).getRelative(right).getType() == Material.STAINED_CLAY) {

																																																																																																																																		if (fourthLevel.getType() == Material.STAINED_GLASS_PANE) {
																																																																																																																																			if (fourthLevel.getRelative(right).getType() == Material.STAINED_GLASS_PANE) {
																																																																																																																																				if (fourthLevel.getRelative(right).getRelative(right).getType() == Material.STAINED_CLAY) {
																																																																																																																																					if (fourthLevel.getRelative(left).getType() == Material.STAINED_GLASS_PANE) {
																																																																																																																																						if (fourthLevel.getRelative(left).getRelative(left).getType() == Material.STAINED_CLAY) {
																																																																																																																																							if (fourthLevel1.getType() == Material.AIR || fourthLevel1.getType() == Material.STATIONARY_LAVA || fourthLevel1.getType() == Material.STATIONARY_WATER) {
																																																																																																																																								if (fourthLevel1.getRelative(right).getType() == Material.AIR || fourthLevel1.getRelative(right).getType() == Material.STATIONARY_LAVA || fourthLevel1.getRelative(right).getType() == Material.STATIONARY_WATER) {
																																																																																																																																									if (fourthLevel1.getRelative(right).getRelative(right).getType() == Material.AIR || fourthLevel1.getRelative(right).getRelative(right).getType() == Material.STATIONARY_LAVA || fourthLevel1.getRelative(right).getRelative(right).getType() == Material.STATIONARY_WATER) {
																																																																																																																																										if (fourthLevel1.getRelative(right).getRelative(right).getRelative(right).getType() == Material.STAINED_CLAY) {
																																																																																																																																											if (fourthLevel1.getRelative(left).getType() == Material.AIR || fourthLevel1.getRelative(left).getType() == Material.STATIONARY_LAVA || fourthLevel1.getRelative(left).getType() == Material.STATIONARY_WATER) {
																																																																																																																																												if (fourthLevel1.getRelative(left).getRelative(left).getType() == Material.AIR || fourthLevel1.getRelative(left).getRelative(left).getType() == Material.STATIONARY_LAVA || fourthLevel1.getRelative(left).getRelative(left).getType() == Material.STATIONARY_WATER) {
																																																																																																																																													if (fourthLevel1.getRelative(left).getRelative(left).getRelative(left).getType() == Material.STAINED_CLAY) {
																																																																																																																																														if (fourthLevel2.getType() == Material.AIR || fourthLevel2.getType() == Material.STATIONARY_LAVA || fourthLevel2.getType() == Material.STATIONARY_WATER) {
																																																																																																																																															if (fourthLevel2.getRelative(right).getType() == Material.AIR || fourthLevel2.getRelative(right).getType() == Material.STATIONARY_LAVA || fourthLevel2.getRelative(right).getType() == Material.STATIONARY_WATER) {
																																																																																																																																																if (fourthLevel2.getRelative(right).getRelative(right).getType() == Material.AIR || fourthLevel2.getRelative(right).getRelative(right).getType() == Material.STATIONARY_LAVA || fourthLevel2.getRelative(right).getRelative(right).getType() == Material.STATIONARY_WATER) {
																																																																																																																																																	if (fourthLevel2.getRelative(right).getRelative(right).getRelative(right).getType() == Material.STAINED_GLASS_PANE) {
																																																																																																																																																		if (fourthLevel2.getRelative(left).getType() == Material.AIR || fourthLevel2.getRelative(left).getType() == Material.STATIONARY_LAVA || fourthLevel2.getRelative(left).getType() == Material.STATIONARY_WATER) {
																																																																																																																																																			if (fourthLevel2.getRelative(left).getRelative(left).getType() == Material.AIR || fourthLevel2.getRelative(left).getRelative(left).getType() == Material.STATIONARY_LAVA || fourthLevel2.getRelative(left).getRelative(left).getType() == Material.STATIONARY_WATER) {
																																																																																																																																																				if (fourthLevel2.getRelative(left).getRelative(left).getRelative(left).getType() == Material.STAINED_GLASS_PANE) {
																																																																																																																																																					if (fourthLevel3.getType() == Material.AIR || fourthLevel3.getType() == Material.STATIONARY_LAVA || fourthLevel3.getType() == Material.STATIONARY_WATER) {
																																																																																																																																																						if (fourthLevel3.getRelative(right).getType() == Material.AIR || fourthLevel3.getRelative(right).getType() == Material.STATIONARY_LAVA || fourthLevel3.getRelative(right).getType() == Material.STATIONARY_WATER) {
																																																																																																																																																							if (fourthLevel3.getRelative(right).getRelative(right).getType() == Material.AIR || fourthLevel3.getRelative(right).getRelative(right).getType() == Material.STATIONARY_LAVA || fourthLevel3.getRelative(right).getRelative(right).getType() == Material.STATIONARY_WATER) {
																																																																																																																																																								if (fourthLevel3.getRelative(right).getRelative(right).getRelative(right).getType() == Material.STAINED_GLASS_PANE) {
																																																																																																																																																									if (fourthLevel3.getRelative(left).getType() == Material.AIR || fourthLevel3.getRelative(left).getType() == Material.STATIONARY_LAVA || fourthLevel3.getRelative(left).getType() == Material.STATIONARY_WATER) {
																																																																																																																																																										if (fourthLevel3.getRelative(left).getRelative(left).getType() == Material.AIR || fourthLevel3.getRelative(left).getRelative(left).getType() == Material.STATIONARY_LAVA || fourthLevel3.getRelative(left).getRelative(left).getType() == Material.STATIONARY_WATER) {
																																																																																																																																																											if (fourthLevel3.getRelative(left).getRelative(left).getRelative(left).getType() == Material.STAINED_GLASS_PANE) {
																																																																																																																																																												if (fourthLevel4.getType() == Material.AIR || fourthLevel4.getType() == Material.STATIONARY_LAVA || fourthLevel4.getType() == Material.STATIONARY_WATER) {
																																																																																																																																																													if (fourthLevel4.getRelative(right).getType() == Material.AIR || fourthLevel4.getRelative(right).getType() == Material.STATIONARY_LAVA || fourthLevel4.getRelative(right).getType() == Material.STATIONARY_WATER) {
																																																																																																																																																														if (fourthLevel4.getRelative(right).getRelative(right).getType() == Material.AIR || fourthLevel4.getRelative(right).getRelative(right).getType() == Material.STATIONARY_LAVA || fourthLevel4.getRelative(right).getRelative(right).getType() == Material.STATIONARY_WATER) {
																																																																																																																																																															if (fourthLevel4.getRelative(right).getRelative(right).getRelative(right).getType() == Material.STAINED_GLASS_PANE) {
																																																																																																																																																																if (fourthLevel4.getRelative(left).getType() == Material.AIR || fourthLevel4.getRelative(left).getType() == Material.STATIONARY_LAVA || fourthLevel4.getRelative(left).getType() == Material.STATIONARY_WATER) {
																																																																																																																																																																	if (fourthLevel4.getRelative(left).getRelative(left).getType() == Material.AIR || fourthLevel4.getRelative(left).getRelative(left).getType() == Material.STATIONARY_LAVA || fourthLevel4.getRelative(left).getRelative(left).getType() == Material.STATIONARY_WATER) {
																																																																																																																																																																		if (fourthLevel4.getRelative(left).getRelative(left).getRelative(left).getType() == Material.STAINED_GLASS_PANE) {
																																																																																																																																																																			if (fourthLevel5.getType() == Material.AIR || fourthLevel5.getType() == Material.STATIONARY_LAVA || fourthLevel5.getType() == Material.STATIONARY_WATER) {
																																																																																																																																																																				if (fourthLevel5.getRelative(right).getType() == Material.AIR || fourthLevel5.getRelative(right).getType() == Material.STATIONARY_LAVA || fourthLevel5.getRelative(right).getType() == Material.STATIONARY_WATER) {
																																																																																																																																																																					if (fourthLevel5.getRelative(right).getRelative(right).getType() == Material.AIR || fourthLevel5.getRelative(right).getRelative(right).getType() == Material.STATIONARY_LAVA || fourthLevel5.getRelative(right).getRelative(right).getType() == Material.STATIONARY_WATER) {
																																																																																																																																																																						if (fourthLevel5.getRelative(right).getRelative(right).getRelative(right).getType() == Material.STAINED_CLAY) {
																																																																																																																																																																							if (fourthLevel5.getRelative(left).getType() == Material.AIR || fourthLevel5.getRelative(left).getType() == Material.STATIONARY_LAVA || fourthLevel5.getRelative(left).getType() == Material.STATIONARY_WATER) {
																																																																																																																																																																								if (fourthLevel5.getRelative(left).getRelative(left).getType() == Material.AIR || fourthLevel5.getRelative(left).getRelative(left).getType() == Material.STATIONARY_LAVA || fourthLevel5.getRelative(left).getRelative(left).getType() == Material.STATIONARY_WATER) {
																																																																																																																																																																									if (fourthLevel5.getRelative(left).getRelative(left).getRelative(left).getType() == Material.STAINED_CLAY) {
																																																																																																																																																																										if (fourthLevel6.getType() == Material.STAINED_GLASS_PANE) {
																																																																																																																																																																											if (fourthLevel6.getRelative(left).getType() == Material.STAINED_GLASS_PANE) {
																																																																																																																																																																												if (fourthLevel6.getRelative(left).getRelative(left).getType() == Material.STAINED_CLAY) {
																																																																																																																																																																													if (fourthLevel6.getRelative(right).getType() == Material.STAINED_GLASS_PANE) {
																																																																																																																																																																														if (fourthLevel6.getRelative(right).getRelative(right).getType() == Material.STAINED_CLAY) {

																																																																																																																																																																															if (fifthLevel.getType() == Material.STAINED_GLASS_PANE) {
																																																																																																																																																																																if (fifthLevel.getRelative(right).getType() == Material.STAINED_GLASS_PANE) {
																																																																																																																																																																																	if (fifthLevel.getRelative(right).getRelative(right).getType() == Material.STAINED_CLAY) {
																																																																																																																																																																																		if (fifthLevel.getRelative(left).getType() == Material.STAINED_GLASS_PANE) {
																																																																																																																																																																																			if (fifthLevel.getRelative(left).getRelative(left).getType() == Material.STAINED_CLAY) {
																																																																																																																																																																																				if (fifthLevel1.getType() == Material.AIR || fifthLevel1.getType() == Material.STATIONARY_LAVA || fifthLevel1.getType() == Material.STATIONARY_WATER) {
																																																																																																																																																																																					if (fifthLevel1.getRelative(right).getType() == Material.AIR || fifthLevel1.getRelative(right).getType() == Material.STATIONARY_LAVA || fifthLevel1.getRelative(right).getType() == Material.STATIONARY_WATER) {
																																																																																																																																																																																						if (fifthLevel1.getRelative(right).getRelative(right).getType() == Material.AIR || fifthLevel1.getRelative(right).getRelative(right).getType() == Material.STATIONARY_LAVA || fifthLevel1.getRelative(right).getRelative(right).getType() == Material.STATIONARY_WATER) {
																																																																																																																																																																																							if (fifthLevel1.getRelative(right).getRelative(right).getRelative(right).getType() == Material.STAINED_CLAY) {
																																																																																																																																																																																								if (fifthLevel1.getRelative(left).getType() == Material.AIR || fifthLevel1.getRelative(left).getType() == Material.STATIONARY_LAVA || fifthLevel1.getRelative(left).getType() == Material.STATIONARY_WATER) {
																																																																																																																																																																																									if (fifthLevel1.getRelative(left).getRelative(left).getType() == Material.AIR || fifthLevel1.getRelative(left).getRelative(left).getType() == Material.STATIONARY_LAVA || fifthLevel1.getRelative(left).getRelative(left).getType() == Material.STATIONARY_WATER) {
																																																																																																																																																																																										if (fifthLevel1.getRelative(left).getRelative(left).getRelative(left).getType() == Material.STAINED_CLAY) {
																																																																																																																																																																																											if (fifthLevel2.getType() == Material.AIR || fifthLevel2.getType() == Material.STATIONARY_LAVA || fifthLevel2.getType() == Material.STATIONARY_WATER) {
																																																																																																																																																																																												if (fifthLevel2.getRelative(right).getType() == Material.AIR || fifthLevel2.getRelative(right).getType() == Material.STATIONARY_LAVA || fifthLevel2.getRelative(right).getType() == Material.STATIONARY_WATER) {
																																																																																																																																																																																													if (fifthLevel2.getRelative(right).getRelative(right).getType() == Material.AIR || fifthLevel2.getRelative(right).getRelative(right).getType() == Material.STATIONARY_LAVA || fifthLevel2.getRelative(right).getRelative(right).getType() == Material.STATIONARY_WATER) {
																																																																																																																																																																																														if (fifthLevel2.getRelative(right).getRelative(right).getRelative(right).getType() == Material.STAINED_GLASS_PANE) {
																																																																																																																																																																																															if (fifthLevel2.getRelative(left).getType() == Material.AIR || fifthLevel2.getRelative(left).getType() == Material.STATIONARY_LAVA || fifthLevel2.getRelative(left).getType() == Material.STATIONARY_WATER) {
																																																																																																																																																																																																if (fifthLevel2.getRelative(left).getRelative(left).getType() == Material.AIR || fifthLevel2.getRelative(left).getRelative(left).getType() == Material.STATIONARY_LAVA || fifthLevel2.getRelative(left).getRelative(left).getType() == Material.STATIONARY_WATER) {
																																																																																																																																																																																																	if (fifthLevel2.getRelative(left).getRelative(left).getRelative(left).getType() == Material.STAINED_GLASS_PANE) {
																																																																																																																																																																																																		if (fifthLevel3.getType() == Material.AIR || fifthLevel3.getType() == Material.STATIONARY_LAVA || fifthLevel3.getType() == Material.STATIONARY_WATER) {
																																																																																																																																																																																																			if (fifthLevel3.getRelative(right).getType() == Material.AIR || fifthLevel3.getRelative(right).getType() == Material.STATIONARY_LAVA || fifthLevel3.getRelative(right).getType() == Material.STATIONARY_WATER) {
																																																																																																																																																																																																				if (fifthLevel3.getRelative(right).getRelative(right).getType() == Material.AIR || fifthLevel3.getRelative(right).getRelative(right).getType() == Material.STATIONARY_LAVA || fifthLevel3.getRelative(right).getRelative(right).getType() == Material.STATIONARY_WATER) {
																																																																																																																																																																																																					if (fifthLevel3.getRelative(right).getRelative(right).getRelative(right).getType() == Material.STAINED_GLASS_PANE) {
																																																																																																																																																																																																						if (fifthLevel3.getRelative(left).getType() == Material.AIR || fifthLevel3.getRelative(left).getType() == Material.STATIONARY_LAVA || fifthLevel3.getRelative(left).getType() == Material.STATIONARY_WATER) {
																																																																																																																																																																																																							if (fifthLevel3.getRelative(left).getRelative(left).getType() == Material.AIR || fifthLevel3.getRelative(left).getRelative(left).getType() == Material.STATIONARY_LAVA || fifthLevel3.getRelative(left).getRelative(left).getType() == Material.STATIONARY_WATER) {
																																																																																																																																																																																																								if (fifthLevel3.getRelative(left).getRelative(left).getRelative(left).getType() == Material.STAINED_GLASS_PANE) {
																																																																																																																																																																																																									if (fifthLevel4.getType() == Material.AIR || fifthLevel4.getType() == Material.STATIONARY_LAVA || fifthLevel4.getType() == Material.STATIONARY_WATER) {
																																																																																																																																																																																																										if (fifthLevel4.getRelative(right).getType() == Material.AIR || fifthLevel4.getRelative(right).getType() == Material.STATIONARY_LAVA || fifthLevel4.getRelative(right).getType() == Material.STATIONARY_WATER) {
																																																																																																																																																																																																											if (fifthLevel4.getRelative(right).getRelative(right).getType() == Material.AIR || fifthLevel4.getRelative(right).getRelative(right).getType() == Material.STATIONARY_LAVA || fifthLevel4.getRelative(right).getRelative(right).getType() == Material.STATIONARY_WATER) {
																																																																																																																																																																																																												if (fifthLevel4.getRelative(right).getRelative(right).getRelative(right).getType() == Material.STAINED_GLASS_PANE) {
																																																																																																																																																																																																													if (fifthLevel4.getRelative(left).getType() == Material.AIR || fifthLevel4.getRelative(left).getType() == Material.STATIONARY_LAVA || fifthLevel4.getRelative(left).getType() == Material.STATIONARY_WATER) {
																																																																																																																																																																																																														if (fifthLevel4.getRelative(left).getRelative(left).getType() == Material.AIR || fifthLevel4.getRelative(left).getRelative(left).getType() == Material.STATIONARY_LAVA || fifthLevel4.getRelative(left).getRelative(left).getType() == Material.STATIONARY_WATER) {
																																																																																																																																																																																																															if (fifthLevel4.getRelative(left).getRelative(left).getRelative(left).getType() == Material.STAINED_GLASS_PANE) {
																																																																																																																																																																																																																if (fifthLevel5.getType() == Material.AIR || fifthLevel5.getType() == Material.STATIONARY_LAVA || fifthLevel5.getType() == Material.STATIONARY_WATER) {
																																																																																																																																																																																																																	if (fifthLevel5.getRelative(right).getType() == Material.AIR || fifthLevel5.getRelative(right).getType() == Material.STATIONARY_LAVA || fifthLevel5.getRelative(right).getType() == Material.STATIONARY_WATER) {
																																																																																																																																																																																																																		if (fifthLevel5.getRelative(right).getRelative(right).getType() == Material.AIR || fifthLevel5.getRelative(right).getRelative(right).getType() == Material.STATIONARY_LAVA || fifthLevel5.getRelative(right).getRelative(right).getType() == Material.STATIONARY_WATER) {
																																																																																																																																																																																																																			if (fifthLevel5.getRelative(right).getRelative(right).getRelative(right).getType() == Material.STAINED_CLAY) {
																																																																																																																																																																																																																				if (fifthLevel5.getRelative(left).getType() == Material.AIR || fifthLevel5.getRelative(left).getType() == Material.STATIONARY_LAVA || fifthLevel5.getRelative(left).getType() == Material.STATIONARY_WATER) {
																																																																																																																																																																																																																					if (fifthLevel5.getRelative(left).getRelative(left).getType() == Material.AIR || fifthLevel5.getRelative(left).getRelative(left).getType() == Material.STATIONARY_LAVA || fifthLevel5.getRelative(left).getRelative(left).getType() == Material.STATIONARY_WATER) {
																																																																																																																																																																																																																						if (fifthLevel5.getRelative(left).getRelative(left).getRelative(left).getType() == Material.STAINED_CLAY) {
																																																																																																																																																																																																																							if (fifthLevel6.getType() == Material.STAINED_GLASS_PANE) {
																																																																																																																																																																																																																								if (fifthLevel6.getRelative(left).getType() == Material.STAINED_GLASS_PANE) {
																																																																																																																																																																																																																									if (fifthLevel6.getRelative(left).getRelative(left).getType() == Material.STAINED_CLAY) {
																																																																																																																																																																																																																										if (fifthLevel6.getRelative(right).getType() == Material.STAINED_GLASS_PANE) {
																																																																																																																																																																																																																											if (fifthLevel6.getRelative(right).getRelative(right).getType() == Material.STAINED_CLAY) {

																																																																																																																																																																																																																												if (sixthLevel.getType() == Material.STAINED_GLASS_PANE) {
																																																																																																																																																																																																																													if (sixthLevel.getRelative(right).getType() == Material.STAINED_GLASS_PANE) {
																																																																																																																																																																																																																														if (sixthLevel.getRelative(right).getRelative(right).getType() == Material.STAINED_CLAY) {
																																																																																																																																																																																																																															if (sixthLevel.getRelative(left).getType() == Material.STAINED_GLASS_PANE) {
																																																																																																																																																																																																																																if (sixthLevel.getRelative(left).getRelative(left).getType() == Material.STAINED_CLAY) {
																																																																																																																																																																																																																																	if (sixthLevel1.getType() == Material.AIR || sixthLevel1.getType() == Material.STATIONARY_LAVA || sixthLevel1.getType() == Material.STATIONARY_WATER) {
																																																																																																																																																																																																																																		if (sixthLevel1.getRelative(right).getType() == Material.AIR || sixthLevel1.getRelative(right).getType() == Material.STATIONARY_LAVA || sixthLevel1.getRelative(right).getType() == Material.STATIONARY_WATER) {
																																																																																																																																																																																																																																			if (sixthLevel1.getRelative(right).getRelative(right).getType() == Material.AIR || sixthLevel1.getRelative(right).getRelative(right).getType() == Material.STATIONARY_LAVA || sixthLevel1.getRelative(right).getRelative(right).getType() == Material.STATIONARY_WATER) {
																																																																																																																																																																																																																																				if (sixthLevel1.getRelative(right).getRelative(right).getRelative(right).getType() == Material.STAINED_CLAY) {
																																																																																																																																																																																																																																					if (sixthLevel1.getRelative(left).getType() == Material.AIR || sixthLevel1.getRelative(left).getType() == Material.STATIONARY_LAVA || sixthLevel1.getRelative(left).getType() == Material.STATIONARY_WATER) {
																																																																																																																																																																																																																																						if (sixthLevel1.getRelative(left).getRelative(left).getType() == Material.AIR || sixthLevel1.getRelative(left).getRelative(left).getType() == Material.STATIONARY_LAVA || sixthLevel1.getRelative(left).getRelative(left).getType() == Material.STATIONARY_WATER) {
																																																																																																																																																																																																																																							if (sixthLevel1.getRelative(left).getRelative(left).getRelative(left).getType() == Material.STAINED_CLAY) {
																																																																																																																																																																																																																																								if (sixthLevel2.getType() == Material.AIR || sixthLevel2.getType() == Material.STATIONARY_LAVA || sixthLevel2.getType() == Material.STATIONARY_WATER) {
																																																																																																																																																																																																																																									if (sixthLevel2.getRelative(right).getType() == Material.AIR || sixthLevel2.getRelative(right).getType() == Material.STATIONARY_LAVA || sixthLevel2.getRelative(right).getType() == Material.STATIONARY_WATER) {
																																																																																																																																																																																																																																										if (sixthLevel2.getRelative(right).getRelative(right).getType() == Material.AIR || sixthLevel2.getRelative(right).getRelative(right).getType() == Material.STATIONARY_LAVA || sixthLevel2.getRelative(right).getRelative(right).getType() == Material.STATIONARY_WATER) {
																																																																																																																																																																																																																																											if (sixthLevel2.getRelative(right).getRelative(right).getRelative(right).getType() == Material.STAINED_GLASS_PANE) {
																																																																																																																																																																																																																																												if (sixthLevel2.getRelative(left).getType() == Material.AIR || sixthLevel2.getRelative(left).getType() == Material.STATIONARY_LAVA || sixthLevel2.getRelative(left).getType() == Material.STATIONARY_WATER) {
																																																																																																																																																																																																																																													if (sixthLevel2.getRelative(left).getRelative(left).getType() == Material.AIR || sixthLevel2.getRelative(left).getRelative(left).getType() == Material.STATIONARY_LAVA || sixthLevel2.getRelative(left).getRelative(left).getType() == Material.STATIONARY_WATER) {
																																																																																																																																																																																																																																														if (sixthLevel2.getRelative(left).getRelative(left).getRelative(left).getType() == Material.STAINED_GLASS_PANE) {
																																																																																																																																																																																																																																															if (sixthLevel3.getType() == Material.AIR || sixthLevel3.getType() == Material.STATIONARY_LAVA || sixthLevel3.getType() == Material.STATIONARY_WATER) {
																																																																																																																																																																																																																																																if (sixthLevel3.getRelative(right).getType() == Material.AIR || sixthLevel3.getRelative(right).getType() == Material.STATIONARY_LAVA || sixthLevel3.getRelative(right).getType() == Material.STATIONARY_WATER) {
																																																																																																																																																																																																																																																	if (sixthLevel3.getRelative(right).getRelative(right).getType() == Material.AIR || sixthLevel3.getRelative(right).getRelative(right).getType() == Material.STATIONARY_LAVA || sixthLevel3.getRelative(right).getRelative(right).getType() == Material.STATIONARY_WATER) {
																																																																																																																																																																																																																																																		if (sixthLevel3.getRelative(right).getRelative(right).getRelative(right).getType() == Material.STAINED_GLASS_PANE) {
																																																																																																																																																																																																																																																			if (sixthLevel3.getRelative(left).getType() == Material.AIR || sixthLevel3.getRelative(left).getType() == Material.STATIONARY_LAVA || sixthLevel3.getRelative(left).getType() == Material.STATIONARY_WATER) {
																																																																																																																																																																																																																																																				if (sixthLevel3.getRelative(left).getRelative(left).getType() == Material.AIR || sixthLevel3.getRelative(left).getRelative(left).getType() == Material.STATIONARY_LAVA || sixthLevel3.getRelative(left).getRelative(left).getType() == Material.STATIONARY_WATER) {
																																																																																																																																																																																																																																																					if (sixthLevel3.getRelative(left).getRelative(left).getRelative(left).getType() == Material.STAINED_GLASS_PANE) {
																																																																																																																																																																																																																																																						if (sixthLevel4.getType() == Material.AIR || sixthLevel4.getType() == Material.STATIONARY_LAVA || sixthLevel4.getType() == Material.STATIONARY_WATER) {
																																																																																																																																																																																																																																																							if (sixthLevel4.getRelative(right).getType() == Material.AIR || sixthLevel4.getRelative(right).getType() == Material.STATIONARY_LAVA || sixthLevel4.getRelative(right).getType() == Material.STATIONARY_WATER) {
																																																																																																																																																																																																																																																								if (sixthLevel4.getRelative(right).getRelative(right).getType() == Material.AIR || sixthLevel4.getRelative(right).getRelative(right).getType() == Material.STATIONARY_LAVA || sixthLevel4.getRelative(right).getRelative(right).getType() == Material.STATIONARY_WATER) {
																																																																																																																																																																																																																																																									if (sixthLevel4.getRelative(right).getRelative(right).getRelative(right).getType() == Material.STAINED_GLASS_PANE) {
																																																																																																																																																																																																																																																										if (sixthLevel4.getRelative(left).getType() == Material.AIR || sixthLevel4.getRelative(left).getType() == Material.STATIONARY_LAVA || sixthLevel4.getRelative(left).getType() == Material.STATIONARY_WATER) {
																																																																																																																																																																																																																																																											if (sixthLevel4.getRelative(left).getRelative(left).getType() == Material.AIR || sixthLevel4.getRelative(left).getRelative(left).getType() == Material.STATIONARY_LAVA || sixthLevel4.getRelative(left).getRelative(left).getType() == Material.STATIONARY_WATER) {
																																																																																																																																																																																																																																																												if (sixthLevel4.getRelative(left).getRelative(left).getRelative(left).getType() == Material.STAINED_GLASS_PANE) {
																																																																																																																																																																																																																																																													if (sixthLevel5.getType() == Material.AIR || sixthLevel5.getType() == Material.STATIONARY_LAVA || sixthLevel5.getType() == Material.STATIONARY_WATER) {
																																																																																																																																																																																																																																																														if (sixthLevel5.getRelative(right).getType() == Material.AIR || sixthLevel5.getRelative(right).getType() == Material.STATIONARY_LAVA || sixthLevel5.getRelative(right).getType() == Material.STATIONARY_WATER) {
																																																																																																																																																																																																																																																															if (sixthLevel5.getRelative(right).getRelative(right).getType() == Material.AIR || sixthLevel5.getRelative(right).getRelative(right).getType() == Material.STATIONARY_LAVA || sixthLevel5.getRelative(right).getRelative(right).getType() == Material.STATIONARY_WATER) {
																																																																																																																																																																																																																																																																if (sixthLevel5.getRelative(right).getRelative(right).getRelative(right).getType() == Material.STAINED_CLAY) {
																																																																																																																																																																																																																																																																	if (sixthLevel5.getRelative(left).getType() == Material.AIR || sixthLevel5.getRelative(left).getType() == Material.STATIONARY_LAVA || sixthLevel5.getRelative(left).getType() == Material.STATIONARY_WATER) {
																																																																																																																																																																																																																																																																		if (sixthLevel5.getRelative(left).getRelative(left).getType() == Material.AIR || sixthLevel5.getRelative(left).getRelative(left).getType() == Material.STATIONARY_LAVA || sixthLevel5.getRelative(left).getRelative(left).getType() == Material.STATIONARY_WATER) {
																																																																																																																																																																																																																																																																			if (sixthLevel5.getRelative(left).getRelative(left).getRelative(left).getType() == Material.STAINED_CLAY) {
																																																																																																																																																																																																																																																																				if (sixthLevel6.getType() == Material.STAINED_GLASS_PANE) {
																																																																																																																																																																																																																																																																					if (sixthLevel6.getRelative(left).getType() == Material.STAINED_GLASS_PANE) {
																																																																																																																																																																																																																																																																						if (sixthLevel6.getRelative(left).getRelative(left).getType() == Material.STAINED_CLAY) {
																																																																																																																																																																																																																																																																							if (sixthLevel6.getRelative(right).getType() == Material.STAINED_GLASS_PANE) {
																																																																																																																																																																																																																																																																								if (sixthLevel6.getRelative(right).getRelative(right).getType() == Material.STAINED_CLAY) {

																																																																																																																																																																																																																																																																									if (seventhLevel.getType() == Material.STAINED_GLASS_PANE) {
																																																																																																																																																																																																																																																																										if (seventhLevel.getRelative(right).getType() == Material.STAINED_GLASS_PANE) {
																																																																																																																																																																																																																																																																											if (seventhLevel.getRelative(right).getRelative(right).getType() == Material.STAINED_CLAY) {
																																																																																																																																																																																																																																																																												if (seventhLevel.getRelative(left).getType() == Material.STAINED_GLASS_PANE) {
																																																																																																																																																																																																																																																																													if (seventhLevel.getRelative(left).getRelative(left).getType() == Material.STAINED_CLAY) {
																																																																																																																																																																																																																																																																														if (seventhLevel1.getType() == Material.AIR || seventhLevel1.getType() == Material.STATIONARY_LAVA || seventhLevel1.getType() == Material.STATIONARY_WATER) {
																																																																																																																																																																																																																																																																															if (seventhLevel1.getRelative(right).getType() == Material.AIR || seventhLevel1.getRelative(right).getType() == Material.STATIONARY_LAVA || seventhLevel1.getRelative(right).getType() == Material.STATIONARY_WATER) {
																																																																																																																																																																																																																																																																																if (seventhLevel1.getRelative(right).getRelative(right).getType() == Material.AIR || seventhLevel1.getRelative(right).getRelative(right).getType() == Material.STATIONARY_LAVA || seventhLevel1.getRelative(right).getRelative(right).getType() == Material.STATIONARY_WATER) {
																																																																																																																																																																																																																																																																																	if (seventhLevel1.getRelative(right).getRelative(right).getRelative(right).getType() == Material.STAINED_CLAY) {
																																																																																																																																																																																																																																																																																		if (seventhLevel1.getRelative(left).getType() == Material.AIR || seventhLevel1.getRelative(left).getType() == Material.STATIONARY_LAVA || seventhLevel1.getRelative(left).getType() == Material.STATIONARY_WATER) {
																																																																																																																																																																																																																																																																																			if (seventhLevel1.getRelative(left).getRelative(left).getType() == Material.AIR || seventhLevel1.getRelative(left).getRelative(left).getType() == Material.STATIONARY_LAVA || seventhLevel1.getRelative(left).getRelative(left).getType() == Material.STATIONARY_WATER) {
																																																																																																																																																																																																																																																																																				if (seventhLevel1.getRelative(left).getRelative(left).getRelative(left).getType() == Material.STAINED_CLAY) {
																																																																																																																																																																																																																																																																																					if (seventhLevel2.getType() == Material.AIR || seventhLevel2.getType() == Material.STATIONARY_LAVA || seventhLevel2.getType() == Material.STATIONARY_WATER) {
																																																																																																																																																																																																																																																																																						if (seventhLevel2.getRelative(right).getType() == Material.AIR || seventhLevel2.getRelative(right).getType() == Material.STATIONARY_LAVA || seventhLevel2.getRelative(right).getType() == Material.STATIONARY_WATER) {
																																																																																																																																																																																																																																																																																							if (seventhLevel2.getRelative(right).getRelative(right).getType() == Material.AIR || seventhLevel2.getRelative(right).getRelative(right).getType() == Material.STATIONARY_LAVA || seventhLevel2.getRelative(right).getRelative(right).getType() == Material.STATIONARY_WATER) {
																																																																																																																																																																																																																																																																																								if (seventhLevel2.getRelative(right).getRelative(right).getRelative(right).getType() == Material.STAINED_GLASS_PANE) {
																																																																																																																																																																																																																																																																																									if (seventhLevel2.getRelative(left).getType() == Material.AIR || seventhLevel2.getRelative(left).getType() == Material.STATIONARY_LAVA || seventhLevel2.getRelative(left).getType() == Material.STATIONARY_WATER) {
																																																																																																																																																																																																																																																																																										if (seventhLevel2.getRelative(left).getRelative(left).getType() == Material.AIR || seventhLevel2.getRelative(left).getRelative(left).getType() == Material.STATIONARY_LAVA || seventhLevel2.getRelative(left).getRelative(left).getType() == Material.STATIONARY_WATER) {
																																																																																																																																																																																																																																																																																											if (seventhLevel2.getRelative(left).getRelative(left).getRelative(left).getType() == Material.STAINED_GLASS_PANE) {
																																																																																																																																																																																																																																																																																												if (seventhLevel3.getType() == Material.AIR || seventhLevel3.getType() == Material.STATIONARY_LAVA || seventhLevel3.getType() == Material.STATIONARY_WATER) {
																																																																																																																																																																																																																																																																																													if (seventhLevel3.getRelative(right).getType() == Material.AIR || seventhLevel3.getRelative(right).getType() == Material.STATIONARY_LAVA || seventhLevel3.getRelative(right).getType() == Material.STATIONARY_WATER) {
																																																																																																																																																																																																																																																																																														if (seventhLevel3.getRelative(right).getRelative(right).getType() == Material.AIR || seventhLevel3.getRelative(right).getRelative(right).getType() == Material.STATIONARY_LAVA || seventhLevel3.getRelative(right).getRelative(right).getType() == Material.STATIONARY_WATER) {
																																																																																																																																																																																																																																																																																															if (seventhLevel3.getRelative(right).getRelative(right).getRelative(right).getType() == Material.STAINED_GLASS_PANE) {
																																																																																																																																																																																																																																																																																																if (seventhLevel3.getRelative(left).getType() == Material.AIR || seventhLevel3.getRelative(left).getType() == Material.STATIONARY_LAVA || seventhLevel3.getRelative(left).getType() == Material.STATIONARY_WATER) {
																																																																																																																																																																																																																																																																																																	if (seventhLevel3.getRelative(left).getRelative(left).getType() == Material.AIR || seventhLevel3.getRelative(left).getRelative(left).getType() == Material.STATIONARY_LAVA || seventhLevel3.getRelative(left).getRelative(left).getType() == Material.STATIONARY_WATER) {
																																																																																																																																																																																																																																																																																																		if (seventhLevel3.getRelative(left).getRelative(left).getRelative(left).getType() == Material.STAINED_GLASS_PANE) {
																																																																																																																																																																																																																																																																																																			if (seventhLevel4.getType() == Material.AIR || seventhLevel4.getType() == Material.STATIONARY_LAVA || seventhLevel4.getType() == Material.STATIONARY_WATER) {
																																																																																																																																																																																																																																																																																																				if (seventhLevel4.getRelative(right).getType() == Material.AIR || seventhLevel4.getRelative(right).getType() == Material.STATIONARY_LAVA || seventhLevel4.getRelative(right).getType() == Material.STATIONARY_WATER) {
																																																																																																																																																																																																																																																																																																					if (seventhLevel4.getRelative(right).getRelative(right).getType() == Material.AIR || seventhLevel4.getRelative(right).getRelative(right).getType() == Material.STATIONARY_LAVA || seventhLevel4.getRelative(right).getRelative(right).getType() == Material.STATIONARY_WATER) {
																																																																																																																																																																																																																																																																																																						if (seventhLevel4.getRelative(right).getRelative(right).getRelative(right).getType() == Material.STAINED_GLASS_PANE) {
																																																																																																																																																																																																																																																																																																							if (seventhLevel4.getRelative(left).getType() == Material.AIR || seventhLevel4.getRelative(left).getType() == Material.STATIONARY_LAVA || seventhLevel4.getRelative(left).getType() == Material.STATIONARY_WATER) {
																																																																																																																																																																																																																																																																																																								if (seventhLevel4.getRelative(left).getRelative(left).getType() == Material.AIR || seventhLevel4.getRelative(left).getRelative(left).getType() == Material.STATIONARY_LAVA || seventhLevel4.getRelative(left).getRelative(left).getType() == Material.STATIONARY_WATER) {
																																																																																																																																																																																																																																																																																																									if (seventhLevel4.getRelative(left).getRelative(left).getRelative(left).getType() == Material.STAINED_GLASS_PANE) {
																																																																																																																																																																																																																																																																																																										if (seventhLevel5.getType() == Material.AIR || seventhLevel5.getType() == Material.STATIONARY_LAVA || seventhLevel5.getType() == Material.STATIONARY_WATER) {
																																																																																																																																																																																																																																																																																																											if (seventhLevel5.getRelative(right).getType() == Material.AIR || seventhLevel5.getRelative(right).getType() == Material.STATIONARY_LAVA || seventhLevel5.getRelative(right).getType() == Material.STATIONARY_WATER) {
																																																																																																																																																																																																																																																																																																												if (seventhLevel5.getRelative(right).getRelative(right).getType() == Material.AIR || seventhLevel5.getRelative(right).getRelative(right).getType() == Material.STATIONARY_LAVA || seventhLevel5.getRelative(right).getRelative(right).getType() == Material.STATIONARY_WATER) {
																																																																																																																																																																																																																																																																																																													if (seventhLevel5.getRelative(right).getRelative(right).getRelative(right).getType() == Material.STAINED_CLAY) {
																																																																																																																																																																																																																																																																																																														if (seventhLevel5.getRelative(left).getType() == Material.AIR || seventhLevel5.getRelative(left).getType() == Material.STATIONARY_LAVA || seventhLevel5.getRelative(left).getType() == Material.STATIONARY_WATER) {
																																																																																																																																																																																																																																																																																																															if (seventhLevel5.getRelative(left).getRelative(left).getType() == Material.AIR || seventhLevel5.getRelative(left).getRelative(left).getType() == Material.STATIONARY_LAVA || seventhLevel5.getRelative(left).getRelative(left).getType() == Material.STATIONARY_WATER) {
																																																																																																																																																																																																																																																																																																																if (seventhLevel5.getRelative(left).getRelative(left).getRelative(left).getType() == Material.STAINED_CLAY) {
																																																																																																																																																																																																																																																																																																																	if (seventhLevel6.getType() == Material.STAINED_GLASS_PANE) {
																																																																																																																																																																																																																																																																																																																		if (seventhLevel6.getRelative(left).getType() == Material.STAINED_GLASS_PANE) {
																																																																																																																																																																																																																																																																																																																			if (seventhLevel6.getRelative(left).getRelative(left).getType() == Material.STAINED_CLAY) {
																																																																																																																																																																																																																																																																																																																				if (seventhLevel6.getRelative(right).getType() == Material.STAINED_GLASS_PANE) {
																																																																																																																																																																																																																																																																																																																					if (seventhLevel6.getRelative(right).getRelative(right).getType() == Material.STAINED_CLAY) {

																																																																																																																																																																																																																																																																																																																						if (eightLevel.getType() == Material.STAINED_GLASS_PANE) {
																																																																																																																																																																																																																																																																																																																							if (eightLevel.getRelative(right).getType() == Material.STAINED_GLASS_PANE) {
																																																																																																																																																																																																																																																																																																																								if (eightLevel.getRelative(right).getRelative(right).getType() == Material.STAINED_CLAY) {
																																																																																																																																																																																																																																																																																																																									if (eightLevel.getRelative(left).getType() == Material.STAINED_GLASS_PANE) {
																																																																																																																																																																																																																																																																																																																										if (eightLevel.getRelative(left).getRelative(left).getType() == Material.STAINED_CLAY) {
																																																																																																																																																																																																																																																																																																																											if (eightLevel1.getType() == Material.AIR || eightLevel1.getType() == Material.STATIONARY_LAVA || eightLevel1.getType() == Material.STATIONARY_WATER) {
																																																																																																																																																																																																																																																																																																																												if (eightLevel1.getRelative(right).getType() == Material.AIR || eightLevel1.getRelative(right).getType() == Material.STATIONARY_LAVA || eightLevel1.getRelative(right).getType() == Material.STATIONARY_WATER) {
																																																																																																																																																																																																																																																																																																																													if (eightLevel1.getRelative(right).getRelative(right).getType() == Material.AIR || eightLevel1.getRelative(right).getRelative(right).getType() == Material.STATIONARY_LAVA || eightLevel1.getRelative(right).getRelative(right).getType() == Material.STATIONARY_WATER) {
																																																																																																																																																																																																																																																																																																																														if (eightLevel1.getRelative(right).getRelative(right).getRelative(right).getType() == Material.STAINED_CLAY) {
																																																																																																																																																																																																																																																																																																																															if (eightLevel1.getRelative(left).getType() == Material.AIR || eightLevel1.getRelative(left).getType() == Material.STATIONARY_LAVA || eightLevel1.getRelative(left).getType() == Material.STATIONARY_WATER) {
																																																																																																																																																																																																																																																																																																																																if (eightLevel1.getRelative(left).getRelative(left).getType() == Material.AIR || eightLevel1.getRelative(left).getRelative(left).getType() == Material.STATIONARY_LAVA || eightLevel1.getRelative(left).getRelative(left).getType() == Material.STATIONARY_WATER) {
																																																																																																																																																																																																																																																																																																																																	if (eightLevel1.getRelative(left).getRelative(left).getRelative(left).getType() == Material.STAINED_CLAY) {
																																																																																																																																																																																																																																																																																																																																		if (eightLevel2.getType() == Material.AIR || eightLevel2.getType() == Material.STATIONARY_LAVA || eightLevel2.getType() == Material.STATIONARY_WATER) {
																																																																																																																																																																																																																																																																																																																																			if (eightLevel2.getRelative(right).getType() == Material.AIR || eightLevel2.getRelative(right).getType() == Material.STATIONARY_LAVA || eightLevel2.getRelative(right).getType() == Material.STATIONARY_WATER) {
																																																																																																																																																																																																																																																																																																																																				if (eightLevel2.getRelative(right).getRelative(right).getType() == Material.AIR || eightLevel2.getRelative(right).getRelative(right).getType() == Material.STATIONARY_LAVA || eightLevel2.getRelative(right).getRelative(right).getType() == Material.STATIONARY_WATER) {
																																																																																																																																																																																																																																																																																																																																					if (eightLevel2.getRelative(right).getRelative(right).getRelative(right).getType() == Material.STAINED_GLASS_PANE) {
																																																																																																																																																																																																																																																																																																																																						if (eightLevel2.getRelative(left).getType() == Material.AIR || eightLevel2.getRelative(left).getType() == Material.STATIONARY_LAVA || eightLevel2.getRelative(left).getType() == Material.STATIONARY_WATER) {
																																																																																																																																																																																																																																																																																																																																							if (eightLevel2.getRelative(left).getRelative(left).getType() == Material.AIR || eightLevel2.getRelative(left).getRelative(left).getType() == Material.STATIONARY_LAVA || eightLevel2.getRelative(left).getRelative(left).getType() == Material.STATIONARY_WATER) {
																																																																																																																																																																																																																																																																																																																																								if (eightLevel2.getRelative(left).getRelative(left).getRelative(left).getType() == Material.STAINED_GLASS_PANE) {
																																																																																																																																																																																																																																																																																																																																									if (eightLevel3.getType() == Material.AIR || eightLevel3.getType() == Material.STATIONARY_LAVA || eightLevel3.getType() == Material.STATIONARY_WATER) {
																																																																																																																																																																																																																																																																																																																																										if (eightLevel3.getRelative(right).getType() == Material.AIR || eightLevel3.getRelative(right).getType() == Material.STATIONARY_LAVA || eightLevel3.getRelative(right).getType() == Material.STATIONARY_WATER) {
																																																																																																																																																																																																																																																																																																																																											if (eightLevel3.getRelative(right).getRelative(right).getType() == Material.AIR || eightLevel3.getRelative(right).getRelative(right).getType() == Material.STATIONARY_LAVA || eightLevel3.getRelative(right).getRelative(right).getType() == Material.STATIONARY_WATER) {
																																																																																																																																																																																																																																																																																																																																												if (eightLevel3.getRelative(right).getRelative(right).getRelative(right).getType() == Material.STAINED_GLASS_PANE) {
																																																																																																																																																																																																																																																																																																																																													if (eightLevel3.getRelative(left).getType() == Material.AIR || eightLevel3.getRelative(left).getType() == Material.STATIONARY_LAVA || eightLevel3.getRelative(left).getType() == Material.STATIONARY_WATER) {
																																																																																																																																																																																																																																																																																																																																														if (eightLevel3.getRelative(left).getRelative(left).getType() == Material.AIR || eightLevel3.getRelative(left).getRelative(left).getType() == Material.STATIONARY_LAVA || eightLevel3.getRelative(left).getRelative(left).getType() == Material.STATIONARY_WATER) {
																																																																																																																																																																																																																																																																																																																																															if (eightLevel3.getRelative(left).getRelative(left).getRelative(left).getType() == Material.STAINED_GLASS_PANE) {
																																																																																																																																																																																																																																																																																																																																																if (eightLevel4.getType() == Material.AIR || eightLevel4.getType() == Material.STATIONARY_LAVA || eightLevel4.getType() == Material.STATIONARY_WATER) {
																																																																																																																																																																																																																																																																																																																																																	if (eightLevel4.getRelative(right).getType() == Material.AIR || eightLevel4.getRelative(right).getType() == Material.STATIONARY_LAVA || eightLevel4.getRelative(right).getType() == Material.STATIONARY_WATER) {
																																																																																																																																																																																																																																																																																																																																																		if (eightLevel4.getRelative(right).getRelative(right).getType() == Material.AIR || eightLevel4.getRelative(right).getRelative(right).getType() == Material.STATIONARY_LAVA || eightLevel4.getRelative(right).getRelative(right).getType() == Material.STATIONARY_WATER) {
																																																																																																																																																																																																																																																																																																																																																			if (eightLevel4.getRelative(right).getRelative(right).getRelative(right).getType() == Material.STAINED_GLASS_PANE) {
																																																																																																																																																																																																																																																																																																																																																				if (eightLevel4.getRelative(left).getType() == Material.AIR || eightLevel4.getRelative(left).getType() == Material.STATIONARY_LAVA || eightLevel4.getRelative(left).getType() == Material.STATIONARY_WATER) {
																																																																																																																																																																																																																																																																																																																																																					if (eightLevel4.getRelative(left).getRelative(left).getType() == Material.AIR || eightLevel4.getRelative(left).getRelative(left).getType() == Material.STATIONARY_LAVA || eightLevel4.getRelative(left).getRelative(left).getType() == Material.STATIONARY_WATER) {
																																																																																																																																																																																																																																																																																																																																																						if (eightLevel4.getRelative(left).getRelative(left).getRelative(left).getType() == Material.STAINED_GLASS_PANE) {
																																																																																																																																																																																																																																																																																																																																																							if (eightLevel5.getType() == Material.AIR || eightLevel5.getType() == Material.STATIONARY_LAVA || eightLevel5.getType() == Material.STATIONARY_WATER) {
																																																																																																																																																																																																																																																																																																																																																								if (eightLevel5.getRelative(right).getType() == Material.AIR || eightLevel5.getRelative(right).getType() == Material.STATIONARY_LAVA || eightLevel5.getRelative(right).getType() == Material.STATIONARY_WATER) {
																																																																																																																																																																																																																																																																																																																																																									if (eightLevel5.getRelative(right).getRelative(right).getType() == Material.AIR || eightLevel5.getRelative(right).getRelative(right).getType() == Material.STATIONARY_LAVA || eightLevel5.getRelative(right).getRelative(right).getType() == Material.STATIONARY_WATER) {
																																																																																																																																																																																																																																																																																																																																																										if (eightLevel5.getRelative(right).getRelative(right).getRelative(right).getType() == Material.STAINED_CLAY) {
																																																																																																																																																																																																																																																																																																																																																											if (eightLevel5.getRelative(left).getType() == Material.AIR || eightLevel5.getRelative(left).getType() == Material.STATIONARY_LAVA || eightLevel5.getRelative(left).getType() == Material.STATIONARY_WATER) {
																																																																																																																																																																																																																																																																																																																																																												if (eightLevel5.getRelative(left).getRelative(left).getType() == Material.AIR || eightLevel5.getRelative(left).getRelative(left).getType() == Material.STATIONARY_LAVA || eightLevel5.getRelative(left).getRelative(left).getType() == Material.STATIONARY_WATER) {
																																																																																																																																																																																																																																																																																																																																																													if (eightLevel5.getRelative(left).getRelative(left).getRelative(left).getType() == Material.STAINED_CLAY) {
																																																																																																																																																																																																																																																																																																																																																														if (eightLevel6.getType() == Material.STAINED_GLASS_PANE) {
																																																																																																																																																																																																																																																																																																																																																															if (eightLevel6.getRelative(left).getType() == Material.STAINED_GLASS_PANE) {
																																																																																																																																																																																																																																																																																																																																																																if (eightLevel6.getRelative(left).getRelative(left).getType() == Material.STAINED_CLAY) {
																																																																																																																																																																																																																																																																																																																																																																	if (eightLevel6.getRelative(right).getType() == Material.STAINED_GLASS_PANE) {
																																																																																																																																																																																																																																																																																																																																																																		if (eightLevel6.getRelative(right).getRelative(right).getType() == Material.STAINED_CLAY) {

																																																																																																																																																																																																																																																																																																																																																																			if (ninthLevel.getType() == Material.STAINED_CLAY) {
																																																																																																																																																																																																																																																																																																																																																																				if (ninthLevel.getRelative(right).getType() == Material.STAINED_CLAY) {
																																																																																																																																																																																																																																																																																																																																																																					if (ninthLevel.getRelative(left).getType() == Material.STAINED_CLAY) {
																																																																																																																																																																																																																																																																																																																																																																						if (ninthLevel1.getType() == Material.STAINED_CLAY) {
																																																																																																																																																																																																																																																																																																																																																																							if (ninthLevel1.getRelative(right).getType() == Material.STAINED_CLAY) {
																																																																																																																																																																																																																																																																																																																																																																								if (ninthLevel1.getRelative(right).getRelative(right).getType() == Material.STAINED_CLAY) {
																																																																																																																																																																																																																																																																																																																																																																									if (ninthLevel1.getRelative(left).getType() == Material.STAINED_CLAY) {
																																																																																																																																																																																																																																																																																																																																																																										if (ninthLevel1.getRelative(left).getRelative(left).getType() == Material.STAINED_CLAY) {
																																																																																																																																																																																																																																																																																																																																																																											if (ninthLevel2.getType() == Material.STAINED_CLAY) {
																																																																																																																																																																																																																																																																																																																																																																												if (ninthLevel2.getRelative(right).getType() == Material.STAINED_CLAY) {
																																																																																																																																																																																																																																																																																																																																																																													if (ninthLevel2.getRelative(right).getRelative(right).getType() == Material.STAINED_CLAY) {
																																																																																																																																																																																																																																																																																																																																																																														if (ninthLevel2.getRelative(right).getRelative(right).getRelative(right).getType() == Material.STAINED_CLAY) {
																																																																																																																																																																																																																																																																																																																																																																															if (ninthLevel2.getRelative(left).getType() == Material.STAINED_CLAY) {
																																																																																																																																																																																																																																																																																																																																																																																if (ninthLevel2.getRelative(left).getRelative(left).getType() == Material.STAINED_CLAY) {
																																																																																																																																																																																																																																																																																																																																																																																	if (ninthLevel2.getRelative(left).getRelative(left).getRelative(left).getType() == Material.STAINED_CLAY) {
																																																																																																																																																																																																																																																																																																																																																																																		if (ninthLevel3.getType() == Material.STAINED_CLAY) {
																																																																																																																																																																																																																																																																																																																																																																																			if (ninthLevel3.getRelative(right).getType() == Material.STAINED_CLAY) {
																																																																																																																																																																																																																																																																																																																																																																																				if (ninthLevel3.getRelative(right).getRelative(right).getType() == Material.STAINED_CLAY) {
																																																																																																																																																																																																																																																																																																																																																																																					if (ninthLevel3.getRelative(right).getRelative(right).getRelative(right).getType() == Material.STAINED_CLAY) {
																																																																																																																																																																																																																																																																																																																																																																																						if (ninthLevel3.getRelative(left).getType() == Material.STAINED_CLAY) {
																																																																																																																																																																																																																																																																																																																																																																																							if (ninthLevel3.getRelative(left).getRelative(left).getType() == Material.STAINED_CLAY) {
																																																																																																																																																																																																																																																																																																																																																																																								if (ninthLevel3.getRelative(left).getRelative(left).getRelative(left).getType() == Material.STAINED_CLAY) {
																																																																																																																																																																																																																																																																																																																																																																																									if (ninthLevel4.getType() == Material.STAINED_CLAY) {
																																																																																																																																																																																																																																																																																																																																																																																										if (ninthLevel4.getRelative(right).getType() == Material.STAINED_CLAY) {
																																																																																																																																																																																																																																																																																																																																																																																											if (ninthLevel4.getRelative(right).getRelative(right).getType() == Material.STAINED_CLAY) {
																																																																																																																																																																																																																																																																																																																																																																																												if (ninthLevel4.getRelative(right).getRelative(right).getRelative(right).getType() == Material.STAINED_CLAY) {
																																																																																																																																																																																																																																																																																																																																																																																													if (ninthLevel4.getRelative(left).getType() == Material.STAINED_CLAY) {
																																																																																																																																																																																																																																																																																																																																																																																														if (ninthLevel4.getRelative(left).getRelative(left).getType() == Material.STAINED_CLAY) {
																																																																																																																																																																																																																																																																																																																																																																																															if (ninthLevel4.getRelative(left).getRelative(left).getRelative(left).getType() == Material.STAINED_CLAY) {
																																																																																																																																																																																																																																																																																																																																																																																																if (ninthLevel5.getType() == Material.STAINED_CLAY) {
																																																																																																																																																																																																																																																																																																																																																																																																	if (ninthLevel5.getRelative(right).getType() == Material.STAINED_CLAY) {
																																																																																																																																																																																																																																																																																																																																																																																																		if (ninthLevel5.getRelative(right).getRelative(right).getType() == Material.STAINED_CLAY) {
																																																																																																																																																																																																																																																																																																																																																																																																			if (ninthLevel5.getRelative(left).getType() == Material.STAINED_CLAY) {
																																																																																																																																																																																																																																																																																																																																																																																																				if (ninthLevel5.getRelative(left).getRelative(left).getType() == Material.STAINED_CLAY) {
																																																																																																																																																																																																																																																																																																																																																																																																					if (ninthLevel6.getType() == Material.STAINED_CLAY) {
																																																																																																																																																																																																																																																																																																																																																																																																						if (ninthLevel6.getRelative(right).getType() == Material.STAINED_CLAY) {
																																																																																																																																																																																																																																																																																																																																																																																																							if (ninthLevel6.getRelative(left).getType() == Material.STAINED_CLAY) {

																																																																																																																																																																																																																																																																																																																																																																																																								return secondLevel3;

																																																																																																																																																																																																																																																																																																																																																																																																							}

																																																																																																																																																																																																																																																																																																																																																																																																						}
																																																																																																																																																																																																																																																																																																																																																																																																					}
																																																																																																																																																																																																																																																																																																																																																																																																				}
																																																																																																																																																																																																																																																																																																																																																																																																			}
																																																																																																																																																																																																																																																																																																																																																																																																		}
																																																																																																																																																																																																																																																																																																																																																																																																	}
																																																																																																																																																																																																																																																																																																																																																																																																}
																																																																																																																																																																																																																																																																																																																																																																																															}
																																																																																																																																																																																																																																																																																																																																																																																														}
																																																																																																																																																																																																																																																																																																																																																																																													}
																																																																																																																																																																																																																																																																																																																																																																																												}
																																																																																																																																																																																																																																																																																																																																																																																											}
																																																																																																																																																																																																																																																																																																																																																																																										}
																																																																																																																																																																																																																																																																																																																																																																																									}
																																																																																																																																																																																																																																																																																																																																																																																								}
																																																																																																																																																																																																																																																																																																																																																																																							}
																																																																																																																																																																																																																																																																																																																																																																																						}
																																																																																																																																																																																																																																																																																																																																																																																					}
																																																																																																																																																																																																																																																																																																																																																																																				}
																																																																																																																																																																																																																																																																																																																																																																																			}
																																																																																																																																																																																																																																																																																																																																																																																		}
																																																																																																																																																																																																																																																																																																																																																																																	}
																																																																																																																																																																																																																																																																																																																																																																																}
																																																																																																																																																																																																																																																																																																																																																																															}
																																																																																																																																																																																																																																																																																																																																																																														}
																																																																																																																																																																																																																																																																																																																																																																													}
																																																																																																																																																																																																																																																																																																																																																																												}
																																																																																																																																																																																																																																																																																																																																																																											}
																																																																																																																																																																																																																																																																																																																																																																										}
																																																																																																																																																																																																																																																																																																																																																																									}
																																																																																																																																																																																																																																																																																																																																																																								}
																																																																																																																																																																																																																																																																																																																																																																							}
																																																																																																																																																																																																																																																																																																																																																																						}
																																																																																																																																																																																																																																																																																																																																																																					}
																																																																																																																																																																																																																																																																																																																																																																				}
																																																																																																																																																																																																																																																																																																																																																																			}
																																																																																																																																																																																																																																																																																																																																																																		}
																																																																																																																																																																																																																																																																																																																																																																	}
																																																																																																																																																																																																																																																																																																																																																																}
																																																																																																																																																																																																																																																																																																																																																															}
																																																																																																																																																																																																																																																																																																																																																														}
																																																																																																																																																																																																																																																																																																																																																													}
																																																																																																																																																																																																																																																																																																																																																												}
																																																																																																																																																																																																																																																																																																																																																											}
																																																																																																																																																																																																																																																																																																																																																										}
																																																																																																																																																																																																																																																																																																																																																									}
																																																																																																																																																																																																																																																																																																																																																								}
																																																																																																																																																																																																																																																																																																																																																							}
																																																																																																																																																																																																																																																																																																																																																						}
																																																																																																																																																																																																																																																																																																																																																					}
																																																																																																																																																																																																																																																																																																																																																				}
																																																																																																																																																																																																																																																																																																																																																			}
																																																																																																																																																																																																																																																																																																																																																		}
																																																																																																																																																																																																																																																																																																																																																	}
																																																																																																																																																																																																																																																																																																																																																}
																																																																																																																																																																																																																																																																																																																																															}
																																																																																																																																																																																																																																																																																																																																														}
																																																																																																																																																																																																																																																																																																																																													}
																																																																																																																																																																																																																																																																																																																																												}
																																																																																																																																																																																																																																																																																																																																											}
																																																																																																																																																																																																																																																																																																																																										}
																																																																																																																																																																																																																																																																																																																																									}
																																																																																																																																																																																																																																																																																																																																								}
																																																																																																																																																																																																																																																																																																																																							}
																																																																																																																																																																																																																																																																																																																																						}
																																																																																																																																																																																																																																																																																																																																					}
																																																																																																																																																																																																																																																																																																																																				}
																																																																																																																																																																																																																																																																																																																																			}
																																																																																																																																																																																																																																																																																																																																		}
																																																																																																																																																																																																																																																																																																																																	}
																																																																																																																																																																																																																																																																																																																																}
																																																																																																																																																																																																																																																																																																																															}
																																																																																																																																																																																																																																																																																																																														}
																																																																																																																																																																																																																																																																																																																													}
																																																																																																																																																																																																																																																																																																																												}
																																																																																																																																																																																																																																																																																																																											}
																																																																																																																																																																																																																																																																																																																										}
																																																																																																																																																																																																																																																																																																																									}
																																																																																																																																																																																																																																																																																																																								}
																																																																																																																																																																																																																																																																																																																							}
																																																																																																																																																																																																																																																																																																																						}
																																																																																																																																																																																																																																																																																																																					}
																																																																																																																																																																																																																																																																																																																				}
																																																																																																																																																																																																																																																																																																																			}
																																																																																																																																																																																																																																																																																																																		}
																																																																																																																																																																																																																																																																																																																	}
																																																																																																																																																																																																																																																																																																																}
																																																																																																																																																																																																																																																																																																															}
																																																																																																																																																																																																																																																																																																														}
																																																																																																																																																																																																																																																																																																													}
																																																																																																																																																																																																																																																																																																												}
																																																																																																																																																																																																																																																																																																											}
																																																																																																																																																																																																																																																																																																										}

																																																																																																																																																																																																																																																																																																									}
																																																																																																																																																																																																																																																																																																								}
																																																																																																																																																																																																																																																																																																							}
																																																																																																																																																																																																																																																																																																						}
																																																																																																																																																																																																																																																																																																					}
																																																																																																																																																																																																																																																																																																				}
																																																																																																																																																																																																																																																																																																			}
																																																																																																																																																																																																																																																																																																		}
																																																																																																																																																																																																																																																																																																	}
																																																																																																																																																																																																																																																																																																}
																																																																																																																																																																																																																																																																																															}
																																																																																																																																																																																																																																																																																														}
																																																																																																																																																																																																																																																																																													}
																																																																																																																																																																																																																																																																																												}
																																																																																																																																																																																																																																																																																											}
																																																																																																																																																																																																																																																																																										}
																																																																																																																																																																																																																																																																																									}
																																																																																																																																																																																																																																																																																								}
																																																																																																																																																																																																																																																																																							}
																																																																																																																																																																																																																																																																																						}
																																																																																																																																																																																																																																																																																					}
																																																																																																																																																																																																																																																																																				}
																																																																																																																																																																																																																																																																																			}
																																																																																																																																																																																																																																																																																		}
																																																																																																																																																																																																																																																																																	}
																																																																																																																																																																																																																																																																																}
																																																																																																																																																																																																																																																																															}
																																																																																																																																																																																																																																																																														}
																																																																																																																																																																																																																																																																													}
																																																																																																																																																																																																																																																																												}
																																																																																																																																																																																																																																																																											}
																																																																																																																																																																																																																																																																										}
																																																																																																																																																																																																																																																																									}
																																																																																																																																																																																																																																																																								}
																																																																																																																																																																																																																																																																							}
																																																																																																																																																																																																																																																																						}
																																																																																																																																																																																																																																																																					}
																																																																																																																																																																																																																																																																				}
																																																																																																																																																																																																																																																																			}
																																																																																																																																																																																																																																																																		}
																																																																																																																																																																																																																																																																	}
																																																																																																																																																																																																																																																																}
																																																																																																																																																																																																																																																															}
																																																																																																																																																																																																																																																														}
																																																																																																																																																																																																																																																													}

																																																																																																																																																																																																																																																												}
																																																																																																																																																																																																																																																											}
																																																																																																																																																																																																																																																										}
																																																																																																																																																																																																																																																									}
																																																																																																																																																																																																																																																								}
																																																																																																																																																																																																																																																							}
																																																																																																																																																																																																																																																						}
																																																																																																																																																																																																																																																					}
																																																																																																																																																																																																																																																				}
																																																																																																																																																																																																																																																			}
																																																																																																																																																																																																																																																		}
																																																																																																																																																																																																																																																	}
																																																																																																																																																																																																																																																}
																																																																																																																																																																																																																																															}
																																																																																																																																																																																																																																														}
																																																																																																																																																																																																																																													}
																																																																																																																																																																																																																																												}
																																																																																																																																																																																																																																											}
																																																																																																																																																																																																																																										}
																																																																																																																																																																																																																																									}
																																																																																																																																																																																																																																								}
																																																																																																																																																																																																																																							}
																																																																																																																																																																																																																																						}
																																																																																																																																																																																																																																					}
																																																																																																																																																																																																																																				}
																																																																																																																																																																																																																																			}
																																																																																																																																																																																																																																		}
																																																																																																																																																																																																																																	}
																																																																																																																																																																																																																																}
																																																																																																																																																																																																																															}
																																																																																																																																																																																																																														}
																																																																																																																																																																																																																													}
																																																																																																																																																																																																																												}
																																																																																																																																																																																																																											}
																																																																																																																																																																																																																										}
																																																																																																																																																																																																																									}
																																																																																																																																																																																																																								}
																																																																																																																																																																																																																							}
																																																																																																																																																																																																																						}
																																																																																																																																																																																																																					}
																																																																																																																																																																																																																				}
																																																																																																																																																																																																																			}
																																																																																																																																																																																																																		}
																																																																																																																																																																																																																	}
																																																																																																																																																																																																																}

																																																																																																																																																																																																															}
																																																																																																																																																																																																														}
																																																																																																																																																																																																													}
																																																																																																																																																																																																												}
																																																																																																																																																																																																											}
																																																																																																																																																																																																										}
																																																																																																																																																																																																									}
																																																																																																																																																																																																								}
																																																																																																																																																																																																							}
																																																																																																																																																																																																						}
																																																																																																																																																																																																					}
																																																																																																																																																																																																				}
																																																																																																																																																																																																			}
																																																																																																																																																																																																		}
																																																																																																																																																																																																	}
																																																																																																																																																																																																}
																																																																																																																																																																																															}
																																																																																																																																																																																														}
																																																																																																																																																																																													}
																																																																																																																																																																																												}
																																																																																																																																																																																											}
																																																																																																																																																																																										}
																																																																																																																																																																																									}
																																																																																																																																																																																								}
																																																																																																																																																																																							}
																																																																																																																																																																																						}
																																																																																																																																																																																					}
																																																																																																																																																																																				}
																																																																																																																																																																																			}
																																																																																																																																																																																		}
																																																																																																																																																																																	}
																																																																																																																																																																																}
																																																																																																																																																																															}
																																																																																																																																																																														}
																																																																																																																																																																													}
																																																																																																																																																																												}
																																																																																																																																																																											}
																																																																																																																																																																										}
																																																																																																																																																																									}
																																																																																																																																																																								}
																																																																																																																																																																							}
																																																																																																																																																																						}
																																																																																																																																																																					}
																																																																																																																																																																				}
																																																																																																																																																																			}

																																																																																																																																																																		}
																																																																																																																																																																	}
																																																																																																																																																																}
																																																																																																																																																															}
																																																																																																																																																														}
																																																																																																																																																													}
																																																																																																																																																												}
																																																																																																																																																											}
																																																																																																																																																										}
																																																																																																																																																									}
																																																																																																																																																								}
																																																																																																																																																							}
																																																																																																																																																						}
																																																																																																																																																					}
																																																																																																																																																				}
																																																																																																																																																			}
																																																																																																																																																		}
																																																																																																																																																	}
																																																																																																																																																}
																																																																																																																																															}
																																																																																																																																														}
																																																																																																																																													}
																																																																																																																																												}
																																																																																																																																											}
																																																																																																																																										}
																																																																																																																																									}
																																																																																																																																								}
																																																																																																																																							}
																																																																																																																																						}
																																																																																																																																					}
																																																																																																																																				}
																																																																																																																																			}
																																																																																																																																		}
																																																																																																																																	}
																																																																																																																																}
																																																																																																																															}
																																																																																																																														}
																																																																																																																													}
																																																																																																																												}
																																																																																																																											}
																																																																																																																										}
																																																																																																																									}
																																																																																																																								}
																																																																																																																							}
																																																																																																																						}

																																																																																																																					}
																																																																																																																				}
																																																																																																																			}
																																																																																																																		}
																																																																																																																	}
																																																																																																																}
																																																																																																															}
																																																																																																														}
																																																																																																													}
																																																																																																												}
																																																																																																											}
																																																																																																										}
																																																																																																									}
																																																																																																								}
																																																																																																							}
																																																																																																						}
																																																																																																					}
																																																																																																				}
																																																																																																			}
																																																																																																		}
																																																																																																	}
																																																																																																}
																																																																																															}
																																																																																														}
																																																																																													}
																																																																																												}
																																																																																											}
																																																																																										}
																																																																																									}
																																																																																								}
																																																																																							}
																																																																																						}
																																																																																					}
																																																																																				}
																																																																																			}
																																																																																		}
																																																																																	}
																																																																																}
																																																																															}
																																																																														}
																																																																													}
																																																																												}
																																																																											}
																																																																										}
																																																																									}

																																																																								}
																																																																							}
																																																																						}
																																																																					}
																																																																				}
																																																																			}
																																																																		}
																																																																	}
																																																																}
																																																															}
																																																														}
																																																													}
																																																												}
																																																											}
																																																										}
																																																									}
																																																								}
																																																							}
																																																						}
																																																					}
																																																				}
																																																			}
																																																		}
																																																	}
																																																}
																																															}
																																														}
																																													}
																																												}
																																											}
																																										}
																																									}
																																								}
																																							}
																																						}
																																					}
																																				}
																																			}
																																		}
																																	}
																																}
																															}
																														}
																													}
																												}

																											}
																										}
																									}
																								}
																							}
																						}
																					}
																				}
																			}
																		}
																	}
																}
															}
														}
													}
												}
											}
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
