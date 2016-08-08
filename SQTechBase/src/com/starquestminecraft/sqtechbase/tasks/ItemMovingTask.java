package com.starquestminecraft.sqtechbase.tasks;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.Chest;
import org.bukkit.block.Dispenser;
import org.bukkit.block.Dropper;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitScheduler;

import com.dibujaron.cardboardbox.CardboardBox;
import com.starquestminecraft.sqtechbase.SQTechBase;
import com.starquestminecraft.sqtechbase.objects.GUIBlock;
import com.starquestminecraft.sqtechbase.objects.Machine;
import com.starquestminecraft.sqtechbase.objects.Network;
import com.starquestminecraft.sqtechbase.util.BlockUtils;
import com.starquestminecraft.sqtechbase.util.InventoryUtils;

import net.md_5.bungee.api.ChatColor;

public class ItemMovingTask extends Thread {

	public void run() {
		
		BukkitScheduler scheduler = Bukkit.getServer().getScheduler();
		
		scheduler.scheduleSyncRepeatingTask(SQTechBase.getPluginMain(), new Runnable() {
			
			@Override
			public void run() {
				
				for (Network network : SQTechBase.networks) {
					
					HashMap<ItemStack, List<GUIBlock>> exports = new HashMap<ItemStack, List<GUIBlock>>();
					HashMap<ItemStack, List<GUIBlock>> imports = new HashMap<ItemStack, List<GUIBlock>>();
					
					for (GUIBlock guiBlock : network.getGUIBlocks()) {
						
						for (ItemStack export : guiBlock.getExports()) {
							
							if (exports.containsKey(export)) {
								
								List<GUIBlock> guiBlocks = exports.get(export);
								guiBlocks.add(guiBlock);
								
								exports.remove(export);
								exports.put(export, guiBlocks);
								
							} else {
								
								List<GUIBlock> guiBlocks = new ArrayList<GUIBlock>();
								guiBlocks.add(guiBlock);
								
								exports.put(export, guiBlocks);
								
							}
							
						}
						
						if (guiBlock.exportAll) {
							
							List<ItemStack> items = new ArrayList<ItemStack>();
							
							for (Block block : BlockUtils.getAdjacentBlocks(guiBlock.getLocation().getBlock())) {
								
								Inventory inventory = null;
								
								if (block.getType().equals(Material.CHEST)) {
									
									Chest chest = (Chest) block.getState();
									
									inventory = chest.getBlockInventory();
									
									for (Block possibleChest : BlockUtils.getAdjacentBlocks(block)) {
										
										Inventory secondInventory = null;
										
										if (possibleChest.getLocation().getBlockY() == block.getLocation().getBlockY()) {
										
											if (possibleChest.getType().equals(Material.CHEST)) {
												
												Chest secondChest = (Chest) possibleChest.getState();
												
												secondInventory = secondChest.getBlockInventory();
												
											}
										
										}
										
										if (secondInventory != null) {
											
											for (ItemStack itemStack : secondInventory.getContents()) {
												
												if (itemStack != null) {
													
													boolean contains = false;
													
													ItemStack newItemStack = (new CardboardBox(itemStack)).unbox();
													newItemStack.setAmount(1);
													
													for (ItemStack item : items) {

														if (item.equals(newItemStack)) {
															
															contains = true;
															
														}
														
													}
													
													if (!contains) {
														
														items.add(newItemStack);
														
													}
													
												}
												
											}
											
										}
										
									}
									
								}
								
								if (block.getType().equals(Material.TRAPPED_CHEST)) {
									
									Chest chest = (Chest) block.getState();
									
									inventory = chest.getBlockInventory();
									
									for (Block possibleChest : BlockUtils.getAdjacentBlocks(block)) {
										
										Inventory secondInventory = null;
										
										if (possibleChest.getLocation().getBlockY() == block.getLocation().getBlockY()) {
										
											if (possibleChest.getType().equals(Material.TRAPPED_CHEST)) {
												
												Chest secondChest = (Chest) possibleChest.getState();
												
												secondInventory = secondChest.getBlockInventory();
												
											}
										
										}
										
										if (secondInventory != null) {
											
											for (ItemStack itemStack : secondInventory.getContents()) {
												
												if (itemStack != null) {
													
													boolean contains = false;
													
													ItemStack newItemStack = (new CardboardBox(itemStack)).unbox();
													newItemStack.setAmount(1);
													
													for (ItemStack item : items) {
														
														if (newItemStack.equals(item)) {
															
															contains = true;
															
														}
														
													}
													
													if (!contains) {
														
														items.add(newItemStack);
														
													}
													
												}
												
											}
											
										}
										
									}
									
								}
								
								if (block.getType().equals(Material.DROPPER)) {
									
									Dropper dropper = (Dropper) block.getState();
									
									inventory = dropper.getInventory();
									
								}
								
								if (block.getType().equals(Material.DISPENSER)) {
									
									Dispenser dispenser = (Dispenser) block.getState();
									
									inventory = dispenser.getInventory();
									
								}
								
								if (inventory != null) {
									
									for (ItemStack itemStack : inventory.getContents()) {
										
										if (itemStack != null) {
											
											boolean contains = false;
											
											ItemStack newItemStack = (new CardboardBox(itemStack)).unbox();
											newItemStack.setAmount(1);
											
											for (ItemStack item : items) {
												
												if (item.equals(newItemStack)) {
													
													contains = true;
													
												}
												
											}
											
											if (!contains) {
												
												items.add(newItemStack);
												
											}
											
										}
										
									}
									
								}
								
							}
							
							for (ItemStack item : items) {
								
								if (exports.containsKey(item)) {
									
									List<GUIBlock> guiBlocks = exports.get(item);
									
									if (!guiBlocks.contains(guiBlock)) {
										
										guiBlocks.add(guiBlock);
										
										exports.remove(item);
										exports.put(item, guiBlocks);
										
									}

								} else {
									
									List<GUIBlock> guiBlocks = new ArrayList<GUIBlock>();
									guiBlocks.add(guiBlock);
									
									exports.put(item, guiBlocks);
									
								}
								
							}
							
						}
						
						for (ItemStack importItem : guiBlock.getImports()) {
							
							if (imports.containsKey(importItem)) {
								
								List<GUIBlock> guiBlocks = imports.get(importItem);
								guiBlocks.add(guiBlock);
								
								imports.remove(importItem);
								imports.put(importItem, guiBlocks);
								
							} else {
								
								List<GUIBlock> guiBlocks = new ArrayList<GUIBlock>();
								guiBlocks.add(guiBlock);
								
								imports.put(importItem, guiBlocks);
								
							}
							
						}
						
					}
					
					for (ItemStack export : exports.keySet()) {
						
						for (ItemStack importItem : imports.keySet()) {
							
							if (export.equals(importItem)) {
								
								int spaceLeft = 0;
								
								List<Inventory> inventories = new ArrayList<Inventory>();
								List<Machine> machines = new ArrayList<Machine>();
								List<ItemStack> items = new ArrayList<ItemStack>();
								
								for (GUIBlock guiBlock : imports.get(importItem)) {
									
									for (Machine machine : SQTechBase.machines) {
										
										if (machine.getGUIBlock() == guiBlock) {
											
											int space = machine.getMachineType().getSpaceLeft(machine, export);
											
											if (space > 0) {
												
												spaceLeft = spaceLeft + machine.getMachineType().getSpaceLeft(machine, export);

												machines.add(machine);
												
											}

										}
										
									}
									
									for (Block block : BlockUtils.getAdjacentBlocks(guiBlock.getLocation().getBlock())) {
										
										Inventory inventory = null;
										Inventory secondInventory = null;
										
										if (block.getType().equals(Material.CHEST)) {
											
											Chest chest = (Chest) block.getState();
											
											inventory = chest.getBlockInventory();
											
											for (Block possibleChest : BlockUtils.getAdjacentBlocks(block)) {
												
												if (possibleChest.getLocation().getBlockY() == block.getLocation().getBlockY()) {
													
													if (possibleChest.getType().equals(Material.CHEST)) {
														
														Chest secondChest = (Chest) possibleChest.getState();
														
														secondInventory = secondChest.getBlockInventory();
														
													}
													
												}

											}
											
										}
										
										if (block.getType().equals(Material.TRAPPED_CHEST)) {
											
											Chest chest = (Chest) block.getState();
											
											inventory = chest.getBlockInventory();
											
											for (Block possibleChest : BlockUtils.getAdjacentBlocks(block)) {
												
												if (possibleChest.getLocation().getBlockY() == block.getLocation().getBlockY()) {
													
													if (possibleChest.getType().equals(Material.TRAPPED_CHEST)) {
														
														Chest secondChest = (Chest) possibleChest.getState();
														
														secondInventory = secondChest.getBlockInventory();
														
													}
													
												}

											}
											
										}
										
										if (block.getType().equals(Material.DROPPER)) {
											
											Dropper dropper = (Dropper) block.getState();
											
											inventory = dropper.getInventory();
											
										}
										
										if (block.getType().equals(Material.DISPENSER)) {
											
											Dispenser dispenser = (Dispenser) block.getState();
											
											inventory = dispenser.getInventory();
											
										}
										
										if (inventory != null) {
											
											if (!inventories.contains(inventory)) {
											
												for (ItemStack itemStack : inventory.getContents()) {
													
													if (itemStack == null) {
														
														spaceLeft = spaceLeft + 64;

														if (!inventories.contains(inventory)) {
														
															inventories.add(inventory);
															
														}
														
													} else {
														
														ItemStack newItemStack = (new CardboardBox(itemStack)).unbox();
														newItemStack.setAmount(export.getAmount());
														
														if (newItemStack.equals(export)) {
													
															spaceLeft = spaceLeft + (itemStack.getMaxStackSize() - itemStack.getAmount());
															
															if (itemStack.getMaxStackSize() != itemStack.getAmount()) {
																
																if (!inventories.contains(inventory)) {
																	
																	inventories.add(inventory);
																	
																}
																
															}
															
														}
														

													}
													
												}
												
												if (!inventories.contains(inventory)) {
													
													if (secondInventory != null) {
														
														if (!inventories.contains(secondInventory)) {
														
															for (ItemStack itemStack : secondInventory.getContents()) {
																
																if (itemStack == null) {
																	
																	spaceLeft = spaceLeft + 64;
																	
																	if (!inventories.contains(secondInventory)) {
																		
																		inventories.add(secondInventory);
																		
																	}
																	
																} else {
																	
																	ItemStack newItemStack = (new CardboardBox(itemStack)).unbox();
																	newItemStack.setAmount(export.getAmount());
																	
																	if (newItemStack.equals(export)) {
																		
																		spaceLeft = spaceLeft + (itemStack.getMaxStackSize() - itemStack.getAmount());
																		
																		if ((itemStack.getMaxStackSize() - itemStack.getAmount()) > 1) {
																			
																			if (!inventories.contains(secondInventory)) {
																				
																				inventories.add(secondInventory);
																				
																			}
																			
																		}
																	
																	}

																}
																
															}
															
														}
														
													}
													
												}
												
											}
											
										}
										
									}
									
								}
								
								for (GUIBlock guiBlock : exports.get(export)) {
									
									for (Block block : BlockUtils.getAdjacentBlocks(guiBlock.getLocation().getBlock())) {
										
										Inventory inventory = null;
										Inventory secondInventory = null;
										
										if (block.getType().equals(Material.CHEST)) {
											
											Chest chest = (Chest) block.getState();
											
											inventory = chest.getBlockInventory();
											
											for (Block possibleChest : BlockUtils.getAdjacentBlocks(block)) {
												
												if (possibleChest.getLocation().getBlockY() == block.getLocation().getBlockY()) {
												
													if (possibleChest.getType().equals(Material.CHEST)) {
														
														Chest secondChest = (Chest) possibleChest.getState();
														
														secondInventory = secondChest.getBlockInventory();
														
													}
													
												}

											}
											
										}
										
										if (block.getType().equals(Material.TRAPPED_CHEST)) {
											
											Chest chest = (Chest) block.getState();
											
											inventory = chest.getBlockInventory();
											
											for (Block possibleChest : BlockUtils.getAdjacentBlocks(block)) {
												
												if (possibleChest.getLocation().getBlockY() == block.getLocation().getBlockY()) {
												
													if (possibleChest.getType().equals(Material.TRAPPED_CHEST)) {
														
														Chest secondChest = (Chest) possibleChest.getState();
														
														secondInventory = secondChest.getBlockInventory();
														
													}
													
												}

											}
											
										}
										
										if (block.getType().equals(Material.DROPPER)) {
											
											Dropper dropper = (Dropper) block.getState();
											
											inventory = dropper.getInventory();
											
										}
										
										if (block.getType().equals(Material.DISPENSER)) {
											
											Dispenser dispenser = (Dispenser) block.getState();
											
											inventory = dispenser.getInventory();
											
										}
										
										int itemsLeft = 16;
										
										if (inventory != null) {
											
											ItemStack[] contents = inventory.getContents();
											
											for (int i = 0; i < contents.length; i ++) {
												
												if (itemsLeft > 0) {
													
													ItemStack itemStack = inventory.getContents()[i];
													
													if (itemStack != null) {
														
														ItemStack newItemStack = (new CardboardBox(itemStack)).unbox();
														newItemStack.setAmount(export.getAmount());
														
														if (newItemStack.equals(export)) {
															
															if (spaceLeft >= itemsLeft) {
																
																if (itemStack.getAmount() >= itemsLeft) {
																	
																	spaceLeft = spaceLeft - itemsLeft;
																	
																	int amount = itemStack.getAmount() - itemsLeft;
																	
																	itemStack.setAmount(1);
																	
																	for (int j = 0; j < itemsLeft; j ++) {
																		
																		items.add(itemStack.clone());
																		
																	}
																	
																	itemStack.setAmount(amount);
																	
																	if (amount == 0) {
																		
																		itemStack.setType(Material.AIR);
																		
																	}
																	
																	itemsLeft = 0;
																	
																} else {
																	
																	spaceLeft = spaceLeft - itemStack.getAmount();
																	itemsLeft = itemsLeft - itemStack.getAmount();
																	
																	int amount = itemStack.getAmount();
																	
																	itemStack.setAmount(1);
																	
																	for (int j = 0; j < amount; j ++) {
																		
																		items.add(itemStack.clone());
																		
																	}
																		
																	itemStack.setType(Material.AIR);
																	
																}
																
															} else {
																
																if (itemStack.getAmount() >= spaceLeft) {
																	
																	int amount = itemStack.getAmount() - spaceLeft;
																	
																	itemStack.setAmount(1);
																	
																	for (int j = 0; j < spaceLeft; j ++) {
																		
																		items.add(itemStack.clone());
																		
																	}

																	itemStack.setAmount(amount);
																	
																	if (amount == 0) {
																		
																		itemStack.setType(Material.AIR);
																		
																	}
																	
																	spaceLeft = 0;
																	itemsLeft = 0;
																	
																} else {

																	itemStack.setAmount(1);
																	
																	for (int j = 0; j < spaceLeft; j ++) {
																		
																		items.add(itemStack.clone());
																		
																	}
																	
																	itemStack.setType(Material.AIR);
																	
																	spaceLeft = spaceLeft - itemStack.getAmount();
																	itemsLeft = itemsLeft - itemStack.getAmount();
																	
																}
																
															}
															
														}
														
													}
													
													contents[i] = itemStack;
													
												}

											}
											
											inventory.setContents(contents);
											
										}
										
										if (itemsLeft > 0) {
											
											if (secondInventory != null) {
												
												ItemStack[] contents = secondInventory.getContents();

												for (int i = 0; i < contents.length; i ++) {
													
													if (itemsLeft > 0) {
														
														ItemStack itemStack = secondInventory.getContents()[i];
														
														if (itemStack != null) {
															
															ItemStack newItemStack = (new CardboardBox(itemStack)).unbox();
															newItemStack.setAmount(export.getAmount());
															
															if (newItemStack.equals(export)) {
																
																if (spaceLeft >= itemsLeft) {
																	
																	if (itemStack.getAmount() >= itemsLeft) {
																		
																		spaceLeft = spaceLeft - itemsLeft;
																		
																		int amount = itemStack.getAmount() - itemsLeft;
																		
																		itemStack.setAmount(1);
																		
																		for (int j = 0; j < itemsLeft; j ++) {
																			
																			items.add(itemStack.clone());
																			
																		}
																		
																		itemStack.setAmount(amount);
																		
																		if (amount == 0) {
																			
																			itemStack.setType(Material.AIR);
																			
																		}
																		
																		itemsLeft = 0;
																		
																	} else {
																		
																		spaceLeft = spaceLeft - itemStack.getAmount();
																		itemsLeft = itemsLeft - itemStack.getAmount();
																		
																		int amount = itemStack.getAmount();
																		
																		itemStack.setAmount(1);
																		
																		for (int j = 0; j < amount; j ++) {
																			
																			items.add(itemStack.clone());
																			
																		}
																			
																		itemStack.setType(Material.AIR);
																		
																	}
																	
																} else {
																	
																	if (itemStack.getAmount() >= spaceLeft) {
																		
																		int amount = itemStack.getAmount() - spaceLeft;
																		
																		itemStack.setAmount(1);
																		
																		for (int j = 0; j < spaceLeft; j ++) {
																			
																			items.add(itemStack.clone());
																			
																		}

																		itemStack.setAmount(amount);
																		
																		if (amount == 0) {
																			
																			itemStack.setType(Material.AIR);
																			
																		}
																		
																		spaceLeft = 0;
																		itemsLeft = 0;
																		
																	} else {

																		itemStack.setAmount(1);
																		
																		for (int j = 0; j < spaceLeft; j ++) {
																			
																			items.add(itemStack.clone());
																			
																		}
																		
																		itemStack.setType(Material.AIR);
																		
																		spaceLeft = spaceLeft - itemStack.getAmount();
																		itemsLeft = itemsLeft - itemStack.getAmount();
																		
																	}
																	
																}
																
															}
															
														}
														
														contents[i] = itemStack;
														
													}

												}
												
												secondInventory.setContents(contents);
												
											}
											
										}

									}
									
								}
								
								int currentInventory = 0;
								
								for (int i = 0; i < items.size(); i ++) {
									
									if (inventories.size() > currentInventory) {
										
										inventories.get(currentInventory).addItem(items.get(i));
										
										int currentSpaceLeft = 0;
										
										for (ItemStack itemStack : inventories.get(currentInventory).getContents()) {
											
											if (itemStack == null) {
												
												currentSpaceLeft = currentSpaceLeft + 64;
												
											} else {
												
												ItemStack newItemStack = (new CardboardBox(itemStack)).unbox();
												newItemStack.setAmount(export.getAmount());
												
												if (newItemStack.equals(export)) {
													
													currentSpaceLeft = currentSpaceLeft + (itemStack.getMaxStackSize() - itemStack.getAmount());
													
												}

											}
											
										}

										if (currentSpaceLeft == 0) {
											
											inventories.remove(currentInventory);

										} else {
											
											currentInventory ++;
											
										}
										
									} else {
										
										Machine machine = machines.get(currentInventory - inventories.size());
										
										machine.getMachineType().sendItems(machine, items.get(i));
										
										if (machine.getMachineType().getSpaceLeft(machine, export) == 0) {
											
											machines.remove(machine);

										} else {
											
											currentInventory ++;
											
										}
										
									}
									
									if ((inventories.size() + machines.size()) == currentInventory) {
										
										currentInventory = 0;
										
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
